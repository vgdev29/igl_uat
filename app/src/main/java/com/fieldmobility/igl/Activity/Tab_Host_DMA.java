package com.fieldmobility.igl.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.fieldmobility.igl.R;

public class Tab_Host_DMA extends TabActivity {

    TabHost TabHostWindow;
    ImageView back,new_regestration;
    TextView header_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host_layout);
        back=findViewById(R.id.back);
        header_title=findViewById(R.id.header_title);
        header_title.setText("DMA");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Assign id to Tabhost.
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("New Registration");
        TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("NIC");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Doc Resubmission");


        //Setting up tab 1 name.
        TabMenu1.setIndicator("New Registration");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this, BP_No_Listing.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("Document Resubmission");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this, BP_No_Resubmition_Listing.class));

        //Setting up tab 2 name.
        TabMenu3.setIndicator("NIC Listing");
        //Set tab 3 activity to tab 1 menu.
        TabMenu3.setContent(new Intent(this, NICListing.class));


        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);

    }
}