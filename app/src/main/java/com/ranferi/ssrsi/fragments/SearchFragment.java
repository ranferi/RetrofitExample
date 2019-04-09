package com.ranferi.ssrsi.fragments;

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
import android.widget.CheckedTextView;

import com.ranferi.ssrsi.R;

import java.util.Objects;


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

        String[] places = getResources().getStringArray(R.array.places_type);
        AutoCompleteTextView editText1 = view.findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_dropdown_item_1line, places);
        editText1.setThreshold(1);
        editText1.setAdapter(adapter1);

        AutoCompleteTextView editText = view.findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, places);
        editText.setThreshold(1);
        editText.setAdapter(adapter);

        String[] price = getResources().getStringArray(R.array.price);
        AutoCompleteTextView editText2 = view.findViewById(R.id.autoCompleteTextView3);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, price);
        editText2.setThreshold(1);
        editText2.setAdapter(adapter2);

        String[] distance = getResources().getStringArray(R.array.distance);
        AutoCompleteTextView editText3 = view.findViewById(R.id.autoCompleteTextView4);
        ArrayAdapter<String> adapter3 =
                new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, distance);
        editText3.setThreshold(1);
        editText3.setAdapter(adapter3);

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
