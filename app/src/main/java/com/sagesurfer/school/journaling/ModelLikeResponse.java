package com.sagesurfer.school.journaling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelLikeResponse {@SerializedName("status")
@Expose
private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ArrayList<ModelLikeResponseModel> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public  ArrayList<ModelLikeResponseModel> getData() {
        return data;
    }

    public void setData( ArrayList<ModelLikeResponseModel> data) {
        this.data = data;
    }

}
