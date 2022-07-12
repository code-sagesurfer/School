package com.sagesurfer.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPlannerData {
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("c_date")
    @Expose
    private String cDate;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
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
    private String isRead;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;

    /*@SerializedName("participants")
    @Expose
    private ArrayList<Participant> participants;*/

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("goal_type")
    @Expose
    private String goalType;
    @SerializedName("impact")
    @Expose
    private String impact;
    @SerializedName("goal_status")
    @Expose
    private String goalStatus;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("occurrences")
    @Expose
    private String occurrences;
    @SerializedName("notify_frequency")
    @Expose
    private String notifyFrequency;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("is_dashboard")
    @Expose
    private String isDashboard;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("frequency")
    @Expose
    private String frequency;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("frequency_unit")
    @Expose
    private String frequencyUnit;
    @SerializedName("frequency_sub_unit_days")
    @Expose
    private String frequencySubUnitDays;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("notify")
    @Expose
    private String notify;
    @SerializedName("notify_at")
    @Expose
    private String notifyAt;
    @SerializedName("goal_posting_date")
    @Expose
    private String goalPostingDate;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("goal_current_status")
    @Expose
    private String goalCurrentStatus;
    @SerializedName("total_pagination_number")
    @Expose
    private String totalPaginationNumber;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("todo_status")
    @Expose
    private String todoStatus;
    @SerializedName("prio")
    @Expose
    private String prio;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("own_or_team")
    @Expose
    private String ownOrTeam;
    @SerializedName("is_owner")
    @Expose
    private String isOwner;

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /*public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getGoalStatus() {
        return goalStatus;
    }

    public void setGoalStatus(String goalStatus) {
        this.goalStatus = goalStatus;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(String occurrences) {
        this.occurrences = occurrences;
    }

    public String getNotifyFrequency() {
        return notifyFrequency;
    }

    public void setNotifyFrequency(String notifyFrequency) {
        this.notifyFrequency = notifyFrequency;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIsDashboard() {
        return isDashboard;
    }

    public void setIsDashboard(String isDashboard) {
        this.isDashboard = isDashboard;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getFrequencyUnit() {
        return frequencyUnit;
    }

    public void setFrequencyUnit(String frequencyUnit) {
        this.frequencyUnit = frequencyUnit;
    }

    public String getFrequencySubUnitDays() {
        return frequencySubUnitDays;
    }

    public void setFrequencySubUnitDays(String frequencySubUnitDays) {
        this.frequencySubUnitDays = frequencySubUnitDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getNotifyAt() {
        return notifyAt;
    }

    public void setNotifyAt(String notifyAt) {
        this.notifyAt = notifyAt;
    }

    public String getGoalPostingDate() {
        return goalPostingDate;
    }

    public void setGoalPostingDate(String goalPostingDate) {
        this.goalPostingDate = goalPostingDate;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getGoalCurrentStatus() {
        return goalCurrentStatus;
    }

    public void setGoalCurrentStatus(String goalCurrentStatus) {
        this.goalCurrentStatus = goalCurrentStatus;
    }

    public String getTotalPaginationNumber() {
        return totalPaginationNumber;
    }

    public void setTotalPaginationNumber(String totalPaginationNumber) {
        this.totalPaginationNumber = totalPaginationNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(String todoStatus) {
        this.todoStatus = todoStatus;
    }

    public String getPrio() {
        return prio;
    }

    public void setPrio(String prio) {
        this.prio = prio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOwnOrTeam() {
        return ownOrTeam;
    }

    public void setOwnOrTeam(String ownOrTeam) {
        this.ownOrTeam = ownOrTeam;
    }

    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }
}
