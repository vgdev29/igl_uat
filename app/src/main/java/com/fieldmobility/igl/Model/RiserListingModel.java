package com.fieldmobility.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RiserListingModel {
    public static class BpDetails {
        public static class User {

            @SerializedName("first_name")
            @Expose
            private String firstName;
            @SerializedName("middle_name")
            @Expose
            private String middleName;
            @SerializedName("last_name")
            @Expose
            private String lastName;
            @SerializedName("mobile_number")
            @Expose
            private String mobileNumber;
            @SerializedName("email_id")
            @Expose
            private String emailId;
            @SerializedName("aadhaar_number")
            @Expose
            private String aadhaarNumber;
            @SerializedName("city_region")
            @Expose
            private String cityRegion;
            @SerializedName("area")
            @Expose
            private String area;
            @SerializedName("society")
            @Expose
            private String society;
            @SerializedName("landmark")
            @Expose
            private String landmark;
            @SerializedName("house_type")
            @Expose
            private String houseType;
            @SerializedName("house_no")
            @Expose
            private String houseNo;
            @SerializedName("block_qtr_tower_wing")
            @Expose
            private String blockQtrTowerWing;
            @SerializedName("floor")
            @Expose
            private String floor;
            @SerializedName("street_gali_road")
            @Expose
            private String streetGaliRoad;
            @SerializedName("pincode")
            @Expose
            private String pincode;
            @SerializedName("customer_type")
            @Expose
            private String customerType;
            @SerializedName("lpg_company")
            @Expose
            private String lpgCompany;
            @SerializedName("bp_number")
            @Expose
            private String bpNumber;
            @SerializedName("bp_date")
            @Expose
            private String bpDate;
            @SerializedName("lpg_distributor")
            @Expose
            private Object lpgDistributor;
            @SerializedName("lpg_conNo")
            @Expose
            private Object lpgConNo;
            @SerializedName("unique_lpg_Id")
            @Expose
            private Object uniqueLpgId;
            @SerializedName("ownerName")
            @Expose
            private Object ownerName;
            @SerializedName("chequeNo")
            @Expose
            private Object chequeNo;
            @SerializedName("chequeDate")
            @Expose
            private Object chequeDate;
            @SerializedName("drawnOn")
            @Expose
            private Object drawnOn;
            @SerializedName("amount")
            @Expose
            private Object amount;
            @SerializedName("addressProof")
            @Expose
            private Object addressProof;
            @SerializedName("igl_status")
            @Expose
            private String iglStatus;
            @SerializedName("igl_code_group")
            @Expose
            private String iglCodeGroup;
            @SerializedName("lead_no")
            @Expose
            private String leadNo;
            @SerializedName("pipeline_description")
            @Expose
            private Object pipelineDescription;
            @SerializedName("pipelineId")
            @Expose
            private Object pipelineId;
            @SerializedName("claimFlag")
            @Expose
            private Object claimFlag;
            @SerializedName("rfcVendor")
            @Expose
            private String rfcVendor;
            @SerializedName("rfcTpi")
            @Expose
            private String rfcTpi;
            @SerializedName("jobFlag")
            @Expose
            private String jobFlag;
            @SerializedName("fesabilityTpimobileNo")
            @Expose
            private Object fesabilityTpimobileNo;
            @SerializedName("vendorMobileNo")
            @Expose
            private Object vendorMobileNo;
            @SerializedName("fesabilityDate")
            @Expose
            private Object fesabilityDate;
            @SerializedName("holdStatus")
            @Expose
            private Object holdStatus;
            @SerializedName("declinedDescription")
            @Expose
            private Object declinedDescription;
            @SerializedName("controlRoom")
            @Expose
            private String controlRoom;
            @SerializedName("document")
            @Expose
            private Object document;
            @SerializedName("document1")
            @Expose
            private Object document1;
            @SerializedName("adhaarCard")
            @Expose
            private Object adhaarCard;
            @SerializedName("addressCard")
            @Expose
            private Object addressCard;
            @SerializedName("rfcadmin")
            @Expose
            private String rfcadmin;
            @SerializedName("plantCode")
            @Expose
            private Object plantCode;
            @SerializedName("meterNo")
            @Expose
            private Object meterNo;
            @SerializedName("supervisor_assigndate")
            @Expose
            private String supervisorAssigndate;
            @SerializedName("tpi_assigndate")
            @Expose
            private Object tpiAssigndate;
            @SerializedName("contractor_assigndate")
            @Expose
            private String contractorAssigndate;
            @SerializedName("riserSup")
            @Expose
            private String riserSup;
            @SerializedName("riserStatus")
            @Expose
            private String riserStatus;
            @SerializedName("riserAssign")
            @Expose
            private String riserAssign;
            @SerializedName("fesabilityTpiName")
            @Expose
            private Object fesabilityTpiName;
            @SerializedName("ca_no")
            @Expose
            private String caNo;
            @SerializedName("idproof")
            @Expose
            private Object idproof;
            @SerializedName("rfctpiname")
            @Expose
            private Object rfctpiname;
            @SerializedName("rfcmobileNo")
            @Expose
            private Object rfcmobileNo;

            @SerializedName("zone")
            @Expose
            private String zone;


            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getMiddleName() {
                return middleName;
            }

            public void setMiddleName(String middleName) {
                this.middleName = middleName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getEmailId() {
                return emailId;
            }

            public void setEmailId(String emailId) {
                this.emailId = emailId;
            }

            public String getAadhaarNumber() {
                return aadhaarNumber;
            }

            public void setAadhaarNumber(String aadhaarNumber) {
                this.aadhaarNumber = aadhaarNumber;
            }

            public String getCityRegion() {
                return cityRegion;
            }

            public void setCityRegion(String cityRegion) {
                this.cityRegion = cityRegion;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getSociety() {
                return society;
            }

            public void setSociety(String society) {
                this.society = society;
            }

            public String getLandmark() {
                return landmark;
            }

            public void setLandmark(String landmark) {
                this.landmark = landmark;
            }

            public String getHouseType() {
                return houseType;
            }

            public void setHouseType(String houseType) {
                this.houseType = houseType;
            }

            public String getHouseNo() {
                return houseNo;
            }

            public void setHouseNo(String houseNo) {
                this.houseNo = houseNo;
            }

            public String getBlockQtrTowerWing() {
                return blockQtrTowerWing;
            }

            public void setBlockQtrTowerWing(String blockQtrTowerWing) {
                this.blockQtrTowerWing = blockQtrTowerWing;
            }

            public String getFloor() {
                return floor;
            }

            public void setFloor(String floor) {
                this.floor = floor;
            }

            public String getStreetGaliRoad() {
                return streetGaliRoad;
            }

            public void setStreetGaliRoad(String streetGaliRoad) {
                this.streetGaliRoad = streetGaliRoad;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getCustomerType() {
                return customerType;
            }

            public void setCustomerType(String customerType) {
                this.customerType = customerType;
            }

            public String getLpgCompany() {
                return lpgCompany;
            }

            public void setLpgCompany(String lpgCompany) {
                this.lpgCompany = lpgCompany;
            }

            public String getBpNumber() {
                return bpNumber;
            }

            public void setBpNumber(String bpNumber) {
                this.bpNumber = bpNumber;
            }

            public String getBpDate() {
                return bpDate;
            }

            public void setBpDate(String bpDate) {
                this.bpDate = bpDate;
            }

            public Object getLpgDistributor() {
                return lpgDistributor;
            }

            public void setLpgDistributor(Object lpgDistributor) {
                this.lpgDistributor = lpgDistributor;
            }

            public Object getLpgConNo() {
                return lpgConNo;
            }

            public void setLpgConNo(Object lpgConNo) {
                this.lpgConNo = lpgConNo;
            }

            public Object getUniqueLpgId() {
                return uniqueLpgId;
            }

            public void setUniqueLpgId(Object uniqueLpgId) {
                this.uniqueLpgId = uniqueLpgId;
            }

            public Object getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(Object ownerName) {
                this.ownerName = ownerName;
            }

            public Object getChequeNo() {
                return chequeNo;
            }

            public void setChequeNo(Object chequeNo) {
                this.chequeNo = chequeNo;
            }

            public Object getChequeDate() {
                return chequeDate;
            }

            public void setChequeDate(Object chequeDate) {
                this.chequeDate = chequeDate;
            }

            public Object getDrawnOn() {
                return drawnOn;
            }

            public void setDrawnOn(Object drawnOn) {
                this.drawnOn = drawnOn;
            }

            public Object getAmount() {
                return amount;
            }

            public void setAmount(Object amount) {
                this.amount = amount;
            }

            public Object getAddressProof() {
                return addressProof;
            }

            public void setAddressProof(Object addressProof) {
                this.addressProof = addressProof;
            }

            public String getIglStatus() {
                return iglStatus;
            }

            public void setIglStatus(String iglStatus) {
                this.iglStatus = iglStatus;
            }

            public String getIglCodeGroup() {
                return iglCodeGroup;
            }

            public void setIglCodeGroup(String iglCodeGroup) {
                this.iglCodeGroup = iglCodeGroup;
            }

            public String getLeadNo() {
                return leadNo;
            }

            public void setLeadNo(String leadNo) {
                this.leadNo = leadNo;
            }

            public Object getPipelineDescription() {
                return pipelineDescription;
            }

            public void setPipelineDescription(Object pipelineDescription) {
                this.pipelineDescription = pipelineDescription;
            }

            public Object getPipelineId() {
                return pipelineId;
            }

            public void setPipelineId(Object pipelineId) {
                this.pipelineId = pipelineId;
            }

            public Object getClaimFlag() {
                return claimFlag;
            }

            public void setClaimFlag(Object claimFlag) {
                this.claimFlag = claimFlag;
            }

            public String getRfcVendor() {
                return rfcVendor;
            }

            public void setRfcVendor(String rfcVendor) {
                this.rfcVendor = rfcVendor;
            }

            public String getRfcTpi() {
                return rfcTpi;
            }

            public void setRfcTpi(String rfcTpi) {
                this.rfcTpi = rfcTpi;
            }

            public String getJobFlag() {
                return jobFlag;
            }

            public void setJobFlag(String jobFlag) {
                this.jobFlag = jobFlag;
            }

            public Object getFesabilityTpimobileNo() {
                return fesabilityTpimobileNo;
            }

            public void setFesabilityTpimobileNo(Object fesabilityTpimobileNo) {
                this.fesabilityTpimobileNo = fesabilityTpimobileNo;
            }

            public Object getVendorMobileNo() {
                return vendorMobileNo;
            }

            public void setVendorMobileNo(Object vendorMobileNo) {
                this.vendorMobileNo = vendorMobileNo;
            }

            public Object getFesabilityDate() {
                return fesabilityDate;
            }

            public void setFesabilityDate(Object fesabilityDate) {
                this.fesabilityDate = fesabilityDate;
            }

            public Object getHoldStatus() {
                return holdStatus;
            }

            public void setHoldStatus(Object holdStatus) {
                this.holdStatus = holdStatus;
            }

            public Object getDeclinedDescription() {
                return declinedDescription;
            }

            public void setDeclinedDescription(Object declinedDescription) {
                this.declinedDescription = declinedDescription;
            }

            public String getControlRoom() {
                return controlRoom;
            }

            public void setControlRoom(String controlRoom) {
                this.controlRoom = controlRoom;
            }

            public Object getDocument() {
                return document;
            }

            public void setDocument(Object document) {
                this.document = document;
            }

            public Object getDocument1() {
                return document1;
            }

            public void setDocument1(Object document1) {
                this.document1 = document1;
            }

            public Object getAdhaarCard() {
                return adhaarCard;
            }

            public void setAdhaarCard(Object adhaarCard) {
                this.adhaarCard = adhaarCard;
            }

            public Object getAddressCard() {
                return addressCard;
            }

            public void setAddressCard(Object addressCard) {
                this.addressCard = addressCard;
            }

            public String getRfcadmin() {
                return rfcadmin;
            }

            public void setRfcadmin(String rfcadmin) {
                this.rfcadmin = rfcadmin;
            }

            public Object getPlantCode() {
                return plantCode;
            }

            public void setPlantCode(Object plantCode) {
                this.plantCode = plantCode;
            }

            public Object getMeterNo() {
                return meterNo;
            }

            public void setMeterNo(Object meterNo) {
                this.meterNo = meterNo;
            }

            public String getSupervisorAssigndate() {
                return supervisorAssigndate;
            }

            public void setSupervisorAssigndate(String supervisorAssigndate) {
                this.supervisorAssigndate = supervisorAssigndate;
            }

            public Object getTpiAssigndate() {
                return tpiAssigndate;
            }

            public void setTpiAssigndate(Object tpiAssigndate) {
                this.tpiAssigndate = tpiAssigndate;
            }

            public String getContractorAssigndate() {
                return contractorAssigndate;
            }

            public void setContractorAssigndate(String contractorAssigndate) {
                this.contractorAssigndate = contractorAssigndate;
            }

            public String getRiserSup() {
                return riserSup;
            }

            public void setRiserSup(String riserSup) {
                this.riserSup = riserSup;
            }

            public String getRiserStatus() {
                return riserStatus;
            }

            public void setRiserStatus(String riserStatus) {
                this.riserStatus = riserStatus;
            }

            public String getRiserAssign() {
                return riserAssign;
            }

            public void setRiserAssign(String riserAssign) {
                this.riserAssign = riserAssign;
            }

            public Object getFesabilityTpiName() {
                return fesabilityTpiName;
            }

            public void setFesabilityTpiName(Object fesabilityTpiName) {
                this.fesabilityTpiName = fesabilityTpiName;
            }

            public String getCaNo() {
                return caNo;
            }

            public void setCaNo(String caNo) {
                this.caNo = caNo;
            }

            public Object getIdproof() {
                return idproof;
            }

            public void setIdproof(Object idproof) {
                this.idproof = idproof;
            }

            public Object getRfctpiname() {
                return rfctpiname;
            }

            public void setRfctpiname(Object rfctpiname) {
                this.rfctpiname = rfctpiname;
            }

            public Object getRfcmobileNo() {
                return rfcmobileNo;
            }

            public void setRfcmobileNo(Object rfcmobileNo) {
                this.rfcmobileNo = rfcmobileNo;
            }

        }

        @SerializedName("users")
        @Expose
        private List<User> users = null;
        @SerializedName("users1")
        @Expose
        private Object users1;

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        public Object getUsers1() {
            return users1;
        }

        public void setUsers1(Object users1) {
            this.users1 = users1;
        }

    }

    @SerializedName("Bp_Details")
    @Expose
    private BpDetails bpDetails;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Sucess")
    @Expose
    private String sucess;
    @SerializedName("status")
    @Expose
    private String status;

    public BpDetails getBpDetails() {
        return bpDetails;
    }

    public void setBpDetails(BpDetails bpDetails) {
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



