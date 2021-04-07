package com.example.rest;

import android.content.SharedPreferences;

import com.example.igl.Model.NguserListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.content.Context.MODE_PRIVATE;
import static com.example.constants.Constant.PREFS_JMR_NO;

public interface Api {

    //String BASE_URL = "https://simplifiedcoding.net/demos/";
    //String BASE_URL = "https://jsonplaceholder.typicode.com/";
    String BASE_URL = "http://49.50.68.240:8080/";



    @GET("api/jmr")
    Call<ArrayList<NguserListModel>> getNgUserList();

    String jmr_no ="2228";

    /*@GET("api/jmr/"+jmr_no)
    Call<List<NguserListModel>> getParticularUser() ;*/

    @GET("api/jmr/{jmr_no}")
    Call<NguserListModel> getParticularUser(@Path(value = "jmr_no", encoded = true) String userId);

    @PUT("api/jmr/"+jmr_no)
    Call<List<NguserListModel>> getUpdateNgUserField(@Body NguserListModel updateNgUserField);


}




