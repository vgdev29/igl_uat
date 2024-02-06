package com.fieldmobility.igl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.fieldmobility.igl.Helper.LogoutService;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Activity.Login_Activity;
import com.fieldmobility.igl.Activity.Tracking_Activity;
import com.fieldmobility.igl.Fragment.HomeFragment;
import com.fieldmobility.igl.Fragment.Notification_Fragment;
import com.fieldmobility.igl.Fragment.Profile_Fragment;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.CustomBottomNavigationView1;
import com.fieldmobility.igl.Helper.LocationMonitoringService;
import com.fieldmobility.igl.Helper.SharedPrefs;
//import com.fieldmobility.igl.tracker.MyIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    SharedPrefs sharedPrefs;
    MaterialDialog materialDialog;
    private static final String TAG = Tracking_Activity.class.getSimpleName();
    String Latitude,Longitude;
    private boolean mAlreadyStartedService = false;
    final Handler handler = new Handler();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String MESSENGER_INTENT_KEY = "msg-intent-key";

    // as google doc says
    // Handler for incoming messages from the service.
   // private IncomingMessageHandler mHandler;
    private TextView locationMsg;
    Button btnPermissions;
    Handler handler_lat_log=new Handler();
    public static final int REQUEST_CODE_PERMISSIONS = 101;
    Timer timer;
    TimerTask doAsynchronousTask;
    AppUpdateManager   appUpdateManager;
    LogoutService logoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs=new SharedPrefs(this);
        CustomBottomNavigationView1 customBottomNavigationView1 = findViewById(R.id.customBottomBar);
        customBottomNavigationView1.inflateMenu(R.menu.bottom_menu);
       // customBottomNavigationView1.setSelectedItemId(R.id.action_schedules);
        customBottomNavigationView1.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        inAppUpdate();
        loadFragment(new HomeFragment());



    //    mHandler = new IncomingMessageHandler();

        handler_lat_log.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callAsynchronousTask();
                    }
                }, 5000);

            }
        }, 3000);


        requestCameraAndStorage();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    //toolbar.setTitle("Shop");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_profile:
                    //toolbar.setTitle("Shop");
                    fragment = new Profile_Fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_notification:
                   // toolbar.setTitle("Shop");

                    fragment = new Notification_Fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_logout:
                    //toolbar.setTitle("Shop");
                    /*fragment = new Setting_Fragment();
                    loadFragment(fragment);*/
                    LOGOUT_Method();
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void LOGOUT_Method() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dilogbox_layout);
        dialog.setCancelable(false);
        final Button accept_button=dialog.findViewById(R.id.accept_button);
        Button cancel_button = (Button) dialog.findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefs.setLoginStatus("false");
                try {
                   //timer.cancel();
                    timer.purge();
                    doAsynchronousTask.cancel();
                //   stopService(new Intent(MainActivity.this, MyIntentService.class));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int PERMISSION_REQUEST_CODE = 123;
    public void requestCameraAndStorage() {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted; request it from the user.
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
                return; // Exit the loop after requesting permissions.
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {

            boolean foreground = false, background = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //foreground permission allowed
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        Toast.makeText(this, "Foreground location permission allowed", Toast.LENGTH_SHORT).show();

                        continue;
                    } else {
                        Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;

            // Check if all requested permissions are granted
            for (int grantResult : grantResults) {
                Log.d("login", "Grant Result" + grantResult + "  " + PackageManager.PERMISSION_GRANTED);
                Log.d("login", "Permissions" + permissions[0].toString() + permissions[1].toString());
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break; // Exit the loop if any permission is not granted.
                }
            }

            if (allPermissionsGranted) {
                // All requested permissions are granted; proceed with your app's logic.
                requestLocationPermission();
                Log.d("login", "All permission granted");
            } else {
                Log.d("login", "All permission not granted");
                requestLocationPermission();
                // Some permissions were denied; handle this situation, e.g., inform the user or implement alternative behavior.
            }
        }

    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
         timer = new Timer();
         doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                           // TPI_Approve(Latitude,Longitude);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 300000); //execute in every 50000 ms
    }


    @Override
    public void onResume() {

        inAppUpdate();
        super.onResume();
        Log.d("mainactivity","on resume");
      //  timers.start();
        //  startStep1();
    }
    /**
     * Step 1: Check Google Play services
     */
    private void startStep1() {
        if (isGooglePlayServicesAvailable()) {
            startStep2(null);
        } else {
            Toast.makeText(this, R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        if (checkPermissions()) {
            startStep3();
        } else {
            requestPermissions();
        }
        return true;
    }
    /**
     * Show A Dialog with button to refresh the internet state.
     */
    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_alert_no_intenet);
        builder.setMessage(R.string.msg_alert_no_internet);
        String positiveText = getString(R.string.btn_label_refresh);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (startStep2(dialog)) {
                            if (checkPermissions()) {
                                //Step 2: Start the Location Monitor Service
                                //Everything is there to start the service.
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startStep3() {
        if (!mAlreadyStartedService) {
           // mMsgView.setText(R.string.msg_location_service_started);
            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);
            mAlreadyStartedService = true;
        }
    }
    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }
    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionState2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }
    /**
     * Start permissions requests.
     */
    private void requestPermissions() {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        boolean shouldProvideRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i("", "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i("", "Requesting permission");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    private void showSnackbar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void onDestroy() {
        //Stop location sharing service to app server.........
        stopService(new Intent(MainActivity.this, LocationMonitoringService.class));
        mAlreadyStartedService = false;
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // timers.cancel();
    }
    /*class IncomingMessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage..." + msg.toString());
            super.handleMessage(msg);
            switch (msg.what) {
                case MyIntentService.LOCATION_MESSAGE:
                    Location obj = (Location) msg.obj;
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    // locationMsg.setText("LAT :  " + obj.getLatitude() + "\nLNG : " + obj.getLongitude() + "\n\n" + obj.toString() + " \n\n\nLast updated- " + currentDateTimeString);

                    Log.i(TAG, "LAT :  " + obj.getLatitude() + "\nLNG : " + obj.getLongitude() );
                    Latitude= String.valueOf(obj.getLatitude());
                    Longitude= String.valueOf(obj.getLongitude());
                    //Toast.makeText(getActivity(), "LAT :  " + obj.getLatitude() + "\nLNG : " + obj.getLongitude(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }*/

    private void requestLocationPermission() {
        boolean foreground = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (foreground) {
            /*boolean background = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;*/

           /* if (background) {
                handleLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
            }*/
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION/*,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION*/}, REQUEST_CODE_PERMISSIONS);
        }
    }


    /*private void handleLocationUpdates() {
        //foreground and background
        Intent startServiceIntent = new Intent(MainActivity.this, MyIntentService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
       startService(startServiceIntent);
        Toast.makeText(MainActivity.this,"Start Foreground and Background Location Updates",Toast.LENGTH_SHORT).show();
    }*/

   /* private void handleForegroundLocationUpdates() {
        //handleForeground Location Updates
        Intent startServiceIntent = new Intent(MainActivity.this, MyIntentService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
        startService(startServiceIntent);
        Toast.makeText(MainActivity.this,"Start foreground location updates",Toast.LENGTH_SHORT).show();
    }*/

    private static final int MY_REQUEST_CODE =11 ;
    public void inAppUpdate()
    {

        Log.d("update","method called in app update");
        appUpdateManager = AppUpdateManagerFactory.create(this);
// Returns an intent object that you use to check for an update.
        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // For a flexible update, use AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Request the update.
                    try {
                       // Toast.makeText(MainActivity.this,"add on succes listener - try",Toast.LENGTH_SHORT).show();

                        Log.d("update","method called in app update - try");
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, MY_REQUEST_CODE);

                    }
                    catch (IntentSender.SendIntentException exception)
                    {
                        //  Toast.makeText(MainActivity.this,"add on succes listener - catch",Toast.LENGTH_SHORT).show();
                        Log.d("update" , "intent sender = "+exception.getMessage());
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("update","method onactivity result");
        if (requestCode == MY_REQUEST_CODE) {
            Log.d("update","request code match");
           //  Toast.makeText(this,"Downloading Started",Toast.LENGTH_SHORT).show();
            if (resultCode != RESULT_OK) {
               //   Toast.makeText(this,"Downloading failed due to result code",Toast.LENGTH_SHORT).show();

                Log.d("onACtivity Result ","Update flow failed! Result code: " + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

}
