package alarm.gpsalarm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;
 
public class SetAlarm extends Activity {
       private OnClickListener radioListener= new OnClickListener() {
              public void onClick(View v) {
                     RadioButton rb=(RadioButton)v;
                     CharSequence ch = rb.getText();
                     File f = new File("myfile.txt");
                     f.delete();
                     if(ch.equals("Alarm_1")){
                         try {
                        	 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("myfile.txt",1));
                             outputStreamWriter.write("1");
                             outputStreamWriter.close();
                         } catch (Exception e) {
                             // TODO Auto-generated catch block
                             e.printStackTrace();
                         }
                         
                     }
                     else if(ch.equals("Alarm_2")){
                         try {
                        	 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("myfile.txt",1));
                             outputStreamWriter.write("2");
                             outputStreamWriter.close();
                         } catch (Exception e) {
                             // TODO Auto-generated catch block
                             e.printStackTrace();
                         }
                     }
                     else{
                         try {
                        	 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("myfile.txt",1));
                             outputStreamWriter.write("3");
                             outputStreamWriter.close();
                         } catch (Exception e) {
                             // TODO Auto-generated catch block
                             e.printStackTrace();
                         }
                     }                     
              }
       };
      
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setalarm);
        int id = 0;
        try{
  			InputStream inputStream = openFileInput("myfile.txt");
  			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
  			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
  			String st = bufferedReader.readLine();
  			bufferedReader.close();
  			id = Integer.parseInt(st);
  		}catch(Exception e){
  				Toast.makeText(getApplicationContext(), String.valueOf(e) , Toast.LENGTH_LONG).show();
  		}
        final RadioButton radio1=(RadioButton)findViewById(R.id.alarm1);
        final RadioButton radio2=(RadioButton)findViewById(R.id.alarm2);
        final RadioButton radio3=(RadioButton)findViewById(R.id.alarm3);
        if(id==1){
        	radio1.setChecked(true);
        }
        else if(id==2){
        	radio2.setChecked(true);
        }
        else{
        	radio3.setChecked(true);
        }
        radio1.setOnClickListener(radioListener);
        radio2.setOnClickListener(radioListener);
        radio3.setOnClickListener(radioListener);
    }
}