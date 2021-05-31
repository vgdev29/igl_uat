package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.itextpdf.text.pdf.security.SecurityConstants;
import com.kyanogen.signatureview.SignatureView;
import com.squareup.picasso.Picasso;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPI_RfcHold_Approval_Activity extends Activity {
    private static final String IMAGE_DIRECTORY = "/signdemo";

    ProgressDialog materialDialog;
    SharedPrefs sharedPrefs;
    ImageView back;

    Bp_No_Item bp_no_item = new Bp_No_Item();
    TextView header_title,bpno,custname,mob,email,adress,description,feasibilitydate,supstatus,contName,contMob,
    supName,supMob,fesiName,fesiMob,followup,rfcdate,cadate,substatus,zone,leadno;
    LinearLayout top_layout;
    Button approve,decline,signature_button;
    String BP_NO,LEAD_NO,signature,statcode,declinedescription;
    private SignatureView signatureView;
    private Bitmap bitmap;
    private ImageView signature_image,holdimage;
    private String imagepath;
    private String audiopath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tpi_rfchold_approval);
        materialDialog = new ProgressDialog(this);
        materialDialog.setTitle("Please wait...");
        materialDialog.setCancelable(true);
        materialDialog.setCanceledOnTouchOutside(true);
        sharedPrefs = new SharedPrefs(this);

        BP_NO = getIntent().getStringExtra("Bp_number");
        LEAD_NO = getIntent().getStringExtra("leadno");
        statcode = getIntent().getStringExtra("iglstatus");

        Log.d("rfchold","bpno = "+BP_NO+"leadno = "+LEAD_NO+" statcode = "+statcode);
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
        top_layout=findViewById(R.id.top_layout);
        header_title=findViewById(R.id.header_title);
        header_title.setText("TPI Approval");
        bpno = findViewById(R.id.txt_bpno);
        custname = findViewById(R.id.txt_custname);
        mob = findViewById(R.id.txt_mob);
        email = findViewById(R.id.txt_email);
        adress = findViewById(R.id.txt_address);
        substatus = findViewById(R.id.txt_rfcsubstatus);
        description = findViewById(R.id.txt_rfcdescription);
        feasibilitydate = findViewById(R.id.txt_rfcfeasibilitydate);
        approve = findViewById(R.id.approve_button);
        decline = findViewById(R.id.decline_button);
        holdimage= findViewById(R.id.holdimage);
        contName= findViewById(R.id.txt_rfccontractor);
        contMob= findViewById(R.id.txt_rfccontno);
        supName= findViewById(R.id.txt_rfcsupname);
        supMob= findViewById(R.id.txt_rfcsupno);
        fesiName= findViewById(R.id.txt_rfcfeasibility);
        fesiMob= findViewById(R.id.txt_rfcfesibilityno);
        followup= findViewById(R.id.txt_rfcfollowup);
        rfcdate= findViewById(R.id.txt_rfcassignment);
        cadate= findViewById(R.id.txt_rfccadate);
        zone= findViewById(R.id.txt_rfczone);
        leadno= findViewById(R.id.rfc_leadno);
        holdimage= findViewById(R.id.holdimage);
        signature_button = findViewById(R.id.signature_button);
        signature_image = findViewById(R.id.signature_image);
        signature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signature_Method();
            }
        });
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TPI_Multipart(signature);
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineReasonDialog();
            }
        });
        Bp_No_List();
    }

    public void Bp_No_List() {


        materialDialog.show();
        String url = Constants.TPI_RFCHOLD_APPROVAl_Data+BP_NO;
        Log.d("tpi",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.TPI_RFCHOLD_APPROVAl_Data+BP_NO+"&statcode="+statcode,
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
                                String firstname = data_object.getString("first_name");
                                String middlename= data_object.getString("middle_name");
                                String lastname = data_object.getString("last_name");
                                String mobno = data_object.getString("mobile_number");
                                String emailid = data_object.getString("email_id");
                                String cityregion = data_object.getString("city_region");
                                String area = data_object.getString("area");
                                String society = data_object.getString("society");
                                String landmark = data_object.getString("landmark");
                                String housetype = data_object.getString("house_type");
                                String houseno = data_object.getString("house_no");
                                String block = data_object.getString("block_qtr_tower_wing");
                            String floor =  data_object.getString("floor");
                            String street =  data_object.getString("street_gali_road");
                            String pincode = data_object.getString("pincode");
                            String customertype = data_object.getString("customer_type");
                            String lpgcompany = data_object.getString("lpg_company");
                            String bp_no =  data_object.getString("bp_number");
                            String bpdate =  data_object.getString("bp_date");
                            String iglstatus =  data_object.getString("igl_status");

                            String rfcstatus =  data_object.getString("rfcStatus");
                            String rfcreason =  data_object.getString("rfcReason");
                            String rfcdescription =  data_object.getString("rfc_description");
                            String fesibilitydate = data_object.getString("fesabilityDate");
                            String approvedecline =  data_object.getString("approveDeclineSupervisor");
                            imagepath = data_object.getString("igl_file_path");
                           // audiopath = data_object.getString("rfc_audio_file");

                            bpno.setText(bp_no);
                            custname.setText(firstname+" "+ lastname);
                            mob.setText(mobno);
                            email.setText(emailid);
                            adress.setText(houseno+","+housetype+","+floor+"\n"+society+","+area+"\n"+cityregion+"-"+pincode);

                            description.setText(rfcdescription);
                            substatus.setText(rfcreason);
                            feasibilitydate.setText(fesibilitydate);

                            Picasso.with(TPI_RfcHold_Approval_Activity.this)
                                    .load("http://"+imagepath)
                                    .placeholder(R.color.red_light)
                                    .into(holdimage);
                            contName.setText(data_object.getString("rfcAdminName"));
                            contMob.setText(data_object.getString("rfcAdminMobileNo"));
                            supName.setText(data_object.getString("rfcVendorName"));
                            supMob.setText(data_object.getString("rfcVendorMobileNo"));
                            fesiName.setText(data_object.getString("fesabilityTpiName"));
                            fesiMob.setText(data_object.getString("fesabilityTpimobileNo"));
                            followup.setText(data_object.getString("rfc_followup_date"));
                            zone.setText(data_object.getString("zonecode"));
                            leadno.setText(data_object.getString("lead_no"));





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
                                materialDialog.dismiss();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                materialDialog.dismiss();
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

    @Override
    public void onResume() {
        super.onResume();

        try {

            Bp_No_List();
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
        Button clear = (Button) dialog.findViewById(R.id.clear);
        Button save = (Button) dialog.findViewById(R.id.save);
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
        save.setOnClickListener(new View.OnClickListener() {
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

    public void TPI_Multipart(String str) {
        if(statcode.equalsIgnoreCase("112"))
        {
            statcode = "12";
        }
        else if (statcode.equalsIgnoreCase("111")){statcode = "11";}
        materialDialog.show();
        try {
            MultipartUploadRequest multipartUploadRequest = new
                    MultipartUploadRequest(this, UUID.randomUUID().toString(), Constants.TPI_RFC_HOLD_APPROVAl_DECLINE_CASE2);
            multipartUploadRequest.addFileToUpload(str, "declinedImage");
            multipartUploadRequest.addParameter("lead_no", LEAD_NO);
            multipartUploadRequest.addParameter("bp_no", BP_NO);
            multipartUploadRequest.addParameter("statcode",statcode);
            multipartUploadRequest.addParameter("description","Approved by TPI");
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
                    Toast.makeText(TPI_RfcHold_Approval_Activity.this, "Succesfull", Toast.LENGTH_SHORT).show();
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
            Log.e(NotificationCompat.CATEGORY_STATUS, statcode);
            Log.e(str, "declinedImage");
        } catch (Exception unused) {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();
            this.materialDialog.dismiss();
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
                    declinedescription = descreption_edit.getText().toString().trim();
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


    public void tpi_decline (){

        statcode = "2";

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
                            CommonUtils.toast_msg(TPI_RfcHold_Approval_Activity.this,msg);
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
                    params.put("lead_no",LEAD_NO);
                    params.put("bp_no", BP_NO);
                    params.put("statcode", statcode);
                    params.put("description",declinedescription);


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