package com.fieldmobility.igl.Complain;

public class ComplainOrderModel {

    String orderType;
    String orderName;

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

    @Override
    public String toString() {
        return  orderName ;
    }
}
