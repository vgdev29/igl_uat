package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class OTP_Forgot_Password extends Activity {

    LinearLayout back_button;
    ProgressDialog progressDialog;
    EditText email,paaword,conf_otp;
    Button change_password;
    SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_forgot_password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        email = (EditText) findViewById(R.id.email);
        paaword = (EditText) findViewById(R.id.paaword);
        conf_otp = (EditText) findViewById(R.id.conf_otp);
        change_password=(Button)findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTP_User();
            }
        });

    }


    public void OTP_User() {
        progressDialog.show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.Update_Password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("otp_responce",json.toString());
                            if (json.getString("success").equalsIgnoreCase("true")) {

                                // JSONObject json_paylode=json.getJSONObject("data");
                                //  user_token=json_paylode.getString("token");
                                // sharedPrefs.setToken(json_paylode.getString("token"));
                                //User_Authorization();
                                Intent intent=new Intent(OTP_Forgot_Password.this,Login_Activity.class);
                                startActivity(intent);
                            } else {
                                //Toast.makeText(Change_Password_Activity.this, "" + json.getString("Message"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(OTP_Forgot_Password.this, "" + "Please Enter Right OTP", Toast.LENGTH_SHORT).show();

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        Toast.makeText(OTP_Forgot_Password.this, "" + error_msg, Toast.LENGTH_SHORT).show();
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
                    params.put("email", email.getText().toString());
                    params.put("password", paaword.getText().toString());
                    params.put("otp", conf_otp.getText().toString());

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
                //headers.put("Authorization", "Bearer " +sharedPrefs.getToken());

                return headers;
            }*/
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }
}