package com.bnb.binh.skyintertainment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bnb.binh.skyintertainment.MainActivity;
import com.bnb.binh.skyintertainment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddProfileActivity extends AppCompatActivity {
    private Button btnXacNhan;
    private EditText edtName;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        edtName = findViewById(R.id.edtName);
        
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        
        database = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                uploadData(name);
            }
        });
    }

    private void uploadData(String name) {
        //add
        HashMap<String, Object> abc = new HashMap<>();
        abc.put("name",name);
        database.updateChildren(abc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(getApplication(), MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProfileActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
            }
        });
       
    }
}
