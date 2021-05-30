package com.example.android_etpj.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.android_etpj.R;
import com.example.android_etpj.StatisticViewPagerAdapter;

public class ResultFragment extends Fragment {

    private ViewPager vpStatistic;


    public ResultFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_result,container,false);

        vpStatistic = view.findViewById(R.id.vp_statistic);


        StatisticViewPagerAdapter statisticViewPagerAdapter = new StatisticViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpStatistic.setAdapter(statisticViewPagerAdapter);


        return view;
    }
}
