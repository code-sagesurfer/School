package com.sagesurfer.school.team_care;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamInvitationFaciliated {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
