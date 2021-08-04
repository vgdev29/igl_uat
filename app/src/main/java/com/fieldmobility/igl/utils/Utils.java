package com.fieldmobility.igl.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import com.fieldmobility.igl.AppConstant;
import com.fieldmobility.igl.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static android.os.Environment.getExternalStorageDirectory;

public class Utils {
    public static final String IMAGE_DIRECTORY = "/signdemo";
    static ProgressDialog dialog;



    public static String getIntentType(String fileExtensionType) {
        switch (fileExtensionType) {
            case AppConstant.FILE_TYPE.PDF:
                return "application/pdf";
            case AppConstant.FILE_TYPE.DOC:
                return "application/msword";
            case AppConstant.FILE_TYPE.IMAGE:
                return "image/*";
            case AppConstant.FILE_TYPE.AUDIO:
                return "audio/*";
            case AppConstant.FILE_TYPE.VIDEO:
                return "video/*";
            default:
                return "text/plain";
        }

    }

    public static void showToast(Context context, String titletext) {
        if (context == null)
            return;
        if (Looper.myLooper() == null)
            Looper.prepare();
        Toast toast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast, null);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(titletext);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    public static String change_to_binary(Bitmap bitmapOrg) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        return ba1;
    }
    public static String saveImage(Context context, Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("Signature_Page++", wallpaperDirectory.toString());
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public static String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        // get current date time with Date()
        Date date = new Date();
        // System.out.println(dateFormat.format(date));
        // don't print it, but save it!
        return dateFormat.format(date);
    }
    public static boolean isNetworkConnected(Context a) {
        if (a != null) {
            try {
                ConnectivityManager cm = (ConnectivityManager) a
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                return ni != null;
            } catch (Exception e) {
                return true;
            }
        }
        return true;
    }



    public ProgressDialog showProgressDialogWithText(Context context,String msg) {
        try {
            if (dialog != null && dialog.isShowing()) {
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new ProgressDialog(context, R.style.MyDialogTheme);
                } else {
                    dialog = new ProgressDialog(context);
                }
                if (TextUtils.isEmpty(msg)) {
                    dialog.setMessage("Loading...");
                } else {
                    dialog.setMessage(msg);
                }

                dialog.show();
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dialog.dismiss();
                        //finish();
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }
    public static void showProgressDialog(Context context) {
        try {
            if (Looper.myLooper() == null)
                Looper.prepare();
            if (dialog != null && dialog.isShowing()) {
            } else {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Loading...");
                dialog.show();
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void hideProgressDialog() {
        Log.i("TAG" + "Dialog", Thread.currentThread().getName());
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void showProgressDialogPersist(String msg) {
        try {
            if (dialog != null && dialog.isShowing()) {
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new ProgressDialog(this, R.style.MyDialogTheme);
                } else {
                    dialog = new ProgressDialog(this);
                }
                if (TextUtils.isEmpty(msg)) {
                    dialog.setMessage("Loading...");
                } else {
                    dialog.setMessage(msg);
                }
                dialog.show();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public static void hideKeyboard(Activity activity) {
        View viewKeyboard = activity.getCurrentFocus();
        if (viewKeyboard != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewKeyboard.getWindowToken(), 0);
        }
    }
    public static final String IMAGE_IGL = "/igl_media";

    public  static Bitmap getBitmapFromPAth(String path){
       Bitmap bitmap = null;
        try {
            File sd = Environment.getExternalStorageDirectory();
            File image = new File(sd+IMAGE_IGL, "temp.jpg");
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }

}
