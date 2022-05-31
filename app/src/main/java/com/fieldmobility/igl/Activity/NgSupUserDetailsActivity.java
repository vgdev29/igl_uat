package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.utils.Utils;
import com.fieldmobility.igl.rest.Api;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Environment.getExternalStorageDirectory;
import static com.fieldmobility.igl.utils.Utils.change_to_binary;
import static com.fieldmobility.igl.utils.Utils.isNetworkConnected;


public class NgSupUserDetailsActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST_ON_HOLD = 1;
    private final int PICK_AUDIO_FILE_REQUEST = 2;
    protected static final int CAMERA_REQUEST_ON_HOLD = 201;
    private Spinner spinner_ngStatus, spinner_ngStatusReason;
    private ArrayList<String> code_status = new ArrayList<>();
    private ArrayList<String> status_id = new ArrayList<>();
    private ArrayList<String> group_status = new ArrayList<>();
    private ArrayList<String> cat_status = new ArrayList<>();
    private ArrayList<String> description_status = new ArrayList<>();
    private ArrayList<String> catalog_status = new ArrayList<>();
    private ArrayList<String> code_substatus = new ArrayList<>();
    private ArrayList<String> group_substatus = new ArrayList<>();
    private ArrayList<String> cat_substatus = new ArrayList<>();
    private ArrayList<String> description_substatus = new ArrayList<>();
    private ArrayList<String> catalog_substatus = new ArrayList<>();
    private  String selected_status_id = "", selected_code_status="",selected_description_status="",selected_description_substatus="",selected_group_status="",selected_cat_status="",selected_catalog_status="" ;
    private String jmrNo, mAssignDate;
    private boolean startJob;
    private List<NguserListModel> nguserdetails;
    private TextView tv_ngUserName, tv_bp_no, tv_jmr_no, tv_houseNoValue, tv_societyValue, tv_preferredDateValue,
            tv_blockValue, tv_areaValue, tv_cityvalue, tv_categoryNameValue, tv_delayDate, tv_floorValue;
    private Button submit_button, picture_button;

    private LinearLayout ll_hold, ll_meterReading,ll_ngStatusreason;
    private TextView et_delayDateValue;


    private EditText tv_alternateNoValue, et_initialReading, et_burnerDetails,tv_mobileNoValue , et_nghold_remarks, corrected_meternoValue;
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
    private TextView tv_serviceNameValue, et_conversationDate;

    private NguserListModel ngUserListModel;
    private NguserListModel intentngUserListModel;
    TextView metermakeValue , metertypeValue, rfcreadingValue , rfcdate_value , meternoValue;
    LinearLayout ll_cmeter ;
    RadioGroup meterRadiogroup;
    String log = "nguserdetails";
    String correctedmeterno = null;
    boolean meterincorrect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng__user__details);
        ngUserListModel = new NguserListModel();
        jmrNo = getIntent().getStringExtra("jmr_no");
        Log.d(log, "jmr no = " + jmrNo);

        mAssignDate = getIntent().getStringExtra("mAssignDate");
        startJob = getIntent().getExtras().getBoolean("startJob");
        intentngUserListModel = (NguserListModel) getIntent().getSerializableExtra("ngmodel");
        ngUserListModel = intentngUserListModel;
        Log.d(log, "jmr no intent= " + intentngUserListModel.getJmr_no());
        nguserdetails = new ArrayList<>();
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

           findViews();
         loadNgStatusSpinners();

        setText(intentngUserListModel);
        if(intentngUserListModel.getCustomer_name().toUpperCase().contains("CHILD"))
        {
            updateNgDialog();
        }

        if (Utils.isNetworkConnected(this)) {
            //  loadUser();
            //DBManager.
        } else {
            Utils.showToast(this, "No internet connection!!");
        }
        tv_ngUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNg();
            }
        });

        spinner_ngStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selected_description_status = spinner_ngStatus.getItemAtPosition(spinner_ngStatus.getSelectedItemPosition()).toString();
               Log.d(log,"description selected ="+selected_description_status);
                selected_cat_status = cat_status.get(position);
                selected_catalog_status = catalog_status.get(position);
                selected_code_status = code_status.get(position);
                selected_group_status = group_status.get(position);
                selected_status_id = status_id.get(position);
                Log.d(log,"description selected ="+selected_description_status);
                Log.d(log,"cat selected ="+selected_cat_status);
                Log.d(log,"catalog selected ="+selected_catalog_status);
                Log.d(log,"code selected ="+selected_code_status);
                Log.d(log,"group selected ="+selected_group_status);
                Log.d(log,"status_id ="+selected_status_id);


                if (selected_description_status.contains("Hold")) {
                    // do nothing
                    ll_hold.setVisibility(View.VISIBLE);
                    ll_meterReading.setVisibility(View.GONE);
                    ll_ngStatusreason.setVisibility(View.VISIBLE);
                    meterRadiogroup.setVisibility(View.GONE);
                    ll_cmeter.setVisibility(View.GONE);
                    loadResonSpinner();
                    ngUserListModel.setStatus("OP");
                } else   {
                    ll_ngStatusreason.setVisibility(View.GONE);
                    ll_hold.setVisibility(View.GONE);
                    ll_meterReading.setVisibility(View.VISIBLE);
                    meterRadiogroup.setVisibility(View.VISIBLE);

                    ngUserListModel.setStatus("DP");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_ngStatusReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selected_description_substatus = spinner_ngStatusReason.getItemAtPosition(spinner_ngStatusReason.getSelectedItemPosition()).toString();
                Log.d(log,"selected descriptn "+ selected_description_substatus);
                selected_cat_status = cat_substatus.get(position);
                selected_catalog_status = catalog_substatus.get(position);
                selected_code_status = code_substatus.get(position);
                selected_group_status = group_substatus.get(position);
                Log.d(log,"description selected ="+selected_description_status);
                Log.d(log,"cat selected ="+selected_cat_status);
                Log.d(log,"catalog selected ="+selected_catalog_status);
                Log.d(log,"code selected ="+selected_code_status);
                Log.d(log,"group selected ="+selected_group_status);

                if (selected_description_substatus.contains("Hold")) {
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

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_button.setClickable(true);
                submit_button.setEnabled(true);
                if (selected_description_status.contains("Hold")) {
                        if (validateDataHold()) {
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            Log.d(log,"date ="+df.format(c));
                            ngUserListModel.setSub_status(selected_description_status);
                            ngUserListModel.setDelay_date(et_delayDateValue.getText().toString().trim());
                            ngUserListModel.setJmr_no(jmrNo);
                            ngUserListModel.setClaim(true);
                            ngUserListModel.setMobile_no(tv_mobileNoValue.getText().toString().trim());
                            ngUserListModel.setCat_id(selected_cat_status);
                            ngUserListModel.setCatalog(selected_catalog_status);
                            ngUserListModel.setCode(selected_code_status);
                            ngUserListModel.setReason(selected_description_status);
                            ngUserListModel.setAlt_number(tv_alternateNoValue.getText().toString().trim());
                            ngUserListModel.setSub_status(selected_description_substatus + " Tech Remarks - "+et_nghold_remarks.getText().toString().trim());
                            Log.d(log,"sub status  ="+selected_description_substatus + " Tech Remarks - "+et_nghold_remarks.getText().toString().trim());
                            ngUserListModel.setNg_update_date(df.format(c));
                            if (isNetworkConnected(NgSupUserDetailsActivity.this)) {
                                submitData(ngUserListModel);
                            } else {
                                Utils.showToast(NgSupUserDetailsActivity.this, "Please check internet connection");
                            }
                    }
                } else if (validateDataDone()) {
                    initialReading = et_initialReading.getText().toString().trim();
                    burnerDetails = et_burnerDetails.getText().toString().trim();
                    String newMob = tv_mobileNoValue.getText().toString().trim();
                    conversationDate = et_conversationDate.getText().toString().trim();

                    Intent intent = new Intent(NgSupUserDetailsActivity.this, NgSupDoneActivity.class);
                    intent.putExtra("bpno",intentngUserListModel.getBp_no());
                    intent.putExtra("jmrNo", jmrNo);
                    intent.putExtra("mAssignDate", mAssignDate);
                    intent.putExtra("initialReading", initialReading);
                    intent.putExtra("burnerDetails", burnerDetails);
                    intent.putExtra("conversationDate", conversationDate);
                    intent.putExtra("catid",selected_cat_status);
                    intent.putExtra("catalog",selected_catalog_status);
                    intent.putExtra("code",selected_code_status);
                    intent.putExtra("reason",selected_description_status);
                    intent.putExtra("substatus","");
                    intent.putExtra("mobile",newMob);
                    intent.putExtra("meter", correctedmeterno);
                    intent.putExtra("meterStatus", meterincorrect);
                    intent.putExtra("altmob", tv_alternateNoValue.getText().toString().trim());
                    intent.putExtra("model", intentngUserListModel);
                    startActivity(intent);
                }

            }
        });

        et_delayDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                cldr.add(Calendar.DATE, 0);
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                pickerDialog_Date = new DatePickerDialog(NgSupUserDetailsActivity.this,
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
                pickerDialog_Date.getDatePicker().setMinDate(cldr.getTimeInMillis());
                pickerDialog_Date.show();
            }


        });

        et_conversationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                cldr.add(Calendar.DATE, -2);
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                pickerDialog_Date = new DatePickerDialog(NgSupUserDetailsActivity.this,
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
                pickerDialog_Date.getDatePicker().setMinDate(cldr.getTimeInMillis());
                pickerDialog_Date.getDatePicker().setMaxDate(System.currentTimeMillis());
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
                            int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                            hold_image.setImageBitmap(scaled);
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
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
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
                            int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                            hold_image.setImageBitmap(scaled);
                        }
                        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
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
                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(NgSupUserDetailsActivity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_ON_HOLD);
                    }
                });
        myAlertDialog.show();

    }

    private void updateNgDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        AlertDialog OptionDialog = myAlertDialog.create();
        myAlertDialog.setCancelable(false);
        myAlertDialog.setTitle("Please Update Customer KYC");
        myAlertDialog.setMessage(" Click Ok to Update...");
        myAlertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                       updateNg();
                    }
                });
        myAlertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        OptionDialog.dismiss();
                    }
                });
        myAlertDialog.show();
   }

    private void findViews() {
        tv_houseNoValue = findViewById(R.id.tv_houseNoValue);
        tv_societyValue = findViewById(R.id.tv_societyValue);
        tv_blockValue = findViewById(R.id.tv_blockValue);
        tv_areaValue = findViewById(R.id.tv_areaValue);
        ll_ngStatusreason = findViewById(R.id.ll_ngStatusreason);
        ll_meterReading = findViewById(R.id.ll_meterReading);
        spinner_ngStatus = findViewById(R.id.spinner_ngStatus);
        spinner_ngStatusReason = findViewById(R.id.spinner_ngStatusReason);
        ll_hold = findViewById(R.id.ll_hold);
        tv_ngUserName = findViewById(R.id.tv_ngUserName);
        rfcdate_value = findViewById(R.id.tv_rfcdate_value);
        tv_jmr_no = findViewById(R.id.tv_jmr_no);
        tv_bp_no = findViewById(R.id.tv_bp_no);
        tv_mobileNoValue = findViewById(R.id.tv_mobileNoValue);
        tv_alternateNoValue = findViewById(R.id.tv_alternateNoValue);
        submit_button = findViewById(R.id.submit_button);
        picture_button = findViewById(R.id.picture_button);
        hold_image = findViewById(R.id.hold_image);
        tv_delayDate = findViewById(R.id.tv_delayDate);
        // String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        tv_floorValue = findViewById(R.id.tv_floorValue);
        audioFile_button = findViewById(R.id.audioFile_button);
        str1 = (TextView) findViewById(R.id.filename1);
        tv_cityvalue = findViewById(R.id.tv_cityvalue);
        tv_preferredDateValue = findViewById(R.id.tv_preferredDateValue);
        tv_categoryNameValue = findViewById(R.id.tv_categoryNameValue);
        et_delayDateValue = findViewById(R.id.et_delayDateValue);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
       // et_delayDateValue.setText(df.format(c));
        et_initialReading = findViewById(R.id.et_initialReading);
        et_burnerDetails = findViewById(R.id.et_burnerDetails);
        et_conversationDate = findViewById(R.id.et_conversationDate);
        et_conversationDate.setText(df.format(c));
        tv_serviceNameValue = findViewById(R.id.tv_serviceNameValue);
        meterRadiogroup = findViewById(R.id.radioGroup_cm);
        rfcreadingValue = findViewById(R.id.tv_rfcreading_value);
        metermakeValue = findViewById(R.id.tv_metermake_value);
        meternoValue = findViewById(R.id.tv_meter_no_value);
        metertypeValue = findViewById(R.id.tv_metertype_value);
        et_nghold_remarks = findViewById(R.id.et_nghold_remarks);
        ll_cmeter = findViewById(R.id.ll_correctedmeter);

        corrected_meternoValue = findViewById(R.id.tv_corrected_meter_no_value);
        meterRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_cmyes:
                        ll_cmeter.setVisibility(View.GONE);
                        meterincorrect = false;
                        break;
                    case R.id.radioButton_cmno:
                        ll_cmeter.setVisibility(View.VISIBLE);
                        meterincorrect = true;
                        break;
                }
            }
        });
    }

    private void loadNgStatusSpinners() {
        code_status.clear();
        group_status.clear();
        description_status.clear();
        cat_status.clear();
        catalog_status.clear();
        CommonUtils.startProgressBar(NgSupUserDetailsActivity.this,"Loading...");
        Log.d(log, "spinner master url = " + Api.BASE_URL + "api/statusjmr?group="+intentngUserListModel.getCode_group());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.BASE_URL + "api/statusjmr?group="+intentngUserListModel.getCode_group(), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d(log,"master_list"+ response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String description = jsonObject1.getString("description");
                        String group = jsonObject1.getString("group");
                        String cat_id = jsonObject1.getString("cat_id");
                        String code = jsonObject1.getString("code");
                        String catalog = jsonObject1.getString("catalog");
                        String id = jsonObject1.getString("id");
                        cat_status.add(cat_id);
                        description_status.add(description);
                        group_status.add(group);
                        code_status.add(code);
                        catalog_status.add(catalog);
                        status_id.add(id);
                    }

                    spinner_ngStatus.setAdapter(new ArrayAdapter<String>(NgSupUserDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, description_status));
                } catch (JSONException e) {
                    CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this);
                    CommonUtils.toast_msg(NgSupUserDetailsActivity.this,"Oops..Error loading status!!");
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this);
                CommonUtils.toast_msg(NgSupUserDetailsActivity.this,"Oops..TimeOut!!");
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadResonSpinner() {
        code_substatus.clear();
        group_substatus.clear();
        description_substatus.clear();
        cat_substatus.clear();
        catalog_substatus.clear();
        CommonUtils.startProgressBar(NgSupUserDetailsActivity.this,"Loading...");
        Log.d(log, "spinner master url = " + Api.BASE_URL + "api/substatusjmr?group="+intentngUserListModel.getCode_group()+"&status_id="+selected_status_id);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.BASE_URL + "api/substatusjmr?group="+intentngUserListModel.getCode_group()+"&status_id="+selected_status_id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this );
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d(log,"master_list"+ response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String description = jsonObject1.getString("description");
                        String group = jsonObject1.getString("group");
                        String cat_id = jsonObject1.getString("cat_id");
                        String code = jsonObject1.getString("code");
                        String catalog = jsonObject1.getString("catalog");
                        cat_substatus.add(cat_id);
                        description_substatus.add(description);
                        group_substatus.add(group);
                        code_substatus.add(code);
                        catalog_substatus.add(catalog);
                    }
                    spinner_ngStatusReason.setAdapter(new ArrayAdapter<String>(NgSupUserDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, description_substatus));

                } catch (JSONException e) {
                    CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this );
                    CommonUtils.toast_msg(NgSupUserDetailsActivity.this,"Oops..Error loading Sub-status!!");
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this );
                CommonUtils.toast_msg(NgSupUserDetailsActivity.this,"Oops..Timeout!!");
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    private void setText(NguserListModel nguserListModel) {

        tv_ngUserName.setText(nguserListModel.getCustomer_name().toUpperCase());
        tv_bp_no.setText("BP - " + nguserListModel.getBp_no());
        tv_jmr_no.setText("JMR - " + nguserListModel.getJmr_no());
        tv_houseNoValue.setText(nguserListModel.getHouse_no());
        tv_societyValue.setText(nguserListModel.getSociety());
        tv_blockValue.setText(nguserListModel.getBlock_qtr());
        rfcdate_value.setText(nguserListModel.getRfc_date());
        tv_areaValue.setText(nguserListModel.getArea());
        tv_mobileNoValue.setText(nguserListModel.getMobile_no());
        tv_alternateNoValue.setText(nguserListModel.getAlt_number());
        tv_cityvalue.setText(nguserListModel.getCity());
        tv_preferredDateValue.setText(nguserListModel.getClaim_date());
        tv_floorValue.setText(nguserListModel.getFloor());
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

        metertypeValue.setText(nguserListModel.getMeter_type());
        meternoValue.setText(nguserListModel.getMeter_no());
        metermakeValue.setText(nguserListModel.getMeter_make());
        rfcreadingValue.setText(nguserListModel.getRfc_initial_reading());

    }

    private boolean validateDataHold() {
        boolean isDataValid = true;
         if (TextUtils.isEmpty(holdImageBinary)) {
            Toast.makeText(NgSupUserDetailsActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
            isDataValid = false;
            return isDataValid;
        }
         else if(TextUtils.isEmpty(selected_cat_status))
         {
             Toast.makeText(NgSupUserDetailsActivity.this, "Please Select Status", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }
         else if (tv_mobileNoValue.getText().toString().trim()==null||tv_mobileNoValue.getText().toString().trim().equalsIgnoreCase("null")||tv_mobileNoValue.getText().toString().trim().length()<10 ||tv_mobileNoValue.getText().toString().trim().isEmpty()) {
             tv_mobileNoValue.setError("Enter valid Mobile no.");
             Toast.makeText(NgSupUserDetailsActivity.this, "Enter valid Mobile no.", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }
         else if (tv_mobileNoValue.getText().toString().trim().length()>10)
         {
             tv_mobileNoValue.setError("Mobile no. in valid");
             Toast.makeText(NgSupUserDetailsActivity.this, " Invalid Mobile no.", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }
         else if (et_nghold_remarks.getText().toString().trim().isEmpty())
         {
             et_nghold_remarks.setError("Remarks Mandatory");
             Toast.makeText(NgSupUserDetailsActivity.this, "Pls enter remarks", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }
         else if (et_delayDateValue.getText().toString().trim().isEmpty())
         {
             et_delayDateValue.setError("Follow Up-date is Mandatory");
             Toast.makeText(NgSupUserDetailsActivity.this, "Select Follow Up-date", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }
         else {
            return isDataValid;
        }
    }

    private boolean validateDataDone() {
        boolean isDataValid = true;
        double initial_reading;
        double rfc_reading;
        double peak_reading;
        int burner_reading  ;


        try {
              initial_reading  = Double.parseDouble(et_initialReading.getText().toString().trim());
              rfc_reading = Double.parseDouble(intentngUserListModel.getRfc_initial_reading());
              peak_reading = rfc_reading+2.0;
              burner_reading  = Integer.parseInt(et_burnerDetails.getText().toString().trim());
              Log.d(log,"rfc reading = "+rfc_reading+" initial reading = "+initialReading);
        }
        catch (Exception e){
            initial_reading = 0.0;
            rfc_reading = 0.0;
            peak_reading = rfc_reading+2.0;
            burner_reading = 0;
        }


         if (TextUtils.isEmpty(et_initialReading.getText().toString().trim())) {
            et_initialReading.setError("Field can't be empty");
            isDataValid = false;
            return isDataValid;
        }
       else if(intentngUserListModel.getCustomer_name().toUpperCase().contains("CHILD"))
        {
            Toast.makeText(NgSupUserDetailsActivity.this, "KYC is Not updated", Toast.LENGTH_SHORT).show();
            isDataValid = false;
            return isDataValid;
        }
         else if (rfc_reading>initial_reading) {
             et_initialReading.setError("Initial reading can't be smaller than RFC reading");
             isDataValid = false;
             return isDataValid;
         }
         else if (initial_reading>peak_reading) {
             et_initialReading.setError("Unexpected Initial reading");
             CommonUtils.toast_msg(this,"Unexpected increase in Initial reading");
             isDataValid = false;
             return isDataValid;
         }
         else if (tv_mobileNoValue.getText().toString().trim()==null||tv_mobileNoValue.getText().toString().trim().equalsIgnoreCase("null")||tv_mobileNoValue.getText().toString().trim().length()<10 ||tv_mobileNoValue.getText().toString().trim().isEmpty()) {
             tv_mobileNoValue.setError("Enter valid Mobile no.");
             Toast.makeText(NgSupUserDetailsActivity.this, "Enter valid Mobile no.", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }
         else if (tv_mobileNoValue.getText().toString().trim().length()>10)
         {
             tv_mobileNoValue.setError("Mobile no. in valid");
             Toast.makeText(NgSupUserDetailsActivity.this, " Invalid Mobile no.", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }

         else if (TextUtils.isEmpty(et_burnerDetails.getText().toString().trim())) {
            et_burnerDetails.setError("Field can't be empty");
            isDataValid = false;
            return isDataValid;
        }
         else if (burner_reading>6) {
             et_burnerDetails.setError("Burner reading can't be greater than 6");
             Toast.makeText(NgSupUserDetailsActivity.this, "Burner reading can't be greater than 6", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }
         else if (TextUtils.isEmpty(et_conversationDate.getText().toString().trim())) {
            et_conversationDate.setError("Field can't be empty");
            isDataValid = false;
            return isDataValid;
        }
         else if(TextUtils.isEmpty(selected_cat_status))
         {
             Toast.makeText(NgSupUserDetailsActivity.this, "Please Select Status", Toast.LENGTH_SHORT).show();
             isDataValid = false;
             return isDataValid;
         }
         else if(meterincorrect)
         {
             correctedmeterno = corrected_meternoValue.getText().toString().trim().toUpperCase();
             if (TextUtils.isEmpty(correctedmeterno)) {
                 Toast.makeText(NgSupUserDetailsActivity.this, "Please Enter Corrected Meter No", Toast.LENGTH_SHORT).show();
                 isDataValid = false;

             }
             return isDataValid;
         }
         else {
            return isDataValid;
        }
    }

    private void submitData(NguserListModel nguserListModel) {
        CommonUtils.startProgressBar(NgSupUserDetailsActivity.this,"Submitting Data..." );
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmrNo, nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {
            @Override
            public void onResponse(Call<List<NguserListModel>> call, Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>", response.toString());

                if (response.code() == 200) {
                    if (!TextUtils.isEmpty(mediaPath1)) {
                        // uploadFile();
                    } else {
                        CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this );
                        Toast.makeText(getApplicationContext(), "Data submitted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NgSupUserDetailsActivity.this, NgSupListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                } else {
                    CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this );
                    Toast.makeText(getApplicationContext(), "Data not submitted", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error", t.getLocalizedMessage());
                CommonUtils.dismissProgressBar(NgSupUserDetailsActivity.this );
                // submitData(nguserListModel);
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void updateNg()
    {
        ProgressDialog progressDialog = ProgressDialog.show(this, "", "Loading", true);
        progressDialog.setCancelable(false);
        Log.d(log, "updateNg = " +Constants.REFRESH_NG +intentngUserListModel.getBp_no());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.REFRESH_NG +intentngUserListModel.getBp_no(), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.d(log,"response"+ response);
                    String status = jsonObject1.getString("status");
                    if (status.equals("200")) {
                        CommonUtils.toast_msg(NgSupUserDetailsActivity.this,jsonObject1.getString("message"));
                        JSONObject jsonObject= jsonObject1.getJSONObject("details");
                        Log.d(log,"details"+ jsonObject.toString());
                        intentngUserListModel.setCustomer_name(jsonObject.getString("name"));
                        intentngUserListModel.setMobile_no(jsonObject.getString("mob"));
                        intentngUserListModel.setAlt_number(jsonObject.getString("mob"));
                        intentngUserListModel.setSociety(jsonObject.getString("society"));
                        intentngUserListModel.setHouse_no(jsonObject.getString("hno"));
                        intentngUserListModel.setBlock_qtr(jsonObject.getString("block"));
                        intentngUserListModel.setFloor(jsonObject.getString("floor"));
                        setText(intentngUserListModel);

                    }
                    else
                    {
                        CommonUtils.toast_msg(NgSupUserDetailsActivity.this,jsonObject1.getString("message"));
                    }
                    } catch (JSONException e) {
                    progressDialog.dismiss();
                    CommonUtils.toast_msg(NgSupUserDetailsActivity.this,"Oops..Error loading status!!");
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                CommonUtils.toast_msg(NgSupUserDetailsActivity.this,"Oops..TimeOut!!");
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



}
