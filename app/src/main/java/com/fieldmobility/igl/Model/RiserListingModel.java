package com.fieldmobility.igl.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RiserListingModel {
    @SerializedName("suballocation_number")
    private String suballocationNumber;
    @SerializedName("allocation")
    private Allocation allocation;
    @SerializedName("agent_name")
    private String agentName;
    @SerializedName("no_of_riser")
    private long noOfRiser;
    @SerializedName("no_of_connections")
    private long noOfConnections;
    @SerializedName("supervisor_id")
    private String supervisorID;
    @SerializedName("tpi_id")
    private String tpiID;
    @SerializedName("claim")
    private boolean claim;

    public String getSuballocationNumber() { return suballocationNumber; }
    public void setSuballocationNumber(String value) { this.suballocationNumber = value; }

    public Allocation getAllocation() { return allocation; }
    public void setAllocation(Allocation value) { this.allocation = value; }

    public String getAgentName() { return agentName; }
    public void setAgentName(String value) { this.agentName = value; }

    public long getNoOfRiser() { return noOfRiser; }
    public void setNoOfRiser(long value) { this.noOfRiser = value; }

    public long getNoOfConnections() { return noOfConnections; }
    public void setNoOfConnections(long value) { this.noOfConnections = value; }

    public String getSupervisorID() { return supervisorID; }
    public void setSupervisorID(String value) { this.supervisorID = value; }

    public String getTpiID() { return tpiID; }
    public void setTpiID(String value) { this.tpiID = value; }

    public boolean getClaim() { return claim; }
    public void setClaim(boolean value) { this.claim = value; }
}
