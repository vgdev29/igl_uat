package com.fieldmobility.igl.Activity;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

public class RFC_Connection_Listing extends Activity implements RFC_Adapter.ContactsAdapterListener{
    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back,new_regestration;
    MaterialDialog materialDialog;
    private List<Bp_No_Item> bp_no_array;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RFC_Adapter adapter;
    EditText editTextSearch;
    TextView header_title;
    TextView list_count;
    LinearLayout top_layout;
    static String log = "rfcListing";
    ImageView rfc_filter;
    private Dialog mFilterDialog;
    private RadioGroup radioGroup;

    private int filtersDialogOpenCount = 0;

    private int mSelectedId;
    RelativeLayout rel_nodata;
    Button refresh ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_bp_no_listing);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        back =(ImageView)findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        top_layout=findViewById(R.id.top_layout);
        top_layout.setVisibility(View.VISIBLE);
        list_count=findViewById(R.id.list_count);
        rfc_filter = findViewById(R.id.rfc_filter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(log,"back button");
                finish();
            }
        });
         rel_nodata =  findViewById(R.id.rel_nodata);
        refresh =  findViewById(R.id.btnTryAgain);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bp_No_List();
            }
        });
        bp_no_array=new ArrayList<>();
        header_title=findViewById(R.id.header_title);
        header_title.setText("RFC Listing");
        new_regestration=findViewById(R.id.new_regestration);
        new_regestration.setVisibility(View.GONE);
       /* new_regestration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(RFC_Connection_Listing.this,New_Regestration_Form.class);
                startActivity(intent);
            }
        });*/
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

        rfc_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showFiltersDialog();
            }
        });
        Bp_No_List();

    }

    public void showFiltersDialog() {
        //Toast.makeText(getApplicationContext(),"Filter icon",Toast.LENGTH_SHORT).show();
        mFilterDialog = new Dialog(RFC_Connection_Listing.this);
        mFilterDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mFilterDialog.setCanceledOnTouchOutside(false);
        mFilterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFilterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mFilterDialog.setContentView(getLayoutInflater().inflate(R.layout.dialog_filter_rfc_user_list,null));
        LinearLayout rl_dialog_title =   mFilterDialog.findViewById(R.id.rl_dialog_title);
        Button btn_applyFilters = (Button) mFilterDialog.findViewById(R.id.btn_applyFilters);
        Button btn_clearAllFilters = (Button) mFilterDialog.findViewById(R.id.btn_clearAllFilters);
        ImageButton ibCancel =   mFilterDialog.findViewById(R.id.ib_create_cancel);
        radioGroup = mFilterDialog.findViewById(R.id.radioGroup);

        if (filtersDialogOpenCount!=0)
        {

        }
        rl_dialog_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtersDialogOpenCount = 0;
                mFilterDialog.dismiss();

            }
        });
        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtersDialogOpenCount = 0;
                mFilterDialog.dismiss();
            }
        });

        btn_clearAllFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               radioGroup.clearCheck();
               clearFilter();
               mFilterDialog.dismiss();
            }
        });

        btn_applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checkedId = radioGroup.getCheckedRadioButtonId();
                Log.d(log,"checked id = "+checkedId+" res id = "+R.id.radioButton_rfcClaim);
                switch (radioGroup.getCheckedRadioButtonId())
                {
                    case R.id.radioButton_rfcClaim:
                        refreshRecyclerClaim();
                        break;
                    case R.id.radioButton_rfcUnClaim:
                        refreshRecyclerUnClaim();
                        break;
                    case R.id.radioButton_rfcJnStart:
                        refreshRecyclerUnClaim();
                        break;
                    case R.id.radioButton_rfcJstart:
                        refreshRecyclerJobstart();
                        break;
                }

                mFilterDialog.dismiss();
                filtersDialogOpenCount = 0;
            }
        });


        mFilterDialog.show();
        filtersDialogOpenCount = 1;
    }

    public void refreshRecyclerClaim()
    {
        List<Bp_No_Item> filterList = new ArrayList<>();
        Log.d(log,"refresh claim");
        for (Bp_No_Item bpNoItem : bp_no_array)
        {
            if (bpNoItem.getClaimFlag().equals("0") && !bpNoItem.getJobFlag().equals("1"))
            {
                Log.d(log,"claim if = "+bpNoItem.getClaimFlag());
                filterList.add(bpNoItem);
            }
        }
        list_count.setText("Count\n"+ filterList.size());
        adapter.setData(filterList);
        adapter.notifyDataSetChanged();

    }
    public void refreshRecyclerUnClaim()
    {
        List<Bp_No_Item> filterList = new ArrayList<>();
        Log.d(log,"refresh claim");
        for (Bp_No_Item bpNoItem : bp_no_array)
        {
            if (bpNoItem.getClaimFlag().equals("null"))
            {
                Log.d(log,"claim if = "+bpNoItem.getRfcTpi());
                filterList.add(bpNoItem);
            }
        }
        list_count.setText("Count\n"+ filterList.size());
        adapter.setData(filterList);
        adapter.notifyDataSetChanged();

    }
    public void refreshRecyclerJobstart()
    {
        List<Bp_No_Item> filterList = new ArrayList<>();
        for (Bp_No_Item bpNoItem : bp_no_array)
        {
            if (bpNoItem.getJobFlag().equalsIgnoreCase("1"))
            {
                filterList.add(bpNoItem);
            }
        }
        list_count.setText("Count\n"+ filterList.size());
        adapter.setData(filterList);

    }

    public void  clearFilter()
    {
        Log.d(log,"clear filter = "+bp_no_array.size());
        adapter.setData(bp_no_array);
        list_count.setText("Count\n"+ bp_no_array.size());
    }



    public void Bp_No_List() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.RFC_LISTING_GET+sharedPrefs.getUUID();
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
                                bp_no_item.setRfcTpi(data_object.getString("rfcTpi"));
                                bp_no_item.setRfcVendor(data_object.getString("rfcVendor"));
                                bp_no_item.setJobFlag(data_object.getString("jobFlag"));
                                bp_no_item.setClaimFlag(data_object.getString("claimFlag"));
                                bp_no_item.setVendorMobileNo(data_object.getString("vendorMobileNo"));
                                bp_no_item.setFesabilityTpimobileNo(data_object.getString("fesabilityTpimobileNo"));
                                bp_no_item.setRfcvendorname(data_object.getString("rfctpiname"));
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
                        adapter = new RFC_Adapter(RFC_Connection_Listing.this,bp_no_array,RFC_Connection_Listing.this);
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


    @Override
    public void onContactSelected(Bp_No_Item contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getBp_number() + ", " + contact.getFirst_name(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
      //  this.recreate();

    }

    /*@Override
    protected void onRestart() {
        super.onRestart();
        Bp_No_List();
    }*/
}