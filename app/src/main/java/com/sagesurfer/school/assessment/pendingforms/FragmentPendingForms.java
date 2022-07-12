package com.sagesurfer.school.assessment.pendingforms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagesurfer.school.R;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPendingForms extends Fragment {

    @BindView(R.id.rv_pending_forms)
    RecyclerView rv_pending_forms;

    @BindView(R.id.tv_error_msg)
    TextView tv_error_msg;


    boolean isLoading = false;
    private static final String TAG = "FragmentPendingForms";
    private ArrayList<Forms_> formsArrayList;
    private int minSize = 0;
    private int maxSize = 30;
    private PendingFormListAdapter pendingFormListAdapter;
    private boolean firstTimeLoading = true;

    public FragmentPendingForms() {
        //Required empty public constructor
    }


    public static FragmentPendingForms newInstance(String param1, String param2) {
        FragmentPendingForms fragment = new FragmentPendingForms();
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
        firstTimeLoading = true;
        getListNew(0, 50);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_forms, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        rv_pending_forms.setLayoutManager(mLayoutManager);
        rv_pending_forms.setItemAnimator(new DefaultItemAnimator());

        rv_pending_forms.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == formsArrayList.size() - 1) {
                        getListNew(minSize, maxSize);
                        isLoading = true;
                    }
                }
            }
        });
        return view;
    }


    private void getListNew(int min, int max) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GET_LIST);
        requestMap.put(General.MIN, "" + min);
        requestMap.put(General.MAX, "" + max);

        requestMap.put("form_date", "");
        requestMap.put("to_date", "");

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_FORM_BUILDER;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());

        APIManager.Companion.getInstance().showProgressDialog(getContext(), false, "Loading..");
        if (requestBody != null) {
            APIManager.Companion.getInstance().mobile_from_builder(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        JsonElement mJsonElement = response.body();
                        Gson gson = new Gson();
                        ModelFormListResponse listResponse = gson.fromJson(mJsonElement.toString(), ModelFormListResponse.class);

                        if (listResponse.getGet_pending_list().get(0).getStatus() == 1) {
                            minSize = maxSize + 1;
                            maxSize = maxSize + 20;
                            tv_error_msg.setVisibility(View.GONE);
                            rv_pending_forms.setVisibility(View.VISIBLE);
                            formsArrayList = listResponse.getGet_pending_list();

                            if (firstTimeLoading) {
                                firstTimeLoading=false;
                                pendingFormListAdapter = new PendingFormListAdapter(formsArrayList, getContext());
                                rv_pending_forms.setAdapter(pendingFormListAdapter);
                                isLoading = false;
                            } else {
                                pendingFormListAdapter.addData(formsArrayList);
                                isLoading = false;
                            }

                          /*  try {

                                int count = formsArrayList.size();
                                if (Preferences.get(General.ROLE).equalsIgnoreCase("Care Coordinator")
                                        || Preferences.get(General.ROLE).equalsIgnoreCase("supervisor")
                                        || Preferences.get(General.ROLE).equalsIgnoreCase("MHSADS")
                                        || Preferences.get(General.ROLE).equalsIgnoreCase("Provider")
                                        || Preferences.get(General.ROLE).equalsIgnoreCase("Case Manager")) {

                                    AssessmentListFragment.tabs.getTabAt(1).getOrCreateBadge().setNumber(formsArrayList.size());
                                    AssessmentListFragment.tabs.getTabAt(1).getOrCreateBadge().setVisible(true);
                                } else {
                                    AssessmentListFragment.tabs.getTabAt(0).getOrCreateBadge().setNumber(formsArrayList.size());
                                    AssessmentListFragment.tabs.getTabAt(0).getOrCreateBadge().setVisible(true);
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/

                        } else {
                            if (firstTimeLoading){
                            tv_error_msg.setVisibility(View.VISIBLE);
                            rv_pending_forms.setVisibility(View.GONE);}
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
        }
    }
}