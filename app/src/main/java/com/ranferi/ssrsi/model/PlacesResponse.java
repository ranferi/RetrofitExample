package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

public class PlacesResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("sitio")
    @Expose
    private RealmList<Place> sitio = new RealmList<>();

    public PlacesResponse() {

    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RealmList<Place> getSitio() {
        return sitio;
    }

    public void setSitio(RealmList<Place> sitio) {
        this.sitio = sitio;
    }
}
