package com.ranferi.ssrsi.model;

import android.os.Parcel;

import org.parceler.Parcels;

/**
 * Clase especifica para un campo RealmList<Comentario>
 */
public class ComentarioListParcelConverter extends RealmListParcelConverter<Comentario> {

    @Override
    public void itemToParcel(Comentario input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Comentario itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Comentario.class.getClassLoader()));
    }
}
