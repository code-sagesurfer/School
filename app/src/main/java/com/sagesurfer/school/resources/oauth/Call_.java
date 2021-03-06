package com.sagesurfer.school.resources.oauth;



import static com.sagesurfer.school.resources.apidata.MakeCall.bodyToString;

import android.content.Context;
import android.os.AsyncTask;


import com.sagesurfer.school.resources.AppLog;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Rahul Maske (monikam@sagesurfer.com)
 *         Created on 13/03/2018
 *         Last Modified on
 */

public class Call_ extends AsyncTask<Void, Void, String> {

    private final OkHttpClient client = new OkHttpClient();

    private static final String TAG = Call_.class.getSimpleName();
    private final String url;
    private final String tag;
    private final RequestBody requestBody;
    Context context;

    public Call_(String url, RequestBody requestBody, String tag, Context _context) {
        this.url = url;
        this.requestBody = requestBody;
        this.tag = tag;
        this.context = _context;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected String doInBackground(Void... params) {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .tag(tag)
                .build();
        String body = bodyToString(request);
        //Logger.debug(tag, url + "?" + body, context);
        AppLog.e(TAG,url+"?"+body);
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            AppLog.i(TAG, "doInBackground: "+res);
            if (response.isSuccessful()) {
                return res;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
 /*
    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
    */


}