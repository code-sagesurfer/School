package com.sagesurfer.school.journaling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelLikeResponseModel {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("is_like")
    @Expose
    private Integer isLike;
    @SerializedName("is_like_symbol")
    @Expose
    private String isLikeSymbol;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public String getIsLikeSymbol() {
        return isLikeSymbol;
    }

    public void setIsLikeSymbol(String isLikeSymbol) {
        this.isLikeSymbol = isLikeSymbol;
    }
}
