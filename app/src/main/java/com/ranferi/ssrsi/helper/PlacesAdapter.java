package com.ranferi.ssrsi.helper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.activities.PlacePagerActivity;
import com.ranferi.ssrsi.model.Nombre;
import com.ranferi.ssrsi.model.Place;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesHolder> {
    private List<Place> mPlaces;
    private Context sContext;

    public PlacesAdapter(List<Place> places, Context context) {
        this.mPlaces = places;
        this.sContext = context;
    }

    @NonNull
    @Override
    public PlacesAdapter.PlacesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new PlacesAdapter.PlacesHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.PlacesHolder placeHolder, int i) {
        Place place = mPlaces.get(i);
        placeHolder.bind(place);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public class PlacesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAddressTextView;
        private Place mPlace;
        private ImageView mSolvedImageView;

        public PlacesHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.place_name);
            mAddressTextView = itemView.findViewById(R.id.place_address);
            mSolvedImageView = itemView.findViewById(R.id.place_solved);
        }

        public void bind(Place place) {
            mPlace = place;
            if (mPlace.getNombres().size() != 0) {
                Nombre nombre1 = mPlace.getNombres().get(0);
               mNameTextView.setText(nombre1.getNombreSitio());
            }
            mAddressTextView.setText(mPlace.getDireccion());
            mSolvedImageView.setVisibility( View.VISIBLE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = PlacePagerActivity.newIntent(sContext, mPlace.getId()); // PlacePagerActivity
            sContext.startActivity(intent);
            if (mPlace.getNombres().size() != 0) {
                Nombre nombre1 = mPlace.getNombres().get(0);
                Toast.makeText(sContext, nombre1.getNombreSitio(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
