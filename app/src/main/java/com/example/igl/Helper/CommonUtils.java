package com.example.igl.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class CommonUtils {

    static ProgressDialog progressDialog;

    public static void startProgressBar(Context context, String msg)
    {
        progressDialog  = ProgressDialog.show(context, "", msg, true);
        progressDialog.setCancelable(true);
        Log.d("commnutils","start progress - "+msg);
    }
    public static void dismissProgressBar(Context context)
    {
        Log.d("commnutils","dismiss progress - ");
        progressDialog.dismiss();
    }

    public static void toast_msg(Context context,String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
