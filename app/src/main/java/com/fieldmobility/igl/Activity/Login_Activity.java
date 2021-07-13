package com.fieldmobility.igl.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MainActivity;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Login_Activity extends Activity {


    LinearLayout user_login;
    EditText email, password;
    TextView forgot_password, register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    String User_Id;
    SharedPreferences _prefrence;
    SharedPreferences.Editor _editor;
    String user_token;
    String android_id,date_select;
    JSONObject login_value;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String MESSENGER_INTENT_KEY = "msg-intent-key";
    String log = "login";
    ScrollView login_layout;
    LinearLayout permission_layout;
    Button allow_permission;


    public static final int REQUEST_CODE_PERMISSIONS = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        _prefrence = PreferenceManager.getDefaultSharedPreferences(this);
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        login_layout = findViewById(R.id.login_layout);
        permission_layout = findViewById(R.id.permission_layout);
        allow_permission = findViewById(R.id.permission_allow_button);
        Layout_ID();

        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            login_layout.setVisibility(View.GONE);
            permission_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            login_layout.setVisibility(View.VISIBLE);
            permission_layout.setVisibility(View.GONE);
        }
        allow_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraAndStorage();
            }
        });


    }

    private void Layout_ID() {
        user_login = (LinearLayout) findViewById(R.id.user_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnected(Login_Activity.this)){
                    SendLoginRequest_User();
                }else {
                    Utils.showToast(Login_Activity.this,"Please check internet connection");
                }


            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Forgot_Password.class);
                startActivity(intent);

            }
        });
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date_select = date_format.format(c);
        Log.d("CurrentDate",  date_select);

        statusCheck();
    }

    public void SendLoginRequest_User() {

        progressDialog.show();
        try {
            login_value = new JSONObject();
            login_value.put("username", email.getText().toString());
            login_value.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String login_request = "login_request";
        Log.d("login","login url - "+Constants.Auth_User);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, Constants.Auth_User, login_value,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        Log.e("Response", response.toString());
                        try {
                            user_token = response.getString("jwt");
                            sharedPrefs.setToken(response.getString("jwt"));
                            sharedPrefs.setLoginStatus("true");
                            sharedPrefs.setLoginDate(date_select);

                            User_Authorization();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Login_Activity.this, "" + "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }) {
           /* @Override
            protected Map<String,String> getParams() {
                // something to do here ??

                return params;
            }*/

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // something to do here ??
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Accept", "application/json");
               // headers.put("Authorization", "Bearer " +user_token);
                return headers;
            }*/
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, login_request);
    }

    public void Authoriztion_User() {

        progressDialog.show();
        try {
            login_value = new JSONObject();
            login_value.put("username", email.getText().toString());
            login_value.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String login_request = "login_request";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, Constants.Auth_User, login_value,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        Log.e("Response", response.toString());
                        try {
                            user_token = response.getString("jwt");
                            User_Authorization();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }) {
           /* @Override
            protected Map<String,String> getParams() {
                // something to do here ??

                return params;
            }*/

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // something to do here ??
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Accept", "application/json");
               // headers.put("Authorization", "Bearer " +user_token);
                return headers;
            }*/
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, login_request);
    }

    public void User_Authorization() {
        progressDialog.show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.GET, Constants.Login_User,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        //User_Admin_Id="2";
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.d(log,"Access token"+json.toString());
                            // if (json.getString("status").equalsIgnoreCase("true")) {

                            // JSONObject json_paylode=json.getJSONObject("data");
                            sharedPrefs.setUUID(json.getString("id_user"));
                            sharedPrefs.setEmail(json.getString("userName"));
                            sharedPrefs.setMobile(json.getString("mobileNo"));
                           // sharedPrefs.setCity(json.getString("city"));
                            sharedPrefs.setName(json.getString("first_name"));
                            sharedPrefs.setType_User(json.getString("type_user"));
                            sharedPrefs.setZone_Code(json.getString("zoneId"));
                            Log.e("type_user", json.getString("type_user"));
                           /* Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            ///finish();*/
                          //  Toast.makeText(Login_Activity.this, "" + "Succesfully Login", Toast.LENGTH_SHORT).show();
                            if(sharedPrefs.getType_User().equals("WEB")){
                                sharedPrefs.setLoginStatus("false");

                                Toast.makeText(Login_Activity.this, "" + "Login Failed", Toast.LENGTH_SHORT).show();

                            }else {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                ///finish();
                                Toast.makeText(Login_Activity.this, "" + "Succesfully Login", Toast.LENGTH_SHORT).show();
                            }
                            //sharedPrefs.setState(json_paylode.getString("Status"));


                            // } else {
                            // Toast.makeText(Login_Activity.this, "" + json.getString("Message"), Toast.LENGTH_SHORT).show();
                            //  }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Login_Activity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Cookie", "JSESSIONID=26A74DC8C72D234EFA902A6CE003B81B");

                return headers;
            }


        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void requestCameraAndStorage() {
        Log.d("login","requestcamera");
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                //Toast.makeText(getActivity(), "Camera+Storage granted.", Toast.LENGTH_SHORT).show();
                requestLocation();
            }
        });
    }
    public void requestLocation() {
        Log.d("login","request Loaction");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options().setRationaleDialogTitle("Info").setSettingsDialogTitle("Warning");
        Permissions.check(this, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                requestLocationPermission();
            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // Toast.makeText(getActivity(), "Location denied.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void requestLocationPermission() {
        Log.d("login","request location permission");
        boolean foreground = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (foreground) {
           /* boolean background = ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (background) {
                handleLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
            }*/
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION/*,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION*/}, REQUEST_CODE_PERMISSIONS);
        }
        login_layout.setVisibility(View.VISIBLE);
        permission_layout.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {

            boolean foreground = false, background = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //foreground permission allowed
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        Toast.makeText(this, "Foreground location permission allowed", Toast.LENGTH_SHORT).show();

                        continue;
                    } else {
                        Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

               /* if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        background = true;
                        Toast.makeText(getActivity(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Background location location permission denied", Toast.LENGTH_SHORT).show();
                    }

                }*/
            }

            /*if (foreground) {
                if (background) {
                    handleLocationUpdates();
                } else {
                    handleForegroundLocationUpdates();
                }
            }*/
        }
    }

    private void handleLocationUpdates() {
        //foreground and background

        Toast.makeText(Login_Activity.this,"Start Foreground and Background Location Updates",Toast.LENGTH_SHORT).show();
    }

    private void handleForegroundLocationUpdates() {
        //handleForeground Location Updates
        Toast.makeText(Login_Activity.this,"Start foreground location updates",Toast.LENGTH_SHORT).show();
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
