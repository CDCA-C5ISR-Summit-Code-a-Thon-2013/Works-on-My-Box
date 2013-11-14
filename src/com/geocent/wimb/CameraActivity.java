package com.geocent.wimb;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Face;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geocent.wimb.recognition.RecognitionService;
import com.geocent.wimb.recognition.model.RecognitionResult;

public class CameraActivity extends Activity implements
	Callback, Camera.FaceDetectionListener{
	
	public static final String RECOG_RESULT = "RECOG_RESULT";

	private static final int CameraID = 0;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	Camera camera;
	Button start;
	Button capture;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	ShutterCallback shutterCallback;
	PictureCallback jpegCallback;
	PictureCallback rawCallback;
	Boolean  cameraStarted = false;
	Overlay overlay;
	static int currentCameraOrientation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);		

		capture = (Button)findViewById(R.id.prevSuspect);
        capture.setOnClickListener(new Button.OnClickListener()
        {
			public void onClick(View arg0) {
				suspectFound();
			}
        });
        
        startCameraSession();
	}
	
	private void startCameraSession()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

	    // start the image capture Intent
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		
	}

	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "WOMB");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) 
	        {
	        	try {
	        		String s = fileUri.toString();
	        		s = s.substring( 6 ); //cut off the protocol part of the URI
					RandomAccessFile f = new RandomAccessFile( s, "r");
					byte[] image = new byte[(int)f.length()];
					f.read( image );
					
			        BitmapFactory.Options options = new BitmapFactory.Options();
			        //options.inSampleSize = 1;

			        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length,options);
			        ImageView view;
			        view = (ImageView)findViewById(R.id.imageView1);
			        view.setImageBitmap(bitmap);
			        f.close();

                    RecognitionService.doRecognition(fileUri, new RecognitionService.Callback() {
                        @Override
                        public void onSuccess(List<RecognitionResult> results) {
                        	
                    	    Intent intent = new Intent(CameraActivity.this, SuspectActivity.class);
                    	    intent.putExtra(RECOG_RESULT, (ArrayList) results);
                    	    startActivity(intent);
                        }

                        @Override
                        public void onError(String errorMessage) {
                        	new AlertDialog.Builder(CameraActivity.this)
                            .setTitle("Service Error")
                            .setMessage("We were unable to reach the remote service.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) { 
                                	//Restart camera session
                                    CameraActivity.this.startCameraSession();
                                }
                             })
                             .show();                        
                        	}
                    });
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	public static void setCameraDisplayOrientation(Activity activity,
	         int cameraId, android.hardware.Camera camera) {
	     android.hardware.Camera.CameraInfo info =
	             new android.hardware.Camera.CameraInfo();
	     android.hardware.Camera.getCameraInfo(cameraId, info);
	     int rotation = activity.getWindowManager().getDefaultDisplay()
	             .getRotation();
	     int degrees = 0;
	     switch (rotation) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	     }

	     int result;
	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	         result = (info.orientation + degrees) % 360;
	         result = (360 - result) % 360;  // compensate the mirror
	     } else {  // back-facing
	         result = (info.orientation - degrees + 360) % 360;
	     }
	     currentCameraOrientation = result;
	     camera.setDisplayOrientation(result);
	 }

	@Override
	public void onFaceDetection(Face[] faces, Camera camera) 
	{
		//overlay.reset();
		Log.i( "Stuff",  "Drawing " + faces.length + " rects");

		for( int i=0; i<faces.length; i++ )
		{
			Face f = faces[i];
		     android.hardware.Camera.CameraInfo info =
		             new android.hardware.Camera.CameraInfo();
		     android.hardware.Camera.getCameraInfo(CameraID, info);

		     Matrix matrix = new Matrix();
//			 CameraInfo info = CameraHolder.instance().getCameraInfo()[cameraId];
			 // Need mirror for front camera.
			 boolean mirror = (info.facing == CameraInfo.CAMERA_FACING_FRONT);
			 matrix.setScale(mirror ? -1 : 1, 1);
			 // This is the value for android.hardware.Camera.setDisplayOrientation.
			 matrix.postRotate(currentCameraOrientation);
			 // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
			 // UI coordinates range from (0, 0) to (width, height).
			 matrix.postScale(overlay.getWidth() / 2000f, overlay.getHeight() / 2000f);
			 matrix.postTranslate(overlay.getWidth() / 2f, overlay.getHeight() / 2f);
			overlay.drawRect( f.rect );
		}
	}
	
	public void suspectFound()
	{
//		 TabActivity tabs = (TabActivity) getParent();
//		 tabs.getTabHost().setCurrentTab(1);		

	}

}
