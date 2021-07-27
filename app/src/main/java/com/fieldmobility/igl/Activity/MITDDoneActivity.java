package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.sun.mail.imap.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MITDDoneActivity extends AppCompatActivity {
    ProgressDialog materialDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    //  MaterialDialog materialDialog;
    Bp_No_Item bp_no_item = new Bp_No_Item();
    TextView tv_error, header_title, bpno, custname, mob, email, adress, metermake, meterno, metertype, initialreading, clamming, leadno, agencyname,
            consumername, tpiname, vendorname, typenr, regulatorno, giinstallation, cuinstallation, ivno, avno, pvcsleeve, meterinstallation, gasmetertesting,
            cementinghole, painting, txt_expipelength, contractorrep, gastype, proptype, ncap, rfctype, expipelength, holedrilled, mcvtesting;

    CheckBox cb_connectivity, cb_tf_avail;
    String BP_NO, LEAD_NO, customername, custmob,
            custemail, tf_avail = "", Connectivity = "", bp_no, lead_no, meter_no;
    private String statcode = "4";
    EditText editTextSearch;
    Button submit_button;
    ScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_i_t_d_done);
        materialDialog = new ProgressDialog(this);
        materialDialog.setTitle("Please wait...");
        materialDialog.setCancelable(true);
        materialDialog.setCanceledOnTouchOutside(true);
        sharedPrefs = new SharedPrefs(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViews();
        ;
    }

    private void findViews() {
        cb_connectivity = findViewById(R.id.cb_connectivity);
        cb_tf_avail = findViewById(R.id.cb_tf_avail);
        editTextSearch = findViewById(R.id.editTextSearch);
        submit_button = findViewById(R.id.submit_button);
        tv_error = findViewById(R.id.tv_error);
        scrollview = findViewById(R.id.scrollview);
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
        txt_expipelength = findViewById(R.id.txt_expipelength);
        gastype = findViewById(R.id.txt_gastype);
        proptype = findViewById(R.id.txt_proptype);
        ncap = findViewById(R.id.txt_ncapavail);
        leadno = findViewById(R.id.txt_leadno);

        //Bp_No_List();
        findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSearch.getText().length() > 0) {
                    String searchText = editTextSearch.getText().toString().trim();
                    RFC_Data(searchText);
                } else {
                    editTextSearch.setError("Enter BP Number To Search");
                }

            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(MITDDoneActivity.this);
                if (detailJson != null) {
                    try {
                        String codeGroup = "";
                        String bp_number = detailJson.getString("bp");
                        codeGroup = detailJson.getString("codeGroup");
                        int tf = 0, connectivity = 0;
                        connectivity = cb_connectivity.isChecked() ? 1 : 0;
                        tf = cb_tf_avail.isChecked() ? 1 : 0;
                        MITD_DONE(bp_number, codeGroup, tf, connectivity);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void RFC_Data(String Bp_number) {
        materialDialog.show();
        StringRequest r2 = new StringRequest(0, Constants.MITDDetails + "/" + Bp_number, new Response.Listener<String>() {
            public void onResponse(String str) {

                materialDialog.dismiss();

                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Log.e("Response++", jSONObject.toString());
                    if (jSONObject.getString("status").equals("200")) {
                        tv_error.setVisibility(View.GONE);
                        scrollview.setVisibility(View.VISIBLE);
                        parseData(jSONObject);
                    } else {
                        scrollview.setVisibility(View.GONE);
                        tv_error.setVisibility(View.VISIBLE);
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

    public void MITD_DONE(String Bp_number, String codeGroup, int tfValue, int connectivity) {
        materialDialog.show();
        StringRequest r2 = new StringRequest(0, Constants.MITD_DONE + "/" + Bp_number + "/" + codeGroup + "/" + tfValue + "/" + connectivity, new Response.Listener<String>() {
            public void onResponse(String str) {

                materialDialog.dismiss();

                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Log.e("Response++", jSONObject.toString());
                    if (jSONObject.getString("status").equals("200")) {
                        String displayMsg = jSONObject.getString("Message");
                        Toast.makeText(MITDDoneActivity.this, displayMsg, Toast.LENGTH_SHORT).show();
                        scrollview.setVisibility(View.GONE);
                        scrollview.fullScroll(ScrollView.FOCUS_UP);
                        editTextSearch.setText("");
                    } else {
                        Toast.makeText(MITDDoneActivity.this, "Error Occurred.\nPlease try again", Toast.LENGTH_SHORT).show();
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


    private boolean isChecked(String value) {
        if (value != null && value.equalsIgnoreCase("yes"))
            return true;
        else
            return false;
    }

    JSONObject detailJson;

    private void parseData(JSONObject jSONObject) {
        JSONArray jSONArray2 = null;
        try {
            jSONArray2 = jSONObject.getJSONObject("RFCdetails").getJSONArray("rfc");
            for (int i = 0; i < jSONArray2.length(); i++) {
                detailJson = jSONArray2.getJSONObject(i);
                String meter_make = detailJson.getString("meter_make");
                String meter_type = detailJson.getString("meter_type");
                meter_no = detailJson.getString("meter_no");
                String initialReading = detailJson.getString("initial_meter_reading");
                String regulator = detailJson.getString("regulator_no");
                String gi_installation = detailJson.getString("gi_installation");
                String cu_installation = detailJson.getString("cu_installation");
                String no_of_iv = detailJson.getString("no_of_iv");
                String no_of_av = detailJson.getString("no_of_av");
                String meter_installation = detailJson.getString("meter_installation");
                String pvc_sleeve = detailJson.getString("pvc_sleeve");
                String clamping = detailJson.getString("clamming");
                String gas_meter_testing = detailJson.getString("gas_meter_testing");
                String cementing_of_holes = detailJson.getString("cementing_of_holes");
                String painting_of_giPipe = detailJson.getString("painting_of_giPipe");
                String customer1 = detailJson.getString("customer1");
                String customer2 = detailJson.getString("customer2");
                String status = detailJson.getString("status");
                String tpiName = detailJson.getString("tpiName");
                String tpiLastName = detailJson.getString("tpiLastName");
                String vendorName = detailJson.getString("vendorName");
                String vendorLastName = detailJson.getString("vendorLastName");
                String firstName = detailJson.getString("firstName");
                String lastName = detailJson.getString("lastName");
                String caNo = detailJson.getString("caNo");
                String agencyName = detailJson.getString("agencyName");
                String rfc_type = detailJson.getString("rfctype");
                String gasType = detailJson.getString("gasType");
                String propertyType = detailJson.getString("propertyType");
                tf_avail = detailJson.getString("tfAvail");
                Connectivity = detailJson.getString("connectivity");
                String ncapavail = detailJson.getString("nCapAvail");
                String hole_drilled = detailJson.getString("holeDrilled");
                String mcv_testing = detailJson.getString("mcvTesting");
                String extrapipe = detailJson.getString("extraPipeLength");
                String manufactureMakeDescription = detailJson.getString("manufactureMakeDescription");
                bpno.setText(detailJson.getString("bp"));
                leadno.setText(detailJson.getString("leadNo"));
                custname.setText(firstName + " " + lastName);
                mob.setText(detailJson.getString("mobileNo"));
                email.setText(custemail);
                adress.setText(detailJson.getString("address"));
                metermake.setText(meter_make);
                meterno.setText(meter_no);
                metertype.setText(manufactureMakeDescription);
                initialreading.setText(bp_no_item.getOwnerName());
                clamming.setText(bp_no_item.getChequeNo());
                agencyname.setText(agencyName);
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
                txt_expipelength.setText(extrapipe);

                vendorname.setText(vendorName);
                rfctype.setText(rfc_type);
                gastype.setText(gasType);
                proptype.setText(propertyType);

                if (isChecked(Connectivity)) {
                    cb_connectivity.setChecked(true);
                } else {
                    cb_connectivity.setChecked(false);
                }
                if (isChecked(tf_avail)) {
                    cb_tf_avail.setChecked(true);
                } else {
                    cb_tf_avail.setChecked(false);
                }
                ncap.setText(ncapavail);
                holedrilled.setText(hole_drilled);
                mcvtesting.setText(mcv_testing);
                expipelength.setText(extrapipe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}