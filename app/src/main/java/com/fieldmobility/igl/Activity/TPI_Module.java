package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.fieldmobility.igl.Adapter.RFC_Adapter;
import com.fieldmobility.igl.Adapter.TPI_RFC_Pending_Adapter;
import com.fieldmobility.igl.Fragment.HomeFragment;
import com.fieldmobility.igl.Fragment.TPI_Feasibility_pending_Fragment;
import com.fieldmobility.igl.Fragment.TPI_RFC_approval_Fragment;
import com.fieldmobility.igl.Fragment.TPI_RFC_pending_Fragment;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.BpDetail;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TPI_Module extends AppCompatActivity {
    ImageView back , refresh_tpidta;
    SharedPrefs sharedPrefs;
    MaterialDialog materialDialog;
    TextView feas_text , rfcpen_text , rfcapp_text;
    CardView cv_feas,cv_rfcpen,cv_rfcapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpi_module);
        sharedPrefs = new SharedPrefs(this);
        back = findViewById(R.id.back);
        refresh_tpidta = findViewById(R.id.refresh_tpidta);
        feas_text = findViewById(R.id.feas_text);
        rfcpen_text = findViewById(R.id.rfcpen_text);
        rfcapp_text = findViewById(R.id.rfcapp_text);
        cv_feas = findViewById(R.id.cv_feas);
        cv_rfcpen = findViewById(R.id.cv_rfcpen);
        cv_rfcapp = findViewById(R.id.cv_rfcapp);
        refresh_tpidta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDetails();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cv_feas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Fragment fragment = new TPI_Feasibility_pending_Fragment(TPI_Module.this);
                loadFragment(fragment);
            }
        });
        cv_rfcpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TPI_RFC_pending_Fragment(TPI_Module.this);
                loadFragment(fragment);
            }
        });
        cv_rfcapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TPI_RFC_approval_Fragment(TPI_Module.this);
                loadFragment(fragment);
            }
        });
        fetchDetails();
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tpi_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void fetchDetails() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String url = Constants.TPI_DATA+sharedPrefs.getZone_Code()+"&id="+sharedPrefs.getUUID();
        Log.d("tpi",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {

                            final JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response++",jsonObject.toString());
                            final String status = jsonObject.getString("status");
                            String feas= jsonObject.getString("feas");
                            String rfcpen= jsonObject.getString("rfcpen");
                            String rfcapp= jsonObject.getString("rfcapp");
                            rfcapp_text.setText(rfcapp);
                            rfcpen_text.setText(rfcpen);
                            feas_text.setText(feas);


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
                }) ;

        RequestQueue requestQueue = Volley.newRequestQueue(TPI_Module.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}