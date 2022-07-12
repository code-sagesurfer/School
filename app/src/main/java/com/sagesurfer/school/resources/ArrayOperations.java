package com.sagesurfer.school.resources;

import java.util.ArrayList;


/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 *         Created on 28-06-2022
 *         Last Modified on
 */

/*
* This class converts list of integer or string to comma separated string avoiding extra any white space
*/

public class ArrayOperations {

    private static final String TAG = ArrayOperations.class.getSimpleName();

    public static String listToString(ArrayList<Integer> list) {
        String dataList = list.toString();
        dataList = dataList.replaceAll("\\[", "").replaceAll("]", "");
        dataList = dataList.replace(" ", "");
        return dataList;
    }

    public static String stringListToString(ArrayList<String> list) {
        String dataList = list.toString();
        dataList = dataList.replaceAll("\\[", "").replaceAll("]", "");
        dataList = dataList.replace(" ", "");
        return dataList;
    }

    public static String listLongToString(ArrayList<Long> list) {
        String dataList = list.toString();
        dataList = dataList.replaceAll("\\[", "").replaceAll("]", "");
        dataList = dataList.replace(" ", "");
        return dataList;
    }

    public static String stringListToStringForCriteria(ArrayList<String> list) {
        String dataList = list.toString();
        dataList = dataList.replaceAll("\\[", "").replaceAll("]", "");
        //dataList = dataList.replace(" ", "");
        return dataList;
    }
}
