package com.example.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.example.igl.Activity.NgUserListActivity;
import com.example.igl.Model.NguserListModel;
import com.example.igl.Model.TpiDetailResponse;
import com.example.igl.interfaces.ListDataPasser;
import com.google.gson.internal.$Gson$Preconditions;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class DBManager {
    public static final String UPLOAD_WORKER_TAG = "CheckUploads";

    public static String API_BASE_URL = "http://49.50.68.239:8080/";
    public static String API_BASE_URL_TPI_DETAILS = "http://49.50.118.112:8080/";


    public static String getApiBaseUrl() {
        return API_BASE_URL;
    }

    public static Call loadNgUserList(final Context ctx, final String status) {
        try {
            Call<ArrayList<NguserListModel>> call_retrofit = CommunicationLink.getNgUserListClaimUnclaimList(status);
            call_retrofit.enqueue(new Callback<ArrayList<NguserListModel>>() {
                @Override
                public void onResponse(Call<ArrayList<NguserListModel>> call, Response<ArrayList<NguserListModel>> response) {
                    Log.e("MyError", "error.............");
                    if (response.isSuccessful()){
                        if (response.body()!=null){
                            final ArrayList<NguserListModel> nguserListModelArrayList = response.body();
                            ListDataPasser<NguserListModel> dataProvider_leave = (ListDataPasser) ctx;
                            dataProvider_leave.notifyDataPasser(nguserListModelArrayList);
                            Log.d("dataProvider_leave", String.valueOf(dataProvider_leave));
                        }

                    }


                }
                @Override
                public void onFailure(Call<ArrayList<NguserListModel>> call, Throwable t) {
                    Log.e("MyError", "error.............");
                }
            });
            return call_retrofit;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Call getClaimedList(final Context ctx,final String status, final String claim) {
        try {
            Call<ArrayList<NguserListModel>> call_retrofit = CommunicationLink.getClaimedList(status,claim);
            call_retrofit.enqueue(new Callback<ArrayList<NguserListModel>>() {
                @Override
                public void onResponse(Call<ArrayList<NguserListModel>> call, Response<ArrayList<NguserListModel>> response) {
                    Log.e("MyError", "error.............");
                    if (response.isSuccessful()){
                        if (response.body()!=null){
                            final ArrayList<NguserListModel> nguserListModelArrayList = response.body();
                            ListDataPasser<NguserListModel> dataProvider_leave = (ListDataPasser) ctx;
                            dataProvider_leave.notifyDataPasser(nguserListModelArrayList);
                            Log.d("dataProvider_leave", String.valueOf(dataProvider_leave));
                        }

                    }


                }
                @Override
                public void onFailure(Call<ArrayList<NguserListModel>> call, Throwable t) {
                    Log.e("MyError", "error.............");
                }
            });
            return call_retrofit;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
