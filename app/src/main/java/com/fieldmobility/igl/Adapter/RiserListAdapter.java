package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Model.NICList;
import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Riser.activity.RiserFormActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RiserListAdapter extends RecyclerView.Adapter<RiserListAdapter.MyHolder> /*implements Filterable*/ {
    Context mContext;
    ArrayList<RiserListingModel> datalist;
    public RiserListAdapter(Context context, ArrayList<RiserListingModel> datalist){
        mContext=context;
        this.datalist=datalist;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiserListAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riser_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        RiserListingModel model=datalist.get(position);
        holder.tv_vendor.setText(model.getAllocation().getVendorName());
        holder.tv_allocation.setText(model.getAllocation().getAllocationNumber());
        holder.tv_sub_allocation.setText(model.getSuballocationNumber());
        holder.tv_address.setText(model.getAllocation().getSociety()+" "+model.getAllocation().getStreet()+", "+model.getAllocation().getArea()+" "+model.getAllocation().getZone()+" "+model.getAllocation().getCity());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_vendor, tv_allocation, tv_sub_allocation, tv_address;


        public MyHolder(View itemView) {
            super(itemView);
            tv_allocation =  itemView.findViewById(R.id.tv_allocation);
            tv_vendor =  itemView.findViewById(R.id.tv_vendor);
            tv_sub_allocation =  itemView.findViewById(R.id.tv_sub_allocation);
            tv_address =  itemView.findViewById(R.id.tv_address);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dataJson=new Gson().toJson(datalist.get(getAdapterPosition()));
                    Intent intent= new Intent(mContext, RiserFormActivity.class);
                    intent.putExtra("data",dataJson);
                    mContext.startActivity(intent);
                }
            });

        }
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    datalist = tempdatalist;
//                } else {
//                    ArrayList<NICList> filteredList = new ArrayList<NICList>();
//                    for (NICList row : tempdatalist) {
//                        if (row.getCustomerName().toLowerCase().contains(charString.toLowerCase()) || row.getCustomerName().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//                    datalist = filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = datalist;
//                return filterResults;
//            }
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                datalist = (ArrayList<NICList>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

}
