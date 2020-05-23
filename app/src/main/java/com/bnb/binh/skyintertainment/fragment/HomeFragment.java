package com.bnb.binh.skyintertainment.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.MainActivity;
import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.NewsActivity;
import com.bnb.binh.skyintertainment.activity.ProfileActivity;
import com.bnb.binh.skyintertainment.adapters.NewsAdapter;
import com.bnb.binh.skyintertainment.adapters.StoriesAdapter;
import com.bnb.binh.skyintertainment.login.LoginActivity;
import com.bnb.binh.skyintertainment.models.ModelStory;
import com.bnb.binh.skyintertainment.models.News;
import com.bnb.binh.skyintertainment.models.Pew;
import com.bnb.binh.skyintertainment.models.Story;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private CircleImageView feedNewAvt;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private StoriesAdapter adapterStory;
    private Button btnNews;
    private NewsAdapter adapterNews;
    public static ArrayList<ModelStory> listItemModelStory;
    private ArrayList<News> listItemNews;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    public static String mID;
    public static String URLavt;
    public static String nameUser;
    private Activity mActivity;
    private List<Story> storyList;
    public static ArrayList<Pew> data;


    private FirebaseDatabase database;
    private DatabaseReference referenceDatabase;


    private LinearLayout mLoadView;
    private LinearLayout mLoadViewTrue;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mLoadView = view.findViewById(R.id.loadView);
        mLoadViewTrue = view.findViewById(R.id.LoadViewTrue);


        if (MainActivity.Totals == false) {
            MainActivity.Totals = true;
            mLoadView.setVisibility(View.VISIBLE);
            mLoadViewTrue.setVisibility(View.GONE);
        } else {
            mLoadView.setVisibility(View.GONE);
            mLoadViewTrue.setVisibility(View.VISIBLE);
        }

        checkUser();    //mặc định nó phải load đầu ^^
        init(view);     // ánh xạ
        getALl();       // load bảng tin
        getData();      // load avt
        setView();      //1 số sự kiện bên trong :)) có load stories...


        return view;
    }

    private void checkUser() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            mID = mUser.getUid();
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }

    private void getData() {
        if (mActivity == null) {
            return;
        } else {
            data = new ArrayList<>();

            database = FirebaseDatabase.getInstance();
            referenceDatabase = database.getReference("Users");

            if (mUser != null) {
                Query query = referenceDatabase.orderByChild("email").equalTo(mUser.getEmail());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String name = "" + ds.child("name").getValue();
                            String avt = "" + ds.child("avt").getValue();

                            nameUser = name;
                            URLavt = avt;

                            if (URLavt.equals("")) {
                                feedNewAvt.setImageResource(R.mipmap.logoavatar);
                            } else {
                                Picasso.get().load(avt).error(R.mipmap.logoavatar).into(feedNewAvt);
                            }
//                        Glide.with(getContext())
//                                .load(avt)
//                                .error(R.mipmap.logoavatar)
//                                .into(feedNewAvt);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerviewHome);
        recyclerView2 = view.findViewById(R.id.recyclerviewHome2);
        btnNews = view.findViewById(R.id.btnNews);
        feedNewAvt = view.findViewById(R.id.feedNewAvt);
    }

    private void setView() {
        feedNewAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("id", mID);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        getDataStories();

        adapterNews = new NewsAdapter(getContext(), listItemNews);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), LinearLayoutManager.VERTICAL));


        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewsActivity.class));
            }
        });


    }

    private void getALl() {
        listItemNews = new ArrayList<>();


        referenceDatabase = FirebaseDatabase.getInstance().getReference("News");
        referenceDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listItemNews.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds != null) {
                        News news = ds.getValue(News.class);
                        listItemNews.add(news);
                        recyclerView2.setAdapter(adapterNews);
                        adapterNews.notifyDataSetChanged();
                    }
                }
                Collections.reverse(listItemNews);
                adapterNews.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDataStories() {
        storyList = new ArrayList<>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Story");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storyList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Story story = ds.getValue(Story.class);
                    storyList.add(story);
                    adapterStory = new StoriesAdapter(getContext(), storyList);
                    adapterStory.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterStory);
                }
                getAllStory();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getAllStory() {
        listItemModelStory = new ArrayList<>();
        listItemModelStory.clear();
        for (int i = 0; i < storyList.size(); i++) {
            final ArrayList<String> imagelist = new ArrayList<>();
            final ArrayList<String> timelist = new ArrayList<>();
            final ArrayList<String> titlelist = new ArrayList<>();
            final ArrayList<String> namlist = new ArrayList<>();
            final ArrayList<String> avtlist = new ArrayList<>();

            imagelist.clear();
            timelist.clear();
            titlelist.clear();
            String id = storyList.get(i).getId();

            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Stories").child(storyList.get(i).getId());
            Query query = mRef.orderByChild("id").equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        // Pew pew = ds.getValue(Pew.class);
                        // data.add(pew);
                        imagelist.add(ds.child("image").getValue().toString());
                        timelist.add(ds.child("time").getValue().toString());
                        titlelist.add(ds.child("title").getValue().toString());
                        namlist.add(ds.child("name").getValue().toString());
                        avtlist.add(ds.child("avt").getValue().toString());

                    }
                    mLoadView.setVisibility(View.GONE);
                    mLoadViewTrue.setVisibility(View.VISIBLE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            listItemModelStory.add(new ModelStory(namlist, avtlist, imagelist, timelist, titlelist));
        }

    }


    @Override
    public void onStart() {
        super.onStart();

    }

}
