package com.example.rest;

import com.example.igl.Model.NguserListModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {


    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgUserListClaimUnclaimList();
}
