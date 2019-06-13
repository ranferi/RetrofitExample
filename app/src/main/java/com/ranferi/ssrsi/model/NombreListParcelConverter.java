package com.ranferi.ssrsi.model;

import android.os.Parcel;

import org.parceler.Parcels;

/**
 * Clase especifica para un campo RealmList<Nombre>
 */
public class NombreListParcelConverter extends RealmListParcelConverter<Nombre> {

    @Override
    public void itemToParcel(Nombre input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Nombre itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Nombre.class.getClassLoader()));
    }
}