package com.example.igl.Activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.igl.Adapter.Bp_no_Adapter;
import com.example.igl.Adapter.View_Leave_Adapter;
import com.example.igl.Adapter.View_Leave_Item;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.RecyclerItemClickListener;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class View_Leave_LIsting_Activity extends AppCompatActivity implements View_Leave_Adapter.ContactsAdapterListener, DatePickerListener {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    MaterialDialog materialDialog;
    private List<View_Leave_Item> bp_no_array;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View_Leave_Adapter adapter;
    private SearchView searchView;
    EditText editTextSearch;
    ImageView new_leave;
    String date_select;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_leave_list_recyclerview);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        Layout_ID();
    }

    private void Layout_ID() {
        back =(ImageView)findViewById(R.id.back);
        new_leave=(ImageView)findViewById(R.id.new_leave);
        new_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(View_Leave_LIsting_Activity.this,Leave_Approval_Activity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

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

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date_select = df.format(c);
        Log.d("CurrentDate",  date_select);
        HorizontalPicker datePicker_leave= (HorizontalPicker) findViewById(R.id.datePicker_leave);
        datePicker_leave.setListener(this)
                .setDays(360)
                .setOffset(30)
                .setDateSelectedColor(Color.RED)
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(Color.RED)
                .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateBackgroundColor(Color.GRAY)
                .setUnselectedDayTextColor(Color.DKGRAY)
                .setDayOfWeekTextColor(Color.DKGRAY )
                .setUnselectedDayTextColor(getResources().getColor(R.color.primaryTextColor))
                .showTodayButton(false)
                .init();
        datePicker_leave.setBackgroundColor(Color.WHITE);
        datePicker_leave.setDate(new DateTime());

        Bp_No_List();
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        Log.i("HorizontalPicker","Fecha seleccionada="+dateSelected.toString());

        String[] separated = dateSelected.toString().split("T");
        date_select= separated[0];
        String stemp=separated[1];
        try {
            bp_no_array.clear();
            adapter.notifyDataSetChanged();
            Bp_No_List();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void Bp_No_List() {
        Log.e("USERID",sharedPrefs.getUUID());

        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GET_LEAVE_LIST+sharedPrefs.getUUID()+"?date="+date_select,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response++",jsonObject.toString());
                            final JSONObject Bp_Details = jsonObject.getJSONObject("List of leaves");
                            JSONArray payload=Bp_Details.getJSONArray("apply");
                            for(int i=0; i<=payload.length();i++) {
                                JSONObject data_object=payload.getJSONObject(i);
                                View_Leave_Item bp_no_item = new View_Leave_Item();
                                bp_no_item.setLeave_from_date(data_object.getString("leave_from_date"));
                                bp_no_item.setLeave_to_date(data_object.getString("leave_to_date"));
                                bp_no_item.setStart_time(data_object.getString("start_time"));
                                bp_no_item.setEnd_time(data_object.getString("end_time"));
                                bp_no_item.setDescription(data_object.getString("description"));
                                bp_no_item.setStatus(data_object.getString("status"));

                               bp_no_array.add(bp_no_item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        // DesignerToast.Custom(KYC_Listing.this,"Successfully",Gravity.BOTTOM,Toast.LENGTH_SHORT, R.drawable.shape_cornor_radious,15,"#FFFFFF",R.drawable.ic_success, 60, 200);
                        adapter = new View_Leave_Adapter(View_Leave_LIsting_Activity.this,bp_no_array,View_Leave_LIsting_Activity.this);
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
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        try {
            bp_no_array.clear();
            adapter.notifyDataSetChanged();

            Bp_No_List();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onContactSelected(Bp_No_Item contact) {
      //  Toast.makeText(getApplicationContext(), "Selected: " + contact.getBp_number() + ", " + contact.getFirst_name(), Toast.LENGTH_LONG).show();
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