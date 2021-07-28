package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Adapter.SimpleTextSelectorAdapter;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Listeners.AddressSelectListener;
import com.fieldmobility.igl.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class NICustomerActivity extends AppCompatActivity implements View.OnClickListener, AddressSelectListener {
    EditText et_mob, et_name, et_email;
    TextView tv_city, tv_area, tv_society, tv_address;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    ArrayList<String> cityNameList = new ArrayList<>();
    ArrayList<String> cityIdList = new ArrayList<>();
    ArrayList<String> areaNameList = new ArrayList<>();
    ArrayList<String> areaIdList = new ArrayList<>();
    ArrayList<String> societyNameList = new ArrayList<>();
    ArrayList<String> societyIdList = new ArrayList<>();
    SimpleTextSelectorAdapter bottomsheetAdapter;
    String selectedCityId = "", selectedAreaId = "", selectedSocietyName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_i_customer);
        sharedPrefs = new SharedPrefs(this);
        findViews();
        bottomSheetInit();
        fetchCityList();
    }

    private void fetchCityList() {
        cityIdList.clear();
        cityNameList.clear();
        materialDialog = new MaterialDialog.Builder(NICustomerActivity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BP_CITY_LISTING + sharedPrefs.getZone_Code(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String city = jsonObject1.getString("user_city");
                        String city_id = jsonObject1.getString("user_id");
                        cityNameList.add(city);
                        cityIdList.add(city_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void fetchAreaList(final String city_id) {
        areaNameList.clear();
        areaIdList.clear();
        materialDialog = new MaterialDialog.Builder(NICustomerActivity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Arealist_reason_Socity + "/" + city_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray_area = jsonObject.getJSONArray("area");
                    for (int i = 0; i < jsonArray_area.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_area.getJSONObject(i);
                        String area_select = jsonObject1.getString("area");
                        String area_id = jsonObject1.getString("area_id");
                        areaIdList.add(area_id);
                        areaNameList.add(area_select);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void fetchSocietyList(String area_Id) {

        societyNameList.clear();
        materialDialog = new MaterialDialog.Builder(NICustomerActivity.this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Socity_List + area_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                materialDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // if(jsonObject.getInt("success")==1){
                    JSONArray jsonArray_society = jsonObject.getJSONArray("list_of_society");
                    for (int i = 0; i < jsonArray_society.length(); i++) {
                        JSONObject jsonObject1 = jsonArray_society.getJSONObject(i);
                        String society_name_select = jsonObject1.getString("society_name");
                        String society_id = jsonObject1.getString("society_id");
                        // Society_Id.add(society_id);
                        societyNameList.add(society_name_select);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void findViews() {
        et_mob = findViewById(R.id.et_mob);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        tv_city = findViewById(R.id.tv_city);
        tv_city.setOnClickListener(this);
        tv_area = findViewById(R.id.tv_area);
        tv_area.setOnClickListener(this);
        tv_society = findViewById(R.id.tv_society);
        tv_society.setOnClickListener(this);
        tv_address = findViewById(R.id.tv_address);
    }

    BottomSheetDialog dialog;
    TextView sheet_title;
    FrameLayout lt_search;

    private void bottomSheetInit() {
        View modalbottomsheet = getLayoutInflater().inflate(R.layout.simple_text_picker_bottomsheet, null);
        RecyclerView rv_bottomsheet = modalbottomsheet.findViewById(R.id.rv_bottomsheet);
        sheet_title = modalbottomsheet.findViewById(R.id.tv_title);
        lt_search = modalbottomsheet.findViewById(R.id.lt_search);
        EditText sheetEditText = modalbottomsheet.findViewById(R.id.et_search);
        rv_bottomsheet.setLayoutManager(new LinearLayoutManager(NICustomerActivity.this));
        bottomsheetAdapter = new SimpleTextSelectorAdapter(this);
        bottomsheetAdapter.setOnItemClickListeneer(this);
        rv_bottomsheet.setAdapter(bottomsheetAdapter);
        sheetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bottomsheetAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        modalbottomsheet.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        dialog.setContentView(modalbottomsheet);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_city) {
            openSheet("Select City");
        } else if (v == tv_area) {
            if (!selectedCityId.isEmpty()) {
                if (areaNameList.size() > 0) {
                    openSheet("Select Area");
                }
            } else
                Toast.makeText(NICustomerActivity.this, "Please Select the City", Toast.LENGTH_SHORT).show();


        } else if (v == tv_society) {
            if (!selectedAreaId.isEmpty()) {
                if (societyNameList.size() > 0) {
                    openSheet("Select Society");
                }
            } else
                Toast.makeText(NICustomerActivity.this, "Please Select the Area", Toast.LENGTH_SHORT).show();

        }

    }

    private void openSheet(String title) {
        sheet_title.setText(title);
        if (title.equals("Select Area")) {
            lt_search.setVisibility(View.VISIBLE);
            bottomsheetAdapter.setIsFor(SimpleTextSelectorAdapter.AREA_SELECTION);
            bottomsheetAdapter.setData(areaNameList);
        } else {
            lt_search.setVisibility(View.GONE);
            if (title.equals("Select City")) {
                bottomsheetAdapter.setIsFor(SimpleTextSelectorAdapter.CITY_SELECTION);
                bottomsheetAdapter.setData(cityNameList);
            } else {
                bottomsheetAdapter.setIsFor(SimpleTextSelectorAdapter.SOCIETY_SELECTION);
                bottomsheetAdapter.setData(societyNameList);
            }
        }

        dialog.show();
    }

    @Override
    public void onCitySelect(String city, int position) {
        tv_city.setText(city);
        tv_area.setText("Select Area");
        tv_society.setText("Select Society");
        selectedCityId = cityIdList.get(position);
        fetchAreaList(selectedCityId);
        dialog.dismiss();
    }

    @Override
    public void onAreaSelect(String area) {
        tv_area.setText(area);
        tv_society.setText("Select Society");
        int originalPosition = areaNameList.indexOf(area);
        selectedAreaId = areaIdList.get(originalPosition);
        fetchSocietyList(selectedAreaId);
        dialog.dismiss();

    }

    @Override
    public void onSocietySelect(String society, int position) {
        tv_society.setText(society);
        selectedSocietyName = society;
        dialog.dismiss();
    }
}