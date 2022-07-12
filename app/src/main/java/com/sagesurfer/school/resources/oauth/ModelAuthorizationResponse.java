package com.sagesurfer.school.resources.oauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */
public class ModelAuthorizationResponse {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
