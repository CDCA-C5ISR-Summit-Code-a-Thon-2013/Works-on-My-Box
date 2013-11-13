package com.geocent.wimb;

import java.io.FileOutputStream;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Face;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CameraActivity extends Activity implements
	Callback, Camera.FaceDetectionListener{

	private static final int CameraID = 0;
	
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

        surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        //surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		setupCamera();

		start = (Button)findViewById(R.id.buttonStart);
        start.setOnClickListener(new Button.OnClickListener()
        {
			public void onClick(View arg0) {
				if( !cameraStarted )
				{
					cameraStarted = true;
					start.setClickable( false );
					start_camera();
				} else
		            camera.startPreview();

					
			//captureImage();
			}
        });
 
		capture = (Button)findViewById(R.id.buttonCap);
        capture.setOnClickListener(new Button.OnClickListener()
        {
			public void onClick(View arg0) {
				captureImage();
			}
        });
 
        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.cameraLayout);
        overlay = new Overlay(myLayout.getContext());

//        overlay.setLayoutParams(new RelativeLayout.LayoutParams(
//        		RelativeLayout.LayoutParams.MATCH_PARENT,
//        		RelativeLayout.LayoutParams.MATCH_PARENT));

        myLayout.addView( overlay );
        myLayout.bringToFront();
	}

	private void setupCamera() 
	{
//		camera = Camera.open();
//		Camera.Parameters parms = camera.getParameters();
		
	    /** Handles data for jpeg picture */
	    shutterCallback = new ShutterCallback() {
	        public void onShutter() {
	            Log.i("Log", "onShutter'd");
	        }
	    };

	    rawCallback = new PictureCallback() {
	        public void onPictureTaken(byte[] data, Camera camera) {
	            Log.d("Log", "onPictureTaken - raw");
	        }
	    };

	    jpegCallback = new PictureCallback() {
	        public void onPictureTaken(byte[] data, Camera camera) {
	            FileOutputStream outStream = null;
//	            try {
//	                outStream = new FileOutputStream(String.format(
//	                        "/sdcard/%d.jpg", System.currentTimeMillis()));
//	                outStream.write(data);
//	                outStream.close();
//	                Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
//	            } catch (FileNotFoundException e) {
//	                e.printStackTrace();
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            } finally {
//	            }
//	            Log.d("Log", "onPictureTaken - jpeg");
	            
				start.setClickable( true );

	        }
	    };
		
		
		
	}

    private void captureImage() {
        // TODO Auto-generated method stub
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    private void start_camera()
    {
        try{
            camera = Camera.open( CameraID );
        }catch(RuntimeException e){
            Log.e("ERROR", "init_camera: " + e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        setCameraDisplayOrientation( this, CameraID, camera );
        //modify parameter
        //param.setPreviewFrameRate(20);
        //param.setPreviewSize(176, 144);
        camera.setParameters(param);
        
        camera.setFaceDetectionListener( this );
        
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            camera.startFaceDetection();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            Log.e("ERROR", "init_camera: " + e);
            return;
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

}
