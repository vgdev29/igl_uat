package com.example.igl.Adapter;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.igl.Fragment.Ready_Inspection_Tpi_Fragment;
import com.example.igl.Helper.AppController;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.Model.BpDetail;
import com.example.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void onBindViewHolder(final TPI_RFC_Pending_Adapter.ViewHolder holder, final int position) {
        final BpDetail Bp_No_array = bp_no_list_array.get(position);
        holder.date_text.setText(Bp_No_array.getBpDate());
        holder.bp_no_text.setText(Bp_No_array.getBpNumber());
        holder.user_name_text.setText(Bp_No_array.getFirstName());
        holder.address_text.setText(Bp_No_array.getHouseNo() + " " + Bp_No_array.getSociety() + " " + Bp_No_array.getArea() + " " + Bp_No_array.getCityRegion());
        holder.zone_text.setText(Bp_No_array.getZoneCode());
        //logic for cliam and unclaimed button
        Log.d("claimflag", "" + Bp_No_array.getClaimFlag());
        if (Bp_No_array.getClaimFlag().equals("null") || Bp_No_array.getClaimFlag().equalsIgnoreCase("1")) {
            holder.unclaimed_button.setVisibility(View.GONE);
            holder.job_start_button.setVisibility(View.GONE);
            Log.d(log, "if RfcTpi = " + Bp_No_array.getClaimFlag());
        } else if (Bp_No_array.getClaimFlag().equals("0")) {
            holder.claimed_button.setVisibility(View.GONE);
            holder.unclaimed_button.setVisibility(View.VISIBLE);
            //LOgic for job start button
            if (Bp_No_array.getJobFlag().equals("1")) {
                holder.job_start_button.setVisibility(View.GONE);

            } else if (Bp_No_array.getJobFlag().equals("0")) {
                holder.job_start_button.setVisibility(View.VISIBLE);
            }
            Log.d(log, "else RfcTpi = " + Bp_No_array.getClaimFlag());
            Log.d(log, "login id = " + sharedPrefs.getUUID());
        }

        //logic for status
        if (Bp_No_array.getIglStatus().equals("null")) {
            holder.status_text.setVisibility(View.GONE);
        } else {
            holder.status_text.setVisibility(View.VISIBLE);
            holder.status_text.setText("RFC Pending");
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
                Claimed_API_POST();
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
                Unclaimed_API_POST();
            }
        });

        holder.job_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BP_N0 = bp_no_list_array.get(position).getBpNumber();
               // holder.job_start_button.setVisibility(View.GONE);
                Job_Start_API_POST();

            }
        });


    }

    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text, user_name_text, address_text, date_text, status_text, zone_text;

        Button claimed_button, unclaimed_button, job_start_button, rfc_info;

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
                        if (row.getBpNumber().toLowerCase().contains(charString.toLowerCase()) || row.getFirstName().toLowerCase().contains(charString.toLowerCase())) {
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

    public void Claimed_API_POST() {
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
                                fragmentReload();
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

    public void Unclaimed_API_POST() {

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
                                 fragmentReload();
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


    public void Job_Start_API_POST() {
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
                                 fragmentReload();
                                //  ((Ready_Inspection_Tpi_Fragment) context).Bp_No_List();

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
        cont_name.setText("Rfc Admin :- " + contractor_Name);
        cont_mob.setText("MobNo :- " + contractor_No);
        sup_name.setText("Rfc Vendor :- " + supervisor);
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