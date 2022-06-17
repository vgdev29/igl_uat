package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fieldmobility.igl.R;

public class NgSupStartup extends AppCompatActivity {

    CardView ngpen, nghold, ngdone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng_sup_startup);
        ngdone = findViewById(R.id.cv_ngdone);
        ngpen = findViewById(R.id.cv_ngpen);
        nghold= findViewById(R.id.cv_nghold);


        ngpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NgSupStartup.this , NgSupListActivity.class);
                startActivity(intent);
            }
        });

        ngdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        nghold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}