package com.example.listexample.lessons;

import com.example.listexample.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Topic1 extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.topic_activity);
	    
	    TextView text = (TextView) findViewById(R.id.topic_description);
	    text.setText("Hello topic 1");
	  }
}
