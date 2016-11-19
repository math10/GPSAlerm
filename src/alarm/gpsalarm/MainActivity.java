package alarm.gpsalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends Activity{
	ProgressBar myProgressBar;
	int myProgress = 0;
	@Override
	protected void onCreate(Bundle project) {
		
		super.onCreate(project);
		setContentView(R.layout.activity_main);
		myProgressBar=(ProgressBar)findViewById(R.id.progressBar1);
		
        
        Runnable myThread = new Runnable(){

  		  @Override
  		  public void run() {
  		   // TODO Auto-generated method stub
  		   while (myProgress<100){
  		    try{
  		     myHandle.sendMessage(myHandle.obtainMessage());
  		     Thread.sleep(1000);
  		    }
  		    catch(Throwable t){
  		    }
  		    
  		    if(myProgress==100){
  		    	Intent openFrontpage = new Intent("alarm.gpsalarm.FRONTPAGE");
				startActivity(openFrontpage);
				finish();
  		    }
  		    
  		   }
  		  }
  		  Handler myHandle = new Handler(){

  		   @Override
  		   public void handleMessage(Message msg) {
  		    // TODO Auto-generated method stub
  		    myProgress += 10;
  		    myProgressBar.setProgress(myProgress);
  		   }
  		  };
  	 };
  	 new Thread(myThread).start();

	}
	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}

