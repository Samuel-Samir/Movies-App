package com.example.android.movies_app.Fragments;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.movies_app.FetchMovies;
import com.example.android.movies_app.Adapters.GridviewAdapter;
import com.example.android.movies_app.Models.MoviesList;
import com.example.android.movies_app.R;


public class PopularFragment extends Fragment {

    private RecyclerView moviesGrid;
    private String currentorder = "popular";
    private GridviewAdapter myAdapter ;
    MoviesList moviesList = new MoviesList() ;
    private ProgressBar progressBar ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        moviesGrid = (RecyclerView) rootView.findViewById(R.id.GridViewLayout);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order =  sharedPreferences.getString(getActivity().getString(R.string.sortingOrderKey),getActivity().getString(R.string.sortingOrderdefault) );
        getActivity().setTitle(order);
        if(!currentorder.equals(order) || moviesList.results.size()==0) {

            fetchData();
            currentorder = order;
        }
        return  rootView ;
    }

    public void onOrientationChange(int orientation){
        if(orientation == Configuration.ORIENTATION_PORTRAIT){

            moviesGrid.setLayoutManager(new GridLayoutManager(getActivity(),2 ));
            myAdapter =new GridviewAdapter(getActivity()  , moviesList,progressBar) ;
            moviesGrid.setAdapter(myAdapter);

        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){

            moviesGrid.setLayoutManager(new GridLayoutManager(getActivity(),3 ));
            myAdapter =new GridviewAdapter(getActivity()  , moviesList,progressBar) ;
            moviesGrid.setAdapter(myAdapter);
        }
    }


    public void fetchData ()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order =  sharedPreferences.getString(getActivity().getString(R.string.sortingOrderKey),getActivity().getString(R.string.sortingOrderdefault) );

      if (!order.equals("favorit")) {
          FetchMovies fetchMovies = new FetchMovies(getActivity());
          fetchMovies.setFetchMoviesCallback(new FetchMovies.FetchMoviesCallback() {
                                                 @Override
                                                 public void onPostExecute(Object object) {
                                                     moviesList = (MoviesList) object;
                                                     onOrientationChange(getResources().getConfiguration().orientation);
                                                     myAdapter = new GridviewAdapter(getActivity(), moviesList,progressBar);
                                                     moviesGrid.setAdapter(myAdapter);
                                                 }
                                             }

          );
          fetchMovies.execute(order, "movie");
      }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        myAdapter.notifyDataSetChanged();
        onOrientationChange(newConfig.orientation);
    }

}
