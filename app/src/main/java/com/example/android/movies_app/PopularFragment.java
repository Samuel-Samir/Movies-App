package com.example.android.movies_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PopularFragment extends Fragment {

    private RecyclerView moveGrid;
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
        moveGrid = (RecyclerView) rootView.findViewById(R.id.GridViewLayout);
        moveGrid.setLayoutManager(new GridLayoutManager(getActivity(),2 ));
        moveGrid.setAdapter( new GridviewAdapter(getActivity() ,gridElements));
        return  rootView ;
    }

}
