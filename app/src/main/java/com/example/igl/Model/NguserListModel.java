package com.example.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NguserListModel implements Serializable {
    @SerializedName("jmr_id")
    @Expose
    private String jmrId;
    @SerializedName("jmr_no")
    @Expose
    private String jmrNo;
    @SerializedName("bp_no")
    @Expose
    private String bpNo;
    @SerializedName("meter_no")
    @Expose
    private String meterNo;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("burner_details")
    @Expose
    private String burnerDetails;
    @SerializedName("ng_update_date")
    @Expose
    private String ngUpdateDate;
    @SerializedName("conversion_date")
    @Expose
    private String conversionDate;
    @SerializedName("ca_no")
    @Expose
    private String caNo;
    @SerializedName("meter_type")
    @Expose
    private String meterType;
    @SerializedName("meter_make")
    @Expose
    private String meterMake;
    @SerializedName("initial_reading")
    @Expose
    private String initialReading;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("rfc_date")
    @Expose
    private String rfcDate;
    @SerializedName("amount_charged")
    @Expose
    private Double amountCharged;
    @SerializedName("house_no")
    @Expose
    private String houseNo;
    @SerializedName("block_qtr")
    @Expose
    private String blockQtr;
    @SerializedName("society")
    @Expose
    private String society;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("floor")
    @Expose
    private String floor;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("photo1")
    @Expose
    private String photo1;
    @SerializedName("photo2")
    @Expose
    private String photo2;
    @SerializedName("photo3")
    @Expose
    private String photo3;
    @SerializedName("photo4")
    @Expose
    private String photo4;
    @SerializedName("customer_sign")
    @Expose
    private String customerSign;
    @SerializedName("executive_sign")
    @Expose
    private String executiveSign;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sub_status")
    @Expose
    private String subStatus;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("delay_date")
    @Expose
    private String delayDate;
    @SerializedName("recording")
    @Expose
    private String recording;
    @SerializedName("hold_images")
    @Expose
    private String holdImages;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("claim")
    @Expose
    private Boolean claim;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("alt_number")
    @Expose
    private String altNumber;
    @SerializedName("landmark")
    @Expose
    private String landmark;



    public String getBpNo() {
        return bpNo;
    }

    public void setBpNo(String bpNo) {
        this.bpNo = bpNo;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }





    public String getNgUpdateDate() {
        return ngUpdateDate;
    }

    public void setNgUpdateDate(String ngUpdateDate) {
        this.ngUpdateDate = ngUpdateDate;
    }

    public String getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(String conversionDate) {
        this.conversionDate = conversionDate;
    }



    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getMeterMake() {
        return meterMake;
    }

    public void setMeterMake(String meterMake) {
        this.meterMake = meterMake;
    }






    public String getRfcDate() {
        return rfcDate;
    }

    public void setRfcDate(String rfcDate) {
        this.rfcDate = rfcDate;
    }

    public Double getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(Double amountCharged) {
        this.amountCharged = amountCharged;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getBlockQtr() {
        return blockQtr;
    }

    public void setBlockQtr(String blockQtr) {
        this.blockQtr = blockQtr;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getCustomerSign() {
        return customerSign;
    }

    public void setCustomerSign(String customerSign) {
        this.customerSign = customerSign;
    }

    public String getExecutiveSign() {
        return executiveSign;
    }

    public void setExecutiveSign(String executiveSign) {
        this.executiveSign = executiveSign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelayDate() {
        return delayDate;
    }

    public void setDelayDate(String delayDate) {
        this.delayDate = delayDate;
    }

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public String getHoldImages() {
        return holdImages;
    }

    public void setHoldImages(String holdImages) {
        this.holdImages = holdImages;
    }



    public Boolean getClaim() {
        return claim;
    }

    public void setClaim(Boolean claim) {
        this.claim = claim;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAltNumber() {
        return altNumber;
    }

    public void setAltNumber(String altNumber) {
        this.altNumber = altNumber;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getJmrId() {
        return jmrId;
    }

    public void setJmrId(String jmrId) {
        this.jmrId = jmrId;
    }

    public String getJmrNo() {
        return jmrNo;
    }

    public void setJmrNo(String jmrNo) {
        this.jmrNo = jmrNo;
    }

    public String getBurnerDetails() {
        return burnerDetails;
    }

    public void setBurnerDetails(String burnerDetails) {
        this.burnerDetails = burnerDetails;
    }

    public String getCaNo() {
        return caNo;
    }

    public void setCaNo(String caNo) {
        this.caNo = caNo;
    }

    public String getInitialReading() {
        return initialReading;
    }

    public void setInitialReading(String initialReading) {
        this.initialReading = initialReading;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}

