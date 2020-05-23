package com.bnb.binh.skyintertainment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.login.LoginActivity;
import com.bnb.binh.skyintertainment.notifications.Data;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsActivity extends AppCompatActivity {
    private CheckBox CheckBoxNews;
    private CheckBox CheckBoxStories;
    private ImageButton backNews;
    private ImageButton btnImageNew;
    private ImageButton btnClearImage;
    private TextView tvDang;
    private TextView nameTonglao;
    private CircleImageView avtTonglao;
    private EditText inputNews;
    private RelativeLayout viewImageInput;
    private ImageView imageInput;
    private Uri imageUri;
    private InputStream imageStream;
    private static final int RESULT_LOAD_IMAGE =1;
    private int check = 0;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference cRef;
    private String imageUploadURL = "";
    private String URLavt="123";
    private String ID;
    private String urlUser;
    private DatabaseReference mData;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        initEvent();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        ID = user.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference();

        mData = FirebaseDatabase.getInstance().getReference("Users");
        Query query = mData.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds!=null){
                        String name = ds.child("name").getValue().toString();
                        String urlImage = ds.child("avt").getValue().toString();
                        urlUser = urlImage;
                        nameTonglao.setText(name);
                        Glide.with(getApplicationContext())
                                .load(urlImage)
                                .error(R.mipmap.logoavatar)
                                .into(avtTonglao);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void initEvent() {
        btnImageNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImageFromAlbum();
            }
        });
        btnClearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoneImage();
            }
        });
        tvDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!CheckBoxStories.isChecked() && !CheckBoxNews.isChecked()){
                    Toast.makeText(NewsActivity.this, "Bạn muốn đăng lên đâu?", Toast.LENGTH_SHORT).show();
                }else {

                    if (imageUri != null || inputNews.length()>0){
                        dialog = ProgressDialog.show(NewsActivity.this, "",
                                "Loading. Please wait...", true);
                        upData();
                    }else {
                        Toast.makeText(NewsActivity.this, "Hay điền gì đó?", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        backNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              backToMainActivity();
            }
        });
    }
    private void init() {
        backNews = findViewById(R.id.backNews);
        btnImageNew = findViewById(R.id.btnImageNew);
        btnClearImage = findViewById(R.id.btnClearImage);
        tvDang = findViewById(R.id.tvDang);
        nameTonglao = findViewById(R.id.nameTonglao);
        avtTonglao = findViewById(R.id.avtTongLao);
        inputNews = findViewById(R.id.inputNews);
        viewImageInput = findViewById(R.id.view4);
        imageInput = findViewById(R.id.imageInput);
        CheckBoxNews = findViewById(R.id.checkNewfeed);
        CheckBoxStories = findViewById(R.id.checkStory);
    }
    private void getImageFromAlbum() {
        try {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
        } catch (Exception exp) {
            Log.i("Error", exp.toString());
        }
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Glide.with(this)
                        .load(imageUri)
                        .into(imageInput);
                setVisibleImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(NewsActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(NewsActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
    private void setVisibleImage() {
        check = 1;
        viewImageInput.setVisibility(View.VISIBLE);
    }
    private void setGoneImage(){
        check = 0;
        viewImageInput.setVisibility(View.GONE);
    }
    private void backToMainActivity(){

            AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
            builder.setMessage("Bạn có muốn xóa bài viết hay không?")
                    .setCancelable(false)
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Thoát");
            alertDialog.show();

    }
    @Override
    public void onBackPressed() {
        backToMainActivity();
    }

    private void upData(){
        if (imageUri == null){
            uploadData(imageUploadURL);
        }else {
            final StorageReference mRef = storageReference.child("imageNews").child(UUID.randomUUID().toString());
            mRef.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                imageUploadURL = task.getResult().toString();
                                Log.d("URL", "onComplete: " + imageUploadURL);
                                uploadData(imageUploadURL);
                            }
                        }
                    });
                    Log.d("IMAGE", "onSuccess: Upload thanh công!");
                    mFinish();
                }
            });
        }
    }

    private void uploadData(String urlImage){
        if (CheckBoxNews.isChecked()){
            String uId = UUID.randomUUID().toString();
            DatabaseReference mRaf = FirebaseDatabase.getInstance().getReference("News");
          
            String news = inputNews.getText().toString().trim();
            String time = String.valueOf(System.currentTimeMillis());
            String url = urlImage;
            String name = nameTonglao.getText().toString();

            HashMap<String,Object> map = new HashMap<>();
            map.put("news",news);
            map.put("urlImage",url);
            map.put("time",time);
            map.put("uId",uId);
            map.put("name",name);
            map.put("like","0");
            map.put("urlAvt1",urlUser);
            map.put("id",user.getUid());

            mRaf.child(time).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dialog.dismiss();
                    Toast.makeText(NewsActivity.this, "Đã đăng!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(NewsActivity.this, "Lỗi rồi người ae !!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (CheckBoxStories.isChecked()){

            HashMap<String,Object> map2 = new HashMap<>();
            map2.put("id",user.getUid());
            map2.put("image",urlImage);
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Story");
            mRef.child(user.getUid()).setValue(map2).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dialog.dismiss();
                    Toast.makeText(NewsActivity.this, "Đã đăng!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            String time = String.valueOf(System.currentTimeMillis());
            String title = inputNews.getText().toString().trim();
            HashMap<String,Object> map3 = new HashMap<>();
            map3.put("id",user.getUid());
            map3.put("image",urlImage);
            map3.put("time",time);
            map3.put("title",title);
            map3.put("name",nameTonglao.getText());
            map3.put("avt",urlUser);

            DatabaseReference mRof = FirebaseDatabase.getInstance().getReference("Stories");
            mRof.child(user.getUid()).child(time).setValue(map3);

        }

        

        

    }

    private void mFinish() {
        
    }
}
