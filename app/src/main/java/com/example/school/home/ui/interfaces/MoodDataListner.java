package com.example.school.home.ui.interfaces;

import android.content.Context;

import com.example.school.home.MoodJournalDataMood_;

import java.util.ArrayList;

public interface MoodDataListner {
    void moodSuccessResponse(ArrayList<MoodJournalDataMood_> dataList, Context context);
    void moodDataFailed();
}
