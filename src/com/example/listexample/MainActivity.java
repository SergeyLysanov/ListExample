package com.example.listexample;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.app.Activity;

public class MainActivity extends Activity 
{   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);
        
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.lessons_list);
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this);
        listView.setAdapter(listAdapter);
    }

}
