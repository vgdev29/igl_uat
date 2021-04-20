package com.example.igl.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TpiDetailResponse implements Serializable {
    @SerializedName("bpNumber")
    @Expose
    private String bpNumber;
    @SerializedName("tpiName")
    @Expose
    private String tpiName;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("tpiId")
    @Expose
    private String tpiId;
    @SerializedName("codeGroup")
    @Expose
    private String codeGroup;

    public String getBpNumber() {
        return bpNumber;
    }

    public void setBpNumber(String bpNumber) {
        this.bpNumber = bpNumber;
    }

    public String getTpiName() {
        return tpiName;
    }

    public void setTpiName(String tpiName) {
        this.tpiName = tpiName;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getTpiId() {
        return tpiId;
    }

    public void setTpiId(String tpiId) {
        this.tpiId = tpiId;
    }

    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
    }

}