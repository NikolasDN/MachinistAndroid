package com.machinist;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;

public class TrackViewMode extends BaseViewMode {

	/*Paint drawPaint = new Paint();
	Paint bluePaint = new Paint();
	Paint uiPaint = new Paint();*/
	Paint greenPaint = new Paint();
	Point p = new Point();
	float zoom = 2; //3;
	
	private int offset_x = 0;
    private int offset_y = 0;
    
    public Node selectedNode = null;
    Matrix matrix = new Matrix();
    //Canvas frame = new Canvas();
    
    /*private String _pitch;
    public void set_pitch(String pitch, View parent) {
    	_pitch = pitch;
    	parent.invalidate();
    }*/
    
	@Override
	public void Initialize(View parent) {

		/*drawPaint.setAntiAlias(true);
		drawPaint.setFilterBitmap(true);
		bluePaint.setColor(Color.BLUE);
		bluePaint.setStrokeWidth(2.1f);*/
		greenPaint.setColor(Color.GREEN);
		greenPaint.setStyle(Style.STROKE);
		greenPaint.setStrokeWidth(1);
		/*uiPaint.setColor(Color.DKGRAY);
		uiPaint.setStrokeWidth(3);*/
		parent.setBackgroundColor(Color.GRAY);
				
		map.put("driewegwissel", BitmapFactory.decodeResource(parent.getResources(), R.drawable.driewegwissel));
		map.put("driewegwissel_1", BitmapFactory.decodeResource(parent.getResources(), R.drawable.driewegwissel_1));
		map.put("driewegwissel_2", BitmapFactory.decodeResource(parent.getResources(), R.drawable.driewegwissel_2));
		map.put("driewegwissel_3", BitmapFactory.decodeResource(parent.getResources(), R.drawable.driewegwissel_3));
		map.put("dubbelekruiswissel", BitmapFactory.decodeResource(parent.getResources(), R.drawable.dubbelekruiswissel));
		map.put("dubbelekruiswissel_1", BitmapFactory.decodeResource(parent.getResources(), R.drawable.dubbelekruiswissel_1));
		map.put("dubbelekruiswissel_2", BitmapFactory.decodeResource(parent.getResources(), R.drawable.dubbelekruiswissel_2));
		map.put("dubbelekruiswissel_3", BitmapFactory.decodeResource(parent.getResources(), R.drawable.dubbelekruiswissel_3));
		map.put("dubbelekruiswissel_4", BitmapFactory.decodeResource(parent.getResources(), R.drawable.dubbelekruiswissel_4));
		map.put("enkelekruiswissel", BitmapFactory.decodeResource(parent.getResources(), R.drawable.enkelekruiswissel));
		map.put("enkelekruiswissel_1", BitmapFactory.decodeResource(parent.getResources(), R.drawable.enkelekruiswissel_1));
		map.put("enkelekruiswissel_2", BitmapFactory.decodeResource(parent.getResources(), R.drawable.enkelekruiswissel_2));
		map.put("kruispunt", BitmapFactory.decodeResource(parent.getResources(), R.drawable.kruispunt));
		map.put("linkerwissel", BitmapFactory.decodeResource(parent.getResources(), R.drawable.linkerwissel));
		map.put("linkerwissel_1", BitmapFactory.decodeResource(parent.getResources(), R.drawable.linkerwissel_1));
		map.put("linkerwissel_2", BitmapFactory.decodeResource(parent.getResources(), R.drawable.linkerwissel_2));
		map.put("rechterwissel", BitmapFactory.decodeResource(parent.getResources(), R.drawable.rechterwissel));
		map.put("rechterwissel_1", BitmapFactory.decodeResource(parent.getResources(), R.drawable.rechterwissel_1));
		map.put("rechterwissel_2", BitmapFactory.decodeResource(parent.getResources(), R.drawable.rechterwissel_2));
		map.put("ontkoppelrail", BitmapFactory.decodeResource(parent.getResources(), R.drawable.ontkoppelrail));
		map.put("alarmbutton", BitmapFactory.decodeResource(parent.getResources(), R.drawable.alarmbutton));
		map.put("alarmbutton2", BitmapFactory.decodeResource(parent.getResources(), R.drawable.alarmbutton2));
		//map.put("trainbutton", BitmapFactory.decodeResource(parent.getResources(), R.drawable.trainbutton));
		map.put("functionbutton", BitmapFactory.decodeResource(parent.getResources(), R.drawable.functionbutton));
		
		p.x = 0;
		p.y = 0;

	}
	
	@Override
	public boolean Touch(View v, MotionEvent event) {
		if (Track.Instance().Nodes == null) {
			return false;
		}
		
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
            offset_x = (int)event.getX();
            offset_y = (int)event.getY();
            
            /*// handle button touches
            // ALARM
            float buttonHeight = Helper.get_smallestAxis() / 8.0f;
            float buttonX = Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f);
            if (offset_x >= buttonX && offset_y <= buttonHeight) {
            	Connector.Instance().SendData(97, 0);
            }
    		// TRAINVIEW
            float buttonY = Helper.get_smallestAxis() - (Helper.get_smallestAxis() / 8.0f);
            if (offset_x >= buttonX && offset_y >= buttonY) {
            	set_activeMode("Train");
            }*/
            HandleButtonTouches(offset_x, offset_y, "Train");
            
            break;
		}
		case MotionEvent.ACTION_MOVE: {
            //
			
			int deltaX = (int)event.getX() - offset_x;
            int deltaY = (int)event.getY() - offset_y;
            
            if (deltaX != 0 || deltaY != 0) {
	            /*if (offset_x >= 0 && offset_x <= 50 && offset_y >= 20 && offset_y <= (Helper.get_smallestAxis() - 20)) {
	            	//zoom = 1 + ((offset_y - 20) * 2 / (Helper.get_smallestAxis() - 40));
	            	zoom = 1 + (((int)event.getY() - 20) * 2 / (Helper.get_smallestAxis() - 40));
	            	bluePaint.setStrokeWidth(zoom);
	            }
	            else {*/
            	if (offset_x >= 0 && offset_x <= 50 && offset_y > 20 && offset_y < (Helper.get_smallestAxis() - 20)) {
            		HandleAcceleration(offset_x, offset_y);
            	}
            	else {
		            p.x += (deltaX / zoom);
		            p.y += (deltaY / zoom);
            	}
	            //}
	            
	            v.invalidate();
	            
	            offset_x = (int)event.getX();
	            offset_y = (int)event.getY();
            }
            
            break;
		}
		case MotionEvent.ACTION_UP: {
			
			float x1 = event.getX(); // * zoom;
            float y1 = event.getY(); // * zoom;
            double smallestDistance = 1000000;
            Node localSelectedNode = null;
            
            for(int i = 0; i < Track.Instance().Nodes.size(); i++) {
            	float x2 = ((Track.Instance().Nodes.get(i).X + p.x) * zoom) + (4.5f * zoom);
	            float y2 = ((Track.Instance().Nodes.get(i).Y + p.y) * zoom) + (4.5f * zoom);
	            
	            double distance = Helper.Distance(x1, y1, x2, y2);
	            if (distance < (9 * zoom) && distance < smallestDistance) {
	            	smallestDistance = distance;
	            	localSelectedNode = Track.Instance().Nodes.get(i);
	            }
            }
            
            if (localSelectedNode != null) {
            	selectedNode = localSelectedNode;
            	ChangeNode(new Node[] {selectedNode} );
            	v.invalidate();		            	
            }
            
            break;
		}
		}
		return true;
	}
	
	private void ChangeNode(Node[] nodes) {
		// todo: ik krijg hier een array van nodes door omdat ooit meerdere wissels tegelijk zullen kunnen switchen via een swipe-beweging
		// maar voorlopig dus maar ��ntje => nodes[0]
		
		if (nodes[0].NodeTypeName.equalsIgnoreCase("kruispunt")) {
			// doe niks
		}
		else {
			if (nodes[0].NodeTypeName.equalsIgnoreCase("ontkoppelrail")) {
				Connector.Instance().SendData(nodes[0].NodeAddress1, nodes[0].NodeAddress2);
			}
			else {
				if (nodes[0].NodeTypeName.equalsIgnoreCase("driewegwissel")) {
					switch (nodes[0].Stand)	{
					case 0: {
						// ga naar rechtdoor
						Connector.Instance().SendData(33, nodes[0].NodeAddress2);
						Connector.Instance().SendData(33, nodes[0].NodeAddress1);
						nodes[0].Stand++;
						break;
					}
					case 1: {
						// ga naar links
						Connector.Instance().SendData(33, nodes[0].NodeAddress2);
						Connector.Instance().SendData(34, nodes[0].NodeAddress1);
						nodes[0].Stand++;
						break;
					}		
					case 2: {
						// ga naar rechts
						Connector.Instance().SendData(34, nodes[0].NodeAddress2);
						Connector.Instance().SendData(33, nodes[0].NodeAddress1);
						nodes[0].Stand = 0;
						break;
					}
					}
				}
				else {
					if (nodes[0].NodeTypeName.equalsIgnoreCase("dubbelekruiswissel")) {
						switch (nodes[0].Stand)	{
						case 0: {
							// ga naar plat rechtdoor
							Connector.Instance().SendData(34, nodes[0].NodeAddress2);
							Connector.Instance().SendData(34, nodes[0].NodeAddress1);
							nodes[0].Stand++;
							break;
						}
						case 1: {
							// ga naar rechts
							Connector.Instance().SendData(33, nodes[0].NodeAddress2);
							Connector.Instance().SendData(34, nodes[0].NodeAddress1);
							nodes[0].Stand++;
							break;
						}
						case 2: {
							// ga naar links
							Connector.Instance().SendData(34, nodes[0].NodeAddress2);
							Connector.Instance().SendData(33, nodes[0].NodeAddress1);
							nodes[0].Stand++;
							break;
						}
						case 3: {
							// ga naar schuin rechtdoor
							Connector.Instance().SendData(33, nodes[0].NodeAddress2);
							Connector.Instance().SendData(33, nodes[0].NodeAddress1);
							nodes[0].Stand = 0;
							break;
						}
						}
					}
					else {
						int commando;
						
						if (nodes[0].Rechtdoor) {
							commando = 34;
							nodes[0].Rechtdoor = false;
						}
						else {
							commando = 33;
							nodes[0].Rechtdoor = true;
						}
						
						Connector.Instance().SendData(commando, nodes[0].NodeAddress1);
					}
				}
			}
			
			
		}		
	}

	@Override
	public void Draw(Canvas canvas) {

		if (Track.Instance().Rails == null | Track.Instance().Nodes == null) {
			//canvas.drawText(Helper.logtext, 100, 20, uiPaint);
			return;
		}
		
		matrix.reset();
		//matrix.postRotate(-Track.Instance().azimuth_angle, (Helper.get_largestAxis()) / 2.0f, (Helper.get_smallestAxis()) / 2.0f);
		matrix.postTranslate(p.x, p.y);
		matrix.postScale(zoom, zoom);
		canvas.setMatrix(matrix);
		
		for(int i = 0; i < Track.Instance().Rails.size(); i++) {
			//canvas.drawLine((Track.Instance().Rails.get(i).StartX + p.x) * zoom, (Track.Instance().Rails.get(i).StartY + p.y) * zoom, (Track.Instance().Rails.get(i).EndX + p.x) * zoom, (Track.Instance().Rails.get(i).EndY + p.y) * zoom, bluePaint);
			canvas.drawLine((Track.Instance().Rails.get(i).StartX + 0) * 1, (Track.Instance().Rails.get(i).StartY + 0) * 1, (Track.Instance().Rails.get(i).EndX + 0) * 1, (Track.Instance().Rails.get(i).EndY + 0) * 1, bluePaint);
		}
		
		for(int i = 0; i < Track.Instance().Nodes.size(); i++) {
			if (selectedNode != null && Track.Instance().Nodes.get(i) == selectedNode) {
				//canvas.drawCircle(((Track.Instance().Nodes.get(i).X + p.x) * zoom) + (4.5f * zoom), ((Track.Instance().Nodes.get(i).Y + p.y) * zoom) + (4.5f * zoom), 9 * zoom, greenPaint);
				canvas.drawCircle(((Track.Instance().Nodes.get(i).X + 0) * 1) + (4.5f * 1), ((Track.Instance().Nodes.get(i).Y + 0) * 1) + (4.5f * 1), 9 * 1, greenPaint);
			}
			
			matrix.reset();
			matrix.postScale(9.0f/32.0f, 9.0f/32.0f);
			matrix.postRotate(Track.Instance().Nodes.get(i).Rotation, 4.5f, 4.5f);
			//matrix.postTranslate((Track.Instance().Nodes.get(i).X + p.x + 0.5f), (Track.Instance().Nodes.get(i).Y + p.y + 0.5f));
			matrix.postTranslate((Track.Instance().Nodes.get(i).X + 0 + 0.5f), (Track.Instance().Nodes.get(i).Y + 0 + 0.5f));
			//matrix.postScale(zoom, zoom);
			if (Track.Instance().Nodes.get(i).NodeTypeName.equalsIgnoreCase("kruispunt") | Track.Instance().Nodes.get(i).NodeTypeName.equalsIgnoreCase("ontkoppelrail")) {
				canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName), matrix, drawPaint);
			}
			else {
				if (Track.Instance().Nodes.get(i).NodeTypeName.equalsIgnoreCase("driewegwissel")) {
					switch (Track.Instance().Nodes.get(i).Stand)	{
					case 0: {
						canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_1"), matrix, drawPaint);
						break;
					}
					case 1: {
						canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_2"), matrix, drawPaint);
						break;
					}
					case 2: {
						canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_3"), matrix, drawPaint);
						break;
					}
					}
				}
				else {
					if (Track.Instance().Nodes.get(i).NodeTypeName.equalsIgnoreCase("dubbelekruiswissel")) {
						switch (Track.Instance().Nodes.get(i).Stand)	{
						case 0: {
							canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_1"), matrix, drawPaint);
							break;
						}
						case 1: {
							canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_2"), matrix, drawPaint);
							break;
						}
						case 2: {
							canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_3"), matrix, drawPaint);
							break;
						}
						case 3: {
							canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_4"), matrix, drawPaint);
							break;
						}
						}
					}
					else {
						if (Track.Instance().Nodes.get(i).Rechtdoor) {
							canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_1"), matrix, drawPaint);	
						}
						else {
							canvas.drawBitmap(map.get(Track.Instance().Nodes.get(i).NodeTypeName + "_2"), matrix, drawPaint);
						}	
					}
				}
			}		
					
		}
		
		matrix.reset();
		canvas.setMatrix(matrix);
	
		// UI
		// Zoom widget
		/*canvas.drawLine(20, 20, 20, Helper.get_smallestAxis() - 20, uiPaint);
		canvas.drawLine(26, 20, 26, Helper.get_smallestAxis() - 20, uiPaint);
		canvas.drawLine(15, 20 + ((zoom - 1) * (Helper.get_smallestAxis() - 40) / 2), 32, 20 + ((zoom - 1) * (Helper.get_smallestAxis() - 40) / 2), uiPaint);
		*/
		DrawSlider(canvas);

		/*// Alarm button
		matrix.reset();
		matrix.postScale(Helper.get_smallestAxis() / 8.0f / 48.0f, Helper.get_smallestAxis() / 8.0f / 48.0f);
		matrix.postTranslate(Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f), 0); //Helper.get_screenHeight() - (Helper.get_smallestAsix() / 6.0f));
		canvas.drawBitmap(map.get("alarmbutton"), matrix, drawPaint);
		// Train button
		matrix.reset();
		matrix.postScale(Helper.get_smallestAxis() / 8.0f / 48.0f, Helper.get_smallestAxis() / 8.0f / 48.0f);
		matrix.postTranslate(Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f), Helper.get_smallestAxis() - (Helper.get_smallestAxis() / 8.0f));
		canvas.drawBitmap(map.get("trainbutton"), matrix, drawPaint);*/
		DrawButtons(canvas, matrix);
		
		/*float[] R = null;
		float[] I = null;
		float[] gravity = null;
		float[] geomagnetic = null;
		SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
		if (R != null) {
			Pitch = R.toString();
		}*/		
		//canvas.drawText(_pitch, 100, 20, uiPaint);
		
		//canvas.drawText(Float.toString(zoom), 300, 300, uiPaint);
	}


}
