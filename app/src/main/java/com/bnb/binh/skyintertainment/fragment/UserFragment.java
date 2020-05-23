package com.bnb.binh.skyintertainment.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.FriendsActivity;
import com.bnb.binh.skyintertainment.activity.ProfileActivity;
import com.bnb.binh.skyintertainment.activity.SettingActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private LinearLayout inForUser;
    private Button settingUser;
    private Button friendUser;
    private CircleImageView userAvatar;
    private TextView userName;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private Activity mActivity;

    private FirebaseDatabase database;
    private DatabaseReference referenceDatabase;
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
        friendUser = view.findViewById(R.id.friendUser);

        event();
        getData();
        return view;
    }


    private void event() {
        inForUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("id", HomeFragment.mID);
                startActivity(intent);
            }
        });
        settingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        friendUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FriendsActivity.class));
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    private void getData() {
        if (mActivity == null) {
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        _ID = mUser.getUid();

        database = FirebaseDatabase.getInstance();
        referenceDatabase = database.getReference("Users");

        Query query = referenceDatabase.orderByChild("email").equalTo(mUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (mActivity == null) {
                        return;
                    }
                    String name = "" + ds.child("name").getValue();
                    String avt = "" + ds.child("avt").getValue();

                    userName.setText(name);
                    Glide.with(getContext())
                            .load(avt)
                            .error(R.mipmap.logoavatar)
                            .into(userAvatar);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
