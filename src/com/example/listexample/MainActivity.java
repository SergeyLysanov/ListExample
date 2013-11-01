package com.example.listexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class MainActivity extends Activity  implements OnChildClickListener{  
	private String packageName;
    private Map<String, List<String>> lessonCollections;
    private List<String> lessons;
    BaseExpandableListAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);
        
        if(savedInstanceState == null){
        	this.packageName =  getString(R.string.package_name);
        	initializeLessons();
        }
       
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.lessons_list);
        mAdapter = new ExpandableListAdapter(this, lessonCollections, lessons);
        listView.setAdapter(mAdapter);
        listView.setOnChildClickListener(this);
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
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		String topic = (String) mAdapter.getChild(groupPosition, childPosition);
		String lessonName = (String) mAdapter.getGroup(groupPosition);
		
		String componentName = packageName + "." + lessonName + "." + topic;
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(packageName, componentName));
		startActivity(intent);
		
		return true;
	}
}
