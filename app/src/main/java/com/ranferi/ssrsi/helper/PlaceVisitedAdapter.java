package com.ranferi.ssrsi.helper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.activities.PlacePagerActivity;
import com.ranferi.ssrsi.model.Comentario;
import com.ranferi.ssrsi.model.Nombre;
import com.ranferi.ssrsi.model.Place;
import com.ranferi.ssrsi.model.User;
import com.ranferi.ssrsi.model.UserPlace;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class PlaceVisitedAdapter extends RecyclerView.Adapter<PlaceVisitedAdapter.PlaceHolder> {
    private List<Place> mPlaces;
    private Context sContext;
    private int id;

    public PlaceVisitedAdapter(List<Place> places, Context context, int id) {
        this.mPlaces = places;
        this.sContext = context;
        this.id = id;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new PlaceHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder placeHolder, int i) {
        Place place = mPlaces.get(i);
        // Log.d("ActividadPT", String.valueOf(place.getId()));
        placeHolder.bind(place);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAddressTextView;
        private Place mPlace;
        private ImageView mSolvedImageView;
        private CheckBox visited;
        private CheckBox liked;
        private TextView similarity;
        private Realm realm;
        UserPlace userPlaces;


        PlaceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place_visited, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.place_name);
            mAddressTextView = itemView.findViewById(R.id.place_address);
            mSolvedImageView = itemView.findViewById(R.id.place_solved);
            visited = itemView.findViewById(R.id.visited);
            liked = itemView.findViewById(R.id.liked);
            similarity = itemView.findViewById(R.id.similarity);
            realm = Realm.getDefaultInstance();
        }

        void bind(Place place) {
            mPlace = place;
            userPlaces = realm.where(UserPlace.class).equalTo("visitantes.id", id).findAll()
                    .where().equalTo("sitio.id", mPlace.getId()).findFirst();

            if (mPlace.getNombres().size() != 0) {
                Nombre nombre1 = mPlace.getNombres().get(0);
                mNameTextView.setText(nombre1.getNombreSitio());
            }

            mAddressTextView.setText(mPlace.getDireccion());
            mSolvedImageView.setVisibility(View.VISIBLE);
            if (userPlaces != null) {
                visited.setChecked(true);
                liked.setChecked(userPlaces.isGusto());
            }
            similarity.setText("Similitud : " + mPlace.getSimilitud());
            visited.setClickable(false);
            liked.setClickable(false);
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