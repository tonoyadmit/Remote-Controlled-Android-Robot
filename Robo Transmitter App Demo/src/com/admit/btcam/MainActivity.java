package com.admit.btcam;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import in.digitechlab.robotransmitter.R;
import java.util.Set;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

   private static final int REQUEST_ENABLE_BT = 1;
   private Button onBtn;
   private Button offBtn;
   private Button listBtn;
   private Button findBtn;
   private TextView text;
   private TextView exit;
   private BluetoothAdapter myBluetoothAdapter;
   private Set<BluetoothDevice> pairedDevices;
   private ListView myListView;
   private ArrayAdapter<String> BTArrayAdapter;
   public static final String KEY_DEVICE="device";
   String value = "";
  
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      if(myBluetoothAdapter == null) {
    	  onBtn.setEnabled(false);
    	  offBtn.setEnabled(false);
    	  listBtn.setEnabled(false);
    	  findBtn.setEnabled(false);
    	  text.setText("Status: not supported");
    	  
    	  Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",
         		 Toast.LENGTH_LONG).show();
      } else {
	      text = (TextView) findViewById(R.id.text);
	      onBtn = (Button)findViewById(R.id.turnOn);
	      onBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				on(v);
			}
	      });
	      
	      offBtn = (Button)findViewById(R.id.turnOff);
	      offBtn.setOnClickListener(new OnClickListener() {
	  		
	  		@Override
	  		public void onClick(View v) {
	  			off(v);
	  		}
	      });
	      
	      listBtn = (Button)findViewById(R.id.paired);
	      listBtn.setOnClickListener(new OnClickListener() {
	  		
	  		@Override
	  		public void onClick(View v) {
	  			list(v);
	  		}
	      });
	      
	      findBtn = (Button)findViewById(R.id.search);
	      findBtn.setOnClickListener(new OnClickListener() {
	  		
	  		@Override
	  		public void onClick(View v) {
	  			find(v);
	  		}
	      });
	      
	      exit = (Button)findViewById(R.id.exit_app);
	      exit.setOnClickListener(new View.OnClickListener()
          {
              public void onClick(View paramAnonymousView)
              {
            	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
              	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              	intent.putExtra("EXIT", true);
              	startActivity(intent);System.exit(0);
              }
            });
	    
	      myListView = (ListView)findViewById(R.id.listView1);
	
	      BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
	      myListView.setAdapter(BTArrayAdapter);
	      
	      myListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
						long arg3) {
					
					//int pos1 = myListView.get
					//String aCount = arg0.getChildAt(1).toString();
					value =	(String)BTArrayAdapter.getItem(pos);
					if(!value.equals("")){
					Intent intent = new Intent(getApplicationContext(), Control.class);
					intent.putExtra(KEY_DEVICE, value);
					startActivity(intent); 
					}else{
				         Toast.makeText(getApplicationContext(), "WRONG SELECTION!" + "\n" + "Try Again!!" ,
				        		 Toast.LENGTH_SHORT).show();	
					}
				}
		    	  
		      });
      }
      
		if (getIntent().getBooleanExtra("EXIT", false)) {
		    finish();
		}
      
   }

   public void on(View view){
      if (!myBluetoothAdapter.isEnabled()) {
         Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
         startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

         Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,
        		 Toast.LENGTH_SHORT).show();
      }
      else{
         Toast.makeText(getApplicationContext(),"Bluetooth is already on",
        		 Toast.LENGTH_SHORT).show();
      }
   }
   
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   if(requestCode == REQUEST_ENABLE_BT){
		   if(myBluetoothAdapter.isEnabled()) {
			   text.setText("Status: Enabled");
		   } else {   
			   text.setText("Status: Disabled");
		   }
	   }
   }
   
   public void list(View view){

      pairedDevices = myBluetoothAdapter.getBondedDevices();
      
      for(BluetoothDevice device : pairedDevices)
    	  BTArrayAdapter.add(device.getName());

      Toast.makeText(getApplicationContext(),"Show Paired Devices",
    		  Toast.LENGTH_SHORT).show();
      
   }
   
   final BroadcastReceiver bReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	             
	        	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	        	 
	             BTArrayAdapter.add(device.getName());
	             BTArrayAdapter.notifyDataSetChanged();
	        }
	    }
	};
	
   public void find(View view) {
	   if (myBluetoothAdapter.isDiscovering()) {
		  
		   myBluetoothAdapter.cancelDiscovery();
	   }
	   else {
			BTArrayAdapter.clear();
			myBluetoothAdapter.startDiscovery();
			
			registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));	
		}    
   }
   
   public void off(View view){
	  myBluetoothAdapter.disable();
	  text.setText("Status: Disconnected");
	  
      Toast.makeText(getApplicationContext(),"Bluetooth turned off",
    		  Toast.LENGTH_SHORT).show();
   }
   
   @SuppressWarnings("deprecation")
@Override
   protected void onDestroy() {
	   super.onDestroy();
	   unregisterReceiver(bReceiver);
   }
}