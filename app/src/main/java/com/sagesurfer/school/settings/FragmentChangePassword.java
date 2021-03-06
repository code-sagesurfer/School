package com.sagesurfer.school.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sagesurfer.school.R;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.login.LoginActivity;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.sagesurfer.school.resources.oauth.OauthPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 31/05/2022
 * Last Modified on
 */
public class FragmentChangePassword extends Fragment implements View.OnClickListener{

    @BindView(R.id.et_current_password)
    EditText et_current_password;

    @BindView(R.id.et_new_password)
    EditText et_new_password;

    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @BindView(R.id.btn_change_password)
    Button btn_change_password;

    @BindView(R.id.btn_password_toggle1)
    ImageView btn_password_toggle1;

    @BindView(R.id.btn_password_toggle2)
    ImageView btn_password_toggle2;

    @BindView(R.id.btn_password_toggle3)
    ImageView btn_password_toggle3;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "FragmentChangePassword";

    private String mParam1;
    private String mParam2;
    MainActivity mainActivity;

    public FragmentChangePassword() {

    }

    public static FragmentChangePassword newInstance(String param1, String param2) {
        FragmentChangePassword fragment = new FragmentChangePassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            mainActivity.setToolbarTitleText(getString(R.string.change_password));
            mainActivity.toggleBellIcon(true);
            mainActivity.changeDrawerIcon(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        /*et_password.setTransformationMethod(null);
                if (!passwordShown) {
                    passwordShown = true;
                    et_password.setTransformationMethod(null);
                    btn_password_toggle.setImageResource(R.drawable.ic_password_unlock);

                } else {
                    passwordShown = false;
                    btn_password_toggle.setImageResource(R.drawable.ic_password);
                    et_password.setTransformationMethod(new PasswordTransformationMethod());
                }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);

        btn_password_toggle1.setOnClickListener(this);
        btn_password_toggle2.setOnClickListener(this);
        btn_password_toggle3.setOnClickListener(this);

        et_confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!et_new_password.getText().toString().trim().equals(et_confirm_password.getText().toString().trim())) {
                        et_confirm_password.setError(getString(R.string.password_not_matched));
                    }
                }
            }
        });

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current_password = et_current_password.getText().toString().trim();
                String newPassword = et_new_password.getText().toString().trim();
                String confirmPassword = et_confirm_password.getText().toString().trim();
                validateViews(current_password, newPassword, confirmPassword);

            }
        });
        return view;
    }

    private void validateViews(String current_password, String newPassword, String confirmPassword) {
        if (current_password.equals("")) {
            et_current_password.setError(getResources().getString(R.string.field_required));
        } else if (current_password.length() < 5) {
            et_current_password.setError(getResources().getString(R.string.min_five_characters));
        } else if (newPassword.equals("")) {
            et_new_password.setError(getResources().getString(R.string.field_required));
        } else if (newPassword.length() < 5) {
            et_confirm_password.setError(getResources().getString(R.string.min_five_characters));
        } else if (confirmPassword.equals("")) {
            et_confirm_password.setError(getResources().getString(R.string.field_required));
        } else if (confirmPassword.length() < 5) {
            et_confirm_password.setError(getResources().getString(R.string.min_five_characters));
        } else if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(mainActivity, "New password and confirm new password should match", Toast.LENGTH_SHORT).show();
        } else {
            changePassword();
        }
    }

    private void changePassword() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.RESET_PASSWORD);
        requestMap.put(General.OLD_PASSWORD, et_current_password.getText().toString().trim());
        requestMap.put(General.PASSWORD, et_new_password.getText().toString().trim());
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));

        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_USER_SETTING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());

        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().showProgressDialog(getActivity(), false, "");

                APIManager.Companion.getInstance().mobile_user_settings(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            JsonElement element = response.body();
                            Gson gson = new Gson();
                            Log.i(TAG, "onResponse:changePassword  " + response.body().toString());
                            ModelResetPasswordResponse resetPasswordResponse = gson.fromJson(response.body(), ModelResetPasswordResponse.class);
                            if (resetPasswordResponse.getResetPassword().getStatus()==1) {
                                Toast.makeText(getActivity(), "" + resetPasswordResponse.getResetPassword().getMsg(), Toast.LENGTH_SHORT).show();
                                OauthPreferences.clear();
                                Preferences.clear();

                                SharedPreferences loginPreferences = getActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
                                loginPrefsEditor.clear();
                                loginPrefsEditor.apply();

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getActivity(), "" + resetPasswordResponse.getResetPassword().getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                        APIManager.Companion.getInstance().dismissProgressDialog();
                    }
                });

                /*String response = NetworkCall_.post(url, requestBody, TAG, getContext(), getActivity());
                if (response != null) {
                    JsonObject jsonObject = GetJson_.getJson(response);
                    JsonObject jsonAddJournal = jsonObject.getAsJsonObject(Actions_.RESET_PASSWORD);
                    if (jsonAddJournal.get(General.STATUS).getAsInt() == 1) {
                        Toast.makeText(getContext(), jsonAddJournal.get(General.MSG).getAsString(), Toast.LENGTH_LONG).show();
                        //onBackPressed();
                        performLogoutTask.logout(getActivity());
                    } else {
                        Toast.makeText(getContext(), jsonAddJournal.get(General.ERROR).getAsString(), Toast.LENGTH_LONG).show();
                    }
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    boolean currentPasswordShown=false;
    boolean newPasswordShown=false;
    boolean newConfirmPasswordShown=false;
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_password_toggle1){
            //et_current_password.setTransformationMethod(null);
            if (!currentPasswordShown) {
                currentPasswordShown = true;
                et_current_password.setTransformationMethod(null);
                btn_password_toggle1.setImageResource(R.drawable.ic_password_unlock);

            } else {
                currentPasswordShown = false;
                btn_password_toggle1.setImageResource(R.drawable.ic_password);
                et_current_password.setTransformationMethod(new PasswordTransformationMethod());
            }
        }else if (view.getId()==R.id.btn_password_toggle2){
            ///et_new_password.setTransformationMethod(null);
            if (!newPasswordShown) {
                newPasswordShown = true;
                et_new_password.setTransformationMethod(null);
                btn_password_toggle2.setImageResource(R.drawable.ic_password_unlock);

            } else {
                newPasswordShown = false;
                btn_password_toggle2.setImageResource(R.drawable.ic_password);
                et_new_password.setTransformationMethod(new PasswordTransformationMethod());
            }
        }else if (view.getId()==R.id.btn_password_toggle3){
            //et_confirm_password.setTransformationMethod(null);
            if (!newConfirmPasswordShown) {
                newConfirmPasswordShown = true;
                et_confirm_password.setTransformationMethod(null);
                btn_password_toggle3.setImageResource(R.drawable.ic_password_unlock);

            } else {
                newConfirmPasswordShown = false;
                btn_password_toggle3.setImageResource(R.drawable.ic_password);
                et_confirm_password.setTransformationMethod(new PasswordTransformationMethod());
            }
        }
    }
}