/*
Assignment : Homework 04
File Name : HW04_Group15.zip
Full Name: Sai Charan Reddy Vallapureddy, Manideep Reddy Nukala
*/
package com.saicharanreddy.hw04_group15;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link secondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link secondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class secondFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<RecipeResults> resultsOutput = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter oAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View view;
    Button finishButton;

    String main_url;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar progressbar;

    private OnFragmentInteractionListener mListener;

    public secondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment secondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static secondFragment newInstance(String param1, String param2) {
        secondFragment fragment = new secondFragment();
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
        view =  inflater.inflate(R.layout.fragment_second, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getActivity().setTitle("Recipes");
        super.onActivityCreated(savedInstanceState);
        progressbar = view.findViewById(R.id.progressBar);
        progressbar.setVisibility(view.INVISIBLE);
        progressbar.setMax(10);
        recyclerView = getActivity().findViewById(R.id.horizontal);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        //layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        finishButton = getView().findViewById(R.id.finish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.INVISIBLE);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.main_container, new mainFragment(), "MainFragment").commit();
            }
        });
        if (getArguments()!=null){
            finishButton.setVisibility(view.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            main_url = getArguments().getString("URL");
            progressbar.setVisibility(view.VISIBLE);
            new AsynTaskMain().execute(main_url);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    class AsynTaskMain extends AsyncTask<String,Integer, ArrayList<RecipeResults>>{

        @Override
        protected ArrayList<RecipeResults> doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);

                HttpURLConnection connection=null;

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                ArrayList<RecipeResults> result = new ArrayList<RecipeResults>();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject root = new JSONObject(json);
                    JSONArray reciepe_details = root.getJSONArray("results");
                    Log.d("LENGTH",String.valueOf(reciepe_details.length()));
                    for(int i=0;i<reciepe_details.length();i++){
                        JSONObject reciepeJson = reciepe_details.getJSONObject(i);
                        RecipeResults reciepe = new RecipeResults(reciepeJson.getString("title"),reciepeJson.getString("thumbnail"),reciepeJson.getString("ingredients"),reciepeJson.getString("href"));
                        result.add(reciepe);
                        publishProgress(i);
                    }
                    Log.d("demo",result.toString());
                    return result;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<RecipeResults> recipeResultses) {
            if(recipeResultses.isEmpty())
            {

                Toast.makeText(getActivity(),"No recipes found", Toast.LENGTH_LONG).show();
                getActivity().getFragmentManager().beginTransaction().replace(R.id.main_container, new mainFragment(), "MainFragment").commit();

            }
            else {
                Log.d("demo", "recipeResults");
                progressbar.setVisibility(view.INVISIBLE);
                finishButton.setVisibility(view.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                super.onPostExecute(recipeResultses);
                oAdapter = new outputAdapter(recipeResultses, getContext());
                recyclerView.setAdapter(oAdapter);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressbar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
