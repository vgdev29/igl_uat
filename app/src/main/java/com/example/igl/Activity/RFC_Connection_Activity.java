package com.example.igl.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.example.igl.Adapter.DropDown_Adapter;
import com.example.igl.Adapter.Learning_Adapter;
import com.example.igl.Adapter.New_BP_NO_Adapter;
import com.example.igl.Helper.ConnectionDetector;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.GPSLocation;
import com.example.igl.Helper.RecyclerItemClickListener;
import com.example.igl.Helper.ScreenshotUtils;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MainActivity;
import com.example.igl.MataData.Bp_No_Item;
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
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static android.os.Environment.getExternalStorageDirectory;

public class RFC_Connection_Activity extends Activity implements DropDown_Adapter.ContactsAdapterListener {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    Spinner spinner_online_offline;
    String online_offline, property_type_string, gas_type_string;
    LinearLayout offline_layout;
    String pvc_sleeve_Avail_NotAvail, meater_intalation_OK_NotOk, clamping_gi_pvc_Done_NotDone, gas_meter_Done_NotDone, cementing_of_holes_Done_NotDone, painting_of_gi_pipe_Done_NotDone,
            hole_drilled_Yes_No, meter_control_valve_testing_Yes_No, natural_gas_usage_Yes_No, ng_conversion_Done_NotDone;
    RadioGroup pvc_sleeve_radioGroup, meater_intalation_radioGroup, clamping_gi_pvc_radioGroup, gas_meter_radioGroup,
            cementing_of_holes_radioGroup, painting_of_gi_pipe_radioGroup, hole_drilled_radioGroup, meter_control_valve_testing_radioGroup, natural_gas_usage_radioGroup, ng_conversion_radioGroup;
    RadioButton pvc_sleeve_radioButton, meater_intalation_radioButton, clamping_gi_pvc_radioButton, gas_meter_radioButton,
            cementing_of_holes_radioButton, painting_of_gi_pipe_radioButton, hole_drilled_radioButton,
            meter_control_valve_testing_radioButton, natural_gas_usage_radioButton, ng_conversion_radioButton;

    RadioButton connectivity_radioButton;
    RadioGroup connectivity_radioGroup;
    RadioButton ncap_avail_radioButton;
    RadioGroup ncap_avail_radioGroup;
    RadioButton tf_avail_radioButton;
    RadioGroup tf_avail_radioGroup;

    EditText manufacture_editext, type_nr_text, meater_no_text, initial_metar_reading_text, regulater_no_text, gi_instalation_text, cu_instalation_text, vo_of_iv_text, no_of_av_text;
    MaterialDialog materialDialog;
    String Online_Offline, signature;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    ImageView signature_image, select_image, select_image1, select_image2, select_image3;
    Button submit_button, image_button, image_button2, image_button3, image_button4, signature_button;
    protected static final int CAMERA_REQUEST = 1;
    protected static final int CAMERA_REQUEST2 = 2;
    protected static final int CAMERA_REQUEST3 = 3;
    protected static final int CAMERA_REQUEST4 = 4;
    private final int PICK_CUSTOMER_IMAGE_SIGNATURE = 5;
    private final int PICK_IMAGE_REQUEST = 8;
    private Uri filePath_Image, Multiple_file_IMG;
    String image_path_string, image_path_string1, image_path_string2, image_path_string3;
    Bitmap bitmap, bitmap_screenshot, bitmap_screenshot1, bitmap_screenshot2, bitmap_screenshot3;
    SignatureView signatureView;
    Button clear, save;
    private List<String> selectedVideos;
    EditText pile_length_edit;
    String MultipleImage_path, meter_type_string, manufacture_make_string, meater_no_string;
    String status, Multiple_Image_Uplode, Status_Type;
    ArrayList<String> Meter_type = new ArrayList<>();
    ArrayList<String> Meter_type_Id = new ArrayList<>();
    ArrayList<String> Manufacture_Make = new ArrayList<>();
    ArrayList<String> Manufacture_Make_ID = new ArrayList<>();
    ArrayList<String> Meter_no_type = new ArrayList<>();
    ArrayList<String> Meter_no = new ArrayList<>();
    Spinner spinner_meter_type, manufacture_make_spinner, meater_no_spinner, property_type_spinner, gas_type_spinner;
    ArrayAdapter<String> metar_type_adapter;
    ArrayAdapter<String> manufacture_make_adapter;
    ArrayAdapter<String> meater_no_adapter;
    String meter_type;
    File screenshot_file, screenshot_file1, screenshot_file2, screenshot_file3;
    String ScreenShot_1, ScreenShot_2, ScreenShot_3, ScreenShot_4;

    public static String Latitude, Longitude, address, city, state, country, pincode, Current_Time, Current_Date;
    FrameLayout capture_fragment, capture_fragment1, capture_fragment2, capture_fragment3;
    TextView time_stemp_text, time_stemp_text1, time_stemp_text2, time_stemp_text3;

    DropDown_Adapter dropDown_adapter;
    RecyclerView recyclerView;
    EditText editTextSearch;
    TextView meter_text;
    String meater_no_set_text, meter_no;
    final Handler handler = new Handler();
    private Uri filePath_customer;
    ImageView adhar_owner_image;
    EditText ownar_name_no;
    String customer_image_select;
    String LOG = "rfcConnection";
    String rfcAdmin;
    private String tf_avail_Done, connectivity_Done, ncap_avail_Done,igl_rfc_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rfc_connection_layout);
        sharedPrefs = new SharedPrefs(this);
        Layout_Id();
        rfcAdmin = getIntent().getStringExtra("rfcAdmin");
        getLocationWithoutInternet();
        getLocationUsingInternet();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage_Method();
            }
        });
        image_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage_Method2();
            }
        });
        image_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage_Method3();
            }
        });
        image_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage_Method4();
            }
        });
        signature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Signature_Method();
                Customer_Signature1();
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "on click event - ");
                takeScreenshot();
            }
        });
        tf_avail_Done = "Yes";
        ncap_avail_Done = "Yes";
        connectivity_Done = "Yes";
        pvc_sleeve_Avail_NotAvail = "Avail";
        meater_intalation_OK_NotOk = "OK";
        clamping_gi_pvc_Done_NotDone = "Done";
        gas_meter_Done_NotDone = "Done";
        cementing_of_holes_Done_NotDone = "Done";
        painting_of_gi_pipe_Done_NotDone = "Done";
        hole_drilled_Yes_No = "Yes";
        meter_control_valve_testing_Yes_No = "Yes";
        natural_gas_usage_Yes_No = "Yes";
        ng_conversion_Done_NotDone = "Yes";
        spinner_online_offline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                online_offline = spinner_online_offline.getItemAtPosition(spinner_online_offline.getSelectedItemPosition()).toString();
                if (online_offline.equals("Online")) {
                    offline_layout.setVisibility(View.GONE);
                    Online_Offline = "0";
                } else {
                    offline_layout.setVisibility(View.VISIBLE);
                    Online_Offline = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        property_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                property_type_string = property_type_spinner.getItemAtPosition(property_type_spinner.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        gas_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                gas_type_string = gas_type_spinner.getItemAtPosition(gas_type_spinner.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        int pvc_sleeve_selectedId = pvc_sleeve_radioGroup.getCheckedRadioButtonId();

        pvc_sleeve_radioButton = (RadioButton) findViewById(pvc_sleeve_selectedId);

        pvc_sleeve_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.avail:
                        pvc_sleeve_Avail_NotAvail = "Avail";
                        break;
                    case R.id.not_avail:
                        pvc_sleeve_Avail_NotAvail = "Not Avail";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        int meater_intalation_selectedId = meater_intalation_radioGroup.getCheckedRadioButtonId();
        meater_intalation_radioButton = (RadioButton) findViewById(meater_intalation_selectedId);
        meater_intalation_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.meater_intalation_ok:
                        meater_intalation_OK_NotOk = "OK";
                        break;
                    case R.id.meater_intalation_not_ok:
                        meater_intalation_OK_NotOk = "Not OK";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        int clamping_gi_pvc_selectedId = clamping_gi_pvc_radioGroup.getCheckedRadioButtonId();
        clamping_gi_pvc_radioButton = (RadioButton) findViewById(clamping_gi_pvc_selectedId);
        clamping_gi_pvc_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.clamping_gi_pvc_done:
                        clamping_gi_pvc_Done_NotDone = "Done";
                        break;
                    case R.id.clamping_gi_pvc_not_done:
                        clamping_gi_pvc_Done_NotDone = "Not Done";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        int gas_meter_selectedId = gas_meter_radioGroup.getCheckedRadioButtonId();
        gas_meter_radioButton = (RadioButton) findViewById(gas_meter_selectedId);
        gas_meter_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.gas_meter_done:
                        gas_meter_Done_NotDone = "Done";
                        break;
                    case R.id.gas_meter_not_done:
                        gas_meter_Done_NotDone = "Not Done";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        int cementing_of_holes_selectedId = cementing_of_holes_radioGroup.getCheckedRadioButtonId();
        cementing_of_holes_radioButton = (RadioButton) findViewById(cementing_of_holes_selectedId);
        cementing_of_holes_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cementing_of_holes_done:
                        cementing_of_holes_Done_NotDone = "Done";
                        break;
                    case R.id.cementing_of_holes_not_done:
                        cementing_of_holes_Done_NotDone = "Not Done";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        int painting_of_gi_pipe_selectedId = painting_of_gi_pipe_radioGroup.getCheckedRadioButtonId();
        painting_of_gi_pipe_radioButton = (RadioButton) findViewById(painting_of_gi_pipe_selectedId);
        painting_of_gi_pipe_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.painting_of_gi_pipe_done:
                        painting_of_gi_pipe_Done_NotDone = "Done";
                        break;
                    case R.id.painting_of_gi_pipe_not_done:
                        painting_of_gi_pipe_Done_NotDone = "Not Done";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        int hole_drilled_selectedId = hole_drilled_radioGroup.getCheckedRadioButtonId();
        hole_drilled_radioButton = (RadioButton) findViewById(hole_drilled_selectedId);
        hole_drilled_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hole_drilled_yes:
                        hole_drilled_Yes_No = "Yes";
                        break;
                    case R.id.hole_drilled_no:
                        hole_drilled_Yes_No = "No";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        int meter_control_valve_testing_selectedId = meter_control_valve_testing_radioGroup.getCheckedRadioButtonId();
        meter_control_valve_testing_radioButton = (RadioButton) findViewById(meter_control_valve_testing_selectedId);
        meter_control_valve_testing_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.meter_control_valve_testing_yes:
                        meter_control_valve_testing_Yes_No = "Yes";
                        break;
                    case R.id.meter_control_valve_testing_no:
                        meter_control_valve_testing_Yes_No = "No";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        int natural_gas_usage_selectedId = natural_gas_usage_radioGroup.getCheckedRadioButtonId();
        natural_gas_usage_radioButton = (RadioButton) findViewById(natural_gas_usage_selectedId);
        natural_gas_usage_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.natural_gas_usage_no:
                        natural_gas_usage_Yes_No = "Yes";
                        break;
                    case R.id.natural_gas_usage_yes:
                        natural_gas_usage_Yes_No = "No";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        int ng_conversion_selectedId = ng_conversion_radioGroup.getCheckedRadioButtonId();
        ng_conversion_radioButton = (RadioButton) findViewById(ng_conversion_selectedId);
        ng_conversion_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.ng_conversion_yes:
                        ng_conversion_Done_NotDone = "Yes";
                        break;
                    case R.id.ng_conversion_no:
                        ng_conversion_Done_NotDone = "Yes";
                        // Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        int tfAvail = tf_avail_radioGroup.getCheckedRadioButtonId();
        tf_avail_radioButton = findViewById(tfAvail);
        tf_avail_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.tf_avail_done:
                        tf_avail_Done = "Yes";
                        return;
                    case R.id.tf_avail_not_done:
                        tf_avail_Done = "No";
                        return;
                    default:
                        return;
                }
            }
        });
        connectivity_radioButton = findViewById(this.connectivity_radioGroup.getCheckedRadioButtonId());
        this.connectivity_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.connectivity_done:
                        connectivity_Done = "Yes";
                        return;
                    case R.id.connectivity_not_done:
                        connectivity_Done = "No";
                        return;
                    default:
                        return;
                }
            }
        });
        ncap_avail_radioButton = (RadioButton) findViewById(this.ncap_avail_radioGroup.getCheckedRadioButtonId());
        ncap_avail_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.ncap_avail_no) {
                    ncap_avail_Done = "Yes";
                } else if (i == R.id.ncap_avail_yes) {
                    ncap_avail_Done = "No";
                }
            }
        });


        spinner_meter_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_meter_type.getItemAtPosition(spinner_meter_type.getSelectedItemPosition()).toString();
                Log.e("meter_type_string+", country);

                meter_type_string = Meter_type_Id.get(position);
                // igl_code_group_Maaster=Igl_Code_Group_Master.get(position);
                Meter_No(meter_type_string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        manufacture_make_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = manufacture_make_spinner.getItemAtPosition(manufacture_make_spinner.getSelectedItemPosition()).toString();
                Log.e("manufacture_make", country);

                manufacture_make_string = Manufacture_Make_ID.get(position);
                // igl_code_group_Maaster=Igl_Code_Group_Master.get(position);
                // Meter_Type(manufacture_make_string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        meater_no_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = meater_no_spinner.getItemAtPosition(meater_no_spinner.getSelectedItemPosition()).toString();
                Log.e("meater_no_string+", country);

                meater_no_string = Meter_no.get(position);
                // igl_code_group_Maaster=Igl_Code_Group_Master.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void Layout_Id() {
        spinner_meter_type = findViewById(R.id.spinner_metar_length);
        manufacture_make_spinner = findViewById(R.id.manufacture_make_spinner);
        meater_no_spinner = (Spinner) findViewById(R.id.meater_no_spinner);
        back = findViewById(R.id.back);
        signature_image = findViewById(R.id.signature_image);
        select_image = findViewById(R.id.select_image);
        select_image1 = findViewById(R.id.select_image1);
        select_image2 = findViewById(R.id.select_image2);
        select_image3 = findViewById(R.id.select_image3);
        image_button = findViewById(R.id.image_button);
        image_button2 = findViewById(R.id.image_button2);
        image_button3 = findViewById(R.id.image_button3);
        image_button4 = findViewById(R.id.image_button4);
        signature_button = findViewById(R.id.signature_button);
        submit_button = findViewById(R.id.submit_button);
        offline_layout = findViewById(R.id.offline_layout);
        spinner_online_offline = findViewById(R.id.spinner_online_offline);
        signature_image = findViewById(R.id.signature_image);
        manufacture_editext = findViewById(R.id.manufacture_editext);
        type_nr_text = findViewById(R.id.type_nr_text);
        meater_no_text = findViewById(R.id.meater_no_text);
        initial_metar_reading_text = findViewById(R.id.initial_metar_reading_text);
        regulater_no_text = findViewById(R.id.regulater_no_text);
        gi_instalation_text = findViewById(R.id.gi_instalation_text);
        cu_instalation_text = findViewById(R.id.cu_instalation_text);
        vo_of_iv_text = findViewById(R.id.vo_of_iv_text);
        no_of_av_text = findViewById(R.id.no_of_av_text);
        pile_length_edit = findViewById(R.id.pile_length_edit);
        property_type_spinner = findViewById(R.id.property_type_spinner);
        gas_type_spinner = findViewById(R.id.gas_type_spinner);
        meter_text = findViewById(R.id.meter_text);
        // FrameLayout
        capture_fragment = findViewById(R.id.capture_fragment);
        capture_fragment1 = findViewById(R.id.capture_fragment1);
        capture_fragment2 = findViewById(R.id.capture_fragment2);
        capture_fragment3 = findViewById(R.id.capture_fragment3);

        /*TextView*/

        time_stemp_text = findViewById(R.id.time_stemp_text);
        time_stemp_text1 = findViewById(R.id.time_stemp_text1);
        time_stemp_text2 = findViewById(R.id.time_stemp_text2);
        time_stemp_text3 = findViewById(R.id.time_stemp_text3);

        pvc_sleeve_radioGroup = (RadioGroup) findViewById(R.id.pvc_sleeve_radioGroup);
        meater_intalation_radioGroup = (RadioGroup) findViewById(R.id.meater_intalation_radioGroup);
        clamping_gi_pvc_radioGroup = (RadioGroup) findViewById(R.id.clamping_gi_pvc_radioGroup);
        gas_meter_radioGroup = (RadioGroup) findViewById(R.id.gas_meter_radioGroup);
        cementing_of_holes_radioGroup = (RadioGroup) findViewById(R.id.cementing_of_holes_radioGroup);
        painting_of_gi_pipe_radioGroup = (RadioGroup) findViewById(R.id.painting_of_gi_pipe_radioGroup);
        hole_drilled_radioGroup = (RadioGroup) findViewById(R.id.hole_drilled_radioGroup);
        meter_control_valve_testing_radioGroup = (RadioGroup) findViewById(R.id.meter_control_valve_testing_radioGroup);
        natural_gas_usage_radioGroup = (RadioGroup) findViewById(R.id.natural_gas_usage_radioGroup);
        ng_conversion_radioGroup = (RadioGroup) findViewById(R.id.ng_conversion_radioGroup);
        tf_avail_radioGroup=findViewById(R.id.tf_avail_radioGroup);
        connectivity_radioGroup=findViewById(R.id.connectivity_radioGroup);
        ncap_avail_radioGroup=findViewById(R.id.ncap_avail_radioGroup);

        List<String> list = new ArrayList<String>();
        list.add("Online");
        list.add("Offline");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_online_offline.setAdapter(dataAdapter);

        List<String> property_type_list = new ArrayList<String>();
        property_type_list.add("High-Rise");
        property_type_list.add("Low Rise");
        ArrayAdapter<String> property_type_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, property_type_list);
        property_type_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        property_type_spinner.setAdapter(property_type_Adapter);

        List<String> gas_type_list = new ArrayList<String>();
        gas_type_list.add("O&M");
        gas_type_list.add("Projects");
        ArrayAdapter<String> gas_type_list_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gas_type_list);
        gas_type_list_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gas_type_spinner.setAdapter(gas_type_list_Adapter);

        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        Current_Time = currentTime;
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Current_Date = df.format(c);

        statusCheck();

        //RFC_Data();
        Manufacture_Make();
    }


    private void SelectImage_Method() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(RFC_Connection_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        //  intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });
        myAlertDialog.show();
    }

    private void SelectImage_Method2() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(RFC_Connection_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        // intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST2);
                    }
                });
        myAlertDialog.show();
    }

    private void SelectImage_Method3() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(RFC_Connection_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST3);
                    }
                });
        myAlertDialog.show();
    }

    private void SelectImage_Method4() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(RFC_Connection_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        // intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST4);
                    }
                });
        myAlertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        select_image.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path1++", file.toString());
                        image_path_string = file.toString();
                        // image_path_address1 =file.toString();
                        // TPI_Multipart(filePath_img_string);
                        time_stemp_text.setText(getIntent().getStringExtra("First_name") + " " + getIntent().getStringExtra("Last_name") + "|" + Latitude + "|" + Longitude + "|" + Current_Time + "|" + Current_Date + "|" + getIntent().getStringExtra("Bp_number"));
                        meter_text.setText(meter_no);
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
            case CAMERA_REQUEST2:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST2) {
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
                        select_image1.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path2++", file.toString());
                        image_path_string1 = file.toString();
                        // image_path_address1 =file.toString();
                        // TPI_Multipart(filePath_img_string);
                        time_stemp_text1.setText(getIntent().getStringExtra("First_name") + " " + getIntent().getStringExtra("Last_name") + "|" + Latitude + "|" + Longitude + "|" + Current_Time + "|" + Current_Date + "|" + getIntent().getStringExtra("Bp_number"));
                        meter_text.setText(meter_no);
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
            case CAMERA_REQUEST3:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST3) {
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
                        select_image2.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path3++", file.toString());
                        image_path_string2 = file.toString();
                        // image_path_address1 =file.toString();
                        // TPI_Multipart(filePath_img_string);
                        time_stemp_text2.setText(getIntent().getStringExtra("First_name") + " " + getIntent().getStringExtra("Last_name") + "|" + Latitude + "|" + Longitude + "|" + Current_Time + "|" + Current_Date + "|" + getIntent().getStringExtra("Bp_number"));
                        meter_text.setText(meter_no);
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
            case CAMERA_REQUEST4:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST4) {
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
                        select_image3.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path4++", file.toString());
                        image_path_string3 = file.toString();
                        // image_path_address1 =file.toString();
                        // TPI_Multipart(filePath_img_string);
                        time_stemp_text3.setText(getIntent().getStringExtra("First_name") + " " + getIntent().getStringExtra("Last_name") + "|" + Latitude + "|" + Longitude + "|" + Current_Time + "|" + Current_Date + "|" + getIntent().getStringExtra("Bp_number"));
                        meter_text.setText(meter_no);
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

            case PICK_CUSTOMER_IMAGE_SIGNATURE:
                if (requestCode == PICK_CUSTOMER_IMAGE_SIGNATURE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_customer = data.getData();
                    Log.e("filePath_customer", filePath_customer.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_customer);
                        // imageView.setImageBitmap(bitmap);
                        signature_image.setImageBitmap(bitmap);
                        adhar_owner_image.setImageBitmap(bitmap);
                        customer_image_select = getPath1(this, filePath_customer);
                        Log.e("owner_image_select+", "" + customer_image_select);
                        // new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    public void TPI_Multipart_Update() {
        if (tf_avail_Done.equalsIgnoreCase("No") || connectivity_Done.equalsIgnoreCase("No"))
        {
            igl_rfc_status = "113";
        }
        else {igl_rfc_status="3";}
        Log.d(LOG, "meter no++++ = " + "" + meter_no);
        try {
            materialDialog = new MaterialDialog.Builder(RFC_Connection_Activity.this)
                    .content("Please wait....")
                    .progress(true, 0)
                    .show();
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(RFC_Connection_Activity.this, uploadId, Constants.RFC_CONNECTION_POST + "/" + getIntent().getStringExtra("Bp_number"))
                    .addFileToUpload(ScreenShot_1, "image")
                    .addFileToUpload(ScreenShot_2, "image")
                    .addFileToUpload(ScreenShot_3, "image")
                    .addFileToUpload(ScreenShot_4, "image")
                    .addFileToUpload(customer_image_select, "sign_image")
                    .addParameter("RFCType", Online_Offline)
                    .addParameter("meter_make", manufacture_make_string)
                    .addParameter("meter_type", meter_type_string)
                    .addParameter("meter_no", meter_no)
                    .addParameter("initial_meter_reading", initial_metar_reading_text.getText().toString())
                    .addParameter("regulator_no", regulater_no_text.getText().toString())
                    .addParameter("gi_installation", gi_instalation_text.getText().toString())
                    .addParameter("cu_installation", cu_instalation_text.getText().toString())
                    .addParameter("no_of_iv", vo_of_iv_text.getText().toString())
                    .addParameter("no_of_av", no_of_av_text.getText().toString())
                    .addParameter("pvc_sleeve", pvc_sleeve_Avail_NotAvail)
                    .addParameter("meter_installation", meater_intalation_OK_NotOk)
                    .addParameter("clamming", clamping_gi_pvc_Done_NotDone)
                    .addParameter("gas_meter_testing", gas_meter_Done_NotDone)
                    .addParameter("cementing_of_holes", cementing_of_holes_Done_NotDone)
                    .addParameter("painting_of_giPipe", painting_of_gi_pipe_Done_NotDone)
                    .addParameter("hole_drilled", hole_drilled_Yes_No)
                    .addParameter("meter_control", meter_control_valve_testing_Yes_No)
                    .addParameter("customer1", natural_gas_usage_Yes_No)
                    .addParameter("customer2", ng_conversion_Done_NotDone)
                    .addParameter("leadNo", getIntent().getStringExtra("lead_no"))
                    .addParameter("extraMeterLength", pile_length_edit.getText().toString())
                    .addParameter("user_latitude", Latitude)
                    .addParameter("user_longitude", Longitude)
                    .addParameter("gasType", gas_type_string)
                    .addParameter("propertyType", property_type_string)
                    .addParameter("tgAvail", tf_avail_Done)
                    .addParameter("connectivity", connectivity_Done)
                    .addParameter("ncapAvail", ncap_avail_Done)
                    .addParameter("igl_rfc_status", igl_rfc_status)
                    .setDelegate(new UploadStatusDelegate() {
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
                            String Uplode = uploadInfo.getSuccessfullyUploadedFiles().toString();
                            String serverResponse1 = serverResponse.getHeaders().toString();
                            String str = serverResponse.getBodyAsString();
                            Log.e("UPLOADEsinin++", str);
                            final JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(str);
                                String Success = jsonObject.getString("Sucess");
                                if (Success.equals("true")) {
                                    finish();
                                    Toast.makeText(RFC_Connection_Activity.this, "" + "Succesfully RFC Done", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RFC_Connection_Activity.this, "" + "UnSuccesfully RFC Done", Toast.LENGTH_SHORT).show();
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
            Log.e("Multiple_Image_Uplode++", Multiple_Image_Uplode);
        } catch (Exception exc) {
            //Toast.makeText(RFC_Connection_Activity.this, "Please select Image", Toast.LENGTH_SHORT).show();
            materialDialog.dismiss();
        }
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
                bitmap = signatureView.getSignatureBitmap();
                signature = saveImage(bitmap);
                signature_image.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
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

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    @SuppressLint("NewApi")
    public static String getPath1(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void RFC_Data() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.RFCDetails + "/" + getIntent().getStringExtra("Bp_number"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response++", jsonObject.toString());
                            Status_Type = jsonObject.getString("Sucess");
                            Log.d(LOG, "Status_Type = " + Status_Type);

                            final JSONObject Bp_Details = jsonObject.getJSONObject("RFCdetails");
                            JSONArray payload = Bp_Details.getJSONArray("rfc");
                            for (int i = 0; i <= payload.length(); i++) {
                                JSONObject data_object = payload.getJSONObject(i);
                                String meter_make = data_object.getString("meter_make");
                                meter_type = data_object.getString("meter_type");
                                meter_no = data_object.getString("meter_no");
                                String initial_meter_reading = data_object.getString("initial_meter_reading");
                                String regulator_no = data_object.getString("regulator_no");
                                String gi_installation = data_object.getString("gi_installation");
                                String cu_installation = data_object.getString("cu_installation");
                                String no_of_iv = data_object.getString("no_of_iv");
                                String no_of_av = data_object.getString("no_of_av");
                                String meter_installation = data_object.getString("meter_installation");
                                String pvc_sleeve = data_object.getString("pvc_sleeve");
                                String clamming = data_object.getString("clamming");
                                String gas_meter_testing = data_object.getString("gas_meter_testing");
                                String cementing_of_holes = data_object.getString("cementing_of_holes");
                                String painting_of_giPipe = data_object.getString("painting_of_giPipe");
                                String customer1 = data_object.getString("customer1");
                                String customer2 = data_object.getString("customer2");
                                String bp = data_object.getString("bp");
                                String status = data_object.getString("status");
                                String tpiName = data_object.getString("tpiName");
                                String tpiLastName = data_object.getString("tpiLastName");
                                String vendorName = data_object.getString("vendorName");
                                String address = data_object.getString("address");
                                String firstName = data_object.getString("firstName");
                                String lastName = data_object.getString("lastName");
                                String mobileNo = data_object.getString("mobileNo");
                                String caNo = data_object.getString("caNo");
                                String rfctype = data_object.getString("rfctype");

                                /*name_of_contractor.setText(vendorName);
                                name_of_consumer.setText(firstName+" "+lastName);
                                address_txt.setText(address);
                                contect_no_txt.setText(mobileNo);*/
                                manufacture_editext.setText(meter_installation);
                                type_nr_text.setText(meter_type);
                                meater_no_text.setText(meter_no);
                                initial_metar_reading_text.setText(initial_meter_reading);
                                regulater_no_text.setText(regulator_no);
                                gi_instalation_text.setText(gi_installation);
                                cu_instalation_text.setText(cu_installation);
                                vo_of_iv_text.setText(no_of_iv);
                                no_of_av_text.setText(no_of_av);
                                meter_text.setText(meter_no);
                                //pvc_sleeve_radioButton.set
                                if (pvc_sleeve.equals("Avail")) {
                                    pvc_sleeve_radioButton.setSelected(true);
                                } else {
                                    pvc_sleeve_radioButton.setSelected(false);
                                }
                                if (clamming.equals("Done")) {
                                    clamping_gi_pvc_radioButton.setSelected(true);
                                } else {
                                    clamping_gi_pvc_radioButton.setSelected(false);
                                }
                                /*pvc_sleeve_text.setText(pvc_sleeve);
                                clamping_gi_pvc_text.setText(clamming);
                                meater_intalation_text.setText(meter_installation);
                                gas_meter_testing_text.setText(gas_meter_testing);
                                cementing_of_holes_text.setText(cementing_of_holes);
                                painting_of_gi_pipe_text.setText(painting_of_giPipe);
                                name_of_contractor_respective_text.setText(vendorName);
                                name_of_tpi.setText(tpiName+" "+tpiLastName);
                                bp_no_text.setText(bp);*/

                            }
                            if (Status_Type.equals("true")) {

                            } else {
                                if (meter_type != null) {
                                    int spinnerPosition = metar_type_adapter.getPosition(meter_type);
                                    spinner_meter_type.setSelection(spinnerPosition);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }) {


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void Manufacture_Make() {
        Manufacture_Make.clear();
        Manufacture_Make_ID.clear();
        materialDialog = new MaterialDialog.Builder(RFC_Connection_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Manufacture_Make, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(LOG, "master_list = " + response.toString());
                    // if(jsonObject.getInt("success")==1){
                    //JSONObject jsonObject_pipeline=jsonObject.getJSONObject("meter_details");
                    JSONArray jsonArray_manufactureDetails = jsonObject.getJSONArray("manufactureDetails");
                    for (int i = 0; i < jsonArray_manufactureDetails.length(); i++) {
                        JSONObject jsonObject_manufactureDetails = jsonArray_manufactureDetails.getJSONObject(i);
                        String meter_description = jsonObject_manufactureDetails.getString("manufactureMake");
                        String meterType = jsonObject_manufactureDetails.getString("manufactureId");
                        Manufacture_Make.add(meter_description);
                        Manufacture_Make_ID.add(meterType);
                    }
                    manufacture_make_adapter = new ArrayAdapter<String>(RFC_Connection_Activity.this, android.R.layout.simple_spinner_dropdown_item, Manufacture_Make);
                    manufacture_make_spinner.setAdapter(manufacture_make_adapter);
                    // spinner_meter_type.setAdapter(new ArrayAdapter<String>(RFC_Connection_Activity.this, android.R.layout.simple_spinner_dropdown_item, Meter_type));
                    //  RFC_Data();
                    Meter_Type();
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

    private void Meter_Type() {
        Meter_type.clear();
        Meter_type_Id.clear();
        materialDialog = new MaterialDialog.Builder(RFC_Connection_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.MeterType, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(LOG, "master_list = " + response);
                    // if(jsonObject.getInt("success")==1){
                    JSONObject jsonObject_pipeline = jsonObject.getJSONObject("meter_details");
                    JSONArray jsonArray_society = jsonObject_pipeline.getJSONArray("mt");
                    for (int i = 0; i < jsonArray_society.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_society.getJSONObject(i);
                        String meter_description = jsonObject1.getString("meter_description");
                        String meterType = jsonObject1.getString("meterType");
                        Meter_type.add(meter_description);
                        Meter_type_Id.add(meterType);
                    }
                    metar_type_adapter = new ArrayAdapter<String>(RFC_Connection_Activity.this, android.R.layout.simple_spinner_dropdown_item, Meter_type);
                    spinner_meter_type.setAdapter(metar_type_adapter);

                    //   Meter_No(  meter_type_string);
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


    private void Meter_No(final String meter_type_string) {
        Meter_no.clear();
        Meter_no_type.clear();
        Meter_no_type.add("---Select Meter---");
        Meter_no.add("Select Meter no");
        Log.d(LOG, "" + Meter_no.size());
        materialDialog = new MaterialDialog.Builder(RFC_Connection_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constants.MeterNo + "?meterType=" + meter_type_string + "&plantCode=3030" + "&id=" + rfcAdmin;
        Log.d(LOG, url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(LOG, "master_list = " + response);
                    if (jsonObject.getString("Sucess").equals("true")) {
                        JSONArray jsonArray_society = jsonObject.getJSONArray("meterNoListing");
                        for (int i = 0; i < jsonArray_society.length(); i++) {

                            JSONObject jsonObject1 = jsonArray_society.getJSONObject(i);
                            String meter_description = jsonObject1.getString("meterType");
                            String meterNo = jsonObject1.getString("meterNo");
                            Meter_no.add(meterNo);
                            Meter_no_type.add(meter_description);
                            //Meter_No_Id.add(meterType);
                        }
                       /*  meter_text.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Dilogbox_NG_Connetion();
                                 Log.d(LOG,"meter no. click");

                             }
                         });*/
                    } else {
                         /*meter_text.setText("");
                         meter_text.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Log.d(LOG,"meter no. click in else");
                             }
                         });*/
                        Meter_no_type.add("No Data");
                        Meter_no.add("Data not available");
                    }
                    meater_no_adapter = new ArrayAdapter<String>(RFC_Connection_Activity.this, android.R.layout.simple_spinner_dropdown_item, Meter_no);
                    meater_no_spinner.setAdapter(meater_no_adapter);

                    RFC_Data();
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

    private void getLocationUsingInternet() {
        boolean isInternetConnected = new ConnectionDetector(RFC_Connection_Activity.this).isConnectingToInternet();
        if (isInternetConnected) {
            // getLocation_usingInternet.setEnabled(false);
            new GPSLocation(RFC_Connection_Activity.this).turnGPSOn();// First turn on GPS
            String getLocation = new GPSLocation(RFC_Connection_Activity.this).getMyCurrentLocation();// Get current location from
            Log.e("getLocation++", getLocation.toString());

            // Toast.makeText(getApplicationContext(), getLocation.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RFC_Connection_Activity.this, "There is no internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    Location location1;

    @SuppressLint("MissingPermission")
    private void getLocationWithoutInternet() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {

                Latitude = String.valueOf(location.getLatitude());
                Longitude = String.valueOf(location.getLongitude());
                //  Toast.makeText(getApplicationContext(), location.getLatitude() + "     " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                // getLocation.setEnabled(true);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(RFC_Connection_Activity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            if (locationManager != null) {

                location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location1 != null) {
                    /// Toast.makeText(getApplicationContext(), location1.getLatitude() + "     " + location1.getLongitude(), Toast.LENGTH_SHORT).show();
                    Latitude = String.valueOf(location1.getLatitude());
                    Longitude = String.valueOf(location1.getLongitude());

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(RFC_Connection_Activity.this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        new GPSLocation(RFC_Connection_Activity.this).turnGPSOff();
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


    private void takeScreenshot() {

        try {
            bitmap_screenshot = ScreenshotUtils.getScreenShot(capture_fragment);
            bitmap_screenshot1 = ScreenshotUtils.getScreenShot1(capture_fragment1);
            bitmap_screenshot2 = ScreenshotUtils.getScreenShot2(capture_fragment2);
            bitmap_screenshot3 = ScreenshotUtils.getScreenShot3(capture_fragment3);
            if (bitmap_screenshot != null) {
                //showScreenShotImage(bitmap_screenshot);

                File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                File saveFile1 = ScreenshotUtils.getMainDirectoryName1(this);
                File saveFile2 = ScreenshotUtils.getMainDirectoryName2(this);
                File saveFile3 = ScreenshotUtils.getMainDirectoryName3(this);
                screenshot_file = ScreenshotUtils.store(bitmap_screenshot, "screenshot" + "Custom" + ".jpg", saveFile);
                screenshot_file1 = ScreenshotUtils.store1(bitmap_screenshot1, "screenshot" + "Custom1" + ".jpg", saveFile1);
                screenshot_file2 = ScreenshotUtils.store2(bitmap_screenshot2, "screenshot" + "Custom2" + ".jpg", saveFile2);
                screenshot_file3 = ScreenshotUtils.store3(bitmap_screenshot3, "screenshot" + "Custom3" + ".jpg", saveFile3);

                ScreenShot_1 = screenshot_file.toString();
                ScreenShot_2 = screenshot_file1.toString();
                ScreenShot_3 = screenshot_file2.toString();
                ScreenShot_4 = screenshot_file3.toString();
                Log.e("ScreenShot_1", ScreenShot_1);
                Log.e("ScreenShot_2", ScreenShot_2);
                Log.e("ScreenShot_3", ScreenShot_3);
                Log.e("ScreenShot_4", ScreenShot_4);
                // uploadMultipart();

                Log.d(LOG, "Status_Type = " + Status_Type);

                TPI_Multipart_Update();

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
        }


    }

    /*  Show screenshot Bitmap */
    private void showScreenShotImage(Bitmap b) {
        // face_image.setImageBitmap(b);
        //face_image_female.setImageBitmap(b);
    }

    @Override
    public void onContactSelected(Bp_No_Item contact) {

    }

   /* private void Dilogbox_NG_Connetion() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dropdown_recyclerview);
        dialog.setCancelable(true);
        ImageView crose_img=dialog.findViewById(R.id.crose_img);
        recyclerView = (RecyclerView)dialog.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                meater_no_set_text = Meter_No.get(position).getMeterNo();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        meter_text.setText(meater_no_set_text);
                        meter_no=meter_text.getText().toString();
                        dialog.dismiss();
                    }
                }, 500);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));
        crose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        editTextSearch=(EditText)dialog.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
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
        dropDown_adapter = new DropDown_Adapter(RFC_Connection_Activity.this,Meter_No,RFC_Connection_Activity.this);
        recyclerView.setAdapter( dropDown_adapter);
        dropDown_adapter.notifyDataSetChanged();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
        dialog.show();
    }*/


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
                bitmap = signatureView.getSignatureBitmap();
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

}