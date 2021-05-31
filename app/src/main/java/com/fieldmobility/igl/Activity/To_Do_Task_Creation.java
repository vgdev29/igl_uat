package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.VideoListData;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class To_Do_Task_Creation  extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back,new_assigning_todo;
    VideoListData[] myListData;
    RecyclerView recyclerView;
    Spinner spinner_todo_catagory;
    String todo_catagory,todo_catagory_id;
    ArrayList<String> TODO_CATAGORY_ARRAY;
    ArrayList<String> TODO_CATAGORY_ARRAY_ID;
    MaterialDialog materialDialog;
    TextView start_date_text,time_text;
    Button date_button,time_button,submit_button;
    EditText descreption_edit,bp_no_edit;
    TimePickerDialog pickerDialog_Time;
    DatePickerDialog pickerDialog_Date;
    String am_pm,am_pm1;
    String BP_Number;
    String date_select,Bp_No_Edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_task_creation);
        TODO_CATAGORY_ARRAY=new ArrayList<>();
        TODO_CATAGORY_ARRAY_ID=new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        //getIntent().getStringExtra("Type");
        Layout_ID();
    }

    private void Layout_ID() {

        back =(ImageView)findViewById(R.id.back);
        spinner_todo_catagory=(Spinner)findViewById(R.id.spinner_todo_catagory);
        date_button=findViewById(R.id.date_button);
        time_button=findViewById(R.id.time_button);
        bp_no_edit=findViewById(R.id.bp_no_edit);
        submit_button=findViewById(R.id.submit_button);
        time_text=findViewById(R.id.time_text);
        start_date_text=findViewById(R.id.start_date_text);
        descreption_edit=findViewById(R.id.descreption_edit);
        loadSpinnerData();
        Click_Event();



    }

    private void Click_Event() {
        if(getIntent().getStringExtra("Type").equals("0")){
            Bp_No_Edit=  bp_no_edit.getText().toString();
            bp_no_edit.setVisibility(View.VISIBLE);
        }else {
            Bp_No_Edit= getIntent().getStringExtra("Bp_number");
            bp_no_edit.setVisibility(View.VISIBLE);
            bp_no_edit.setText(Bp_No_Edit);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinner_todo_catagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_todo_catagory.getItemAtPosition(spinner_todo_catagory.getSelectedItemPosition()).toString();
                Log.e("CITY+",country);
                todo_catagory=TODO_CATAGORY_ARRAY.get(position);
                todo_catagory_id=TODO_CATAGORY_ARRAY_ID.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerDialog_Time = new TimePickerDialog(To_Do_Task_Creation.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hour, int minutes) {

                                //int month = monthOfYear + 1;
                                String formattedHours = "" + hour;
                                String formattedMinut = "" + minutes;

                                if(hour < 10){

                                    formattedHours = "0" + hour;
                                }
                                if(minutes < 10){

                                    formattedMinut = "0" + minutes;
                                }
                                Log.e("Date",(formattedHours) + ":" + formattedMinut);
                                if(hour > 12) {
                                    am_pm1 = "PM";
                                    hour = hour - 12;
                                }
                                else
                                {
                                    am_pm1="AM";
                                }
                                time_text.setText(formattedHours + ":" + formattedMinut+" "+am_pm1);
                            }
                        }, hour, minutes, true);

                pickerDialog_Time.show();
            }
        });
        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        time_text.setText(currentTime);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date_select = df.format(c);
        start_date_text.setText(date_select);
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                pickerDialog_Date = new DatePickerDialog(To_Do_Task_Creation.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;

                                if(month < 10){

                                    formattedMonth = "0" + month;
                                }
                                if(dayOfMonth < 10){

                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                Log.e("Date",year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);

                                start_date_text.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                pickerDialog_Date.show();
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                To_DO_List();
            }
        });
    }

    private void loadSpinnerData() {
        TODO_CATAGORY_ARRAY.clear();
        TODO_CATAGORY_ARRAY_ID.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.Get_TODOCATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject jsonObject_Bp_Details=jsonObject.getJSONObject("Bp_Details");
                    JSONArray jsonArray=jsonObject_Bp_Details.getJSONArray("todo");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String category=jsonObject1.getString("category");
                        todo_catagory_id=jsonObject1.getString("id");
                        TODO_CATAGORY_ARRAY.add(category);
                        TODO_CATAGORY_ARRAY_ID.add(todo_catagory_id);
                    }
                    spinner_todo_catagory.setAdapter(new ArrayAdapter<String>(To_Do_Task_Creation.this, android.R.layout.simple_spinner_dropdown_item, TODO_CATAGORY_ARRAY));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    public void To_DO_List() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.TODO_CREATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());


                            Toast.makeText(To_Do_Task_Creation.this, "" + "Successfully", Toast.LENGTH_SHORT).show();

                            finish();
                           // Toast.makeText(To_Do_Task_Creation.this, "" + "Error", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("id", sharedPrefs.getUUID());
                    params.put("bpno",Bp_No_Edit);
                    params.put("date", start_date_text.getText().toString());
                    params.put("time", time_text.getText().toString());
                    params.put("Category_id", todo_catagory_id);
                    params.put("description", descreption_edit.getText().toString());
                    params.put("address", getIntent().getStringExtra("Address"));
                   /* params.put("leave_date", wisper_edittext.getText().toString());
                    params.put("leave_end_date", wisper_edittext.getText().toString());
                    params.put("start_time", wisper_edittext.getText().toString());
                    params.put("end_time", wisper_edittext.getText().toString());
                    params.put("description", wisper_edittext.getText().toString());*/
                } catch (Exception e) {
                }
                return params;
            }
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
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }
}
