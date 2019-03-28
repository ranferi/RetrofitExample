package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Index;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class UserPlace extends RealmObject {
    @SerializedName("sitio_src")
    @Expose
    private String sitioSrc;
    @SerializedName("precio")
    @Expose
    private String precio;
    @SerializedName("gusto")
    @Expose
    private boolean gusto;
    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("sitio")
    @Expose
    private RealmList<Place> sitio = new RealmList<>();
    @LinkingObjects("visito")
    private final RealmResults<User> visitantes = null;


    public UserPlace() {
    }

    /**
     * @param sitioSrc
     * @param precio
     * @param gusto
     * @param comentario
     * @param sitio
     */
    public UserPlace(String sitioSrc, String precio, boolean gusto, String comentario, RealmList<Place> sitio) {
        super();
        this.sitioSrc = sitioSrc;
        this.precio = precio;
        this.gusto = gusto;
        this.comentario = comentario;
        this.sitio = sitio;
    }

    public String getSitioSrc() {
        return sitioSrc;
    }

    public void setSitioSrc(String sitioSrc) {
        this.sitioSrc = sitioSrc;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public boolean isGusto() {
        return gusto;
    }

    public void setGusto(boolean gusto) {
        this.gusto = gusto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public RealmList<Place> getSitio() {
        return sitio;
    }

    public void setSitio(RealmList<Place> sitio) {
        this.sitio = sitio;
    }

}
