package com.fieldmobility.igl.Riser.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RiserTpiPendingListAdapter extends RecyclerView.Adapter<RiserTpiPendingListAdapter.MyHolder> implements Filterable {
    Context mContext;
    List<RiserListingModel.BpDetails.User> datalist;
    List<RiserListingModel.BpDetails.User> tempDatalist;
    SharedPrefs sharedPrefs;
    public RiserTpiPendingListAdapter(Context context, List<RiserListingModel.BpDetails.User> datalist){
        mContext=context;
        this.datalist=datalist;
        this.tempDatalist=datalist;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPrefs = new SharedPrefs(mContext);
        return new RiserTpiPendingListAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tpi_item_riser_pendinglist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        RiserListingModel.BpDetails.User model=datalist.get(position);
        String fullName=model.getFirstName() +" "+model.getLastName();
        holder.tv_name.setText(fullName);
        holder.tv_bp_num.setText(model.getBpNumber());
        holder.tv_mob.setText(model.getMobileNumber());
        holder.tv_assign_date.setText(model.getRiserAssign());
        holder.tv_address.setText(model.getHouseNo()+" "+model.getFloor()+" "+model.getBlockQtrTowerWing()+" "+model.getSociety()+" "+model.getStreetGaliRoad()+", "+model.getArea()+" "+model.getCityRegion()+",\n"+model.getPincode());

        if ( model.getRiserClaim().equals("1"))
        {
            if (model.getRiserTpi().equals(sharedPrefs.getUUID()))
            {
                holder.claim.setText("Unclaim");
                holder.claim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        claim(null, "0", model, position);
                    }
                });
            }
            else {
                holder.claim.setText("Claimed by Other Tpi");
                holder.claim.setEnabled(false);
            }
        }
        else
        {
            holder.claim.setText("Claim");
            holder.claim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    claim(sharedPrefs.getUUID() , "1",model ,position);
                }
            });
        }

    }

    private void claim(String tpi, String s , RiserListingModel.BpDetails.User model , int position) {
      MaterialDialog  materialDialog = new MaterialDialog.Builder(mContext)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Log.d("riser", Constants.RISER_TPIPendingClaim+"/"+model.getBpNumber()+"/"+sharedPrefs.getUUID()+"/"+s);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.RISER_TPIPendingClaim+model.getBpNumber()+"/"+tpi+"/"+s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("200")) {
                        CommonUtils.toast_msg(mContext,"Claimed");
                        model.setRiserClaim(s);
                        model.setRiserTpi(sharedPrefs.getUUID());
                        datalist.set(position,model);
                        notifyDataSetChanged();
                    }
                    else
                    {
                        CommonUtils.toast_msg(mContext,jsonObject.getString("Message"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_mob, tv_assign_date, tv_address,tv_bp_num;
        Button claim;


        public MyHolder(View itemView) {
            super(itemView);
            tv_bp_num =  itemView.findViewById(R.id.tv_bp_num);
            tv_mob =  itemView.findViewById(R.id.tv_mob);
            tv_name =  itemView.findViewById(R.id.tv_name);
            tv_address =  itemView.findViewById(R.id.tv_address);
            tv_assign_date =  itemView.findViewById(R.id.tv_assign_date);
            claim = itemView.findViewById(R.id.tpiclaim);


        }
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
                    List<RiserListingModel.BpDetails.User> filteredList = new ArrayList<>();
                    for (RiserListingModel.BpDetails.User row : tempDatalist) {
                        String name=row.getFirstName()+" "+row.getLastName();
                        if (row.getBpNumber().toLowerCase().contains(charString.toLowerCase()) || name.toLowerCase().contains(charString.toLowerCase())) {
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
                datalist = (ArrayList<RiserListingModel.BpDetails.User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
