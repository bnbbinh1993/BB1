package com.bnb.binh.skyintertainment.activity;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.custom.StoryPageTranstion;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.fragment.StoryFragment;
import com.google.firebase.database.FirebaseDatabase;


public class StoryActivity extends FragmentActivity {
    public static int INDEX;
    public static String id;
    public static ViewPager2 viewPager;

    private FragmentStateAdapter pagerAdapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        viewPager = findViewById(R.id.pager);
        viewPager.setPageTransformer(new StoryPageTranstion());
        pagerAdapter = new StorySlideAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(INDEX);

    }



   private class StorySlideAdapter extends FragmentStateAdapter {
        public StorySlideAdapter(FragmentActivity fa) {
            super(fa);
        }
        @Override
        public Fragment createFragment(int position) {
            return new StoryFragment(HomeFragment.listItemModelStory.get(position));
        }

        @Override
        public int getItemCount() {
            return HomeFragment.listItemModelStory.size();
        }
    }

}
