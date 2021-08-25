package com.fieldmobility.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RiserTpiListingModel {
    public class BpDetail {

        @SerializedName("iglFirstName")
        @Expose
        private String iglFirstName;
        @SerializedName("iglMobileNo")
        @Expose
        private String iglMobileNo;
        @SerializedName("iglCityRegion")
        @Expose
        private String iglCityRegion;
        @SerializedName("iglArea")
        @Expose
        private String iglArea;
        @SerializedName("iglSociety")
        @Expose
        private String iglSociety;
        @SerializedName("iglHouseNo")
        @Expose
        private String iglHouseNo;
        @SerializedName("iglHouseType")
        @Expose
        private String iglHouseType;
        @SerializedName("iglBlockQtrTowerWing")
        @Expose
        private String iglBlockQtrTowerWing;
        @SerializedName("iglFloor")
        @Expose
        private String iglFloor;
        @SerializedName("riserNo")
        @Expose
        private String riserNo;
        @SerializedName("bpNumber")
        @Expose
        private String bpNumber;
        @SerializedName("connectedHouse")
        @Expose
        private String connectedHouse;
        @SerializedName("propertyType")
        @Expose
        private String propertyType;
        @SerializedName("gasType")
        @Expose
        private String gasType;
        @SerializedName("hseFloor")
        @Expose
        private String hseFloor;
        @SerializedName("hseGi")
        @Expose
        private String hseGi;
        @SerializedName("length")
        @Expose
        private String length;
        @SerializedName("isolationValve")
        @Expose
        private String isolationValve;
        @SerializedName("laying")
        @Expose
        private String laying;
        @SerializedName("testing")
        @Expose
        private String testing;
        @SerializedName("commissioning")
        @Expose
        private String commissioning;
        @SerializedName("lateralTapping")
        @Expose
        private String lateralTapping;
        @SerializedName("areaType")
        @Expose
        private String areaType;
        @SerializedName("site_photo")
        @Expose
        private Object sitePhoto;
        @SerializedName("contractorId")
        @Expose
        private String contractorId;
        @SerializedName("tpiId")
        @Expose
        private String tpiId;
        @SerializedName("tpiApproval")
        @Expose
        private String tpiApproval;
        @SerializedName("approvalDate")
        @Expose
        private String approvalDate;
        @SerializedName("supervisorId")
        @Expose
        private String supervisorId;
        @SerializedName("completionDate")
        @Expose
        private String completionDate;
        @SerializedName("tpiRemarks")
        @Expose
        private String tpiRemarks;
        @SerializedName("riserStatus")
        @Expose
        private String riserStatus;
        @SerializedName("total_ib")
        @Expose
        private String totalIb;
        @SerializedName("imageList")
        @Expose
        private ArrayList<String> imageList = null;

        public String getIglFirstName() {
            return iglFirstName;
        }

        public void setIglFirstName(String iglFirstName) {
            this.iglFirstName = iglFirstName;
        }

        public String getIglMobileNo() {
            return iglMobileNo;
        }

        public void setIglMobileNo(String iglMobileNo) {
            this.iglMobileNo = iglMobileNo;
        }

        public String getIglCityRegion() {
            return iglCityRegion;
        }

        public void setIglCityRegion(String iglCityRegion) {
            this.iglCityRegion = iglCityRegion;
        }

        public String getIglArea() {
            return iglArea;
        }

        public void setIglArea(String iglArea) {
            this.iglArea = iglArea;
        }

        public String getIglSociety() {
            return iglSociety;
        }

        public void setIglSociety(String iglSociety) {
            this.iglSociety = iglSociety;
        }

        public String getIglHouseNo() {
            return iglHouseNo;
        }

        public void setIglHouseNo(String iglHouseNo) {
            this.iglHouseNo = iglHouseNo;
        }

        public String getIglHouseType() {
            return iglHouseType;
        }

        public void setIglHouseType(String iglHouseType) {
            this.iglHouseType = iglHouseType;
        }

        public String getIglBlockQtrTowerWing() {
            return iglBlockQtrTowerWing;
        }

        public void setIglBlockQtrTowerWing(String iglBlockQtrTowerWing) {
            this.iglBlockQtrTowerWing = iglBlockQtrTowerWing;
        }

        public String getIglFloor() {
            return iglFloor;
        }

        public void setIglFloor(String iglFloor) {
            this.iglFloor = iglFloor;
        }

        public String getRiserNo() {
            return riserNo;
        }

        public void setRiserNo(String riserNo) {
            this.riserNo = riserNo;
        }

        public String getBpNumber() {
            return bpNumber;
        }

        public void setBpNumber(String bpNumber) {
            this.bpNumber = bpNumber;
        }

        public String getConnectedHouse() {
            return connectedHouse;
        }

        public void setConnectedHouse(String connectedHouse) {
            this.connectedHouse = connectedHouse;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public String getGasType() {
            return gasType;
        }

        public void setGasType(String gasType) {
            this.gasType = gasType;
        }

        public String getHseFloor() {
            return hseFloor;
        }

        public void setHseFloor(String hseFloor) {
            this.hseFloor = hseFloor;
        }

        public String getHseGi() {
            return hseGi;
        }

        public void setHseGi(String hseGi) {
            this.hseGi = hseGi;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getIsolationValve() {
            return isolationValve;
        }

        public void setIsolationValve(String isolationValve) {
            this.isolationValve = isolationValve;
        }

        public String getLaying() {
            return laying;
        }

        public void setLaying(String laying) {
            this.laying = laying;
        }

        public String getTesting() {
            return testing;
        }

        public void setTesting(String testing) {
            this.testing = testing;
        }

        public String getCommissioning() {
            return commissioning;
        }

        public void setCommissioning(String commissioning) {
            this.commissioning = commissioning;
        }

        public String getLateralTapping() {
            return lateralTapping;
        }

        public void setLateralTapping(String lateralTapping) {
            this.lateralTapping = lateralTapping;
        }

        public String getAreaType() {
            return areaType;
        }

        public void setAreaType(String areaType) {
            this.areaType = areaType;
        }

        public Object getSitePhoto() {
            return sitePhoto;
        }

        public void setSitePhoto(Object sitePhoto) {
            this.sitePhoto = sitePhoto;
        }

        public String getContractorId() {
            return contractorId;
        }

        public void setContractorId(String contractorId) {
            this.contractorId = contractorId;
        }

        public String getTpiId() {
            return tpiId;
        }

        public void setTpiId(String tpiId) {
            this.tpiId = tpiId;
        }

        public String getTpiApproval() {
            return tpiApproval;
        }

        public void setTpiApproval(String tpiApproval) {
            this.tpiApproval = tpiApproval;
        }

        public String getApprovalDate() {
            return approvalDate;
        }

        public void setApprovalDate(String approvalDate) {
            this.approvalDate = approvalDate;
        }

        public String getSupervisorId() {
            return supervisorId;
        }

        public void setSupervisorId(String supervisorId) {
            this.supervisorId = supervisorId;
        }

        public String getCompletionDate() {
            return completionDate;
        }

        public void setCompletionDate(String completionDate) {
            this.completionDate = completionDate;
        }

        public String getTpiRemarks() {
            return tpiRemarks;
        }

        public void setTpiRemarks(String tpiRemarks) {
            this.tpiRemarks = tpiRemarks;
        }

        public String getRiserStatus() {
            return riserStatus;
        }

        public void setRiserStatus(String riserStatus) {
            this.riserStatus = riserStatus;
        }

        public String getTotalIb() {
            return totalIb;
        }

        public void setTotalIb(String totalIb) {
            this.totalIb = totalIb;
        }

        public ArrayList<String> getImageList() {
            return imageList;
        }

        public void setImageList(ArrayList<String> imageList) {
            this.imageList = imageList;
        }

    }

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
