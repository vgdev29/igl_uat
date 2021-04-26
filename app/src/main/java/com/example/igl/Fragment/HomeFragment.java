package com.example.igl.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.igl.Activity.Learning_Activity;
import com.example.igl.Activity.Login_Activity;
import com.example.igl.Activity.NgUserListActivity;
import com.example.igl.Activity.RFC_Connection_Listing;
import com.example.igl.Activity.TPI_ViewPager_Activity;
import com.example.igl.Activity.Tab_Host_DMA;
import com.example.igl.Activity.Tab_Host_EKYC;
import com.example.igl.Activity.Tab_Host_PMC;
import com.example.igl.Activity.Tab_Host_Pager_TPI_Claim;
import com.example.igl.Activity.To_DoList_Activity;
import com.example.igl.Activity.Tracking_Activity;
import com.example.igl.Activity.View_Attendance_Activity;
import com.example.igl.Helper.AppController;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.LocationMonitoringService;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.VideoListData1;
import com.example.igl.R;
import com.example.igl.tracker.MyIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    View root;
    SharedPrefs sharedPrefs;
    VideoListData1[] myListData;
    LinearLayout attendance_layout,to_do_list,learning_layout,new_regestration_layout,ekyc_layout,tpi_layout,
            rfc_layout,pmc_layout,ng_pending_layout,ng_conversion_layout;
    MaterialDialog materialDialog;
    private static final String TAG = Tracking_Activity.class.getSimpleName();
   // private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    TextView mMsgView;
    String Latitude,Longitude;
    private boolean mAlreadyStartedService = false;
    final Handler handler = new Handler();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String MESSENGER_INTENT_KEY = "msg-intent-key";

    // as google doc says
    // Handler for incoming messages from the service.
    private IncomingMessageHandler mHandler;
    private TextView locationMsg;
    Button btnPermissions;
    Handler handler_lat_log=new Handler();
    public static final int REQUEST_CODE_PERMISSIONS = 101;
    public HomeFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.homepage_layout, container, false);
        sharedPrefs=new SharedPrefs(getActivity());
        mMsgView=root.findViewById(R.id.msgView);

        mHandler = new IncomingMessageHandler();

        handler_lat_log.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestLocationPermission();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                   //     callAsynchronousTask();
                    }
                }, 5000);

            }
        }, 3000);
       // requestLocationPermission();
        Layout_id();

        return root;
    }
    private void Layout_id() {
        attendance_layout=(LinearLayout)root.findViewById(R.id.attendance_layout);
        to_do_list=(LinearLayout)root.findViewById(R.id.to_do_list);
        learning_layout=(LinearLayout)root.findViewById(R.id.learning_layout);
        new_regestration_layout=(LinearLayout)root.findViewById(R.id.new_regestration_layout);
        ekyc_layout=(LinearLayout)root.findViewById(R.id.ekyc_layout);
        tpi_layout=(LinearLayout)root.findViewById(R.id.tpi_layout);
        ng_pending_layout=(LinearLayout)root.findViewById(R.id.ng_pending_layout);
        rfc_layout=(LinearLayout)root.findViewById(R.id.rfc_layout);
        pmc_layout=(LinearLayout)root.findViewById(R.id.pmc_layout);
        ng_conversion_layout=(LinearLayout)root.findViewById(R.id.ng_conversion_layout);
       /* new_regestration_layout.setVisibility(View.GONE);
        ekyc_layout.setVisibility(View.GONE);
        tpi_layout.setVisibility(View.GONE);
        rfc_layout.setVisibility(View.GONE);*/

        if (sharedPrefs.getType_User().equals("DMA")){
            new_regestration_layout.setVisibility(View.VISIBLE);
            ekyc_layout.setVisibility(View.GONE);
            tpi_layout.setVisibility(View.GONE);
            ng_pending_layout.setVisibility(View.GONE);
            rfc_layout.setVisibility(View.GONE);
            ng_conversion_layout.setVisibility(View.GONE);
            pmc_layout.setVisibility(View.GONE);
        }
        else if (sharedPrefs.getType_User().equals("TPI")){
            new_regestration_layout.setVisibility(View.GONE);
            ekyc_layout.setVisibility(View.GONE);
            tpi_layout.setVisibility(View.VISIBLE);
            ng_pending_layout.setVisibility(View.VISIBLE);
            rfc_layout.setVisibility(View.GONE);
            ng_conversion_layout.setVisibility(View.GONE);
            pmc_layout.setVisibility(View.GONE);
        }
        else if (sharedPrefs.getType_User().equals("RFC")){
            new_regestration_layout.setVisibility(View.GONE);
            ekyc_layout.setVisibility(View.GONE);
            tpi_layout.setVisibility(View.GONE);
            ng_pending_layout.setVisibility(View.GONE);
            rfc_layout.setVisibility(View.VISIBLE);
            ng_conversion_layout.setVisibility(View.VISIBLE);
            pmc_layout.setVisibility(View.GONE);
        }
        else if (sharedPrefs.getType_User().equals("EKYC")){
            new_regestration_layout.setVisibility(View.GONE);
            ekyc_layout.setVisibility(View.VISIBLE);
            tpi_layout.setVisibility(View.GONE);
            ng_pending_layout.setVisibility(View.GONE);
            rfc_layout.setVisibility(View.GONE);
            ng_conversion_layout.setVisibility(View.GONE);
            pmc_layout.setVisibility(View.GONE);
            attendance_layout.setVisibility(View.GONE);
        }else if (sharedPrefs.getType_User().equals("PMCSI")){
            new_regestration_layout.setVisibility(View.GONE);
            pmc_layout.setVisibility(View.VISIBLE);

            ekyc_layout.setVisibility(View.GONE);
            tpi_layout.setVisibility(View.GONE);
            ng_pending_layout.setVisibility(View.GONE);
            rfc_layout.setVisibility(View.GONE);
            ng_conversion_layout.setVisibility(View.GONE);
        }else {
            sharedPrefs.setLoginStatus("false");
            Intent intent = new Intent(getActivity(), Login_Activity.class);
            startActivity(intent);
        }
        attendance_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance=new Intent(getActivity(), View_Attendance_Activity.class);
                startActivity(attendance);
            }
        });
        to_do_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activities=new Intent(getActivity(), To_DoList_Activity.class);
                 activities.putExtra("Type","0");
                startActivity(activities);
            }
        });
        learning_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent learning=new Intent(getActivity(), Learning_Activity.class);
                startActivity(learning);
            }
        });
        new_regestration_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todo_list=new Intent(getActivity(), Tab_Host_DMA.class);
                startActivity(todo_list);
            }
        });
        ekyc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance=new Intent(getActivity(), Tab_Host_EKYC.class);
                startActivity(attendance);
            }
        });
        tpi_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tpiIntent = new Intent(getActivity(), TPI_ViewPager_Activity.class);
                //Intent tpiIntent = new Intent(getActivity(), TPI_Connection_Lisitng.class);
                startActivity(tpiIntent);
            }
        });
        ng_pending_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent attendance=new Intent(getActivity(), Tab_Host_Pager.class);
                attendance.putExtra("heading","TPI");

                startActivity(attendance);*/
               Intent intent = new Intent(getActivity(), Tab_Host_Pager_TPI_Claim.class);
               startActivity(intent);
            }
        });
        rfc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rfcintent = new Intent(getActivity(), RFC_Connection_Listing.class);
                startActivity(rfcintent);
            }
        });
        ng_conversion_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance=new Intent(getActivity(), NgUserListActivity.class);
                startActivity(attendance);
            }
        });
        pmc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance=new Intent(getActivity(), Tab_Host_PMC.class);
                attendance.putExtra("heading","PMC");
                startActivity(attendance);
            }
        });
        //requestCameraAndStorage();


    }
    public void requestCameraAndStorage() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(getActivity(), permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                //Toast.makeText(getActivity(), "Camera+Storage granted.", Toast.LENGTH_SHORT).show();
                requestLocation();
            }
        });
    }
    public void requestLocation() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options().setRationaleDialogTitle("Info").setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {

            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
               // Toast.makeText(getActivity(), "Location denied.", Toast.LENGTH_SHORT).show();
            }
        });
   /* }
    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            TPI_Approve(Latitude,Longitude);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 300000); //execute in every 50000 ms*/
    }

    public void TPI_Approve(final String latitude, final String longitude) {
        /*materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Please wait....")
                .progress(true, 0)
                .show();*/
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.UserTracking,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response Save", json.toString());
                            //  Toast.makeText(getActivity(), "" + "Successfully", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   materialDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    Log.e("latitude",latitude);
                    Log.e("longitude", longitude);

                    params.put("latitude",latitude);
                    params.put("longitude", longitude);
                    params.put("id", sharedPrefs.getUUID());
                } catch (Exception e) {
                }
                return params;
            }
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }
    @Override
    public void onResume() {
        super.onResume();
      //  startStep1();
    }
    /**
     * Step 1: Check Google Play services
     */
    private void startStep1() {
        if (isGooglePlayServicesAvailable()) {
            startStep2(null);
        } else {
            Toast.makeText(getActivity(), R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        if (!mAlreadyStartedService && mMsgView != null) {
            mMsgView.setText(R.string.msg_location_service_started);
            //Start location sharing service to app server.........
            Intent intent = new Intent(getActivity(), LocationMonitoringService.class);
            getActivity().startService(intent);
            mAlreadyStartedService = true;
        }
    }
    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(getActivity(), status, 2404).show();
            }
            return false;
        }
        return true;
    }
    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionState2 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }
    /**
     * Start permissions requests.
     */
    private void requestPermissions() {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        boolean shouldProvideRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i("", "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i("", "Requesting permission");
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    private void showSnackbar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void onDestroy() {
        //Stop location sharing service to app server.........
        getActivity().stopService(new Intent(getActivity(), LocationMonitoringService.class));
        mAlreadyStartedService = false;
        super.onDestroy();
    }
    class IncomingMessageHandler extends Handler {
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
    }

    private void requestLocationPermission() {

        boolean foreground = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (foreground) {
            boolean background = ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (background) {
                handleLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
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
                        Toast.makeText(getActivity(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();

                        continue;
                    } else {
                        Toast.makeText(getActivity(), "Location Permission denied", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        background = true;
                        Toast.makeText(getActivity(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Background location location permission denied", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            if (foreground) {
                if (background) {
                    handleLocationUpdates();
                } else {
                    handleForegroundLocationUpdates();
                }
            }
        }
    }

    private void handleLocationUpdates() {
        //foreground and background
        Intent startServiceIntent = new Intent(getActivity(), MyIntentService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
        getActivity().startService(startServiceIntent);
        Toast.makeText(getActivity(),"Start Foreground and Background Location Updates",Toast.LENGTH_SHORT).show();
    }

    private void handleForegroundLocationUpdates() {
        //handleForeground Location Updates
        Intent startServiceIntent = new Intent(getActivity(), MyIntentService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
        getActivity().startService(startServiceIntent);
        Toast.makeText(getActivity(),"Start foreground location updates",Toast.LENGTH_SHORT).show();
    }
}
