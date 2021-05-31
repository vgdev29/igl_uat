package com.fieldmobility.igl.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TpiDetailResponse implements Serializable {

        @SerializedName("TpiDetails")
        @Expose
        private TpiDetails tpiDetails;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("Sucess")
        @Expose
        private String sucess;
        @SerializedName("Message")
        @Expose
        private String message;

        public TpiDetails getTpiDetails() {
            return tpiDetails;
        }

        public void setTpiDetails(TpiDetails tpiDetails) {
            this.tpiDetails = tpiDetails;
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


