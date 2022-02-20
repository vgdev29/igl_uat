package com.fieldmobility.igl.Complain;

import java.util.ArrayList;

public class MasterRequest {
    ArrayList<CompMatMaster> ms;
    ArrayList<CompServMaster> ss;


    public ArrayList<CompServMaster> getSs() {
        return ss;
    }

    public void setSs(ArrayList<CompServMaster> ss) {
        this.ss = ss;
    }

    public ArrayList<CompMatMaster> getMs() {
        return ms;
    }

    public void setMs(ArrayList<CompMatMaster> ms) {
        this.ms = ms;
    }
}
