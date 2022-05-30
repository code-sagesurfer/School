package com.example.school.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsCondtions {
    @SerializedName("terms")
    @Expose
    private String terms;

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}
