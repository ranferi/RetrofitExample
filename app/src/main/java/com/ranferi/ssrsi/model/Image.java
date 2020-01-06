
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.com_ranferi_ssrsi_model_ImageRealmProxy;

@RealmClass
@Parcel(implementations = { com_ranferi_ssrsi_model_ImageRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Image.class })
public class Image extends RealmObject {

    @SerializedName("imagen")
    @Expose
    @PrimaryKey
    private String imagen;
    @LinkingObjects("mImagenes")
    private final RealmResults<Place> imagenesSitio = null;

    public Image() {
    }

    /**
     * 
     * @param imagen
     */
    public Image(String imagen) {
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
