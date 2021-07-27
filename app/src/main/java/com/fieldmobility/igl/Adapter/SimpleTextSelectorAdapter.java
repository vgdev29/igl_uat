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
import com.fieldmobility.igl.Listeners.AddressSelectListener;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SimpleTextSelectorAdapter extends RecyclerView.Adapter<SimpleTextSelectorAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<String> dataList = new ArrayList<>();
    ArrayList<String> tempdataList = new ArrayList<>();
    public static final int CITY_SELECTION = 10;
    public static final int AREA_SELECTION = 11;
    public static final int SOCIETY_SELECTION = 12;
    AddressSelectListener listener;

    public int getIsFor() {
        return isFor;
    }

    public void setIsFor(int isFor) {
        this.isFor = isFor;
    }

    public int isFor = -1;

    public void setOnItemClickListeneer(AddressSelectListener listener) {
        this.listener = listener;
    }

    public SimpleTextSelectorAdapter(Context context) {

        this.context = context;
    }

    public void setData(ArrayList<String> dataList) {
        this.dataList.clear();
        tempdataList.clear();
        this.dataList.addAll(dataList);
        tempdataList.addAll(dataList);
        notifyDataSetChanged();
    }


    @Override
    public SimpleTextSelectorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bottomsheet_item_simple_text, parent, false));


    }

    @Override
    public void onBindViewHolder(SimpleTextSelectorAdapter.ViewHolder holder, final int position) {
        String item = dataList.get(position);
        holder.text.setText(item);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (getIsFor()) {
                        case CITY_SELECTION:
                            listener.onCitySelect(dataList.get(getAdapterPosition()),getAdapterPosition());
                            break;

                        case AREA_SELECTION:
                            listener.onAreaSelect(dataList.get(getAdapterPosition()));
                            break;
                        case SOCIETY_SELECTION:
                            listener.onSocietySelect(dataList.get(getAdapterPosition()),getAdapterPosition());
                            break;
                    }
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
                    dataList = tempdataList;
                } else {
                    ArrayList<String> filteredList = new ArrayList<>();
                    for (String data : tempdataList) {
                        if (data.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(data);
                        }
                    }
                    dataList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataList = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}