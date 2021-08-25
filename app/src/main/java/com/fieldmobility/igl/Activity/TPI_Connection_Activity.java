package com.fieldmobility.igl.Activity;

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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.ConnectionDetector;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.GPSLocation;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.os.Environment.getExternalStorageDirectory;

public class TPI_Connection_Activity  extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    MaterialDialog materialDialog;

    ArrayList<String> Type_Of_Sub_Master;
    ArrayList<String> CatID_Sub_Master;
    ArrayList<String> Igl_Code;
    ArrayList<String> Igl_Code_Group;
    ArrayList<String> Igl_Catalog;

    ArrayList<String> Type_Of_Master;
    ArrayList<String> Type_Of_Master_ID;
    ArrayList<String> CatID_Master;
    ArrayList<String> Igl_Code_Master;
    ArrayList<String> Igl_Code_Group_Master;
    ArrayList<String> Igl_Catalog_Master;
    ArrayList<String> PipeLine_Catagory;
    ArrayList<String> PipeLine_ID;
    ArrayList<String> Igl_Pipe_line;
    Spinner spinner_master,spinner_sub_master,spinner_pipe_line;
    String type_of_master,type_of_sub_master,type_of_master_id,igl_code,igl_code_group,igl_catagory,catid_Sub_Master;
    String igl_code_Master,igl_code_group_Maaster,igl_catagory_Master,catid_Master;
    EditText descreption_edit;
    Button approve_button,decline_button;
    ImageView image_upload;
    Button save_button;
    protected static final int CAMERA_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST = 2;
    private Uri filePath_Image;
    String filePath_img_string,Status_Master;
    Bitmap bitmap;
    JSONArray jsonArray_SubMaster;
    String TPI_Status_Code,Address,Feasibility_Type;
    TextView bp_no_text,address_text,header_text;
    String complete_igl_code,complete_igl_code_group,complete_igl_catagory,complete_catid,pipeline_catagory,bpno;
    private String Latitude;
    private String Longitude;
    private RadioGroup radioGroup;
    String riserStatus = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tpi_layout);
        PipeLine_ID=new ArrayList<>();
        Layout_Id();
    }

    private void Layout_Id() {
        back=findViewById(R.id.back);
        spinner_master=(Spinner)findViewById(R.id.spinner_master);
        spinner_sub_master=(Spinner)findViewById(R.id.spinner_sub_master);
        spinner_pipe_line=(Spinner)findViewById(R.id.spinner_pipe_line);
        descreption_edit=findViewById(R.id.descreption_edit);
        approve_button=findViewById(R.id.approve_button);
        decline_button=findViewById(R.id.decline_button);
        bp_no_text=findViewById(R.id.bp_no_text);
        address_text=findViewById(R.id.address_text);
        header_text=findViewById(R.id.header_text);
        radioGroup = findViewById(R.id.radioGroup);
        Type_Of_Master=new ArrayList<>();
        Type_Of_Master_ID=new ArrayList<>();
        Type_Of_Sub_Master=new ArrayList<>();
        Igl_Code=new ArrayList<>();
        Igl_Code_Group=new ArrayList<>();
        Igl_Catalog=new ArrayList<>();
        CatID_Sub_Master=new ArrayList<>();
        PipeLine_Catagory=new ArrayList<>();
        Address=getIntent().getStringExtra("House_no")+" "+getIntent().getStringExtra("House_type")+" "+
                getIntent().getStringExtra("Landmark")+" "+getIntent().getStringExtra("Society")+" "+getIntent().getStringExtra("Area")+" "
                +getIntent().getStringExtra("City_region");

        address_text.setText(Address);
        bp_no_text.setText(getIntent().getStringExtra("Bp_number"));
        bpno = getIntent().getStringExtra("Bp_number");
        CatID_Master=new ArrayList<>();
        Igl_Code_Master=new ArrayList<>();
        Igl_Code_Group_Master=new ArrayList<>();
        Igl_Catalog_Master=new ArrayList<>();
        Igl_Pipe_line=new ArrayList<>();
        Status_Master= getIntent().getStringExtra("igl_code_group");
        Log.e("Status_Master",Status_Master);
        TPI_Status_Code=getIntent().getStringExtra("TPI_Status_Code");
        Log.e("TPI_Status_Code",TPI_Status_Code);
        Feasibility_Type=getIntent().getStringExtra("Feasibility_Type");
        OnClick_Event();
        sharedPrefs = new SharedPrefs(this);
        getLocationUsingInternet();
    }

    private void OnClick_Event() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();

            }
        });
        approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Feasibility_Type.equals("0")){
                    header_text.setText("Feasibility Pending");
                    switch (radioGroup.getCheckedRadioButtonId())
                    {
                        case R.id.radioButton_riseryes:
                            riserStatus = "Yes";
                            break;
                        default:
                            riserStatus = "No";
                            break;
                    }
                    TPI_Approve();
                }

            }
        });

        spinner_master.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_master.getItemAtPosition(spinner_master.getSelectedItemPosition()).toString();
                Log.e("Society+",country);
                type_of_master=Type_Of_Master.get(position);
                igl_code_Master=Igl_Code_Master.get(position);
                igl_code_group_Maaster=Igl_Code_Group_Master.get(position);
                igl_catagory_Master=Igl_Catalog_Master.get(position);
                catid_Master=CatID_Master.get(position);
                Log.e("catid_Master+++++",catid_Master);
                loadSpinnerType_Of_SubMaster( catid_Master);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_sub_master.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_sub_master.getItemAtPosition(spinner_sub_master.getSelectedItemPosition()).toString();
                Log.e("house_type_name+",country);

                type_of_sub_master=Type_Of_Sub_Master.get(position);
                igl_code=Igl_Code.get(position);
                igl_code_group=Igl_Code_Group.get(position);
                igl_catagory=Igl_Catalog.get(position);
                catid_Sub_Master=CatID_Sub_Master.get(position);
                Log.e("type_of_sub_master",type_of_sub_master);
                Log.e("igl_codesub", igl_code);
                Log.e("igl_code_groupbb", igl_code_group);
                Log.e("catid_Sub_Masterbb", catid_Sub_Master);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_pipe_line.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String pipe_line=  spinner_pipe_line.getItemAtPosition(spinner_pipe_line.getSelectedItemPosition()).toString();
                Log.e("pipe_line+",pipe_line);
                pipeline_catagory=PipeLine_ID.get(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Pipe_Line_Catagory();
    }


    private void loadSpinnerType_Master() {

        Type_Of_Master.clear();
        Igl_Code_Master.clear();
        Igl_Code_Group_Master.clear();
        Igl_Catalog_Master.clear();
        CatID_Master.clear();
        materialDialog = new MaterialDialog.Builder(TPI_Connection_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.d("feas","url status = "+Constants.FEAS_STATUS_DROPDOWN+Status_Master+"?status="+TPI_Status_Code+"&bpno="+bpno);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.FEAS_STATUS_DROPDOWN+Status_Master+"?status="+TPI_Status_Code+"&bpno="+bpno, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    Log.e("master_list",response);
                     /*if(jsonObject.getString("Sucess").equals("true")){

                     }*/
                    JSONArray jsonArray_society=jsonObject.getJSONArray("Bp_Details");
                    for(int i=0;i<jsonArray_society.length();i++){
                        JSONObject jsonObject1=jsonArray_society.getJSONObject(i);
                        String society_name_select=jsonObject1.getString("description");
                        String Catid_code=jsonObject1.getString("code");
                        String igl_code_master=jsonObject1.getString("igl_code");
                        String igl_code_group_master=jsonObject1.getString("igl_code_group");
                        String igl_catalog_master=jsonObject1.getString("igl_catalog");
                        CatID_Master.add(Catid_code);
                        Type_Of_Master.add(society_name_select);
                        Igl_Code_Master.add(igl_code_master);
                        Igl_Code_Group_Master.add(igl_code_group_master);
                        Igl_Catalog_Master.add(igl_catalog_master);

                    }

                    spinner_master.setAdapter(new ArrayAdapter<String>(TPI_Connection_Activity.this, android.R.layout.simple_spinner_dropdown_item, Type_Of_Master));


                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void loadSpinnerType_Of_SubMaster(String catid_Master) {
        Type_Of_Sub_Master.clear();
        Igl_Code.clear();
        Igl_Code_Group.clear();
        Igl_Catalog.clear();
        CatID_Sub_Master.clear();
        materialDialog = new MaterialDialog.Builder(TPI_Connection_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.TYPE_SUBMASTER_STATUS + catid_Master, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // if(jsonObject.getInt("success")==1){
                    Log.e("SubMaster",jsonObject.toString());
                    jsonArray_SubMaster = jsonObject.getJSONArray("Bp_Details");
                    for (int i = 0; i < jsonArray_SubMaster.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_SubMaster.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("sub_status");
                        String CatId_code = jsonObject1.getString("sub_status_code");
                        String igl_code = jsonObject1.getString("igl_code");
                        String igl_code_group = jsonObject1.getString("igl_code_group");
                        String igl_catalog = jsonObject1.getString("igl_catalog");
                        Igl_Code.add(igl_code);
                        Igl_Code_Group.add(igl_code_group);
                        Igl_Catalog.add(igl_catalog);
                        Type_Of_Sub_Master.add(society_name_select);
                        CatID_Sub_Master.add(CatId_code);
                    }

                    spinner_sub_master.setAdapter(new ArrayAdapter<String>(TPI_Connection_Activity.this, android.R.layout.simple_spinner_dropdown_item, Type_Of_Sub_Master));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    private void Pipe_Line_Catagory() {

        PipeLine_Catagory.clear();
        PipeLine_ID.clear();
        materialDialog = new MaterialDialog.Builder(TPI_Connection_Activity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.PIPELINE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    Log.e("master_list",response);
                    // if(jsonObject.getInt("success")==1){
                    JSONObject jsonObject_pipeline=jsonObject.getJSONObject("pipeLineList");
                    JSONArray jsonArray_society=jsonObject_pipeline.getJSONArray("pipelineList");
                    for(int i=0;i<jsonArray_society.length();i++){
                        JSONObject jsonObject1=jsonArray_society.getJSONObject(i);
                        String pileline=jsonObject1.getString("pipelineDescription");
                        String pipelineId=jsonObject1.getString("pipelineId");

                        PipeLine_Catagory.add(pileline);
                        PipeLine_ID.add(pipelineId);
                    }

                    spinner_pipe_line.setAdapter(new ArrayAdapter<String>(TPI_Connection_Activity.this, android.R.layout.simple_spinner_dropdown_item, PipeLine_Catagory));
                    loadSpinnerType_Master( );
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    public void TPI_Approve() {
        if(jsonArray_SubMaster!=null&& jsonArray_SubMaster.length()>0){
            complete_igl_code=igl_code;
            complete_igl_code_group=igl_code_group;
            complete_igl_catagory=igl_catagory;
            complete_catid=catid_Sub_Master;
        }else{
            complete_igl_code=igl_code_Master;
            complete_igl_code_group=igl_code_group_Maaster;
            complete_igl_catagory=igl_catagory_Master;
            complete_catid=catid_Master;
        }

        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.FESABILITY_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());
                            Toast.makeText(TPI_Connection_Activity.this, "" + "Successfully", Toast.LENGTH_SHORT).show();
                            setResult(Activity.RESULT_OK);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                        JSONObject obj = new JSONObject(res);
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    Log.e("lead_no",getIntent().getStringExtra("lead_no"));
                    Log.e("bp_no", getIntent().getStringExtra("Bp_number"));
                    Log.e("cat_id", complete_catid);
                    Log.e("igl_code", complete_igl_code);
                    Log.e("igl_code_group", complete_igl_code_group);
                    Log.e("igl_catalog", complete_igl_catagory);
                    Log.e("mobile_no", getIntent().getStringExtra("Mobile_number"));
                    Log.e("email_id", getIntent().getStringExtra("Email_id"));
                    Log.e("latitude", Latitude);
                    Log.e("longitude", Longitude);
                    Log.e("remarks", descreption_edit.getText().toString().trim());
                    Log.e("riserStatus", riserStatus);
                    // params.put("id", sharedPrefs.getUUID());
                    params.put("lead_no",getIntent().getStringExtra("lead_no"));
                    params.put("bp_no", getIntent().getStringExtra("Bp_number"));
                    params.put("cat_id", complete_catid);
                    params.put("igl_code", complete_igl_code);
                    params.put("igl_code_group", complete_igl_code_group);
                    params.put("igl_catalog", complete_igl_catagory);
                    params.put("mobile_no", getIntent().getStringExtra("Mobile_number"));
                    params.put("email_id", getIntent().getStringExtra("Email_id"));
                    params.put("pipeline_id", pipeline_catagory);
                    params.put("latitude", Latitude);
                    params.put("longitude", Longitude);
                    params.put("remarks", descreption_edit.getText().toString().trim());
                    params.put("riserStatus", riserStatus);


                } catch (Exception e) {
                }
                return params;
            }
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }

    private void getLocationUsingInternet() {
        boolean isInternetConnected = new ConnectionDetector(TPI_Connection_Activity.this).isConnectingToInternet();
        try {
            if (isInternetConnected) {
                // getLocation_usingInternet.setEnabled(false);
                new GPSLocation(TPI_Connection_Activity.this).turnGPSOn();// First turn on GPS
                String getLocation = new GPSLocation(TPI_Connection_Activity.this).getMyCurrentLocation();// Get current location from
                Log.d("getLocation++", getLocation.toString());
                Latitude = GPSLocation.Latitude;
                Longitude = GPSLocation.Longitude;
                Log.d("Latitude++", Latitude);
                Log.d("Longitude++", Longitude);
            } else {
                Toast.makeText(TPI_Connection_Activity.this, "There is no internet connection.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Latitude = "NA";
            Longitude = "NA";
        }
    }
    public void TPI_Multipart(String filePath_img_string) {
        /*if(filePath_img_string==null){
            filePath_img_string="\\/ekyc\\/bp_details\\/fesabilityAdd";
            Log.e("filePath_img_string", "" + filePath_img_string);
        }*/
        if(jsonArray_SubMaster!=null&& jsonArray_SubMaster.length()>0){
            complete_igl_code=igl_code;
            complete_igl_code_group=igl_code_group;
            complete_igl_catagory=igl_catagory;
            complete_catid=catid_Sub_Master;
        }else{
            complete_igl_code=igl_code_Master;
            complete_igl_code_group=igl_code_group_Maaster;
            complete_igl_catagory=igl_catagory_Master;
            complete_catid=catid_Master;
        }
        try {
            materialDialog = new MaterialDialog.Builder(TPI_Connection_Activity.this)
                    .content("Please wait....")
                    .progress(true, 0)
                    .show();
            String uploadId = UUID.randomUUID().toString();
            /*uploadReceiver.setDelegate((SingleUploadBroadcastReceiver.Delegate) this);
            uploadReceiver.setUploadID(uploadId);*/
            MultipartUploadRequest request= new MultipartUploadRequest(TPI_Connection_Activity.this, uploadId, Constants.FESABILITY_ADD_Declined);
            request.addFileToUpload(filePath_img_string, "declinedImage");
            request.addParameter("lead_no", getIntent().getStringExtra("lead_no"));
            request.addParameter("bp_no", getIntent().getStringExtra("Bp_number"));
            request .addParameter("cat_id", complete_catid);
            request .addParameter("igl_code", complete_igl_code);
            request .addParameter("igl_code_group", complete_igl_code_group);
            request.addParameter("igl_catalog", complete_igl_catagory);
            request .addParameter("mobile_no", getIntent().getStringExtra("Mobile_number"));
            request .addParameter("email_id", getIntent().getStringExtra("Email_id"));
            request .addParameter("pipeline_id", pipeline_catagory);
            request.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(Context context,UploadInfo uploadInfo) {

                }
                @Override
                public void onError( Context context,UploadInfo uploadInfo,  Exception exception) {
                    exception.printStackTrace();
                    materialDialog.dismiss();
                    //Dilogbox_Error();
                    Log.e("Uplodeerror++", uploadInfo.getSuccessfullyUploadedFiles().toString());
                }
                @Override
                public void onCompleted(Context context,UploadInfo uploadInfo, ServerResponse serverResponse) {
                    materialDialog.dismiss();
                    String Uplode = uploadInfo.getSuccessfullyUploadedFiles().toString();
                    String serverResponse1 = serverResponse.getHeaders().toString();
                    String str = serverResponse.getBodyAsString();
                    final JSONObject jsonObject;
                    Log.e("UPLOADEsinin++", str);
                    setResult(Activity.RESULT_OK);
                    finish();
                    Toast.makeText(TPI_Connection_Activity.this, "" + "Succesfully Feasibility Approve", Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onCancelled(Context context,UploadInfo uploadInfo) {
                    progressDialog.dismiss();
                }
            });
            request.setUtf8Charset();
            request.setUsesFixedLengthStreamingMode(true);
            request.setMaxRetries(2);

            request.setAutoDeleteFilesAfterSuccessfulUpload(true);
            request.startUpload(); //Starting the upload

            //request.setCustomUserAgent(getUserAgent());
        } catch (Exception exc) {
            Toast.makeText(TPI_Connection_Activity.this, "Please select Image", Toast.LENGTH_SHORT).show();
            materialDialog.dismiss();
        }

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
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(TPI_Connection_Activity.this, getApplicationContext().getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });
        myAlertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                    filePath_Image = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath_Image);
                        // imageView.setImageBitmap(bitmap);
                        image_upload.setImageBitmap(bitmap);
                        //address_image.setImageBitmap(bitmap1);
                        filePath_img_string = getPath(filePath_Image);
                        Log.e("image_path_aadhar+,", "" + filePath_Image);
                        if(Feasibility_Type.equals("0")){
                            TPI_Multipart(filePath_img_string);
                        }

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
                        image_upload.setImageBitmap(bitmap);
                        String path = getExternalStorageDirectory().getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        Log.e("Camera_Path++", file.toString());
                        filePath_img_string = file.toString();
                        // image_path_address1 =file.toString();
                        if(Feasibility_Type.equals("0")){
                            TPI_Multipart(filePath_img_string);
                        }

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
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return path;
    }
}