package com.ranferi.ssrsi.fragments;

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
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.helper.ViewPagerAdapter;
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

    public PlaceFragment() {
    }

    public static PlaceFragment newInstance(int placeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLACE_ID, placeId);
        PlaceFragment fragment = new PlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place, container, false);
        int placeId = (int) getArguments().getSerializable(ARG_PLACE_ID);
        realm = Realm.getDefaultInstance();

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

        TextView nameField = v.findViewById(R.id.place_name);
        nameField.setText(nombresSitio.get(0).getNombreSitio());
        List<TextView> nombresTextViews = new ArrayList<>();
        ConstraintLayout layout = v.findViewById(R.id.linearLayout);

        for (Nombre nombre : nombresSitio) {
            TextView textView1 = new TextView(getActivity());
            TextView textView2 = new TextView(getActivity());
            textView1.setId(generateId());
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
            textView1.setText(nombre.getNombreSitio());
            textView1.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
            nombresTextViews.add(textView1);
            layout.addView(textView1);
            textView2.setId(generateId());
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
            textView2.setText(buildStringWithIcon(getActivity().getApplicationContext(), "proviene de ", getIconResource(nombre.getProviene())));
            textView2.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
            nombresTextViews.add(textView2);
            layout.addView(textView2);
        }

        // margenes
        int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, getResources().getDisplayMetrics());
        int sideMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                16, getResources().getDisplayMetrics());

        ConstraintSet set = new ConstraintSet();
        set.clone(layout);
        int topFieldId = R.id.place_name;
        int currentId = 0;
        for (Iterator<TextView> it = nombresTextViews.iterator(); it.hasNext();) {
            TextView tw = it.next();
            currentId = tw.getId();
            set.connect(currentId, ConstraintSet.TOP, topFieldId, ConstraintSet.BOTTOM, topMargin);
            set.connect(currentId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
            set.connect(currentId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
            if (!it.hasNext()) {
                set.connect(R.id.place_address, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
            } else {
                TextView tv = it.next();
                int next = tv.getId();
                Log.d("ActividadPT", "id <<< " + currentId + " <" + tw.getText());
                Log.d("ActividadPT", "next <<< " + next + " " + tv.getText());
                set.connect(next, ConstraintSet.TOP, currentId, ConstraintSet.BOTTOM, topMargin);
                set.connect(next, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
                set.connect(next, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
                if (!it.hasNext()) {
                    Log.d("ActividadPT", "next <<< adentro");
                    set.connect(R.id.place_address, ConstraintSet.TOP, next, ConstraintSet.BOTTOM, topMargin);
                }
                topFieldId = next;
            }
        }

        set.applyTo(layout);


        //TextView addressField = (TextView) v.findViewById(R.id.place_address);
        //addressField.setText(place.getDireccion());

        //CheckBox likedCheckBox = (CheckBox) v.findViewById(R.id.place_like);
        //likedCheckBox.setChecked(place.isMusica());
//        likedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//                // mPlace.setLiked(isChecked);
//            }
//        });


        /*CheckBox musicCheckBox = (CheckBox) v.findViewById(R.id.place_music);
        musicCheckBox.setChecked(place.isMusica());
        musicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                place.setMusica(isChecked);
            }
        });*/

        /*Button dateButton = (Button) v.findViewById(R.id.crime_date);
        dateButton.setText(place.getDireccion());
        dateButton.setEnabled(false);*/

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getContext(), Msg, Toast.LENGTH_SHORT).show();
    }

    public SpannableStringBuilder buildStringWithIcon(Context context, CharSequence text, int resource) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text).append("  ");
        builder.setSpan(new ImageSpan(context, resource),
                builder.length() - 1, builder.length(), 0);
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
                iconResourceId = R.drawable.google;
                break;
            case "Foursquare":
                iconResourceId = R.drawable.foursquare_;
                break;
            case "DENUE":
                iconResourceId = R.drawable.google;
                break;
            default:
                iconResourceId = R.drawable.google;
        }
        return iconResourceId;
    }
}
