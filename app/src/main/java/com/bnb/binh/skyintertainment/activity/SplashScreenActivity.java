package com.bnb.binh.skyintertainment.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.bnb.binh.skyintertainment.MainActivity;
import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.login.LoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime = 2000;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference RootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(1000);
                        if (_active) {
                            waited += 1000;
                        }
                    }
                } catch (Exception e){

                } finally {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            };
        };
        thread.start();

    }

    private void checkData(){
        RootRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("name");
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    if (ds.exists()){
                        String name = ds.getValue().toString();
                        if (name.equals("DEV")){
                            startActivity(new Intent(getApplicationContext(),AddAccountInforActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
