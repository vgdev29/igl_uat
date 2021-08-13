package com.fieldmobility.igl.Riser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Adapter.RiserTpiApprovalDetailAdapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Model.RiserTpiListingModel;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;

import org.json.JSONObject;

public class RiserTpiApprovalDetailActivity extends AppCompatActivity {
    RecyclerView rv_detail;
    ImageView iv_one, iv_two, iv_three;
    Button btn_submit;
    RadioGroup rg_approve;
    RadioButton rb_approve, rb_decline;
    RiserTpiApprovalDetailAdapter adapter;
    RiserTpiListingModel.BpDetail data;
    boolean isApproved = true;
    EditText et_remark;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    LinearLayout lt_remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riser_tpi_approval_detail);
        sharedPrefs = new SharedPrefs(this);
        btn_submit = findViewById(R.id.btn_submit);
        lt_remark = findViewById(R.id.lt_remark);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isApproved) {
                    submitData();
                } else if (!isApproved && et_remark.getText().length() > 0) {
                    submitData();
                } else {
                    Toast.makeText(RiserTpiApprovalDetailActivity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rv_detail = findViewById(R.id.rv_detail);
        iv_one = findViewById(R.id.iv_one);
        iv_two = findViewById(R.id.iv_two);
        iv_three = findViewById(R.id.iv_three);
        rg_approve = findViewById(R.id.rg_approve);
        et_remark = findViewById(R.id.et_remark);
        rg_approve.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                btn_submit.setEnabled(true);
//                btn_submit.setAlpha(1f);
                switch (checkedId) {
                    case R.id.rb_approve:
                        isApproved = true;
                        lt_remark.setVisibility(View.GONE);

                        break;
                    case R.id.rb_decline:
                        isApproved = false;
                        lt_remark.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rv_detail.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent().hasExtra("data")) {
            String json = getIntent().getStringExtra("data");
            data = new Gson().fromJson(json, RiserTpiListingModel.BpDetail.class);
            initViews();
        }

    }

    private void initViews() {
        adapter = new RiserTpiApprovalDetailAdapter(this, data);
        rv_detail.setAdapter(adapter);
        ((TextView) findViewById(R.id.tv_bp_num)).setText("BP " + data.getBpNumber());
        data.getSitePhoto1();
        if (data.getSitePhoto3()!=null && !data.getSitePhoto3().isEmpty()){
            iv_three.setVisibility(View.VISIBLE);
        }
//        Picasso.with(RiserTpiApprovalDetailActivity.this).load(Constants.BASE_URL+data.getSitePhoto1()).into(iv_id_proof);


    }

    private void submitData() {
        String status = "4";
        String remarks = "IT_IS_APPROVED";
        if (!isApproved) {
            status = "3";
            remarks = et_remark.getText().toString().trim();
        }

        materialDialog = new MaterialDialog.Builder(RiserTpiApprovalDetailActivity.this)
                .content("Please wait....")
                .progress(true, 0)

                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.RISER__APPROVAL_DECLINE + "/" + data.getBpNumber() + "?remarks=" + remarks + "&status=" + status, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("200")){
                        Toast.makeText(RiserTpiApprovalDetailActivity.this,"Succesfully Updated",Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(RiserTpiApprovalDetailActivity.this,jsonObject.getString("Message"),Toast.LENGTH_SHORT).show();

                    }
//
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RiserTpiApprovalDetailActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();

                }
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

}