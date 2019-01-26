
package com.ranferi.ssrsi.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Places {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("medi")
    @Expose
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
    @SerializedName("mNombres")
    @Expose
    private List<Nombre> mNombres = new ArrayList<Nombre>();
    @SerializedName("mCalificaciones")
    @Expose
    private List<Calificacione> mCalificaciones = new ArrayList<Calificacione>();
    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("categorias")
    @Expose
    private List<Categoria> categorias = new ArrayList<Categoria>();
    @SerializedName("mImagenes")
    @Expose
    private List<Imagene> mImagenes = new ArrayList<Imagene>();
    @SerializedName("comentarios")
    @Expose
    private List<Comentario> comentarios = new ArrayList<Comentario>();

    /**
     * No args constructor for use in serialization
     */
    public Places() {
    }

    /**
     * @param total
     * @param id
     * @param musica
     * @param direccion
     * @param imagenes
     * @param nombres
     * @param medi
     * @param comentarios
     * @param calificaciones
     * @param latitud
     * @param categorias
     * @param longitud
     */
    public Places(int id, String medi, String latitud, String longitud, String direccion, boolean musica, List<Nombre> nombres, List<Calificacione> calificaciones, double total, List<Categoria> categorias, List<Imagene> imagenes, List<Comentario> comentarios) {
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

    public List<Nombre> getNombres() {
        return mNombres;
    }

    public void setNombres(List<Nombre> mNombres) {
        this.mNombres = mNombres;
    }

    public List<Calificacione> getCalificaciones() {
        return mCalificaciones;
    }

    public void setCalificaciones(List<Calificacione> mCalificaciones) {
        this.mCalificaciones = mCalificaciones;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Imagene> getImagenes() {
        return mImagenes;
    }

    public void setImagenes(List<Imagene> mImagenes) {
        this.mImagenes = mImagenes;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

}
