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
    private String customer_Name;
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
    private String meter_Type;
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
    private String rfC_Date;
    @SerializedName("amount_charged")
    @Expose
    private Double amount_to_be_Charged;
    @SerializedName("house_no")
    @Expose
    private String houseNo;
    @SerializedName("block_qtr")
    @Expose
    private String block_Qtr;
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
    private String street_gali_Road;


    @SerializedName("code_group")
    @Expose
    private String Code_Group;
    @SerializedName("area")

    @Expose
    private String area;
    @SerializedName("home_address")
    @Expose
    private String home_address;
    @SerializedName("meter_photo")
    @Expose
    private String meter_photo;
    @SerializedName("service_photo")
    @Expose
    private String service_photo;
    @SerializedName("installation_photo")
    @Expose
    private String installation_photo;
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
    private String email_ID;
    @SerializedName("alt_number")
    @Expose
    private String alt_Number;
    @SerializedName("landmark")
    @Expose
    private String landmark;

    @SerializedName("contractor_id")
    @Expose
    private String contractor_id;

    @SerializedName("tpi_id")
    @Expose
    private String tpi_id;

    @SerializedName("zi_id")
    @Expose
    private String zi_id;

    @SerializedName("supervisor_id")
    @Expose
    private String supervisor_id;

    @SerializedName("crm_status")
    @Expose
    private String crm_status;

    @SerializedName("crm_reason")
    @Expose
    private String crm_reason;

    @SerializedName("lead_no")
    @Expose
    private String lead_no;

    @SerializedName("start_job")
    @Expose
    private Boolean start_job;

    public Boolean getStart_job() {
        return start_job;
    }

    public void setStart_job(Boolean start_job) {
        this.start_job = start_job;
    }

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


    public String getMeterMake() {
        return meterMake;
    }

    public void setMeterMake(String meterMake) {
        this.meterMake = meterMake;
    }



    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
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


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getMeter_photo() {
        return meter_photo;
    }

    public void setMeter_photo(String meter_photo) {
        this.meter_photo = meter_photo;
    }

    public String getService_photo() {
        return service_photo;
    }

    public void setService_photo(String service_photo) {
        this.service_photo = service_photo;
    }

    public String getInstallation_photo() {
        return installation_photo;
    }

    public void setInstallation_photo(String installation_photo) {
        this.installation_photo = installation_photo;
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


    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public String getMeter_Type() {
        return meter_Type;
    }

    public void setMeter_Type(String meter_Type) {
        this.meter_Type = meter_Type;
    }

    public String getRfC_Date() {
        return rfC_Date;
    }

    public void setRfC_Date(String rfC_Date) {
        this.rfC_Date = rfC_Date;
    }

    public Double getAmount_to_be_Charged() {
        return amount_to_be_Charged;
    }

    public void setAmount_to_be_Charged(Double amount_to_be_Charged) {
        this.amount_to_be_Charged = amount_to_be_Charged;
    }

    public String getBlock_Qtr() {
        return block_Qtr;
    }

    public void setBlock_Qtr(String block_Qtr) {
        this.block_Qtr = block_Qtr;
    }

    public String getStreet_gali_Road() {
        return street_gali_Road;
    }

    public void setStreet_gali_Road(String street_gali_Road) {
        this.street_gali_Road = street_gali_Road;
    }

    public String getCode_Group() {
        return Code_Group;
    }

    public void setCode_Group(String code_Group) {
        Code_Group = code_Group;
    }

    public String getEmail_ID() {
        return email_ID;
    }

    public void setEmail_ID(String email_ID) {
        this.email_ID = email_ID;
    }

    public String getAlt_Number() {
        return alt_Number;
    }

    public void setAlt_Number(String alt_Number) {
        this.alt_Number = alt_Number;
    }
}

