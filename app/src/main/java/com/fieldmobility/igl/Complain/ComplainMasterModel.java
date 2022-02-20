package com.fieldmobility.igl.Complain;

import java.io.Serializable;

public class ComplainMasterModel implements Serializable {

    long cmId;
    String ticketNo;
    String bpNum;
    String compType;
    //status
    String catId;
    String catalog;
    String codeGroup;
    String statusDescription;
    String code;
    // order
    String orderType;
    String orderName;
    //sign
    String filePath;
    String supSign;
    String tpiSign;
    String tpiDate;
    String customerSign;

    //feedback
    String satisfactionNo;
    String unsatisfactionNo;
    String leakTest;
    String smellTest;

    String creationDate;
    String modificationDate;
    String supRemarks;
    String followupDate;

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public String getSupRemarks() {
        return supRemarks;
    }

    public void setSupRemarks(String supRemarks) {
        this.supRemarks = supRemarks;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCmId() {
        return cmId;
    }

    public void setCmId(long cmId) {
        this.cmId = cmId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getBpNum() {
        return bpNum;
    }

    public void setBpNum(String bpNum) {
        this.bpNum = bpNum;
    }

    public String getCompType() {
        return compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSupSign() {
        return supSign;
    }

    public void setSupSign(String supSign) {
        this.supSign = supSign;
    }

    public String getTpiSign() {
        return tpiSign;
    }

    public void setTpiSign(String tpiSign) {
        this.tpiSign = tpiSign;
    }

    public String getTpiDate() {
        return tpiDate;
    }

    public void setTpiDate(String tpiDate) {
        this.tpiDate = tpiDate;
    }

    public String getCustomerSign() {
        return customerSign;
    }

    public void setCustomerSign(String customerSign) {
        this.customerSign = customerSign;
    }

    public String getSatisfactionNo() {
        return satisfactionNo;
    }

    public void setSatisfactionNo(String satisfactionNo) {
        this.satisfactionNo = satisfactionNo;
    }

    public String getUnsatisfactionNo() {
        return unsatisfactionNo;
    }

    public void setUnsatisfactionNo(String unsatisfactionNo) {
        this.unsatisfactionNo = unsatisfactionNo;
    }

    public String getLeakTest() {
        return leakTest;
    }

    public void setLeakTest(String leakTest) {
        this.leakTest = leakTest;
    }

    public String getSmellTest() {
        return smellTest;
    }

    public void setSmellTest(String smellTest) {
        this.smellTest = smellTest;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
}
