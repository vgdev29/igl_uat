package com.fieldmobility.igl.Mdpe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MdpeTPI_Adapter extends RecyclerView.Adapter<MdpeTPI_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    public List<MdpeSubAllocation> mdpesublist;
    public List<MdpeSubAllocation> new_mdpesublist;
    String Vender_Name,Vender_No;
    SharedPrefs sharedPrefs;
    static String log = "mdpeadapter";
    public MdpeTPI_Adapter(Context context, List<MdpeSubAllocation> mdpelist) {
        this.mdpesublist = mdpelist;
        this.new_mdpesublist = mdpelist;
        this.context = context;
    }

    @Override
    public MdpeTPI_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mdpetpi_adapter, parent, false);
        sharedPrefs=new SharedPrefs(context);
        MdpeTPI_Adapter.ViewHolder viewHolder = new MdpeTPI_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MdpeTPI_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MdpeSubAllocation mdpesuball = mdpesublist.get(position);
        holder.tv_allo.setText(mdpesuball.getAllocationNumber());
        holder.tv_suballo.setText(mdpesuball.getSuballocationNumber());
        holder.tv_wbs.setText(mdpesuball.getWbsNumber());
        holder.tv_address.setText(mdpesuball.getCity()+" "+mdpesuball.getArea()+" "+mdpesuball.getSociety()+" "+mdpesuball.getZone());
        holder.tv_assign_date.setText(mdpesuball.getAgentAssignDate());
        holder.tv_assignment.setText(
        mdpesuball.getMethod()+" "+mdpesuball.getAreaType()+" "+mdpesuball.getSize()+" "+mdpesuball.getLength()+" "+mdpesuball.getTrenchlessMethod());

        try {
            holder.tv_tpi.setText(mdpesuball.getUserName()+"\n"+mdpesuball.getUserMob());
            holder.tv_tpi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+ mdpesuball.getTpiClaim()));
                        context.startActivity(callIntent);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){}

        if ( mdpesuball.getTpiClaim()==1)
        {
            if (mdpesuball.getTpiId().equals(sharedPrefs.getUUID()))
            {
                holder.tpiclaim.setText("Unclaim");
                holder.tpiclaim.setTag("0");

            }
            else {
                holder.tpiclaim.setText("Claimed by other TPI");
                holder.tpiclaim.setTag("2");
            }
        }
        else
        {
            holder.tpiclaim.setText("Claim");
            holder.tpiclaim.setTag("1");
        }

        holder.tpiclaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String timedate = dateFormat.format(date);
                if (holder.tpiclaim.getTag().equals("1")) {
                    mdpesuball.setTpiClaim(1);
                    mdpesuball.setTpiId(sharedPrefs.getUUID());
                    mdpesuball.setClaimDate(timedate);
                    Log.d(log,"Agent id = "+mdpesuball.getAgentId());
                    claim(mdpesuball, position);
                }
                else  if (holder.tpiclaim.getTag().equals("0")) {
                    mdpesuball.setTpiClaim(0);
                    mdpesuball.setTpiId(null);
                    claim(mdpesuball, position);
                }
            }
        });



    }

    private void claim(MdpeSubAllocation subAllocation , int position) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String jsonInString = new Gson().toJson(subAllocation);
        Log.d(log,"jsoninstring suballo = "+ jsonInString);
        JSONObject mJSONObject = null;
        try {
            mJSONObject = new JSONObject(jsonInString);
            Log.d(log,"jsoninstring suballo obj= "+ mJSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Constants.MDPETPIPENDING_CLAIM,mJSONObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                materialDialog.dismiss();
                try {
                    Log.d(log,response.toString());
                    String status = response.getString("status");
                    if (status.equals("200")) {
                        JSONObject data_object = response.getJSONObject("data");
                        CommonUtils.toast_msg(context,"Claimed");
                        subAllocation.setTpiClaim(data_object.getInt("tpiClaim"));
                        subAllocation.setClaimDate(data_object.getString("claimDate"));
                        subAllocation.setTpiId(data_object.getString("tpiId"));
                        mdpesublist.set(position,subAllocation);
                        notifyDataSetChanged();
                    }
                    else
                    {
                        CommonUtils.toast_msg(context,response.getString("message"));
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
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public int getItemCount() {
        return mdpesublist.size();
    }


    public void setData(List<MdpeSubAllocation> filterList)
    {
        Log.d("mdpelist","setData = "+filterList.size());
       this.mdpesublist = filterList;
       notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_allo, tv_assignment, tv_assign_date, tv_address,tv_suballo,tv_tpi,tv_wbs;
        LinearLayout mdpe_card;
        Button tpiclaim;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_allo =  itemView.findViewById(R.id.tv_allo_num);
            tv_suballo =  itemView.findViewById(R.id.tv_suballo);
            tv_wbs =  itemView.findViewById(R.id.tv_wbs);
            tv_address =  itemView.findViewById(R.id.tv_address);
            tv_assign_date =  itemView.findViewById(R.id.tv_assign_date);
            tv_tpi = itemView.findViewById(R.id.tv_tpi);
            tv_assignment = itemView.findViewById(R.id.tv_assignment);
            mdpe_card = itemView.findViewById(R.id.mdpe_card);
            tpiclaim = itemView.findViewById(R.id.mdpetpiclaim);

        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mdpesublist = new_mdpesublist;
                } else {
                    List<MdpeSubAllocation> filteredList = new ArrayList<>();
                    for (MdpeSubAllocation row : new_mdpesublist) {
                        if (row.getAllocationNumber().toLowerCase().contains(charString.toLowerCase())
                                || row.getSuballocationNumber().toLowerCase().contains(charString.toLowerCase())
                                ||row.getWbsNumber().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row);
                        }
                    }
                    mdpesublist = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mdpesublist;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mdpesublist = (ArrayList<MdpeSubAllocation>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }




    private void BP_N0_DilogBox(String vender_Name, final String vender_No) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bp_no_dilogbox);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setTitle("Signature");
        TextView bp_no_text=dialog.findViewById(R.id.bp_no_text);
        TextView  vendar_no=dialog.findViewById(R.id.vendar_no);
        bp_no_text.setText("TPI Name :- "+vender_Name);
        vendar_no.setText("TPI MobNo :- "+vender_No);
        vendar_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+ vender_No));
                    context.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        Button ok_button = (Button) dialog.findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // finish();
            }
        });


        dialog.show();
    }

}