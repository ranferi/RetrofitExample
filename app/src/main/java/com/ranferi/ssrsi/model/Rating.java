
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.com_ranferi_ssrsi_model_RatingRealmProxy;

@RealmClass
@Parcel(implementations = { com_ranferi_ssrsi_model_RatingRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Rating.class })
public class Rating extends RealmObject {

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


    public Rating() {
    }

    public Rating(String calificacion, String proviene) {
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
