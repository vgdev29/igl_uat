package com.fieldmobility.igl.MITDtoRFC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Model.BpDetail;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MITD_PendingList extends AppCompatActivity {
    SharedPrefs sharedPrefs;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TPI_MITD_Pending_Adapter tpi_inspection_adapter;
    EditText editTextSearch;

    TextView  search_bp,text_searchdata;
    ProgressDialog progressDialog;

    static String log = "mitdpending";
    ArrayList<BpDetail> bpDetails = new ArrayList<>();
    ArrayList<BpDetail> searchdetails = new ArrayList<>();
    ImageView rfc_filter;
    private Dialog mFilterDialog;
    private RadioGroup radioGroup;
    RelativeLayout rel_nodata;
    Button refresh ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitd_pending);
        sharedPrefs = new SharedPrefs(this);
        Layout_ID();
        rel_nodata =  findViewById(R.id.rel_nodata);
        refresh =  findViewById(R.id.btnTryAgain);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendingNetwork_call();
            }
        });
    }

    private void Layout_ID() {
        text_searchdata=findViewById(R.id.text_searchdata);
        rfc_filter = findViewById(R.id.rfc_filter);
        rfc_filter.setVisibility(View.GONE);
        search_bp = findViewById(R.id.search_tpibp);
        search_bp.setText("Search");
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    bpDetails.clear();
                    pendingNetwork_call();
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch=findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tpi_inspection_adapter.getFilter().filter(s.toString());
                if (start == 0 && count==0)
                {
                    if(bpDetails.size()>0) {
                        tpi_inspection_adapter.setData(bpDetails);
                        text_searchdata.setText("Count - "+ bpDetails.size());

                    }

                }
                Log.d("editetxt","start = "+start+" count = "+count+" before = "+before);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rfc_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  showFiltersDialog();
            }
        });

        search_bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bp = editTextSearch.getText().toString().trim();
                if (bp.length()>0)
                {
                    searchBp_network(bp);
                }
            }
        });
        text_searchdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        pendingNetwork_call();
    }

    public void pendingNetwork_call() {
        bpDetails.clear();
        progressDialog = ProgressDialog.show(this, "", "Loading data", true);
        progressDialog.setCancelable(false);
        String url = Constants.TPI_MITD_PENDING+sharedPrefs.getZone_Code();
        Log.d(log,url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            final JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response++",jsonObject.toString());
                            final String status = jsonObject.getString("status");
                            if (status.equals("200")) {
                                rel_nodata.setVisibility(View.GONE);
                                final JSONArray Bp_Details = jsonObject.getJSONArray("Bp_Details");
                                text_searchdata.setText("Count - "+String.valueOf(Bp_Details.length()));
                                for(int i=0; i<Bp_Details.length();i++) {
                                    JSONObject data_object = Bp_Details.getJSONObject(i);
                                    BpDetail bp_no_item = new BpDetail();
                                    bp_no_item.setFirstName(data_object.getString("first_name"));
                                    bp_no_item.setMiddleName(data_object.getString("middle_name"));
                                    bp_no_item.setLastName(data_object.getString("last_name"));
                                    bp_no_item.setMobileNumber(data_object.getString("mobile_number"));
                                    bp_no_item.setEmailId(data_object.getString("email_id"));
                                    bp_no_item.setAadhaarNumber(data_object.getString("aadhaar_number"));
                                    bp_no_item.setCityRegion(data_object.getString("city_region"));
                                    bp_no_item.setArea(data_object.getString("area"));
                                    bp_no_item.setSociety(data_object.getString("society"));
                                    bp_no_item.setLandmark(data_object.getString("landmark"));
                                    bp_no_item.setHouseType(data_object.getString("house_type"));
                                    bp_no_item.setHouseNo(data_object.getString("house_no"));
                                    bp_no_item.setBlockQtrTowerWing(data_object.getString("block_qtr_tower_wing"));
                                    bp_no_item.setFloor(data_object.getString("floor"));
                                    bp_no_item.setStreetGaliRoad(data_object.getString("street_gali_road"));
                                    bp_no_item.setPincode(data_object.getString("pincode"));
                                    bp_no_item.setCustomerType(data_object.getString("customer_type"));
                                    bp_no_item.setLpgCompany(data_object.getString("lpg_company"));
                                    bp_no_item.setBpNumber(data_object.getString("bp_number"));
                                    bp_no_item.setBpDate(data_object.getString("bp_date"));
                                    bp_no_item.setIglStatus(data_object.getString("igl_status"));
                                    bp_no_item.setLpgDistributor(data_object.getString("lpg_distributor"));
                                    bp_no_item.setLpgConNo(data_object.getString("lpg_conNo"));
                                    bp_no_item.setUniqueLpgId(data_object.getString("unique_lpg_Id"));
                                    bp_no_item.setOwnerName(data_object.getString("ownerName"));
                                    bp_no_item.setChequeNo(data_object.getString("chequeNo"));
                                    bp_no_item.setChequeDate(data_object.getString("chequeDate"));
                                    bp_no_item.setLeadNo(data_object.getString("lead_no"));
                                    bp_no_item.setDrawnOn(data_object.getString("drawnOn"));
                                    bp_no_item.setAmount(data_object.getString("amount"));
                                    bp_no_item.setAddressProof(data_object.getString("addressProof"));
                                    bp_no_item.setIdproof(data_object.getString("idproof"));
                                    bp_no_item.setIglCodeGroup(data_object.getString("igl_code_group"));
                                    bp_no_item.setClaimFlag(data_object.getString("claimFlag"));
                                    bp_no_item.setJobFlag(data_object.getString("jobFlag"));
                                    bp_no_item.setRfcTpi(data_object.getString("rfcTpi"));
                                    bp_no_item.setRfcVendor(data_object.getString("rfcVendor"));
                                    bp_no_item.setTc_status(data_object.getInt("holdStatus"));
                                    bp_no_item.setZoneCode(data_object.getString("zonecode"));
                                    bp_no_item.setControlRoom(data_object.getString("controlRoom"));
                                    bp_no_item.setIgl_rfcvendor_assigndate(data_object.getString("supervisor_assigndate"));
                                    bpDetails.add(bp_no_item);
                                }
                            }
                            else
                            {
                                rel_nodata.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        tpi_inspection_adapter = new TPI_MITD_Pending_Adapter(MITD_PendingList.this,bpDetails);
                        recyclerView.setAdapter(tpi_inspection_adapter);
                        tpi_inspection_adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
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
                    params.put("id", sharedPrefs.getUUID());
                } catch (Exception e) {
                }
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                1200,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void searchBp_network(String bp) {
        searchdetails.clear();
        progressDialog = ProgressDialog.show(this, "", "Loading data", true);
        progressDialog.setCancelable(false);
        String url = Constants.TPI_MITDPENSEARCH+sharedPrefs.getZone_Code()+"&bp="+bp;
        Log.d("tpi",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       progressDialog.dismiss();
                        try {

                            final JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response++",jsonObject.toString());

                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("Message");
                            if (status.equals("200")) {
                                rel_nodata.setVisibility(View.GONE);
                                final JSONArray Bp_Details = jsonObject.getJSONArray("Bp_Details");
                                text_searchdata.setText("Search Result - "+String.valueOf(Bp_Details.length()));
                                for(int i=0; i<Bp_Details.length();i++) {
                                    JSONObject data_object = Bp_Details.getJSONObject(i);
                                    BpDetail bp_no_item = new BpDetail();
                                    bp_no_item.setFirstName(data_object.getString("first_name"));
                                    bp_no_item.setMiddleName(data_object.getString("middle_name"));
                                    bp_no_item.setLastName(data_object.getString("last_name"));
                                    bp_no_item.setMobileNumber(data_object.getString("mobile_number"));
                                    bp_no_item.setEmailId(data_object.getString("email_id"));
                                    bp_no_item.setAadhaarNumber(data_object.getString("aadhaar_number"));
                                    bp_no_item.setCityRegion(data_object.getString("city_region"));
                                    bp_no_item.setArea(data_object.getString("area"));
                                    bp_no_item.setSociety(data_object.getString("society"));
                                    bp_no_item.setLandmark(data_object.getString("landmark"));
                                    bp_no_item.setHouseType(data_object.getString("house_type"));
                                    bp_no_item.setHouseNo(data_object.getString("house_no"));
                                    bp_no_item.setBlockQtrTowerWing(data_object.getString("block_qtr_tower_wing"));
                                    bp_no_item.setFloor(data_object.getString("floor"));
                                    bp_no_item.setStreetGaliRoad(data_object.getString("street_gali_road"));
                                    bp_no_item.setPincode(data_object.getString("pincode"));
                                    bp_no_item.setCustomerType(data_object.getString("customer_type"));
                                    bp_no_item.setLpgCompany(data_object.getString("lpg_company"));
                                    bp_no_item.setBpNumber(data_object.getString("bp_number"));
                                    bp_no_item.setBpDate(data_object.getString("bp_date"));
                                    bp_no_item.setIglStatus(data_object.getString("igl_status"));
                                    bp_no_item.setLpgDistributor(data_object.getString("lpg_distributor"));
                                    bp_no_item.setLpgConNo(data_object.getString("lpg_conNo"));
                                    bp_no_item.setUniqueLpgId(data_object.getString("unique_lpg_Id"));
                                    bp_no_item.setOwnerName(data_object.getString("ownerName"));
                                    bp_no_item.setChequeNo(data_object.getString("chequeNo"));
                                    bp_no_item.setChequeDate(data_object.getString("chequeDate"));
                                    bp_no_item.setLeadNo(data_object.getString("lead_no"));
                                    bp_no_item.setDrawnOn(data_object.getString("drawnOn"));
                                    bp_no_item.setAmount(data_object.getString("amount"));
                                    bp_no_item.setAddressProof(data_object.getString("addressProof"));
                                    bp_no_item.setIdproof(data_object.getString("idproof"));
                                    bp_no_item.setIglCodeGroup(data_object.getString("igl_code_group"));
                                    bp_no_item.setClaimFlag(data_object.getString("claimFlag"));
                                    bp_no_item.setJobFlag(data_object.getString("jobFlag"));
                                    bp_no_item.setRfcTpi(data_object.getString("rfcTpi"));
                                    bp_no_item.setRfcVendor(data_object.getString("rfcVendor"));
                                    bp_no_item.setRfcAdminName(data_object.getString("rfcAdminName"));
                                    bp_no_item.setRfcAdminMobileNo(data_object.getString("rfcAdminMobileNo"));
                                    bp_no_item.setRfcVendorMobileNo(data_object.getString("rfcVendorMobileNo"));
                                    bp_no_item.setRfcVendorName(data_object.getString("rfcVendorName"));
                                    bp_no_item.setTc_status(data_object.getInt("holdStatus"));
                                    bp_no_item.setZoneCode(data_object.getString("zonecode"));
                                    bp_no_item.setControlRoom(data_object.getString("controlRoom"));
                                    bp_no_item.setIgl_rfcvendor_assigndate(data_object.getString("supervisor_assigndate"));

                                    searchdetails.add(bp_no_item);
                                }
                            }
                            else
                            {
                                CommonUtils.toast_msg(MITD_PendingList.this,message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        tpi_inspection_adapter.setData(searchdetails);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CommonUtils.dismissProgressBar(MITD_PendingList.this);
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
                    params.put("id", sharedPrefs.getUUID());
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