package com.example.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NgSupervisorResponse {
    @SerializedName("UserDetails")
    @Expose
    private UserDetails userDetails;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Sucess")
    @Expose
    private boolean sucess;
    @SerializedName("status")
    @Expose
    private String status;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
