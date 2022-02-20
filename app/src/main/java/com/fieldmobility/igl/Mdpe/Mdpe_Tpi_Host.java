package com.fieldmobility.igl.Mdpe;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import androidx.databinding.DataBindingUtil;

import com.fieldmobility.igl.Activity.TPI_NgPending_Activity;
import com.fieldmobility.igl.Activity.TPI_Ng_Approval_Activity;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.databinding.ActivityMdpeTpiHostBinding;

public class Mdpe_Tpi_Host extends TabActivity {

    ActivityMdpeTpiHostBinding tpibinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tpibinding = DataBindingUtil.setContentView(this,R.layout.activity_mdpe_tpi_host);
        tpibinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpibinding.headerTitle.setText("MDPE");
        TabHost.TabSpec TabMenu1 = tpibinding.tabhost.newTabSpec("NG Claim");
        TabHost.TabSpec TabMenu2 = tpibinding.tabhost.newTabSpec("NG Pending");
        TabMenu2.setIndicator("DPR Approval");
        TabMenu2.setContent(new Intent(this, MdpeTpiDpr.class));
        TabMenu1.setIndicator("Allocation Claim");
        TabMenu1.setContent(new Intent(this, MdpeTpiPending.class));
        tpibinding.tabhost.addTab(TabMenu1);
        tpibinding.tabhost.addTab(TabMenu2);

    }
}