package com.sagesurfer.school.skill_development;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelInputProvidingResponse {
    @SerializedName("add_count")
    @Expose
    private ArrayList<ModelCount> addCount = null;

    public ArrayList<ModelCount> getAddCount() {
        return addCount;
    }

    public void setAddCount(ArrayList<ModelCount> addCount) {
        this.addCount = addCount;
    }
}
