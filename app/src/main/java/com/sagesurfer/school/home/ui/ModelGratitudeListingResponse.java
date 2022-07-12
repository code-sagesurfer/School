package com.sagesurfer.school.home.ui;

import com.sagesurfer.school.home.ModelGratitudeListingResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelGratitudeListingResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ArrayList<ModelGratitudeListingResponseData> data = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<ModelGratitudeListingResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<ModelGratitudeListingResponseData> data) {
        this.data = data;
    }
}
