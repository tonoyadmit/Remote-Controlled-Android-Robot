package com.admit.roborecieve;

import in.digitechlab.roboreceiver.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


@SuppressWarnings("deprecation")
public class RecActivity extends ActionBarActivity {
	
    private Camera mCamera;
    private CameraPreview mCameraPreview;
 //   public static final String ACTION_SMS_SENT = "com.techblogon.android.apis.os.SMS_SENT_ACTION";
    protected Context context;
 //   String msg = "Photo Captured at Current Position Successfully! ";
 //   String strReceipent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);
        
        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mCameraPreview);
        
         final Button captureButton = (Button) findViewById(R.id.button_capture);
         
         
         captureButton.setOnClickListener(new View.OnClickListener() {
        
        	 
        @Override
         public void onClick(View v) {
    		 Toast.makeText(getApplicationContext(), "Robot.Camera..Action..." ,
    		       Toast.LENGTH_LONG).show();
    		 Toast.makeText(getApplicationContext(), "Demo Auto Camera On Robot!" ,
  		           Toast.LENGTH_SHORT).show();
    		 Toast.makeText(getApplicationContext(), "Location & SMS Service is Off" ,
  		           Toast.LENGTH_SHORT).show();
         mCamera.takePicture(null, null, mPicture);
         send_sms();
           }
        });
         
         captureButton.post(new Runnable(){
             @Override
             public void run() {
            	 captureButton.performClick();
             }
 });
         
    }


    private void send_sms() {
		
//    	 String strSMSBody = msg;
// 		SmsManager sms = SmsManager.getDefault();
//         List<String> messages = sms.divideMessage(strSMSBody);
//         for (String message : messages) {
//             sms.sendTextMessage(strReceipent, null, message, PendingIntent.getBroadcast(
//             		this, 0, new Intent(ACTION_SMS_SENT), 0), null);
//
//             }	
    	
    	
         
     	final Handler handler = new Handler();
     	handler.postDelayed(new Runnable() {
     	  @Override
     	  public void run() {	
     	        finish();
     	        System.exit(0);
     	  }
     	}, 8000);
         
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rec, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
        }
    };
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Robo Receiver");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Robo Receiver", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "Robo_" + timeStamp + ".jpg");

        return mediaFile;
    }
    
}
