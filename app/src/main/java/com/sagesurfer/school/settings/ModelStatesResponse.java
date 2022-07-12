package com.sagesurfer.school.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelStatesResponse {
    @SerializedName("get_state")
    @Expose
    private ArrayList<GetState> getState = null;

    @SerializedName("get_city")
    @Expose
    private ArrayList<GetState> get_city = null;

    public ArrayList<GetState> getGetState() {
        return getState;
    }

    public void setGetState(ArrayList<GetState> getState) {
        this.getState = getState;
    }

    public ArrayList<GetState> getGet_city() {
        return get_city;
    }

    public void setGet_city(ArrayList<GetState> get_city) {
        this.get_city = get_city;
    }
}
