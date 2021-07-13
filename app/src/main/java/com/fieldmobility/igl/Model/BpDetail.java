
package com.fieldmobility.igl.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BpDetail {

    @SerializedName("aadhaar_number")
    private String aadhaarNumber;
    @Expose
    private String addressCard;
    @Expose
    private String addressProof;
    @Expose
    private String adhaarCard;
    @Expose
    private String amount;
    @Expose
    private String area;
    @SerializedName("block_qtr_tower_wing")
    private String blockQtrTowerWing;
    @SerializedName("bp_date")
    private String bpDate;
    @SerializedName("bp_number")
    private String bpNumber;
    @SerializedName("ca_no")
    private String caNo;
    @Expose
    private String chequeDate;
    @Expose
    private String chequeNo;
    @SerializedName("city_region")
    private String cityRegion;
    @Expose
    private String claimFlag;
    @Expose
    private String controlRoom;
    @SerializedName("customer_type")
    private String customerType;
    @Expose
    private String declinedDescription;
    @Expose
    private String document;
    @Expose
    private String document1;
    @Expose
    private String drawnOn;
    @SerializedName("email_id")
    private String emailId;
    @Expose
    private String fesabilityDate;
    @Expose
    private String fesabilityTpiName;
    @Expose
    private String fesabilityTpimobileNo;
    @SerializedName("first_name")
    private String firstName;
    @Expose
    private String floor;
    @Expose
    private String holdStatus;
    @SerializedName("house_no")
    private String houseNo;
    @SerializedName("house_type")
    private String houseType;
    @Expose
    private String idproof;
    @SerializedName("igl_code_group")
    private String iglCodeGroup;
    @SerializedName("igl_status")
    private String iglStatus;
    @Expose
    private String jobFlag;
    @Expose
    private String landmark;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("lead_no")
    private String leadNo;
    @SerializedName("lpg_company")
    private String lpgCompany;
    @SerializedName("lpg_conNo")
    private String lpgConNo;
    @SerializedName("lpg_distributor")
    private String lpgDistributor;
    @Expose
    private String meterNo;
    @SerializedName("middle_name")
    private String middleName;
    @SerializedName("mobile_number")
    private String mobileNumber;
    @Expose
    private String ownerName;
    @Expose
    private String pincode;
    @SerializedName("pipeline_description")
    private String pipelineDescription;
    @Expose
    private String pipelineId;
    @Expose
    private String plantCode;
    @Expose
    private String rfcTpi;
    @Expose
    private String rfcVendor;
    @Expose
    private String rfcadmin;
    @Expose
    private String rfcmobileNo;
    @Expose
    private String rfctpiname;
    @Expose
    private String society;
    @SerializedName("street_gali_road")
    private String streetGaliRoad;
    @SerializedName("unique_lpg_Id")
    private String uniqueLpgId;
    @Expose
    private String vendorMobileNo;

    @Expose
    private String rfcVendorName;
    @Expose
    private String rfcVendorMobileNo;
    @Expose
    private String rfcAdminMobileNo;
    @Expose
    private String rfcAdminName;
    @Expose
    private String ZoneCode;
    @Expose
    String igl_rfcvendor_assigndate;


    public String getIgl_rfcvendor_assigndate() {
        return igl_rfcvendor_assigndate;
    }

    public void setIgl_rfcvendor_assigndate(String igl_rfcvendor_assigndate) {
        this.igl_rfcvendor_assigndate = igl_rfcvendor_assigndate;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getAddressCard() {
        return addressCard;
    }

    public void setAddressCard(String addressCard) {
        this.addressCard = addressCard;
    }

    public String getAddressProof() {
        return addressProof;
    }

    public void setAddressProof(String addressProof) {
        this.addressProof = addressProof;
    }

    public String getAdhaarCard() {
        return adhaarCard;
    }

    public void setAdhaarCard(String adhaarCard) {
        this.adhaarCard = adhaarCard;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBlockQtrTowerWing() {
        return blockQtrTowerWing;
    }

    public void setBlockQtrTowerWing(String blockQtrTowerWing) {
        this.blockQtrTowerWing = blockQtrTowerWing;
    }

    public String getBpDate() {
        return bpDate;
    }

    public void setBpDate(String bpDate) {
        this.bpDate = bpDate;
    }

    public String getBpNumber() {
        return bpNumber;
    }

    public void setBpNumber(String bpNumber) {
        this.bpNumber = bpNumber;
    }

    public String getCaNo() {
        return caNo;
    }

    public void setCaNo(String caNo) {
        this.caNo = caNo;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(String cityRegion) {
        this.cityRegion = cityRegion;
    }

    public String getClaimFlag() {
        return claimFlag;
    }

    public void setClaimFlag(String claimFlag) {
        this.claimFlag = claimFlag;
    }

    public String getControlRoom() {
        return controlRoom;
    }

    public void setControlRoom(String controlRoom) {
        this.controlRoom = controlRoom;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getDeclinedDescription() {
        return declinedDescription;
    }

    public void setDeclinedDescription(String declinedDescription) {
        this.declinedDescription = declinedDescription;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocument1() {
        return document1;
    }

    public void setDocument1(String document1) {
        this.document1 = document1;
    }

    public String getDrawnOn() {
        return drawnOn;
    }

    public void setDrawnOn(String drawnOn) {
        this.drawnOn = drawnOn;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFesabilityDate() {
        return fesabilityDate;
    }

    public void setFesabilityDate(String fesabilityDate) {
        this.fesabilityDate = fesabilityDate;
    }

    public String getFesabilityTpiName() {
        return fesabilityTpiName;
    }

    public void setFesabilityTpiName(String fesabilityTpiName) {
        this.fesabilityTpiName = fesabilityTpiName;
    }

    public String getFesabilityTpimobileNo() {
        return fesabilityTpimobileNo;
    }

    public void setFesabilityTpimobileNo(String fesabilityTpimobileNo) {
        this.fesabilityTpimobileNo = fesabilityTpimobileNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getHoldStatus() {
        return holdStatus;
    }

    public void setHoldStatus(String holdStatus) {
        this.holdStatus = holdStatus;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getIdproof() {
        return idproof;
    }

    public void setIdproof(String idproof) {
        this.idproof = idproof;
    }

    public String getIglCodeGroup() {
        return iglCodeGroup;
    }

    public void setIglCodeGroup(String iglCodeGroup) {
        this.iglCodeGroup = iglCodeGroup;
    }

    public String getIglStatus() {
        return iglStatus;
    }

    public void setIglStatus(String iglStatus) {
        this.iglStatus = iglStatus;
    }

    public String getJobFlag() {
        return jobFlag;
    }

    public void setJobFlag(String jobFlag) {
        this.jobFlag = jobFlag;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLeadNo() {
        return leadNo;
    }

    public void setLeadNo(String leadNo) {
        this.leadNo = leadNo;
    }

    public String getLpgCompany() {
        return lpgCompany;
    }

    public void setLpgCompany(String lpgCompany) {
        this.lpgCompany = lpgCompany;
    }

    public String getLpgConNo() {
        return lpgConNo;
    }

    public void setLpgConNo(String lpgConNo) {
        this.lpgConNo = lpgConNo;
    }

    public String getLpgDistributor() {
        return lpgDistributor;
    }

    public void setLpgDistributor(String lpgDistributor) {
        this.lpgDistributor = lpgDistributor;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPipelineDescription() {
        return pipelineDescription;
    }

    public void setPipelineDescription(String pipelineDescription) {
        this.pipelineDescription = pipelineDescription;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getRfcTpi() {
        return rfcTpi;
    }

    public void setRfcTpi(String rfcTpi) {
        this.rfcTpi = rfcTpi;
    }

    public String getRfcVendor() {
        return rfcVendor;
    }

    public void setRfcVendor(String rfcVendor) {
        this.rfcVendor = rfcVendor;
    }

    public String getRfcadmin() {
        return rfcadmin;
    }

    public void setRfcadmin(String rfcadmin) {
        this.rfcadmin = rfcadmin;
    }

    public String getRfcmobileNo() {
        return rfcmobileNo;
    }

    public void setRfcmobileNo(String rfcmobileNo) {
        this.rfcmobileNo = rfcmobileNo;
    }

    public String getRfctpiname() {
        return rfctpiname;
    }

    public void setRfctpiname(String rfctpiname) {
        this.rfctpiname = rfctpiname;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getStreetGaliRoad() {
        return streetGaliRoad;
    }

    public void setStreetGaliRoad(String streetGaliRoad) {
        this.streetGaliRoad = streetGaliRoad;
    }

    public String getUniqueLpgId() {
        return uniqueLpgId;
    }

    public void setUniqueLpgId(String uniqueLpgId) {
        this.uniqueLpgId = uniqueLpgId;
    }

    public String getVendorMobileNo() {
        return vendorMobileNo;
    }

    public void setVendorMobileNo(String vendorMobileNo) {
        this.vendorMobileNo = vendorMobileNo;
    }

    public String getRfcVendorName() {
        return rfcVendorName;
    }

    public void setRfcVendorName(String rfcVendorName) {
        this.rfcVendorName = rfcVendorName;
    }

    public String getRfcVendorMobileNo() {
        return rfcVendorMobileNo;
    }

    public void setRfcVendorMobileNo(String rfcVendorMobileNo) {
        this.rfcVendorMobileNo = rfcVendorMobileNo;
    }

    public String getRfcAdminMobileNo() {
        return rfcAdminMobileNo;
    }

    public void setRfcAdminMobileNo(String rfcAdminMobileNo) {
        this.rfcAdminMobileNo = rfcAdminMobileNo;
    }

    public String getRfcAdminName() {
        return rfcAdminName;
    }

    public void setRfcAdminName(String rfcAdminName) {
        this.rfcAdminName = rfcAdminName;
    }

    public String getZoneCode() {
        return ZoneCode;
    }

    public void setZoneCode(String zoneCode) {
        ZoneCode = zoneCode;
    }
}
