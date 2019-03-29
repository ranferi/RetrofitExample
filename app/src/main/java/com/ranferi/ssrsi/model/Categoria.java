
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

public class Categoria extends RealmObject {

    @SerializedName("categoria")
    @Expose
    private String categoria;
    @SerializedName("proviene")
    @Expose
    private String proviene;
    @LinkingObjects("categorias")
    private final RealmResults<Place> categoriasSitio = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Categoria() {
    }

    /**
     * 
     * @param categoria
     * @param proviene
     */
    public Categoria(String categoria, String proviene) {
        super();
        this.categoria = categoria;
        this.proviene = proviene;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProviene() {
        return proviene;
    }

    public void setProviene(String proviene) {
        this.proviene = proviene;
    }

}
