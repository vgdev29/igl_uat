package com.fieldmobility.igl.Mdpe;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;

import com.fieldmobility.igl.databinding.ActivityMdpeSupHostBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TabHost;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fieldmobility.igl.R;

public class Mdpe_Sup_Host extends TabActivity {

ActivityMdpeSupHostBinding supHostBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supHostBinding = DataBindingUtil.setContentView(this,R.layout.activity_mdpe_sup_host);
        supHostBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        supHostBinding.headerTitle.setText("MDPE");
        TabHost.TabSpec TabMenu1 = supHostBinding.tabhost.newTabSpec("Allocation");
        TabHost.TabSpec TabMenu2 = supHostBinding.tabhost.newTabSpec("Declined DPR");
        TabMenu2.setIndicator("Declined DPR");
        TabMenu2.setContent(new Intent(this, MdpeDeclinedDpr.class));
        TabMenu1.setIndicator("Allocation");
        TabMenu1.setContent(new Intent(this, Mdpe_List_Activity.class));
        supHostBinding.tabhost.addTab(TabMenu1);
        supHostBinding.tabhost.addTab(TabMenu2);
    }


}