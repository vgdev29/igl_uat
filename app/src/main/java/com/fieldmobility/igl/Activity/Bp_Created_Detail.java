package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class Bp_Created_Detail extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;

    LinearLayout todo_creation;
    String Address, status, sub_status, IGL_Status;
    EditText descreption_edit;
    Spinner spinner1, spinner_sub_master;
    Bp_No_Item bp_No_Item;
    TextView name_txt, date_txt, bp_no_text, email_id_txt, mobile_no_txt, created_by_person_txt, lead_no_txt, ca_no_txt, tpi_status_txt, tpi_date_txt, resedential_type_txt, address_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bp_created_detail);
        String dataJson = getIntent().getStringExtra("data");
        if (dataJson!=null && !dataJson.isEmpty()){
            bp_No_Item = new Gson().fromJson(dataJson, Bp_No_Item.class);
            initViews();
            findViewById(R.id.tv_appointment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent=new Intent(Bp_Created_Detail.this,To_Do_Task_Creation.class);
//                    intent.putExtra("Bp_number",bp_No_Item.getBp_number());
//                    intent.putExtra("Address",Address);
//                    intent.putExtra("Type","1");
//                    startActivity(intent);
                }
            });
        }
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        Layout_Id();
//        Implementation_Mentod();
    }

    private void initViews() {
        ((TextView)findViewById(R.id.tv_fname)).setText(bp_No_Item.getFirst_name());
        ((TextView)findViewById(R.id.tv_mname)).setText(bp_No_Item.getMiddle_name());
        ((TextView)findViewById(R.id.tv_lname)).setText(bp_No_Item.getLast_name());
        ((TextView)findViewById(R.id.tv_mobile)).setText(bp_No_Item.getMobile_number());
        ((TextView)findViewById(R.id.tv_email)).setText(bp_No_Item.getEmail_id());
        ((TextView)findViewById(R.id.tv_father_name)).setText(bp_No_Item.getFather_name());
        ((TextView)findViewById(R.id.tv_aadhaar)).setText(bp_No_Item.getAadhaar_number());
        ((TextView)findViewById(R.id.tv_city)).setText(bp_No_Item.getCity_region());
        ((TextView)findViewById(R.id.tv_area)).setText(bp_No_Item.getArea());
        ((TextView)findViewById(R.id.tv_society)).setText(bp_No_Item.getSociety());
        ((TextView)findViewById(R.id.tv_landmark)).setText(bp_No_Item.getLandmark());
        ((TextView)findViewById(R.id.tv_house_type)).setText(bp_No_Item.getHouse_type());
        ((TextView)findViewById(R.id.tv_house_no)).setText(bp_No_Item.getHouse_no());
        ((TextView)findViewById(R.id.tv_hblock)).setText(bp_No_Item.getBlock_qtr_tower_wing());
        ((TextView)findViewById(R.id.tv_hfloor)).setText(bp_No_Item.getFloor());
        ((TextView)findViewById(R.id.tv_street)).setText(bp_No_Item.getStreet_gali_road());
        ((TextView)findViewById(R.id.tv_pin)).setText(bp_No_Item.getPincode());
        ((TextView)findViewById(R.id.tv_lpg_company)).setText(bp_No_Item.getLpg_company());
        ((TextView)findViewById(R.id.tv_lpg_dist)).setText(bp_No_Item.getLpg_distributor());
        ((TextView)findViewById(R.id.tv_lpg_cnum)).setText(bp_No_Item.getLpg_conNo());
        ((TextView)findViewById(R.id.tv_uid)).setText(bp_No_Item.getUnique_lpg_Id());
        ((TextView)findViewById(R.id.tv_customer_type)).setText(bp_No_Item.getCustomer_type());
        ((TextView)findViewById(R.id.tv_id_type)).setText(bp_No_Item.getIdproof());
        ((TextView)findViewById(R.id.tv_address_proof_type)).setText(bp_No_Item.getAddressProof());
        ImageView iv_id_proof=findViewById(R.id.iv_id_proof);
        ImageView iv_address_proof=findViewById(R.id.iv_address_proof);
        ImageView iv_customerSignature=findViewById(R.id.iv_signature);
        ImageView iv_owner_signature=findViewById(R.id.iv_owner_signature);
        ImageView iv_cheque=findViewById(R.id.iv_cheque);


        try{
            if (bp_No_Item.getImageList()!=null && bp_No_Item.getImageList().size()>0){
                String img_id_proof_url = "",img_address_proof_url="",img_customer_signature_url="",img_owner_signature_url="",img_cheque="";
                for (int i =0;i<bp_No_Item.getImageList().size();i++){
                    if (bp_No_Item.getImageList().get(i).contains("address_proof")){
                        img_address_proof_url=bp_No_Item.getImageList().get(i);
                    }
                    else if (bp_No_Item.getImageList().get(i).contains("id_proof")){
                        img_id_proof_url=bp_No_Item.getImageList().get(i);

                    }
                    else if (bp_No_Item.getImageList().get(i).contains("customer_signature")){
                        img_customer_signature_url=bp_No_Item.getImageList().get(i);

                    }
                    else if (bp_No_Item.getImageList().get(i).contains("owner_signature")){
                        img_owner_signature_url=bp_No_Item.getImageList().get(i);

                    }
                    else if (bp_No_Item.getImageList().get(i).contains("cheque")){
                        img_cheque=bp_No_Item.getImageList().get(i);

                    }

                }

                if (!img_id_proof_url.isEmpty())
                    Picasso.with(Bp_Created_Detail.this).load(img_id_proof_url).into(iv_id_proof);
                if (!img_address_proof_url.isEmpty())
                    Picasso.with(Bp_Created_Detail.this).load(img_address_proof_url).into(iv_address_proof);
                if (!img_customer_signature_url.isEmpty())
                    Picasso.with(Bp_Created_Detail.this).load(img_customer_signature_url).into(iv_customerSignature);
                if (!img_owner_signature_url.isEmpty()) {
                    findViewById(R.id.lt_owner_signature).setVisibility(View.VISIBLE);
                    Picasso.with(Bp_Created_Detail.this).load(img_owner_signature_url).into(iv_owner_signature);
                }
                else {
                    findViewById(R.id.lt_owner_signature).setVisibility(View.GONE);

                }
                if (!img_cheque.isEmpty()) {
                    findViewById(R.id.lt_cheque_image).setVisibility(View.VISIBLE);
                    Picasso.with(Bp_Created_Detail.this).load(img_cheque).into(iv_cheque);
                }
                else {
                    findViewById(R.id.lt_cheque_image).setVisibility(View.GONE);

                }

            }
        }
        catch (Exception e){

        }

    }


//    private void Layout_Id() {
//
//        back=findViewById(R.id.back);
//
//        bp_no_text=findViewById(R.id.bp_no_text);
//        address_text=findViewById(R.id.address_text);
//        name_txt=findViewById(R.id.name_txt);
//        date_txt=findViewById(R.id.date_txt);
//        mobile_no_txt=findViewById(R.id.mobile_no_txt);
//        email_id_txt=findViewById(R.id.email_id_txt);
//        created_by_person_txt=findViewById(R.id.created_by_person_txt);
//        lead_no_txt=findViewById(R.id.lead_no_txt);
//        ca_no_txt=findViewById(R.id.ca_no_txt);
//        tpi_status_txt=findViewById(R.id.tpi_status_txt);
//        tpi_date_txt=findViewById(R.id.tpi_date_txt);
//        resedential_type_txt=findViewById(R.id.resedential_type_txt);
//        todo_creation=findViewById(R.id.todo_creation);
//        Address=getIntent().getStringExtra("House_no")+" "+getIntent().getStringExtra("House_type")+" "+
//                getIntent().getStringExtra("Landmark")+" "+getIntent().getStringExtra("Society")+" "+getIntent().getStringExtra("Area")+" "
//                +getIntent().getStringExtra("City_region");
//
//        address_text.setText(Address);
//        bp_no_text.setText(getIntent().getStringExtra("Bp_number"));
//        name_txt.setText(getIntent().getStringExtra("First_name")+" "+getIntent().getStringExtra("Middle_name")+" "+getIntent().getStringExtra("Last_name"));
//        date_txt.setText(getIntent().getStringExtra("Bp_date"));
//        mobile_no_txt.setText(getIntent().getStringExtra("Mobile_number"));
//        created_by_person_txt.setText(getIntent().getStringExtra("Customer_type"));
//        email_id_txt.setText(getIntent().getStringExtra("Email_id"));
//
//        lead_no_txt.setText(getIntent().getStringExtra("lead_no"));
//        IGL_Status=getIntent().getStringExtra("IGL_Status");
//        ca_no_txt.setText(getIntent().getStringExtra("Aadhaar_number"));
//        tpi_status_txt.setText(getIntent().getStringExtra("Customer_type"));
//        tpi_date_txt.setText(getIntent().getStringExtra("Lpg_company"));
//        resedential_type_txt.setText(getIntent().getStringExtra("Unique_lpg_Id"));
//
//
//    }
//
//    private void Implementation_Mentod() {
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//
//        todo_creation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Bp_Created_Detail.this,To_Do_Task_Creation.class);
//                intent.putExtra("Bp_number",getIntent().getStringExtra("Bp_number"));
//                intent.putExtra("Address",Address);
//                intent.putExtra("Type","1");
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void Dilogbox_NG_Connetion() {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.ng_connection_layout);
//        //dialog.setCancelable(false);
//     //   descreption_edit=dialog.findViewById(R.id.descreption_edit);
//        Button submit_button = (Button) dialog.findViewById(R.id.submit_button);
//        submit_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dialog.dismiss();
//            }
//        });
//
//
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//        dialog.getWindow().setAttributes(layoutParams);
//        dialog.show();
//        dialog.show();
//    }
}