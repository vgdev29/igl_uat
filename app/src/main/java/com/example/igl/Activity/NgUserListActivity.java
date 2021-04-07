package com.example.igl.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.igl.Adapter.NgUserListAdapter;
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

public class NgUserListActivity extends AppCompatActivity {

    private RecyclerView recyclerView_ngAssignment;
    private NgUserListAdapter adapter;
    //private SwipeRefreshLayout mSwipeRefreshLayout;
    private NgUserListViewModel ngUserListViewModel;
    private LinearLayout ll_sort;
    private RelativeLayout rel_noNgUserListingData;

    private List<NguserListModel> ngUserList;
    private Button btnTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng_user_list);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        recyclerView_ngAssignment = findViewById(R.id.recyclerView_ngAssignment);
        recyclerView_ngAssignment.setHasFixedSize(true);
        recyclerView_ngAssignment.setLayoutManager(new LinearLayoutManager(this));
        ngUserList = new ArrayList<>();
        rel_noNgUserListingData = findViewById(R.id.rel_noNgUserListingData);
        loadNgUserList();

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
    }
    private void loadNgUserList() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<NguserListModel>> call = api.getNgUserList();


        call.enqueue(new Callback<ArrayList<NguserListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguserListModel>> call, Response<ArrayList<NguserListModel>> response) {

                //finally we are setting the list to our MutableLiveData
                //ngUserList.setValue(response.body());
                ngUserList = response.body();
                setListData(ngUserList);
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
