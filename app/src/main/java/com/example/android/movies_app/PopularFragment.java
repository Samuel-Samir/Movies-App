package com.example.android.movies_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.movies_app.Models.MoviesList;


public class PopularFragment extends Fragment {

    private RecyclerView moviesGrid;
    private GridviewAdapter myAdapter ;
    MoviesList moviesList = new MoviesList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        fetchData () ;
        moviesGrid = (RecyclerView) rootView.findViewById(R.id.GridViewLayout);
        moviesGrid.setLayoutManager(new GridLayoutManager(getActivity(),2 ));
        myAdapter =new GridviewAdapter(getActivity()  , moviesList) ;
        moviesGrid.setAdapter(myAdapter);
        return  rootView ;
    }


    public void fetchData ()
    {

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
        fetchMovies.execute("popular");
    }

}
