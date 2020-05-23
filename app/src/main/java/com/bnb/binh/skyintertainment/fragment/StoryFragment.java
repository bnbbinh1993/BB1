package com.bnb.binh.skyintertainment.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.StoryActivity;
import com.bnb.binh.skyintertainment.models.ModelStory;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class StoryFragment extends Fragment {
    private ModelStory modelStory;

    public StoryFragment(ModelStory modelStory) {
        this.modelStory = modelStory;
    }
    private ImageView imageStory;
    private TextView nameUserSotry;
    private TextView timeStory;
    private TextView titleStory;

    private CircleImageView avtStoryFragment;
    private LinearLayout progressContainer;
    private ProgressBar defaultProgressbar;
    private Timer timer;
    private TimerTask timerTask;
    private int progressCount = 0;
    private int progressBarIndex = 0;
    private boolean timeOn = true;
    private float upperlimit,lowerlimit;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

    public StoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initEvent();
        setProgressBars();
        setControls();
    }
    private void initEvent() {

        Glide.with(getContext())
                .load(modelStory.getAvt().get(0))
                .into(avtStoryFragment);
        nameUserSotry.setText(modelStory.getName().get(0));

        Glide.with(getContext())
                .load(modelStory.getImages().get(0))
                .into(imageStory);

        titleStory.setText(modelStory.getTitle().get(0));

        calendar.setTimeInMillis(Long.parseLong(modelStory.getTime().get(0)));
        String dateTime = DateFormat.format("hh:mm aa", calendar).toString();
        timeStory.setText(dateTime);


    }

    private void init(View view) {

        imageStory = view.findViewById(R.id.imageStory);
        nameUserSotry = view.findViewById(R.id.nameStoryFragment);
        timeStory = view.findViewById(R.id.timeStory);
        avtStoryFragment = view.findViewById(R.id.avtStoryFragment);
        titleStory = view.findViewById(R.id.titleStory);


        progressContainer = view.findViewById(R.id.progress_container);
        defaultProgressbar = ((ProgressBar)progressContainer.getChildAt(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        setTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
        timerTask.cancel();
    }

    private void setProgressBars(){
        if (imageStory!=null) {
            for (int i = 1; i< modelStory.getImages().size(); i++) {
                ProgressBar progressBar = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleHorizontal);
                progressBar.setLayoutParams(defaultProgressbar.getLayoutParams());
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.story_progress));
                progressContainer.addView(progressBar);
            }
        }
    }
    private void setTimer(){
        timer = new Timer();
        timerTask =new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (timeOn) {
                            if (progressCount == 100) {
                                progressBarIndex++;
                                if (progressBarIndex < progressContainer.getChildCount()) {
                                    progressCount = 0;
                                    Glide.with(getContext())
                                            .load(modelStory.getImages().get(progressBarIndex))
                                            .into(imageStory);
                                    calendar.setTimeInMillis(Long.parseLong(modelStory.getTime().get(progressBarIndex)));
                                    String dateTime = DateFormat.format("hh:mm aa", calendar).toString();
                                    timeStory.setText(dateTime);
                                    titleStory.setText(modelStory.getTitle().get(progressBarIndex));
                                } else {
                                    if (StoryActivity.viewPager.getCurrentItem()<HomeFragment.listItemModelStory.size()-1){
                                            progressBarIndex =0;
                                            progressCount=0;
                                           StoryActivity.viewPager.setCurrentItem(StoryActivity.viewPager.getCurrentItem()+1);
                                    } else {
                                        timer.cancel();
                                        getActivity().finish();
                                    }
                                }

                            } else {
                                progressCount++;
                                ((ProgressBar) progressContainer.getChildAt(progressBarIndex))
                                        .setProgress(progressCount);
                            }
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,50);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void setControls(){
        float widthProportion = (getResources().getDisplayMetrics().widthPixels/100.0f)*25;
        upperlimit= getResources().getDisplayMetrics().widthPixels - widthProportion;
        lowerlimit= widthProportion;
        imageStory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        if (event.getX()<=lowerlimit && progressBarIndex>0){
                            ((ProgressBar) progressContainer.getChildAt(progressBarIndex))
                                    .setProgress(0);
                            progressBarIndex--;
                            ((ProgressBar) progressContainer.getChildAt(progressBarIndex))
                                    .setProgress(0);
                            progressCount=0;
                            Glide.with(getContext())
                                    .load(modelStory.getImages().get(progressBarIndex))
                                    .into(imageStory);
                            calendar.setTimeInMillis(Long.parseLong(modelStory.getTime().get(progressBarIndex)));
                            String dateTime = DateFormat.format("hh:mm aa", calendar).toString();
                            timeStory.setText(dateTime);
                            titleStory.setText(modelStory.getTitle().get(progressBarIndex));
                        }else if (event.getX()>=upperlimit && progressBarIndex<progressContainer.getChildCount()-1){
                            ((ProgressBar) progressContainer.getChildAt(progressBarIndex))
                                    .setProgress(100);
                            progressBarIndex++;
                            progressCount=0;
                            Glide.with(getContext())
                                    .load(modelStory.getImages().get(progressBarIndex))
                                    .into(imageStory);
                            calendar.setTimeInMillis(Long.parseLong(modelStory.getTime().get(progressBarIndex)));
                            String dateTime = DateFormat.format("hh:mm aa", calendar).toString();
                            timeStory.setText(dateTime);
                            titleStory.setText(modelStory.getTitle().get(progressBarIndex));

                        }else if (event.getX()<=lowerlimit && progressBarIndex==0 && StoryActivity.viewPager.getCurrentItem()>0){
                                StoryActivity.viewPager.setCurrentItem(StoryActivity.viewPager.getCurrentItem()-1);


                        }else if (event.getX()>upperlimit && progressBarIndex==progressContainer.getChildCount()-1 && StoryActivity.viewPager.getCurrentItem()<HomeFragment.listItemModelStory.size()-1){
                            StoryActivity.viewPager.setCurrentItem(StoryActivity.viewPager.getCurrentItem()+1);

                        }else {
                            timeOn =false;
                            return true;
                        }

                    case MotionEvent.ACTION_UP:
                        timeOn =true;
                        return true;
                }
                return false;
            }
        });
    }
}
