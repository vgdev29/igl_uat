package com.example.igl.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.igl.Fragment.Ready_Inspection_Tpi_Fragment;
import com.example.igl.R;

public class Tab_Host_Inspection extends TabActivity {

    TabHost TabHostWindow;
    ImageView back,new_regestration;
    TextView header_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host_inspection);
        back=findViewById(R.id.back);
        header_title=findViewById(R.id.header_title);
        header_title.setText("RFC");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Assign id to Tabhost.
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("RFC Pending");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("RFC Declined");
       // TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("Approval");



        TabMenu1.setIndicator("RFC Pending");
        TabMenu1.setContent(new Intent(this, RFC_Connection_Listing.class));
        TabMenu2.setIndicator("RFC Declined");
        TabMenu2.setContent(new Intent(this, RFC_Declined_Listing.class));
        //TabMenu3.setIndicator("Approval");
        //TabMenu3.setContent(new Intent(this, TPI_Approval_Activity.class));


        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        //TabHostWindow.addTab(TabMenu3);

    }
}