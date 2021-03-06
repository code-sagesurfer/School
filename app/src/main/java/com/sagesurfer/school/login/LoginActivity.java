package com.sagesurfer.school.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sagesurfer.school.BuildConfig;
import com.sagesurfer.school.GetStartedActivity;
import com.sagesurfer.school.R;
import com.sagesurfer.school.login.forgotpassword.ActivityForgotPassword;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.Connectivity;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.DeviceInfo;
import com.sagesurfer.school.resources.GetTime;
import com.sagesurfer.school.resources.LoginValidator;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.APIInterface;
import com.sagesurfer.school.resources.apidata.LoginAPIManager;
import com.sagesurfer.school.resources.apidata.KeyMaker_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.sagesurfer.school.resources.apidata._Base64;
import com.sagesurfer.school.resources.callbacks.AuthorizationCallbacks;
import com.sagesurfer.school.resources.callbacks.TokenCallbacks;
import com.sagesurfer.school.resources.oauth.Authorize;
import com.sagesurfer.school.resources.oauth.Token;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AuthorizationCallbacks, TokenCallbacks {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.btn_login)
    TextView btn_login;

    @BindView(R.id.tv_forgot_pass)
    TextView tv_forgot_pass;

    @BindView(R.id.tv_register_link)
    TextView tv_register_link;

    @BindView(R.id.cb_remember_me)
    CheckBox cb_remember_me;

    @BindView(R.id.btn_password_toggle)
    ImageView btn_password_toggle;
    SharedPreferences loginSharedPreferences;
    SharedPreferences.Editor loginPrefEdit;
    private ArrayList<ModelInstancesData> serverCodeList;
    private SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences loginPreferences;
    boolean passwordShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        //Hiding keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        serverCodeList = new ArrayList<>();
        loginPreferences = getSharedPreferences("loginPrefs_school", MODE_PRIVATE);
        btn_password_toggle.setOnClickListener(this);
        //btn_login.setEnabled(false);
        if (!BuildConfig.DEBUG) {
        } else {
           /* et_code.setText("sage036");
            et_username.setText("rahuladult");
            et_password.setText("Sag&#2539!");*/
        }
        tv_forgot_pass.setOnClickListener(this);
        tv_register_link.setOnClickListener(this);
        btn_login.setOnClickListener(LoginActivity.this);
        try {
            getInstances();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginSharedPreferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE);

        if (loginPreferences.contains(General.USERNAME)){
            Log.i(TAG, "onCreate: entered in USERNAME ");
            et_username.setText(loginPreferences.getString(General.USERNAME,""));

        }

        //et_code.setText("daskdjakljsd");
        if (loginPreferences.contains("server_code")){
            Log.i(TAG, "onCreate: entered in LOGIN_CODE ");
            et_code.setText(loginPreferences.getString("server_code",""));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        showAnimation();
    }

    private void showAnimation() {
        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.et_username));

        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.et_password));
        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.et_code));
        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.btn_login));

        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.cb_remember_me));

        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.tv_forgot_pass));

        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.textView10));
        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.tv_register_link));
        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.tv_text_std_login));
        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .repeat(0)
                .playOn(findViewById(R.id.iv_sagesurfer_icon));
    }

    @OnClick
    public void getInstances() {
        if (Connectivity.isInternetAvailable(LoginActivity.this)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://design.sagesurfer.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            APIInterface retrofitAPI = retrofit.create(APIInterface.
                    class);
            HashMap<String, String> keyMap = KeyMaker_.getKey();
            RequestBody formBody = new FormBody.Builder()
                    .add(General.KEY, keyMap.get(General.KEY))
                    .add(General.TOKEN, keyMap.get(General.TOKEN))
                    .add(General.ACTION, Actions_.GET_INSTANCES)
                    .add(General.VERSION, General.INSTANCE_VERSION)
                    .build();

            RequestBody requestBody = null;
            try {
                requestBody = MakeCall.postGetInstances("https://design.sagesurfer.com/mobile_get_instances.php", formBody, TAG, this, this);
                Call<JsonElement> call = retrofitAPI.get_instances(requestBody);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        AppLog.i(TAG, "getInstances onResponse: " + response.body());
                        JsonElement mJsonElement = response.body();
                        Gson gson = new Gson();
                        ModelInstancesResponse modelInstancesResponse = gson.fromJson(mJsonElement.toString(), ModelInstancesResponse.class);
                        serverCodeList = modelInstancesResponse.getInstances();
                        if (serverCodeList != null) {
                            //btn_login.setEnabled(true);
                        } else {
                            Toast.makeText(LoginActivity.this, "" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            //  btn_login.setEnabled(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        AppLog.i(TAG, "getInstances onResponse: " + t.getMessage());
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                AppLog.i(TAG, " getInstances catch block " + e.getMessage());
            }
        } else {
            Toast.makeText(this, "Internet connection required", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String code = et_code.getText().toString().trim();
                Log.i(TAG, "onClick: "+code);


                if (isValid(username, password, code)) {
                    // Toast.makeText(this, "login now...", Toast.LENGTH_SHORT).show();
                    proceedToLoginUser();
                }
                break;

            case R.id.btn_password_toggle:

                et_password.setTransformationMethod(null);
                if (!passwordShown) {
                    passwordShown = true;
                    et_password.setTransformationMethod(null);
                    btn_password_toggle.setImageResource(R.drawable.ic_password_unlock);

                } else {
                    passwordShown = false;
                    btn_password_toggle.setImageResource(R.drawable.ic_password);
                    et_password.setTransformationMethod(new PasswordTransformationMethod());
                }
                break;

            case R.id.tv_register_link:
                startActivity(new Intent(LoginActivity.this, ActivityRegisterUser.class));
                break;

            case R.id.tv_forgot_pass:
                startActivity(new Intent(LoginActivity.this, ActivityForgotPassword.class));
                break;
        }

    }

    private void proceedToLoginUser() {
        final String user_name = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        String code = et_code.getText().toString();

        if (cb_remember_me.isChecked()) {
            loginPrefsEditor = loginPreferences.edit();
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", user_name);
            loginPrefsEditor.putString("server_code", code);
            loginPrefsEditor.commit();
        } else {
           /* loginPrefsEditor = loginPreferences.edit();
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();*/
        }

        //doLogin(user_name, password);
        loginUser(user_name, password);
    }

    //Checking if all fields are valid or not
    private boolean isValid(String user_name, String password, String code) {
        if (user_name.length() == 0) {
            et_username.setError(this.getResources().getString(R.string.please_enter_username));
            return false;
        } else if (!LoginValidator.isUsername(user_name)) {
            et_username.setError(this.getResources().getString(R.string.invalid_input));
            return false;
        }

        if (password.trim().length() == 0) {
            et_password.setError(this.getResources().getString(R.string.please_enter_password));
            return false;
        } else if (!LoginValidator.isPassword(password)) {
            et_password.setError(this.getResources().getString(R.string.invalid_input));
            return false;
        }

        if (code.trim().length() == 0) {
            et_code.setError(this.getResources().getString(R.string.please_enter_code));
            return false;
        } else if (!LoginValidator.isCode(code)) {
            et_code.setError(this.getResources().getString(R.string.invalid_input));
            return false;
        }

        if (code.equalsIgnoreCase("sage018") || code.equalsIgnoreCase("sage019")) {
            et_code.setError(this.getResources().getString(R.string.invalid_server_code));
            return false;
        }
        if (!checkCode(code)) {
            et_code.setError(this.getResources().getString(R.string.invalid_server_code));
            return false;
        }
        return true;
    }

    //Checking code validity and storing domain details in preferences
    private boolean checkCode(String code) {
        if (serverCodeList != null && serverCodeList.size() > 0) {
            for (ModelInstancesData server : serverCodeList) {
                if (server.getInstanceKey().equalsIgnoreCase(code)) {

                    Preferences.initialize(LoginActivity.this);
                    Preferences.save(General.DOMAIN, server.getInstanceUrl());//server.getDomainUrl());
                    Preferences.save(General.DOMAIN_CODE, code);

                    SharedPreferences domainUrlPref = getSharedPreferences("schoolDomainUrlPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = domainUrlPref.edit();
                    editor.putString(General.DOMAIN, "" + server.getInstanceUrl());
                    editor.apply();
                    Preferences.save(General.CHAT_URL, server.getCometchat());

                    AppLog.e(TAG, "DomainCodeBeforeSaving: " + code);
                    AppLog.e(TAG, "DomainURLMain: " + server.getInstanceUrl());
                    return true;
                }
            }
        } else {
            AppLog.e(TAG, "empty or null code array");
        }
        return false;
    }

    private void loginUser(String user_name, String password) {
        try {

            HashMap<String, String> keyMap = KeyMaker_.getKey();
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put(General.KEY, keyMap.get(General.KEY));
            requestMap.put(General.TOKEN, keyMap.get(General.TOKEN));
            requestMap.put(General.DEVICE, "a");
            requestMap.put(General.INFO, _Base64.encode(DeviceInfo.get(LoginActivity.this)));
            requestMap.put(General.UID, DeviceInfo.getDeviceId(LoginActivity.this));
            requestMap.put(General.USERNAME, "" + user_name);
            requestMap.put(General.PASSWORD, "" + password);
            requestMap.put(General.START_TIME, "" + GetTime.getChatTimestamp());
            requestMap.put(General.END_TIME, "" + GetTime.getChatTimestamp());
            requestMap.put(General.IP, "" + DeviceInfo.getDeviceMAC(LoginActivity.this));
            requestMap.put(General.DOMAIN_CODE, "" + Preferences.get(General.DOMAIN_CODE));
            requestMap.put(General.VERSION, LoginActivity.this.getPackageManager().getPackageInfo(LoginActivity.this.getPackageName(), 0).versionName);
            requestMap.put(General.COUNTRY, "" + LoginActivity.this.getResources().getConfiguration().locale.getCountry());
            requestMap.put(General.CITY, "");
            requestMap.put(General.TIMEZONE, DeviceInfo.getTimeZone());


            String url = Preferences.get(General.DOMAIN) + "/" + Urls_.LOGIN_URL;

            RequestBody requestBody = MakeCall.makeLoginCall(requestMap, url, TAG, this, this);

            APIManager.Companion.getInstance().showProgressDialog(LoginActivity.this, true, "Loading....");
            APIInterface apiInterface = LoginAPIManager.getClient().create(APIInterface.class);

            Call<JsonElement> jsonElementCall = apiInterface.doGetListResources(requestBody);
            jsonElementCall.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    AppLog.e(TAG, "onResponse: " + response.toString());
                    JsonElement mJsonElement = response.body();
                    Gson gson = new Gson();
                    ModelMainResponse mRewardsCategoryResponseModel = gson.fromJson(String.valueOf(response.body()), ModelMainResponse.class);
                    APIManager.Companion.getInstance().dismissProgressDialog();

                    if (mRewardsCategoryResponseModel.getDetails().getStatus() == 1) {
                        Preferences.save(General.USER_ID, mRewardsCategoryResponseModel.getDetails().getUserid());
                        Preferences.save(General.CLIENT_ID, mRewardsCategoryResponseModel.getDetails().getClientId());
                        Preferences.save(General.CLIENT_SECRET, mRewardsCategoryResponseModel.getDetails().getClientSecret());
                        Preferences.save(General.TIMEZONE, DeviceInfo.getTimeZone());
                        Preferences.save(General.TIMEZONE_SERVER, mRewardsCategoryResponseModel.getDetails().getUserTimezone());
                        Preferences.save(General.COUNTRY_ID, mRewardsCategoryResponseModel.getDetails().getCountry());
                        Preferences.save(General.STATE_ID, mRewardsCategoryResponseModel.getDetails().getState());
                        Preferences.save(General.CITY, mRewardsCategoryResponseModel.getDetails().getCity());
                        Preferences.save(General.FIRST_NAME, mRewardsCategoryResponseModel.getDetails().getFirstname());
                        Preferences.save(General.LAST_NAME, mRewardsCategoryResponseModel.getDetails().getLastname());
                        Preferences.save(General.USERNAME, mRewardsCategoryResponseModel.getDetails().getUsername());
                        Preferences.save(General.EMAIL, mRewardsCategoryResponseModel.getDetails().getEmail());
                        Preferences.save(General.NAME, mRewardsCategoryResponseModel.getDetails().getName());
                        Preferences.save(General.BIRTDATE, mRewardsCategoryResponseModel.getDetails().getDob());
                        Preferences.save(General.ROLE, mRewardsCategoryResponseModel.getDetails().getRole());
                        Preferences.save(General.ROLE_ID, mRewardsCategoryResponseModel.getDetails().getRoleId());
                        Preferences.save(General.GENDER, mRewardsCategoryResponseModel.getDetails().getGender());
                        Preferences.save(General.IMAGE, mRewardsCategoryResponseModel.getDetails().getImage());
                        //  Preferences.save(Constants.DOMAIN_CODE, Preferences.get(Constants.DOMAIN_CODE));


                        AppLog.i(TAG, "onResponse: url " + Preferences.get(General.DOMAIN));

                        if (mRewardsCategoryResponseModel.getDetails().getUserid() != null) {
                            Authorize authorize = new Authorize(LoginActivity.this);
                        /*authorize.authorizeUserForApp(mRewardsCategoryResponseModel.getDetails().getClientId(),
                                mRewardsCategoryResponseModel.getDetails().getClientSecret(),
                                Preferences.get(Constants.DOMAIN).replaceAll(Constants.INSATNCE_NAME, ""),
                                getApplicationContext(),LoginActivity.this);*/
                            authorize.getAuthorized(mRewardsCategoryResponseModel.getDetails().getClientId(),
                                    mRewardsCategoryResponseModel.getDetails().getClientSecret(),
                                    Preferences.get(General.DOMAIN).replaceAll(General.INSATNCE_NAME, ""),
                                    getApplicationContext());
                            Toast.makeText(LoginActivity.this, "Account logged in successfully", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Username and password are invalid", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    AppLog.e(TAG, "onResponse: " + t.getMessage());
                    APIManager.Companion.getInstance().dismissProgressDialog();
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            AppLog.e(TAG, "onResponse: " + e.getMessage());
            APIManager.Companion.getInstance().dismissProgressDialog();
        }
    }

    //AuthorizationCallbacks overridden methods after getting authorized
    @Override
    public void authorizationSuccessCallback(JSONObject jsonObject) {
        AppLog.d(TAG, "authorizationSuccessCallback: ");
        Token token = new Token(this);
        token.getToken(Preferences.get(General.CLIENT_ID),
                Preferences.get(General.CLIENT_SECRET),
                Preferences.get(General.DOMAIN).replaceAll(General.INSATNCE_NAME, ""), this);
    }

    @Override
    public void authorizationFailCallback(JSONObject jsonObject) {
        AppLog.d(TAG, "authorizationFailCallback: ");
    }
    //AuthorizationCallbacks overridden methods after getting authorized end


    //TokenCallbacks overridden methods after getting token after authorization
    @Override
    public void tokenSuccessCallback(JSONObject jsonObject) {
        AppLog.d(TAG, "tokenSuccessCallback: ");
        Intent intent = new Intent(LoginActivity.this, GetStartedActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void tokenFailCallback(JSONObject jsonObject) {
        AppLog.d(TAG, "tokenFailCallback: ");
    }
}


//TokenCallbacks overridden methods after getting token after authorization end}