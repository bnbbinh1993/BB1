package com.bnb.binh.skyintertainment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
    private EditText diachi;
    private EditText truonghoc;
    private EditText congviec;
    private EditText ngay;
    private EditText thang;
    private EditText nam;
    private RadioButton btnNam;
    private RadioButton btnNu;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        intit();
        
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        
        database = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();

            }
        });
    }

    private void intit() {
        btnXacNhan = findViewById(R.id.btnXacNhan);
        edtName = findViewById(R.id.edtName);
        diachi = findViewById(R.id.diachi);
        truonghoc = findViewById(R.id.truonghoc);
        congviec = findViewById(R.id.congviec);
        btnNam = findViewById(R.id.btnNam);
        btnNu = findViewById(R.id.btnNu);
        ngay = findViewById(R.id.ngay);
        thang = findViewById(R.id.thang);
        nam = findViewById(R.id.nam);

    }
    private void getEditText(){
        String name = edtName.getText().toString();
        String day = ngay.getText().toString();
        String month = thang.getText().toString();
        String year = nam.getText().toString();
        String address = diachi.getText().toString();
        String school = truonghoc.getText().toString();
        String work = congviec.getText().toString();
        String sex = "";
        if (!btnNu.isChecked() && btnNam.isChecked()){
            Toast.makeText(this, "Vui lòng chọn giới tính phù hợp với bạn!", Toast.LENGTH_SHORT).show();
        }else if (btnNam.isChecked()){
             sex = "Nam";
        }else if (btnNu.isChecked()){
             sex = "Nữ";
        }

        if (name.length()<=0){
            Toast.makeText(this, "Vui lòng điền tên của bạn!", Toast.LENGTH_SHORT).show();
        }else if (address.length()<=0){
            Toast.makeText(this, "Vui lòng điền tỉnh của bạn!", Toast.LENGTH_SHORT).show();
        }else if (school.length()<=0){
            Toast.makeText(this, "Vui lòng điền trường học của bạn!", Toast.LENGTH_SHORT).show();
        }else if (work.length()<=0){
            Toast.makeText(this, "Vui lòng điền công việc của bạn!", Toast.LENGTH_SHORT).show();
        }else if (day.length()<=0){
            Toast.makeText(this, "Vui lòng điền ngày sinh của bạn!", Toast.LENGTH_SHORT).show();
        }else if (month.length()<=0){
            Toast.makeText(this, "Vui lòng điền tháng sinh của bạn!", Toast.LENGTH_SHORT).show();
        }else if (year.length()<=0){
            Toast.makeText(this, "Vui lòng điền năm sinh của bạn!", Toast.LENGTH_SHORT).show();
        }
        if (Integer.parseInt(day)>31 || Integer.parseInt(day)<=0){
            Toast.makeText(this, "Vui lòng điền lại ngày sinh của bạn!", Toast.LENGTH_SHORT).show();
        }else
        if (Integer.parseInt(month)>12 || Integer.parseInt(month)<=0){
            Toast.makeText(this, "Vui lòng điền lại tháng sinh của bạn!", Toast.LENGTH_SHORT).show();
        }else
        if (Integer.parseInt(year)>3000 || Integer.parseInt(year)<=1900){
            Toast.makeText(this, "Vui lòng điền lại năm sinh của bạn!", Toast.LENGTH_SHORT).show();
        }else {
            String sn =day+"/"+month+"/"+year;
            uploadData(name,address,school,work,sex,sn);
        }
    }

    private void uploadData(String name,String address,String school,String work,String sex,String sinhnhat) {
        //add
        HashMap<String, Object> abc = new HashMap<>();
       //thêm sau
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
