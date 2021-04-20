package com.example.igl.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.example.igl.R;
import com.example.igl.Helper.AppController;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;

import com.iceteck.silicompressorr.FileUtils;
import com.itextpdf.text.pdf.security.SecurityConstants;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.kyanogen.signatureview.SignatureView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.gotev.uploadservice.ContentType;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TPI_Inspection_RFC_Activity extends Activity {
    protected static final int CAMERA_REQUEST = 1;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    private final int PICK_IMAGE_REQUEST = 2;
    String Status;
    TextView address_txt;
    Button approve_button;
    ImageView back;
    Bitmap bitmap;
    TextView bp_no_text;
    TextView cementing_of_holes_text;
    TextView clamping_gi_pvc_text;
    Button clear;
    TextView connectivity_type;
    TextView contect_no_txt;
    TextView contractor_mobile_text;
    ImageView crose_img;
    TextView cu_instalation_text;
    Button decline_button;
    EditText descreption_edit;
    private Uri filePath_Image;
    String filePath_img_string;
    TextView gas_meter_testing_text;
    TextView gas_type_text;
    TextView gi_instalation_text;
    ImageView image1;
    String image1_path;
    ImageView image2;
    String image2_path;
    ImageView image3;
    String image3_path;
    ImageView image4;
    String image4_path;
    String image_path;
    ImageView image_upload;
    TextView initial_metar_reading_text;
    TextView manufacture_make_txt;
    MaterialDialog materialDialog;
    TextView meater_intalation_text;
    String meter_no;
    TextView meter_no_txt;
    TextView name_of_consumer;
    TextView name_of_contractor;
    TextView name_of_contractor_respective_text;
    TextView name_of_tpi;
    TextView nane_of_pmc_igl_txt;
    TextView ncapAvail_type;
    TextView no_of_av_text;
    TextView painting_of_gi_pipe_text;
    TextView pmc_igl_mobile_txt;
    TextView property_type;
    TextView pvc_sleeve_text;
    TextView regulater_no_text;
    TextView rfc_type;
    Button save;
    SharedPrefs sharedPrefs;
    String signature;
    SignatureView signatureView;
    Button signature_button;
    ImageView signature_image;
    TextView tgAvail_type;
    TextView to_do_creation;
    TextView tpi_mobile_txt;
    TextView type_nr_txt;
    TextView vo_of_iv_text;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.tpi_inspection_rfc_details);
        this.sharedPrefs = new SharedPrefs(this);
        Layout_Id();
    }

    private void Layout_Id() {
        this.back = (ImageView) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(-1);
                finish();
            }
        });
        this.signature_image = (ImageView) findViewById(R.id.signature_image);
        this.signature_button = (Button) findViewById(R.id.signature_button);
        this.approve_button = (Button) findViewById(R.id.approve_button);
        this.decline_button = (Button) findViewById(R.id.decline_button);
        this.image1 = (ImageView) findViewById(R.id.image1);
        this.image2 = (ImageView) findViewById(R.id.image2);
        this.image3 = (ImageView) findViewById(R.id.image3);
        this.image4 = (ImageView) findViewById(R.id.image4);
        this.bp_no_text = (TextView) findViewById(R.id.bp_no_text);
        this.name_of_contractor = (TextView) findViewById(R.id.name_of_contractor);
        this.name_of_consumer = (TextView) findViewById(R.id.name_of_consumer);
        this.address_txt = (TextView) findViewById(R.id.address_txt);
        this.contect_no_txt = (TextView) findViewById(R.id.contect_no_txt);
        this.manufacture_make_txt = (TextView) findViewById(R.id.manufacture_make_txt);
        this.type_nr_txt = (TextView) findViewById(R.id.type_nr_txt);
        this.meter_no_txt = (TextView) findViewById(R.id.meter_no_txt);
        this.initial_metar_reading_text = (TextView) findViewById(R.id.initial_metar_reading_text);
        this.regulater_no_text = (TextView) findViewById(R.id.regulater_no_text);
        this.gi_instalation_text = (TextView) findViewById(R.id.gi_instalation_text);
        this.cu_instalation_text = (TextView) findViewById(R.id.cu_instalation_text);
        this.vo_of_iv_text = (TextView) findViewById(R.id.vo_of_iv_text);
        this.no_of_av_text = (TextView) findViewById(R.id.no_of_av_text);
        this.pvc_sleeve_text = (TextView) findViewById(R.id.pvc_sleeve_text);
        this.clamping_gi_pvc_text = (TextView) findViewById(R.id.clamping_gi_pvc_text);
        this.meater_intalation_text = (TextView) findViewById(R.id.meater_intalation_text);
        this.gas_meter_testing_text = (TextView) findViewById(R.id.gas_meter_testing_text);
        this.cementing_of_holes_text = (TextView) findViewById(R.id.cementing_of_holes_text);
        this.painting_of_gi_pipe_text = (TextView) findViewById(R.id.painting_of_gi_pipe_text);
        this.name_of_contractor_respective_text = (TextView) findViewById(R.id.name_of_contractor_respective_text);
        this.contractor_mobile_text = (TextView) findViewById(R.id.contractor_mobile_text);
        this.name_of_tpi = (TextView) findViewById(R.id.name_of_tpi);
        this.tpi_mobile_txt = (TextView) findViewById(R.id.tpi_mobile_txt);
        this.nane_of_pmc_igl_txt = (TextView) findViewById(R.id.nane_of_pmc_igl_txt);
        this.pmc_igl_mobile_txt = (TextView) findViewById(R.id.pmc_igl_mobile_txt);
        this.to_do_creation = (TextView) findViewById(R.id.to_do_creation);
        this.rfc_type = (TextView) findViewById(R.id.rfc_type);
        this.gas_type_text = (TextView) findViewById(R.id.gas_type_text);
        this.property_type = (TextView) findViewById(R.id.property_type);
        this.tgAvail_type = (TextView) findViewById(R.id.tgAvail_type);
        this.connectivity_type = (TextView) findViewById(R.id.connectivity_type);
        this.ncapAvail_type = (TextView) findViewById(R.id.ncapAvail_type);
        OnClick();
        RFC_Data();
    }

    private void OnClick() {
        this.signature_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Signature_Method();
            }
        });
        this.approve_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Status = "0";
                TPI_Multipart(signature);
            }
        });
        this.decline_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Status = "1";
                Dilogbox_Image_Uplode();
            }
        });
    }

    /* access modifiers changed from: private */
    public void Signature_Method() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        getWindow().setLayout(-1, -1);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle(SecurityConstants.Signature);
        dialog.setCancelable(true);
        this.signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        this.clear = (Button) dialog.findViewById(R.id.clear);
        this.save = (Button) dialog.findViewById(R.id.save);
        this.clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signatureView.clearCanvas();
            }
        });
        ((ImageView) dialog.findViewById(R.id.crose_img)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bitmap = signatureView.getSignatureBitmap();
                signature = saveImage(bitmap);
                signature_image.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String saveImage(Bitmap bitmap2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        File file = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
            Log.d("Signature_Page++", file.toString());
        }
        try {
            File file2 = new File(file, Calendar.getInstance().getTimeInMillis() + ".jpg");
            file2.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{file2.getPath()}, new String[]{ContentType.IMAGE_JPEG}, (MediaScannerConnection.OnScanCompletedListener) null);
            fileOutputStream.close();
            Log.d("TAG", "File Saved::--->" + file2.getAbsolutePath());
            return file2.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void RFC_Data() {
        materialDialog = new MaterialDialog
                .Builder(this)
                .content((CharSequence) "Please wait....")
                .progress(true, 0)
                .show();
        StringRequest r2 = new StringRequest(0, Constants.RFCDetails + "/" + getIntent().getStringExtra("Bp_number"), new Response.Listener<String>() {
            public void onResponse(String str) {
                String str2 = "http://192.168.31.251:8081/";
                materialDialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Log.e("Response++", jSONObject.toString());
                    JSONArray jSONArray = jSONObject.getJSONArray("File_Path");
                    int i = 0;
                    for (int i2 = 0; i2 <= jSONArray.length(); i2++) {
                        image_path = jSONArray.getJSONObject(0).getString("files0");
                        image1_path = jSONArray.getJSONObject(1).getString("files1");
                        image2_path = jSONArray.getJSONObject(2).getString("files2");
                        image3_path = jSONArray.getJSONObject(3).getString("files3");
                    }
                    JSONArray jSONArray2 = jSONObject.getJSONObject("RFCdetails").getJSONArray("rfc");
                    for (i =0; i<jSONArray2.length();i++) {
                        JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
                        String meter_make= jSONObject2.getString("meter_make");
                        String metertype = jSONObject2.getString("meter_type");
                        String meter_no = jSONObject2.getString("meter_no");
                        String initialReading = jSONObject2.getString("initial_meter_reading");
                        String regulator = jSONObject2.getString("regulator_no");
                        String gi_installation = jSONObject2.getString("gi_installation");
                        String cu_installation = jSONObject2.getString("cu_installation");
                        String no_of_iv = jSONObject2.getString("no_of_iv");
                        String no_of_av = jSONObject2.getString("no_of_av");
                        String meter_installation = jSONObject2.getString("meter_installation");
                        String pvc_sleeve = jSONObject2.getString("pvc_sleeve");
                        String clamming = jSONObject2.getString("clamming");
                        String gas_meter_testing = jSONObject2.getString("gas_meter_testing");
                        String cementing_of_holes = jSONObject2.getString("cementing_of_holes");
                        String painting_of_giPipe = jSONObject2.getString("painting_of_giPipe");
                        String customer1 = jSONObject2.getString("customer1");
                        String customer2 = jSONObject2.getString("customer2");
                        String bp = jSONObject2.getString("bp");
                        String status=  jSONObject2.getString("status");
                        String tpiName = jSONObject2.getString("tpiName");
                        String tpiLastName = jSONObject2.getString("tpiLastName");
                        String vendorName = jSONObject2.getString("vendorName");
                        String vendorLastName = jSONObject2.getString("vendorLastName");
                        final String address = jSONObject2.getString("address");
                        final String firstName = jSONObject2.getString("firstName");
                        String lastName = jSONObject2.getString("lastName");
                        final String mobileNo = jSONObject2.getString("mobileNo");
                        String caNo = jSONObject2.getString("caNo");
                        String agencyName = jSONObject2.getString("agencyName");
                        String rfctype = jSONObject2.getString("rfctype");
                        String gasType = jSONObject2.getString("gasType");
                        String propertyType = jSONObject2.getString("propertyType");
                        String tfavail = jSONObject2.getString("tfAvail");
                        String connectivity = jSONObject2.getString("connectivity");
                        String ncapavail = jSONObject2.getString("nCapAvail");
                        String hole_drilled = jSONObject2.getString("holeDrilled");
                        String mcv_testing = jSONObject2.getString("mcvTesting");
                        String manufactureMakeDescription = jSONObject2.getString("manufactureMakeDescription");
                        name_of_contractor.setText(agencyName);
                        name_of_consumer.setText(firstName+" "+lastName);
                        address_txt.setText(address);
                        contect_no_txt.setText(mobileNo);
                        manufacture_make_txt.setText(meter_make);
                        type_nr_txt.setText(customer1);
                        meter_no_txt.setText(meter_no);
                        initial_metar_reading_text.setText(initialReading);
                        regulater_no_text.setText(regulator);
                        gi_instalation_text.setText(gi_installation);
                        cu_instalation_text.setText(cu_installation);
                        vo_of_iv_text.setText(no_of_iv);
                        no_of_av_text.setText(no_of_av);
                        pvc_sleeve_text.setText(pvc_sleeve);
                        clamping_gi_pvc_text.setText(clamming);
                        meater_intalation_text.setText(meter_installation);
                        gas_meter_testing_text.setText(gas_meter_testing);
                        cementing_of_holes_text.setText(cementing_of_holes);
                        painting_of_gi_pipe_text.setText(painting_of_giPipe);
                        name_of_contractor_respective_text.setText(agencyName);
                        name_of_tpi.setText(tpiName);
                        bp_no_text.setText(bp);
                        rfc_type.setText(rfctype);
                        gas_type_text.setText(gasType);
                        property_type.setText(propertyType);
                        tgAvail_type.setText(tfavail);
                        connectivity_type.setText(connectivity);
                        ncapAvail_type.setText(ncapavail);
                        contect_no_txt.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent("android.intent.action.DIAL");
                                    intent.setData(Uri.parse("tel:" + mobileNo));
                                    startActivity(intent);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        to_do_creation.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent intent = new Intent(TPI_Inspection_RFC_Activity.this, To_Do_Task_Creation_TPI.class);
                                intent.putExtra("Bp_number", getIntent().getStringExtra("Bp_number"));
                                intent.putExtra("Address", address);
                                intent.putExtra("mobileNo", mobileNo);
                                intent.putExtra("Full_Name", firstName);
                                intent.putExtra("Type", "1");
                                startActivity(intent);
                                finish();
                            }
                        });
                        Log.e("image_path++", image_path);
                        Log.e("image1_path++", image1_path);
                        Log.e("image2_path++", image2_path);
                        Log.e("image3_path++", image3_path);
                        RequestManager with = Glide.with((Activity) TPI_Inspection_RFC_Activity.this);
                        StringBuilder sb = new StringBuilder();

                        sb.append(str2);
                        sb.append(image_path);
                        ((RequestBuilder) with.load(sb.toString()).centerCrop()).into(image1);
                        RequestManager with2 = Glide.with((Activity) TPI_Inspection_RFC_Activity.this);
                        ((RequestBuilder) with2.load(sb.toString() + image1_path).centerCrop()).into(image2);
                        RequestManager with3 = Glide.with((Activity) TPI_Inspection_RFC_Activity.this);
                        ((RequestBuilder) with3.load(sb.toString() + image2_path).centerCrop()).into(image3);
                        RequestManager with4 = Glide.with((Activity) TPI_Inspection_RFC_Activity.this);
                        ((RequestBuilder) with4.load(sb.toString() + image3_path).centerCrop()).into(image4);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e2) {
                    e2.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                materialDialog.dismiss();
                NetworkResponse networkResponse = volleyError.networkResponse;
                if ((volleyError instanceof ServerError) && networkResponse != null) {
                    try {
                        new JSONObject(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8")));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }) {
        };
        Volley.newRequestQueue(this).add(r2);
        r2.setRetryPolicy(new DefaultRetryPolicy(12000, 1, 1.0f));
    }

    public void TPI_Multipart(String str) {
        try {
            this.materialDialog = new MaterialDialog.Builder(this).content((CharSequence) "Please wait....").progress(true, 0).show();
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(this, UUID.randomUUID().toString(), Constants.RFCAddDeclined);
            multipartUploadRequest.addFileToUpload(str, "declinedImage");
            multipartUploadRequest.addParameter("lead_no", getIntent().getStringExtra("lead_no"));
            multipartUploadRequest.addParameter("bp_no", getIntent().getStringExtra("Bp_number"));
            multipartUploadRequest.addParameter(NotificationCompat.CATEGORY_STATUS, this.Status);
            multipartUploadRequest.addParameter("meterNo", this.meter_no);
            multipartUploadRequest.setDelegate(new UploadStatusDelegate() {
                public void onProgress(Context context, UploadInfo uploadInfo) {
                }

                public void onError(Context context, UploadInfo uploadInfo, Exception exc) {
                    exc.printStackTrace();
                    materialDialog.dismiss();
                    Log.e("Uplodeerror++", uploadInfo.getSuccessfullyUploadedFiles().toString());
                }

                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                    materialDialog.dismiss();
                    uploadInfo.getSuccessfullyUploadedFiles().toString();
                    serverResponse.getHeaders().toString();
                    Log.e("UPLOADEsinin++", serverResponse.getBodyAsString());
                    setResult(-1);
                    finish();
                    Toast.makeText(TPI_Inspection_RFC_Activity.this, "Succesfully Feasibility Approve", Toast.LENGTH_SHORT).show();
                }

                public void onCancelled(Context context, UploadInfo uploadInfo) {
                    materialDialog.dismiss();
                }
            });
            multipartUploadRequest.setUtf8Charset();
            multipartUploadRequest.setUsesFixedLengthStreamingMode(true);
            multipartUploadRequest.setMaxRetries(2);
            multipartUploadRequest.setAutoDeleteFilesAfterSuccessfulUpload(true);
            multipartUploadRequest.startUpload();
            Log.e(NotificationCompat.CATEGORY_STATUS, this.Status);
            Log.e(str, "declinedImage");
        } catch (Exception unused) {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();
            this.materialDialog.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void Dilogbox_Image_Uplode() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.image_upload_dilogbox);
//        this.image_upload = (ImageView) dialog.findViewById(R.id.image_upload);
        this.crose_img = (ImageView) dialog.findViewById(R.id.crose_img);
        this.image_upload.setVisibility(View.VISIBLE);
        this.descreption_edit = (EditText) dialog.findViewById(R.id.descreption_edit);
        this.descreption_edit.setVisibility(View.GONE);

        ((Button) dialog.findViewById(R.id.save_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (validateData()) {
                    New_Regestration_Form();
                }
            }
        });
        this.crose_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void selectImage_address() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Pictures Option");
        builder.setMessage("How do you want to set your picture?");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setType(FileUtils.MIME_TYPE_IMAGE);
                intent.setAction("android.intent.action.GET_CONTENT");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                TPI_Inspection_RFC_Activity tPI_Inspection_RFC_Activity = TPI_Inspection_RFC_Activity.this;
                intent.putExtra("output", FileProvider.getUriForFile(tPI_Inspection_RFC_Activity, getApplicationContext().getPackageName() + ".provider", file));
                // intent.addFlags(1);
                startActivityForResult(intent, 1);
            }
        });
        builder.show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 1) {
            if (i == 2 && i == 2 && i2 == -1 && intent != null && intent.getData() != null) {
                this.filePath_Image = intent.getData();
                try {
                    this.bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), this.filePath_Image);
                    this.image_upload.setImageBitmap(this.bitmap);
                    this.signature = getPath(this.filePath_Image);
                    Log.e("image_path_aadhar+,", "" + this.filePath_Image);
                    TPI_Multipart(this.signature);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (i2 == -1 && i == 1) {
            File file = new File(Environment.getExternalStorageDirectory().toString());
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            int i3 = 0;
            while (true) {
                if (i3 < length) {
                    File file2 = listFiles[i3];
                    if (file2.getName().equals("temp.jpg")) {
                        file = file2;
                        break;
                    }
                    i3++;
                }
            }
            try {
                this.bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), new BitmapFactory.Options());
                this.image_upload.setImageBitmap(this.bitmap);
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                file.delete();
                File file3 = new File(absolutePath, String.valueOf(System.currentTimeMillis()) + ".jpg");
                Log.e("Camera_Path++", file3.toString());
                this.signature = file3.toString();
                TPI_Multipart(this.signature);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                    this.bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                } catch (IOException e3) {
                    e3.printStackTrace();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        String str = null;
        try {
            Cursor query = getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
            query.moveToFirst();
            String string = query.getString(0);
            String substring = string.substring(string.lastIndexOf(":") + 1);
            query.close();
            Cursor query2 = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, (String[]) null, "_id = ? ", new String[]{substring}, (String) null);
            query2.moveToFirst();
            str = query2.getString(query2.getColumnIndex("_data"));
            query2.close();
            return str;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return str;
        } catch (CursorIndexOutOfBoundsException e2) {
            e2.printStackTrace();
            return str;
        }
    }

    public void New_Regestration_Form() {
        this.materialDialog = new MaterialDialog.Builder(this).content((CharSequence) "Please wait....").progress(true, 0).cancelable(false).show();
        StringRequest r1 = new StringRequest(1, Constants.RFCADD, new Response.Listener<String>() {
            public void onResponse(String str) {
                materialDialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Log.e("BPCreationResponse++", jSONObject.toString());
                    Toast.makeText(TPI_Inspection_RFC_Activity.this, jSONObject.getString("Message"), Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                materialDialog.dismiss();
                NetworkResponse networkResponse = volleyError.networkResponse;
                if ((volleyError instanceof ServerError) && networkResponse != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8")));
                        Log.e("object", jSONObject.toString());
                        jSONObject.getJSONObject("error").getString("message");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                try {
                    hashMap.put("lead_no", getIntent().getStringExtra("lead_no"));
                    hashMap.put("bp_no", getIntent().getStringExtra("Bp_number"));
                    hashMap.put(NotificationCompat.CATEGORY_STATUS, Status);
                    hashMap.put("meterNo", meter_no);
                    hashMap.put(DublinCoreProperties.DESCRIPTION, descreption_edit.getText().toString());
                } catch (Exception unused) {
                }
                return hashMap;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                return new HashMap();
            }
        };
        r1.setRetryPolicy(new DefaultRetryPolicy(200000, 20, 1.0f));
        r1.setTag("login_request");
        AppController.getInstance().addToRequestQueue(r1, "login_request");
    }

    /* access modifiers changed from: private */
    public boolean validateData() {
        if (this.descreption_edit.getText().length() != 0) {
            return true;
        }
        this.descreption_edit.setError("Enter Descreption");
        return false;
    }
}
