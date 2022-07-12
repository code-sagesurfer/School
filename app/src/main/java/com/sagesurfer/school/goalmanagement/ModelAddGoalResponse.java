package com.sagesurfer.school.goalmanagement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelAddGoalResponse {
    @SerializedName("goal_action")
    @Expose
    private ArrayList<GoalAction> goalAction = null;

    public ArrayList<GoalAction> getGoalAction() {
        return goalAction;
    }

    public void setGoalAction(ArrayList<GoalAction> goalAction) {
        this.goalAction = goalAction;
    }
}
