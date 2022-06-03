package com.example.school.emotional_support;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.home.DataJournaling;
import com.example.school.home.MainActivity;
import com.example.school.home.ModelGratitudeListingResponseData;
import com.example.school.home.DataMood;
import com.example.school.home.MoodJournalDataMood_;
import com.example.school.home.adapters.AdapterGratitudeJournalingList;
import com.example.school.home.adapters.AdaptersMoodData;
import com.example.school.selfcaremanagement.AdapterSelfcareData;
import com.example.school.selfcaremanagement.Content_;
import com.example.school.selfcaremanagement.SelfcareData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentEmotionalSupport extends Fragment {

    private FragmentEmotionalSupportViewModel mViewModel;
    RecyclerView rv_moods,rv_journaling;
    DataJournaling dataJournaling;
    public static FragmentEmotionalSupport newInstance() {
        return new FragmentEmotionalSupport();
    }
    DataMood dataMood;
    private static final String TAG = "FragmentEmotionalSuppor";
    SelfcareData selfcareData;
    RecyclerView rv_inspirational_contents;
    MainActivity mainActivity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            mainActivity.setToolbarTitleText(getString(R.string.emotional_support));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_emotional_support_fragment, container, false);

        rv_moods=view.findViewById(R.id.rv_moods);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_moods.setLayoutManager(mLayoutManager);
        rv_moods.setItemAnimator(new DefaultItemAnimator());

        dataMood = new DataMood();
        dataJournaling =new DataJournaling();
        selfcareData=new SelfcareData();

        rv_journaling = view.findViewById(R.id.rv_journaling);
        RecyclerView.LayoutManager mLayoutManagerJournaling = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_journaling.setLayoutManager(mLayoutManagerJournaling);
        rv_journaling.setItemAnimator(new DefaultItemAnimator());

        rv_inspirational_contents = view.findViewById(R.id.rv_events);
        RecyclerView.LayoutManager mLayoutManagerInspContent = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_inspirational_contents.setLayoutManager(mLayoutManagerInspContent);
        rv_inspirational_contents.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataMood.fetchJournalMoodDataNew(0, 50, getContext(), getActivity(), this);

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(currentTime);
        dataJournaling.fetchGratitudesDataList("", strDate, "", "",getContext(),getActivity(),this);

        selfcareData.fetchSelfcareNewData(0,30,getContext(),getActivity(),TAG,true,FragmentEmotionalSupport.this);

    }

    public void moodDataResponse(ArrayList<MoodJournalDataMood_> dataList,Context context) {
        Toast.makeText(getContext(), "data received", Toast.LENGTH_SHORT).show();
        AdaptersMoodData journalListAdapter = new AdaptersMoodData(getActivity(), dataList);
       rv_moods.setAdapter(journalListAdapter);

    }

    public void moodDataResponseFailed() {

    }

    public void journalingData(ArrayList<ModelGratitudeListingResponseData> dataArrayList) {
        AdapterGratitudeJournalingList adapterGratitudeJournalingList = new AdapterGratitudeJournalingList(dataArrayList, getContext());
        rv_journaling.setAdapter(adapterGratitudeJournalingList);
    }

    public void setDataList(ArrayList<Content_> contentArrayList) {
           /* careContentListAdapter =
                                                new SelfCareContentListAdapter(activity, contentArrayList,
                                                        SelfCareContentListFragment.this);
                                        swipeMenuListView.setAdapter(careContentListAdapter);*/

        AdapterSelfcareData selfcareDataAdapter= new AdapterSelfcareData(getContext(),contentArrayList,FragmentEmotionalSupport.this) ;
        rv_inspirational_contents.setAdapter(selfcareDataAdapter);
    }
}