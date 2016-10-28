package com.example.android.movies_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samuel on 10/29/2016.
 */


public class DataProvider {

    public static HashMap<String,String> getInfo()
    {
        HashMap<String, String>MoviesDetails = new HashMap<String, String>();

        List<String> Action_Movies = new ArrayList<String>();
        Action_Movies.add("300 Rise of an Empire");


        List<String> Romntic_Movies = new ArrayList<String>();
        Romntic_Movies.add("Mean Girls");


        List<String> Horror_Movies= new ArrayList<String>();
        Horror_Movies.add("The Conjuring");


        List<String> Comedy_Movies = new ArrayList<String>();
        Comedy_Movies.add("Ride Along");

        MoviesDetails.put("Action Movies", "Action Movies");
        MoviesDetails.put("Romantic Movies", "Action Movies");
        MoviesDetails.put("Horror Movies", "Action Movies");
        MoviesDetails.put("Comedy Movies", "Action Movies");

        return MoviesDetails;

    }

}
