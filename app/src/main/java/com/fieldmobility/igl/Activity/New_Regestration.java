package com.fieldmobility.igl.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.badoualy.stepperindicator.StepperIndicator;
import com.fieldmobility.igl.Adapter.NonSwipeableViewPager;

import com.fieldmobility.igl.Fragment.BePartnerStepOneFragment;
import com.fieldmobility.igl.Fragment.BePartnerStepThreeFragment;
import com.fieldmobility.igl.Fragment.BePartnerStepTwoFragment;
import com.fieldmobility.igl.MataData.Pdf_Item;
import com.fieldmobility.igl.R;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.ArrayList;

public class New_Regestration extends AppCompatActivity implements BePartnerStepOneFragment.OnStepOneListener, BePartnerStepTwoFragment.OnStepTwoListener, BePartnerStepThreeFragment.OnStepThreeListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private NonSwipeableViewPager mViewPager;
    private StepperIndicator stepperIndicator;
    private PdfWriter pdfWriter;
    String reportName ="Android1";
    //we will add some products to arrayListRProductModel to show in the PDF document
    private static ArrayList<Pdf_Item> arrayListRProductModel = new ArrayList<Pdf_Item>();
    private static String fullname ;
    private static String father_husbandname;
    private static String mobile_no;
    private static String contect_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_regestration);

        // setToolbarBackVisibility(true);
        setTitle("Become Partner");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new New_Regestration.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        stepperIndicator = findViewById(R.id.stepperIndicator);


        stepperIndicator.showLabels(false);
        stepperIndicator.setViewPager(mViewPager);
        // or keep last page as "end page"
        stepperIndicator.setViewPager(mViewPager, mViewPager.getAdapter().getCount() - 1); //

        /*// or manual change
        indicator.setStepCount(3);
        indicator.setCurrentStep(2);
*/

    }


   /* @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }*/


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return BePartnerStepOneFragment.newInstance("", "");
                case 1:
                    return BePartnerStepTwoFragment.newInstance("", "");
                case 2:
                    return BePartnerStepThreeFragment.newInstance("", "","");
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "First Level";
                case 1:
                    return "Second Level";
                case 2:
                    return "Finish";
            }
            return null;
        }
    }


    @Override
    public void onNextPressed(Fragment fragment) {
        if (fragment instanceof BePartnerStepOneFragment) {
            mViewPager.setCurrentItem(1, true);
        } else if (fragment instanceof BePartnerStepTwoFragment) {
            mViewPager.setCurrentItem(2, true);

        } else if (fragment instanceof BePartnerStepThreeFragment) {
            Toast.makeText(this, "Thanks For Registering", Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(New_Regestration.this, Login_Activity.class);
            startActivity(intent);*/
        }
    }


    @Override
    public void onBackPressed(Fragment fragment) {
        if (fragment instanceof BePartnerStepTwoFragment) {
            mViewPager.setCurrentItem(0, true);
        } else if (fragment instanceof BePartnerStepThreeFragment) {
            mViewPager.setCurrentItem(1, true);
        }
    }



}