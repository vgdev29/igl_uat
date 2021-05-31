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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import java.util.ArrayList;
import java.util.List;


public class Bp_no_Adapter extends RecyclerView.Adapter<Bp_no_Adapter.ViewHolder> implements Filterable{
    Context context;
    // RecyclerView recyclerView;
    private List<Bp_No_Item> bp_no_list_array;
    private List<Bp_No_Item> New_bp_no_list_array;
    private ContactsAdapterListener listener;
    public Bp_no_Adapter(Context context,List<Bp_No_Item> New_bp_no_list_array,ContactsAdapterListener listener) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.listener = listener;
        this.context = context;
    }
    @Override
    public Bp_no_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.bp_no_listing_item, parent, false);
        Bp_no_Adapter.ViewHolder viewHolder = new Bp_no_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Bp_no_Adapter.ViewHolder holder, int position) {
        final Bp_No_Item Bp_No_array = bp_no_list_array.get(position);
        holder.date_text.setText(Bp_No_array.getBp_date());
        holder.bp_no_text.setText(Bp_No_array.getBp_number());
        holder.user_name_text.setText(Bp_No_array.getFirst_name());
        holder.address_text.setText(Bp_No_array.getHouse_no()+" "+Bp_No_array.getSociety()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getCity_region());


        if(Bp_No_array.getIgl_status().equals("null")){
            holder.status_text.setVisibility(View.GONE);
        }else {
            if(Bp_No_array.getIgl_status().equals("0")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("Un Assigned");
            }
            if(Bp_No_array.getIgl_status().equals("1")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("Assigned");
            }
            if(Bp_No_array.getIgl_status().equals("2")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("Verification Pending");
            }
        }


    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text,user_name_text,address_text,date_text,status_text;
        public LinearLayout relativeLayout;
        public CardView linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            status_text= (TextView) itemView.findViewById(R.id.status_text);
            //linearLayout = (CardView)itemView.findViewById(R.id.linearLayout);
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