package com.fieldmobility.igl.Riser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Activity.NICListing;
import com.fieldmobility.igl.Activity.NICustomerActivity;
import com.fieldmobility.igl.Adapter.NICListAdapter;
import com.fieldmobility.igl.Adapter.RiserListAdapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Model.NiListingModel;
import com.fieldmobility.igl.Model.RiserListingModel;
import com.fieldmobility.igl.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RiserListActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    RiserListingModel dataModel;
    RiserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riser__list);
        sharedPrefs = new SharedPrefs(this);
        findViews();
        loadListData();
    }

    private void loadListData() {

        materialDialog = new MaterialDialog.Builder(RiserListActivity.this)
                .content("Please wait....")
                .progress(true, 0)

                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("riser",Constants.RISER_LISTING+"/"+sharedPrefs.getUUID());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.RISER_LISTING+"/"+sharedPrefs.getUUID(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
//                    Type userListType = new TypeToken<ArrayList<RiserListingModel>>(){}.getType();
                    dataModel = new Gson().fromJson(response, RiserListingModel.class);
                    if (dataModel != null) {
                        initViews(dataModel);
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

    SwipeRefreshLayout mSwipeRefreshLayout;
    EditText editTextSearch;

    private void findViews() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerView = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    loadListData();
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    mSwipeRefreshLayout.setRefreshing(false);

                }
            }
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void initViews(RiserListingModel dataModel) {
        adapter = new RiserListAdapter(RiserListActivity.this, dataModel.getBpDetails().getUsers());
        recyclerView.setAdapter(adapter);

    }
}