package com.ranferi.ssrsi.helper;

import android.content.Context;

import com.ranferi.ssrsi.model.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlaceLab {

    private static PlaceLab sPlaceLab;

    private List<Place> mPlaces;

    public static PlaceLab get(Context context) {
        if (sPlaceLab == null) {
            sPlaceLab = new PlaceLab(context);
        }
        return sPlaceLab;
    }
    private PlaceLab(Context context) {
        mPlaces = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Place crime = new Place();
            crime.setNombre("Sitio #" + i);
            crime.setMusica(i % 2 == 0); // Every other one
            mPlaces.add(crime);
        }
    }
    public List<Place> getPlaces() {
        return mPlaces;
    }
    public Place getPlace(UUID id) {
        for (Place place : mPlaces) {
            if (place.getId().equals(id)) {
                return place;
            }
        }
        return null;
    }
}
