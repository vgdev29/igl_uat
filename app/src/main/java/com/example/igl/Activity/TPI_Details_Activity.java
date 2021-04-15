package com.example.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.igl.R;

public class TPI_Details_Activity extends AppCompatActivity {

    private Button btn_startJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpi__details_);
        btn_startJob = findViewById(R.id.btn_startJob);

    }
}
