package com.sagesurfer.school.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelGratitudeListingResponseData {
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("shared_user_ids")
    @Expose
    private String sharedUserIds;
    @SerializedName("shared_user_name")
    @Expose
    private String sharedUserName;
    @SerializedName("total_share_count")
    @Expose
    private int totalShareCount;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("gratitute_id")
    @Expose
    private String gratituteId;
    @SerializedName("file_attachment")
    @Expose
    private String fileAttachment;
    @SerializedName("gratitute_name")
    @Expose
    private String gratituteName;
    @SerializedName("gratitute_category")
    @Expose
    private String gratituteCategory;
    @SerializedName("gratitute_category_id")
    @Expose
    private String gratituteCategoryId;
    @SerializedName("gratitute_user_id")
    @Expose
    private String gratituteUserId;
    @SerializedName("gratitute_description")
    @Expose
    private String gratituteDescription;
    @SerializedName("added_date")
    @Expose
    private String addedDate;
    @SerializedName("total_count_updated")
    @Expose
    private String totalCountUpdated;
    @SerializedName("like_count")
    @Expose
    private String likeCount;
    @SerializedName("is_like_symbol")
    @Expose
    private String isLikeSymbol;

    @SerializedName("random_bg_color_code")
    @Expose
    private String random_bg_color_code;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSharedUserIds() {
        return sharedUserIds;
    }

    public void setSharedUserIds(String sharedUserIds) {
        this.sharedUserIds = sharedUserIds;
    }

    public String getSharedUserName() {
        return sharedUserName;
    }

    public void setSharedUserName(String sharedUserName) {
        this.sharedUserName = sharedUserName;
    }

    public int getTotalShareCount() {
        return totalShareCount;
    }

    public void setTotalShareCount(int totalShareCount) {
        this.totalShareCount = totalShareCount;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGratituteId() {
        return gratituteId;
    }

    public void setGratituteId(String gratituteId) {
        this.gratituteId = gratituteId;
    }

    public String getFileAttachment() {
        return fileAttachment;
    }

    public void setFileAttachment(String fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    public String getGratituteName() {
        return gratituteName;
    }

    public void setGratituteName(String gratituteName) {
        this.gratituteName = gratituteName;
    }

    public String getGratituteCategory() {
        return gratituteCategory;
    }

    public void setGratituteCategory(String gratituteCategory) {
        this.gratituteCategory = gratituteCategory;
    }

    public String getGratituteCategoryId() {
        return gratituteCategoryId;
    }

    public void setGratituteCategoryId(String gratituteCategoryId) {
        this.gratituteCategoryId = gratituteCategoryId;
    }

    public String getGratituteUserId() {
        return gratituteUserId;
    }

    public void setGratituteUserId(String gratituteUserId) {
        this.gratituteUserId = gratituteUserId;
    }

    public String getGratituteDescription() {
        return gratituteDescription;
    }

    public void setGratituteDescription(String gratituteDescription) {
        this.gratituteDescription = gratituteDescription;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getTotalCountUpdated() {
        return totalCountUpdated;
    }

    public void setTotalCountUpdated(String totalCountUpdated) {
        this.totalCountUpdated = totalCountUpdated;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getIsLikeSymbol() {
        return isLikeSymbol;
    }

    public void setIsLikeSymbol(String isLikeSymbol) {
        this.isLikeSymbol = isLikeSymbol;
    }

    public String getRandom_bg_color_code() {
        return random_bg_color_code;
    }

    public void setRandom_bg_color_code(String random_bg_color_code) {
        this.random_bg_color_code = random_bg_color_code;
    }
}
