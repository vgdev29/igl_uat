package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Model.RiserTpiListingModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Riser.activity.RiserFormActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RiserTpiApprovalDetailAdapter extends RecyclerView.Adapter<RiserTpiApprovalDetailAdapter.MyHolder> {
    Context mContext;
    RiserTpiListingModel.BpDetail data;

    public RiserTpiApprovalDetailAdapter(Context context, RiserTpiListingModel.BpDetail data) {
        mContext = context;
        this.data = data;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiserTpiApprovalDetailAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_detail_sceen_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Log.d("riser approval adapter ","holder position = "+ position);
        switch (position) {
            case 0:
                holder.tv_key.setText("Name :");
                holder.tv_value.setText(data.getIglFirstName());
                break;
            case 1:
                holder.tv_key.setText("Mobile No. :");
                holder.tv_value.setText(data.getIglMobileNo());
                break;
            case 2:
                holder.tv_key.setText("House No. :");
                holder.tv_value.setText(data.getIglHouseNo());
                break;
            case 3:
                holder.tv_key.setText("Floor :");
                holder.tv_value.setText(data.getIglFloor());
                break;
            case 4:
                holder.tv_key.setText("Society :");
                holder.tv_value.setText(data.getIglSociety());
                break;
            case 5:
                holder.tv_key.setText("Block :");
                holder.tv_value.setText(data.getIglBlockQtrTowerWing());
                break;
            case 6:
                holder.tv_key.setText("Area :");
                holder.tv_value.setText(data.getIglArea());
                break;
            case 7:
                holder.tv_key.setText("City :");
                holder.tv_value.setText(data.getIglCityRegion());
                break;
            case 8:
                holder.tv_key.setText("Riser No. :");
                holder.tv_value.setText(data.getRiserNo());
                break;
            case 9:
                holder.tv_key.setText("Connected House :");
                holder.tv_value.setText(data.getConnectedHouse());
                break;
            case 10:
                holder.tv_key.setText("Property Type :");
                holder.tv_value.setText(data.getPropertyType());
                break;
            case 11:
                holder.tv_key.setText("Gas Type :");
                holder.tv_value.setText(data.getGasType());
                break;
            case 12:
                holder.tv_key.setText("HSE Floor :");
                holder.tv_value.setText(data.getHseFloor());
                break;
            case 13:
                holder.tv_key.setText("HSE GI :");
                holder.tv_value.setText(data.getHseGi());
                break;
            case 14:
                holder.tv_key.setText("Total IV :");
                holder.tv_value.setText(data.getTotalIb());
                break;

            case 15:
                holder.tv_key.setText("Area Type :");
                holder.tv_value.setText(data.getAreaType());
                break;
            case 16:
                holder.tv_key.setText("Laying :");
                String laying = "Not Done";
                if (data.getLaying().equals("1")) {
                laying = "Done";
            }
            holder.tv_value.setText(laying);
            break;
            case 17:
                holder.tv_key.setText("Testing :");
                String testing = "Not Done";
                if (data.getTesting().equals("1")) {
                    testing = "Done";
                }
                holder.tv_value.setText(testing);
                break;
            case 18:
                holder.tv_key.setText("Commissioning :");
                String commissioning = "Not Done";
                if (data.getCommissioning().equals("1")) {
                    commissioning = "Done";
                }
                holder.tv_value.setText(commissioning);
                break;
            case 20:
                holder.tv_key.setText("Lateral Tapping :");
                holder.tv_value.setText(data.getLateralTapping());
                break;
            case 19:
                holder.tv_key.setText("Contractor :");
                holder.tv_value.setText(data.getContractorId());
                break;
            case 21:
                holder.tv_key.setText("Supervisor :");
                holder.tv_value.setText(data.getSupervisorId());
                break;
            case 22:
                holder.tv_key.setText("Completion Date :");
                holder.tv_value.setText(data.getCompletionDate());
                break;
            case 23:
                holder.tv_key.setText("Riser Length G1/2:");
                holder.tv_value.setText(data.getRl12());
                break;
            case 24:
                holder.tv_key.setText("Riser Length G3/4:");
                holder.tv_value.setText(data.getRl34());
                break;
            case 25:
                holder.tv_key.setText("Riser Length G1:");
                holder.tv_value.setText(data.getRl1());
                break;
            case 26:
                holder.tv_key.setText("Riser Length G2:");
                holder.tv_value.setText(data.getRl2());
                break;
            case 27:
                holder.tv_key.setText("Isolation Valve G1/2 :");
                holder.tv_value.setText(data.getIv12());
                break;
            case 28:
                holder.tv_key.setText("Isolation Value G3/4:");
                holder.tv_value.setText(data.getIv34());
                break;
            case 29:
                holder.tv_key.setText("Isolation Value G1:");
                holder.tv_value.setText(data.getIv1());
                break;
            case 30:
                holder.tv_key.setText("Isolation Value G2:");
                holder.tv_value.setText(data.getIv2());
                break;

        }
    }


    @Override
    public int getItemCount() {
        return 30;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_key, tv_value;


        public MyHolder(View itemView) {
            super(itemView);
            tv_value = itemView.findViewById(R.id.tv_value);
            tv_key = itemView.findViewById(R.id.tv_key);
        }
    }


}
