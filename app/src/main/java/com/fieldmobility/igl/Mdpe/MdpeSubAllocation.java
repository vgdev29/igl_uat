package com.fieldmobility.igl.Mdpe;

import java.io.Serializable;

public class MdpeSubAllocation implements Serializable {

    public long id;
    public String allocationNumber;
    public String suballocationNumber;
    public String agentId;
    public String poNumber;
    public String city;
    public String zone;
    public String area;
    public String society;
    public String wbsNumber;
    public String method;
    public String areaType;
    public String size;
    public String trenchlessMethod;
    public String length;
    public  int tpiClaim;
    public String claimDate;
    public String tpiId;
    public String agentAssignDate;
    public String userName;
    public String userMob;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMob() {
        return userMob;
    }

    public void setUserMob(String userMob) {
        this.userMob = userMob;
    }

    public String getAgentAssignDate() {
        return agentAssignDate;
    }

    public void setAgentAssignDate(String agentAssignDate) {
        this.agentAssignDate = agentAssignDate;
    }

    public int getTpiClaim() {
        return tpiClaim;
    }

    public void setTpiClaim(int tpiClaim) {
        this.tpiClaim = tpiClaim;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public String getTpiId() {
        return tpiId;
    }

    public void setTpiId(String tpiId) {
        this.tpiId = tpiId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAllocationNumber() {
        return allocationNumber;
    }

    public void setAllocationNumber(String allocationNumber) {
        this.allocationNumber = allocationNumber;
    }

    public String getSuballocationNumber() {
        return suballocationNumber;
    }

    public void setSuballocationNumber(String suballocationNumber) {
        this.suballocationNumber = suballocationNumber;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
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

    public String getWbsNumber() {
        return wbsNumber;
    }

    public void setWbsNumber(String wbsNumber) {
        this.wbsNumber = wbsNumber;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTrenchlessMethod() {
        return trenchlessMethod;
    }

    public void setTrenchlessMethod(String trenchlessMethod) {
        this.trenchlessMethod = trenchlessMethod;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
