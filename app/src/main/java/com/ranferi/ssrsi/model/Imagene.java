
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Imagene extends RealmObject {

    @SerializedName("imagen")
    @Expose
    @PrimaryKey
    private String imagen;
    @LinkingObjects("mImagenes")
    private final RealmResults<Place> imagenesSitio = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Imagene() {
    }

    /**
     * 
     * @param imagen
     */
    public Imagene(String imagen) {
        super();
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
