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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Activity.Bp_Created_Detail;
import com.fieldmobility.igl.Activity.DocumentResumissionDetail;
import com.fieldmobility.igl.Activity.Document_varification_Detail;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class New_BP_NO_Adapter extends RecyclerView.Adapter<New_BP_NO_Adapter.ViewHolder> implements Filterable {
    Context context;
    boolean isForResubmition=false;
    // RecyclerView recyclerView;
    private List<Bp_No_Item> bp_no_list_array;
    private List<Bp_No_Item> New_bp_no_list_array;
    private New_BP_NO_Adapter.ContactsAdapterListener listener;
    public New_BP_NO_Adapter(Context context, List<Bp_No_Item> New_bp_no_list_array, New_BP_NO_Adapter.ContactsAdapterListener listener,boolean isForResubmition) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.listener = listener;
        this.context = context;
        this.isForResubmition=isForResubmition;
    }



    @Override
    public New_BP_NO_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View listItem= layoutInflater.inflate(R.layout.bp_no_listing_item, parent, false);
//        New_BP_NO_Adapter.ViewHolder viewHolder = new New_BP_NO_Adapter.ViewHolder(listItem);
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        listItem.setLayoutParams(lp);
//        return viewHolder;
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bp_no_listing_item, parent, false));


    }

    @Override
    public void onBindViewHolder(New_BP_NO_Adapter.ViewHolder holder, final int position) {
        final Bp_No_Item Bp_No_array = bp_no_list_array.get(position);
        holder.status_text.setVisibility(View.GONE);
        holder.date_text.setText("Registration Date - "+Bp_No_array.getBp_date());
        holder.bp_no_text.setText(Bp_No_array.getBp_number());
        holder.user_name_text.setText(Bp_No_array.getFirst_name());
    //    holder.address_text.setText(Bp_No_array.getHouse_no()+" "+Bp_No_array.getSociety()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getCity_region());
        holder.address_text.setText(Bp_No_array.getHouse_no()+" "+Bp_No_array.getFloor()+" "+Bp_No_array.getHouse_type()+" "+Bp_No_array.getSociety()+" \n"
                +Bp_No_array.getBlock_qtr_tower_wing()+" "+Bp_No_array.getStreet_gali_road()+" "+Bp_No_array.getLandmark()+" "+Bp_No_array.getCity_region());
       holder.mob_text.setText(Bp_No_array.getMobile_number());


        holder.liner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataJson=new Gson().toJson(bp_no_list_array.get(position));
                Intent intent;

                intent=new Intent(context, isForResubmition? DocumentResumissionDetail.class : Bp_Created_Detail.class);
                intent.putExtra("data",dataJson);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text,user_name_text,address_text,date_text,status_text,mob_text;
        public LinearLayout liner_layout;
        public CardView linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            status_text= (TextView) itemView.findViewById(R.id.status_text);
            mob_text =itemView.findViewById(R.id.mobile_text);
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
                    bp_no_list_array = New_bp_no_list_array;
                } else {
                    List<Bp_No_Item> filteredList = new ArrayList<>();
                    for (Bp_No_Item row : New_bp_no_list_array) {
                        if (row.getBp_number().toLowerCase().contains(charString.toLowerCase()) || row.getFirst_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    bp_no_list_array = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = bp_no_list_array;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                bp_no_list_array = (ArrayList<Bp_No_Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Bp_No_Item contact);
    }
}