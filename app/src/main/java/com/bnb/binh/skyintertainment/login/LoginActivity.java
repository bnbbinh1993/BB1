package com.bnb.binh.skyintertainment.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bnb.binh.skyintertainment.MainActivity;
import com.bnb.binh.skyintertainment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    private Button Login;
    private TextView loginTv;
    private EditText loginTk;
    private EditText loginMk;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference RootRef;
    private EditText UserEmail, UserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        init();
        event();
    }

    private void event() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang đang nhập...");


        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email = loginTk.getText().toString().trim();
                String password = loginMk.getText().toString().trim();
                loginToMainActitvity(email, password);
            }
        });
    }

    private void loginToMainActitvity(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại !!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    private void init() {
        Login = findViewById(R.id.login);
        loginTk = findViewById(R.id.login_email);
        loginMk = findViewById(R.id.login_pass);
        loginTv = findViewById(R.id.text_view2_login);

    }
}
