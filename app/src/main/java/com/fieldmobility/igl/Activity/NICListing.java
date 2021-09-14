package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Adapter.NICListAdapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Model.NiListingModel;
import com.fieldmobility.igl.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class NICListing extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton nicreg;
    RecyclerView recyclerView;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    NiListingModel dataModel;
    NICListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niclisting);
        sharedPrefs=new SharedPrefs(this);

        findViews();
        loadListData();
    }

    private void loadListData() {

        materialDialog = new MaterialDialog.Builder(NICListing.this)
                .content("Please wait....")
                .progress(true, 0)

                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.ni_user_listing + sharedPrefs.getUUID(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                     dataModel = new Gson().fromJson(response, NiListingModel.class);
                    if (dataModel.getStatus().equals("200")){
                        initViews(dataModel);
                    }

                } catch (Exception e) {
                    materialDialog.dismiss();
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void findViews() {
        nicreg = findViewById(R.id.nic_regestration);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nicreg.setOnClickListener(this);
        SwipeRefreshLayout        mSwipeRefreshLayout =  findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              loadListData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == nicreg) {
            Intent intent = new Intent(NICListing.this, NICustomerActivity.class);
            startActivity(intent);
        }
    }

    private void initViews(NiListingModel dataModel) {
        adapter= new NICListAdapter(NICListing.this,dataModel.getNICList());
        recyclerView.setAdapter(adapter);

    }
}