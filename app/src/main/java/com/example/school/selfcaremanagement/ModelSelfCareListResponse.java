package com.example.school.selfcaremanagement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Rahul Maske(monikam@sagesurfer.com)
 *         Created on 28-05-2022
 *         Last Modified on
 */
public class ModelSelfCareListResponse {
    @SerializedName("get_data")
    @Expose
    private ArrayList<Content_> get_data;

    public ArrayList<Content_> getGet_data() {
        return get_data;
    }

    public void setGet_data(ArrayList<Content_> get_data) {
        this.get_data = get_data;
    }
}
