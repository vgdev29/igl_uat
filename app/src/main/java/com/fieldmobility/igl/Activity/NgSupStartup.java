package com.fieldmobility.igl.Activity;

import static com.fieldmobility.igl.utils.Utils.showProgressDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Mdpe.MdpeTpiApproval;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Riser.activity.RiserTpiApprovalDetailActivity;
import com.fieldmobility.igl.database.DatabaseUtil;
import com.fieldmobility.igl.database.DatabseDeleteListener;
import com.fieldmobility.igl.database.ReadNgUsersListener;
import com.fieldmobility.igl.rest.Api;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NgSupStartup extends AppCompatActivity {

    CardView ngpen, nghold, ngdone;
    TextView rfcpen_count_text,rfcdone_count_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng_sup_startup);
        ngdone = findViewById(R.id.cv_ngdone);
        ngpen = findViewById(R.id.cv_ngpen);
        nghold = findViewById(R.id.cv_nghold);
        rfcpen_count_text = findViewById(R.id.rfcpen_count_text);
        rfcdone_count_text = findViewById(R.id.rfcdone_count_text);


        ngpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NgSupStartup.this, NgSupListActivity.class);
                startActivity(intent);
            }
        });

        ngdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseUtil.fetchNgUsersFromDb(getApplicationContext(), "DP", new ReadNgUsersListener() {
                    @Override
                    public void onSuccess(List<NguserListModel> users) {
                        if (users != null && users.size() > 0) {
                            submitDPData(users);
//                            loadNgUserList(users);
                        }

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });

        nghold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseUtil.fetchNgUsersFromDb(getApplicationContext(), "OP", new ReadNgUsersListener() {
                    @Override
                    public void onSuccess(List<NguserListModel> users) {
                        if (users != null && users.size() > 0) {
                            submitOPData(users);
//                            loadNgUserList(users);
                        }

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNgHoldCount();
        refreshNgDoneCount();
    }

    MaterialDialog materialDialog;

    private void submitOPData(List<NguserListModel> users) {
        String data = new Gson().toJson(users);
        try {
            JSONArray dataArray = new JSONArray(data);
            JSONObject reqObj = new JSONObject();
            reqObj.put("data", dataArray);


            materialDialog = new MaterialDialog.Builder(NgSupStartup.this)
                    .content("Please wait....")
                    .progress(true, 0)

                    .show();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.PUT, Constants.NGHOLD_SYNC, dataArray, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    materialDialog.dismiss();
                    try {
                        Log.d("log", response.toString());

                        if (response != null && response.length() > 0) {
                            JSONObject resObj = response.getJSONObject(0);
                            if (resObj.getString("code").equals("200")) {
                                List<String> jmr_nos = new ArrayList();
                                JSONArray jmrs = resObj.getJSONArray("jmr");
                                if (jmrs != null) {
                                    for (int i = 0; i < jmrs.length(); i++) {
                                        jmr_nos.add(jmrs.getString(i));
                                    }
                                    DatabaseUtil.deleteUserByJmrNo(NgSupStartup.this, jmr_nos, new DatabseDeleteListener() {
                                        @Override
                                        public void onDataDeleted() {
                                            Log.e("Databse_log", "Deleted users successfully");
                                            try {
                                                refreshNgHoldCount();
                                                CommonUtils.toast_msg(NgSupStartup.this, resObj.getString("message"));
                                            } catch (JSONException jsonException) {
                                                jsonException.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure() {
                                            Log.e("Databse_log", "Deleting users failed");
                                        }
                                    });
                                }

                            } else {
                                CommonUtils.toast_msg(NgSupStartup.this, resObj.getString("message"));
                            }
                        }

                    } catch (Exception e) {
                        materialDialog.dismiss();
                        e.printStackTrace();
                    }
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    materialDialog.dismiss();
                    error.printStackTrace();
                    CommonUtils.toast_msg(NgSupStartup.this, "Something went wrong.Please try again");

                }
            }

            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    //or try with this:
                    //headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    return headers;
                }
            };
            int socketTimeout = 30000;

            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);


            requestQueue.add(jsonObjectRequest);

        } catch (Exception jsonException) {
            jsonException.printStackTrace();
        }
    }
    private void submitDPData(List<NguserListModel> users) {
        String data = new Gson().toJson(users);
        try {
            JSONArray dataArray = new JSONArray(data);
            JSONObject reqObj = new JSONObject();
            reqObj.put("data", dataArray);


            materialDialog = new MaterialDialog.Builder(NgSupStartup.this)
                    .content("Please wait....")
                    .progress(true, 0)

                    .show();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.PUT, Constants.NGDONE_SYNC, dataArray, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    materialDialog.dismiss();
                    try {
                        Log.d("log", response.toString());

                        if (response != null && response.length() > 0) {
                            JSONObject resObj = response.getJSONObject(0);
                            if (resObj.getString("code").equals("200")) {
                                List<String> jmr_nos = new ArrayList();
                                JSONArray jmrs = resObj.getJSONArray("jmr");
                                if (jmrs != null) {
                                    for (int i = 0; i < jmrs.length(); i++) {
                                        jmr_nos.add(jmrs.getString(i));
                                    }
                                    DatabaseUtil.deleteUserByJmrNo(NgSupStartup.this, jmr_nos, new DatabseDeleteListener() {
                                        @Override
                                        public void onDataDeleted() {
                                            Log.e("Databse_log", "Deleted users successfully");
                                            try {
                                                refreshNgDoneCount();
                                                CommonUtils.toast_msg(NgSupStartup.this, resObj.getString("message"));
                                            } catch (JSONException jsonException) {
                                                jsonException.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure() {
                                            Log.e("Databse_log", "Deleting users failed");
                                        }
                                    });
                                }

                            } else {
                                CommonUtils.toast_msg(NgSupStartup.this, resObj.getString("message"));
                            }
                        }

                    } catch (Exception e) {
                        materialDialog.dismiss();
                        e.printStackTrace();
                    }
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    materialDialog.dismiss();
                    error.printStackTrace();
                    CommonUtils.toast_msg(NgSupStartup.this, "Something went wrong.Please try again");

                }
            }

            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    //or try with this:
                    //headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    return headers;
                }
            };
            int socketTimeout = 30000;

            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);


            requestQueue.add(jsonObjectRequest);

        } catch (Exception jsonException) {
            jsonException.printStackTrace();
        }
    }


    private void refreshNgHoldCount() {
        DatabaseUtil.fetchNgUsersFromDb(getApplicationContext(), "OP", new ReadNgUsersListener() {
            @Override
            public void onSuccess(List<NguserListModel> users) {
                if (users != null) {
                    rfcpen_count_text.setText("" + users.size());
                    if (users.size() > 0) {
                        nghold.setAlpha(1f);
                        nghold.setEnabled(true);
                    } else {
                        nghold.setAlpha(0.5f);
                        nghold.setEnabled(false);
                    }
                }

            }

            @Override
            public void onFailure() {
                nghold.setAlpha(0.5f);
                nghold.setEnabled(false);
            }
        });

    }
    private void refreshNgDoneCount() {
        DatabaseUtil.fetchNgUsersFromDb(getApplicationContext(), "DP", new ReadNgUsersListener() {
            @Override
            public void onSuccess(List<NguserListModel> users) {
                if (users != null) {
                    rfcdone_count_text.setText("" + users.size());
                    if (users.size() > 0) {
                        ngdone.setAlpha(1f);
                        ngdone.setEnabled(true);
                    } else {
                        ngdone.setAlpha(0.5f);
                        ngdone.setEnabled(false);
                    }
                }

            }

            @Override
            public void onFailure() {
                ngdone.setAlpha(0.5f);
                ngdone.setEnabled(false);
            }
        });

    }

}