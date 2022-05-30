package com.example.school.home;

import com.example.school.resources.General;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

public class MoodJournal_ implements Serializable {
    @SerializedName(General.DATA)
    private ArrayList<MoodJournalData_> data;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("status")
    @Expose
    private int status = 1;

    public ArrayList<MoodJournalData_> getData() {
        return data;
    }

    public void setData(ArrayList<MoodJournalData_> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
