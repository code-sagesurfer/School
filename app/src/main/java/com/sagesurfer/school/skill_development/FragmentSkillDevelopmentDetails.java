package com.sagesurfer.school.skill_development;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sagesurfer.school.R;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.GetTime;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSkillDevelopmentDetails extends Fragment implements View.OnClickListener {

    @BindView(R.id.tv_question_desc)
    TextView tv_question_desc;

    @BindView(R.id.goal_desc)
    TextView goal_desc;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_input_date)
    TextView tv_input_date;

    @BindView(R.id.btn_submit)
    TextView btn_submit;

    @BindView(R.id.btn_yes)
    Button btn_yes;

    @BindView(R.id.btn_no)
    Button btn_no;

    @BindView(R.id.ll_buttons)
    LinearLayout ll_buttons;

    @BindView(R.id.btn_cancel_gratitude)
    Button btn_cancel_gratitude;

    DatePickerDialog.OnDateSetListener startDateDatePicker;
    final Calendar myCalendar = Calendar.getInstance();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Goal_ goal_;
    private String mParam1;
    private String mParam2;
    MainActivity mainActivity;
    boolean yesButtonSelected;
    boolean isButtonClicked = false;
    String SelectedDate;
    private static final String TAG = "FragmentSkillDevelopmen";

    public FragmentSkillDevelopmentDetails() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }

    public static FragmentSkillDevelopmentDetails newInstance(String param1, String param2) {
        FragmentSkillDevelopmentDetails fragment = new FragmentSkillDevelopmentDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(General.GOAL_OBJ)) {
                goal_ = (Goal_) getArguments().getSerializable(General.GOAL_OBJ);
                Log.d(TAG, "onCreate: " + goal_.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skill_development_details, container, false);
        ButterKnife.bind(this, view);

        tv_question_desc.setText(goal_.getName());
        tv_date.setText(GetTime.month_DdYyyy(goal_.getStart_date()) + " to " + GetTime.month_DdYyyy(goal_.getEnd_date()));
        goal_desc.setText(goal_.getDescription());

        if (goal_.getToday_status() == 1) {
            //Input needed
            ll_buttons.setVisibility(View.VISIBLE);
        } else if (goal_.getToday_status() == 2) {
            //Input provided
            ll_buttons.setVisibility(View.GONE);
        } else {
            //completed
            ll_buttons.setVisibility(View.GONE);

        }

        btn_cancel_gratitude.setOnClickListener(this);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_yes.setBackground(getResources().getDrawable(R.drawable.rounded_green_button, null));
                btn_no.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_white_button_with_border, null));
                btn_yes.setTextColor(getContext().getResources().getColor(R.color.white));
                btn_no.setTextColor(getContext().getResources().getColor(R.color.black));
                yesButtonSelected = true;
                isButtonClicked = true;
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_no.setBackground(getResources().getDrawable(R.drawable.rounded_green_button));
                btn_yes.setBackground(getResources().getDrawable(R.drawable.rounded_white_button_with_border));
                btn_yes.setTextColor(getContext().getResources().getColor(R.color.black));
                btn_no.setTextColor(getContext().getResources().getColor(R.color.white));
                yesButtonSelected = false;
                isButtonClicked = true;
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_date.getText().toString().equalsIgnoreCase("Select Date")) {
                    //tv_date.setError(getString(R.string.field_required));
                    Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
                } else if (!isButtonClicked) {
                    Toast.makeText(getContext(), "Please provide input", Toast.LENGTH_SHORT).show();
                } else {
                    addCount("1", tv_date.getText().toString().trim());
                }
            }
        });

        startDateDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                setDate("start");
            }
        };

      /*  tv_input_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });*/

        tv_input_date.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_gratitude:
                getActivity().onBackPressed();
                break;

            case R.id.tv_input_date:
                openDatePicker();
                break;
        }
    }

    private void setDate(String view) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String myFormat2 = "MM-dd-yyyy";
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

       /* String myFormatToSendServer = "dd-MM-yyyy";
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormatToSendServer, Locale.US);*/


        SelectedDate = sdf.format(myCalendar.getTime());
        tv_input_date.setText(sdf2.format(myCalendar.getTime()));

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity) {
            mainActivity = (MainActivity) getContext();
            mainActivity.setToolbarTitleText("Goal details");
            mainActivity.changeDrawerIcon(true);
        }
    }

    private void openDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), startDateDatePicker,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }


    // Api Called For Add input Count
    private void addCount(String count, String date) {
        String time = ""; //convert time 12 hrs format to 24 hrs format
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss a");
            time = tv_date.getText().toString().trim();


        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.ADD_COUNT);
        requestMap.put(General.TIMEZONE, Preferences.get(General.TIMEZONE));
        requestMap.put(General.ID, String.valueOf(goal_.getId()));

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
        time = sdf.format(dt);
        requestMap.put("on_date", date);
        requestMap.put("on_time", time);
        requestMap.put("answer", count);

        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_SELF_GOAL;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());
        if (requestBody != null) {
            try {

                APIManager.Companion.getInstance().mobile_self_goal(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            JsonElement element = response.body();
                            Log.d(TAG, "onResponse: " + response.body().toString());

                            Gson gson = new Gson();
                            ModelInputProvidingResponse modelInputProvidingResponse = gson.fromJson(element.toString(), ModelInputProvidingResponse.class);
                            ArrayList<ModelCount> modelCounterArrayList = modelInputProvidingResponse.getAddCount();
                            if (modelInputProvidingResponse.getAddCount().get(0).getStatus() == 1) {
                                Toast.makeText(mainActivity, "" + modelCounterArrayList.get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            } else {
                                Toast.makeText(mainActivity, "" + modelCounterArrayList.get(0).getMsg(), Toast.LENGTH_SHORT).show();
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

                /*response = NetworkCall_.post(url, requestBody, TAG, SageSelfGoalDetailActivity.this, this);
                Log.e("AddCountResponse", response);
                if (response != null && !response.equalsIgnoreCase("13")) {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has(Actions_.ADD_COUNT)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Actions_.ADD_COUNT);
                        JSONObject object = jsonArray.getJSONObject(0);
                        if (object.has(General.ERROR)) {
//                            error = object.getString(General.ERROR);
                            Log.e(TAG, "jsonError: " + object.getString(General.ERROR));
                            Toast.makeText(this, object.getString(General.ERROR), Toast.LENGTH_SHORT).show();
                        }
                        if (object.has(General.ID)) {
                            id = object.getString(General.ID);
                            CountListModel_ model = new CountListModel_();
                            model.setId(id);
                            model.setCount(count);
                            model.setOnDate(date);
                            model.setOnTime(time);
                            arryCountList.add(model);

                            // model will be added into arraylist
                            addLayout(model); // object will be passed into layout and set layout
                            Log.e(TAG, "addCount: error is : " + object.getString(General.MSG));
                            //Toast.makeText(this, object.getString(General.MSG), Toast.LENGTH_SHORT).show();
                        } else {
                            //ShowToast.internalErrorOccurred(getApplicationContext());
                        }
                    }

                }*/
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AddCountError", e.getMessage());
            }
        }
    }
}