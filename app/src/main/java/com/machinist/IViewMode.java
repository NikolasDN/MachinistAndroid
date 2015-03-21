package com.machinist;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public interface IViewMode {
	void Initialize(View parent);
	boolean Touch(View v, MotionEvent event);
	void Draw(Canvas canvas);
		
	String get_activeMode();
	void set_activeMode(String _activeMode);
}
