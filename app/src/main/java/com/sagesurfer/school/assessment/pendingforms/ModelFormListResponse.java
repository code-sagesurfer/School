package com.sagesurfer.school.assessment.pendingforms;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelFormListResponse {
    @SerializedName("get_list_submitted")
    private ArrayList<Forms_> formsArrayList;

    @SerializedName("get_list")
    private ArrayList<Forms_> get_pending_list;

    public ArrayList<Forms_> getFormsArrayList() {
        return formsArrayList;
    }

    public void setFormsArrayList(ArrayList<Forms_> formsArrayList) {
        this.formsArrayList = formsArrayList;
    }

    public ArrayList<Forms_> getGet_pending_list() {
        return get_pending_list;
    }

    public void setGet_pending_list(ArrayList<Forms_> get_pending_list) {
        this.get_pending_list = get_pending_list;
    }
}
