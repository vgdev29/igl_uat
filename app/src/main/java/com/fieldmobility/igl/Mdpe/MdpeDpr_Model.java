package com.fieldmobility.igl.Mdpe;


import java.io.Serializable;

public class MdpeDpr_Model implements Serializable {

	long id;
	String allocationNo;
	String subAllocation;
	String dprNo;
	String sectionId;
	Double input;
	String latitude;
	String longitude;
	String filesPath;
	String tpiId;
	String creationDate;
	int dprStatus;
	String tpiRemarks;
	String tpiSignature;
	String tpiActionOn;

	public MdpeDpr_Model(long id, String allocationNo, String subAllocation, String dprNo, String sectionId, Double input, String latitude, String longitude, String filesPath, String tpiId, String creationDate, int dprStatus, String tpiRemarks, String tpiSignature, String tpiActionOn) {
		this.id = id;
		this.allocationNo = allocationNo;
		this.subAllocation = subAllocation;
		this.dprNo = dprNo;
		this.sectionId = sectionId;
		this.input = input;
		this.latitude = latitude;
		this.longitude = longitude;
		this.filesPath = filesPath;
		this.tpiId = tpiId;
		this.creationDate = creationDate;
		this.dprStatus = dprStatus;
		this.tpiRemarks = tpiRemarks;
		this.tpiSignature = tpiSignature;
		this.tpiActionOn = tpiActionOn;
	}

	public MdpeDpr_Model() {
	}

	public int getDprStatus() {
		return dprStatus;
	}

	public void setDprStatus(int dprStatus) {
		this.dprStatus = dprStatus;
	}

	public String getTpiRemarks() {
		return tpiRemarks;
	}

	public void setTpiRemarks(String tpiRemarks) {
		this.tpiRemarks = tpiRemarks;
	}

	public String getTpiSignature() {
		return tpiSignature;
	}

	public void setTpiSignature(String tpiSignature) {
		this.tpiSignature = tpiSignature;
	}

	public String getTpiActionOn() {
		return tpiActionOn;
	}

	public void setTpiActionOn(String tpiActionOn) {
		this.tpiActionOn = tpiActionOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAllocationNo() {
		return allocationNo;
	}

	public void setAllocationNo(String allocationNo) {
		this.allocationNo = allocationNo;
	}

	public String getSubAllocation() {
		return subAllocation;
	}

	public void setSubAllocation(String subAllocation) {
		this.subAllocation = subAllocation;
	}

	public String getDprNo() {
		return dprNo;
	}

	public void setDprNo(String dprNo) {
		this.dprNo = dprNo;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public Double getInput() {
		return input;
	}

	public void setInput(Double input) {
		this.input = input;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getFilesPath() {
		return filesPath;
	}

	public void setFilesPath(String filesPath) {
		this.filesPath = filesPath;
	}

	public String getTpiId() {
		return tpiId;
	}

	public void setTpiId(String tpiId) {
		this.tpiId = tpiId;
	}
}
