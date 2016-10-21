package com.example.android.movies_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState ==null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container ,new PopularFragment()).commit() ;
        }

    }
}
//c8732cd2368db4d409b3d97274d27600