package com.example.school.resources



import com.google.gson.JsonElement
import com.google.gson.JsonObject

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*



interface Api {
    @FormUrlEncoded
    @POST("login")
    fun calllogin(
        @Field("action") friend_list: String,
        @Field("password") password: String
    ): Call<JsonObject>


    @FormUrlEncoded
    @POST("oauth/token/")
    fun refreshtoken(
        @Field("client_id") friend_list: String,
        @Field("client_secret") password: String,
        @Field("refresh_token") refresh_token: String,
        @Field("scope") scope: String,
        @Field("grant_type") grant_type: String
    ): Call<JsonObject>

    @POST("mobile_crisis.php")
    fun getMobileCrisis(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_community.php")
    fun getMobileCommunity(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_wel_come.php")
    fun mobile_wel_come(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_selfcare_new.php")
    fun getMobile_selfcare_new(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_dashboard.php")
    fun getmobile_dashboard(@Body params: RequestBody): Call<JsonElement>

    @POST(Urls_.MOBILE_REWARDS)
    fun getRewardsData(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_youth_operations_new.php")
    fun mobile_youth_operations_new(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_cometchat.php")
    fun mobile_cometchat(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_calendar.php")
    fun mobile_calendar(@Body params: RequestBody): Call<JsonElement>

    @POST("oauth/token/")
    fun mobile_token(@Body params: RequestBody): Call<JsonElement>


    @POST("mobile_teams.php")
    fun mobile_teams(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_firebase_register.php")
    fun mobile_firebase_register(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_caseload.php")
    fun fetchCaseloadData(@Body body: RequestBody?): Call<JsonElement>


    @POST("mobile_team_all_data.php")
    fun fetch_mobile_team_all_data(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_team_data.php")
    fun fetch_mobile_team_data(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_gallery_operations.php")
    fun mobile_gallery_operations(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_fms.php")
    fun fetch_fms_data(@Body body: RequestBody?): Call<JsonElement>


    @POST("mobile_sos_fetch.php")
    fun mobile_sos_fetch(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_sos_read.php")
    fun mobile_sos_read(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_sos_new.php")
    fun mobile_sos_new(@Body body: RequestBody?): Call<JsonElement>



    @POST("mobile_teams.php")
    fun fetchTeamsData(@Body body: RequestBody?): Call<JsonElement>


    @POST("mobile_users.php")
    fun mobile_users(@Body body: RequestBody?): Call<JsonElement>

    @POST("mobile_tasklist.php")
    fun mobile_tasklist(@Body body: RequestBody?): Call<JsonElement>



    @POST("mobile_my_story.php")
    fun mobile_my_story(@Body params: RequestBody): Call<JsonElement>


    @POST("mobile_mood.php")
    fun mobile_mood(@Body params: RequestBody): Call<JsonElement>


    @POST("mobile_quotes.php")
    fun mobile_quotes(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_uplift.php")
    fun mobile_uplift(@Body params: RequestBody): Call<JsonElement>


    @POST("mobile_alerts.php")
    fun mobile_alerts(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_youth_dashboard.php")
    fun mobile_youth_dashboard(@Body params: RequestBody): Call<JsonElement>


    @POST("mobile_templates.php")
    fun mobile_templates(@Body params: RequestBody): Call<JsonElement>

    @POST(Urls_.SELF_CARE_URL)
    fun fetchselfcare(@Body body: RequestBody?): Call<JsonElement>

    @POST("add_notes_api.php")
    fun mobile_notes(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_team_contacts.php")
    fun mobile_team_contacts(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_wall_feeds.php")
    fun mobile_wall_feeds(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_profile.php")
    fun mobile_mobile_profile(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_form_builder.php")
    fun mobile_form_builder(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_werhope_team_operations.php")
    fun mobile_werhope_team_operations(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_youth_dashboard.php")
    fun mobile_uploader(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_selfcare_new.php")
    fun mobile_self_care(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_user_settings.php")
    fun mobile_user_settings(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_self_goal.php")
    fun mobile_self_goal(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_sos_counter.php")
    fun mobile_sos_counter(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_postcard.php")
    fun mobile_postcard(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_cometchat.php")
    fun mobile_mobile_cometchat(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_mhaw_appointment.php")
    fun mobile_mobile_mhaw_appointment(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_mood_dashboard.php")
    fun mobile_mood_dashboard(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_gratitude_journaling.php")
    fun get_gratitude_list(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_fms.php")
    fun get_mobile_fms(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_doc_c.php")
    fun get_mobile_doxy(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_medication.php")
    fun mobile_medication(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_addiction_track.php")
    fun mobile_addiction_track(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_get_signup_instances.php")
    fun get_instances(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_invitation_operations.php")
    fun mobile_invitation_operations(@Body params: RequestBody): Call<JsonElement>

    @POST("mobile_consent.php")
    fun get_mobile_consent(@Body params: RequestBody): Call<JsonElement>

    @Multipart
    @POST("mobile_gratitude_journaling.php")
    fun upload_gratitude_with_file(@Body params: RequestBody): Call<JsonElement>

    @Multipart
    @POST("mobile_gratitude_journaling.php")
    fun UploadGratitude(
        @Part file_attachment: MultipartBody.Part,
        @Query("action") action: String,
        @Query("title") title: String,
        @Query("category") category: String,
        @Query("userid") userid: String,
        @Query("desc") desc: String,
        @Query("shared_user_id") shared_user_id: String,
        @Query("debug") debug: String,
    ): Call<JsonElement>

    @POST("mobile_day_planner.php")
    fun mobile_day_planner(@Body body: RequestBody?): Call<JsonElement>

}