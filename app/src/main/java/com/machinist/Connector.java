package com.machinist;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class Connector {
	private Connector() {
		
	}
	
	public static synchronized Connector Instance()
    {
      if (ref == null)
          ref = new Connector();
      return ref;
    }
    private static Connector ref;
	
    
	Socket s = null;
		
	public boolean ConnectClient(String IP)
    {
        try {
        	//AddTextToLog("Connecting 192.168.2.250:3000", true);
        	s = new Socket();
        	s.setReuseAddress(true);
        	SocketAddress adr = new InetSocketAddress(IP,3000);
        	s.setSoTimeout(5*1000);
      		s.connect(adr);
      		//AddTextToLog("My IP is " + s.getLocalAddress().toString(), true);
        	//AddTextToLog("Connection succesful", true);

      		return true;
        } catch (SocketTimeoutException e) {
        	//AddTextToLog(e.getMessage(), true);
        		e.printStackTrace();
        		return false;
	    } catch (UnknownHostException e) {
	    	//AddTextToLog(e.getMessage(), true);
	            e.printStackTrace();
	            return false;
	    } catch (IOException e) {
	    	//AddTextToLog(e.getMessage(), true);
	            e.printStackTrace();
	            return false;
	    } 
    }
	
	public void SendData(int i1, int i2) // String text)
    {
		if (s != null) {
	    	try {
	    		//AddTextToLog("Sending " + text, true);
	            //outgoing stream redirect to socket
	            OutputStream out = s.getOutputStream();
	           
	            PrintWriter output = new PrintWriter(out);
	            char c1 = (char)i1;
	            char c2 = (char)i2;
	            String text = Character.toString(c1) + Character.toString(c2);
	            //Log.d("Send", text);
	            //output.println(text);
	            output.print(text);
	            output.flush();
	            //AddTextToLog("Sending succesful", true);
	            
	           
		    } catch (UnknownHostException e) {
		    	//AddTextToLog(e.getMessage(), true);
		            e.printStackTrace();
		    } catch (IOException e) {
		    	//AddTextToLog(e.getMessage(), true);
		            e.printStackTrace();
		    }
		}
    }
	
	/*public Thread StartReadingData = new Thread()
    {
        public void run() 
        {

    		while(s != null && s.isConnected() && !s.isClosed())
    		{
    			try
    			{
	    			//Server is waiting for client here, if needed
	    			//AddTextToLog("Start reading data...", true);
	                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
	                int text = input.read(); //.readLine();
	                //AddTextToLog(Integer.toString(text), true);
    			}
    			catch (SocketTimeoutException e)
    	    	{
    	        	//AddTextToLog(e.getMessage(), true);
    	        		e.printStackTrace();
    		    }
    	    	catch (UnknownHostException e) 
    	    	{
    		    	//AddTextToLog(e.getMessage(), true);
    		            e.printStackTrace();
    		    }
    	    	catch (IOException e)
    	    	{
    		    	//AddTextToLog(e.getMessage(), true);
    		            e.printStackTrace();
    		    }
    		}
	                      
	        
        }
    };*/
    
    public boolean DisconnectClient()
    {
    	try {
    		if (s != null)
    		{
    			//AddTextToLog("Disconnecting 192.168.2.250:3000", true);
                //Close connection
                s.close();
                //AddTextToLog("Disconnection succesful", true);	
    		}
    		
    		//if (StartReadingData.isAlive()) {
    		//	StartReadingData.stop();
    		//}
    		    		    		
    		return true;
    		           
	    } catch (UnknownHostException e) {
	    	//AddTextToLog(e.getMessage(), true);
	            e.printStackTrace();
	            return false;
	    } catch (IOException e) {
	    	//AddTextToLog(e.getMessage(), true);
	            e.printStackTrace();
	            return false;
	    }	
    }
}
