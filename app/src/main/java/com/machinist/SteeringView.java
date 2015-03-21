package com.machinist;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;


public class SteeringView extends View {
		
	IViewMode _activeMode;
	TrackViewMode _trackViewMode;
	TrainViewMode _trainViewMode;
	Paint uiPaint = new Paint();
	Thread loaderThread;
	
	/*static Handler hRefresh = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			
		}
	};*/
    
	public SteeringView(Context context) {
		super(context);
				
		_trackViewMode = new TrackViewMode();
		_trackViewMode.Initialize(this);
		
		_trainViewMode = new TrainViewMode();
		_trainViewMode.Initialize(this);
		
		_activeMode = _trackViewMode;
		
		uiPaint.setColor(Color.DKGRAY);
		uiPaint.setStrokeWidth(3);
		
		loaderThread = new Thread(new Runnable() {
			public void run() {
				if (Helper.AppStopped) return;
				parseTrack();		
				if (Helper.AppStopped) return;
				parseTrains();
			}
		});
		loaderThread.start();
				
		this.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				boolean result = _activeMode.Touch(v, event);
				
				if (_activeMode.get_activeMode().equalsIgnoreCase("Track")) {
					_activeMode.set_activeMode("");
					_activeMode = _trackViewMode;
				}
				if (_activeMode.get_activeMode().equalsIgnoreCase("Train")) {
					_activeMode.set_activeMode("");
					_activeMode = _trainViewMode;
				}
				v.invalidate();
				
				return result;
			}
		});
				
	}		
	
	protected void parseTrack() {
    	Helper.AddTextToLog("Track loading...");
    	this.postInvalidate();
    	
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(Helper.getInputStream(Helper.get_trackName() + ".xml"));
            
            Track.Instance().Rails = new ArrayList<Rail>();
            {
	            NodeList items = dom.getElementsByTagName("Rail");
	            for (int i=0;i<items.getLength();i++){
	            	if (Helper.AppStopped) return;
	            	Rail rail = new Rail();
	                Node item = items.item(i);
	                NodeList properties = item.getChildNodes();
	                for (int j=0;j<properties.getLength();j++){
	                    Node property = properties.item(j);
	                    String name = property.getNodeName();
	                    if (name.equalsIgnoreCase("StartX")){
	                    	rail.StartX = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    	//AddTextToLog("StartX: " + property.getFirstChild().getNodeValue(), true);
	                    } else if (name.equalsIgnoreCase("StartY")){
	                    	rail.StartY = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    	//AddTextToLog("StartY: " + property.getFirstChild().getNodeValue(), true);
	                    } else if (name.equalsIgnoreCase("EndX")){
	                    	rail.EndX = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    	//AddTextToLog("EndX: " + property.getFirstChild().getNodeValue(), true);
	                    } else if (name.equalsIgnoreCase("EndY")){
	                    	rail.EndY = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    	//AddTextToLog("EndY: " + property.getFirstChild().getNodeValue(), true);
	                    }
	                }
	                Track.Instance().Rails.add(rail);
	            }
            }
            
            Track.Instance().Nodes = new ArrayList<com.machinist.Node>();
            {
            	NodeList items = dom.getElementsByTagName("Node");
            	for (int i=0;i<items.getLength();i++){
            		if (Helper.AppStopped) return;
            		com.machinist.Node node = new com.machinist.Node();
	                Node item = items.item(i);
	                NodeList properties = item.getChildNodes();
	                for (int j=0;j<properties.getLength();j++){
	                    Node property = properties.item(j);
	                    String name = property.getNodeName();
	                    if (name.equalsIgnoreCase("X")){
	                    	node.X = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    } else if (name.equalsIgnoreCase("Y")){
	                    	node.Y = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    } else if (name.equalsIgnoreCase("Rotation")){
	                    	node.Rotation = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    } else if (name.equalsIgnoreCase("NodeTypeName")){
	                    	node.NodeTypeName = property.getFirstChild().getNodeValue();
	                    } else if (name.equalsIgnoreCase("NodeAddress1")){
	                    	node.NodeAddress1 = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    } else if (name.equalsIgnoreCase("NodeAddress2")){
	                    	node.NodeAddress2 = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    } else if (name.equalsIgnoreCase("NodeNr1")){
	                    	node.NodeNr1 = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    } else if (name.equalsIgnoreCase("NodeNr2")){
	                    	node.NodeNr2 = Integer.parseInt(property.getFirstChild().getNodeValue());
	                    }
	                    node.Rechtdoor = false;
	                    node.Stand = 0;
	                }
	                Track.Instance().Nodes.add(node);
	            }
            }
            
            Helper.AddTextToLog("Track loaded.");
            this.postInvalidate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    protected void parseTrains() {
    	Helper.AddTextToLog("Trains loading...");
    	this.postInvalidate();
    	
    	TrainInventory.Instance().Trains = new ArrayList<Train>();
    	for(int i = 1; i < 80; i++) {
    		if (Helper.AppStopped) return;
    		String treinadres = Integer.toString(i);
    		if (treinadres.length() == 1) {
    			treinadres = "0" + treinadres;
    		}
    		
        	try {
        		InputStream is = Helper.getInputStream("treinen/" + treinadres + ".jpg");
        		if (is != null) {
        			Helper.AddTextToLog("Train " + treinadres + " is loading...");
        	    	this.postInvalidate();
        			
        			Train trainToAdd = new Train();
        			trainToAdd.Address = Integer.parseInt(treinadres);
        			trainToAdd.Picture = BitmapFactory.decodeStream(is);
        			trainToAdd.Speed = 0;
        			TrainInventory.Instance().Trains.add(trainToAdd);
        		}
        		else {
        			Helper.AddTextToLog("Train " + treinadres + " does not exist...");
        	    	this.postInvalidate();
        		}
        		
        	}
        	catch (Exception e) {
        		// doe niks, trein bestaat niet
        		Helper.AddTextToLog("Train " + treinadres + " failed to load...");
    	    	this.postInvalidate();
        	}
        }
    	Helper.AddTextToLog("");
    	this.postInvalidate();
    }
    
	
	@Override
    public void onDraw(Canvas canvas) {
		canvas.drawText(Helper.logtext, 100, 20, uiPaint);
		
		_activeMode.Draw(canvas);
		
		
		// compass
		/*int xPoint = getMeasuredWidth() / 2;
	    int yPoint = getMeasuredHeight() / 2;

	    float radius = (float) (Math.max(xPoint, yPoint) * 0.6);
	    //canvas.drawCircle(xPoint, yPoint, radius, uiPaint);
	    //canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), uiPaint);

	    // 3.143 is a good approximation for the circle
	    canvas.drawLine(xPoint,
	        yPoint,
	        (float) (xPoint + radius
	            * Math.sin((double) (-Track.Instance().azimuth_angle) / 180 * 3.143)),
	        (float) (yPoint - radius
	            * Math.cos((double) (-Track.Instance().azimuth_angle) / 180 * 3.143)), uiPaint);*/
    }
	
	
}
