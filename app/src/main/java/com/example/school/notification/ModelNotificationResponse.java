package com.example.school.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelNotificationResponse {
    @SerializedName("notification")
    @Expose
    private ArrayList<Notification> getNotifications;

    public ArrayList<Notification> getNotifications() {
        return getNotifications;
    }

    public void setGetNotifications(ArrayList<Notification> getNotifications) {
        this.getNotifications = getNotifications;
    }

    @SerializedName("status")
    @Expose
    private String status="1" ;

    @SerializedName("error_description")
    @Expose
    private String error_description;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
