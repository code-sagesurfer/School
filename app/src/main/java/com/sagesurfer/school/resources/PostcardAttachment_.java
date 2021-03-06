package com.sagesurfer.school.resources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

public class PostcardAttachment_ implements Serializable {

    @SerializedName(General.STATUS)
    private int status;

    @SerializedName(General.ID)
    private int id;

    @SerializedName("file")
    private String file;

    @SerializedName(General.NAME)
    private String name;

    @SerializedName(General.SIZE)
    private long size;

    private boolean isNewFile=false;

    /*Setter Method*/
    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /*Getter Method*/
    public int getStatus() {
        return this.status;
    }

    public int getId() {
        return this.id;
    }

    public String getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return this.size;
    }

    public boolean isNewFile() {
        return isNewFile;
    }

    public void setNewFile(boolean newFile) {
        isNewFile = newFile;
    }
}
