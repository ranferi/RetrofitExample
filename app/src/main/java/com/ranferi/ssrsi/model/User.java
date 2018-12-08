package com.ranferi.ssrsi.model;

public class User {

    private int id;
    private String name;
    private String lastName;
    private String mothersMaidenName;
    private String user;
    private String email;
    private String password;

    // private String gender;

    // NUEVA En SignUpActivity.java, lo usamos para CREAR un usuario
    public User(String name, String lastName, String mothersMaidenName, String user, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.mothersMaidenName = mothersMaidenName;
        this.user = user;
        this.email = email;
        this.password = password;
    }

    // NUEVA En ProfileFragment.java, para ver y actualizar información
    public User(int id, String name, String lastName, String mothersMaidenName, String user, String email, String password) {
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
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mothersMaidenName = mothersMaidenName;
        this.user = user;
        this.email = email;
    }

    // En SignUpActivity.java, lo usamos para CREAR un usuario
    public User(String name, String email, String password, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        // this.gender = gender;
    }

    // En ProfileFragment.java, para ver y actualizar información
    public User(int id, String name, String email, String password, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        // this.gender = gender;
    }

    // En SharedPrefManager.java, para obtener información del usuario (sin password)
    public User(int id, String name, String email, String gender) {
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

//    public String getGender() {
//        return gender;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mothersMaidenName='" + mothersMaidenName + '\'' +
                ", user='" + user + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                // ", gender='" + gender + '\'' +
                '}';
    }
}
