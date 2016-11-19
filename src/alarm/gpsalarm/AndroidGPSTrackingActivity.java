package alarm.gpsalarm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidGPSTrackingActivity extends Activity {
	public static boolean Flag = true;
	public static Trie root = new Trie();
	double EPS = .001;   
	static double LO = 0.0,LA = 0.0;
	int cnt = 0;
    Button btnShowLocation;
    EditText locationName;
    TextView name1,name2,name3;
   
    // GPSTracker class
    GPSTracker gps;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.androidgpstrackingactivity);
       locationName = (EditText) findViewById(R.id.locationName);
       name1 = (TextView) findViewById(R.id.name1);
       name2 = (TextView) findViewById(R.id.name2);
       name3 = (TextView) findViewById(R.id.name3);
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        if(Flag){
        	addFileName();
        	Flag = false;
        }
        name1.setText("");
    	name2.setText("");
    	name3.setText("");
        
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
           
            @Override
            public void onClick(View arg0) {       
                // create class object
            	gps = new GPSTracker(AndroidGPSTrackingActivity.this);
            	boolean flag = false;
            	CharSequence ch = locationName.getText();
    			String st = String.valueOf(ch);
    			flag = find(st);
            	if(flag && gps.canGetLocation()){
            		//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + LO + "\nLong: " + LA, Toast.LENGTH_LONG).show(); 
            		Intent GPSp = new Intent("alarm.gpsalarm.GPSPROCES");
					startActivity(GPSp);
					finish();
            	}
            	else if(!flag){
            		Toast.makeText(getApplicationContext(), "invalid location", Toast.LENGTH_LONG).show();   
            	}
            	else if(!gps.canGetLocation()){
            		gps.showSettingsAlert();   
            	}
               
            }
        });
        
        
        locationName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
       // create class object
            	
            	CharSequence st = locationName.getText();
            	name1.setText("");
            	name2.setText("");
            	name3.setText("");
    			if(st.length()>0){
    				print_name(st);
    			}
            }

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
        });
        
        name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {       
                // create class object
            	locationName.setText(name1.getText());
            }
        });
        
        name2.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {       
                // create class object
            	locationName.setText(name2.getText());
            }
        });
        
        name3.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {       
                // create class object
            	locationName.setText(name3.getText());
            }
        });
        
    }
    public static class Trie extends Activity {
		Trie t[] ;
		boolean isWord;
		double l1,l2;
		Trie(){
			t = new Trie[130];
			isWord = false;
			l1 = 0.0;
			l2 = 0.0;
			for(int i = 0;i<130;i++) t[i] = null;
		}
	}

	void addWord(String st,double _l1,double _l2){
		int len = st.length();
		Trie tmp = root;
		for(int i = 0;i<len;i++){
			
			int id = index(st.charAt(i));
			if(tmp.t[id]==null){
				tmp.t[id] = new Trie();
			}
			tmp = tmp.t[id];
		}
		tmp.isWord = true;
		tmp.l1 = _l1;
		tmp.l2 = _l2;
	}
	
	
	
	boolean findWord(String st){
		int len = st.length();
		Trie tmp = root;
		for(int i = 0;i<len;i++){
			int id = index(st.charAt(i));
			if(tmp.t[id]==null){
				return false;
			}
			tmp = tmp.t[id];
		}
		LO = tmp.l1;
		LA = tmp.l2;
		return tmp.isWord;
	}
	
	int index(char ch){
		if(ch>='a' && ch<='z') return (int)(ch-'a'+'A');
		else return (int)ch;
	}
	
	double getLO(){
		return LO;
	}
	
	double getLA(){
		return LA;
	}
	
	
	void addFileName(){
		InputStream inputStream = null;
		try{
    		inputStream =  getResources().openRawResource(R.raw.gps);
    		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    		String line = null;
    		while((line = reader.readLine())!=null){
    			String tmp = reader.readLine();
    			double _l1 = Double.parseDouble(tmp);
    			tmp = reader.readLine();
    			double _l2 = Double.parseDouble(tmp);
    			addWord(line,_l1,_l2);
    			tmp = reader.readLine();
    		}
    		inputStream.close(); reader.close();
    	}catch(Exception e){
    		Toast.makeText(getApplicationContext(), String.valueOf(e) , Toast.LENGTH_LONG).show();   
        	
    	}finally{
    		if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(), String.valueOf(e) , Toast.LENGTH_LONG).show();   
                	
				}
			}
    	}
	}
	
	
	void print_name(CharSequence ch){
		cnt = 0;
		Trie tmp = root;
		String st = "";
		int len = ch.length();
		for(int i = 0 ;i<len;i++ ){
			if(tmp.t[index(ch.charAt(i))] == null) return ;
			tmp = tmp.t[index(ch.charAt(i))];
			st += (char)index(ch.charAt(i));
		}
		call(tmp,st);
	}
	
	void call(Trie tmp,String st){
		if(tmp.isWord){
			if(cnt==0) name1.setText(st);
			
			else if(cnt==1) name2.setText(st);
			
			else name3.setText(st);
			
			cnt++;
			
			if(cnt==3) return ;
		}
		for(char i = 0;i<129;i++){
			if(tmp.t[i]!=null) call(tmp.t[i],st+i);
			if(cnt==3) return;
		}
	}
	
	boolean find(String st){
		return findWord(st);
	}
	
   
}
