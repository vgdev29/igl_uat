package com.fieldmobility.igl.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.fieldmobility.igl.Adapter.TPI_Fesivility_Adapter;
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
import java.util.Map;


public class TPI_Feasibility_pending_Fragment extends Fragment {

    Context context;
    SharedPrefs sharedPrefs;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TPI_Fesivility_Adapter tpi_inspection_adapter;
    EditText editTextSearch;
    TextView header_title;
    TextView list_count;
    View root;
    LinearLayout top_layout;
    static String log = "feasibilitypending";
    ArrayList<Bp_No_Item> bpDetails = new ArrayList<>();
    private MaterialDialog materialDialog;
    ImageView rfc_filter;
    private Dialog mFilterDialog;
    private RadioGroup radioGroup;
    RelativeLayout rel_nodata;
    Button refresh ;


    public TPI_Feasibility_pending_Fragment(Activity activity) {
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
        top_layout.setVisibility(View.GONE);
        list_count=root.findViewById(R.id.list_count);
        rfc_filter = root.findViewById(R.id.rfc_filter);
        rfc_filter.setVisibility(View.GONE);
        header_title=root.findViewById(R.id.header_title);
        header_title.setText("TPI");

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
                if (tpi_inspection_adapter!=null) {
                    tpi_inspection_adapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Feasivility_List();
    }

    public void Feasivility_List() {
        bpDetails.clear();
      //  CommonUtils.startProgressBar(context,"Loading....");
        materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.TPI_LISTING_GET+sharedPrefs.getUUID();
        Log.d(log,"url = "+url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.TPI_LISTING_GET+sharedPrefs.getUUID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                 //      CommonUtils.dismissProgressBar(context);
                        try {
                           /* JSONObject jsonObject = new JSONObject(response);
                            Log.d(log,"Response++ = "+jsonObject.toString());
                           BpListing bpListing = new Gson().fromJson(response.toString(), BpListing.class);
                            bpDetails = bpListing.getBpDetails();
                            list_count.setText("Count= "+String.valueOf(bpDetails.size()));*/
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response++",jsonObject.toString());
                            final String status = jsonObject.getString("status");
                            if (status.equals("200")) {
                                rel_nodata.setVisibility(View.GONE);
                                final JSONObject Bp_Details = jsonObject.getJSONObject("Bp_Details");
                                JSONArray payload = Bp_Details.getJSONArray("users");
                                list_count.setText("Count= " + String.valueOf(payload.length()));
                                for (int i = 0; i < payload.length(); i++) {
                                    JSONObject data_object = payload.getJSONObject(i);
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
                                    bp_no_item.setFesabilityDate(data_object.getString("assigndate"));
                                    bp_no_item.setControlRoom(data_object.getString("controlRoom"));
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
                        tpi_inspection_adapter = new TPI_Fesivility_Adapter(context,bpDetails);
                        recyclerView.setAdapter(tpi_inspection_adapter);
                        tpi_inspection_adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                     // CommonUtils.dismissProgressBar(context);
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