package com.example.listexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.listexample.lessons.*;

public class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private static final String TAG = "TAG";
	private Activity context;
    private Map<String, List<String>> lessonCollections;
    private List<String> lessons;

    public ExpandableListAdapter(Activity context)
    {
    	this.context = context;
    	InitializeLists();
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
		
		final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
 
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
 
        TextView item = (TextView) convertView.findViewById(R.id.topic);
        
        item.setText(laptop);
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
	
	private void InitializeLists()
	{
		Intent i = new Intent("com.example.listexample.lessons.Topic1");
		context.startActivity(i);
		/*
	    i.addCategory(Intent.CATEGORY_LAUNCHER);
	    PackageManager pm = context.getPackageManager();

	    List<PackageInfo> packages = pm.getInstalledPackages(pm.GET_ACTIVITIES);

	    for (PackageInfo packageInfo : packages) {
	        Log.d(TAG, "Installed package  :" + packageInfo.packageName);
	        //Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName)); 
	    }
	    
	    PackageInfo info;
		try {
			info = pm.getPackageInfo("com.example.listexample.lessons", PackageManager.GET_ACTIVITIES);
		    ApplicationInfo test = info.applicationInfo;
		    ActivityInfo[] list = info.activities;
		    Log.d(TAG, test.toString());
		    Log.d(TAG, list.toString());
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		lessons = new ArrayList<String>();
		lessons.add("Lesson 1");
		//lessons.add("Lesson 3");
		
		lessonCollections = new HashMap<String, List<String>>();
		
		List childList = new ArrayList<String>();
		childList.add("Topic 1");
		childList.add("Topic 2");
		childList.add("Topic 3");
		
		lessonCollections.put("Lesson 1", childList);*/
	}
	

}
