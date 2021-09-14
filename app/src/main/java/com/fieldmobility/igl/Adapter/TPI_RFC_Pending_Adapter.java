package com.fieldmobility.igl.Adapter;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.fieldmobility.igl.Activity.NgSupUserDetailsActivity;
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
import java.util.List;
import java.util.Map;


public class TPI_RFC_Pending_Adapter extends RecyclerView.Adapter<TPI_RFC_Pending_Adapter.ViewHolder> implements Filterable {
    Context context;
    private ArrayList<BpDetail> bp_no_list_array;
    private ArrayList<BpDetail> New_bp_no_list_array;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    String Claim_Flage, JobFlage, BP_N0;
    String log = "rfcpendingadapter";
    Fragment fragment;
    String claimFlag ="";

    public TPI_RFC_Pending_Adapter(Context context, ArrayList<BpDetail> New_bp_no_list_array, Fragment fragment) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public TPI_RFC_Pending_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rfc_pending_layout, parent, false);
        sharedPrefs = new SharedPrefs(context);
        TPI_RFC_Pending_Adapter.ViewHolder viewHolder = new TPI_RFC_Pending_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TPI_RFC_Pending_Adapter.ViewHolder holder,  int position) {
        final BpDetail Bp_No_array = bp_no_list_array.get(position);

        if (Bp_No_array.getIgl_rfcvendor_assigndate().equalsIgnoreCase("null")||Bp_No_array.getIgl_rfcvendor_assigndate().equals(null))
        {
            holder.date_text.setText("NA");
        }
        else{
            holder.date_text.setText("Assign Date- "+Bp_No_array.getIgl_rfcvendor_assigndate());
        }
        holder.bp_no_text.setText(Bp_No_array.getBpNumber());
        holder.user_name_text.setText(Bp_No_array.getFirstName());
        holder.address_text.setText(Bp_No_array.getHouseNo()+" "+Bp_No_array.getFloor()+" "+Bp_No_array.getHouseType()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getSociety()+" \n"
                +Bp_No_array.getBlockQtrTowerWing()+" "+Bp_No_array.getStreetGaliRoad()+" "+Bp_No_array.getLandmark()+" "+Bp_No_array.getCityRegion()
        +"\nControl room - "+Bp_No_array.getControlRoom());
        holder.zone_text.setText(Bp_No_array.getZoneCode());
        holder.mobile_text.setText(Bp_No_array.getMobileNumber());
        //logic for cliam and unclaimed button
        Log.d(log,"claimflag"+" " + Bp_No_array.getClaimFlag()+"  "+Bp_No_array.getBpNumber());
        //logic for status
        if (Bp_No_array.getIglStatus().equals("null")) {
            holder.status_text.setVisibility(View.GONE);
        } else {
            holder.status_text.setVisibility(View.VISIBLE);
            holder.status_text.setText("RFC Pending");
        }

        if (Bp_No_array.getClaimFlag().equals("null") ||Bp_No_array.getClaimFlag()==null )
        {
            claimFlag = "";
            Log.d(log,"claimflag"+" if" + Bp_No_array.getClaimFlag()+"  "+Bp_No_array.getBpNumber());
        }
        else {
            claimFlag =Bp_No_array.getClaimFlag();
            Log.d(log,"claimflag"+"else" + Bp_No_array.getClaimFlag()+"  "+Bp_No_array.getBpNumber());
        }
        if (claimFlag.equalsIgnoreCase("") || claimFlag.equalsIgnoreCase("1")) {
            holder.unclaimed_button.setVisibility(View.GONE);
            holder.claimed_button.setVisibility(View.VISIBLE);
            holder.job_start_button.setVisibility(View.GONE);
            Log.d(log, "if RfcTpi = " + Bp_No_array.getClaimFlag()+"  "+Bp_No_array.getBpNumber());
        } else if (claimFlag.equalsIgnoreCase("0")) {
            if ((Bp_No_array.getRfcTpi()!=null||!Bp_No_array.getRfcTpi().equalsIgnoreCase("null")) && Bp_No_array.getRfcTpi().equals(sharedPrefs.getUUID())) {
                holder.claimed_button.setVisibility(View.GONE);
                holder.unclaimed_button.setVisibility(View.VISIBLE);
                Log.d(log, "else RfcTpi = " + Bp_No_array.getClaimFlag() + "  " + Bp_No_array.getBpNumber());
                //LOgic for job start button
                if (Bp_No_array.getJobFlag().equals("1")) {
                    holder.job_start_button.setVisibility(View.GONE);

                } else if (Bp_No_array.getJobFlag().equals("0")) {
                    holder.job_start_button.setVisibility(View.VISIBLE);
                }
                Log.d(log, "else RfcTpi = " + Bp_No_array.getClaimFlag() + "  " + Bp_No_array.getBpNumber());
                Log.d(log, "login id = " + sharedPrefs.getUUID() + "  " + Bp_No_array.getBpNumber());
            }
            else {
                holder.claimed_button.setVisibility(View.GONE);
                holder.unclaimed_button.setVisibility(View.GONE);
                holder.job_start_button.setVisibility(View.GONE);
                holder.status_text.setText("RFC Claimed by other TPI");
            }
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
                rfcInfoDialog(Bp_No_array.getRfcAdminName(),Bp_No_array.getRfcAdminMobileNo(),Bp_No_array.getRfcVendorName(),Bp_No_array.getRfcVendorMobileNo());

            }
        });

        holder.claimed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Claim_Flage = "0";
                BP_N0 = bp_no_list_array.get(position).getBpNumber();
               /* holder.unclaimed_button.setVisibility(View.VISIBLE);
                holder.job_start_button.setVisibility(View.VISIBLE);
                holder.claimed_button.setVisibility(View.GONE);*/
                Claimed_API_POST(Bp_No_array,position);
            }
        });
        holder.unclaimed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Claim_Flage = "1";
                BP_N0 = bp_no_list_array.get(position).getBpNumber();
               /* holder.unclaimed_button.setVisibility(View.GONE);
                holder.job_start_button.setVisibility(View.GONE);
                holder.claimed_button.setVisibility(View.VISIBLE);*/
                Unclaimed_API_POST(Bp_No_array,position);
            }
        });

        holder.job_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BP_N0 = bp_no_list_array.get(position).getBpNumber();
               // holder.job_start_button.setVisibility(View.GONE);
                Job_Start_API_POST(Bp_No_array, position);

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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.REFRESH_RFC +Bp_No_array.getBpNumber(), new com.android.volley.Response.Listener<String>() {
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
        }, new com.android.volley.Response.ErrorListener() {
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

    public void Claimed_API_POST(BpDetail Bp_No_array , int position) {
        materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.ClaimUnclaim + BP_N0 + "?flag=" + Claim_Flage + "&id=" + sharedPrefs.getUUID();
        Log.d(log, "url = " + url);
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.ClaimUnclaim + BP_N0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());
                            if (json.getString("Sucess").equals("true")) {

                                Toast.makeText(context, "" + "Successfully Updated", Toast.LENGTH_SHORT).show();
                                Bp_No_array.setClaimFlag(Claim_Flage);
                                if (Claim_Flage.equals("1")) {
                                    Bp_No_array.setRfcTpi(null);
                                    Bp_No_array.setJobFlag(null);
                                }
                                else
                                {
                                    Bp_No_array.setRfcTpi(sharedPrefs.getUUID());
                                    Bp_No_array.setJobFlag("0");
                                }
                                bp_no_list_array.set(position,Bp_No_array);
                                notifyDataSetChanged();
                                // fragmentReload();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object", obj.toString());
                        JSONObject error1 = obj.getJSONObject("error");
                        String error_msg = error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("flag", Claim_Flage);
                    params.put("id", sharedPrefs.getUUID());
                } catch (Exception e) {
                }
                return params;
            }
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }

    public void Unclaimed_API_POST(BpDetail Bp_No_array , int position) {

        materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.ClaimUnclaim + BP_N0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());
                            if (json.getString("Sucess").equals("true")) {
                                Toast.makeText(context, "" + "Successfully Updated", Toast.LENGTH_SHORT).show();
                                Bp_No_array.setClaimFlag(Claim_Flage);
                                if (Claim_Flage.equals("1")) {
                                    Bp_No_array.setRfcTpi(null);
                                    Bp_No_array.setJobFlag(null);
                                }
                                else
                                {
                                    Bp_No_array.setRfcTpi(sharedPrefs.getUUID());
                                    Bp_No_array.setJobFlag("0");
                                }
                                bp_no_list_array.set(position,Bp_No_array);
                                notifyDataSetChanged();
                                // fragmentReload();
                                // ((Ready_Inspection_Tpi_Fragment) context).Bp_No_List();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object", obj.toString());
                        JSONObject error1 = obj.getJSONObject("error");
                        String error_msg = error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("flag", Claim_Flage);
                    params.put("id", sharedPrefs.getUUID());
                } catch (Exception e) {
                }
                return params;
            }
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }


    public void Job_Start_API_POST(BpDetail Bp_No_array , int position) {
        materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.JobStart + BP_N0;
        Log.d(log, "job start url = " + url);
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.JobStart + BP_N0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());
                            if (json.getString("Sucess").equals("true")) {
                                Toast.makeText(context, "" + "Successfully Updated", Toast.LENGTH_SHORT).show();
                                Bp_No_array.setJobFlag("1");

                                bp_no_list_array.set(position,Bp_No_array);
                                notifyDataSetChanged();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object", obj.toString());
                        JSONObject error1 = obj.getJSONObject("error");
                        String error_msg = error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }) {
           /* @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("flag", Claim_Flage);
                    params.put("id", sharedPrefs.getUUID());
                } catch (Exception e) {
                }
                return params;
            }*/
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }

    public void fragmentReload() {
        //Fragment currentFragment = fragment.getFragmentManager().findFragmentByTag("YourFragmentTag");
        FragmentTransaction fragmentTransaction = fragment.getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
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