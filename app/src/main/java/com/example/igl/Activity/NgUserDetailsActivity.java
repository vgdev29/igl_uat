package com.example.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.igl.Model.TpiDetailResponse;
import com.example.igl.utils.Utils;
import com.example.rest.Api;
import com.example.igl.Model.NguserListModel;
import com.example.igl.R;


import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.mail.Quota;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Environment.getExternalStorageDirectory;
import static com.example.igl.utils.Utils.change_to_binary;
import static com.example.igl.utils.Utils.isNetworkConnected;


public class NgUserDetailsActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST_ON_HOLD = 1;
    private final int PICK_AUDIO_FILE_REQUEST = 2;
    protected static final int CAMERA_REQUEST_ON_HOLD = 201;
    private Spinner spinner_ngStatus, spinner_ngStatusReason;
    private ArrayList<String> ngStatusValues = new ArrayList<>();
    private ArrayList<String> ngStatusReasonvalue = new ArrayList<>();
    private String jmrNo, mAssignDate;
    private boolean startJob;
    private List<NguserListModel> nguserdetails;
    private TextView tv_ngUserName, tv_cutomerIdValue, tv_houseNoValue, tv_societyValue, tv_preferredDateValue,
            tv_blockValue, tv_areaValue, tv_mobileNoValue, tv_alternateNoValue, tv_cityvalue, tv_categoryNameValue, tv_delayDate,tv_floorValue;
    private Button submit_button, picture_button;
    private String ngStatus, ngStatusReason;
    private LinearLayout ll_ngStatusreason, ll_delayDate, ll_meterReading, ll_audioFile, ll_hold_image;
    private Button et_delayDateValue;
    private RadioGroup radioGroup;
    private RadioButton rb_delay, rb_complete;
    private EditText et_initialReading, et_burnerDetails, et_conversationDate;
    private DatePickerDialog pickerDialog_Date;
    private String initialReading, burnerDetails, conversationDate;
    private ImageView hold_image;
    private String holdImageBinary;
    private ImageView back;
    private Button audioFile_button;
    private TextView str1;
    private String mediaPath1;
    private Uri filePath_address;
    private String image_path_address = "";
    private Bitmap bitmap;
    private TextView tv_serviceNameValue;

    private NguserListModel ngUserListModel;
    private MaterialDialog materialDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng__user__details);
        ngUserListModel = new NguserListModel();
        jmrNo = getIntent().getStringExtra("jmr_no");
        mAssignDate = getIntent().getStringExtra("mAssignDate");
        startJob = getIntent().getExtras().getBoolean("startJob");
        nguserdetails = new ArrayList<>();

        findViews();
        loadNgStatusSpinners();
        loadResonSpinner();
        loadUser();

        spinner_ngStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ngStatus = spinner_ngStatus.getItemAtPosition(spinner_ngStatus.getSelectedItemPosition()).toString();
                ngStatus = ngStatusValues.get(position);
                if (ngStatus.equalsIgnoreCase(getResources().getString(R.string.please_ng_status))) {
                    // do nothing
                    ll_ngStatusreason.setVisibility(View.GONE);
                } else if (ngStatus.equalsIgnoreCase(getResources().getString(R.string.ng_on_hold))) {
                    ll_ngStatusreason.setVisibility(View.VISIBLE);
                    rb_complete.setVisibility(View.GONE);
                    ll_meterReading.setVisibility(View.GONE);
                    ngUserListModel.setStatus("OP");
                } else if (ngStatus.equalsIgnoreCase(getResources().getString(R.string.ng_pending))) {

                    ll_ngStatusreason.setVisibility(View.VISIBLE);
                    rb_delay.setVisibility(View.GONE);
                    rb_complete.setVisibility(View.VISIBLE);
                    ll_delayDate.setVisibility(View.GONE);
                    ll_audioFile.setVisibility(View.GONE);
                    ll_hold_image.setVisibility(View.GONE);
                    ll_meterReading.setVisibility(View.VISIBLE);
                    ngUserListModel.setStatus("DP");

                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_ngStatusReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ngStatusReason = spinner_ngStatusReason.getItemAtPosition(spinner_ngStatusReason.getSelectedItemPosition()).toString();
                Log.e("Society+", ngStatusReason);
                ngStatusReason = ngStatusReasonvalue.get(position);
                if (ngStatusReason.equalsIgnoreCase(getResources().getString(R.string.please_ng_status))) {
                    // do nothing
                } else {
                    //ngStatusReason.equalsIgnoreCase(getResources().getString(R.string.ng_status_reason_other));


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        picture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage_address();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!startJob) {

                    Toast.makeText(NgUserDetailsActivity.this, "Job not started", Toast.LENGTH_SHORT).show();
                } else {
                    submit_button.setClickable(true);
                    submit_button.setEnabled(true);
                    if (validateData()) {
                        if (rb_complete.isChecked()) {
                            if (!TextUtils.isEmpty(et_initialReading.getText().toString().trim())
                                    && !TextUtils.isEmpty(et_burnerDetails.getText().toString().trim())
                                    && !TextUtils.isEmpty(et_conversationDate.getText().toString().trim())) {
                                if (!TextUtils.isEmpty(et_initialReading.getText().toString().trim())) {
                                    initialReading = et_initialReading.getText().toString().trim();
                                }

                                if (!TextUtils.isEmpty(et_initialReading.getText().toString().trim())) {
                                    burnerDetails = et_burnerDetails.getText().toString().trim();
                                }
                                if (!TextUtils.isEmpty(et_initialReading.getText().toString().trim())) {
                                    conversationDate = et_conversationDate.getText().toString().trim();
                                }

                                Intent intent = new Intent(NgUserDetailsActivity.this, NgAssignmentDetailsActivity.class);
                                intent.putExtra("jmrNo", jmrNo);
                                intent.putExtra("mAssignDate", mAssignDate);
                                intent.putExtra("initialReading", initialReading);
                                intent.putExtra("burnerDetails", burnerDetails);
                                intent.putExtra("conversationDate", conversationDate);


                                startActivity(intent);
                            } else {
                                Utils.showToast(NgUserDetailsActivity.this, "Please select meter reading");
                            }

                        } else if (rb_delay.isChecked()) {
                            if (!TextUtils.isEmpty(et_delayDateValue.getText().toString().trim()) &&
                                    et_delayDateValue.getText().toString().trim().equalsIgnoreCase("Select Delay Date")) {
                                Utils.showToast(NgUserDetailsActivity.this, "Select Delay Date");


                            } else {
                                if (!TextUtils.isEmpty(holdImageBinary) || !TextUtils.isEmpty(mediaPath1)) {
                                    ngUserListModel.setSub_status(ngStatusReason);

                                    ngUserListModel.setDelay_date(et_delayDateValue.getText().toString().trim());
                                    ngUserListModel.setJmr_no(jmrNo);
                                    if (isNetworkConnected(NgUserDetailsActivity.this)) {
                                        submitData(ngUserListModel);
                                    } else {
                                        Utils.showToast(NgUserDetailsActivity.this, "Please check internet connection");
                                    }
                                } else {
                                    Utils.showToast(NgUserDetailsActivity.this, "Please select image");
                                }
                            }

                        } else {
                            Utils.showToast(NgUserDetailsActivity.this, "Please select checkbox");
                        }
                    }
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_complete:
                        //Toast.makeText(NgUserDetailsActivity.this, "complete selected", Toast.LENGTH_SHORT).show();
                        ll_delayDate.setVisibility(View.GONE);
                        ll_audioFile.setVisibility(View.GONE);
                        ll_hold_image.setVisibility(View.GONE);


                        ll_meterReading.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_delay:
                        //Toast.makeText(NgUserDetailsActivity.this, "delay selected", Toast.LENGTH_SHORT).show();
                        tv_delayDate.setText("Follow up Date");

                        ll_delayDate.setVisibility(View.VISIBLE);
                        ll_audioFile.setVisibility(View.VISIBLE);
                        ll_hold_image.setVisibility(View.VISIBLE);

                        ll_meterReading.setVisibility(View.GONE);

                        break;
                }

            }
        });

        et_delayDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                pickerDialog_Date = new DatePickerDialog(NgUserDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;

                                if (month < 10) {

                                    formattedMonth = "0" + month;
                                }
                                if (dayOfMonth < 10) {

                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                Log.e("Date", year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);

                                et_delayDateValue.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                pickerDialog_Date.show();
            }


        });

        et_conversationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                pickerDialog_Date = new DatePickerDialog(NgUserDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;

                                if (month < 10) {

                                    formattedMonth = "0" + month;
                                }
                                if (dayOfMonth < 10) {

                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                Log.e("Date", year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);

                                et_conversationDate.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                pickerDialog_Date.show();
            }
        });


        audioFile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("*/*");
                startActivityForResult(galleryIntent, PICK_AUDIO_FILE_REQUEST);
            }
        });

    }

    public String getPath(Uri uri) {
        String path = null;
        try {
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();
            cursor = this.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST_ON_HOLD:
                if (requestCode == PICK_IMAGE_REQUEST_ON_HOLD && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_address = data.getData();
                    Log.e("Camera_Pathaddress++", filePath_address.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_address);
                        // imageView.setImageBitmap(bitmap);
                        //hold_image.setImageBitmap(bitmap);
                        if (bitmap != null) {
                            holdImageBinary = change_to_binary(bitmap);
                            ngUserListModel.setHold_images(holdImageBinary);
                            hold_image.setImageBitmap(bitmap);
                        }
                        image_path_address = getPath(filePath_address);
                        Log.e("image_path_address+", "" + image_path_address);
                        // new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CAMERA_REQUEST_ON_HOLD:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_ON_HOLD) {
                    File f = new File(getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        if (bitmap != null) {
                            holdImageBinary = change_to_binary(bitmap);
                            ngUserListModel.setHold_images(holdImageBinary);
                            hold_image.setImageBitmap(bitmap);
                        }
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_address = file.toString();
                        // image_path_address1 =file.toString();
                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_AUDIO_FILE_REQUEST:
                try {
                    // When an Image is picked
                    //if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                    if (requestCode == PICK_AUDIO_FILE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                        Uri selectedVideo = data.getData();
                        String[] filePathColumn = {MediaStore.Audio.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                        assert cursor != null;
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                        mediaPath1 = cursor.getString(columnIndex);
                        str1.setText(mediaPath1);
                        cursor.close();
                    } else {
                        Toast.makeText(this, "You haven't picked recording file", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
                break;

        }


    }

    private void selectImage_address() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_ON_HOLD);
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(NgUserDetailsActivity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_ON_HOLD);
                    }
                });
        myAlertDialog.show();
    }

    private void findViews() {
        ll_meterReading = findViewById(R.id.ll_meterReading);
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
        picture_button = findViewById(R.id.picture_button);
        hold_image = findViewById(R.id.hold_image);
        tv_delayDate = findViewById(R.id.tv_delayDate);
        tv_floorValue = findViewById(R.id.tv_floorValue);

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
        back = findViewById(R.id.back);
        tv_serviceNameValue = findViewById(R.id.tv_serviceNameValue);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        SharedPreferences sh = getSharedPreferences("MySharedPrefContact", Context.MODE_PRIVATE);
        String tpiName = sh.getString("TPIName", "");
        String tpi_code_group = sh.getString("TPI_CODE_GROUP", "");
        if (!TextUtils.isEmpty(tpi_code_group)){
            if (tpi_code_group.equalsIgnoreCase("ZLEAD001")){
                tv_serviceNameValue.setText("Private Normal");
            }
            if (tpi_code_group.equalsIgnoreCase("ZLEAD002")){
                tv_serviceNameValue.setText("Government");
            }
            if (tpi_code_group.equalsIgnoreCase("ZLEAD004")){
                tv_serviceNameValue.setText("Government");
            }
            if (tpi_code_group.equalsIgnoreCase("ZLEAD005")){
                tv_serviceNameValue.setText("Private EMI");
            }

        }


    }

    private void loadNgStatusSpinners() {
        ngStatusValues.add(getResources().getString(R.string.please_ng_status));
        ngStatusValues.add(getResources().getString(R.string.ng_on_hold));
        ngStatusValues.add(getResources().getString(R.string.ng_pending));
        spinner_ngStatus.setAdapter(new ArrayAdapter<String>(NgUserDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, ngStatusValues));
    }

    private void loadResonSpinner() {
        ll_ngStatusreason.setVisibility(View.VISIBLE);
        ngStatusReasonvalue.add("PP");
        ngStatusReasonvalue.add("EM");
        ngStatusReasonvalue.add("GC");
        ngStatusReasonvalue.add("BC");
        //ngStatusReasonvalue.add("others");
        spinner_ngStatusReason.setAdapter(new ArrayAdapter<String>(NgUserDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, ngStatusReasonvalue));

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
                        for (NguserListModel nguserListModel : nguserdetails) {
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
                            tv_floorValue.setText(nguserListModel.getFloor());


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

    private boolean validateData() {
        boolean isDataValid = true;
        if (ngStatus.equalsIgnoreCase(getResources().getString(R.string.please_ng_status))) {
            isDataValid = false;
            Toast.makeText(NgUserDetailsActivity.this, "Please status reason", Toast.LENGTH_SHORT).show();
        }


        return isDataValid;
    }

    private void submitData(NguserListModel nguserListModel) {
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
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmrNo, nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {
            @Override
            public void onResponse(Call<List<NguserListModel>> call, Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>", "weldone............");

                if (response.code() == 200) {
                    if (!TextUtils.isEmpty(mediaPath1)) {
                        uploadFile();
                    } else {
                        materialDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Data submitted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NgUserDetailsActivity.this, NgUserListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                } else {
                    materialDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data not submitted", Toast.LENGTH_SHORT).show();
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

    private void uploadFile() {
        try {


            NguserListModel nguserListModel = new NguserListModel();
            nguserListModel.setRecording(mediaPath1);
            // Map is used to multipart the file using okhttp3.RequestBody
            File file = new File(mediaPath1);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Parsing any Media type file
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, jmrNo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("recording", file.getName(), requestBody);
            MultipartBody.Part jmr_no = MultipartBody.Part.createFormData("jmr_no", jmrNo, requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


            Api api = retrofit.create(Api.class);
            Call<List<NguserListModel>> call = api.uploadFile(jmrNo, fileToUpload, description);
            //Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
            call.enqueue(new Callback<List<NguserListModel>>() {
                @Override
                public void onResponse(Call<List<NguserListModel>> call, Response<List<NguserListModel>> response) {
                    Log.v("Response", response.toString());
                    materialDialog.dismiss();

                    if (response.code() == 200) {
                        Toast.makeText(getApplicationContext(), "Audio file submitted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NgUserDetailsActivity.this, NgUserListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Audio file not submitted please try again!!", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                    Log.v("Response", t.toString());
                    materialDialog.dismiss();
                    Utils.showToast(getApplicationContext(), "Error for uploading data");

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            materialDialog.dismiss();
            Utils.showToast(getApplicationContext(), "server error");
        }
    }


}
