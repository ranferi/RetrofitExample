package com.ranferi.ssrsi.model;

import android.os.Parcel;

import org.parceler.Parcels;

/**
 * Clase especifica para un campo RealmList<Categoria>
 */
public class CategoriaListParcelConverter extends RealmListParcelConverter<Categoria> {

    @Override
    public void itemToParcel(Categoria input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Categoria itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Categoria.class.getClassLoader()));
    }
}
