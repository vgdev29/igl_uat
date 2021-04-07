package com.example.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.igl.Activity.NgUserDetailsActivity;
import com.example.igl.Model.NguserListModel;
import com.example.igl.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.constants.Constant.JMR_NO;
import static com.example.constants.Constant.PREFS_JMR_NO;

public class NgUserListAdapter extends RecyclerView.Adapter<NgUserListAdapter.NguserListViewHolder> {

    private Context mctx;
    private List<NguserListModel> nguserList;

    public NgUserListAdapter(Context context, List<NguserListModel> nguserList) {
        this.nguserList = nguserList;
        this.mctx= context;
    }

    @NonNull
    @Override
    public NguserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.ng_user_list_row_items,parent,false);

        return new NguserListViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NguserListViewHolder holder, final int position) {
        final NguserListModel nguserListModel =nguserList.get(position);
        if (!TextUtils.isEmpty(nguserListModel.getBpNo())) {
            holder.tv_bpName.setText(nguserListModel.getBpNo());
        }
        if (!TextUtils.isEmpty(nguserListModel.getNgUpdateDate())) {
            holder.tv_perferedDate.setText(nguserListModel.getNgUpdateDate());
        }
        holder.tv_perferedTime.setText("- - -");

        // priority - 2 is for high color is red
        //            1 is moderate color is blue
        //            0 is for low color is green
        if (!TextUtils.isEmpty(nguserListModel.getPriority())){
            if (nguserListModel.getPriority().equalsIgnoreCase("2")){
                holder.tv_priority.setBackgroundColor(Color.parseColor("#EC4134"));
                holder.tv_priority.setTextColor(Color.WHITE);
            }else if (nguserListModel.getPriority().equalsIgnoreCase("1")){
                holder.tv_priority.setBackgroundColor(Color.parseColor("#2274BE"));
                holder.tv_priority.setTextColor(Color.WHITE);
            }else if (nguserListModel.getPriority().equalsIgnoreCase("0")){
                holder.tv_priority.setBackgroundColor(Color.parseColor("#1BA25A"));
                holder.tv_priority.setTextColor(Color.WHITE);
            }
        }


        holder.ll_bpNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mctx,"click" + position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mctx, NgUserDetailsActivity.class);
                intent.putExtra("jmr_no", nguserListModel.getJmrNo());
                mctx.startActivity(intent);

            }
        });
        SharedPreferences.Editor editor = mctx.getSharedPreferences(PREFS_JMR_NO, MODE_PRIVATE).edit();
        editor.putString(JMR_NO, nguserListModel.getJmrNo());
        editor.apply();


    }

    @Override
    public int getItemCount() {
        return nguserList.size();
    }

    public class NguserListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bpName,tv_perferedDate,tv_perferedTime,tv_priority;
        LinearLayout ll_bpNumber;
        public NguserListViewHolder(View itemView) {
            super(itemView);

            tv_bpName= itemView.findViewById(R.id.tv_bpName);
            tv_perferedDate= itemView.findViewById(R.id.tv_perferedDate);
            tv_perferedTime= itemView.findViewById(R.id.tv_perferedTime);
            tv_priority= itemView.findViewById(R.id.tv_priority);
            ll_bpNumber= itemView.findViewById(R.id.ll_bpNumber);
        }
    }
}
