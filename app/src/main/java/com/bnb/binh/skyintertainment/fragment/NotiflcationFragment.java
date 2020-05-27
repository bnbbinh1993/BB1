package com.bnb.binh.skyintertainment.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.adapters.NotiflcationAdapter;
import com.bnb.binh.skyintertainment.models.Notiflcation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotiflcationFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView tvNotification;
    private ProgressBar progressBar;
    private LinearLayout layout;

    private ArrayList<Notiflcation> itemlist;
    private NotiflcationAdapter adapter;

    public NotiflcationFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_notiflcation, container, false);

        init(v);
        setLayout();

        recyclerView = v.findViewById(R.id.recyclerviewNotiflcation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        fireBaseData();

        return v;
    }

    private void init(View v) {
        tvNotification = v.findViewById(R.id.tvNotiflcation);
        progressBar = v.findViewById(R.id.progressNotification);
        layout = v.findViewById(R.id.layoutView);
    }
    private void setLayout(){
        layout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        tvNotification.setVisibility(View.GONE);
    }


    private void fireBaseData(){
        itemlist = new ArrayList<>();
        adapter = new NotiflcationAdapter(getContext(),itemlist);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notification");
        reference.child(HomeFragment.mID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemlist.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Notiflcation notiflcation = ds.getValue(Notiflcation.class);
                    itemlist.add(notiflcation);
                    recyclerView.setAdapter(adapter);
                }
                Collections.reverse(itemlist);
                adapter.notifyDataSetChanged();

                if (itemlist.size()<=0){
                    progressBar.setVisibility(View.GONE);
                    tvNotification.setVisibility(View.VISIBLE);
                }else {
                    layout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
