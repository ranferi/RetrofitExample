package com.ranferi.ssrsi.model;

import android.os.Parcel;

import org.parceler.Parcels;

/**
 * Clase especifica para un campo RealmList<Imagene>
 */
public class ImageneListParcelConverter extends RealmListParcelConverter<Imagene> {

    @Override
    public void itemToParcel(Imagene input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Imagene itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Imagene.class.getClassLoader()));
    }
}