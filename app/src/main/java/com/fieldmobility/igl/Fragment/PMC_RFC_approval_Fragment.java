package com.fieldmobility.igl.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.fieldmobility.igl.Adapter.TPI_RFC_Approval_Adapter;
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


public class PMC_RFC_approval_Fragment extends Fragment {

    Context context;
    SharedPrefs sharedPrefs;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TPI_RFC_Approval_Adapter tpi_inspection_adapter;
    EditText editTextSearch;
    TextView header_title;
    TextView list_count;
    View root;
    LinearLayout top_layout;
    static String log = "pmcrfcapproval";
    ArrayList<BpDetail> bpDetails = new ArrayList<>();
    public PMC_RFC_approval_Fragment(Activity activity) {
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
        return root;
    }


    private void Layout_ID() {
        top_layout=root.findViewById(R.id.top_layout);
        top_layout.setVisibility(View.GONE);
        list_count=root.findViewById(R.id.list_count);

        header_title=root.findViewById(R.id.header_title);
        header_title.setText("PMC");


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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Feasivility_List();
    }

    public void Feasivility_List() {
        bpDetails.clear();
        CommonUtils.startProgressBar(context,"Please wait....");
        String url = Constants.TPI_RFC_APPROVAl + sharedPrefs.getUUID();
       //String url = Constants.TPI_RFC_APPROVAl;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CommonUtils.dismissProgressBar(context);
                        try {
                           /* JSONObject jsonObject = new JSONObject(response);
                            Log.d(log,"Response++ = "+jsonObject.toString());
                           BpListing bpListing = new Gson().fromJson(response.toString(), BpListing.class);
                            bpDetails = bpListing.getBpDetails();
                            list_count.setText("Count= "+String.valueOf(bpDetails.size()));*/
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response++",jsonObject.toString());
                            final JSONArray Bp_Details = jsonObject.getJSONArray("Bp_Details");
                            list_count.setText("Count\n"+String.valueOf(Bp_Details.length()));
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
                                bp_no_item.setZoneCode(data_object.getString("zonecode"));
                                bp_no_item.setControlRoom(data_object.getString("controlRoom"));
                                bp_no_item.setIgl_rfcvendor_assigndate(data_object.getString("supervisor_assigndate"));
                                bpDetails.add(bp_no_item);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        tpi_inspection_adapter = new TPI_RFC_Approval_Adapter(context,bpDetails, PMC_RFC_approval_Fragment.this);
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

    @Override
    public void onResume() {
        super.onResume();
        Log.d(log,"onResume");
        if (bpDetails.size()!=0)
        {
            Feasivility_List();
            Log.d(log,"onResume called if");
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(log,"onAttach");
        //Feasivility_List();
    }
}