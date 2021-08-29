package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AutoSuggestAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> listData;
    public AutoSuggestAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        listData = new ArrayList<>();
    }
    public void setData(List<String> list) {
        listData.clear();
        listData.addAll(list);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public String getItem(int position) {
        return listData.get(position);
    }

    public String getObject(int position) {
        return listData.get(position);
    }
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = listData;
                    filterResults.count = listData.size();
                }
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }

}
