package com.fieldmobility.igl.Complain;

public class ComplainMaterialModel {

    long mrId;
    String orderType;
    String orderName;
    int materialNo;
    String materialDescription;
    String unit;
    int dismantle_sec;
    int consumed_sec;
    Double rate;


    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public long getMrId() {
        return mrId;
    }

    public void setMrId(long mrId) {
        this.mrId = mrId;
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

    public int getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(int materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getDismantle_sec() {
        return dismantle_sec;
    }

    public void setDismantle_sec(int dismantle_sec) {
        this.dismantle_sec = dismantle_sec;
    }

    public int getConsumed_sec() {
        return consumed_sec;
    }

    public void setConsumed_sec(int consumed_sec) {
        this.consumed_sec = consumed_sec;
    }

    @Override
    public String toString() {
        return   materialDescription;
    }
}
