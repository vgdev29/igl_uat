package com.fieldmobility.igl.MataData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown=false)
public class Bp_No_Item implements Serializable {

    String first_name;
    String middle_name;
    String last_name;
    String mobile_number;
    String email_id;
    String aadhaar_number;

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    String father_name;
    String city_region;
    String area;
    String society;
    String landmark;
    String house_type;
    String house_no;
    String block_qtr_tower_wing;
    String floor;
    String street_gali_road;
    String pincode;
    String customer_type;
    String lpg_company;
    String bp_number;
    String bp_date;
    String igl_status;

    String lpg_distributor;
    String lpg_conNo ;
    String unique_lpg_Id;
    String ownerName;
    String chequeNo;
    String chequeDate;
    String lead_no;
    String drawnOn;
    String amount;
    String addressProof;
    String idproof;
    String igl_code_group;
    String fesabilityTpiName;
    String pipeline_length;
    String pipeline_length_id;
    String claimFlag;
    String jobFlag;
    String rfcTpi;
    String url_path;
    String type_url;
    String rfcVendor;
    String vendorMobileNo;
    String fesabilityTpimobileNo;
    String Rfcvendorname;
    String fesabilityDate;
    String meterNo;
    String holdStatus;
    String text;
    String declinedDescription;
    String rfcAdmin;
    String controlRoom;

    String id_image;
    String address_image;
    String customer_image;
    String owner_image;
    String igl_rfcvendor_assigndate;

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    ArrayList<String> imageList;

    public String getIgl_rfcvendor_assigndate() {
        return igl_rfcvendor_assigndate;
    }

    public void setIgl_rfcvendor_assigndate(String igl_rfcvendor_assigndate) {
        this.igl_rfcvendor_assigndate = igl_rfcvendor_assigndate;
    }

    public String getId_image() {
        return id_image;
    }

    public void setId_image(String id_image) {
        this.id_image = id_image;
    }

    public String getAddress_image() {
        return address_image;
    }

    public void setAddress_image(String address_image) {
        this.address_image = address_image;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getOwner_image() {
        return owner_image;
    }

    public void setOwner_image(String owner_image) {
        this.owner_image = owner_image;
    }

    public String getControlRoom() {
        return controlRoom;
    }

    public void setControlRoom(String controlRoom) {
        this.controlRoom = controlRoom;
    }

    public String getRfcAdmin() {
        return rfcAdmin;
    }

    public void setRfcAdmin(String rfcAdmin) {
        this.rfcAdmin = rfcAdmin;
    }

    public String getDeclinedDescription() {
        return declinedDescription;
    }

    public void setDeclinedDescription(String declinedDescription) {
        this.declinedDescription = declinedDescription;
    }

    public String getRFCMobileNo() {
        return RFCMobileNo;
    }

    public void setRFCMobileNo(String RFCMobileNo) {
        this.RFCMobileNo = RFCMobileNo;
    }

    String RFCMobileNo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHoldStatus() {
        return holdStatus;
    }

    public void setHoldStatus(String holdStatus) {
        this.holdStatus = holdStatus;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getFesabilityDate() {
        return fesabilityDate;
    }

    public void setFesabilityDate(String fesabilityDate) {
        this.fesabilityDate = fesabilityDate;
    }

    public String getRfcvendorname() {
        return Rfcvendorname;
    }

    public void setRfcvendorname(String rfcvendorname) {
        Rfcvendorname = rfcvendorname;
    }

    public String getVendorMobileNo() {
        return vendorMobileNo;
    }

    public void setVendorMobileNo(String vendorMobileNo) {
        this.vendorMobileNo = vendorMobileNo;
    }

    public String getFesabilityTpimobileNo() {
        return fesabilityTpimobileNo;
    }

    public void setFesabilityTpimobileNo(String fesabilityTpimobileNo) {
        this.fesabilityTpimobileNo = fesabilityTpimobileNo;
    }

    public String getRfcVendor() {
        return rfcVendor;
    }

    public void setRfcVendor(String rfcVendor) {
        this.rfcVendor = rfcVendor;
    }

    public String getUrl_path() {
        return url_path;
    }

    public void setUrl_path(String url_path) {
        this.url_path = url_path;
    }

    public String getType_url() {
        return type_url;
    }

    public void setType_url(String type_url) {
        this.type_url = type_url;
    }


    public String getRfcTpi() {
        return rfcTpi;
    }

    public void setRfcTpi(String rfcTpi) {
        this.rfcTpi = rfcTpi;
    }

    public String getClaimFlag() {
        return claimFlag;
    }

    public void setClaimFlag(String claimFlag) {
        this.claimFlag = claimFlag;
    }

    public String getJobFlag() {
        return jobFlag;
    }

    public void setJobFlag(String jobFlag) {
        this.jobFlag = jobFlag;
    }

    public String getPipeline_length_id() {
        return pipeline_length_id;
    }

    public void setPipeline_length_id(String pipeline_length_id) {
        this.pipeline_length_id = pipeline_length_id;
    }



    public String getFesabilityTpiName() {
        return fesabilityTpiName;
    }

    public void setFesabilityTpiName(String fesabilityTpiName) {
        this.fesabilityTpiName = fesabilityTpiName;
    }

    public String getPipeline_length() {
        return pipeline_length;
    }

    public void setPipeline_length(String pipeline_length) {
        this.pipeline_length = pipeline_length;
    }




    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getAadhaar_number() {
        return aadhaar_number;
    }

    public void setAadhaar_number(String aadhaar_number) {
        this.aadhaar_number = aadhaar_number;
    }

    public String getCity_region() {
        return city_region;
    }

    public void setCity_region(String city_region) {
        this.city_region = city_region;
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

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getBlock_qtr_tower_wing() {
        return block_qtr_tower_wing;
    }

    public void setBlock_qtr_tower_wing(String block_qtr_tower_wing) {
        this.block_qtr_tower_wing = block_qtr_tower_wing;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getStreet_gali_road() {
        return street_gali_road;
    }

    public void setStreet_gali_road(String street_gali_road) {
        this.street_gali_road = street_gali_road;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getLpg_company() {
        return lpg_company;
    }

    public void setLpg_company(String lpg_company) {
        this.lpg_company = lpg_company;
    }

    public String getBp_number() {
        return bp_number;
    }

    public void setBp_number(String bp_number) {
        this.bp_number = bp_number;
    }

    public String getBp_date() {
        return bp_date;
    }

    public void setBp_date(String bp_date) {
        this.bp_date = bp_date;
    }

    public String getIgl_status() {
        return igl_status;
    }

    public void setIgl_status(String igl_status) {
        this.igl_status = igl_status;
    }



    public String getLpg_distributor() {
        return lpg_distributor;
    }

    public void setLpg_distributor(String lpg_distributor) {
        this.lpg_distributor = lpg_distributor;
    }

    public String getLpg_conNo() {
        return lpg_conNo;
    }

    public void setLpg_conNo(String lpg_conNo) {
        this.lpg_conNo = lpg_conNo;
    }

    public String getUnique_lpg_Id() {
        return unique_lpg_Id;
    }

    public void setUnique_lpg_Id(String unique_lpg_Id) {
        this.unique_lpg_Id = unique_lpg_Id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getLead_no() {
        return lead_no;
    }

    public void setLead_no(String lead_no) {
        this.lead_no = lead_no;
    }

    public String getDrawnOn() {
        return drawnOn;
    }

    public void setDrawnOn(String drawnOn) {
        this.drawnOn = drawnOn;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddressProof() {
        return addressProof;
    }

    public void setAddressProof(String addressProof) {
        this.addressProof = addressProof;
    }

    public String getIdproof() {
        return idproof;
    }

    public void setIdproof(String idproof) {
        this.idproof = idproof;
    }

    public String getIgl_code_group() {
        return igl_code_group;
    }

    public void setIgl_code_group(String igl_code_group) {
        this.igl_code_group = igl_code_group;
    }

    @Override
    public String toString() {
        return "Bp_No_Item{" +
                "first_name='" + first_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", email_id='" + email_id + '\'' +
                ", aadhaar_number='" + aadhaar_number + '\'' +
                ", city_region='" + city_region + '\'' +
                ", area='" + area + '\'' +
                ", society='" + society + '\'' +
                ", landmark='" + landmark + '\'' +
                ", house_type='" + house_type + '\'' +
                ", house_no='" + house_no + '\'' +
                ", block_qtr_tower_wing='" + block_qtr_tower_wing + '\'' +
                ", floor='" + floor + '\'' +
                ", street_gali_road='" + street_gali_road + '\'' +
                ", pincode='" + pincode + '\'' +
                ", customer_type='" + customer_type + '\'' +
                ", lpg_company='" + lpg_company + '\'' +
                ", bp_number='" + bp_number + '\'' +
                ", bp_date='" + bp_date + '\'' +
                ", igl_status='" + igl_status + '\'' +
                ", lpg_distributor='" + lpg_distributor + '\'' +
                ", lpg_conNo='" + lpg_conNo + '\'' +
                ", unique_lpg_Id='" + unique_lpg_Id + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", chequeNo='" + chequeNo + '\'' +
                ", chequeDate='" + chequeDate + '\'' +
                ", lead_no='" + lead_no + '\'' +
                ", drawnOn='" + drawnOn + '\'' +
                ", amount='" + amount + '\'' +
                ", addressProof='" + addressProof + '\'' +
                ", idproof='" + idproof + '\'' +
                ", igl_code_group='" + igl_code_group + '\'' +
                ", fesabilityTpiName='" + fesabilityTpiName + '\'' +
                ", pipeline_length='" + pipeline_length + '\'' +
                ", pipeline_length_id='" + pipeline_length_id + '\'' +
                ", claimFlag='" + claimFlag + '\'' +
                ", jobFlag='" + jobFlag + '\'' +
                ", rfcTpi='" + rfcTpi + '\'' +
                ", url_path='" + url_path + '\'' +
                ", type_url='" + type_url + '\'' +
                ", rfcVendor='" + rfcVendor + '\'' +
                ", vendorMobileNo='" + vendorMobileNo + '\'' +
                ", fesabilityTpimobileNo='" + fesabilityTpimobileNo + '\'' +
                ", Rfcvendorname='" + Rfcvendorname + '\'' +
                ", fesabilityDate='" + fesabilityDate + '\'' +
                ", meterNo='" + meterNo + '\'' +
                ", holdStatus='" + holdStatus + '\'' +
                ", text='" + text + '\'' +
                ", declinedDescription='" + declinedDescription + '\'' +
                ", RFCMobileNo='" + RFCMobileNo + '\'' +
                '}';
    }
}
