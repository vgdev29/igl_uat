package com.fieldmobility.igl.Activity;


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
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
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

import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;

import com.android.volley.Response;

import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.ConnectionDetector;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.GPSLocation;
import com.fieldmobility.igl.Helper.ScreenshotUtils;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MainActivity;
import com.fieldmobility.igl.Model.User_bpData;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.FilePath;
import com.fieldmobility.igl.utils.PDFBase64Sender;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.iceteck.silicompressorr.SiliCompressor;
import com.kyanogen.signatureview.SignatureView;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import static android.os.Environment.getExternalStorageDirectory;

public class BP_Creation_Form_step2 extends Activity implements AdapterView.OnItemSelectedListener, PickiTCallbacks {
    MaterialDialog materialDialog;
    ImageView id_imageView, address_imageView, customer_signature_imageview, owner_signature_imageview;
    Button customer_signature_button, id_button, address_button, viewpdf_button;
    EditText owner_name, chequeno_edit, drawnon_edit, amount_edit,et_total_floor,et_address;
    Button submit_button;
    TextView chequedate_edit, tv_pdf_path;
    SharedPrefs sharedPrefs;
    RadioGroup radioGroup, rg_payment_type;
    RadioButton genderradioButton, rbPayment;
    Bitmap bitmap_cheque, bitmap_id, bitmap_address, bitmap_cus, bitmap_own;
    Button clear, save;
    LinearLayout lt_cheque_image;
    boolean isSaleDeedSelected = false;

    String owner_signature_path, ownar_name = "", customer_signature_path;
    private final int PICK_IMAGE_REQUEST_ID = 1;
    private final int PICK_IMAGE_REQUEST_CHEQUE = 6;
    private final int PICK_IMAGE_REQUEST_ADDRESS = 3;
    static final int CAMERA_REQUEST_ID = 200;
    static final int CAMERA_REQUEST_CHEQUE = 601;
    static final int CAMERA_REQUEST_ADDRESS = 201;
    static final int CAMERA_OWNER_SIGNATURE = 4;
    static final int CAMERA_CUSTOMER_SIGNATURE = 5;
    static final int REQUEST_CODE_PDF_PICKER = 1001;
    static final int REQUEST_CODE_GALLERY_SIGN_PICK_CUSTOMER = 1002;
    static final int REQUEST_CODE_GALLERY_SIGN_PICK_OWNER = 1003;


    static final String IMAGE_DIRECTORY = "/signdemo";
    Spinner idproof_Spinner, address_spinner,spinner_formfill;
    private Uri filePathUri_cheque, filePathUri_id, filePathUri_address, filePathUri_owner, filePathUri_customer;
    String image_path_cheque, image_path_id, image_path_address, owner_image_select, pdf_path;
    String pdf_file_path;
    ImageView back;

    String id_proof, address_proof, Type_Of_Owner, typeOfPayment = "OFFLINE",form_location;

    String Bp_Number;

    CheckBox checkBox_term_cond;
    TextView checkbox_text , floor_text , address_text;
    DatePickerDialog pickerDialog_Date;

    ImageView iv_cheque;
    CheckBox /*check_undertaking_gpa,*/ /*check_undertaking_owner,*/ check_address_issue, check_multiple_floor;
    String /*annexure_gpa = "No",*/ /*annexure_owner = "No",*/ annexure_address = "No", annexure_floor = "No";
    LinearLayout ll_ownersig;
    Button owner_sign_button, btn_upload_cheque;
    User_bpData user_bpData;
    String Latitude, Longitude;
    //Animesh for pdf selection
    PickiT pickiT;
    String TAG = "BP_Creation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bp_creation_form_2);
        getLocationUsingInternet();
        sharedPrefs = new SharedPrefs(this);
        user_bpData = (User_bpData)getIntent().getSerializableExtra("data");
        Log.d(TAG, "oncreate");
        btn_upload_cheque = findViewById(R.id.btn_upload_cheque);
        et_total_floor = findViewById(R.id.et_total_floor);
        et_address = findViewById(R.id.et_address);
        lt_cheque_image = findViewById(R.id.lt_cheque_image);
        iv_cheque = findViewById(R.id.iv_cheque);
        chequeno_edit = findViewById(R.id.chequeno_edit);
        chequedate_edit = findViewById(R.id.chequedate_edit);
        tv_pdf_path = findViewById(R.id.tv_pdf_path);
        drawnon_edit = findViewById(R.id.drawnon_edit);
        amount_edit = findViewById(R.id.amount_edit);
        submit_button = findViewById(R.id.submit_button);
        idproof_Spinner = (Spinner) findViewById(R.id.spinner1);
        address_spinner = (Spinner) findViewById(R.id.spinner2);
        spinner_formfill = findViewById(R.id.spinner_formfill);
        id_imageView = findViewById(R.id.adhar_image);
        address_imageView = findViewById(R.id.address_image);
        customer_signature_imageview = findViewById(R.id.signature_image);
        owner_signature_imageview = findViewById(R.id.owner_signature_image);
        customer_signature_button = findViewById(R.id.signature_button);
        id_button = findViewById(R.id.adhar_button);
        address_button = findViewById(R.id.address_button);
        viewpdf_button = findViewById(R.id.viewpdf_button);
        owner_name = findViewById(R.id.owner_name);

        back = findViewById(R.id.back);
        checkBox_term_cond = findViewById(R.id.checkbox);
        checkbox_text = findViewById(R.id.checkbox_text);
        address_text = findViewById(R.id.address_text);
        floor_text = findViewById(R.id.floor_text);
//        check_undertaking_gpa = findViewById(R.id.undertaking_gpa);
//        check_undertaking_owner = findViewById(R.id.undertaking_owner);
        check_multiple_floor = findViewById(R.id.multiple_floor);
        check_multiple_floor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_total_floor.setVisibility(View.VISIBLE);
                    floor_text.setVisibility(View.VISIBLE);
                }
                else {
                    et_total_floor.setVisibility(View.GONE);
                    floor_text.setVisibility(View.GONE);
                }

            }
        });
        check_address_issue = findViewById(R.id.address_issue);
        check_address_issue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_address.setVisibility(View.VISIBLE);
                    address_text.setVisibility(View.VISIBLE);
                }
                else {
                    et_address.setVisibility(View.GONE);
                    address_text.setVisibility(View.GONE);
                }

            }
        });
        ll_ownersig = findViewById(R.id.ll_ownersig);
        owner_sign_button = findViewById(R.id.owner_signature_button);

        //Initialize PickiT
        //context, listener, activity
        pickiT = new PickiT(this, this, this);

        Click_Event();



    }

    private void Click_Event() {

        Type_Of_Owner = "Owner";
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_Method();
            }
        });
        customer_signature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_CustomerSignature_Method();
            }
        });
        owner_sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_OwnerSignature_Method();
            }
        });
        id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage_id();
            }
        });
        btn_upload_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChequeImage();
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


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.owner:
                        Type_Of_Owner = "Owner";
                        ll_ownersig.setVisibility(View.GONE);
                        break;
                    case R.id.rents:
                        Type_Of_Owner = "Rented";
                        ll_ownersig.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        rg_payment_type = (RadioGroup) findViewById(R.id.rg_payment_type);
        int selectedpaymentId = rg_payment_type.getCheckedRadioButtonId();
        rbPayment = (RadioButton) findViewById(selectedpaymentId);
        rg_payment_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_offline:
                        typeOfPayment = "OFFLINE";
                        lt_cheque_image.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_online:
                        typeOfPayment = "ONLINE";
                        lt_cheque_image.setVisibility(View.GONE);
                        break;
                }
            }
        });

        List<String> addressProofList = new ArrayList<String>();
        addressProofList.add("ELECTRICITY BILL");
        addressProofList.add("WATER BILL");
        addressProofList.add("SALE DEED/OTHER DOC");
        addressProofList.add("HOUSE TAX RECEIPT");
        addressProofList.add("ALLOTMENT LETTER");
        addressProofList.add("ANY OTHER");

        List<String> idProofList = new ArrayList<String>();
        idProofList.add("AADHAAR CARD");
        idProofList.add("DRIVING LICENCE");
        idProofList.add("UNDERTAKING (GPA CASE)");
        idProofList.add("UNDERTAKING (OWNER’s DEATH CASE)");
        idProofList.add("ANY OTHER");


        List<String> formlocation = new ArrayList<String>();
        formlocation.add("Customer Premises");
        formlocation.add("Other");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idProofList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idproof_Spinner.setAdapter(dataAdapter);


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, addressProofList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address_spinner.setAdapter(dataAdapter1);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, formlocation);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_formfill.setAdapter(dataAdapter2);

        idproof_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                id_proof = idproof_Spinner.getItemAtPosition(idproof_Spinner.getSelectedItemPosition()).toString();
                if (id_proof.equals("ANY OTHER")) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        address_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                address_proof = address_spinner.getItemAtPosition(address_spinner.getSelectedItemPosition()).toString();
                if (address_proof.equals("ANY OTHER")) {

                }
                if (address_proof.equals("SALE DEED/OTHER DOC")) {
                    isSaleDeedSelected = true;
                    address_button.setText("Browse file");
                    address_imageView.setVisibility(View.GONE);
                    tv_pdf_path.setVisibility(View.VISIBLE);
                    tv_pdf_path.setText("");
                   // pdf_path = "";
                    isSaleDeedSelected = true;

                } else {
                    isSaleDeedSelected = false;
                    address_button.setText("Address Proof");
                    tv_pdf_path.setVisibility(View.GONE);
                    address_imageView.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_formfill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                form_location = spinner_formfill.getItemAtPosition(spinner_formfill.getSelectedItemPosition()).toString();
                Log.d(TAG,form_location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        String first = "I agree to the ";
        String second = "<font color='#EE0000'> Terms and conditions</font>";
        String adress_issue = "<font color='#EE0000'>Address Issue Annexure</font>";
        String floor_issue = "<font color='#EE0000'>Multiple Floor Annexure</font>";
        String third = " of PNG registration.";
        checkbox_text.setText(Html.fromHtml(first + second + third));
        address_text.setText(Html.fromHtml("Click here to view "+adress_issue));
        floor_text.setText(Html.fromHtml("Click here to view "+floor_issue));

        checkbox_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BP_Creation_Form_step2.this, WebView_Activity.class);
                startActivity(intent);

            }
        });
        address_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BP_Creation_Form_step2.this, Address_Annexure.class);
                startActivity(intent);

            }
        });
        floor_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BP_Creation_Form_step2.this, Floor_Annexure.class);
                startActivity(intent);

            }
        });
        checkBox_term_cond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox_term_cond.setChecked(true);
                    Toast.makeText(BP_Creation_Form_step2.this, "You checked the checkbox!", Toast.LENGTH_SHORT).show();
                } else {
                    checkBox_term_cond.setChecked(false);
                    Toast.makeText(BP_Creation_Form_step2.this, "You unchecked the checkbox!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox_term_cond.isChecked()) {
                    if ((Bp_Number == null || Bp_Number.isEmpty()) && validateData()) {
                        Form_dataSubmit();
                        //uploadMultipart();
                        // BP_N0_DilogBox();
                    } else if (Bp_Number != null && !Bp_Number.isEmpty() && imgUploadError) {
                        uploadMultipart(Bp_Number);
                    }

                } else {
                    Toast.makeText(BP_Creation_Form_step2.this, "Please tick to accept Tearm & Condition", Toast.LENGTH_SHORT).show();
                }
            }
        });


        chequedate_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                pickerDialog_Date = new DatePickerDialog(BP_Creation_Form_step2.this,
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
                                Log.e(TAG, year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);

                                chequedate_edit.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                pickerDialog_Date.show();
            }
        });
    }

    private void  selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
     //   startActivityForResult(Intent.createChooser(intent, "Select PDF"), REQUEST_CODE_PDF_PICKER);
        startActivityForResult(intent, REQUEST_CODE_PDF_PICKER);
      /*  Intent intent4 = new Intent(this, NormalFilePickActivity.class);
        intent4.putExtra(Constant.MAX_NUMBER, 1);
        intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"pdf"});
        startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);*/


    }

    private void selectChequeImage() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_CHEQUE);
                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(BP_Creation_Form_step2.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_CHEQUE);
                    }
                });
        myAlertDialog.show();
    }


    private void selectImage_id() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_ID);
                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(BP_Creation_Form_step2.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_ID);
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
                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(BP_Creation_Form_step2.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_ADDRESS);
                    }
                });
        myAlertDialog.show();
    }

    private void select_CustomerSignature_Method() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your Signature?");
        myAlertDialog.setNegativeButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
//                        Uri photoURI = FileProvider.getUriForFile(BP_Creation_Form_step2.this, getApplicationContext().getPackageName() + ".provider", f);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(intent, CAMERA_CUSTOMER_SIGNATURE);
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_GALLERY_SIGN_PICK_CUSTOMER);

                    }
                });
        myAlertDialog.setPositiveButton("Signature",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        customer_Signature_View();
                    }
                });
        myAlertDialog.show();
    }

    private void select_OwnerSignature_Method() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your Signature?");
        myAlertDialog.setNegativeButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
//                        Uri photoURI = FileProvider.getUriForFile(BP_Creation_Form_step2.this, getApplicationContext().getPackageName() + ".provider", f);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(intent, CAMERA_OWNER_SIGNATURE);
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_GALLERY_SIGN_PICK_OWNER);

                    }
                });
        myAlertDialog.setPositiveButton("Signature",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        owner_Signature_View();
                    }
                });
        myAlertDialog.show();
    }

    private void customer_Signature_View() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        final SignatureView customer_signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
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
                customer_signatureView.clearCanvas();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap_cus = customer_signatureView.getSignatureBitmap();
                if (bitmap_cus != null) {
                    customer_signature_path = change_to_binary(bitmap_cus);
                    customer_signature_imageview.setImageBitmap(bitmap_cus);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void owner_Signature_View() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        final SignatureView signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
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
                bitmap_own = signatureView.getSignatureBitmap();
                if (bitmap_own != null) {
                    owner_signature_path = change_to_binary(bitmap_own);
                    owner_signature_imageview.setImageBitmap(bitmap_own);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
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
                Toast.makeText(BP_Creation_Form_step2.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PDF_PICKER:
                if (requestCode == REQUEST_CODE_PDF_PICKER && resultCode == this.RESULT_OK && data != null && data.getData() != null) {

                    pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                }
                break;
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK) {
                    ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    Log.d(TAG, "pdf file list = " + list.size());
                    if (list != null && list.size() > 0) {
                        pdf_path = list.get(0).getPath();
                    //    Log.d(TAG, "pdf path = " + pdf_path);
                        tv_pdf_path.setText(list.get(0).getName());
                    }
                }
                break;

            case PICK_IMAGE_REQUEST_ID:
                if (requestCode == PICK_IMAGE_REQUEST_ID && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathUri_id = data.getData();
                    try {

                        bitmap_id = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathUri_id);
                    //    bitmap_id = getResizedBitmap(bitmap_id, 1600);
                     //   id_imageView.setImageBitmap(bitmap_id);
                     //   File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                     //   image_path_id = ScreenshotUtils.store(bitmap_id, "id_proof" + ".jpg", saveFile).toString();
                        if (bitmap_id != null) {
                            int nh = (int) ( bitmap_id.getHeight() * (512.0 / bitmap_id.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_id, 512, nh, true);
                            id_imageView.setImageBitmap(scaled);
                            image_path_id = change_to_binary(bitmap_id);
                            }
                       // image_path_id = getPath(filePathUri_id);
                    //    Log.d(TAG, "imagepath id pick image = " + image_path_id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case CAMERA_REQUEST_ID:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_ID) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap_id = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);

                        int nh = (int) ( bitmap_id.getHeight() * (512.0 / bitmap_id.getWidth()) );
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmap_id, 512, nh, true);
                        id_imageView.setImageBitmap(scaled);
                        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                       // Log.e("Camera_Path++", file.toString());
                       // image_path_id = file.toString();
                    //    File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                    //    image_path_id = ScreenshotUtils.store(bitmap_id, "id_proof" + ".jpg", saveFile).toString();
                     //   Log.d(TAG, "imagepath id camera image = " + image_path_id);
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap_id.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
                            outFile.flush();
                            outFile.close();
                            if (bitmap_id != null) {
                                image_path_id = change_to_binary(bitmap_id);
                                //nguserListModel.setHome_address(homeAddress_pic_binary);
                            }
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
            case PICK_IMAGE_REQUEST_CHEQUE:
                if (requestCode == PICK_IMAGE_REQUEST_CHEQUE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathUri_cheque = data.getData();
                    try {

                        bitmap_cheque = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathUri_cheque);
                     //   iv_cheque.setImageBitmap(bitmap_cheque);
                       // image_path_cheque = getPath(filePathUri_cheque);
                    //    File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                    //    image_path_cheque = ScreenshotUtils.store(bitmap_cheque, "cheque" + ".jpg", saveFile).toString();
                      //  Log.d(TAG, "imagepath cheque pick image = " + image_path_cheque);
                        if (bitmap_cheque != null) {
                            int nh = (int) ( bitmap_cheque.getHeight() * (512.0 / bitmap_cheque.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_cheque, 512, nh, true);
                            iv_cheque.setImageBitmap(scaled);
                            image_path_cheque = change_to_binary(bitmap_cheque);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CAMERA_REQUEST_CHEQUE:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CHEQUE) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap_cheque = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                       // File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                       // image_path_cheque = ScreenshotUtils.store(bitmap_cheque, "cheque" + ".jpg", saveFile).toString();
                        f.delete();
                    //    Log.d("bpcreation", "imagepath id camera image = " + image_path_cheque);
                        if (bitmap_cheque != null) {
                            int nh = (int) ( bitmap_cheque.getHeight() * (512.0 / bitmap_cheque.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_cheque, 512, nh, true);
                            iv_cheque.setImageBitmap(scaled);
                            image_path_cheque = change_to_binary(bitmap_cheque);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_IMAGE_REQUEST_ADDRESS:
                if (requestCode == PICK_IMAGE_REQUEST_ADDRESS && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathUri_address = data.getData();
               //     Log.e("Camera_Pathaddress++", filePathUri_address.toString());
                    try {

                        bitmap_address = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathUri_address);
                        // imageView.setImageBitmap(bitmap);
                     //   address_imageView.setImageBitmap(bitmap_address);
                    //    File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                    //    image_path_address = ScreenshotUtils.store(bitmap_address, "address_proof" + ".jpg", saveFile).toString();

                       // image_path_address = getPath1(filePathUri_address);
                      //  Log.d("bpcreation", "imagepath address pick image = " + image_path_address);
                        if (bitmap_address != null) {
                            int nh = (int) ( bitmap_address.getHeight() * (512.0 / bitmap_address.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_address, 512, nh, true);
                            address_imageView.setImageBitmap(scaled);
                            image_path_address = change_to_binary(bitmap_address);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CAMERA_REQUEST_ADDRESS:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_ADDRESS) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap_address = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                       // address_imageView.setImageBitmap(bitmap_address);
                       // File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                      //  image_path_address = ScreenshotUtils.store(bitmap_address, "address_proof" + ".jpg", saveFile).toString();
                        f.delete();
                        if (bitmap_address != null) {
                            int nh = (int) ( bitmap_address.getHeight() * (512.0 / bitmap_address.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_address, 512, nh, true);
                            address_imageView.setImageBitmap(scaled);
                            image_path_address = change_to_binary(bitmap_address);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CAMERA_CUSTOMER_SIGNATURE:
                if (resultCode == RESULT_OK && requestCode == CAMERA_CUSTOMER_SIGNATURE) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap_cus = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                     //   File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                     //   customer_signature_path = ScreenshotUtils.store(bitmap_cus, "customer_signature" + ".jpg", saveFile).toString();
                        f.delete();
                        if (bitmap_cus != null) {
                            int nh = (int) ( bitmap_cus.getHeight() * (512.0 / bitmap_cus.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_cus, 512, nh, true);
                            customer_signature_imageview.setImageBitmap(scaled);
                            customer_signature_path = change_to_binary(bitmap_address);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CAMERA_OWNER_SIGNATURE:
                if (resultCode == RESULT_OK && requestCode == CAMERA_OWNER_SIGNATURE) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap_own = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                        f.delete();
                        if (bitmap_own != null) {
                            int nh = (int) ( bitmap_own.getHeight() * (512.0 / bitmap_own.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_own, 512, nh, true);
                            owner_signature_imageview.setImageBitmap(scaled);
                            owner_signature_path = change_to_binary(bitmap_own);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_CODE_GALLERY_SIGN_PICK_CUSTOMER:
                if (requestCode == REQUEST_CODE_GALLERY_SIGN_PICK_CUSTOMER && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathUri_customer = data.getData();
                    try {
                        Bitmap bitmap_ = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathUri_customer);
                      //  File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                      //  customer_signature_path = ScreenshotUtils.store(bitmap_, "customer_signature" + ".jpg", saveFile).toString();

                       // customer_signature_path = getPath(filePathUri_customer);
                        if (bitmap_ != null) {
                            int nh = (int) ( bitmap_.getHeight() * (512.0 / bitmap_.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_, 512, nh, true);
                            customer_signature_imageview.setImageBitmap(scaled);
                            customer_signature_path = change_to_binary(bitmap_);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case REQUEST_CODE_GALLERY_SIGN_PICK_OWNER:
                if (requestCode == REQUEST_CODE_GALLERY_SIGN_PICK_OWNER && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathUri_owner = data.getData();
                    try {
                        bitmap_own = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathUri_owner);
                        File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                        owner_signature_path = ScreenshotUtils.store(bitmap_own, "owner_signature" + ".jpg", saveFile).toString();

                      //  owner_signature_path = getPath(filePathUri_owner);
                        if (bitmap_own != null) {
                            int nh = (int) ( bitmap_own.getHeight() * (512.0 / bitmap_own.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap_own, 512, nh, true);
                            owner_signature_imageview.setImageBitmap(scaled);
                            owner_signature_path = change_to_binary(bitmap_own);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;


        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                getFilesDir() + IMAGE_DIRECTORY /*iDyme folder*/);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d(TAG, wallpaperDirectory.toString());
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d(TAG, "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



    boolean imgUploadError = false;

    public void uploadMultipart(String Bp_Number) {
        if (owner_signature_path == null) {
            owner_signature_path = customer_signature_path;
            Log.e("signature_path", owner_signature_path);
        }
        try {
            materialDialog = new MaterialDialog.Builder(BP_Creation_Form_step2.this)
                    .content("Please wait....")
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            String uploadId = UUID.randomUUID().toString();
            Log.e("uploadId+,,,,,,,,,,", "" + uploadId);
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(BP_Creation_Form_step2.this, uploadId, Constants.BP_Images + "/" + Bp_Number);
            if (image_path_cheque != null && !image_path_cheque.isEmpty()) {
                multipartUploadRequest.addFileToUpload(image_path_cheque, "image", "cheque.jpg");
            }
            if (image_path_id!=null && !image_path_id.isEmpty()) {
                multipartUploadRequest.addFileToUpload(image_path_id, "image", "id_proof.jpg");
            }
            if (isSaleDeedSelected) {
                multipartUploadRequest.addFileToUpload(pdf_path, "image", "sale_deed.pdf", ContentType.APPLICATION_PDF);

            } else {
                multipartUploadRequest.addFileToUpload(image_path_address, "image", "address_proof.jpg");
            }
            multipartUploadRequest.addFileToUpload(customer_signature_path, "image", "customer_signature.jpg");
            multipartUploadRequest.addFileToUpload(owner_signature_path, "image", "owner_signature.jpg");
            multipartUploadRequest.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(Context context, UploadInfo uploadInfo) {
                    Log.e("UplodeINFO++", uploadInfo.getSuccessfullyUploadedFiles().toString());
                }

                @Override
                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                    exception.printStackTrace();
                    materialDialog.dismiss();
                    imgUploadError = true;
                    Log.e("Uplodeerror++", uploadInfo.getSuccessfullyUploadedFiles().toString());
                }

                @Override
                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                    materialDialog.dismiss();
                    String str = serverResponse.getBodyAsString();
                    final JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(str);
                        Log.e("Response++", jsonObject.toString());
                        if (jsonObject.getString("Code").equals("200")) {
                            String Msg = jsonObject.getString("Message");
                            Toast.makeText(BP_Creation_Form_step2.this, Msg, Toast.LENGTH_SHORT).show();
                            imgUploadError = false;
                            BP_N0_DilogBox();
                        } else {
                            imgUploadError = true;
                            String Msg = jsonObject.getString("Message");
                            Toast.makeText(BP_Creation_Form_step2.this, Msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        imgUploadError = true;
                        Toast.makeText(BP_Creation_Form_step2.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(Context context, UploadInfo uploadInfo) {
                    imgUploadError = true;
                    materialDialog.dismiss();

                }
            });

            multipartUploadRequest.setMaxRetries(2);
            multipartUploadRequest.startUpload(); //Starting the upload


        } catch (Exception exc) {
            imgUploadError = true;
            Log.d(TAG,exc.getLocalizedMessage());
            Toast.makeText(BP_Creation_Form_step2.this, "Please Select ID & Address Proof and Proper Signature", Toast.LENGTH_SHORT).show();
            materialDialog.dismiss();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), "Selected User: " + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int i) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String reason) {
        //  Check if it was a Drive/local/unknown provider file and display a Toast
        if (wasDriveFile) {
            Log.d(TAG,"Drive file was selected");
        }
        else if (wasUnknownProvider) {

            Log.d(TAG,"File was selected from unknown provider");
        } else {
            Log.d(TAG,"Local file was selected");
        }

        //  Chick if it was successful
        if (wasSuccessful) {
            //  Set returned path to TextView

            // Log.d("bpcreation","pickit path = "+path);
            try {
                if (path.contains("/proc/")) {
                    //   pickitTv.setText("Sub-directory inside Downloads was selected." + "\n" + " We will be making use of the /proc/ protocol." + "\n" + " You can use this path as you would normally." + "\n\n" + "PickiT path:" + "\n" + path);
                    pdf_path = path;
                    Log.d(TAG, "pdf path = " + pdf_path);
                    tv_pdf_path.setText(pdf_path);
                    byte[] pdfByte = PDFBase64Sender.readPDFFile(pdf_path);
                    pdf_path = PDFBase64Sender.encodeToBase64(pdfByte);
                 //   Log.d(TAG, "pdf path = " + pdf_path);
                    tv_pdf_path.setText(pdf_path);
                } else {
                    pdf_path = path;
                    Log.d(TAG, "pdf path else = " + pdf_path);
                    tv_pdf_path.setText(pdf_path);
                    byte[] pdfByte = PDFBase64Sender.readPDFFile(pdf_path);
                    pdf_path = PDFBase64Sender.encodeToBase64(pdfByte);
                   // Log.d(TAG, "pdf path = " + pdf_path);

                }
            }
            catch (Exception e){
                Log.d(TAG,"Exception pdf path = "+e.getLocalizedMessage());
            }

    }
        else{
            Log.d(TAG, "pdf path = else " );
        }
    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> arrayList, boolean b, String s) {
      //  Log.d("bpcreation","pickit path multiple = "+arrayList.toString());
    }


    public void Form_dataSubmit() {
        if (owner_signature_path == null) {
            owner_signature_path = customer_signature_path;
         //   Log.e("signature_path", owner_signature_path);
        }


        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();
        String login_request = "login_request";
        Log.d(TAG, Constants.BP_Creation);

        StringRequest jr = new StringRequest(Request.Method.POST, Constants.BP_Creation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.d(TAG, json.toString());
                            String Msg = json.getString("Message");
                            Bp_Number = json.getString("Details");

                            if (json.getString("Code").equals("200")) {
                                Toast.makeText(BP_Creation_Form_step2.this, Msg, Toast.LENGTH_SHORT).show();
                                BP_N0_DilogBox();
                            } else {
                                if (Bp_Number.isEmpty()) {
                                    Toast.makeText(BP_Creation_Form_step2.this, Msg, Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(BP_Creation_Form_step2.this, Msg, Toast.LENGTH_SHORT).show();
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BP_Creation_Form_step2.this, "Error: Bp not created", Toast.LENGTH_SHORT).show();

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
                        Log.e(TAG, res);
                        JSONObject obj = new JSONObject(res);
                        Log.e(TAG, obj.toString());
                        String errormsg = obj.getString("message");
                        Toast.makeText(BP_Creation_Form_step2.this, errormsg, Toast.LENGTH_SHORT).show();

                    } catch (Exception e1) {
                        Log.e(TAG, e1.getLocalizedMessage());
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("First_Name", user_bpData.getIgl_first_name());
                    params.put("Middle_Name", user_bpData.getIgl_middle_name());
                    params.put("Last_Name", user_bpData.getIgl_last_name());
                    params.put("Mobile_Number", user_bpData.getIgl_mobile_no());
                    params.put("Alternate_Mobile_Number", user_bpData.getIgl_alternate_mobile_no());
                    params.put("Email_ID", user_bpData.getIgl_email_id());
                    params.put("Father_Name", user_bpData.getIgl_father_name());
                    params.put("Aadhaar_Number", user_bpData.getIgl_aadhaar_no());
                    params.put("City_Region", user_bpData.getIgl_city_region());
                    params.put("Area", user_bpData.getIgl_area());
                    params.put("Society", user_bpData.getIgl_society());
                    params.put("Landmark", user_bpData.getIgl_landmark());
                    params.put("House_Type", user_bpData.getIgl_house_type());
                    params.put("HouseNo", user_bpData.getIgl_house_no());
                    params.put("Block_Qtr_Tower_Wing", user_bpData.getIgl_Block_Qtr_Tower_Wing());
                    params.put("Floor", user_bpData.getIgl_floor());
                    params.put("Street_Gali_Road", user_bpData.getIgl_street_gali_road());
                    params.put("Pin_Code", user_bpData.getIgl_pincode());
                    params.put("LPG_Company", user_bpData.getIgl_lpg_company());
                    params.put("Customer_Type", user_bpData.getIgl_type_of_customer());//
                    params.put("LPG_DISTRIBUTOR", user_bpData.getId_lpg_distributor());
                    params.put("LPG_CONSUMER_NO", user_bpData.getIgl_lpg_consumer_no());
                    params.put("UNIQUE_LPG_ID", user_bpData.getIgl_unique_lpg_id());
                    params.put("ownerName", owner_name.getText().toString().trim());
                    params.put("chequeNo", chequeno_edit.getText().toString());
                    params.put("chequeDate", chequedate_edit.getText().toString());
                    params.put("drawnOn", drawnon_edit.getText().toString());
                    params.put("amt", amount_edit.getText().toString());
                    params.put("idProof", id_proof);
                    params.put("adressProof", address_proof);
                    params.put("type_of_owner", Type_Of_Owner);
                    params.put("type_of_payment", typeOfPayment);
                    params.put("id", sharedPrefs.getUUID());
                    params.put("correspondingLanguage", "EN");
                    // params.put("SearchTerm",fullname.getText().toString().toUpperCase() );
 //                   params.put("annexure_gpa", annexure_gpa);
                    params.put("annexure_floor", annexure_floor);
                    params.put("annexure_address", annexure_address);
//                    params.put("annexure_owner", annexure_owner);
                    params.put("block_type", user_bpData.getBlock_tower_type());
                    params.put("street_type", user_bpData.getStreet_road_type());
                    params.put("latitude", Latitude);
                    params.put("longitude", Longitude);
                    params.put("ownership_multi_floor", et_total_floor.getText().toString());
                    params.put("ownership_complete_address", et_address.getText().toString());
                    params.put("filling_location", form_location);
                    //Added on 7-9-23 by Animesh
                    if (image_path_cheque!=null && !image_path_cheque.isEmpty()){
                        params.put("chq_img",image_path_cheque);
                        Log.d(TAG,"Image path cheq selected");
                    }
                    else{
                        params.put("chq_img","null");
                        Log.d(TAG,"Image path cheq null");
                    }

                    if (image_path_id!=null && !image_path_id.isEmpty()){
                        params.put("id_img",image_path_id);
                        Log.d(TAG,"Image path id selected");
                    }
                    else{
                        params.put("id_img","null");
                        Log.d(TAG,"Image path id null");
                    }

                  //  params.put("id_img", image_path_id);

                    if (isSaleDeedSelected){
                        params.put("address_img", "null");
                        params.put("saledeed", pdf_path);
                        Log.d(TAG,"Saledeed selected");
                    }else{
                        params.put("address_img", image_path_address);
                        params.put("saledeed", "null");
                        Log.d(TAG,"Saledeed not selected");
                    }


                    params.put("cust_img", customer_signature_path);
                    params.put("own_img", owner_signature_path);

                    Log.d(TAG, "params size = "+params.size());

                    Log.d("First_Name", user_bpData.getIgl_first_name());
                    Log.d("Middle_Name", user_bpData.getIgl_middle_name());
                    Log.d("Last_Name", user_bpData.getIgl_last_name());
                    Log.d("Father_Name", user_bpData.getIgl_father_name());
                    Log.d("Mobile_Number", user_bpData.getIgl_mobile_no());
                    Log.d("Email_ID", user_bpData.getIgl_email_id());
                    Log.d("Aadhaar_Number", user_bpData.getIgl_aadhaar_no());
                    Log.d("City_Region", user_bpData.getIgl_city_region());
                    Log.d("Area", user_bpData.getIgl_area());
                    Log.d("Society", user_bpData.getIgl_society());
                    Log.d("Landmark", user_bpData.getIgl_landmark());
                    Log.d("House_Type", user_bpData.getIgl_house_type());
                    Log.d("HouseNo", user_bpData.getIgl_house_no());
                    Log.d("Block_Qtr_Tower_Wing", user_bpData.getIgl_Block_Qtr_Tower_Wing());
                    Log.d("Floor", user_bpData.getIgl_floor());
                    Log.d("Street_Gali_Road", user_bpData.getIgl_street_gali_road());
                    Log.d("Pin_Code", user_bpData.getIgl_pincode());
                    Log.d("LPG_Company", user_bpData.getIgl_lpg_company());
                    Log.d("Customer_Type", user_bpData.getIgl_type_of_customer());//
                    Log.d("LPG_DISTRIBUTOR", user_bpData.getId_lpg_distributor());
                    Log.d("LPG_CONSUMER_NO", user_bpData.getIgl_lpg_consumer_no());
                    Log.d("UNIQUE_LPG_ID", user_bpData.getIgl_unique_lpg_id());
                    Log.d("ownerName", owner_name.getText().toString().trim());
                    Log.d("chequeNo", chequeno_edit.getText().toString());
                    Log.d("chequeDate", chequedate_edit.getText().toString());
                    Log.d("drawnOn", drawnon_edit.getText().toString());
                    Log.d("amt", amount_edit.getText().toString());
                    Log.d("idProof", id_proof);
                    Log.d("adressProof", address_proof);
                    Log.d("type_of_owner", Type_Of_Owner);
                    Log.d("id", sharedPrefs.getUUID());
                    Log.d("correspondingLanguage", "EN");
                    // params.put("SearchTerm",fullname.getText().toString().toUpperCase() );
//                    Log.d("annexure_gpa", annexure_gpa);
                    Log.d("annexure_floor", annexure_floor);
                    Log.d("annexure_address", annexure_address);
//                    Log.d("annexure_owner", annexure_owner);
                    Log.d("block_type", user_bpData.getBlock_tower_type());
                    Log.d("street_type", user_bpData.getStreet_road_type());
                    Log.d("dma_agency", sharedPrefs.getUUID());
                    Log.d("lattitude", Latitude);
                    Log.d("longitude", Longitude);
                    Log.d("ownership_multi_floor", et_total_floor.getText().toString());
                    Log.d("ownership_complete_address", et_address.getText().toString());
                    Log.d("Alternate_Mobile_Number", user_bpData.getIgl_alternate_mobile_no());
                    Log.d("filling_location", form_location);
                  //  Log.d("bpcreation","params  = " +params.toString());

                    //Added on 7-9-23 by Animesh
                    /*Log.d("chq_img",image_path_cheque);
                    Log.d("id_img", image_path_id);
                    Log.d("address_img", image_path_address);
                    Log.d("cust_img", customer_signature_path);
                    Log.d("own_img", owner_signature_path);*/

                } catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }
                return params;
            }

        };
        jr.setRetryPolicy(new DefaultRetryPolicy(25 * 10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }

    private void BP_N0_DilogBox() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.bp_no_dilogbox);
        //dialog.setTitle("Signature");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView bp_no_text = dialog.findViewById(R.id.bp_no_text);
        bp_no_text.setText(Bp_Number);
        Button ok_button = (Button) dialog.findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
                Intent intent = new Intent(BP_Creation_Form_step2.this, MainActivity.class);
                startActivity(intent);

            }
        });


        dialog.show();
    }

    private boolean validateData() {
        boolean isDataValid = true;

      /*  if (image_path_id == null) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form_step2.this, "Please Select Image ID Proof", Toast.LENGTH_SHORT).show();
        } */
        if (check_multiple_floor.isChecked() && et_total_floor.getText().length()==0){
            isDataValid = false;
            Toast.makeText(BP_Creation_Form_step2.this, "Please Enter Total Floors", Toast.LENGTH_SHORT).show();
        }
       else if (check_address_issue.isChecked() && et_address.getText().length()==0){
            isDataValid = false;
            Toast.makeText(BP_Creation_Form_step2.this, "Please Enter Ownership Address", Toast.LENGTH_SHORT).show();
        }
        else if (isSaleDeedSelected && pdf_path == null) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form_step2.this, "Please Select Address Proof", Toast.LENGTH_SHORT).show();
        }
        else if (!isSaleDeedSelected  && image_path_address == null ) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form_step2.this, "Please Select Address Proof", Toast.LENGTH_SHORT).show();
        }else if (customer_signature_path == null || customer_signature_path.isEmpty()) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form_step2.this, "Please Select Customer Signature", Toast.LENGTH_SHORT).show();
        }
        /*else if (image_path_id == null || image_path_id.isEmpty()) {
            isDataValid = false;
            Toast.makeText(BP_Creation_Form_step2.this, "Please Select ID Proof", Toast.LENGTH_SHORT).show();
        }*/
        else if (Type_Of_Owner.equalsIgnoreCase("Rented") && owner_name.getText().toString().isEmpty()) {
            Toast.makeText(BP_Creation_Form_step2.this, "Please Enter Owner name", Toast.LENGTH_SHORT).show();
            owner_name.setError("Enter owner name");
            isDataValid = false;
        } else if (Type_Of_Owner.equalsIgnoreCase("Rented") && (owner_signature_path == null || owner_signature_path.isEmpty())) {
            Toast.makeText(BP_Creation_Form_step2.this, "Please Select owner Signature", Toast.LENGTH_SHORT).show();
            isDataValid = false;
        } else {
            isDataValid = true;
        }
//        if (check_undertaking_owner.isChecked()) {
//           annexure_owner = "Yes";
//        }
        if (check_address_issue.isChecked()) {
            annexure_address = "Yes";
        }
        if (check_multiple_floor.isChecked()) {
            annexure_floor = "Yes";
        }
//        if (check_undertaking_gpa.isChecked()) {
//            annexure_gpa = "Yes";
//        }
        return isDataValid;
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


   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("idphotopath", image_path_id);
        //outState.putParcelable("bitmap_id",bitmap_id);
        outState.putString("addressphotopath", image_path_address);
        //outState.putParcelable("bitmap_address",bitmap_address);
        outState.putString("cussig", customer_signature_path);
        //outState.putParcelable("bitmap_cus",bitmap_cus);
        outState.putString("ownsig", owner_signature_path);
        //outState.putParcelable("bitmap_own",bitmap_own);
        outState.putString("cheque", image_path_cheque);

        outState.putString("chequeDate", chequedate_edit.getText().toString().trim());
       // Log.d("bpcreation", "onSaveInstance" + outState);
        super.onSaveInstanceState(outState);
    }*/

 /*   @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    //    Log.d("bpcreation", "onRestoreInstance" + savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString("idphotopath") != null && !savedInstanceState.getString("idphotopath").isEmpty()) {

                image_path_id = savedInstanceState.getString("idphotopath");
                // Bitmap bitmap = savedInstanceState.getParcelable("bitmap_id");
                //   id_imageView.setImageBitmap(bitmap);
             //   Log.d("bpcreation", "on restore image " + image_path_id);
            }
            if (savedInstanceState.getString("addressphotopath") != null && !savedInstanceState.getString("addressphotopath").isEmpty()) {

                image_path_address = savedInstanceState.getString("addressphotopath");
                //  Bitmap bitmap = savedInstanceState.getParcelable("bitmap_address");
                //address_imageView.setImageBitmap(bitmap);
            }
            if (savedInstanceState.getString("cussig") != null && !savedInstanceState.getString("cussig").isEmpty()) {

                customer_signature_path = savedInstanceState.getString("cussig");
                //customer_signature_imageview.setImageBitmap(savedInstanceState.getParcelable("bitmap_cus"));
            }
            if (savedInstanceState.getString("ownsig") != null && !savedInstanceState.getString("ownsig").isEmpty()) {

                owner_signature_path = savedInstanceState.getString("ownsig");
                //owner_signature_imageview.setImageBitmap(savedInstanceState.getParcelable("bitmap_own"));
            }

            if (savedInstanceState.getString("cheque") != null && !savedInstanceState.getString("cheque").isEmpty()) {

                image_path_cheque = savedInstanceState.getString("cheque");
                //owner_signature_imageview.setImageBitmap(savedInstanceState.getParcelable("bitmap_own"));
            }
            if (savedInstanceState.getString("chequeDate") != null && !savedInstanceState.getString("chequeDate").isEmpty()) {

                String date = savedInstanceState.getString("chequeDate");
                chequedate_edit.setText(date);
                //owner_signature_imageview.setImageBitmap(savedInstanceState.getParcelable("bitmap_own"));
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("bpcreation", "on destroy");
        if (!isChangingConfigurations()) {
            pickiT.deleteTemporaryFile(this);
        }
    }

    @Override
    public void onBackPressed() {
        pickiT.deleteTemporaryFile(this);
        super.onBackPressed();
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        maxSize = 512;
        int nh = (int) ( bitmap.getHeight() * (maxSize / bitmap.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, maxSize, nh, true);
        return scaled;
    }

    private void getLocationUsingInternet() {
        boolean isInternetConnected = new ConnectionDetector(BP_Creation_Form_step2.this).isConnectingToInternet();
        try {
            if (isInternetConnected) {
                // getLocation_usingInternet.setEnabled(false);
                new GPSLocation(BP_Creation_Form_step2.this).turnGPSOn();// First turn on GPS
                String getLocation = new GPSLocation(BP_Creation_Form_step2.this).getMyCurrentLocation();// Get current location from
                Log.d(TAG, getLocation.toString());
                Latitude = GPSLocation.Latitude;
                Longitude = GPSLocation.Longitude;
                Log.d(TAG, Latitude);
                Log.d(TAG, Longitude);
            } else {
                Toast.makeText(BP_Creation_Form_step2.this, "There is no internet connection.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e ){ Toast.makeText(BP_Creation_Form_step2.this, "Switch to Mobile data for location.", Toast.LENGTH_SHORT).show();}
    }

    private String  change_to_binary(Bitmap bitmapOrg) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        return ba1;
    }

}


