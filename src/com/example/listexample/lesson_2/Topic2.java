package com.example.listexample.lesson_2;

import com.example.listexample.R;
import com.example.listexample.R.id;
import com.example.listexample.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Topic2 extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.topic_activity);
	    
	    TextView text = (TextView) findViewById(R.id.topic_description);
	    text.setText("Lesson 2\nTopic 2");
	  }
}
