package com.example.igl.Activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import com.example.igl.Adapter.To_DO_Task_Adapter;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.To_Do_Item;
import com.example.igl.R;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.vdx.designertoast.DesignerToast;

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

public class To_DoList_Activity extends Activity implements DatePickerListener {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back,new_assigning_todo;
    RecyclerView recyclerView;
    MaterialDialog materialDialog;
    private List<To_Do_Item> todo_lisitng_array;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String date_select;
    To_DO_Task_Adapter adapter;
    String Message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_dolist_layout);
        todo_lisitng_array=new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        //
        Layout_ID();
    }

    private void Layout_ID() {
        back =(ImageView)findViewById(R.id.back);
        new_assigning_todo =(ImageView)findViewById(R.id.new_assigning_todo);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    todo_lisitng_array.clear();
                    TO_DO_LIST();
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
         recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Click_Event();
    }

    private void Click_Event() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new_assigning_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(To_DoList_Activity.this,To_Do_Task_Creation.class);
                intent.putExtra("Bp_number"," ");
                intent.putExtra("Address","  ");
                intent.putExtra("Type",getIntent().getStringExtra("Type"));
                startActivity(intent);
            }
        });
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date_select = df.format(c);
        Log.d("CurrentDate",  date_select);
        HorizontalPicker picker= (HorizontalPicker) findViewById(R.id.datePicker);
        picker.setListener(this)
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
        picker.setBackgroundColor(Color.WHITE);
        picker.setDate(new DateTime());

        TO_DO_LIST();
    }
    @Override
    public void onDateSelected(DateTime dateSelected) {
        Log.i("HorizontalPicker","Fecha seleccionada="+dateSelected.toString());

        String[] separated = dateSelected.toString().split("T");
        date_select= separated[0];
        String stemp=separated[1];
        try {
            todo_lisitng_array.clear();
            adapter.notifyDataSetChanged();
            TO_DO_LIST();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void TO_DO_LIST() {
        Log.e("userid",sharedPrefs.getUUID());
        todo_lisitng_array.clear();
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GET_TODO_LIST+date_select+"/"+sharedPrefs.getUUID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response++",jsonObject.toString());
                            final JSONObject Bp_Details = jsonObject.getJSONObject("TODOList");
                              Message=jsonObject.getString("Message");
                              JSONArray payload=Bp_Details.getJSONArray("create");
                              for(int i=0; i<=payload.length();i++) {
                                JSONObject data_object=payload.getJSONObject(i);
                                To_Do_Item bp_no_item = new To_Do_Item();
                                bp_no_item.setBp(data_object.getString("bp"));
                                bp_no_item.setDate(data_object.getString("date"));
                                bp_no_item.setTime(data_object.getString("time"));
                                bp_no_item.setDescription(data_object.getString("description"));
                                bp_no_item.setCategory_name(data_object.getString("category_name"));
                                bp_no_item.setIgl_address(data_object.getString("igl_address"));
                                bp_no_item.setId(data_object.getString("id"));
                                bp_no_item.setTodo_id(data_object.getString("todo_id"));
                                todo_lisitng_array.add(bp_no_item);
                            }
                            DesignerToast.Custom(To_DoList_Activity.this,Message, Gravity.BOTTOM, Toast.LENGTH_SHORT, R.drawable.shape_cornor_radious,13,"#FFFFFF",R.drawable.ic_success, 50, 200);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        adapter = new To_DO_Task_Adapter(To_DoList_Activity.this,todo_lisitng_array);
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
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
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
            /*@Override
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
        try {
            todo_lisitng_array.clear();
            adapter.notifyDataSetChanged();
            TO_DO_LIST();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
