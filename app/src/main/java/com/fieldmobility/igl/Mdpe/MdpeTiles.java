package com.fieldmobility.igl.Mdpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.databinding.ActivityMdpeTilesBinding;

public class MdpeTiles extends AppCompatActivity {
    ImageView back;
    MdpeSubAllocation subAllocation = new MdpeSubAllocation();
    String log = "mdpetiles";
    TextView header_title;
    ActivityMdpeTilesBinding tilesBinding;
    SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tilesBinding = DataBindingUtil.setContentView(this,R.layout.activity_mdpe_tiles);
        sharedPrefs = new SharedPrefs(this);
        tilesBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getIntent()!=null) {
            subAllocation = (MdpeSubAllocation) getIntent().getSerializableExtra("data");
        }
        Log.d(log,subAllocation.getAllocationNumber());
        tilesBinding.headerTitle.setText("Allocation No -"+ subAllocation.getAllocationNumber());

    }

    public void onClickLayout(View view) {
        Fragment fragment;
        switch (view.getId())
        {
            case R.id.ll_pipeline:
                tilesBinding.frameContainer.setVisibility(View.VISIBLE);
                tilesBinding.headerTitle.setText("Pipe Line");
                fragment = MPipeFragment.newInstance(this,subAllocation.getAllocationNumber(),subAllocation.getSuballocationNumber(),subAllocation.getTpiId(),subAllocation.getContId(),subAllocation.getZone());
                loadFragment(fragment);
                break;
            case R.id.ll_construction:
                tilesBinding.frameContainer.setVisibility(View.VISIBLE);
                tilesBinding.headerTitle.setText("Construction");
                fragment = MConstrFragment.newInstance(this,subAllocation.getAllocationNumber(),subAllocation.getSuballocationNumber(),subAllocation.getTpiId(),subAllocation.getContId(),subAllocation.getZone());
                loadFragment(fragment);
                break;
            case R.id.ll_excavation:
                tilesBinding.frameContainer.setVisibility(View.VISIBLE);
                tilesBinding.headerTitle.setText("Excavation");
                fragment = MCommonFragment.newInstance(this,subAllocation.getAllocationNumber(),subAllocation.getSuballocationNumber(),subAllocation.getTpiId(),1,subAllocation.getContId(),subAllocation.getZone());
                loadFragment(fragment);
                break;
            case R.id.ll_restoration:
                tilesBinding.frameContainer.setVisibility(View.VISIBLE);
                tilesBinding.headerTitle.setText("Restoration");
                fragment = MCommonFragment.newInstance(this,subAllocation.getAllocationNumber(),subAllocation.getSuballocationNumber(),subAllocation.getTpiId(),2,subAllocation.getContId(),subAllocation.getZone());
                loadFragment(fragment);
                break;
            case R.id.ll_survey:
                tilesBinding.frameContainer.setVisibility(View.VISIBLE);
                tilesBinding.headerTitle.setText("Survey");
                fragment = MCommonFragment.newInstance(this,subAllocation.getAllocationNumber(),subAllocation.getSuballocationNumber(),subAllocation.getTpiId(),3,subAllocation.getContId(),subAllocation.getZone());
                loadFragment(fragment);
                break;
            case R.id.ll_marker:
                tilesBinding.frameContainer.setVisibility(View.VISIBLE);
                tilesBinding.headerTitle.setText("Marker");
                fragment = MCommonFragment.newInstance(this,subAllocation.getAllocationNumber(),subAllocation.getSuballocationNumber(),subAllocation.getTpiId(),4,subAllocation.getContId(),subAllocation.getZone());
                loadFragment(fragment);
                break;
            case R.id.ll_liasioning:
                tilesBinding.frameContainer.setVisibility(View.VISIBLE);
                tilesBinding.headerTitle.setText("Liasioning");
                fragment = MCommonFragment.newInstance(this,subAllocation.getAllocationNumber(),subAllocation.getSuballocationNumber(),subAllocation.getTpiId(),5,subAllocation.getContId(),subAllocation.getZone());
                loadFragment(fragment);
                break;
            case R.id.ll_shifting:
                tilesBinding.frameContainer.setVisibility(View.VISIBLE);
                tilesBinding.headerTitle.setText("Shifting Works");
                fragment = MCommonFragment.newInstance(this,subAllocation.getAllocationNumber(),subAllocation.getSuballocationNumber(),subAllocation.getTpiId(),6,subAllocation.getContId(),subAllocation.getZone());
                loadFragment(fragment);
                break;


        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);

        if (fragment != null && fragment.isVisible())
        {
            super.onBackPressed();
            tilesBinding.frameContainer.setVisibility(View.GONE);
            tilesBinding.headerTitle.setText("Allocation No -"+ subAllocation.getAllocationNumber());
            Log.d(log,"Fragment visible "+fragment.getId());
        }
        else
        { super.onBackPressed();
            Log.d(log,"Fragment not visible ");
        }

    }
}