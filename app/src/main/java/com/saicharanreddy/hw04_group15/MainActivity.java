/*
Assignment : Homework 04
File Name : HW04_Group15.zip
Full Name: Sai Charan Reddy Vallapureddy, Manideep Reddy Nukala
*/
package com.saicharanreddy.hw04_group15;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements mainFragment.OnFragmentInteractionListener, secondFragment.OnFragmentInteractionListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.main_container, new mainFragment(), "MainFragment").commit();
    }


    @Override
    public void onFragmentInteraction(String url) {
        secondFragment recipeFragment = new secondFragment();
        Log.d("demomain",url);
        Bundle bundle = new Bundle();
        bundle.putString("URL",url);
        recipeFragment.setArguments(bundle);


        getFragmentManager().beginTransaction()
                .replace(R.id.main_container,recipeFragment,"second")
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
