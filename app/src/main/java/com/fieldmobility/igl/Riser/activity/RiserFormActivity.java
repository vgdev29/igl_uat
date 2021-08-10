package com.fieldmobility.igl.Riser.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.fieldmobility.igl.Activity.NICustomerActivity;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.google.gson.Gson;
import com.opensooq.supernova.gligar.GligarPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Environment.getExternalStorageDirectory;

public class RiserFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private ArrayList<String> propertyTypeList = new ArrayList<>();
    private ArrayList<String> gasTypeList = new ArrayList<>();
    private ArrayList<String> hseList = new ArrayList<>();
    private ArrayList<String> giList = new ArrayList<>();
    private ArrayList<String> areaTypeList = new ArrayList<>();

    String selectedPropertyType = "low-rise", selectedGasType = "Non Gasified", selectedHSE = "", selectedRiserLength = "Select Diameter", selectedIsolationValue = "Select Diameter", selectedLateralTapping = "", selectedAreaType = "Off Line/Project";
    boolean isRiserTestingDone, isRiserLayingDone, isRiserCommissioningDone;
    String imagePathOne = "", imagePathTwo = "", imagePathOptional = "";
    MaterialDialog materialDialog;
    RiserListingModel.BpDetails.User dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riser_form);
        if (getIntent().hasExtra("data")) {
            String data = getIntent().getStringExtra("data");
            dataModel = new Gson().fromJson(data, RiserListingModel.BpDetails.User.class);
        }
        feedListData();
        findViews();
        if (dataModel != null) {
            initViews();
        }

    }K

    public void callSubmitApi() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();

        String login_request = "Riser form Submission";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.RISER__PROJECT_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("Code").equals("200")) {
                                String Msg = json.getString("Message");
                                CommonUtils.toast_msg(RiserFormActivity.this,Msg);
                                finish();

                            } else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object", obj.toString());
                        JSONObject error1 = obj.getJSONObject("error");
                        String error_msg = error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {


//                    params.put("allocation", tv_allocation_num.getText().toString().trim());
                    params.put("agent_name", tv_agent_name.getText().toString().trim());
                    params.put("riser_no", "2");
                    params.put("city", tv_city.getText().toString().trim());
                    params.put("zone", tv_zone.getText().toString().trim());
                    params.put("area", tv_area.getText().toString().trim());
                    params.put("society", tv_society.getText().toString().trim());
                    params.put("street", dataModel.getStreetGaliRoad());
                    params.put("bp_number", dataModel.getBpNumber());

//                    params.put("user_id", et_connected_house.getText().toString().trim());
                    params.put("property_type", selectedPropertyType);
                    params.put("gas_type", selectedGasType);
                    params.put("hse_floor", selectedHSE);
                    params.put("length", selectedRiserLength);
                    params.put("isolation_valve", selectedIsolationValue);
                    String laying=isRiserLayingDone?"1":"0";
                    params.put("laying",laying );
                    String testing=isRiserTestingDone?"1":"0";
                    params.put("testing",testing );
                    String commi=isRiserCommissioningDone?"1":"0";
                    params.put("commissioning",commi );
                    params.put("lateral_tapping", selectedLateralTapping);
                    params.put("area_type", selectedAreaType);
                    params.put("site_photo1", imagePathOne);
                    params.put("site_photo2", imagePathTwo);
                    params.put("site_photo3", imagePathOptional);


                } catch (Exception e) {
                }
                return params;
            }

        };
        jr.setRetryPolicy(new DefaultRetryPolicy(25 * 10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }

    private void initViews() {
        ArrayAdapter<String> adapterProperty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, propertyTypeList);
        adapterProperty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_property_type.setAdapter(adapterProperty);

        ArrayAdapter<String> gasType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gasTypeList);
        gasType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gas_type.setAdapter(gasType);

        ArrayAdapter<String> hseType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hseList);
        hseType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_hse.setAdapter(hseType);

        ArrayAdapter<String> GITyppe = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, giList);
        GITyppe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_riser_length.setAdapter(GITyppe);
        sp_isolation_value.setAdapter(GITyppe);
        sp_lateral_tapping.setAdapter(GITyppe);

        ArrayAdapter<String> areaTyppe = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areaTypeList);
        areaTyppe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_area_type.setAdapter(areaTyppe);

//        tv_allocation_num.setText(dataModel.getAllocation().getAllocationNumber());
//        tv_sub_allocatio.setText(dataModel.getSuballocationNumber());
        tv_agent_name.setText(dataModel.getFirstName()+" "+dataModel.getLastName());
//        tv_po_num.setText(dataModel.getAllocation().getPoNumber());
        tv_city.setText(dataModel.getCityRegion());
        tv_zone.setText(dataModel.getZone());
        tv_area.setText(dataModel.getArea());
        tv_society.setText(dataModel.getSociety());

        et_gali.setText(dataModel.getStreetGaliRoad());
        et_bp_num.setText(dataModel.getBpNumber());
    }

    TextView /*tv_allocation_num, tv_sub_allocatio,*/ tv_agent_name, /*tv_po_num,*/ tv_city, tv_zone, tv_area, tv_society;
    EditText et_gali, et_bp_num/*, et_connected_house*/;
    RadioGroup rg_riser_laying, rg_riser_testing, rg_riser_commissioning;
    ImageView iv_one, iv_two, iv_three;
    FrameLayout fl_image1, fl_image2, fl_image3;
    Spinner sp_property_type, sp_gas_type, sp_hse, sp_riser_length, sp_isolation_value, sp_lateral_tapping, sp_area_type;
    Button approve_button;

    private void findViews() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        tv_allocation_num = findViewById(R.id.tv_allocation_num);
//        tv_sub_allocatio = findViewById(R.id.tv_sub_allocatio);
        tv_agent_name = findViewById(R.id.tv_agent_name);
//        tv_po_num = findViewById(R.id.tv_po_num);
        tv_city = findViewById(R.id.tv_city);
        tv_zone = findViewById(R.id.tv_zone);
        tv_area = findViewById(R.id.tv_area);
        tv_society = findViewById(R.id.tv_society);
        et_gali = findViewById(R.id.et_gali);
        et_bp_num = findViewById(R.id.et_bp_num);
//        et_connected_house = findViewById(R.id.et_connected_house);
        rg_riser_laying = findViewById(R.id.rg_riser_laying);
        rg_riser_laying.setOnCheckedChangeListener(this);
        rg_riser_testing = findViewById(R.id.rg_riser_testing);
        rg_riser_testing.setOnCheckedChangeListener(this);
        rg_riser_commissioning = findViewById(R.id.rg_riser_commissioning);
        rg_riser_commissioning.setOnCheckedChangeListener(this);
        approve_button = findViewById(R.id.approve_button);
        approve_button.setOnClickListener(this);
        iv_three = findViewById(R.id.iv_three);
        iv_two = findViewById(R.id.iv_two);
        iv_one = findViewById(R.id.iv_one);
        fl_image3 = findViewById(R.id.fl_image3);
        fl_image3.setOnClickListener(this);

        fl_image2 = findViewById(R.id.fl_image2);
        fl_image2.setOnClickListener(this);
        fl_image1 = findViewById(R.id.fl_image1);
        fl_image1.setOnClickListener(this);

        //ALL SPINNER VIEWS BELOW
        sp_property_type = findViewById(R.id.sp_property_type);
        sp_property_type.setOnItemSelectedListener(this);
        sp_gas_type = findViewById(R.id.sp_gas_type);
        sp_gas_type.setOnItemSelectedListener(this);
        sp_hse = findViewById(R.id.sp_hse);
        sp_hse.setOnItemSelectedListener(this);
        sp_riser_length = findViewById(R.id.sp_riser_length);
        sp_riser_length.setOnItemSelectedListener(this);
        sp_isolation_value = findViewById(R.id.sp_isolation_value);
        sp_isolation_value.setOnItemSelectedListener(this);
        sp_lateral_tapping = findViewById(R.id.sp_lateral_tapping);
        sp_lateral_tapping.setOnItemSelectedListener(this);
        sp_area_type = findViewById(R.id.sp_area_type);
        sp_area_type.setOnItemSelectedListener(this);
    }

    private void feedListData() {
        //PROPERTY TYPE DATA
        propertyTypeList.add("low-rise");
        propertyTypeList.add("high-rise");

        //GAS TYPE DATA
        gasTypeList.add("Non Gasified");
        gasTypeList.add("Gasified");

        //HSE data
        hseList.add("Select HSE");
        hseList.add("HSE (g+4)");
        hseList.add("HSE(g+14 & above)");

        //RISER LENGTH, ISOLATION VALUE, LATERAL TAPPING
        giList.add("Select Diameter");
        giList.add("GI ½");
        giList.add("GI 3/4");
        giList.add("GI 1");
        giList.add("GI 2");

        //AREA TYPE LIST
        areaTypeList.add("Off Line/Project"); //default
        areaTypeList.add("Online/O&M"); //if lateral tapping value selected


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == sp_riser_length) {
            selectedRiserLength = giList.get(position);

        } else if (parent == sp_property_type) {
            selectedPropertyType = propertyTypeList.get(position);

        } else if (parent == sp_lateral_tapping) {
            selectedLateralTapping = giList.get(position);

        } else if (parent == sp_isolation_value) {
            selectedIsolationValue = giList.get(position);

        } else if (parent == sp_hse) {
            selectedHSE = hseList.get(position);

        } else if (parent == sp_gas_type) {
            selectedGasType = gasTypeList.get(position);

        } else if (parent == sp_area_type) {
            selectedAreaType = areaTypeList.get(position);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == fl_image1) {
            openImagePickerOption(isForImage1);

        } else if (v == fl_image2) {
            openImagePickerOption(isForImage2);


        } else if (v == fl_image3) {
            openImagePickerOption(isForImage3);


        } else if (v == approve_button) {
            if (isValidData()) {
                callSubmitApi();

            }

        }

    }

    private boolean isValidData() {
        boolean isValid = false;
//        if (/*tv_allocation_num.getText().length() > 0 &&*/
//                /*tv_sub_allocatio.getText().length() > 0 &&*/
//                tv_agent_name.getText().length() > 0 &&
///*
//                tv_po_num.getText().length() > 0 &&
//*/
//                tv_zone.getText().length() > 0 &&
//                tv_society.getText().length() > 0
//        ) {
//            isValid = true;
//        } else {
//            isValid = false;
//            return isValid;
//        }
        if (!selectedRiserLength.equals("Select Diameter") && !selectedIsolationValue.equals("Select Diameter")) {
            isValid = true;
        } else {
            isValid = false;
            CommonUtils.toast_msg(RiserFormActivity.this,"Please Select Mandatory Fields");
            return isValid;
        }
        if (!imagePathOne.isEmpty() && !imagePathTwo.isEmpty())
            isValid = true;
        else {
            isValid = false;
            CommonUtils.toast_msg(RiserFormActivity.this,"Please Upload Mandatory Photos");
            return isValid;
        }

        return isValid;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == rg_riser_commissioning) {
            isRiserCommissioningDone = checkedId == R.id.rb_rcomm_pos;

        } else if (group == rg_riser_laying) {
            isRiserLayingDone = checkedId == R.id.rb_rlaying_pos;

        } else if (group == rg_riser_testing) {
            isRiserTestingDone = checkedId == R.id.rb_rtesting_pos;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CODE_PICK_IMAGE_1_GALLERY:
            case REQUEST_CODE_PICK_IMAGE_2_GALLERY:
            case REQUEST_CODE_PICK_IMAGE_3_GALLERY:
                if (resultCode == this.RESULT_OK && data != null && data.getData() != null) {
//                    String pathsList[] = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); // return list of selected images paths.
                    Uri filePathHomeAddress = data.getData();
                    Bitmap resultBitmap = null;
                    try {
                        resultBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathHomeAddress);
                        String selectedImagePath = getPath(filePathHomeAddress);
                        Log.e("image_path_aadhar+,", "" + selectedImagePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (resultBitmap != null){
                        if (requestCode==REQUEST_CODE_PICK_IMAGE_1_GALLERY){
                            imagePathOne=change_to_binary(resultBitmap);
                            iv_one.setImageBitmap(resultBitmap);
                        }
                        if (requestCode==REQUEST_CODE_PICK_IMAGE_2_GALLERY){
                            imagePathTwo=change_to_binary(resultBitmap);
                            iv_two.setImageBitmap(resultBitmap);
                        }
                        if (requestCode==REQUEST_CODE_PICK_IMAGE_3_GALLERY){
                            imagePathOptional=change_to_binary(resultBitmap);
                            iv_three.setImageBitmap(resultBitmap);

                        }
                    }
                }


        }
    }
    private String  change_to_binary(Bitmap bitmapOrg) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        return ba1;
    }
    final private static int REQUEST_CODE_PICK_IMAGE_1_GALLERY = 1, REQUEST_CODE_PICK_IMAGE_1_CAMERA = 11, REQUEST_CODE_PICK_IMAGE_2_GALLERY = 2, REQUEST_CODE_PICK_IMAGE_2_CAMERA = 22, REQUEST_CODE_PICK_IMAGE_3_GALLERY = 3, REQUEST_CODE_PICK_IMAGE_3_CAMERA = 33;
    final private static int isForImage1 = 10, isForImage2 = 20, isForImage3 = 30;

    private void openImagePickerOption(int isFor) {
        int requestCodeCamera = -1, requestCodeGallery = -1;
        switch (isFor) {
            case isForImage1:
                requestCodeGallery = REQUEST_CODE_PICK_IMAGE_1_GALLERY;
                requestCodeCamera = REQUEST_CODE_PICK_IMAGE_1_CAMERA;
                break;
            case isForImage2:
                requestCodeGallery = REQUEST_CODE_PICK_IMAGE_2_GALLERY;
                requestCodeCamera = REQUEST_CODE_PICK_IMAGE_2_CAMERA;
                break;
            case isForImage3:
                requestCodeGallery = REQUEST_CODE_PICK_IMAGE_3_GALLERY;
                requestCodeCamera = REQUEST_CODE_PICK_IMAGE_3_CAMERA;
                break;

        }
//        new GligarPicker().requestCode(requestCodeGallery).withActivity(this).show();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCodeGallery);


//        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
//        myAlertDialog.setTitle("Upload Pictures Option");
//        myAlertDialog.setMessage("How do you want to set your picture?");
//        int finalRequestCodeGallery = requestCodeGallery;
//        myAlertDialog.setPositiveButton("Gallery",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), finalRequestCodeGallery);
//                    }
//                });
//        int finalRequestCodeCamera = requestCodeCamera;
//        myAlertDialog.setNegativeButton("Camera",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
//                        Uri photoURI = FileProvider.getUriForFile(RiserFormActivity.this, getApplicationContext().getPackageName() + ".provider", f);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(intent, finalRequestCodeCamera);
//                    }
//                });
//        myAlertDialog.show();
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

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }




}