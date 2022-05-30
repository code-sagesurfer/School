package com.example.school.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelInstancesData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("instance_key")
    @Expose
    private String instanceKey;
    @SerializedName("instance_url")
    @Expose
    private String instanceUrl;
    @SerializedName("cometchat")
    @Expose
    private String cometchat;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceKey() {
        return instanceKey;
    }

    public void setInstanceKey(String instanceKey) {
        this.instanceKey = instanceKey;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public String getCometchat() {
        return cometchat;
    }

    public void setCometchat(String cometchat) {
        this.cometchat = cometchat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
