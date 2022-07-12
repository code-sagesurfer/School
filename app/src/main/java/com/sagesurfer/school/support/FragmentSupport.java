package com.sagesurfer.school.support;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagesurfer.school.R;
import com.sagesurfer.school.home.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSupport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSupport extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CardView card_faq,cv_feedback,cv_about_us;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MainActivity mainActivity;

    public FragmentSupport() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSupport.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSupport newInstance(String param1, String param2) {
        FragmentSupport fragment = new FragmentSupport();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        card_faq=view.findViewById(R.id.card_faq);
        cv_feedback=view.findViewById(R.id.cv_feedback);

        cv_about_us=view.findViewById(R.id.cv_about_us);

        card_faq.setOnClickListener(this);
        cv_feedback.setOnClickListener(this);
        cv_about_us.setOnClickListener(this);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity) {
            mainActivity = (MainActivity) getContext();
            mainActivity.setToolbarTitleText(getString(R.string.support));
            mainActivity.changeDrawerIcon(false);

            mainActivity.toggleBellIcon(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_faq:
                openFragmentFAQ();
                break;

            case R.id.cv_feedback:
                openFragmentFeedback();
                break;

            case R.id.cv_about_us:
                openFragmentAboutUs();
                break;
        }
    }

    private void openFragmentFeedback() {
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentFeedback(), "FragmentFeedback");
        ft.addToBackStack("FragmentSupport");
        ft.commit();
    }

    private void openFragmentFAQ() {
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentFAQDetails(), "FragmentFAQDetails");
        ft.addToBackStack("FragmentSupport");
        ft.commit();
    }

    private void openFragmentAboutUs() {
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentAboutUs(), "FragmentAboutUs");
        ft.addToBackStack("FragmentSupport");
        ft.commit();
    }
}