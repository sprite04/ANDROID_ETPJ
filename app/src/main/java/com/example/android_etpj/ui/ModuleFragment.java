package com.example.android_etpj.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_etpj.LoadData;
import com.example.android_etpj.ModuleAdapter;
import com.example.android_etpj.R;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Feedback;
import com.example.android_etpj.models.Module;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleFragment extends Fragment implements LoadData {

    private RecyclerView rcvModule;
    private ModuleAdapter moduleAdapter;
    private TextView tvTitle;
    private ImageButton btnAdd;
    private List<Module> moduleList;

    public ModuleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common,container,false);

        rcvModule=view.findViewById(R.id.rcv_common);
        tvTitle=view.findViewById(R.id.tv_title);
        btnAdd=view.findViewById(R.id.btn_add);
        //btnAdd.setVisibility(View.GONE);



        moduleAdapter=new ModuleAdapter(this);
        loadData();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rcvModule.setLayoutManager(linearLayoutManager);
        rcvModule.setAdapter(moduleAdapter);
        //setRecyclerView();




        return view;
    }

    private void setRecyclerView() {
        moduleAdapter=new ModuleAdapter(this);
        moduleAdapter.setData(moduleList);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());

        rcvModule.setLayoutManager(linearLayoutManager);
        rcvModule.setAdapter(moduleAdapter);
    }

    @Override
    public void loadData() {
        ApiService.apiService.getModules().enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                moduleList = (ArrayList<Module>) response.body();
                tvTitle.setText("Module List");

                moduleAdapter.setData(moduleList);


            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {

            }
        });
    }
}
