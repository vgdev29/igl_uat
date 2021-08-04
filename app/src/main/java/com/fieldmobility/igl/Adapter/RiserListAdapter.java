package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Riser.activity.RiserFormActivity;
import com.google.gson.Gson;

import java.util.List;

public class RiserListAdapter extends RecyclerView.Adapter<RiserListAdapter.MyHolder> /*implements Filterable*/ {
    Context mContext;
    List<RiserListingModel.BpDetails.User> datalist;
    public RiserListAdapter(Context context, List<RiserListingModel.BpDetails.User> datalist){
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
        RiserListingModel.BpDetails.User model=datalist.get(position);
        String fullName=model.getFirstName() +" "+model.getLastName();
        holder.tv_name.setText(fullName);
        holder.tv_mob.setText(model.getMobileNumber());
        holder.tv_assign_date.setText(model.getRiserAssign());
        holder.tv_address.setText(model.getHouseNo()+" "+model.getFloor()+" "+model.getBlockQtrTowerWing()+" "+model.getSociety()+" "+model.getStreetGaliRoad()+", "+model.getArea()+" "+model.getCityRegion()+",\n"+model.getPincode());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_mob, tv_assign_date, tv_address;


        public MyHolder(View itemView) {
            super(itemView);
            tv_mob =  itemView.findViewById(R.id.tv_mob);
            tv_name =  itemView.findViewById(R.id.tv_name);
            tv_address =  itemView.findViewById(R.id.tv_address);
            tv_assign_date =  itemView.findViewById(R.id.tv_assign_date);
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
