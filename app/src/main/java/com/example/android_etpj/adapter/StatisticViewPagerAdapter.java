package com.example.android_etpj.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android_etpj.models.Admin;
import com.example.android_etpj.models.Trainee;
import com.example.android_etpj.models.Trainer;
import com.example.android_etpj.ui.ClassStatisticFragment;
import com.example.android_etpj.ui.PercentStatisticFragment;
import com.example.android_etpj.ui.TopicStatisticFragment;
import com.example.android_etpj.ui.view.ViewClassFragment;

public class StatisticViewPagerAdapter extends FragmentPagerAdapter {
    private Object user;

    public StatisticViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Object user) {
        super(fm, behavior);
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (user instanceof Admin) {
            switch (position) {
                case 0:
                    return new ClassStatisticFragment(user);
                case 1:
                    return new TopicStatisticFragment(user);
                case 2:
                    return new PercentStatisticFragment(user);
                default:
                    return new ClassStatisticFragment(user);
            }
        }
        if (user instanceof Trainer) {
            switch (position) {
                case 0:
                    return new ClassStatisticFragment(user);
                case 1:
                    return new PercentStatisticFragment(user);
                default:
                    return new ClassStatisticFragment(user);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        if (user instanceof Admin) {
            return 3;
        }
        if (user instanceof Trainer) {
            return 2;
        }
        return 0;
    }
}
