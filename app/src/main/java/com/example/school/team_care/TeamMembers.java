package com.example.school.team_care;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamMembers implements Parcelable {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("comet_chat_id")
    @Expose
    private String cometChatId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("is_friend")
    @Expose
    private Integer isFriend;
    @SerializedName("photo")
    @Expose
    private String photo;

    protected TeamMembers(Parcel in) {
        userId = in.readString();
        cometChatId = in.readString();
        username = in.readString();
        if (in.readByte() == 0) {
            isFriend = null;
        } else {
            isFriend = in.readInt();
        }
        photo = in.readString();
    }

    public static final Creator<TeamMembers> CREATOR = new Creator<TeamMembers>() {
        @Override
        public TeamMembers createFromParcel(Parcel in) {
            return new TeamMembers(in);
        }

        @Override
        public TeamMembers[] newArray(int size) {
            return new TeamMembers[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCometChatId() {
        return cometChatId;
    }

    public void setCometChatId(String cometChatId) {
        this.cometChatId = cometChatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(Integer isFriend) {
        this.isFriend = isFriend;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(cometChatId);
        parcel.writeString(username);
        if (isFriend == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(isFriend);
        }
        parcel.writeString(photo);
    }
}
