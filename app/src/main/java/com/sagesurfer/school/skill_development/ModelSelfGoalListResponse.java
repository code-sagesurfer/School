package com.sagesurfer.school.skill_development;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ModelSelfGoalListResponse {
    @SerializedName("all_goal")
    private ArrayList<Goal_> goalArrayList;

    public ArrayList<Goal_> getGoalArrayList() {
        return goalArrayList;
    }

    public void setGoalArrayList(ArrayList<Goal_> goalArrayList) {
        this.goalArrayList = goalArrayList;
    }
}
