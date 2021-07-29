package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.fieldmobility.igl.rest.Api;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Environment.getExternalStorageDirectory;

public class NgSupDoneActivity extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST_HOME_ADDRESS = 1;
    private final int PICK_IMAGE_REQUEST_METR_PHOTO = 2;
    private final int PICK_IMAGE_REQUEST_INSTALLATION = 3;
    private final int PICK_IMAGE_REQUEST_SERVICE_CARD = 4;
    private final int PICK_CUSTOMER_IMAGE_SIGNATURE = 5;
    protected static final int CAMERA_REQUEST_HOME_ADDRESS = 200;
    protected static final int CAMERA_REQUEST_METER_PHOTO = 201;
    protected static final int CAMERA_REQUEST_INSTALLATION = 202;
    protected static final int CAMERA_REQUEST_SERVICE_CARD = 203;
    private static final String IMAGE_DIRECTORY = "/signdemo";

    private Button btn_home_address, btn_meterPhoto, btn_installation, btn_serviceCard;
    private ImageView iv_homeAddress, iv_meterPhoto, iv_installation, iv_serviceCard;

    String image_path_HomeAddress, image_path_meterPhoto, image_path_installation, image_path_service_card,image_path_signature;
    private TextView tv_startWorkValues, tv_assignedTimeValues, tv_jmrValue, tv_bpValue, tv_initialreadingValue, tv_burnerdetailsValue;

    private Uri filePathHomeAddress, filePathMeterPhoto, filePathInstallation, filePathServiceCard , filePathSignature;
    private Bitmap mBitmapHome, mBitmapmeterPhoto, mBitmapInstallation, mBitmapServiceCard,mBitmapSignature;
    //private RadioGroup radioGroup;
    private String Type_Of_Status;
    //RadioButton genderradioButton;
    private Button submit_button;
    ImageView back_button;
    private String homeAddress_pic_binary, meter_pic_binary, installation_pic_binary, serviceCard_pic_binary, signatureBinary;
    private LinearLayout ll_meterReading;
    private NguserListModel nguserListModel;
    private String jmrNo, assignDate,newMob,bpno;
    private String initialReading, burnerDetails, conversationDate;
    private Button btn_viewJmrForm, signature_button;
    private ImageView signature_image;
    private int responseCode;
    private MaterialDialog materialDialog;
    String log = "nguser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng_assignment_details);
        back_button = findViewById(R.id.back);
        tv_bpValue = findViewById(R.id.tv_bpValues);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getIntent() != null) {
            bpno = getIntent().getStringExtra("bpno");
            jmrNo = getIntent().getStringExtra("jmrNo");
            assignDate = getIntent().getStringExtra("mAssignDate");
            initialReading = getIntent().getStringExtra("initialReading");
            burnerDetails = getIntent().getStringExtra("burnerDetails");
            conversationDate = getIntent().getStringExtra("conversationDate");
            newMob = getIntent().getStringExtra("mobile");
            Log.d(log,"date ng update = "+conversationDate);
            nguserListModel = new NguserListModel();
            nguserListModel.setInitial_reading(initialReading);
            nguserListModel.setBurner_details(burnerDetails);
            nguserListModel.setNg_update_date(conversationDate);
            nguserListModel.setJmr_no(jmrNo);
            nguserListModel.setMobile_no(newMob);
            nguserListModel.setCat_id(getIntent().getStringExtra("catid"));
            nguserListModel.setCatalog(getIntent().getStringExtra("catalog"));
            nguserListModel.setCode(getIntent().getStringExtra("code"));
            nguserListModel.setReason(getIntent().getStringExtra("reason"));
            nguserListModel.setSub_status(getIntent().getStringExtra("substatus"));

        }



        mFindViewById();
        clickEvent();
        setText();

    }

    private void mFindViewById() {
        btn_home_address = findViewById(R.id.home_address_btn);
        btn_meterPhoto = findViewById(R.id.btn_meterPhoto);
        btn_installation = findViewById(R.id.btn_installation);
        btn_serviceCard = findViewById(R.id.btn_serviceCard);
        iv_homeAddress = findViewById(R.id.iv_homeAddress);
        iv_meterPhoto = findViewById(R.id.iv_meterPhoto);
        iv_installation = findViewById(R.id.iv_installation);
        iv_serviceCard = findViewById(R.id.iv_serviceCard);
        submit_button = findViewById(R.id.submit_button);
        ll_meterReading = findViewById(R.id.ll_meterReading);
        btn_viewJmrForm = findViewById(R.id.btn_viewJmrForm);
        tv_startWorkValues = findViewById(R.id.tv_startWorkValues);
        tv_assignedTimeValues = findViewById(R.id.tv_assignedTimeValues);
        signature_button = findViewById(R.id.signature_button);
        signature_image = findViewById(R.id.signature_image);

        tv_jmrValue = findViewById(R.id.tv_jmrValues);
        tv_initialreadingValue = findViewById(R.id.tv_initialReadingValues);
        tv_burnerdetailsValue = findViewById(R.id.tv_burnerValues);
        tv_jmrValue.setText(jmrNo);
        tv_bpValue.setText(bpno);
        tv_initialreadingValue.setText(initialReading);
        tv_burnerdetailsValue.setText(burnerDetails);


    }

    private void setText() {
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        tv_startWorkValues.setText(currentDateTimeString);
        tv_assignedTimeValues.setText(assignDate);

    }

    private void clickEvent() {
        btn_home_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = btn_home_address.getText().toString().trim();
                //selectImage(text);
                selectHomeAddressImage();
            }
        });
        btn_meterPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = btn_meterPhoto.getText().toString().trim();
                //selectImage(text);
                selectMeterImage();
            }
        });
        btn_installation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = btn_installation.getText().toString().trim();
                selectInstallationImage();
            }
        });
        btn_serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = btn_serviceCard.getText().toString().trim();
                selectServiceCardImage();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData())
                    submitData(nguserListModel);
            }
        });


        btn_viewJmrForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(jmrNo)) {
                    String url = "http://49.50.68.239:8080/api/jmr/pdfprint/" + jmrNo + "/customer";
                    Intent intent = new Intent(NgSupDoneActivity.this, CustomPdfViewActivity.class);
                    intent.putExtra("DownloadUrl", url);
                    startActivity(intent);
                }
            }
        });
        signature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Signature_Method();
               User_Signature_Image();
            }
        });
    }

    public boolean validateData() {
        boolean allOk = true;
        if (TextUtils.isEmpty(jmrNo)) {
            allOk = false;
            Utils.showToast(this, "JMR No is empty");
            return allOk;
        } else if (TextUtils.isEmpty(initialReading)) {
            allOk = false;
            Utils.showToast(this, "Initial reading is empty");
            return allOk;
        } else if (TextUtils.isEmpty(burnerDetails)) {
            allOk = false;
            Utils.showToast(this, "BurnerDetails is empty");
            return allOk;
        } else if (TextUtils.isEmpty(conversationDate)) {
            allOk = false;
            Utils.showToast(this, "Conversion Date is empty");
            return allOk;
        } else if (TextUtils.isEmpty(homeAddress_pic_binary)) {
            allOk = false;
            Utils.showToast(this, "Pls Select HomeAdress Image");
            return allOk;
        } else if (TextUtils.isEmpty(meter_pic_binary)) {
            allOk = false;
            Utils.showToast(this, "Pls Select Meter Image");
            return allOk;
        } else if (TextUtils.isEmpty(installation_pic_binary)) {
            allOk = false;
            Utils.showToast(this, "Pls Select Installation Image");
            return allOk;
        } else if (TextUtils.isEmpty(serviceCard_pic_binary)) {
            allOk = false;
            Utils.showToast(this, "Pls Select Service Card Image");
            return allOk;
        } else if (TextUtils.isEmpty(signatureBinary)) {
            allOk = false;
            Utils.showToast(this, "Signature is Missing");
            return allOk;
        } else {
            return allOk;
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
        ImageView adhar_owner_image = dialog.findViewById(R.id.adhar_owner_image);
        Button adhar_button = dialog.findViewById(R.id.adhar_button);
        TextView signature_select = dialog.findViewById(R.id.signature_select);
        TextView image_select = dialog.findViewById(R.id.image_select);
        TextView nocash = dialog.findViewById(R.id.nopayment_sig);
        nocash.setVisibility(View.VISIBLE);
        image_select.setVisibility(View.GONE);
        signature_select.setVisibility(View.GONE);

        TextView save_select = dialog.findViewById(R.id.save_select);
        final LinearLayout signature_layout = dialog.findViewById(R.id.signature_layout);
        final LinearLayout image_capture_layout = dialog.findViewById(R.id.image_capture_layout);
        signature_layout.setVisibility(View.VISIBLE);
        image_capture_layout.setVisibility(View.GONE);
        ImageView crose_img = dialog.findViewById(R.id.crose_img);
        EditText ownar_name_no = dialog.findViewById(R.id.ownar_name_no);
        ownar_name_no.setVisibility(View.GONE);
        //final ImageView signature_image = dialog.findViewById(R.id.signature_image);
        final SignatureView signatureView = (SignatureView) dialog.findViewById(R.id.ownar_signature_view);
        Button clear = (Button) dialog.findViewById(R.id.clear);
        Button save = (Button) dialog.findViewById(R.id.save);
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
                Bitmap signatureBitmap = signatureView.getSignatureBitmap();
                String customer_image_select = saveImage(signatureBitmap);
                if (signatureBitmap != null) {
                    signatureBinary = change_to_binary(signatureBitmap);
                    nguserListModel.setCustomer_sign(signatureBinary);
                    signature_image.setImageBitmap(signatureBitmap);
                }

                dialog.dismiss();
            }
        });
        signature_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        myAlertDialog.setNegativeButton("Signature",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Customer_Signature1();
                    }
                });

        myAlertDialog.show();
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

    private void submitData(NguserListModel nguserListModel) {
        //  Log.d(log, "hom adress = " + homeAddress_pic_binary);
        nguserListModel.setHome_address(homeAddress_pic_binary);
        nguserListModel.setMeter_photo(meter_pic_binary);
        nguserListModel.setInstallation_photo(installation_pic_binary);
        nguserListModel.setService_photo(serviceCard_pic_binary);
        nguserListModel.setCustomer_sign(signatureBinary);
        nguserListModel.setStatus("DP");

        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getPostPhoto(jmrNo, nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {
            @Override
            public void onResponse(Call<List<NguserListModel>> call, Response<List<NguserListModel>> response) {
                responseCode = response.code();
                Log.d(log, "resonse = " + response.toString());
                materialDialog.dismiss();
                if (response.body() != null) {
                    if (responseCode == 200) {

                        Log.d(log, "Mysucess>>>>>>>>>>" + "weldone............");

                        Toast.makeText(getApplicationContext(), "Data submitted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NgSupDoneActivity.this, NgSupListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                } else {
                    if (responseCode == 400) {
                        materialDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to submit please try again", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                materialDialog.dismiss();
                Log.e(log, "My error" + "error comes" + t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "Failed to submit please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void selectHomeAddressImage() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_HOME_ADDRESS);


                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(NgSupDoneActivity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_HOME_ADDRESS);
                    }
                });
        myAlertDialog.show();
    }

    private void selectMeterImage() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_METR_PHOTO);


                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(NgSupDoneActivity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_METER_PHOTO);
                    }
                });
        myAlertDialog.show();
    }

    private void selectInstallationImage() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_INSTALLATION);


                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(NgSupDoneActivity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_INSTALLATION);
                    }
                });
        myAlertDialog.show();
    }

    private void selectServiceCardImage() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_SERVICE_CARD);


                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(NgSupDoneActivity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST_SERVICE_CARD);
                    }
                });
        myAlertDialog.show();
    }

    private String  change_to_binary(Bitmap bitmapOrg) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        return ba1;
    }

    private String convertToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST_HOME_ADDRESS:
                if (requestCode == PICK_IMAGE_REQUEST_HOME_ADDRESS && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathHomeAddress = data.getData();
                    try {

                        mBitmapHome = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathHomeAddress);

                        image_path_HomeAddress = getPath(filePathHomeAddress);
                        if (mBitmapHome != null) {
                            iv_homeAddress.setImageBitmap(mBitmapHome);
                            homeAddress_pic_binary = change_to_binary(mBitmapHome);
                            //nguserListModel.setHome_address(homeAddress_pic_binary);
                        }

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_HomeAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_IMAGE_REQUEST_METR_PHOTO:
                if (requestCode == PICK_IMAGE_REQUEST_METR_PHOTO && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathMeterPhoto = data.getData();
                    try {

                        mBitmapmeterPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathMeterPhoto);
                        // imageView.setImageBitmap(bitmap);

                        //address_image.setImageBitmap(bitmap1);
                        image_path_meterPhoto = getPath(filePathMeterPhoto);
                        if (mBitmapmeterPhoto != null) {
                            iv_meterPhoto.setImageBitmap(mBitmapmeterPhoto);
                            meter_pic_binary = change_to_binary(mBitmapmeterPhoto);
                            //nguserListModel.setMeter_photo(meter_pic_binary);
                        }
                        Log.e("image_path_aadhar+,", "" + image_path_HomeAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_IMAGE_REQUEST_INSTALLATION:
                if (requestCode == PICK_IMAGE_REQUEST_INSTALLATION && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathInstallation = data.getData();
                    try {

                        mBitmapInstallation = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathInstallation);
                        // imageView.setImageBitmap(bitmap);

                        //address_image.setImageBitmap(bitmap1);
                        image_path_installation = getPath(filePathInstallation);

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_HomeAddress);
                        iv_installation.setImageBitmap(mBitmapInstallation);
                        if (mBitmapInstallation != null) {

                            installation_pic_binary = change_to_binary(mBitmapInstallation);
                            //nguserListModel.setInstallation_photo(installation_pic_binary);
                            //primaryOrderHeader.setImage_binary(pic_binary);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_IMAGE_REQUEST_SERVICE_CARD:
                if (requestCode == PICK_IMAGE_REQUEST_SERVICE_CARD && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathServiceCard = data.getData();
                    try {

                        mBitmapServiceCard = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathServiceCard);
                        // imageView.setImageBitmap(bitmap);

                        //address_image.setImageBitmap(bitmap1);
                        image_path_service_card = getPath(filePathServiceCard);
                        iv_serviceCard.setImageBitmap(mBitmapServiceCard);
                        if (mBitmapServiceCard != null) {

                            serviceCard_pic_binary = change_to_binary(mBitmapServiceCard);
                            //nguserListModel.setService_photo(serviceCard_pic_binary);
                            //primaryOrderHeader.setImage_binary(pic_binary);
                        }

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_HomeAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_CUSTOMER_IMAGE_SIGNATURE:
                if (requestCode == PICK_CUSTOMER_IMAGE_SIGNATURE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePathSignature = data.getData();
                    Log.d(log,"filepathsig= "+filePathSignature);
                    try {

                        mBitmapSignature = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePathSignature);
                        // imageView.setImageBitmap(bitmap);
                        Log.d(log,"mBitmapSugnature= "+mBitmapSignature);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_signature = getPath(filePathSignature);

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_signature);



                        signature_image.setImageBitmap(mBitmapSignature);
                        if (mBitmapSignature != null) {

                            signatureBinary = change_to_binary(mBitmapSignature);
                            //nguserListModel.setInstallation_photo(installation_pic_binary);
                            //primaryOrderHeader.setImage_binary(pic_binary);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(log,"catch");
                    }
                }
                break;
            case CAMERA_REQUEST_HOME_ADDRESS:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_HOME_ADDRESS) {
                    File f = new File(getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {

                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        mBitmapHome = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);


                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_HomeAddress = file.toString();
                        // image_path_address1 =file.toString();
                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        try {
                            outFile = new FileOutputStream(image_path_HomeAddress);
                            mBitmapHome.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
                            outFile.flush();
                            outFile.close();
                            iv_homeAddress.setImageBitmap(mBitmapHome);
                            if (mBitmapHome != null) {

                                homeAddress_pic_binary = change_to_binary(mBitmapHome);
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
            case CAMERA_REQUEST_METER_PHOTO:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_METER_PHOTO) {
                    File f = new File(getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {

                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        mBitmapmeterPhoto = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);


                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_meterPhoto = file.toString();
                        // image_path_address1 =file.toString();
                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        try {
                            outFile = new FileOutputStream(image_path_meterPhoto);
                            mBitmapmeterPhoto.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
                            outFile.flush();
                            outFile.close();
                            iv_meterPhoto.setImageBitmap(mBitmapmeterPhoto);
                            if (mBitmapmeterPhoto != null) {

                                meter_pic_binary = change_to_binary(mBitmapmeterPhoto);
                                //nguserListModel.setService_photo(meter_pic_binary);
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
            case CAMERA_REQUEST_INSTALLATION:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_INSTALLATION) {
                    File f = new File(getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {

                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        mBitmapInstallation = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);

                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_installation = file.toString();
                        // image_path_address1 =file.toString();
                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        try {
                            outFile = new FileOutputStream(image_path_installation);
                            mBitmapInstallation.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
                            outFile.flush();
                            outFile.close();

                            iv_installation.setImageBitmap(mBitmapInstallation);
                            if (mBitmapInstallation != null) {

                                installation_pic_binary = change_to_binary(mBitmapInstallation);
                                // nguserListModel.setService_photo(installation_pic_binary);
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
            case CAMERA_REQUEST_SERVICE_CARD:
                if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_SERVICE_CARD) {
                    File f = new File(getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {

                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        mBitmapServiceCard = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);

                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        image_path_service_card = file.toString();
                        // image_path_address1 =file.toString();
                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        try {
                            outFile = new FileOutputStream(image_path_service_card);
                            mBitmapServiceCard.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
                            outFile.flush();
                            outFile.close();
                            iv_serviceCard.setImageBitmap(mBitmapServiceCard);
                            if (mBitmapServiceCard != null) {

                                serviceCard_pic_binary = change_to_binary(mBitmapServiceCard);
                                // nguserListModel.setService_photo(serviceCard_pic_binary);
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

        }
    }

    private String getPath(Uri uri) {
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

    /*public String getPath(Uri contentUri) {
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
            *//*cursor = this.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();*//*
            return path;
        }
    }*/
}

