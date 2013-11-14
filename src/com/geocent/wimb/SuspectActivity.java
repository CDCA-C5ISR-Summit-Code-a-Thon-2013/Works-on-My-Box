package com.geocent.wimb;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geocent.wimb.recognition.model.RecognitionResult;

public class SuspectActivity extends Activity {

	ImageView imageView;
	TextView name;
	TextView aliases;
	Button prevImage;
	Button nextImage;
	ListView listView;
	Button prevSuspect;
	Button matchSuspect;
	Button nextSuspect;
	List<RecognitionResult> results;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suspect);
		
		initWidgets();
		
		Intent intent = getIntent();
		
		results = (List<RecognitionResult>) 
				intent.getSerializableExtra( CameraActivity.RECOG_RESULT );
		loadResult( 0 );
	}

	private void initWidgets()
	{
		imageView = (ImageView) findViewById( R.id.imageView1 );
		name = (TextView)findViewById(R.id.namefield);
		aliases = (TextView)findViewById(R.id.aliasfield);
		prevImage = (Button) findViewById( R.id.prevImage );
		nextImage = (Button) findViewById( R.id.nextImage );
		listView = (ListView) findViewById( R.id.listView1 );
		prevSuspect = (Button) findViewById( R.id.prevSuspect );
		matchSuspect = (Button) findViewById( R.id.matchSuspect );
		nextSuspect = (Button) findViewById( R.id.nextSuspect );
	}
	
	private void loadResult(int i) 
	{
		RecognitionResult result = results.get( i );
		name.setText( result.getName() );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suspect, menu);
		return true;
	}

}
