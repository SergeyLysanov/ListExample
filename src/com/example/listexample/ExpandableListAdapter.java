package com.example.listexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements OnChildClickListener
{
	private static final String packageName = "com.example.listexample";
    private static final String TAG = "TAG";
	private Activity context;
    private Map<String, List<String>> lessonCollections;
    private List<String> lessons;

    public ExpandableListAdapter(Activity context)
    {
    	this.context = context;
    	InitializeLessons();
    }
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return lessonCollections.get(lessons.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		 return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		String topic = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
 
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
 
        TextView item = (TextView) convertView.findViewById(R.id.topic);
        
        item.setText(topic);
        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return lessonCollections.get(lessons.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		 return lessons.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return lessons.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		 return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String lessonName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.lesson);
        item.setText(lessonName);
        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	private void InitializeLessons()
	{
		lessons = new ArrayList<String>();
		lessonCollections = new HashMap<String, List<String>>();
		
	    PackageManager pm = context.getPackageManager();
	    ActivityInfo[] activities;
		try {
			activities = pm.getPackageInfo(packageName, pm.GET_ACTIVITIES).activities;
			if (activities != null) {
				for (ActivityInfo activityInfo : activities) {
					//Log.d(TAG, "activityInfo.packageName : " + activityInfo.packageName);
				    //Log.d(TAG, "activityInfo.name: " + activityInfo.name); 
				    
				    String activityName = activityInfo.name;
				    ParseActivityName(activityName);

				}
			} 
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void ParseActivityName(String activityName){
	    int startInd = activityName.indexOf("lesson");
	    int endInd = activityName.indexOf("Topic");
	    
	    if(startInd > 0)
	    {
	    	String lesson = activityName.substring(startInd, endInd-1);
	    	String topic = activityName.substring(endInd);
			Log.d(TAG, "Lesson: " + lesson);
		    Log.d(TAG, "Topic: " + topic); 
	    	if(!lessonCollections.containsKey(lesson)) //Lesson doesn't exist. Add new
	    	{
	    		List<String> childList = new ArrayList<String>();
	    		childList.add(topic);
	    		
	    		lessons.add(lesson);
	    		lessonCollections.put(lesson, childList);
	    	}
	    	else
	    	{
	    		List<String> childList = lessonCollections.get(lesson);
	    		childList.add(topic);
	    	}
	    }
	}
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) 
	{
		String topic = (String) getChild(groupPosition, childPosition);
		String lessonName = (String) getGroup(groupPosition);
		
		String componentName = packageName + "." + lessonName + "." + topic;
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(packageName, componentName));
		context.startActivity(intent);
		
		return true;
	}
	
	

}
