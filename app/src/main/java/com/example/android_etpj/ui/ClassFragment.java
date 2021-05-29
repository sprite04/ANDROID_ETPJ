package com.example.android_etpj.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_etpj.ClassAdapter;
import com.example.android_etpj.ExchangeClass;
import com.example.android_etpj.MainActivity;
import com.example.android_etpj.R;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Class;
import com.example.android_etpj.models.Module;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment implements ExchangeClass {

    private MainActivity mainActivity;
    private RecyclerView rcvClass;
    private ClassAdapter classAdapter;
    private TextView tvTitle;
    private ImageButton btnAdd;
    private List<Class> classList;

    public ClassFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common,container,false);

        mainActivity=(MainActivity)getActivity();

        rcvClass=view.findViewById(R.id.rcv_common);
        tvTitle=view.findViewById(R.id.tv_title);
        btnAdd=view.findViewById(R.id.btn_add);
        //btnAdd.setVisibility(View.GONE);

        classAdapter=new ClassAdapter(this);
        loadData();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rcvClass.setLayoutManager(linearLayoutManager);
        rcvClass.setAdapter(classAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.addClassFragment();
            }
        });

        return view;
    }

    @Override
    public void loadData() {
        ApiService.apiService.getClasses().enqueue(new Callback<List<Class>>() {
            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {
                classList = (ArrayList<Class>) response.body();
                tvTitle.setText("Class List");

                classAdapter.setData(classList);
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {

            }
        });
    }

    @Override
    public void editData(Class clss) {

        mainActivity.editClassFragment(clss);
    }
}
