package com.example.android_etpj.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_etpj.R;

public class AccessForbiddenHomePageFragment extends Fragment {
    private TextView btnHomepage;
    private AcessForBiddenHF acessForBiddenHF;
    public interface AcessForBiddenHF{
        void fragmentHome();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_access_forbidden_2, container, false);
        btnHomepage = view.findViewById(R.id.btn_go_to_homepage);
        btnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessForBiddenHF.fragmentHome();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        acessForBiddenHF=(AcessForBiddenHF) getActivity();
    }
}
