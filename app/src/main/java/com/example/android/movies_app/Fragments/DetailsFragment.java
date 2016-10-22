package com.example.android.movies_app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.R;

import org.w3c.dom.Text;

public class DetailsFragment extends Fragment {

    private MovieContent movieContent ;
    TextView textView ;
    TextView textView2 ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

         View rootView=  inflater.inflate(R.layout.fragment_details, container, false);
        movieContent = (MovieContent) getActivity().getIntent().getSerializableExtra("myMovie");
        textView = (TextView)rootView.findViewById(R.id.name) ;
        textView2 = (TextView)rootView.findViewById(R.id.review) ;

        if (movieContent!=null)
        {
            textView.setText(movieContent.title);
            textView2.setText(movieContent.overview);

        }

        return rootView ;
    }


}
