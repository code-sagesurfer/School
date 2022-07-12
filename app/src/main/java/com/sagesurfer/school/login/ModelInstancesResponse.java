package com.sagesurfer.school.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelInstancesResponse {
    @SerializedName("instances")
    @Expose
    private ArrayList<ModelInstancesData> instances = null;

    public ArrayList<ModelInstancesData> getInstances() {
        return instances;
    }

    public void setInstances(ArrayList<ModelInstancesData> instances) {
        this.instances = instances;
    }
}
