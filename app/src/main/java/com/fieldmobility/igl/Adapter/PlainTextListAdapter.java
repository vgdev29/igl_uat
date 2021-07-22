package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Listeners.PlainTextListItemSelectListener;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;

import java.util.ArrayList;
import java.util.List;

public class PlainTextListAdapter extends RecyclerView.Adapter<PlainTextListAdapter.MyHolder> implements Filterable {

    Context context;
    PlainTextListItemSelectListener selectListener;
    public PlainTextListAdapter(Context context, PlainTextListItemSelectListener selectListener) {
        this.context = context;
        this.selectListener=selectListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_simple_text_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.drawnon_text.setText(datalist.get(position));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    ArrayList<String> datalist = new ArrayList<>();
    ArrayList<String> tempDatalist = new ArrayList<>();

    public void setData(ArrayList<String> datalist) {
        this.datalist.clear();
        this.tempDatalist.clear();
        this.datalist.addAll(datalist);
        this.tempDatalist.addAll(datalist);

        notifyDataSetChanged();
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
                    ArrayList<String> filteredList = new ArrayList<>();
                    for (String row : tempDatalist) {
                        if (row.toLowerCase().contains(charString.toLowerCase())) {
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
                datalist = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView drawnon_text;

        public MyHolder(View itemView) {
            super(itemView);
            drawnon_text = itemView.findViewById(R.id.drawnon_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectListener.onPlainTextItemSelect(datalist.get(getAdapterPosition()),getAdapterPosition());
                }
            });
        }
    }

}
