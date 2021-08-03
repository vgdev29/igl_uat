package com.fieldmobility.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allocation {

    @SerializedName("allocation_number")
    @Expose
    private String allocationNumber;
    @SerializedName("po_number")
    @Expose
    private String poNumber;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("society")
    @Expose
    private String society;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("wbs")
    @Expose
    private String wbs;
    @SerializedName("allocation_letter")
    @Expose
    private String allocationLetter;
    @SerializedName("tower")
    @Expose
    private String tower;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("connections")
    @Expose
    private Integer connections;
    @SerializedName("riser_type")
    @Expose
    private String riserType;
    @SerializedName("allocation_amount")
    @Expose
    private Double allocationAmount;
    @SerializedName("zi_id")
    @Expose
    private String ziId;
    @SerializedName("contractor_id")
    @Expose
    private String contractorId;
    @SerializedName("withheld_amount")
    @Expose
    private Double withheldAmount;
    @SerializedName("hse_multiplier")
    @Expose
    private Double hseMultiplier;

    public String getAllocationNumber() {
        return allocationNumber;
    }

    public void setAllocationNumber(String allocationNumber) {
        this.allocationNumber = allocationNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public String getAllocationLetter() {
        return allocationLetter;
    }

    public void setAllocationLetter(String allocationLetter) {
        this.allocationLetter = allocationLetter;
    }

    public String getTower() {
        return tower;
    }

    public void setTower(String tower) {
        this.tower = tower;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getConnections() {
        return connections;
    }

    public void setConnections(Integer connections) {
        this.connections = connections;
    }

    public String getRiserType() {
        return riserType;
    }

    public void setRiserType(String riserType) {
        this.riserType = riserType;
    }

    public Double getAllocationAmount() {
        return allocationAmount;
    }

    public void setAllocationAmount(Double allocationAmount) {
        this.allocationAmount = allocationAmount;
    }

    public String getZiId() {
        return ziId;
    }

    public void setZiId(String ziId) {
        this.ziId = ziId;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public Double getWithheldAmount() {
        return withheldAmount;
    }

    public void setWithheldAmount(Double withheldAmount) {
        this.withheldAmount = withheldAmount;
    }

    public Double getHseMultiplier() {
        return hseMultiplier;
    }

    public void setHseMultiplier(Double hseMultiplier) {
        this.hseMultiplier = hseMultiplier;
    }

}