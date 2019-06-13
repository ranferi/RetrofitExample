package com.ranferi.ssrsi.model;

import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Clase abstracta para trabajar con RealmList
 * https://gist.github.com/cmelchior/72c35fcb55cec33a71e1
 * @param <T>
 */
public abstract class RealmListParcelConverter<T extends RealmObject> extends CollectionParcelConverter<T, RealmList<T>> {
    @Override
    public RealmList<T> createCollection() {
        return new RealmList<T>();
    }
}
