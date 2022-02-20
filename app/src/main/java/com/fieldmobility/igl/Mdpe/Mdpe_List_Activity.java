package com.fieldmobility.igl.Mdpe;

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
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Mdpe_List_Activity extends AppCompatActivity {
    SharedPrefs sharedPrefs;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MdpeList_Adapter mdpeList_adapter;
    EditText editTextSearch;
    TextView  text_count;
    ProgressDialog progressDialog;
    static String log = "mdpepending";
    ArrayList<MdpeSubAllocation> suballo = new ArrayList<>();
    ImageView rfc_filter,back;
    private Dialog mFilterDialog;
    private RadioGroup radioGroup;
    RelativeLayout rel_nodata;
    Button refresh ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdpe_list);
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
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Layout_ID() {
        text_count = findViewById(R.id.list_count);
        rfc_filter = findViewById(R.id.rfc_filter);
        rfc_filter.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    suballo.clear();
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
                mdpeList_adapter.getFilter().filter(s.toString());
                if (start == 0 && count==0)
                {
                    if(suballo.size()>0) {
                        mdpeList_adapter.setData(suballo);
                        text_count.setText("Count - "+ suballo.size());

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


        pendingNetwork_call();
    }

    public void pendingNetwork_call() {
        suballo.clear();
        progressDialog = ProgressDialog.show(this, "", "Loading data", true);
        progressDialog.setCancelable(false);
        String url = Constants.MDPELIST_SUP+sharedPrefs.getUUID();
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
                                final JSONArray data_array = jsonObject.getJSONArray("data");
                                text_count.setText("Count - "+String.valueOf(data_array.length()));
                                for(int i=0; i<data_array.length();i++) {
                                    JSONObject data_object = data_array.getJSONObject(i);
                                    MdpeSubAllocation subAllocation = new MdpeSubAllocation();
                                    subAllocation.setId(data_object.getLong("id"));
                                    subAllocation.setAllocationNumber(data_object.getString("allocationNumber"));
                                    subAllocation.setSuballocationNumber(data_object.getString("suballocationNumber"));
                                    subAllocation.setAgentId(data_object.getString("agentId"));
                                    subAllocation.setPoNumber(data_object.getString("poNumber"));
                                    subAllocation.setCity(data_object.getString("city"));
                                    subAllocation.setZone(data_object.getString("zone"));
                                    subAllocation.setArea(data_object.getString("area"));
                                    subAllocation.setSociety(data_object.getString("society"));
                                    subAllocation.setWbsNumber(data_object.getString("wbsNumber"));
                                    subAllocation.setMethod(data_object.getString("method"));
                                    subAllocation.setAreaType(data_object.getString("areaType"));
                                    subAllocation.setSize(data_object.getString("size"));
                                    subAllocation.setTrenchlessMethod(data_object.getString("trenchlessMethod"));
                                    subAllocation.setLength(data_object.getString("length"));
                                    subAllocation.setUserName(data_object.getString("tpi_name"));
                                    subAllocation.setUserMob(data_object.getString("tpi_mob"));
                                    subAllocation.setTpiClaim(data_object.getInt("tpiClaim"));
                                    subAllocation.setClaimDate(data_object.getString("claimDate"));
                                    subAllocation.setTpiId(data_object.getString("tpiId"));
                                    subAllocation.setContId(data_object.getString("contId"));
                                    subAllocation.setAgentAssignDate(data_object.getString("agentAssignDate"));

                                    suballo.add(subAllocation);
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
                        mdpeList_adapter = new MdpeList_Adapter(Mdpe_List_Activity.this,suballo);
                        recyclerView.setAdapter(mdpeList_adapter);
                        mdpeList_adapter.notifyDataSetChanged();
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
                }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                1200,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}