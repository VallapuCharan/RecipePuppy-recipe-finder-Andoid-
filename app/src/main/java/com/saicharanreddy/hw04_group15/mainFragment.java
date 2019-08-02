/*
Assignment : Homework 04
File Name : HW04_Group15.zip
Full Name: Sai Charan Reddy Vallapureddy, Manideep Reddy Nukala
*/
package com.saicharanreddy.hw04_group15;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainFragment extends Fragment {
    Recipe recipe;
    ArrayList<Ingredients> ingredients = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public mainFragment() {
        // Required empty public constructor
    }



    public static mainFragment newInstance(String param1, String param2) {
        mainFragment fragment = new mainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton addButton =  (ImageButton) view.findViewById(R.id.floatingButton);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ingredients.add(new Ingredients(""));
        mAdapter = new MyAdapter(ingredients);
        recyclerView.setAdapter(mAdapter);

        Button searchButton = (Button)view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText disname = (EditText) view.findViewById(R.id.dishInput);
                if (disname.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Please add the Dish", Toast.LENGTH_SHORT).show();
                } else {
                    recipe = new Recipe(disname.getText().toString(), ingredients);
                    Log.d("demo", ingredients.toString());
                    StringBuilder sb = new StringBuilder();
                    sb.append("i=");
                    for (Ingredients in : recipe.getIngredients()) {
                        sb.append(in.getIngredients() + ",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    if (disname.getText().toString().length() == 0) {
                        Toast.makeText(getActivity(), "ADD THE DISH", Toast.LENGTH_SHORT).show();
                    } else {
                        String Url = "http://www.recipepuppy.com/api/?" + sb.toString() + "&q=" + recipe.getDishname();
                        Log.d("url", Url);
                        mListener.onFragmentInteraction(Url);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Recipe Puppy");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String url) {
        if (mListener != null) {
            mListener.onFragmentInteraction(url);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String url);
    }
}
