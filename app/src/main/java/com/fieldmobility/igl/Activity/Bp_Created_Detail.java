package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.fieldmobility.igl.R;

public class Bp_Created_Detail extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;

    LinearLayout todo_creation;
    String Address,status,sub_status,IGL_Status;
     EditText descreption_edit;
     Spinner spinner1,spinner_sub_master;
    TextView name_txt,date_txt,bp_no_text,email_id_txt,mobile_no_txt,created_by_person_txt,lead_no_txt,ca_no_txt,tpi_status_txt,tpi_date_txt,resedential_type_txt,address_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bp_created_detail);
        Layout_Id();
        Implementation_Mentod();
    }

    private void Layout_Id() {

        back=findViewById(R.id.back);

        bp_no_text=findViewById(R.id.bp_no_text);
        address_text=findViewById(R.id.address_text);
        name_txt=findViewById(R.id.name_txt);
        date_txt=findViewById(R.id.date_txt);
        mobile_no_txt=findViewById(R.id.mobile_no_txt);
        email_id_txt=findViewById(R.id.email_id_txt);
        created_by_person_txt=findViewById(R.id.created_by_person_txt);
        lead_no_txt=findViewById(R.id.lead_no_txt);
        ca_no_txt=findViewById(R.id.ca_no_txt);
        tpi_status_txt=findViewById(R.id.tpi_status_txt);
        tpi_date_txt=findViewById(R.id.tpi_date_txt);
        resedential_type_txt=findViewById(R.id.resedential_type_txt);
        todo_creation=findViewById(R.id.todo_creation);
          Address=getIntent().getStringExtra("House_no")+" "+getIntent().getStringExtra("House_type")+" "+
                getIntent().getStringExtra("Landmark")+" "+getIntent().getStringExtra("Society")+" "+getIntent().getStringExtra("Area")+" "
                +getIntent().getStringExtra("City_region");

        address_text.setText(Address);
        bp_no_text.setText(getIntent().getStringExtra("Bp_number"));
        name_txt.setText(getIntent().getStringExtra("First_name")+" "+getIntent().getStringExtra("Middle_name")+" "+getIntent().getStringExtra("Last_name"));
        date_txt.setText(getIntent().getStringExtra("Bp_date"));
        mobile_no_txt.setText(getIntent().getStringExtra("Mobile_number"));
        created_by_person_txt.setText(getIntent().getStringExtra("Customer_type"));
        email_id_txt.setText(getIntent().getStringExtra("Email_id"));

        lead_no_txt.setText(getIntent().getStringExtra("lead_no"));
        IGL_Status=getIntent().getStringExtra("IGL_Status");
        ca_no_txt.setText(getIntent().getStringExtra("Aadhaar_number"));
        tpi_status_txt.setText(getIntent().getStringExtra("Customer_type"));
        tpi_date_txt.setText(getIntent().getStringExtra("Lpg_company"));
        resedential_type_txt.setText(getIntent().getStringExtra("Unique_lpg_Id"));


    }

    private void Implementation_Mentod() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        todo_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Bp_Created_Detail.this,To_Do_Task_Creation.class);
                intent.putExtra("Bp_number",getIntent().getStringExtra("Bp_number"));
                intent.putExtra("Address",Address);
                intent.putExtra("Type","1");
                startActivity(intent);
            }
        });
    }





    private void Dilogbox_NG_Connetion() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.ng_connection_layout);
        //dialog.setCancelable(false);
     //   descreption_edit=dialog.findViewById(R.id.descreption_edit);
        Button submit_button = (Button) dialog.findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
        dialog.show();
    }
}