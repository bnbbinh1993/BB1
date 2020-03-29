package com.bnb.binh.skyintertainment.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.ProfileActivity;
import com.bnb.binh.skyintertainment.activity.SettingActivity;
import com.bnb.binh.skyintertainment.login.LoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private LinearLayout inForUser;
    private Button settingUser;
    private CircleImageView userAvatar;
    private TextView userName;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private FirebaseDatabase mData;
    private String _ID;


    public UserFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        inForUser = view.findViewById(R.id.inForUser);
        settingUser = view.findViewById(R.id.settingUser);
        userAvatar = view.findViewById(R.id.userAvatar);
        userName = view.findViewById(R.id.userName);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        _ID = mUser.getUid();
        mData = FirebaseDatabase.getInstance();
        mReference = mData.getReference().child("Users").child(_ID);
        getData();
        event();
        return view;
    }

    private void event() {
        inForUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
        settingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

    }

    private void getData(){

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = ""+ds.child("name").getValue();
                    String avt = ""+ds.child("address").getValue();

                    userName.setText(name);
                    Picasso.get().load(avt)
                            .centerCrop()
                            .error(R.mipmap.avt)
                            .into(userAvatar);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
