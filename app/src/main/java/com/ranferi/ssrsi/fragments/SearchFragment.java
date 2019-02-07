package com.ranferi.ssrsi.fragments;

import android.content.Context;
import android.net.Uri;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private CheckedTextView mCheckedTextView;
    private AutoCompleteTextView editText;

//    private OnFragmentInteractionListener mListener;

    public SearchFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            // mParam1 = getArguments().getString(ARG_PARAM1);
            // mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
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
        String[] price = getResources().getStringArray(R.array.price);
        String[] distance = getResources().getStringArray(R.array.distance);

        AutoCompleteTextView editText1 = view.findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.select_dialog_item, places);
        editText1.setAdapter(adapter1);

        AutoCompleteTextView editText = view.findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, places);

        editText.setAdapter(adapter);

        AutoCompleteTextView editText2 = view.findViewById(R.id.autoCompleteTextView3);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, price);

        editText2.setAdapter(adapter2);

        AutoCompleteTextView editText3 = view.findViewById(R.id.autoCompleteTextView4);
        ArrayAdapter<String> adapter3 =
                new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, distance);

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

    // Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
/*        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /*
      This interface must be implemented by activities that contain this
      fragment to allow an interaction in this fragment to be communicated
      to the activity and potentially other fragments contained in that
      activity.
      <p>
      See the Android Training lesson <a href=
      "http://developer.android.com/training/basics/fragments/communicating.html"
      >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnFragmentInteractionListener {
        //  Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
