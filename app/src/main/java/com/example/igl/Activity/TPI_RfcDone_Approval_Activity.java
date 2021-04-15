package com.example.igl.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

import com.example.igl.Helper.CommonUtils;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;
import com.itextpdf.text.pdf.security.SecurityConstants;
import com.kyanogen.signatureview.SignatureView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.os.Environment.getExternalStorageDirectory;

public class TPI_RfcDone_Approval_Activity extends Activity {

    private static final String IMAGE_DIRECTORY = "/signdemo";
    ProgressDialog materialDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    //  MaterialDialog materialDialog;
    Bp_No_Item bp_no_item = new Bp_No_Item();
    TextView header_title, bpno, custname, mob, email, adress, metermake, meterno, metertype, initialreading, clamming, leadno, agencyname,
            consumername, tpiname, vendorname, typenr, regulatorno, giinstallation, cuinstallation, ivno, avno, pvcsleeve, meterinstallation, gasmetertesting,
            cementinghole, painting, contractorrep, gastype, proptype, tfavail, connectivity, ncap, rfctype, holedrilled, mcvtesting;
    LinearLayout top_layout;
    Button approve, decline, signature_button, clear, save;
    ImageView image_path, image1_path, image2_path, image3_path, signature_image,image_upload;
    String BP_NO, LEAD_NO, image1, image2, image3, image4, customername, custmob,
            custemail,signature,status,description,declinedImagePath , tf_avail,Connectivity , bp_no,lead_no,meter_no;
    SignatureView signatureView;
    Bitmap bitmap;
    protected static final int CAMERA_REQUEST = 1;
    private String statcode="4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tpi_rfcdone_approval);
        materialDialog = new ProgressDialog(this);
        materialDialog.setTitle("Please wait...");
        materialDialog.setCancelable(true);
        materialDialog.setCanceledOnTouchOutside(true);
        sharedPrefs = new SharedPrefs(this);
        BP_NO = getIntent().getStringExtra("Bp_number");
        LEAD_NO = getIntent().getStringExtra("leadno");
        customername = getIntent().getStringExtra("custname");
        custmob = getIntent().getStringExtra("mobile");
        custemail = getIntent().getStringExtra("email");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Layout_ID();
    }

    private void Layout_ID() {
        top_layout = findViewById(R.id.top_layout);

        header_title = findViewById(R.id.header_title);
        header_title.setText("TPI Approval");
        bpno = findViewById(R.id.txt_bpno);
        custname = findViewById(R.id.txt_custname);
        mob = findViewById(R.id.txt_mob);
        email = findViewById(R.id.txt_email);
        adress = findViewById(R.id.txt_address);
        metermake = findViewById(R.id.txt_metermake);
        metertype = findViewById(R.id.txt_metertype);
        meterno = findViewById(R.id.txt_meterno);
        initialreading = findViewById(R.id.txt_initialread);
        clamming = findViewById(R.id.txt_clamming);
        rfctype = findViewById(R.id.txt_rfctype);
        agencyname = findViewById(R.id.txt_agencyname);
        holedrilled = findViewById(R.id.txt_holedrilled);
        mcvtesting = findViewById(R.id.txt_mcvtest);
        vendorname = findViewById(R.id.txt_vendorname);
        metermake = findViewById(R.id.txt_metermake);
        typenr = findViewById(R.id.txt_typenr);
        metertype = findViewById(R.id.txt_metertype);
        regulatorno = findViewById(R.id.txt_regulatorno);
        giinstallation = findViewById(R.id.txt_giinstallation);
        cuinstallation = findViewById(R.id.txt_cuinstallation);
        ivno = findViewById(R.id.txt_ivno);
        avno = findViewById(R.id.txt_avno);
        pvcsleeve = findViewById(R.id.txt_pvcsleeve);
        meterinstallation = findViewById(R.id.txt_meterinstallation);
        gasmetertesting = findViewById(R.id.txt_gasmeter);
        cementinghole = findViewById(R.id.txt_cementing);
        painting = findViewById(R.id.txt_paintingGi);
        gastype = findViewById(R.id.txt_gastype);
        proptype = findViewById(R.id.txt_proptype);
        tfavail = findViewById(R.id.txt_tfavail);
        connectivity = findViewById(R.id.txt_connectivity);
        ncap = findViewById(R.id.txt_ncapavail);
        approve = findViewById(R.id.approve_button);
        decline = findViewById(R.id.decline_button);
        leadno = findViewById(R.id.txt_leadno);
        image_path = (ImageView) findViewById(R.id.image1);
        image1_path = (ImageView) findViewById(R.id.image2);
        image2_path = (ImageView) findViewById(R.id.image3);
        image3_path = (ImageView) findViewById(R.id.image4);
        signature_image = (ImageView) findViewById(R.id.signature_image);
        signature_button = (Button) findViewById(R.id.signature_button);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "0";
                TPI_Multipart(signature);
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineReasonDialog();
            }
        });

        signature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signature_Method();
            }
        });
        //Bp_No_List();
        RFC_Data();
    }

    /*public void Bp_No_List() {
        materialDialog.show();
        String url = Constants.TPI_RFCDONE_APPROVAl_Data+BP_NO;
        Log.d("tpi",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.TPI_RFCDONE_APPROVAl_Data+BP_NO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        approve.setEnabled(true);
                        decline.setEnabled(true);
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.d("tpi",jsonObject.toString());
                            final JSONArray Bp_Details = jsonObject.getJSONArray("Bp_Details");


                                JSONObject data_object=Bp_Details.getJSONObject(0);

                                bp_no_item.setFirst_name(data_object.getString("first_name"));
                                bp_no_item.setMiddle_name(data_object.getString("middle_name"));
                                bp_no_item.setLast_name(data_object.getString("last_name"));
                                bp_no_item.setMobile_number(data_object.getString("mobile_number"));
                                bp_no_item.setEmail_id(data_object.getString("email_id"));
                                bp_no_item.setAadhaar_number(data_object.getString("aadhaar_number"));
                                bp_no_item.setCity_region(data_object.getString("city_region"));
                                bp_no_item.setArea(data_object.getString("area"));
                                bp_no_item.setSociety(data_object.getString("society"));
                                bp_no_item.setLandmark(data_object.getString("landmark"));
                                bp_no_item.setHouse_type(data_object.getString("house_type"));
                                bp_no_item.setHouse_no(data_object.getString("house_no"));
                                bp_no_item.setBlock_qtr_tower_wing(data_object.getString("block_qtr_tower_wing"));
                                bp_no_item.setFloor(data_object.getString("floor"));
                                bp_no_item.setStreet_gali_road(data_object.getString("street_gali_road"));
                                bp_no_item.setPincode(data_object.getString("pincode"));
                                bp_no_item.setCustomer_type(data_object.getString("customer_type"));
                                bp_no_item.setLpg_company(data_object.getString("lpg_company"));
                                bp_no_item.setBp_number(data_object.getString("bp_number"));
                                bp_no_item.setBp_date(data_object.getString("bp_date"));
                                bp_no_item.setIgl_status(data_object.getString("igl_status"));

                                bp_no_item.setLpg_distributor(data_object.getString("mobileMeterMake"));
                                bp_no_item.setLpg_conNo(data_object.getString("mobileMeterType"));
                                bp_no_item.setUnique_lpg_Id(data_object.getString("mobileMeterNo"));
                                bp_no_item.setOwnerName(data_object.getString("mobileInitialMeterReading"));
                                bp_no_item.setChequeNo(data_object.getString("clamming"));
                                inflateData();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
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
    }*/

    public void RFC_Data() {
        materialDialog.show();
        StringRequest r2 = new StringRequest(0, Constants.RFCDetails + "/" + getIntent().getStringExtra("Bp_number"), new Response.Listener<String>() {
            public void onResponse(String str) {

                materialDialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Log.e("Response++", jSONObject.toString());
                    JSONArray jSONArray = jSONObject.getJSONArray("File_Path");
                    int i = 0;
                    for (int i2 = 0; i2 <= jSONArray.length(); i2++) {
                        image1 = jSONArray.getJSONObject(0).getString("files0");
                        image2 = jSONArray.getJSONObject(1).getString("files1");
                        image3 = jSONArray.getJSONObject(2).getString("files2");
                        image4 = jSONArray.getJSONObject(3).getString("files3");
                    }
                    JSONArray jSONArray2 = jSONObject.getJSONObject("RFCdetails").getJSONArray("rfc");
                    for (i = 0; i < jSONArray2.length(); i++) {
                        JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
                        String meter_make = jSONObject2.getString("meter_make");
                        String meter_type = jSONObject2.getString("meter_type");
                        meter_no = jSONObject2.getString("meter_no");
                        String initialReading = jSONObject2.getString("initial_meter_reading");
                        String regulator = jSONObject2.getString("regulator_no");
                        String gi_installation = jSONObject2.getString("gi_installation");
                        String cu_installation = jSONObject2.getString("cu_installation");
                        String no_of_iv = jSONObject2.getString("no_of_iv");
                        String no_of_av = jSONObject2.getString("no_of_av");
                        String meter_installation = jSONObject2.getString("meter_installation");
                        String pvc_sleeve = jSONObject2.getString("pvc_sleeve");
                        String clamping = jSONObject2.getString("clamming");
                        String gas_meter_testing = jSONObject2.getString("gas_meter_testing");
                        String cementing_of_holes = jSONObject2.getString("cementing_of_holes");
                        String painting_of_giPipe = jSONObject2.getString("painting_of_giPipe");
                        String customer1 = jSONObject2.getString("customer1");
                        String customer2 = jSONObject2.getString("customer2");
                        bp_no = jSONObject2.getString("bp");
                        lead_no = jSONObject2.getString("leadNo");
                        String status = jSONObject2.getString("status");
                        String tpiName = jSONObject2.getString("tpiName");
                        String tpiLastName = jSONObject2.getString("tpiLastName");
                        String vendorName = jSONObject2.getString("vendorName");
                        String vendorLastName = jSONObject2.getString("vendorLastName");
                        String address = jSONObject2.getString("address");
                        String firstName = jSONObject2.getString("firstName");
                        String lastName = jSONObject2.getString("lastName");
                        String mobileNo = jSONObject2.getString("mobileNo");
                        String caNo = jSONObject2.getString("caNo");
                        String agencyName = jSONObject2.getString("agencyName");
                        String rfc_type = jSONObject2.getString("rfctype");
                        String gasType = jSONObject2.getString("gasType");
                        String propertyType = jSONObject2.getString("propertyType");
                          tf_avail = jSONObject2.getString("tfAvail");
                          Connectivity = jSONObject2.getString("connectivity");
                        String ncapavail = jSONObject2.getString("nCapAvail");
                        String hole_drilled = jSONObject2.getString("holeDrilled");
                        String mcv_testing = jSONObject2.getString("mcvTesting");
                        String manufactureMakeDescription = jSONObject2.getString("manufactureMakeDescription");
                        bpno.setText(BP_NO);
                        leadno.setText(LEAD_NO);
                        custname.setText(customername);
                        mob.setText(custmob);
                        email.setText(custemail);
                        adress.setText(address);
                        metermake.setText(meter_make);
                        meterno.setText(meter_no);
                        metertype.setText(manufactureMakeDescription);
                        initialreading.setText(bp_no_item.getOwnerName());
                        clamming.setText(bp_no_item.getChequeNo());
                        agencyname.setText(agencyName);
                        adress.setText(address);
                        metermake.setText(meter_make);
                        typenr.setText(customer1);
                        meterno.setText(meter_no);
                        initialreading.setText(initialReading);
                        regulatorno.setText(regulator);
                        giinstallation.setText(gi_installation);
                        cuinstallation.setText(cu_installation);
                        ivno.setText(no_of_iv);
                        avno.setText(no_of_av);
                        pvcsleeve.setText(pvc_sleeve);
                        clamming.setText(clamping);
                        meterinstallation.setText(meter_installation);
                        gasmetertesting.setText(gas_meter_testing);
                        cementinghole.setText(cementing_of_holes);
                        painting.setText(painting_of_giPipe);

                        vendorname.setText(vendorName);
                        bpno.setText(bp_no);
                        rfctype.setText(rfc_type);
                        gastype.setText(gasType);
                        proptype.setText(propertyType);
                        tfavail.setText(tf_avail);
                        connectivity.setText(Connectivity);
                        ncap.setText(ncapavail);
                        holedrilled.setText(hole_drilled);
                        mcvtesting.setText(mcv_testing);



                        RequestManager with = Glide.with((Activity) TPI_RfcDone_Approval_Activity.this);

                        ((RequestBuilder) with.load(image1).centerCrop()).into(image_path);
                        RequestManager with2 = Glide.with((Activity) TPI_RfcDone_Approval_Activity.this);
                        ((RequestBuilder) with2.load(image2).centerCrop()).into(image1_path);
                        RequestManager with3 = Glide.with((Activity) TPI_RfcDone_Approval_Activity.this);
                        ((RequestBuilder) with3.load(image3).centerCrop()).into(image2_path);
                        RequestManager with4 = Glide.with((Activity) TPI_RfcDone_Approval_Activity.this);
                        ((RequestBuilder) with4.load(image4).centerCrop()).into(image3_path);

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

    @Override
    public void onResume() {
        super.onResume();

        try {

            RFC_Data();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public void Signature_Method() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        getWindow().setLayout(-1, -1);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle(SecurityConstants.Signature);
        dialog.setCancelable(true);
        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);
        clear.setOnClickListener(new View.OnClickListener() {
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

    public void declineReasonDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.image_upload_dilogbox);

        final EditText descreption_edit =  dialog.findViewById(R.id.descreption_edit);

        (dialog.findViewById(R.id.save_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               if (descreption_edit.getText().toString().trim().isEmpty())
               {
                   descreption_edit.setError("*Mandatory to fill");
               }
               else {
                   description = descreption_edit.getText().toString().trim();
                    tpi_decline();
               }
            }
        });
        dialog.findViewById(R.id.crose_img).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void TPI_Multipart(String str) {
        if (tf_avail.equalsIgnoreCase("No")||Connectivity.equalsIgnoreCase("No"))
        {
            statcode = "13";
        }
        else {statcode = "4";}
        materialDialog.show();
        try {
            MultipartUploadRequest multipartUploadRequest = new
                    MultipartUploadRequest(this, UUID.randomUUID().toString(), Constants.TPI_RFC_APPROVAl_DECLINE_CASE1);
            multipartUploadRequest.addFileToUpload(str, "declinedImage");
            multipartUploadRequest.addParameter("lead_no", lead_no);
            multipartUploadRequest.addParameter("bp_no", bp_no);
            multipartUploadRequest.addParameter("status",status);
            multipartUploadRequest.addParameter("meterNo", meter_no);
            multipartUploadRequest.addParameter("statcode",statcode);
            multipartUploadRequest.setDelegate(new UploadStatusDelegate() {
                public void onProgress(Context context, UploadInfo uploadInfo) {
                    materialDialog.show();
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
                    Toast.makeText(TPI_RfcDone_Approval_Activity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                    finish();
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
            Log.e(NotificationCompat.CATEGORY_STATUS, statcode+status);
            Log.e(str, "declinedImage");
        } catch (Exception unused) {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();
            this.materialDialog.dismiss();
        }
    }

    public void tpi_decline (){
        if (tf_avail.equalsIgnoreCase("No")||Connectivity.equalsIgnoreCase("No"))
        {
            statcode = "2";
        }
        else {statcode = "2";}
        materialDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TPI_DECLINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("Message");
                            Log.d("Response++",jsonObject.toString());
                            CommonUtils.toast_msg(TPI_RfcDone_Approval_Activity.this,msg);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
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

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("lead_no",lead_no);
                    params.put("bp_no", bp_no);
                    params.put("statcode", statcode);
                    params.put("description",description);


                } catch (Exception e) {
                }
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}