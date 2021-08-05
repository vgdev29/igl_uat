package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.Model.RiserTpiListingModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Riser.activity.RiserFormActivity;
import com.fieldmobility.igl.Riser.activity.RiserTpiApprovalDetailActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RiserTpiApprovalListAdapter extends RecyclerView.Adapter<RiserTpiApprovalListAdapter.MyHolder> implements Filterable {
    Context mContext;
    List<RiserTpiListingModel.BpDetail> datalist;
    List<RiserTpiListingModel.BpDetail> tempDatalist;
    public RiserTpiApprovalListAdapter(Context context, List<RiserTpiListingModel.BpDetail> datalist){
        mContext=context;
        this.datalist=datalist;
        this.tempDatalist=datalist;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiserTpiApprovalListAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riser_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        RiserTpiListingModel.BpDetail model=datalist.get(position);
        String fullName=model.getIglFirstName() ;
        holder.tv_name.setText(fullName);
        holder.tv_bp_num.setText(model.getBpNumber());
        holder.tv_mob.setText(model.getIglMobileNo());
        holder.tv_assign_date.setText(model.getCompletionDate());
        holder.tv_address.setText(model.getIglHouseNo()+" "+model.getIglFloor()+" "+model.getIglBlockQtrTowerWing()+" "+model.getIglSociety()+", "+model.getIglArea()+" "+model.getIglCityRegion());
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_mob, tv_assign_date, tv_address,tv_bp_num,tv_date_key;


        public MyHolder(View itemView) {
            super(itemView);
            tv_bp_num =  itemView.findViewById(R.id.tv_bp_num);
            tv_mob =  itemView.findViewById(R.id.tv_mob);
            tv_name =  itemView.findViewById(R.id.tv_name);
            tv_address =  itemView.findViewById(R.id.tv_address);
            tv_assign_date =  itemView.findViewById(R.id.tv_assign_date);
            tv_date_key =  itemView.findViewById(R.id.tv_date_key);
            tv_date_key.setText("Completion Date");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dataJson=new Gson().toJson(datalist.get(getAdapterPosition()));
                    Intent intent= new Intent(mContext, RiserTpiApprovalDetailActivity.class);
                    intent.putExtra("data",dataJson);
                    mContext.startActivity(intent);
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
                    List<RiserTpiListingModel.BpDetail> filteredList = new ArrayList<>();
                    for (RiserTpiListingModel.BpDetail row : tempDatalist) {
                        String name=row.getIglFirstName();
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
                datalist = (ArrayList<RiserTpiListingModel.BpDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
