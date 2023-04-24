package com.fieldmobility.igl.Helper;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fieldmobility.igl.Activity.Login_Activity;
import com.fieldmobility.igl.MainActivity;

public class LogoutService extends Service {

    public static CountDownTimer timer;
    SharedPrefs sharedPrefs;
    @Override
    public void onCreate(){
        super.onCreate();
        sharedPrefs = new SharedPrefs(this);
        timer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Some code
                Log.d("Counter", "Service Started");
            }

            public void onFinish() {
                Log.d("Counter", "Call Logout by Service");
                // Code for Logout
                sharedPrefs.setLoginStatus("false");
                Intent intent = new Intent(LogoutService.this, Login_Activity.class);
                startActivity(intent);

                stopSelf();
            }
        };
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
