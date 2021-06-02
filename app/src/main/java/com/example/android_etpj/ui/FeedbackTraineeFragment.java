package com.example.android_etpj.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_etpj.MainActivity;
import com.example.android_etpj.R;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Assignment;
import com.example.android_etpj.models.Trainee;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackTraineeFragment extends Fragment{

    private RecyclerView rcvFeedback;
    private TextView tvTitle;
    private ImageButton btnAdd;
    private List<Assignment> assignmentList;

    private MainActivity mainActivity;
    private Trainee trainee;
    //thêm
    int check;
    private List<Assignment> assignmentsList;
    private Assignment assignment;

    public FeedbackTraineeFragment(Trainee trainee, int check) {
        this.trainee=trainee;
        //thêm
        this.check=check;
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
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rcvFeedback.setLayoutManager(linearLayoutManager);
        //////////////////////////////////////
        showDialogJoin();
        ////////////////////////////////////////////////////////////////

        return view;
    }
    public void showDialogJoin(){
        Dialog dialogJoin = new Dialog(getActivity());
        dialogJoin.setContentView(R.layout.dialog_join);
        dialogJoin.setCancelable(false);

        Window window = dialogJoin.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtCode=dialogJoin.findViewById(R.id.edt_code_assignment);

        Button btnClose = dialogJoin.findViewById(R.id.btn_close);
        Button btnSubmit = dialogJoin.findViewById(R.id.btn_submit);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogJoin.cancel();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService.apiService.getAssignmentByRegistrationCode(edtCode.getText().toString().trim()).enqueue(new Callback<List<Assignment>>() {
                    @Override
                    public void onResponse(Call<List<Assignment>> call, Response<List<Assignment>> response) {
                        assignmentsList= (ArrayList<Assignment>) response.body();
                        if(assignmentsList!=null){
                            assignment=assignmentList.get(0);
                            if(assignment!=null){
                                ApiService.apiService.getAssignmentsByTrainee(trainee.getUserId().toString().trim()).enqueue(new Callback<List<Assignment>>() {
                                    @Override
                                    public void onResponse(Call<List<Assignment>> call, Response<List<Assignment>> response) {
                                        assignmentsList= (ArrayList<Assignment>) response.body();
                                        int checkk=0;//nếu đi qua vòng lặp
                                        if(assignmentsList!=null){
                                            for(int i = 1; i <= assignmentsList.size(); i++){
                                                if(assignmentsList.get(i).getRegistrationCode().equals(assignment.getRegistrationCode())||assignmentsList.get(i).getClassID()==assignment.getClassID()){
                                                    //hiện thông báo đã vào assignment này rồi
                                                    Dialog dialog = new Dialog(getActivity());
                                                    dialog.setContentView(R.layout.dialog_error);
                                                    dialog.setCancelable(false);

                                                    Window window = dialog.getWindow();
                                                    if (window == null)
                                                        return;
                                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                                    TextView tvTitleSuccess=dialog.findViewById(R.id.tv_title);
                                                    tvTitleSuccess.setText("You already join this module, please try another!!!");

                                                    Button btnOk=dialog.findViewById(R.id.btn_ok_error);
                                                    btnOk.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                                    dialog.show();
                                                    checkk=1;
                                                    break;
                                                }
                                            }
                                        }
                                        if(checkk==0){
                                            ApiService.apiService.addEnrollment(assignment.getClassID(),trainee.getUserId()).enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                    if(response.body()==true){
                                                        Dialog dialogSuccess=new Dialog(getActivity());
                                                        dialogSuccess.setContentView(R.layout.dialog_notification);
                                                        dialogSuccess.setCancelable(false);

                                                        Window window=dialogSuccess.getWindow();
                                                        if(window==null)
                                                            return ;
                                                        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                                                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                                        TextView tvTitleSuccess=dialogSuccess.findViewById(R.id.tv_title);
                                                        tvTitleSuccess.setText("Join Success!");

                                                        Button btnOk=dialogSuccess.findViewById(R.id.btn_ok);
                                                        btnOk.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                dialogSuccess.cancel();
                                                                if(getActivity().getSupportFragmentManager()!=null){
                                                                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                                                                    fragmentManager.popBackStack();
                                                                }
                                                            }
                                                        });
                                                        dialogSuccess.show();
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {

                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Assignment>> call, Throwable t) {

                                    }
                                });
                            }
                        }else {
                            Dialog dialog = new Dialog(getActivity());
                            dialog.setContentView(R.layout.dialog_error);
                            dialog.setCancelable(false);

                            Window window = dialog.getWindow();
                            if (window == null)
                                return;
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            TextView tvTitleSuccess=dialog.findViewById(R.id.tv_title);
                            tvTitleSuccess.setText("Invalid Registation Code!!!");

                            Button btnOk=dialog.findViewById(R.id.btn_ok_error);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Assignment>> call, Throwable t) {

                    }
                });
            }
        });
        dialogJoin.show();
    }


}