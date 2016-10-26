package com.example.android.movies_app.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by samuel on 10/25/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "movie.db";
    public MovieDbHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " ( "+
                MovieContract.MovieEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_TITLE +" TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_RATING+" REAL NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_DESCRIPTION+" TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_POSTER_URL+ " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_REALEASE_DATE+" TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_BAVKDROP_URL+" TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VOTE_COUNT+" TEXT NOT NULL, " +

                " FOREIGN KEY ("+ MovieContract.MovieEntry.COLUMN_MOVIE_ID+") REFERENCES " +
                MovieContract.TrailerEntry.TABLE_NAME+ " ("+ MovieContract.TrailerEntry.COLUMN_MOVIE_ID+") " +
                "ON DELETE CASCADE ON UPDATE CASCADE "+
                " FOREIGN KEY ("+ MovieContract.MovieEntry.COLUMN_MOVIE_ID+") REFERENCES " +
                MovieContract.ReviewEntry.TABLE_NAME+ " ("+ MovieContract.ReviewEntry.COLUMN_MOVIE_ID+") " +
                "ON DELETE CASCADE ON UPDATE CASCADE );";
        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + MovieContract.TrailerEntry.TABLE_NAME + " ("+
                MovieContract.TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.TrailerEntry.COLUMN_MOVIE_ID +" INTEGER NOT NULL,"+
                MovieContract.TrailerEntry.COLUMN_KEY + " TEXT NOT NULL, "+
                MovieContract.TrailerEntry.COLUMN_NAME + " TEXT NOT NULL );";
        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE "+ MovieContract.ReviewEntry.TABLE_NAME+" ("+
                MovieContract.ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MovieContract.ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, "+
                MovieContract.ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, "+
                MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.TrailerEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.ReviewEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
    }
}
