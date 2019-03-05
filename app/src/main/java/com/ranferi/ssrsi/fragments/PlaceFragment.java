package com.ranferi.ssrsi.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.ViewPagerAdapter;
import com.ranferi.ssrsi.model.Calificacione;
import com.ranferi.ssrsi.model.Categoria;
import com.ranferi.ssrsi.model.Comentario;
import com.ranferi.ssrsi.model.Nombre;
import com.ranferi.ssrsi.model.Place;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;


public class PlaceFragment extends Fragment {

    private static final String ARG_PLACE_ID = "place_id";

    private Realm realm;
    MapView mMapView;
    private GoogleMap googleMap;

    public PlaceFragment() {
    }

    public static PlaceFragment newInstance(int placeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLACE_ID, placeId);
        PlaceFragment fragment = new PlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place, container, false);
        int placeId = (int) getArguments().getSerializable(ARG_PLACE_ID);
        realm = Realm.getDefaultInstance();

        mMapView = v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        ViewPager viewPager = v.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        final PageIndicatorView pageIndicatorView = v.findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(viewPagerAdapter.getCount()); // especifica el total de indicadores

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                pageIndicatorView.setSelection(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        RealmQuery<Place> query = realm.where(Place.class);
        Place place = query.equalTo("id", placeId).findFirst();
        List<Nombre> nombresSitio = place.getNombres();
        List<Categoria> categoriasSitio = place.getCategorias();
        List<Calificacione> calificacionesSitios = place.getCalificaciones();
        List<Comentario> comentariosSitios = place.getComentarios();

        List<TextView> nombresTextViews = new ArrayList<>();
        List<TextView> categoriasTextViews = new ArrayList<>();
        List<TextView> categoriasGooglePlacesTextViews = new ArrayList<>();
        List<TextView> calificacionesTextViews = new ArrayList<>();
        List<TextView> comentariosTextViews = new ArrayList<>();

        TextView nameField = v.findViewById(R.id.place_name);
        nameField.setText(nombresSitio.get(0).getNombreSitio());

        ConstraintLayout layout = v.findViewById(R.id.linearLayout);
        ConstraintSet set = new ConstraintSet();

        // margenes
        int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, getResources().getDisplayMetrics());
        int sideMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                25, getResources().getDisplayMetrics());
        int bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                24, getResources().getDisplayMetrics());

        for (Nombre nombre : nombresSitio) {
            TextView textView1 = new TextView(getActivity());
            textView1.setId(generateId());
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
            textView1.setText(buildStringWithIcon(getActivity().getApplicationContext(), nombre.getNombreSitio() + " (de ", getIconResource(nombre.getProviene())));
            textView1.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
            nombresTextViews.add(textView1);
            layout.addView(textView1);
        }

        for (Categoria categoria : categoriasSitio) {
            if (categoria.getProviene().equals("GooglePlaces")) {
                TextView textView1 = new TextView(getActivity());
                textView1.setId(generateId());
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
                textView1.setText(categoria.getCategoria());
                textView1.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                categoriasGooglePlacesTextViews.add(textView1);
                layout.addView(textView1);
            }
        }

        for (Categoria categoria : categoriasSitio) {
            TextView textView1 = new TextView(getActivity());
            textView1.setId(generateId());
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
            textView1.setText(buildStringWithIcon(getActivity().getApplicationContext(), categoria.getCategoria() + " (de ", getIconResource(categoria.getProviene())));
            textView1.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
            categoriasTextViews.add(textView1);
            layout.addView(textView1);
        }

        for (Calificacione calificacione : calificacionesSitios) {
            TextView textView1 = new TextView(getActivity());
            textView1.setId(generateId());
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
            textView1.setText(buildStringWithIcon(getActivity().getApplicationContext(), calificacione.getCalificacion() + " (de ", getIconResource(calificacione.getProviene())));
            textView1.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
            calificacionesTextViews.add(textView1);
            layout.addView(textView1);
        }

        for (Comentario comentario : comentariosSitios) {
            TextView textView1 = new TextView(getActivity());
            textView1.setId(generateId());
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
            textView1.setText(buildStringWithIcon(getActivity().getApplicationContext(), comentario.getComentario() + " (de ", getIconResource(comentario.getProviene())));
            textView1.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
            comentariosTextViews.add(textView1);
            layout.addView(textView1);
        }

        set.clone(layout);

        int topFieldId = R.id.namesInfo;
        int currentId;
        for (Iterator<TextView> it = nombresTextViews.iterator(); it.hasNext();) {
            currentId = it.next().getId();
            set.connect(currentId, ConstraintSet.TOP, topFieldId, ConstraintSet.BOTTOM, topMargin);
            set.connect(currentId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
            set.connect(currentId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
            if (!it.hasNext()) {
                set.connect(R.id.moreCategories, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
            } else {
                int next = it.next().getId();
                set.connect(next, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
                set.connect(next, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
                set.connect(next, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
                if (!it.hasNext()) {
                    set.connect(R.id.moreCategories, ConstraintSet.TOP, next, ConstraintSet.BOTTOM, topMargin);
                }
                topFieldId = next;
            }
        }

        topFieldId = R.id.place_categories;
        for (Iterator<TextView> it = categoriasGooglePlacesTextViews.iterator(); it.hasNext();) {
            currentId = it.next().getId();
            set.connect(currentId, ConstraintSet.TOP, topFieldId, ConstraintSet.BOTTOM, topMargin);
            set.connect(currentId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
            set.connect(currentId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
            if (!it.hasNext()) {
                set.connect(R.id.place_music, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
            } else {
                int next = it.next().getId();
                set.connect(next, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
                set.connect(next, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
                set.connect(next, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
                if (!it.hasNext()) {
                    set.connect(R.id.place_music, ConstraintSet.TOP, next, ConstraintSet.BOTTOM, topMargin);
                }
                topFieldId = next;
            }
        }

        topFieldId = R.id.moreCategories;
        for (Iterator<TextView> it = categoriasTextViews.iterator(); it.hasNext();) {
            currentId = it.next().getId();
            set.connect(currentId, ConstraintSet.TOP, topFieldId, ConstraintSet.BOTTOM, topMargin);
            set.connect(currentId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
            set.connect(currentId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
            if (!it.hasNext()) {
                set.connect(R.id.moreRatings, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
            } else {
                int next = it.next().getId();
                set.connect(next, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
                set.connect(next, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
                set.connect(next, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
                if (!it.hasNext()) {
                    set.connect(R.id.moreRatings, ConstraintSet.TOP, next, ConstraintSet.BOTTOM, topMargin);
                }
                topFieldId = next;
            }
        }

        topFieldId = R.id.moreRatings;
        for (Iterator<TextView> it = calificacionesTextViews.iterator(); it.hasNext();) {
            currentId = it.next().getId();
            set.connect(currentId, ConstraintSet.TOP, topFieldId, ConstraintSet.BOTTOM, topMargin);
            set.connect(currentId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
            set.connect(currentId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
            if (!it.hasNext()) {
                set.connect(R.id.comments, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
            } else {
                int next = it.next().getId();
                set.connect(next, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
                set.connect(next, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
                set.connect(next, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
                if (!it.hasNext()) {
                    set.connect(R.id.comments, ConstraintSet.TOP, next, ConstraintSet.BOTTOM, topMargin);
                }
                topFieldId = next;
            }
        }

        topFieldId = R.id.comments;
        for (Iterator<TextView> it = comentariosTextViews.iterator(); it.hasNext();) {
            currentId = it.next().getId();
            set.connect(currentId, ConstraintSet.TOP, topFieldId, ConstraintSet.BOTTOM, topMargin);
            set.connect(currentId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
            set.connect(currentId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
            if (!it.hasNext()) {
                set.connect(currentId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, bottomMargin);
            }
            topFieldId = currentId;
        }

        set.applyTo(layout);

        TextView addressField = v.findViewById(R.id.place_address);
        addressField.setText(place.getDireccion());

        TextView ratingField = v.findViewById(R.id.place_rating);
        String sourceString = "Calificación (promedio):    " +  "<b>" + String.valueOf(place.getTotal()) + "</b> ";
        ratingField.setText(Html.fromHtml(sourceString));

        CheckBox musicCheckBox = v.findViewById(R.id.place_music);
        musicCheckBox.setChecked(place.isMusica());

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            LatLng origin = new LatLng(APIUrl.latitud, APIUrl.longitud);
            LatLng destination = new LatLng(Double.valueOf(place.getLatitud()),  Double.valueOf(place.getLongitud ()));
            googleMap.addMarker(new MarkerOptions().position(origin).title("Tu posición").snippet(""));
            googleMap.addMarker(new MarkerOptions().position(destination).title("Tu posición").snippet(""));
            /*GoogleDirection.withServerKey(APIUrl.serverKey)
                    .from(origin)
                    .to(destination)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            if(direction.isOK()) {
                                Route route = direction.getRouteList().get(0);
                                Leg leg = route.getLegList().get(0);

                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(getActivity(), directionPositionList, 5, Color.RED);
                                googleMap.addPolyline(polylineOptions);
                            } else {
                                Log.d("ActividadPT", direction.getStatus() + " " + direction.getErrorMessage());
                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {

                        }
                    });*/

            CameraPosition cameraPosition = new CameraPosition.Builder().target(origin).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        });

        CheckBox likedCheckBox = v.findViewById(R.id.place_like);
        likedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
            } else {
                // The toggle is disabled
            }
        });

        ToggleButton toggle = v.findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
            } else {
                // The toggle is disabled
            }
        });


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getContext(), Msg, Toast.LENGTH_SHORT).show();
    }

    public SpannableStringBuilder buildStringWithIcon(Context context, CharSequence text, int resource) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text).append(" ");
        builder.setSpan(new ImageSpan(context, resource, DynamicDrawableSpan.ALIGN_BASELINE),
                builder.length() - 1, builder.length(), 0);
        builder.append(")");
        return builder;
    }

    public float sizeOfText(int dimension) {
        float desiredSp = getResources().getDimension(dimension);
        float density = getResources().getDisplayMetrics().density;
        return desiredSp / density;
    }

    public int generateId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return ViewCompat.generateViewId();
        }
    }

    public int getIconResource(String provieneDe) {
        int iconResourceId;
        switch (provieneDe) {
            case "GooglePlaces":
                iconResourceId = R.drawable.google_32;
                break;
            case "Foursquare":
                iconResourceId = R.drawable.foursquare_1;
                break;
            case "DENUE":
                iconResourceId = R.drawable.inegi_32;
                break;
            default:
                iconResourceId = R.drawable.google_24;
        }
        return iconResourceId;
    }



}
