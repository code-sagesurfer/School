package com.sagesurfer.school.team_care;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelCometchatTeamListResponse {
    @SerializedName("cometchat")
    @Expose
    private ArrayList<CometChatTeamMembers_> cometchat = null;

    @SerializedName("error")
    @Expose
    String error;

    @SerializedName("error_description")
    @Expose
    String error_description;
    public ArrayList<CometChatTeamMembers_> getCometchat() {
        return cometchat;
    }

    public void setCometchat(ArrayList<CometChatTeamMembers_> cometchat) {
        this.cometchat = cometchat;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
