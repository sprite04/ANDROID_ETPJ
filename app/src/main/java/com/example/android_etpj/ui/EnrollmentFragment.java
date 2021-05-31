package com.example.android_etpj.ui;

import android.os.Bundle;
import android.util.Log;
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

import com.example.android_etpj.MainActivity;
import com.example.android_etpj.R;
import com.example.android_etpj.adapter.AssignmentAdapter;
import com.example.android_etpj.adapter.EnrollmentAdapter;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.interfaces.ExchangeAssignment;
import com.example.android_etpj.interfaces.ExchangeEnrollment;
import com.example.android_etpj.models.Assignment;
import com.example.android_etpj.models.Enrollment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollmentFragment extends Fragment implements ExchangeEnrollment {

    private RecyclerView rcvEnrollment;
    private EnrollmentAdapter enrollmentAdapter;
    private TextView tvTitle;
    private ImageButton btnAdd;
    private List<Enrollment> enrollmentList;
    private MainActivity mainActivity;
    public EnrollmentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common,container,false);

        mainActivity=(MainActivity)getActivity();

        rcvEnrollment=view.findViewById(R.id.rcv_common);
        tvTitle=view.findViewById(R.id.tv_title);
        btnAdd=view.findViewById(R.id.btn_add);

        enrollmentAdapter=new EnrollmentAdapter(this);
        loadData();

        Log.e("value",String.valueOf(enrollmentAdapter.getItemCount()));

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rcvEnrollment.setLayoutManager(linearLayoutManager);
        rcvEnrollment.setAdapter(enrollmentAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.addEnrollmentFragment();
            }
        });
        return view;
    }

    @Override
    public void loadData() {
        ApiService.apiService.getEnrollment().enqueue(new Callback<List<Enrollment>>() {
            @Override
            public void onResponse(Call<List<Enrollment>> call, Response<List<Enrollment>> response) {
                enrollmentList = (ArrayList<Enrollment>) response.body();
                tvTitle.setText("Enrollment");
                enrollmentAdapter.setData(enrollmentList);
            }

            @Override
            public void onFailure(Call<List<Enrollment>> call, Throwable t) {

            }
        });
    }

    @Override
    public void editData(Enrollment enrollment) {
        mainActivity.editEnrollmentFragment(enrollment);
    }

    @Override
    public void viewData(Enrollment enrollment) {
        mainActivity.viewEnrollmentFragment(enrollment);
    }
}
