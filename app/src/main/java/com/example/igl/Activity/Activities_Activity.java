package com.example.igl.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.igl.Helper.ConnectionDetector;
import com.example.igl.Helper.GPSLocation;

import com.example.igl.Helper.LocationHelper;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MainActivity;
import com.example.igl.R;

public class Activities_Activity extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    final String TAG = "MainActivity.java";



    LocationHelper.LocationResult locationResult;
    LocationHelper locationHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_layout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        Layout_ID();
        getLocationWithoutInternet();
        getLocationUsingInternet();
    }

    private void Layout_ID() {
        // to get location updates, initialize LocationResult
        this.locationResult = new LocationHelper.LocationResult(){
            @Override
            public void gotLocation(Location location){

                //Got the location!
                if(location!=null){

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Toast.makeText(getApplicationContext(), latitude+"  "+longitude, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "lat: " + latitude + ", long: " + longitude);

                }else{
                    Log.e(TAG, "Location is null.");
                }

            }

        };

        // initialize our useful class,
        this.locationHelper = new LocationHelper();
    }

    // Method that will return location in longitude, latitude, city, state, and
    // country
    private void getLocationUsingInternet() {

        boolean isInternetConnected = new ConnectionDetector(Activities_Activity.this)
                .isConnectingToInternet();

        // Before proceding we have to check if therr is internet connection or
        // not
        if (isInternetConnected) {
           // getLocation_usingInternet.setText("Please wait...");// while getting
            // location
            // please wait
            // and disable
            // the button
           // getLocation_usingInternet.setEnabled(false);
            new GPSLocation(Activities_Activity.this).turnGPSOn();// First turn on GPS
            String getLocation = new GPSLocation(Activities_Activity.this)
                    .getMyCurrentLocation();// Get current location from
            Log.e("getLocation++",getLocation.toString());
            // Location class
           // displayInternetLocation.setText(getLocation);// Set location over
            // textview
            Toast.makeText(
                    getApplicationContext(),
                    getLocation.toString(), Toast.LENGTH_SHORT)
                    .show();
            // Now again change the state of button
          //  getLocation_usingInternet.setText(R.string.location_internet);
           // getLocation_usingInternet.setEnabled(true);
        } else {

            // If there is no internet connection toast will be displayed
            Toast.makeText(Activities_Activity.this,
                    "There is no internet connection.", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    // Method that will fetch the location in longitude and latitude in absence
    // of internet
    @SuppressLint("MissingPermission")
    private void getLocationWithoutInternet() {

        // Change the state of button
        //getLocation.setText("Please wait..");
       // getLocation.setEnabled(false);

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {

                // Display currnet longitude and latitude over textview
               // displayLocation.setText("Latitude : " + location.getLatitude() + "nLongitude : " + location.getLongitude());

                // The toast will show loaction continuosly as we are requesting
                // local updates
                Toast.makeText(
                        getApplicationContext(),
                        location.getLatitude() + "     "
                                + location.getLongitude(), Toast.LENGTH_SHORT)
                        .show();
                Log.e("location", String.valueOf(location.getLatitude()));
                Log.e("location++", String.valueOf(location.getLongitude()));
                // Now, again change the state of button
              //  getLocation.setText(R.string.location_without_internet);
               // getLocation.setEnabled(true);

            }
        };

        // Register the listener with the Location Manager to receive location
        // updates
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        // When activity destroyed we have to turn off GPS
        new GPSLocation(Activities_Activity.this).turnGPSOff();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
