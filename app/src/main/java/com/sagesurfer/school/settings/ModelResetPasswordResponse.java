package com.sagesurfer.school.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelResetPasswordResponse {
    @SerializedName("reset_password")
    @Expose
    private ResetPassword resetPassword;

    public ResetPassword getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(ResetPassword resetPassword) {
        this.resetPassword = resetPassword;
    }
}
