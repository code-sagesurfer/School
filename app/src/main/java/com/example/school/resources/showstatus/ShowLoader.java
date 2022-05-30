package com.example.school.resources.showstatus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 30/05/2022
 * Last Modified on
 */

/*
 * This class is used to show loader dialog
 */

public class ShowLoader {

    private ProgressDialog progress;

    public ShowLoader() {
    }

    public void showUploadDialog(Activity activity,String message) {
        if (progress == null) {
            progress = new ProgressDialog(activity);
            progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progress.setMessage(message);
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setIndeterminate(false);
            progress.show();
        } else {
            progress.show();
        }
    }

    public void dismissUploadDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void showPostingDialog(Activity activity) {
        if (progress == null) {
            progress = new ProgressDialog(activity);
            progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progress.setMessage("Posting...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setIndeterminate(false);
            progress.show();
        } else {
            progress.show();
        }
    }

    public void dismissPostingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void dismissSendProgressDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
}
