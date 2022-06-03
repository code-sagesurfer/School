package com.example.school;

public class ModelDetailData {

    String Title;
    String Date;
    String ImagePath;
    String Desc;

    public ModelDetailData(String title, String date, String imagePath, String desc) {
        Title = title;
        Date = date;
        ImagePath = imagePath;
        Desc = desc;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
