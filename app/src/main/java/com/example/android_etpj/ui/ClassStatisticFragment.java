package com.example.android_etpj.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android_etpj.MainActivity;
import com.example.android_etpj.R;
import com.example.android_etpj.SpinnerAdapter;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Class;
import com.example.android_etpj.models.Module;
import com.example.android_etpj.models.Topic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassStatisticFragment extends Fragment {

    Spinner spClassSearch;
    Spinner spModuleSearch;

    TextView tvClassSpinner;
    TextView tvModuleSpinner;
    TextView tvStatisticTitle;
    TextView tvGraphTitle;

    Class clss;
    Module module;



    public ClassStatisticFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_class_statistic, container, false);

        clss = new Class();
        module = new Module();

        tvClassSpinner = view.findViewById(R.id.tv_title_search_1);
        tvModuleSpinner = view.findViewById(R.id.tv_title_search_2);
        tvStatisticTitle = view.findViewById(R.id.tv_title_statistic);
        tvGraphTitle = view.findViewById(R.id.tv_title_graph);

        tvClassSpinner.setText("Class:");
        tvModuleSpinner.setText("Module:");

        spClassSearch = view.findViewById(R.id.sp_search_1);
        spModuleSearch = view.findViewById(R.id.sp_search_2);

        setClassSpinnerSearch();
        setModuleSpinnerSearch();

        return view;
    }

    private void setClassSpinnerSearch() {
        ApiService.apiService.getClasses().enqueue(new Callback<List<Class>>() {
            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {
                List<Class> arrayClassList=(ArrayList<Class>) response.body();
                List<Object> classes = new ArrayList<>();
                classes.addAll(arrayClassList);

                SpinnerAdapter spClassAdapter = new SpinnerAdapter(getContext(),R.layout.item_sp_selected,classes);
                try {
                    spClassSearch.setAdapter(spClassAdapter);
                } catch (Exception e) {
                    Log.e("a",e.getMessage());
                }


                if(arrayClassList.size()>=2) {
                    spClassSearch.setSelection(spClassAdapter.getPosition(arrayClassList.get(0)));
                }

                spClassSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object object=spClassAdapter.getItem(position);
                        clss = (Class) object;
                        String tmp = "<font color = #000000>Feedback Statistics of Class </font> <font color = #F4D484>" + clss.getClassName() +"</font>";
                        tvStatisticTitle.setText(Html.fromHtml(tmp,1));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {

            }
        });
    }

    private void setModuleSpinnerSearch() {
        ApiService.apiService.getModules().enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                List<Module> arrayModuleList=(ArrayList<Module>) response.body();
                List<Object> modules = new ArrayList<>();
                modules.addAll(arrayModuleList);

                SpinnerAdapter spModuleAdapter = new SpinnerAdapter(getContext(),R.layout.item_sp_selected,modules);
                try {
                    spModuleSearch.setAdapter(spModuleAdapter);
                } catch (Exception e) {
                    Log.e("a",e.getMessage());
                }


                if(arrayModuleList.size()>=2) {
                    spModuleSearch.setSelection(spModuleAdapter.getPosition(arrayModuleList.get(0)));
                }

                spModuleSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object object=spModuleAdapter.getItem(position);
                        module = (Module)object;
                        String tmp = "<font color = #000000>Feedback Statistics of Module </font> <font color = #F4D484>" + module.getModuleName() +"</font>";
                        tvGraphTitle.setText(Html.fromHtml(tmp,1));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {

            }
        });
    }
}