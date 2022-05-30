package com.example.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlannerData {
    @SerializedName("last_updated")
    @Expose
    private Integer lastUpdated;
    @SerializedName("c_date")
    @Expose
    private String cDate;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("event_time")
    @Expose
    private String eventTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_zoom_meeting")
    @Expose
    private String isZoomMeeting;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("is_read")
    @Expose
    private Integer isRead;
    @SerializedName("is_delete")
    @Expose
    private Integer isDelete;
    @SerializedName("participants")
    @Expose
    private Integer participants;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsZoomMeeting() {
        return isZoomMeeting;
    }

    public void setIsZoomMeeting(String isZoomMeeting) {
        this.isZoomMeeting = isZoomMeeting;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
