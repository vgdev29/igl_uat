package com.fieldmobility.igl.MITDtoRFC;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;

import com.fieldmobility.igl.R;


import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class Tab_Host_MitdTpi extends TabActivity {
    TabHost TabHostWindow;
    ImageView back ;
    TextView header_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab__host__mitd_tpi);
        back=findViewById(R.id.back);
        header_title=findViewById(R.id.header_title);
        header_title.setText("Testing & Commissioning");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Assign id to Tabhost.
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("AIR-RFC Pending");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("AIR-RFC Approval");


        //Setting up tab 1 name.
        TabMenu1.setIndicator("AIR-RFC Pending");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this, MITD_PendingList.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("AIR-RFC Approval");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this, MITD_ApprovalList.class));

        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);

    }
}