package com.bnb.binh.skyintertainment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.adapters.AdapterChat;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.models.ModelChat;
import com.bnb.binh.skyintertainment.models.ModelUser;
import com.bnb.binh.skyintertainment.notifications.APIService;
import com.bnb.binh.skyintertainment.notifications.Client;
import com.bnb.binh.skyintertainment.notifications.Data;
import com.bnb.binh.skyintertainment.notifications.Response;
import com.bnb.binh.skyintertainment.notifications.Sender;
import com.bnb.binh.skyintertainment.notifications.Token;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class ChatActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CircleImageView profileImage;
    private TextView nameTv;
    private TextView userStatusTv;
    private EditText messengeEt;
    private ImageButton btnSend;
    private ImageButton backTv;
    private ImageView imageOnlineStatus;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference mRefData;

    private ValueEventListener seenListener;
    private DatabaseReference userRefForSeen;

    private List<ModelChat> chatList;
    private AdapterChat adapterChat;

    private String hisUid;
    private String myUid;

    private String hisImage;

    private APIService apiService;
    private boolean notify = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
        initView();
    }

    private void initView() {


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mRefData = database.getReference("Users");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);


        Intent intent = getIntent();
        hisUid = intent.getStringExtra("hisuid");
        myUid = user.getUid();

        Query query = mRefData.orderByChild("id").equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue();
                    hisImage = "" + ds.child("avt").getValue();

                    //lay du lieu online
                    String onlineStatus = "" + ds.child("onlineStatus").getValue();

                    if (onlineStatus.equals("online")) {
                        userStatusTv.setText("Đang hoạt động");
                        imageOnlineStatus.setBackgroundResource(R.drawable.bg_online);
                    } else {
                        imageOnlineStatus.setBackgroundResource(R.drawable.bg_offline);
                        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                        calendar.setTimeInMillis(Long.parseLong(onlineStatus));
                        String dateTime = DateFormat.format("hh:mm aa", calendar).toString();

                        userStatusTv.setText("Hoạt động lúc: " + dateTime);
                    }
                    nameTv.setText(name);
                    Glide.with(getApplicationContext())
                            .load(hisImage)
                            .error(R.mipmap.logoavatar)
                            .into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                String message = messengeEt.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {

                } else {
                    sendData(message);
                    sendDataTest(message);
                }
                messengeEt.setText("");
            }
        });

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        readMessage();
        seenMessage();

    }

    private void sendDataTest(final String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();

        String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> map = new HashMap<>();
        map.put("sender", myUid);
        map.put("receiver", hisUid);
        map.put("message", message);
        map.put("timestamp", timestamp);
        map.put("nameSender", nameTv.getText());
        map.put("avtSender", hisImage);
        map.put("isSeen", false);


        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("sender", hisUid);
        map2.put("receiver", myUid);
        map2.put("message", message);
        map2.put("timestamp", timestamp);
        map2.put("nameSender", HomeFragment.nameUser);
        map2.put("avtSender", HomeFragment.URLavt);
        map2.put("isSeen", false);


        reference.child("ListChats").child(myUid).child(myUid).child(hisUid).setValue(map);
        reference2.child("ListChats").child(hisUid).child(hisUid).child(myUid).setValue(map2);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelUser user = dataSnapshot.getValue(ModelUser.class);
                if (notify) {
                    sendNotification(hisUid, user.getAvt(), message);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void seenMessage() {
        userRefForSeen = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelChat modelChat = ds.getValue(ModelChat.class);
                    if (modelChat.getReceiver().equals(myUid) && modelChat.getSender().equals(hisUid)) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("isSeen", true);
                        ds.getRef().updateChildren(map);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessage() {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelChat modelChat = ds.getValue(ModelChat.class);
                    if (modelChat.getReceiver().equals(myUid) && modelChat.getSender().equals(hisUid) ||
                            modelChat.getReceiver().equals(hisUid) && modelChat.getSender().equals(myUid)) {
                        chatList.add(modelChat);
                    }
                    adapterChat = new AdapterChat(getApplicationContext(), chatList, hisImage);
                    adapterChat.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterChat);
                    seenMessage();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendData(final String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> map = new HashMap<>();
        map.put("sender", myUid);
        map.put("receiver", hisUid);
        map.put("message", message);
        map.put("timestamp", timestamp);
        map.put("isSeen", false);


        reference.child("Chats").push().setValue(map);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelUser user = dataSnapshot.getValue(ModelUser.class);
                if (notify) {
                    sendNotification(hisUid, user.getAvt(), message);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendNotification(final String hisUid, String avt, final String message) {

        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Token token = ds.getValue(Token.class);
                    Data data = new Data(myUid, nameTv + ":" + message, "Tin nhắn mới", hisUid, R.mipmap.logoavatar);

                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void init() {
        recyclerView = findViewById(R.id.chatRecyclerview);
        profileImage = findViewById(R.id.imageChat);
        nameTv = findViewById(R.id.nameTv);
        userStatusTv = findViewById(R.id.userStatusTv);
        messengeEt = findViewById(R.id.messageEt);
        btnSend = findViewById(R.id.btnSend);
        backTv = findViewById(R.id.backTv);
        imageOnlineStatus = findViewById(R.id.imageOnlineStatus);
    }

    private void CheckOnlineStatus(String status) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("onlineStatus", status);
        reference.updateChildren(map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        //menu.findItem(R.id.setting).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting1) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String timstamp = String.valueOf(System.currentTimeMillis());
        CheckOnlineStatus(timstamp);
        userRefForSeen.removeEventListener(seenListener);
    }

    @Override
    protected void onResume() {
        CheckOnlineStatus("online");
        super.onResume();
    }

    @Override
    protected void onStart() {
        CheckOnlineStatus("online");
        super.onStart();
    }
}
