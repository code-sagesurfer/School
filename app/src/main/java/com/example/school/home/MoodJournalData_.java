package com.example.school.home;

import com.example.school.resources.General;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

public class MoodJournalData_ {
    @SerializedName(General.MOOD)
    private ArrayList<MoodJournalDataMood_> mood;

    public ArrayList<MoodJournalDataMood_> getMood() {
        return mood;
    }

    public void setMood(ArrayList<MoodJournalDataMood_> mood) {
        this.mood = mood;
    }
}
