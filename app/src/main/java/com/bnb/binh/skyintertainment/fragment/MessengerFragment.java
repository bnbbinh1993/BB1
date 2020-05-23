package com.bnb.binh.skyintertainment.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.adapters.MSG1Adapter;
import com.bnb.binh.skyintertainment.adapters.MSG2Adapter;
import com.bnb.binh.skyintertainment.models.MSG1;
import com.bnb.binh.skyintertainment.models.MSG2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MessengerFragment extends Fragment {
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private MSG1Adapter adapter1;
    private MSG2Adapter adapter2;
    private ArrayList<MSG1> msg1s;
    private ArrayList<MSG2> msg2s;
    private ArrayList<MSG2> chatList;
    private String myId;
    private LinearLayout view2;
    private LinearLayout loadViewMess;
    private RelativeLayout loadViewMess2;


    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference mRefData;

    public MessengerFragment() {
    }
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_messenger, container, false);
        msg1s = new ArrayList<>();
        msg2s = new ArrayList<>();
        adapter1 = new MSG1Adapter(getContext(),msg1s);
        view2 = v.findViewById(R.id.view24);
        loadViewMess = v.findViewById(R.id.loadViewMess);
        loadViewMess2 = v.findViewById(R.id.loadViewMess2);

        recyclerView1 = v.findViewById(R.id.recyclerviewMSG1);
        recyclerView2 = v.findViewById(R.id.recyclerviewMSG2);



        loadViewMess.setVisibility(View.VISIBLE);



        setData();

        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView1.setAdapter(adapter1);


        if (chatList.size()<=0){
            view2.setVisibility(View.VISIBLE);
        }else {
            view2.setVisibility(View.GONE);
        }
        return v;
    }

    private void setData() {
        msg1s.add(new MSG1("Bình"));
        msg1s.add(new MSG1("Nam"));
        getData();


    }
    private void getData(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        myId = user.getUid();
        chatList = new ArrayList<>();

        mRefData = FirebaseDatabase.getInstance().getReference("ListChats").child(myId).child(myId);
        mRefData.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();

                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    if (ds.exists()){
                        view2.setVisibility(View.GONE);
                        MSG2 msg2 = ds.getValue(MSG2.class);
                        chatList.add(msg2);
                        adapter2 = new MSG2Adapter(getContext(),chatList);
                        adapter2.notifyDataSetChanged();
                        recyclerView2.setAdapter(adapter2);
                        loadViewMess.setVisibility(View.GONE);
                        loadViewMess2.setVisibility(View.VISIBLE);

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}