package com.example.school.skill_development;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelGetGoalResponse {
    @SerializedName("my_goal")
    @Expose
    private ArrayList<ModelMyGoal> myGoal = null;
    @SerializedName("counter")
    @Expose
    private ArrayList<ModelCounter> counter = null;

    public ArrayList<ModelMyGoal> getMyGoal() {
        return myGoal;
    }

    public void setMyGoal(ArrayList<ModelMyGoal> myGoal) {
        this.myGoal = myGoal;
    }

    public ArrayList<ModelCounter> getCounter() {
        return counter;
    }

    public void setCounter(ArrayList<ModelCounter> counter) {
        this.counter = counter;
    }
}
