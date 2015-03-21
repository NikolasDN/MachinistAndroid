package com.machinist;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class TrainViewMode extends BaseViewMode {

	//Paint greenPaint = new Paint();
	
		
	Point p = new Point();
	Point startP = new Point(100, 50);
	//float speed = 0;
	//float selectedSize = 1.0f;
	
	private int offset_x = 0;
    private int offset_y = 0;
    
    //Map<String, Bitmap> map = new HashMap<String, Bitmap>();
    Matrix matrix = new Matrix();
    boolean busy = false;
	
	@Override
	public void Initialize(View parent) {
				
		parent.setBackgroundColor(Color.GRAY);
		
		//map.put("alarmbutton", BitmapFactory.decodeResource(parent.getResources(), R.drawable.alarmbutton));
		//map.put("trainbutton", BitmapFactory.decodeResource(parent.getResources(), R.drawable.trainbutton));
		map.put("alarmbutton", BitmapFactory.decodeResource(parent.getResources(), R.drawable.alarmbutton));
		map.put("alarmbutton2", BitmapFactory.decodeResource(parent.getResources(), R.drawable.alarmbutton2));
		//map.put("trainbutton", BitmapFactory.decodeResource(parent.getResources(), R.drawable.trainbutton));
		map.put("functionbutton", BitmapFactory.decodeResource(parent.getResources(), R.drawable.functionbutton));
		
		p.x = 0;
		p.y = 0;
	}

	@Override
	public boolean Touch(View v, MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
            offset_x = (int)event.getX();
            offset_y = (int)event.getY();
            
            // handle button touches
            // Trains
            if (offset_x > 50) {
	            int kolom = 0;
	    		int rij = 0;
	            for(int i = 0; i < TrainInventory.Instance().Trains.size(); i++) {
	    			if (startP.y + (rij * 80) + 150 > Helper.get_smallestAxis()) {
	    				kolom++;
	    				rij = 0;
	    			}			
	    			
	    			if (offset_x >= p.x + startP.x + (kolom * 200) && offset_x <= p.x + startP.x + (kolom * 200) + 200 && offset_y >= startP.y + (rij * 80) && offset_y <= startP.y + (rij * 80) + 80) {
	    				TrainInventory.Instance().selectedTrain = i;
	    			}
	    			rij++;
	            }
            }
            
            /*// ALARM
            float buttonHeight = Helper.get_smallestAxis() / 8.0f;
            float buttonX = Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f);
            if (offset_x >= buttonX && offset_y <= buttonHeight) {
            	Connector.Instance().SendData(97, 0);
            }
    		// TRAINVIEW
            float buttonY = Helper.get_smallestAxis() - (Helper.get_smallestAxis() / 8.0f);
            if (offset_x >= buttonX && offset_y >= buttonY) {
            	set_activeMode("Track");
            }*/
            HandleButtonTouches(offset_x, offset_y, "Track");
            
            break;
		}
		case MotionEvent.ACTION_MOVE: {
            //
			if (!busy) {
				busy = true;
				int deltaX = (int)event.getX() - offset_x;
	            int deltaY = (int)event.getY() - offset_y;
	            
	            if (deltaX != 0 || deltaY != 0) {
	            	if (offset_x >= 0 && offset_x <= 50 && offset_y > 20 && offset_y < (Helper.get_smallestAxis() - 20)) {
	            		HandleAcceleration(offset_x, offset_y);
	            		/*float newSpeed = - 15 + (((int)offset_y - 20) * 30 / (Helper.get_smallestAxis() - 40));
	            		if (Math.abs(newSpeed - TrainInventory.Instance().Trains.get(selectedTrain).Speed) > 1.0f) {
	            			if (newSpeed > TrainInventory.Instance().Trains.get(selectedTrain).Speed) {
	            				newSpeed = TrainInventory.Instance().Trains.get(selectedTrain).Speed + 1.0f;	
	            			}
	            			else {
	            				newSpeed = TrainInventory.Instance().Trains.get(selectedTrain).Speed - 1.0f;
	            			}
	            		}
	            		int intNewSpeed = (int)newSpeed;
	            		int intOldSpeed = (int)TrainInventory.Instance().Trains.get(selectedTrain).Speed;
	            		
		            	TrainInventory.Instance().Trains.get(selectedTrain).Speed = newSpeed;
		            	//Helper.AddTextToLog(Float.toString(newSpeed));

	            		if (intNewSpeed != intOldSpeed) {
                			// eventueel omkeren
                    		if (TrainInventory.Instance().Trains.get(selectedTrain).Richting.equals("-") && intNewSpeed > 0) {
                    			Omkeren();
                    		}
                    		else {
                    			if (TrainInventory.Instance().Trains.get(selectedTrain).Richting.equals("+") && intNewSpeed < 0) {
                    				Omkeren();
                    			}
                    		}
                    		if (intNewSpeed > 0) {
                    			TrainInventory.Instance().Trains.get(selectedTrain).Richting = "+";
                    		}
                    		if (intNewSpeed < 0) {
                    			TrainInventory.Instance().Trains.get(selectedTrain).Richting = "-";
                    		}
	                		
	                		// versturen
	    	            	Connector.Instance().SendData(Math.abs(intNewSpeed), TrainInventory.Instance().Trains.get(selectedTrain).Address);
	            		}      
	            		*/
	            		// SPAMMEN!
	            		//Connector.Instance().SendData(Math.abs(intNewSpeed), TrainInventory.Instance().Trains.get(selectedTrain).Address);
		            }
	            	else {
	            		if (p.x <= 0 && p.x >= -(((TrainInventory.Instance().Trains.size()) * 200.0f) - Helper.get_largestAxis())) {
	                		p.x += deltaX; 
	                		if (p.x > 0) {
	                			p.x = 0;
	                		}
	                		if (p.x < -(((TrainInventory.Instance().Trains.size()) * 200.0f) - Helper.get_largestAxis())) {
	                			p.x = Math.round(-(((TrainInventory.Instance().Trains.size()) * 200.0f) - Helper.get_largestAxis()));
	                		}
	                	}
	            	}
		            
		            v.invalidate();
		            
		            offset_x = (int)event.getX();
		            offset_y = (int)event.getY();
	            }
	            busy = false;
			}
			
            break;
		}
		}
		return true;
	}
	
	/*private void Omkeren() {
		Connector.Instance().SendData(15, TrainInventory.Instance().Trains.get(selectedTrain).Address);
		// EDITS voodoo
		Connector.Instance().SendData(1, TrainInventory.Instance().Trains.get(selectedTrain).Address);
		Connector.Instance().SendData(0, TrainInventory.Instance().Trains.get(selectedTrain).Address);
	}*/
		
	@Override
	public void Draw(Canvas canvas) {

		// Scrollable speed widgets
		/*for(int i = 0; i < TrainInventory.Instance().Trains.size(); i++) {
			canvas.drawLine(p.x + ((i + 0.5f) * 100), 20, p.x + ((i + 0.5f) * 100), Helper.get_smallestAxis() - 20, uiPaint);
			canvas.drawLine(p.x + ((i + 0.5f) * 100 + 6), 20, p.x + ((i + 0.5f) * 100 + 6), Helper.get_smallestAxis() - 20, uiPaint);
			
			matrix.reset();
			//matrix.postScale(0.5f, 0.5f);
			matrix.postTranslate(p.x + (i * 100), 0 + ((-1 * TrainInventory.Instance().Trains.get(i).Speed) + 100) * ((Helper.get_smallestAxis() - 20) / 200));
			canvas.drawBitmap(TrainInventory.Instance().Trains.get(i).Picture, matrix, drawPaint);
			//canvas.drawLine(15, 20 + ((zoom - 1) * (Helper.get_smallestAxis() - 40) / 2), 32, 20 + ((zoom - 1) * (Helper.get_smallestAxis() - 40) / 2), uiPaint);			
		}*/
		
		// Train buttons
		int kolom = 0;
		int rij = 0;
		int selectedKolom = 0;
		int selectedRij = 0;
		for(int i = 0; i < TrainInventory.Instance().Trains.size(); i++) {
			matrix.reset();
			//if (startP.y + (rij * 80) + 100 > Helper.get_smallestAxis()) {
			if (startP.y + (rij * 80) + 150 > Helper.get_smallestAxis()) {
				kolom++;
				rij = 0;
			}			

			matrix.postTranslate(p.x + startP.x + (kolom * 200), startP.y + (rij * 80));
			if (TrainInventory.Instance().selectedTrain != i) {
				canvas.drawBitmap(TrainInventory.Instance().Trains.get(i).Picture, matrix, drawPaint);	
			}			
			else {
				selectedKolom = kolom;
				selectedRij = rij;
			}
			rij++;
		}
		// draw selected train
		if (TrainInventory.Instance().Trains.size() > 0) {
			matrix.reset();
			matrix.postScale(1.2f, 1.2f);
			matrix.postTranslate(p.x + startP.x - 10 + (selectedKolom * 200), startP.y - 8 + (selectedRij * 80));
			canvas.drawRect(p.x + startP.x - 10 + (selectedKolom * 200) - 2, startP.y - 8 + (selectedRij * 80) - 2, p.x + startP.x - 10 + (selectedKolom * 200) + 240 + 2, startP.y - 10 + (selectedRij * 80) + 96 + 2, redPaint);
			canvas.drawBitmap(TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Picture, matrix, drawPaint);
			
			// Slider
			/*canvas.drawRect(0, 0, 50, Helper.get_smallestAxis(), grayPaint);
			canvas.drawLine(20, 20, 20, Helper.get_smallestAxis() - 20, uiPaint);
			canvas.drawLine(26, 20, 26, Helper.get_smallestAxis() - 20, uiPaint);
			for(int i = 0; i <= 13; i++) {
				if (i % 2 == 0) {
					canvas.drawLine(23, 20 + (i * (Helper.get_smallestAxis() - 40) / 30), 23, 20 + ((i + 1) * (Helper.get_smallestAxis() - 40) / 30), bluePaint);
				}
				else {
					canvas.drawLine(23, 20 + (i * (Helper.get_smallestAxis() - 40) / 30), 23, 20 + ((i + 1) * (Helper.get_smallestAxis() - 40) / 30), yellowPaint);
				}
			}
			canvas.drawLine(23, 20 + (14 * (Helper.get_smallestAxis() - 40) / 30), 23, 20 + (16 * (Helper.get_smallestAxis() - 40) / 30), redPaint);
			for(int i = 16; i <= 29; i++) {
				if (i % 2 == 0) {
					canvas.drawLine(23, 20 + (i * (Helper.get_smallestAxis() - 40) / 30), 23, 20 + ((i + 1) * (Helper.get_smallestAxis() - 40) / 30), yellowPaint);
				}
				else {
					canvas.drawLine(23, 20 + (i * (Helper.get_smallestAxis() - 40) / 30), 23, 20 + ((i + 1) * (Helper.get_smallestAxis() - 40) / 30), bluePaint);
				}
			}
			canvas.drawLine(2, 20 + ((TrainInventory.Instance().Trains.get(selectedTrain).Speed + 15) * (Helper.get_smallestAxis() - 40) / 30), 48, 20 + ((TrainInventory.Instance().Trains.get(selectedTrain).Speed + 15) * (Helper.get_smallestAxis() - 40) / 30), whitePaint);
			*/
			DrawSlider(canvas);
		}
		/*if (selectedSize < 1.5f) {
			selectedSize += 0.02f;
		}*/
		
		//canvas.drawText(Float.toString(speed), 500, 20, uiPaint);
		
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
	}

}
