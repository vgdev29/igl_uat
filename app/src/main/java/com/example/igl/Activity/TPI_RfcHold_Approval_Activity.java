package com.example.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igl.Adapter.TPI_Approval_Adapter;
import com.example.igl.Helper.CommonUtils;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TPI_RfcHold_Approval_Activity extends Activity {

    ProgressDialog materialDialog;
    SharedPrefs sharedPrefs;
    ImageView back;

    Bp_No_Item bp_no_item = new Bp_No_Item();
    TextView header_title,bpno,custname,mob,email,adress,status,substatus,description,feasibilitydate,supstatus;
    LinearLayout top_layout;
    Button approve,decline;
    String BP_NO,LEAD_NO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tpi_rfchold_approval);
        materialDialog = new ProgressDialog(this);
        materialDialog.setTitle("Please wait...");
        materialDialog.setCancelable(true);
        materialDialog.setCanceledOnTouchOutside(true);
        sharedPrefs = new SharedPrefs(this);
        BP_NO = getIntent().getStringExtra("bpno");
        LEAD_NO = getIntent().getStringExtra("leadno");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Layout_ID();
    }
    private void Layout_ID() {
        top_layout=findViewById(R.id.top_layout);
        header_title=findViewById(R.id.header_title);
        header_title.setText("TPI Approval");
        bpno = findViewById(R.id.txt_bpno);
        custname = findViewById(R.id.txt_custname);
        mob = findViewById(R.id.txt_mob);
        email = findViewById(R.id.txt_email);
        adress = findViewById(R.id.txt_address);
        status = findViewById(R.id.txt_rfcstatus);
        substatus = findViewById(R.id.txt_rfcsubstatus);
        description = findViewById(R.id.txt_rfcdescription);
        feasibilitydate = findViewById(R.id.txt_rfcfeasibilitydate);
        supstatus = findViewById(R.id.txt_rfcapprove);
        approve = findViewById(R.id.approve_button);
        decline = findViewById(R.id.decline_button);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpi_approval_done();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpi_decline_done();
            }
        });
        Bp_No_List();
    }

    public void Bp_No_List() {


        materialDialog.show();
        String url = Constants.TPI_RFCHOLD_APPROVAl_Data+BP_NO;
        Log.d("tpi",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.TPI_RFCHOLD_APPROVAl_Data+BP_NO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        approve.setEnabled(true);
                        decline.setEnabled(true);
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.d("tpi",jsonObject.toString());
                            final JSONArray Bp_Details = jsonObject.getJSONArray("Bp_Details");


                                JSONObject data_object=Bp_Details.getJSONObject(0);

                                bp_no_item.setFirst_name(data_object.getString("first_name"));
                                bp_no_item.setMiddle_name(data_object.getString("middle_name"));
                                bp_no_item.setLast_name(data_object.getString("last_name"));
                                bp_no_item.setMobile_number(data_object.getString("mobile_number"));
                                bp_no_item.setEmail_id(data_object.getString("email_id"));
                                bp_no_item.setAadhaar_number(data_object.getString("aadhaar_number"));
                                bp_no_item.setCity_region(data_object.getString("city_region"));
                                bp_no_item.setArea(data_object.getString("area"));
                                bp_no_item.setSociety(data_object.getString("society"));
                                bp_no_item.setLandmark(data_object.getString("landmark"));
                                bp_no_item.setHouse_type(data_object.getString("house_type"));
                                bp_no_item.setHouse_no(data_object.getString("house_no"));
                                bp_no_item.setBlock_qtr_tower_wing(data_object.getString("block_qtr_tower_wing"));
                                bp_no_item.setFloor(data_object.getString("floor"));
                                bp_no_item.setStreet_gali_road(data_object.getString("street_gali_road"));
                                bp_no_item.setPincode(data_object.getString("pincode"));
                                bp_no_item.setCustomer_type(data_object.getString("customer_type"));
                                bp_no_item.setLpg_company(data_object.getString("lpg_company"));
                                bp_no_item.setBp_number(data_object.getString("bp_number"));
                                bp_no_item.setBp_date(data_object.getString("bp_date"));
                                bp_no_item.setIgl_status(data_object.getString("igl_status"));

                                bp_no_item.setLpg_distributor(data_object.getString("rfcStatus"));
                                bp_no_item.setLpg_conNo(data_object.getString("rfcReason"));
                                bp_no_item.setUnique_lpg_Id(data_object.getString("rfcDescription"));
                                bp_no_item.setOwnerName(data_object.getString("fesabilityDate"));
                                bp_no_item.setChequeNo(data_object.getString("approveDeclineSupervisor"));
                                inflateData();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                materialDialog.dismiss();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                materialDialog.dismiss();
                            }
                        }
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onResume() {
        super.onResume();

        try {

            Bp_No_List();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void inflateData()
    {
        materialDialog.dismiss();
        bpno.setText(bp_no_item.getBp_number());
        custname.setText(bp_no_item.getFirst_name() +" "+bp_no_item.getLast_name());
        mob.setText(bp_no_item.getMobile_number());
        email.setText(bp_no_item.getEmail_id());
        adress.setText(bp_no_item.getHouse_no()+","+bp_no_item.getHouse_type()+","+bp_no_item.getFloor()+"\n"+bp_no_item.getSociety()+","+bp_no_item.getArea()+"\n"+bp_no_item.getCity_region()+"-"+bp_no_item.getPincode());
        status.setText(bp_no_item.getLpg_distributor());
        description.setText(bp_no_item.getUnique_lpg_Id());
        substatus.setText(bp_no_item.getLpg_conNo());
        feasibilitydate.setText(bp_no_item.getOwnerName());
        supstatus.setText(bp_no_item.getChequeNo());

    }


    public void tpi_approval_done() {


        materialDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TPI_RFC_HOLD_APPROVAl_DECLINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("Message");
                            Log.d("Response++",jsonObject.toString());
                            CommonUtils.toast_msg(TPI_RfcHold_Approval_Activity.this,msg);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
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
                    params.put("lead_no",LEAD_NO);
                    params.put("bp_no", BP_NO);
                    params.put("holdFlag", "0");


                } catch (Exception e) {
                }
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public void tpi_decline_done() {


        materialDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TPI_RFC_HOLD_APPROVAl_DECLINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("Message");
                            Log.d("Response++",jsonObject.toString());
                            CommonUtils.toast_msg(TPI_RfcHold_Approval_Activity.this,msg);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
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
                    params.put("lead_no",LEAD_NO);
                    params.put("bp_no", BP_NO);
                    params.put("holdFlag", "1");


                } catch (Exception e) {
                }
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }




}