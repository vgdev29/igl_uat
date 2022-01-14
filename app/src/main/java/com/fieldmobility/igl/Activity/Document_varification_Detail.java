package com.fieldmobility.igl.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;
import com.kyanogen.signatureview.SignatureView;

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

import static android.os.Environment.getExternalStorageDirectory;

public class Document_varification_Detail extends Activity {
    SharedPrefs sharedPrefs;
    ImageView back;
    MaterialDialog materialDialog;
    Spinner spinner1, spinner2;
    private Uri filePath_aadhaar, filePath_address, filePath_customer;
    String image_path_id, image_path_address;

    String id_proof, address_proof;
    Bitmap bitmap, bitmap1;
    Button clear, save;
    SignatureView signatureView;
    String customer_signature_path;
    private final int PICK_IMAGE_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST_ADDRESS = 3;
    private final int PICK_OWNER_IMAGE_REQUEST_ADDRESS = 4;
    private final int PICK_CUSTOMER_IMAGE_SIGNATURE = 5;
    protected static final int CAMERA_REQUEST = 200;
    protected static final int CAMERA_REQUEST_ADDRESS = 201;
    protected static final int CAMERA_OWNER_REQUEST_ADDRESS = 202;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    ImageView adhar_image, address_image, signature_image;
    Button signature_button, adhar_button, address_button, approve_button;
    ImageView adhar_owner_image;
    EditText ownar_name_no;
    String Address;
    TextView first_name, middle_name, bp_no_text, last_name, mobile_no_txt, email_id_txt, aadhaar_number_txt, customer_type_txt, lpg_company_text, address_text, lpg_distributor_text, lpg_conNo_text, unique_lpg_Id_text, ownerName_text, chequeNo_text, chequeDate_text, drawnOn_text, amount_text, igl_code_group_text, lead_no_text;
    Bp_No_Item bp_No_Item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_varification_detail);
        sharedPrefs = new SharedPrefs(this);
        String dataJson = getIntent().getStringExtra("data");
        if (dataJson != null && !dataJson.isEmpty()) {
            bp_No_Item = new Gson().fromJson(dataJson, Bp_No_Item.class);
            Layout_Id();
        }

    }

    private void Layout_Id() {
        back = findViewById(R.id.back);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        first_name = findViewById(R.id.first_name);
        middle_name = findViewById(R.id.middle_name);
        last_name = findViewById(R.id.last_name);
        mobile_no_txt = findViewById(R.id.mobile_no_txt);
        email_id_txt = findViewById(R.id.email_id_txt);
        aadhaar_number_txt = findViewById(R.id.aadhaar_number_txt);
        customer_type_txt = findViewById(R.id.customer_type_txt);
        lpg_company_text = findViewById(R.id.lpg_company_text);
        lpg_distributor_text = findViewById(R.id.lpg_distributor_text);
        lpg_conNo_text = findViewById(R.id.lpg_conNo_text);
        unique_lpg_Id_text = findViewById(R.id.unique_lpg_Id_text);
        ownerName_text = findViewById(R.id.ownerName_text);
        chequeNo_text = findViewById(R.id.chequeNo_text);
        chequeDate_text = findViewById(R.id.customer_type_txt);
        drawnOn_text = findViewById(R.id.drawnOn_text);
        amount_text = findViewById(R.id.amount_text);
        igl_code_group_text = findViewById(R.id.igl_code_group_text);
        lead_no_text = findViewById(R.id.lead_no_text);
        bp_no_text = findViewById(R.id.bp_no_text);
        address_text = findViewById(R.id.address_txt);

        adhar_image = findViewById(R.id.adhar_image);
        address_image = findViewById(R.id.address_image);
        signature_image = findViewById(R.id.signature_image);
        signature_button = findViewById(R.id.signature_button);
        adhar_button = findViewById(R.id.adhar_button);
        address_button = findViewById(R.id.address_button);
        approve_button = findViewById(R.id.approve_button);


        first_name.setText(bp_No_Item.getFirst_name());
        middle_name.setText(bp_No_Item.getMiddle_name());
        last_name.setText(bp_No_Item.getLast_name());
        mobile_no_txt.setText(bp_No_Item.getMobile_number());
        email_id_txt.setText(bp_No_Item.getEmail_id());
        aadhaar_number_txt.setText(bp_No_Item.getAadhaar_number());
        customer_type_txt.setText(bp_No_Item.getCustomer_type());
        lpg_company_text.setText(bp_No_Item.getLpg_company());
        lpg_distributor_text.setText(bp_No_Item.getLpg_distributor());
        lpg_conNo_text.setText(bp_No_Item.getLpg_conNo());
        unique_lpg_Id_text.setText(bp_No_Item.getUnique_lpg_Id());
        ownerName_text.setText(bp_No_Item.getOwnerName());


        chequeNo_text.setText(bp_No_Item.getChequeNo());
        chequeDate_text.setText(bp_No_Item.getChequeDate());
        drawnOn_text.setText(bp_No_Item.getDrawnOn());
        amount_text.setText(bp_No_Item.getAmount());
        igl_code_group_text.setText(bp_No_Item.getIgl_code_group());
        lead_no_text.setText(bp_No_Item.getLead_no());
        OnClick();
    }

    private void OnClick() {
        Address = bp_No_Item.getHouse_no() + " " + bp_No_Item.getHouse_type() + " " +
                bp_No_Item.getLandmark() + " " + bp_No_Item.getSociety() + " " + bp_No_Item.getArea() + " "
                + bp_No_Item.getCity_region();

        address_text.setText(Address);
        bp_no_text.setText(bp_No_Item.getBp_number());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        List<String> IdProofOptionList = new ArrayList<String>();
        IdProofOptionList.add("AADHAAR CARD");
        IdProofOptionList.add("DRIVING LICENCE");
        IdProofOptionList.add("ANY OTHER");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, IdProofOptionList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
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
        approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){
                    uploadMultipart();
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
                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(Document_varification_Detail.this, getApplicationContext().getPackageName() + ".provider", f);
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
                        File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(Document_varification_Detail.this, getApplicationContext().getPackageName() + ".provider", f);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_aadhaar = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_aadhaar);
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
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
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
                        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
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
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
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
                        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
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
                getFilesDir() + IMAGE_DIRECTORY /*iDyme folder*/);
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

    private boolean validateData() {
        boolean isDataValid = true;

        if (image_path_id == null) {
            isDataValid = false;
            Toast.makeText(Document_varification_Detail.this,"Please Select Image ID Proof",Toast.LENGTH_SHORT).show();
        }
        else if (image_path_address== null) {
            isDataValid = false;
            Toast.makeText(Document_varification_Detail.this,"Please Select Image Address Proof",Toast.LENGTH_SHORT).show();
        }
        else if (customer_signature_path== null) {
            isDataValid = false;
            Toast.makeText(Document_varification_Detail.this,"Please Select Customer Signature",Toast.LENGTH_SHORT).show();
        }

        return isDataValid;
    }



}
