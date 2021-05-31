package com.fieldmobility.igl.Activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.fieldmobility.igl.Adapter.KYC_Adapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class KYC_Verification_Activity extends AppCompatActivity{

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    MaterialDialog materialDialog;
    private List<Bp_No_Item> bp_no_array;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    KYC_Adapter adapter;
    private SearchView searchView;
    TextView header_title;
    EditText editTextSearch;
    TextView list_count;
    LinearLayout header_layout;
    String log = "kyclisting";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_recyclerview);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        // toolbar fancy stuff
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        /*getActionBar().setTitle(R.string.toolbar_title);*/
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        back =(ImageView)findViewById(R.id.back);
         back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Layout_ID();
    }

    private void Layout_ID() {
        header_layout=findViewById(R.id.header_layout);
        header_layout.setVisibility(View.GONE);
        header_title=findViewById(R.id.header_title);

        list_count=findViewById(R.id.list_count);

        header_title.setText("KYC Listing");
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
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Bp_No_List();
    }


    public void Bp_No_List() {
        Log.d(log,"url verification = "+Constants.BP_No_Get_Listing+"/"+sharedPrefs.getUUID());

        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        Log.d(log,"url = "+ Constants.BP_No_Get_Listing+"/"+sharedPrefs.getUUID());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BP_No_Get_Listing+"/"+sharedPrefs.getUUID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response++",jsonObject.toString());
                            final JSONArray Bp_Details = jsonObject.getJSONArray("Bp_Details");
                            // JSONArray payload=Bp_Details.getJSONArray("users");

                            for(int i=0; i<Bp_Details.length();i++) {
                                JSONObject data_object=Bp_Details.getJSONObject(i);
                                if (data_object.getString("igl_status").equalsIgnoreCase("3")) {
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
                                    bp_no_item.setAddress_image(data_object.getString("addressCard"));
                                    bp_no_item.setId_image(data_object.getString("adhaarCard"));
                                    bp_no_item.setCustomer_image(data_object.getString("document"));
                                    bp_no_item.setOwner_image(data_object.getString("document1"));
                                    bp_no_item.setMeterNo(data_object.getString("meterNo"));
                                    bp_no_array.add(bp_no_item);
                                }
                            }
                            list_count.setText("Count= "+ bp_no_array.size());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        // DesignerToast.Custom(KYC_Listing.this,"Successfully",Gravity.BOTTOM,Toast.LENGTH_SHORT, R.drawable.shape_cornor_radious,15,"#FFFFFF",R.drawable.ic_success, 60, 200);
                        adapter = new KYC_Adapter(KYC_Verification_Activity.this,bp_no_array);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                    }
                }) {


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
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        bp_no_array.clear();
        adapter.notifyDataSetChanged();

        Bp_No_List();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}