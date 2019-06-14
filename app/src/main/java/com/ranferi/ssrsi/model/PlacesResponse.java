package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import io.realm.com_ranferi_ssrsi_model_PlacesResponseRealmProxy;

@RealmClass
@Parcel(implementations = { com_ranferi_ssrsi_model_PlacesResponseRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { PlacesResponse.class })
public class PlacesResponse extends RealmObject {
    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("places")
    @Expose

    private RealmList<Place> mPlaces;

    public PlacesResponse() {
    }

    public PlacesResponse(RealmList<Place> places) {
        super();
        this.mPlaces = places;
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

    public RealmList<Place> getPlaces() {
        return mPlaces;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setPlaces(RealmList<Place> places) {
        this.mPlaces = places;
    }
}
