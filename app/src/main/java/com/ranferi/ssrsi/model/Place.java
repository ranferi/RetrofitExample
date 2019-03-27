
package com.ranferi.ssrsi.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Place extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @Index
    private int id;
    @SerializedName("medi")
    @Expose
    @Index
    private String medi;
    @SerializedName("latitud")
    @Expose
    private String latitud;
    @SerializedName("longitud")
    @Expose
    private String longitud;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("musica")
    @Expose
    private boolean musica;
    @SerializedName("nombres")
    @Expose
    private RealmList<Nombre> mNombres = new RealmList<>();
    @SerializedName("calificaciones")
    @Expose
    private RealmList<Calificacione> mCalificaciones = new RealmList<>();
    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("categorias")
    @Expose
    private RealmList<Categoria> categorias = new RealmList<>();
    @SerializedName("imagenes")
    @Expose
    private RealmList<Imagene> mImagenes = new RealmList<>();
    @SerializedName("comentarios")
    @Expose
    private RealmList<Comentario> comentarios = new RealmList<>();
    private RealmList<UserPlace> visitaron = new RealmList<>();

    /**
     * No args constructor for use in serialization
     */
    public Place() {
    }


    public Place(int id, String medi, String latitud, String longitud, String direccion,
                 boolean musica, RealmList<Nombre> nombres, RealmList<Calificacione> calificaciones,
                 double total, RealmList<Categoria> categorias, RealmList<Imagene> imagenes,
                 RealmList<Comentario> comentarios) {
        super();
        this.id = id;
        this.medi = medi;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.musica = musica;
        this.mNombres = nombres;
        this.mCalificaciones = calificaciones;
        this.total = total;
        this.categorias = categorias;
        this.mImagenes = imagenes;
        this.comentarios = comentarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedi() {
        return medi;
    }

    public void setMedi(String medi) {
        this.medi = medi;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }


    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isMusica() {
        return musica;
    }

    public void setMusica(boolean musica) {
        this.musica = musica;
    }

    public RealmList<Nombre> getNombres() {
        return mNombres;
    }

    public void setNombres(RealmList<Nombre> mNombres) {
        this.mNombres = mNombres;
    }

    public RealmList<Calificacione> getCalificaciones() {
        return mCalificaciones;
    }

    public void setCalificaciones(RealmList<Calificacione> mCalificaciones) {
        this.mCalificaciones = mCalificaciones;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public RealmList<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(RealmList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public RealmList<Imagene> getImagenes() {
        return mImagenes;
    }

    public void setImagenes(RealmList<Imagene> mImagenes) {
        this.mImagenes = mImagenes;
    }

    public RealmList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(RealmList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public RealmList<UserPlace> getVisitaron() {
        return visitaron;
    }

    public void setVisitaron(RealmList<UserPlace> visitaron) {
        this.visitaron = visitaron;
    }
}
