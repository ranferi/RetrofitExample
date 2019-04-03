
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Comentario extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("proviene")
    @Expose
    private String proviene;
    @SerializedName("user")
    public User user;
    @LinkingObjects("comentarios")
    private final RealmResults<Place> comentariosSitio = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Comentario() {
    }

    /**
     *
     * @param comentario del usuario
     * @param proviene en cual BD se encuentra
     */
    public Comentario(String comentario, String proviene) {
        super();
        this.comentario = comentario;
        this.proviene = proviene;
    }


    /**
     *
     * @param comentario del usuario
     * @param proviene de que BD
     * @param user de que usuario
     */
    public Comentario(String comentario, String proviene, User user) {
        super();
        this.comentario = comentario;
        this.proviene = proviene;
        this.user = user;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getProviene() {
        return proviene;
    }

    public void setProviene(String proviene) {
        this.proviene = proviene;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
