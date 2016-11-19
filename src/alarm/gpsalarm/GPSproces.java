package alarm.gpsalarm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GPSproces extends Activity{
	double LO,LA;
	double EPS = .01;
	double latitude = 0.0;
	double longitude = 0.0;
	GPSTracker gps;
	Button back,GetLocation;
	Location location; 
	MediaPlayer ourSong;
	boolean flag = true;
	int cnt = 0;
	@Override
	protected void onCreate(Bundle project) {
		
		super.onCreate(project);
		setContentView(R.layout.gpsproces);
		AndroidGPSTrackingActivity A = new AndroidGPSTrackingActivity();
		
		LO = A.getLO();
		LA = A.getLA();
		//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + LO + "\nLong: " + LA, Toast.LENGTH_LONG).show(); 		
		final TextView myTextView = (TextView) findViewById(R.id.TT); //grab your tv
		 Runnable myRunnable = new Runnable() {
		      @Override
		      public void run() {
		           while (flag) {
		                try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally{
							
							myTextView.post(new Runnable() { 
			                     @Override
			                     public void run() {
										gps = new GPSTracker(GPSproces.this);
										latitude = gps.getLatitude();
										longitude = gps.getLongitude();	
										if((latitude > 0 && longitude > 0 && abs(latitude-LO)<EPS && abs(longitude-LA)<EPS)){ 	
											flag = false;
										}
										myTextView.setText(latitude + "\n" + longitude + "\n" + LO + "\n" + LA);
			                 }});
							
						}
		                
		           }
		        int id = 0;
		        try {
		            InputStream inputStream = openFileInput("myfile.txt");
		             
		            if ( inputStream != null ) {
		                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		                String receiveString = bufferedReader.readLine();
		                id = Integer.parseInt(receiveString);
		            }
		            inputStream.close();
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
		        ProgressBar sp = (ProgressBar) findViewById(R.id.progressBar1);
		        sp.setVisibility(0);
		        if(id==1)
		        	ourSong = MediaPlayer.create(GPSproces.this, R.raw.alarm_1);
		        else if(id==2)
		        	ourSong = MediaPlayer.create(GPSproces.this, R.raw.alarm_2);
		        else
		        	ourSong = MediaPlayer.create(GPSproces.this, R.raw.alarm_3);
           		ourSong.start();
           		Thread timer = new Thread(){
           			public void run(){
           				try{
           					sleep(20000);
           					
           				} catch (InterruptedException e){
           					e.printStackTrace();
           				}finally{
           					finish();
           				}
           			}
           		};
           		timer.start();
		      }
		 };
		 Thread myThread = new Thread(myRunnable);
		 myThread.start();
		
	}
	
	
	double abs(double e){
		if(e<0) return (e*(-1));
		return e;
	}
	
}
