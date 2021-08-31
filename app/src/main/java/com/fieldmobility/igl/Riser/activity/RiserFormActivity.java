package com.fieldmobility.igl.Riser.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.fieldmobility.igl.Activity.BP_Creation_Form;
import com.fieldmobility.igl.Activity.MITDDoneActivity;
import com.fieldmobility.igl.Activity.NICustomerActivity;
import com.fieldmobility.igl.Activity.RFC_Connection_Activity;
import com.fieldmobility.igl.Adapter.AutoSuggestAdapter;
import com.fieldmobility.igl.Adapter.ConnectedHouseAdapter;
import com.fieldmobility.igl.Adapter.PlainTextListAdapter;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.ImageCompression;
import com.fieldmobility.igl.Helper.ScreenshotUtils;
import com.fieldmobility.igl.Listeners.PlainTextListItemSelectListener;
import com.fieldmobility.igl.Model.ConnectedHouseModel;
import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

public class RiserFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private ArrayList<String> propertyTypeList = new ArrayList<>();
    private ArrayList<String> gasTypeList = new ArrayList<>();
    private ArrayList<String> hseList = new ArrayList<>();
    private ArrayList<String> giList = new ArrayList<>();
    private ArrayList<String> areaTypeList = new ArrayList<>();
    int ConnectedHouse = 0;
    int connectedHseMaxLimit = 5;
    ConnectedHouseAdapter connectedHouseAdapter;
    String selectedPropertyType = "low-rise", selectedGasType = "Non Gasified", selectedHSE = "", selectedRiserLength = "Select Diameter", selectedIsolationValue = "Select Diameter", selectedLateralTapping = "", selectedAreaType = "Off Line/Project";
    boolean isRiserTestingDone, isRiserLayingDone, isRiserCommissioningDone;
    String imagePathOne = "", imagePathTwo = "", imagePathOptional = "";
    MaterialDialog materialDialog;
    RiserListingModel.BpDetails.User dataModel;
    ArrayList<String> Floor_name1 = new ArrayList<>();

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

    }

    public void callSubmitApi() {
        try {
            String lat = "", log = "";
            if (!Utils.getLocationUsingInternet(RiserFormActivity.this).isEmpty()) {
                String latLong[] = Utils.getLocationUsingInternet(RiserFormActivity.this).split("/");
                lat = latLong[0];
                log = latLong[1];
            }
            String bplistData="";
            //if (rg_pbc_houses.getCheckedRadioButtonId()==R.id.rb_yes){
                JSONObject jsonArray = connectedHouseAdapter.getJsonData();
                bplistData=String.valueOf(jsonArray);
                Log.d("search", "jsonarray = " + jsonArray.toString());
           // }
            String riserNum = "R" + dataModel.getZone() + Utils.getRandomNumWithChar(5).toUpperCase();
            String laying = isRiserLayingDone ? "1" : "0";
            String testing = isRiserTestingDone ? "1" : "0";
            String commi = isRiserCommissioningDone ? "1" : "0";
            materialDialog = new MaterialDialog.Builder(this)
                    .content("Please wait....")
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            Log.d("riser", Constants.RISER__PROJECT_REPORT);
            String login_request = "Riser form Submission";
            String uploadId = UUID.randomUUID().toString();

            MultipartUploadRequest req = new MultipartUploadRequest(RiserFormActivity.this, uploadId, Constants.RISER__PROJECT_REPORT);
            req.addFileToUpload(imagePathOne, "site_photo")
                    .addFileToUpload(imagePathTwo, "site_photo")
                    .addParameter("riser_no", riserNum)
                    .addParameter("city", tv_city.getText().toString().trim())
                    .addParameter("zone", tv_zone.getText().toString().trim())
                    .addParameter("area", tv_area.getText().toString().trim())
                    .addParameter("society", tv_society.getText().toString().trim())
                    .addParameter("street", dataModel.getStreetGaliRoad())
                    .addParameter("bp_number", dataModel.getBpNumber())
                    .addParameter("property_type", selectedPropertyType)
                    .addParameter("gas_type", selectedGasType)
                    .addParameter("hse_floor", selectedHSE)
//                    .addParameter("riser_length", selectedRiserLength)
//                    .addParameter("isolation_valve", selectedIsolationValue)
                    .addParameter("rl12", et_rl12.getText().toString())
                    .addParameter("rl34", et_rl34.getText().toString())
                    .addParameter("rl1", et_rl1.getText().toString())
                    .addParameter("rl2", et_rl2.getText().toString())
                    .addParameter("iv12", et_iv12.getText().toString())
                    .addParameter("iv34", et_iv34.getText().toString())
                    .addParameter("iv1", et_iv1.getText().toString())
                    .addParameter("iv2", et_iv2.getText().toString())
                    .addParameter("laying", laying)
                    .addParameter("testing", testing)
                    .addParameter("commissioning", commi)
                    .addParameter("lateral_tapping", selectedLateralTapping)
                    .addParameter("area_type", selectedAreaType)
                    .addParameter("connected_bp", "" + ConnectedHouse)
                    .addParameter("connected_house", "" + ConnectedHouse)
//                   .addParameter("hse_gi", selectedLateralTapping)
                    .addParameter("bplist", bplistData)
                    .addParameter("total_ib", et_ib.getText().toString().trim()) // Edittext
                    .addParameter("contractor_id", dataModel.getRfcadmin()) //admin
                    .addParameter("tpi_id", dataModel.getRiserTpi()) //tpi
                    .addParameter("supervisor_id", dataModel.getRiserSup()) //riserSup
                    .addParameter("latitude", lat) //KycResubmission method
                    .addParameter("longitude", log);
            if (imagePathOptional != null && !imagePathOptional.isEmpty()) {
                req.addFileToUpload(imagePathOptional, "site_photo");
            }

            req.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(Context context, UploadInfo uploadInfo) {
                    materialDialog.show();
                }

                @Override
                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                    exception.printStackTrace();
                    materialDialog.dismiss();
                    //Dilogbox_Error();
                    Log.e("Uplodeerror++", uploadInfo.getSuccessfullyUploadedFiles().toString());
                }

                @Override
                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                    materialDialog.dismiss();
                    String str = serverResponse.getBodyAsString();
                    Log.e("UPLOADEsinin++", str);
                    final JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("200")) {
                            String Msg = jsonObject.getString("Message");
                            CommonUtils.toast_msg(RiserFormActivity.this, Msg);
                            finish();

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(Context context, UploadInfo uploadInfo) {
                    materialDialog.dismiss();
                }
            })
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            //Toast.makeText(RFC_Connection_Activity.this, "Please select Image", Toast.LENGTH_SHORT).show();
            materialDialog.dismiss();
        }
    }

    private void initViews() {
        ArrayAdapter<String> adapterProperty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, propertyTypeList);
        adapterProperty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_property_type.setAdapter(adapterProperty);

        ArrayAdapter<String> gasType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gasTypeList);
        gasType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gas_type.setAdapter(gasType);

        hseList.add("HSE (g+4)");
        selectedHSE = "HSE (g+4)";
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
        tv_agent_name.setText(dataModel.getFirstName() + " " + dataModel.getLastName());
//        tv_po_num.setText(dataModel.getAllocation().getPoNumber());
        tv_city.setText(dataModel.getCityRegion());
        tv_zone.setText(dataModel.getZone());
        tv_area.setText(dataModel.getArea());
        tv_society.setText(dataModel.getSociety());

        et_gali.setText(dataModel.getStreetGaliRoad());
        tv_bp_num.setText(dataModel.getBpNumber());
        connectedHouseAdapter = new ConnectedHouseAdapter(this);
        rv_connected_house.setAdapter(connectedHouseAdapter);
    }

    TextView /*tv_allocation_num, tv_sub_allocatio,*/ tv_bp_num, tv_agent_name, /*tv_po_num,*/
            tv_city, tv_zone, tv_area, tv_society, tv_add_more_hse;
    AutoCompleteTextView hse_tv_bp_num;
    EditText et_gali, et_ib/*, et_connected_house*/, hse_et_house, hse_et_floor, et_rl12, et_iv12, et_rl34, et_iv34, et_rl1, et_iv1, et_rl2, et_iv2;
    RadioGroup rg_riser_laying, rg_riser_testing, rg_riser_commissioning, rg_pbc_houses;
    ImageView iv_one, iv_two, iv_three;
    FrameLayout fl_image1, fl_image2, fl_image3;
    Spinner sp_property_type, sp_gas_type, sp_hse, sp_riser_length, sp_isolation_value, sp_lateral_tapping, sp_area_type;
    Button approve_button;
    RecyclerView rv_connected_house;
    LinearLayout lt_pbc_houses;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;
    Spinner sp_floor;

    private void findViews() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        tv_allocation_num = findViewById(R.id.tv_allocation_num);
//        tv_sub_allocatio = findViewById(R.id.tv_sub_allocatio);
        rg_pbc_houses = findViewById(R.id.rg_pbc_houses);
        lt_pbc_houses = findViewById(R.id.lt_pbc_houses);
        et_rl12 = findViewById(R.id.et_rl12);
        et_iv12 = findViewById(R.id.et_iv12);
        et_rl34 = findViewById(R.id.et_rl34);
        et_iv34 = findViewById(R.id.et_iv34);
        et_rl1 = findViewById(R.id.et_rl1);
        et_iv1 = findViewById(R.id.et_iv1);
        et_rl2 = findViewById(R.id.et_rl2);
        et_iv2 = findViewById(R.id.et_iv2);
        addTextWatchers();

//        sp_floor = findViewById(R.id.sp_floor);
        hse_et_house = findViewById(R.id.et_house);
        hse_et_floor = findViewById(R.id.et_floor);
        hse_tv_bp_num = findViewById(R.id.hse_tv_bp_num);
        hse_tv_bp_num.setThreshold(8);
        autoSuggestAdapter = new AutoSuggestAdapter(this,
                android.R.layout.simple_dropdown_item_1line);
        hse_tv_bp_num.setAdapter(autoSuggestAdapter);
        hse_tv_bp_num.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        try {
                            ConnectedHouseModel model = new ConnectedHouseModel();
                            model.setBp_number(autoSuggestAdapter.getObject(position));
                            model.setHouse_num(bpDetailsArray.getJSONObject(position).getString("house"));
                            model.setFloor_num(bpDetailsArray.getJSONObject(position).getString("floor"));
                            connectedHouseAdapter.addMoreHouse(model);
                            Utils.hideKeyboard(RiserFormActivity.this);

                            resetConnectedHouseInputs();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        hse_tv_bp_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(hse_tv_bp_num.getText())) {
                        searchBP(hse_tv_bp_num.getText().toString());
                    }
                }
                return false;
            }
        });
//        hse_tv_bp_num.setOnClickListener(this);
        rv_connected_house = findViewById(R.id.rv_connected_house);
        rv_connected_house.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        tv_add_more_hse = findViewById(R.id.tv_add_connectedHouse);
        tv_add_more_hse.setOnClickListener(this);
        tv_agent_name = findViewById(R.id.tv_agent_name);
//        tv_po_num = findViewById(R.id.tv_po_num);
        et_ib = findViewById(R.id.et_ib);
        tv_city = findViewById(R.id.tv_city);
        tv_zone = findViewById(R.id.tv_zone);
        tv_area = findViewById(R.id.tv_area);
        tv_society = findViewById(R.id.tv_society);
        et_gali = findViewById(R.id.et_gali);
        tv_bp_num = findViewById(R.id.tv_bp_num);
//        et_connected_house = findViewById(R.id.et_connected_house);
        rg_riser_laying = findViewById(R.id.rg_riser_laying);
        rg_riser_laying.setOnCheckedChangeListener(this);
        rg_riser_testing = findViewById(R.id.rg_riser_testing);
        rg_riser_testing.setOnCheckedChangeListener(this);
        rg_pbc_houses.setOnCheckedChangeListener(this);
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

    private void addTextWatchers() {
        et_rl12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty() || s.toString().equals("0")) {
                    if (!et_iv34.isEnabled()) {
                        et_iv12.setEnabled(false);
                        et_iv12.setText("0");
                        et_iv12.setBackgroundResource(R.drawable.filled_form_data_bg);
                    }
                } else {
                    et_iv12.setEnabled(true);
                    et_iv12.setBackgroundResource(R.drawable.shape_blue_border);

                }

            }
        });
        et_rl34.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty() || s.toString().equals("0")) {
                    if (!et_iv1.isEnabled()) {
                        if (!hasValidValue(et_rl12.getText().toString())) {
                            et_iv12.setEnabled(false);
                            et_iv12.setText("0");
                            et_iv12.setBackgroundResource(R.drawable.filled_form_data_bg);

                        }
                        et_iv34.setEnabled(false);
                        et_iv34.setText("0");
                        et_iv34.setBackgroundResource(R.drawable.filled_form_data_bg);
                    }
                } else {
                    et_iv12.setEnabled(true);
                    et_iv12.setBackgroundResource(R.drawable.shape_blue_border);
                    et_iv34.setEnabled(true);
                    et_iv34.setBackgroundResource(R.drawable.shape_blue_border);

                }

            }
        });
        et_rl1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hasValidValue(s.toString())) {
                    if (!et_iv2.isEnabled()) {
                        et_iv1.setEnabled(false);
                        et_iv1.setText("0");
                        et_iv1.setBackgroundResource(R.drawable.filled_form_data_bg);
                        if (!hasValidValue(et_rl34.getText().toString())) {
                            et_iv34.setEnabled(false);
                            et_iv34.setText("0");
                            et_iv34.setBackgroundResource(R.drawable.filled_form_data_bg);
                            if (!hasValidValue(et_rl12.getText().toString())) {
                                et_iv12.setEnabled(false);
                                et_iv12.setText("0");
                                et_iv12.setBackgroundResource(R.drawable.filled_form_data_bg);

                            }
                        }


                    }
                } else {
                    et_iv12.setEnabled(true);
                    et_iv12.setBackgroundResource(R.drawable.shape_blue_border);
                    et_iv34.setEnabled(true);
                    et_iv34.setBackgroundResource(R.drawable.shape_blue_border);
                    et_iv1.setEnabled(true);
                    et_iv1.setBackgroundResource(R.drawable.shape_blue_border);

                }

            }
        });
        et_rl2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hasValidValue(s.toString())) {
                    et_iv2.setEnabled(false);
                    et_iv2.setText("0");
                    et_iv2.setBackgroundResource(R.drawable.filled_form_data_bg);
                    if (!hasValidValue(et_rl1.getText().toString())) {
                        et_iv1.setEnabled(false);
                        et_iv1.setText("0");
                        et_iv1.setBackgroundResource(R.drawable.filled_form_data_bg);
                        if (!hasValidValue(et_rl34.getText().toString())) {
                            et_iv34.setEnabled(false);
                            et_iv34.setText("0");
                            et_iv34.setBackgroundResource(R.drawable.filled_form_data_bg);
                            if (!hasValidValue(et_rl12.getText().toString())) {
                                et_iv12.setEnabled(false);
                                et_iv12.setText("0");
                                et_iv12.setBackgroundResource(R.drawable.filled_form_data_bg);

                            }
                        }
                    }


                } else {
                    et_iv12.setEnabled(true);
                    et_iv12.setBackgroundResource(R.drawable.shape_blue_border);
                    et_iv34.setEnabled(true);
                    et_iv34.setBackgroundResource(R.drawable.shape_blue_border);
                    et_iv1.setEnabled(true);
                    et_iv1.setBackgroundResource(R.drawable.shape_blue_border);
                    et_iv2.setEnabled(true);
                    et_iv2.setBackgroundResource(R.drawable.shape_blue_border);

                }

            }
        });


    }

    private void feedListData() {
        //PROPERTY TYPE DATA
        propertyTypeList.add("low-rise");
        propertyTypeList.add("high-rise");

        //GAS TYPE DATA
        gasTypeList.add("Non Gasified");
        gasTypeList.add("Gasified");


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
            hseList.clear();
            if (selectedPropertyType.equals("low-rise")) {
//                if (connectedHseMaxLimit == 15) {
//                    connectedHouseAdapter.itemCount = 5;
//                    connectedHouseAdapter.notifyDataSetChanged();
//                }
                connectedHseMaxLimit = 5;
                hseList.add("HSE Upto (G+4)");
                hseList.add("HSE (G+5) & Above");
                selectedHSE = "HSE Upto (G+4)";
            } else {
                connectedHseMaxLimit = 15;
                hseList.add("HSE(g+14 & above)");
                selectedHSE = "HSE(g+14 & above)";

            }
            ArrayAdapter<String> hseType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hseList);
            hseType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_hse.setAdapter(hseType);


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

        } else if (v == tv_add_more_hse) {
            if (connectedHouseAdapter.getFilledData().size() < connectedHseMaxLimit) {
                if (isValidConnectedHseData()) {
                    ConnectedHouseModel model = new ConnectedHouseModel();
                    model.setBp_number(hse_tv_bp_num.getText().toString().trim());
                    model.setHouse_num(hse_et_house.getText().toString().trim());
                    model.setFloor_num(hse_et_floor.getText().toString().trim());
                    connectedHouseAdapter.addMoreHouse(model);
                    resetConnectedHouseInputs();
                }
            } else {
                Toast.makeText(RiserFormActivity.this, "Maximum " + connectedHseMaxLimit + " houses can be added", Toast.LENGTH_SHORT).show();
            }


        } else if (v == hse_tv_bp_num) {
            openAreaDialogBox();

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

    private void resetConnectedHouseInputs() {
        hse_tv_bp_num.setText("");
        hse_et_house.setText("");
        hse_et_floor.setText("");
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
        if ((hasValidValue(et_rl12.getText().toString()) || hasValidValue(et_rl34.getText().toString()) || hasValidValue(et_rl1.getText().toString()) || hasValidValue(et_rl2.getText().toString()))) {
            isValid = true;

        } else {
            isValid = false;
            CommonUtils.toast_msg(RiserFormActivity.this, "Please Fill Riser Length Details");
            return isValid;

        }
        if ((hasValidValue(et_iv12.getText().toString()) || hasValidValue(et_iv34.getText().toString()) || hasValidValue(et_iv1.getText().toString()) || hasValidValue(et_iv2.getText().toString()))) {
            isValid = true;

        } else {
            isValid = false;
            CommonUtils.toast_msg(RiserFormActivity.this, "Please Fill Isolation Value Details");
            return isValid;

        }
        if (!imagePathOne.isEmpty() && !imagePathTwo.isEmpty())
            isValid = true;
        else {
            isValid = false;
            CommonUtils.toast_msg(RiserFormActivity.this, "Please Upload Mandatory Photos");
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

        } else if (group == rg_pbc_houses) {
            if (checkedId == R.id.rb_yes)
                lt_pbc_houses.setVisibility(View.VISIBLE);
            else
                lt_pbc_houses.setVisibility(View.GONE);
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
                    String selectedImagePath = null;
                    String path = null;
                    try {
                        resultBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathHomeAddress);

                        resultBitmap = getResizedBitmap(resultBitmap, 1600);
                        String newFilePath = getPath(filePathHomeAddress);
                       //  path =    new ImageCompression(this).compressImage(newFilePath);
                        Log.d("image path = ",newFilePath+" new path = "+ path );
                        File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                        selectedImagePath = ScreenshotUtils.store(resultBitmap, "riser_"+requestCode +"_"+ dataModel.getBpNumber() + ".jpg", saveFile).toString();

//                        selectedImagePath=new ImageCompression(RiserFormActivity.this).compressImage(selectedImagePath);
                        Log.e("image_path_aadhar+,", "" + selectedImagePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (resultBitmap != null) {
                        if (requestCode == REQUEST_CODE_PICK_IMAGE_1_GALLERY) {
//                            imagePathOne=change_to_binary(resultBitmap);
                            imagePathOne = selectedImagePath;
                            iv_one.setImageBitmap(resultBitmap);
                        }
                        if (requestCode == REQUEST_CODE_PICK_IMAGE_2_GALLERY) {
                            imagePathTwo = selectedImagePath;
                            iv_two.setImageBitmap(resultBitmap);
                        }
                        if (requestCode == REQUEST_CODE_PICK_IMAGE_3_GALLERY) {
                            imagePathOptional = selectedImagePath;
                            iv_three.setImageBitmap(resultBitmap);

                        }
                    }
                }


        }
    }

    private String change_to_binary(Bitmap bitmapOrg) {
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

    private boolean isValidConnectedHseData() {
        boolean isValid = true;
      /*  if (hse_tv_bp_num.getText().toString().isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(RiserFormActivity.this, "BP No. is required.");
        } else*/
        if (hse_et_floor.getText().toString().isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(RiserFormActivity.this, "Floor info is required.");
        } else if (hse_et_house.getText().toString().isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(RiserFormActivity.this, "House No. is required.");
        } else {
            isValid = true;
        }

        return isValid;
    }

    private PlainTextListAdapter dropDown_adapter;

    private void openAreaDialogBox() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_bp_search);
        dialog.setCancelable(true);
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        RadioGroup radiogrp = dialog.findViewById(R.id.radiogrp);

        LinearLayout lt_search = dialog.findViewById(R.id.lt_search);
        LinearLayout lt_manual = dialog.findViewById(R.id.lt_manual);
        EditText et_bp = dialog.findViewById(R.id.et_bp);
        TextView tv_done = dialog.findViewById(R.id.tv_done);
        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_search) {
                    lt_search.setVisibility(View.VISIBLE);
                    lt_manual.setVisibility(View.GONE);
                }
                if (checkedId == R.id.rb_Enter) {
                    lt_search.setVisibility(View.GONE);
                    lt_manual.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bp_num = et_bp.getText().toString();
                hse_tv_bp_num.setText(bp_num);
                dialog.dismiss();

            }
        });
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        crose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dropDown_adapter = new PlainTextListAdapter(RiserFormActivity.this, new PlainTextListItemSelectListener() {
            @Override
            public void onPlainTextItemSelect(String item, int itemPosition) {
                hse_tv_bp_num.setText(item);
                try {
                    hse_et_floor.setText(bpDetailsArray.getJSONObject(itemPosition).getString("floor"));
                    hse_et_house.setText(bpDetailsArray.getJSONObject(itemPosition).getString("house"));
                } catch (Exception e) {

                }
                dialog.dismiss();
            }


        });
        recyclerView.setAdapter(dropDown_adapter);

        EditText editTextSearch = (EditText) dialog.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //  bookadapter.getFilter().filter(s.toString());
//                dropDown_adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() >= 8) {
                    searchBP(s.toString());
                }
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
        dialog.show();
    }

    JSONArray bpDetailsArray;

    private void searchBP(String bp) {
//        ProgressDialog progressDialog = ProgressDialog.show(this, "", "Searching BP number", true);
//        progressDialog.setCancelable(false);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("searchbp", Constants.RISER__SEARCH_BP + bp + "/" + dataModel.getArea() + "/" + dataModel.getSociety());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.RISER__SEARCH_BP + bp + "/" + dataModel.getArea() + "/" + dataModel.getSociety(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("searchbp", response.toString());
                    if (jsonObject.getString("status").equals("200")) {
                        ArrayList<String> bpList = new ArrayList<>();
                        bpDetailsArray = jsonObject.getJSONArray("Bp_Details");
                        for (int i = 0; i < bpDetailsArray.length(); i++) {
                            bpList.add(bpDetailsArray.getJSONObject(i).getString("bp"));

                        }
//                        dropDown_adapter.setData(bpList);
                        autoSuggestAdapter.setData(bpList);
                        autoSuggestAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
//                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                progressDialog.dismiss();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private boolean hasValidValue(String s) {
        if (s.isEmpty() || s.equals("0"))
            return false;
        else
            return true;
    }

}