package com.example.igl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;
import java.util.ArrayList;
import java.util.List;

public class DropDown_Adapter extends RecyclerView.Adapter<DropDown_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private List<Bp_No_Item> bp_no_list_array;
    private List<Bp_No_Item> New_bp_no_list_array;
    private DropDown_Adapter.ContactsAdapterListener listener;
    public DropDown_Adapter(Context context, List<Bp_No_Item> New_bp_no_list_array, DropDown_Adapter.ContactsAdapterListener listener) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.listener = listener;
        this.context = context;
    }
    @Override
    public DropDown_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.dropdown_text, parent, false);
        DropDown_Adapter.ViewHolder viewHolder = new DropDown_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DropDown_Adapter.ViewHolder holder, int position) {
        final Bp_No_Item Bp_No_array = bp_no_list_array.get(position);
        holder.drawnon_text.setText(Bp_No_array.getMeterNo());

    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView drawnon_text;
        public ViewHolder(View itemView) {
            super(itemView);
            drawnon_text = (TextView) itemView.findViewById(R.id.drawnon_text);
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
                        if (row.getMeterNo().toLowerCase().contains(charString.toLowerCase())) {
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