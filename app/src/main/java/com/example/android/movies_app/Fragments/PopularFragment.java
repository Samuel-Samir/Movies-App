package com.example.android.movies_app.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.movies_app.FetchFromDataBase;
import com.example.android.movies_app.FetchMovies;
import com.example.android.movies_app.Adapters.GridviewAdapter;
import com.example.android.movies_app.Models.MoviesList;
import com.example.android.movies_app.R;
import com.example.android.movies_app.Utilities;

import java.util.Locale;


public class PopularFragment extends Fragment {

    private RecyclerView moviesGrid;
    private GridviewAdapter myAdapter ;
    private RelativeLayout relativeLayout ;
    private MoviesList moviesList = new MoviesList() ;
    public static   String order;
    private static final int PICK_WIFI_REQUEST = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.re);
        moviesGrid = (RecyclerView) rootView.findViewById(R.id.GridViewLayout);
        getActivity().setTitle(Utilities.getOrderName(getActivity()));

        order =  Utilities.getOrder(getActivity());
        if (order.equals("favorit"))
        {
            fetchDataFromDB();
        }
        else
            checkConnection() ;
        return  rootView ;
    }



    public void onOrientationChange(int orientation){
        //Check orientation and set recyclerview
        if(orientation == Configuration.ORIENTATION_PORTRAIT){

            moviesGrid.setLayoutManager(new GridLayoutManager(getActivity(),2 ));
            myAdapter =new GridviewAdapter(getActivity()  , moviesList) ;
            moviesGrid.setAdapter(myAdapter);

        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){

            moviesGrid.setLayoutManager(new GridLayoutManager(getActivity(),3 ));
            myAdapter =new GridviewAdapter(getActivity()  , moviesList);
            moviesGrid.setAdapter(myAdapter);
        }
    }


    public void fetchData ( )
    {
        FetchMovies fetchMovies = new FetchMovies(getActivity());
        fetchMovies.setFetchMoviesCallback(new FetchMovies.FetchMoviesCallback() {
                                               @Override
                                               public void onPostExecute(Object object) {
                                                   moviesList = (MoviesList) object;
                                                   onOrientationChange(getResources().getConfiguration().orientation);
                                                   myAdapter = new GridviewAdapter(getActivity(), moviesList);
                                                   moviesGrid.setAdapter(myAdapter);


                                               }
                                           }

        );
        fetchMovies.execute(order, "movie");


    }
    public void fetchDataFromDB() {
        FetchFromDataBase fetchFromDataBase = new FetchFromDataBase(getActivity());
        fetchFromDataBase.setFetchMoviesDBCallback(new FetchFromDataBase.FetchFromDBCallback() {
            @Override
            public void onPostExecute(Object Object) {
                moviesList = (MoviesList) Object;
                onOrientationChange(getResources().getConfiguration().orientation);
                myAdapter = new GridviewAdapter(getActivity(), moviesList);
                moviesGrid.setAdapter(myAdapter);
                // Set no Favorite in case of the movies list are empty
                if (moviesList.results.size()==0)
                {
                    Resources res = getResources();
                    Drawable drawable = res.getDrawable(R.drawable.nofav);
                    relativeLayout.setBackground(drawable);
                }
            }
        });
        fetchFromDataBase.execute("movie" ,"movie");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        myAdapter.notifyDataSetChanged();
        onOrientationChange(newConfig.orientation);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private void checkConnection() {
        // if there is internet connection than fetch data
        if (Utilities.checkInternetConnection(getActivity()))
        {
            fetchData();

        }
        else {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Connection failed")
                    .setMessage("Enable mobile network or Wi-Fi, Cancel Lead you to favorite movies.")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent openWifi = new Intent(Settings.ACTION_WIFI_SETTINGS) ;
                            startActivityForResult(openWifi, PICK_WIFI_REQUEST);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            order= "favorit";
                            getActivity().setTitle("Favorit");
                            fetchDataFromDB();

                        }
                    })
                    .show();
        }
    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_WIFI_REQUEST) {
            checkConnection();
        }
    }

}
