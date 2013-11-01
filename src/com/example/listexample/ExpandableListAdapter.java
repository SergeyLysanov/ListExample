package com.example.listexample;


import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
	
	private Context context;
    private Map<String, List<String>> lessonCollections;
    private List<String> lessons;

    public ExpandableListAdapter(Context context, Map<String, List<String>> lessonCollections, List<String> lessons){
    	this.context = context;
    	this.lessonCollections = lessonCollections;
    	this.lessons = lessons;
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
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
 
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
}
