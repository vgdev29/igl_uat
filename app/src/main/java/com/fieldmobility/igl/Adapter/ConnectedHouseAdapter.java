package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Model.ConnectedHouseModel;
import com.fieldmobility.igl.Model.NICList;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConnectedHouseAdapter extends RecyclerView.Adapter<ConnectedHouseAdapter.MyHolder> {
    Context mContext;
    private  ArrayList<ConnectedHouseModel> dataList = new ArrayList<>();

    public ConnectedHouseAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConnectedHouseAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_connected_hse, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ConnectedHouseModel model = dataList.get(position);
        holder.tv_bp_num.setText(model.getBp_number());
        holder.tv_house_num.setText(model.getHouse_num());
        holder.tv_floor_num.setText(model.getFloor_num());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_bp_num, tv_house_num, tv_floor_num;
        ImageView iv_remove;

        public MyHolder(View itemView) {
            super(itemView);
            iv_remove = itemView.findViewById(R.id.iv_remove);
            tv_house_num = itemView.findViewById(R.id.tv_house_num);
            tv_bp_num = itemView.findViewById(R.id.tv_bp_num);
            tv_floor_num = itemView.findViewById(R.id.tv_floor_num);
            iv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }
    }

    public void addMoreHouse(ConnectedHouseModel model) {
        dataList.add(model);
        notifyDataSetChanged();
    }

    public  ArrayList<ConnectedHouseModel> getFilledData() {
        return dataList;
    }

    public  JSONObject getJsonData() {
        JSONObject objects = new JSONObject();
        JSONArray array = new JSONArray();
        try {

        for (ConnectedHouseModel model : dataList)
        {
            JSONObject object = new JSONObject();

                object.put("bp",model.getBp_number());
                object.put("hno",model.getHouse_num());
                object.put("floor",model.getFloor_num());

            array.put(object);
        }

        objects.put("list",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objects;
    }


}
