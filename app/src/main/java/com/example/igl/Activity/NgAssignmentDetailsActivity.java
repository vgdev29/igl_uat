package com.example.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.igl.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.os.Environment.getExternalStorageDirectory;

public class NgAssignmentDetailsActivity extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST_HOME_ADDRESS = 1;
    private final int PICK_IMAGE_REQUEST_METR_PHOTO = 2;
    private final int PICK_IMAGE_REQUEST_INSTALLATION = 3;
    private final int PICK_IMAGE_REQUEST_SERVICE_CARD = 4;
    protected static final int CAMERA_REQUEST_HOME_ADDRESS = 200;
    protected static final int CAMERA_REQUEST_METER_PHOTO = 201;
    protected static final int CAMERA_REQUEST_INSTALLATION = 202;
    protected static final int CAMERA_REQUEST_SERVICE_CARD = 203;
    private static final String IMAGE_DIRECTORY = "/signdemo";

    private Button btn_home_address, btn_meterPhoto,btn_installation,btn_serviceCard;
    private ImageView iv_homeAddress,iv_meterPhoto,iv_installation,iv_serviceCard;

    String image_path_HomeAddress,image_path_address,owner_image_select;

    private Uri filePath;
    Bitmap mBitmap;
    private RadioGroup radioGroup;
    private String Type_Of_Status;
    RadioButton genderradioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng_assignment_details);

        mFindViewById();
        clickEvent();

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


    }

    private void clickEvent() {
        btn_home_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= btn_home_address.getText().toString().trim();
                selectImage(text);
            }
        });
        btn_meterPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= btn_meterPhoto.getText().toString().trim();
                selectImage(text);
            }
        });
        btn_installation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= btn_installation.getText().toString().trim();
                selectImage(text);
            }
        });
        btn_serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= btn_serviceCard.getText().toString().trim();
                selectImage(text);
            }
        });
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        final int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton)findViewById(selectedId);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_complete:
                        //Toast.makeText(New_Regestration_Form.this, "Type", Toast.LENGTH_LONG).show();
                        Type_Of_Status="Complete";
                        break;
                    case R.id.rb_delay:

                        Type_Of_Status="Delay";
                        //Ownar_Signature();
                        break;
                }
                if(selectedId==-1){
                    Toast.makeText(NgAssignmentDetailsActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NgAssignmentDetailsActivity.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectImage(final String text) {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        if (text.equalsIgnoreCase(btn_home_address.getText().toString().trim())){
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_HOME_ADDRESS);
                        }else  if (text.equalsIgnoreCase(btn_meterPhoto.getText().toString().trim())){
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_METR_PHOTO);
                        }else  if (text.equalsIgnoreCase(btn_installation.getText().toString().trim())){
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_INSTALLATION);
                        }else  if (text.equalsIgnoreCase(btn_serviceCard.getText().toString().trim())){
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_SERVICE_CARD);
                        }

                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(NgAssignmentDetailsActivity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        if (text.equalsIgnoreCase(btn_home_address.getText().toString().trim())){
                            startActivityForResult(intent,CAMERA_REQUEST_HOME_ADDRESS);
                        }else if (text.equalsIgnoreCase(btn_meterPhoto.getText().toString().trim())){
                            startActivityForResult(intent,CAMERA_REQUEST_METER_PHOTO);
                        }else if (text.equalsIgnoreCase(btn_installation.getText().toString().trim())){
                            startActivityForResult(intent,CAMERA_REQUEST_INSTALLATION);
                        }else if (text.equalsIgnoreCase(btn_serviceCard.getText().toString().trim())){
                            startActivityForResult(intent,CAMERA_REQUEST_SERVICE_CARD);
                        }
                        //startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });
        myAlertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST_HOME_ADDRESS:
                if (requestCode == PICK_IMAGE_REQUEST_HOME_ADDRESS && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath = data.getData();
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                        // imageView.setImageBitmap(bitmap);
                        iv_homeAddress.setImageBitmap(mBitmap);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_HomeAddress = getPath(filePath);

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_HomeAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_IMAGE_REQUEST_METR_PHOTO:
                if (requestCode ==  PICK_IMAGE_REQUEST_METR_PHOTO&& resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath = data.getData();
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                        // imageView.setImageBitmap(bitmap);
                        iv_meterPhoto.setImageBitmap(mBitmap);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_HomeAddress = getPath(filePath);

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_HomeAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_IMAGE_REQUEST_INSTALLATION:
                if (requestCode == PICK_IMAGE_REQUEST_INSTALLATION && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath = data.getData();
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                        // imageView.setImageBitmap(bitmap);
                        iv_installation.setImageBitmap(mBitmap);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_HomeAddress = getPath(filePath);

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_HomeAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_IMAGE_REQUEST_SERVICE_CARD:
                if (requestCode == PICK_IMAGE_REQUEST_SERVICE_CARD && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath = data.getData();
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                        // imageView.setImageBitmap(bitmap);
                        iv_serviceCard.setImageBitmap(mBitmap);
                        //address_image.setImageBitmap(bitmap1);
                        image_path_HomeAddress = getPath(filePath);

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.e("image_path_aadhar+,", "" + image_path_HomeAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
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
                        mBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        iv_homeAddress.setImageBitmap(mBitmap);
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
                            mBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
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
                        mBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        iv_meterPhoto.setImageBitmap(mBitmap);
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
                            mBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
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
                        mBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        iv_installation.setImageBitmap(mBitmap);
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
                            mBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
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
                        mBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        iv_serviceCard.setImageBitmap(mBitmap);
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
                            mBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
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
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return path;
    }
}
