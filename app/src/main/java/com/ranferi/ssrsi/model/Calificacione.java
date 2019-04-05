
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Calificacione extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("calificacion")
    @Expose
    private String calificacion;
    @SerializedName("proviene")
    @Expose
    private String proviene;
    @LinkingObjects("mCalificaciones")
    private final RealmResults<Place> calificacionesSitio = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Calificacione() {
    }

    /**
     * 
     * @param calificacion calificación del sitio
     * @param proviene de una base de datos
     */
    public Calificacione(String calificacion, String proviene) {
        super();
        this.calificacion = calificacion;
        this.proviene = proviene;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getProviene() {
        return proviene;
    }

    public void setProviene(String proviene) {
        this.proviene = proviene;
    }

}
