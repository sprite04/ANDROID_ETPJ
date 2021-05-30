package com.example.android_etpj.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.android_etpj.R;
import com.example.android_etpj.Role;
import com.example.android_etpj.SpinnerAdapter;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.sharedpreference.DataLocal;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.android_etpj.models.*;
public class HomeFragment extends Fragment {

    View view1;
    private Object user;
    public HomeFragment(Object user) {
        this.user=user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        TextView tvTest=view.findViewById(R.id.tv_test);
        Spinner spinner=view.findViewById(R.id.sp_test);
        /*SpinnerAdapter spinnerAdapter=new SpinnerAdapter(getContext(),R.layout.item_sp_selected,getListCategory());
        spinnerAdapter.setDropDownViewResource(R.layout.item_sp_category);*/
        view1=view.findViewById(R.id.layout_home);

        TextView tvRetrofit=view.findViewById(R.id.tv_retrofit);
        TextView tvRetrofit2=view.findViewById(R.id.tv_retrofit_2);
        Button btnAdd=view.findViewById(R.id.btn_add);

        /*
        * My add start here for testing only
        * */






        /*
        * My add end here
        * */

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });




        ApiService.apiService.getFeedbackById(1).enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback=response.body();
                if(feedback!=null){
                    tvRetrofit.setText(feedback.toString());
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {

            }
        });

        ApiService.apiService.getModules().enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                ArrayList<Module> modules = (ArrayList<Module>) response.body();
                if(modules.size()>0){
                    tvRetrofit2.setText(modules.get(0).toString());
                }
                else{
                    tvRetrofit2.setText("0");
                }
            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {

            }
        });

       /* ApiService.apiService.getAdmins().enqueue(new Callback<List<Admin>>() {
            @Override
            public void onResponse(Call<List<Admin>> call, Response<List<Admin>> response) {
                ArrayList<Admin> modules = (ArrayList<Admin>) response.body();
                if(modules.size()>0){
                    tvRetrofit2.setText(modules.get(0).toString());
                }
                else{
                    tvRetrofit2.setText("0");
                }
            }

            @Override
            public void onFailure(Call<List<Admin>> call, Throwable t) {

            }
        });*/





        /*spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Snackbar snackbar = Snackbar.make(view1,spinnerAdapter.getItem(position).toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        String sourceString = "<b>" + "No: " + "</b> " + "1"+"<br>"+"<b>" + "Module Name: " + "</b> "+ "Truyền thông và mạng máy tính";
        tvTest.setText(Html.fromHtml(sourceString,1));
        return view;
    }

    private void test(){

        Snackbar snackbar = Snackbar.make(view1,((Admin)this.user).getName(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void addFeedback(){
        Feedback feedback=new Feedback("Feedback 6","admin2",2);

        ApiService.apiService.addFeedback(feedback).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                Snackbar snackbar = Snackbar.make(view1,response.body().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(view1,"Failure", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void editFeedback(){
        Feedback feedback=new Feedback(1,"Feedback Edit 22","admin2",2);

        ApiService.apiService.editFeedback(feedback).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Snackbar snackbar = Snackbar.make(view1,response.body().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private void deleteFeedback(){
        //Feedback feedback=new Feedback(1,"Feedback Edit 22","admin2",2);

        ApiService.apiService.deleteFeedback(2).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Snackbar snackbar = Snackbar.make(view1,response.body().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

   /* private List<Object> getListCategory() {
        List<Feedback> feedbackList=new ArrayList<>();
        feedbackList.add(new Feedback("Feedback1",".NET"));
        feedbackList.add(new Feedback("Feedback2",".NET"));
        feedbackList.add(new Feedback("Feedback3",".NET"));
        List<Object> objectList=new ArrayList<>();
        objectList.addAll(feedbackList);
        return  objectList;
    }*/
}
