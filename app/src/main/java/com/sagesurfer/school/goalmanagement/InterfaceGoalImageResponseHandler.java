package com.sagesurfer.school.goalmanagement;

import android.content.Context;

public interface InterfaceGoalImageResponseHandler {
    public void onGoalImageSelected(Context context,String path,long file_id);
    public void showGoalErrorMessage(String Message);
}
