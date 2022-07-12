package com.sagesurfer.school.journaling;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.sagesurfer.school.home.ModelGratitudeListingResponseData;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeUnlikeGratitude {
    private static final String TAG = "LikeUnlikeGratitude";

    public void onClickedLike(ModelGratitudeListingResponseData model, Context context, Activity activity) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, "like_gratitute");
        requestMap.put("gratitute_journling_id", model.getGratituteId());

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_GRATITUDE_JOURNALING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, context,activity);
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().get_gratitude_list(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        //APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            Gson gson = new Gson();
                            ModelLikeResponse mTodoMonthListingModel = gson.fromJson(response.body(), ModelLikeResponse.class);
                            if (mTodoMonthListingModel.getStatus() == 200) {
                                if (!mTodoMonthListingModel.getData().get(0).getIsLikeSymbol().equalsIgnoreCase("Like")) {
                                    Toast.makeText(activity, "Like", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, "Unlike", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        //APIManager.Companion.getInstance().dismissProgressDialog();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //showError(true, status);
        }
    }
}