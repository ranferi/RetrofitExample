
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Index;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.com_ranferi_ssrsi_model_PlaceRealmProxy;

@RealmClass
@Parcel(implementations = { com_ranferi_ssrsi_model_PlaceRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Place.class })
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
    @SerializedName("similitud")
    @Expose
    private int similitud;
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
    @LinkingObjects("sitio")
    private final RealmResults<UserPlace> visitaron = null;

    public Place() {
    }



    public Place(int id, String medi, String latitud, String longitud, String direccion, boolean musica, int similitud, double total,
                 RealmList<Nombre> nombres, RealmList<Calificacione> calificaciones, RealmList<Categoria> categorias,
                 RealmList<Imagene> imagenes, RealmList<Comentario> comentarios) {
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

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setNombres(RealmList<Nombre> mNombres) {
        this.mNombres = mNombres;
    }

    public RealmList<Calificacione> getCalificaciones() {
        return mCalificaciones;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setCalificaciones(RealmList<Calificacione> mCalificaciones) {
        this.mCalificaciones = mCalificaciones;
    }

    public int getSimilitud() {
        return similitud;
    }

    public void setSimilitud(int similitud) {
        this.similitud = similitud;
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

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setCategorias(RealmList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public RealmList<Imagene> getImagenes() {
        return mImagenes;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setImagenes(RealmList<Imagene> mImagenes) {
        this.mImagenes = mImagenes;
    }

    public RealmList<Comentario> getComentarios() {
        return comentarios;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setComentarios(RealmList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

}
