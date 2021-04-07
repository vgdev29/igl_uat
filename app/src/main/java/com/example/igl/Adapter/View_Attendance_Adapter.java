package com.example.igl.Adapter;

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

import com.example.igl.Activity.View_Attendance_Activity;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;

import java.util.ArrayList;
import java.util.List;

public class View_Attendance_Adapter extends RecyclerView.Adapter<View_Attendance_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private List<View_Leave_Item> bp_no_list_array;
    private List<View_Leave_Item> New_bp_no_list_array;
    private View_Attendance_Adapter.ContactsAdapterListener listener;
    public View_Attendance_Adapter(Context context, List<View_Leave_Item> New_bp_no_list_array, View_Attendance_Adapter.ContactsAdapterListener listener) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.listener = listener;
        this.context = context;
    }



    @Override
    public View_Attendance_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.view_attendance_item, parent, false);
        View_Attendance_Adapter.ViewHolder viewHolder = new View_Attendance_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(View_Attendance_Adapter.ViewHolder holder, int position) {
        final View_Leave_Item Bp_No_array = bp_no_list_array.get(position);
        holder.from_date_text.setText(Bp_No_array.getLeave_from_date());
        holder.from_time_text.setText(Bp_No_array.getStart_time());
        holder.to_time_text.setText(Bp_No_array.getEnd_time());


    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView status_text,from_date_text,to_date_text,from_time_text,to_time_text,descreption_text;
        public LinearLayout relativeLayout;
        public CardView linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            status_text = (TextView) itemView.findViewById(R.id.status_text);
            from_date_text = (TextView) itemView.findViewById(R.id.from_date_text);
            to_date_text = (TextView) itemView.findViewById(R.id.to_date_text);
            from_time_text = (TextView) itemView.findViewById(R.id.from_time_text);
            to_time_text = (TextView) itemView.findViewById(R.id.to_time_text);
            descreption_text = (TextView) itemView.findViewById(R.id.descreption_text);
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
                    List<View_Leave_Item> filteredList = new ArrayList<>();
                    for (View_Leave_Item row : New_bp_no_list_array) {
                        if (row.getDescription().toLowerCase().contains(charString.toLowerCase())) {
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
                bp_no_list_array = (ArrayList<View_Leave_Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Bp_No_Item contact);
    }
}