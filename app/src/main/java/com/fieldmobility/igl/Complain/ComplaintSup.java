package com.fieldmobility.igl.Complain;

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

public class ComplaintSup extends AppCompatActivity {

    SharedPrefs sharedPrefs;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ComSupList_Adapter comsup_adap;
    EditText editTextSearch;
    TextView text_count;
    ProgressDialog progressDialog;
    static String log = "mdpepending";
    ArrayList<ComplainModel> complist = new ArrayList<>();
    ImageView rfc_filter,back;
    private Dialog mFilterDialog;
    private RadioGroup radioGroup;
    RelativeLayout rel_nodata;
    Button refresh ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_sup);
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
                onBackPressed();
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
                    complist.clear();
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
                comsup_adap.getFilter().filter(s.toString());
                if (start == 0 && count==0)
                {
                    if(complist.size()>0) {
                        comsup_adap.setData(complist);
                        text_count.setText("Count - "+ complist.size());

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
        complist.clear();
        progressDialog = ProgressDialog.show(this, "", "Loading data", true);
        progressDialog.setCancelable(false);
        String url = Constants.COMPLAIN_SUPID+sharedPrefs.getUUID();
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
                                    ComplainModel comp = new ComplainModel();
                                    comp.setComplaintId(data_object.getLong("complaintId"));
                                    comp.setCompType(data_object.getString("compType"));
                                    comp.setSupName(data_object.getString("supName"));
                                    comp.setSupMob(data_object.getString("supMob"));
                                    comp.setTpiName(data_object.getString("tpiName"));
                                    comp.setTpiMob(data_object.getString("tpiMob"));
                                    comp.setCriName(data_object.getString("criName"));
                                    comp.setCriMob(data_object.getString("criMob"));
                                    comp.setBpNumber(data_object.getInt("bpNumber"));
                                    comp.setComplaintCategoryId(data_object.getInt("complaintCategoryId"));
                                    comp.setZone(data_object.getString("zone"));
                                    comp.setControlRoom(data_object.getString("controlRoom"));
                                    comp.setTicketType(data_object.getString("ticketType"));
                                    comp.setMeterNo(data_object.getString("meterNo"));
                                    comp.setMeterReading(data_object.getString("meterReading"));
                                    comp.setMeterReadingDate(data_object.getString("meterReadingDate"));
                                    comp.setBurnerNumber(data_object.getString("burnerNumber"));
                                    comp.setLineItemNumber(data_object.getString("lineItemNumber"));
                                    comp.setQuantity(data_object.getString("quantity"));
                                    comp.setLocType(data_object.getString("locType"));
                                    comp.setReasonRefund(data_object.getString("reasonRefund"));
                                    comp.setIglCustomer(data_object.getString("iglCustomer"));
                                    comp.setLocation(data_object.getString("location"));
                                    comp.setAffectedArea(data_object.getString("affectedArea"));
                                    comp.setTimeSlot(data_object.getString("timeSlot"));
                                    comp.setAppointmentDay(data_object.getString("appointmentDay"));
                                    comp.setAppointmentDate(data_object.getString("appointmentDate"));
                                    comp.setApntDay(data_object.getString("apntDay"));
                                    comp.setReminderDate(data_object.getString("reminderDate"));
                                    comp.setConversinType(data_object.getString("conversinType"));
                                    comp.setPngLpg(data_object.getString("pngLpg"));
                                    comp.setContactNo(data_object.getString("contactNo"));
                                    comp.setMeterType(data_object.getString("meterType"));
                                    comp.setDateReading(data_object.getString("dateReading"));
                                    comp.setMaterialRemoved(data_object.getString("materialRemoved"));
                                    comp.setLastMeterRead(data_object.getString("lastMeterRead"));
                                    comp.setMeterReadingOnDisconnection(data_object.getString("meterReadingOnDisconnection"));
                                    comp.setPeriodFrom(data_object.getString("periodFrom"));
                                    comp.setPeriodTo(data_object.getString("periodTo"));
                                    comp.setMetrReadDate(data_object.getString("metrReadDate"));
                                    comp.setPhysicalMeterNo(data_object.getString("physicalMeterNo"));
                                    comp.setReading(data_object.getString("reading"));
                                    comp.setCriId(data_object.getString("criId"));
                                    comp.setSupervisorId(data_object.getString("supervisorId"));
                                    comp.setTpiId(data_object.getString("tpiId"));
                                    comp.setStatusFlag(data_object.getInt("statusFlag"));
                                    comp.setTpIassigneddate(data_object.getString("tpIassigneddate"));
                                    comp.setCodeGroup(data_object.getString("codeGroup"));
                                    comp.setSupAssignDate(data_object.getString("supAssignDate"));

                                    complist.add(comp);
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
                        comsup_adap = new ComSupList_Adapter(ComplaintSup.this,complist);
                        recyclerView.setAdapter(comsup_adap);
                        comsup_adap.notifyDataSetChanged();
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