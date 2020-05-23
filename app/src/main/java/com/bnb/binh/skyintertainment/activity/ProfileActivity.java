package com.bnb.binh.skyintertainment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.adapters.NewsAdapter;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.models.News;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private ImageView backProfile;
    private ImageView backgroundProfile;
    private ImageView backgroundProfileTop;
    private ProgressBar progressBarProfile;
    private CircleImageView avtProfile;
    private CircleImageView btnTichXanh;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference cRef;
    private DatabaseReference mRefData;
    private static final int RESULT_LOAD_IMAGE = 1;
    private int check = -1;
    private Uri imageUri;
    private String imageURI;
    private String urlSave;
    private String imageUploadURL;
    private String ID;
    private TextView nameProfile;
    private TextView address;
    private TextView cv;
    private TextView school;
    private TextView age;
    private StorageReference mStorageRef;
    private InputStream imageStream;
    private String ID_avater = "10000";
    private SharedPreferences avatarURL;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> listPost;
    private String key;
    private LinearLayout Layout;
    private Button btnNt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        ID = mUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        key=id;
        init();
        checkID();
        //getData();
        event();
        loadData();
        LoadPost();


    }
    private void LoadPost() {
        recyclerView = findViewById(R.id.recyclerviewProfile);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        listPost = new ArrayList<>();


        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("News");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    News news =ds.getValue(News.class);
                    if (news.getId().equals(key)){
                        listPost.add(news);
                    }
                    adapter=new NewsAdapter(getApplicationContext(),listPost);
                    recyclerView.setAdapter(adapter);
                }
                Collections.reverse(listPost);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void checkID() {
        if (key.equals("t0D8H6PMCGbTFaJ1Ohrta5S5z4i1")){
            btnTichXanh.setVisibility(View.VISIBLE);
        }else {
            btnTichXanh.setVisibility(View.GONE);
        }

        if (key.equals(HomeFragment.mID)){
            Layout.setVisibility(View.GONE);
        }else {
            Layout.setVisibility(View.VISIBLE);
        }

//        avatarURL = getSharedPreferences("URL_CODE", getApplicationContext().MODE_PRIVATE);
//        urlSave = avatarURL.getString("URL_AVT", "");
//        String id = avatarURL.getString("ID_USER", "");
//        if (ID.equals(id)) {
//            if (urlSave.length()>0) {
//                LoadAvat(urlSave);
//            }
//        }
    }


    private void init() {
        backProfile = findViewById(R.id.backProfile);
        backgroundProfile = findViewById(R.id.backgroundProfile);
        backgroundProfileTop = findViewById(R.id.backgroundProfileTop);
        progressBarProfile = findViewById(R.id.progressBarProfile);
        avtProfile = findViewById(R.id.avtProfile);
        nameProfile = findViewById(R.id.nameProfile);
        address = findViewById(R.id.address);
        cv = findViewById(R.id.cv);
        school = findViewById(R.id.school);
        age = findViewById(R.id.age);
        btnTichXanh = findViewById(R.id.btnTichXanh);
        Layout = findViewById(R.id.Layout);
        btnNt = findViewById(R.id.btnNt);
    }

    private void event() {
        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        avtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 0;
                getImageFromAlbum();
            }
        });
        backgroundProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 1;
                getImageFromAlbum();
            }
        });


        btnNt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("hisuid",key);
                startActivity(intent);
            }
        });
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
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                switch (check) {
                    case 0: {
                        avtProfile.setImageBitmap(selectedImage);
                        progressBarProfile.setVisibility(View.VISIBLE);
                        UploadImage();
                        break;
                    }
                    case 1: {
                        Glide.with(this)
                                .load(selectedImage)
                                .centerCrop()
                                .into(backgroundProfile);
                        progressBarProfile.setVisibility(View.VISIBLE);
                        backgroundProfileTop.setVisibility(View.VISIBLE);
                        UploadImage();
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ProfileActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void UploadImage() {
        if (imageUri != null) {
            switch (check) {
                case 0: {
                    imageURI = "images/user/" + ID + "/avatarProfile/" + ID_avater;
                    break;
                }
                case 1: {
                    imageURI = "images/user/" + ID + "/backgroudProfile/" + UUID.randomUUID().toString();
                    break;
                }
            }
            final StorageReference mRef = mStorageRef.child(imageURI);
            mRef.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, "Upload image fail!", Toast.LENGTH_SHORT).show();
                    mFinish();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                imageUploadURL = task.getResult().toString();
                                if (check==0){
                                    upDataUser(imageUploadURL);
                                    UpdateData(imageUploadURL);
                                }else {
                                    uploadBg(imageUploadURL);
                                }
                                Log.d("URL", "onComplete: " + imageUploadURL);
                                //update link on user data in databse cloud firebase
                            }
                        }
                    });
                    Toast.makeText(ProfileActivity.this, "Upload image success!", Toast.LENGTH_SHORT).show();
                    mFinish();
                }
            });
        }
    }

    private void uploadBg(String imageUploadURL) {
        mRefData = FirebaseDatabase.getInstance().getReference("Users").child(ID);
        HashMap<String,Object> map =new HashMap<>();
        map.put("bg",imageUploadURL);
        mRefData.updateChildren(map);
    }

    private void upDataUser(String imageUploadURL) {
        mRefData = FirebaseDatabase.getInstance().getReference("Users").child(ID);
        HashMap<String,Object> map =new HashMap<>();
        map.put("avt",imageUploadURL);
        mRefData.updateChildren(map);
    }

    private void UpdateData(final String imageUploadURL) {
        Map<String, Object> setData = new HashMap<>();
        setData.put("1000", imageUploadURL);
        firebaseFirestore.collection("Database").document("Users").collection(ID)
                .document("avatar")
                .set(setData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("SETURL", "onSuccess: OK");
                        SaveLogURL(imageUploadURL);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("SETURL", "onFailure: Fail");
            }
        });

    }

    private void SaveLogURL(String imageUploadURL) {
        SharedPreferences.Editor editor = avatarURL.edit();
        editor.putString("URL_AVT", imageUploadURL);
        editor.putString("ID_USER", ID);
        editor.commit();
    }

    private void getData() {
        DocumentReference ref = firebaseFirestore.collection("Database").document("Users")
                .collection(ID).document("avatar");
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        if (!urlSave.equals(snapshot.getData().get("1000"))) {
                            imageUploadURL = snapshot.getData().get("1000").toString();
                            LoadAvat(imageUploadURL);
                            SaveLogURL(imageUploadURL);
                            Log.d("DOC", "onComplete: " + snapshot.getData().get("1000"));
                        }
                    }

                }
            }
        });


    }

    private void mFinish() {
        progressBarProfile.setVisibility(View.GONE);
        backgroundProfileTop.setVisibility(View.GONE);
    }

    private void LoadAvat(String URL) {
        Glide.with(getApplicationContext())
                .load(URL)
                .centerCrop()
                .into(avtProfile);

    }

    private void loadData(){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");

        Query query = mRef.orderByChild("id").equalTo(key);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    String name = ""+ds.child("name").getValue();
                    String address1 = ""+ds.child("address").getValue();
                    String cv1 = ""+ds.child("cv").getValue();
                    String age1 = ""+ds.child("age").getValue();
                    String school1 = ""+ds.child("school").getValue();
                    String bg = ""+ds.child("bg").getValue();
                    String avt = ""+ds.child("avt").getValue();

                    nameProfile.setText(name);
                    cv.setText(cv1);
                    address.setText(address1);
                    age.setText(age1);
                    school.setText(school1);

                    Glide.with(getApplicationContext())
                            .load(bg)
                            .centerCrop()
                            .into(backgroundProfile);

                    Glide.with(getApplicationContext())
                            .load(avt)
                            .error(R.mipmap.logoavatar)
                            .centerCrop()
                            .into(avtProfile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
