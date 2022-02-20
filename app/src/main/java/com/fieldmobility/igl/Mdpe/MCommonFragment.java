package com.fieldmobility.igl.Mdpe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.databinding.FragmentMCommonBinding;
import com.fieldmobility.igl.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class MCommonFragment extends Fragment {


    private static final String ARG_ALLO = "allo";
    private static final String ARG_SUBALLO = "suballo";
    private static final String ARG_TPI = "tpiid";
    private static final String ARG_TYPE = "type";
    private static final String ARG_CONT = "contid";
    private static final String ARG_ZONE = "zone";
    String log = "pipefragment";
    FragmentMCommonBinding cmmnbinding;
    ArrayList<MapKeyValue_Model> sectionlist = new ArrayList<>();
    public String allocation="",suballocation="",dpr="",lati="",longi="",imagepath="",section="", tpiId ="", contId= "",zone = "";
    double input = 0.0;
    int type=0;
    Context activity;
    SharedPrefs sharedPrefs;
    private final int PICK_IMAGE_REQUEST_PIPELINE = 1;
    private final int CLICK_IMAGE_REQUEST_PIPELINE= 2;
    private Uri filepath;
    private Bitmap mBitmap;
    ProgressDialog progressDialog;
    public MCommonFragment() {

    }

    public static MCommonFragment newInstance(Activity activity, String allo, String suballo, String tpi, int type,String cont,String zone) {
        MCommonFragment fragment = new MCommonFragment();
        fragment.activity = activity;
        Bundle args = new Bundle();
        args.putString(ARG_ALLO, allo);
        args.putString(ARG_SUBALLO, suballo);
        args.putString(ARG_TPI, tpi);
        args.putString(ARG_CONT, cont);
        args.putString(ARG_ZONE, zone);
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allocation = getArguments().getString(ARG_ALLO);
            suballocation = getArguments().getString(ARG_SUBALLO);
            tpiId = getArguments().getString(ARG_TPI);
            type = getArguments().getInt(ARG_TYPE);
            contId = getArguments().getString(ARG_CONT);
            zone = getArguments().getString(ARG_ZONE);
            Log.d(log,allocation+"  on craete "+suballocation+"  "+tpiId);
        }
        sharedPrefs = new SharedPrefs(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(log, "  on craete view");
        cmmnbinding = FragmentMCommonBinding.inflate(inflater, container, false);
        initSection(type);
        cmmnbinding.etAllo.setText(allocation);
        cmmnbinding.etSuballo.setText(suballocation);
        String date = Utils.currentDate();
        dpr = allocation+"/"+suballocation+"/"+date+"/"+sharedPrefs.getUUID();
        cmmnbinding.etDpr.setText(dpr);
        if (!Utils.getLocationUsingInternet(activity).isEmpty()) {
            String latLong[] = Utils.getLocationUsingInternet(activity).split("/");
            lati = latLong[0];
            longi = latLong[1];
            cmmnbinding.etLat.setText(lati);
            cmmnbinding.etLongi.setText(longi);
        }
        else
        {
            statusCheck();
        }
        cmmnbinding.imgGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        cmmnbinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    dprNetworkCall();
                }
            }
        });
        return cmmnbinding.getRoot();
    }

    public void initSection(int type)
    {
        sectionlist.clear();
        switch (type)
        {
            case 0:
                sectionlist.add(new MapKeyValue_Model("0","Select Section"));
                break;
            case 1: //Excavation
                sectionlist.add(new MapKeyValue_Model("0","Select Section"));
                sectionlist.add(new MapKeyValue_Model("EX_TR","Excavation of trench or pits"));
                sectionlist.add(new MapKeyValue_Model("EX_HR","Excavation of Hard Rock"));
                sectionlist.add(new MapKeyValue_Model("EX_CA","Excavation in Congested Area"));
                sectionlist.add(new MapKeyValue_Model("EX_SA","Excavation in seepage area"));

                break;
            case 2: // Restoration
                sectionlist.add(new MapKeyValue_Model("0","Select Section"));
                sectionlist.add(new MapKeyValue_Model("RE_TR","Restoration of Trench"));
                sectionlist.add(new MapKeyValue_Model("RE_CE","Restoration of Cement"));
                break;
            case 3: //Survey
                sectionlist.add(new MapKeyValue_Model("0","Select Section"));
                sectionlist.add(new MapKeyValue_Model("GPR","GPR Survey"));
                break;
            case 4://Markers
                sectionlist.add(new MapKeyValue_Model("0","Select Section"));
                sectionlist.add(new MapKeyValue_Model("MRKR_STONE","Stone Route Markers"));
                sectionlist.add(new MapKeyValue_Model("MRKR_POLE","Pole markers with foundation"));
                sectionlist.add(new MapKeyValue_Model("MRKR_PLATE","Plate marker"));
                break;
            case 5://Liaisoning
                sectionlist.add(new MapKeyValue_Model("0","Select Section"));
                sectionlist.add(new MapKeyValue_Model("LIA_ALL","For All sizes"));
                sectionlist.add(new MapKeyValue_Model("LIA_PRO","For reserved/ Protected Forests"));
                sectionlist.add(new MapKeyValue_Model("LIA_UNPRO","For Unprotected Forest"));
                break;
            case 6://Shifting FRS
                sectionlist.add(new MapKeyValue_Model("0","Select Section"));
                sectionlist.add(new MapKeyValue_Model("FRS","Shifting and Installation of Field Regulating Station"));
                break;

            default:
                break;

        }
        cmmnbinding.spinnerMethod.setAdapter(new ArrayAdapter<MapKeyValue_Model>(activity, android.R.layout.simple_spinner_dropdown_item, sectionlist));
        cmmnbinding.spinnerMethod.setSelection(0);
        cmmnbinding.spinnerMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MapKeyValue_Model keyValue = (MapKeyValue_Model) parent.getSelectedItem();
                section = keyValue.getKey();
                //CommonUtils.toast_msg(activity,section);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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

    private void selectImage() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(activity);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_PIPELINE);


                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, CLICK_IMAGE_REQUEST_PIPELINE);
                    }
                });
        myAlertDialog.show();
    }
    private String getPath(Uri uri) {
        String path = null;
        try {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();
            cursor = activity.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return path;
    }
    private String  change_to_binary(Bitmap bitmapOrg) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        return ba1.toString();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST_PIPELINE:
                if (requestCode == PICK_IMAGE_REQUEST_PIPELINE && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
                    filepath = data.getData();
                    try {

                        mBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), filepath);
                        imagepath = getPath(filepath);
                        if (mBitmap != null) {
                            int nh = (int) ( mBitmap.getHeight() * (512.0 / mBitmap.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(mBitmap, 512, nh, true);
                            cmmnbinding.imgGraph.setImageBitmap(scaled);
                            imagepath = change_to_binary(mBitmap);

                        }

                        //  new ImageCompressionAsyncTask(this).execute(image_path_aadhar, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
                        Log.d(log, "After binary" + imagepath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CLICK_IMAGE_REQUEST_PIPELINE:
                if (resultCode == getActivity().RESULT_OK && requestCode == CLICK_IMAGE_REQUEST_PIPELINE) {
                    File f = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        mBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        int nh = (int) ( mBitmap.getHeight() * (512.0 / mBitmap.getWidth()) );
                        Bitmap scaled = Bitmap.createScaledBitmap(mBitmap, 512, nh, true);
                        String path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        imagepath = file.toString();
                        Log.d(log, "normal path= "+imagepath);
                        try {
                            outFile = new FileOutputStream(imagepath);
                            mBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outFile);
                            outFile.flush();
                            outFile.close();
                            cmmnbinding.imgGraph.setImageBitmap(scaled);
                            if (mBitmap != null) {

                                imagepath = change_to_binary(mBitmap);
                                Log.d(log, "after camera click conversion= "+imagepath);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;


        }
    }
    public boolean validate()
    {
        boolean isValid = true;
        if (allocation==null || allocation.isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(activity, "Allocation no. is Required");
        } else if (suballocation==null||suballocation.isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(activity, "Sub Allocation no. is required.");
        }
        else if (tpiId==null||tpiId.isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(activity, "Contractor Id is required.");
        }else if (lati==null||lati.isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(activity, "Location is required.Pls check your Internet/GPS");
        }
        else if (longi==null||longi.isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(activity, "Location is required.Pls check your Internet/GPS");
        }
        else if (section.equalsIgnoreCase("0")||section.isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(activity, "Pls select Size");
        }
        else if (cmmnbinding.etLength.getText().toString().trim().isEmpty()|| cmmnbinding.etLength.getText().toString().trim().equalsIgnoreCase("0")) {
            isValid = false;
            CommonUtils.toast_msg(activity, "Length can't be blank or '0'");
        }
        else if (imagepath==null||imagepath.isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(activity, "Pls select Image");
        }
        else {
            isValid = true;
        }

        return isValid;
    }

    public void dprNetworkCall() {

        progressDialog = ProgressDialog.show(activity, "", "Submitting Data....", true);
        progressDialog.setCancelable(false);

        JSONObject dpr_obj = new JSONObject();
        try {
            dpr_obj.put("allocationNo", allocation);
            dpr_obj.put("subAllocation", suballocation);
            dpr_obj.put("dprNo", dpr);
            dpr_obj.put("sectionId", section);
            dpr_obj.put("input", cmmnbinding.etLength.getText().toString().trim());
            dpr_obj.put("latitude", lati);
            dpr_obj.put("longitude", longi);
            dpr_obj.put("tpiId", tpiId);
            dpr_obj.put("contId", contId);
            dpr_obj.put("zone", zone);
            dpr_obj.put("filesPath", imagepath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Constants.MDPEDPR_CREATE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, dpr_obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d(log, "response= "+response.toString());
                        try {
                            int status = response.getInt("status");
                            Log.d(log, "status= "+status);
                            if (status==200) {
                                CommonUtils.toast_msg(activity,response.getString("message"));
                                getActivity().onBackPressed();
                            }
                            else
                            {
                                CommonUtils.toast_msg(activity,response.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                CommonUtils.toast_msg(activity,error.getMessage());
                Log.d(log, error.getLocalizedMessage());
            }


        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
}