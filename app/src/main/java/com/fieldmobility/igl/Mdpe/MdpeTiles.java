package com.fieldmobility.igl.Mdpe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldmobility.igl.R;

public class MdpeTiles extends AppCompatActivity {
    ImageView back;
    MdpeSubAllocation subAllocation = new MdpeSubAllocation();
    String log = "mdpetiles";
    TextView header_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdpe_tiles);
        back=findViewById(R.id.back);
        if (getIntent()!=null) {
            subAllocation = (MdpeSubAllocation) getIntent().getSerializableExtra("data");
        }
        Log.d(log,subAllocation.getAllocationNumber());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        header_title = findViewById(R.id.header_title);
        header_title.setText("Allocation No -"+ subAllocation.getAllocationNumber());
    }
}