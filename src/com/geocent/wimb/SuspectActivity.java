package com.geocent.wimb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geocent.wimb.recognition.model.Note;
import com.geocent.wimb.recognition.model.RecognitionResult;

public class SuspectActivity extends Activity {

	ImageView imageView;
	TextView name;
	TextView aliases;
	Button prevImageButton;
	Button nextImageButton;
	ListView listView;
	Button prevSuspect;
	Button matchSuspect;
	Button nextSuspect;
	List<RecognitionResult> results;
	RecognitionResult currentResult; //result being displayed
	int currentResultIndex;
	int currentResultImageIndex;
	
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
		
		prevImageButton = (Button) findViewById( R.id.prevImage );
		prevImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	setImageToIndex(currentResultImageIndex-1);
            }
        });

        nextImageButton = (Button) findViewById( R.id.nextImage );
        nextImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	setImageToIndex(currentResultImageIndex+1);
            }
        });

		listView = (ListView) findViewById( R.id.listView1 );

		prevSuspect = (Button) findViewById( R.id.prevSuspect );
		prevSuspect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	loadResult(currentResultIndex-1);
            }
        });

		matchSuspect = (Button) findViewById( R.id.matchSuspect );
		
		nextSuspect = (Button) findViewById( R.id.nextSuspect );
		prevImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	loadResult(currentResultIndex+1);
            }
        });
	}
	
	//load the result
	private void loadResult(int i) 
	{
		if( i<0 || i>results.size() )
			return;
		
		currentResultIndex = i;
		setImageToIndex( 0 );
		currentResult = results.get( i );
		name.setText( currentResult.getName() );
		
		String aliasList = "";
		for( String s : currentResult.getAliases() )
		{
			aliasList += s;
			aliasList += " ";
		}
		aliases.setText( aliasList );
		
		List<Note> notes = currentResult.getNotes();
		ArrayList<String> list = new ArrayList<String>();
		for( Note n : notes )
		{
			String item;
		    int threat = n.getThreatLevel();
		    
		    if ( threat == Note.NO_THREAT)
		    {
		    	item = " ";
		    } else if ( threat == Note.THREAT_LEVEL_UNKNOWN)
		    {
		    	item = "WARN: ";
		    } else
		    {
		    	item = "THREAT: ";
		    }
		    item += n.getDetails();
		    
			list.add( item );
		}
		
	    final StableArrayAdapter adapter = new StableArrayAdapter(this,
	            android.R.layout.simple_list_item_1, list);
	        listView.setAdapter(adapter);

	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	          @Override
	          public void onItemClick(AdapterView<?> parent, final View view,
	              int position, long id) {
	            final String item = (String) parent.getItemAtPosition(position);
//	            view.animate().setDuration(2000).alpha(0)
//	                .withEndAction(new Runnable() {
//	                  @Override
//	                  public void run() {
//	                    list.remove(item);
//	                    adapter.notifyDataSetChanged();
//	                    view.setAlpha(1);
//	                  }
//	                });
	          }

	        });
	      }
//		NoteAdapter adapter = new NoteAdapter(this, R.layout.rowlayout, notes);
//		listView.setAdapter(adapter);
		
	
	private void setImageToIndex( int i )
	{
		List<Bitmap> list = currentResult.getImages();
		if( i<0 || i > list.size() )
			return;
		
		Bitmap bm = currentResult.getImages().get( i );
		imageView.setImageBitmap( bm );
		currentResultImageIndex = i;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suspect, menu);
		return true;
	}
	

	  private class StableArrayAdapter extends ArrayAdapter<String> {

		    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		    public StableArrayAdapter(Context context, int textViewResourceId,
		        List<String> objects) {
		      super(context, textViewResourceId, objects);
		      for (int i = 0; i < objects.size(); ++i) {
		        mIdMap.put(objects.get(i), i);
		      }
		    }

		    @Override
		    public long getItemId(int position) {
		      String item = getItem(position);
		      return mIdMap.get(item);
		    }

		    @Override
		    public boolean hasStableIds() {
		      return true;
		    }

		  }

}
