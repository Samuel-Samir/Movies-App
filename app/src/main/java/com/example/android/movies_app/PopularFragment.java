package com.example.android.movies_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movies_app.Models.MoviesList;

import java.security.PublicKey;

public class PopularFragment extends Fragment {

    private RecyclerView moveGrid;
    private GridviewAdapter myAdapter ;
    public static MoviesList moviesListResult ;
    private Integer[] gridElements =
            {
                    R.drawable.im, R.drawable.im,  R.drawable.im,  R.drawable.im,  R.drawable.im,
                    R.drawable.im,  R.drawable.im,  R.drawable.im,  R.drawable.im,
                    R.drawable.im,  R.drawable.im ,R.drawable.im
            };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        fetchData () ;
        moveGrid = (RecyclerView) rootView.findViewById(R.id.GridViewLayout);
        moveGrid.setLayoutManager(new GridLayoutManager(getActivity(),2 ));
        myAdapter =new GridviewAdapter(getActivity() ,gridElements , moviesListResult) ;
        moveGrid.setAdapter(myAdapter);


        return  rootView ;
    }

    public void fetchData ()
    {
        FetchMovies fetchMovies = new FetchMovies() ;
        fetchMovies.execute("popular");
        /*FetchMovies fetchMovies = new FetchMovies();
        fetchMovies.setFetchMoviesCallback(new FetchMovies.FetchMoviesCallback()
            {
                @Override
                public void onPostExecute(MoviesList moviesList)
                    {
                        moviesListResult = moviesList ;
                    }
            }

        );
        fetchMovies.execute("popular");*/
    }

}
/*

 */