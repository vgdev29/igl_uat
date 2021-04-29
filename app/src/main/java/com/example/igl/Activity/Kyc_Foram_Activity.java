package com.example.igl.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igl.Helper.ConnectionDetector;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.GPSLocation;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.R;
import com.kyanogen.signatureview.SignatureView;


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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static android.os.Environment.getExternalStorageDirectory;
import static com.example.igl.Activity.Selfie_Activity.MY_PERMISSIONS_REQUEST_CAMERA;
import static com.example.igl.utils.Utils.hideProgressDialog;
import static com.example.igl.utils.Utils.showProgressDialog;

public class Kyc_Foram_Activity extends Activity {

    private MaterialDialog materialDialog;

    ImageView adhar_image, address_image, signature_image;
    Button select_button;
    EditText fullname, middle_name, lastname, mobile_no, email_id, aadhaar_no, city_reasion, area, society, landmark, house_type, house_no, block_tower,
            floor, street_road, pincode, lpg_company, lpg_distributer, lpg_consumer, unique_id_no, ownar_name_no, chequeno_edit, chequedate_edit, drawnon_edit, amount_edit, meater_no;
    Button submit_button;
    SharedPrefs sharedPrefs;
    RadioGroup radioGroup;
    RadioButton genderradioButton;
    //Bitmap bitmap;
    Button clear, save;
    SignatureView signatureView, ownar_signature_view;
    String path, signature_path;
    ImageView image_signature;
    int WRITEN_Permision;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    ImageView back;
    String pdf_file_path;
    LinearLayout todo_creation;
    private final int PICK_IMAGE_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST_ADDRESS = 3;
    protected static final int CAMERA_REQUEST = 200;
    protected static final int CAMERA_REQUEST_ADDRESS = 201;
    private final int PICK_OWNER_IMAGE_REQUEST_ADDRESS = 4;
    private final int PICK_CUSTOMER_IMAGE_SIGNATURE = 5;
    private Uri filePath_aadhaar, filePath_address, filePath_owner, filePath_customer;
    String image_path_aadhar, image_path_address, owner_image_select, customer_image_select;
    Button signature_button, adhar_button, address_button;
    Spinner spinner1, spinner2;
    Spinner spinner_city;
    ArrayList<String> CityName;
    ArrayList<String> CityId;
    ArrayList<String> Area;
    ArrayList<String> Area_Id;
    ArrayList<String> Society;
    ArrayList<String> Landmark;
    ArrayList<String> Customer_Type;
    ArrayList<String> House_type_name;
    ArrayList<String> Floor_name;
    ArrayList<String> Block_tower_name;
    ArrayList<String> Street_road_name;
    ArrayList<String> Lpg_company_name;
    String id_proof, address_proof, Type_Of_Owner, ownar_name;
    String city_id, city_name, area_name, area_id, customer_type, soceity_name, house_type_name, floor_name, block_tower_name, street_road_name, lpg_company_name, street_road_type_name, block_tower_type_name;
    Spinner spinner_customertype, spinner_area, spinner_society, spinner_house_type, spinner_floor, spinner_block_tower, spinner_street_road, spinner_lpg_company;
    ImageView adhar_owner_image;
    TextView bp_no_text;
    CheckBox checkBox_term_cond;
    TextView checkbox_text;
    String Latitude, Longitude;

    Spinner spinner_street_road_type, spinner_block_tower_type;
    ArrayList<String> Street_road_type_name;
    ArrayList<String> Block_tower_type_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_regestration_form);
        sharedPrefs = new SharedPrefs(this);
        getLocationUsingInternet();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fullname = findViewById(R.id.fullname);
        middle_name = findViewById(R.id.middle_name);
        lastname = findViewById(R.id.lastname);
        mobile_no = findViewById(R.id.mobile_no);
        email_id = findViewById(R.id.email_id);
        aadhaar_no = findViewById(R.id.aadhaar_no);
        city_reasion = findViewById(R.id.city_reasion);
        area = findViewById(R.id.area);
        society = findViewById(R.id.society);
        landmark = findViewById(R.id.landmark);
        house_type = findViewById(R.id.house_type);
        house_no = findViewById(R.id.house_no);
        block_tower = findViewById(R.id.block_tower);
        floor = findViewById(R.id.floor);
        street_road = findViewById(R.id.street_road);
        pincode = findViewById(R.id.pincode);
        lpg_company = findViewById(R.id.lpg_company);
        chequeno_edit = findViewById(R.id.chequeno_edit);
        chequedate_edit = findViewById(R.id.chequedate_edit);
        drawnon_edit = findViewById(R.id.drawnon_edit);
        amount_edit = findViewById(R.id.amount_edit);
        meater_no = findViewById(R.id.meater_no);
        // customer_type= findViewById(R.id.customer_type);
        submit_button = findViewById(R.id.submit_button);
        adhar_image = findViewById(R.id.adhar_image);
        address_image = findViewById(R.id.address_image);
        signature_image = findViewById(R.id.signature_image);
        signature_button = findViewById(R.id.select_button);
        adhar_button = findViewById(R.id.adhar_button);
        address_button = findViewById(R.id.address_button);
        checkBox_term_cond = findViewById(R.id.checkbox);
        checkbox_text = findViewById(R.id.checkbox_text);

        lpg_distributer = findViewById(R.id.lpg_distributer_edit);
        lpg_consumer = findViewById(R.id.lpg_consumer_edit);
        unique_id_no = findViewById(R.id.unique_id_no_edit);
        todo_creation = findViewById(R.id.todo_creation);
        bp_no_text = findViewById(R.id.bp_no_text);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_area = (Spinner) findViewById(R.id.spinner_area);
        spinner_society = (Spinner) findViewById(R.id.spinner_society);
        spinner_house_type = (Spinner) findViewById(R.id.spinner_house_type);
        spinner_floor = (Spinner) findViewById(R.id.spinner_floor);
        spinner_block_tower = (Spinner) findViewById(R.id.spinner_block_tower);
        spinner_street_road = (Spinner) findViewById(R.id.spinner_street_road);
        spinner_customertype = (Spinner) findViewById(R.id.spinner_customertype);
        spinner_lpg_company = (Spinner) findViewById(R.id.spinner_lpg_company);
        spinner_block_tower_type = (Spinner) findViewById(R.id.spinner_block_tower_type);
        spinner_street_road_type = (Spinner) findViewById(R.id.spinner_street_road_type);


        final String Address = getIntent().getStringExtra("House_no") + " " + getIntent().getStringExtra("House_type") + " " +
                getIntent().getStringExtra("Landmark") + " " + getIntent().getStringExtra("Society") + " " + getIntent().getStringExtra("Area") + " "
                + getIntent().getStringExtra("City_region");
        ;
        todo_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kyc_Foram_Activity.this, To_Do_Task_Creation.class);
                intent.putExtra("Bp_number", getIntent().getStringExtra("Bp_number"));
                intent.putExtra("Address", Address);
                intent.putExtra("Type", "1");
                startActivity(intent);
            }
        });
        bp_no_text.setText(getIntent().getStringExtra("Bp_number"));
        signature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Signature_Method();
                Customer_Signature1();
            }
        });
        adhar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage_aadhaar();
            }
        });
        address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage_address();
            }
        });
        List<String> list = new ArrayList<String>();
        list.add("Electricity Bill");
        list.add("Water Bill");
        list.add("Sale/Conveyance Deed");
        list.add("House Tax Receipt");
        list.add("Mutation Copy");
        //list.add("ALLOTMENT LETTER");
        // list.add("ANY OTHER");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        List<String> list1 = new ArrayList<String>();
        list1.add("AADHAAR CARD");
        list1.add("Voter ID card");
        list1.add("PAN card");
        list1.add("DRIVING LICENCE");
        list1.add("Passport");
        //list1.add("ANY OTHER");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter1);
        spinner1.setSelection(0,false);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                id_proof = spinner1.getItemAtPosition(spinner1.getSelectedItemPosition()).toString();
                if (id_proof.equals("ANY OTHER")) {
                    Edit_text();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner2.setSelection(0,false);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                address_proof = spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString();
                if (address_proof.equals("ANY OTHER")) {
                    Edit_text();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Type_Of_Owner = "Owner";
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.owner:
                        Toast.makeText(Kyc_Foram_Activity.this, "Type", Toast.LENGTH_LONG).show();
                        Type_Of_Owner = "Owner";
                        break;
                    case R.id.rents:
                        Type_Of_Owner = "Rented";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        Ownar_Signature();
                        break;
                }
            }
        });
        CityName = new ArrayList<>();
        CityId = new ArrayList<>();
        Area = new ArrayList<>();
        Area_Id = new ArrayList<>();
        Society = new ArrayList<>();
        Landmark = new ArrayList<>();
        Customer_Type = new ArrayList<>();
        House_type_name = new ArrayList<>();
        Floor_name = new ArrayList<>();
        Block_tower_name = new ArrayList<>();
        Street_road_name = new ArrayList<>();
        Lpg_company_name = new ArrayList<>();
        Street_road_type_name = new ArrayList<>();
        Block_tower_type_name = new ArrayList<>();
        fullname.setText(getIntent().getStringExtra("First_name"));
        middle_name.setText(getIntent().getStringExtra("Middle_name"));
        lastname.setText(getIntent().getStringExtra("Last_name"));
        mobile_no.setText(getIntent().getStringExtra("Mobile_number"));
        email_id.setText(getIntent().getStringExtra("Email_id"));
        aadhaar_no.setText(getIntent().getStringExtra("Aadhaar_number"));
        city_reasion.setText(getIntent().getStringExtra("City_region"));
        area.setText(getIntent().getStringExtra("Area"));
        society.setText(getIntent().getStringExtra("Society"));
        landmark.setText(getIntent().getStringExtra("Landmark"));
        house_type.setText(getIntent().getStringExtra("House_type"));
        house_no.setText(getIntent().getStringExtra("House_no"));
        block_tower.setText(getIntent().getStringExtra("Block_qtr_tower_wing"));
        floor.setText(getIntent().getStringExtra("Floor"));
        street_road.setText(getIntent().getStringExtra("Street_gali_road"));
        pincode.setText(getIntent().getStringExtra("Pincode"));
        lpg_company.setText(getIntent().getStringExtra("Lpg_company"));

        //  customer_type.setText(getIntent().getStringExtra("Customer_type"));


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                checkPermission();
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        } else {
        }
        if (ContextCompat.checkSelfPermission(Kyc_Foram_Activity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) Kyc_Foram_Activity.this, Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions((Activity) Kyc_Foram_Activity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }

        }
        loadSpinnerData();
        spinner_city.setSelection(0,false);
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_city.getItemAtPosition(spinner_city.getSelectedItemPosition()).toString();
                Log.e("CITY+", country);
                city_id = CityId.get(position);
                city_name = CityName.get(position);
                loadSpinner_reasion_city(city_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_area.setSelection(0,false);
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_area.getItemAtPosition(spinner_area.getSelectedItemPosition()).toString();
                Log.e("area_name+", country);
                area_name = Area.get(position);
                area_id = Area_Id.get(position);
                loadSpinnerSocity(area_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_society.setSelection(0,false);
        spinner_society.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_society.getItemAtPosition(spinner_society.getSelectedItemPosition()).toString();
                Log.e("Society+", country);
                soceity_name = Society.get(position);
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
                house_type_name = House_type_name.get(position);
                //city_name=CityName.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_floor.setSelection(0,false);
        spinner_floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_floor.getItemAtPosition(spinner_floor.getSelectedItemPosition()).toString();
                Log.e("floor_name+", country);
                floor_name = Floor_name.get(position);


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
                block_tower_name = Block_tower_name.get(position);


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
                street_road_name = Street_road_name.get(position);


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
                customer_type = Customer_Type.get(position);

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
        String first = "I agree to the ";
        String second = "<font color='#EE0000'> Terms and conditions</font>";
        String third = " of PNG registration.";
        checkbox_text.setText(Html.fromHtml(first + second + third));
        checkbox_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kyc_Foram_Activity.this, WebView_Activity.class);
                startActivity(intent);

            }
        });
        checkBox_term_cond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox_term_cond.setChecked(true);
                    Toast.makeText(Kyc_Foram_Activity.this, "You checked the checkbox!", Toast.LENGTH_SHORT).show();
                } else {
                    checkBox_term_cond.setChecked(false);
                    Toast.makeText(Kyc_Foram_Activity.this, "You unchecked the checkbox!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox_term_cond.isChecked()) {
                    if (validateData()) {
                        // New_Regestration_Form();

                        uploadMultipart();
                    }

                } else {
                    Toast.makeText(Kyc_Foram_Activity.this, "Please tick to accept Tearm & Condition", Toast.LENGTH_SHORT).show();

                }

            }
        });

        statusCheck();
    }


    private void selectImage_aadhaar() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(Kyc_Foram_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });
        myAlertDialog.show();


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
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_ADDRESS);
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(Kyc_Foram_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_ADDRESS);
                    }
                });
        myAlertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}
                }
            }
            break;

            case PICK_IMAGE_REQUEST:
                if (requestCode == PICK_IMAGE_REQUEST) {

                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_aadhaar = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_aadhaar);
                        // imageView.setImageBitmap(bitmap);
                        adhar_image.setImageBitmap(bitmap);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_aadhar = getPath(filePath_aadhaar);
                        Log.e("image_path_aadhar+,", "" + image_path_aadhar);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
                    File f = new File(getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        adhar_image.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_aadhar = file.toString();
                        // image_path_address1 =file.toString();
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                            adhar_image.setImageBitmap(bitmap);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            //Toast.makeText(this, "file not found", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (IOException e) {
                            //Toast.makeText(this, "IO execption", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (Exception e) {
                            //Toast.makeText(this, "Execption1", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        //ecf eToast.makeText(this, "Execption2", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_IMAGE_REQUEST_ADDRESS:
                if (requestCode == PICK_IMAGE_REQUEST_ADDRESS && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_address = data.getData();
                    Log.e("Camera_Pathaddress++", filePath_address.toString());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_address);
                        // imageView.setImageBitmap(bitmap);
                        address_image.setImageBitmap(bitmap);
                        image_path_address = getPath1(filePath_address);
                        Log.e("image_path_address+", "" + image_path_address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CAMERA_REQUEST_ADDRESS:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_ADDRESS) {
                    File f = new File(getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        address_image.setImageBitmap(bitmap);
                        //BitMapToString(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_address = file.toString();
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
            case PICK_OWNER_IMAGE_REQUEST_ADDRESS:
                if (requestCode == PICK_OWNER_IMAGE_REQUEST_ADDRESS && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_owner = data.getData();
                    Log.e("Camera_Pathaddress++", filePath_owner.toString());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_owner);
                        // imageView.setImageBitmap(bitmap);
                        adhar_owner_image.setImageBitmap(bitmap);
                        signature_path = getPath1(filePath_owner);
                        Log.e("owner_image_select+", "" + signature_path);
                        // new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_CUSTOMER_IMAGE_SIGNATURE:
                if (requestCode == PICK_CUSTOMER_IMAGE_SIGNATURE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_customer = data.getData();
                    Log.e("filePath_customer", filePath_customer.toString());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_customer);
                        // imageView.setImageBitmap(bitmap);
                        signature_image.setImageBitmap(bitmap);
                        adhar_owner_image.setImageBitmap(bitmap);
                        customer_image_select = getPath1(filePath_customer);
                        Log.e("owner_image_select+", "" + customer_image_select);
                        // new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
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

    public String getPath1(Uri uri) {
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

    private void Edit_text() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.edit_text_layout);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);

        EditText any_other_edit = (EditText) dialog.findViewById(R.id.any_other_edit);
        save = (Button) dialog.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Kyc_Foram_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Kyc_Foram_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Kyc_Foram_Activity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Kyc_Foram_Activity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITEN_Permision);
        }
    }


    private void Ownar_Signature() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.customer_signature);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
        adhar_owner_image = dialog.findViewById(R.id.adhar_owner_image);
        Button adhar_button = dialog.findViewById(R.id.adhar_button);
        TextView signature_select = dialog.findViewById(R.id.signature_select);
        TextView image_select = dialog.findViewById(R.id.image_select);
        TextView save_select = dialog.findViewById(R.id.save_select);
        final LinearLayout signature_layout = dialog.findViewById(R.id.signature_layout);
        final LinearLayout image_capture_layout = dialog.findViewById(R.id.image_capture_layout);
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        ownar_name_no = dialog.findViewById(R.id.ownar_name_no);
        ownar_signature_view = (SignatureView) dialog.findViewById(R.id.ownar_signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ownar_signature_view.clearCanvas();
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
                Bitmap bitmap = ownar_signature_view.getSignatureBitmap();
                signature_path = saveImage(bitmap);
                //signature_image.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        signature_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature_layout.setVisibility(View.VISIBLE);
                image_capture_layout.setVisibility(View.GONE);
            }
        });
        image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature_layout.setVisibility(View.GONE);
                image_capture_layout.setVisibility(View.VISIBLE);
            }
        });

        adhar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Owner_address();
            }
        });
        save_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void Signature_Method() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);

        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        crose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = signatureView.getSignatureBitmap();
                path = saveImage(bitmap);
                signature_image.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        dialog.show();

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
        adhar_owner_image = dialog.findViewById(R.id.adhar_owner_image);
        Button adhar_button = dialog.findViewById(R.id.adhar_button);
        TextView signature_select = dialog.findViewById(R.id.signature_select);
        TextView image_select = dialog.findViewById(R.id.image_select);
        TextView save_select = dialog.findViewById(R.id.save_select);
        final LinearLayout signature_layout = dialog.findViewById(R.id.signature_layout);
        final LinearLayout image_capture_layout = dialog.findViewById(R.id.image_capture_layout);
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        ownar_name_no = dialog.findViewById(R.id.ownar_name_no);
        ownar_name_no.setVisibility(View.GONE);
        signatureView = (SignatureView) dialog.findViewById(R.id.ownar_signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);
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
                Bitmap bitmap = signatureView.getSignatureBitmap();
                customer_image_select = saveImage(bitmap);
                signature_image.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        signature_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature_layout.setVisibility(View.VISIBLE);
                image_capture_layout.setVisibility(View.GONE);
            }
        });
        image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature_layout.setVisibility(View.GONE);
                image_capture_layout.setVisibility(View.VISIBLE);
            }
        });

        adhar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Signature_Image();
            }
        });
        save_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void Owner_address() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_OWNER_IMAGE_REQUEST_ADDRESS);
                    }
                });

       /* myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(New_Regestration_Form.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_OWNER_REQUEST_ADDRESS);
                    }
                });*/
        myAlertDialog.show();
    }

    private void User_Signature_Image() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_CUSTOMER_IMAGE_SIGNATURE);
                    }
                });

        myAlertDialog.show();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        // have the object build the directory structure, if needed.
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

    public void uploadMultipart() {
        materialDialog = new MaterialDialog.Builder(Kyc_Foram_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        if (signature_path == null) {
            signature_path = customer_image_select;
            Log.e("signature_path", signature_path);
        }
        if (Type_Of_Owner.equals("Owner")) {
            ownar_name = "null";
        } else {
            ownar_name = ownar_name_no.getText().toString();
        }
        try {

            String uploadId = UUID.randomUUID().toString();
            Log.e("uploadId+,,,,,,,,,,", "testing" + uploadId);
            MultipartUploadRequest uploadRequest = new MultipartUploadRequest(Kyc_Foram_Activity.this, uploadId, Constants.BP_No_Post + "/" + getIntent().getStringExtra("Bp_number"));

            uploadRequest.addFileToUpload(image_path_aadhar, "doc1");
            uploadRequest.addFileToUpload(image_path_address, "doc2");
            uploadRequest.addFileToUpload(customer_image_select, "sign_file");
            uploadRequest.addFileToUpload(signature_path, "ownerSign");
            uploadRequest.addParameter("first_name", fullname.getText().toString());
            uploadRequest.addParameter("middle_name", middle_name.getText().toString());
            uploadRequest.addParameter("last_name", lastname.getText().toString());
            uploadRequest.addParameter("mobile_number", mobile_no.getText().toString());
            uploadRequest.addParameter("email_id", email_id.getText().toString());
            uploadRequest.addParameter("aadhaar_number", aadhaar_no.getText().toString());
            uploadRequest.addParameter("city_region", city_name);
            uploadRequest.addParameter("area", area_name);
            uploadRequest.addParameter("society", soceity_name);
            uploadRequest.addParameter("landmark", landmark.getText().toString());
            uploadRequest.addParameter("house_type", house_type_name);
            uploadRequest.addParameter("house_no", house_no.getText().toString());
            uploadRequest.addParameter("block_qtr_tower_wing", block_tower_name);
            uploadRequest.addParameter("floor", floor_name);
            uploadRequest.addParameter("street_gali_road", street_road_name);
            uploadRequest.addParameter("pincode", pincode.getText().toString());
            uploadRequest.addParameter("lpg_company", lpg_company_name);
            uploadRequest.addParameter("customer_type", customer_type);
            uploadRequest.addParameter("lpg_distributor", lpg_distributer.getText().toString());
            uploadRequest.addParameter("lpg_consumer_no", lpg_consumer.getText().toString());
            uploadRequest.addParameter("unique_id", unique_id_no.getText().toString());
            uploadRequest.addParameter("meterNo", meater_no.getText().toString());
            uploadRequest.addParameter("ownerName", ownar_name);
            uploadRequest.addParameter("chequeNo", chequeno_edit.getText().toString());
            uploadRequest.addParameter("chequeDate", chequedate_edit.getText().toString());
            uploadRequest.addParameter("drawnOn", drawnon_edit.getText().toString());
            uploadRequest.addParameter("amt", amount_edit.getText().toString());
            uploadRequest.addParameter("idProof", id_proof);
            uploadRequest.addParameter("adressProof", address_proof);
            uploadRequest.addParameter("type_of_owner", Type_Of_Owner);
            uploadRequest.addParameter("latitude", Latitude);
            uploadRequest.addParameter("longitude", Longitude);
            uploadRequest.addParameter("select1", block_tower_type_name);
            uploadRequest.addParameter("select2", street_road_type_name);
            uploadRequest.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(Context context, UploadInfo uploadInfo) {

                }

                @Override
                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                    materialDialog.dismiss();
                    //hideProgressDialog();
                    //Dilogbox_Error();
                    //   Log.e("Uplodeerror++", uploadInfo.getSuccessfullyUploadedFiles().toString());
                }

                @Override
                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                    materialDialog.dismiss();
                    //hideProgressDialog();
                    String Uplode = uploadInfo.getSuccessfullyUploadedFiles().toString();
                    String serverResponse1 = serverResponse.getHeaders().toString();
                    String str = serverResponse.getBodyAsString();
                    final JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(str);
                        Log.e("Response++", jsonObject.toString());
                        // if (jsonObject.getString("success").equals("true")) {
                        JSONArray Details = jsonObject.getJSONArray("Details");
                        for (int i = 0; i < Details.length(); i++) {
                            JSONObject filepath = Details.getJSONObject(i);
                            pdf_file_path = filepath.getString("file_path");
                            //String Message=filepath.getString("Bp_Number");
                            // Dilogbox_Select_Option();
                        }
                        String Message = jsonObject.getString("Message");
                        //String Complete_Video_Url = success.getString("url");
                        Toast.makeText(Kyc_Foram_Activity.this, Message, Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(Context context, UploadInfo uploadInfo) {
                    //hideProgressDialog();
                    materialDialog.dismiss();
                }
            });
            uploadRequest.setMaxRetries(5);
            //  uploadRequest.setNotificationConfig(new UploadNotificationConfig());
            uploadRequest.startUpload(); //Starting the upload
            Log.e("KEY_IMAGE+,,,,,,,,,,", "" + "");


            Log.e("aadhaar_file", image_path_aadhar);
            Log.e("pan_file", image_path_address);
            Log.e("sign_file", customer_image_select);
            Log.e("ownerSign", signature_path);
            Log.e("first_name", fullname.getText().toString());
            Log.e("Middle_Name", middle_name.getText().toString());
            Log.e("Last_Name", lastname.getText().toString());
            Log.e("Mobile_Number", mobile_no.getText().toString());
            Log.e("Email_ID", email_id.getText().toString());
            Log.e("Aadhaar_Number", aadhaar_no.getText().toString());
            Log.e("City_Region", city_name);
            Log.e("Area", area_name);
            Log.e("Society", soceity_name);
            Log.e("Landmark", landmark.getText().toString());
            Log.e("House_Type", house_type_name);
            Log.e("HouseNo", house_no.getText().toString());
            Log.e("Block_Qtr_Tower_Wing", block_tower_name);
            Log.e("Floor", floor_name);
            Log.e("Street_Gali_Road", street_road_name);
            Log.e("Pin_Code", pincode.getText().toString());
            Log.e("LPG_Company", lpg_company_name);
            Log.e("Customer_Type", customer_type);
            Log.e("LPG_DISTRIBUTOR", lpg_distributer.getText().toString());
            Log.e("LPG_CONSUMER_NO", lpg_consumer.getText().toString());
            Log.e("UNIQUE_LPG_ID", unique_id_no.getText().toString());
            Log.e("ownerName", ownar_name_no.getText().toString());
            Log.e("chequeNo", chequeno_edit.getText().toString());
            Log.e("chequeDate", chequedate_edit.getText().toString());
            Log.e("drawnOn", drawnon_edit.getText().toString());
            Log.e("amt", amount_edit.getText().toString());
            Log.e("idProof", id_proof);
            Log.e("adressProof", address_proof);
            Log.e("type_of_owner", Type_Of_Owner);
        } catch (Exception exc) {
            // Toast.makeText(Kyc_Foram_Activity.this, "Please Select Proper Signature", Toast.LENGTH_SHORT).show();
            //hideProgressDialog();
            materialDialog.dismiss();
        }
    }

    private void openPdf() {

        File file = new File(pdf_file_path);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            Log.e("uri++", uri.toString());
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(Kyc_Foram_Activity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void Dilogbox_Select_Option() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pdf_dilogbox);
        //dialog.setCancelable(false);

        Button view_pdf = (Button) dialog.findViewById(R.id.view_pdf);
        Button cancle = (Button) dialog.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                finish();
            }
        });

        view_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPdf();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void loadSpinnerData() {
        CityId.clear();
        CityName.clear();
        materialDialog = new MaterialDialog.Builder(Kyc_Foram_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.City_List, new Response.Listener<String>() {
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, CityName);
                    spinner_city.setAdapter(adapter);
                    String City_region = getIntent().getStringExtra("City_region");
                    Log.e("City_region++", City_region);
                    if (City_region != null) {
                        int spinnerPosition = adapter.getPosition(City_region);
                        spinner_city.setSelection(spinnerPosition);
                    }

                    // spinner_city.setAdapter(new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, CityName));
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

    private void loadSpinner_reasion_city(final String city_id) {
        // Society.clear();
        Area.clear();
        Area_Id.clear();
        Block_tower_name.clear();
        Customer_Type.clear();
        Street_road_name.clear();
        House_type_name.clear();
        Floor_name.clear();
        materialDialog = new MaterialDialog.Builder(Kyc_Foram_Activity.this)
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
                        Area.add(area_select);
                        Area_Id.add(area_id);
                    }
                    JSONArray jsonArray_block_no = jsonObject.getJSONArray("block_no");
                    for (int i = 0; i < jsonArray_block_no.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_block_no.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("block_no");
                        Block_tower_name.add(society_name_select);

                    }
                    JSONArray jsonArray_customer_type = jsonObject.getJSONArray("customer_type");
                    for (int i = 0; i < jsonArray_customer_type.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_customer_type.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("customer_type");
                        Customer_Type.add(society_name_select);

                    }
                    /*JSONArray jsonArray_society=jsonObject.getJSONArray("society");
                    for(int i=0;i<jsonArray_society.length();i++){
                        JSONObject jsonObject1=jsonArray_society.getJSONObject(i);
                        String society_name_select=jsonObject1.getString("society_name");
                        Society.add(society_name_select);

                    }*/
                    JSONArray jsonArray_street_no = jsonObject.getJSONArray("street_no");
                    for (int i = 0; i < jsonArray_street_no.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_street_no.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("street_no");
                        Street_road_name.add(society_name_select);

                    }
                    JSONArray jsonArray_house_type = jsonObject.getJSONArray("house_type");
                    for (int i = 0; i < jsonArray_house_type.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_house_type.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("house_type");
                        House_type_name.add(society_name_select);
                    }

                    JSONArray jsonArray_floor = jsonObject.getJSONArray("floor");
                    for (int i = 0; i < jsonArray_floor.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_floor.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("floor_name");
                        Floor_name.add(society_name_select);

                    }
                    ArrayAdapter<String> area_adapter = new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Area);
                    ArrayAdapter<String> block_tower_adapter = new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Block_tower_name);
                    ArrayAdapter<String> customertype_adapter = new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Customer_Type);
                    ArrayAdapter<String> street_road_adapter = new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Street_road_name);
                    ArrayAdapter<String> house_type_adapter = new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, House_type_name);
                    ArrayAdapter<String> floor_adapter = new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Floor_name);

                    // spinner_area.setAdapter(new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Area));
                    /*spinner_block_tower.setAdapter(new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Block_tower_name));
                    spinner_customertype.setAdapter(new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Customer_Type));
                    //
                    spinner_street_road.setAdapter(new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Street_road_name));
                    spinner_house_type.setAdapter(new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, House_type_name));
                    spinner_floor.setAdapter(new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Floor_name));
*/


                    spinner_area.setAdapter(area_adapter);
                    spinner_block_tower.setAdapter(block_tower_adapter);
                    spinner_customertype.setAdapter(customertype_adapter);

                    spinner_street_road.setAdapter(street_road_adapter);
                    spinner_house_type.setAdapter(house_type_adapter);
                    spinner_floor.setAdapter(floor_adapter);
                    String area = getIntent().getStringExtra("Area");
                    String block_tower = getIntent().getStringExtra("Block_qtr_tower_wing");
                    // String customertype=getIntent().getStringExtra("Area");

                    String street_road = getIntent().getStringExtra("Street_gali_road");
                    String house_type = getIntent().getStringExtra("House_type");
                    String floor = getIntent().getStringExtra("Floor");
                    Log.e("srea", area);
                    if (area != null) {
                        int spinnerPosition = area_adapter.getPosition(area);
                        spinner_area.setSelection(spinnerPosition);
                    }
                    if (block_tower != null) {
                        int spinnerPosition = block_tower_adapter.getPosition(block_tower);
                        spinner_block_tower.setSelection(spinnerPosition);
                    }
                    /*if ( customertype!= null) {
                        int spinnerPosition = customertype_adapter.getPosition(area);
                        spinner_area.setSelection(spinnerPosition);
                    }*/

                    if (street_road != null) {
                        int spinnerPosition = street_road_adapter.getPosition(street_road);
                        spinner_street_road.setSelection(spinnerPosition);
                    }
                    if (house_type != null) {
                        int spinnerPosition = house_type_adapter.getPosition(house_type);
                        spinner_house_type.setSelection(spinnerPosition);
                    }
                    if (floor != null) {
                        int spinnerPosition = floor_adapter.getPosition(floor);
                        spinner_floor.setSelection(spinnerPosition);
                    }

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
        materialDialog = new MaterialDialog.Builder(Kyc_Foram_Activity.this)
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
                    ArrayAdapter<String> society_adapter = new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Society);
                    //  spinner_society.setAdapter(new ArrayAdapter<String>(Kyc_Foram_Activity.this, android.R.layout.simple_spinner_dropdown_item, Society));
                    spinner_society.setAdapter(society_adapter);
                    String society = getIntent().getStringExtra("Society");

                    if (society != null) {
                        int spinnerPosition = society_adapter.getPosition(society);
                        spinner_society.setSelection(spinnerPosition);
                    }
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


    private boolean validateData() {
        boolean isDataValid = true;

        if (fullname.getText().length() == 0) {
            isDataValid = false;
            fullname.setError("Enter First Name");
        }
        if (lastname.getText().length() == 0) {
            isDataValid = false;
            lastname.setError("Enter Last Name");
        }
        if (mobile_no.getText().length() == 0) {
            isDataValid = false;
            mobile_no.setError("Enter Mobile No");
        }
        //if (meater_no.getText().length()==0) {
        if (TextUtils.isEmpty(meater_no.getText().toString().trim())) {
            isDataValid = false;
            meater_no.setError("Enter Meter No");
        }
        if (TextUtils.isEmpty(customer_type)) {

            customer_type="NA";
        }
        if (TextUtils.isEmpty(customer_image_select)) {
            isDataValid = false;
            Toast.makeText(Kyc_Foram_Activity.this,"Please Select Customer Signature",Toast.LENGTH_SHORT).show();

        }
        /*if (lpg_company_name.equals("Select LPG Company")) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Select LPG Company",Toast.LENGTH_SHORT).show();
        }
        if (customer_type.equals("Select Customer Type")) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Select Customer Type",Toast.LENGTH_SHORT).show();
        }*/
        if (house_no.getText().length() == 0) {
            isDataValid = false;
            house_no.setError("Enter House No");
            //Toast.makeText(New_Regestration_Form.this,"Enter Society",Toast.LENGTH_SHORT).show();
        }
        if (pincode.getText().length() == 0) {
            isDataValid = false;
            pincode.setError("Enter PinCode");
        }
        //if (image_path_aadhar == null) {
        if (TextUtils.isEmpty(image_path_aadhar)) {
            isDataValid = false;
            Toast.makeText(Kyc_Foram_Activity.this, "Please Select Image ID Proof", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(image_path_address)) {
            //if (image_path_address== null) {
            isDataValid = false;
            Toast.makeText(Kyc_Foram_Activity.this, "Please Select Image Address Proof", Toast.LENGTH_SHORT).show();
        }
        /*if (signature_path== null) {
            isDataValid = false;
            Toast.makeText(Kyc_Foram_Activity.this,"Please Select Customer Signature",Toast.LENGTH_SHORT).show();
        }*/
        return isDataValid;
    }


    private void getLocationUsingInternet() {
        boolean isInternetConnected = new ConnectionDetector(Kyc_Foram_Activity.this).isConnectingToInternet();
        if (isInternetConnected) {
            // getLocation_usingInternet.setEnabled(false);
            new GPSLocation(Kyc_Foram_Activity.this).turnGPSOn();// First turn on GPS
            String getLocation = new GPSLocation(Kyc_Foram_Activity.this).getMyCurrentLocation();// Get current location from
            Log.e("getLocation++", getLocation.toString());
            Latitude = GPSLocation.Latitude;
            Longitude = GPSLocation.Longitude;
            Log.e("Latitude++", Latitude);
            Log.e("Longitude++", Longitude);
        } else {
            Toast.makeText(Kyc_Foram_Activity.this, "There is no internet connection.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        new GPSLocation(Kyc_Foram_Activity.this).turnGPSOff();
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}