package com.ranferi.ssrsi.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public UserResponse(Boolean error, String message, User user) {

        this.error = error;
        this.message = message;
        this.user = user;
        Log.d("ActividadPT", "En constructor UserResponse, usuario: " + this.user);
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
