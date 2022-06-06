package com.fieldmobility.igl.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NguserListModel implements Serializable {
    @SerializedName("jmr_no")
    @Expose
    private String jmr_no;
    @SerializedName("bp_no")
    @Expose
    private String bp_no;
    @SerializedName("meter_no")
    @Expose
    private String meter_no;
    @SerializedName("customer_name")
    @Expose
    private String customer_name;
    @SerializedName("burner_details")
    @Expose
    private String burner_details;
    @SerializedName("ng_update_date")
    @Expose
    private String ng_update_date;
    @SerializedName("conversion_date")
    @Expose
    private String conversion_date;
    @SerializedName("ca_no")
    @Expose
    private String ca_no;
    @SerializedName("meter_type")
    @Expose
    private String meter_type;
    @SerializedName("meter_make")
    @Expose
    private String meter_make;
    @SerializedName("initial_reading")
    @Expose
    private String initial_reading;
    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;
    @SerializedName("rfc_date")
    @Expose
    private String rfc_date;
    @SerializedName("amount_charged")
    @Expose
    private String amount_charged;
    @SerializedName("house_no")
    @Expose
    private String house_no;
    @SerializedName("block_qtr")
    @Expose
    private String block_qtr;
    @SerializedName("society")
    @Expose
    private String society;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("floor")
    @Expose
    private String floor;
    @SerializedName("street")
    @Expose
    private String street;
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
    private String customer_sign;
    @SerializedName("executive_sign")
    @Expose
    private String executive_sign;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sub_status")
    @Expose
    private String sub_status;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("delay_date")
    @Expose
    private String delay_date;
    @SerializedName("recording")
    @Expose
    private String recording;
    @SerializedName("hold_images")
    @Expose
    private String hold_images;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("claim")
    @Expose
    private Boolean claim;
    @SerializedName("email_id")
    @Expose
    private String email_id;
    @SerializedName("alt_number")
    @Expose
    private String alt_number;
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
    @SerializedName("code_group")
    @Expose
    private String code_group;
    @SerializedName("old")
    @Expose
    private Boolean old;
    @SerializedName("pushed_to_crm")
    @Expose
    private Boolean pushed_to_crm;
    @SerializedName("claim_date")
    @Expose
    private String claim_date;

    @SerializedName("cat_id")
    @Expose
    private String cat_id;

    @SerializedName("catalog")
    @Expose
    private String catalog;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("control_room")
    @Expose
    private String control_room;

    @SerializedName("rfc_initial_reading")
    @Expose
    private String rfc_initial_reading;

    @SerializedName("supervisor_assigned_date")
    @Expose
    private String supervisor_assigned_date;

    @SerializedName("contractor_assigned_date")
    @Expose
    private String contractor_assigned_date;


    @SerializedName("lattitude")
    @Expose
    private String lattitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("corrected_meter_no")
    @Expose
    private String corrected_meter_no;

    @SerializedName("meter_status")
    @Expose
    private boolean meter_status;


    public String getCorrected_meter_no() {
        return corrected_meter_no;
    }

    public void setCorrected_meter_no(String corrected_meter_no) {
        this.corrected_meter_no = corrected_meter_no;
    }

    public boolean isMeter_status() {
        return meter_status;
    }

    public void setMeter_status(boolean meter_status) {
        this.meter_status = meter_status;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSupervisor_assigned_date() {
        return supervisor_assigned_date;
    }

    public void setSupervisor_assigned_date(String supervisor_assigned_date) {
        this.supervisor_assigned_date = supervisor_assigned_date;
    }

    public String getContractor_assigned_date() {
        return contractor_assigned_date;
    }

    public void setContractor_assigned_date(String contractor_assigned_date) {
        this.contractor_assigned_date = contractor_assigned_date;
    }

    public String getRfc_initial_reading() {
        return rfc_initial_reading;
    }

    public void setRfc_initial_reading(String rfc_initial_reading) {
        this.rfc_initial_reading = rfc_initial_reading;
    }

    public String getControl_room() {
        return control_room;
    }

    public void setControl_room(String control_room) {
        this.control_room = control_room;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getClaim_date() {
        return claim_date;
    }

    public void setClaim_date(String claim_date) {
        this.claim_date = claim_date;
    }

    public String getJmr_no() {
        return jmr_no;
    }

    public void setJmr_no(String jmr_no) {
        this.jmr_no = jmr_no;
    }

    public String getBp_no() {
        return bp_no;
    }

    public void setBp_no(String bp_no) {
        this.bp_no = bp_no;
    }

    public String getMeter_no() {
        return meter_no;
    }

    public void setMeter_no(String meter_no) {
        this.meter_no = meter_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getBurner_details() {
        return burner_details;
    }

    public void setBurner_details(String burner_details) {
        this.burner_details = burner_details;
    }

    public String getNg_update_date() {
        return ng_update_date;
    }

    public void setNg_update_date(String ng_update_date) {
        this.ng_update_date = ng_update_date;
    }

    public String getConversion_date() {
        return conversion_date;
    }

    public void setConversion_date(String conversion_date) {
        this.conversion_date = conversion_date;
    }

    public String getCa_no() {
        return ca_no;
    }

    public void setCa_no(String ca_no) {
        this.ca_no = ca_no;
    }

    public String getMeter_type() {
        return meter_type;
    }

    public void setMeter_type(String meter_type) {
        this.meter_type = meter_type;
    }

    public String getMeter_make() {
        return meter_make;
    }

    public void setMeter_make(String meter_make) {
        this.meter_make = meter_make;
    }

    public String getInitial_reading() {
        return initial_reading;
    }

    public void setInitial_reading(String initial_reading) {
        this.initial_reading = initial_reading;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getRfc_date() {
        return rfc_date;
    }

    public void setRfc_date(String rfc_date) {
        this.rfc_date = rfc_date;
    }

    public String getAmount_charged() {
        return amount_charged;
    }

    public void setAmount_charged(String amount_charged) {
        this.amount_charged = amount_charged;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getBlock_qtr() {
        return block_qtr;
    }

    public void setBlock_qtr(String block_qtr) {
        this.block_qtr = block_qtr;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
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

    public String getCustomer_sign() {
        return customer_sign;
    }

    public void setCustomer_sign(String customer_sign) {
        this.customer_sign = customer_sign;
    }

    public String getExecutive_sign() {
        return executive_sign;
    }

    public void setExecutive_sign(String executive_sign) {
        this.executive_sign = executive_sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSub_status() {
        return sub_status;
    }

    public void setSub_status(String sub_status) {
        this.sub_status = sub_status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelay_date() {
        return delay_date;
    }

    public void setDelay_date(String delay_date) {
        this.delay_date = delay_date;
    }

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public String getHold_images() {
        return hold_images;
    }

    public void setHold_images(String hold_images) {
        this.hold_images = hold_images;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Boolean getClaim() {
        return claim;
    }

    public void setClaim(Boolean claim) {
        this.claim = claim;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getAlt_number() {
        return alt_number;
    }

    public void setAlt_number(String alt_number) {
        this.alt_number = alt_number;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getContractor_id() {
        return contractor_id;
    }

    public void setContractor_id(String contractor_id) {
        this.contractor_id = contractor_id;
    }

    public String getTpi_id() {
        return tpi_id;
    }

    public void setTpi_id(String tpi_id) {
        this.tpi_id = tpi_id;
    }

    public String getZi_id() {
        return zi_id;
    }

    public void setZi_id(String zi_id) {
        this.zi_id = zi_id;
    }

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public String getCrm_status() {
        return crm_status;
    }

    public void setCrm_status(String crm_status) {
        this.crm_status = crm_status;
    }

    public String getCrm_reason() {
        return crm_reason;
    }

    public void setCrm_reason(String crm_reason) {
        this.crm_reason = crm_reason;
    }

    public String getLead_no() {
        return lead_no;
    }

    public void setLead_no(String lead_no) {
        this.lead_no = lead_no;
    }

    public Boolean getStart_job() {
        return start_job;
    }

    public void setStart_job(Boolean start_job) {
        this.start_job = start_job;
    }

    public String getCode_group() {
        return code_group;
    }

    public void setCode_group(String code_group) {
        this.code_group = code_group;
    }

    public Boolean getOld() {
        return old;
    }

    public void setOld(Boolean old) {
        this.old = old;
    }

    public Boolean getPushed_to_crm() {
        return pushed_to_crm;
    }

    public void setPushed_to_crm(Boolean pushed_to_crm) {
        this.pushed_to_crm = pushed_to_crm;
    }
}