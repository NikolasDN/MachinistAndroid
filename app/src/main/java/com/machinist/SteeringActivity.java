package com.machinist;

import android.app.Activity;
//import android.content.Context;
import android.content.pm.ActivityInfo;
/*import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;*/
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class SteeringActivity extends Activity { // implements SensorEventListener {
	@Override
	protected void onDestroy() {
		Connector.Instance().DisconnectClient();
    	Track.Instance().Destroy();
    	TrainInventory.Instance().Destroy();
		
		super.onDestroy();
	}

	SteeringView steeringView;
	
	//private SensorManager sensorService;
	//private Sensor sensor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        steeringView = new SteeringView(this);
        setContentView(steeringView);
        
        //sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //steeringView.set_pitch("Unavailable");
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
        	Helper.AppStopped = true;
        	if (steeringView != null) {
        		steeringView.loaderThread.interrupt();        		
        	}
            Connector.Instance().DisconnectClient();
            Track.Instance().Destroy();
            TrainInventory.Instance().Destroy();
            finish();
            //DisconnectClientSocket();
        }
        else if(keyCode == KeyEvent.KEYCODE_HOME)
        {
        	Helper.AppStopped = true;
        	if (steeringView != null) {
        		steeringView.loaderThread.interrupt();        		
        	}
        	Connector.Instance().DisconnectClient();
        	Track.Instance().Destroy();
        	TrainInventory.Instance().Destroy();
        	finish();
        	//DisconnectClientSocket();
        }
        return super.onKeyDown(keyCode, event);
    }
		
	/*@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}*/

	/*@Override
	public void onSensorChanged(SensorEvent event) {
		if (event != null) {
			Track.Instance().azimuth_angle = event.values[0];
			steeringView.postInvalidate();
			
			//Helper.logtext = Float.toString(Track.Instance().azimuth_angle);
			
			*//*synchronized(this) {
				switch (event.sensor.getType()) {
					case Sensor.TYPE_GYROSCOPE: {
						Track.Instance().azimuth_angle = event.values[0];
						Track.Instance().pitch_angle = event.values[1];
						Track.Instance().roll_angle = event.values[2];
						
						//steeringView.set_pitch(String.format("%1$ / %2$} / %3$}", Float.toString(azimuth_angle), Float.toString(pitch_angle), Float.toString(roll_angle)));
						//String log = String.format("%1$ / %2$} / %3$}", Float.toString(Track.Instance().azimuth_angle), Float.toString(Track.Instance().pitch_angle), Float.toString(Track.Instance().roll_angle));
						Helper.logtext = Float.toString(Track.Instance().azimuth_angle);
					}
				}
			}*//*
		}
	}*/
	
	/*@Override
	protected void onResume() {
		super.onResume();

		sensorService.registerListener(this, sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
	}*/

	/*@Override
	protected void onStop() {
		super.onStop();
		
		sensorService.unregisterListener(this, sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION));
	}*/
}
