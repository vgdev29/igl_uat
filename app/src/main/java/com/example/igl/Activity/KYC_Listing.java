package com.example.igl.Activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import com.example.igl.Adapter.KYC_Adapter;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.RecyclerItemClickListener;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class KYC_Listing extends AppCompatActivity implements KYC_Adapter.ContactsAdapterListener{

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
    EditText  editTextSearch;
    TextView list_count;
    LinearLayout header_layout;
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
        Layout_ID();
    }

    private void Layout_ID() {
        header_layout=findViewById(R.id.header_layout);
        header_layout.setVisibility(View.GONE);
        header_title=findViewById(R.id.header_title);
        back =(ImageView)findViewById(R.id.back);
        list_count=findViewById(R.id.list_count);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
         /*recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
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
                Intent intent=new Intent(KYC_Listing.this,Kyc_Foram_Activity.class);
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
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));*/

        editTextSearch=findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              //  bookadapter.getFilter().filter(s.toString());
               // bp_no_array.clear();
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
        Log.e("USERID",sharedPrefs.getUUID());
        bp_no_array.clear();
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BP_No_Get_Listing+"/"+sharedPrefs.getUUID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response++",jsonObject.toString());
                            final JSONObject Bp_Details = jsonObject.getJSONObject("Bp_Details");
                            JSONArray payload=Bp_Details.getJSONArray("users");
                            list_count.setText("Count= "+String.valueOf(payload.length()));
                            for(int i=0; i<=payload.length();i++) {
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
                                bp_no_array.add(bp_no_item);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                       // DesignerToast.Custom(KYC_Listing.this,"Successfully",Gravity.BOTTOM,Toast.LENGTH_SHORT, R.drawable.shape_cornor_radious,15,"#FFFFFF",R.drawable.ic_success, 60, 200);
                         adapter = new KYC_Adapter(KYC_Listing.this,bp_no_array, KYC_Listing.this);
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
    public void onContactSelected(Bp_No_Item contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getBp_number() + ", " + contact.getFirst_name(), Toast.LENGTH_LONG).show();
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