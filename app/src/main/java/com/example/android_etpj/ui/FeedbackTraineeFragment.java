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
import com.example.android_etpj.adapter.FeedbackAdapter;
import com.example.android_etpj.adapter.FeedbackTraineeAdapter;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.interfaces.ExchangeFeedbackTrainee;
import com.example.android_etpj.models.Assignment;
import com.example.android_etpj.models.Trainee;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackTraineeFragment extends Fragment implements ExchangeFeedbackTrainee {

    private RecyclerView rcvFeedback;
    private FeedbackTraineeAdapter feedbackTraineeAdapter;
    private TextView tvTitle;
    private ImageButton btnAdd;
    private List<Assignment> assignmentList;

    private MainActivity mainActivity;
    private Trainee trainee;

    public FeedbackTraineeFragment(Trainee trainee) {
        this.trainee=trainee;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common,container,false);

        mainActivity=(MainActivity)getActivity();

        rcvFeedback=view.findViewById(R.id.rcv_common);
        tvTitle=view.findViewById(R.id.tv_title);
        btnAdd=view.findViewById(R.id.btn_add);
        btnAdd.setVisibility(View.GONE);



        feedbackTraineeAdapter=new FeedbackTraineeAdapter(this,trainee);
        loadData();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rcvFeedback.setLayoutManager(linearLayoutManager);
        rcvFeedback.setAdapter(feedbackTraineeAdapter);



        return view;
    }

    @Override
    public void loadData() {
        ApiService.apiService.getAssignmentsByTrainee(trainee.getUserId()).enqueue(new Callback<List<Assignment>>() {
            @Override
            public void onResponse(Call<List<Assignment>> call, Response<List<Assignment>> response) {
                assignmentList=response.body();
                Log.e("thu",String.valueOf(assignmentList.size()));
                tvTitle.setText("List Feedback");
                feedbackTraineeAdapter.setData(assignmentList);
            }

            @Override
            public void onFailure(Call<List<Assignment>> call, Throwable t) {
                Log.e("loi",t.getMessage());
            }
        });
    }


    @Override
    public void reviewData(Assignment assignment, Trainee trainee) {

        mainActivity.addFeedbackTraineeReviewFragment(assignment,trainee);

    }
}
