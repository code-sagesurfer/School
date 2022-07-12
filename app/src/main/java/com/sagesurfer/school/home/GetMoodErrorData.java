package com.sagesurfer.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMoodErrorData {
/*    @SerializedName("mood_status")
    @Expose
    private ErrorObject moodDataList;

    public ErrorObject getMoodDataList() {
        return moodDataList;
    }

    public void setMoodDataList(ErrorObject moodDataList) {
        this.moodDataList = moodDataList;
    }*/

    @SerializedName("error")
    @Expose
    private String Error;

    @SerializedName("status")
    @Expose
    private int status;

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
