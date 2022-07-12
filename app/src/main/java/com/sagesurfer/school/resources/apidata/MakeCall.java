package com.sagesurfer.school.resources.apidata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sagesurfer.school.login.LoginActivity;
import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.DeviceInfo;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.oauth.ModelToken;
import com.sagesurfer.school.resources.oauth.Oauth;
import com.sagesurfer.school.resources.oauth.OauthPreferences;
import com.sagesurfer.school.resources.oauth.RefreshToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

@SuppressWarnings("ConstantConditions")
public class MakeCall {
    private static final String TAG = "MakeCall";
    private static String CLASS_TAG = "MakeCall";
    private static final OkHttpClient client = new OkHttpClient();
    private static RefreshToken refreshToken;

    public static RequestBody postGetInstances(String url, RequestBody formBody, String TAG, Context _context, Activity activity) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .tag(TAG)
                .build();
        AppLog.e(TAG, url + "?" + bodyToString(request));
        return convertBody(bodyToString(request));
    }

    public synchronized static RequestBody makeLoginCall(HashMap<String, String> map, String url, String tag,
                                                         Context _context, Activity activity) {
        if (!tag.equalsIgnoreCase("Authorize")) {
            map.put(General.IMEI, DeviceInfo.getDeviceId(activity));
            map.put(General.VERSION, DeviceInfo.getVersion());
            map.put(General.MODELNO, DeviceInfo.getDeviceName());
        }

        Request request = new Request.Builder()
                .url(url)
                .post(makeBody(map))
                .tag(tag)
                .build();

        String body = bodyToString(request);
        Log.i(TAG, "makeLoginCall: "+url+"?"+body);
        return finalBodyForLogin(body);

    }

    // make normal call without encrypting parameters
    public static void postNormal(String url, RequestBody formBody, String TAG, Context context)
            throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .tag(TAG)
                .build();
        //Logger.error(TAG, url + "?" + bodyToString(request), context);
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        //Logger.error(TAG, res, context);
    }

    // add encrypted parameters to one key and make call with device details
    private static RequestBody convertBody(String params) {
        params = UrlEncoder_.encrypt(params);
        return new FormBody.Builder()
                .add("akujs", params)
                .add("d", "a")
                .build();
    }

    // convert encrypted parameters to normal string
    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }





    // make final url with encrypted parameters and agent name
    private static RequestBody finalBodyForLogin(String mainBody) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder
                .add("akujs", UrlEncoder_.encrypt(mainBody))
                .add("d", "a");
        return formBuilder.build();
    }

    private static RequestBody makeBody(HashMap<String, String> map) {
        Set keys = map.keySet();
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Object object : keys) {
            String key = (String) object;
            String value = map.get(key);
            formBuilder.add(key, value);
        }
        Log.i(TAG, "makeBody: "+formBuilder.build());
        return formBuilder.build();
    }

    private static RequestBody makeBodyNormalCall(HashMap<String, String> map) {
        HashMap<String, String> keyMap = KeyMaker_.getKey();
        map.put(General.TOKEN, keyMap.get(General.TOKEN));
        map.put(General.KEY, keyMap.get(General.KEY));
        map.put(General.USER_ID, Preferences.get(General.USER_ID)); //logged in user id
        map.put(General.TIMEZONE, Preferences.get(General.TIMEZONE));
        map.put(General.TIMEZONE_SERVER, Preferences.get(General.TIMEZONE_SERVER));
        map.put(General.DOMAIN_CODE, Preferences.get(General.DOMAIN_CODE));

        Set keys = map.keySet();
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Object object : keys) {
            String key = (String) object;
            String value = map.get(key);
            formBuilder.add(key, value);
        }
        Log.i(TAG, "makeBody: "+formBuilder.build());
        return formBuilder.build();
    }


    public synchronized static RequestBody make(HashMap<String, String> map, String url, String tag,
                                                Context _context, Activity activity) {
        CLASS_TAG = tag;
        //HashMap<String, String> requestMap = new HashMap<>();
        /*String deviceID = DeviceInfo.getDeviceId(activity);
        String deviceMAC = DeviceInfo.getDeviceMAC(activity);
        String deviceIDMEI = DeviceInfo.getImei(activity);
        String deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);*/

        map.put(General.IMEI, DeviceInfo.getDeviceId(activity));
        map.put(General.VERSION, DeviceInfo.getVersion());
        map.put(General.MODELNO, DeviceInfo.getDeviceName());

        refreshToken = new RefreshToken(_context);
        Request request = new Request.Builder()
                .url(url)
                .post(makeBodyNormalCall(map))
                .tag(tag)
                .build();

        String body = bodyToString(request);
        Log.e(General.MY_TEST_TAG, tag + " make: request body " + body);

        //Logger.debug(tag, url + "?" + body, _context);
        AppLog.i(General.MY_TEST_TAG, ""+url+"?"+body);
        String token = getToken(_context);
        Log.e(General.MY_TEST_TAG, tag + " make: request body " + body + "token " + token);



        if (token != null) {
            Log.e(General.MY_TEST_TAG, tag + " make: final body " + finalBody(body, token));
            return finalBody(body, token);
        }
        Log.i(General.MY_TEST_TAG, "make: is null called");
        return null;
    }

    // make call to fetch oauth token
    private synchronized static String getToken(Context _context) {
        String access_token = null;
        ModelToken token;
        OauthPreferences.initialize(_context);
        String i = OauthPreferences.get(Oauth.EXPIRES_AT);
        try {
            Log.i(TAG,General.MY_TEST_TAG+ "getToken : OauthPreferences.get(Oauth.EXPIRES_AT) "+OauthPreferences.get(Oauth.EXPIRES_AT));
            if (System.currentTimeMillis() >= Long.parseLong(OauthPreferences.get(Oauth.EXPIRES_AT))) {
                token = refreshToken.getRefreshToken(Preferences.get(Oauth.CLIENT_ID),
                        Preferences.get(Oauth.CLIENT_SECRET),
                        Preferences.get(General.DOMAIN).replaceAll(General.INSATNCE_NAME, ""),
                        _context);

                Log.e(TAG,General.MY_TEST_TAG+ "Token Response Status: " + token.getStatus());

                if (token.getStatus() == 1) {
                    access_token = token.getAccessToken();
                } else if (token.getStatus() == 2) {
                    Intent newintent = new Intent(_context, LoginActivity.class);
                    newintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    _context.startActivity(newintent);
                } else {
                    getToken(_context);
                }
            } else {
                access_token = OauthPreferences.get(Oauth.ACCESS_TOKEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return access_token;
    }

    // make final url with encrypted parameters and agent name
    private static RequestBody finalBody(String mainBody, String token) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder
                .add("akujs", UrlEncoder_.encrypt(mainBody))
                .add("d", "a")
                .add(Oauth.ACCESS_TOKEN, token);
        Log.i(TAG, "finalBody: " + formBuilder.build());
        return formBuilder.build();
    }


}
