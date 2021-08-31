package com.fieldmobility.igl.Helper;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fieldmobility.igl.R;

public class CommonUtils {

    static ProgressDialog progressDialog;
    public static final boolean isDebugBuild=false; // make it FALSE while giving app to others

    public static void startProgressBar(Context context, String msg) {
        progressDialog = ProgressDialog.show(context, "", msg, true);
        progressDialog.setCancelable(false);
        Log.d("commnutils", "start progress - " + msg);
    }

    public static void dismissProgressBar(Context context) {
        Log.d("commnutils", "dismiss progress - ");
        progressDialog.dismiss();
    }

    public static void toast_msg(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showZoomImageView(Context context, String url) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_zoomimageview);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView iv = dialog.findViewById(R.id.image);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv);
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void setImageUsingGLide(Context context,String url,ImageView view){
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(view);
    }
}
