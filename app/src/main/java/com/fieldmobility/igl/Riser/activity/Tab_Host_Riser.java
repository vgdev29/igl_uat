package com.fieldmobility.igl.Riser.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.fieldmobility.igl.Activity.BP_No_Listing;
import com.fieldmobility.igl.Activity.BP_No_Resubmition_Listing;
import com.fieldmobility.igl.Activity.NICListing;
import com.fieldmobility.igl.R;

public class Tab_Host_Riser extends TabActivity {

    TabHost TabHostWindow;
    ImageView back,new_regestration;
    TextView header_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host_layout);
        back=findViewById(R.id.back);
        header_title=findViewById(R.id.header_title);
        header_title.setText("Riser");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Assign id to Tabhost.
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("Pending List");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Approval List");


        //Setting up tab 1 name.
        TabMenu1.setIndicator("Pending List");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this, RiserTpiPendingActivity.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("Approval List");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this, RiserTpiApprovalActivity.class));



        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);


    }
}