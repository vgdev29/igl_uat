package com.example.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rest.Api;
import com.example.igl.Model.NguserListModel;
import com.example.igl.R;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Quota;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NgUserDetailsActivity extends AppCompatActivity {
    private Spinner spinner_ngStatus,spinner_ngStatusReason;
    private ArrayList<String> ngStatusValues = new ArrayList<>();
    private ArrayList<String> ngStatusReasonvalue = new ArrayList<>();
    String bpNumber;
    private List<NguserListModel> nguserdetails;
    private TextView tv_ngUserName,tv_cutomerIdValue,tv_houseNoValue,tv_societyValue,tv_preferredDateValue,
            tv_blockValue,tv_areaValue,tv_mobileNoValue,tv_alternateNoValue,tv_cityvalue,tv_categoryNameValue;
    private Button submit_button;
    private String ngStatus, ngStatusReason;
    private LinearLayout ll_ngStatusreason,ll_ngStatusReasonRemarks;
    private EditText et_enterRemarks;

    private NguserListModel ngUserListModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng__user__details);
        ngUserListModel =  new NguserListModel();
        bpNumber =getIntent().getStringExtra("jmr_no");
        Log.d("nguser","bpNUmber = "+bpNumber);
        nguserdetails= new ArrayList<>();
        findViews();
        loadNgStatusSpinners();
        loadUser();

        spinner_ngStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ngStatus=  spinner_ngStatus.getItemAtPosition(spinner_ngStatus.getSelectedItemPosition()).toString();
                Log.d("nguser","ngstatus spinner = "+ngStatus);
                ngStatus=ngStatusValues.get(position);
                Log.d("nguser","ngstatus spinner = "+ngStatus);
                if (ngStatus.equalsIgnoreCase(getResources().getString(R.string.please_ng_status))){
                    // do nothing
                }else {
                    loadResonSpinner();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_ngStatusReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ngStatusReason=  spinner_ngStatusReason.getItemAtPosition(spinner_ngStatusReason.getSelectedItemPosition()).toString();
                Log.d("nguser","ng user reason = "+ngStatusReason);
                ngStatusReason=ngStatusReasonvalue.get(position);
                Log.d("nguser","ng user reason = "+ngStatusReason);
                if (ngStatusReason.equalsIgnoreCase(getResources().getString(R.string.please_ng_status))){
                    // do nothing
                }else {
                    //ngStatusReason.equalsIgnoreCase(getResources().getString(R.string.ng_status_reason_other));
                    //ll_ngStatusReasonRemarks.setVisibility(View.VISIBLE);


                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngUserListModel.setStatus(ngStatus);
                ngUserListModel.setSubStatus(ngStatus);
                if (ngUserListModel.getStatus().equalsIgnoreCase(getResources().getString(R.string.ng_on_hold))){
                    if (!TextUtils.isEmpty(et_enterRemarks.getText().toString().trim())){
                        String user_remarks = et_enterRemarks.getText().toString().trim();
                        NguserListModel nguserListModel = new NguserListModel();
                        nguserListModel.setRemarks(user_remarks);
                        submitData(nguserListModel);
                    }


                }else {

                    Intent intent = new Intent(NgUserDetailsActivity.this,NgAssignmentDetailsActivity.class);
                    startActivity(intent);

                }

            }
        });


    }



    private void findViews(){
        spinner_ngStatus = findViewById(R.id.spinner_ngStatus);
        spinner_ngStatusReason = findViewById(R.id.spinner_ngStatusReason);
        ll_ngStatusreason = findViewById(R.id.ll_ngStatusreason);
        ll_ngStatusreason.setVisibility(View.GONE);
        tv_ngUserName = findViewById(R.id.tv_ngUserName);
        tv_cutomerIdValue = findViewById(R.id.tv_cutomerIdValue);
        tv_houseNoValue = findViewById(R.id.tv_houseNoValue);
        tv_societyValue = findViewById(R.id.tv_societyValue);
        tv_blockValue = findViewById(R.id.tv_blockValue);
        tv_areaValue = findViewById(R.id.tv_areaValue);
        tv_mobileNoValue = findViewById(R.id.tv_mobileNoValue);
        tv_alternateNoValue = findViewById(R.id.tv_alternateNoValue);
        submit_button = findViewById(R.id.submit_button);
        ll_ngStatusReasonRemarks = findViewById(R.id.ll_ngStatusReasonRemarks);
        et_enterRemarks = findViewById(R.id.et_enterRemarks);
        tv_cityvalue = findViewById(R.id.tv_cityvalue);
        tv_preferredDateValue = findViewById(R.id.tv_preferredDateValue);
        tv_categoryNameValue = findViewById(R.id.tv_categoryNameValue);

    }
    private void loadNgStatusSpinners(){
        ngStatusValues.add(getResources().getString(R.string.please_ng_status));
        ngStatusValues.add(getResources().getString(R.string.ng_on_hold));
        ngStatusValues.add(getResources().getString(R.string.ng_pending));
        spinner_ngStatus.setAdapter(new ArrayAdapter<String>(NgUserDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, ngStatusValues));
    }
    private void loadResonSpinner(){
        ll_ngStatusreason.setVisibility(View.VISIBLE);
        ngStatusReasonvalue.clear();
        ngStatusReasonvalue.add("PP");
        ngStatusReasonvalue.add("EM");
        ngStatusReasonvalue.add("GC");
        ngStatusReasonvalue.add("BC");
        ngStatusReasonvalue.add("others");
        spinner_ngStatusReason.setAdapter(new ArrayAdapter<String>(NgUserDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, ngStatusReasonvalue));

    }


    private void loadUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<NguserListModel> call = api.getParticularUser(bpNumber);

        call.enqueue(new Callback<NguserListModel>() {
            @Override
                public void onResponse(Call<NguserListModel> call, Response<NguserListModel> response) {
               NguserListModel nguserListModel = response.body();
                Log.d("nguser","ng userDetails response = "+nguserdetails.size());
              // for (NguserListModel nguserListModel: nguserdetails){
                   Log.d("nguser","ng userDetails response = "+nguserListModel.getCustomerName());
                   tv_ngUserName.setText(nguserListModel.getCustomerName());
                   tv_cutomerIdValue.setText(nguserListModel.getBpNo());
                   tv_houseNoValue.setText(nguserListModel.getHouseNo());
                   tv_societyValue.setText(nguserListModel.getSociety());
                   tv_blockValue.setText(nguserListModel.getBlockQtr());
                   tv_areaValue.setText(nguserListModel.getArea());
                   tv_mobileNoValue.setText(nguserListModel.getMobileNo());
                   tv_alternateNoValue.setText(nguserListModel.getAltNumber());
                   tv_cityvalue.setText(nguserListModel.getCity());
                   tv_preferredDateValue.setText(nguserListModel.getConversionDate());

              // }

            }

            @Override
            public void onFailure(Call<NguserListModel> call, Throwable t) {

            }


        });
    }

    private void submitData(NguserListModel nguserListModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField(nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {
            @Override
            public void onResponse(Call<List<NguserListModel>> call, Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>" , "weldone............");
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error" , "error comes");
                Toast.makeText(getApplicationContext(),"Fail to success",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
