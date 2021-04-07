package com.example.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.igl.Adapter.Notification_Adapter;
import com.example.igl.Adapter.Task_List_Adapter;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.VideoListData;
import com.example.igl.R;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Task_List_Astivity extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_recyclerview);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs = new SharedPrefs(this);
        Layout_ID();
    }

    private void Layout_ID() {
        back =(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VideoListData[] myListData = new VideoListData[] {
                new VideoListData("RFC work pending", "18 Mar 2020"),
                new VideoListData("Non-LMC","13 Mar 2020"),
                new VideoListData("NG","18 Mar 2020"),
                new VideoListData("Feasibility", "11 Mar 2020"),
                new VideoListData("Non-LMC","17 Mar 2020"),
                new VideoListData("NG", "22 Mar 2020"),
                new VideoListData("Feasibility", "26 Mar 2020"),

        };

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        Task_List_Adapter adapter = new Task_List_Adapter(this,myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}