package com.ranferi.ssrsi.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.ViewPagerAdapter;
import com.ranferi.ssrsi.model.Calificacione;
import com.ranferi.ssrsi.model.Categoria;
import com.ranferi.ssrsi.model.Comentario;
import com.ranferi.ssrsi.model.Imagene;
import com.ranferi.ssrsi.model.Nombre;
import com.ranferi.ssrsi.model.Place;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;


public class PlaceFragment extends Fragment {

    private static final String ARG_PLACE_ID = "place_id";

    private Realm realm;
    MapView mMapView;
    private GoogleMap googleMap;
    private ConstraintLayout layout;
    private ConstraintSet set;
    private int topMargin;
    private int sideMargin;
    private int bottomMargin;

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


        RealmQuery<Place> query = realm.where(Place.class);
        Place place = query.equalTo("id", placeId).findFirst();
        List<Nombre> nombresSitio = place.getNombres();
        List<Categoria> categoriasSitio = place.getCategorias();
        List<Calificacione> calificacionesSitio = place.getCalificaciones();
        List<Comentario> comentariosSitio = place.getComentarios();
        List<Imagene> imagenesSitio = place.getImagenes();

        List<TextView> nombresTextViews = new ArrayList<>();
        List<TextView> categoriasTextViews = new ArrayList<>();
        List<TextView> categoriasGooglePlacesTextViews = new ArrayList<>();
        List<TextView> calificacionesTextViews = new ArrayList<>();
        List<TextView> comentariosTextViews = new ArrayList<>();

        ViewPager viewPager = v.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), imagenesSitio);
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

        TextView nameField = v.findViewById(R.id.place_name);
        nameField.setText(nombresSitio.get(0).getNombreSitio());

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
            googleMap.addMarker(new MarkerOptions().position(destination).title("Sitio De Interés").snippet(""));
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


        layout = v.findViewById(R.id.linearLayout);
        set = new ConstraintSet();

        // margenes
        topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, getResources().getDisplayMetrics());
        sideMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                25, getResources().getDisplayMetrics());
        bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                24, getResources().getDisplayMetrics());

        addTextViewsToCollection(nombresSitio, nombresTextViews, getActivity().getApplicationContext(), false);
        addTextViewsToCollection(categoriasSitio, categoriasGooglePlacesTextViews, getActivity().getApplicationContext(), true);
        addTextViewsToCollection(categoriasSitio, categoriasTextViews, getActivity().getApplicationContext(), false);
        addTextViewsToCollection(comentariosSitio, comentariosTextViews, getActivity().getApplicationContext(), false);
        addTextViewsToCollection(calificacionesSitio, calificacionesTextViews, getActivity().getApplicationContext(), false);

        set.clone(layout);

        setConstraintsViews(nombresTextViews, R.id.namesInfo, R.id.moreCategories);
        setConstraintsViews(categoriasGooglePlacesTextViews, R.id.place_categories, R.id.place_music);
        setConstraintsViews(categoriasTextViews, R.id.moreCategories, R.id.moreRatings);
        setConstraintsViews(calificacionesTextViews, R.id.moreRatings, R.id.comments);
        setConstraintsViews(comentariosTextViews, R.id.comments, 0);

        set.applyTo(layout);

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

    public <T, V> void addTextViewsToCollection(Collection<T> c, Collection<V> cv, Context context, Boolean soloNombres) {
        CharSequence text;
        for (T t : c) {
            TextView textView = new TextView(getActivity());
            textView.setId(generateId());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
            if (soloNombres) {
                text = setTextForView(t);
            } else {
                text = buildStringWithIcon(context, setTextForView(t) + " (de ", getIconResource(getDBForIcon(t)));
            }
            textView.setText(text);
            textView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
            cv.add((V) textView);
            layout.addView(textView);
        }
    }

    public <T> void setConstraintsViews(Collection<T> c, int topFieldId, int bottomFieldId) {
        int currentId;
        for (Iterator<TextView> it = (Iterator<TextView>) c.iterator(); it.hasNext();) {
             currentId = it.next().getId();
            set.connect(currentId, ConstraintSet.TOP, topFieldId, ConstraintSet.BOTTOM, topMargin);
            set.connect(currentId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
            set.connect(currentId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
            if (!it.hasNext()) {
                if (bottomFieldId == 0) {
                    set.connect(currentId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, bottomMargin);
                } else {
                    set.connect(bottomFieldId, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
                }
            } else {
                topFieldId = currentId;
            }
        }
    }

    private <T> String setTextForView(T o) {
        if(o instanceof Nombre)
            return ((Nombre) o).getNombreSitio();
        else if(o instanceof Categoria)
            return ((Categoria) o).getCategoria();
        else if(o instanceof Calificacione)
            return ((Calificacione) o).getCalificacion();
        else if(o instanceof Comentario)
            return ((Comentario) o).getComentario();
        else
            return null;
    }

    private <T> String getDBForIcon(T o) {
        if(o instanceof Nombre)
            return ((Nombre) o).getProviene();
        else if(o instanceof Categoria)
            return ((Categoria) o).getProviene();
        else if(o instanceof Calificacione)
            return ((Calificacione) o).getProviene();
        else if(o instanceof Comentario)
            return ((Comentario) o).getProviene();
        else
            return null;
    }

}
