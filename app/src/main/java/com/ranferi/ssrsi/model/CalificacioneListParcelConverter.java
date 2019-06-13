package com.ranferi.ssrsi.model;

import android.os.Parcel;

import org.parceler.Parcels;

/**
 * Clase especifica para un campo RealmList<Calificacione>
 */
public class CalificacioneListParcelConverter extends RealmListParcelConverter<Calificacione> {

    @Override
    public void itemToParcel(Calificacione input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Calificacione itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Calificacione.class.getClassLoader()));
    }
}
