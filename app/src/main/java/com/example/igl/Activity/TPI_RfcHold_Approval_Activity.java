package com.example.igl.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.example.igl.Adapter.TPI_Approval_Adapter;
import com.example.igl.Helper.CommonUtils;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;
import com.google.gson.JsonObject;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TPI_RfcHold_Approval_Activity extends Activity {
    private static final String IMAGE_DIRECTORY = "/signdemo";

    ProgressDialog materialDialog;
    SharedPrefs sharedPrefs;
    ImageView back;

    Bp_No_Item bp_no_item = new Bp_No_Item();
    TextView header_title,bpno,custname,mob,email,adress,status,substatus,description,feasibilitydate,supstatus,contName,contMob,
    supName,supMob,fesiName,fesiMob,followup,catid,codegrp,code,catalog,zone,leadno;
    LinearLayout top_layout;
    Button approve,decline,signature_button;
    String BP_NO,LEAD_NO,signature,statcode,declinedescription;
    private SignatureView signatureView;
    private Bitmap bitmap;
    private ImageView signature_image,holdimage;



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
        status = findViewById(R.id.txt_rfcstatus);
        substatus = findViewById(R.id.txt_rfcsubstatus);
        description = findViewById(R.id.txt_rfcdescription);
        feasibilitydate = findViewById(R.id.txt_rfcfeasibilitydate);
        supstatus = findViewById(R.id.txt_rfcapprove);
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
        catid= findViewById(R.id.txt_rfccatid);
        codegrp= findViewById(R.id.txt_rfccodegroup);
        code= findViewById(R.id.txt_rfccode);
        catalog= findViewById(R.id.txt_rfccatalog);
        zone= findViewById(R.id.txt_rfczone);
        leadno= findViewById(R.id.rfc_leadno);
        holdimage= findViewById(R.id.holdimage);
        holdimage= findViewById(R.id.holdimage);
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

                                bp_no_item.setLpg_distributor(data_object.getString("rfcStatus"));
                                bp_no_item.setLpg_conNo(data_object.getString("rfcReason"));
                                bp_no_item.setUnique_lpg_Id(data_object.getString("rfc_description"));
                                bp_no_item.setOwnerName(data_object.getString("fesabilityDate"));
                                bp_no_item.setChequeNo(data_object.getString("approveDeclineSupervisor"));
                            inflateData();
                                contName.setText(data_object.getString("rfcAdminName"));
                            contMob.setText(data_object.getString("rfcAdminMobileNo"));
                            supName.setText(data_object.getString("rfcVendorName"));
                            supMob.setText(data_object.getString("rfcVendorMobileNo"));
                            fesiName.setText(data_object.getString("fesabilityTpiName"));
                            fesiMob.setText(data_object.getString("fesabilityTpimobileNo"));
                            followup.setText(data_object.getString("rfc_followup_date"));
                            catid.setText(data_object.getString("rfcIglCatId"));
                            catalog.setText(data_object.getString("rfcIglCatalog"));
                            code.setText(data_object.getString("rfcIglCode"));
                            codegrp.setText(data_object.getString("igl_code_group"));
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

    public void inflateData()
    {
        materialDialog.dismiss();
        bpno.setText(bp_no_item.getBp_number());
        custname.setText(bp_no_item.getFirst_name() +" "+bp_no_item.getLast_name());
        mob.setText(bp_no_item.getMobile_number());
        email.setText(bp_no_item.getEmail_id());
        adress.setText(bp_no_item.getHouse_no()+","+bp_no_item.getHouse_type()+","+bp_no_item.getFloor()+"\n"+bp_no_item.getSociety()+","+bp_no_item.getArea()+"\n"+bp_no_item.getCity_region()+"-"+bp_no_item.getPincode());
        status.setText(bp_no_item.getLpg_distributor());
        description.setText(bp_no_item.getUnique_lpg_Id());
        substatus.setText(bp_no_item.getLpg_conNo());
        feasibilitydate.setText(bp_no_item.getOwnerName());
        supstatus.setText(bp_no_item.getChequeNo());

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
            Log.e(NotificationCompat.CATEGORY_STATUS, statcode+status);
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