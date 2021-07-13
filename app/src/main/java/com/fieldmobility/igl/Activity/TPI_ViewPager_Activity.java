package com.fieldmobility.igl.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fieldmobility.igl.Fragment.TPI_Feasibility_pending_Fragment;
import com.fieldmobility.igl.Fragment.TPI_RFC_approval_Fragment;
import com.fieldmobility.igl.Fragment.TPI_RFC_pending_Fragment;
import com.fieldmobility.igl.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TPI_ViewPager_Activity extends AppCompatActivity {
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    ImageView back;
    TextView header_title;
    ImageView new_regestration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_viewpager_layout);
        new_regestration=findViewById(R.id.new_regestration);
        new_regestration.setVisibility(View.GONE);
        header_title=findViewById(R.id.header_title);
        header_title.setText("Feasibility & RFC");
        back =(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTablayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        setupViewPager(mViewPager);
        //mViewPager.setOffscreenPageLimit(0);
        mTablayout.setupWithViewPager(mViewPager);


    }


    private void setupViewPager(ViewPager viewPager){
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TPI_Feasibility_pending_Fragment(TPI_ViewPager_Activity.this),"Feasibility");
        adapter.addFragment(new TPI_RFC_pending_Fragment(TPI_ViewPager_Activity.this),"RFC Pending");
        adapter.addFragment(new TPI_RFC_approval_Fragment(TPI_ViewPager_Activity.this),"RFC Approval");
        viewPager.setAdapter(adapter);

    }

    class viewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTittleList = new ArrayList<>();

        public viewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment (Fragment fragment, String tittle){
            mFragmentList.add(fragment);
            mFragmentTittleList.add(tittle);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTittleList.get(position);
        }
    }
}