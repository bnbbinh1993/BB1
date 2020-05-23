package com.bnb.binh.skyintertainment.resoucres;

import androidx.annotation.NonNull;

import com.bnb.binh.skyintertainment.models.Pew;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DpStory {

    private List<Pew> data;
    private DatabaseReference mRef;
    public List<Pew> getData(String id){
        data = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference().child("Stories").child(id);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Pew pew = ds.getValue(Pew.class);
                    data.add(pew);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return data;
    }

}
