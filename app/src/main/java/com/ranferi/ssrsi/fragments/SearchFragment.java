package com.ranferi.ssrsi.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.ranferi.ssrsi.model.PlacesResponse;
import com.ranferi.ssrsi.model.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ranferi.ssrsi.api.APIUrl.latitud;
import static com.ranferi.ssrsi.api.APIUrl.longitud;


public class SearchFragment extends Fragment {

    private CheckedTextView mCheckedTextView;
    private AutoCompleteTextView editText;


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

        final int user = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        AutoCompleteTextView editText1 = view.findViewById(R.id.autoCompleteTextView);
        // ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_dropdown_item_1line, places);
        /*editText1.setThreshold(1);
        editText1.setAdapter(adapter1);*/
        editText1.setEnabled(false);

        String[] places = getResources().getStringArray(R.array.places_type);
        List<String> placesList = new ArrayList<>(Arrays.asList(places));
        Spinner spinner1 = view.findViewById(R.id.typePlaceAutoComplete);
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, placesList) {
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
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(spinnerArrayAdapter1);

        String[] price = getResources().getStringArray(R.array.price);
        List<String> priceList = new ArrayList<>(Arrays.asList(price));
        Spinner spinner2 = view.findViewById(R.id.priceAutoComplete);
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
        spinner2.setAdapter(spinnerArrayAdapter2);

        String[] distance = getResources().getStringArray(R.array.distance);
        List<String> distanceList = new ArrayList<>(Arrays.asList(distance));
        Spinner spinner3 = view.findViewById(R.id.distanceAutoComplete);
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
        spinner3.setAdapter(spinnerArrayAdapter3);

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

        Button searchButton = view.findViewById(R.id.button2);
        searchButton.setOnClickListener(v -> {
            String a = spinner1.getSelectedItem().toString();
            String b = spinner2.getSelectedItem().toString();
            String c = spinner3.getSelectedItem().toString();
            boolean d = mCheckedTextView.isChecked();
            showToastMsg(a + " " + b + " " + c + " " + d + " " + latitud + " " + longitud);
            sendSearch(getActivity(), user, a, b, c, d, latitud, longitud);
        });

    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getContext(), Msg, Toast.LENGTH_SHORT).show();
    }

    private void sendSearch(Context context, int id, String typePlace, String price, String distance, boolean music, double latitud, double longitud) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando...");
        progressDialog.show();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<PlacesResponse> call = service.searchPlaces(id, typePlace, price, distance, music);

        call.enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlacesResponse> call, @NonNull Response<PlacesResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (!response.body().getError()) {
                        Toast.makeText(context, "Espera un momento", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Hubo un problema intenta de nuevo", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlacesResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
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
}
