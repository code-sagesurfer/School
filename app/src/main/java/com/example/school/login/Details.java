package com.example.school.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("themes_id")
    @Expose
    private String themesId;
    @SerializedName("meeting_id")
    @Expose
    private String meetingId;
    @SerializedName("meeting_pwd")
    @Expose
    private String meetingPwd;
    @SerializedName("addiction")
    @Expose
    private int addiction;
    @SerializedName("login_id")
    @Expose
    private int loginId;
    @SerializedName("comet_chat_id")
    @Expose
    private String cometChatId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("welcome_page_skip")
    @Expose
    private String welcomePageSkip;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("user_date_format")
    @Expose
    private String userDateFormat;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("is_reset")
    @Expose
    private String isReset;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("role_id")
    @Expose
    private String roleId;
    @SerializedName("is_reviewer")
    @Expose
    private String isReviewer;
    @SerializedName("is_uploader")
    @Expose
    private int isUploader;
    @SerializedName("mood_reminder_status")
    @Expose
    private String moodReminderStatus;
    @SerializedName("mood_setting")
    @Expose
    private String moodSetting;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("clinician_id")
    @Expose
    private String clinicianId;
    @SerializedName("clinician_username")
    @Expose
    private String clinicianUsername;
    @SerializedName("show_privacy_popup")
    @Expose
    private int showPrivacyPopup;
    @SerializedName("privacy_url")
    @Expose
    private String privacyUrl;
    @SerializedName("show_one_time_survey")
    @Expose
    private int showOneTimeSurvey;
    @SerializedName("show_daily_survey")
    @Expose
    private int showDailySurvey;
    @SerializedName("show_daily_dosage")
    @Expose
    private int showDailyDosage;
    @SerializedName("senjam_goal_id")
    @Expose
    private int senjamGoalId;
    @SerializedName("show_reward_popup")
    @Expose
    private int showRewardPopup;
    @SerializedName("notification_id_reward")
    @Expose
    private int notificationIdReward;
    @SerializedName("category_id_reward")
    @Expose
    private int categoryIdReward;
    @SerializedName("users_time")
    @Expose
    private int usersTime;
    @SerializedName("show_appointment_popup")
    @Expose
    private int showAppointmentPopup;
    @SerializedName("app_id")
    @Expose
    private int appId;
    @SerializedName("is_appointment_add")
    @Expose
    private int isAppointmentAdd;
    @SerializedName("show_goal_pop")
    @Expose
    private int showGoalPop;
    @SerializedName("invite_title")
    @Expose
    private String inviteTitle;
    @SerializedName("is_reset_password")
    @Expose
    private int isResetPassword;
    @SerializedName("intake_form")
    @Expose
    private String intakeForm;
    @SerializedName("show_behavioral")
    @Expose
    private String showBehavioral;
    @SerializedName("profile_completion")
    @Expose
    private int profileCompletion;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("login_log_id")
    @Expose
    private int loginLogId;
    @SerializedName("userTimezone")
    @Expose
    private int userTimezone;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("client_secret")
    @Expose
    private String clientSecret;

    @SerializedName("landing_questions")
    @Expose
    private int landingQuestions;
    @SerializedName("is_show_sows")
    @Expose
    private int isShowSows;

    public String getThemesId() {
        return themesId;
    }

    public void setThemesId(String themesId) {
        this.themesId = themesId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingPwd() {
        return meetingPwd;
    }

    public void setMeetingPwd(String meetingPwd) {
        this.meetingPwd = meetingPwd;
    }

    public int getAddiction() {
        return addiction;
    }

    public void setAddiction(int addiction) {
        this.addiction = addiction;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
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

    public String getWelcomePageSkip() {
        return welcomePageSkip;
    }

    public void setWelcomePageSkip(String welcomePageSkip) {
        this.welcomePageSkip = welcomePageSkip;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserDateFormat() {
        return userDateFormat;
    }

    public void setUserDateFormat(String userDateFormat) {
        this.userDateFormat = userDateFormat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsReset() {
        return isReset;
    }

    public void setIsReset(String isReset) {
        this.isReset = isReset;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getIsReviewer() {
        return isReviewer;
    }

    public void setIsReviewer(String isReviewer) {
        this.isReviewer = isReviewer;
    }

    public int getIsUploader() {
        return isUploader;
    }

    public void setIsUploader(int isUploader) {
        this.isUploader = isUploader;
    }

    public String getMoodReminderStatus() {
        return moodReminderStatus;
    }

    public void setMoodReminderStatus(String moodReminderStatus) {
        this.moodReminderStatus = moodReminderStatus;
    }

    public String getMoodSetting() {
        return moodSetting;
    }

    public void setMoodSetting(String moodSetting) {
        this.moodSetting = moodSetting;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(String clinicianId) {
        this.clinicianId = clinicianId;
    }

    public String getClinicianUsername() {
        return clinicianUsername;
    }

    public void setClinicianUsername(String clinicianUsername) {
        this.clinicianUsername = clinicianUsername;
    }

    public int getShowPrivacyPopup() {
        return showPrivacyPopup;
    }

    public void setShowPrivacyPopup(int showPrivacyPopup) {
        this.showPrivacyPopup = showPrivacyPopup;
    }

    public String getPrivacyUrl() {
        return privacyUrl;
    }

    public void setPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
    }

    public int getShowOneTimeSurvey() {
        return showOneTimeSurvey;
    }

    public void setShowOneTimeSurvey(int showOneTimeSurvey) {
        this.showOneTimeSurvey = showOneTimeSurvey;
    }

    public int getShowDailySurvey() {
        return showDailySurvey;
    }

    public void setShowDailySurvey(int showDailySurvey) {
        this.showDailySurvey = showDailySurvey;
    }

    public int getShowDailyDosage() {
        return showDailyDosage;
    }

    public void setShowDailyDosage(int showDailyDosage) {
        this.showDailyDosage = showDailyDosage;
    }

    public int getSenjamGoalId() {
        return senjamGoalId;
    }

    public void setSenjamGoalId(int senjamGoalId) {
        this.senjamGoalId = senjamGoalId;
    }

    public int getShowRewardPopup() {
        return showRewardPopup;
    }

    public void setShowRewardPopup(int showRewardPopup) {
        this.showRewardPopup = showRewardPopup;
    }

    public int getNotificationIdReward() {
        return notificationIdReward;
    }

    public void setNotificationIdReward(int notificationIdReward) {
        this.notificationIdReward = notificationIdReward;
    }

    public int getCategoryIdReward() {
        return categoryIdReward;
    }

    public void setCategoryIdReward(int categoryIdReward) {
        this.categoryIdReward = categoryIdReward;
    }

    public int getUsersTime() {
        return usersTime;
    }

    public void setUsersTime(int usersTime) {
        this.usersTime = usersTime;
    }

    public int getShowAppointmentPopup() {
        return showAppointmentPopup;
    }

    public void setShowAppointmentPopup(int showAppointmentPopup) {
        this.showAppointmentPopup = showAppointmentPopup;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getIsAppointmentAdd() {
        return isAppointmentAdd;
    }

    public void setIsAppointmentAdd(int isAppointmentAdd) {
        this.isAppointmentAdd = isAppointmentAdd;
    }

    public int getShowGoalPop() {
        return showGoalPop;
    }

    public void setShowGoalPop(int showGoalPop) {
        this.showGoalPop = showGoalPop;
    }

    public String getInviteTitle() {
        return inviteTitle;
    }

    public void setInviteTitle(String inviteTitle) {
        this.inviteTitle = inviteTitle;
    }

    public int getIsResetPassword() {
        return isResetPassword;
    }

    public void setIsResetPassword(int isResetPassword) {
        this.isResetPassword = isResetPassword;
    }

    public String getIntakeForm() {
        return intakeForm;
    }

    public void setIntakeForm(String intakeForm) {
        this.intakeForm = intakeForm;
    }

    public String getShowBehavioral() {
        return showBehavioral;
    }

    public void setShowBehavioral(String showBehavioral) {
        this.showBehavioral = showBehavioral;
    }

    public int getProfileCompletion() {
        return profileCompletion;
    }

    public void setProfileCompletion(int profileCompletion) {
        this.profileCompletion = profileCompletion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(int loginLogId) {
        this.loginLogId = loginLogId;
    }

    public int getUserTimezone() {
        return userTimezone;
    }

    public void setUserTimezone(int userTimezone) {
        this.userTimezone = userTimezone;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLandingQuestions() {
        return landingQuestions;
    }

    public void setLandingQuestions(int landingQuestions) {
        this.landingQuestions = landingQuestions;
    }

    public int getIsShowSows() {
        return isShowSows;
    }

    public void setIsShowSows(int isShowSows) {
        this.isShowSows = isShowSows;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
