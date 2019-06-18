
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.com_ranferi_ssrsi_model_NombreRealmProxy;

@RealmClass
@Parcel(implementations = { com_ranferi_ssrsi_model_NombreRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Nombre.class })
public class Nombre extends RealmObject {

    @SerializedName("nombre_sitio")
    @Expose
    @PrimaryKey
    private String nombreSitio;
    @SerializedName("proviene")
    @Expose
    private String proviene;
    @LinkingObjects("mNombres")
    private final RealmResults<Place> nombresSitio = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Nombre() {
    }

    /**
     * 
     * @param nombreSitio
     * @param proviene
     */
    public Nombre(String nombreSitio, String proviene) {
        super();
        this.nombreSitio = nombreSitio;
        this.proviene = proviene;
    }

    public String getNombreSitio() {
        return nombreSitio;
    }

    public void setNombreSitio(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }

    public String getProviene() {
        return proviene;
    }

    public void setProviene(String proviene) {
        this.proviene = proviene;
    }

}
