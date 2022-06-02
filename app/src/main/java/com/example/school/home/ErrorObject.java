package com.example.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ErrorObject {
    @SerializedName("error")
    @Expose
    private String Error;

    @SerializedName("status")
    @Expose
    private int status;

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
