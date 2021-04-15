package com.example.igl.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.igl.Adapter.NgUserClaimListAdapter;
import com.example.igl.Adapter.NgUserDone_DeclineListAdapter;
import com.example.igl.Adapter.NguserDeclineListAdapter;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.Model.NguserListModel;
import com.example.igl.R;
import com.example.igl.utils.Utils;
import com.example.rest.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Ng_Decline_Activity extends Activity {

    private ProgressDialog progressDialog;
    private SharedPrefs sharedPrefs;
    private ImageView back,new_regestration;
    private MaterialDialog materialDialog;
    private List<Bp_No_Item> bp_no_array;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    //TPI_Fesivility_Adapter tpi_inspection_adapter;
    private EditText editTextSearch;
    //private TextView header_title;
    private TextView list_count,header_title;

    View root;
    private Button btnTryAgain;
    private LinearLayout top_layout;


    private ArrayList<NguserListModel> claimUserList= new ArrayList<>();
    private NguserDeclineListAdapter adapter;
    private RelativeLayout rel_noNgUserListingData;
    private TextView tv_ngUserListdata;
    private int responseCode;


    public Ng_Decline_Activity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpi__claim__un_claim);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        Layout_ID();
    }

    private void Layout_ID() {
        top_layout=findViewById(R.id.top_layout);
        top_layout.setVisibility(View.GONE);
        list_count=findViewById(R.id.list_count);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_claim);
        claimUserList= new ArrayList<>();
        header_title=findViewById(R.id.header_title);
        rel_noNgUserListingData = findViewById(R.id.rel_noNgUserListingData);
        tv_ngUserListdata = findViewById(R.id.tv_ngUserListdata);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        header_title.setText("TPI");


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {

                    if(Utils.isNetworkConnected(Ng_Decline_Activity.this)){
                        loadNgUserList();
                    }else {
                        Utils.showToast(Ng_Decline_Activity.this,"Please check your connectivity");
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch=findViewById(R.id.editTextSearch);
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
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isNetworkConnected(Ng_Decline_Activity.this)){
                    loadNgUserList();
                }else {
                    Utils.showToast(Ng_Decline_Activity.this,"Please check your connectivity");
                    rel_noNgUserListingData.setVisibility(View.VISIBLE);
                    tv_ngUserListdata.setText("Please check your connectivity!!");
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });


        if(Utils.isNetworkConnected(Ng_Decline_Activity.this)){
            loadNgUserList();
        }else {
            Utils.showToast(Ng_Decline_Activity.this,"Please check your connectivity");
        }
    }
    private void loadNgUserList() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<NguserListModel>> call = api.getNgPendingList("DC");
        call.enqueue(new Callback<ArrayList<NguserListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguserListModel>> call, retrofit2.Response<ArrayList<NguserListModel>> response) {
                //if (response.isSuccessful()){
                    responseCode = response.code();
                    if (response.body()!=null){
                        claimUserList = response.body();
                        setListData(claimUserList,responseCode);

                    }else {
                        setListData(claimUserList,responseCode);

                    }

                //}
            }

            @Override
            public void onFailure(Call<ArrayList<NguserListModel>> call, Throwable t) {
                Log.e("MyError" , "error.............");
                setListData(claimUserList,responseCode);
            }
        });
    }
    private void setListData(final List<NguserListModel> claimUserList, final int responseCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (claimUserList.size()>0) {
                    materialDialog.dismiss();
                    adapter = new NguserDeclineListAdapter(Ng_Decline_Activity.this, claimUserList);
                    recyclerView.setAdapter(adapter);


                }else {
                    materialDialog.dismiss();
                    rel_noNgUserListingData.setVisibility(View.VISIBLE);
                    if (responseCode==200){
                        tv_ngUserListdata.setText("No Data found!!");
                    }else if (responseCode==500){
                        tv_ngUserListdata.setText("Internal server error!!");
                    }else {
                        tv_ngUserListdata.setText("Failed to Fetch data.Please try after some time!!");
                    }
                    recyclerView.setVisibility(View.GONE);

                }

            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();

        try {
            //bp_no_array.clear();
            adapter.notifyDataSetChanged();
            if(Utils.isNetworkConnected(Ng_Decline_Activity.this)){
                loadNgUserList();
            }else {
                Utils.showToast(Ng_Decline_Activity.this,"Please check your connectivity");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



}
