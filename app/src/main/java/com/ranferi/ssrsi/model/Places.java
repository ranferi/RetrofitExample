package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Places extends RealmObject {

    @SerializedName("places")
    @Expose
    private RealmList<Place> mPlaces;

    public Places() { }

    /**
     *
     * @param places
     */
    public Places(RealmList<Place> places) {
        super();
        this.mPlaces = places;
    }

    public RealmList<Place> getPlaces() {
        return mPlaces;
    }

    public void setPlaces(RealmList<Place> places) {
        this.mPlaces = places;
    }

}
