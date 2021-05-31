package com.fieldmobility.igl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NgSupervisorResponse {
    @SerializedName("SupervisorDetails")
    @Expose
    private SupervisorDetails supervisorDetails;
    @SerializedName("ContractorDetails")
    @Expose
    private ContractorDetails contractorDetails;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Sucess")
    @Expose
    private String sucess;
    @SerializedName("Message")
    @Expose
    private String message;

    public SupervisorDetails getSupervisorDetails() {
        return supervisorDetails;
    }

    public void setSupervisorDetails(SupervisorDetails supervisorDetails) {
        this.supervisorDetails = supervisorDetails;
    }

    public ContractorDetails getContractorDetails() {
        return contractorDetails;
    }

    public void setContractorDetails(ContractorDetails contractorDetails) {
        this.contractorDetails = contractorDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSucess() {
        return sucess;
    }

    public void setSucess(String sucess) {
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
