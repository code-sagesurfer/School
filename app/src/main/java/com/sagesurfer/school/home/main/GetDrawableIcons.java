package com.sagesurfer.school.home.main;

import com.sagesurfer.school.R;

public class GetDrawableIcons {
    public static int get(int id) {
        switch (id) {
            case 0:
            case 1:
                return R.drawable.ic_home;
            case 2:
                return R.drawable.mood_tracking;
            case 3:
                return R.drawable.journaling;
            case 4:
                return R.drawable.ic_assessment;
            case 5:
                return R.drawable.self_care;
            case 6:
                return R.drawable.ic_calendar;
            case 7:
                return R.drawable.ic_goal_management;
            case 8:
                return R.drawable.ic_proper_nutrition;
            case 9:
                return R.drawable.ic_education_spiritual;
            case 10:
                return R.drawable.ic_positive_quotes;
            case 11:
                return R.drawable.ic_team_care;

            case 12:
                return R.drawable.ic_notifications;
            case 13:
                return R.drawable.ic_support;

            case 14:
                return R.drawable.ic_drawer_menu_logout;

            default:
                return R.drawable.ic_home;
        }
    }
}
