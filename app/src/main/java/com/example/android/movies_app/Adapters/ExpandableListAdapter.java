package com.example.android.movies_app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies_app.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by samuel on 10/29/2016.
 */

public class ExpandableListAdapter
        extends BaseExpandableListAdapter {
    private Activity activity ;
    private HashMap<String,String> Movies_Category;
    private List<String> Movies_List;
    private boolean isVideo ;

    public ExpandableListAdapter (Activity activity, HashMap<String, String> Movies_Category, List<String> Movies_List , boolean isVideo)
    {
        this.activity = activity;
        this.Movies_Category = Movies_Category;
        this.Movies_List = Movies_List;
        this.isVideo =isVideo ;

    }

    @Override
    public Object getChild(int parent, int child) {


        return Movies_Category.get(Movies_List.get(parent));
    }

    @Override
    public long getChildId(int parent, int child) {
        // TODO Auto-generated method stub
        return child;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertview,
                             ViewGroup parentview)
    {
        String child_title =  (String) getChild(parent, child);
        if(convertview == null)
        {
            LayoutInflater inflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.child_layout, parentview,false);
        }
        TextView child_textview = (TextView) convertview.findViewById(R.id.child_txt);
        if (isVideo==true) {
            String  videoBase = "https://www.youtube.com/watch?v=";
            child_title = videoBase+child_title ;
            child_textview.setText(child_title);
            child_textview.setTextColor((Color.parseColor("#2c5af2")));
            final String finalChild_title = child_title;
            child_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW , Uri.parse(finalChild_title));
                    activity.startActivity(youtubeIntent);
                }
            });
        }
        else
            child_textview.setText(child_title);

        return convertview;
    }

    @Override
    public int getChildrenCount(int arg0) {

        return 1;
    }

    @Override
    public Object getGroup(int arg0) {
        // TODO Auto-generated method stub
        return Movies_List.get(arg0);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return Movies_List.size();
    }

    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertview, ViewGroup parentview) {
        // TODO Auto-generated method stub
        String group_title = (String) getGroup(parent);
        if(convertview == null)
        {
            LayoutInflater inflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.parent_layout, parentview,false);
        }
        TextView parent_textview = (TextView) convertview.findViewById(R.id.parent_txtView);

        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);
        return convertview;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

}
