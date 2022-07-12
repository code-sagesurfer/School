package com.sagesurfer.school.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelMainResponse {
    @SerializedName("drawer")
    @Expose
    private ArrayList<Drawer> drawer = null;


    public ArrayList<Drawer> getDrawer() {
        return drawer;
    }

    public void setDrawer(ArrayList<Drawer> drawer) {
        this.drawer = drawer;
    }

    @SerializedName("home")
    @Expose
    private ArrayList<Home> home = null;
    @SerializedName("quote")
    @Expose
    private Quote quote;
    @SerializedName("faq")
    @Expose
    private Faq faq;
    @SerializedName("terms_condtions")
    @Expose
    private TermsCondtions termsCondtions;
    @SerializedName("aboutus")
    @Expose
    private Aboutus aboutus;
    @SerializedName("details")
    @Expose
    private Details details;


    public ArrayList<Home> getHome() {
        return home;
    }

    public void setHome(ArrayList<Home> home) {
        this.home = home;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public Faq getFaq() {
        return faq;
    }

    public void setFaq(Faq faq) {
        this.faq = faq;
    }

    public TermsCondtions getTermsCondtions() {
        return termsCondtions;
    }

    public void setTermsCondtions(TermsCondtions termsCondtions) {
        this.termsCondtions = termsCondtions;
    }

    public Aboutus getAboutus() {
        return aboutus;
    }

    public void setAboutus(Aboutus aboutus) {
        this.aboutus = aboutus;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

}
