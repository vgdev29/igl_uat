package com.example.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.igl.Adapter.NgUserClaimListAdapter;
import com.example.igl.Model.NguserListModel;
import com.example.igl.NgUserListViewModel;
import com.example.igl.R;
import com.example.rest.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NgUserClaimListActivity extends AppCompatActivity {

    private RecyclerView recyclerView_claim;
    private NgUserClaimListAdapter adapter;
    private TextView tv_ngUserListdata;
    private RelativeLayout rel_noNgUserListingData;
    private ImageView back;
    private ImageView dummy;
    //private SwipeRefreshLayout mSwipeRefreshLayout;
    private NgUserListViewModel ngUserListViewModel;
    private LinearLayout ll_sort;
    //private RelativeLayout rel_noNgUserListingData;

    private ArrayList<NguserListModel> claimUserList;
    private Button btnTryAgain,btn_claim;
    private MaterialDialog materialDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView list_count;
    private EditText editTextSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpi__claim__un_claim);
        recyclerView_claim = findViewById(R.id.recyclerView_claim);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        back = findViewById(R.id.back);
        list_count=findViewById(R.id.list_count);
        editTextSearch=findViewById(R.id.editTextSearch);
        rel_noNgUserListingData = findViewById(R.id.rel_noNgUserListingData);
        tv_ngUserListdata = findViewById(R.id.tv_ngUserListdata);
        //btn_claim = findViewById(R.id.btn_claim);
        //recyclerView_claim.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_claim.setLayoutManager(layoutManager);

        claimUserList = new ArrayList<>();
        //rel_noNgUserListingData = findViewById(R.id.rel_noNgUserListingData);


        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadNgUserList();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    loadNgUserList();
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  bookadapter.getFilter().filter(s.toString());
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadNgUserList();


    }
    private void loadNgUserList() {
        materialDialog = new MaterialDialog.Builder(NgUserClaimListActivity.this)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<NguserListModel>> call = api.getNgUserListClaimUnclaimList();
        call.enqueue(new Callback<ArrayList<NguserListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguserListModel>> call, Response<ArrayList<NguserListModel>> response) {
                if (response.body()!=null){
                    claimUserList = response.body();
                    setListData(claimUserList);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<NguserListModel>> call, Throwable t) {
                Log.e("MyError" , "error.............");
                setListData(claimUserList);
            }
        });
    }

    private void setListData(final List<NguserListModel> claimUserList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (claimUserList.size()>0) {
                    materialDialog.dismiss();
                    adapter = new NgUserClaimListAdapter(NgUserClaimListActivity.this, claimUserList);
                    recyclerView_claim.setAdapter(adapter);


                }else {
                    materialDialog.dismiss();
                    rel_noNgUserListingData.setVisibility(View.VISIBLE);
                    tv_ngUserListdata.setText("Failed to Fetch data.Please try after some time!!");
                    recyclerView_claim.setVisibility(View.GONE);

                }
            }
        });
    }






}
