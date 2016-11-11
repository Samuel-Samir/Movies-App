package com.example.android.movies_app.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.android.movies_app.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//  Declare a new thread to do a preference check
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //  Initialize MySharedPreferences
                        SharedPreferences getPrefs = PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext());

                        //  Create a new boolean and preference and set it to true
                        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                        //  If the activity has never started before...
                        if (isFirstStart) {

                            //  Launch app intro
                            Intent i = new Intent(SplashScreenActivity.this, IntroActivity.class);
                            startActivity(i);

                            //  Make a new preferences editor
                            SharedPreferences.Editor e = getPrefs.edit();

                            //  Edit preference to make it false because we don't want this to run again
                            e.putBoolean("firstStart", false);

                            //  Apply changes
                            e.apply();
                        } else {
                            //  Launch Main Activity
                            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                        finish();
                    }
                });
                // Start the thread
                t.start();
            }
        }, SPLASH_TIME_OUT);

    }
}




