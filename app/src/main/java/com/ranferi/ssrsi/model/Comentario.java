
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comentario {

    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("proviene")
    @Expose
    private String proviene;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Comentario() {
    }

    /**
     * 
     * @param proviene
     * @param comentario
     */
    public Comentario(String comentario, String proviene) {
        super();
        this.comentario = comentario;
        this.proviene = proviene;
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

}
