package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.com_ranferi_ssrsi_model_UserPlaceRealmProxy;

@RealmClass
@Parcel(implementations = { com_ranferi_ssrsi_model_UserPlaceRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { UserPlace.class })
public class UserPlace extends RealmObject {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
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
    private Comment comentarioUsuario;
    @SerializedName("sitio")
    @Expose
    private RealmList<Place> sitio = new RealmList<>();
    @LinkingObjects("visito")
    private final RealmResults<User> visitantes = null;

    public UserPlace() {
    }

    public UserPlace(int id, String sitioSrc, String precio, boolean gusto,
    @ParcelPropertyConverter(RealmListParcelConverter.class) RealmList<Place> sitio) {
        super();
        this.id = id;
        this.sitioSrc = sitioSrc;
        this.precio = precio;
        this.gusto = gusto;

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

    public RealmList<Place> getSitio() {
        return sitio;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setSitio(RealmList<Place> sitio) {
        this.sitio = sitio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comment getComentarioUsuario() {
        return comentarioUsuario;
    }

    public void setComentarioUsuario(Comment comentarioUsuario) {
        this.comentarioUsuario = comentarioUsuario;
    }
}
