package com.fieldmobility.igl.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.fieldmobility.igl.Adapter.TPI_RFC_Pending_Adapter;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.BpDetail;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TPI_RFC_pending_Fragment extends Fragment {

    Context context;
    SharedPrefs sharedPrefs;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TPI_RFC_Pending_Adapter tpi_inspection_adapter;
    EditText editTextSearch;
    TextView header_title;
    TextView  search_bp,text_searchdata;
    View root;
    LinearLayout top_layout;
    static String log = "feasibilitypending";
    ArrayList<BpDetail> bpDetails = new ArrayList<>();
    ArrayList<BpDetail> searchdetails = new ArrayList<>();
    ImageView rfc_filter;
    private Dialog mFilterDialog;
    private RadioGroup radioGroup;
    RelativeLayout rel_nodata;
    Button refresh ;


    public TPI_RFC_pending_Fragment(Activity activity) {
      this.context = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_feasibility_pending_, container, false);
        sharedPrefs = new SharedPrefs(getActivity());
        Layout_ID();
        rel_nodata = root.findViewById(R.id.rel_nodata);
        refresh = root.findViewById(R.id.btnTryAgain);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Feasivility_List();
            }
        });
        return root;
    }


    private void Layout_ID() {
        top_layout=root.findViewById(R.id.top_layout);

        text_searchdata=root.findViewById(R.id.text_searchdata);

        rfc_filter = root.findViewById(R.id.rfc_filter);
        header_title=root.findViewById(R.id.header_title);
        header_title.setText("RFC Pending");
        search_bp = root.findViewById(R.id.search_tpibp);
        search_bp.setText("Search");
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    bpDetails.clear();
                    Feasivility_List();
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        editTextSearch=root.findViewById(R.id.editTextSearch);
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
                showFiltersDialog();
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

        Feasivility_List();
    }

    public void showFiltersDialog() {
        //Toast.makeText(getApplicationContext(),"Filter icon",Toast.LENGTH_SHORT).show();
        mFilterDialog = new Dialog(context);
        mFilterDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mFilterDialog.setCanceledOnTouchOutside(false);
        mFilterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFilterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mFilterDialog.setContentView(getLayoutInflater().inflate(R.layout.dialog_filter_tpi_pending_list,null));
        LinearLayout rl_dialog_title =   mFilterDialog.findViewById(R.id.rl_dialog_title);
        Button btn_applyFilters = (Button) mFilterDialog.findViewById(R.id.btn_applyFilters);
        Button btn_clearAllFilters = (Button) mFilterDialog.findViewById(R.id.btn_clearAllFilters);
        ImageButton ibCancel =   mFilterDialog.findViewById(R.id.ib_create_cancel);
        radioGroup = mFilterDialog.findViewById(R.id.radioGroup);


        rl_dialog_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFilterDialog.dismiss();

            }
        });
        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        refreshRecyclerClaimbyOther();
                        break;
                    case R.id.radioButton_rfcJstart:
                        refreshRecyclerJobstart();
                        break;
                }

                mFilterDialog.dismiss();

            }
        });


        mFilterDialog.show();

    }


    public void refreshRecyclerClaim()
    {
        ArrayList<BpDetail> filterList = new ArrayList<>();
        Log.d(log,"refresh claim");
        for (BpDetail bpNoItem : bpDetails)
        {
            if (bpNoItem.getClaimFlag().equals("0") && !bpNoItem.getJobFlag().equals("1"))
            {
                if (bpNoItem.getRfcTpi().equals(sharedPrefs.getUUID())) {
                    Log.d(log, "claim if = " + bpNoItem.getClaimFlag());
                    filterList.add(bpNoItem);
                }
            }
        }
        text_searchdata.setText("Search Result - "+ filterList.size());
        tpi_inspection_adapter.setData(filterList);


    }
    public void refreshRecyclerClaimbyOther()
    {
        ArrayList<BpDetail> filterList = new ArrayList<>();
        Log.d(log,"refresh claim");
        for (BpDetail bpNoItem : bpDetails)
        {
            if (bpNoItem.getClaimFlag().equals("0")  && !bpNoItem.getRfcTpi().equals(sharedPrefs.getUUID()))
            {

                    Log.d(log, "claim if = " + bpNoItem.getClaimFlag());
                    filterList.add(bpNoItem);

            }
        }
        text_searchdata.setText("Search Result - "+ filterList.size());
        tpi_inspection_adapter.setData(filterList);


    }
    public void refreshRecyclerUnClaim()
    {
        ArrayList<BpDetail>  filterList = new ArrayList<>();
        Log.d(log,"refresh claim");
        for (BpDetail bpNoItem : bpDetails)
        {
            if (bpNoItem.getClaimFlag().equalsIgnoreCase("null") || bpNoItem.getClaimFlag().equalsIgnoreCase("1"))
            {
                Log.d(log,"claim if = "+bpNoItem.getRfcTpi());
                filterList.add(bpNoItem);
            }
        }
        text_searchdata.setText("Search Result - "+ filterList.size());
        tpi_inspection_adapter.setData(filterList);


    }
    public void refreshRecyclerJobstart()
    {
        ArrayList<BpDetail>  filterList = new ArrayList<>();
        for (BpDetail bpNoItem : bpDetails)
        {
            if (bpNoItem.getJobFlag().equalsIgnoreCase("1") && bpNoItem.getRfcTpi().equals(sharedPrefs.getUUID()))
            {
                filterList.add(bpNoItem);
            }
        }
        text_searchdata.setText("Search Result - "+ filterList.size());
        tpi_inspection_adapter.setData(filterList);

    }

    public void  clearFilter()
    {
        Log.d(log,"clear filter = "+bpDetails.size());
        tpi_inspection_adapter.setData(bpDetails);
        text_searchdata.setText("Count - "+ bpDetails.size());
    }

    public void Feasivility_List() {
        bpDetails.clear();
        CommonUtils.startProgressBar(context,"Please wait....");
        String url = Constants.TPI_RFC_PENDING+sharedPrefs.getZone_Code();
        Log.d("tpi",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.TPI_RFC_PENDING+sharedPrefs.getZone_Code(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       CommonUtils.dismissProgressBar(context);
                        try {
                           /* JSONObject jsonObject = new JSONObject(response);
                            Log.d(log,"Response++ = "+jsonObject.toString());
                            Gson gson = new GsonBuilder()
                                    .setPrettyPrinting()
                                    .serializeNulls()
                                    .create();
                            BpListing bpListing = gson.fromJson(response.toString(), BpListing.class);
                             bpDetails = bpListing.getBpDetails();
                            list_count.setText("Count= "+String.valueOf(bpDetails.size()));*/
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
                                bp_no_item.setRfcAdminName(data_object.getString("rfcAdminName"));
                                bp_no_item.setRfcAdminMobileNo(data_object.getString("rfcAdminMobileNo"));
                                bp_no_item.setRfcVendorMobileNo(data_object.getString("rfcVendorMobileNo"));
                                bp_no_item.setRfcVendorName(data_object.getString("rfcVendorName"));
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
                        tpi_inspection_adapter = new TPI_RFC_Pending_Adapter(context,bpDetails,TPI_RFC_pending_Fragment.this);
                        recyclerView.setAdapter(tpi_inspection_adapter);
                        tpi_inspection_adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CommonUtils.dismissProgressBar(context);
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void searchBp_network(String bp) {
        searchdetails.clear();
        CommonUtils.startProgressBar(context,"Please wait....");
        String url = Constants.TPI_RFCPENSEARCH+sharedPrefs.getZone_Code()+"&bp="+bp;
        Log.d("tpi",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CommonUtils.dismissProgressBar(context);
                        try {
                           /* JSONObject jsonObject = new JSONObject(response);
                            Log.d(log,"Response++ = "+jsonObject.toString());
                            Gson gson = new GsonBuilder()
                                    .setPrettyPrinting()
                                    .serializeNulls()
                                    .create();
                            BpListing bpListing = gson.fromJson(response.toString(), BpListing.class);
                             bpDetails = bpListing.getBpDetails();
                            list_count.setText("Count= "+String.valueOf(bpDetails.size()));*/
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
                                    bp_no_item.setZoneCode(data_object.getString("zonecode"));
                                    bp_no_item.setControlRoom(data_object.getString("controlRoom"));
                                    bp_no_item.setIgl_rfcvendor_assigndate(data_object.getString("supervisor_assigndate"));

                                    searchdetails.add(bp_no_item);
                                }
                            }
                            else
                            {
                                CommonUtils.toast_msg(context,message);
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
                        CommonUtils.dismissProgressBar(context);
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }



}