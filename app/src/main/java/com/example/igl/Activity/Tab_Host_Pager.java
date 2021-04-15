package com.example.igl.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.igl.Fragment.Feasibility_Tpi_Fragment;
import com.example.igl.Fragment.Ready_Inspection_Tpi_Fragment;
import com.example.igl.R;

public class Tab_Host_Pager extends TabActivity {

    TabHost TabHostWindow;
    ImageView back,new_regestration;
    TextView header_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host_layout);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Assign id to Tabhost.
        header_title=findViewById(R.id.header_title);
        header_title.setText(getIntent().getStringExtra("heading"));
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("Feasibility");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("RFC Pending");
        TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("RFC Approval");


        //Setting up tab 1 name.
        TabMenu1.setIndicator("Feasibility");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this, Feasibility_Tpi_Fragment.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("RFC Pending");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this, Ready_Inspection_Tpi_Fragment.class));

        //Setting up tab 2 name.
        TabMenu3.setIndicator("TPI Approval");
        //Set tab 3 activity to tab 1 menu.
        TabMenu3.setContent(new Intent(this, TPI_Approval_Activity.class));


        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);

    }
}