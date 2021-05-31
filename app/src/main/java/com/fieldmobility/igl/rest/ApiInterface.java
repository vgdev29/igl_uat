package com.fieldmobility.igl.rest;

import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.Model.TpiDetailResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgUserListClaimUnclaimList(@Query("status") String statusOP,@Query("supervisor_id") String supervisor_id);

    //get claim list
    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getClaimedList(@Query("status") String statusAS,@Query("claim") String claim);

    @GET("ekyc/bp_details/tpi_detail")
    Call<ArrayList<TpiDetailResponse>> getTpiDetails(@Query("bpNumber") String bpNumber);
}
