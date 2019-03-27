package com.ranferi.ssrsi.model;


import io.realm.RealmList;
import io.realm.RealmObject;

public class Users extends RealmObject {

    private RealmList<User> users;

    public Users() { }

    public RealmList<User> getUsers() {
        return users;
    }

    public void setUsers(RealmList<User> users) {
        this.users = users;
    }

}
