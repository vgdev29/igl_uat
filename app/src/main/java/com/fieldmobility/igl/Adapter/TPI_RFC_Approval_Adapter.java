package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Activity.TPI_RfcDone_Approval_Activity;
import com.fieldmobility.igl.Activity.TPI_RfcHold_Approval_Activity;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.BpDetail;
import com.fieldmobility.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TPI_RFC_Approval_Adapter extends RecyclerView.Adapter<TPI_RFC_Approval_Adapter.ViewHolder> implements Filterable {
    Context context;
    private ArrayList<BpDetail> bp_no_list_array;
    private ArrayList<BpDetail> New_bp_no_list_array;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    String Claim_Flage, JobFlage, BP_N0;
    String log = "rfcpendingadapter";
    Fragment fragment;

    public TPI_RFC_Approval_Adapter(Context context, ArrayList<BpDetail> New_bp_no_list_array, Fragment fragment) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public TPI_RFC_Approval_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rfc_approval_layout, parent, false);
        sharedPrefs = new SharedPrefs(context);
        TPI_RFC_Approval_Adapter.ViewHolder viewHolder = new TPI_RFC_Approval_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TPI_RFC_Approval_Adapter.ViewHolder holder, final int position) {
        final BpDetail Bp_No_array = bp_no_list_array.get(position);
        if (Bp_No_array.getIgl_rfcvendor_assigndate().equalsIgnoreCase("null")||Bp_No_array.getIgl_rfcvendor_assigndate().equals(null))
        {
            holder.date_text.setText("NA");
        }
        else{
            holder.date_text.setText(Bp_No_array.getIgl_rfcvendor_assigndate());
        }
        holder.bp_no_text.setText(Bp_No_array.getBpNumber());
        holder.user_name_text.setText(Bp_No_array.getFirstName());
        holder.address_text.setText(Bp_No_array.getHouseNo()+" "+Bp_No_array.getFloor()+" "+Bp_No_array.getHouseType()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getSociety()+" \n"
                +Bp_No_array.getBlockQtrTowerWing()+" "+Bp_No_array.getStreetGaliRoad()+" "+Bp_No_array.getLandmark()+" "+Bp_No_array.getCityRegion()
                +"\nControl room - "+Bp_No_array.getControlRoom());
        holder.zone_text.setText(Bp_No_array.getZoneCode());
        holder.mobile_text.setText(Bp_No_array.getMobileNumber());
        //logic for cliam and unclaimed button
        Log.d("claimflag", "" + Bp_No_array.getClaimFlag());
        final String igl_status = Bp_No_array.getIglStatus();

        //logic for status
        if (igl_status.equals("3")) {
            holder.status_text.setText("RFC Done");

        }
        if (igl_status.equals("111")) {
            holder.status_text.setText("RFC Hold");
        }
         if (igl_status.equals("112")) {
            holder.status_text.setText("Feasibility Failed");
        }
          if (igl_status.equals("113")) {
             holder.status_text.setText("Meter Installed \n Testing Done");
         }

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

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Bp_number = bp_no_list_array.get(position).getBpNumber();
                String lead_no = bp_no_list_array.get(position).getLeadNo();
                String First_name = bp_no_list_array.get(position).getFirstName();
                String Middle_name = bp_no_list_array.get(position).getMiddleName();
                String Last_name = bp_no_list_array.get(position).getLastName();
                String Mobile_number = bp_no_list_array.get(position).getMobileNumber();
                String Email_id = bp_no_list_array.get(position).getEmailId();
                String Aadhaar_number = bp_no_list_array.get(position).getAadhaarNumber();
                String City_region = bp_no_list_array.get(position).getCityRegion();
                String Area = bp_no_list_array.get(position).getArea();
                String Society = bp_no_list_array.get(position).getSociety();
                String Landmark = bp_no_list_array.get(position).getLandmark();
                String House_type = bp_no_list_array.get(position).getHouseType();
                String House_no = bp_no_list_array.get(position).getHouseNo();
                String Block_qtr_tower_wing = bp_no_list_array.get(position).getBlockQtrTowerWing();
                String Floor = bp_no_list_array.get(position).getFloor();
                String Street_gali_road = bp_no_list_array.get(position).getStreetGaliRoad();
                String Pincode = bp_no_list_array.get(position).getPincode();
                String Customer_type = bp_no_list_array.get(position).getCustomerType();
                String Lpg_company = bp_no_list_array.get(position).getLpgCompany();
                String Bp_date = bp_no_list_array.get(position).getBpDate();
                String IGL_Status = bp_no_list_array.get(position).getIglStatus();

                String lpg_distributor = bp_no_list_array.get(position).getLpgDistributor();
                String lpg_conNo = bp_no_list_array.get(position).getLpgConNo();
                String Unique_lpg_Id = bp_no_list_array.get(position).getUniqueLpgId();
                 String ownerName = bp_no_list_array.get(position).getOwnerName();
                String igl_code_group = bp_no_list_array.get(position).getIglCodeGroup();

                if (Bp_No_array.getIglStatus().equalsIgnoreCase("3")||Bp_No_array.getIglStatus().equalsIgnoreCase("113")) {
                    Intent intent = new Intent(context, TPI_RfcDone_Approval_Activity.class);
                    intent.putExtra("Bp_number", Bp_number);
                    intent.putExtra("leadno", lead_no);
                    intent.putExtra("custname",First_name+" "+Last_name);
                    intent.putExtra("mobile",Mobile_number);
                    intent.putExtra("email",Email_id);
                    intent.putExtra("iglstatus",igl_status);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, TPI_RfcHold_Approval_Activity.class);
                    intent.putExtra("Bp_number", Bp_number);
                    intent.putExtra("leadno", lead_no);
                    intent.putExtra("leadno", lead_no);
                    intent.putExtra("custname",First_name+" "+Last_name);
                    intent.putExtra("mobile",Mobile_number);
                    intent.putExtra("email",Email_id);
                    intent.putExtra("iglstatus",igl_status);
                    context.startActivity(intent);
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

    private void updatefc(BpDetail Bp_No_array, int position)
    {
        CommonUtils.startProgressBar(context,"Loading...");
        Log.d(log, "updateNg = " + Constants.REFRESH_RFC +Bp_No_array.getBpNumber());
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

    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text, user_name_text, address_text, date_text, status_text, zone_text,mobile_text;

        Button claimed_button, unclaimed_button, job_start_button, rfc_info;
        RelativeLayout layout;
        ImageButton refresh_data;

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
            layout = itemView.findViewById(R.id.liner_layout);
            refresh_data = itemView.findViewById(R.id.refresh_data);
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






    public void fragmentReload() {
        //Fragment currentFragment = fragment.getFragmentManager().findFragmentByTag("YourFragmentTag");
        FragmentTransaction fragmentTransaction = fragment.getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }



 }