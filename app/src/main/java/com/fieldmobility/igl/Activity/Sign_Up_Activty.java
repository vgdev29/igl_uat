package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sign_Up_Activty  extends Activity {
        //implements View.OnClickListener {

    EditText name, email, password, phone_number;
    Button signup_button_user,signup_button_admin;
    LinearLayout back_button;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    String Latitude,Longitude;
    SharedPrefs sharedPrefs;
    String android_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        Layout_ID();
    }
    private void Layout_ID() {
        /*name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone_number = (EditText) findViewById(R.id.phone_number);
        signup_button_user = (Button) findViewById(R.id.signup_button_user);
        signup_button_admin=(Button)findViewById(R.id.signup_button_admin);
        signup_button_user.setOnClickListener(this);
        signup_button_admin.setOnClickListener(this);
        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button.setOnClickListener(this);*/


    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_button_user:

                if (name.getText().toString().length() == 0) {
                    displayErrorMessage("Name Should Not Empty!");
                } else if (!(email.getText().toString().matches(emailPattern) && email.getText().toString().length() > 0)) {
                    displayErrorMessage("Enter Valid Email Id!");
                } else if (password.getText().toString().length() == 0) {
                    displayErrorMessage("Enter Password");
                }  else if (phone_number.getText().toString().length() == 0) {
                    displayErrorMessage("Enter Phone!");
                } else {
                    /// Do API call Here for Registration and Call LoginActivity after getting success in responce /////
                    Regeseter_User();
                }
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }*/

    private void displayErrorMessage(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }





    public void Regeseter_User() {
        progressDialog.show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.SignUp_User,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //User_Admin_Id="2";
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response",json.toString());
                            if (json.getString("success").equalsIgnoreCase("true")) {

                                JSONObject json_paylode=json.getJSONObject("data");
                               // user_token=json_paylode.getString("token");
                                sharedPrefs.setUUID(json_paylode.getString("id"));
                                sharedPrefs.setName(json_paylode.getString("name"));
                                sharedPrefs.setEmail(json_paylode.getString("email"));
                                sharedPrefs.setMobile(json_paylode.getString("mobile"));
                               // sharedPrefs.setState(json_paylode.getString("Status"));
                                //sharedPrefs.setLoginStatus("true");

                                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Sign_Up_Activty.this, "" + json.getString("Message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Sign_Up_Activty.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("name", name.getText().toString());
                    params.put("mobile", phone_number.getText().toString());
                    params.put("device_type", "android");
                    params.put("device_id", android_id);

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
