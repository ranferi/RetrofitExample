package com.ranferi.ssrsi.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.PlacesResponse;
import com.ranferi.ssrsi.model.UserResponse;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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

        String[] places = getResources().getStringArray(R.array.places_type);
        AutoCompleteTextView editText1 = view.findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_dropdown_item_1line, places);
        /*editText1.setThreshold(1);
        editText1.setAdapter(adapter1);*/
        editText1.setEnabled(false);

        AutoCompleteTextView typePlaceAutoComplete = view.findViewById(R.id.typePlaceAutoComplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, places);
        typePlaceAutoComplete.setThreshold(1);
        typePlaceAutoComplete.setAdapter(adapter);

        String[] price = getResources().getStringArray(R.array.price);
        AutoCompleteTextView priceAutoComplete = view.findViewById(R.id.priceAutoComplete);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, price);
        priceAutoComplete.setThreshold(1);
        priceAutoComplete.setAdapter(adapter2);

        String[] distance = getResources().getStringArray(R.array.distance);
        AutoCompleteTextView distanceAutoComplete = view.findViewById(R.id.distanceAutoComplete);
        ArrayAdapter<String> adapter3 =
                new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, distance);
        distanceAutoComplete.setThreshold(1);
        distanceAutoComplete.setAdapter(adapter3);

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
            String a = typePlaceAutoComplete.getText().toString();
            String b = priceAutoComplete.getText().toString();
            String c = distanceAutoComplete.getText().toString();
            boolean d = mCheckedTextView.isChecked();
            showToastMsg(a + " " + b + " " + c + " " + d);
            // sendSearch(getActivity(), user, a, b, c, d);
        });

    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getContext(), Msg, Toast.LENGTH_SHORT).show();
    }

    private void sendSearch(Context context, int id, String typePlace, String price, String distance, boolean music) {
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

        /*Call<PlacesResponse> call = service.searchPlaces(id, typePlace, price, distance, music);

        call.enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlacesResponse> call, @NonNull Response<PlacesResponse> response) {
                progressDialog.dismiss();

                if (!response.body().getError()) {
                    Toast.makeText(context, "Gracias por tu opinión", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Hubo un problema intenta de nuevo", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlacesResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });*/
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
