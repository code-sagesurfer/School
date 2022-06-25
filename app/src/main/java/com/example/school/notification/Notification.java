package com.example.school.notification;

import com.example.school.resources.General;
import com.example.school.resources.apidata._Base64;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author RahulMaske(rahul.maske@sagesurfer.com)
 * Created on 24-06-2022
 *
 **/

public class Notification {

    @SerializedName(General.TYPE)
    private String type;

    @SerializedName(General.MEMBER_USER_ID)
    private String member_user_id;

    @SerializedName(General.GROUP_OWNER_ID_N)
    private String group_owner_id;

    @SerializedName("mhaw_services")
    private String mhaw_services;

    @SerializedName("mhaw_type")
    private String mhaw_type;

    @SerializedName(General.SUBTYPE)
    private String sub_type;

    @SerializedName(General.REF_TYPE)
    private String ref_type;

    @SerializedName(General.IS_READ)
    private int is_read;

    @SerializedName(General.ID)
    private long id;

    @SerializedName(General.ADDED_BY)
    private String added_by;

    @SerializedName("profile")
    private String profile;

    @SerializedName(General.GROUP_NAME)
    private String group_name;

    public String getIs_decline() {
        return is_decline;
    }

    public void setIs_decline(String is_decline) {
        this.is_decline = is_decline;
    }

    @SerializedName(General.GROUP_ID)
    private long group_id;

    @SerializedName(General.TITLE)
    private String title;

    @SerializedName("is_member")
    private String is_member;

    @SerializedName("group_type")
    private String group_type;

    @SerializedName("is_decline")
    private String is_decline;

    @SerializedName("is_added")
    private String is_added;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastname;



    public String getIs_added() {
        return is_added;
    }

    public void setIs_added(String is_added) {
        this.is_added = is_added;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    public String getGroup_password() {
        return group_password;
    }

    public void setGroup_password(String group_password) {
        this.group_password = group_password;
    }

    @SerializedName("group_password")
    private String group_password;

    public String getIs_member() {
        return is_member;
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member;
    }

    public String getMhaw_services() {
        return mhaw_services;
    }

    public void setMhaw_services(String mhaw_services) {
        this.mhaw_services = mhaw_services;
    }

    public String getMhaw_type() {
        return mhaw_type;
    }

    public void setMhaw_type(String mhaw_type) {
        this.mhaw_type = mhaw_type;
    }

    @SerializedName("title2")
    private String title1;

    @SerializedName("title1")
    private String title_1_other;

    @SerializedName("description1")
    private String description1;

    public String getGroup_owner_id() {
        return group_owner_id;
    }

    public void setGroup_owner_id(String group_owner_id) {
        this.group_owner_id = group_owner_id;
    }

    @SerializedName(General.DESCRIPTION)
    private String description;

    @SerializedName(General.TIMESTAMP)
    private long timestamp;

    @SerializedName(General.REF_ID)
    private long ref_id;

    @SerializedName(General.NAME)
    private String name;

    @SerializedName(General.MOOD_NAME)
    private String mood_name;

    @SerializedName(General.HIGH_PRIORITY)
    private int high_priority;

    @SerializedName(General.DETAILS)
    private String details;

    @SerializedName(General.MODULE)
    private String module;

    @SerializedName(General.STATUS)
    private int status;

    @SerializedName(General.IS_DELETE)
    private int is_delete;             //providing random value because of some webservice issues

/*    @SerializedName(General.PARTICIPANTS)//participants
    private ArrayList<Participant_> participants;*/

    @SerializedName(General.CONSUMER_ID)
    private int consumer_id;

    @SerializedName(General.IS_FILLED)
    private int is_filled;

    @SerializedName("added_by_id")
    private int added_by_id;

    private boolean selected = false;

    @SerializedName(General.AM_PM)
    private String ampm;

    @SerializedName("mat_date_time")
    private String mat_date_time;

    @SerializedName("medicine_name")
    private String medicine_name;

    @SerializedName("reason")
    private String reason;

    @SerializedName(General.AM_PM_MESSAGE)
    private String ampm_msg;

    @SerializedName("sober_for")
    private String sober_for;


    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @SerializedName("stud_id")
    private int stud_id;


/*    private Announcement_ announcement_;
    private FileSharing_ fms_;
    private TeamTalk_ teamTalk_;
    private Poll_ poll_;
    private Task_ task_;
    private Event_ event_;
    private Goal_ goal_;
    private Content_ selfCare_;*/

    public String getTitle_1_other() {
        return title_1_other;
    }

    public void setTitle_1_other(String title_1_other) {
        this.title_1_other = title_1_other;
    }

    public int getHigh_priority() {
        return high_priority;
    }

    public void setHigh_priority(int high_priority) {
        this.high_priority = high_priority;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getMood_name() {
        return mood_name;
    }

    public void setMood_name(String mood_name) {
        this.mood_name = mood_name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /* Getter Methods */

    public String getType() {
        return type;
    }

    public int getIs_read() {
        return is_read;
    }

    public long getId() {
        return id;
    }

    public String getAdded_by() {
        return added_by;
    }

    public String getProfile() {
        return profile;
    }

    public String getGroup_name() {
        return group_name;
    }

    public long getGroup_id() {
        return group_id;
    }

    public String getTitle() {
        return _Base64.decode(title);
    }

    public String getDescription() {
        return _Base64.decode(description);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getRef_id() {
        return ref_id;
    }

    public void setRef_id(long ref_id) {
        this.ref_id = ref_id;
    }

    public int getStatus() {
        return status;
    }

/*    public Announcement_ getAnnouncement_() {
        return announcement_;
    }

    public void setAnnouncement_(Announcement_ announcement_) {
        this.announcement_ = announcement_;
    }

    public FileSharing_ getFms_() {
        return fms_;
    }

    public void setFms_(FileSharing_ fms_) {
        this.fms_ = fms_;
    }

    public TeamTalk_ getTeamTalk_() {
        return teamTalk_;
    }

    public void setTeamTalk_(TeamTalk_ teamTalk_) {
        this.teamTalk_ = teamTalk_;
    }

    public Poll_ getPoll_() {
        return poll_;
    }

    public void setPoll_(Poll_ poll_) {
        this.poll_ = poll_;
    }

    public Task_ getTask_() {
        return task_;
    }

    public void setTask_(Task_ task_) {
        this.task_ = task_;
    }

    public Event_ getEvent_() {
        return event_;
    }

    public void setEvent_(Event_ event_) {
        this.event_ = event_;
    }

    public Goal_ getGoal_() {
        return goal_;
    }

    public void setGoal_(Goal_ goal_) {
        this.goal_ = goal_;
    }

    public Content_ getSelfCare_() {
        return selfCare_;
    }

    public void setSelfCare_(Content_ selfCare_) {
        this.selfCare_ = selfCare_;
    }*/

    /*public ArrayList<Participant_> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant_> participants) {
        this.participants = participants;
    }*/

    public int getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    public int getIs_filled() {
        return is_filled;
    }

    public void setIs_filled(int is_filled) {
        this.is_filled = is_filled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getAdded_by_id() {
        return added_by_id;
    }

    public void setAdded_by_id(int added_by_id) {
        this.added_by_id = added_by_id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getStud_id() {
        return stud_id;
    }

    public void setStud_id(int stud_id) {
        this.stud_id = stud_id;
    }

    public String getTitle1() {

        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getRef_type() {
        return ref_type;
    }

    public void setRef_type(String ref_type) {
        this.ref_type = ref_type;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public String getAmpm_msg() {
        return ampm_msg;
    }

    public void setAmpm_msg(String ampm_msg) {
        this.ampm_msg = ampm_msg;
    }

    public String getMember_user_id() {
        return member_user_id;
    }

    public void setMember_user_id(String member_user_id) {
        this.member_user_id = member_user_id;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getMat_date_time() {
        return mat_date_time;
    }

    public void setMat_date_time(String mat_date_time) {
        this.mat_date_time = mat_date_time;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSober_for() {
        return sober_for;
    }

    public void setSober_for(String sober_for) {
        this.sober_for = sober_for;
    }
}
