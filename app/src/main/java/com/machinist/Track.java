package com.machinist;

import java.util.List;

public class Track {
	private Track() {
		
	}
	
	public static synchronized Track Instance()
    {
      if (ref == null)
          ref = new Track();
      return ref;
    }
    private static Track ref;
    
	
	public List<Rail> Rails;
    public List<Node> Nodes;
    public boolean AlarmState = false;
    
    public float azimuth_angle = 0;
    public float pitch_angle = 0;
    public float roll_angle = 0;
    
    public void Destroy() {
    	Rails = null;
    	Nodes = null;
    	ref = null;
    }
}
