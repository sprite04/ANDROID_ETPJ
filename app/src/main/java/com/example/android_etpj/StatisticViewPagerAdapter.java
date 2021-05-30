package com.example.android_etpj;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android_etpj.ui.ClassStatisticFragment;
import com.example.android_etpj.ui.TopicStatisticFragment;

public class StatisticViewPagerAdapter extends FragmentPagerAdapter {
    public StatisticViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ClassStatisticFragment();
            case 1:
                return new TopicStatisticFragment();
            default:
                return new ClassStatisticFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
