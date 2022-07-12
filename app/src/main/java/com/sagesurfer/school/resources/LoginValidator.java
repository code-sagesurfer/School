package com.sagesurfer.school.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

/*
 * validate all login page fields for proper data
 */

public class LoginValidator {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && !(email.length() < 3 || email.length() > 100);
    }

    public static boolean isUsername(String username) {
        return !(username == null || username.length() < 3 || username.length() > 30);
    }

    public static boolean isUsername_one(String username) {
        return !(username == null || username.length() < 5 || username.length() > 100);
    }

    public static boolean isPassword(String password) {
        return !(password == null || password.length() < 4 || password.length() > 18);
    }

    public static boolean isCode(String code) {
        return !(code == null || code.length() > 10 || code.length() <= 0);
    }

    public static boolean isName(String name) {
        return name.trim().length() > 0 && !(name.length() <= 2 || name.length() > 30);
    }

    public static boolean isAddress(String address) {
        return !(address == null || address.length() < 5 || address.length() > 30);
    }
}
