package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fieldmobility.igl.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NICListing extends AppCompatActivity {

    FloatingActionButton nicreg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niclisting);
        nicreg = findViewById(R.id.nic_regestration);
        nicreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(NICListing.this, NICustomerActivity.class);
                startActivity(intent);
            }
        });
    }


}