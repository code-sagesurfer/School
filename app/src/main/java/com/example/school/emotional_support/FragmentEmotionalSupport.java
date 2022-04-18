package com.example.school.emotional_support;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.school.R;

public class FragmentEmotionalSupport extends Fragment {

    private FragmentEmotionalSupportViewModel mViewModel;

    public static FragmentEmotionalSupport newInstance() {
        return new FragmentEmotionalSupport();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emotional_support_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentEmotionalSupportViewModel.class);
        // TODO: Use the ViewModel
    }

}