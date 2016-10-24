package com.example.android.movies_app.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.movies_app.FetchMovies;
import com.example.android.movies_app.GridviewAdapter;
import com.example.android.movies_app.Models.MoviesList;
import com.example.android.movies_app.R;


public class PopularFragment extends Fragment {

    private RecyclerView moviesGrid;
    private GridviewAdapter myAdapter ;
    MoviesList moviesList = new MoviesList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        if (moviesList.results.size() ==0)
        {
            fetchData () ;
        }
        moviesGrid = (RecyclerView) rootView.findViewById(R.id.GridViewLayout);
        moviesGrid.setLayoutManager(new GridLayoutManager(getActivity(),2 ));
        myAdapter =new GridviewAdapter(getActivity()  , moviesList) ;
        moviesGrid.setAdapter(myAdapter);
        return  rootView ;
    }


    public void fetchData ()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order =  sharedPreferences.getString(getActivity().getString(R.string.sortingOrderKey),getActivity().getString(R.string.sortingOrderdefault) );

        FetchMovies fetchMovies = new FetchMovies();
        fetchMovies.setFetchMoviesCallback(new FetchMovies.FetchMoviesCallback()
            {
                @Override
                public void onPostExecute(MoviesList moviesList)
                    {
                        myAdapter =new GridviewAdapter(getActivity() , moviesList) ;
                        moviesGrid.setAdapter(myAdapter);
                    }
            }

        );
        fetchMovies.execute(order , "movie");

    }

}
/*
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_location_key),
                context.getString(R.string.pref_location_default));
 */