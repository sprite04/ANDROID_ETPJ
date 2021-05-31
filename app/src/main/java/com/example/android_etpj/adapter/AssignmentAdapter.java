package com.example.android_etpj.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_etpj.interfaces.ExchangeAssignment;
import com.example.android_etpj.R;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<Assignment> assignmentsList;
    private Activity activity;
    private ExchangeAssignment exchange;


    public void setData(List<Assignment> list) {
        this.assignmentsList = list;
        notifyDataSetChanged();
    }

    public AssignmentAdapter(ExchangeAssignment exchange) {
        this.exchange = exchange;
    }

    @NonNull
    @Override
    public AssignmentAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = new Activity();
        activity = (Activity) parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_common, parent, false);
        return new AssignmentAdapter.AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.AssignmentViewHolder holder, int position) {
        Assignment assignment = assignmentsList.get(position);
        if (assignment == null)
            return;

        String displayText = "";
        displayText = "<b>" + "No.: " + "</b> " + position+1 + "<br>" +
                "<b>" + "Course Name: " + "</b> " + assignment.getModule().getModuleName() + "<br>" +
                "<b>" + "Class Name: " + "</b> " + assignment.getClss().getClassName() + "<br>" +
                "<b>" + "Trainer Name: " + "</b> " + assignment.getTrainer().getName() + "<br>" +
                "<b>" + "Registration : " + "</b> " + assignment.getRegistrationCode() + "<br>";


        holder.tvItem.setText(Html.fromHtml(displayText, 1));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEdit(assignment);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelete(assignment);
            }
        });


    }

    private void setEdit(Assignment assignment) {
        exchange.editData(assignment);
    }

    @Override
    public int getItemCount() {
        if(assignmentsList!=null)
            return assignmentsList.size();
        return 0;
    }

    private void setDelete(Assignment assignment) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        tvTitle.setText("Are you sure?");

        TextView tvContent = dialog.findViewById(R.id.tv_content);
        tvContent.setText("Do you want to delete this Assignment?");

        Button btnYes = dialog.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService.apiService.deleteAssignment(assignment.getClassID(), assignment.getModuleID()).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Boolean result = response.body();
                        if (result == true) {
                            Boolean deleted=response.body();
                            if(deleted==true){
                                dialog.cancel();
                                //create dialog success
                                dialogSuccess();
                                exchange.loadData();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });
        Button btnCancel=dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    public class AssignmentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;
        private ImageButton btnEdit;
        private ImageButton btnDelete;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_content);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
    private void dialogSuccess(){
        Dialog dialogSuccess=new Dialog(activity);
        dialogSuccess.setContentView(R.layout.dialog_notification);
        dialogSuccess.setCancelable(false);

        Window window=dialogSuccess.getWindow();
        if(window==null)
            return ;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitleSuccess=dialogSuccess.findViewById(R.id.tv_title);
        tvTitleSuccess.setText("Delete Success!");

        Button btnOk=dialogSuccess.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccess.cancel();
            }
        });
    }
}

