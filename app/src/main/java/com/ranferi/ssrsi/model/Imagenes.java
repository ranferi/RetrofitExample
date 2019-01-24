
package com.ranferi.ssrsi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Imagenes {

    @SerializedName("imagen")
    @Expose
    private String imagen;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Imagenes() {
    }

    /**
     * 
     * @param imagen
     */
    public Imagenes(String imagen) {
        super();
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
