package com.example.igl.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import com.example.igl.Helper.AppController;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.R;
import com.iceteck.silicompressorr.SiliCompressor;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.igl.Activity.Selfie_Activity.MY_PERMISSIONS_REQUEST_CAMERA;

public class New_Regestration_Form extends Activity implements AdapterView.OnItemSelectedListener{

    MaterialDialog materialDialog;
    ImageView adhar_image,address_image,signature_image;
    Button signature_button,adhar_button,address_button,viewpdf_button;
    EditText fullname,middle_name,lastname,mobile_no,email_id,aadhaar_no,city_reasion,area,society,landmark,house_type,house_no,block_tower,
            floor,street_road,pincode,lpg_company,lpg_distributer,lpg_consumer,unique_id_no,ownar_name_no,chequeno_edit,chequedate_edit,drawnon_edit,amount_edit,search_team,language_edit;
    Button submit_button;
    SharedPrefs sharedPrefs;
    RadioGroup radioGroup;
    RadioButton genderradioButton;
    Bitmap bitmap,bitmap1;
    Button clear, save;
    SignatureView signatureView,ownar_signature_view;
    String Customer_Signature_path ,signature_path,ownar_name,customer_image_select;
    private final int PICK_IMAGE_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST_ADDRESS = 3;
    private final int PICK_OWNER_IMAGE_REQUEST_ADDRESS = 4;
    private final int PICK_CUSTOMER_IMAGE_SIGNATURE = 5;
    protected static final int CAMERA_REQUEST = 200;
    protected static final int CAMERA_REQUEST_ADDRESS = 201;
    protected static final int CAMERA_OWNER_REQUEST_ADDRESS = 202;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    Spinner spinner1,spinner2;
    private Uri filePath_aadhaar,filePath_address,filePath_owner,filePath_customer;
    String image_path_aadhar,image_path_address,owner_image_select;
    String pdf_file_path;
    ImageView back;
    Spinner spinner_city;
    ArrayList<String> CityName;
    ArrayList<String> CityId;
    ArrayList<String> CityName1;
    ArrayList<String> CityId1;
    ArrayList<String> Area;
    ArrayList<String> Area1;
    ArrayList<String> Area_ID;
    ArrayList<String> Area_ID1;
    ArrayList<String>Society;
    ArrayList<String>Society1;
    ArrayList<String>Landmark;
    ArrayList<String> Customer_Type;
    ArrayList<String> Customer_Type1;
    ArrayList<String> House_type_name;
    ArrayList<String> House_type_name1;
    ArrayList<String> Floor_name;
    ArrayList<String> Floor_name1;
    ArrayList<String> Block_tower_name;
    ArrayList<String> Block_tower_name1;
    ArrayList<String> Street_road_name;
    ArrayList<String> Street_road_name1;
    ArrayList<String> Lpg_company_name;
    ArrayList<String> Street_road_type_name;
    ArrayList<String> Block_tower_type_name;
    String id_proof,address_proof,Type_Of_Owner;
    String city_id,city_name,customer_type,area_name,area_Id,soceity_name,house_type_name,floor_name,block_tower_name,street_road_name,lpg_company_name,street_road_type_name,block_tower_type_name;
    Spinner spinner_customertype,spinner_area,spinner_society,spinner_house_type,spinner_floor,spinner_block_tower,spinner_street_road,spinner_lpg_company;
    String Compress_Address_Image,Compress_Adahar_Image;
    Spinner spinner_street_road_type,spinner_block_tower_type;
    Uri compressUri = null;
    String Bp_Number;
    ImageView adhar_owner_image;
    CheckBox checkBox_term_cond;
    TextView checkbox_text;
    DatePickerDialog pickerDialog_Date;
    String date_select;
    String emailPattern = "@[A-Z][a-z]+\\.+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_regestration_form);
        sharedPrefs=new SharedPrefs(this);
        CityName=new ArrayList<>();
        CityId=new ArrayList<>();
        CityName1=new ArrayList<>();
        CityId1=new ArrayList<>();
        Area=new ArrayList<>();
        Area_ID=new ArrayList<>();
        Area1=new ArrayList<>();
        Area_ID1=new ArrayList<>();
        Society=new ArrayList<>();
        Society1=new ArrayList<>();
        Landmark=new ArrayList<>();
        Customer_Type=new ArrayList<>();
        Customer_Type1=new ArrayList<>();
        House_type_name=new ArrayList<>();
        House_type_name1=new ArrayList<>();
        Floor_name=new ArrayList<>();
        Floor_name1=new ArrayList<>();
        Block_tower_name=new ArrayList<>();
        Block_tower_name1=new ArrayList<>();
        Street_road_name=new ArrayList<>();
        Street_road_name1=new ArrayList<>();
        Lpg_company_name=new ArrayList<>();
        Street_road_type_name=new ArrayList<>();
        Block_tower_type_name=new ArrayList<>();

        fullname=findViewById(R.id.fullname);
        middle_name=findViewById(R.id.middle_name);
        lastname=findViewById(R.id.lastname);
        mobile_no=findViewById(R.id.mobile_no);
        email_id=findViewById(R.id.email_id);
        aadhaar_no=findViewById(R.id.aadhaar_no);
        city_reasion=findViewById(R.id.city_reasion);
        area=findViewById(R.id.area);
        society=findViewById(R.id.society);
        landmark=findViewById(R.id.landmark);
        house_type=findViewById(R.id.house_type);
        house_no=findViewById(R.id.house_no);
        block_tower=findViewById(R.id.block_tower);
        floor=findViewById(R.id.floor);
        street_road=findViewById(R.id.street_road);
        pincode=findViewById(R.id.pincode);
        lpg_company=findViewById(R.id.lpg_company);
        lpg_distributer=findViewById(R.id.lpg_distributer_edit);
        lpg_consumer=findViewById(R.id.lpg_consumer_edit);
        unique_id_no=findViewById(R.id.unique_id_no_edit);
        chequeno_edit=findViewById(R.id.chequeno_edit);
        chequedate_edit=findViewById(R.id.chequedate_edit);
        drawnon_edit=findViewById(R.id.drawnon_edit);
        amount_edit=findViewById(R.id.amount_edit);
        search_team=findViewById(R.id.search_team);
        language_edit=findViewById(R.id.language_edit);
        submit_button=findViewById(R.id.submit_button);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        adhar_image=findViewById(R.id.adhar_image);
        address_image=findViewById(R.id.address_image);
        signature_image=findViewById(R.id.signature_image);
        signature_button=findViewById(R.id.signature_button);
        adhar_button=findViewById(R.id.adhar_button);
        address_button=findViewById(R.id.address_button);
        viewpdf_button=findViewById(R.id.viewpdf_button);

        spinner_city=(Spinner)findViewById(R.id.spinner_city);
        spinner_area=(Spinner)findViewById(R.id.spinner_area);
        spinner_society=(Spinner)findViewById(R.id.spinner_society);
        spinner_house_type=(Spinner)findViewById(R.id.spinner_house_type);
        spinner_floor=(Spinner)findViewById(R.id.spinner_floor);
        spinner_block_tower=(Spinner)findViewById(R.id.spinner_block_tower);
        spinner_street_road=(Spinner)findViewById(R.id.spinner_street_road);

        spinner_block_tower_type=(Spinner)findViewById(R.id.spinner_block_tower_type);
        spinner_street_road_type=(Spinner)findViewById(R.id.spinner_street_road_type);
        spinner_customertype=(Spinner)findViewById(R.id.spinner_customertype);
        spinner_lpg_company=(Spinner)findViewById(R.id.spinner_lpg_company);
        back=findViewById(R.id.back);
        checkBox_term_cond=findViewById(R.id.checkbox);
        checkbox_text=findViewById(R.id.checkbox_text);
       Click_Event();
    }

    private void Click_Event() {
        Type_Of_Owner="Owner";
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        signature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Signature_Method();
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

        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if(permissionGranted) {

            try {
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_ADDRESS);
            try {
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
            }
        }
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton)findViewById(selectedId);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.owner:
                        //Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        Type_Of_Owner="Owner";
                        break;
                    case R.id.rents:

                        Type_Of_Owner="Rented";
                        Ownar_Signature();
                        break;
                }
            }
        });
        List<String> list = new ArrayList<String>();
        list.add("ELECTRICITY BILL");
        list.add("WATER BILL");
        list.add("SALE DEEP");
        list.add("HOUSE TAX RECEIPT");
        list.add("ALLOTMENT LETTER");
        list.add("ANY OTHER");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        List<String> list1 = new ArrayList<String>();
        list1.add("AADHAAR CARD");
        list1.add("DRIVING LICENCE");
        list1.add("ANY OTHER");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                 id_proof=  spinner1.getItemAtPosition(spinner1.getSelectedItemPosition()).toString();
                if(id_proof.equals("ANY OTHER")){
                    Edit_text();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                 address_proof=  spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString();
                if(address_proof.equals("ANY OTHER")){
                    Edit_text();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        loadSpinnerData();
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_city.getItemAtPosition(spinner_city.getSelectedItemPosition()).toString();
                Log.e("CITY+",country);
                city_id=CityId1.get(position);
                city_name=CityName1.get(position);
                Log.e("city_id+",city_id);
                Log.e("city_name+",city_name);
                loadSpinner_reasion_city(city_id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_area.getItemAtPosition(spinner_area.getSelectedItemPosition()).toString();
                Log.e("area_name+",country);
                area_name=Area1.get(position);
                area_Id=Area_ID1.get(position);
                Log.e("area_Id+",area_Id);
                Log.e("area_name+",area_name);
                loadSpinnerSocity(area_Id);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_society.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_society.getItemAtPosition(spinner_society.getSelectedItemPosition()).toString();
                Log.e("Society+",country);
                soceity_name=Society1.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_house_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_house_type.getItemAtPosition(spinner_house_type.getSelectedItemPosition()).toString();
                Log.e("house_type_name+",country);
                house_type_name=House_type_name1.get(position);
                //city_name=CityName.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_floor.getItemAtPosition(spinner_floor.getSelectedItemPosition()).toString();
                Log.e("floor_name+",country);
                floor_name=Floor_name1.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_block_tower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_block_tower.getItemAtPosition(spinner_block_tower.getSelectedItemPosition()).toString();
                Log.e("block_tower_name+",country);
                block_tower_name=Block_tower_name1.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_street_road.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_street_road.getItemAtPosition(spinner_street_road.getSelectedItemPosition()).toString();
                Log.e("street_road_name+",country);
                street_road_name=Street_road_name1.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_customertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String customer_type11=  spinner_customertype.getItemAtPosition(spinner_customertype.getSelectedItemPosition()).toString();
                Log.e("customer_type+",customer_type11);
                customer_type=Customer_Type1.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_lpg_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String customer_type11=  spinner_lpg_company.getItemAtPosition(spinner_lpg_company.getSelectedItemPosition()).toString();
                Log.e("lpg_company_name+",customer_type11);
                lpg_company_name=Lpg_company_name.get(position);
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
                String customer_type11=  spinner_block_tower_type.getItemAtPosition(spinner_block_tower_type.getSelectedItemPosition()).toString();
                Log.e("lpg_company_name+",customer_type11);
                block_tower_type_name=Block_tower_type_name.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_street_road_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String customer_type11=  spinner_street_road_type.getItemAtPosition(spinner_street_road_type.getSelectedItemPosition()).toString();
                Log.e("lpg_company_name+",customer_type11);
                street_road_type_name=Street_road_type_name.get(position);
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

        viewpdf_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                checkPermission();
            } else {
                requestPermission();
            }
        } else {
        }
        String first = "I agree to the ";
        String second = "<font color='#EE0000'> Terms and conditions</font>";
        String third = " of PNG registration.";
        checkbox_text.setText(Html.fromHtml(first + second+third));
        checkbox_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent =new Intent(New_Regestration_Form.this,WebView_Activity.class);
             startActivity(intent);

            }
        });
        checkBox_term_cond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checkBox_term_cond.setChecked(true);
                    Toast.makeText(New_Regestration_Form.this, "You checked the checkbox!", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkBox_term_cond.setChecked(false);
                    Toast.makeText(New_Regestration_Form.this, "You unchecked the checkbox!", Toast.LENGTH_SHORT).show();
                }
            }
        });
            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox_term_cond.isChecked()) {
                        if (validateData()) {
                            New_Regestration_Form();
                            //uploadMultipart();
                        }

                    } else {
                        Toast.makeText(New_Regestration_Form.this, "Please tick to accept Tearm & Condition", Toast.LENGTH_SHORT).show();
                    }
                }
            });

       /* Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date_select = df.format(c);
        start_date_text.setText(date_select);*/
        chequedate_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                pickerDialog_Date = new DatePickerDialog(New_Regestration_Form.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;

                                if(month < 10){

                                    formattedMonth = "0" + month;
                                }
                                if(dayOfMonth < 10){

                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                Log.e("Date",year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);

                                chequedate_edit.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                pickerDialog_Date.show();
            }
        });
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
                        Uri photoURI = FileProvider.getUriForFile(New_Regestration_Form.this, getApplicationContext().getPackageName() + ".provider", f);
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
                        Uri photoURI = FileProvider.getUriForFile(New_Regestration_Form.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_ADDRESS);
                    }
                });
        myAlertDialog.show();
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
        myAlertDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }
            break;
            case PICK_IMAGE_REQUEST:
                if (requestCode == CAMERA_REQUEST_ADDRESS) {

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
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            Log.e("uri++",uri.toString());
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(New_Regestration_Form.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if ( requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_aadhaar = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_aadhaar);
                        // imageView.setImageBitmap(bitmap);
                        adhar_image.setImageBitmap(bitmap);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_aadhar = getPath(filePath_aadhaar);

                      //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
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
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        adhar_image.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath() ;
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++",file.toString());
                        image_path_aadhar =file.toString();
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
                case PICK_IMAGE_REQUEST_ADDRESS:
                if ( requestCode == PICK_IMAGE_REQUEST_ADDRESS && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_address = data.getData();
                    Log.e("Camera_Pathaddress++",filePath_address.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_address);
                        // imageView.setImageBitmap(bitmap);
                        address_image.setImageBitmap(bitmap);
                        image_path_address = getPath1(filePath_address);
                        Log.e("image_path_address+", "" + image_path_address);
                       // new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
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
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        address_image.setImageBitmap(bitmap);
                        //BitMapToString(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath() ;
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++",file.toString());
                        image_path_address =file.toString();
                     //   new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");

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
                if ( requestCode == PICK_OWNER_IMAGE_REQUEST_ADDRESS && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_owner = data.getData();
                    Log.e("Camera_Pathaddress++",filePath_owner.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_owner);
                        // imageView.setImageBitmap(bitmap);
                        adhar_owner_image.setImageBitmap(bitmap);
                        owner_image_select = getPath1(filePath_owner);
                        Log.e("owner_image_select+", "" + owner_image_select);
                        // new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_CUSTOMER_IMAGE_SIGNATURE:
                if ( requestCode == PICK_CUSTOMER_IMAGE_SIGNATURE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_customer = data.getData();
                    Log.e("filePath_customer",filePath_customer.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_customer);
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
    private void Signature_Method() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
        ImageView crose_img=dialog.findViewById(R.id.crose_img);
        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);
        crose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                Customer_Signature_path = saveImage(bitmap);
                signature_image.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void Ownar_Signature() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.customer_signature);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
         adhar_owner_image=dialog.findViewById(R.id.adhar_owner_image);
        Button adhar_button=dialog.findViewById(R.id.adhar_button);
        TextView signature_select=dialog.findViewById(R.id.signature_select);
        TextView image_select=dialog.findViewById(R.id.image_select);
        TextView save_select=dialog.findViewById(R.id.save_select);
        final LinearLayout signature_layout=dialog.findViewById(R.id.signature_layout);
        final LinearLayout image_capture_layout=dialog.findViewById(R.id.image_capture_layout);
        ImageView crose_img=dialog.findViewById(R.id.crose_img);
        ownar_name_no =dialog.findViewById(R.id.ownar_name_no);
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
                bitmap1 = ownar_signature_view.getSignatureBitmap();
                signature_path = saveImage1(bitmap1);
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
    private void Customer_Signature1() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.customer_signature);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
        TextView owner_name=dialog.findViewById(R.id.owner_name);
        owner_name.setVisibility(View.GONE);
        adhar_owner_image=dialog.findViewById(R.id.adhar_owner_image);
        Button adhar_button=dialog.findViewById(R.id.adhar_button);
        TextView signature_select=dialog.findViewById(R.id.signature_select);
        TextView image_select=dialog.findViewById(R.id.image_select);
        TextView save_select=dialog.findViewById(R.id.save_select);
        final LinearLayout signature_layout=dialog.findViewById(R.id.signature_layout);
        final LinearLayout image_capture_layout=dialog.findViewById(R.id.image_capture_layout);
        ImageView crose_img=dialog.findViewById(R.id.crose_img);
        ownar_name_no =dialog.findViewById(R.id.ownar_name_no);
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

    public String saveImage1(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("Signature_Page++", wallpaperDirectory.toString());
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
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
        }catch (CursorIndexOutOfBoundsException e){
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
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return path;
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(New_Regestration_Form.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(New_Regestration_Form.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(New_Regestration_Form.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(New_Regestration_Form.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST);
        }
    }
    public void uploadMultipart(String Bp_Number) {
        if(signature_path==null){
            signature_path=customer_image_select;
            Log.e("signature_path",signature_path);
        }
        try {
            materialDialog = new MaterialDialog.Builder(New_Regestration_Form.this)
                    .content("Please wait....")
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            String uploadId = UUID.randomUUID().toString();
            Log.e("uploadId+,,,,,,,,,,", "" + uploadId);
            MultipartUploadRequest multipartUploadRequest=   new MultipartUploadRequest(New_Regestration_Form.this, uploadId, Constants.BP_Images+"/"+Bp_Number);
            multipartUploadRequest.addFileToUpload(image_path_aadhar, "image");
            multipartUploadRequest .addFileToUpload(image_path_address, "image");
            multipartUploadRequest .addFileToUpload(customer_image_select, "image");
            multipartUploadRequest .addFileToUpload(signature_path, "image");
            multipartUploadRequest .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context,UploadInfo uploadInfo) {
                            Log.e("UplodeINFO++",uploadInfo.getSuccessfullyUploadedFiles().toString());
                        }
                        @Override
                        public void onError(Context context, UploadInfo uploadInfo,  Exception exception) {
                            exception.printStackTrace();
                            materialDialog.dismiss();
                            Log.e("Uplodeerror++",uploadInfo.getSuccessfullyUploadedFiles().toString());
                        }
                        @Override
                        public void onCompleted(Context context,UploadInfo uploadInfo, ServerResponse serverResponse) {
                            materialDialog.dismiss();
                            String str=serverResponse.getBodyAsString();
                            final JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(str);
                                Log.e("Response++",jsonObject.toString());
                                String Msg=jsonObject.getString("Message");
                               // Toast.makeText(New_Regestration_Form.this, Msg, Toast.LENGTH_SHORT).show();
                               // finish();
                                BP_N0_DilogBox();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onCancelled(Context context,UploadInfo uploadInfo) {
                            materialDialog.dismiss();
                        }
                    });
            multipartUploadRequest .setUtf8Charset();
            multipartUploadRequest .setAutoDeleteFilesAfterSuccessfulUpload(true);
            multipartUploadRequest .setMaxRetries(5);
            multipartUploadRequest .setUsesFixedLengthStreamingMode(true);
            multipartUploadRequest .setUsesFixedLengthStreamingMode(true);
            multipartUploadRequest .startUpload(); //Starting the upload

            Log.e("aadhaar_file",  image_path_aadhar);
            Log.e("pan_file",  image_path_address);
            Log.e("sign_file",  customer_image_select);
            Log.e("ownerSign",  signature_path);
            Log.e("Middle_Name",  middle_name.getText().toString());
            Log.e("Last_Name",  lastname.getText().toString());
            Log.e("Mobile_Number",  mobile_no.getText().toString());
            Log.e("Email_ID",  email_id.getText().toString());
            Log.e("Aadhaar_Number",  aadhaar_no.getText().toString());
            Log.e("City_Region",  city_name);
            Log.e("Area",  area_name);
            Log.e("Society",  soceity_name);
            Log.e("Landmark",  landmark.getText().toString());
            Log.e("House_Type",  house_type_name);
            Log.e("HouseNo",  house_no.getText().toString());
            Log.e("Block_Qtr_Tower_Wing",  block_tower_name);
            Log.e("Floor",  floor_name);
            Log.e("Street_Gali_Road",  street_road_name);
            Log.e("Pin_Code",  pincode.getText().toString());
            Log.e("LPG_Company",  lpg_company_name);
            Log.e("Customer_Type",  customer_type);
            Log.e("LPG_DISTRIBUTOR",  lpg_distributer.getText().toString());
            Log.e("LPG_CONSUMER_NO",  lpg_consumer.getText().toString());
            Log.e("UNIQUE_LPG_ID",   unique_id_no.getText().toString());

        } catch (Exception exc) {
           // Toast.makeText(New_Regestration_Form.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(New_Regestration_Form.this, "Please Select ID & Address Proof and Proper Signature", Toast.LENGTH_SHORT).show();
            materialDialog.dismiss();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), "Selected User: "+i ,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        CityId1.clear();
        CityName1.clear();
        materialDialog = new MaterialDialog.Builder(New_Regestration_Form.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.City_List, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try{
                    //JSONObject jsonObject=new JSONObject(response);
                   // if(jsonObject.getInt("success")==1){
                        JSONArray jsonArray=new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String city=jsonObject1.getString("user_city");
                            city_id=jsonObject1.getString("user_id");
                            CityName.add(city);
                            CityId.add(city_id);
                            //city_id=CityId.get(0).toString();
                            //loadSpinner_reasion_city(city_id);
                      //  }
                    }
                    String city_select1="Select City";
                    String city_id1="1";
                    CityId1.add(city_id1);
                    CityId1.addAll(CityId);
                    CityName1.add(city_select1);
                    CityName1.addAll(CityName);

                    spinner_city.setAdapter(new ArrayAdapter<String>(New_Regestration_Form.this, android.R.layout.simple_spinner_dropdown_item, CityName1));
                }catch (JSONException e){e.printStackTrace();}
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
        materialDialog = new MaterialDialog.Builder(New_Regestration_Form.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.Arealist_reason_Socity+"/"+city_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray_area=jsonObject.getJSONArray("area");
                    for(int i=0;i<jsonArray_area.length();i++){
                        JSONObject jsonObject1=jsonArray_area.getJSONObject(i);
                        String area_select=jsonObject1.getString("area");
                        String area_id=jsonObject1.getString("area_id");
                        Area_ID.add(area_id);
                        Area.add(area_select);
                    }
                    String area_select1="Select Area";
                    String area_id1="1";
                    Area_ID1.add(area_id1);
                    Area_ID1.addAll(Area_ID);
                    Area1.add(area_select1);
                    Area1.addAll(Area);
                    JSONArray jsonArray_block_no=jsonObject.getJSONArray("block_no");
                    for(int i=0;i<jsonArray_block_no.length();i++){
                        JSONObject jsonObject1=jsonArray_block_no.getJSONObject(i);
                        String society_name_select=jsonObject1.getString("block_no");
                        Block_tower_name.add(society_name_select);
                    }
                    String block_tower_select1="";
                    Block_tower_name1.add(block_tower_select1);
                    Block_tower_name1.addAll(Block_tower_name);

                    JSONArray jsonArray_customer_type=jsonObject.getJSONArray("customer_type");
                    for(int i=0;i<jsonArray_customer_type.length();i++){
                        JSONObject jsonObject1=jsonArray_customer_type.getJSONObject(i);
                        String society_name_select=jsonObject1.getString("customer_type");
                        Customer_Type.add(society_name_select);
                        Collections.reverse(Customer_Type);
                    }
                    String customer_type_select1="Select Customer Type";
                    Customer_Type1.add(customer_type_select1);
                    Customer_Type1.addAll(Customer_Type);

                    JSONArray jsonArray_street_no=jsonObject.getJSONArray("street_no");
                    for(int i=0;i<jsonArray_street_no.length();i++){
                        JSONObject jsonObject1=jsonArray_street_no.getJSONObject(i);
                        String society_name_select=jsonObject1.getString("street_no");
                        Street_road_name.add(society_name_select);
                    }
                    String street_road_select1="";
                    Street_road_name1.add(street_road_select1);
                    Street_road_name1.addAll(Street_road_name);

                    JSONArray jsonArray_house_type=jsonObject.getJSONArray("house_type");
                    for(int i=0;i<jsonArray_house_type.length();i++){
                        JSONObject jsonObject1=jsonArray_house_type.getJSONObject(i);
                        String society_name_select=jsonObject1.getString("house_type");
                        House_type_name.add(society_name_select);
                    }
                    String house_type_select1="";
                    House_type_name1.add(house_type_select1);
                    House_type_name1.addAll(House_type_name);

                    JSONArray jsonArray_floor=jsonObject.getJSONArray("floor");
                    for(int i=0;i<jsonArray_floor.length();i++){
                        JSONObject jsonObject1=jsonArray_floor.getJSONObject(i);
                        String society_name_select=jsonObject1.getString("floor_name");
                        Floor_name.add(society_name_select);

                    }

                    String floor_select1="Select Floor";
                    Floor_name1.add(floor_select1);
                    Floor_name1.addAll(Floor_name);

                    spinner_area.setAdapter(new ArrayAdapter<String>(New_Regestration_Form.this, android.R.layout.simple_spinner_dropdown_item, Area1));
                    spinner_block_tower.setAdapter(new ArrayAdapter<String>(New_Regestration_Form.this, android.R.layout.simple_spinner_dropdown_item, Block_tower_name1));
                    spinner_customertype.setAdapter(new ArrayAdapter<String>(New_Regestration_Form.this, android.R.layout.simple_spinner_dropdown_item, Customer_Type1));
                    spinner_street_road.setAdapter(new ArrayAdapter<String>(New_Regestration_Form.this, android.R.layout.simple_spinner_dropdown_item, Street_road_name1));
                    spinner_house_type.setAdapter(new ArrayAdapter<String>(New_Regestration_Form.this, android.R.layout.simple_spinner_dropdown_item, House_type_name1));
                    spinner_floor.setAdapter(new ArrayAdapter<String>(New_Regestration_Form.this, android.R.layout.simple_spinner_dropdown_item, Floor_name1));


                    // loadSpinner_Customer_Type(city_id);
                }catch (JSONException e){e.printStackTrace();}
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
        materialDialog = new MaterialDialog.Builder(New_Regestration_Form.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.Socity_List+area_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    // if(jsonObject.getInt("success")==1){
                    JSONArray jsonArray_society=jsonObject.getJSONArray("list_of_society");
                    for(int i=0;i<jsonArray_society.length();i++){
                        JSONObject jsonObject1=jsonArray_society.getJSONObject(i);
                        String society_name_select=jsonObject1.getString("society_name");
                        String society_id=jsonObject1.getString("society_id");
                       // Society_Id.add(society_id);
                        Society.add(society_name_select);

                    }
                    String area_select1="Select Society";
                    Society1.add(area_select1);
                    Society1.addAll(Society);
                    spinner_society.setAdapter(new ArrayAdapter<String>(New_Regestration_Form.this, android.R.layout.simple_spinner_dropdown_item, Society1));
                }catch (JSONException e){e.printStackTrace();}
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

    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {
        Context mContext;
        public ImageCompressionAsyncTask(Context context) {
            mContext = context;
        }
        @Override
        protected String doInBackground(String... params) {
            return SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
        }
        @Override
        protected void onPostExecute(String s) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                compressUri = Uri.parse(s);
                Log.e("compressUri",compressUri.toString());
                Compress_Adahar_Image= compressUri.toString();
            } else {
                File imageFile = new File(s);
                compressUri = Uri.fromFile(imageFile);
                Compress_Adahar_Image= compressUri.toString();
                Log.e("Compress",Compress_Adahar_Image);
            }
        }
    }
    class ImageCompressionAsyncTask1 extends AsyncTask<String, Void, String> {
        Context mContext;
        public ImageCompressionAsyncTask1(Context context) {
            mContext = context;
        }
        @Override
        protected String doInBackground(String... params) {
            return SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
        }
        @Override
        protected void onPostExecute(String s) {
            float length = 0;
            String name;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                compressUri = Uri.parse(s);
                Compress_Address_Image= compressUri.toString();

            } else {
                File imageFile = new File(s);
                compressUri = Uri.fromFile(imageFile);
                 Compress_Address_Image= compressUri.toString();
                Log.e("Compress",Compress_Address_Image);

            }
        }
    }
    public void New_Regestration_Form() {
        if(Type_Of_Owner.equals("Owner")){
            ownar_name="null";
        }else{
            ownar_name=ownar_name_no.getText().toString();
        }
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.BP_Creation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            //jsonObject = new JSONObject(str);
                            Log.e("BPCreationResponse++",json.toString());
                            String Msg=json.getString("Message");
                            Toast.makeText(New_Regestration_Form.this, Msg, Toast.LENGTH_SHORT).show();
                            // if (jsonObject.getString("success").equals("true")) {
                            JSONArray success=json.getJSONArray("Details");
                            for (int i = 0; i < success.length(); i++){
                                JSONObject Details = success.getJSONObject(0);
                                //pdf_file_path=Details.getString("file_path");
                                Bp_Number=Details.getString("Bp_Number");
                                //Dilogbox_Select_Option();
                                Log.e("Bp_Number++",Bp_Number);
                            }
                              uploadMultipart(Bp_Number);
                           // Toast.makeText(New_Regestration_Form.this, "" + "Error", Toast.LENGTH_SHORT).show();

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
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("First_Name",  fullname.getText().toString());
                    params.put("Middle_Name",  middle_name.getText().toString());
                    params.put("Last_Name",  lastname.getText().toString());
                    params.put("Mobile_Number",  mobile_no.getText().toString());
                    params.put("Email_ID",  email_id.getText().toString());
                    params.put("Aadhaar_Number",  aadhaar_no.getText().toString());
                    params.put("City_Region",  city_name);
                    params.put("Area",  area_name);
                    params.put("Society",  soceity_name);
                    params.put("Landmark",  landmark.getText().toString());
                    params.put("House_Type",  house_type_name);
                    params.put("HouseNo",  house_no.getText().toString());
                    params.put("Block_Qtr_Tower_Wing",  block_tower_name);
                    params.put("Floor",  floor_name);
                    params.put("Street_Gali_Road",  street_road_name);
                    params.put("Pin_Code",  pincode.getText().toString());
                    params.put("LPG_Company",  lpg_company_name);
                    params.put("Customer_Type",  customer_type);
                    params.put("LPG_DISTRIBUTOR", lpg_distributer.getText().toString());
                    params.put("LPG_CONSUMER_NO", lpg_consumer.getText().toString());
                    params.put("UNIQUE_LPG_ID",  unique_id_no.getText().toString());
                    params.put("ownerName",  ownar_name);
                    params.put("chequeNo",  chequeno_edit.getText().toString());
                    params.put("chequeDate",  chequedate_edit.getText().toString());
                    params.put("drawnOn",  drawnon_edit.getText().toString());
                    params.put("amt", amount_edit.getText().toString());
                    params.put("idProof", id_proof);
                    params.put("adressProof",  address_proof);
                    params.put("type_of_owner",  Type_Of_Owner);
                    params.put("id",  sharedPrefs.getUUID());
                    params.put("correspondingLanguage", "EN");
                    //params.put("SearchTerm", mobile_no.getText().toString());
                    params.put("select1",  block_tower_type_name);
                    params.put("select2",  street_road_type_name);


                } catch (Exception e) {
                }
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
               // headers.put("X-Requested-With", "XMLHttpRequest");
                  headers.put(" Content-Type", "multipart/form-data");
                //headers.put("Accept", "application/json");
               /// headers.put("Authorization", "Bearer " +sharedPrefs.getToken());
                return headers;
            }
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }

    private void BP_N0_DilogBox() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.bp_no_dilogbox);
        //dialog.setTitle("Signature");
        dialog.setCancelable(true);
        TextView bp_no_text=dialog.findViewById(R.id.bp_no_text);
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
        }
        if (lastname.getText().length() == 0) {
            isDataValid = false;
            lastname.setError("Enter Last Name");
        }
            /*if (email_id.getText().toString().trim().matches(emailPattern)) {
                // Toast.makeText(getActivity(),"valid email address",Toast.LENGTH_SHORT).show();
                isDataValid = true;
            } else {
              *//*  email_id.setError("Invalid email address");
                Toast.makeText(New_Regestration_Form.this,"Invalid email address", Toast.LENGTH_SHORT).show();
                isDataValid = false;*//*
            }*/

        if (mobile_no.getText().length()==0) {
            isDataValid = false;
            mobile_no.setError("Enter Mobile No");
        }
        if (city_name.equals("Select City")) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Enter City",Toast.LENGTH_SHORT).show();
        }
        if (area_name.equals("Select Area")) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Enter Area",Toast.LENGTH_SHORT).show();
        }
        if (soceity_name.equals("Select Society")) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Enter Society",Toast.LENGTH_SHORT).show();
        }
        if (floor_name.equals("Select Floor")) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Enter Floor",Toast.LENGTH_SHORT).show();
        }
        if (lpg_company_name.equals("Select LPG Company")) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Select LPG Company",Toast.LENGTH_SHORT).show();
        }
        if (customer_type.equals("Select Customer Type")) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Select Customer Type",Toast.LENGTH_SHORT).show();
        }
        if (house_no.getText().length() == 0) {
            isDataValid = false;
            house_no.setError("Enter House No");
            //Toast.makeText(New_Regestration_Form.this,"Enter Society",Toast.LENGTH_SHORT).show();
        }
        if (pincode.getText().length() == 0) {
            isDataValid = false;
            pincode.setError("Enter PinCode");
        }
        if (image_path_aadhar == null) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Please Select Image ID Proof",Toast.LENGTH_SHORT).show();
        }
        if (image_path_address== null) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Please Select Image Address Proof",Toast.LENGTH_SHORT).show();
        }
       /* if (Customer_Signature_path== null) {
            isDataValid = false;
            Toast.makeText(New_Regestration_Form.this,"Please Select Customer Signature",Toast.LENGTH_SHORT).show();
        }*/
        return isDataValid;
    }
}


