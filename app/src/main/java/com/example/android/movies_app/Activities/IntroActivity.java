package com.example.android.movies_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.example.android.movies_app.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends  AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        int background = getResources().getColor(R.color.colorPrimary);
        int white = getResources().getColor(R.color.white);

        addSlide(AppIntroFragment.newInstance("Popular Movies", "The Most Popular Movies", R.drawable.popular, background));
        addSlide(AppIntroFragment.newInstance("Top Rated Movies", "The Top Rated Movies", R.drawable.top, background));
        addSlide(AppIntroFragment.newInstance("Add To Favorites", "Add Any movie to Your Favorite Movies", R.drawable.add, background));
        addSlide(AppIntroFragment.newInstance("Favorites Movies", "The Favorites Movies", R.drawable.fav, background));


        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(background);
        setSeparatorColor(white);

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent i = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(i);
        finish();

        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
