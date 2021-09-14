package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Activity.Bp_Created_Detail;
import com.fieldmobility.igl.Activity.DocumentResumissionDetail;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

public class CheckBpAdapter extends RecyclerView.Adapter<CheckBpAdapter.ViewHolder> {
    Context context;


    public CheckBpAdapter(Context context) {
        this.context = context;
    }


    @Override
    public CheckBpAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_check_bp, parent, false));


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            JSONObject data = dataArray.getJSONObject(position);

            holder.date_text.setText( data.getString("bp_date").split(" ")[0]);
            holder.bp_no_text.setText(data.getString("bp_number"));
            holder.user_name_text.setText(data.getString("first_name")+" "+data.getString("last_name"));
            holder.address_text.setText(data.getString("house_no") + " " + data.getString("floor") + " " + data.getString("house_type") + " " + data.getString("society") + " \n"
                    + data.getString("block_qtr_tower_wing") + " " + data.getString("street_gali_road") + " " + data.getString("landmark") + " " + data.getString("city_region"));
            holder.mob_text.setText(data.getString("mobile_number"));

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return dataArray.length();
    }

    public void clearData() {
        dataArray= new JSONArray();
        notifyDataSetChanged();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text, user_name_text, address_text, date_text, mob_text;
        public LinearLayout liner_layout;
        public CardView linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            mob_text = itemView.findViewById(R.id.mobile_text);
            liner_layout = (LinearLayout) itemView.findViewById(R.id.liner_layout);
        }
    }

    public JSONArray dataArray = new JSONArray();

    public void setData(JSONArray jsonArray) {
        dataArray = jsonArray;
        notifyDataSetChanged();
    }


}