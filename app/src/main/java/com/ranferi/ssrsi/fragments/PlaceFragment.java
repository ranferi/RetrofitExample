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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.helper.ViewPagerAdapter;
import com.ranferi.ssrsi.model.Calificacione;
import com.ranferi.ssrsi.model.Categoria;
import com.ranferi.ssrsi.model.Comentario;
import com.ranferi.ssrsi.model.Imagene;
import com.ranferi.ssrsi.model.Nombre;
import com.ranferi.ssrsi.model.Place;
import com.ranferi.ssrsi.model.User;
import com.ranferi.ssrsi.model.UserPlace;
import com.rd.PageIndicatorView;

import org.apache.commons.text.WordUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    private int idUser;

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
        final String SEPARATOR = ",";
        final Spinner spinner = v.findViewById(R.id.spinner);
        String[] stringPrice = new String[]{
                "elige una calificación (precio)...",
                "Barato",
                "Moderado",
                "Caro",
                "MuyCaro"
        };
        final List<String> priceList = new ArrayList<>(Arrays.asList(stringPrice));

        realm = Realm.getDefaultInstance();
        RealmQuery<Place> query = realm.where(Place.class);
        Place place = query.equalTo("id", placeId).findFirst();
        List<Nombre> nombresSitio = place.getNombres();
        List<Categoria> categoriasSitio = place.getCategorias();
        List<Calificacione> calificacionesSitio = place.getCalificaciones();
        List<Comentario> comentariosSitio = place.getComentarios();
        List<Imagene> imagenesSitio = place.getImagenes();

        idUser  = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        UserPlace userPlaces = realm.where(UserPlace.class).equalTo("visitantes.id", idUser).findAll()
                .where().equalTo("sitio.id", placeId).findFirst();

        List<TextView> nombresTextViews = new ArrayList<>();
        List<TextView> categoriasTextViews = new ArrayList<>();
        List<TextView> calificacionesTextViews = new ArrayList<>();
        List<TextView> comentariosTextViews = new ArrayList<>();

        ViewPager viewPager = v.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), imagenesSitio);
        viewPager.setAdapter(viewPagerAdapter);

        final PageIndicatorView pageIndicatorView = v.findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(viewPagerAdapter.getCount()); // especifica el total de indicadores
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

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

        TextView categoriesField = v.findViewById(R.id.place_categories);
        List<String> categoriesClean = cleanCategoriesCollection(categoriasSitio);
        StringBuilder csvBuilder = new StringBuilder();
        for (int i = 0; i < categoriesClean.size(); i++) {
            if (i >= 3) { break; }
            else {
                csvBuilder.append(categoriesClean.get(i));
                csvBuilder.append(SEPARATOR);
            }
        }
        String csv = csvBuilder.toString();
        csv = csv.substring(0, csv.length() - SEPARATOR.length());
        String categoryString = "Categorías :    " +  "<b>" + csv + "</b> ";
        categoriesField.setText(Html.fromHtml(categoryString));

        CheckBox musicCheckBox = v.findViewById(R.id.place_music);
        musicCheckBox.setChecked(place.isMusica());

        ToggleButton toggleVisited = v.findViewById(R.id.toggleButton);

        mMapView = v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(),R.layout.spinner_item,priceList){
            @Override
            public boolean isEnabled(int position){
                // Se inutiliza el primer item
                // Se utiliza para hint
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        CheckBox likedCheckBox = v.findViewById(R.id.place_like);
        EditText editTextComment = v.findViewById(R.id.editTextComment);

        if (userPlaces != null) {
            Log.d("ActividadPT", String.valueOf(spinnerArrayAdapter.getPosition(userPlaces.getPrecio().substring(3))) + " " + userPlaces.getPrecio().substring(3));
            int i = spinnerArrayAdapter.getPosition(userPlaces.getPrecio().substring(3));
            spinner.post(() -> spinner.setSelection(i, true));
            editTextComment.setText(userPlaces.getComentarioUsuario().getComentario());
            toggleVisited.setChecked(true);
            likedCheckBox.setChecked(userPlaces.isGusto());
        }

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Si el usuario cambia el default
                if(position > 0){
                    Toast.makeText
                            (getActivity(), "Se selecciono : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        Button opinionButton = v.findViewById(R.id.opinionBtn);
        opinionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = editTextComment.getText().toString();
                String b = spinner.getSelectedItem().toString();
                boolean c = likedCheckBox.isChecked();
                showToastMsg(a + " " + b + " " + c);
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
        addTextViewsToCollection(categoriasSitio, categoriasTextViews, getActivity().getApplicationContext(), false);
        addTextViewsToCollection(comentariosSitio, comentariosTextViews, getActivity().getApplicationContext(), false);
        addTextViewsToCollection(calificacionesSitio, calificacionesTextViews, getActivity().getApplicationContext(), false);

        set.clone(layout);
        setConstraintsViews(nombresTextViews, R.id.namesInfo, R.id.moreCategories);
        setConstraintsViews(categoriasTextViews, R.id.moreCategories, R.id.moreRatings);
        setConstraintsViews(calificacionesTextViews, R.id.moreRatings, R.id.comments);
        setConstraintsViews(comentariosTextViews, R.id.comments, 0);
        set.applyTo(layout);

        return v;
    }

    public <T> List<String> cleanCategoriesCollection(Collection<T> c) {
        List<String> normalize = new ArrayList<>();
        for (T t : c) {
            if(t instanceof Categoria)
                normalize.add(((Categoria) t).getCategoria());
        }
        ListIterator<String> iterator = normalize.listIterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            iterator.set(s.replace("_", " "));
        }
        for (final ListIterator<String> i = normalize.listIterator(); i.hasNext();) {
            final String element = i.next();
            i.set(WordUtils.capitalizeFully(element));
        }
        return removeDuplicates(normalize);
    }

    public <T> List<T> removeDuplicates(List<T> list) {
        List<T> newList = new ArrayList<T>();

        for (T element : list) {
            if (!newList.contains(element))
                newList.add(element);
        }
        return newList;
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

    public SpannableStringBuilder buildStringWithUser(Context context, CharSequence text, User user) {
        String usuario = "";
        if (user != null) {
            usuario = user.getUser();
            if (usuario == null || usuario.isEmpty()) {
                usuario = user.getEmail();
            }
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text).append(" ").append(usuario);
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
            User user = null;
            boolean existUser = false;
            if(t instanceof Comentario) {
                try {
                    Field field = t.getClass().getField("user");
                    if (field.getType().equals(User.class)) {
                        user = getUser((Comentario) t);
                        if (user != null) existUser = true;
                    }
                } catch (NoSuchFieldException ignored) { }
            }
            if (soloNombres) {
                text = setTextForView(t);
            } else {
                if (existUser)
                    if (user.getId() != idUser)
                        text = buildStringWithUser(context, setTextForView(t) + " (de usuario:", user);
                    else
                        continue;
                else
                    text = buildStringWithIcon(context, setTextForView(t) + " (de ", getIconResource(getDBForIcon(t)));
            }
            textView.setText(text);
            textView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
            cv.add((V) textView);
            layout.addView(textView);
        }
    }

    public User getUser(Comentario comentario) {
        return comentario.getUser();
    }

    public <T> void setConstraintsViews(Collection<T> c, int topFieldId, int bottomFieldId) {
        T currentTextView;
        int currentId;
        for (Iterator<T> it = c.iterator(); it.hasNext();) {
            currentTextView = it.next();
            if (currentTextView instanceof TextView) {
                currentId = ((TextView) currentTextView).getId();
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
