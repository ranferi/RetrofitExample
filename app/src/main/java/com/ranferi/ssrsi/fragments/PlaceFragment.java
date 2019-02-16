package com.ranferi.ssrsi.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.helper.ExpandListAdapter;
import com.ranferi.ssrsi.helper.Group;
import com.ranferi.ssrsi.helper.ViewPagerAdapter;
import com.ranferi.ssrsi.model.Place;
import com.rd.PageIndicatorView;

//import at.blogc.android.views.ExpandableTextView;
//import hakobastvatsatryan.DropdownTextView;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;

public class PlaceFragment extends Fragment {

    private static final String ARG_PLACE_ID = "place_id";

    private ExpandListAdapter ExpAdapter;
    private ArrayList<Group> ExpListItems;
    private ExpandableListView ExpandList;

    private Realm realm;

    public PlaceFragment() { }

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
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                pageIndicatorView.setSelection(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        RealmQuery<Place> query = realm.where(Place.class);
        Place place = query.equalTo("id", placeId).findFirst();
        String nombres =  place.getNombres().get(0).getNombreSitio() + "\n" + "Otros nombres" + "\n" + "Más nombres";

        TextView middleView = new TextView(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            middleView.setId(View.generateViewId());
        }
        middleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeOfText(R.dimen.desired_sp));
        middleView.setText(buildStringWithIcon(getActivity().getApplicationContext(), "Middle View", R.drawable.foursquare_));

        ConstraintLayout layout = v.findViewById(R.id.linearLayout);
        ConstraintLayout.LayoutParams lp =
                new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(middleView, lp);

        ConstraintSet set = new ConstraintSet();
        set.clone(layout);
        int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, getResources().getDisplayMetrics());
        int sideMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                16, getResources().getDisplayMetrics());
        // Set up the connections for the new view. Constrain its top to the bottom of the top view.
        set.connect(middleView.getId(), ConstraintSet.TOP, R.id.place_name, ConstraintSet.BOTTOM, topMargin);
        // Constrain the top of the bottom view to the bottom of the new view. This will replace
        // the constraint from the bottom view to the bottom of the top view.
        set.connect(R.id.place_address, ConstraintSet.TOP, middleView.getId(), ConstraintSet.BOTTOM, topMargin);

        set.connect(middleView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin);
        set.connect(middleView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin);
        set.applyTo(layout);

        //final ExpandableTextView expandableTextView = (ExpandableTextView) v.findViewById(R.id.expandableTextView);
        // final ImageButton buttonToggle = (ImageButton) v.findViewById(R.id.imageButton);

        //TextView nameField = (TextView) v.findViewById(R.id.place_name);
        //nameField.setText(place.getNombres().get(0).getNombreSitio());
        //nameField.setText(nombres);
        //expandableTextView.setText(nombres);

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

        /*buttonToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                expandableTextView.toggle();
            }
        });*/

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
        builder.append(text).append(" ");
        builder.setSpan(new ImageSpan(context, resource),
                builder.length() - 1, builder.length(), 0);
        return builder;
    }

    public float sizeOfText(int dimension) {
        float desiredSp = getResources().getDimension(dimension);
        float density = getResources().getDisplayMetrics().density;
        return desiredSp / density;
    }
}
