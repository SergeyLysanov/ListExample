package com.example.listexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class MainActivity extends Activity {   
    private Map<String, List<String>> lessonCollections;
    private List<String> lessons;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);
        
        initializeLessons();
        
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.lessons_list);
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, lessonCollections, lessons);
        listView.setAdapter(listAdapter);
        listView.setOnChildClickListener(listAdapter);
    }
    
	private void initializeLessons(){
		lessons = new ArrayList<String>();
		lessonCollections = new HashMap<String, List<String>>();
		
	    PackageManager pm = getPackageManager();
	    String packageName =  getString(R.string.package_name);
	    ActivityInfo[] activities;
		try {
			activities = pm.getPackageInfo(packageName, pm.GET_ACTIVITIES).activities;
			if (activities != null) {
				for (ActivityInfo activityInfo : activities) {
				    String activityName = activityInfo.name;
				    parseActivityName(activityName);
				}
			} 
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void parseActivityName(String activityName){
		Pattern MY_PATTERN = Pattern.compile("lesson.\\d*|\\w+\\z");
		Matcher matcher = MY_PATTERN.matcher(activityName);
		
		List<String> listMatches = new ArrayList<String>();
        while(matcher.find()){
            listMatches.add(matcher.group(0));
        }
        
        //Find lesson and topic
        if(listMatches.size() == 2){
	    	String lesson = listMatches.get(0);
	    	String topic = listMatches.get(1);
	        
		    //Lesson doesn't exist. Add new
	    	if(!lessonCollections.containsKey(lesson)){
	    		List<String> childList = new ArrayList<String>();
	    		childList.add(topic);
	    		
	    		lessons.add(lesson);
	    		lessonCollections.put(lesson, childList);
	    	} else {
	    		List<String> childList = lessonCollections.get(lesson);
	    		childList.add(topic);
	    	}
        }
	}
}
