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
        private Realm realm;
        UserPlace userPlaces;

        public PlaceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place_visited, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.place_name);
            mAddressTextView = itemView.findViewById(R.id.place_address);
            mSolvedImageView = itemView.findViewById(R.id.place_solved);
            visited = itemView.findViewById(R.id.visited);
            liked = itemView.findViewById(R.id.liked);
            realm = Realm.getDefaultInstance();
        }

        public void bind(Place place) {
            Log.d("ActividadPT", "------------ PlaceVisitedAdapter, PlaceHolder, bind --- ");
            mPlace = place;
            userPlaces = realm.where(UserPlace.class).equalTo("visitantes.id", id).findAll()
                    .where().equalTo("sitio.id", mPlace.getId()).findFirst();
            Log.d("ActividadPT", String.valueOf(id));
            Log.d("ActividadPT", String.valueOf(userPlaces));

            if (mPlace.getNombres().size() != 0) {
                Nombre nombre1 = mPlace.getNombres().get(0);
                mNameTextView.setText(nombre1.getNombreSitio());
            }

            if (!mPlace.getComentarios().isEmpty()) {
                RealmList<Comentario> comentarios = mPlace.getComentarios();
                for (Comentario comentario : comentarios) {
                    if (comentario.getUser() != null) {
                        User user = comentario.getUser();
                        int userId = user.getId();
                        if (userId == id) {
                            Log.d("ActividadPT", comentario.getComentario());
                        }
                    }
                }
            }
            mAddressTextView.setText(mPlace.getDireccion());
            mSolvedImageView.setVisibility(View.VISIBLE);
            if (userPlaces != null) {
                visited.setChecked(userPlaces.isGusto());
                liked.setChecked(userPlaces.isGusto());
            }
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