package com.example.igl.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.igl.Helper.AppController;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Leave_Approval_Activity extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back,new_assigning_todo;
    String todo_catagory,todo_catagory_id;
    ArrayList<String> TODO_CATAGORY_ARRAY;
    ArrayList<String> TODO_CATAGORY_ARRAY_ID;
    MaterialDialog materialDialog;
    TextView start_date_text,end_date_text,start_time_text,end_time_text,view_list;
    Button start_date_button,end_date_button,start_time_button,end_time_button,submit_button;
    EditText descreption_edit;
    TimePickerDialog pickerDialog_Time;
    DatePickerDialog pickerDialog_Date;
    String am_pm,am_pm1;
    String BP_Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_approval_layout);
        TODO_CATAGORY_ARRAY=new ArrayList<>();
        TODO_CATAGORY_ARRAY_ID=new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        Layout_ID();
    }

    private void Layout_ID() {

        back =(ImageView)findViewById(R.id.back);
       // new_assigning_todo =(ImageView)findViewById(R.id.new_assigning_todo);
        start_date_button=findViewById(R.id.start_date_button);
        end_date_button=findViewById(R.id.end_date_button);
        start_time_button=findViewById(R.id.start_time_button);
        end_time_button=findViewById(R.id.end_time_button);
        submit_button=findViewById(R.id.submit_button);
        view_list=findViewById(R.id.view_list);
        start_date_text=findViewById(R.id.start_date_text);
        end_date_text=findViewById(R.id.end_date_text);
        start_time_text=findViewById(R.id.start_time_text);
        end_time_text=findViewById(R.id.end_time_text);
        descreption_edit=findViewById(R.id.descreption_edit);
        Click_Event();

    }

    private void Click_Event() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        view_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Leave_Approval_Activity.this,View_Leave_LIsting_Activity.class);
                startActivity(intent);
            }
        });

        start_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerDialog_Time = new TimePickerDialog(Leave_Approval_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hour, int minutes) {
                                if(hour > 12) {
                                    am_pm1 = "PM";
                                    hour = hour - 12;
                                }
                                else
                                {
                                    am_pm1="AM";
                                }
                                start_time_text.setText(hour + ":" + minutes+" "+am_pm1);
                            }
                        }, hour, minutes, true);

                pickerDialog_Time.show();
            }
        });
        end_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerDialog_Time = new TimePickerDialog(Leave_Approval_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hour, int minutes) {
                                if(hour > 12) {
                                    am_pm1 = "PM";
                                    hour = hour - 12;
                                }
                                else
                                {
                                    am_pm1="AM";
                                }
                                end_time_text.setText(hour + ":" + minutes+" "+am_pm1);
                            }
                        }, hour, minutes, true);

                pickerDialog_Time.show();
            }
        });

        start_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                pickerDialog_Date = new DatePickerDialog(Leave_Approval_Activity.this,
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

        end_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                pickerDialog_Date = new DatePickerDialog(Leave_Approval_Activity.this,
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
                                end_date_text.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
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

    public void To_DO_List() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.APPLY_LEAVE+sharedPrefs.getUUID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());


                            Toast.makeText(Leave_Approval_Activity.this, "" + "Successfully", Toast.LENGTH_SHORT).show();
                            finish();

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
                   // params.put("id", sharedPrefs.getUUID());
                    params.put("leave_date",start_date_text.getText().toString());
                    params.put("leave_end_date", end_date_text.getText().toString());
                    params.put("start_time", start_time_text.getText().toString());
                    params.put("end_time", end_time_text.getText().toString());
                    params.put("description", descreption_edit.getText().toString());

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
