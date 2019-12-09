
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.com_ranferi_ssrsi_model_CommentRealmProxy;

@RealmClass
@Parcel(implementations = { com_ranferi_ssrsi_model_CommentRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Comment.class })
public class Comment extends RealmObject {
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
    @LinkingObjects("comentarioUsuario")
    private final RealmResults<UserPlace> comentariosUsuario = null;

    public Comment() {
    }

    /**
     *
     * @param comentario del usuario
     * @param proviene en cual BD se encuentra
     */
    public Comment(String comentario, String proviene) {
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
    public Comment(String comentario, String proviene, User user) {
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
