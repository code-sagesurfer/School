package com.sagesurfer.school.team_care;

import android.os.Parcel;
import android.os.Parcelable;

import com.sagesurfer.school.resources.General;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul maske on 16/06/2022.
 */

public class CometChatTeamMembers_ implements Parcelable {

    @SerializedName(General.ID)
    private int id;

    @SerializedName(General.U_MEMBER_ID)
    private int u_member_id;

    @SerializedName(General.N)
    private String n;

    @SerializedName(General.L)
    private String l;

    @SerializedName(General.A) //image
    private String a;

    @SerializedName(General.D)
    private int d;

    @SerializedName(General.S)
    private String s;

    @SerializedName(General.M)
    private String m;

    @SerializedName(General.LS)
    private long ls;

    @SerializedName(General.LSTN)
    private int lstn;

    @SerializedName(General.CH)
    private String ch;

    @SerializedName(General.ROLE_ID)
    private String role_id;

    @SerializedName(General.RDRS)
    private int rdrs;

    @SerializedName(General.PROTYPE)
    private int protype;

    @SerializedName(General.GID)
    private int gid;

    @SerializedName(General.STATUS)
    private int status;

    @SerializedName(General.NAME)
    private String name;

    @SerializedName(General.FIRST_NAME)
    private String firstname;

    @SerializedName(General.ROLE)
    private String role;

    @SerializedName(General.TOTAL_UNREAD_MESSAGES)
    private String unread_message_count;


    protected CometChatTeamMembers_(Parcel in) {
        id = in.readInt();
        u_member_id = in.readInt();
        n = in.readString();
        l = in.readString();
        a = in.readString();
        d = in.readInt();
        s = in.readString();
        m = in.readString();
        ls = in.readLong();
        lstn = in.readInt();
        ch = in.readString();
        role_id = in.readString();
        rdrs = in.readInt();
        protype = in.readInt();
        gid = in.readInt();
        status = in.readInt();
        name = in.readString();
        firstname = in.readString();
        role = in.readString();
        unread_message_count = in.readString();
    }

    public static final Creator<CometChatTeamMembers_> CREATOR = new Creator<CometChatTeamMembers_>() {
        @Override
        public CometChatTeamMembers_ createFromParcel(Parcel in) {
            return new CometChatTeamMembers_(in);
        }

        @Override
        public CometChatTeamMembers_[] newArray(int size) {
            return new CometChatTeamMembers_[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public long getLs() {
        return ls;
    }

    public void setLs(long ls) {
        this.ls = ls;
    }

    public int getLstn() {
        return lstn;
    }

    public void setLstn(int lstn) {
        this.lstn = lstn;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public int getRdrs() {
        return rdrs;
    }

    public void setRdrs(int rdrs) {
        this.rdrs = rdrs;
    }

    public int getProtype() {
        return protype;
    }

    public void setProtype(int protype) {
        this.protype = protype;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getU_member_id() {
        return u_member_id;
    }

    public void setU_member_id(int u_member_id) {
        this.u_member_id = u_member_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(u_member_id);
        parcel.writeString(n);
        parcel.writeString(l);
        parcel.writeString(a);
        parcel.writeInt(d);
        parcel.writeString(s);
        parcel.writeString(m);
        parcel.writeLong(ls);
        parcel.writeInt(lstn);
        parcel.writeString(ch);
        parcel.writeString(role_id);
        parcel.writeInt(rdrs);
        parcel.writeInt(protype);
        parcel.writeInt(gid);
        parcel.writeInt(status);
        parcel.writeString(name);
        parcel.writeString(firstname);
        parcel.writeString(role);
        parcel.writeString(unread_message_count);
    }
}
