package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.fieldmobility.igl.Adapter.CheckBpAdapter;
import com.fieldmobility.igl.Adapter.New_BP_NO_Adapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CheckBpActivity extends Activity  {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    MaterialDialog materialDialog;
    private List<Bp_No_Item> bp_no_array;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CheckBpAdapter adapter;
    EditText editTextSearch;
    TextView tv_search;

    LinearLayout top_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bp);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        Layout_ID();
    }

    private void Layout_ID() {

        tv_search = findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSearch.getText().toString().length() == 0)
                    Toast.makeText(CheckBpActivity.this, "Please Enter Mobile No. to Search", Toast.LENGTH_SHORT).show();
                else
                    callSearchApi(editTextSearch.getText().toString());

            }
        });
        top_layout = findViewById(R.id.top_layout);
        top_layout.setVisibility(View.GONE);
        bp_no_array = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch = findViewById(R.id.editTextSearch);
        adapter = new CheckBpAdapter(CheckBpActivity.this);
        recyclerView.setAdapter(adapter);


    }

    public void callSearchApi(String mobileNo) {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.findbpbymob +  mobileNo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            String responseMsg=jsonObject.getString("Message");
                            Toast.makeText(CheckBpActivity.this, responseMsg, Toast.LENGTH_SHORT).show();
                            if (jsonObject.getString("Code").equals("200")){
                                JSONArray data=jsonObject.getJSONArray("bp_details");
                                adapter.setData(data);
                            }
                            else {
                                adapter.clearData();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                        Log.d("Bpcreation", "on error = " + error.getMessage());
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

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }



//    @Override
//    public void onContactSelected(Bp_No_Item contact) {
//        Toast.makeText(getApplicationContext(), "Selected: " + contact.getBp_number() + ", " + contact.getFirst_name(), Toast.LENGTH_LONG).show();
//
//    }
}