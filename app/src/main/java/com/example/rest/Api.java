package com.example.rest;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.igl.Model.BpDetail;
import com.example.igl.Model.NguserListModel;
import com.example.igl.Model.TpiDetailResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.content.Context.MODE_PRIVATE;
import static com.example.constants.Constant.JMR_NO;
import static com.example.constants.Constant.PREFS_JMR_NO;

public interface Api {

    //String BASE_URL = "http://49.50.68.240:8080/";
    String BASE_URL = "http://49.50.68.239:8080/";
    String BASE_URL_TPI_DETAILS = "http://49.50.118.112:8080/";


    // tpi list for claim and unclaim
    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getUnclaimedAssignList(@Query("status") String status);

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgUserListClaimUnclaimList();

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getClaimUnclaimList(@Query("claim") String claim,@Query("status") String statusOP);

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getClaimUnclaimList(@Query("status") String statusOP);

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgPendingList(@Query("status") String status);

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgListForApproval(@Query("status") String statusOP,@Query("status") String statusDP,@Query("claim") String claim);
    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgListForApproval(@Query("status") String statusOP,@Query("status") String statusDP,@Query("status") String statusOH,@Query("claim") String claim);


    @GET("api/jmr/")
    Call<List<NguserListModel>> getParticularUser(@Query("jmr") String jmr);


    @GET("ekyc/bp_details/tpi_detail")
    Call<ArrayList<TpiDetailResponse>> getTpiDetails(@Query("bpNumber") String bpNumber);

    @PUT("api/jmr/")
    Call<List<NguserListModel>> getUpdateNgUserField1(@Query("jmr") String jmr_no,@Body NguserListModel updateNgUserField);




    @PUT("api/jmr/")
    Call<List<NguserListModel>> getPostPhoto(@Query("jmr") String jmr_no ,@Body NguserListModel updateNgUserField);

    @Multipart
    @PUT("api/jmr/")
    Call<List<NguserListModel>> uploadFile(@Query("jmr") String jmr_no1,@Part MultipartBody.Part recording,

                                           @Part ("jmr_no") RequestBody jmr_no

    );



}




