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

public class MdpeTpiDpr extends AppCompatActivity {
    SharedPrefs sharedPrefs;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MdpeTPIDPR_Adapter mdpeDpr_adapter;
    EditText editTextSearch;
    TextView text_count;
    ProgressDialog progressDialog;
    static String log = "mdpepending";
    ArrayList<DprDetails_Model> dprlist = new ArrayList<>();
    ImageView rfc_filter;
    private Dialog mFilterDialog;
    private RadioGroup radioGroup;
    RelativeLayout rel_nodata;
    Button refresh ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdpe_tpi_dpr);
        sharedPrefs = new SharedPrefs(this);
        Layout_ID();
        rel_nodata =  findViewById(R.id.rel_nodata);
        refresh =  findViewById(R.id.btnTryAgain);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dprNetwork_call();
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
                    dprlist.clear();
                    dprNetwork_call();
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
                mdpeDpr_adapter.getFilter().filter(s.toString());
                if (start == 0 && count==0)
                {
                    if(dprlist.size()>0) {
                        mdpeDpr_adapter.setData(dprlist);
                        text_count.setText("Count - "+ dprlist.size());

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


        dprNetwork_call();
    }
    public void dprNetwork_call() {
        dprlist.clear();
        progressDialog = ProgressDialog.show(this, "", "Loading data", true);
        progressDialog.setCancelable(false);
        String url = Constants.MDPETPI_DPRDETAILS+sharedPrefs.getUUID();
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
                                    DprDetails_Model dpr = new DprDetails_Model();
                                    dpr.setAllocation_no(data_object.getString("allocation_no"));
                                    dpr.setSub_allocation(data_object.getString("sub_allocation"));
                                    dpr.setAgent_id(data_object.getString("agent_id"));
                                    dpr.setPo_number(data_object.getString("po_number"));
                                    dpr.setCity(data_object.getString("city"));
                                    dpr.setZone(data_object.getString("zone"));
                                    dpr.setArea(data_object.getString("area"));
                                    dpr.setSociety(data_object.getString("society"));
                                    dpr.setWbs_number(data_object.getString("wbs_number"));
                                    dpr.setMethod(data_object.getString("method"));
                                    dpr.setArea_type(data_object.getString("area_type"));
                                    dpr.setSize(data_object.getString("size"));
                                    dpr.setTrenchless_method(data_object.getString("trenchless_method"));
                                    dpr.setLength(data_object.getString("length"));
                                    dpr.setClaim_date(data_object.getString("claim_date"));
                                    dpr.setTpi_claim(data_object.getInt("tpi_claim"));
                                    dpr.setTpi_id(data_object.getString("tpi_id"));
                                    dpr.setAgent_assign_date(data_object.getString("agent_assign_date"));
                                    dpr.setAgent(data_object.getString("agent"));
                                    dpr.setAgent_mob(data_object.getString("agent_mob"));
                                    dpr.setSection_id(data_object.getString("section_id"));
                                    dpr.setCategory(data_object.getString("category"));
                                    dpr.setSection(data_object.getString("section"));
                                    dpr.setSub_Section(data_object.getString("sub_Section"));
                                    dpr.setInput_unit(data_object.getString("input_unit"));
                                    dpr.setDpr_no(data_object.getString("dpr_no"));
                                    dpr.setFiles_path(data_object.getString("files_path"));
                                    dpr.setInput(data_object.getDouble("input"));
                                    dpr.setLatitude(data_object.getString("latitude"));
                                    dpr.setLongitude(data_object.getString("longitude"));
                                    dpr.setCreation_date(data_object.getString("creation_date"));
                                    dpr.setIdDpr(data_object.getLong("idDpr"));
                                    dpr.setIdSection(data_object.getLong("idSection"));
                                    dpr.setIdSuballo(data_object.getLong("idSuballo"));
                                    dpr.setLocation(data_object.getString("location"));
                                    dprlist.add(dpr);
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
                        mdpeDpr_adapter = new MdpeTPIDPR_Adapter(MdpeTpiDpr.this,dprlist);
                        recyclerView.setAdapter(mdpeDpr_adapter);
                        mdpeDpr_adapter.notifyDataSetChanged();
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