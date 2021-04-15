package com.example.rest;

import com.example.igl.Model.NguserListModel;

import java.util.ArrayList;

import retrofit2.Call;

public class CommunicationLink {



    public static Call<ArrayList<NguserListModel>> getNgUserListClaimUnclaimList() {
        ApiInterface service = CommunicationApi.getIglService();
        return service.getNgUserListClaimUnclaimList();
    }
}
