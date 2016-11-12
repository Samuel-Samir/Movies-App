package com.example.android.movies_app.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.movies_app.Adapters.GridviewAdapter;
import com.example.android.movies_app.Fragments.DetailsFragment;
import com.example.android.movies_app.Fragments.PopularFragment;
import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.R;
import com.example.android.movies_app.Utilities;

public class MainActivity extends AppCompatActivity implements GridviewAdapter.Callback {

    final String DETAIL_FRAGMENT_TAG = "DETAILMOVIE";
    public static boolean mTwoPane;
    String mSortMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSortMethod = Utilities.getOrder(this);

        if (findViewById(R.id.activity_movie_details) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_movie_details, new DetailsFragment(),DETAIL_FRAGMENT_TAG)
                        .addToBackStack(DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);

        }

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

//    public void onResume(){
//        super.onResume();
//        String sort = Utilities.getOrder(this);
//
//        if (sort != null && !sort.equals(mSortMethod)) {
//            PopularFragment mainFragment = (PopularFragment) getSupportFragmentManager().findFragmentById(R.id.main_Activity_Fragment);
//            if ( mainFragment != null ) {
//               // mainFragment.updateData(sort);
//
//            }
//            DetailsFragment detailFragment = (DetailsFragment) getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
//            if ( detailFragment != null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.main_Activity_Fragment, new DetailsFragment() ,DETAIL_FRAGMENT_TAG).
//                        commit();
//            }
//
//            mSortMethod = sort;
//        }
//
//    }

    @Override
    public void onBackPressed() {
        if(mTwoPane) {
            // remove the fragment added to backstack
            getSupportFragmentManager().popBackStack();
            //remove the original one
            getSupportFragmentManager().popBackStack();
        }
        super.onBackPressed();
    }

    @Override
    public void onMovieSelected(MovieContent movie) {
        if(mTwoPane){
            Bundle arguments = new Bundle();
            arguments.putSerializable("myMovie", movie);
            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_movie_details, fragment)
                    .commit();
        }
        else {
            Intent intent = new Intent(this ,MovieDetailsActivity.class);
            intent.putExtra("myMovie" , movie);
            startActivity(intent);

        }
    }
}
