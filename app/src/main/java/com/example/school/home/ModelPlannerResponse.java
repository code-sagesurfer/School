package com.example.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelPlannerResponse {
    @SerializedName("get_data")
    @Expose
    private ArrayList<PlannerData> getData = null;

    public ArrayList<PlannerData> getGetData() {
        return getData;
    }

    public void setGetData(ArrayList<PlannerData> getData) {
        this.getData = getData;
    }
}
