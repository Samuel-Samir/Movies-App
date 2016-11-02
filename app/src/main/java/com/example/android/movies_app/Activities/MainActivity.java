package com.example.android.movies_app.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.movies_app.Fragments.PopularFragment;
import com.example.android.movies_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState ==null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container ,new PopularFragment()).commit() ;
        }
        getSupportActionBar().setElevation(0f);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menue ,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       int id =item.getItemId() ;
        if(id ==R.id.action_settings)
        {
           startActivity(new Intent(this ,SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
