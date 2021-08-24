package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.fieldmobility.igl.Adapter.New_BP_NO_Adapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BP_No_Resubmition_Listing extends Activity implements New_BP_NO_Adapter.ContactsAdapterListener {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView rfc_filter;
    //    FloatingActionButton new_regestration;
    MaterialDialog materialDialog;
    private List<Bp_No_Item> bp_no_array;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    New_BP_NO_Adapter adapter;
    EditText editTextSearch;
    TextView list_count;

    LinearLayout top_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_bp_no_listing);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        Layout_ID();
    }

    private void Layout_ID() {

        list_count = findViewById(R.id.list_count);
        rfc_filter = findViewById(R.id.rfc_filter);
        rfc_filter.setVisibility(View.GONE);
        top_layout = findViewById(R.id.top_layout);
        top_layout.setVisibility(View.GONE);
        bp_no_array = new ArrayList<>();

        findViewById(R.id.new_regestration).setVisibility(View.GONE);
//        new_regestration.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(BP_No_Resubmition_Listing.this, BP_Creation_Form.class);
//                startActivity(intent);
//            }
//        });
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       /* recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String First_name = bp_no_array.get(position).getFirst_name();
                String Middle_name = bp_no_array.get(position).getMiddle_name();
                String Last_name = bp_no_array.get(position).getLast_name();
                String Mobile_number = bp_no_array.get(position).getMobile_number();
                String Email_id = bp_no_array.get(position).getEmail_id();
                String Aadhaar_number = bp_no_array.get(position).getAadhaar_number();
                String City_region = bp_no_array.get(position).getCity_region();
                String Area = bp_no_array.get(position).getArea();
                String Society = bp_no_array.get(position).getSociety();
                String Landmark = bp_no_array.get(position).getLandmark();
                String House_type = bp_no_array.get(position).getHouse_type();
                String House_no = bp_no_array.get(position).getHouse_no();
                String Block_qtr_tower_wing = bp_no_array.get(position).getBlock_qtr_tower_wing();
                String Floor = bp_no_array.get(position).getFloor();
                String Street_gali_road = bp_no_array.get(position).getStreet_gali_road();
                String Pincode = bp_no_array.get(position).getPincode();
                String Customer_type = bp_no_array.get(position).getCustomer_type();
                String Lpg_company = bp_no_array.get(position).getLpg_company();
                String Bp_number = bp_no_array.get(position).getBp_number();
                String Bp_date = bp_no_array.get(position).getBp_date();
                String IGL_Status = bp_no_array.get(position).getIgl_status();

                String lpg_distributor = bp_no_array.get(position).getLpg_distributor();
                String lpg_conNo = bp_no_array.get(position).getLpg_conNo();
                String Unique_lpg_Id = bp_no_array.get(position).getUnique_lpg_Id();
                String lead_no = bp_no_array.get(position).getLead_no();
                String ownerName = bp_no_array.get(position).getOwnerName();
                String igl_code_group = bp_no_array.get(position).getIgl_code_group();

                Intent intent=new Intent(New_BP_No_Listing.this,New_Regestration_DetailPage.class);
                intent.putExtra("First_name",First_name);
                intent.putExtra("Middle_name",Middle_name);
                intent.putExtra("Last_name",Last_name);
                intent.putExtra("Mobile_number",Mobile_number);
                intent.putExtra("Email_id",Email_id);
                intent.putExtra("Aadhaar_number",Aadhaar_number);
                intent.putExtra("City_region",City_region);
                intent.putExtra("Area",Area);
                intent.putExtra("Society",Society);
                intent.putExtra("Landmark",Landmark);
                intent.putExtra("House_type",House_type);
                intent.putExtra("House_no",House_no);
                intent.putExtra("Block_qtr_tower_wing",Block_qtr_tower_wing);
                intent.putExtra("Floor",Floor);
                intent.putExtra("Street_gali_road",Street_gali_road);
                intent.putExtra("Pincode",Pincode);
                intent.putExtra("Customer_type",Customer_type);
                intent.putExtra("Lpg_company",Lpg_company);
                intent.putExtra("Bp_number",Bp_number);
                intent.putExtra("Bp_date",Bp_date);
                intent.putExtra("IGL_Status",IGL_Status);

                intent.putExtra("lpg_distributor",lpg_distributor);
                intent.putExtra("lpg_conNo",lpg_conNo);
                intent.putExtra("Unique_lpg_Id",Unique_lpg_Id);
                intent.putExtra("lead_no",lead_no);
                intent.putExtra("ownerName",ownerName);
                intent.putExtra("igl_code_group",igl_code_group);

                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));*/

        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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

    /* @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
             moveTaskToBack(false);
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }*/
    public void Bp_No_List() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        Log.d("Bpcreation", "url = " + Constants.BP_No_Listing + "/" + sharedPrefs.getUUID());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BP_No_Resubmition_Listing + sharedPrefs.getUUID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        Log.d("Bpcreation", "response = " + response.toString());
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response++", jsonObject.toString());
                            //   final JSONObject Bp_Details = jsonObject.getJSONObject("Bp_Details");
                            //   Log.e("Response++",Bp_Details.toString());
                            JSONArray payload = jsonObject.getJSONArray("Bp_Details");
                            list_count.setText("Count= " + String.valueOf(payload.length()));
                            for (int i = 0; i < payload.length(); i++) {
                                JSONObject data_object = payload.getJSONObject(i);
                                Bp_No_Item bp_no_item = new Bp_No_Item();
                                bp_no_item.setFirst_name(data_object.getString("igl_first_name"));
                                bp_no_item.setMiddle_name(data_object.getString("igl_middle_name"));
                                bp_no_item.setLast_name(data_object.getString("igl_last_name"));
                                bp_no_item.setMobile_number(data_object.getString("igl_mobile_no"));
                                bp_no_item.setEmail_id(data_object.getString("igl_email_id"));
                                bp_no_item.setAadhaar_number(data_object.getString("igl_aadhaar_no"));
                                bp_no_item.setCity_region(data_object.getString("igl_city_region"));
                                bp_no_item.setArea(data_object.getString("igl_area"));
                                bp_no_item.setSociety(data_object.getString("igl_society"));
                                bp_no_item.setLandmark(data_object.getString("igl_landmark"));
                                bp_no_item.setHouse_type(data_object.getString("igl_house_type"));
                                bp_no_item.setHouse_no(data_object.getString("igl_house_no"));
                                bp_no_item.setBlock_qtr_tower_wing(data_object.getString("igl_Block_Qtr_Tower_Wing"));
                                bp_no_item.setFloor(data_object.getString("igl_floor"));
                                bp_no_item.setStreet_gali_road(data_object.getString("igl_street_gali_road"));
                                bp_no_item.setPincode(data_object.getString("igl_pincode"));
                                bp_no_item.setCustomer_type(data_object.getString("igl_consumer_type"));
                                bp_no_item.setLpg_company(data_object.getString("igl_lpg_company"));
                                bp_no_item.setBp_number(data_object.getString("igl_bp_creation_no"));
                                bp_no_item.setBp_date(data_object.getString("igl_bp_creation_date"));
                                bp_no_item.setIgl_status(data_object.getString("igl_status"));

                                bp_no_item.setLpg_distributor(data_object.getString("id_lpg_distributor"));
                                bp_no_item.setLpg_conNo(data_object.getString("igl_lpg_consumer_no"));
                                bp_no_item.setUnique_lpg_Id(data_object.getString("igl_unique_lpg_id"));
                                bp_no_item.setOwnerName(data_object.getString("owner_name"));
                                bp_no_item.setChequeNo(data_object.getString("cheque_no"));
                                bp_no_item.setChequeDate(data_object.getString("cheque_date"));
                                bp_no_item.setLead_no(data_object.getString("igl_lead_no"));
                                bp_no_item.setDrawnOn(data_object.getString("drawn_on"));
                                bp_no_item.setAmount(data_object.getString("amount"));
                                bp_no_item.setAddressProof(data_object.getString("igl_address_type"));
                                bp_no_item.setIdproof(data_object.getString("id_proof_type"));
                                bp_no_item.setIgl_code_group(data_object.getString("igl_code_group"));
                                bp_no_array.add(bp_no_item);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        //  DesignerToast.Custom(New_BP_No_Listing.this,"Successfully",Gravity.BOTTOM,Toast.LENGTH_SHORT, R.drawable.shape_cornor_radious,15,"#FFFFFF",R.drawable.ic_success, 60, 200);
                        //DesignerToast.Success(New_BP_No_Listing.this, "Successfully", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        adapter = new New_BP_NO_Adapter(BP_No_Resubmition_Listing.this, bp_no_array, BP_No_Resubmition_Listing.this, true);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                        Log.d("Bpcreation", "on error = " + error.getMessage());
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

           /* @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("id", sharedPrefs.getUUID());
                } catch (Exception e) {
                }
                return params;
            }*/
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                //headers.put(" Content-Type", "text/html");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " +sharedPrefs.getToken());

                return headers;
            }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        bp_no_array.clear();
        adapter.notifyDataSetChanged();
        Bp_No_List();
    }

    @Override
    public void onContactSelected(Bp_No_Item contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getBp_number() + ", " + contact.getFirst_name(), Toast.LENGTH_LONG).show();

    }
}