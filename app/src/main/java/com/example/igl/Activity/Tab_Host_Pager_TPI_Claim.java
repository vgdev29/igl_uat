package com.example.igl.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.igl.Fragment.NgClaim_Tpi_Fragment;
import com.example.igl.Fragment.Ng_Decline_Activity;
import com.example.igl.Fragment.Ng_Done_Fragment;
import com.example.igl.Fragment.Ng_Pending_Fragment;
import com.example.igl.R;

public class Tab_Host_Pager_TPI_Claim extends TabActivity {

    TabHost TabHostWindow;
    ImageView back,new_regestration;
    TextView header_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host_layout_tpi_claim);
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
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("NG Claim");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("NG Pending");
        //TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("NG Decline");
        //TabHost.TabSpec TabMenu4 = TabHostWindow.newTabSpec("NG Done");


        //Setting up tab 1 name.
        TabMenu1.setIndicator("NG Pending");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this, NgClaim_Tpi_Fragment.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("NG Approval");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this, Ng_Pending_Fragment.class));
        //TabMenu3.setIndicator("NG Decline");
        //TabMenu3.setContent(new Intent(this, Ng_Decline_Activity.class));

        //Setting up tab 2 name.
        //TabMenu4.setIndicator("NG Done ");
        //Set tab 3 activity to tab 1 menu.
        //TabMenu4.setContent(new Intent(this, Ng_Done_Fragment.class));


        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        //TabHostWindow.addTab(TabMenu3);
        //TabHostWindow.addTab(TabMenu4);

    }
}
