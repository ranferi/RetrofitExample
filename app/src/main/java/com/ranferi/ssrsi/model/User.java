package com.ranferi.ssrsi.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class User extends RealmObject {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("mothersMaidenName")
    @Expose
    private String mothersMaidenName;
    @SerializedName("usuario")
    @Expose
    private String user;
    @SerializedName("email")
    @Expose
    private String email;
    private String password;

    public User() {}

    // NUEVA En SignUpActivity.java, lo usamos para CREAR un usuario
    public User(String name, String lastName, String mothersMaidenName, String user, String email, String password) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.mothersMaidenName = mothersMaidenName;
        this.user = user;
        this.email = email;
        this.password = password;
    }

    // NUEVA En ProfileFragment.java, para ver y actualizar información
    public User(int id, String name, String lastName, String mothersMaidenName, String user, String email, String password) {
        super();
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mothersMaidenName = mothersMaidenName;
        this.user = user;
        this.email = email;
        this.password = password;
    }

    // NUEVA En SharedPrefManager.java, para obtener información del usuario (sin password)
    public User(int id, String name, String lastName, String mothersMaidenName, String user, String email) {
        super();
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mothersMaidenName = mothersMaidenName;
        this.user = user;
        this.email = email;
    }

    // En SignUpActivity.java, lo usamos para CREAR un usuario
    public User(String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        // this.gender = gender;
    }

    // En ProfileFragment.java, para ver y actualizar información
    public User(int id, String name, String email, String password) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        // this.gender = gender;
    }

    // En SharedPrefManager.java, para obtener información del usuario (sin password)
    public User(int id, String name, String email) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        // this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMothersMaidenName() {
        return mothersMaidenName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMothersMaidenName(String mothersMaidenName) {
        this.mothersMaidenName = mothersMaidenName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mothersMaidenName='" + mothersMaidenName + '\'' +
                ", user='" + user + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
