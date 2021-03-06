package com.sagesurfer.school.resources.apidata;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

/*
 * Url encoder class which encode URL for hiding user information in open com.sagesurfer.network
 */

public class UrlEncoder_ {

    private static final String TAG = UrlEncoder_.class.getSimpleName();

    public static String encrypt(String url) {
        return generate(url);
    }

    private static String generate(String url) {
        MCrypt_ crypt_ = new MCrypt_();
        String encrypted = null;
        try {
            encrypted = KeyMaker_.key(url);
            encrypted = MCrypt_.bytesToHex(crypt_.encrypt(encrypted));
            encrypted = _Base64.encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }
}
