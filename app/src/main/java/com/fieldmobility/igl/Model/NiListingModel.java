package com.fieldmobility.igl.Model;

import java.util.ArrayList;

public class NiListingModel {
    private String Message;
    private String Sucess;
    private ArrayList<NICList> nic_list;
    private String status;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String value) {
        this.Message = value;
    }

    public String getSucess() {
        return Sucess;
    }

    public void setSucess(String value) {
        this.Sucess = value;
    }

    public ArrayList<NICList> getNICList() {
        return nic_list;
    }

    public void setNICList(ArrayList<NICList> value) {
        this.nic_list = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }
}

