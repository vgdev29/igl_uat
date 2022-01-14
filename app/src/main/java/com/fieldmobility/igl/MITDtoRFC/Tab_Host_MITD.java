package com.fieldmobility.igl.MITDtoRFC;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.fieldmobility.igl.R;

public class Tab_Host_MITD extends TabActivity {
    TabHost TabHostWindow;
    ImageView back,new_regestration;
    TextView header_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host_mitd2);
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
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("MITD (Own)");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("MITD (Other)");


        //Setting up tab 1 name.
        TabMenu1.setIndicator("MITD (Own)");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this, MitdOwn.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("MITD (Other)");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this, MitdOther.class));

        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);

    }
}