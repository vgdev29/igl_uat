package com.example.igl.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igl.Adapter.NgUserListAdapter;
import com.example.igl.Fragment.NgClaim_Tpi_Fragment;
import com.example.igl.Model.NguserListModel;
import com.example.igl.NgUserListViewModel;
import com.example.igl.R;
import com.example.igl.utils.Utils;
import com.example.rest.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NgUserListActivity extends AppCompatActivity {

    private RecyclerView recyclerView_ngAssignment;
    private NgUserListAdapter adapter;
    private ImageView back;
    private LinearLayout ll_sort;
    private RelativeLayout rel_noNgUserListingData;

    private List<NguserListModel> ngUserList;
    private Button btnTryAgain;
    private TextView tv_ngUserListdata;
    private EditText editTextSearch;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng_user_list);

        btnTryAgain = findViewById(R.id.btnTryAgain);
        back = findViewById(R.id.back);
        tv_ngUserListdata = findViewById(R.id.tv_ngUserListdata);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        recyclerView_ngAssignment = findViewById(R.id.recyclerView_ngAssignment);
        recyclerView_ngAssignment.setHasFixedSize(true);
        recyclerView_ngAssignment.setLayoutManager(new LinearLayoutManager(this));
        ngUserList = new ArrayList<>();

        rel_noNgUserListingData = findViewById(R.id.rel_noNgUserListingData);
        editTextSearch=findViewById(R.id.editTextSearch);


        ll_sort = findViewById(R.id.ll_sort);

        ll_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext()," Sort",Toast.LENGTH_SHORT).show();

            }
        });
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


        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  bookadapter.getFilter().filter(s.toString());
                if (adapter!=null)
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    //bp_no_array.clear();
                    //Feasivility_List();
                    if(Utils.isNetworkConnected(NgUserListActivity.this)){
                        loadNgUserList();

                    }else {
                        Utils.showToast(NgUserListActivity.this,"Please check your connectivity");
                        rel_noNgUserListingData.setVisibility(View.VISIBLE);
                        tv_ngUserListdata.setText("Please check your connectivity!!");
                        recyclerView_ngAssignment.setVisibility(View.GONE);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        loadNgUserList();

    }
    private void loadNgUserList() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<NguserListModel>> call = api.getClaimUnclaimList("1");


        call.enqueue(new Callback<ArrayList<NguserListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguserListModel>> call, Response<ArrayList<NguserListModel>> response) {

                //finally we are setting the list to our MutableLiveData
                //ngUserList.setValue(response.body());
                if (response.body()!=null) {
                    ngUserList = response.body();
                    if (ngUserList.size()>0){
                        setListData(ngUserList);
                    }else {

                        recyclerView_ngAssignment.setVisibility(View.GONE);
                        rel_noNgUserListingData.setVisibility(View.VISIBLE);
                        tv_ngUserListdata.setText("No data found!!");
                    }
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView_ngAssignment.setVisibility(View.GONE);
                            rel_noNgUserListingData.setVisibility(View.VISIBLE);
                            tv_ngUserListdata.setText("Failed o fetch data due to some problem please try after some time!!");
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ArrayList<NguserListModel>> call, Throwable t) {
                Log.e("MyError" , "error.............");

                rel_noNgUserListingData.setVisibility(View.VISIBLE);
                recyclerView_ngAssignment.setVisibility(View.GONE);
            }
        });
    }

    private void setListData(final List<NguserListModel> ngUserList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ngUserList.size()>0) {
                        recyclerView_ngAssignment.setVisibility(View.VISIBLE);
                        rel_noNgUserListingData.setVisibility(View.GONE);
                        adapter = new NgUserListAdapter(NgUserListActivity.this, ngUserList);
                        recyclerView_ngAssignment.setAdapter(adapter);

                    }else {
                        rel_noNgUserListingData.setVisibility(View.VISIBLE);
                    }
                }
            });
    }



}
