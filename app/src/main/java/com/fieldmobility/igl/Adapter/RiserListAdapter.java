package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.Model.RiserTpiListingModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Riser.activity.RiserFormActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RiserListAdapter extends RecyclerView.Adapter<RiserListAdapter.MyHolder> implements Filterable {
    Context mContext;
    List<RiserListingModel.BpDetails.User> datalist;
    List<RiserListingModel.BpDetails.User> tempDatalist;
    public RiserListAdapter(Context context, List<RiserListingModel.BpDetails.User> datalist){
        mContext=context;
        this.datalist=datalist;
        this.tempDatalist=datalist;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiserListAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riser_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        RiserListingModel.BpDetails.User model=datalist.get(position);
        String fullName=model.getFirstName() +" "+model.getLastName();
        holder.tv_name.setText(fullName);
        holder.tv_bp_num.setText(model.getBpNumber());
        holder.tv_mob.setText(model.getMobileNumber());
        try {
            holder.tv_tpi.setText(model.getRfctpiname()+"\n"+model.getRfcmobileNo());
            holder.tv_tpi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+ model.getRfcmobileNo()));
                        mContext.startActivity(callIntent);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){}

        holder.tv_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+ model.getMobileNumber()));
                    mContext.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.tv_assign_date.setText(model.getRiserAssign());
        holder.tv_address.setText(model.getHouseNo()+" "+model.getFloor()+" "+model.getBlockQtrTowerWing()+" "+model.getSociety()+" "+model.getStreetGaliRoad()+", "+model.getArea()+" "+model.getCityRegion()+",\n"+model.getPincode());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_mob, tv_assign_date, tv_address,tv_bp_num,tv_tpi;


        public MyHolder(View itemView) {
            super(itemView);
            tv_bp_num =  itemView.findViewById(R.id.tv_bp_num);
            tv_mob =  itemView.findViewById(R.id.tv_mob);
            tv_name =  itemView.findViewById(R.id.tv_name);
            tv_address =  itemView.findViewById(R.id.tv_address);
            tv_assign_date =  itemView.findViewById(R.id.tv_assign_date);
            tv_tpi = itemView.findViewById(R.id.tv_tpi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(datalist.get(getAdapterPosition()).getRiserClaim().equals("1") ) {
                        String dataJson = new Gson().toJson(datalist.get(getAdapterPosition()));
                        Intent intent = new Intent(mContext, RiserFormActivity.class);
                        intent.putExtra("data", dataJson);
                        mContext.startActivity(intent);
                    }
                    else
                    {
                        CommonUtils.toast_msg(mContext,"TPI not Claimed the Job yet.... ");
                    }
                }
            });

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    datalist = tempDatalist;
                } else {
                    List<RiserListingModel.BpDetails.User> filteredList = new ArrayList<>();
                    for (RiserListingModel.BpDetails.User row : tempDatalist) {
                        String name=row.getFirstName()+" "+row.getLastName();
                        if (row.getBpNumber().toLowerCase().contains(charString.toLowerCase()) || name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    datalist = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = datalist;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                datalist = (ArrayList<RiserListingModel.BpDetails.User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
