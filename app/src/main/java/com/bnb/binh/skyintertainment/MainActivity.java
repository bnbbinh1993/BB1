package com.bnb.binh.skyintertainment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.bnb.binh.skyintertainment.activity.AddProfileActivity;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.fragment.MessengerFragment;
import com.bnb.binh.skyintertainment.fragment.NotiflcationFragment;
import com.bnb.binh.skyintertainment.fragment.UserFragment;
import com.bnb.binh.skyintertainment.fragment.VideoFragment;
import com.bnb.binh.skyintertainment.login.LoginActivity;
import com.bnb.binh.skyintertainment.notifications.Token;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private int IN = 0;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private BottomNavigationView view;
    private Toolbar toolbar;
    private String myId;
    private DatabaseReference RootRef;
    public static boolean Totals = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        XinCapQuyenDocGhi();
        init();
        setHome();
        envent();

    }

    private void envent() {
        view.setOnNavigationItemSelectedListener(selectedListener);
        setSupportActionBar(toolbar);
        toolbar.setTitle("B Intertainment");
        toolbar.setTitleTextColor(Color.BLACK);
    }

    private void XinCapQuyenDocGhi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Log.d("READ", "XinCapQuyenDocGhi: Oke");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.d("READ", "XinCapQuyenDocGhi: Oke");

                } else {
                    Log.d("READ", "XinCapQuyenDocGhi: Chưa");
                }
            }
        }
    }

    private void init() {
        view = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbarMain);

    }


    public void updateToken(String token) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        ref.child(myId).setValue(mToken);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    if (IN != R.id.nav_home) {
                        setHome();
                        CheckClick(R.id.nav_home);
                    }
                    return true;

                case R.id.nav_video:

                    if (IN != R.id.nav_video) {
                        VideoFragment videoFragment = new VideoFragment();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.context, videoFragment);
                        transaction2.commit();

                        CheckClick(R.id.nav_video);
                    }
                    return true;
                case R.id.nav_messenger:

                    if (IN != R.id.nav_messenger) {
                        MessengerFragment messengerFragment = new MessengerFragment();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.context, messengerFragment);
                        transaction3.commit();
                        CheckClick(R.id.nav_messenger);
                    }
                    return true;
                case R.id.nav_notiflcations:

                    if (IN != R.id.nav_notiflcations) {
                        NotiflcationFragment notiflcationFragment = new NotiflcationFragment();
                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.context, notiflcationFragment);
                        transaction4.commit();
                        CheckClick(R.id.nav_notiflcations);
                    }

                    return true;
                case R.id.nav_users:

                    if (IN != R.id.nav_users) {
                        UserFragment userFragment = new UserFragment();
                        FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                        transaction5.replace(R.id.context, userFragment);
                        transaction5.commit();
                        CheckClick(R.id.nav_users);
                    }
                    return true;
            }
            return false;
        }
    };


    private void CheckClick(int i) {
        IN = i;
    }


    private void checkUser() {
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {

            updateToken(FirebaseInstanceId.getInstance().getToken());
            myId = user.getUid();
            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", myId);
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting1:
                // làm cái gì đó ^^
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            myId = user.getUid();
        }
    }

    private void setHome() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.context, homeFragment);
        transaction1.commit();
    }

    @Override
    protected void onResume() {
        checkUser();
        super.onResume();
    }

    private void checkData() {
        RootRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        String name = ds.child("name").getValue().toString();
                        if (name.equals("DEV")) {
                            startActivity(new Intent(getApplicationContext(), AddProfileActivity.class));
                        } else {

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //bug bug clg j bug lắm fix mãi đ** chịu hết đy~t m** nó
}
