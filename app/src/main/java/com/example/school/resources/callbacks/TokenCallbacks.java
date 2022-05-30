package com.example.school.resources.callbacks;

import org.json.JSONObject;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

/*
 * OAuth call back to get token after successful authorization
 */

public interface TokenCallbacks {
    void tokenSuccessCallback(JSONObject jsonObject);

    void tokenFailCallback(JSONObject jsonObject);
}
