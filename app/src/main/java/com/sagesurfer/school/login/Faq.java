package com.sagesurfer.school.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Faq {
    @SerializedName("faq_url")
    @Expose
    private String faqUrl;

    public String getFaqUrl() {
        return faqUrl;
    }

    public void setFaqUrl(String faqUrl) {
        this.faqUrl = faqUrl;
    }
}
