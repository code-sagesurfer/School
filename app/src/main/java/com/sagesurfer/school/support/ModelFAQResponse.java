package com.sagesurfer.school.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelFAQResponse {
    @SerializedName("get_faq")
    @Expose
    private ArrayList<ModelGetFaq> getFaq = null;

    public ArrayList<ModelGetFaq> getGetFaq() {
        return getFaq;
    }

    public void setGetFaq(ArrayList<ModelGetFaq> getFaq) {
        this.getFaq = getFaq;

    }
}
