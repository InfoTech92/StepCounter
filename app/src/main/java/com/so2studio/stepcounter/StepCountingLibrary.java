package com.so2studio.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;

public class StepCountingLibrary implements SensorEventListener {
	
	
	public boolean begin;
	public int i;
	public int k;
	public double max;
	public int steps;
	public double a;
	public double b;
	public double th;
	public Handler handler;
	public boolean running;
	
	public StepCountingLibrary(Handler hand){
		super();
		
		begin = false;
		i = 0;
		steps = 0;
		
		a = 0.2679;
		b = 3.1541;
		
		handler = hand;
		
		running = false;
	}
	
	@Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    	
		if(running = false){
			return;
		}
		
		i++;
		
		
		if( ! begin ){
			if(sensorEvent.values[0] >= 0.08 && 10 + sensorEvent.values[1] >= -0.3 && 10 - sensorEvent.values[1] <= 0.3){
				begin = true;
				k = i;
				max = sensorEvent.values[0];
			}
			
		}else{
			th = a / (i-k) + b;
			
			
			
			
			
			if(max - sensorEvent.values[0] >= th){
				steps++;
				k = i;
				max = sensorEvent.values[0];
				
				
			}else{
				if(sensorEvent.values[0] > max){
					max = sensorEvent.values[0];
				}
			}
			
		}
		
		handler.sendEmptyMessage(1);
		
		
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	
    }
    
    
    
}
