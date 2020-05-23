package com.bnb.binh.skyintertainment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bnb.binh.skyintertainment.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowProfileUserActivity extends AppCompatActivity {
    private ImageView btnBackShowProfile;
    private ImageView backgroundProfileShow;
    private ImageView backgroundProfileTopShow;
    private CircleImageView imageShowProfile;
    private CircleImageView btnTichXanhShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile_user);
        init();


    }

    private void init() {
        btnBackShowProfile = findViewById(R.id.btnBackShowProfile);
    //    backgroundProfileShow = findViewById(R.id.backgroundProfileShow);
    //    backgroundProfileTopShow = findViewById(R.id.backgroundProfileTopShow);
        imageShowProfile = findViewById(R.id.imageShowProfile);
        btnTichXanhShow = findViewById(R.id.btnTichXanhShow);

    }
}
