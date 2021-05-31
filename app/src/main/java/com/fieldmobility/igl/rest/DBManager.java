package com.fieldmobility.igl.rest;

import android.content.Context;
import android.util.Log;

import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.interfaces.ListDataPasser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBManager {
    public static final String UPLOAD_WORKER_TAG = "CheckUploads";

    public static String API_BASE_URL = "http://49.50.68.239:8080/";
    public static String API_BASE_URL_TPI_DETAILS = "http://49.50.118.112:8080/";


    public static String getApiBaseUrl() {
        return API_BASE_URL;
    }

    public static Call loadNgUserList(final Context ctx, String status, final String supervisor_id) {
        try {
            Call<ArrayList<NguserListModel>> call_retrofit = CommunicationLink.getNgUserListClaimUnclaimList(status,supervisor_id);
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
