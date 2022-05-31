package com.example.school.settings;

import com.example.school.resources.General;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rahul maske  on 31/05/2022.
 */
public class Student_ implements Serializable {
    @SerializedName(General.ID)
    private int id;

    @SerializedName(General.NAME)
    private String name;

    @SerializedName("total_assigned")
    private int total_assigned;

    @SerializedName(General.STATUS)
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal_assigned() {
        return total_assigned;
    }

    public void setTotal_assigned(int total_assigned) {
        this.total_assigned = total_assigned;
    }
}
