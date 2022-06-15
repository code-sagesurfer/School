package com.example.school.skill_development;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMyGoal {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
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
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("is_read")
    @Expose
    private Integer isRead;
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
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
