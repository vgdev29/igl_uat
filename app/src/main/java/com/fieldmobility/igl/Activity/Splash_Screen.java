package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MainActivity;
import com.fieldmobility.igl.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Splash_Screen extends Activity {
    SharedPrefs sharedPrefs;
    final Handler handler = new Handler();
    String date_select,confirm_date;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Layout_Id();
    }
    private void Layout_Id() {
        sharedPrefs = new SharedPrefs(this);
        confirm_date=sharedPrefs.getLoginDate();
        Log.d("confirm_date",  confirm_date);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date_select = date_format.format(c);
        Log.d("CurrentDate",  date_select);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent  in;
                if (sharedPrefs.getLoginStatus().equalsIgnoreCase("true")) {
                    if (date_select.equals(confirm_date)) {
                        in = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(in);
                        finish();
                    }else {
                        sharedPrefs.setLoginStatus("false");
                        in = new Intent(getApplicationContext(), Login_Activity.class);
                        startActivity(in);
                        finish();
                    }
                } else {
                    in = new Intent(getApplicationContext(), Login_Activity.class);
                    startActivity(in);
                    finish();
                }

            }
        }, 3000);
    }

}
