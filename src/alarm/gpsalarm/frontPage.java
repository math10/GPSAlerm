package alarm.gpsalarm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class frontPage extends Activity {
	
	Button startApp,setAlarm,aboutUs,eXit,fB;   
	protected void onCreate(Bundle gpsongoing) {
		super.onCreate(gpsongoing);
		setContentView(R.layout.frontpage);
		startApp = (Button) findViewById(R.id.sApp);
		setAlarm = (Button) findViewById(R.id.sAlarm);
		aboutUs = (Button) findViewById(R.id.aUs);
		eXit = (Button) findViewById(R.id.eXit);
		fB = (Button) findViewById(R.id.fB);
		
		try {
            InputStream inputStream = openFileInput("myfile.txt");
            inputStream.close();
        }
        catch (Exception e) {
           // e.printStackTrace();
            OutputStreamWriter outputStreamWriter = null;
			try {
				outputStreamWriter = new OutputStreamWriter(openFileOutput("myfile.txt",1));
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
            try {
				outputStreamWriter.write("1");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				outputStreamWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } 
		
        eXit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
       startApp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
							Intent openFrontpage = new Intent("alarm.gpsalarm.ANDROIDGPSTRACKINGACTIVITY");
							startActivity(openFrontpage);
							
			} 
					
							
			
		});
       
       aboutUs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent aU = new Intent("alarm.gpsalarm.ABOUTUS");
				startActivity(aU);
							
			} 
					
							
			
		});
       
       setAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent sA = new Intent("alarm.gpsalarm.SETALARM");
				startActivity(sA);
							
			} 
					
							
			
		});
       
       fB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				goToUrl ( "https://www.facebook.com/AustCseBatch28");
			
			} 
		});
       
       
       
	}
	 private void goToUrl (String url) {
	        Uri uriUrl = Uri.parse(url);
	        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	        startActivity(launchBrowser);
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
