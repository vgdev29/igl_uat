package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.squareup.picasso.Picasso;

public class KYC_VerificationDetails extends AppCompatActivity {

    Bp_No_Item bp_no_item = new Bp_No_Item();
    TextView bp_no,custname,mobile,email,houseno,housetype,block,floor,street,pincode,society,area,city,landmark,lpgcompany,metero;
    ImageView id_image,address_image,customer_image,owner_image,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_verification_details);
        bp_no_item = (Bp_No_Item) getIntent().getSerializableExtra("kycdata");
        bp_no = findViewById(R.id.txt_bpno);
        custname = findViewById(R.id.txt_custname);
        mobile = findViewById(R.id.txt_mob);
        email = findViewById(R.id.txt_email);
        houseno = findViewById(R.id.txt_houseno);
        housetype = findViewById(R.id.txt_housetype);
        block = findViewById(R.id.txt_block);
        floor = findViewById(R.id.txt_floor);
        street = findViewById(R.id.txt_street);
        pincode = findViewById(R.id.txt_pincode);
        society = findViewById(R.id.txt_society);
        area = findViewById(R.id.txt_area);
        city = findViewById(R.id.txt_city);
        landmark = findViewById(R.id.txt_landmark);
        lpgcompany = findViewById(R.id.txt_lpgcompany);
        metero = findViewById(R.id.txt_meterno);
        id_image = findViewById(R.id.image_id);
        customer_image = findViewById(R.id.image_customer);
        address_image = findViewById(R.id.image_address);
        owner_image = findViewById(R.id.image_owner);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setField();
    }

    public void setField()
    {
        bp_no.setText(bp_no_item.getBp_number());
        custname.setText(bp_no_item.getFirst_name());
        mobile.setText(bp_no_item.getMobile_number());
        email.setText(bp_no_item.getEmail_id());
        houseno.setText(bp_no_item.getHouse_no());
        housetype.setText(bp_no_item.getHouse_type());
        block.setText(bp_no_item.getBlock_qtr_tower_wing());
        floor.setText(bp_no_item.getFloor());
        street.setText(bp_no_item.getStreet_gali_road());
        pincode.setText(bp_no_item.getPincode());
        society.setText(bp_no_item.getSociety());
        area.setText(bp_no_item.getArea());
        city.setText(bp_no_item.getCity_region());
        landmark.setText(bp_no_item.getLandmark());
        lpgcompany.setText(bp_no_item.getLpg_company());
        metero.setText(bp_no_item.getMeterNo());
        Picasso.with(this)
                .load(bp_no_item.getId_image())
                .placeholder(R.color.red_light)
                .into(id_image);
        Picasso.with(this)
                .load(bp_no_item.getAddress_image())
                .placeholder(R.color.red_light)
                .into(address_image);
        Picasso.with(this)
                .load(bp_no_item.getCustomer_image())
                .placeholder(R.color.red_light)
                .into(customer_image);
        Picasso.with(this)
                .load(bp_no_item.getOwner_image())
                .placeholder(R.color.red_light)
                .into(owner_image);
    }
}