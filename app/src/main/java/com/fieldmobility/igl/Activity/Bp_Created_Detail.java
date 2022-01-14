package com.fieldmobility.igl.Activity;

import static android.os.Environment.getExternalStorageDirectory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.ScreenshotUtils;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;
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

public class Bp_Created_Detail extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    private String log = "kycform";

    LinearLayout todo_creation, lt_resubmit, lt_proof_detail;
    String Address, status, sub_status, IGL_Status;
    EditText descreption_edit;
    Spinner spinner1, spinner_sub_master;
    Bp_No_Item bp_No_Item;
    TextView btn_submit_address_proof, btn_submit_id_proof, btn_submit_signature;
    Spinner address_proof_spinner, id_proof_spinner;

    boolean hasImageData = false;
    RadioButton owner , rents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bp_created_detail);
        String dataJson = getIntent().getStringExtra("data");
        lt_proof_detail = findViewById(R.id.lt_proof_detail);
        lt_resubmit = findViewById(R.id.lt_resubmit);
        if (dataJson != null && !dataJson.isEmpty()) {
            bp_No_Item = new Gson().fromJson(dataJson, Bp_No_Item.class);
            hasImageData = bp_No_Item.isImages();
            initViews();
            findViewById(R.id.tv_appointment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent=new Intent(Bp_Created_Detail.this,To_Do_Task_Creation.class);
//                    intent.putExtra("Bp_number",bp_No_Item.getBp_number());
//                    intent.putExtra("Address",Address);
//                    intent.putExtra("Type","1");
//                    startActivity(intent);
                }
            });
        }
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        Layout_Id();
//        Implementation_Mentod();
    }

    String img_id_proof_url = "", img_address_proof_url = "", img_customer_signature_url = "", img_owner_signature_url = "", img_cheque = "";

    private void initViews() {
        lt_resubmit.setVisibility(View.VISIBLE);
        lt_proof_detail.setVisibility(View.GONE);
        initUploadViews();
        ((TextView) findViewById(R.id.tv_fname)).setText(bp_No_Item.getFirst_name());
        ((TextView) findViewById(R.id.tv_mname)).setText(bp_No_Item.getMiddle_name());
        ((TextView) findViewById(R.id.tv_lname)).setText(bp_No_Item.getLast_name());
        ((TextView) findViewById(R.id.tv_mobile)).setText(bp_No_Item.getMobile_number());
        ((TextView) findViewById(R.id.tv_email)).setText(bp_No_Item.getEmail_id());
        ((TextView) findViewById(R.id.tv_father_name)).setText(bp_No_Item.getFather_name());
        ((TextView) findViewById(R.id.tv_aadhaar)).setText(bp_No_Item.getAadhaar_number());
        ((TextView) findViewById(R.id.tv_city)).setText(bp_No_Item.getCity_region());
        ((TextView) findViewById(R.id.tv_area)).setText(bp_No_Item.getArea());
        ((TextView) findViewById(R.id.tv_society)).setText(bp_No_Item.getSociety());
        ((TextView) findViewById(R.id.tv_landmark)).setText(bp_No_Item.getLandmark());
        ((TextView) findViewById(R.id.tv_house_type)).setText(bp_No_Item.getHouse_type());
        ((TextView) findViewById(R.id.tv_house_no)).setText(bp_No_Item.getHouse_no());
        ((TextView) findViewById(R.id.tv_hblock)).setText(bp_No_Item.getBlock_qtr_tower_wing());
        ((TextView) findViewById(R.id.tv_hfloor)).setText(bp_No_Item.getFloor());
        ((TextView) findViewById(R.id.tv_street)).setText(bp_No_Item.getStreet_gali_road());
        ((TextView) findViewById(R.id.tv_pin)).setText(bp_No_Item.getPincode());
        ((TextView) findViewById(R.id.tv_lpg_company)).setText(bp_No_Item.getLpg_company());
        ((TextView) findViewById(R.id.tv_lpg_dist)).setText(bp_No_Item.getLpg_distributor());
        ((TextView) findViewById(R.id.tv_lpg_cnum)).setText(bp_No_Item.getLpg_conNo());
        ((TextView) findViewById(R.id.tv_uid)).setText(bp_No_Item.getUnique_lpg_Id());
        ((TextView) findViewById(R.id.tv_customer_type)).setText(bp_No_Item.getCustomer_type());
        ((TextView) findViewById(R.id.tv_id_type)).setText(bp_No_Item.getIdproof());
        ((TextView) findViewById(R.id.tv_address_proof_type)).setText(bp_No_Item.getAddressProof());
        owner = findViewById(R.id.owner);
        rents = findViewById(R.id.rents);
        if(bp_No_Item.getProperty_type().equalsIgnoreCase("Rented"))
        {
            rents.setChecked(true);
        }
        else
        {
            owner.setChecked(true);
        }
        ImageView iv_id_proof = findViewById(R.id.iv_id_proof);
        id_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showZoomImageView(Bp_Created_Detail.this, img_id_proof_url);
            }
        });
        ImageView iv_address_proof = findViewById(R.id.iv_address_proof);
        address_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showZoomImageView(Bp_Created_Detail.this, img_address_proof_url);
            }
        });
        ImageView iv_customerSignature = findViewById(R.id.iv_signature);
        customer_sigimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showZoomImageView(Bp_Created_Detail.this, img_customer_signature_url);
            }
        });
        ImageView iv_owner_signature = findViewById(R.id.iv_owner_signature);
        owner_sigimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showZoomImageView(Bp_Created_Detail.this, img_owner_signature_url);
            }
        });
        ImageView iv_cheque = findViewById(R.id.iv_cheque);
        cheque_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showZoomImageView(Bp_Created_Detail.this, img_cheque);
            }
        });


//        if (hasImageData) {
//            lt_resubmit.setVisibility(View.GONE);
//            lt_proof_detail.setVisibility(View.VISIBLE);

        if (hasImageData){
            try {
                if (bp_No_Item.getImageList() != null && bp_No_Item.getImageList().size() > 0) {
                    for (int i = 0; i < bp_No_Item.getImageList().size(); i++) {
                        if (bp_No_Item.getImageList().get(i).contains("address_proof")) {
                            img_address_proof_url = bp_No_Item.getImageList().get(i);
                        } else if (bp_No_Item.getImageList().get(i).contains("id_proof")) {
                            img_id_proof_url = bp_No_Item.getImageList().get(i);

                        } else if (bp_No_Item.getImageList().get(i).contains("customer_signature")) {
                            img_customer_signature_url = bp_No_Item.getImageList().get(i);

                        } else if (bp_No_Item.getImageList().get(i).contains("owner_signature")) {
                            img_owner_signature_url = bp_No_Item.getImageList().get(i);

                        } else if (bp_No_Item.getImageList().get(i).contains("cheque")) {
                            img_cheque = bp_No_Item.getImageList().get(i);

                        }

                    }

                    if (!img_id_proof_url.isEmpty())
//                        Picasso.with(Bp_Created_Detail.this).load(img_id_proof_url).into(id_image);
                    CommonUtils.setImageUsingGLide(Bp_Created_Detail.this,img_id_proof_url,id_image);
                    if (!img_address_proof_url.isEmpty())
//                        Picasso.with(Bp_Created_Detail.this).load(img_address_proof_url).into(address_image);
                    CommonUtils.setImageUsingGLide(Bp_Created_Detail.this,img_address_proof_url,address_image);

                    if (!img_customer_signature_url.isEmpty())
//                        Picasso.with(Bp_Created_Detail.this).load(img_customer_signature_url).into(customer_sigimage);
                    CommonUtils.setImageUsingGLide(Bp_Created_Detail.this,img_customer_signature_url,customer_sigimage);

                    if (!img_owner_signature_url.isEmpty()) {
                        findViewById(R.id.lt_owner_signature).setVisibility(View.VISIBLE);
                        owner_name.setText(bp_No_Item.getOwnerName());
//                        Picasso.with(Bp_Created_Detail.this).load(img_owner_signature_url).into(owner_sigimage);
                        CommonUtils.setImageUsingGLide(Bp_Created_Detail.this,img_owner_signature_url,owner_sigimage);

                    } else {
                        findViewById(R.id.lt_owner_signature).setVisibility(View.GONE);

                    }
                    if (!img_cheque.isEmpty()) {
//                        findViewById(R.id.lt_cheque_image).setVisibility(View.VISIBLE);
//                        Picasso.with(Bp_Created_Detail.this).load(img_cheque).into(cheque_image);
                        CommonUtils.setImageUsingGLide(Bp_Created_Detail.this,img_cheque,cheque_image);

                    } else {
//                        findViewById(R.id.lt_cheque_image).setVisibility(View.GONE);

                    }

                }
            } catch (Exception e) {

            }
        }

//        } else {
//            lt_resubmit.setVisibility(View.VISIBLE);
//            lt_proof_detail.setVisibility(View.GONE);
//            initUploadViews();
//        }


    }


    String address_proof, pdf_path, id_proof;
    boolean isSaleDeedSelected = false;
    Button address_button, id_button;
    TextView tv_pdf_path;
    Bitmap bitmap_id, bitmap_address, bitmap_custmsig, bitmap_ownersig;
    File file_id, file_address, file_cutmsig, file_ownersig;
    String screenshot_id, screenshot_address, screenshot_custsig, screenshot_ownersig, bp_no;
    LinearLayout ll_owner_signature, ll_capture_id, ll_capture_address, ll_capture_custmsig, ll_capture_ownersig;
    ImageView owner_sigimage, id_image, customer_sigimage,address_image, cheque_image;
    String image_path_id, image_path_address, customer_signature_path, owner_signature_path, Type_Of_Owner, image_path_cheque, ownar_name;
    RadioGroup radioGroup;
    EditText owner_name;
    Button btn_upload_cheque;

    private void initUploadViews() {
        cheque_image = findViewById(R.id.iv_cheque_);
        owner_name = findViewById(R.id.owner_name);
        owner_sigimage = findViewById(R.id.owner_signature_image);
        id_image = findViewById(R.id.adhar_image);
        btn_upload_cheque = findViewById(R.id.btn_upload_cheque);
        TextView btn_submit_cheque = findViewById(R.id.btn_submit_cheque);
        btn_upload_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChequeImage();
            }
        });
        customer_sigimage = findViewById(R.id.signature_image);
        id_proof_spinner = findViewById(R.id.id_proof_spinner);
        address_button = findViewById(R.id.address_button);
        address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSaleDeedSelected)
                    selectPdf();
                else
                    selectImage_address();

            }
        });

        Button customer_image_button = findViewById(R.id.customer_image_button);
        customer_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCustomerSignature();
            }
        });
        Button owner_image_button = findViewById(R.id.owner_image_button);
        owner_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOnwerSignature();
            }
        });
        address_image = findViewById(R.id.address_image);
        tv_pdf_path = findViewById(R.id.tv_pdf_path);
        id_button = findViewById(R.id.id_button);
        ll_capture_custmsig = findViewById(R.id.ll_capture_customersig);
        ll_capture_ownersig = findViewById(R.id.ll_capture_ownersig);
        ll_owner_signature = findViewById(R.id.ll_owner_signature);
        ll_capture_address = findViewById(R.id.ll_capture_address);
        ll_capture_id = findViewById(R.id.ll_capture_id);
        id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage_ID();
            }
        });
        Type_Of_Owner = "Owner";
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        int selectedId = radioGroup.getCheckedRadioButtonId();
//        genderradioButton = (RadioButton) findViewById(selectedId);
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
        btn_submit_id_proof = findViewById(R.id.btn_submit_id_proof);
        btn_submit_address_proof = findViewById(R.id.btn_submit_address_proof);
        btn_submit_signature = findViewById(R.id.btn_submit_signature);
        btn_submit_id_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(image_path_id)) {
                    CommonUtils.toast_msg(Bp_Created_Detail.this, "Please Select ID proof");

                } else {
                    takeScreenshot(true);
                    id_proof = id_proof_spinner.getItemAtPosition(id_proof_spinner.getSelectedItemPosition()).toString();
                    uploadMultipart(FOR_ID);
                }


            }
        });
        btn_submit_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(image_path_cheque)) {
                    CommonUtils.toast_msg(Bp_Created_Detail.this, "Please select an image");

                } else {
                    uploadMultipart(FOR_CHEQUE);

                }
            }
        });
        btn_submit_address_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(image_path_address) && TextUtils.isEmpty(pdf_path)) {
                    CommonUtils.toast_msg(Bp_Created_Detail.this, "Please Select Address proof");

                } else {
                    address_proof = address_proof_spinner.getItemAtPosition(address_proof_spinner.getSelectedItemPosition()).toString();
                    if (!isSaleDeedSelected) {
                        takeScreenshot(false);
                    }
                    uploadMultipart(FOR_ADDRESS);
                }

            }
        });
        btn_submit_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(customer_signature_path)) {
                    CommonUtils.toast_msg(Bp_Created_Detail.this, "Please Select Customer signature");
                } else if (Type_Of_Owner.equalsIgnoreCase("Rented") && TextUtils.isEmpty(owner_signature_path)) {
                    CommonUtils.toast_msg(Bp_Created_Detail.this, "Please Select Owner signature");
                } else if (Type_Of_Owner.equalsIgnoreCase("Rented") && TextUtils.isEmpty(owner_name.getText().toString().trim())) {
                    CommonUtils.toast_msg(Bp_Created_Detail.this, "Please Enter Owner Name");
                    owner_name.setError("Owner name mandatory");

                } else {
                    uploadMultipart(FOR_SIGNATURE);
                }
            }
        });

        address_proof_spinner = findViewById(R.id.address_proof_spinner);
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


    }

    private final int PICK_ID_IMAGE_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST_ADDRESS = 3;
    protected static final int CAMERA_ID_REQUEST = 200;
    protected static final int CAMERA_REQUEST_ADDRESS = 201;
    private final int PICK_OWNER_SIGNATUREIMG_REQUEST = 4;
    private final int PICK_CUSTOMER_IMAGE_SIGNATURE = 5;

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
                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(Bp_Created_Detail.this, getApplicationContext().getPackageName() + ".provider", f);
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
                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(Bp_Created_Detail.this, getApplicationContext().getPackageName() + ".provider", f);
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


    private void takeScreenshot(boolean isForId) {

        try {
            if (isForId) {
                bitmap_id = ScreenshotUtils.getScreenShot_ekyc_id(ll_capture_id);
                if (bitmap_id != null) {

                    File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                    file_id = ScreenshotUtils.store(bitmap_id, "id_" + bp_no + ".jpg", saveFile);
                    screenshot_id = file_id.toString();

                }
            } else {
                bitmap_address = ScreenshotUtils.getScreenShot_ekyc_address(ll_capture_address);
                if (bitmap_address != null) {
                    File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                    file_address = ScreenshotUtils.store(bitmap_address, "address_" + bp_no + ".jpg", saveFile);
                    screenshot_address = file_address.toString();
                }
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

        } catch (
                NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void takeScreenshot_Customer() {

        try {
            bitmap_custmsig = ScreenshotUtils.getScreenShot_ekyc_custsig(ll_capture_custmsig);
            File saveFile = ScreenshotUtils.getMainDirectoryName(this);
            file_cutmsig = ScreenshotUtils.store(bitmap_custmsig, "custsig_" + bp_no + ".jpg", saveFile);

            screenshot_custsig = file_cutmsig.toString();

        } catch (
                NullPointerException e) {
            e.printStackTrace();
        }

    }

    private Uri filePath_aadhaar, filePathUri_cheque, filePath_address, filePath_owner, filePath_customer;

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
            case PICK_IMAGE_REQUEST_CHEQUE:
                if (requestCode == PICK_IMAGE_REQUEST_CHEQUE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathUri_cheque = data.getData();
                    try {
                        Bitmap bitmap_cheque = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathUri_cheque);
                        cheque_image.setImageBitmap(bitmap_cheque);
                        image_path_cheque = getPath(filePathUri_cheque);
                        Log.d("bpcreation", "imagepath cheque pick image = " + image_path_cheque);
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
                        Bitmap bitmap_cheque = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        bitmap_cheque = getResizedBitmap(bitmap_cheque, 500);
                        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_cheque = file.toString();
                        Log.d("bpcreation", "imagepath id camera image = " + image_path_cheque);
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap_cheque.compress(Bitmap.CompressFormat.JPEG, 95, outFile);
                            cheque_image.setImageBitmap(bitmap_cheque);
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
            case CAMERA_ID_REQUEST:
                if (resultCode == RESULT_OK && requestCode == CAMERA_ID_REQUEST) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
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
                        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
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
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_address);
                        // imageView.setImageBitmap(bitmap);
                        address_image.setImageBitmap(bitmap);
                        image_path_address = getPath1(filePath_address);
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
                        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        address_image.setImageBitmap(bitmap);
                        //BitMapToString(bitmap);
                        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
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

    SignatureView customer_signature_view, owner_signature_view;
    Button clear, save;

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

    private static final String IMAGE_DIRECTORY = "/signdemo";

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                getFilesDir() + IMAGE_DIRECTORY /*iDyme folder*/);
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

    private void selectPdf() {
        Intent intent4 = new Intent(this, NormalFilePickActivity.class);
        intent4.putExtra(Constant.MAX_NUMBER, 1);
        intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"pdf"});
        startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);


    }

    private static final String FOR_ID = "for_id", FOR_ADDRESS = "for_address", FOR_SIGNATURE = "for_signature", FOR_CHEQUE = "for_cheque";
    MaterialDialog materialDialog;
    private final int PICK_IMAGE_REQUEST_CHEQUE = 6, CAMERA_REQUEST_CHEQUE = 601;

    public void uploadMultipart(String isFor) {

        try {
            materialDialog = new MaterialDialog.Builder(Bp_Created_Detail.this)
                    .content("Please wait....")
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            String uploadId = UUID.randomUUID().toString();
            Log.e("uploadId+,,,,,,,,,,", "" + uploadId);
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(Bp_Created_Detail.this, uploadId, Constants.BP_Images + "/" + bp_No_Item.getBp_number());
            if (isFor.equals(FOR_CHEQUE)) {
                if (image_path_cheque != null && !image_path_cheque.isEmpty()) {
                    multipartUploadRequest.addFileToUpload(image_path_cheque, "image", "cheque.jpg");

                }
            }
            if (isFor.equals(FOR_ID)) {
                multipartUploadRequest.addFileToUpload(screenshot_id, "image", "id_proof.jpg");
            }
            if (isFor.equals(FOR_ADDRESS)) {
                if (isSaleDeedSelected) {
                    multipartUploadRequest.addFileToUpload(pdf_path, "image", "sale_deed.pdf", ContentType.APPLICATION_PDF);

                } else {
                    multipartUploadRequest.addFileToUpload(screenshot_address, "image", "address_proof.jpg");
                }
            }
            if (isFor.equals(FOR_SIGNATURE)) {
                if (owner_signature_path == null) {
                    owner_signature_path = customer_signature_path;
                }
                multipartUploadRequest.addFileToUpload(customer_signature_path, "image", "customer_signature.jpg");
                multipartUploadRequest.addFileToUpload(owner_signature_path, "image", "owner_signature.jpg");
            }

            multipartUploadRequest.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(Context context, UploadInfo uploadInfo) {
                    Log.e("UplodeINFO++", uploadInfo.getSuccessfullyUploadedFiles().toString());
                }

                @Override
                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                    exception.printStackTrace();
                    materialDialog.dismiss();
//                    imgUploadError = true;
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
                        if (jsonObject.getInt("Code") == 200) {
                            if (jsonObject.has("Message")) {
                                String Msg = jsonObject.getString("Message");
                                Toast.makeText(Bp_Created_Detail.this, Msg, Toast.LENGTH_SHORT).show();
                            }
//                            imgUploadError = false;
//                            BP_N0_DilogBox();
                        } else {
//                            imgUploadError = true;
                            if (jsonObject.has("Message")) {
                                String Msg = jsonObject.getString("Message");
                                Toast.makeText(Bp_Created_Detail.this, Msg, Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (JSONException e) {
//                        imgUploadError = true;
                        Toast.makeText(Bp_Created_Detail.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(Context context, UploadInfo uploadInfo) {
//                    imgUploadError = true;
                    materialDialog.dismiss();

                }
            });
            multipartUploadRequest.setUtf8Charset();
            multipartUploadRequest.setAutoDeleteFilesAfterSuccessfulUpload(true);
            multipartUploadRequest.setMaxRetries(5);
            multipartUploadRequest.setUsesFixedLengthStreamingMode(true);
            multipartUploadRequest.setUsesFixedLengthStreamingMode(true);
            multipartUploadRequest.startUpload(); //Starting the upload


        } catch (Exception exc) {
//            imgUploadError = true;
            Toast.makeText(Bp_Created_Detail.this, "Please Select ID & Address Proof and Proper Signature", Toast.LENGTH_SHORT).show();
            materialDialog.dismiss();
        }
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
                        Uri photoURI = FileProvider.getUriForFile(Bp_Created_Detail.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_CHEQUE);
                    }
                });
        myAlertDialog.show();
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