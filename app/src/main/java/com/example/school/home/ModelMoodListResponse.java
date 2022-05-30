package com.example.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ModelMoodListResponse {
    @SerializedName("mood_status")
    @Expose
    private ArrayList<MoodJournal_> moodDataList;

    public ArrayList<MoodJournal_> getMoodDataList() {
        return moodDataList;
    }

    public void setMoodDataList(ArrayList<MoodJournal_> moodDataList) {
        this.moodDataList = moodDataList;
    }
}
