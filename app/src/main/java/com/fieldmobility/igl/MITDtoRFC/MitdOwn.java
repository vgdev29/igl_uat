package com.fieldmobility.igl.MITDtoRFC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.fieldmobility.igl.Activity.RFC_Connection_Listing;
import com.fieldmobility.igl.Adapter.RFC_Adapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MitdOwn extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    MaterialDialog materialDialog;
    private List<Bp_No_Item> bp_no_array;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MITDRFC_Adapter adapter;
    EditText editTextSearch;
    TextView list_count;
    static String log = "mitdownlisting";
    private Dialog mFilterDialog;
    private RadioGroup radioGroup;
    private int filtersDialogOpenCount = 0;
    private int mSelectedId;
    RelativeLayout rel_nodata;
    Button refresh ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitd_own);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        list_count = findViewById(R.id.list_count);
        rel_nodata =  findViewById(R.id.rel_nodata);
        refresh =  findViewById(R.id.btnTryAgain);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bp_No_List();
            }
        });
        bp_no_array=new ArrayList<>();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    bp_no_array.clear();
                    Bp_No_List();
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch=findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  bookadapter.getFilter().filter(s.toString());
                adapter.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Bp_No_List();
    }



    public void Bp_No_List() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.MITD_OWN_LISTING_GET+sharedPrefs.getUUID();
        Log.d(log,"bp no list = "+url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.d(log,"Response++"+jsonObject.toString());
                            final String status = jsonObject.getString("status");
                            if (status.equals("200")) {
                                rel_nodata.setVisibility(View.GONE);
                                final JSONObject Bp_Details = jsonObject.getJSONObject("Bp_Details");
                                JSONArray payload=Bp_Details.getJSONArray("users");
                                list_count.setText("Count\n"+String.valueOf(payload.length()));
                                for(int i=0; i<payload.length();i++) {
                                    JSONObject data_object=payload.getJSONObject(i);
                                    Bp_No_Item bp_no_item = new Bp_No_Item();
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
                                    bp_no_item.setLpg_distributor(data_object.getString("lpg_distributor"));
                                    bp_no_item.setLpg_conNo(data_object.getString("lpg_conNo"));
                                    bp_no_item.setUnique_lpg_Id(data_object.getString("unique_lpg_Id"));
                                    bp_no_item.setOwnerName(data_object.getString("ownerName"));
                                    bp_no_item.setChequeNo(data_object.getString("chequeNo"));
                                    bp_no_item.setChequeDate(data_object.getString("chequeDate"));
                                    bp_no_item.setLead_no(data_object.getString("lead_no"));
                                    bp_no_item.setDrawnOn(data_object.getString("drawnOn"));
                                    bp_no_item.setAmount(data_object.getString("amount"));
                                    bp_no_item.setAddressProof(data_object.getString("addressProof"));
                                    bp_no_item.setIdproof(data_object.getString("idproof"));
                                    bp_no_item.setIgl_code_group(data_object.getString("igl_code_group"));
                                    bp_no_item.setFesabilityTpiName(data_object.getString("fesabilityTpiName"));
                                    bp_no_item.setPipeline_length(data_object.getString("pipeline_description"));
                                    bp_no_item.setPipeline_length_id(data_object.getString("pipelineId"));
                                    bp_no_item.setRfcTpi(data_object.getString("rfctpiname"));
                                    bp_no_item.setRfcVendor(data_object.getString("rfcVendor"));
                                    bp_no_item.setJobFlag(data_object.getString("jobFlag"));
                                    bp_no_item.setClaimFlag(data_object.getString("claimFlag"));
                                    bp_no_item.setVendorMobileNo(data_object.getString("vendorMobileNo"));
                                    bp_no_item.setFesabilityTpimobileNo(data_object.getString("fesabilityTpimobileNo"));

                                    bp_no_item.setRFCMobileNo(data_object.getString("rfcmobileNo"));
                                    bp_no_item.setDeclinedDescription(data_object.getString("declinedDescription"));
                                    bp_no_item.setRfcAdmin(data_object.getString("rfcadmin"));
                                    bp_no_item.setControlRoom(data_object.getString("controlRoom"));
                                    bp_no_item.setIgl_rfcvendor_assigndate(data_object.getString("supervisor_assigndate"));
                                    //fesabilityTpiName : "suruchipmc"
                                    Log.d(log,"bp item = " + bp_no_item.toString());
                                    bp_no_array.add(bp_no_item);
                                }
                            }
                            else
                            {
                                rel_nodata.setVisibility(View.VISIBLE);
                            }

                        }catch (JSONException e) {
                            Log.d(log,"catch1");
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            Log.d(log,"catch2");
                            e.printStackTrace();
                        }catch (Exception e) {
                            Log.d(log,"catch3");
                            e.printStackTrace();
                        }
                        adapter = new MITDRFC_Adapter(MitdOwn.this,bp_no_array);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(log,"error"+error.getMessage()+error.networkResponse);
                        materialDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            Log.d(log,"error if");
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


    /*@Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        //  this.recreate();

    }*/
}