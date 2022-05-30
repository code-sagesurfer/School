package com.example.school.intakeconsent;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.school.intakeconsent.consent.FragmentConsent;
import com.example.school.intakeconsent.intake.FragmentIntake;

public class IntakePageAdapter extends FragmentStateAdapter {
    int fragmentsCount =2;
    Fragment fragment;
    Activity activity;
    Context context;
    public IntakePageAdapter(@NonNull Context context, Activity activity, Fragment fragment) {
        super(fragment);
        this.fragment=fragment;
        this.activity=activity;
        this.context=context;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //FragmentMedicine fragmentMedicine=FragmentMedicine.newInstance(activity);
        switch (position){
            case 0: return new FragmentIntake();

            case 1:return new FragmentConsent();

            default:return null;
        }
    }

    @Override
    public int getItemCount() {
        return fragmentsCount;
    }

}
