package com.example.android_etpj;

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

import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Class;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<Class> classList;
    private Activity activity;
    private ExchangeClass exchange;
    private Date date;

    public void setData(List<Class> list){
        this.classList=list;
        notifyDataSetChanged();
    }

    public ClassAdapter(ExchangeClass exchange) {
        this.exchange = exchange;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity=new Activity();
        activity=(Activity) parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_common,parent,false);
        return new ClassAdapter.ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        Class clss =classList.get(position);
        if(clss==null)
            return;

        SimpleDateFormat formatterDate= new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterDateTime= new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String displayText="";
        displayText="<b>" + "Class ID: " + "</b> " + clss.getClassID()+"<br>"+
                "<b>" + "Class Name: " + "</b> "+ clss.getClassName()+"<br>"+
                "<b>" + "Capacity: " + "</b> " + clss.getCapacity()+"<br>"+
                "<b>" + "Start Date: " + "</b> "+ (clss.getStartTime()!=null ?formatterDate.format(clss.getStartTime()):"" )+"<br>"+
                "<b>" + "End Date: " + "</b> " + (clss.getEndTime()!=null ?formatterDate.format(clss.getEndTime()):"" )+"<br>"

        ;


        holder.tvItem.setText(Html.fromHtml(displayText,1));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEdit(clss);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelete(clss);
            }
        });

    }

    private void setDelete(Class clss) {
        if (new Date().before(clss.getStartTime()) || new Date().after(clss.getEndTime())) {
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
            tvContent.setText("Do you want to delete this item?");

            Button btnYes = dialog.findViewById(R.id.btn_yes);
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApiService.apiService.deleteClass(clss.getClassID()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean deleted = response.body();
                            if (deleted == true) {
                                dialog.cancel();

                                //create dialog success
                                dialogSuccess();

                                exchange.loadData();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
            });

            Button btnCancel = dialog.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            dialog.show();
        } else {
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
            tvContent.setText("Class is opearting. Do you really want to delete this item?");

            Button btnYes = dialog.findViewById(R.id.btn_yes);
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApiService.apiService.deleteClass(clss.getClassID()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean deleted = response.body();
                            if (deleted == true) {
                                dialog.cancel();

                                //create dialog success
                                dialogSuccess();

                                exchange.loadData();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
            });

            Button btnCancel = dialog.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            dialog.show();
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

    private void setEdit(Class clss) {

        exchange.editData(clss);
    }



    @Override
    public int getItemCount() {
        if(classList!=null)
            return classList.size();
        return 0;
    }

    public class ClassViewHolder extends  RecyclerView.ViewHolder{
        private TextView tvItem;
        private ImageButton btnEdit;
        private ImageButton btnDelete;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=itemView.findViewById(R.id.tv_content);
            btnEdit=itemView.findViewById(R.id.btn_edit);
            btnDelete=itemView.findViewById(R.id.btn_delete);

        }
    }
}
