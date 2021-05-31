package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

public class NG_Connection_Activity extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back;
    EditText bp_no_text,meater_no,meater_date,meater_reading,jmr_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ng_connection_layout);

        Layout_Id();
        Implement_Method();
    }

    private void Implement_Method() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Layout_Id() {

        back=findViewById(R.id.back);
        bp_no_text=findViewById(R.id.bp_no_text);
        meater_no=findViewById(R.id.meater_no);
        meater_date=findViewById(R.id.meater_date);
        meater_reading=findViewById(R.id.meater_reading);
        jmr_date=findViewById(R.id.jmr_date);

    }
}
