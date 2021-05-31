package com.example.android_etpj;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Class;
import com.example.android_etpj.models.Trainee;

import java.util.List;

public class ViewClassAdapter extends RecyclerView.Adapter<ViewClassAdapter.ViewClassViewHolder> {
    private List<String> traineeIDList;
    private Activity activity;

    public void setData(List<String> list){
        this.traineeIDList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity=new Activity();
        activity=(Activity) parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_common,parent,false);
        return new ViewClassAdapter.ViewClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewClassViewHolder holder, int position) {

        String traineeID = traineeIDList.get(position);
        if(traineeID==null)
            return;
//
//        ApiService.apiService.
    }

    @Override
    public int getItemCount() {
        if(traineeIDList!=null)
            return traineeIDList.size();
        return 0;
    }

    public class ViewClassViewHolder extends RecyclerView.ViewHolder{
        private TextView tvItem;
        private ImageButton btnEdit;
        private ImageButton btnDelete;
        private ImageButton btnView;
        public ViewClassViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=itemView.findViewById(R.id.tv_content);
            btnEdit=itemView.findViewById(R.id.btn_edit);
            btnDelete=itemView.findViewById(R.id.btn_delete);
            btnView=itemView.findViewById(R.id.btn_view);
        }
    }
}
