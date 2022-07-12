package com.sagesurfer.school.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aboutus {
    @SerializedName("aboutus_url")
    @Expose
    private String aboutusUrl;

    public String getAboutusUrl() {
        return aboutusUrl;
    }

    public void setAboutusUrl(String aboutusUrl) {
        this.aboutusUrl = aboutusUrl;
    }
}
