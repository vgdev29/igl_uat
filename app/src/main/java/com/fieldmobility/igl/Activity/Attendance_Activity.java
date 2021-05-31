package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.GPSLocation;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MainActivity;
import com.fieldmobility.igl.R;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Attendance_Activity extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    Button sign_in,sign_out;
    ImageView back;
    TextView sign_in_time,sign_out_time,attendance_list;

    String am_pm,am_pm1,date_select,confirm_date,Login_Logout;
    TimePickerDialog pickerDialog_signin,pickerDialog_signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_layout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        Layout_ID();
    }

    private void Layout_ID() {
        confirm_date=sharedPrefs.getDate();
        Login_Logout=sharedPrefs.getLogin_User();
        Log.e("confirm_date",confirm_date);
        Log.e("Login_Logout",Login_Logout);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sign_in=(Button)findViewById(R.id.sign_in);
        sign_out=(Button)findViewById(R.id.sign_out);
        sign_in_time=findViewById(R.id.sign_in_time);
        sign_out_time=findViewById(R.id.sign_out_time);
        attendance_list=findViewById(R.id.attendance_list);
        sign_out_time.setVisibility(View.GONE);
        attendance_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Attendance_Activity.this,View_Attendance_Activity.class);
                startActivity(intent);
            }
        });
        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);
        datePickerTimeline.setInitialDate(2019, 10, 12);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 5);
        datePickerTimeline.setActiveDate(date);
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                //Do Something
                Log.d(TAG, "onDateSelected: " + day);
            }
            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                Log.d(TAG, "onDisabledDateSelected: " + day);
            }
        });
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date_select = date_format.format(c);
        Log.d("CurrentDate",  date_select);
        DateFormat df = new SimpleDateFormat("h:mm a");
        String Time = df.format(Calendar.getInstance().getTime());
        Log.d("Current TIME",  Time);
        if(date_select.equals(confirm_date) && Login_Logout.equals("0")){
            sign_out.setVisibility(View.VISIBLE);
            sign_in.setVisibility(View.GONE);
        }else {
            if(date_select.equals(confirm_date) && Login_Logout.equals("1")){
                sign_out.setVisibility(View.GONE);
                sign_in.setVisibility(View.GONE);
            }else {
                sign_out.setVisibility(View.GONE);
                sign_in.setVisibility(View.VISIBLE);
            }

        }


        sign_in_time.setText(Time);
        sign_out_time.setText(Time);
        Date[] dates = {Calendar.getInstance().getTime()};
        datePickerTimeline.deactivateDates(dates);
        /*sign_in_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerDialog_signin = new TimePickerDialog(Attendance_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hour, int minutes) {
                                // eText.setText(sHour + ":" + sMinute);
                                if(hour > 12) {
                                    am_pm = "PM";
                                    hour = hour - 12;
                                }
                                else
                                {
                                    am_pm="AM";
                                }
                                sign_in_time.setText(hour + ":" + minutes+" "+am_pm);
                            }
                        }, hour, minutes, true);


                pickerDialog_signin.show();
            }
        });
*/
       /* sign_out_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerDialog_signout = new TimePickerDialog(Attendance_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hour, int minutes) {
                                if(hour > 12) {
                                    am_pm1 = "PM";
                                    hour = hour - 12;
                                }
                                else
                                {
                                    am_pm1="AM";
                                }
                                sign_out_time.setText(hour + ":" + minutes+" "+am_pm1);
                            }
                        }, hour, minutes, true);

                pickerDialog_signout.show();
            }
        });*/
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attendance_Signin();
            }
        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Attendance_Signout();
            }
        });
    }

    public void Attendance_Signin() {
        try {
            progressDialog.show();
            String uploadId = UUID.randomUUID().toString();
            Log.e("Camera Path+,", "" + Selfie_Activity.Camera_Path);
            Log.e("address+,,,,", "" +  GPSLocation.address);
            Log.e("user_latitude+", "" + GPSLocation.Latitude);
            new MultipartUploadRequest(Attendance_Activity.this, uploadId, Constants.Attendance_Signin_Post+sharedPrefs.getUUID())
                    //.addHeader("Content-Type", "application/json")
                    //  .addHeader("Accept", "application/json")
                    //  .addHeader("Authorization", "Bearer " + sharedPrefs.getToken())
                    .addFileToUpload(Selfie_Activity.Camera_Path, "punch_in_image")
                    .addParameter("address", GPSLocation.address)
                    .addParameter("user_latitude", GPSLocation.Latitude)
                    .addParameter("user_longitude", GPSLocation.Longitude)
                    .addParameter("punch_in", sign_in_time.getText().toString())

                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                        }
                        @Override
                        public void onError(Context context, UploadInfo uploadInfo,  Exception exception) {
                            exception.printStackTrace();
                            progressDialog.dismiss();
                            //Dilogbox_Error();
                            Log.e("Uplodeerror++", uploadInfo.getSuccessfullyUploadedFiles().toString());

                        }
                        @Override
                        public void onCompleted(Context context,UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();
                            String Uplode = uploadInfo.getSuccessfullyUploadedFiles().toString();
                            String serverResponse1 = serverResponse.getHeaders().toString();
                            String str = serverResponse.getBodyAsString();
                            final JSONObject jsonObject;
                            sharedPrefs.setDate(date_select);
                            sharedPrefs.setLogin_User("0");
                            Log.e("UPLOADEsinin++", str);
                            Intent sign_out=new Intent(Attendance_Activity.this, MainActivity.class);
                            startActivity(sign_out);
                            Toast.makeText(Attendance_Activity.this, "" + "Succesfully Attendance SignIn Submit", Toast.LENGTH_SHORT).show();


                        }
                        @Override
                        public void onCancelled(Context context,UploadInfo uploadInfo) {
                            progressDialog.dismiss();
                        }
                    })
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
            Log.e("KEY_IMAGE+,,,,,,,,,,", "" + "");

        } catch (Exception exc) {
            Toast.makeText(Attendance_Activity.this, "Please select Image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public void Attendance_Signout() {
        try {
            progressDialog.show();
            String uploadId = UUID.randomUUID().toString();
            Log.e("uploadId+,,,,,,,,,,", "" + uploadId);
            new MultipartUploadRequest(Attendance_Activity.this, uploadId, Constants.Attendance_Signout_Post+sharedPrefs.getUUID())
                    //.addHeader("Content-Type", "application/json")
                    //  .addHeader("Accept", "application/json")
                    //  .addHeader("Authorization", "Bearer " + sharedPrefs.getToken())
                    .addFileToUpload(Selfie_Activity.Camera_Path, "punch_in_image")
                    .addParameter("address", GPSLocation.address)
                    .addParameter("user_latitude", GPSLocation.Latitude)
                    .addParameter("user_longitude", GPSLocation.Longitude)
                    .addParameter("punch_out", sign_out_time.getText().toString())
                   // .addParameter("date", date_select)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context,UploadInfo uploadInfo) {

                        }
                        @Override
                        public void onError(Context context, UploadInfo uploadInfo,  Exception exception) {
                            exception.printStackTrace();
                            progressDialog.dismiss();
                            //Dilogbox_Error();
                            Log.e("Uplodeerror++", uploadInfo.getSuccessfullyUploadedFiles().toString());

                        }
                        @Override
                        public void onCompleted(Context context,UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();
                            String Uplode = uploadInfo.getSuccessfullyUploadedFiles().toString();
                            String serverResponse1 = serverResponse.getHeaders().toString();
                            String str = serverResponse.getBodyAsString();
                            final JSONObject jsonObject;
                            Log.e("UPLOADE++", str);
                            sharedPrefs.setLogin_User("1");
                            Intent sign_out=new Intent(Attendance_Activity.this, MainActivity.class);
                            startActivity(sign_out);
                            Toast.makeText(Attendance_Activity.this, "" + "Succesfully Attendance SignOut Submit", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(Context context,UploadInfo uploadInfo) {
                            progressDialog.dismiss();
                        }
                    })
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
            Log.e("KEY_IMAGE+,,,,,,,,,,", "" + "");

        } catch (Exception exc) {
            Toast.makeText(Attendance_Activity.this, "Please select Image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}
