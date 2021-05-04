package com.example.igl.Fragment;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.igl.Adapter.NgUserClaimListAdapter;
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

import static com.example.igl.utils.Utils.hideProgressDialog;
import static com.example.igl.utils.Utils.showProgressDialog;

public class NgClaim_Tpi_Fragment extends Activity implements NgUserClaimListAdapter.ListParser {

    private SharedPrefs sharedPrefs;
    private ImageView back,new_regestration;
    private List<Bp_No_Item> bp_no_array;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EditText editTextSearch;
    private TextView header_title;

    View root;
    private LinearLayout top_layout;


    private ArrayList<NguserListModel> claimUserList= new ArrayList<>();
    private NgUserClaimListAdapter adapter;
    private RelativeLayout  rel_noNgUserListingData;
    private TextView tv_ngUserListdata;
    private int responseCode;
    private Button btnTryAgain;




    public NgClaim_Tpi_Fragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpi__claim__un_claim);
        sharedPrefs = new SharedPrefs(getApplicationContext());
        Layout_ID();
    }

    private void Layout_ID() {
        top_layout=findViewById(R.id.top_layout);
        top_layout.setVisibility(View.GONE);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_claim);
        claimUserList= new ArrayList<>();
        header_title=findViewById(R.id.header_title);
        rel_noNgUserListingData = findViewById(R.id.rel_noNgUserListingData);
        tv_ngUserListdata = findViewById(R.id.tv_ngUserListdata);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        editTextSearch=findViewById(R.id.editTextSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        header_title.setText("TPI");


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    //bp_no_array.clear();
                    //Feasivility_List();
                    if(Utils.isNetworkConnected(NgClaim_Tpi_Fragment.this)){
                        loadNgUserList();

                    }else {
                        Utils.showToast(NgClaim_Tpi_Fragment.this,"Please check your connectivity");
                        rel_noNgUserListingData.setVisibility(View.VISIBLE);
                        tv_ngUserListdata.setText("Please check your connectivity!!");
                        recyclerView.setVisibility(View.GONE);
                    }
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
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isNetworkConnected(NgClaim_Tpi_Fragment.this)){
                    loadNgUserList();
                }else {
                    Utils.showToast(NgClaim_Tpi_Fragment.this,"Please check your connectivity");
                    rel_noNgUserListingData.setVisibility(View.VISIBLE);
                    tv_ngUserListdata.setText("Please check your connectivity!!");
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        if(Utils.isNetworkConnected(NgClaim_Tpi_Fragment.this)){
            loadNgUserList();
            //DBManager.getNgUserListClaimUnclaimList();
        }else {
            Utils.showToast(NgClaim_Tpi_Fragment.this,"Please check your connectivity");
            rel_noNgUserListingData.setVisibility(View.VISIBLE);
            tv_ngUserListdata.setText("Please check your connectivity!!");
            recyclerView.setVisibility(View.GONE);
        }
    }
    private void loadNgUserList() {

        showProgressDialog(this);
            String zonId = sharedPrefs.getZone_Code();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        //Call<ArrayList<NguserListModel>> call = api.getNgUserListClaimUnclaimList();
        Call<ArrayList<NguserListModel>> call = api.getUnclaimedAssignList("AS",zonId);
        call.enqueue(new Callback<ArrayList<NguserListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguserListModel>> call, retrofit2.Response<ArrayList<NguserListModel>> response) {
                    responseCode = response.code();
                    if (response.body()!=null){
                        claimUserList = response.body();
                        setListData(claimUserList,responseCode);

                    }else {
                        setListData(claimUserList,responseCode);
                    }
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
                    hideProgressDialog();
                    adapter = new NgUserClaimListAdapter(NgClaim_Tpi_Fragment.this, claimUserList);
                    recyclerView.setAdapter(adapter);
                    rel_noNgUserListingData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);


                }else {
                    hideProgressDialog();
                    rel_noNgUserListingData.setVisibility(View.VISIBLE);
                    if (responseCode==200){
                        tv_ngUserListdata.setText("No Data found!!");
                    }else if (responseCode==500){
                        tv_ngUserListdata.setText("Internal server error!!");
                    }
                    else {
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
            adapter.notifyDataSetChanged();
            if(Utils.isNetworkConnected(NgClaim_Tpi_Fragment.this)){
                loadNgUserList();
            }else {
                Utils.showToast(NgClaim_Tpi_Fragment.this,"Please check your connectivity");
                rel_noNgUserListingData.setVisibility(View.VISIBLE);
                tv_ngUserListdata.setText("Please check your connectivity!!");
                recyclerView.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void listParser() {


    }

    /*public void refreshList(){

    }*/

    public void Claimed_API_POST(String jmr_number, NguserListModel nguserListModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number ,nguserListModel);
        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>" , "weldone............");
                if (response.code()==200){
                    if(response.body()!=null){
                        loadNgUserList();
                        Toast.makeText(getApplicationContext(),"Claim Successfully",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error" , "error comes");
                Toast.makeText(getApplicationContext(),"Fail to success",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void UnClaimed_API_POST(String jmr_number,NguserListModel nguserListModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number,nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>" , "weldone............");
                if (response.code()==200){
                    if(response.body()!=null){
                        loadNgUserList();
                        Toast.makeText(getApplicationContext(),"UnClaim Successfully ",Toast.LENGTH_SHORT).show();
                    }
                }



            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error" , "error comes");
                Toast.makeText(getApplicationContext(),"Fail to success",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void startJob(String jmr_number,NguserListModel nguserListModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number,nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>" , "weldone............");
                if (response.code()==200){
                    if(response.body()!=null){
                        loadNgUserList();
                        Toast.makeText(getApplicationContext(),"Start Job Successfully ",Toast.LENGTH_SHORT).show();
                    }
                }




            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error" , "error comes");
                Toast.makeText(getApplicationContext(),"Start Job Successfully ",Toast.LENGTH_SHORT).show();

            }
        });
    }
}