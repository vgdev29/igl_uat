package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.CustomRequest;
import com.fieldmobility.igl.Helper.GenericTextWatcher;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MainActivity;
import com.fieldmobility.igl.Model.Otp;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpActivity extends AppCompatActivity {

    Otp otpbean;
    TextView otp_dialog;
    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four;
    String otp_get;
    Button submit;
    ProgressDialog otp_submit;
    SharedPrefs sharedPrefs;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        sharedPrefs = new SharedPrefs(this);
        progressBar = findViewById(R.id.progressBar_cyclic);
        otp_dialog = findViewById(R.id.otp_head);
        otp_textbox_one = findViewById(R.id.otp_edit_box1);
        otp_textbox_two = findViewById(R.id.otp_edit_box2);
        otp_textbox_three = findViewById(R.id.otp_edit_box3);
        otp_textbox_four = findViewById(R.id.otp_edit_box4);
        submit = findViewById(R.id.otp_submit);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        if ( sharedPrefs.getMobile()!=null);
        {
            otpbean = (Otp) getIntent().getSerializableExtra("otp");
            otp_dialog.setText("Enter verification code recieved on "+otpbean.getMobileNo());
        }

        EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four};
        otp_textbox_one.addTextChangedListener(new GenericTextWatcher(otp_textbox_one, edit));

        otp_textbox_two.addTextChangedListener(new GenericTextWatcher(otp_textbox_two, edit));
        otp_textbox_three.addTextChangedListener(new GenericTextWatcher(otp_textbox_three, edit));
        otp_textbox_four.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, edit));


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp_get = otp_textbox_one.getText().toString().trim()+otp_textbox_two.getText().toString().trim()+
                        otp_textbox_three.getText().toString().trim()+otp_textbox_four.getText().toString().trim();
                if(otp_get.length()<4)
                {
                    toast_msg("Enter Complete OTP !!");
                }
                else {

                    otp_submit = new ProgressDialog(OtpActivity.this);
                    otp_submit.setMessage("Please Wait...");
                    otp_submit.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    otp_submit.setCancelable(false);
                    otp_submit.show();
                    final JSONObject object_mob = new JSONObject();
                    try {
                        object_mob.put("otpId", otpbean.getOtpId());
                        object_mob.put("code",otp_get);
                        object_mob.put("mobileNo",otpbean.getMobileNo());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Verify_Otp, object_mob,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    otp_submit.dismiss();
                                    try {
                                        int status = response.getInt("status");
                                        if (status==200) {
                                            callDashboard();
                                        }
                                        else {
                                            Log.d("otpActivity-else",""+status);
                                            toast_msg(response.getString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            otp_submit.dismiss();
                            toast_msg("Wrong otp");
                        }


                    });
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                            12000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    CustomRequest.getInstance(OtpActivity.this).addToRequestQue(jsonObjectRequest);
                }
            }
        });



    }

    public void toast_msg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void callDashboard() {
        Intent intent = new Intent(OtpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}