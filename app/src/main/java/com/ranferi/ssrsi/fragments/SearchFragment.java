package com.ranferi.ssrsi.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.Comment;
import com.ranferi.ssrsi.model.Place;
import com.ranferi.ssrsi.model.PlacesResponse;
import com.ranferi.ssrsi.model.Types;
import com.ranferi.ssrsi.model.User;
import com.ranferi.ssrsi.model.UserPlace;
import com.ranferi.ssrsi.model.Users;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ranferi.ssrsi.api.APIUrl.latitud;
import static com.ranferi.ssrsi.api.APIUrl.longitud;

//import android.util.Log;


public class SearchFragment extends Fragment {

    private CheckedTextView mCheckedTextView;
    private Realm realm;
    private int user;

    private Spinner mSpinnerPrice;
    private Spinner mSpinnerCats;
    private Spinner mSpinnerDistance;
    private Button mSearchButton;


    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Log.d("ActividadPT", "------------ SearchFragment, onViewCreated --- ");
        RealmConfiguration config2 = new RealmConfiguration.Builder()
                .name("ssrsi.realm")
                .deleteRealmIfMigrationNeeded()
                .build();


        realm = Realm.getInstance(config2);

        /*realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                bgRealm.deleteAll();
            }
        });*/

        user = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        UserPlace userPlaces = realm.where(UserPlace.class).equalTo("visitantes.id", user).findFirst();

        AutoCompleteTextView editText1 = view.findViewById(R.id.autoCompleteTextView);
        // ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_dropdown_item_1line, places);
        /*editText1.setThreshold(1);
        editText1.setAdapter(adapter1);*/
        editText1.setEnabled(false);

        // String[] places = getResources().getStringArray(R.array.places_type);
        // List<String> placesList = new ArrayList<>(Arrays.asList(places));

        mSpinnerCats = view.findViewById(R.id.typePlaceAutoComplete);

        final ArrayAdapter<String> stringArrayCatsAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, Types.strCatsNames) {
            @Override
            public boolean isEnabled (int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) tv.setTextColor(getResources().getColor(R.color.base));
                else tv.setTextColor(Color.BLACK);

                return view;
            }
        };
        stringArrayCatsAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerCats.setAdapter(stringArrayCatsAdapter);

        String[] price = getResources().getStringArray(R.array.price);
        List<String> priceList = new ArrayList<>(Arrays.asList(price));

        mSpinnerPrice = view.findViewById(R.id.priceAutoComplete);
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, priceList) {
            @Override
            public boolean isEnabled (int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) tv.setTextColor(getResources().getColor(R.color.base));
                else tv.setTextColor(Color.BLACK);

                return view;
            }
        };
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerPrice.setAdapter(spinnerArrayAdapter2);

        String[] distance = getResources().getStringArray(R.array.distance);
        List<String> distanceList = new ArrayList<>(Arrays.asList(distance));

        mSpinnerDistance = view.findViewById(R.id.distanceAutoComplete);
        final ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, distanceList) {
            @Override
            public boolean isEnabled (int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) tv.setTextColor(getResources().getColor(R.color.base));
                else tv.setTextColor(Color.BLACK);

                return view;
            }
        };
        spinnerArrayAdapter3.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerDistance.setAdapter(spinnerArrayAdapter3);

        mCheckedTextView = view.findViewById(R.id.checkedTextView);
        mCheckedTextView.setOnClickListener(view1 -> {
            if (mCheckedTextView.isChecked()) {
                mCheckedTextView.setChecked(false);
                mCheckedTextView.setText(getString(R.string.with_music));
            } else {
                mCheckedTextView.setChecked(true);
                mCheckedTextView.setText(getString(R.string.without_music));
            }
        });

        mSearchButton = view.findViewById(R.id.button2);
        mSearchButton.setOnClickListener(v -> {
            String cat = Types.strCatsCodes[mSpinnerCats.getSelectedItemPosition()];
            String prices = mSpinnerPrice.getSelectedItem().toString();
            String dist = mSpinnerDistance.getSelectedItem().toString();
            boolean music = mCheckedTextView.isChecked();
            showToastMsg("Categoría: " + cat + " precio: " + prices + " distancia: " + dist + " música: " + music + " latitud: " + latitud + " longitud: " + longitud);
            sendSearch(getActivity(), cat, prices, dist, music, latitud, longitud);
        });

    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getContext(), Msg, Toast.LENGTH_SHORT).show();
    }

    private void sendSearch(Context context, String typePlace, String price, String distance, boolean music, double latitud, double longitud) {

        if (!validate()) {
            onValidationFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando...");
        progressDialog.show();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /*OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();*/

        OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
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
                    //Log.d("ActividadPT", "SearchFragment onResponse(): Error code = " + response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                //Log.d("ActividadPT", "Estás en onFailure " + t.getMessage());
            }
        });

       Call<PlacesResponse> call2 = service.searchPlaces(user, typePlace, price, distance, music);
        call2.enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlacesResponse> call, @NonNull Response<PlacesResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null && !response.body().getError()) {
                    RealmList<Place> places = response.body().getPlaces();

                    for (Place visitedPlace : places) {
                        Place place = realm.where(Place.class).equalTo("id", visitedPlace.getId()).findFirst();
                        if (place == null)
                            realm.executeTransaction(realm1 -> realm.copyToRealmOrUpdate(visitedPlace));

                        if (visitedPlace.getComentarios() != null) {
                            RealmList<Comment> comentarios = visitedPlace.getComentarios();
                            for (Comment comentario : comentarios) {
                                User userVisited = comentario.getUser();
                                if (userVisited != null && userVisited.getId() == user) {
                                    Comment c = realm.where(Comment.class).equalTo("id", comentario.getId()).findFirst();
                                    if (c != null) realm.executeTransaction(realm1 -> realm.copyToRealm(c));
                                }
                            }
                        }
                    } 

                    SearchListFragment fragment = new SearchListFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("places", Parcels.wrap(response.body()));
                    fragment.setArguments(args);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.content_frame, fragment).commit();

                    Toast.makeText(context, "Espera un momento", Toast.LENGTH_LONG).show();
                } else {
                    if (response.body() != null) Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    else Toast.makeText(context, "Hubo un problema, intenta de nuevo", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlacesResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                // Log.d("ActividadPT", t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public boolean validate() {
        boolean valid = true;

        if(mSpinnerCats == null && mSpinnerCats.getSelectedItem() == null ) {
            valid = false;
            //Log.d("ActividadPT", "s:" + mSpinnerCats.getSelectedItem());
        } else if (mSpinnerDistance == null && mSpinnerDistance.getSelectedItem() == null) {
            valid = false;
            //Log.d("ActividadPT", "s:" + mSpinnerDistance.getSelectedItem());
        } else if (mSpinnerPrice == null && mSpinnerPrice.getSelectedItem() == null) {
            valid = false;
            // Log.d("ActividadPT", "s:" + mSpinnerPrice.getSelectedItem());
        }
        return valid;
    }

    public void onValidationFailed() {
        showToastMsg("Faltan campos");

    }
}
