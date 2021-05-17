package com.example.android_etpj.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_etpj.Feedback;
import com.example.android_etpj.R;
import com.example.android_etpj.SpinnerAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        TextView tvTest=view.findViewById(R.id.tv_test);
        Spinner spinner=view.findViewById(R.id.sp_test);
        SpinnerAdapter spinnerAdapter=new SpinnerAdapter(getContext(),R.layout.item_sp_selected,getListCategory());
        spinnerAdapter.setDropDownViewResource(R.layout.item_sp_category);
        View view1=view.findViewById(R.id.layout_home);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Snackbar snackbar = Snackbar.make(view1,spinnerAdapter.getItem(position).toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String sourceString = "<b>" + "No: " + "</b> " + "1"+"<br>"+"<b>" + "Module Name: " + "</b> "+ "Truyền thông và mạng máy tính";
        tvTest.setText(Html.fromHtml(sourceString,1));
        return view;
    }

    private List<Object> getListCategory() {
        List<Feedback> feedbackList=new ArrayList<>();
        feedbackList.add(new Feedback("Feedback1",".NET"));
        feedbackList.add(new Feedback("Feedback2",".NET"));
        feedbackList.add(new Feedback("Feedback3",".NET"));
        List<Object> objectList=new ArrayList<>();
        objectList.addAll(feedbackList);
        return  objectList;
    }
}
