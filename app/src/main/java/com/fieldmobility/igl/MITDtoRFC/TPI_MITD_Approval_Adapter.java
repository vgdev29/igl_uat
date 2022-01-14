package com.fieldmobility.igl.MITDtoRFC;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.BpDetail;
import com.fieldmobility.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TPI_MITD_Approval_Adapter extends RecyclerView.Adapter<TPI_MITD_Approval_Adapter.ViewHolder> implements Filterable {
    Context context;
    private ArrayList<BpDetail> bp_no_list_array;
    private ArrayList<BpDetail> New_bp_no_list_array;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    String Claim_Flage, JobFlage, BP_N0;
    String log = "rfcpendingadapter";


    public TPI_MITD_Approval_Adapter(Context context, ArrayList<BpDetail> New_bp_no_list_array) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.context = context;

    }

    @Override
    public TPI_MITD_Approval_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rfc_pending_layout, parent, false);
        sharedPrefs = new SharedPrefs(context);
        TPI_MITD_Approval_Adapter.ViewHolder viewHolder = new TPI_MITD_Approval_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TPI_MITD_Approval_Adapter.ViewHolder holder, int position) {
        final BpDetail Bp_No_array = bp_no_list_array.get(position);

        if (Bp_No_array.getIgl_rfcvendor_assigndate().equalsIgnoreCase("null")||Bp_No_array.getIgl_rfcvendor_assigndate().equals(null))
        {
            holder.date_text.setText("NA");
        }
        else{
            holder.date_text.setText("Assign Date- "+Bp_No_array.getIgl_rfcvendor_assigndate());
        }
        holder.unclaimed_button.setVisibility(View.GONE);
        holder.claimed_button.setVisibility(View.GONE);
        holder.job_start_button.setVisibility(View.GONE);

        holder.bp_no_text.setText(Bp_No_array.getBpNumber());
        holder.user_name_text.setText(Bp_No_array.getFirstName());
        holder.address_text.setText(Bp_No_array.getHouseNo()+" "+Bp_No_array.getFloor()+" "+Bp_No_array.getHouseType()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getSociety()+" \n"
                +Bp_No_array.getBlockQtrTowerWing()+" "+Bp_No_array.getStreetGaliRoad()+" "+Bp_No_array.getLandmark()+" "+Bp_No_array.getCityRegion()
        +"\nControl room - "+Bp_No_array.getControlRoom());
        holder.zone_text.setText(Bp_No_array.getZoneCode());
        holder.mobile_text.setText(Bp_No_array.getMobileNumber());
        //logic for cliam and unclaimed button
        //Log.d(log,"claimflag"+" " + Bp_No_array.getClaimFlag()+"  "+Bp_No_array.getBpNumber());
        //logic for status
        if (Bp_No_array.getIglStatus().equals("131")) {
            holder.status_text.setVisibility(View.VISIBLE);
            holder.status_text.setText("Testing & Commissioning Pending");

        } else
            {
            holder.status_text.setVisibility(View.GONE);
        }



        if (!TextUtils.isEmpty(Bp_No_array.getAmount())){
            if (Bp_No_array.getAmount().equalsIgnoreCase("2")){
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#90EE90"));
                holder.tv_priority.setTextColor(Color.BLACK);
                holder.tv_priority.setText("Intrested Customer");

            }else if (Bp_No_array.getAmount().equalsIgnoreCase("0")){
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.tv_priority.setTextColor(Color.BLACK);
                holder.tv_priority.setText("Normal Customer");
            }
        }



        holder.rfc_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContDetails(bp_no_list_array.get(position).getBpNumber(),bp_no_list_array.get(position).getTc_status());
            }
        });



        holder.mobile_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" +Bp_No_array.getMobileNumber() ));
                    context.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.refresh_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatefc( Bp_No_array,position);
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MITD_Approval.class);
                intent.putExtra("custname",Bp_No_array.getFirstName());
                intent.putExtra("mobile",Bp_No_array.getMobileNumber());
                intent.putExtra("leadno",Bp_No_array.getLeadNo());
                intent.putExtra("Bp_number",Bp_No_array.getBpNumber());
                intent.putExtra("codeGroup",Bp_No_array.getIglCodeGroup());

                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public void setData(ArrayList<BpDetail> filterList)
    {

        this.bp_no_list_array = filterList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text, user_name_text, address_text, date_text, status_text, zone_text,mobile_text,tv_priority;

        Button claimed_button, unclaimed_button, job_start_button, rfc_info;
        ImageButton refresh_data;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            status_text = (TextView) itemView.findViewById(R.id.status_text);
            status_text.setTextSize(14);
            claimed_button = (Button) itemView.findViewById(R.id.claimed_button);
            unclaimed_button = (Button) itemView.findViewById(R.id.unclaimed_button);
            job_start_button = (Button) itemView.findViewById(R.id.job_start_button);
            zone_text = itemView.findViewById(R.id.zone_text);
            rfc_info = itemView.findViewById(R.id.rfcinfo_text);
            mobile_text = itemView.findViewById(R.id.mobile_text);
            refresh_data = itemView.findViewById(R.id.refresh_data);
            tv_priority = itemView.findViewById(R.id.tv_priority);
            relativeLayout = itemView.findViewById(R.id.rl_rfclist);
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
                    ArrayList<BpDetail> filteredList = new ArrayList<>();
                    for (BpDetail row : New_bp_no_list_array) {
                        if (row.getBpNumber().toLowerCase().contains(charString.toLowerCase()) || row.getFirstName().toLowerCase().contains(charString.toLowerCase())
                                ||row.getHouseNo().toLowerCase().contains(charString.toLowerCase())||row.getFloor().toLowerCase().contains(charString.toLowerCase())
                                ||row.getArea().toLowerCase().contains(charString.toLowerCase())||row.getSociety().toLowerCase().contains(charString.toLowerCase())
                                ||row.getBlockQtrTowerWing().toLowerCase().contains(charString.toLowerCase())) {
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
                bp_no_list_array = (ArrayList<BpDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Bp_No_Item contact);
    }

    private void updatefc(BpDetail Bp_No_array, int position)
    {
        CommonUtils.startProgressBar(context,"Loading...");
        Log.d(log, "updateNg = " +Constants.REFRESH_RFC +Bp_No_array.getBpNumber());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.REFRESH_RFC +Bp_No_array.getBpNumber(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CommonUtils.dismissProgressBar(context);
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.d(log,"response"+ response);
                    String status = jsonObject1.getString("status");
                    if (status.equals("200")) {
                        CommonUtils.toast_msg(context,jsonObject1.getString("message"));
                        JSONObject jsonObject= jsonObject1.getJSONObject("details");
                        Log.d(log,"details"+ jsonObject.toString());
                        String name = jsonObject.getString("name");
                        String mob = jsonObject.getString("mob");
                        String society = jsonObject.getString("society");
                        String hno = jsonObject.getString("hno");
                        String block = jsonObject.getString("block");
                        String floor = jsonObject.getString("floor");
                        Bp_No_array.setFirstName(name);
                        Bp_No_array.setMobileNumber(mob);
                        Bp_No_array.setSociety(society);
                        Bp_No_array.setHouseNo(hno);
                        Bp_No_array.setBlockQtrTowerWing(block);
                        Bp_No_array.setFloor(floor);
                        bp_no_list_array.set(position,Bp_No_array);
                        notifyDataSetChanged();


                    }
                    else
                    {
                        CommonUtils.toast_msg( context,jsonObject1.getString("message"));
                    }
                } catch (JSONException e) {
                    CommonUtils.dismissProgressBar(context);
                    CommonUtils.toast_msg(context,"Oops..Error loading status!!");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtils.dismissProgressBar(context);
                CommonUtils.toast_msg(context,"Oops..TimeOut!!");
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void getContDetails(String bp, int tc_status)
    {
        CommonUtils.startProgressBar(context,"Loading...");
        Log.d(log, "updateNg = " +Constants.TPI_CONT_DETAIL);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TPI_CONT_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(log,"response"+ response);
                CommonUtils.dismissProgressBar(context);
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.d(log,"response"+ response);
                    String status = jsonObject1.getString("status");
                    if (status.equals("200")) {
                         String contName = jsonObject1.getString("contName");
                        String contMob = jsonObject1.getString("contMob");
                        String supName = jsonObject1.getString("supName");
                        String supMob = jsonObject1.getString("supMob");

                        rfcInfoDialog(contName,contMob,supName,supMob);
                    }
                    else
                    {
                        CommonUtils.toast_msg( context,jsonObject1.getString("message"));
                    }
                } catch (JSONException e) {
                    CommonUtils.dismissProgressBar(context);
                    CommonUtils.toast_msg(context,"Oops..Error loading status!!");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtils.dismissProgressBar(context);
                CommonUtils.toast_msg(context,"Oops..TimeOut!!");
                error.printStackTrace();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("bp", bp);
                    params.put("tc_status", String.valueOf(tc_status));
                } catch (Exception e) {
                }
                return params;
            }

        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }




    private void rfcInfoDialog(String contractor_Name, final String contractor_No, String supervisor, final String supMob) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rfc_info_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setTitle("Signature");
        TextView cont_name = dialog.findViewById(R.id.cont_name_text);
        TextView cont_mob = dialog.findViewById(R.id.cont_mob_no);
        TextView sup_name = dialog.findViewById(R.id.sup_name_text);
        TextView sup_mob = dialog.findViewById(R.id.sup_mob_no);
        cont_name.setText("Cont. :- " + contractor_Name);
        cont_mob.setText("MobNo :- " + contractor_No);
        sup_name.setText("Sup. :- " + supervisor);
        sup_mob.setText("MobNo :- " + supMob);
        sup_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + supMob));
                    context.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        cont_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + contractor_No));
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