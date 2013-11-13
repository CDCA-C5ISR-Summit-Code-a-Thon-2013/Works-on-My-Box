package com.geocent.wimb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class SuspectActivity extends Activity {

	TextView name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suspect);
		
		Intent intent = getIntent();
		String data =intent.getStringExtra( CameraActivity.RECOG_RESULT);
		name = (TextView)findViewById(R.id.namefield);
		name.setText( data );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suspect, menu);
		return true;
	}

}
