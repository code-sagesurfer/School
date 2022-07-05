package com.example.school.assessment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.assessment.model.AllCounterResponseModel;
import com.example.school.assessment.pendingforms.FragmentPendingForms;
import com.example.school.assessment.submitted.FragmentSubmittedForms;
import com.example.school.home.main.MainActivity;
import com.example.school.resources.APIManager;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAssessmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAssessmentMain extends Fragment implements View.OnClickListener {
    @BindView(R.id.cl_pending_forms)
    ConstraintLayout cl_pending_forms;

    @BindView(R.id.tv_pending_count)
    TextView tv_pending_count;

    @BindView(R.id.cl_submitted_forms)
    ConstraintLayout cl_submitted_forms;

    @BindView(R.id.tv_submitted_count)
    TextView tv_submitted_count;

    @BindView(R.id.tv_pending_forms)
    TextView tv_pending_forms;

    @BindView(R.id.tv_submitted_form)
    TextView tv_submitted_form;

    @BindView(R.id.fl_assessment_container)
    FrameLayout fl_main_container;
    MainActivity mainActivity;
    private static final String TAG = "FragmentAssessmentMain";
    public FragmentAssessmentMain() {

    }

    public static FragmentAssessmentMain newInstance(String param1, String param2) {
        FragmentAssessmentMain fragment = new FragmentAssessmentMain();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assessment_main, container, false);
        ButterKnife.bind(this,view);
        cl_submitted_forms.setOnClickListener(this);
        cl_pending_forms.setOnClickListener(this);

        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.fl_assessment_container, new FragmentPendingForms(), "FragmentPendingForms");
        ft.addToBackStack("FragmentAssessmentMain");
        ft.commit();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_pending_forms:
                pendingFormClick();
                break;

            case R.id.cl_submitted_forms:
                submittedFormClick();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity) {
            mainActivity = (MainActivity) getContext();
            mainActivity.setToolbarTitleText(getString(R.string.menu_assessment));

            mainActivity.toggleBellIcon(false);
        }
        getCounter();
    }

    private void submittedFormClick() {
        cl_pending_forms.setBackground(getContext().getDrawable(R.drawable.rounded_background_white));
        tv_pending_count.setBackground(getContext().getDrawable(R.drawable.date_physical_activity_bg_purple_36dp));
        tv_pending_count.setTextColor(getContext().getResources().getColor(R.color.white));
        tv_pending_forms.setTextColor(getContext().getResources().getColor(R.color.black));

        cl_submitted_forms.setBackground(getContext().getDrawable(R.drawable.date_physical_activity_bg_purple_36dp));
        tv_submitted_count.setBackground(getContext().getDrawable(R.drawable.rounded_background_white));
        tv_submitted_count.setTextColor(getContext().getResources().getColor(R.color.color_primary));
        tv_submitted_form.setTextColor(getContext().getResources().getColor(R.color.white));
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.fl_assessment_container, new FragmentSubmittedForms(), "FragmentSubmittedForms");
        ft.addToBackStack("FragmentAssessmentMain");
        ft.commit();
    }

    private void pendingFormClick() {

        cl_pending_forms.setBackground(getContext().getDrawable(R.drawable.date_physical_activity_bg_purple_36dp));
        tv_pending_count.setBackground(getContext().getDrawable(R.drawable.rounded_background_white));
        tv_pending_count.setTextColor(getContext().getResources().getColor(R.color.color_primary));
        tv_pending_forms.setTextColor(getContext().getResources().getColor(R.color.white));

        cl_submitted_forms.setBackground(getContext().getDrawable(R.drawable.rounded_background_white));
        tv_submitted_count.setBackground(getContext().getDrawable(R.drawable.date_physical_activity_bg_purple_36dp));
        tv_submitted_count.setTextColor(getContext().getResources().getColor(R.color.white));
        tv_submitted_form.setTextColor(getContext().getResources().getColor(R.color.black));

        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.fl_assessment_container, new FragmentPendingForms(), "FragmentPendingForms");
        ft.addToBackStack("FragmentAssessmentMain");
        ft.commit();

    }

    private void getCounter() {

//        "user_id=//login user id
//        tz=//time zone
//                action=//get_all_counter"

        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, "get_all_counter");
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_FORM_BUILDER;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG,getContext(), getActivity());

        if (requestBody != null) {
            APIManager.Companion.getInstance().showProgressDialog(getContext(), false, "Loading..");
            if (requestBody != null) {
                APIManager.Companion.getInstance().mobile_from_builder(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            JsonElement mJsonElement = response.body();
                            Gson gson = new Gson();
                            AllCounterResponseModel listResponse = gson.fromJson(mJsonElement.toString(), AllCounterResponseModel.class);
                            if (Integer.parseInt(listResponse.getGet_all_counter().getData().getPending_count())>0) {
                                tv_pending_count.setVisibility(View.VISIBLE);
                                tv_pending_count.setText(listResponse.getGet_all_counter().getData().getPending_count());
                            }else{
                                tv_pending_count.setVisibility(View.GONE);
                            }

                            if (Integer.parseInt(listResponse.getGet_all_counter().getData().getSubmit_count())>0) {
                                tv_submitted_count.setVisibility(View.VISIBLE);
                                tv_submitted_count.setText(listResponse.getGet_all_counter().getData().getSubmit_count());
                            }else{
                                tv_submitted_count.setVisibility(View.GONE);
                            }

                            /*if (listResponse != null) {

                                if (listResponse.getGet_all_counter().getData().getStatus() == 1) {

                                    try {

                                        if (Preferences.get(General.ROLE).equalsIgnoreCase("Care Coordinator")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("supervisor")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("MHSADS")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("Provider")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("Case Manager")) {
                                            int count = Integer.parseInt(listResponse.getGet_all_counter().getData().getPending_count());

                                            if (count > 0) {
                                                tabs.getTabAt(0).getOrCreateBadge().setNumber(Integer.parseInt(listResponse.getGet_all_counter().getData().getManage_count()));
                                                tabs.getTabAt(0).getOrCreateBadge().setVisible(true);

                                            } else {
                                                tabs.getTabAt(0).getOrCreateBadge().setVisible(false);
                                            }
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        int count = Integer.parseInt(listResponse.getGet_all_counter().getData().getPending_count());


                                        if (Preferences.get(General.ROLE).equalsIgnoreCase("Care Coordinator")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("supervisor")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("MHSADS")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("Provider")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("Case Manager")) {
                                            if (count > 0) {
                                                tabs.getTabAt(1).getOrCreateBadge().setNumber(count);
                                                tabs.getTabAt(1).getOrCreateBadge().setVisible(true);
                                            } else {
                                                tabs.getTabAt(1).getOrCreateBadge().setVisible(false);
                                            }
                                        } else {
                                            if (count > 0) {
                                                tabs.getTabAt(0).getOrCreateBadge().setNumber(count);
                                                tabs.getTabAt(0).getOrCreateBadge().setVisible(true);
                                            } else {
                                                tabs.getTabAt(0).getOrCreateBadge().setVisible(false);
                                            }
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        int count = Integer.parseInt(listResponse.getGet_all_counter().getData().getSubmit_count());


                                        if (Preferences.get(General.ROLE).equalsIgnoreCase("Care Coordinator")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("supervisor")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("MHSADS")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("Provider")
                                                || Preferences.get(General.ROLE).equalsIgnoreCase("Case Manager")) {
                                            if (count > 0) {
                                                tabs.getTabAt(2).getOrCreateBadge().setNumber(count);
                                                tabs.getTabAt(2).getOrCreateBadge().setVisible(true);
                                            } else {
                                                tabs.getTabAt(2).getOrCreateBadge().setVisible(false);
                                            }
                                        } else {
                                            if (count > 0) {
                                                tabs.getTabAt(1).getOrCreateBadge().setNumber(count);
                                                tabs.getTabAt(1).getOrCreateBadge().setVisible(true);
                                            } else {
                                                tabs.getTabAt(1).getOrCreateBadge().setVisible(false);

                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                    }
                });
            }
        }
    }
}