package com.ranferi.ssrsi.model;

import java.util.Date;
import java.util.UUID;

public class Place1 {

    private UUID mId;
    private String nombre;
    private boolean musica;
    private double lat;
    private double lon;
    private String direccion;

    private Date mDate;

    public Place1() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isMusica() {
        return musica;
    }

    public void setMusica(boolean musica) {
        this.musica = musica;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getDireccion() {
        return direccion;
    }


    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }

}
