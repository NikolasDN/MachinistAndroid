package com.machinist;

import java.util.ArrayList;
import java.util.List;

public class TrainInventory {
	
	public int selectedTrain = 0;
	
	private TrainInventory() {
		
	}
	
	public static synchronized TrainInventory Instance()
    {
      if (ref == null)
          ref = new TrainInventory();
      return ref;
    }
    private static TrainInventory ref;
    
	public List<Train> Trains = new ArrayList<Train>();
	
	public void Destroy() {
		Trains = null;
		ref = null;
	}
}
