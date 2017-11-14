package com.admit.btcam;

import in.digitechlab.robotransmitter.R;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static com.admit.btcam.MainActivity.KEY_DEVICE;


public class Control extends Activity {
	
    private Button up;
    private Button down;
    private Button right;
    private Button left;
    private Button stop;
    private Button click;
    private Button exit;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    InputStream mmInputStream;
    OutputStream mmOutputStream;
    String Robot_Device = "No-Robot";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);
        
        String dName = "";
        Intent intent = getIntent();
        if (null != intent)
		dName = intent.getStringExtra(KEY_DEVICE);

		
     	if(!dName.equals(Robot_Device)){
			Toast.makeText(this, "SORRY! DEMO VERSION" + "\n" + "No Robot Detected!!", Toast.LENGTH_LONG).show();
		}
       
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();     
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        
                if (pairedDevices.size()>0){
        	for(BluetoothDevice device:pairedDevices){
        		if(device.getName().equals(Robot_Device)){
        			Toast.makeText(this, "No Robot!", Toast.LENGTH_LONG).show();
        			mmDevice = device;
        			break;
        		}
        	}
        }

        
//        try {
//			mmSocket = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
//			mmSocket.connect();
//			Toast.makeText(this, "ROBOT device connected", Toast.LENGTH_LONG).show();
//			mmInputStream = mmSocket.getInputStream();
//			mmOutputStream = mmSocket.getOutputStream();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//			Toast.makeText(this, "Socket problem found", Toast.LENGTH_LONG).show();
//		}
        
            up = (Button) findViewById(R.id.GoUp);
            down = (Button) findViewById(R.id.GoDown);
            right = (Button) findViewById(R.id.GoRight);
            left = (Button) findViewById(R.id.GoLeft);
            stop = (Button) findViewById(R.id.Stop);
            click = (Button) findViewById(R.id.click);
            exit = (Button) findViewById(R.id.exit_app);
           
            
            up.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                  Control.this.sendMessage(String.valueOf('U'));
              }
            });
            down.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                  Control.this.sendMessage(String.valueOf('D'));
              }
            });
            left.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                  Control.this.sendMessage(String.valueOf('L'));
              }
            });
            right.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                  Control.this.sendMessage(String.valueOf('R'));
              }
            });
            stop.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                  Control.this.sendMessage(String.valueOf('C'));
              }
            });
            click.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View paramAnonymousView)
                {
                	Intent intent = new Intent(getApplicationContext(), Camera.class);
					startActivity(intent);
                }
              });
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
                    
    }

	protected void sendMessage(String message)
	{
        //if (message.length() > 0) {
           // byte[] send = message.getBytes();
            //mmOutputStream.write(send);
			Toast.makeText(this, "No Robot Detected!", Toast.LENGTH_SHORT).show();

        //}
    }
}