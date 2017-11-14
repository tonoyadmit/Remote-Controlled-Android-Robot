package com.admit.btcam;

import in.digitechlab.robotransmitter.R;
import java.io.File;
import java.util.List;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Camera extends Activity {
	
    private Button back;
    private Button capture;
    BluetoothAdapter mBluetoothAdapter;
    public static final String KEY_DEVICE="device";
    String Robot_Device = "HC-05";    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        
        Intent intent = getIntent();
        if (null != intent){
			Toast.makeText(this, "Select Camera Device!" + "\n" + "(Mounted On Robot)", Toast.LENGTH_LONG).show();
		}
        
        capture = (Button) findViewById(R.id.capture);
        
        capture.setOnClickListener(new View.OnClickListener(){
        	
        	 public void onClick(View paramAnonymousView)
             {
       
         mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();     
      	 enableBluetooth();
      	 Toast.makeText(getApplicationContext(), "Camera Selection!", Toast.LENGTH_SHORT).show();
                
        }

			private void enableBluetooth() {
	    	    Intent intent = new Intent();
	    		intent.setAction(Intent.ACTION_SEND);
	    		intent.setType("image/jpg");
	    		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "trigger.jpg");
	    		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));    		
	    		PackageManager pm = getPackageManager();
	    		List<ResolveInfo> appsList = pm.queryIntentActivities(intent, 0);
	    		
	    		if(appsList.size() > 0) {
	    			String packageName = null;
	    			String className = null;
	    			boolean found = false;
	    			
	    			for(ResolveInfo info : appsList) {
	    				packageName = info.activityInfo.packageName;
	    				if(packageName.equals("com.android.bluetooth")) {
	    					className = info.activityInfo.name;
	    					found = true;
	    					break;
	    				}
	    			}
	    			
					if (!found) {
						Toast.makeText(getApplicationContext(), "Bluetooth havn't been found",
								Toast.LENGTH_LONG).show();
					} else {
						intent.setClassName(packageName, className);
						startActivity(intent);
					}
	    		}
				
			}
        	 });
        

        back = (Button) findViewById(R.id.back);
                      
            back.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
            		
                 	Intent intent = new Intent(getApplicationContext(), Control.class);
  					intent.putExtra(KEY_DEVICE, Robot_Device);
  					startActivity(intent);
              }
            });   
            
            
       //smsText = (TextView) findViewById(R.id.text_sms);
       //input = isFrom.concat(isBody).toCharArray();
       //smsText.setText(input, 0, 200);
                    
    }
}