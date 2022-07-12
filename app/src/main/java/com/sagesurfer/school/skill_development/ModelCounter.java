package com.sagesurfer.school.skill_development;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCounter {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("completed")
    @Expose
    private String completed;
    @SerializedName("miss_out")
    @Expose
    private String missOut;
    @SerializedName("running")
    @Expose
    private String running;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private int status;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getMissOut() {
        return missOut;
    }

    public void setMissOut(String missOut) {
        this.missOut = missOut;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
