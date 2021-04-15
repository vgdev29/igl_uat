package com.example.rest;

import android.content.Context;
import android.util.Log;

import com.example.igl.Model.NguserListModel;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBManager {

    public static String API_BASE_URL = "http://49.50.68.240:8080/";

    public static String getApiBaseUrl() {
        return API_BASE_URL;
    }

    public static Call getNgUserListClaimUnclaimList() {
        final Call<ArrayList<NguserListModel>> call_retrofit = CommunicationLink.getNgUserListClaimUnclaimList();
        call_retrofit.enqueue(new Callback<ArrayList<NguserListModel>>() {
            @Override
            public void onResponse(final Call<ArrayList<NguserListModel>> call, final Response<ArrayList<NguserListModel>> response) {
                new Thread() {
                    public void run() {
                        Log.e("MyError" , "error.............");
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<ArrayList<NguserListModel>> call, Throwable t) {
                Log.e("MyError" , "error.............");

            }
        });

        return call_retrofit;

    }





}
