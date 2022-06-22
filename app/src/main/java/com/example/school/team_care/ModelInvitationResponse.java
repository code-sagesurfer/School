package com.example.school.team_care;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelInvitationResponse {
    @SerializedName("team_invitation_faciliated")
    @Expose
    private TeamInvitationFaciliated teamInvitationFaciliated;

    public TeamInvitationFaciliated getTeamInvitationFaciliated() {
        return teamInvitationFaciliated;
    }

    public void setTeamInvitationFaciliated(TeamInvitationFaciliated teamInvitationFaciliated) {
        this.teamInvitationFaciliated = teamInvitationFaciliated;
    }
}
