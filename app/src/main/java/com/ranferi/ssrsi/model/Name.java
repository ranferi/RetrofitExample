
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.com_ranferi_ssrsi_model_NameRealmProxy;

@RealmClass
@Parcel(implementations = { com_ranferi_ssrsi_model_NameRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Name.class })
public class Name extends RealmObject {

    @SerializedName("nombre_sitio")
    @Expose
    @PrimaryKey
    private String nombreSitio;
    @SerializedName("proviene")
    @Expose
    private String proviene;
    @LinkingObjects("mNombres")
    private final RealmResults<Place> nombresSitio = null;

    public Name() {
    }

    /**
     * 
     * @param nombreSitio
     * @param proviene
     */
    public Name(String nombreSitio, String proviene) {
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
