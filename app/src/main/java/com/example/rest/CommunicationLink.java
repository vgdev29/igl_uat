package com.example.rest;

import com.example.igl.Model.NguserListModel;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class CommunicationLink {
    public static <T> T getResponse(Call call) throws IOException {
        return (T) call.execute().body();
    }



    public static Call<ArrayList<NguserListModel>> getNgUserListClaimUnclaimList(String status) {
        ApiInterface service = CommunicationApi.getIglService();
        return service.getNgUserListClaimUnclaimList(status);
    }
    public static Call<ArrayList<NguserListModel>> getClaimedList(String status ,String claimed) {
        ApiInterface service = CommunicationApi.getIglService();
        return service.getClaimedList(status ,claimed);
    }

}
