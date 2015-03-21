package com.machinist;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Helper {
	public static double Distance(double x1, double y1, double x2, double y2) {
	  double dx   = x2 - x1;         //horizontal difference 
	  double dy   = y2 - y1;         //vertical difference 
	  return Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
	}
	
	public static boolean AppStopped = false;
	
	public static InputStream getInputStream(String fileName) {
        try {
        	URL url; url = new URL(Helper.get_rootDir() + fileName);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	private static float _screenWidth;
	private static float _screenHeight;
	private static String _rootDir;
	private static String _trackName;
	
	public static String get_rootDir() {
		return _rootDir;
	}

	public static void set_rootDir(String _rootDir) {
		Helper._rootDir = _rootDir;
	}

	public static float get_screenWidth() {
		return _screenWidth;
	}

	public static void set_screenWidth(float screenWidth) {
		_screenWidth = screenWidth;
	}

	public static float get_screenHeight() {
		return _screenHeight;
	}

	public static void set_screenHeight(float screenHeight) {
		_screenHeight = screenHeight;
	}
	
	public static float get_smallestAxis() {
		return Math.min(_screenHeight, _screenWidth);
	}
	
	public static float get_largestAxis() {
		return Math.max(_screenHeight, _screenWidth);
	}
	
	public static String logtext = "";
	
	public static void AddTextToLog(String text)
	{
    	Helper.logtext = text;
    	//hRefresh.sendEmptyMessage(0);
	}

	public static String get_trackName() {
		return _trackName;
	}

	public static void set_trackName(String _trackName) {
		Helper._trackName = _trackName;
	}
}
