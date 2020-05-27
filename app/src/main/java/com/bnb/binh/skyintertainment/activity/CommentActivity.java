package com.bnb.binh.skyintertainment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.adapters.CmtAdapter;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.models.Cmt;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView recyclerView;
    private ImageButton btnCmt;
    private EditText cmtEt;
    private FirebaseUser user;
    private DatabaseReference database;
    private String myId;
    private String hisId;
    private String key;
    private String keyId;
    private List<Cmt> list;
    private CmtAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("Cmts");
        myId = user.getUid();
        list = new ArrayList<>();

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        keyId = intent.getStringExtra("keyId");

        init();
        initView();

    }

    private void init() {
        btnBack = findViewById(R.id.btnBackCmt);
        recyclerView = findViewById(R.id.recyclerviewCmt);
        btnCmt = findViewById(R.id.btnCmt);
        cmtEt = findViewById(R.id.cmtEt);
    }

    private void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        getData();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmt = cmtEt.getText().toString().trim();
               if (!cmt.equals("")){
                   uploadData(cmt);
               }
            }
        });

    }

    private void uploadData(String cmt) {

        String time = String.valueOf(System.currentTimeMillis());

        HashMap<String,Object> map = new HashMap<>();
        map.put("hisId",myId);
        map.put("Cmt",cmt);
        map.put("timeCmt",time);

        DatabaseReference mNotification = FirebaseDatabase.getInstance().getReference("Notification");
        if (!key.equals(HomeFragment.mID)){
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("id",time);
            map2.put("hisId",HomeFragment.mID);
            map2.put("content","đã bình luận về bài viết của bạn.");
            mNotification.child(keyId).child(time).setValue(map2);
        }

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Cmts").child(key).child(time);
        mRef.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                cmtEt.setText("");
            }
        });
    }

    private void getData() {
            DatabaseReference  mRef = FirebaseDatabase.getInstance().getReference("Cmts").child(key);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                        Cmt listcmt = ds.getValue(Cmt.class);
                        list.add(listcmt);
                        adapter = new CmtAdapter(getApplicationContext(),list);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        Log.d("Cmt", "onDataChange: "+list.get(0).getCmt());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }


}
