package com.example.school.support;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.school.R;
import com.example.school.home.MainActivity;
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


public class FragmentFAQDetails extends Fragment implements View.OnClickListener{

    @BindView(R.id.rv_faq_list)
    RecyclerView rv_faq_list;

    MainActivity mainActivity;
    private String mParam1;
    private String mParam2;
    private static final String TAG = "FragmentFAQDetails";
    FAQAdapter mFAQAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            mainActivity.setToolbarTitleText(getString(R.string.faq));
            mainActivity.changeDrawerIcon(true);
        }
    }

    public FragmentFAQDetails() {
        // Required empty public constructor
    }


    public static FragmentFAQDetails newInstance(String param1, String param2) {
        FragmentFAQDetails fragment = new FragmentFAQDetails();
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
        View view = inflater.inflate(R.layout.fragment_f_a_q_details, container, false);
        ButterKnife.bind(this,view);
        mFAQAdapter = new FAQAdapter(getContext(), FragmentFAQDetails.this);
        rv_faq_list.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rv_faq_list.setAdapter(mFAQAdapter);
        getFAQ();
        return view;
    }

    private void getFAQ() {
        APIManager.Companion.getInstance().showProgressDialog(getContext(), true, "Loading....");
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, "get_faq");

        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_SUPPORT;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());
        APIManager.Companion.getInstance().mobile_support(requestBody, new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                APIManager.Companion.getInstance().dismissProgressDialog();
                try {
                    JsonElement element = response.body();
                    Gson gson = new Gson();
                    ModelFAQResponse mDashBoardResponse = gson.fromJson(element.toString(), ModelFAQResponse.class);
                    if (!mDashBoardResponse.getGetFaq().isEmpty()) {
                        mFAQAdapter.addData(mDashBoardResponse.getGetFaq());
                        //mFAQAdapter.objList.get(0).setSelect("1");
                        mFAQAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {

    }
}