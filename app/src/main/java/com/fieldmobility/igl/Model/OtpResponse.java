package com.fieldmobility.igl.Model;

public class OtpResponse {
    int status;
    String message;
    Otp data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Otp getData() {
        return data;
    }

    public void setData(Otp data) {
        this.data = data;
    }
}
