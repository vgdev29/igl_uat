package com.fieldmobility.igl.Adapter;

import android.content.Context;
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

import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.NICList;
import com.fieldmobility.igl.Model.NiListingModel;
import com.fieldmobility.igl.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NICListAdapter extends RecyclerView.Adapter<NICListAdapter.MyHolder> implements Filterable {
    Context mContext;
    ArrayList<NICList> datalist;
    ArrayList<NICList> tempdatalist;
    public NICListAdapter(Context context, ArrayList<NICList> datalist){
        mContext=context;
        this.datalist=datalist;
        this.tempdatalist=datalist;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NICListAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nic_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        NICList model=datalist.get(position);
        holder.date_text.setText("Submitted On - "+model.getCreatedOn());
        holder.user_name_text.setText(model.getCustomerName());
        holder.address_text.setText(model.getAddress()+"\n"+model.getSociety()+", "+model.getArea()+"\n"+model.getCity());
        holder.mob_text.setText(model.getMobile());
        holder.tv_reason.setText(model.getReason());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView user_name_text, address_text, date_text, mob_text,tv_reason;
        public LinearLayout liner_layout;
        public CardView linearLayout;

        public MyHolder(View itemView) {
            super(itemView);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            tv_reason = (TextView) itemView.findViewById(R.id.tv_reason);
            mob_text = itemView.findViewById(R.id.mobile_text);
            liner_layout = (LinearLayout) itemView.findViewById(R.id.liner_layout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    datalist = tempdatalist;
                } else {
                    ArrayList<NICList> filteredList = new ArrayList<NICList>();
                    for (NICList row : tempdatalist) {
                        if (row.getCustomerName().toLowerCase().contains(charString.toLowerCase()) || row.getCustomerName().toLowerCase().contains(charString.toLowerCase())) {
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
                datalist = (ArrayList<NICList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
