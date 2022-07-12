package com.sagesurfer.school.resources.callbacks;

import org.json.JSONObject;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

/*
 * OAuth call back after authorization
 */

public interface AuthorizationCallbacks {

    void authorizationSuccessCallback(JSONObject jsonObject);

    void authorizationFailCallback(JSONObject jsonObject);
}
