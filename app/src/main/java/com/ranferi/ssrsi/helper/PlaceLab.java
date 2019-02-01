package com.ranferi.ssrsi.helper;

import android.content.Context;

import com.ranferi.ssrsi.model.Place;

import java.util.ArrayList;
import java.util.List;

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
            crime.setDireccion("Sitio #" + i);
            crime.setMusica(i % 2 == 0); // Every other one
            mPlaces.add(crime);
        }
    }

    public List<Place> getPlaces() {
        return mPlaces;
    }

    public Place getPlace(int id) {
        for (Place place : mPlaces) {
            if (place.getId() == id) {
                return place;
            }
        }
        return null;
    }
}
