package com.fieldmobility.igl.rest;

import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Model.NgSupervisorResponse;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.Model.TpiDetailResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = Constants.BASE_URL_PYTHON;
    String BASE_URL_TPI_DETAILS = Constants.BASE_URL;


    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getUnclaimedAssignList(@Query("status") String status,@Query("zoneId") String zoneId);

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgUserListClaimUnclaimList();

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getClaimUnclaimList(@Query("claim") String claim,@Query("status") String statusOP);

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getClaimUnclaimList(@Query("status") String statusOP);

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgPendingList(@Query("status") String status);

    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgListForApproval(@Query("status") String statusOP,@Query("status") String statusDP,@Query("claim") String claim, @Query("tpi_id") String tpi_id);
  /*  @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgListForApproval(@Query("status") String statusOP,@Query("status") String statusDP,@Query("status") String statusOH,@Query("claim") String claim);
*/

    @GET("api/jmr/")
    Call<List<NguserListModel>> getParticularUser(@Query("jmr") String jmr);


    @GET("ekyc/bp_details/NgTpiDetails")
    Call< TpiDetailResponse> getTpiDetails(@Query("Tpiemailid") String Tpiemailid);

    @PUT("api/jmrhold/")
    Call<List<NguserListModel>> getUpdateNgUserField1(@Query("jmr") String jmr_no,@Body NguserListModel updateNgUserField);

    @PUT("api/jmr/")
    Call<List<NguserListModel>> getUpdateNgUserFieldnull(@Query("jmr") String jmr_no,@Query("claim_date") String claim_date);


    @PUT("api/jmrdone/")
    Call<List<NguserListModel>> getPostPhoto(@Query("jmr") String jmr_no ,@Body NguserListModel updateNgUserField);

    @Multipart
    @PUT("api/jmr/")
    Call<List<NguserListModel>> uploadFile(@Query("jmr") String jmr_no1,@Part MultipartBody.Part recording, @Part ("jmr_no") RequestBody jmr_no

    );



    @GET("ekyc/bp_details/userDetails")
    Call<NgSupervisorResponse> getNgAgentData(@Query("Supervisoremailid") String Supervisoremailid,@Query("Contractoremailid") String Contractoremailid);




}




