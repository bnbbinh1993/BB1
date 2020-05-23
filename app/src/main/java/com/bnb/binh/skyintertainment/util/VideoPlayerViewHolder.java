package com.bnb.binh.skyintertainment.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.models.MediaObject;
import com.bumptech.glide.RequestManager;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbnail;
    public ImageView volumeControl;
    public ProgressBar progressBar;
    public RequestManager requestManager;
    public FrameLayout media_container;
    public TextView title;
    public View parent;


    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        media_container = itemView.findViewById(R.id.media_container);
        thumbnail = itemView.findViewById(R.id.thumbnail);
        title = itemView.findViewById(R.id.title);
        progressBar = itemView.findViewById(R.id.progressBar);
        volumeControl = itemView.findViewById(R.id.volume_control);
    }

    public void onBind(MediaObject mediaObject, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);
        title.setText(mediaObject.getTitle());
        this.requestManager
                .load(mediaObject.getThumbnail())
                .into(thumbnail);
    }

}
