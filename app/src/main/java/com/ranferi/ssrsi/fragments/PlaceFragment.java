package com.ranferi.ssrsi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.helper.Child;
import com.ranferi.ssrsi.helper.ExpandListAdapter;
import com.ranferi.ssrsi.helper.Group;
import com.ranferi.ssrsi.helper.ViewPagerAdapter;
import com.ranferi.ssrsi.misc.Utility;
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

        //final ExpandableTextView expandableTextView = (ExpandableTextView) v.findViewById(R.id.expandableTextView);
        final ImageButton buttonToggle = (ImageButton) v.findViewById(R.id.imageButton);


        RealmQuery<Place> query = realm.where(Place.class);
        Place place = query.equalTo("id", placeId).findFirst();
        String nombres =  place.getNombres().get(0).getNombreSitio() + "\n" + "Otros nombres" + "\n" + "Más nombres";

        //TextView nameField = (TextView) v.findViewById(R.id.place_name);
        //nameField.setText(place.getNombres().get(0).getNombreSitio());
        //nameField.setText(nombres);
        //expandableTextView.setText(nombres);

        TextView addressField = (TextView) v.findViewById(R.id.place_address);
        addressField.setText(place.getDireccion());

        CheckBox likedCheckBox = (CheckBox) v.findViewById(R.id.place_like);
        likedCheckBox.setChecked(place.isMusica());
        likedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // mPlace.setLiked(isChecked);
            }
        });

        /*buttonToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                expandableTextView.toggle();
            }
        });*/

        CheckBox musicCheckBox = (CheckBox) v.findViewById(R.id.place_music);
        musicCheckBox.setChecked(place.isMusica());
        musicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                place.setMusica(isChecked);
            }
        });

        /*Button dateButton = (Button) v.findViewById(R.id.crime_date);
        dateButton.setText(place.getDireccion());
        dateButton.setEnabled(false);*/


        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewPager);
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

        ExpandList = (ExpandableListView) v.findViewById(R.id.exp_list);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(getContext(), ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
        Utility.setListViewHeightBasedOnChildren(ExpandList);

        ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String group_name = ExpListItems.get(groupPosition).getName();

                ArrayList<Child> ch_list = ExpListItems.get(
                        groupPosition).getItems();

                String child_name = ch_list.get(childPosition).getName();

                showToastMsg(group_name + "\n" + child_name);

                return false;
            }
        });

        ExpandList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                String group_name = ExpListItems.get(groupPosition).getName();
                showToastMsg(group_name + "\n Expanded");

            }
        });

        ExpandList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                String group_name = ExpListItems.get(groupPosition).getName();
                showToastMsg(group_name + "\n Expanded");

            }
        });

        /*DropdownTextView secondDropdownTextView = (DropdownTextView) v.findViewById(R.id.first_dropdown_text_view);
        secondDropdownTextView.setTitleText(place.getNombres().get(0).getNombreSitio());
        secondDropdownTextView.setContentText(nombres);*/

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    public ArrayList<Group> SetStandardGroups() {

        ArrayList<Group> group_list = new ArrayList<>();
        ArrayList<Child> child_list;

        // Setting Group 1
        child_list = new ArrayList<>();
        Group gru1 = new Group();
        gru1.setName("Apple");

        Child ch1_1 = new Child();
        ch1_1.setName("Iphone");
        child_list.add(ch1_1);

        Child ch1_2 = new Child();
        ch1_2.setName("ipad");
        child_list.add(ch1_2);

        Child ch1_3 = new Child();
        ch1_3.setName("ipod");
        child_list.add(ch1_3);

        gru1.setItems(child_list);

        // Setting Group 2
        child_list = new ArrayList<>();
        Group gru2 = new Group();
        gru2.setName("SAMSUNG");

        Child ch2_1 = new Child();
        ch2_1.setName("Galaxy Grand");
        child_list.add(ch2_1);

        Child ch2_2 = new Child();
        ch2_2.setName("Galaxy Note");
        child_list.add(ch2_2);

        Child ch2_3 = new Child();
        ch2_3.setName("Galaxy Mega");
        child_list.add(ch2_3);

        Child ch2_4 = new Child();
        ch2_4.setName("Galaxy Neo");
        child_list.add(ch2_4);

        gru2.setItems(child_list);

        //listing all groups
        group_list.add(gru1);
        group_list.add(gru2);

        return group_list;
    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getContext(), Msg, Toast.LENGTH_SHORT).show();
    }
}
