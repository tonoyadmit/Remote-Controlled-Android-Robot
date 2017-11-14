package com.admit.btcam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import in.digitechlab.robotransmitter.R;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Toast.makeText(getApplicationContext(),"Please Switch On The Robot",
        		 Toast.LENGTH_LONG).show();
		
		Thread t = new Thread(){
			
		public void run(){
		
			try{
				sleep(4000);
			}catch (InterruptedException e){
		}
			finally{
				Intent i = new Intent ("com.admit.btscan.MAINACTIVITY");
				startActivity(i);
			}
	}
};
t.start();

}
	
	   @SuppressWarnings("deprecation")
	   @Override
	      protected void onDestroy() {
	   	   super.onDestroy();
	   	   System.runFinalizersOnExit(true);
	      }
}