
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Nombre extends RealmObject {

    @SerializedName("nombre_sitio")
    @Expose
    private String nombreSitio;
    @SerializedName("proviene")
    @Expose
    private String proviene;

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
