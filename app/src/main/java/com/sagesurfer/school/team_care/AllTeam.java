package com.sagesurfer.school.team_care;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllTeam implements Parcelable {
    @SerializedName("last_visit")
    @Expose
    private int lastVisit;
    @SerializedName("last_activity")
    @Expose
    private int lastActivity;
    @SerializedName("is_modarator")
    @Expose
    private int isModarator;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("permission")
    @Expose
    private String permission;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("members")
    @Expose
    private int members;
    @SerializedName("members_list")
    @Expose
    private ArrayList<TeamMembers> membersList = null;
    @SerializedName("modrator")
    @Expose
    private String modrator;
    @SerializedName("is_joined_team")
    @Expose
    private String isJoinedTeam;
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("status")
    @Expose
    private int status;

    protected AllTeam(Parcel in) {
        lastVisit = in.readInt();
        lastActivity = in.readInt();
        isModarator = in.readInt();
        groupId = in.readString();
        type = in.readString();
        name = in.readString();
        permission = in.readString();
        photo = in.readString();
        members = in.readInt();
        modrator = in.readString();
        isJoinedTeam = in.readString();
        ownerId = in.readString();
        bannerImg = in.readString();
        status = in.readInt();
    }

    public static final Creator<AllTeam> CREATOR = new Creator<AllTeam>() {
        @Override
        public AllTeam createFromParcel(Parcel in) {
            return new AllTeam(in);
        }

        @Override
        public AllTeam[] newArray(int size) {
            return new AllTeam[size];
        }
    };

    public int getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(int lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(int lastActivity) {
        this.lastActivity = lastActivity;
    }

    public int getIsModarator() {
        return isModarator;
    }

    public void setIsModarator(int isModarator) {
        this.isModarator = isModarator;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public ArrayList<TeamMembers> getMembersList() {
        return membersList;
    }

    public void setMembersList(ArrayList<TeamMembers> membersList) {
        this.membersList = membersList;
    }

    public String getModrator() {
        return modrator;
    }

    public void setModrator(String modrator) {
        this.modrator = modrator;
    }

    public String getIsJoinedTeam() {
        return isJoinedTeam;
    }

    public void setIsJoinedTeam(String isJoinedTeam) {
        this.isJoinedTeam = isJoinedTeam;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(lastVisit);
        parcel.writeInt(lastActivity);
        parcel.writeInt(isModarator);
        parcel.writeString(groupId);
        parcel.writeString(type);
        parcel.writeString(name);
        parcel.writeString(permission);
        parcel.writeString(photo);
        parcel.writeInt(members);
        parcel.writeString(modrator);
        parcel.writeString(isJoinedTeam);
        parcel.writeString(ownerId);
        parcel.writeString(bannerImg);
        parcel.writeInt(status);
    }
}
