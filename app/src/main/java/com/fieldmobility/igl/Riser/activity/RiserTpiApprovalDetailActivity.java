package com.fieldmobility.igl.Riser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fieldmobility.igl.Activity.Bp_Created_Detail;
import com.fieldmobility.igl.Adapter.RiserTpiApprovalDetailAdapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Model.RiserTpiListingModel;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class RiserTpiApprovalDetailActivity extends AppCompatActivity {
    RecyclerView rv_detail;
    ImageView iv_one,iv_two,iv_three;
    Button btn_submit;
    RadioGroup rg_approve;
    RadioButton rb_approve,rb_decline;
    RiserTpiApprovalDetailAdapter adapter;
    RiserTpiListingModel.BpDetail data;
boolean isApproved=false;
EditText et_remark ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riser_tpi_approval_detail);
        rv_detail=findViewById(R.id.rv_detail);
        iv_one=findViewById(R.id.iv_one);
        iv_two=findViewById(R.id.iv_two);
        iv_three=findViewById(R.id.iv_three);
        rg_approve=findViewById(R.id.rg_approve);
        et_remark=findViewById(R.id.et_remark);
        rg_approve.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_approve:
                        isApproved=true;
                        et_remark.setVisibility(View.GONE);

                        break;
                    case R.id.rb_decline:
                        isApproved=false;
                        et_remark.setVisibility(View.VISIBLE);

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
        if (getIntent().hasExtra("data")){
            String json= getIntent().getStringExtra("data");
            data=new Gson().fromJson(json,RiserTpiListingModel.BpDetail.class);
            initViews();
        }

    }

    private void initViews() {
        adapter=new RiserTpiApprovalDetailAdapter(this,data);
        rv_detail.setAdapter(adapter);
        ((TextView)findViewById(R.id.tv_bp_num)).setText("BP "+data.getBpNumber());
        data.getSitePhoto1();
//        Picasso.with(RiserTpiApprovalDetailActivity.this).load(Constants.BASE_URL+data.getSitePhoto1()).into(iv_id_proof);


    }
}