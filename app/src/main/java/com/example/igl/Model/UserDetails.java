package com.example.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("firstName")
    @Expose
    private String firstName;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
