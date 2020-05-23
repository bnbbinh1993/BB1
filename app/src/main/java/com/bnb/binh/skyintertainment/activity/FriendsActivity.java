package com.bnb.binh.skyintertainment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.adapters.AdapterUser;
import com.bnb.binh.skyintertainment.adapters.Friend1Adapter;
import com.bnb.binh.skyintertainment.adapters.Friend2Adapter;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.models.Friends;
import com.bnb.binh.skyintertainment.models.ModelUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ArrayList<Friends> arrayList1;
    private ArrayList<Friends> arrayList2;
    private ArrayList<ModelUser> allUserItem;
    private Friend1Adapter adapter1;
    private Friend2Adapter adapter2;
    private AdapterUser userAdapter;
    private ImageView backFriend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        init();
        initEvent();
    }

    private void initEvent() {
        getData();
        adapter1 = new Friend1Adapter(this,arrayList1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter1);


        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, LinearLayoutManager.VERTICAL));
        backFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        arrayList1.add(new Friends("","","","","","Bùi Bình",""));
        allUserItem = new ArrayList<>();
        getAllUser();



    }

    private void getAllUser() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allUserItem.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    ModelUser modelUser  = ds.getValue(ModelUser.class);
                    if (!modelUser.getId().equals(HomeFragment.mID)){
                        allUserItem.add(modelUser);
                    }
                    userAdapter = new AdapterUser(getApplicationContext(),allUserItem);
                    recyclerView2.setAdapter(userAdapter);
                }
               userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerviewFriend1);
        recyclerView2 = findViewById(R.id.recyclerviewFriend2);
        backFriend = findViewById(R.id.backFriend);
    }
}
