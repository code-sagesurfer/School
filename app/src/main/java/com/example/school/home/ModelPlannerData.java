package com.example.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPlannerData {
    @SerializedName("last_updated")
    @Expose
    private int lastUpdated;
    @SerializedName("c_date")
    @Expose
    private String cDate;
    @SerializedName("timestamp")
    @Expose
    private int timestamp;
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
    private int isRead;
    @SerializedName("is_delete")
    @Expose
    private int isDelete;
    @SerializedName("participants")
    @Expose
    private int participants;
    @SerializedName("status")
    @Expose
    private int status;

    public int getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
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

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
