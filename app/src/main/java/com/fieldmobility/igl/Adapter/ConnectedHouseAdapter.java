package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Model.NICList;
import com.fieldmobility.igl.R;

import java.util.ArrayList;

public class ConnectedHouseAdapter extends RecyclerView.Adapter<ConnectedHouseAdapter.MyHolder> {
    Context mContext;
    public ConnectedHouseAdapter(Context mContext) {
     this.mContext=mContext;
    }

    public  int itemCount = 0;


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConnectedHouseAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_connected_house, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_bp_num;
        EditText et_house, et_floor;

        public MyHolder(View itemView) {
            super(itemView);
            et_floor = itemView.findViewById(R.id.et_floor);
            et_house = itemView.findViewById(R.id.et_house);
            tv_bp_num = itemView.findViewById(R.id.tv_bp_num);

        }
    }

    public void addMoreHouse() {
        itemCount++;
        notifyDataSetChanged();
    }


}
