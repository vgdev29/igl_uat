
package com.fieldmobility.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class BpListing implements Serializable {

    @SerializedName("Bp_Details")
    private ArrayList<BpDetail> bpDetails;
    @SerializedName("Message")
    private String message;
    @Expose
    private String status;
    @SerializedName("Sucess")
    private String sucess;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSucess() {
        return sucess;
    }

    public void setSucess(String sucess) {
        this.sucess = sucess;
    }

}
