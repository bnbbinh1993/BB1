package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.CommentActivity;
import com.bnb.binh.skyintertainment.activity.ProfileActivity;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.handle.ItemClickView;
import com.bnb.binh.skyintertainment.models.News;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Viewholder> {
    private Context context;
    private List<News> newsList;
    private boolean bb = false;
    private ItemClickView itemClickView;


    private DatabaseReference likeRef;
    private DatabaseReference CmtRef;


    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        likeRef = FirebaseDatabase.getInstance().getReference().child("Liked");
        CmtRef = FirebaseDatabase.getInstance().getReference("Cmts");

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_recyclerview, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder viewholder, final int i) {

        final News posts = newsList.get(i);
        viewholder.userNameNews.setText(posts.getName());
        viewholder.timePost.setText(posts.getName()); ///
        viewholder.post.setText(posts.getNews());
        String avtImage = posts.getUrlAvt1();
        String urlImage = posts.getUrlImage();
        final String time = posts.getTime();
        final String uId = posts.getId();
        final String key = newsList.get(i).getTime();

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(time));
        final String dateTime = DateFormat.format("hh:mm aa", calendar).toString();
        viewholder.bind(i);
        viewholder.timePost.setText(dateTime);

        if (!urlImage.equals("")) {
            Glide.with(context)
                    .load(urlImage)
                    .into(viewholder.imagePost);
        } else {
            viewholder.imagePost.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(avtImage)
                .error(R.mipmap.logoavatar)
                .into(viewholder.avtarUserPost);


        if (uId.equals("t0D8H6PMCGbTFaJ1Ohrta5S5z4i1")) {
            viewholder.checkUser.setVisibility(View.VISIBLE);
        } else {
            viewholder.checkUser.setVisibility(View.GONE);
        }
        viewholder.checkUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Tích xanh chính chủ", Toast.LENGTH_SHORT).show();
            }
        });


        viewholder.btnLikePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(key).hasChild(HomeFragment.mID)) {
                            DatabaseReference mNotification;
                            mNotification = FirebaseDatabase.getInstance().getReference("Notification");
                            String idKey = newsList.get(i).getId();
                            String timeKey = String.valueOf(System.currentTimeMillis());
                            if (!idKey.equals(HomeFragment.mID)){
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("id",timeKey);
                                map.put("hisId",HomeFragment.mID);
                                map.put("content","đã thích bài viết của bạn.");
                                mNotification.child(idKey).child(timeKey).setValue(map);
                            }
                            bb = true;
                        } else {
                            bb = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        bb = false;
                    }
                });

                if (bb == true) {
                    likeRef.child(newsList.get(i).getTime()).child(HomeFragment.mID).removeValue().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            viewholder.btnLikePost.setImageResource(R.drawable.ic_no_like);
                            bb = false;
                        }
                    });

                } else {

                    likeRef.child(newsList.get(i).getTime()).child(HomeFragment.mID).setValue(true).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            viewholder.btnLikePost.setImageResource(R.drawable.ic_tym_red);
                            bb = true;
                        }
                    });
                }
            }

        });
        viewholder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("key", time);
                intent.putExtra("keyId", uId);
                context.startActivity(intent);
            }
        });

        CmtRef.child(time).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cout = "" + dataSnapshot.getChildrenCount();
                viewholder.cmtCout.setText(cout);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        viewholder.avtarUserPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("id", uId);
                context.startActivity(intent);
            }
        });
        viewholder.userNameNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("id", uId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setItemClickView(ItemClickView itemClickView) {
        this.itemClickView = itemClickView;
    }

    //t0D8H6PMCGbTFaJ1Ohrta5S5z4i1

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView userNameNews;
        private CircleImageView avtarUserPost;
        private CircleImageView checkUser;
        private ImageButton btnLikePost;
        private ImageButton btnComment;
        private TextView timePost;
        private ImageView imagePost;
        private TextView post;
        private TextView coutLike;
        private TextView cmtCout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            userNameNews = itemView.findViewById(R.id.userNameNews);
            avtarUserPost = itemView.findViewById(R.id.avtarUserPost);
            timePost = itemView.findViewById(R.id.timePost);
            imagePost = itemView.findViewById(R.id.imagePost);
            post = itemView.findViewById(R.id.post);
            checkUser = itemView.findViewById(R.id.checkUser);
            coutLike = itemView.findViewById(R.id.likePostTv);
            btnLikePost = itemView.findViewById(R.id.btnLikePost);
            btnComment = itemView.findViewById(R.id.btnComment);
            cmtCout = itemView.findViewById(R.id.cmtCout);
            btnLikePost.setOnClickListener(this);
        }

        void bind(int i) {
            final String key = newsList.get(i).getTime();

            likeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(key).hasChild(HomeFragment.mID)) {
                        btnLikePost.setImageResource(R.drawable.ic_tym_red);
                        coutLike.setText(String.valueOf((int) dataSnapshot.child(key).getChildrenCount()));

                    } else {
                        btnLikePost.setImageResource(R.drawable.ic_no_like);
                        coutLike.setText(String.valueOf((int) dataSnapshot.child(key).getChildrenCount()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (itemClickView != null) {
                itemClickView.onclick(v, getAdapterPosition());
            }

            final int i = getAdapterPosition();
            if (bb == true) {
                likeRef.child(newsList.get(i).getTime()).child(HomeFragment.mID).removeValue().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        btnLikePost.setImageResource(R.drawable.ic_no_like);
                    }
                });
                bb = false;
            } else {

                likeRef.child(newsList.get(i).getTime()).child(HomeFragment.mID).setValue(true).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        btnLikePost.setImageResource(R.drawable.ic_tym_red);
                    }
                });
                bb = true;
            }

        }
    }

}
