package com.fieldmobility.igl.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.fieldmobility.igl.Helper.ConnectionDetector;
import com.fieldmobility.igl.Helper.GPSLocation;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Location_Activity  extends Activity {

    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    LinearLayout next_layout;
    ImageView back;
    public static String Latitude,Longitude,address,city,state,country,pincode;
    TextView home_address,city_address;
    String beforeEnable;
    LocationManager locationManager ;
    boolean GpsStatus ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        statusCheck();


        Layout_ID();
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted) {
            // {Some Code}
            try {
               // getLocationWithoutInternet();
                getLocationUsingInternet();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            try {
                //getLocationWithoutInternet();
                getLocationUsingInternet();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}
                }
            }
        }
    }
    private void Layout_ID() {
        home_address=findViewById(R.id.home_address);
        city_address=findViewById(R.id.city_address);
        back=(ImageView) findViewById(R.id.back);
        next_layout=(LinearLayout)findViewById(R.id.next_layout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
           // getLocationWithoutInternet();
            getLocationUsingInternet();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        next_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Location_Activity.this,Selfie_Activity.class);
                startActivity(intent);

            }
        });

    }
    private void getLocationUsingInternet() {
        boolean isInternetConnected = new ConnectionDetector(Location_Activity.this).isConnectingToInternet();
        if (isInternetConnected) {
            // getLocation_usingInternet.setEnabled(false);
            new GPSLocation(Location_Activity.this).turnGPSOn();// First turn on GPS
            String getLocation = new GPSLocation(Location_Activity.this).getMyCurrentLocation();// Get current location from
            Log.e("getLocation++",getLocation.toString());
            city_address.setText(GPSLocation.city+" "+GPSLocation.state+" "+GPSLocation.country+" "+GPSLocation.pincode);
            home_address.setText(GPSLocation.address);
            // Toast.makeText(getApplicationContext(), getLocation.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Location_Activity.this, "There is no internet connection.", Toast.LENGTH_SHORT).show();
        }
    }
    Location location1;
    @SuppressLint("MissingPermission")
    private void getLocationWithoutInternet() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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

                Latitude =String.valueOf(location.getLatitude());
                Longitude =String.valueOf(location.getLongitude());
              //  Toast.makeText(getApplicationContext(), location.getLatitude() + "     " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                // getLocation.setEnabled(true);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(Location_Activity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                     address = addresses.get(0).getAddressLine(0);
                     city = addresses.get(0).getLocality();
                     state = addresses.get(0).getAdminArea();
                     country = addresses.get(0).getCountryName();
                     pincode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    home_address.setText(address);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        };
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        if (locationManager != null) {

            location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location1 != null) {
               /// Toast.makeText(getApplicationContext(), location1.getLatitude() + "     " + location1.getLongitude(), Toast.LENGTH_SHORT).show();
                Latitude =String.valueOf(location1.getLatitude());
                Longitude =String.valueOf(location1.getLongitude());

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(Location_Activity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1);
                    address = addresses.get(0).getAddressLine(0);
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    pincode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    home_address.setText(address);
                    city_address.setText(city+" "+state+" "+country+" "+pincode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        new GPSLocation(Location_Activity.this).turnGPSOff();
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}