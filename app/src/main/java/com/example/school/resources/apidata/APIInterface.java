package com.example.school.resources.apidata;

import com.example.school.resources.General;
import com.google.gson.JsonElement;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */
public interface APIInterface {
    @POST("mobile_get_instances.php")
    Call<JsonElement> get_instances(@Body RequestBody requestBody);

    @POST("mobile_login.php")
    Call<JsonElement> doGetListResources(@Body RequestBody requestBody);

    @POST(General.AUTHORIZE)
    Call<JsonElement> doAuthorised(@Body RequestBody requestBody);
}