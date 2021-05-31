package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
/*import com.example.igl.Activity.TPI_Inspection_RFC_Activity;
import com.example.igl.Activity.To_DoList_Activity;*/
import com.fieldmobility.igl.Fragment.Ready_Inspection_Tpi_Fragment;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RFC_Approval_Adapter extends RecyclerView.Adapter<RFC_Approval_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private List<Bp_No_Item> bp_no_list_array;
    private List<Bp_No_Item> New_bp_no_list_array;
    private RFC_Approval_Adapter.ContactsAdapterListener listener;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    String Claim_Flage,JobFlage,BP_N0;
    public RFC_Approval_Adapter(Context context, List<Bp_No_Item> New_bp_no_list_array, RFC_Approval_Adapter.ContactsAdapterListener listener) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.listener = listener;
        this.context = context;
    }
    @Override
    public RFC_Approval_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.tpi_fesibility_adapter, parent, false);
        sharedPrefs=new SharedPrefs(context);
        RFC_Approval_Adapter.ViewHolder viewHolder = new RFC_Approval_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RFC_Approval_Adapter.ViewHolder holder, final int position) {
        final Bp_No_Item Bp_No_array = bp_no_list_array.get(position);
        holder.date_text.setText(Bp_No_array.getBp_date());
        holder.bp_no_text.setText(Bp_No_array.getBp_number());
        holder.user_name_text.setText(Bp_No_array.getFirst_name());
        holder.address_text.setText(Bp_No_array.getHouse_no()+" "+Bp_No_array.getSociety()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getCity_region());

        if(Bp_No_array.getClaimFlag().equals("null")){
            holder.unclaimed_button.setVisibility(View.GONE);
            holder.job_start_button.setVisibility(View.GONE);
        }else {
            if(Bp_No_array.getClaimFlag().equals("0")){
                holder.claimed_button.setVisibility(View.GONE);
                holder.unclaimed_button.setVisibility(View.VISIBLE);
                holder.job_start_button.setVisibility(View.VISIBLE);

                Log.e("RfcTpi",Bp_No_array.getRfcTpi());
                Log.e("login id",sharedPrefs.getUUID());
                if(Bp_No_array.getRfcTpi().equals(sharedPrefs.getUUID())){
                    holder.job_start_button.setVisibility(View.VISIBLE);
                    holder.unclaimed_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Claim_Flage="1";
                            BP_N0=bp_no_list_array.get(position).getBp_number();
                            Claimed_API_POST();
                        }
                    });

                    holder.job_start_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BP_N0=bp_no_list_array.get(position).getBp_number();
                            Job_Start_API_POST();
                        }
                    });

                }else {
                    holder.unclaimed_button.setVisibility(View.GONE);
                    holder.job_start_button.setVisibility(View.GONE);
                }
            }else {
                // holder.job_start_button.setVisibility(View.GONE);
            }
        }

        if(Bp_No_array.getIgl_status().equals("null")){
            holder.status_text.setVisibility(View.GONE);
        }else {
            if(Bp_No_array.getIgl_status().equals("0")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("Feasibility Pending");
            }else {
                //holder.status_text.setVisibility(View.GONE);
            }
            if(Bp_No_array.getIgl_status().equals("2")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("RFC Pending");
            }
            if(Bp_No_array.getIgl_status().equals("3")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("TPI Approval Pending");
                holder.unclaimed_button.setVisibility(View.GONE);
                holder.job_start_button.setVisibility(View.GONE);

            }
        }
        if(Bp_No_array.getJobFlag().equals("1")){
            holder.job_start_button.setVisibility(View.GONE);
            if(Bp_No_array.getIgl_status().equals("3")){
                holder.unclaimed_button.setVisibility(View.GONE);
            }

        }

        holder.claimed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Claim_Flage="0";
                BP_N0=bp_no_list_array.get(position).getBp_number();
                Claimed_API_POST();
            }
        });


        holder.liner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String First_name = bp_no_list_array.get(position).getFirst_name();
                String Middle_name = bp_no_list_array.get(position).getMiddle_name();
                String Last_name = bp_no_list_array.get(position).getLast_name();
                String Mobile_number = bp_no_list_array.get(position).getMobile_number();
                String Email_id = bp_no_list_array.get(position).getEmail_id();
                String Aadhaar_number = bp_no_list_array.get(position).getAadhaar_number();
                String City_region = bp_no_list_array.get(position).getCity_region();
                String Area = bp_no_list_array.get(position).getArea();
                String Society = bp_no_list_array.get(position).getSociety();
                String Landmark = bp_no_list_array.get(position).getLandmark();
                String House_type = bp_no_list_array.get(position).getHouse_type();
                String House_no = bp_no_list_array.get(position).getHouse_no();
                String Block_qtr_tower_wing = bp_no_list_array.get(position).getBlock_qtr_tower_wing();
                String Floor = bp_no_list_array.get(position).getFloor();
                String Street_gali_road = bp_no_list_array.get(position).getStreet_gali_road();
                String Pincode = bp_no_list_array.get(position).getPincode();
                String Customer_type = bp_no_list_array.get(position).getCustomer_type();
                String Lpg_company = bp_no_list_array.get(position).getLpg_company();
                String Bp_number = bp_no_list_array.get(position).getBp_number();
                String Bp_date = bp_no_list_array.get(position).getBp_date();
                String IGL_Status = bp_no_list_array.get(position).getIgl_status();
                String lpg_distributor = bp_no_list_array.get(position).getLpg_distributor();
                String lpg_conNo = bp_no_list_array.get(position).getLpg_conNo();
                String Unique_lpg_Id = bp_no_list_array.get(position).getUnique_lpg_Id();
                String lead_no = bp_no_list_array.get(position).getLead_no();
                String ownerName = bp_no_list_array.get(position).getOwnerName();
                String igl_code_group = bp_no_list_array.get(position).getIgl_code_group();
                if(IGL_Status.equals("2")){
                    Toast.makeText(context, "" + "Only Open TPI Approval Pending", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "else - " + "RFC Approval Adapter", Toast.LENGTH_SHORT).show();
                    /*Intent intent=new Intent(context, TPI_Inspection_RFC_Activity.class);
                    intent.putExtra("First_name",First_name);
                    intent.putExtra("Middle_name",Middle_name);
                    intent.putExtra("Last_name",Last_name);
                    intent.putExtra("Mobile_number",Mobile_number);
                    intent.putExtra("Email_id",Email_id);
                    intent.putExtra("Aadhaar_number",Aadhaar_number);
                    intent.putExtra("City_region",City_region);
                    intent.putExtra("Area",Area);
                    intent.putExtra("Society",Society);
                    intent.putExtra("Landmark",Landmark);
                    intent.putExtra("House_type",House_type);
                    intent.putExtra("House_no",House_no);
                    intent.putExtra("Block_qtr_tower_wing",Block_qtr_tower_wing);
                    intent.putExtra("Floor",Floor);
                    intent.putExtra("Street_gali_road",Street_gali_road);
                    intent.putExtra("Pincode",Pincode);
                    intent.putExtra("Customer_type",Customer_type);
                    intent.putExtra("Lpg_company",Lpg_company);
                    intent.putExtra("Bp_number",Bp_number);
                    intent.putExtra("Bp_date",Bp_date);
                    intent.putExtra("IGL_Status",IGL_Status);
                    intent.putExtra("lpg_distributor",lpg_distributor);
                    intent.putExtra("lpg_conNo",lpg_conNo);
                    intent.putExtra("Unique_lpg_Id",Unique_lpg_Id);
                    intent.putExtra("lead_no",lead_no);
                    intent.putExtra("ownerName",ownerName);
                    intent.putExtra("igl_code_group",igl_code_group);
                    intent.putExtra("TPI_Status_Code","1");
                    intent.putExtra("Feasibility_Type","1");
                    context.startActivity(intent);*/
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text,user_name_text,address_text,date_text,status_text;
        public LinearLayout liner_layout;
        public CardView linearLayout;
        Button claimed_button,unclaimed_button,job_start_button;
        public ViewHolder(View itemView) {
            super(itemView);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            status_text= (TextView) itemView.findViewById(R.id.status_text);
            claimed_button= (Button) itemView.findViewById(R.id.claimed_button);
            unclaimed_button= (Button) itemView.findViewById(R.id.unclaimed_button);
            job_start_button= (Button) itemView.findViewById(R.id.job_start_button);
            liner_layout = (LinearLayout) itemView.findViewById(R.id.liner_layout);
        }
    }
    @Override
    public Filter getFilter() { return new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                bp_no_list_array = New_bp_no_list_array;
            } else {
                List<Bp_No_Item> filteredList = new ArrayList<>();
                for (Bp_No_Item row : New_bp_no_list_array) {
                    if (row.getBp_number().toLowerCase().contains(charString.toLowerCase()) || row.getFirst_name().toLowerCase().contains(charString.toLowerCase())) {
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

    public void Claimed_API_POST() {
        materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.ClaimUnclaim+BP_N0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());
                            if(json.getString("Sucess").equals("true")){
                                Toast.makeText(context, "" + "Successfully Updated", Toast.LENGTH_SHORT).show();
                                ((Ready_Inspection_Tpi_Fragment)context).Bp_No_List();

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
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }            }
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


    public void Job_Start_API_POST() {
        materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.JobStart+BP_N0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());
                            if(json.getString("Sucess").equals("true")){
                                Toast.makeText(context, "" + "Successfully Updated", Toast.LENGTH_SHORT).show();
                                ((Ready_Inspection_Tpi_Fragment)context).Bp_No_List();

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
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }            }
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
}