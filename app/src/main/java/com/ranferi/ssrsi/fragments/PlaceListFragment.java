package com.ranferi.ssrsi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.PlaceVisitedAdapter;
import com.ranferi.ssrsi.helper.PlacesAdapter;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.Comentario;
import com.ranferi.ssrsi.model.Place;
import com.ranferi.ssrsi.model.Places;
import com.ranferi.ssrsi.model.User;
import com.ranferi.ssrsi.model.UserPlace;
import com.ranferi.ssrsi.model.Users;


import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceListFragment extends Fragment {
    private Realm realm;
    private RecyclerView mPlaceRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public PlaceListFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ActividadPT", "------------ PlaceListFragment, onViewCreated --- ");

        if (getActivity() != null) getActivity().setTitle("Sitios");

        realm = Realm.getDefaultInstance();
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                bgRealm.deleteAll();
            }
        });*/

        final int user = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        UserPlace userPlaces = realm.where(UserPlace.class).equalTo("visitantes.id", user).findFirst();
        Log.d("ActividadPT", String.valueOf(user));
        Log.d("ActividadPT", String.valueOf(userPlaces));

        mPlaceRecyclerView = view.findViewById(R.id.place_recycler_view);
        mPlaceRecyclerView.setHasFixedSize(true);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();*/

        Retrofit retrofit = new Retrofit.Builder()
                // .client(client)
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Users> call1 = service.getVisited(user);
        call1.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RealmList<User> users = response.body().getUsers();
                    RealmList<UserPlace> visitados = users.first().getVisito();
                    if (!visitados.isEmpty())
                        realm.executeTransaction(bgRealm -> bgRealm.copyToRealmOrUpdate(users));
                } else {
                    Log.d("ActividadPT", "VisitedFragmentFragment onResponse(): Error code = " + response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                Log.d("ActividadPT", "Estás en onFailure " + t.getMessage());
            }
        });


        Call<Places> call2 = service.getPlaces();
        call2.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(@NonNull Call<Places> call, @NonNull Response<Places> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RealmList<Place> places = response.body().getPlaces();

                    for (Place visitedPlace : places) {
                        Place place = realm.where(Place.class).equalTo("id", visitedPlace.getId()).findFirst();
                        if (place == null) {
                            realm.executeTransaction(realm1 -> {
                                realm.copyToRealmOrUpdate(visitedPlace);
                            });
                        }
                        if (visitedPlace.getComentarios() != null) {
                            RealmList<Comentario> comentarios = visitedPlace.getComentarios();
                            for (Comentario comentario : comentarios) {
                                User userVisited = comentario.getUser();
                                if (userVisited != null && userVisited.getId() == user) {
                                    Comentario comment = realm
                                            .where(Comentario.class)
                                            .equalTo("id", comentario.getId()).findFirst();
                                    if (comment != null) {
                                        realm.executeTransaction(realm1 -> realm.copyToRealm(comment));
                                    }
                                }
                            }
                        }
                    }
                    RealmResults<Place> places1 = realm.where(Place.class).findAll().sort("id");
                    mAdapter = new PlaceVisitedAdapter(places1, getActivity(), user);
                    mPlaceRecyclerView.setAdapter(mAdapter);

                } else {
                    int statusCode = response.code();
                    Log.d("ActividadPT", "PlaceListFragment onResponse(): Error code = " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Places> call, @NonNull Throwable t) {
                Log.d("ActividadPT", "Estás en onFailure " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

}
