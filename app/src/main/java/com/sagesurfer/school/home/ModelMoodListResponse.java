package com.sagesurfer.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ModelMoodListResponse {
    @SerializedName("mood_status")
    @Expose
    private ArrayList<MoodJournal_> moodDataList;


    @SerializedName("get_data")
    @Expose
    private ArrayList<ErrorObject> get_data;



    public ArrayList<MoodJournal_> getMoodDataList() {
        return moodDataList;
    }
    public void setMoodDataList(ArrayList<MoodJournal_> moodDataList) {
        this.moodDataList = moodDataList;
    }

    public ArrayList<ErrorObject> getGet_data() {
        return get_data;
    }

    public void setGet_data(ArrayList<ErrorObject> get_data) {
        this.get_data = get_data;
    }
}
