package com.example.android.movies_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.android.movies_app.Adapters.ExpandableListAdapter;
import com.example.android.movies_app.Database.MovieContract;

/**
 * Created by samuel on 11/4/2016.
 */

public class Utilities {

    public static String getOrderName (Activity activity)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String order =  sharedPreferences.getString(activity.getString(R.string.sortingOrderKey),activity.getString(R.string.sortingOrderdefault) );
        if (order.equals("popular"))
            return  "Popular" ;
        else if (order.equals("top_rated"))
            return "Top rated";
        else return "Favorit" ;
    }

    public static String getOrder (Activity activity)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String order =  sharedPreferences.getString(activity.getString(R.string.sortingOrderKey),activity.getString(R.string.sortingOrderdefault) );
        return order ;
    }

    public static boolean checkInternetConnection(Context context)
    {

        ConnectivityManager con_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static void connectionAlart (final Activity activity)
    {

        AlertDialog  alertDialog =  new AlertDialog.Builder(activity)
                .setTitle("Connection failed")
                .setMessage("This application requires network access. Please, enable " +
                        "mobile network or Wi-Fi.")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    public static void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    ////////////////////////////////////////////////////////////////////////////////
    public static boolean checkInsertedInDatabase( Activity activity  , int movieId)
    {
        // TODO
       /* Uri moviesWithIdUri =Uri.parse(MovieContract.MovieEntry.CONTENT_URI.toString());
        Cursor movieListCursor = activity.getContentResolver().query(moviesWithIdUri,null,null,null,null);
        int movieIdIndex =movieListCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        while(movieListCursor.moveToNext()){
            int indexIterator = movieListCursor.getInt(movieIdIndex);
            if(indexIterator == movieId){
                return true;
            }
        }*/
        return false;
    }




}
