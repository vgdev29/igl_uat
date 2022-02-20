package com.fieldmobility.igl.Complain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.security.SecurityConstants;
import com.kyanogen.signatureview.SignatureView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ComplainGirm extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    ImageView back;
    Spinner spinner_order, spinner_service, spinner_material;
    ArrayList<ComplainOrderModel> orderlist = new ArrayList<>();
    ComplainOrderModel selectedOrder ;
    ArrayList<CompMatMaster> materialist = new ArrayList<>();
    CompMatMaster selectedMaterial;
    ArrayList<CompServMaster> servicelist = new ArrayList<>();
    CompServMaster selectedService;

    ArrayList<CompMatMaster> selectedmaterialist = new ArrayList<>();
    ArrayList<CompServMaster> selectedservicelist = new ArrayList<>();

    static String log = "compgirm";
    SignatureView supsignatureView;
    SignatureView cussignatureView;
    ImageView imv_sup_image,imv_cus_image;
    Button approve_button,bt_cus_sig,bt_sup_sig ;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    TextView ticket_num,comp_type;
    ComplainMasterModel masterModel;
    Service_Adapter serviceAdapter;
    Material_Adapter materialAdapter;
    RecyclerView rv_serv, rv_mat;
    String supSign="", cusSign="", feedSatis="",feedDissatis="",feedLeak="",feedSmell="";
    Bitmap supBitmap,cusBitmap;
    RadioGroup rg_sats,rg_dissats,rg_smell,rg_leak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_girm);
        back = findViewById(R.id.back);
        spinner_service = findViewById(R.id.spinner_service);
        spinner_order = findViewById(R.id.spinner_order);
        spinner_material = findViewById(R.id.spinner_material);
        ticket_num = findViewById(R.id.ticket_num);
        comp_type = findViewById(R.id.comp_type);
        rv_mat = findViewById(R.id.rv_material_consumed);
        rv_serv = findViewById(R.id.rv_service_consumed);
        imv_cus_image = findViewById(R.id.imv_customer_sig);
        imv_sup_image = findViewById(R.id.imv_sup_sig);
        approve_button = findViewById(R.id.approve_button);
        bt_cus_sig = findViewById(R.id.bt_customer_sig);
        bt_sup_sig = findViewById(R.id.bt_sup_sig);
        rg_dissats = findViewById(R.id.rg_notsatisfied);
        rg_sats = findViewById(R.id.rg_satisfied);
        rg_smell = findViewById(R.id.rg_smell);
        rg_leak = findViewById(R.id.rg_leak);
        rg_leak.setOnCheckedChangeListener(this);
        rg_smell.setOnCheckedChangeListener(this);
        rg_sats.setOnCheckedChangeListener(this);
        rg_dissats.setOnCheckedChangeListener(this);

        bt_sup_sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supSignatureDialog();
            }
        });
        bt_cus_sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cusSignatureDialog();
            }
        });

        rv_serv.setHasFixedSize(true);
        rv_serv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        serviceAdapter = new Service_Adapter(ComplainGirm.this,selectedservicelist);
        rv_serv.setAdapter(serviceAdapter);

        rv_mat.setHasFixedSize(true);
        rv_mat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        materialAdapter = new Material_Adapter(ComplainGirm.this,selectedmaterialist);
        rv_mat.setAdapter(materialAdapter);
        loadSpinner_Order();
        if (getIntent()!=null)
        {
            masterModel = (ComplainMasterModel) getIntent().getSerializableExtra("data");
        }
        ticket_num.setText("Ticket no :- "+masterModel.getTicketNo());
        comp_type.setText("Complain type :- "+masterModel.getCompType());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    masterModel.setCustomerSign(cusSign);
                    masterModel.setSupSign(supSign);
                    masterModel.setSatisfactionNo(feedSatis);
                    masterModel.setUnsatisfactionNo(feedDissatis);
                    masterModel.setLeakTest(feedLeak);
                    masterModel.setSmellTest(feedSmell);
                    masterModel.setOrderName(selectedOrder.orderName);
                    masterModel.setOrderType(selectedOrder.getOrderType());
                   SupGirmUpdate(masterModel);
                }

            }
        });

        spinner_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedOrder = (ComplainOrderModel) adapterView.getSelectedItem();
                loadSpinner_Service(selectedOrder.getOrderType());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(log,"position - "+position+" long- "+l);
                selectedService = (CompServMaster) adapterView.getSelectedItem();
                ServQtyRemarks_dialog();

                CommonUtils.toast_msg(ComplainGirm.this,"service list size = "+selectedservicelist.size());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedMaterial = (CompMatMaster) adapterView.getSelectedItem();
                MatQtyRemarks_dialog();
                CommonUtils.toast_msg(ComplainGirm.this,"material list size = "+selectedmaterialist.size());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void loadSpinner_Order() {
        orderlist.clear();

        MaterialDialog   materialDialog = new MaterialDialog.Builder(ComplainGirm.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.COMPLAIN_ORDER ;
        Log.d(log, "spinner order url = " +url );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    Log.d("Response++",jsonObject.toString());
                    final String status = jsonObject.getString("status");
                    if (status.equals("200")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            ComplainOrderModel orderModel = new ComplainOrderModel();
                            orderModel.setOrderType(jsonObject1.getString("orderType"));
                            orderModel.setOrderName( jsonObject1.getString("orderName"));

                            orderlist.add(orderModel);

                        }
                    }
                    else
                    {
                        CommonUtils.toast_msg(ComplainGirm.this,jsonObject.getString("message"));
                    }
                    spinner_order.setAdapter(new ArrayAdapter<ComplainOrderModel>(ComplainGirm.this, android.R.layout.simple_spinner_dropdown_item, orderlist));
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

    private void loadSpinner_Service(String orderType) {
        servicelist.clear();

       MaterialDialog materialDialog = new MaterialDialog.Builder(ComplainGirm.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.COMPLAIN_SERVICE+orderType ;
        Log.d(log, "spinner service url = " +url );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    Log.d("Response++",jsonObject.toString());
                    final String status = jsonObject.getString("status");
                    if (status.equals("200")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            CompServMaster serviceModel = new CompServMaster();
                            serviceModel.setTicketNo(masterModel.getTicketNo());
                            serviceModel.setCompType(masterModel.getCompType());
                            serviceModel.setServiceNo( jsonObject1.getInt("serviceNo"));
                            serviceModel.setServiceDescription( jsonObject1.getString("serviceDescription"));
                            serviceModel.setUnit( jsonObject1.getString("unit"));
                            serviceModel.setRate( jsonObject1.getDouble("rate"));


                            servicelist.add(serviceModel);

                        }
                    }
                    else
                    {
                        CommonUtils.toast_msg(ComplainGirm.this,jsonObject.getString("message"));
                    }
                    spinner_service.setAdapter(new ArrayAdapter<CompServMaster>(ComplainGirm.this, android.R.layout.simple_spinner_dropdown_item, servicelist));
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

    private void loadSpinner_Material(String orderType) {
        materialist.clear();

        MaterialDialog  materialDialog = new MaterialDialog.Builder(ComplainGirm.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.COMPLAIN_MATERIAL+orderType ;
        Log.d(log, "spinner material url = " +url );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    Log.d("Response++",jsonObject.toString());
                    final String status = jsonObject.getString("status");
                    if (status.equals("200")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            CompMatMaster materialModel = new CompMatMaster();
                            materialModel.setTicketNo(masterModel.getTicketNo());
                            materialModel.setCompType(masterModel.getCompType());
                            materialModel.setMaterialNo( jsonObject1.getInt("materialNo"));
                            materialModel.setMaterialDescription( jsonObject1.getString("materialDescription"));
                            materialModel.setUnit( jsonObject1.getString("unit"));
                            materialModel.setRate( jsonObject1.getDouble("rate"));


                            materialist.add(materialModel);

                        }
                    }
                    else
                    {
                        CommonUtils.toast_msg(ComplainGirm.this,jsonObject.getString("message"));
                    }
                    spinner_material.setAdapter(new ArrayAdapter<CompMatMaster>(ComplainGirm.this, android.R.layout.simple_spinner_dropdown_item, materialist));
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


    public void supSignatureDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        getWindow().setLayout(-1, -1);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle(SecurityConstants.Signature);
        dialog.setCancelable(true);
        supsignatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        Button clear =   dialog.findViewById(R.id.clear);
        Button save =   dialog.findViewById(R.id.save);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                supsignatureView.clearCanvas();
            }
        });
        ((ImageView) dialog.findViewById(R.id.crose_img)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                supBitmap = supsignatureView.getSignatureBitmap();
                if (supBitmap != null) {
                    supSign = change_to_binary(supBitmap);
                    imv_sup_image.setImageBitmap(supBitmap);
                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void cusSignatureDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        getWindow().setLayout(-1, -1);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle(SecurityConstants.Signature);
        dialog.setCancelable(true);
        cussignatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        Button clear =   dialog.findViewById(R.id.clear);
        Button save =   dialog.findViewById(R.id.save);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cussignatureView.clearCanvas();
            }
        });
        ((ImageView) dialog.findViewById(R.id.crose_img)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cusBitmap = cussignatureView.getSignatureBitmap();
                if (cusBitmap != null) {
                    cusSign = change_to_binary(cusBitmap);
                    imv_cus_image.setImageBitmap(cusBitmap);
                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private String change_to_binary(Bitmap bitmapOrg) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        return ba1;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        Log.d(log,radioGroup.toString());
        if (radioGroup == rg_dissats) {
            int id = radioGroup.getCheckedRadioButtonId();
            RadioButton clickedButton = findViewById(id);
            feedDissatis = clickedButton.getText().toString().trim();
            Log.d(log,feedDissatis);

        } else if (radioGroup == rg_sats) {
            int id = radioGroup.getCheckedRadioButtonId();
            RadioButton clickedButton = findViewById(id);
            feedSatis = clickedButton.getText().toString().trim();
            Log.d(log,feedSatis);
        }
        else if (radioGroup == rg_smell) {
            int id = radioGroup.getCheckedRadioButtonId();
            RadioButton clickedButton = findViewById(id);
            feedSmell = clickedButton.getText().toString().trim();
            Log.d(log,feedSmell);
        }
        else if (radioGroup == rg_leak) {
            int id = radioGroup.getCheckedRadioButtonId();
            RadioButton clickedButton = findViewById(id);
            feedLeak = clickedButton.getText().toString().trim();
            Log.d(log,feedLeak);
        }

    }

    public boolean validate() {
        boolean isDataValid = true;

            if (feedLeak.isEmpty() ||feedSmell.isEmpty()||feedSatis.isEmpty()||feedDissatis.isEmpty() )
            {
                isDataValid = false;
                Toast.makeText(ComplainGirm.this, "Please take Feedback from Customer", Toast.LENGTH_SHORT).show();
            }
            else if (supSign.isEmpty() )
            {
                isDataValid = false;
                Toast.makeText(ComplainGirm.this, "Please select Representative Signature", Toast.LENGTH_SHORT).show();
            }
            else if (cusSign.isEmpty())
            {
                isDataValid = false;
                Toast.makeText(ComplainGirm.this, "Please Select Customer Signature", Toast.LENGTH_SHORT).show();
            }
            else
            {
                isDataValid = true;
            }

        return isDataValid;
    }

    public void SupGirmUpdate(ComplainMasterModel master) {
       MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String jsonInString = new Gson().toJson(master);
        JSONObject mJSONObject = null;
        try {
            mJSONObject = new JSONObject(jsonInString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(log,"master json = "+mJSONObject.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.COMPLAIN_GIRMMASTERSUBMIT,mJSONObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                materialDialog.dismiss();
                try {

                    Log.d(log,response.toString());
                    String status = response.getString("status");
                    if (status.equals("200")) {
                        JSONObject data_object = response.getJSONObject("data");
                        CommonUtils.toast_msg(ComplainGirm.this,response.getString("message"));
                        SupServUpdate(selectedservicelist,selectedmaterialist);

                    }
                    else
                    {
                        CommonUtils.toast_msg(ComplainGirm.this,response.getString("message"));
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
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }

    public void SupServUpdate(ArrayList<CompServMaster> master , ArrayList<CompMatMaster> mastermat) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        MasterRequest test = new MasterRequest();
        test.setSs(master);
        test.setMs(mastermat);
        String jsonInString = new Gson().toJson(test);
        JSONObject mJSONObject = null;
        try {
            mJSONObject = new JSONObject(jsonInString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(log,"master json = "+mJSONObject.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.COMPLAIN_SERVMASTERSUBMIT,mJSONObject, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                materialDialog.dismiss();
                try {

                    Log.d(log,response.toString());
                    String status = response.getString("status");
                    if (status.equals("200")) {
                        //JSONObject data_object = response.getJSONObject("data");
                        CommonUtils.toast_msg(ComplainGirm.this,response.getString("message"));
                        onBackPressed();
                       //SupMatUpdate(selectedmaterialist);
                    }
                    else
                    {
                        CommonUtils.toast_msg(ComplainGirm.this,response.getString("message"));
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
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }


    private void ServQtyRemarks_dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comp_qty_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setTitle("Signature");
        TextView comp_title = dialog.findViewById(R.id.comp_title);
        comp_title.setText(selectedService.getServiceDescription());
        Button save=dialog.findViewById(R.id.ok_button);
        Button cancel=dialog.findViewById(R.id.cancel_button);
        EditText qty =dialog.findViewById(R.id.comp_qty);
        EditText remarks =dialog.findViewById(R.id.comp_remarks);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quant = qty.getText().toString().trim();
                String remr = remarks.getText().toString().trim();
                if (quant.isEmpty())
                {
                    qty.setError("Please Enter Quantity");
                }
                else
                {
                    Double amt = selectedService.getRate()*Double.parseDouble(quant);
                    selectedService.setQty(Double.parseDouble(quant));
                    selectedService.setRemarks(remr);
                    selectedService.setAmt(amt);
                    selectedservicelist.add(selectedService);
                    serviceAdapter.setData(selectedservicelist);
                    dialog.dismiss();
                    loadSpinner_Material(selectedOrder.getOrderType());
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // finish();
            }
        });


        dialog.show();
    }

    private void MatQtyRemarks_dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comp_qty_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setTitle("Signature");
        TextView comp_title = dialog.findViewById(R.id.comp_title);
        comp_title.setText(selectedMaterial.getMaterialDescription());
        Button save=dialog.findViewById(R.id.ok_button);
        Button cancel=dialog.findViewById(R.id.cancel_button);
        EditText qty =dialog.findViewById(R.id.comp_qty);
        EditText remarks =dialog.findViewById(R.id.comp_remarks);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quant = qty.getText().toString().trim();
                String remr = remarks.getText().toString().trim();
                if (quant.isEmpty())
                {
                    qty.setError("Please Enter Quantity");
                }
                else
                {
                    Double amt = selectedMaterial.getRate()*Double.parseDouble(quant);
                    selectedMaterial.setQty(Double.parseDouble(quant));
                    selectedMaterial.setRemarks(remr);
                    selectedMaterial.setAmt(amt);
                    selectedmaterialist.add(selectedMaterial);
                    materialAdapter.setData(selectedmaterialist);
                    dialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // finish();
            }
        });


        dialog.show();
    }


}