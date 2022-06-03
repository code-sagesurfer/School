package com.example.school.social_activity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BalanceResponse(
    @SerializedName("data")
    val `data`: List<Datax>,
    val msg: String,
    val status: Int
)

data class Datax(

    //total":"140"
    //"title":"team search 30 day recurrence",
    // "points":"Credited 5 Points",
    // "date_time":"2021-09-15 08:09:38 am",
    // "category_id":"78",
    // "attached_image":"https:\/\/designstaging.sagesurfer.com\/design\/AppStore\/reward\/fc06382692ae5174392dabe94a4fb9cf.PNG",
    // "description":"team search will get 5 point by spent 2 min",
    // "end_time":"31 Dec, 2021"

    val pointsinnumber: String,
    val end_time: String,
    val description: String,
    val attached_image: String,
    val category_id: String,
    val date_time: String,
    val points: String,
    val title: String,
    val total: String

) : Serializable {

}