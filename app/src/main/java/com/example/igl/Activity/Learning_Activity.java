package com.example.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igl.Adapter.Learning_Adapter;
import com.example.igl.Adapter.New_BP_NO_Adapter;
import com.example.igl.Helper.Constants;
import com.example.igl.Helper.RecyclerItemClickListener;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Learning_Activity extends Activity implements New_BP_NO_Adapter.ContactsAdapterListener{


    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    ImageView back,new_regestration;
    MaterialDialog materialDialog;
    private List<Bp_No_Item> bp_no_array;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Learning_Adapter adapter;
    String Url_path;
    EditText editTextSearch;
  //  TextView list_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_layout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPrefs=new SharedPrefs(this);
        Layout_ID();
        checkSystemWritePermission();
    }

    private boolean checkSystemWritePermission() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            Log.d("TAG", "Can Write Settings: " + retVal);
            if(retVal){

            }else{
                openAndroidPermissionsMenu();
            }
        }
        return retVal;
    }


    private void openAndroidPermissionsMenu() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + Learning_Activity.this.getPackageName()));
        startActivity(intent);

    }
    private void Layout_ID() {
        back =(ImageView)findViewById(R.id.back);
        //list_count=findViewById(R.id.list_count);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bp_no_array=new ArrayList<>();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    bp_no_array.clear();
                    Bp_No_List();
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                 Url_path = bp_no_array.get(position).getUrl_path();
                String Type_url = bp_no_array.get(position).getType_url();
                if(Type_url.equals("Vedio")){
                    Intent intent=new Intent(Learning_Activity.this,Video_View.class);
                    intent.putExtra("video",Url_path);
                    intent.putExtra("type",Type_url);
                    startActivity(intent);
                }else {
                    Url_path = bp_no_array.get(position).getUrl_path();
                   /* Intent intent=new Intent(Learning_Activity.this,Video_View.class);
                    intent.putExtra("video",Url_path);
                    startActivity(intent);*/
                 //  openPdf();


                    Intent intent=new Intent(Learning_Activity.this,Waveview_NewData.class);
                    intent.putExtra("pdf",Url_path);
                    intent.putExtra("type",Type_url);
                    startActivity(intent);
                }

            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));



        Bp_No_List();
    }

    private void openPdf() {
        File file = new File(Url_path);
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            Log.e("uri++",uri.toString());
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(Learning_Activity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }

    }
    public void Bp_No_List() {
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Learning,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response++",jsonObject.toString());
                            final JSONObject Bp_Details = jsonObject.getJSONObject("Learning_Details");
                            JSONArray payload=Bp_Details.getJSONArray("learning");
                            //list_count.setText("Count= "+String.valueOf(payload.length()));
                            for(int i=0; i<=payload.length();i++) {
                                JSONObject data_object=payload.getJSONObject(i);
                                Bp_No_Item bp_no_item = new Bp_No_Item();
                                bp_no_item.setUrl_path(data_object.getString("url_path"));
                                bp_no_item.setType_url(data_object.getString("type_url"));
                                bp_no_item.setText(data_object.getString("text"));

                                bp_no_array.add(bp_no_item);
                                Log.e("bp_no_array++",bp_no_array.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        adapter = new Learning_Adapter(Learning_Activity.this,bp_no_array,Learning_Activity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        materialDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        bp_no_array.clear();
        adapter.notifyDataSetChanged();
        Bp_No_List();
    }

    @Override
    public void onContactSelected(Bp_No_Item contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getBp_number() + ", " + contact.getFirst_name(), Toast.LENGTH_LONG).show();

    }
}
