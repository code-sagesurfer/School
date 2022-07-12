package com.sagesurfer.school.settings;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class ModelCountryResponse {
    @SerializedName("get_country")
    private ArrayList<Student_> data_list_country;

    @SerializedName("get_state")
    private ArrayList<Student_> data_list_state;

    @SerializedName("get_city")
    private ArrayList<Student_> data_list_city;

    public ArrayList<Student_> getData_list_country() {
        return data_list_country;
    }

    public void setData_list_country(ArrayList<Student_> data_list_country) {
        this.data_list_country = data_list_country;
    }

    public ArrayList<Student_> getData_list_state() {
        return data_list_state;
    }

    public void setData_list_state(ArrayList<Student_> data_list_state) {
        this.data_list_state = data_list_state;
    }

    public ArrayList<Student_> getData_list_city() {
        return data_list_city;
    }

    public void setData_list_city(ArrayList<Student_> data_list_city) {
        this.data_list_city = data_list_city;
    }
}
