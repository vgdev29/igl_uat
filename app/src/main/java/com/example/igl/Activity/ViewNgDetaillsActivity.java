package com.example.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.igl.Model.NguserListModel;
import com.example.igl.R;
import com.example.igl.utils.Utils;
import com.example.rest.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewNgDetaillsActivity extends AppCompatActivity {

    private final int PICK_AUDIO_FILE_REQUEST = 2;
    protected static final int CAMERA_REQUEST_ON_HOLD = 201;
    private ArrayList<String> ngStatusValues = new ArrayList<>();
    private ArrayList<String> ngStatusReasonvalue = new ArrayList<>();
    private String jmrNo, mAssignDate;
    private boolean startJob;
    private List<NguserListModel> nguserdetails;
    private TextView tv_ngUserName, tv_cutomerIdValue, tv_houseNoValue, tv_societyValue, tv_preferredDateValue,
            tv_blockValue, tv_areaValue, tv_mobileNoValue, tv_alternateNoValue, tv_cityvalue,tvNgStatusValue, tv_categoryNameValue, tv_delayDate;
    private Button submit_button, picture_button;
    private EditText et_decline_remarks;
    private String ngStatus, ngStatusReason;
    private LinearLayout  ll_delayDate, ll_meterReading, ll_customerSign,ll_photo,
            ll_audioFile, ll_hold_image,ll_remarks,ll_decline_remarks,ll_home_image;
    private Button et_delayDateValue;
    private RadioGroup radioGroup;
    private RadioButton rb_delay, rb_complete;
    private TextView et_initialReading, et_burnerDetails, et_conversationDate,tv_floorValue;
    private DatePickerDialog pickerDialog_Date;
    private String initialReading, burnerDetails, conversationDate;
    private ImageView hold_image,iv_homeAddress,iv_meterPhoto,iv_installation,iv_serviceCard,signature_image;
    private String holdImageBinary;
    private ImageView back;
    private Button audioFile_button;
    private TextView str1;
    private String mediaPath1;
    private Uri filePath_address;
    private String image_path_address = "";
    private Bitmap bitmap;
    private Button btn_tpidecline,btn_tpiApprove;


    private TextView tv_ngStatusValue;
    private MaterialDialog materialDialog;
    private NguserListModel ngUserListModel1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ng_detaills);
        ngUserListModel1 = new NguserListModel();
        jmrNo = getIntent().getStringExtra("jmr_no");
        nguserdetails = new ArrayList<>();
        findViews();
        loadUser();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_tpiApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguserListModel nguserListModel= new NguserListModel();
                nguserListModel.setJmr_no(jmrNo);
                if (ngUserListModel1.getStatus().equalsIgnoreCase("OP")) {
                    nguserListModel.setStatus("OH");
                } else if (ngUserListModel1.getStatus().equalsIgnoreCase("DP")) {
                    nguserListModel.setStatus("DN");
                }
                approveTbyTPI(jmrNo, nguserListModel);
            }
        });
        btn_tpidecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_decline_remarks.setVisibility(View.VISIBLE);
                String reamrks = et_decline_remarks.getText().toString().trim();
                if (!TextUtils.isEmpty(reamrks)){
                    NguserListModel nguserListModel = new NguserListModel();
                    nguserListModel.setRemarks(reamrks);
                    nguserListModel.setJmr_no(jmrNo);
                    if (ngUserListModel1.getStatus().equalsIgnoreCase("OP")) {
                        nguserListModel.setStatus("PG");
                    } else if (ngUserListModel1.getStatus().equalsIgnoreCase("DP")) {
                        nguserListModel.setStatus("PG");
                    }
                    declineNg(jmrNo, nguserListModel);
                }
                else {
                    Utils.showToast(ViewNgDetaillsActivity.this,"Please fill remarks field");
                }
            }
        });
    }
    //ll_remarks

    private void declineNg(String jmr_number, NguserListModel nguserListModel) {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number, nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>", "weldone............");
                materialDialog.dismiss();


                //notifyDataSetChanged();
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), "Decline Successfully ", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error", "error comes");
                materialDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail to success", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    private void approveTbyTPI(String jmr_number, NguserListModel nguserListModel) {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number, nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>", "weldone............");
                materialDialog.dismiss();


                //notifyDataSetChanged();
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), "Approve Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Bad Request please try again", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error", "error comes");
                materialDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail to success", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void findViews() {
        ll_meterReading = findViewById(R.id.ll_meterReading);
        ll_customerSign = findViewById(R.id.ll_customerSign);
        ll_photo = findViewById(R.id.ll_photo);
        ll_decline_remarks = findViewById(R.id.ll_decline_remarks);
        ll_home_image = findViewById(R.id.ll_home_image);
        et_decline_remarks = findViewById(R.id.et_decline_remarks);
        //ll_remarks = findViewById(R.id.ll_remarks);
        tv_ngStatusValue = findViewById(R.id.tvNgStatusValue);
        ll_hold_image = findViewById(R.id.ll_hold_image);




        tv_ngUserName = findViewById(R.id.tv_ngUserName);

        tv_cutomerIdValue = findViewById(R.id.tv_cutomerIdValue);
        tv_houseNoValue = findViewById(R.id.tv_houseNoValue);
        tv_societyValue = findViewById(R.id.tv_societyValue);
        tv_blockValue = findViewById(R.id.tv_blockValue);
        tv_areaValue = findViewById(R.id.tv_areaValue);
        tv_mobileNoValue = findViewById(R.id.tv_mobileNoValue);
        tv_alternateNoValue = findViewById(R.id.tv_alternateNoValue);
        submit_button = findViewById(R.id.submit_button);
        picture_button = findViewById(R.id.picture_button);
        hold_image = findViewById(R.id.hold_image);
        iv_homeAddress = findViewById(R.id.iv_homeAddress);
        iv_meterPhoto = findViewById(R.id.iv_meterPhoto);
        iv_installation = findViewById(R.id.iv_installation);
        iv_serviceCard = findViewById(R.id.iv_serviceCard);
        signature_image = findViewById(R.id.signature_image);
        tv_delayDate = findViewById(R.id.tv_delayDate);




        ll_delayDate = findViewById(R.id.ll_delayDate);
        ll_audioFile = findViewById(R.id.ll_audioFile);
        ll_hold_image = findViewById(R.id.ll_hold_image);

        audioFile_button = findViewById(R.id.audioFile_button);
        str1 = (TextView) findViewById(R.id.filename1);


        tv_cityvalue = findViewById(R.id.tv_cityvalue);
        tv_preferredDateValue = findViewById(R.id.tv_preferredDateValue);
        tv_categoryNameValue = findViewById(R.id.tv_categoryNameValue);
        et_delayDateValue = findViewById(R.id.et_delayDateValue);
        rb_delay = findViewById(R.id.rb_delay);
        rb_complete = findViewById(R.id.rb_complete);
        et_initialReading = findViewById(R.id.et_initialReading);
        et_burnerDetails = findViewById(R.id.et_burnerDetails);
        et_conversationDate = findViewById(R.id.et_conversationDate);

        btn_tpidecline = findViewById(R.id.decline_button);
        btn_tpiApprove = findViewById(R.id.approve_button);
        tv_floorValue = findViewById(R.id.tv_floorValue);
        hold_image = findViewById(R.id.hold_image);
        back = findViewById(R.id.back);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);





    }
    private void setText(final NguserListModel nguserListModel){
        tv_ngUserName.setText(nguserListModel.getCustomer_name());
        tv_cutomerIdValue.setText(nguserListModel.getBp_no());
        tv_houseNoValue.setText(nguserListModel.getHouse_no());
        tv_societyValue.setText(nguserListModel.getSociety());
        tv_blockValue.setText(nguserListModel.getBlock_qtr());
        tv_areaValue.setText(nguserListModel.getArea());
        tv_mobileNoValue.setText(nguserListModel.getMobile_no());
        tv_alternateNoValue.setText(nguserListModel.getAlt_number());
        tv_cityvalue.setText(nguserListModel.getCity());
        tv_preferredDateValue.setText(nguserListModel.getConversion_date());
        et_initialReading.setText(nguserListModel.getInitial_reading());
        et_burnerDetails.setText(nguserListModel.getBurner_details());
        et_conversationDate.setText(nguserListModel.getConversion_date());
        et_delayDateValue.setText(nguserListModel.getDelay_date());
        tv_floorValue.setText(nguserListModel.getFloor());
        if (!TextUtils.isEmpty(nguserListModel.getHold_images())){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(nguserListModel.getHold_images()).apply(options).into(hold_image);
        }
        if (!TextUtils.isEmpty(nguserListModel.getHome_address())){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(nguserListModel.getHome_address()).apply(options).into(iv_homeAddress);
        }
        if (!TextUtils.isEmpty(nguserListModel.getMeter_photo())){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(nguserListModel.getMeter_photo()).apply(options).into(iv_meterPhoto);
        }
        if (!TextUtils.isEmpty(nguserListModel.getInstallation_photo())){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(nguserListModel.getInstallation_photo()).apply(options).into(iv_installation);
        }
        if (!TextUtils.isEmpty(nguserListModel.getService_photo())){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(nguserListModel.getService_photo()).apply(options).into(iv_serviceCard);
        }
        if (!TextUtils.isEmpty(nguserListModel.getCustomer_sign())){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(nguserListModel.getCustomer_sign()).apply(options).into(signature_image);
        }



        if (nguserListModel.getStatus().equalsIgnoreCase("OP")) {
            tv_ngStatusValue.setText("ON HOLD");
            rb_delay.setChecked(true);
            ll_meterReading.setVisibility(View.GONE);
            ll_customerSign.setVisibility(View.GONE);
            ll_photo.setVisibility(View.GONE);

            ll_home_image.setVisibility(View.GONE);
            ll_delayDate.setVisibility(View.VISIBLE);
            ll_hold_image.setVisibility(View.VISIBLE);
            rb_complete.setVisibility(View.GONE);

        }else {
            tv_ngStatusValue.setText("Done");
            rb_complete.setChecked(true);
            rb_delay.setVisibility(View.GONE);
            ll_meterReading.setVisibility(View.VISIBLE);
            ll_customerSign.setVisibility(View.VISIBLE);
            ll_photo.setVisibility(View.VISIBLE);
            ll_home_image.setVisibility(View.VISIBLE);
            ll_hold_image.setVisibility(View.GONE);
            ll_delayDate.setVisibility(View.GONE);
        }


    }

    private void loadUser() {
        NguserListModel nguserListModel = new NguserListModel();
        nguserListModel.setJmr_no(jmrNo);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getParticularUser(jmrNo);

        call.enqueue(new Callback<List<NguserListModel>>() {
            @Override
            public void onResponse(Call<List<NguserListModel>> call, Response<List<NguserListModel>> response) {
                nguserdetails = response.body();
                if (nguserdetails != null) {
                    if (nguserdetails.size() > 0) {
                        //if (CollectionsUtils.isEmpty)

                        for (NguserListModel mResponse : nguserdetails) {
                            ngUserListModel1.setJmr_no(mResponse.getJmr_no());
                            ngUserListModel1.setStatus(mResponse.getStatus());
                            ngUserListModel1.setService_photo(mResponse.getService_photo());
                            ngUserListModel1.setInstallation_photo(mResponse.getInstallation_photo());
                            ngUserListModel1.setMeter_photo(mResponse.getMeter_photo());
                            ngUserListModel1.setHome_address(mResponse.getHome_address());
                            ngUserListModel1.setHold_images(mResponse.getHold_images());
                            ngUserListModel1.setStart_job(mResponse.getStart_job());
                            ngUserListModel1.setRecording(mResponse.getRecording());
                            ngUserListModel1.setAlt_number(mResponse.getAlt_number());
                            ngUserListModel1.setAmount_charged(mResponse.getAmount_charged());
                            ngUserListModel1.setArea(mResponse.getArea());
                            ngUserListModel1.setBlock_qtr(mResponse.getBlock_qtr());
                            ngUserListModel1.setBp_no(mResponse.getBp_no());
                            ngUserListModel1.setBurner_details(mResponse.getBurner_details());
                            ngUserListModel1.setCa_no(mResponse.getCa_no());
                            ngUserListModel1.setCity(mResponse.getCity());
                            ngUserListModel1.setCode_group(mResponse.getCode_group());
                            ngUserListModel1.setClaim(mResponse.getClaim());
                            ngUserListModel1.setConversion_date(mResponse.getConversion_date());
                            ngUserListModel1.setCustomer_name(mResponse.getCustomer_name());
                            ngUserListModel1.setCustomer_name(mResponse.getCustomer_name());
                            ngUserListModel1.setDelay_date(mResponse.getDelay_date());
                            ngUserListModel1.setInitial_reading(mResponse.getInitial_reading());
                            ngUserListModel1.setRemarks(mResponse.getRemarks());
                            ngUserListModel1.setFloor(mResponse.getFloor());



                           /* tv_ngUserName.setText(nguserListModel.getCustomer_Name());
                            tv_cutomerIdValue.setText(nguserListModel.getBpNo());
                            tv_houseNoValue.setText(nguserListModel.getHouseNo());
                            tv_societyValue.setText(nguserListModel.getSociety());
                            tv_blockValue.setText(nguserListModel.getBlock_Qtr());
                            tv_areaValue.setText(nguserListModel.getArea());
                            tv_mobileNoValue.setText(nguserListModel.getMobileNo());
                            tv_alternateNoValue.setText(nguserListModel.getAlt_Number());
                            tv_cityvalue.setText(nguserListModel.getCity());
                            tv_preferredDateValue.setText(nguserListModel.getConversionDate());
                            et_initialReading.setText(nguserListModel.getInitialReading());
                            et_burnerDetails.setText(nguserListModel.getBurnerDetails());
                            et_burnerDetails.setText(nguserListModel.getConversionDate());
                            et_delayDateValue.setText(nguserListModel.getDelayDate());
                            if (nguserListModel.getStatus().equalsIgnoreCase("OP")) {
                                tv_ngStatusValue.setText("ON HOLD");
                                rb_delay.setChecked(true);
                                ll_meterReading.setVisibility(View.GONE);
                                ll_delayDate.setVisibility(View.VISIBLE);
                                rb_complete.setVisibility(View.GONE);

                            }else {
                                tv_ngStatusValue.setText("Done");
                                rb_complete.setChecked(true);
                                rb_delay.setVisibility(View.GONE);
                                ll_meterReading.setVisibility(View.VISIBLE);
                                ll_delayDate.setVisibility(View.GONE);
                            }*/

                            setText(ngUserListModel1);



                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error", "error comes");

            }
        });
    }


}
