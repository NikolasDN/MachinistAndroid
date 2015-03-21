package com.machinist;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public abstract class BaseViewMode implements IViewMode {
	private String _activeMode;
	Paint drawPaint = new Paint();
	Paint grayPaint = new Paint();
	Paint uiPaint = new Paint();
	Paint redPaint = new Paint();
	Paint yellowPaint = new Paint();
	Paint bluePaint = new Paint();
	Paint whitePaint = new Paint();
	Map<String, Bitmap> map = new HashMap<String, Bitmap>();
	
	public BaseViewMode() {
		super();
		_activeMode = "";
		
		drawPaint.setAntiAlias(true);
		drawPaint.setFilterBitmap(true);
		uiPaint.setColor(Color.DKGRAY);
		uiPaint.setStrokeWidth(3);
		//greenPaint.setColor(Color.GREEN);
		//greenPaint.setStrokeWidth(7);
		redPaint.setColor(Color.RED);
		redPaint.setStrokeWidth(3);
		yellowPaint.setColor(Color.YELLOW);
		yellowPaint.setStrokeWidth(3);
		bluePaint.setColor(Color.BLUE);
		//bluePaint.setStrokeWidth(3);
		bluePaint.setStrokeWidth(1);
		whitePaint.setColor(Color.WHITE);
		whitePaint.setStrokeWidth(3);
		grayPaint.setColor(Color.GRAY);
		
		//setBackgroundColor(Color.GRAY);
	}

	public String get_activeMode() {
		return _activeMode;
	}

	public void set_activeMode(String _activeMode) {
		this._activeMode = _activeMode;
	}
	
	protected void HandleAcceleration(int offset_x, int offset_y) {
		float newSpeed = - 15 + (((int)offset_y - 20) * 30 / (Helper.get_smallestAxis() - 40));
		if (Math.abs(newSpeed - TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Speed) > 1.0f) {
			if (newSpeed > TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Speed) {
				newSpeed = TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Speed + 1.0f;	
			}
			else {
				newSpeed = TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Speed - 1.0f;
			}
		}
		int intNewSpeed = (int)newSpeed;
		int intOldSpeed = (int)TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Speed;
		
    	TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Speed = newSpeed;

		if (intNewSpeed != intOldSpeed) {
			// eventueel omkeren
    		if (TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Richting.equals("-") && intNewSpeed > 0) {
    			Omkeren();
    		}
    		else {
    			if (TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Richting.equals("+") && intNewSpeed < 0) {
    				Omkeren();
    			}
    		}
    		if (intNewSpeed > 0) {
    			TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Richting = "+";
    		}
    		if (intNewSpeed < 0) {
    			TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Richting = "-";
    		}			          			
    		
    		// versturen
        	Connector.Instance().SendData(Math.abs(intNewSpeed), TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Address);
		}		
	}
	
	protected void HandleButtonTouches(int offset_x, int offset_y, String activeMode) {
        // ALARM
        float buttonHeight = Helper.get_smallestAxis() / 8.0f;
        float buttonX = Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f);
        if (offset_x >= buttonX && offset_y <= buttonHeight) {
        	if (!Track.Instance().AlarmState) {
        		Connector.Instance().SendData(97, 0);
        		Track.Instance().AlarmState = true;
        	}
        	else {
        		Connector.Instance().SendData(96, 0);
        		Track.Instance().AlarmState = false;
        	}        		
        }
        
        // FUNCTION
        buttonX = Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f);
        if (offset_x >= buttonX && offset_y <= buttonHeight && offset_y >= 100.0f) {
        	//Connector.Instance().SendData(97, 0);
        	// TODO: Functie voodoo toevoegen
        }
        
		// TRAINVIEW
        //float buttonY = Helper.get_smallestAxis() - (Helper.get_smallestAxis() / 8.0f);
        //if (offset_x >= buttonX && offset_y >= buttonY) {
        if (offset_x >= (Helper.get_largestAxis() - 200.0f) && offset_y >= (Helper.get_smallestAxis() - 78.0f)) {
        	set_activeMode(activeMode);
        }
	}
	
	protected void DrawSlider(Canvas canvas) {
		if (TrainInventory.Instance().Trains.size() > 0) {
			// Slider
			canvas.drawRect(0, 0, 50, Helper.get_smallestAxis(), grayPaint);
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
			canvas.drawLine(2, 20 + ((TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Speed + 15) * (Helper.get_smallestAxis() - 40) / 30), 48, 20 + ((TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Speed + 15) * (Helper.get_smallestAxis() - 40) / 30), whitePaint);
		}
	}
		
	
	protected void DrawButtons(Canvas canvas, Matrix matrix) {
		// Alarm button
		matrix.reset();
		matrix.postScale(Helper.get_smallestAxis() / 8.0f / 48.0f, Helper.get_smallestAxis() / 8.0f / 48.0f);
		matrix.postTranslate(Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f), 0); //Helper.get_screenHeight() - (Helper.get_smallestAsix() / 6.0f));
		if (!Track.Instance().AlarmState) {
			canvas.drawBitmap(map.get("alarmbutton"), matrix, drawPaint);
		}
		else {
			canvas.drawBitmap(map.get("alarmbutton2"), matrix, drawPaint);
		}
		
		// Function button
		matrix.reset();
		matrix.postScale(Helper.get_smallestAxis() / 8.0f / 48.0f, Helper.get_smallestAxis() / 8.0f / 48.0f);
		matrix.postTranslate(Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f), 100.0f); //Helper.get_screenHeight() - (Helper.get_smallestAsix() / 6.0f));
		canvas.drawBitmap(map.get("functionbutton"), matrix, drawPaint);
		
		if (TrainInventory.Instance().Trains.size() > 0) {
			// Train button
			matrix.reset();
			//matrix.postScale(Helper.get_smallestAxis() / 8.0f / 48.0f, Helper.get_smallestAxis() / 8.0f / 48.0f);
			//matrix.postTranslate(Helper.get_largestAxis() - (Helper.get_smallestAxis() / 8.0f), Helper.get_smallestAxis() - (Helper.get_smallestAxis() / 8.0f));
			matrix.postTranslate(Helper.get_largestAxis() - 200.0f, Helper.get_smallestAxis() - 78.0f);
			canvas.drawBitmap(TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Picture, matrix, drawPaint);
		}
	}
	
	private void Omkeren() {
		Connector.Instance().SendData(15, TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Address);
		// EDITS voodoo
		Connector.Instance().SendData(1, TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Address);
		Connector.Instance().SendData(0, TrainInventory.Instance().Trains.get(TrainInventory.Instance().selectedTrain).Address);
	}
}
