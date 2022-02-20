package com.fieldmobility.igl.Complain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.security.SecurityConstants;
import com.kyanogen.signatureview.SignatureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CompSupStatus extends AppCompatActivity {


    SharedPrefs sharedPrefs;
    ImageView back;
    MaterialDialog materialDialog;
    ArrayList<String> Igl_Code = new ArrayList<>();
    ArrayList<String> Igl_Code_Group = new ArrayList<>();
    ArrayList<String> Igl_Catalog = new ArrayList<>();
    ArrayList<String> Igl_status_description = new ArrayList<>(); 
    ArrayList<String> Igl_CatId = new ArrayList<>();
    ArrayList<Integer> Igl_GimStatus = new ArrayList<>();
    Spinner spinner_master;
    String code="", codegroup="", catalog="",catid="",descrip_status="";
    int girmstat=0;
    Button approve_button,bt_comp_status_sig    ;
    ImageView imv_signature_image;
    Bitmap bitmap;
    TextView ticketnum, address_text, comptype,start_date, start_time;
    static String log = "compstatus";
    TimePickerDialog pickerDialog_Time;
    DatePickerDialog pickerDialog_Date;
    String am_pm1 = "";
    LinearLayout ll_hold_layout;
    String followup = "", remarks = "",supSign="";
    EditText descreption_edit;
    ComplainModel complainModel;
    ComplainMasterModel compMaster = new ComplainMasterModel();
    SignatureView signatureView;
    private static final String IMAGE_DIRECTORY = "/signdemo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_sup_status);

        sharedPrefs = new SharedPrefs(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        complainModel = (ComplainModel) getIntent().getSerializableExtra("data");
         
        spinner_master = (Spinner) findViewById(R.id.spinner_master);
        
        descreption_edit = findViewById(R.id.descreption_edit);
        approve_button = findViewById(R.id.approve_button);
        ticketnum = findViewById(R.id.ticket_num);
        address_text = findViewById(R.id.address_text);
        comptype = findViewById(R.id.comp_type);
         
        start_date = findViewById(R.id.start_date_text);
        start_time = findViewById(R.id.time_text);
        bt_comp_status_sig = findViewById(R.id.bt_comp_status_sig);
        imv_signature_image = findViewById(R.id.imv_comp_status_sig);
        ll_hold_layout = findViewById(R.id.ll_hold_layout);
        inflateData();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        start_date.setText(df.format(c));
        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        start_time.setText(currentTime);
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                pickerDialog_Date = new DatePickerDialog(CompSupStatus.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;

                                if (month < 10) {

                                    formattedMonth = "0" + month;
                                }
                                if (dayOfMonth < 10) {

                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                Log.e("Date", year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);

                                start_date.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                pickerDialog_Date.show();
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                // time picker dialog
                pickerDialog_Time = new TimePickerDialog(CompSupStatus.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hour, int minutes) {

                                //int month = monthOfYear + 1;
                                String formattedHours = "" + hour;
                                String formattedMinut = "" + minutes;

                                if (hour < 10) {

                                    formattedHours = "0" + hour;
                                }
                                if (minutes < 10) {

                                    formattedMinut = "0" + minutes;
                                }
                                Log.e("Date", (formattedHours) + ":" + formattedMinut);
                                if (hour > 12) {
                                    am_pm1 = "PM";
                                    hour = hour - 12;
                                } else {
                                    am_pm1 = "AM";
                                }
                                start_time.setText(formattedHours + ":" + formattedMinut + " " + am_pm1);
                            }
                        }, hour, minutes, true);

                pickerDialog_Time.show();
            }
        });

        bt_comp_status_sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signature_Method();
            }
        });

         


        approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TPI_Approve();

                if (girmstat==1)
                {
                    if (validate()) {
                        Intent intent = new Intent(CompSupStatus.this, ComplainGirm.class);
                        compMaster.setCatalog(catalog);
                        compMaster.setCatId(catid);
                        compMaster.setCodeGroup(codegroup);
                        compMaster.setCode(code);
                        compMaster.setStatusDescription(descrip_status);
                        compMaster.setTicketNo(complainModel.getTicketType());
                        compMaster.setCompType(complainModel.getCompType());
                        compMaster.setBpNum(String.valueOf(complainModel.getBpNumber()));
                        intent.putExtra("data", compMaster);
                        startActivity(intent);
                        finish();
                    }
                }
                else
                {
                    if (validate())
                    {
                        compMaster.setCatalog(catalog);
                        compMaster.setCatId(catid);
                        compMaster.setCodeGroup(codegroup);
                        compMaster.setCode(code);
                        compMaster.setStatusDescription(descrip_status);
                        compMaster.setTicketNo(complainModel.getTicketType());
                        compMaster.setCompType(complainModel.getCompType());
                        compMaster.setSupSign(supSign);
                        compMaster.setSupRemarks(remarks);
                        compMaster.setFollowupDate(followup);
                        compMaster.setBpNum(String.valueOf(complainModel.getBpNumber()));
                        SupHoldUpdate(compMaster);

                    }

                }
            }
        });

        spinner_master.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String country = spinner_master.getItemAtPosition(spinner_master.getSelectedItemPosition()).toString();
                Log.d(log, "Society+= " + country);
                descrip_status = Igl_status_description.get(position);
                code = Igl_Code.get(position);
                codegroup = Igl_Code_Group.get(position);
                catalog = Igl_Catalog.get(position);
                catid = Igl_CatId.get(position);
                girmstat = Igl_GimStatus.get(position);
                if (girmstat==0)
                {
                    ll_hold_layout.setVisibility(View.VISIBLE);
                }
                else{
                    ll_hold_layout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        
        loadSpinnerType_Master();
    }
    private void inflateData() {
        ticketnum.setText("Ticket No. - "+complainModel.getTicketType());
        comptype.setText("Complain Type - "+complainModel.getCompType());
         

    }


    private void loadSpinnerType_Master() {
        Igl_CatId.clear();
        Igl_Catalog.clear();
        Igl_Code_Group.clear();
        Igl_Code.clear();
        Igl_status_description.clear();
        Igl_GimStatus.clear();
        materialDialog = new MaterialDialog.Builder(CompSupStatus.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.COMPLAIN_STATUS + complainModel.getCodeGroup();
        Log.d(log, "spinner master url = " +url );
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
                            String des = jsonObject1.getString("description");
                            String cat = jsonObject1.getString("catId");
                            String code = jsonObject1.getString("code");
                            String cg = jsonObject1.getString("codeGroup");
                            String cata = jsonObject1.getString("catalog");
                            int gim = jsonObject1.getInt("girmStatus");
                            Igl_CatId.add(cat);
                            Igl_status_description.add(des);
                            Igl_Code.add(code);
                            Igl_Catalog.add(cata);
                            Igl_Code_Group.add(cg);
                            Igl_GimStatus.add(gim);
                        }
                    }
                    else
                    {
                        CommonUtils.toast_msg(CompSupStatus.this,"Error finding status");
                    }
                    spinner_master.setAdapter(new ArrayAdapter<String>(CompSupStatus.this, android.R.layout.simple_spinner_dropdown_item, Igl_status_description));
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




    public void SupHoldUpdate(ComplainMasterModel master) {
        materialDialog = new MaterialDialog.Builder(this)
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.COMPLAIN_MASTERSUBMIT,mJSONObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                materialDialog.dismiss();
                try {

                    Log.d(log,response.toString());
                    String status = response.getString("status");
                    if (status.equals("200")) {
                        JSONObject data_object = response.getJSONObject("data");
                        CommonUtils.toast_msg(CompSupStatus.this,response.getString("message"));
                        onBackPressed();
                    }
                    else
                    {
                        CommonUtils.toast_msg(CompSupStatus.this,response.getString("message"));
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


    public void Signature_Method() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        getWindow().setLayout(-1, -1);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle(SecurityConstants.Signature);
        dialog.setCancelable(true);
        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
       Button clear =   dialog.findViewById(R.id.clear);
        Button save =   dialog.findViewById(R.id.save);
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
                if (bitmap != null) {
                    supSign = change_to_binary(bitmap);
                    imv_signature_image.setImageBitmap(bitmap);
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




    public boolean validate() {
        boolean isDataValid = true;
        if (girmstat==1) {
            if (catid.length() == 0 || code.length() == 0 || codegroup.length() == 0 || catalog.length() == 0 || descrip_status.length() == 0) {
                isDataValid = false;
                Toast.makeText(CompSupStatus.this, "Select Status type", Toast.LENGTH_SHORT).show();
            }
            else {
                isDataValid = true;
            }
        }
        else if (girmstat==0)
        {
            followup = start_date.getText().toString().trim() + " " + start_time.getText().toString().trim();
            remarks = descreption_edit.getText().toString().trim();
           if (remarks.isEmpty() )
           {
               isDataValid = false;
               descreption_edit.setError("Can't be empty");
               Toast.makeText(CompSupStatus.this, "Please Enter Remarks", Toast.LENGTH_SHORT).show();
           }
           else if (followup.isEmpty() )
            {
                isDataValid = false;
                Toast.makeText(CompSupStatus.this, "Please Enter Follow Up date", Toast.LENGTH_SHORT).show();
            }
           else if (supSign.isEmpty())
           {
               isDataValid = false;
               Toast.makeText(CompSupStatus.this, "Please Select Signature", Toast.LENGTH_SHORT).show();
           }
           else
           {
               isDataValid = true;
           }
        }
        return isDataValid;
    }



}