package com.bnb.binh.skyintertainment;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.fragment.MessengerFragment;
import com.bnb.binh.skyintertainment.fragment.NotiflcationFragment;
import com.bnb.binh.skyintertainment.fragment.UserFragment;
import com.bnb.binh.skyintertainment.fragment.VideoFragment;
import com.bnb.binh.skyintertainment.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private BottomNavigationView view;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
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



    private void init() {
        view = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbarMain);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    setHome();
                    return true;
                case R.id.nav_video:
                    VideoFragment videoFragment = new VideoFragment();
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.context,videoFragment);
                    transaction2.commit();
                    return true;
                case R.id.nav_messenger:
                    MessengerFragment messengerFragment = new MessengerFragment();
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.context,messengerFragment);
                    transaction3.commit();
                    return true;
                case R.id.nav_notiflcations:
                    NotiflcationFragment notiflcationFragment= new NotiflcationFragment();
                    FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                    transaction4.replace(R.id.context,notiflcationFragment);
                    transaction4.commit();
                    return true;
                case R.id.nav_users:
                    UserFragment userFragment = new UserFragment();
                    FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                    transaction5.replace(R.id.context,userFragment);
                    transaction5.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                // làm cái gì đó ^^
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void setHome(){
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.context,homeFragment);
        transaction1.commit();
    }
}
