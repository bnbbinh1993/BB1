package com.bnb.binh.skyintertainment.resoucres;

import com.bnb.binh.skyintertainment.models.MediaObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Resources {
    public static final MediaObject[] MEDIA_OBJECTS = {
            new MediaObject("Sending Data to a New Activity with Intent Extras",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                    "Description for media object #1"),
            new MediaObject("Phép màu - Cover By Thùy Dương",
                    "https://firebasestorage.googleapis.com/v0/b/skyintertainment.appspot.com/o/y2mate.com%20-%20Phép%20Màu%20-%20Thùy%20Dương%20Cover_Da3hSdAOxFo_1080p.mp4?alt=media&token=142bf48e-5f36-453b-ba15-f02b2c1ba286",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png",
                    "Description for media object #2"),
            new MediaObject("MVVM and LiveData",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/MVVM+and+LiveData+for+youtube.mp4",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/mvvm+and+livedata.png",
                    "Description for media object #3"),
            new MediaObject("Swiping Views with a ViewPager",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/SwipingViewPager+Tutorial.mp4",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Swiping+Views+with+a+ViewPager.png",
                    "Description for media object #4"),
            new MediaObject("Database Cache, MVVM, Retrofit, REST API demo for upcoming course",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                    "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+API+Integration+with+MVVM.png",
                    "Description for media object #5"),
    };

}
