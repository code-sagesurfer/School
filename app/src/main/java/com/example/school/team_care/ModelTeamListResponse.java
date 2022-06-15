package com.example.school.team_care;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelTeamListResponse implements Parcelable {
    @SerializedName("all_teams")
    @Expose
    private ArrayList<AllTeam> allTeams = null;

    protected ModelTeamListResponse(Parcel in) {
        allTeams = in.createTypedArrayList(AllTeam.CREATOR);
    }

    public static final Creator<ModelTeamListResponse> CREATOR = new Creator<ModelTeamListResponse>() {
        @Override
        public ModelTeamListResponse createFromParcel(Parcel in) {
            return new ModelTeamListResponse(in);
        }

        @Override
        public ModelTeamListResponse[] newArray(int size) {
            return new ModelTeamListResponse[size];
        }
    };

    public ArrayList<AllTeam> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(ArrayList<AllTeam> allTeams) {
        this.allTeams = allTeams;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(allTeams);
    }
}
