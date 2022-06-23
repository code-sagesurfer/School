package com.example.school.support

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetFaq (var isSelected :String): Serializable {

    @SerializedName("answer")
    var answer = ""

    @SerializedName("question_title")
    val question_title = ""

    @SerializedName("topic")
    val topic = ""


    @SerializedName("isVisible")
    var isVisible = ""




}