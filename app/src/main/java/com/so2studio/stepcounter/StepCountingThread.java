package com.so2studio.stepcounter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
 

 
/**
 * 
 * @author Created by Saeid on 22-4-2014.
 * @author Alessio
 */
final class StepCountingThread implements Runnable {
 
    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    public StepCountingLibrary mListener;
    private HandlerThread mHandlerThread;
    public Handler handler;
    
    
    public StepCountingThread(Context context, Handler hand) {

    	mContext = context;
    	handler = hand;
    	
    }
 
    
    
    @Override
    public void run() {
    	
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       
        mHandlerThread = new HandlerThread("AccelerometerLogListener");
        mHandlerThread.start();
        
        Handler handlerThread = new Handler(mHandlerThread.getLooper());
 
        mListener = new StepCountingLibrary(this.handler);

        mSensorManager.registerListener(mListener, mSensor, 500000 , handlerThread);
    	
    }
 
 
}