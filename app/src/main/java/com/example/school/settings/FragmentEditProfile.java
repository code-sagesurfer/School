package com.example.school.settings;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.main.MainActivity;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.General;
import com.example.school.resources.GetCounters;
import com.example.school.resources.GetTime;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.Utils;
import com.example.school.resources.apidata.MakeCall;
import com.example.school.resources.showstatus.ShowToast;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 31/05/2022
 * Last Modified on
 */
public class FragmentEditProfile extends AppCompatActivity implements View.OnClickListener, InterfaceSaveEditedData {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "FragmentEditProfile";
    private int sYear, sMonth, sDay;
    private ArrayList<Student_> cityArrayList = new ArrayList<>(), countryArrayList = new ArrayList<>();
    ArrayList<GetState> stateArrayList = new ArrayList<>();
    private int mYear = 0, mMonth = 0, mDay = 0;
    private MainActivity mainActivity;
    static int stateId = 0;
    static int cityId = 0;
    @BindView(R.id.et_fname)
    EditText et_fname;

    @BindView(R.id.et_lname)
    EditText et_lname;

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.et_dob)
    EditText et_dob;

    @BindView(R.id.sp_city)
    Spinner sp_city;

    @BindView(R.id.sp_state)
    Spinner sp_state;

    @BindView(R.id.sp_country)
    Spinner sp_country;

    @BindView(R.id.iv_save_data)
    TextView iv_save_data;

    @BindView(R.id.tv_header_uname)
    TextView tv_header_uname;

    @BindView(R.id.tv_header_role)
    TextView tv_header_role;

    @BindView(R.id.iv_user_profile)
    CircleImageView iv_user_profile;

    @BindView(R.id.iv_save_img)
    ImageView iv_save_img;
    @BindView(R.id.iv_backButton)
    ImageView iv_backButton;

    @BindView(R.id.iv_save_profile)
    TextView iv_save_profile;

    StatesFetch statesFetch;
    CityFetch cityFetch;
    String dateOfBirth;
    boolean showMsg = true;
    DatePickerDialog.OnDateSetListener startDateDatePicker;
    final Calendar myCalendar = Calendar.getInstance();
    /*public static FragmentEditProfile newInstance(String param1, String param2) {
        FragmentEditProfile fragment = new FragmentEditProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        ButterKnife.bind(this);
        statesFetch = new StatesFetch();
        cityFetch = new CityFetch();
        sp_country.setOnItemSelectedListener(onCountrySelected);
        sp_state.setOnItemSelectedListener(onStateSelected);
        sp_city.setOnItemSelectedListener(onCitySelected);


        et_lname.setText(Preferences.get(General.LAST_NAME));
        et_fname.setText(Preferences.get(General.FIRST_NAME));
        et_email.setText(Preferences.get(General.EMAIL));
        et_username.setText(Preferences.get(General.USERNAME));
        et_dob.setText(Utils.convertDateStringToString(Preferences.get(General.BIRTDATE),"yyyy-MM-dd","MM-dd-yyyy"));
        tv_header_uname.setText(Preferences.get(General.NAME));
        tv_header_role.setText(Preferences.get(General.ROLE));
        dateOfBirth = Preferences.get(General.BIRTDATE);

        iv_save_data.setOnClickListener(this);
        iv_backButton.setOnClickListener(this);
        et_dob.setOnClickListener(this);
        iv_save_img.setOnClickListener(this);
        iv_save_profile.setOnClickListener(this);
        if (Preferences.get(General.IMAGE) != null && Preferences.get(General.IMAGE).length() != 0) {
            Glide.with(this)
                    .load(Preferences.get(General.IMAGE))
                    .placeholder(R.drawable.ic_user_male)
                    .error(R.drawable.ic_user_male)
                    .into(iv_user_profile);
        }

        startDateDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                setDate();
            }
        };
    }

    private void setDate() {
        String myFormatSendServer = "yyyy-MM-dd";
        String myFormatToshow = "MM-dd-yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormatSendServer, Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormatToshow, Locale.US);

        et_dob.setText(sdf2.format(myCalendar.getTime()));
        dateOfBirth=sdf.format(myCalendar.getTime());
    }



/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);
        statesFetch = new StatesFetch();
        cityFetch = new CityFetch();
        sp_country.setOnItemSelectedListener(onCountrySelected);
        sp_state.setOnItemSelectedListener(onStateSelected);
        sp_city.setOnItemSelectedListener(onCitySelected);
        return view;
    }*/

/*    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }*/

    @Override
    public void onResume() {
        super.onResume();
        loadCountryListNew();
       /* if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
            mainActivity.setToolbarTitleText("Edit Profile Setting");
            mainActivity.changeDrawerIcon(true);
            mainActivity.toogleSaveButton(true);
            mainActivity.toggleBellIcon(true);
        }*/
    }

    private final AdapterView.OnItemSelectedListener onCountrySelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            sp_country.setSelection(position);
            showMsg = true;
            loadStateList(countryArrayList.get(sp_country.getSelectedItemPosition()).getId(), TAG, FragmentEditProfile.this, FragmentEditProfile.this);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private final AdapterView.OnItemSelectedListener onStateSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            sp_state.setSelection(position);
            showMsg = true;
            Log.i(TAG, "onItemSelected: " + position);
            loadCityList(stateArrayList.get(sp_state.getSelectedItemPosition()).getId(), FragmentEditProfile.this, FragmentEditProfile.this);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private final AdapterView.OnItemSelectedListener onCitySelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            sp_city.setSelection(position);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    public void loadStateList(int counrtyId, String Tag, Context context, Activity activity) {

        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GET_STATE);
        requestMap.put(General.COUNTRY_ID, String.valueOf(counrtyId));
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.SELF_CARE_URL;
        RequestBody requestBody = MakeCall.make(requestMap, url, Tag, context, activity);
        if (requestBody != null) {

            APIManager.Companion.getInstance().showProgressDialog(activity, false, "Loading...");

            APIManager.Companion.getInstance().mobile_self_care(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        if (response!=null){
                            JsonElement element = response.body();
                            Gson gson = new Gson();

                            Log.i(Tag, "onResponse: loadStateList "+response.body().toString());
                            ModelStatesResponse modelStatesResponse = gson.fromJson(response.body(), ModelStatesResponse.class);
                            stateArrayList = new ArrayList<>();
                            stateArrayList=modelStatesResponse.getGetState();
                            /*if (fragment instanceof FragmentEditProfile){
                                FragmentEditProfile editProfile=(FragmentEditProfile) fragment;
                                editProfile.setStateData(stateArrayList);
                            }*/
                            setStateData(stateArrayList);
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
           /* try {
                String response = MakeCall.post(url, requestBody, Tag, context, activity);
                hideDialog();
                if (response != null) {
                    stateArrayList = new ArrayList<>();
                    stateArrayList.addAll(CaseloadParser_.parseStudentList(response, Actions_.GET_STATE, getContext(), TAG));

                    if (stateArrayList.get(0).getStatus() == 1) {
                        ArrayList<String> stateNameList = new ArrayList<String>();
                        for (int i = 0; i < stateArrayList.size(); i++) {
                            stateNameList.add(stateArrayList.get(i).getName());
                        }

                        if (stateNameList.size() > 0) {
                            ArrayAdapter<String> adapterConsumer = new ArrayAdapter<String>(getContext(), R.layout.drop_down_selected_text_item_layout, stateNameList);
                            adapterConsumer.setDropDownViewResource(R.layout.drop_down_text_item_layout);
                            spinnerStateList.setAdapter(adapterConsumer);

                            for (int i = 0; i < stateArrayList.size(); i++) {
                                if (Integer.parseInt(Preferences.get(General.STATE_ID)) == stateArrayList.get(i).getId()) {
                                    //for default selection of state
                                    spinnerStateList.setSelection(i);
                                    break;
                                }
                            }

                            spinnerStateListOne.setVisibility(View.GONE);
                            spinnerStateList.setVisibility(View.VISIBLE);
                            spinnerCityListOne.setVisibility(View.GONE);
                            spinnerCityList.setVisibility(View.VISIBLE);
                        }

                    } else {
                        spinnerStateListOne.setVisibility(View.VISIBLE);
                        spinnerStateList.setVisibility(View.GONE);
                        spinnerCityListOne.setVisibility(View.VISIBLE);
                        spinnerCityList.setVisibility(View.GONE);

                        if (showMsg) {
                            stateArrayList.clear();
                            Toast.makeText(getContext(), "State: No Data", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }

    public void loadCityList(int stateId, Context context, Activity activity) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GET_CITY);
        requestMap.put(General.STATE_ID, String.valueOf(stateId));
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.SELF_CARE_URL;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, context, activity);
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().showProgressDialog(activity, false, "Loading...");

                APIManager.Companion.getInstance().mobile_self_care(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            if (response!=null){
                                JsonElement element = response.body();
                                Gson gson = new Gson();
                                Log.i(TAG, "onResponse: loadStateList "+response.body().toString());
                                ModelStatesResponse modelStatesResponse = gson.fromJson(response.body(), ModelStatesResponse.class);
                                /*if (fragment instanceof FragmentEditProfile){
                                    FragmentEditProfile editProfile=(FragmentEditProfile) fragment;

                                    editProfile.setCityData(modelStatesResponse.getGet_city());
                                }*/
                              setCityData(modelStatesResponse.getGet_city());


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


                /*String response = NetworkCall_.post(url, requestBody, TAG, context, activity);
                hideDialog();
                if (response != null) {
                    cityArrayList = new ArrayList<>();
                    cityArrayList.addAll(CaseloadParser_.parseStudentList(response, Actions_.GET_CITY, getContext(), TAG));

                    if (cityArrayList.get(0).getStatus() == 1) {

                        ArrayList<String> cityNameList = new ArrayList<String>();
                        for (int i = 0; i < cityArrayList.size(); i++) {
                            cityNameList.add(cityArrayList.get(i).getName());
                        }

                        if (cityNameList.size() > 0) {
                            ArrayAdapter<String> adapterConsumer = new ArrayAdapter<String>(getContext(), R.layout.drop_down_selected_text_item_layout, cityNameList);
                            adapterConsumer.setDropDownViewResource(R.layout.drop_down_text_item_layout);
                            spinnerCityList.setAdapter(adapterConsumer);

                            for (int i = 0; i < cityArrayList.size(); i++) {
                                if (Integer.parseInt(Preferences.get(General.CITY_ID)) == cityArrayList.get(i).getId()) {
                                    //for default selection of state
                                    spinnerCityList.setSelection(i);
                                    break;
                                }
                            }
                            spinnerCityListOne.setVisibility(View.GONE);
                            spinnerCityList.setVisibility(View.VISIBLE);
                        }

                    } else {
                        spinnerCityListOne.setVisibility(View.VISIBLE);
                        spinnerCityList.setVisibility(View.GONE);
                        if (showMsg) {
                            cityArrayList.clear();
                            Toast.makeText(getContext(), "City : No Data", Toast.LENGTH_LONG).show();
                        }
                    }
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCountryListNew() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GET_COUNTRY);
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.SELF_CARE_URL;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, FragmentEditProfile.this, FragmentEditProfile.this);
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().showProgressDialog(FragmentEditProfile.this, false, "Loading...");

                APIManager.Companion.getInstance().mobile_self_care(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            JsonElement element = response.body();
                            Gson gson = new Gson();
                            ModelCountryResponse mGetProfileResponse = gson.fromJson(response.body(), ModelCountryResponse.class);
                            countryArrayList = new ArrayList<>();
                            //countryArrayList.addAll(CaseloadParser_.parseStudentList(response, Actions_.GET_COUNTRY, getContext(), TAG));
                            countryArrayList = mGetProfileResponse.getData_list_country();
                            if (countryArrayList.get(0).getStatus() == 1) {
                                ArrayList<String> countryNameList = new ArrayList<String>();
                                for (int i = 0; i < countryArrayList.size(); i++) {
                                    countryNameList.add(countryArrayList.get(i).getName());
                                }

                                if (countryNameList.size() > 0) {
                                    ArrayAdapter<String> adapterConsumer = new ArrayAdapter<String>(FragmentEditProfile.this, R.layout.drop_down_selected_text_item_layout_setting, countryNameList);
                                    adapterConsumer.setDropDownViewResource(R.layout.drop_down_text_item_layout);
                                    sp_country.setAdapter(adapterConsumer);

                                    for (int i = 0; i < countryArrayList.size(); i++) {
                                        if (Integer.parseInt(Preferences.get(General.COUNTRY_ID)) == countryArrayList.get(i).getId()) {
                                            //for default selection of country
                                            sp_country.setSelection(i);
                                            break;
                                        }
                                    }
                                }
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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void setStateData(ArrayList<GetState> stateArrayList) {
        this.stateArrayList = stateArrayList;
        if (stateArrayList.get(0).getStatus().equals("1")) {
            ArrayList<String> stateNameList = new ArrayList<String>();
            for (int i = 0; i < stateArrayList.size(); i++) {
                stateNameList.add(stateArrayList.get(i).getName());
            }
//drop_down_selected_text_item_layout_setting, drop_down_text_item_layout
            if (stateNameList.size() > 0) {
                ArrayAdapter<String> adapterConsumer = new ArrayAdapter<String>(FragmentEditProfile.this, R.layout.drop_down_selected_text_item_layout_setting, stateNameList);
                adapterConsumer.setDropDownViewResource(R.layout.drop_down_text_item_layout);
                sp_state.setAdapter(adapterConsumer);

                for (int i = 0; i < stateArrayList.size(); i++) {
                    if (Integer.parseInt(Preferences.get(General.STATE_ID)) == stateArrayList.get(i).getId()) {
                        //for default selection of state
                        sp_state.setSelection(i);
                        break;
                    }
                }
            }
        } else {
            if (showMsg) {
                stateArrayList.clear();
                Toast.makeText(FragmentEditProfile.this, "State: No Data", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_save_img || view.getId() == R.id.iv_save_profile ) {
            if (validateViews()){
                saveUpdatedProfile();
            }
        } else if (view.getId() == R.id.et_dob) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentEditProfile.this, startDateDatePicker,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();

            /*DatePickerDialog datePicker = new DatePickerDialog(FragmentEditProfile.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            monthOfYear = (monthOfYear + 1);
                            sDay = dayOfMonth;
                            sMonth = monthOfYear;
                            sYear = year;

                            dateOfBirth = sYear + "-" + GetCounters.checkDigit(sMonth) + "-" + GetCounters.checkDigit(sDay);
                            try {
                                Log.i(TAG, "onDateSet: day " + sDay);
                                Log.i(TAG, "onDateSet: sMonth " + sMonth);
                                Log.i(TAG, "onDateSet: sYear " + sYear);
                                Log.i(TAG, "onDateSet: date_of_birth " + dateOfBirth);
                                int result = compareDate(mYear + "-" + (mMonth + 1) + "-" + mDay, dateOfBirth);
                                if (result == 1) {
                                    et_dob.setText(GetTime.yy_mm_dd(dateOfBirth));
                                    //Preferences.save(General.BIRTDATE, GetTime.yy_mm_dd(date_of_birth));
                                } else {
                                    dateOfBirth = null;
                                    et_dob.setText("");
                                    ShowToast.toast("Invalid Date of Birth", FragmentEditProfile.this);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, mYear, mMonth, mDay);



            datePicker.show();*/
        }else if (view.getId()==R.id.iv_backButton){
            onBackPressed();
        }
    }

    private boolean validateViews() {
        if (et_fname.getText().toString().trim().length()<=0){
            et_fname.setError(getResources().getString(R.string.field_required));
            return false;
        }else if (et_lname.getText().toString().trim().length()<=0){
            et_lname.setError(getResources().getString(R.string.field_required));
            return false;
        }else {
            return true;
        }
    }

    private void saveUpdatedProfile() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.EDIT_PROFILE);
        requestMap.put(General.DOB, et_dob.getText().toString().trim());
        requestMap.put(General.FIRST_NAME, et_fname.getText().toString().trim());
        requestMap.put(General.LAST_NAME, et_lname.getText().toString().trim());
        int posCounrty = sp_country.getSelectedItemPosition();
        int counrtyId = countryArrayList.get(posCounrty).getId();
        requestMap.put(General.COUNTRY, String.valueOf(counrtyId));

        if (stateArrayList.size() == 0) {
            requestMap.put(General.STATE, String.valueOf(stateId));
        } else {
            int posState = sp_state.getSelectedItemPosition();
            stateId = stateArrayList.get(posState).getId();
            requestMap.put(General.STATE, String.valueOf(stateId));
        }

        if (cityArrayList.size() == 0) {
            requestMap.put(General.CITY, String.valueOf(cityId));
        } else {
            int posCity = sp_city.getSelectedItemPosition();
            cityId = cityArrayList.get(posCity).getId();
            requestMap.put(General.CITY, String.valueOf(cityId));
        }
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));



        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_USER_SETTING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, FragmentEditProfile.this, FragmentEditProfile.this);
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().showProgressDialog(FragmentEditProfile.this, false, "Updating profile...");

                APIManager.Companion.getInstance().mobile_user_settings(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            if (response != null) {
                                JsonElement element = response.body();
                                Gson gson = new Gson();

                                Log.i(TAG, "onResponse: saveUpdatedProfile " + response.body().toString());
                                ModelUpdateProfileResponse modelStatesResponse = gson.fromJson(response.body(), ModelUpdateProfileResponse.class);
                                {
                                    if (modelStatesResponse.getEditProfile().getStatus() == 1) {
                                        Toast.makeText(FragmentEditProfile.this, "" + modelStatesResponse.getEditProfile().getMsg(), Toast.LENGTH_SHORT).show();
                                        Preferences.save(General.FIRST_NAME, "" + et_fname.getText().toString().trim());

                                        Preferences.save(General.LAST_NAME, "" + et_lname.getText().toString().trim());
                                        Preferences.save(General.USERNAME, "" + et_username.getText().toString().trim());
                                        Preferences.save(General.COUNTRY_ID, counrtyId);
                                        Preferences.save(General.EMAIL, "" + et_email.getText().toString().trim());
                                        Preferences.save(General.STATE_ID, stateId);
                                        Preferences.save(General.CITY_ID, cityId);
                                        Preferences.save(General.BIRTDATE, dateOfBirth);

                                        //Preferences.save(General.BIRTDATE, et_dob.getText().toString().trim());
                                        Preferences.save(General.NAME, "" + et_fname.getText().toString().trim() + " " +
                                                et_lname.getText().toString().trim());
                                    } else {
                                        Toast.makeText(mainActivity, "" + modelStatesResponse.getEditProfile().getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }

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

               /* String response = NetworkCall_.post(url, requestBody, TAG, getContext(), getActivity());
                if (response != null) {
                    JsonObject jsonObject = GetJson_.getJson(response);
                    JsonObject jsonAddJournal = jsonObject.getAsJsonObject(Actions_.EDIT_PROFILE);
                    if (jsonAddJournal.get(General.STATUS).getAsInt() == 1) {
                        Toast.makeText(getContext(), jsonAddJournal.get(General.MSG).getAsString(), Toast.LENGTH_LONG).show();
                        onBackPressed();
                        Preferences.save(General.FIRST_NAME,""+et_firstname.getText().toString().trim());
                        Preferences.save(General.LAST_NAME,""+et_lastname.getText().toString().trim());
                        Preferences.save(General.LAST_NAME,""+et_lastname.getText().toString().trim());
                        Preferences.save(General.USERNAME,""+et_username.getText().toString().trim());
                        Preferences.save(General.LAST_NAME,""+et_lastname.getText().toString().trim());
                        Preferences.save(General.COUNTRY_ID, counrtyId);
                        Preferences.save(General.EMAIL, ""+et_email.getText().toString().trim());
                        Preferences.save(General.STATE_ID, stateId);
                        Preferences.save(General.CITY_ID, cityId);
                        //Preferences.save(General.BIRTDATE, GetTime.yy_mm_dd(date_of_birth));
                        Log.i(TAG, "submitUserSetting: BIRTDATE "+date_of_birth);
                        Preferences.save(General.BIRTDATE, date_of_birth);
                        Preferences.save(General.NAME,""+et_firstname.getText().toString().trim()+" "+
                                et_lastname.getText().toString().trim());

                        mainActivity.setDrawerUserName(et_firstname.getText().toString().trim()+" "+
                                et_lastname.getText().toString().trim());

                    } else {
                        Toast.makeText(getContext(),
                                jsonAddJournal.get(General.ERROR).getAsString(),
                                Toast.LENGTH_LONG).show();
                    }
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setCityData(ArrayList<GetState> cityArrayList) {
        Log.i(TAG, "setCityData: " + cityArrayList.size());
        if (cityArrayList.get(0).getStatus().equals("1")) {

            ArrayList<String> cityNameList = new ArrayList<String>();
            for (int i = 0; i < cityArrayList.size(); i++) {
                cityNameList.add(cityArrayList.get(i).getName());
            }

            if (cityNameList.size() > 0) {
                ArrayAdapter<String> adapterConsumer = new ArrayAdapter<String>(FragmentEditProfile.this, R.layout.drop_down_selected_text_item_layout_setting, cityNameList);
                adapterConsumer.setDropDownViewResource(R.layout.drop_down_text_item_layout);
                sp_city.setAdapter(adapterConsumer);
//drop_down_selected_text_item_layout_setting, drop_down_text_item_layout
                for (int i = 0; i < cityArrayList.size(); i++) {
                    if (Integer.parseInt(Preferences.get(General.CITY_ID)) == cityArrayList.get(i).getId()) {
                        //for default selection of state
                        sp_city.setSelection(i);
                        break;
                    }
                }

            }
        } else {
            if (showMsg) {
                cityArrayList.clear();
                Toast.makeText(FragmentEditProfile.this, "City : No Data", Toast.LENGTH_LONG).show();
            }
        }
    }

    private int compareDate(String today, String selected_date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        Date date1 = dateFormat.parse(today);
        Date date2 = dateFormat.parse(selected_date);

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        return calendar1.compareTo(calendar2);
    }

    @Override
    public void saveEditedData() {
        saveUpdatedProfile();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}