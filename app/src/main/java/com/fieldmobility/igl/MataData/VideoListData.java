package com.fieldmobility.igl.MataData;

public class VideoListData {

    private String description;
    private String imgId;
    public VideoListData(String description, String imgId) {
        this.description = description;
        this.imgId = imgId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImgId() {
        return imgId;
    }
    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
}
