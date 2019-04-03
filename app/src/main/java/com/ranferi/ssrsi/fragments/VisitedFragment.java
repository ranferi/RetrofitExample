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
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.PlacessAdapter;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.Categoria;
import com.ranferi.ssrsi.model.Place;
import com.ranferi.ssrsi.model.Places;
import com.ranferi.ssrsi.model.User;
import com.ranferi.ssrsi.model.UserPlace;
import com.ranferi.ssrsi.model.Users;


import java.util.Arrays;
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

public class VisitedFragment extends Fragment {
    private Realm realm;
    private RecyclerView mPlaceRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public VisitedFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) getActivity().setTitle("Visitados");

        final int user = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        realm = Realm.getDefaultInstance();
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                bgRealm.deleteAll();
            }
        });*/
        RealmResults<UserPlace> userPlaces = realm.where(UserPlace.class).equalTo("visitantes.id", user).findAll();

        mPlaceRecyclerView = view.findViewById(R.id.place_recycler_view);
        mPlaceRecyclerView.setHasFixedSize(true);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        APIService service = retrofit.create(APIService.class);
        Call<Users> call = service.getVisited(user);
        //Call<Places> call = service.getPlaces();

        /*call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(@NonNull Call<Places> call, @NonNull Response<Places> response) {
                if (response.isSuccessful()) {

                    RealmList<Place> places = response.body().getPlaces();

                    if (places != null) {
                        Log.d("ActividadPT", "---" + places.toString());
                        mAdapter = new PlacessAdapter(places, getActivity());
                        mPlaceRecyclerView.setAdapter(mAdapter);
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(places);
                        realm.commitTransaction();
                        realm.close();
                    } else {
                        Log.d("ActividadPT", "PlaceListFragment: List<> empty ");
                    }
                } else {
                    int statusCode = response.code();
                    Log.d("ActividadTT", "PlaceListFragment onResponse(): Error code = " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Places> call, @NonNull Throwable t) {
                Log.d("ActividadPT", "Estás en onFailure " + t.getMessage());
            }
        });*/

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful()) {

                    RealmList<User> users = response.body().getUsers();
                    RealmList<UserPlace> visitados = users.first().getVisito();

                    RealmResults<Categoria> userCategorias = realm.where(Categoria.class).findAll();

                    Iterator<UserPlace> visited = visitados.iterator();

                    while (visited.hasNext()) {
                        UserPlace userPlace = visited.next();
                        for (UserPlace sitio : userPlaces) {
                            if (userPlace.getSitioSrc().equals(sitio.getSitioSrc())) {
                                visited.remove();
                            }
                        }

                    }

                    if (visitados != null && !visitados.isEmpty()) {

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(@NonNull Realm bgRealm) {
                                bgRealm.copyToRealmOrUpdate(users);
                            }
                        });

                        RealmQuery<Place> query = realm.where(Place.class).equalTo("visitaron.visitantes.id", user);
                        List<Place> places = query.findAll();

                        mAdapter = new PlacessAdapter(places, getActivity());
                        mPlaceRecyclerView.setAdapter(mAdapter);
                    } else {
                        Log.d("ActividadPT", "VisitedFragmentFragment: List<> empty ");
                    }
                } else {
                    int statusCode = response.code();
                    Log.d("ActividadTT", "VisitedFragmentFragment onResponse(): Error code = " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
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
