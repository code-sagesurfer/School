package com.sagesurfer.school.goalmanagement;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sagesurfer.school.R;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.GetTime;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
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

public class FragmentAddGoal extends Fragment implements View.OnClickListener, InterfaceGoalImageResponseHandler {
    @BindView(R.id.et_goal_title)
    EditText et_goal_title;

    @BindView(R.id.et_desc)
    EditText et_desc;

    @BindView(R.id.sp_goal_frequency)
    Spinner sp_goal_frequency;

    @BindView(R.id.tv_goal_start_date)
    TextView tv_goal_start_date;

    @BindView(R.id.tv_goal_end_date)
    TextView tv_goal_end_date;

    @BindView(R.id.tv_goal_attachment)
    TextView tv_goal_attachment;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.btn_cancel_gratitude)
    Button btn_cancel_gratitude;

    String file_path;
    static String SelectedFileId = "";
    MainActivity mainActivity;
    static RoundedImageView iv_goal_attached_image;
    final Calendar myCalendar = Calendar.getInstance();
    private static final String TAG = "FragmentAddGoal";
    AlertDialog.Builder builder;
    DatePickerDialog.OnDateSetListener startDateDatePicker;
    DatePickerDialog.OnDateSetListener endDateDatePicker;

    public FragmentAddGoal() {

    }

    public static FragmentAddGoal newInstance(String param1, String param2) {
        FragmentAddGoal fragment = new FragmentAddGoal();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity) {
            mainActivity = (MainActivity) getContext();
            mainActivity.changeDrawerIcon(true);
            mainActivity.setToolbarTitleText("Add Goal");
            mainActivity.toggleBellIcon(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_goal, container, false);
        ButterKnife.bind(this, view);

        iv_goal_attached_image = view.findViewById(R.id.iv_goal_attached_image);
        tv_goal_attachment.setOnClickListener(this);
        tv_goal_start_date.setOnClickListener(this);
        tv_goal_end_date.setOnClickListener(this);
        btn_cancel_gratitude.setOnClickListener(this);
        String[] frequencyArray = {"Select Frequency", "Daily", "Weekly", "Monthly"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item_goal, frequencyArray);
        sp_goal_frequency.setAdapter(dataAdapter);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateViews();
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

        endDateDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                setDate("end");
            }
        };
        return view;
    }

    String StartDate, EndDate;

    private void setDate(String view) {
        String myFormat = "yyyy-MM-dd";
        String myDisplayFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat(myDisplayFormat, Locale.US);

       /* String myFormatToSendServer = "dd-MM-yyyy";
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormatToSendServer, Locale.US);*/

        if (view.equals("start")) {
            StartDate = sdf.format(myCalendar.getTime());
            tv_goal_start_date.setText(sdf2.format(myCalendar.getTime()));
        } else {
            tv_goal_end_date.setText(sdf2.format(myCalendar.getTime()));
            EndDate = sdf.format(myCalendar.getTime());
        }
    }

    private void validateViews() {
        String GoalName = et_goal_title.getText().toString().trim();
        String GoalDesc = et_desc.getText().toString().trim();
        String StartDate = tv_goal_start_date.getText().toString().trim();
        String EndDate = tv_goal_end_date.getText().toString().trim();
        AppLog.d(TAG, "validateViews: "+GetTime.compareDate(StartDate,EndDate));
        if (GoalName.equalsIgnoreCase("")) {
            et_goal_title.setError(getString(R.string.field_required));
        } else if (GoalDesc.equalsIgnoreCase("")) {
            et_desc.setError(getString(R.string.field_required));
        } else if (StartDate.equalsIgnoreCase("Enter date")) {
            tv_goal_start_date.setError(getString(R.string.field_required));
        } else if (EndDate.equalsIgnoreCase("Enter date")) {
            tv_goal_end_date.setError(getString(R.string.field_required));
        } else if (sp_goal_frequency.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), "Please select frequency", Toast.LENGTH_SHORT).show();
        } else if (GetTime.compareDate(StartDate,EndDate).equals("DateTwoIsGreater")
                || GetTime.compareDate(StartDate,EndDate).equals("BothAreEqual"))
        {
            if (sp_goal_frequency.getSelectedItem().toString().equals("Weekly")){
                if (GetTime.checkWeekDifference(StartDate,EndDate)){
                        createGoal(Actions_.GOAL_ACTION, GoalName, GoalDesc);
                }else{
                    Toast.makeText(getContext(), "Please select valid start and end date.", Toast.LENGTH_SHORT).show();
                }
            }else  if (sp_goal_frequency.getSelectedItem().toString().equals("Daily")){
                createGoal(Actions_.GOAL_ACTION, GoalName, GoalDesc);
            }else  if (sp_goal_frequency.getSelectedItem().toString().equals("Monthly")){
                Toast.makeText(getContext(), "You can not create monthly goal.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "End date should be greater.", Toast.LENGTH_SHORT).show();
        }
    }


    private void checkPermission() {
        Preferences.initialize(getContext());
        Preferences.save(General.UPLOADING_CONTENT_FROM, "FragmentAddGoal");
        if (checkSelfPermission(
                requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Intent musicIntent = new Intent(Intent.ACTION_GET_CONTENT);
            musicIntent.setType("*/*");
            getActivity().startActivityForResult(musicIntent, 201);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showRotationalPremissionDialog();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    General.GOAL_PERMISSION);
        }
    }

    @SuppressLint("ResourceType")
    private void showRotationalPremissionDialog() {
        builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage(requireActivity().getResources().getString(R.string.upload_permission))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                General.GOAL_PERMISSION);
                    }
                })
                .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert");
        alert.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goal_attachment:
                checkPermission();
                break;

            case R.id.tv_goal_start_date:
                openDatePicker();
                break;

            case R.id.tv_goal_end_date:
                openEndDatePicker();
                break;

            case R.id.btn_cancel_gratitude:
                getActivity().onBackPressed();
                break;
        }
    }

    private void openEndDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), endDateDatePicker,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.show();
    }

    private void openDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), startDateDatePicker,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.show();

    }

    @Override
    public void onGoalImageSelected(Context context, String path, long file_id) {
        AppLog.d(TAG, "onGoalImageSelected: data reached");
        file_path = path;
        SelectedFileId = String.valueOf(file_id);
        setAttachedImage(file_path);
    }

    public void setAttachedImage(String file_path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        iv_goal_attached_image.setVisibility(View.VISIBLE);
        final Bitmap b = BitmapFactory.decodeFile(file_path, options);
        iv_goal_attached_image.setImageBitmap(b);
        AppLog.i(TAG, "setAttachedImage: id - " + SelectedFileId);
    }

    @Override
    public void showGoalErrorMessage(String Message) {

    }

    // make a network call to create goal
    // 0: Coung goal
    // 1: Yes/no Goal
    // 2: Global goal (Tread this goal same as Counting goal)
    private void createGoal(String action, String goalName, String goalDesc) {
        String frequencyUnit = "";
        String checked_noti = "";
        if (sp_goal_frequency.getSelectedItem().toString().equals("Daily")) {
            checked_noti = "day";
            frequencyUnit = "1";
        } else if (sp_goal_frequency.getSelectedItem().toString().equals("Weekly")) {
            checked_noti = "week-tue";
            frequencyUnit = "1,Mon-Tue-Wed-Thu-Fri-Sat-Sun";
        } else if (sp_goal_frequency.getSelectedItem().toString().equals("Monthly")) {
            //this block will not execute for now whenever we are not going to make changes in API for monthly goal..
            checked_noti = "week-tue";
            frequencyUnit = "1,Mon-Tue-Wed-Thu-Fri-Sat-Sun";
        }

        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.TIMEZONE, Preferences.get(General.TIMEZONE));
        requestMap.put(General.ACTION, Actions_.GOAL_ACTION);
        requestMap.put("action_type", "add");
        requestMap.put(General.NAME, goalName);
        requestMap.put(General.GOAL_TYPE, "1");
        requestMap.put(General.UNITS, "Pm");
        requestMap.put(General.START_DATE, StartDate);
        requestMap.put(General.END_DATE, EndDate);
        requestMap.put(General.DESCRIPTION, goalDesc);
        requestMap.put(General.ID, "0");
        requestMap.put(General.MILESTONE_ID, "");
        requestMap.put("del_mile_id", "0");
        requestMap.put(General.MILESTONE, "");
        requestMap.put(General.MILESTONE_DATE, "");
        requestMap.put("notification", "1");
        requestMap.put("notify_at", "0");
        requestMap.put("frequency_unit", frequencyUnit);
        requestMap.put("occurrences", "1");
        requestMap.put("quantity", "0");
        requestMap.put("checked_noti", checked_noti);
        requestMap.put("img_gallery_id", SelectedFileId);
        requestMap.put(General.START_TIME, "12:00");

        if (sp_goal_frequency.getSelectedItem().toString().equalsIgnoreCase("weekly")) {
            requestMap.put(General.FREQUENCY, "daily");
        } else {
            requestMap.put(General.FREQUENCY, "week");
        }

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_SELF_GOAL;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().self_goal(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            Gson gson = new Gson();
                            ModelAddGoalResponse listResponse = gson.fromJson(response.body(), ModelAddGoalResponse.class);
                            if (listResponse.getGoalAction().get(0).getStatus() == 1) {
                                Toast.makeText(getContext(), "" + listResponse.getGoalAction().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                sp_goal_frequency.setSelection(0);
                                et_goal_title.setText("");
                                et_desc.setText("");
                                tv_goal_start_date.setText("");
                                tv_goal_start_date.setError(null);
                                tv_goal_end_date.setText("");
                                tv_goal_end_date.setError(null);
                                iv_goal_attached_image.setVisibility(View.INVISIBLE);
                            } else {
                                Toast.makeText(getContext(), "" + listResponse.getGoalAction().get(0).getMsg(), Toast.LENGTH_SHORT).show();
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

                /*String response = NetworkCall_.post(url, requestBody, TAG, this, this);
                if (response != null && !response.equalsIgnoreCase("13")) {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("goal_action")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("goal_action");
                        JSONObject object = jsonArray.getJSONObject(0);
                        int status = object.getInt(General.STATUS);
                        if (status == 1) {
                            ShowToast.toast(this.getResources().getString(R.string.successful), getApplicationContext());
                            AddGoalPreferences.clear(TAG);
                        } else {
                            ShowToast.toast(this.getResources().getString(R.string.action_failed), getApplicationContext());
                        }
                    }
                    onBackPressed();
                    return;
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //ShowToast.toast(this.getResources().getString(R.string.action_failed), getApplicationContext());
    }
}