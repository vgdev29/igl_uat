package com.example.igl;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rest.Api;
import com.example.igl.Model.NguserListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NgUserListViewModel extends ViewModel {
    private MutableLiveData<List<NguserListModel>> ngUserList;
    private int size ;

    public LiveData<List<NguserListModel>> getngUserList(){
        if (ngUserList== null){
            ngUserList= new MutableLiveData<>();
            //loadNgUserList();

        }

        return ngUserList;

    }
    /*private void loadNgUserList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getNgUserList();


        call.enqueue(new Callback<List<NguserListModel>>() {
            @Override
            public void onResponse(Call<List<NguserListModel>> call, Response<List<NguserListModel>> response) {

                //finally we are setting the list to our MutableLiveData
                ngUserList.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error" , "error comes");

            }
        });
    }*/




}
