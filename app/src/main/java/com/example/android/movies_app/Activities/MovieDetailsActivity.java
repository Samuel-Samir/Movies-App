package com.example.android.movies_app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.movies_app.Fragments.DetailsFragment;
import com.example.android.movies_app.R;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_details , new DetailsFragment()).commit();
        }
    }


}
