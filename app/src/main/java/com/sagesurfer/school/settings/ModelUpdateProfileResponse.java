package com.sagesurfer.school.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUpdateProfileResponse {
    @SerializedName("edit_profile")
    @Expose
    private ModelUpdateProfileResponseData editProfile;

    public ModelUpdateProfileResponseData getEditProfile() {
        return editProfile;
    }

    public void setEditProfile(ModelUpdateProfileResponseData editProfile) {
        this.editProfile = editProfile;
    }
}
