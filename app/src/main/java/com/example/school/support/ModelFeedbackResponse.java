package com.example.school.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelFeedbackResponse {
    @SerializedName("feedback")
    @Expose
    private ArrayList<Feedback> feedback = null;

    public ArrayList<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(ArrayList<Feedback> feedback) {
        this.feedback = feedback;
    }
}
