package com.example.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelPlannerResponse {
    @SerializedName("get_data")
    @Expose
    private ArrayList<ModelPlannerData> getData = null;

    public ArrayList<ModelPlannerData> getGetData() {
        return getData;
    }

    public void setGetData(ArrayList<ModelPlannerData> getData) {
        this.getData = getData;
    }
}
