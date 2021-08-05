package com.fieldmobility.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RiserTpiListingModel {

    @SerializedName("Bp_Details")
    @Expose
    private ArrayList<BpDetail> bpDetails = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Sucess")
    @Expose
    private String sucess;
    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<BpDetail> getBpDetails() {
        return bpDetails;
    }

    public void setBpDetails(ArrayList<BpDetail> bpDetails) {
        this.bpDetails = bpDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSucess() {
        return sucess;
    }

    public void setSucess(String sucess) {
        this.sucess = sucess;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
