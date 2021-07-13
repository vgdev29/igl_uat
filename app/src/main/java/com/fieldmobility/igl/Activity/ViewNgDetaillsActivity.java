package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.fieldmobility.igl.rest.Api;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Environment.getExternalStorageDirectory;

public class ViewNgDetaillsActivity extends AppCompatActivity {

    private final int PICK_AUDIO_FILE_REQUEST = 2;
    protected static final int CAMERA_REQUEST_ON_HOLD = 201;
    private ArrayList<String> ngStatusValues = new ArrayList<>();
    private ArrayList<String> ngStatusReasonvalue = new ArrayList<>();
    private String jmrNo, mAssignDate;
    private boolean startJob;
    private List<NguserListModel> nguserdetails;
    NguserListModel intent_nguserlist;
    private TextView tv_ngUserName, tv_serviceNameValue, tv_houseNoValue, tv_societyValue, tv_preferredDateValue,tv_jmr_no,tv_bp_no,tv_ngdate,tv_jobstartdateValue,
            tv_blockValue, tv_areaValue, tv_mobileNoValue, tv_alternateNoValue, tv_cityvalue,tvNgStatusValue, tv_categoryNameValue, tv_delayDate;
    private EditText et_decline_remarks;
    private String ngStatus, ngStatusReason;
    private LinearLayout  ll_ngdone,ll_nghold;
    private TextView et_initialReading, et_burnerDetails, et_conversationDate,tv_floorValue ,et_delayDateValue;
    private DatePickerDialog pickerDialog_Date;
    private String initialReading, burnerDetails, conversationDate;
    private ImageView hold_image,iv_homeAddress,iv_meterPhoto,iv_installation,iv_serviceCard,signature_image;
    private String holdImageBinary;
    private ImageView back;
    private TextView str1;
    private String mediaPath1;
    private Uri filePath_address;
    private String image_path_address = "";
    private Bitmap bitmap;
    private Button btn_tpidecline,btn_tpiApprove,signature_button;
    private TextView tv_ngStatusValue;
    private MaterialDialog materialDialog;
    private NguserListModel ngUserListModel1;
    private String signatureBinary;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    TextView metermakeValue , meternoValue, metertypeValue, rfcreadingValue,reasonvalue,substatusvalue , rfcdate_value;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ng_detaills);
        jmrNo = getIntent().getStringExtra("jmr_no");
        intent_nguserlist = (NguserListModel) getIntent().getSerializableExtra("nglist");
        ngUserListModel1 = intent_nguserlist;
        nguserdetails = new ArrayList<>();
        findViews();
       setText(intent_nguserlist);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       signature_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           Customer_Signature1();
           }
       });
        btn_tpiApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguserListModel nguserListModel= new NguserListModel();
                nguserListModel.setJmr_no(jmrNo);
                nguserListModel.setExecutive_sign(signatureBinary);
                if (ngUserListModel1.getStatus().equalsIgnoreCase("OP")) {
                    nguserListModel.setStatus("OH");
                } else if (ngUserListModel1.getStatus().equalsIgnoreCase("DP")) {
                    nguserListModel.setStatus("DN");
                }
                if(validateApproval()) {
                    approveTbyTPI(jmrNo, nguserListModel);
                }
            }
        });
        btn_tpidecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reamrks = et_decline_remarks.getText().toString().trim();
                    NguserListModel nguserListModel = new NguserListModel();
                    nguserListModel.setRemarks(reamrks);
                    nguserListModel.setJmr_no(jmrNo);
                nguserListModel.setTpi_id("");
                nguserListModel.setSupervisor_id("");
                nguserListModel.setClaim(false);
                nguserListModel.setStart_job(false);
                nguserListModel.setClaim_date("null");
                nguserListModel.setConversion_date("null");
                    if (ngUserListModel1.getStatus().equalsIgnoreCase("OP")) {
                        nguserListModel.setStatus("PG");
                    } else if (ngUserListModel1.getStatus().equalsIgnoreCase("DP")) {
                        nguserListModel.setStatus("PG");
                    }

                    if (validateDecline()) {
                        declineNg(jmrNo, nguserListModel);
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
                Log.e("Mysucess>>>>>>>>>>", response.toString());
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
                Log.e("Mysucess>>>>>>>>>>", response.toString());
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
        tv_houseNoValue = findViewById(R.id.tv_houseNoValue);
        tv_societyValue = findViewById(R.id.tv_societyValue);
        tv_blockValue = findViewById(R.id.tv_blockValue);
        tv_areaValue = findViewById(R.id.tv_areaValue);
        tv_mobileNoValue = findViewById(R.id.tv_mobileNoValue);
        tv_alternateNoValue = findViewById(R.id.tv_alternateNoValue);
        tv_preferredDateValue = findViewById(R.id.tv_claimDateValue);
        tv_jobstartdateValue = findViewById(R.id.tv_startDateValue);
        tv_ngdate = findViewById(R.id.tv_NgDateValue);
        rfcdate_value = findViewById(R.id.tv_rfcdate_value);
        signature_button = findViewById(R.id.signature_button);
        ll_ngdone = findViewById(R.id.ll_ngdone);
        ll_nghold = findViewById(R.id.ll_nghold);
        et_decline_remarks = findViewById(R.id.et_decline_remarks);
        //ll_remarks = findViewById(R.id.ll_remarks);
        tv_ngStatusValue = findViewById(R.id.tvNgStatusValue);
        tv_ngUserName = findViewById(R.id.tv_ngUserName);
        tv_jmr_no = findViewById(R.id.tv_jmr_no);
        tv_bp_no = findViewById(R.id.tv_bp_no);
        et_delayDateValue = findViewById(R.id.et_delayDateValue);
        hold_image = findViewById(R.id.hold_image);
        iv_homeAddress = findViewById(R.id.iv_homeAddress);
        iv_meterPhoto = findViewById(R.id.iv_meterPhoto);
        iv_installation = findViewById(R.id.iv_installation);
        iv_serviceCard = findViewById(R.id.iv_serviceCard);
        signature_image = findViewById(R.id.signature_image);
        tv_delayDate = findViewById(R.id.tv_delayDate);
        str1 = (TextView) findViewById(R.id.filename1);
        tv_cityvalue = findViewById(R.id.tv_cityvalue);
        tv_categoryNameValue = findViewById(R.id.tv_categoryNameValue);
        et_initialReading = findViewById(R.id.et_initialReading);
        et_burnerDetails = findViewById(R.id.et_burnerDetails);
        btn_tpidecline = findViewById(R.id.decline_button);
        btn_tpiApprove = findViewById(R.id.approve_button);
        tv_floorValue = findViewById(R.id.tv_floorValue);
        hold_image = findViewById(R.id.hold_image);
        back = findViewById(R.id.back);
        rfcreadingValue = findViewById(R.id.tv_rfcreading_value);
        metermakeValue = findViewById(R.id.tv_metermake_value);
        meternoValue = findViewById(R.id.tv_meter_no_value);
        metertypeValue = findViewById(R.id.tv_metertype_value);
        tv_serviceNameValue = findViewById(R.id.tv_serviceNameValue);
        substatusvalue = findViewById(R.id.tv_substatusValue);
        reasonvalue = findViewById(R.id.tv_NgreasonValue);

    }
    private void setText(final NguserListModel nguserListModel){
        tv_ngUserName.setText(nguserListModel.getCustomer_name());
        tv_jmr_no.setText("JMR No. - "+jmrNo);
        tv_bp_no.setText("BP No. - "+nguserListModel.getBp_no());
        tv_jobstartdateValue.setText(nguserListModel.getConversion_date());
        tv_ngdate.setText(nguserListModel.getNg_update_date());
        Log.d("viewNg",nguserListModel.toString());
        tv_houseNoValue.setText(nguserListModel.getHouse_no());
        rfcdate_value.setText(nguserListModel.getRfc_date());
        tv_societyValue.setText(nguserListModel.getSociety());
        tv_blockValue.setText(nguserListModel.getBlock_qtr());
        tv_areaValue.setText(nguserListModel.getArea());
        tv_mobileNoValue.setText(nguserListModel.getMobile_no());
        tv_alternateNoValue.setText(nguserListModel.getAlt_number());
        tv_cityvalue.setText(nguserListModel.getCity());
        tv_preferredDateValue.setText(nguserListModel.getConversion_date());
        et_initialReading.setText(nguserListModel.getInitial_reading());
        et_burnerDetails.setText(nguserListModel.getBurner_details());
        et_delayDateValue.setText(nguserListModel.getDelay_date());
        tv_floorValue.setText(nguserListModel.getFloor());
        metertypeValue.setText(nguserListModel.getMeter_type());
        meternoValue.setText(nguserListModel.getMeter_no());
        metermakeValue.setText(nguserListModel.getMeter_make());
        rfcreadingValue.setText(nguserListModel.getRfc_initial_reading());
        reasonvalue.setText(nguserListModel.getReason());
        substatusvalue.setText(nguserListModel.getSub_status());

        String tpi_code_group =  nguserListModel.getCode_group();
        if (!TextUtils.isEmpty(tpi_code_group)) {
            if (tpi_code_group.equalsIgnoreCase("ZLEAD001")) {
                tv_serviceNameValue.setText("Private Normal");
            }
            if (tpi_code_group.equalsIgnoreCase("ZLEAD002")) {
                tv_serviceNameValue.setText("Government");
            }
            if (tpi_code_group.equalsIgnoreCase("ZLEAD004")) {
                tv_serviceNameValue.setText("Government");
            }
            if (tpi_code_group.equalsIgnoreCase("ZLEAD005")) {
                tv_serviceNameValue.setText("Private EMI");
            }

        }

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



        if (nguserListModel.getStatus().equalsIgnoreCase("OP")) {
            tv_ngStatusValue.setText("NG OnHold Details");
            ll_nghold.setVisibility(View.VISIBLE);
            ll_ngdone.setVisibility(View.GONE);

        }else {
            tv_ngStatusValue.setText("NG Done Details");
             ll_nghold.setVisibility(View.GONE);
             ll_ngdone.setVisibility(View.VISIBLE);
        }


    }

    private void Customer_Signature1() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.customer_signature);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
        TextView owner_name = dialog.findViewById(R.id.owner_name);
        owner_name.setVisibility(View.GONE);
        ImageView adhar_owner_image = dialog.findViewById(R.id.adhar_owner_image);
        Button adhar_button = dialog.findViewById(R.id.adhar_button);
        TextView signature_select = dialog.findViewById(R.id.signature_select);
        TextView image_select = dialog.findViewById(R.id.image_select);
        image_select.setVisibility(View.GONE);
        signature_select.setVisibility(View.GONE);

        TextView save_select = dialog.findViewById(R.id.save_select);
        final LinearLayout signature_layout = dialog.findViewById(R.id.signature_layout);
        final LinearLayout image_capture_layout = dialog.findViewById(R.id.image_capture_layout);
        signature_layout.setVisibility(View.VISIBLE);
        image_capture_layout.setVisibility(View.GONE);
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        EditText ownar_name_no = dialog.findViewById(R.id.ownar_name_no);
        ownar_name_no.setVisibility(View.GONE);
        //final ImageView signature_image = dialog.findViewById(R.id.signature_image);
        final SignatureView signatureView = (SignatureView) dialog.findViewById(R.id.ownar_signature_view);
        Button clear = (Button) dialog.findViewById(R.id.clear);
        Button save = (Button) dialog.findViewById(R.id.save);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });
        crose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap signatureBitmap = signatureView.getSignatureBitmap();
                String customer_image_select = saveImage(signatureBitmap);
                if (signatureBitmap != null) {
                    signatureBinary = change_to_binary(signatureBitmap);
                    ngUserListModel1.setExecutive_sign(signatureBinary);
                    signature_image.setImageBitmap(signatureBitmap);
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("Signature_Page++", wallpaperDirectory.toString());
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private String change_to_binary(Bitmap bitmapOrg) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        return ba1;
    }

    public boolean validateApproval()
    {
        boolean isValidate = true;
        if(TextUtils.isEmpty(signatureBinary))
        {
            isValidate = false;
            Utils.showToast(this,"Pls select Signature");
            return isValidate;
        }
        else {
            return isValidate;
        }
    }
    public boolean validateDecline()
    {
        boolean isValidate = true;
        if(TextUtils.isEmpty(et_decline_remarks.getText().toString().trim()))
        {
            isValidate = false;
            et_decline_remarks.setError("fields can't be empty");
            return isValidate;
        }
        else {
            return isValidate;
        }
    }


}
