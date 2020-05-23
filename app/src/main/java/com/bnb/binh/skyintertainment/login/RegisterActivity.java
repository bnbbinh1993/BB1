package com.bnb.binh.skyintertainment.login;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bnb.binh.skyintertainment.MainActivity;
import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.AddProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private TextView registerTv;
    private EditText registerTk;
    private EditText registerMk;

    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang đang ký...");
        Event();
    }

    private void init() {
        register = findViewById(R.id.register);
        registerTv = findViewById(R.id.text_view2_register);
        registerTk = findViewById(R.id.register_email);
        registerMk = findViewById(R.id.register_pass);

    }

    private void Event(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerTk.getText().toString().trim();
                String password = registerMk.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this, "Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();
                }else if (password.length()<6){
                    Toast.makeText(RegisterActivity.this, "Ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                }else {
                    Createuser(email,password);
                }
            }
        });

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void Createuser(String email, String password){
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            setData();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại.", Toast.LENGTH_SHORT).show();

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    private void setData() {

        database = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser().getUid();
        String id = currentUser;
        String email = registerTk.getText().toString();
        String name = "B-Team";
        String sdt = "0 tới 9 tự xếp";
        String address ="Hà Nội";
        String age = "18";

        HashMap<String, String> abc = new HashMap<>();
        abc.put("id",id);
        abc.put("avt","");
        abc.put("email",email);
        abc.put("name", name);
        abc.put("onlineStatus", "online");
        abc.put("sdt", sdt);
        abc.put("address",address);
        abc.put("age",age);
        abc.put("bg","");
        abc.put("cv","Student");
        abc.put("school","Hà Nội");

       // FirebaseDatabase.getInstance().getReference().child("Persion").child("Users").child(currentUser).setValue(abc);
        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).setValue(abc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(getApplication(), AddProfileActivity.class));
                finish();
            }
        });

    }

}
