package com.example.school.home.main;

public class ModelDrawerMenuListItems {

    private int IconId;
    private String name;
    private int divider;
    public ModelDrawerMenuListItems(int iconId, String name,int divider) {
        IconId = iconId;
        this.name = name;
        this.divider = divider;
    }

    public int getIconId() {
        return IconId;
    }

    public void setIconId(int iconId) {
        IconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDivider() {
        return divider;
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }
}
