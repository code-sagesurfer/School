package com.example.school.team_care;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelRoleResponse {
    @SerializedName("roles_for_invitation")
    private ArrayList<Choices_> choices_arrayList;

    public ArrayList<Choices_> getChoices_arrayList() {
        return choices_arrayList;
    }

    public void setChoices_arrayList(ArrayList<Choices_> choices_arrayList) {
        this.choices_arrayList = choices_arrayList;
    }

}
