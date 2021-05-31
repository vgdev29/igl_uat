package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;


import com.fieldmobility.igl.Adapter.Home_Adapter;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.VideoListData1;
import com.fieldmobility.igl.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomePage_Activity extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        Layout_ID();
    }
    private void Layout_ID() {

                VideoListData1[] myListData = new VideoListData1[] {
                new VideoListData1("ATTENDANCE", R.drawable.attendance),
                new VideoListData1("TASK LIST",R.drawable.activation),
                new VideoListData1("TO-DO List",R.drawable.to_do_list),
                new VideoListData1("LEARNING", R.drawable.learning),

        };
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        Home_Adapter adapter = new Home_Adapter(this,myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);
    }
}
