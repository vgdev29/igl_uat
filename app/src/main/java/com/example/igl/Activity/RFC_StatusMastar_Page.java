package com.example.igl.Activity;

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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.igl.Helper.AppController;
import com.example.igl.Helper.CommonUtils;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.R;
import com.kyanogen.signatureview.SignatureView;

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
import java.util.Map;
import java.util.UUID;

import static android.os.Environment.getExternalStorageDirectory;

public class RFC_StatusMastar_Page extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    MaterialDialog materialDialog;
    ArrayList<String> Type_Of_Sub_Master = new ArrayList<>();
    ArrayList<String> CatID_Sub_Master= new ArrayList<>();
    ArrayList<String> Igl_Code = new ArrayList<>();
    ArrayList<String> Igl_Code_Group = new ArrayList<>();
    ArrayList<String> Igl_Catalog = new ArrayList<>();
    ArrayList<String> Type_Of_Master = new ArrayList<>();
    ArrayList<String> Type_Of_Master_ID = new ArrayList<>();
    ArrayList<String> CatID_Master = new ArrayList<>();
    ArrayList<String> Igl_Code_Master = new ArrayList<>();
    ArrayList<String> Igl_Code_Group_Master = new ArrayList<>();
    ArrayList<String> Igl_Catalog_Master = new ArrayList<>();
    ArrayList<String> PipeLine_Catagory = new ArrayList<>();
    ArrayList<String> PipeLine_ID = new ArrayList<>();
    ArrayList<String> Igl_Pipe_line = new ArrayList<>();

    Spinner spinner_master,spinner_sub_master,spinner_pipe_line;
    String type_of_master,type_of_sub_master="",type_of_master_id,igl_code,igl_code_group,igl_catagory,catid_Sub_Master;
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
    TextView bp_no_text,address_text,header_text,pipleline_text,fesibility_person_name_text,fesibility_person_no_text,rfc_person_no_text,rfc_person_name_text;
    String complete_igl_code,complete_igl_code_group,complete_igl_catagory,complete_catid,pipeline_catagory;
    SignatureView signatureView;
    Button clear,save;
    String signature;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    ImageView signature_image;
    LinearLayout pipeline_layout;
    static String log = "rfcstatusmaster";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rfc_approval_status_mastar);
        sharedPrefs = new SharedPrefs(this);
        back=findViewById(R.id.back);
        spinner_master=(Spinner)findViewById(R.id.spinner_master);
        spinner_sub_master=(Spinner)findViewById(R.id.spinner_sub_master);
        spinner_pipe_line=(Spinner)findViewById(R.id.spinner_pipe_line);
        descreption_edit=findViewById(R.id.descreption_edit);
        approve_button=findViewById(R.id.approve_button);
        decline_button=findViewById(R.id.decline_button);
        pipleline_text=findViewById(R.id.pipleline_text);
        fesibility_person_name_text=findViewById(R.id.fesibility_person_name_text);
        rfc_person_name_text=findViewById(R.id.rfc_person_name_text);
        fesibility_person_no_text=findViewById(R.id.fesibility_person_no_text);
        rfc_person_no_text=findViewById(R.id.rfc_person_no_text);
        bp_no_text=findViewById(R.id.bp_no_text);
        address_text=findViewById(R.id.address_text);
        header_text=findViewById(R.id.header_text);
        pipeline_layout=findViewById(R.id.pipeline_layout);

        inflateData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TPI_Approve();
            }
        });

        fesibility_person_no_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+ getIntent().getStringExtra("FesabilityTpimobileNo")));
                    startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        spinner_master.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country=  spinner_master.getItemAtPosition(spinner_master.getSelectedItemPosition()).toString();
                Log.d(log,"Society+= "+country);
                type_of_master=Type_Of_Master.get(position);
                //type_of_master_id=CatID_Master.get(position);
                //loadSpinner_reasion_city(city_id);
                igl_code_Master=Igl_Code_Master.get(position);
                igl_code_group_Maaster=Igl_Code_Group_Master.get(position);
                igl_catagory_Master=Igl_Catalog_Master.get(position);
                catid_Master=CatID_Master.get(position);
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

       // Pipe_Line_Catagory();
      loadSpinnerType_Master();
    }

    private void inflateData() {

        if(getIntent().getStringExtra("PipeLine_Length").equals("null")){
            pipleline_text.setText("");
            // pipeline_layout.setVisibility(View.VISIBLE);
            // pipleline_text.setVisibility(View.GONE);
        }else {
            pipleline_text.setText(getIntent().getStringExtra("PipeLine_Length"));
            Log.d(log,"PipeLine_Length = "+getIntent().getStringExtra("PipeLine_Length_Id"));
            // pipeline_catagory=getIntent().getStringExtra("PipeLine_Length_Id");
            // pipeline_layout.setVisibility(View.GONE);
            // pipleline_text.setVisibility(View.VISIBLE);
        }

        fesibility_person_name_text.setText("Feasibility TPI: "+getIntent().getStringExtra("Fesibility_TPI_Name"));
        rfc_person_name_text.setText("RFC Vendor: "+getIntent().getStringExtra("Rfcvendorname"));
        fesibility_person_no_text.setText("Feasibility TPI No: "+getIntent().getStringExtra("FesabilityTpimobileNo"));
        rfc_person_no_text.setText("RFC Vendor No: "+getIntent().getStringExtra("VendorMobileNo"));
        Address=getIntent().getStringExtra("House_no")+" "+getIntent().getStringExtra("House_type")+" "+
                getIntent().getStringExtra("Landmark")+" "+getIntent().getStringExtra("Society")+" "+getIntent().getStringExtra("Area")+" "
                +getIntent().getStringExtra("City_region");
        //intent.putExtra("Fesibility_TPI_Name",Fesibility_TPI_Name);
        //intent.putExtra("PipeLine_Length",PipeLine_Length);
        address_text.setText(Address);
        bp_no_text.setText(getIntent().getStringExtra("Bp_number"));
        Status_Master= getIntent().getStringExtra("igl_code_group");
        Log.d(log,"Status_Master = "+Status_Master);
        TPI_Status_Code=getIntent().getStringExtra("TPI_Status_Code");
        Log.d(log,"TPI_Status_Code = "+TPI_Status_Code);
        Feasibility_Type=getIntent().getStringExtra("Feasibility_Type");
        Log.d(log,"feasibility type  = "+Feasibility_Type);

    }



    private void loadSpinnerType_Master() {
        CatID_Master.clear();
        Igl_Catalog_Master.clear();
        Igl_Code_Group_Master.clear();
        Igl_Catalog_Master.clear();
        Type_Of_Master.clear();
        materialDialog = new MaterialDialog.Builder(RFC_StatusMastar_Page.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        Log.d(log,"spinner master url = "+Constants.TYPE_MASTER_STATUS+Status_Master+"?status="+TPI_Status_Code);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.TYPE_MASTER_STATUS+Status_Master+"?status="+TPI_Status_Code, new Response.Listener<String>() {
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
                    spinner_master.setAdapter(new ArrayAdapter<String>(RFC_StatusMastar_Page.this, android.R.layout.simple_spinner_dropdown_item, Type_Of_Master));
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
        Igl_Code.clear();
        Igl_Code_Group.clear();
        Igl_Catalog.clear();
        Type_Of_Sub_Master.clear();
        CatID_Sub_Master.clear();
        materialDialog = new MaterialDialog.Builder(RFC_StatusMastar_Page.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        Log.d(log,"spinner sub master url = "+Constants.TYPE_SUBMASTER_STATUS + catid_Master);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.TYPE_SUBMASTER_STATUS + catid_Master, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // if(jsonObject.getInt("success")==1){
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
                    spinner_sub_master.setAdapter(new ArrayAdapter<String>(RFC_StatusMastar_Page.this, android.R.layout.simple_spinner_dropdown_item, Type_Of_Sub_Master));
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
        materialDialog = new MaterialDialog.Builder(RFC_StatusMastar_Page.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        Log.d(log,"spinner pipeline url = "+ Constants.PIPELINE);
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
                    spinner_pipe_line.setAdapter(new ArrayAdapter<String>(RFC_StatusMastar_Page.this, android.R.layout.simple_spinner_dropdown_item, PipeLine_Catagory));
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
            Log.d(log,"json array sub master data = "+complete_igl_code+"  "+complete_igl_code_group+"  "+complete_igl_catagory+"  "+complete_catid + type_of_master + type_of_sub_master);
        }else{
            complete_igl_code=igl_code_Master;
            complete_igl_code_group=igl_code_group_Maaster;
            complete_igl_catagory=igl_catagory_Master;
            complete_catid=catid_Master;
            Log.d(log,"json array sub master data = "+complete_igl_code+"  "+complete_igl_code_group+"  "+complete_igl_catagory+"  "+complete_catid + type_of_master + type_of_sub_master);

        }
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        Log.d(log,"tpi approval api = "+Constants.RFCApproval+"?lead_no="+getIntent().getStringExtra("lead_no")+"&bp_no="+getIntent().getStringExtra("Bp_number")+"&cat_id="
        +complete_catid+"&igl_code="+complete_igl_code+"&igl_code_group="+complete_igl_code_group+"&igl_catalog="+complete_igl_catagory+"&mobile_no="+getIntent().getStringExtra("Mobile_number")+
                "&email_id="+getIntent().getStringExtra("Email_id"));
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.RFCApproval,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.d(log,"Response Save "+ json.toString());
                            String Success=json.getString("Sucess");

                            if(Success.equals("true")){
                                String holdCode=json.getString("holdCode");
                                String msg = json.getString("Message");
                                Log.d(log,"hold code =  "+ holdCode);
                                if(holdCode.equals("0")){
                                    CommonUtils.toast_msg(RFC_StatusMastar_Page.this,msg);
                                    finish();

                                }else {
                                    Toast.makeText(RFC_StatusMastar_Page.this, "" + "Proceed for Connection", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RFC_StatusMastar_Page.this, RFC_Connection_Activity.class);
                                    intent.putExtra("Bp_number", getIntent().getStringExtra("Bp_number"));
                                    intent.putExtra("First_name", getIntent().getStringExtra("First_name"));
                                    intent.putExtra("Last_name", getIntent().getStringExtra("Last_name"));
                                    intent.putExtra("lead_no", getIntent().getStringExtra("lead_no"));
                                    startActivity(intent);
                                    finish();
                                }

                            }else {
                                Toast.makeText(RFC_StatusMastar_Page.this, "" + "UnSuccesful", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(log,"catch = "+e.getMessage());
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
                        Log.d(log,"response = "+obj.toString());
                        JSONObject error1=obj.getJSONObject("error");

                        String error_msg=error1.getString("message");
                        Log.d(log,"response = "+error_msg);
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
                    Log.d(log , "lead_no"+getIntent().getStringExtra("lead_no"));
                    Log.d(log,"bp_no"+ getIntent().getStringExtra("Bp_number"));
                    Log.d(log,"cat_id"+ complete_catid);
                    Log.d(log,"igl_code"+ complete_igl_code);
                    Log.d(log,"igl_code_group"+ complete_igl_code_group);
                    Log.d(log,"igl_catalog"+ complete_igl_catagory);
                    Log.d(log,"mobile_no"+ getIntent().getStringExtra("Mobile_number"));
                    Log.d(log,"email_id"+ getIntent().getStringExtra("Email_id"));
                    Log.d(log,"status "+ type_of_master);
                    Log.d(log,"reason"+ type_of_sub_master);

                    // params.put("id", sharedPrefs.getUUID());
                    params.put("lead_no",getIntent().getStringExtra("lead_no"));
                    params.put("bp_no", getIntent().getStringExtra("Bp_number"));
                    params.put("cat_id", complete_catid);
                    params.put("igl_code", complete_igl_code);
                    params.put("igl_code_group", complete_igl_code_group);
                    params.put("igl_catalog", complete_igl_catagory);
                    params.put("mobile_no", getIntent().getStringExtra("Mobile_number"));
                    params.put("email_id", getIntent().getStringExtra("Email_id"));
                    params.put("pipeline_id", getIntent().getStringExtra("PipeLine_Length_Id"));
                    params.put("status", type_of_master);
                    params.put("reason", type_of_sub_master);


                } catch (Exception e) {
                }
                return params;
            }
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                //headers.put(" Content-Type", "text/html");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " +sharedPrefs.getToken());
                return headers;
            }*/
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }

    private void Signature_Method() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle("Signature");
        dialog.setCancelable(true);

        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });
        ImageView crose_img=dialog.findViewById(R.id.crose_img);
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
                signature = saveImage(bitmap);
                signature_image.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
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


}