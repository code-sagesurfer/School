package com.sagesurfer.school.home.ui.interfaces;

import android.content.Context;

import com.sagesurfer.school.home.MoodJournalDataMood_;

import java.util.ArrayList;

public interface MoodDataListner {
    void moodSuccessResponse(ArrayList<MoodJournalDataMood_> dataList, Context context);
    void moodDataFailed();
}
