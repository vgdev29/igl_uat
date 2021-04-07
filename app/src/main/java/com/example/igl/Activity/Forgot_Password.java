package com.example.igl.Activity;

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
import com.example.igl.Helper.AppController;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Forgot_Password extends Activity {

    LinearLayout back_button;
    EditText email;
    ProgressDialog progressDialog;
    Button use_login;
    SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        use_login=(Button)findViewById(R.id.use_login);
        use_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendLoginRequest_User();
            }
        });
        email=(EditText)findViewById(R.id.email);
    }


    public void SendLoginRequest_User() {
        progressDialog.show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.Forgot_Password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response",json.toString());
                            if (json.getString("success").equalsIgnoreCase("true")) {

                                //  JSONObject json_paylode=json.getJSONObject("data");
                                //  user_token=json_paylode.getString("token");
                                // sharedPrefs.setToken(json_paylode.getString("token"));
                                //User_Authorization();
                                Intent intent=new Intent(Forgot_Password.this,OTP_Forgot_Password.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Forgot_Password.this, "" + "enter valid otp", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Forgot_Password.this, "" + error_msg, Toast.LENGTH_SHORT).show();
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


                } catch (Exception e) {
                }
                return params;
            }


        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }
}
