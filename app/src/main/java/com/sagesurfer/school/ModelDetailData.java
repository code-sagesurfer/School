package com.sagesurfer.school;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelDetailData implements Parcelable {

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

    protected ModelDetailData(Parcel in) {
        Title = in.readString();
        Date = in.readString();
        ImagePath = in.readString();
        Desc = in.readString();
    }

    public static final Creator<ModelDetailData> CREATOR = new Creator<ModelDetailData>() {
        @Override
        public ModelDetailData createFromParcel(Parcel in) {
            return new ModelDetailData(in);
        }

        @Override
        public ModelDetailData[] newArray(int size) {
            return new ModelDetailData[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Title);
        parcel.writeString(Date);
        parcel.writeString(ImagePath);
        parcel.writeString(Desc);
    }

    @Override
    public String toString() {
        return "ModelDetailData{" +
                "Title='" + Title + '\'' +
                ", Date='" + Date + '\'' +
                ", ImagePath='" + ImagePath + '\'' +
                ", Desc='" + Desc + '\'' +
                '}';
    }
}
