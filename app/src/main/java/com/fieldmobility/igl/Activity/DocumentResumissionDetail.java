package com.fieldmobility.igl.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;
import com.kyanogen.signatureview.SignatureView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;

public class DocumentResumissionDetail extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    private Uri filePath_aadhaar, filePath_address, filePath_customer;
    Bitmap bitmap;
    CheckBox checkBox_term_cond;
    TextView checkbox_text;
    LinearLayout todo_creation;
    String id_proof;
    EditText descreption_edit;
    Spinner spinner1, spinner2;
    Bp_No_Item bp_No_Item;
    TextView name_txt, date_txt, bp_no_text, email_id_txt, mobile_no_txt, created_by_person_txt, lead_no_txt, ca_no_txt, tpi_status_txt, tpi_date_txt, resedential_type_txt, address_text;
    SignatureView signatureView;
    String customer_signature_path,image_path_id, image_path_address,address_proof;
    Button signature_button, adhar_button, address_button, approve_button;
    ImageView adhar_image, address_image, signature_image,adhar_owner_image;

    private final int PICK_IMAGE_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST_ADDRESS = 3;
    private final int PICK_OWNER_IMAGE_REQUEST_ADDRESS = 4;
    private final int PICK_CUSTOMER_IMAGE_SIGNATURE = 5;
    protected static final int CAMERA_REQUEST = 200;
    protected static final int CAMERA_REQUEST_ADDRESS = 201;
    protected static final int CAMERA_OWNER_REQUEST_ADDRESS = 202;
    private static final String IMAGE_DIRECTORY = "/signdemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_resubmission_detail);
        String dataJson = getIntent().getStringExtra("data");

findViews();
        if (dataJson!=null && !dataJson.isEmpty()){
            bp_No_Item = new Gson().fromJson(dataJson, Bp_No_Item.class);
            initViews();

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
    EditText tv_fname,tv_mname,tv_lname,tv_mobile,tv_email,tv_aadhaar,tv_landmark,tv_house_no,tv_hfloor,tv_pin,tv_lpg_dist,tv_lpg_cnum,tv_uid;
  private void  findViews(){
      checkBox_term_cond=findViewById(R.id.checkbox);
      checkbox_text=findViewById(R.id.checkbox_text);
      tv_fname=findViewById(R.id.tv_fname);
      tv_mname=findViewById(R.id.tv_mname);
      tv_lname=findViewById(R.id.tv_lname);
      tv_mobile=findViewById(R.id.tv_mobile);
      tv_email=findViewById(R.id.tv_email);
      tv_aadhaar=findViewById(R.id.tv_aadhaar);
      tv_landmark=findViewById(R.id.tv_landmark);
      tv_house_no=findViewById(R.id.tv_house_no);
      tv_hfloor=findViewById(R.id.tv_hfloor);
      tv_pin=findViewById(R.id.tv_pin);
      tv_lpg_dist=findViewById(R.id.tv_lpg_dist);
      tv_lpg_cnum=findViewById(R.id.tv_lpg_cnum);
      tv_uid=findViewById(R.id.tv_uid);

      adhar_image = findViewById(R.id.adhar_image);
      address_image = findViewById(R.id.address_image);
      signature_image = findViewById(R.id.signature_image);
      signature_button = findViewById(R.id.signature_button);
      adhar_button = findViewById(R.id.adhar_button);
      address_button = findViewById(R.id.address_button);
      approve_button = findViewById(R.id.approve_button);
      spinner1 = (Spinner) findViewById(R.id.spinner1);
      spinner2 = (Spinner) findViewById(R.id.spinner2);
  }

    private void initViews() {


        tv_fname.setText(bp_No_Item.getFirst_name());
        tv_mname.setText(bp_No_Item.getMiddle_name());
        tv_lname.setText(bp_No_Item.getLast_name());
        tv_mobile.setText(bp_No_Item.getMobile_number());
        tv_email.setText(bp_No_Item.getEmail_id());
        tv_aadhaar.setText(bp_No_Item.getAadhaar_number());
        ((TextView)findViewById(R.id.tv_city)).setText(bp_No_Item.getCity_region());
        ((TextView)findViewById(R.id.tv_area)).setText(bp_No_Item.getArea());
        ((TextView)findViewById(R.id.tv_society)).setText(bp_No_Item.getSociety());
        tv_landmark.setText(bp_No_Item.getLandmark());
        ((TextView)findViewById(R.id.tv_house_type)).setText(bp_No_Item.getHouse_type());
        tv_house_no.setText(bp_No_Item.getHouse_no());
        ((TextView)findViewById(R.id.tv_hblock)).setText(bp_No_Item.getBlock_qtr_tower_wing());
        tv_hfloor.setText(bp_No_Item.getFloor());
        ((TextView)findViewById(R.id.tv_street)).setText(bp_No_Item.getStreet_gali_road());
        tv_pin.setText(bp_No_Item.getPincode());
        ((TextView)findViewById(R.id.tv_lpg_company)).setText(bp_No_Item.getLpg_company());
        tv_lpg_dist.setText(bp_No_Item.getLpg_distributor());
        tv_lpg_cnum.setText(bp_No_Item.getLpg_conNo());
        tv_uid.setText(bp_No_Item.getUnique_lpg_Id());
        ((TextView)findViewById(R.id.tv_customer_type)).setText(bp_No_Item.getCustomer_type());

        List<String> IdProofOptionList = new ArrayList<String>();
        IdProofOptionList.add("AADHAAR CARD");
        IdProofOptionList.add("DRIVING LICENCE");
        IdProofOptionList.add("ANY OTHER");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, IdProofOptionList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
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

        List<String> addressOptionList = new ArrayList<String>();
        addressOptionList.add("ELECTRICITY BILL");
        addressOptionList.add("WATER BILL");
        addressOptionList.add("SALE DEEP");
        addressOptionList.add("HOUSE TAX RECEIPT");
        addressOptionList.add("ALLOTMENT LETTER");
        addressOptionList.add("ANY OTHER");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, addressOptionList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter1);

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
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (permissionGranted) {

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
        String first = "I agree to the ";
        String second = "<font color='#EE0000'> Terms and conditions</font>";
        String third = " of PNG registration.";

        checkbox_text.setText(Html.fromHtml(first + second+third));
        checkbox_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DocumentResumissionDetail.this,WebView_Activity.class);
                startActivity(intent);

            }
        });
        checkBox_term_cond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checkBox_term_cond.setChecked(true);
                    Toast.makeText(DocumentResumissionDetail.this, "You checked the checkbox!", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkBox_term_cond.setChecked(false);
                    Toast.makeText(DocumentResumissionDetail.this, "You unchecked the checkbox!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_term_cond.isChecked()) {

                    if (validateData()) {
                        uploadMultipart();
                    }
                }
                else {
                    Toast.makeText(DocumentResumissionDetail.this, "Please tick to accept Tearm & Condition", Toast.LENGTH_SHORT).show();
                }
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
                        Uri photoURI = FileProvider.getUriForFile(DocumentResumissionDetail.this, getApplicationContext().getPackageName() + ".provider", f);
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
                        Uri photoURI = FileProvider.getUriForFile(DocumentResumissionDetail.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_ADDRESS);
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
      Button  save = (Button) dialog.findViewById(R.id.save_button);

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
    private boolean validateData() {
        boolean isDataValid = true;
        if (tv_fname.getText().length() == 0) {
            isDataValid = false;
            tv_fname.setError("Enter First Name");
            Toast.makeText(DocumentResumissionDetail.this, "Enter First Name", Toast.LENGTH_SHORT).show();
        } else if (tv_lname.getText().length() == 0) {
            isDataValid = false;
            tv_lname.setError("Enter Last Name");
            Toast.makeText(DocumentResumissionDetail.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
        } else if (tv_mobile.getText().length() == 0 || tv_mobile.getText().length() > 10) {
            isDataValid = false;
            tv_mobile.setError("Enter Mobile No");
            Toast.makeText(DocumentResumissionDetail.this, "Enter Valid Mobile no.", Toast.LENGTH_SHORT).show();
        }
        else if (tv_house_no.getText().length() == 0) {
            isDataValid = false;
            tv_house_no.setError("Enter House No");
            Toast.makeText(DocumentResumissionDetail.this, "Enter House no", Toast.LENGTH_SHORT).show();
            //Toast.makeText(New_Regestration_Form.this,"Enter Society",Toast.LENGTH_SHORT).show();
        } else if (tv_hfloor.getText().length() == 0) {
            isDataValid = false;
            Toast.makeText(DocumentResumissionDetail.this, "Enter Floor", Toast.LENGTH_SHORT).show();
        } else if (tv_pin.getText().length() == 0) {
            isDataValid = false;
            tv_pin.setError("Enter PinCode");
            Toast.makeText(DocumentResumissionDetail.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
        }
        if (image_path_id == null) {
            isDataValid = false;
            Toast.makeText(DocumentResumissionDetail.this,"Please Select Image ID Proof",Toast.LENGTH_SHORT).show();
        }
        else if (image_path_address== null) {
            isDataValid = false;
            Toast.makeText(DocumentResumissionDetail.this,"Please Select Image Address Proof",Toast.LENGTH_SHORT).show();
        }
        else if (customer_signature_path== null) {
            isDataValid = false;
            Toast.makeText(DocumentResumissionDetail.this,"Please Select Customer Signature",Toast.LENGTH_SHORT).show();
        }

        return isDataValid;
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
        EditText ownar_name_no = dialog.findViewById(R.id.ownar_name_no);
        ownar_name_no.setVisibility(View.GONE);
        signatureView = (SignatureView) dialog.findViewById(R.id.ownar_signature_view);
        Button    clear =  dialog.findViewById(R.id.clear);
        Button   save =  dialog.findViewById(R.id.save);
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
                customer_signature_path = saveImage(bitmap);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_aadhaar = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_aadhaar);
                        bitmap=getResizedBitmap(bitmap,500);

                        // imageView.setImageBitmap(bitmap);
                        adhar_image.setImageBitmap(bitmap);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_id = getPath(filePath_aadhaar);

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_id);
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
                        bitmap=getResizedBitmap(bitmap,500);

                        adhar_image.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_id = file.toString();
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
                if (requestCode == PICK_IMAGE_REQUEST_ADDRESS && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_address = data.getData();
                    Log.e("Camera_Pathaddress++", filePath_address.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_address);
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
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        bitmap=getResizedBitmap(bitmap,500);

                        address_image.setImageBitmap(bitmap);
                        //BitMapToString(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_address = file.toString();
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


            case PICK_CUSTOMER_IMAGE_SIGNATURE:
                if (requestCode == PICK_CUSTOMER_IMAGE_SIGNATURE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_customer = data.getData();
                    Log.e("filePath_customer", filePath_customer.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_customer);
                        bitmap=getResizedBitmap(bitmap,500);

                        // imageView.setImageBitmap(bitmap);
                        signature_image.setImageBitmap(bitmap);
                        adhar_owner_image.setImageBitmap(bitmap);
                        customer_signature_path = getPath1(filePath_customer);
                        Log.e("owner_image_select+", "" + customer_signature_path);
                        // new ImageCompressionAsyncTask1(this).execute(image_path_address, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    public void uploadMultipart() {

//        try {
//            materialDialog = new MaterialDialog.Builder(Document_varification_Detail.this)
//                    .content("Please wait....")
//                    .progress(true, 0)
//                    .cancelable(false)
//                    .show();
//            String uploadId = UUID.randomUUID().toString();
//            Log.e("uploadId+,,,,,,,,,,", "" + Constants.Document_POST + getIntent().getStringExtra("Bp_number"));
//            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(Document_varification_Detail.this, uploadId, Constants.Document_POST + getIntent().getStringExtra("Bp_number"));
//            multipartUploadRequest.addFileToUpload(image_path_id, "image");
//            multipartUploadRequest.addFileToUpload(image_path_address, "image");
//            multipartUploadRequest.addFileToUpload(customer_signature_path, "image");
//            multipartUploadRequest.addParameter("adressProof", address_proof);
//            multipartUploadRequest.addParameter("idProof", id_proof);
//            multipartUploadRequest.setDelegate(new UploadStatusDelegate() {
//                @Override
//                public void onProgress(Context context, UploadInfo uploadInfo) {
//                    Log.e("UplodeINFO++", uploadInfo.getSuccessfullyUploadedFiles().toString());
//                }
//
//                @Override
//                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
//                    exception.printStackTrace();
//                    materialDialog.dismiss();
//                    Log.e("Uplodeerror++", uploadInfo.getSuccessfullyUploadedFiles().toString());
//                }
//
//                @Override
//                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
//                    materialDialog.dismiss();
//                    String str = serverResponse.getBodyAsString();
//                    final JSONObject jsonObject;
//                    try {
//                        jsonObject = new JSONObject(str);
//                        Log.e("Response++", jsonObject.toString());
//                        // String Msg=jsonObject.getString("Message");
//                        // Toast.makeText(New_Regestration_Form.this, Msg, Toast.LENGTH_SHORT).show();
//                        finish();
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onCancelled(Context context, UploadInfo uploadInfo) {
//                    materialDialog.dismiss();
//                }
//            });
//            multipartUploadRequest.setUtf8Charset();
//            multipartUploadRequest.setAutoDeleteFilesAfterSuccessfulUpload(true);
//            multipartUploadRequest.setMaxRetries(5);
//            multipartUploadRequest.setUsesFixedLengthStreamingMode(true);
//            multipartUploadRequest.setUsesFixedLengthStreamingMode(true);
//            multipartUploadRequest.startUpload(); //Starting the upload
//
//            Log.e("aadhaar_file", image_path_id);
//            Log.e("pan_file", image_path_address);
//            Log.e("sign_file", customer_signature_path);
//        } catch (Exception exc) {
//            // Toast.makeText(New_Regestration_Form.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(Document_varification_Detail.this, "Please Select ID & Address Proof and Proper Signature", Toast.LENGTH_SHORT).show();
//            materialDialog.dismiss();
//        }
    }

}