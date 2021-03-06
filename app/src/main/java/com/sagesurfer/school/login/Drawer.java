package com.sagesurfer.school.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drawer {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("menu")
    @Expose
    private String menu;
/*    @SerializedName("submenu")
    @Expose
    private ArrayList<Object> submenu = null;*/
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

   /* public ArrayList<Object> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(ArrayList<Object> submenu) {
        this.submenu = submenu;
    }
*/
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
