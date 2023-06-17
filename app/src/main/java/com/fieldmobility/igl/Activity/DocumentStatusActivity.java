package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DocumentStatusActivity extends AppCompatActivity {

    TextView bp_no_text,address_text,remarks_txt;
    Spinner spinner_master;
    ArrayList<String> type_Of_Master;
    ArrayList<String> type_Of_Master_ID;
    ImageView back;
    EditText descreption_edit;
    String address,bpno,codegroup,cat_master="",catid_Master;
    Bp_No_Item bp_No_Item;
    Button approve_button;

    MaterialDialog materialDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_status);
        String dataJson = getIntent().getStringExtra("data");
        if (dataJson != null && !dataJson.isEmpty()) {
            bp_No_Item = new Gson().fromJson(dataJson, Bp_No_Item.class);
            Layout_Id();

        }
    }
    private void Layout_Id() {
        back=findViewById(R.id.back);
        spinner_master=(Spinner)findViewById(R.id.spinner_master);
        descreption_edit=findViewById(R.id.descreption_edit);
        bp_no_text=findViewById(R.id.bp_no_text);
        address_text=findViewById(R.id.address_text);
        approve_button=findViewById(R.id.approve_button);
        type_Of_Master=new ArrayList<>();
        type_Of_Master_ID=new ArrayList<>();

        address=bp_No_Item.getHouse_no()+" "+bp_No_Item.getHouse_type()+" "+
                bp_No_Item.getLandmark()+" "+bp_No_Item.getSociety()+" "+bp_No_Item.getArea()+" "
                +bp_No_Item.getCity_region();

        address_text.setText(address);
        bp_no_text.setText(bp_No_Item.getBp_number());
        bpno = bp_No_Item.getBp_number();
        codegroup = bp_No_Item.getIgl_code_group();
        OnClick_Event();

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
                if(catid_Master.equalsIgnoreCase("EMI_65") || catid_Master.equalsIgnoreCase("PVT_70")|| cat_master.contains("Document Submission pending")){
                    String dataJson = new Gson().toJson(bp_No_Item);
                    Intent intent  = new Intent(DocumentStatusActivity.this, DocumentResumissionDetail.class);//isForResubmition ? DocumentResumissionDetail.class : Bp_Created_Detail.class);
                    intent.putExtra("data", dataJson);
                    startActivity(intent);
                }
                else
                {
                    updateStatus();
                }

            }
        });

        spinner_master.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                cat_master=  spinner_master.getItemAtPosition(spinner_master.getSelectedItemPosition()).toString();
                Log.d("cat_master= ",cat_master);
                cat_master=type_Of_Master.get(position);
                catid_Master=type_Of_Master_ID.get(position);
                Log.d("cat_master= ",cat_master+"catid_Master = "+catid_Master);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        loadSpinnerType_Master();

    }

    private void loadSpinnerType_Master() {

        type_Of_Master.clear();

        materialDialog = new MaterialDialog.Builder(DocumentStatusActivity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.d("feas","url status = "+ Constants.Doc_Resub_Status+codegroup);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.Doc_Resub_Status+codegroup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    Log.e("master_list",response);
                     /*if(jsonObject.getString("Sucess").equals("true")){

                     }*/
                    JSONArray jsonArray_society=jsonObject.getJSONArray("CodeGroupDetails");
                    for(int i=0;i<jsonArray_society.length();i++){
                        JSONObject jsonObject1=jsonArray_society.getJSONObject(i);
                        String description=jsonObject1.getString("description");
                        String Catid_code=jsonObject1.getString("catId");

                        type_Of_Master_ID.add(Catid_code);
                        type_Of_Master.add(description);


                    }

                    spinner_master.setAdapter(new ArrayAdapter<String>(DocumentStatusActivity.this, android.R.layout.simple_spinner_dropdown_item, type_Of_Master));


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

    private void updateStatus() {
        String remarks = descreption_edit.getText().toString().trim();
        materialDialog = new MaterialDialog.Builder(DocumentStatusActivity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.d("feas","url status = "+ Constants.Doc_Resub_Status+codegroup);
        StringRequest stringRequest=new StringRequest(Request.Method.PUT, Constants.Doc_Resub_Submit+catid_Master+"&igl_bp_creation_no="+bpno+"&remarks="+remarks, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("master_list",response);

                     /* {
                        "Details": "Done",
                            "Message": "Inserted successfull of user details",
                            "Sucess": "true",
                            "Code": "200"
                    }*/
                     String code = jsonObject.get("Code").toString();
                    String msg = jsonObject.get("Message").toString();
                     if (code.equalsIgnoreCase("200")){
                         Utils.showToast(DocumentStatusActivity.this,msg);
                         finish();
                         onBackPressed();
                     }
                     else{
                         Utils.showToast(DocumentStatusActivity.this,msg);
                     }
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
}