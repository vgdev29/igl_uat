package com.fieldmobility.igl.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Adapter.DropDown_Adapter;
import com.fieldmobility.igl.Adapter.PlainTextListAdapter;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.RecyclerItemClickListener;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Listeners.PlainTextListItemSelectListener;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.User_bpData;
import com.fieldmobility.igl.R;
import com.iceteck.silicompressorr.SiliCompressor;
import com.kyanogen.signatureview.SignatureView;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.os.Environment.getExternalStorageDirectory;

public class BP_Creation_Form extends Activity implements AdapterView.OnItemSelectedListener {
    String dummyData = "{\"aadhaar_number\":\"656656656566\",\"addressProof\":\"AADHAAR CARD\",\"amount\":\"\",\"area\":\"RAMPRASTHA COLONY\",\"block_qtr_tower_wing\":\"F\",\"bp_date\":\"2021-07-17\",\"bp_number\":\"7000049734\",\"chequeDate\":\"\",\"chequeNo\":\"\",\"city_region\":\"GHAZIABAD\",\"customer_type\":\"EMI\",\"drawnOn\":\"\",\"email_id\":\"AA@GG.COM\",\"first_name\":\"FIRST\",\"floor\":\"1ST FLOOR\",\"house_no\":\"225\",\"house_type\":\"TYPE B\",\"idproof\":\"ELECTRICITY BILL\",\"igl_code_group\":\"null\",\"igl_status\":\"null\",\"landmark\":\"LAND MARK\",\"last_name\":\"LAST\",\"lead_no\":\"null\",\"lpg_company\":\"NO LPG\",\"lpg_conNo\":\"null\",\"lpg_distributor\":\"null\",\"middle_name\":\"\",\"mobile_number\":\"5656656565\",\"ownerName\":\"Owner Name\",\"pincode\":\"222222\",\"society\":\"BLOCK A\",\"street_gali_road\":\"7\",\"unique_lpg_Id\":\"\"}";
    Boolean isForResubmission = true;

    MaterialDialog materialDialog;
    EditText fullname, middle_name, lastname, mobile_no, email_id, aadhaar_no, landmark,et_father_name,
            house_no, pincode, lpg_distributer, lpg_consumer, unique_id_no;
    Button submit_button;
    SharedPrefs sharedPrefs;
    ImageView back;
    Spinner spinner_city;
    TextView tv_select_area;
    ArrayList<String> CityName = new ArrayList<>();
    ArrayList<String> CityId = new ArrayList<>();
    ArrayList<String> CityName1 = new ArrayList<>();
    ArrayList<String> CityId1 = new ArrayList<>();
    ArrayList<String> Area = new ArrayList<>();
    ArrayList<String> Area1 = new ArrayList<>();
    ArrayList<String> Area_ID = new ArrayList<>();
    ArrayList<String> Area_ID1 = new ArrayList<>();
    ArrayList<String> Society = new ArrayList<>();
    ArrayList<String> Society1 = new ArrayList<>();
    ArrayList<String> Landmark = new ArrayList<>();
    ArrayList<String> Customer_Type = new ArrayList<>();
    ArrayList<String> Customer_Type1 = new ArrayList<>();
    ArrayList<String> House_type_name = new ArrayList<>();
    ArrayList<String> House_type_name1 = new ArrayList<>();
    ArrayList<String> Floor_name = new ArrayList<>();
    ArrayList<String> Floor_name1 = new ArrayList<>();
    ArrayList<String> Block_tower_name = new ArrayList<>();
    ArrayList<String> Block_tower_name1 = new ArrayList<>();
    ArrayList<String> Street_road_name = new ArrayList<>();
    ArrayList<String> Street_road_name1 = new ArrayList<>();
    ArrayList<String> Lpg_company_name = new ArrayList<>();
    ArrayList<String> Street_road_type_name = new ArrayList<>();
    ArrayList<String> Block_tower_type_name = new ArrayList<>();


    String city_id, city_name, customer_type, area_name = "Select Area", area_Id, soceity_name, house_type_name, floor_name, block_tower_name, street_road_name, lpg_company_name, street_road_type_name, block_tower_type_name;
    Spinner spinner_customertype/*,spinner_area*/, spinner_society, spinner_house_type, spinner_floor, spinner_block_tower, spinner_street_road, spinner_lpg_company;

    Spinner spinner_street_road_type, spinner_block_tower_type;

    String Bp_Number;


    DatePickerDialog pickerDialog_Date;
    String date_select;
    String emailPattern = "@[A-Z][a-z]+\\.+";
    JSONObject filledDatajson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bp_creation_form);
        sharedPrefs = new SharedPrefs(this);

        findViews();
//        if (isForResubmission) {
//            try {
//                filledDatajson = new JSONObject(dummyData);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
        checkPermission();
        Click_Event();

        loadSpinnerData();
    }


    private void Click_Event() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_Method();
            }
        });
        tv_select_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!city_name.equals("Select City")) {
                    openAreaDialogBox();
                } else {
                    Toast.makeText(BP_Creation_Form.this, "Please Select the City", Toast.LENGTH_SHORT).show();
                }

            }
        });
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_city.getItemAtPosition(spinner_city.getSelectedItemPosition()).toString();
                Log.e("CITY+", country);
                area_name = "Select Area";
                tv_select_area.setText(area_name);
                city_id = CityId1.get(position);
                city_name = CityName1.get(position);
                Log.e("city_id+", city_id);
                Log.e("city_name+", city_name);
                loadSpinner_reasion_city(city_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

//        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                String country=  spinner_area.getItemAtPosition(spinner_area.getSelectedItemPosition()).toString();
//                Log.e("area_name+",country);
//                area_name=Area1.get(position);
//                area_Id=Area_ID1.get(position);
//                Log.e("area_Id+",area_Id);
//                Log.e("area_name+",area_name);
//                loadSpinnerSocity(area_Id);
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

        spinner_society.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_society.getItemAtPosition(spinner_society.getSelectedItemPosition()).toString();
                Log.e("Society+", country);
                soceity_name = Society1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_house_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_house_type.getItemAtPosition(spinner_house_type.getSelectedItemPosition()).toString();
                Log.e("house_type_name+", country);
                house_type_name = House_type_name1.get(position);
                //city_name=CityName.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_floor.getItemAtPosition(spinner_floor.getSelectedItemPosition()).toString();
                Log.e("floor_name+", country);
                floor_name = Floor_name1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_block_tower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_block_tower.getItemAtPosition(spinner_block_tower.getSelectedItemPosition()).toString();
                Log.e("block_tower_name+", country);
                block_tower_name = Block_tower_name1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_street_road.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_street_road.getItemAtPosition(spinner_street_road.getSelectedItemPosition()).toString();
                Log.e("street_road_name+", country);
                street_road_name = Street_road_name1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_customertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String customer_type11 = spinner_customertype.getItemAtPosition(spinner_customertype.getSelectedItemPosition()).toString();
                Log.e("customer_type+", customer_type11);
                customer_type = Customer_Type1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_lpg_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String customer_type11 = spinner_lpg_company.getItemAtPosition(spinner_lpg_company.getSelectedItemPosition()).toString();
                Log.e("lpg_company_name+", customer_type11);
                lpg_company_name = Lpg_company_name.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Lpg_company_name.add("Select LPG Company");
        Lpg_company_name.add("IOCL");
        Lpg_company_name.add("BPCL");
        Lpg_company_name.add("HPCL");
        Lpg_company_name.add("NO LPG");
        ArrayAdapter<String> lpg_compney_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Lpg_company_name);
        lpg_compney_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_lpg_company.setAdapter(lpg_compney_Adapter);
        spinner_block_tower_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String customer_type11 = spinner_block_tower_type.getItemAtPosition(spinner_block_tower_type.getSelectedItemPosition()).toString();
                Log.e("lpg_company_name+", customer_type11);
                block_tower_type_name = Block_tower_type_name.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_street_road_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String customer_type11 = spinner_street_road_type.getItemAtPosition(spinner_street_road_type.getSelectedItemPosition()).toString();
                Log.e("lpg_company_name+", customer_type11);
                street_road_type_name = Street_road_type_name.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Block_tower_type_name.add("");
        Block_tower_type_name.add("BLOCK");
        Block_tower_type_name.add("QTR");
        Block_tower_type_name.add("TOWER");
        Block_tower_type_name.add("WING");
        ArrayAdapter<String> spinner_block_tower_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Block_tower_type_name);
        spinner_block_tower_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_block_tower_type.setAdapter(spinner_block_tower_Adapter);

        Street_road_type_name.add("");
        Street_road_type_name.add("STREET");
        Street_road_type_name.add("ROAD");
        Street_road_type_name.add("GALI");
        ArrayAdapter<String> street_road_type_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Street_road_type_name);
        street_road_type_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_street_road_type.setAdapter(street_road_type_Adapter);


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    User_bpData userBpData = new User_bpData();
                    userBpData.setIgl_first_name(fullname.getText().toString());
                    userBpData.setIgl_middle_name(middle_name.getText().toString());
                    userBpData.setIgl_last_name(lastname.getText().toString());
                    userBpData.setIgl_mobile_no(mobile_no.getText().toString());
                    userBpData.setIgl_email_id(email_id.getText().toString());
                    userBpData.setIgl_aadhaar_no(aadhaar_no.getText().toString());
                    userBpData.setIgl_father_name(et_father_name.getText().toString());
                    userBpData.setIgl_city_region(city_name);
                    userBpData.setIgl_area(area_name);
                    userBpData.setIgl_society(soceity_name);
                    userBpData.setIgl_landmark(landmark.getText().toString());
                    userBpData.setIgl_house_type(house_type_name);
                    userBpData.setIgl_house_no(house_no.getText().toString());
                    userBpData.setIgl_Block_Qtr_Tower_Wing(block_tower_name);
                    userBpData.setIgl_floor(floor_name);
                    userBpData.setIgl_street_gali_road(street_road_name);
                    userBpData.setIgl_pincode(pincode.getText().toString());
                    userBpData.setIgl_lpg_company(lpg_company_name);
                    userBpData.setIgl_type_of_customer(customer_type);//
                    userBpData.setId_lpg_distributor(lpg_distributer.getText().toString());
                    userBpData.setIgl_lpg_consumer_no(lpg_consumer.getText().toString());
                    userBpData.setIgl_unique_lpg_id(unique_id_no.getText().toString());
                    userBpData.setIgl_corresponding_language("EN");
                    userBpData.setBlock_tower_type(block_tower_type_name);
                    userBpData.setStreet_road_type(street_road_type_name);
                    userBpData.setDma_user(sharedPrefs.getUUID());
                    Intent intent = new Intent(BP_Creation_Form.this, BP_Creation_Form_step2.class);
                    intent.putExtra("data", userBpData);
                    startActivity(intent);
                    //uploadMultipart();
                }


            }
        });


    }


    private void checkPermission() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {

            }
        });
    }


    private void loadSpinnerData() {
        CityId.clear();
        CityName.clear();
        CityId1.clear();
        CityName1.clear();
        materialDialog = new MaterialDialog.Builder(BP_Creation_Form.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("bpcreation",Constants.BP_CITY_LISTING+sharedPrefs.getZone_Code());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BP_CITY_LISTING+sharedPrefs.getZone_Code(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    //JSONObject jsonObject=new JSONObject(response);
                    // if(jsonObject.getInt("success")==1){
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String city = jsonObject1.getString("user_city");
                        city_id = jsonObject1.getString("user_id");
                        CityName.add(city);
                        CityId.add(city_id);
                        //city_id=CityId.get(0).toString();
                        //loadSpinner_reasion_city(city_id);
                        //  }
                    }
                    String city_select1 = "Select City";
                    String city_id1 = "1";
                    CityId1.add(city_id1);
                    CityId1.addAll(CityId);
                    CityName1.add(city_select1);
                    CityName1.addAll(CityName);


                    spinner_city.setAdapter(new ArrayAdapter<String>(BP_Creation_Form.this, android.R.layout.simple_spinner_dropdown_item, CityName1));

//                    if (isForResubmission) {
//                        initViewsWithData();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void initViewsWithData() {
        try {
            fullname.setText(filledDatajson.getString("first_name"));
            lastname.setText(filledDatajson.getString("last_name"));
            middle_name.setText(filledDatajson.getString("middle_name"));
            mobile_no.setText(filledDatajson.getString("mobile_number"));
            email_id.setText(filledDatajson.getString("email_id"));
            aadhaar_no.setText(filledDatajson.getString("aadhaar_number"));
            String selectedCity = filledDatajson.getString("city_region");

            if (selectedCity != null && !selectedCity.isEmpty()) {
                spinner_city.setSelection(CityName1.indexOf(selectedCity));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void loadSpinner_reasion_city(final String city_id) {
        // Society.clear();
        Area.clear();
        Area_ID.clear();
        Area1.clear();
        Area_ID1.clear();
        Block_tower_name.clear();
        Block_tower_name1.clear();
        Customer_Type.clear();
        Customer_Type1.clear();
        Street_road_name.clear();
        Street_road_name1.clear();
        House_type_name.clear();
        House_type_name1.clear();
        Floor_name.clear();
        Floor_name1.clear();
        materialDialog = new MaterialDialog.Builder(BP_Creation_Form.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Arealist_reason_Socity + "/" + city_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray_area = jsonObject.getJSONArray("area");
                    for (int i = 0; i < jsonArray_area.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_area.getJSONObject(i);
                        String area_select = jsonObject1.getString("area");
                        String area_id = jsonObject1.getString("area_id");
                        Area_ID.add(area_id);
                        Area.add(area_select);
                    }
//                    String area_id1="1";
//                    Area_ID1.add(area_id1);
                    Area_ID1.addAll(Area_ID);
                    Area1.addAll(Area);
                    JSONArray jsonArray_block_no = jsonObject.getJSONArray("block_no");
                    for (int i = 0; i < jsonArray_block_no.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_block_no.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("block_no");
                        Block_tower_name.add(society_name_select);
                    }
                    String block_tower_select1 = "";
                    Block_tower_name1.add(block_tower_select1);
                    Block_tower_name1.addAll(Block_tower_name);

                    JSONArray jsonArray_customer_type = jsonObject.getJSONArray("customer_type");
                    for (int i = 0; i < jsonArray_customer_type.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_customer_type.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("customer_type");
                        Customer_Type.add(society_name_select);
                        Collections.reverse(Customer_Type);
                    }
                    String customer_type_select1 = "Select Customer Type";
                    Customer_Type1.add(customer_type_select1);
                    Customer_Type1.addAll(Customer_Type);

                    JSONArray jsonArray_street_no = jsonObject.getJSONArray("street_no");
                    for (int i = 0; i < jsonArray_street_no.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_street_no.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("street_no");
                        Street_road_name.add(society_name_select);
                    }
                    String street_road_select1 = "";
                    Street_road_name1.add(street_road_select1);
                    Street_road_name1.addAll(Street_road_name);

                    JSONArray jsonArray_house_type = jsonObject.getJSONArray("house_type");
                    for (int i = 0; i < jsonArray_house_type.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_house_type.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("house_type");
                        House_type_name.add(society_name_select);
                    }
                    String house_type_select1 = "";
                    House_type_name1.add(house_type_select1);
                    House_type_name1.addAll(House_type_name);

                    JSONArray jsonArray_floor = jsonObject.getJSONArray("floor");
                    for (int i = 0; i < jsonArray_floor.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_floor.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("floor_name");
                        Floor_name.add(society_name_select);

                    }

                    String floor_select1 = "Select Floor";
                    Floor_name1.add(floor_select1);
                    Floor_name1.addAll(Floor_name);

//                    spinner_area.setAdapter(new ArrayAdapter<String>(BP_Creation_Form.this, android.R.layout.simple_spinner_dropdown_item, Area1));
                    spinner_block_tower.setAdapter(new ArrayAdapter<String>(BP_Creation_Form.this, android.R.layout.simple_spinner_dropdown_item, Block_tower_name1));
                    spinner_customertype.setAdapter(new ArrayAdapter<String>(BP_Creation_Form.this, android.R.layout.simple_spinner_dropdown_item, Customer_Type1));
                    spinner_street_road.setAdapter(new ArrayAdapter<String>(BP_Creation_Form.this, android.R.layout.simple_spinner_dropdown_item, Street_road_name1));
                    spinner_house_type.setAdapter(new ArrayAdapter<String>(BP_Creation_Form.this, android.R.layout.simple_spinner_dropdown_item, House_type_name1));
                    spinner_floor.setAdapter(new ArrayAdapter<String>(BP_Creation_Form.this, android.R.layout.simple_spinner_dropdown_item, Floor_name1));
//                    if (isForResubmission) {
//                        tv_select_area.setText(filledDatajson.getString("area"));
//
//                    }

                    // loadSpinner_Customer_Type(city_id);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void loadSpinnerSocity(String area_Id) {

        Society.clear();
        Society1.clear();
        materialDialog = new MaterialDialog.Builder(BP_Creation_Form.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Socity_List + area_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // if(jsonObject.getInt("success")==1){
                    JSONArray jsonArray_society = jsonObject.getJSONArray("list_of_society");
                    for (int i = 0; i < jsonArray_society.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_society.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("society_name");
                        String society_id = jsonObject1.getString("society_id");
                        // Society_Id.add(society_id);
                        Society.add(society_name_select);

                    }
                    String area_select1 = "Select Society";
                    Society1.add(area_select1);
                    Society1.addAll(Society);
                    spinner_society.setAdapter(new ArrayAdapter<String>(BP_Creation_Form.this, android.R.layout.simple_spinner_dropdown_item, Society1));
                } catch (JSONException e) {
                    e.printStackTrace();
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


    private void BP_N0_DilogBox() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.bp_no_dilogbox);
        //dialog.setTitle("Signature");
        dialog.setCancelable(true);
        TextView bp_no_text = dialog.findViewById(R.id.bp_no_text);
        bp_no_text.setText(Bp_Number);
        Button ok_button = (Button) dialog.findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });


        dialog.show();
    }

    private boolean validateData() {
        boolean isDataValid = true;

        if (fullname.getText().length() == 0) {
            isDataValid = false;
            fullname.setError("Enter First Name");
            Toast.makeText(BP_Creation_Form.this, "Enter First Name", Toast.LENGTH_SHORT).show();
        } else if (lastname.getText().length() == 0) {
            isDataValid = false;
            lastname.setError("Enter Last Name");
            Toast.makeText(BP_Creation_Form.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
        } else if (mobile_no.getText().length() == 0 || mobile_no.getText().length() > 10) {
            isDataValid = false;
            mobile_no.setError("Enter Mobile No");
            Toast.makeText(BP_Creation_Form.this, "Enter Valid Mobile no.", Toast.LENGTH_SHORT).show();
        } else if (city_name.equals("Select City")) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form.this, "Enter City", Toast.LENGTH_SHORT).show();
        } else if (area_name.equals("Select Area")) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form.this, "Enter Area", Toast.LENGTH_SHORT).show();
        } else if (soceity_name.equals("Select Society")) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form.this, "Enter Society", Toast.LENGTH_SHORT).show();
        } else if (house_no.getText().length() == 0) {
            isDataValid = false;
            house_no.setError("Enter House No");
            Toast.makeText(BP_Creation_Form.this, "Select House no", Toast.LENGTH_SHORT).show();
            //Toast.makeText(New_Regestration_Form.this,"Enter Society",Toast.LENGTH_SHORT).show();
        } else if (floor_name.equals("Select Floor")) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form.this, "Enter Floor", Toast.LENGTH_SHORT).show();
        } else if (pincode.getText().length() == 0) {
            isDataValid = false;
            pincode.setError("Enter PinCode");
            Toast.makeText(BP_Creation_Form.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
        } else if (lpg_company_name.equals("Select LPG Company")) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form.this, "Select LPG Company", Toast.LENGTH_SHORT).show();
        } else if (customer_type.equals("Select Customer Type")) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form.this, "Select Customer Type", Toast.LENGTH_SHORT).show();
        } else {
            isDataValid = true;
        }

        return isDataValid;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onBackPressed() {
        back_Method();
    }

    private void back_Method() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.backbutton_layout);
        dialog.setCancelable(false);
        final Button accept_button = dialog.findViewById(R.id.accept_button);
        Button cancel_button = (Button) dialog.findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void openAreaDialogBox() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_text_search);
        dialog.setCancelable(true);
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        crose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        PlainTextListAdapter dropDown_adapter = new PlainTextListAdapter(BP_Creation_Form.this, new PlainTextListItemSelectListener() {
            @Override
            public void onPlainTextItemSelect(String item, int itemPosition) {
                String country = item;
                tv_select_area.setText(country);
                Log.e("area_name+", country);
                area_name = item;
                int originalPosition = Area1.indexOf(item);
                area_Id = Area_ID1.get(originalPosition);
                Log.e("area_Id+", area_Id);
                Log.e("area_name+", area_name);
                loadSpinnerSocity(area_Id);
                dialog.dismiss();
            }
        });
        recyclerView.setAdapter(dropDown_adapter);
        dropDown_adapter.setData(Area1);

        EditText editTextSearch = (EditText) dialog.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  bookadapter.getFilter().filter(s.toString());
                dropDown_adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
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

    private void findViews() {
        tv_select_area = findViewById(R.id.tv_select_area);
        fullname = findViewById(R.id.fullname);
        middle_name = findViewById(R.id.middle_name);
        lastname = findViewById(R.id.lastname);
        mobile_no = findViewById(R.id.mobile_no);
        email_id = findViewById(R.id.email_id);
        aadhaar_no = findViewById(R.id.aadhaar_no);
        et_father_name = findViewById(R.id.et_father_name);
        landmark = findViewById(R.id.landmark);
        house_no = findViewById(R.id.house_no);
        pincode = findViewById(R.id.pincode);
        lpg_distributer = findViewById(R.id.lpg_distributer_edit);
        lpg_consumer = findViewById(R.id.lpg_consumer_edit);
        unique_id_no = findViewById(R.id.unique_id_no_edit);

        submit_button = findViewById(R.id.submit_button);


        spinner_city = (Spinner) findViewById(R.id.spinner_city);
//        spinner_area=(Spinner)findViewById(R.id.spinner_area);
        spinner_society = (Spinner) findViewById(R.id.spinner_society);
        spinner_house_type = (Spinner) findViewById(R.id.spinner_house_type);
        spinner_floor = (Spinner) findViewById(R.id.spinner_floor);
        spinner_block_tower = (Spinner) findViewById(R.id.spinner_block_tower);
        spinner_street_road = (Spinner) findViewById(R.id.spinner_street_road);
        spinner_block_tower_type = (Spinner) findViewById(R.id.spinner_block_tower_type);
        spinner_street_road_type = (Spinner) findViewById(R.id.spinner_street_road_type);
        spinner_customertype = (Spinner) findViewById(R.id.spinner_customertype);
        spinner_lpg_company = (Spinner) findViewById(R.id.spinner_lpg_company);
        back = findViewById(R.id.back);
    }

}


