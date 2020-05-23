package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.StoryActivity;
import com.bnb.binh.skyintertainment.models.ModelStory;
import com.bnb.binh.skyintertainment.models.Story;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {
    private Context context;
    private List<Story> modelStoryList;

    public StoriesAdapter(Context context, List<Story> modelStoryList) {
        this.context = context;
        this.modelStoryList = modelStoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_story_recyclerview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
//        final ModelStory modelStory = modelStoryList.get(i);
//        viewHolder.userName.setText(modelStory.getName());
//        Glide.with(context).load(modelStory.getAvt()).placeholder(R.mipmap.logoavatar)
//                .into(viewHolder.avtStories);
//        Glide.with(context).load(modelStory.getImages().get(0))
//                .centerCrop()
//                .placeholder(R.mipmap.background)
//                .into(viewHolder.thumbnailStories);



        Story story = modelStoryList.get(i);
        final String id = story.getId();
        String image = story.getImage();
        Glide.with(context).load(image).centerCrop().into(viewHolder.thumbnailStories);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(id).child("name").getValue().toString();
                String avt = dataSnapshot.child(id).child("avt").getValue().toString();

                viewHolder.userName.setText(name);
                Glide.with(context).load(avt).into(viewHolder.avtStories);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryActivity.class);
                context.startActivity(intent);
                StoryActivity.INDEX = i;
                StoryActivity.id = id;
            }
        });





    }

    @Override
    public int getItemCount() {
        return modelStoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private ImageView thumbnailStories;
        private CircleImageView avtStories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            avtStories = itemView.findViewById(R.id.avtStories);
            thumbnailStories = itemView.findViewById(R.id.thumbnailStories);
        }
    }
}
