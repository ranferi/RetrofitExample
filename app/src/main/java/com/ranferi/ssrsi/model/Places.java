
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
    @SerializedName("nombres")
    @Expose
    private List<Nombres> nombres = new ArrayList<Nombres>();
    @SerializedName("calificaciones")
    @Expose
    private List<Calificaciones> calificaciones = new ArrayList<Calificaciones>();
    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("categorias")
    @Expose
    private List<Categoria> categorias = new ArrayList<Categoria>();
    @SerializedName("imagenes")
    @Expose
    private List<Imagenes> imagenes = new ArrayList<Imagenes>();
    @SerializedName("comentarios")
    @Expose
    private List<Comentario> comentarios = new ArrayList<Comentario>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Places() {
    }

    /**
     * 
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
    public Places(int id, String medi, String latitud, String longitud, String direccion, boolean musica, List<Nombres> nombres, List<Calificaciones> calificaciones, double total, List<Categoria> categorias, List<Imagenes> imagenes, List<Comentario> comentarios) {
        super();
        this.id = id;
        this.medi = medi;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.musica = musica;
        this.nombres = nombres;
        this.calificaciones = calificaciones;
        this.total = total;
        this.categorias = categorias;
        this.imagenes = imagenes;
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

    public List<Nombres> getNombres() {
        return nombres;
    }

    public void setNombres(List<Nombres> nombres) {
        this.nombres = nombres;
    }

    public List<Calificaciones> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Calificaciones> calificaciones) {
        this.calificaciones = calificaciones;
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

    public List<Imagenes> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagenes> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

}
