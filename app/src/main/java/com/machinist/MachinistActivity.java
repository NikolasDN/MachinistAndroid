package com.machinist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MachinistActivity extends Activity {
	/** Called when the activity is first created. */
	
	static TextView log;
	Button connectButton;
	Button demoButton;
	EditText userName;
	EditText trackName;
	EditText IP;
	//ServerSocket ss = null;
	//Socket cs = null;
	//private Track track;
		
	static Handler hRefresh = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			log.setText(Helper.logtext);
			
			float lineNr = 0;
			lineNr = (log.getLineCount() - 3) * log.getLineHeight();
			log.scrollTo(0, (int)lineNr);
		}
	};
						
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Display display = getWindowManager().getDefaultDisplay();
		Helper.set_screenWidth(display.getWidth());
		Helper.set_screenHeight(display.getHeight());
        
		Helper.AppStopped = false;
        log = (TextView)findViewById(R.id.textViewLog);
        //log.setMovementMethod(new ScrollingMovementMethod());
        userName = (EditText)findViewById(R.id.editTextUsername);
        trackName = (EditText)findViewById(R.id.editTextTrackname);
        IP = (EditText)findViewById(R.id.editTextIP);
        
        connectButton = (Button)findViewById(R.id.buttonConnect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//SendData(message.getText().toString());
            	
            	try {
            		Helper.set_rootDir("https://dl.dropbox.com/u/42735795/" + userName.getText().toString() + "/");
            		Helper.set_trackName(trackName.getText().toString());
            		
            		if (Connector.Instance().ConnectClient(IP.getText().toString())) {
                        // ECHT WEG!!!! //ConnectServer.start();
                        //Connector.Instance().StartReadingData.start();
                        
                        Intent myIntent = new Intent(MachinistActivity.this, SteeringActivity.class);
                        MachinistActivity.this.startActivity(myIntent);
                    	finish();
            		}
            		else {
            			Helper.AddTextToLog("Failed to connect to IP " + IP.getText().toString());
            			hRefresh.sendEmptyMessage(0);
            		}
            	}
            	catch(Exception e) {
            		Helper.AddTextToLog(e.getMessage());
            		hRefresh.sendEmptyMessage(0);
            	}
            	                
            }
        });
        demoButton = (Button)findViewById(R.id.buttonDemo);
        demoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		Helper.set_rootDir("https://dl.dropbox.com/u/42735795/" + userName.getText().toString() + "/");
            		Helper.set_trackName(trackName.getText().toString());
            		
        		    Intent myIntent = new Intent(MachinistActivity.this, SteeringActivity.class);
                    MachinistActivity.this.startActivity(myIntent);
                	finish();
            	}
            	catch(Exception e) {
            		Helper.AddTextToLog(e.getMessage());
            		hRefresh.sendEmptyMessage(0);
            	}
            }
        });
        
        if (isOnline())
        {
        	Helper.AddTextToLog("Connected to the internet");
        	hRefresh.sendEmptyMessage(0);
        }
        else
        {
        	Helper.AddTextToLog("Not connected to the internet!");
        	hRefresh.sendEmptyMessage(0);
        }
                        
        
    }
    
        
    /*private void trimCache(Context context) {
        try {
           File dir = context.getCacheDir();
           if (dir != null && dir.isDirectory()) {
              deleteDir(dir);
           }
        } catch (Exception e) {
           // TODO: handle exception
        }
     }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
           String[] children = dir.list();
           for (int i = 0; i < children.length; i++) {
              boolean success = deleteDir(new File(dir, children[i]));
              if (!success) {
                 return false;
              }
           }
        }

        // The directory is now empty so delete it
        return dir.delete();
     }*/
        
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
        	Helper.AppStopped = true;
            Connector.Instance().DisconnectClient();
            Track.Instance().Destroy();
            TrainInventory.Instance().Destroy();
            finish();
            //DisconnectClientSocket();
            
            /*try {
                trimCache(this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }
        else if(keyCode == KeyEvent.KEYCODE_HOME)
        {
        	Helper.AppStopped = true;
        	Connector.Instance().DisconnectClient();
        	Track.Instance().Destroy();
        	TrainInventory.Instance().Destroy();
        	finish();
        	//DisconnectClientSocket();
        	
        	/*try {
                trimCache(this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }
                        
        return super.onKeyDown(keyCode, event);
    }

    
    private boolean isOnline()
	{
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
    
    
    
    /*private Thread ConnectServer = new Thread()
    {
    	public void run() 
        {
	        try {
	        	
	        	while(cs == null)
	            {
		            //Boolean end = false;
		            //ss = new ServerSocket(3000);
	        		ss = new ServerSocket(3010);
	        		ss.setReuseAddress(true);
		            AddTextToLog("Server created", true);
		            //while(!end)
		            //{
		                //Server is waiting for client here, if needed
		                cs = ss.accept();
		                AddTextToLog("Client accepted to allow listening...", true);
		                //end = true;
		            //}
	            }
	                       
	        } catch (SocketTimeoutException e) {
	        	AddTextToLog(e.getMessage(), true);
	        		e.printStackTrace();
		    } catch (UnknownHostException e) {
		    	AddTextToLog(e.getMessage(), true);
		            e.printStackTrace();
		    } catch (IOException e) {
		    	AddTextToLog(e.getMessage(), true);
		            e.printStackTrace();
		    } 
        }
    };*/
    
        
    /*private void DisconnectClientSocket()
    {
    	try {
            if (cs != null)
            {
            	cs.close();	
            }
            if (ss != null)
            {
            	ss.close();
            }                      
           
        } catch (SocketTimeoutException e) {
        	AddTextToLog(e.getMessage(), true);
        		e.printStackTrace();
	    } catch (UnknownHostException e) {
	    	AddTextToLog(e.getMessage(), true);
	            e.printStackTrace();
	    } catch (IOException e) {
	    	AddTextToLog(e.getMessage(), true);
	            e.printStackTrace();
	    }
    }*/
    
        
    /*private void AddTextToLog(String text, boolean scrolling)
	{
    	Helper.logtext = Helper.logtext + "\n" + text;
    	//hRefresh.sendEmptyMessage(0);
	}*/
    
        
}