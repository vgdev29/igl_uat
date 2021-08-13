package com.fieldmobility.igl.Activity;

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
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.ConnectionDetector;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.GPSLocation;
import com.fieldmobility.igl.Helper.ScreenshotUtils;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;
import com.kyanogen.signatureview.SignatureView;
import com.squareup.picasso.Picasso;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;


import net.gotev.uploadservice.ContentType;
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
import static com.fieldmobility.igl.Activity.Selfie_Activity.MY_PERMISSIONS_REQUEST_CAMERA;

public class Kyc_Form_Activity extends Activity {
    MaterialDialog materialDialog;
    Button select_button;
    EditText fullname;
    EditText middle_name;
    EditText lastname;
    EditText mobile_no;
    EditText email_id;
    EditText aadhaar_no;
    EditText landmark;
    EditText house_no;
    EditText pincode;
    EditText ownar_name_no;
    EditText meater_no;
    Button submit_button;
    SharedPrefs sharedPrefs;
    RadioGroup radioGroup;
    RadioButton genderradioButton;
    //Bitmap bitmap;
    Button clear, save;
    SignatureView customer_signature_view, owner_signature_view;
    int WRITEN_Permision;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    ImageView back;
    String pdf_file_path;
    LinearLayout todo_creation;
    private final int PICK_ID_IMAGE_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST_ADDRESS = 3;
    protected static final int CAMERA_ID_REQUEST = 200;
    protected static final int CAMERA_REQUEST_ADDRESS = 201;
    private final int PICK_OWNER_SIGNATUREIMG_REQUEST = 4;
    private final int PICK_CUSTOMER_IMAGE_SIGNATURE = 5;
    private Uri filePath_aadhaar, filePath_address, filePath_owner, filePath_customer;
    String image_path_id, image_path_address, customer_signature_path, owner_signature_path;
    Button customer_image_button, id_button, address_button, owner_image_button;
    Spinner address_proof_spinner, id_proof_spinner;
    Spinner spinner_city;
    ArrayList<String> CityName;
    ArrayList<String> CityId;
    ArrayList<String> Area;
    ArrayList<String> Area_Id;
    ArrayList<String> Society;
    ArrayList<String> Landmark;
    ArrayList<String> House_type_name;
    ArrayList<String> Floor_name;
    ArrayList<String> Block_tower_name;
    ArrayList<String> Street_road_name;
    ArrayList<String> Lpg_company_name;
    String id_proof, address_proof, Type_Of_Owner, ownar_name;
    String city_id, city_name, area_name, area_id, soceity_name, house_type_name, floor_name, block_tower_name, street_road_name, lpg_company_name, street_road_type_name, block_tower_type_name;
    Spinner spinner_area, spinner_society, spinner_house_type, spinner_floor, spinner_block_tower, spinner_street_road, spinner_lpg_company;
    ImageView owner_sigimage, id_image, address_image, customer_sigimage;
    ;
    TextView bp_no_text, tv_pdf_path;
    CheckBox checkBox_term_cond;
    TextView checkbox_text;
    String Latitude, Longitude;
    Spinner spinner_street_road_type, spinner_block_tower_type;
    ArrayList<String> Street_road_type_name;
    ArrayList<String> Block_tower_type_name;
    private String log = "kycform";
    LinearLayout ll_owner_signature, ll_capture_id, ll_capture_address, ll_capture_custmsig, ll_capture_ownersig;
    Bitmap bitmap_id, bitmap_address, bitmap_custmsig, bitmap_ownersig;
    File file_id, file_address, file_cutmsig, file_ownersig;
    String screenshot_id, screenshot_address, screenshot_custsig, screenshot_ownersig, bp_no, pdf_path;
    EditText owner_name;
    boolean isSaleDeedSelected = false;
    String intent_addressImage, intent_idImage, intent_cusImage, intent_ownImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_form_activity);
        sharedPrefs = new SharedPrefs(this);
        getLocationUsingInternet();
        back = (ImageView) findViewById(R.id.back);
        tv_pdf_path = findViewById(R.id.tv_pdf_path);
        ll_capture_id = findViewById(R.id.ll_capture_id);
        ll_capture_address = findViewById(R.id.ll_capture_address);
        ll_capture_custmsig = findViewById(R.id.ll_capture_customersig);
        ll_capture_ownersig = findViewById(R.id.ll_capture_ownersig);
        owner_name = findViewById(R.id.owner_name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        owner_sigimage = findViewById(R.id.owner_signature_image);
        owner_image_button = findViewById(R.id.owner_image_button);
        ll_owner_signature = findViewById(R.id.ll_owner_signature);
        fullname = findViewById(R.id.fullname);
        middle_name = findViewById(R.id.middle_name);
        lastname = findViewById(R.id.lastname);
        mobile_no = findViewById(R.id.mobile_no);
        email_id = findViewById(R.id.email_id);
        aadhaar_no = findViewById(R.id.aadhaar_no);
        landmark = findViewById(R.id.landmark);
        house_no = findViewById(R.id.house_no);
        pincode = findViewById(R.id.pincode);
        meater_no = findViewById(R.id.meater_no);
        submit_button = findViewById(R.id.submit_button);
        id_image = findViewById(R.id.adhar_image);
        address_image = findViewById(R.id.address_image);
        customer_sigimage = findViewById(R.id.signature_image);
        customer_image_button = findViewById(R.id.customer_image_button);
        id_button = findViewById(R.id.id_button);
        address_button = findViewById(R.id.address_button);
        checkBox_term_cond = findViewById(R.id.checkbox);
        checkbox_text = findViewById(R.id.checkbox_text);
        todo_creation = findViewById(R.id.todo_creation);
        bp_no_text = findViewById(R.id.bp_no_text);
        address_proof_spinner = (Spinner) findViewById(R.id.address_proof_spinner);
        id_proof_spinner = (Spinner) findViewById(R.id.id_proof_spinner);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_area = (Spinner) findViewById(R.id.spinner_area);
        spinner_society = (Spinner) findViewById(R.id.spinner_society);
        spinner_house_type = (Spinner) findViewById(R.id.spinner_house_type);
        spinner_floor = (Spinner) findViewById(R.id.spinner_floor);
        spinner_block_tower = (Spinner) findViewById(R.id.spinner_block_tower);
        spinner_street_road = (Spinner) findViewById(R.id.spinner_street_road);
        spinner_lpg_company = (Spinner) findViewById(R.id.spinner_lpg_company);
        spinner_block_tower_type = (Spinner) findViewById(R.id.spinner_block_tower_type);
        spinner_street_road_type = (Spinner) findViewById(R.id.spinner_street_road_type);
        bp_no_text.setText("BP-" + getIntent().getStringExtra("Bp_number"));
        bp_no = getIntent().getStringExtra("Bp_number");
        intent_addressImage = getIntent().getStringExtra("address");
        intent_idImage = getIntent().getStringExtra("id");
        intent_cusImage = getIntent().getStringExtra("custsig");
        intent_ownImage = getIntent().getStringExtra("ownsig");

        Picasso.with(this)
                .load(intent_addressImage)

                .into(address_image);
        Picasso.with(this)
                .load(intent_idImage)

                .into(id_image);
        Picasso.with(this)
                .load(intent_cusImage)

                .into(customer_sigimage);
        Picasso.with(this)
                .load(intent_ownImage)

                .into(owner_sigimage);


        String Address = getIntent().getStringExtra("House_no") + " " +
                getIntent().getStringExtra("House_type") + " " +
                getIntent().getStringExtra("Landmark") + " " +
                getIntent().getStringExtra("Society") + " " +
                getIntent().getStringExtra("Area") + " " +
                getIntent().getStringExtra("City_region");

        todo_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kyc_Form_Activity.this, To_Do_Task_Creation.class);
                intent.putExtra("Bp_number", getIntent().getStringExtra("Bp_number"));
                intent.putExtra("Address", Address);
                intent.putExtra("Type", "1");
                startActivity(intent);
            }
        });

        customer_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCustomerSignature();
                //Customer_Signature1();
            }
        });
        owner_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOnwerSignature();
            }
        });
        id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage_ID();
            }
        });
        address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSaleDeedSelected)
                    selectPdf();
                else
                    selectImage_address();
            }
        });

        List<String> address_list = new ArrayList<String>();
        address_list.add("Electricity Bill");
        address_list.add("Water Bill");
        address_list.add("Sale/Conveyance Deed");
        address_list.add("House Tax Receipt");
        address_list.add("Mutation Copy");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, address_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address_proof_spinner.setAdapter(dataAdapter);

        List<String> id_list = new ArrayList<String>();
        id_list.add("Aadhar Card");
        id_list.add("Voter-ID card");
        id_list.add("Pan card");
        id_list.add("Driving License");
        id_list.add("Passport");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, id_list);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_proof_spinner.setAdapter(dataAdapter1);

        address_proof_spinner.setSelection(0, false);
        address_proof_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                address_proof = address_proof_spinner.getItemAtPosition(address_proof_spinner.getSelectedItemPosition()).toString();
                if (address_proof.equals("Sale/Conveyance Deed")) {
                    isSaleDeedSelected = true;
                    address_button.setText("Browse file");
                    address_image.setVisibility(View.GONE);
                    tv_pdf_path.setVisibility(View.VISIBLE);
                    tv_pdf_path.setText("");
                    pdf_path = "";

                } else {
                    isSaleDeedSelected = false;
                    address_button.setText("Address Proof");
                    tv_pdf_path.setVisibility(View.GONE);
                    address_image.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        id_proof_spinner.setSelection(0, false);
        id_proof_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                id_proof = id_proof_spinner.getItemAtPosition(id_proof_spinner.getSelectedItemPosition()).toString();

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
                        Type_Of_Owner = "Owner";
                        ll_owner_signature.setVisibility(View.GONE);
                        owner_name.setVisibility(View.GONE);
                        break;
                    case R.id.rents:
                        Type_Of_Owner = "Rented";
                        ll_owner_signature.setVisibility(View.VISIBLE);
                        owner_name.setVisibility(View.VISIBLE);
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
        landmark.setText(getIntent().getStringExtra("Landmark"));
        house_no.setText(getIntent().getStringExtra("House_no"));
        pincode.setText(getIntent().getStringExtra("Pincode"));
        //  customer_type.setText(getIntent().getStringExtra("Customer_type"));


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                checkPermission();
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        }

        if (ContextCompat.checkSelfPermission(Kyc_Form_Activity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) Kyc_Form_Activity.this, Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions((Activity) Kyc_Form_Activity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }

        }
        loadSpinnerData();
        spinner_city.setSelection(0, false);
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_city.getItemAtPosition(spinner_city.getSelectedItemPosition()).toString();
                Log.d("CITY+", country);
                city_id = CityId.get(position);
                city_name = CityName.get(position);
                loadSpinner_reasion_city(city_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_area.setSelection(0, false);
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_area.getItemAtPosition(spinner_area.getSelectedItemPosition()).toString();
                Log.d("area_name+", country);
                area_name = Area.get(position);
                area_id = Area_Id.get(position);
                loadSpinnerSocity(area_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_society.setSelection(0, false);
        spinner_society.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_society.getItemAtPosition(spinner_society.getSelectedItemPosition()).toString();
                Log.d("Society+", country);
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
                Log.d("house_type_name+", country);
                house_type_name = House_type_name.get(position);
                //city_name=CityName.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_floor.setSelection(0, false);
        spinner_floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_floor.getItemAtPosition(spinner_floor.getSelectedItemPosition()).toString();
                Log.d("floor_name+", country);
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
                Log.d("block_tower_name+", country);
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
                Log.d("street_road_name+", country);
                street_road_name = Street_road_name.get(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_lpg_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String customer_type11 = spinner_lpg_company.getItemAtPosition(spinner_lpg_company.getSelectedItemPosition()).toString();
                Log.d("lpg_company_name+", customer_type11);
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
                Log.d("lpg_company_name+", customer_type11);
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
                Log.d("lpg_company_name+", customer_type11);
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
                Intent intent = new Intent(Kyc_Form_Activity.this, WebView_Activity.class);
                startActivity(intent);

            }
        });
        checkBox_term_cond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox_term_cond.setChecked(true);
                    //  Toast.makeText(Kyc_Form_Activity.this, "You checked the checkbox!", Toast.LENGTH_SHORT).show();
                } else {
                    checkBox_term_cond.setChecked(false);
                    //  Toast.makeText(Kyc_Form_Activity.this, "You unchecked the checkbox!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    takeScreenshot();
                    uploadMultipart();
                }
            }
        });

        statusCheck();
    }


    private void selectImage_ID() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_ID_IMAGE_REQUEST);
                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(Kyc_Form_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_ID_REQUEST);
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
                        Uri photoURI = FileProvider.getUriForFile(Kyc_Form_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_ADDRESS);
                    }
                });
        myAlertDialog.show();
    }

    private void selectOnwerSignature() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_OWNER_SIGNATUREIMG_REQUEST);
                    }
                });

        myAlertDialog.setNegativeButton("Signature",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Owner_Signature();
                    }
                });
        myAlertDialog.show();
    }

    private void selectCustomerSignature() {
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

        myAlertDialog.setNegativeButton("Signature",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Customer_Signature();
                    }
                });
        myAlertDialog.show();
    }


    private void takeScreenshot() {

        try {
            bitmap_id = ScreenshotUtils.getScreenShot_ekyc_id(ll_capture_id);
            bitmap_address = ScreenshotUtils.getScreenShot_ekyc_address(ll_capture_address);
            bitmap_custmsig = ScreenshotUtils.getScreenShot_ekyc_custsig(ll_capture_custmsig);
            Log.d(log, "bitmap" + "size scresnshot = " + bitmap_custmsig.getByteCount());
            if (bitmap_id != null) {

                File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                file_id = ScreenshotUtils.store(bitmap_id, "id_" + bp_no + ".jpg", saveFile);
                file_address = ScreenshotUtils.store(bitmap_address, "address_" + bp_no + ".jpg", saveFile);
                file_cutmsig = ScreenshotUtils.store(bitmap_custmsig, "custsig_" + bp_no + ".jpg", saveFile);

                screenshot_id = file_id.toString();
                screenshot_address = file_address.toString();
                screenshot_custsig = file_cutmsig.toString();

                Log.d(log, "screenshot_id " + screenshot_id);
                Log.d(log, "screenshot_address = " + screenshot_address);
                Log.d(log, "screenshot_custsig = " + screenshot_custsig);

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
        }


    }

    private void takeScreenshot_owner() {

        try {
            bitmap_ownersig = ScreenshotUtils.getScreenShot_ekyc_ownersig(ll_capture_ownersig);
            File saveFile = ScreenshotUtils.getMainDirectoryName(this);
            file_ownersig = ScreenshotUtils.store(bitmap_ownersig, "ownersig_" + bp_no + ".jpg", saveFile);
            screenshot_ownersig = file_ownersig.toString();
            Log.d(log, "screenshot_ownersig = " + screenshot_ownersig);

        } catch (
                NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_ID_IMAGE_REQUEST:
                if (requestCode == PICK_ID_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_aadhaar = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_aadhaar);
                        // imageView.setImageBitmap(bitmap);
                        id_image.setImageBitmap(bitmap);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_id = getPath1(filePath_aadhaar);
                        Log.d(log, " PICK_IMAGE_REQUEST: image_path_aadhar+, " + "" + image_path_id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK) {
                    ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    Log.d("bpcreation", "pdf file list = " + list.size());
                    if (list != null && list.size() > 0) {
                        pdf_path = list.get(0).getPath();
                        Log.d("bpcreation", "pdf path = " + pdf_path);
                        tv_pdf_path.setText(list.get(0).getName());
                    }


                }
                break;
            case CAMERA_ID_REQUEST:
                if (resultCode == RESULT_OK && requestCode == CAMERA_ID_REQUEST) {
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
                        id_image.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.d(log, "CAMERA_REQUEST: Camera_Path++ request =  " + file.toString());
                        image_path_id = file.toString();
                        // image_path_address1 =file.toString();
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                            id_image.setImageBitmap(bitmap);
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
                    Log.d(log, "PICK_IMAGE_REQUEST_ADDRESS filepath = " + filePath_address.toString());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_address);
                        // imageView.setImageBitmap(bitmap);
                        address_image.setImageBitmap(bitmap);
                        image_path_address = getPath1(filePath_address);
                        Log.d(log, "PICK_IMAGE_REQUEST_ADDRESS image path" + "" + image_path_address);
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
                        Log.d(log, "CAMERA_REQUEST_ADDRESS Camera_Path++" + file.toString());
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
            case PICK_OWNER_SIGNATUREIMG_REQUEST:
                if (requestCode == PICK_OWNER_SIGNATUREIMG_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_owner = data.getData();
                    Log.d("PICK_OWNER_IMAGE_REQUEST_ADDRESS: Camera_Pathaddress++", filePath_owner.toString());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_owner);
                        owner_sigimage.setImageBitmap(bitmap);
                        owner_signature_path = getPath1(filePath_owner);
                        Log.d(log, "PICK_OWNER_IMAGE_REQUEST_ADDRESS: owner_image_select+" + "" + owner_signature_path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_CUSTOMER_IMAGE_SIGNATURE:
                if (requestCode == PICK_CUSTOMER_IMAGE_SIGNATURE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_customer = data.getData();
                    Log.d("PICK_CUSTOMER_IMAGE_SIGNATURE: filePath_customer", filePath_customer.toString());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_customer);
                        customer_sigimage.setImageBitmap(bitmap);
                        customer_signature_path = getPath1(filePath_customer);
                        Log.d(log, "PICK_CUSTOMER_IMAGE_SIGNATURE: owner_image_select+" + "" + customer_signature_path);
                        // new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }


    public String getPath(Uri contentUri) {
        try {
            String res = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
            return res;
        } catch (Exception e) {
            String path = null;
            Cursor cursor = this.getContentResolver().query(contentUri, null, null, null, null);
            Log.d(log, "catch = ");
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            path = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();
            return path;
        }
    }

    public String getPath1(Uri uri) {
        String path = null;
        try {
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            path = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();
            cursor = this.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        } catch (NullPointerException e) {
            Log.d(log, "null pontr excptn = " + e.getLocalizedMessage() + e.getMessage());
            e.printStackTrace();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(column_index);
            }
            cursor.close();

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.d(log, "cursor pontr excptn = " + e.getLocalizedMessage() + e.getMessage());

        }
        return path;
    }

    private void Owner_Signature() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
        owner_signature_view = (SignatureView) dialog.findViewById(R.id.signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owner_signature_view.clearCanvas();
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
                Bitmap bitmap = owner_signature_view.getSignatureBitmap();
                owner_signature_path = saveImage(bitmap);
                owner_sigimage.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void Customer_Signature() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
        customer_signature_view = (SignatureView) dialog.findViewById(R.id.signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer_signature_view.clearCanvas();
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
                Bitmap bitmap = customer_signature_view.getSignatureBitmap();
                customer_signature_path = saveImage(bitmap);
                customer_sigimage.setImageBitmap(bitmap);
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
        CommonUtils.startProgressBar(this, "Submitting Data...!!!");
        if (owner_signature_path == null) {
            screenshot_ownersig = screenshot_custsig;
            Log.d("signature_path", screenshot_ownersig);
        } else {
            takeScreenshot_owner();
        }
        if (Type_Of_Owner.equals("Owner")) {
            ownar_name = "";
        } else {
            ownar_name = owner_name.getText().toString().trim();
        }
        try {
            String uploadId = UUID.randomUUID().toString();
            Log.d(log, "uploadId+,,,,,,,,,," + "testing" + uploadId);
            MultipartUploadRequest uploadRequest = new MultipartUploadRequest(Kyc_Form_Activity.this, uploadId, Constants.BP_No_Post + "/" + getIntent().getStringExtra("Bp_number"));
            uploadRequest.addFileToUpload(screenshot_id, "doc1");
            if (isSaleDeedSelected) {
                uploadRequest.addFileToUpload(pdf_path, "doc2", "sale_deed.pdf", ContentType.APPLICATION_PDF);

            } else {
                uploadRequest.addFileToUpload(screenshot_address, "doc2");
            }
            uploadRequest.addFileToUpload(screenshot_custsig, "sign_file");
            uploadRequest.addFileToUpload(screenshot_ownersig, "ownerSign");
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
            uploadRequest.addParameter("customer_type", "");
            uploadRequest.addParameter("lpg_distributor", "");
            uploadRequest.addParameter("lpg_consumer_no", "");
            uploadRequest.addParameter("unique_id", "");
            uploadRequest.addParameter("meterNo", meater_no.getText().toString());
            uploadRequest.addParameter("ownerName", ownar_name);
            uploadRequest.addParameter("chequeNo", "");
            uploadRequest.addParameter("chequeDate", "");
            uploadRequest.addParameter("drawnOn", "");
            uploadRequest.addParameter("amt", "");
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
                    CommonUtils.dismissProgressBar(Kyc_Form_Activity.this);
                    Toast.makeText(Kyc_Form_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                    CommonUtils.dismissProgressBar(Kyc_Form_Activity.this);
                    //hideProgressDialog();
                    String Uplode = uploadInfo.getSuccessfullyUploadedFiles().toString();
                    String serverResponse1 = serverResponse.getHeaders().toString();
                    String str = serverResponse.getBodyAsString();
                    final JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(str);
                        Log.d("Response++", jsonObject.toString());
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
                        Toast.makeText(Kyc_Form_Activity.this, Message, Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(Kyc_Form_Activity.this, "Catching issues", Toast.LENGTH_SHORT).show();
                        CommonUtils.dismissProgressBar(Kyc_Form_Activity.this);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(Context context, UploadInfo uploadInfo) {
                    CommonUtils.dismissProgressBar(Kyc_Form_Activity.this);
                }
            });
            uploadRequest.setMaxRetries(5);
            uploadRequest.startUpload(); //Starting the upload
            Log.d("KEY_IMAGE+,,,,,,,,,,", "" + "");
            Log.d(log, "aadhaar_file" + screenshot_id);
            Log.d(log, "pan_file" + screenshot_address);
            Log.d(log, "sign_file" + screenshot_custsig);
            Log.d(log, "ownerSign" + screenshot_ownersig);
            Log.d(log, "first_name" + fullname.getText().toString());
            Log.d(log, "Middle_Name" + middle_name.getText().toString());
            Log.d(log, "Last_Name" + lastname.getText().toString());
            Log.d(log, "Mobile_Number" + mobile_no.getText().toString());
            Log.d(log, "Email_ID" + email_id.getText().toString());
            Log.d(log, "Aadhaar_Number" + aadhaar_no.getText().toString());
            Log.d(log, "City_Region" + city_name);
            Log.d(log, "Area" + area_name);
            Log.d(log, "Society" + soceity_name);
            Log.d(log, "Landmark" + landmark.getText().toString());
            Log.d(log, "House_Type" + house_type_name);
            Log.d(log, "HouseNo" + house_no.getText().toString());
            Log.d(log, "Block_Qtr_Tower_Wing" + block_tower_name);
            Log.d(log, "Floor" + floor_name);
            Log.d(log, "Street_Gali_Road" + street_road_name);
            Log.d(log, "Pin_Code" + pincode.getText().toString());
            Log.d(log, "LPG_Company" + lpg_company_name);
            Log.d(log, "Customer_Type" + "");
            Log.d(log, "LPG_DISTRIBUTOR" + "");
            Log.d(log, "LPG_CONSUMER_NO" + "");
            Log.d(log, "UNIQUE_LPG_ID" + "");
            Log.d(log, "ownerName" + owner_name.getText().toString());
            Log.d(log, "idProof" + id_proof);
            Log.d(log, "adressProof" + address_proof);
            Log.d(log, "type_of_owner" + Type_Of_Owner);
        } catch (Exception exc) {
            Toast.makeText(Kyc_Form_Activity.this, "Files Error", Toast.LENGTH_SHORT).show();
            Log.d(log, "upload mulitiprt catch = " + exc.getLocalizedMessage() + exc.getMessage());
            CommonUtils.dismissProgressBar(Kyc_Form_Activity.this);
        }
    }

    private void loadSpinnerData() {
        CityId.clear();
        CityName.clear();
        materialDialog = new MaterialDialog.Builder(Kyc_Form_Activity.this)
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Kyc_Form_Activity.this, android.R.layout.simple_spinner_dropdown_item, CityName);
                    spinner_city.setAdapter(adapter);
                    String City_region = getIntent().getStringExtra("City_region");
                    Log.d("City_region++", City_region);
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
        Street_road_name.clear();
        House_type_name.clear();
        Floor_name.clear();
        materialDialog = new MaterialDialog.Builder(Kyc_Form_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d(log, "customer type = " + Constants.Arealist_reason_Socity + "/" + city_id);
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


                    }

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
                    ArrayAdapter<String> area_adapter = new ArrayAdapter<String>(Kyc_Form_Activity.this, android.R.layout.simple_spinner_dropdown_item, Area);
                    ArrayAdapter<String> block_tower_adapter = new ArrayAdapter<String>(Kyc_Form_Activity.this, android.R.layout.simple_spinner_dropdown_item, Block_tower_name);
                    ArrayAdapter<String> street_road_adapter = new ArrayAdapter<String>(Kyc_Form_Activity.this, android.R.layout.simple_spinner_dropdown_item, Street_road_name);
                    ArrayAdapter<String> house_type_adapter = new ArrayAdapter<String>(Kyc_Form_Activity.this, android.R.layout.simple_spinner_dropdown_item, House_type_name);
                    ArrayAdapter<String> floor_adapter = new ArrayAdapter<String>(Kyc_Form_Activity.this, android.R.layout.simple_spinner_dropdown_item, Floor_name);


                    spinner_area.setAdapter(area_adapter);
                    spinner_block_tower.setAdapter(block_tower_adapter);
                    spinner_street_road.setAdapter(street_road_adapter);
                    spinner_house_type.setAdapter(house_type_adapter);
                    spinner_floor.setAdapter(floor_adapter);
                    String area = getIntent().getStringExtra("Area");
                    String block_tower = getIntent().getStringExtra("Block_qtr_tower_wing");
                    String street_road = getIntent().getStringExtra("Street_gali_road");
                    String house_type = getIntent().getStringExtra("House_type");
                    String floor = getIntent().getStringExtra("Floor");
                    Log.d("srea", area);
                    if (area != null) {
                        int spinnerPosition = area_adapter.getPosition(area);
                        spinner_area.setSelection(spinnerPosition);
                    }
                    if (block_tower != null) {
                        int spinnerPosition = block_tower_adapter.getPosition(block_tower);
                        spinner_block_tower.setSelection(spinnerPosition);
                    }

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
        materialDialog = new MaterialDialog.Builder(Kyc_Form_Activity.this)
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
                    ArrayAdapter<String> society_adapter = new ArrayAdapter<String>(Kyc_Form_Activity.this, android.R.layout.simple_spinner_dropdown_item, Society);
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

        if (TextUtils.isEmpty(fullname.getText().toString())) {
            isDataValid = false;
            fullname.setError("Enter First Name");
            CommonUtils.toast_msg(this, "Name cant be empty");

            return isDataValid;
        } else if (TextUtils.isEmpty(mobile_no.getText().toString().trim())) {
            isDataValid = false;
            mobile_no.setError("Enter Mobile No");
            CommonUtils.toast_msg(this, "Enter Mobile no.");
            return isDataValid;
        } else if (TextUtils.isEmpty(email_id.getText().toString().trim())) {
            isDataValid = false;
            email_id.setError("Enter Email Id");
            CommonUtils.toast_msg(this, "Enter email id");
            return isDataValid;
        } else if (TextUtils.isEmpty(city_name)) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Select City Name");
            return isDataValid;
        } else if (TextUtils.isEmpty(area_name)) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Select area");
            return isDataValid;
        } else if (TextUtils.isEmpty(soceity_name)) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Select Society");
            return isDataValid;
        } else if (TextUtils.isEmpty(house_no.getText().toString().trim())) {
            isDataValid = false;
            house_no.setError("Enter House No");
            CommonUtils.toast_msg(this, "Enter House no");
            return isDataValid;
        } else if (TextUtils.isEmpty(floor_name)) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Select floor");
            return isDataValid;
        } else if (TextUtils.isEmpty(pincode.getText().toString().trim())) {
            isDataValid = false;
            pincode.setError("Enter Pincode");
            CommonUtils.toast_msg(this, "Enter pin code");
            return isDataValid;
        } else if (TextUtils.isEmpty(image_path_id)) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Please Select Id proof");
            return isDataValid;
        } else if (TextUtils.isEmpty(image_path_address) && TextUtils.isEmpty(pdf_path)) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Please Select Address proof");
            return isDataValid;
        } else if (TextUtils.isEmpty(customer_signature_path)) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Please Select Customer signature");
            return isDataValid;
        } else if (Type_Of_Owner.equalsIgnoreCase("Rented") && TextUtils.isEmpty(owner_signature_path)) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Please Select Owner signature");
            return isDataValid;
        } else if (Type_Of_Owner.equalsIgnoreCase("Rented") && TextUtils.isEmpty(owner_name.getText().toString().trim())) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Please Enter Owner Name");
            owner_name.setError("Owner name mandatory");
            return isDataValid;
        } else if (!checkBox_term_cond.isChecked()) {
            isDataValid = false;
            CommonUtils.toast_msg(this, "Please tick to accept Term & Condition");
            return isDataValid;
        } else {
            return isDataValid;
        }
    }


    private void getLocationUsingInternet() {
        boolean isInternetConnected = new ConnectionDetector(Kyc_Form_Activity.this).isConnectingToInternet();
        if (isInternetConnected) {
            // getLocation_usingInternet.setEnabled(false);
            new GPSLocation(Kyc_Form_Activity.this).turnGPSOn();// First turn on GPS
            String getLocation = new GPSLocation(Kyc_Form_Activity.this).getMyCurrentLocation();// Get current location from
            Log.d("getLocation++", getLocation.toString());
            Latitude = GPSLocation.Latitude;
            Longitude = GPSLocation.Longitude;
            Log.d("Latitude++", Latitude);
            Log.d("Longitude++", Longitude);
        } else {
            Toast.makeText(Kyc_Form_Activity.this, "There is no internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        new GPSLocation(Kyc_Form_Activity.this).turnGPSOff();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_ID_REQUEST: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}
                }
            }
            break;

            case PICK_ID_IMAGE_REQUEST:
                if (requestCode == PICK_ID_IMAGE_REQUEST) {

                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private void openPdf() {

        File file = new File(pdf_file_path);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            Log.d("uri++", uri.toString());
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(Kyc_Form_Activity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
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
    private void  selectPdf() {
//        Intent intent = new Intent();
//        intent.setType("application/pdf");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select PDF"), REQUEST_CODE_PDF_PICKER);
        Intent intent4 = new Intent(this, NormalFilePickActivity.class);
        intent4.putExtra(Constant.MAX_NUMBER, 1);
        intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"pdf"});
        startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);


    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Kyc_Form_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Kyc_Form_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Kyc_Form_Activity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Kyc_Form_Activity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITEN_Permision);
        }
    }


}