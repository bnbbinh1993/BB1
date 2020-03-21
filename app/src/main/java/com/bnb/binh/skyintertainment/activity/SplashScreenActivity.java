package com.bnb.binh.skyintertainment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bnb.binh.skyintertainment.MainActivity;
import com.bnb.binh.skyintertainment.R;

public class SplashScreenActivity extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }
            };
        };

        thread.start();

    }
}
