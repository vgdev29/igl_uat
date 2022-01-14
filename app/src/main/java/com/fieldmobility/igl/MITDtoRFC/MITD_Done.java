package com.fieldmobility.igl.MITDtoRFC;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.fieldmobility.igl.Activity.RFC_StatusMastar_Page;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.RiserTpiListingModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Riser.activity.RiserTpiApprovalActivity;
import com.google.gson.Gson;
import com.kyanogen.signatureview.SignatureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MITD_Done extends AppCompatActivity {

    ProgressDialog materialDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    Bp_No_Item bp_no_item = new Bp_No_Item();
    TextView header_title, bpno, custname, mob, metermake, meterno, metertype, initialreading,  leadno,
              regulatorno, giinstallation, cuinstallation,
              gastype, proptype, tfavail, connectivity,rfctype, expipelength;
    LinearLayout top_layout;
    Button approve;
    String BP_NO, LEAD_NO,   customername, custmob, tf_avail="",Connectivity="" , bp_no,lead_no,meter_no;
    private String statcode = "";
    Spinner spinner_master;
    ArrayList<String> code_description = new ArrayList<>();
    ArrayList<String> CatID_Master = new ArrayList<>();
    ArrayList<String> Igl_Code_Master = new ArrayList<     >();
    ArrayList<String> Igl_Code_Group_Master = new ArrayList<>();
    ArrayList<String> Igl_Catalog_Master = new ArrayList<>();
    String igl_code_Master, igl_code_group_Maaster, igl_catagory_Master, catid_Master,catid_description,master_cat_id="";
    private String log= "mitddone";
    private String code_group="";
    private String TPI_Status_Code = "1";
    RadioGroup rg_tf, rg_conn;
    private String connectivity_Done = "Yes",tf_done = "Yes";
    EditText extraGi12,extraGi34,extraGi1,extraGi2,extraGi;
    RiserTpiListingModel dataModel;
    TextView tv_riserno,tv_linkedbp,tv_riserdate,tv_gi12,tv_gi34,tv_gi1,tv_gi2;
    String riserStat = "0";
    LinearLayout ll_riserlength, ll_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitd_done);
        materialDialog = new ProgressDialog(this);
        materialDialog.setTitle("Please wait...");
        materialDialog.setCancelable(true);
        materialDialog.setCanceledOnTouchOutside(true);
        sharedPrefs = new SharedPrefs(this);
        BP_NO = getIntent().getStringExtra("Bp_number");
        LEAD_NO = getIntent().getStringExtra("leadno");
        customername = getIntent().getStringExtra("custname");
        custmob = getIntent().getStringExtra("mobile");
        code_group = getIntent().getStringExtra("codeGroup");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        top_layout = findViewById(R.id.top_layout);
        header_title = findViewById(R.id.header_title);
        header_title.setText("Testing & Commission");
        bpno = findViewById(R.id.txt_bpno);
        bpno.setText(bp_no);
        expipelength = findViewById(R.id.txt_expipelength);
        custname = findViewById(R.id.txt_custname);
        custname.setText(customername);
        mob = findViewById(R.id.txt_mob);
        mob.setText(custmob);
        metermake = findViewById(R.id.txt_metermake);
        metertype = findViewById(R.id.txt_metertype);
        meterno = findViewById(R.id.txt_meterno);
        initialreading = findViewById(R.id.txt_initialread);

        rfctype = findViewById(R.id.txt_rfctype);

        metermake = findViewById(R.id.txt_metermake);

        metertype = findViewById(R.id.txt_metertype);
        regulatorno = findViewById(R.id.txt_regulatorno);
        giinstallation = findViewById(R.id.txt_giinstallation);
        cuinstallation = findViewById(R.id.txt_cuinstallation);

        gastype = findViewById(R.id.txt_gastype);
        proptype = findViewById(R.id.txt_proptype);
        tfavail = findViewById(R.id.txt_tfavail);
        connectivity = findViewById(R.id.txt_connectivity);

        approve = findViewById(R.id.approve_button);
        leadno = findViewById(R.id.txt_leadno);
        leadno.setText(LEAD_NO);
        spinner_master = findViewById(R.id.spinner_master);
        rg_tf = findViewById(R.id.rg_tf);
        rg_conn = findViewById(R.id.rg_conn);
        extraGi12 = findViewById(R.id.et_extragi12);
        extraGi34 = findViewById(R.id.et_extragi34);
        extraGi1 = findViewById(R.id.et_extragi1);
        extraGi2 = findViewById(R.id.et_extragi2);
        extraGi = findViewById(R.id.et_extragi);
        tv_gi1 = findViewById(R.id.txt_gi1);
        tv_gi2 = findViewById(R.id.txt_gi2);
        tv_gi12 = findViewById(R.id.txt_gi12);
        tv_gi34 = findViewById(R.id.txt_gi34);
        tv_riserdate = findViewById(R.id.txt_riserdate);
        tv_riserno = findViewById(R.id.txt_riserno);
        tv_linkedbp = findViewById(R.id.txt_linkedbp);
        ll_riserlength = findViewById(R.id.ll_riserlength);
        ll_length = findViewById(R.id.ll_length);


        spinner_master.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_master.getItemAtPosition(spinner_master.getSelectedItemPosition()).toString();
                Log.d(log, "Society+= " + country);
                catid_description = code_description.get(position);
                //code_description_id=CatID_Master.get(position);
                //loadSpinner_reasion_city(city_id);
                igl_code_Master = Igl_Code_Master.get(position);
                igl_code_group_Maaster = Igl_Code_Group_Master.get(position);
                igl_catagory_Master = Igl_Catalog_Master.get(position);
                catid_Master = CatID_Master.get(position);
                master_cat_id = CatID_Master.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
             Log.d(log,"extraGi12 - "+extraGi12.getText().toString().trim()+" extraGi34 - "+extraGi34.getText().toString().trim()+"  extraGi1 - "+extraGi1.getText().toString().trim()+"  extraGi2 - "+extraGi2.getText().toString().trim()+"  connectivity done = "+connectivity_Done+"  tf_done = "+ tf_done);
              Log.d(log,catid_description+" "+catid_Master+" "+master_cat_id+" "+igl_catagory_Master+" "+igl_code_Master+" "+igl_code_group_Maaster);
            }
        });

        this.rg_tf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_tf_pos:
                        tf_done = "Yes";
                        return;
                    case R.id.rb_tf_neg:
                        tf_done = "No";
                        return;
                    default:
                        return;
                }
            }
        });
        this.rg_conn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_conn_pos:
                        connectivity_Done = "Yes";
                        return;
                    case R.id.rb_conn_neg:
                        connectivity_Done = "No";
                        return;
                    default:
                        return;
                }
            }
        });




        //Bp_No_List();
        RFC_Data();
        loadSpinnerType_Master();
        findRiser();
    }



    public void RFC_Data() {
        materialDialog.show();
        Log.d("mitd done",Constants.RFCMobDetails + "/" + BP_NO);
        StringRequest r2 = new StringRequest(0, Constants.RFCMobDetails + "/" + BP_NO, new Response.Listener<String>() {
            public void onResponse(String str) {

                materialDialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Log.e("Response++", jSONObject.toString());

                    int i = 0;

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
                        String extrapipe = jSONObject2.getString("extraPipeLength");
                        String manufactureMakeDescription = jSONObject2.getString("manufactureMakeDescription");
                        bpno.setText(BP_NO);
                        leadno.setText(LEAD_NO);
                        custname.setText(customername);
                        mob.setText(custmob);

                        metermake.setText(meter_make);
                        meterno.setText(meter_no);
                        metertype.setText(manufactureMakeDescription);
                        initialreading.setText(bp_no_item.getOwnerName());



                        metermake.setText(meter_make);

                        meterno.setText(meter_no);
                        initialreading.setText(initialReading);
                        regulatorno.setText(regulator);
                        giinstallation.setText(gi_installation);
                        cuinstallation.setText(cu_installation);
                        bpno.setText(bp_no);
                        rfctype.setText(rfc_type);
                        gastype.setText(gasType);
                        proptype.setText(propertyType);
                        tfavail.setText(tf_avail);
                        connectivity.setText(Connectivity);
                        expipelength.setText(extrapipe);

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

    private void loadSpinnerType_Master() {
        CatID_Master.clear();
        Igl_Catalog_Master.clear();
        Igl_Code_Group_Master.clear();
        Igl_Catalog_Master.clear();
        code_description.clear();
        materialDialog.show();
        Log.d(log, "spinner master url = " + Constants.TYPE_MASTER_STATUS + code_group + "?status=" + TPI_Status_Code+"&bpno="+BP_NO);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.TYPE_MASTER_STATUS + code_group + "?status=" + TPI_Status_Code+"&bpno="+BP_NO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("master_list", response);
                     /*if(jsonObject.getString("Sucess").equals("true")){
                     }*/
                    JSONArray jsonArray_society = jsonObject.getJSONArray("Bp_Details");
                    for (int i = 0; i < jsonArray_society.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_society.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("description");
                        String Catid_code = jsonObject1.getString("code");
                        String igl_code_master = jsonObject1.getString("igl_code");
                        String igl_code_group_master = jsonObject1.getString("igl_code_group");
                        String igl_catalog_master = jsonObject1.getString("igl_catalog");
                        CatID_Master.add(Catid_code);
                        code_description.add(society_name_select);
                        Igl_Code_Master.add(igl_code_master);
                        Igl_Code_Group_Master.add(igl_code_group_master);
                        Igl_Catalog_Master.add(igl_catalog_master);
                    }
                    spinner_master.setAdapter(new ArrayAdapter<String>(MITD_Done.this, android.R.layout.simple_spinner_dropdown_item, code_description));
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


    private void findRiser() {

        materialDialog.show();
        String url = Constants.FINDRISER + "/" + "6764467378";// "6764467378" BP_NO;
        Log.d("riser", url);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    materialDialog.dismiss();
                final JSONObject jsonObject = new JSONObject(response);
                Log.d(log,"Response++"+jsonObject.toString());
                final String status = jsonObject.getString("status");
                if (status.equals("200")) {
                    riserStat="1";
                    ll_length.setVisibility(View.GONE);
                    ll_riserlength.setVisibility(View.VISIBLE);
                    dataModel = new Gson().fromJson(response, RiserTpiListingModel.class);
                    if (dataModel != null) {
                        Log.d(log, ""+dataModel.getBpDetails().size());
                        tv_riserno.setText(dataModel.getBpDetails().get(0).getRiserNo());
                        tv_linkedbp.setText(dataModel.getBpDetails().get(0).getBpNumber());
                        tv_riserdate.setText(dataModel.getBpDetails().get(0).getCompletionDate());
                        tv_gi1.setText(dataModel.getBpDetails().get(0).getRl1());
                        tv_gi2.setText(dataModel.getBpDetails().get(0).getRl2());
                        tv_gi12.setText(dataModel.getBpDetails().get(0).getRl12());
                        tv_gi34.setText(dataModel.getBpDetails().get(0).getRl34());

                    }
                }

                } catch (Exception e) {
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


    public void submitData()
    {
        materialDialog.show();
        String url = Constants.SUBMITMITD;
        Log.d(log,"bp no list = "+url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.d(log,"Response++"+jsonObject.toString());
                            final String status = jsonObject.getString("status");
                            if (status.equals("200")) {
                                CommonUtils.toast_msg(MITD_Done.this,jsonObject.getString("Message"));
                                back.performClick();
                            }
                            else
                            {
                                CommonUtils.toast_msg(MITD_Done.this,jsonObject.getString("Message"));
                            }


                        }catch (Exception e) {
                            Log.d(log,"catch3");
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(log,"error"+error.getMessage()+error.networkResponse);
                        materialDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("bp", BP_NO);
                    params.put("riser_stat", riserStat);
                    params.put("riser_no", tv_riserno.getText().toString().trim());
                    params.put("tf", tf_done);
                    params.put("conn", connectivity_Done);
                    params.put("gi12", extraGi12.getText().toString().trim());
                    params.put("gi34", extraGi34.getText().toString().trim());
                    params.put("gi1", extraGi1.getText().toString().trim());
                    params.put("gi2", extraGi2.getText().toString().trim());
                    params.put("gi", extraGi.getText().toString().trim());
                    params.put("cat_id", catid_Master);
                    params.put("cat_desc", catid_description);
                    params.put("cat_code", igl_code_Master);
                    params.put("cat_catalog", igl_catagory_Master);

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