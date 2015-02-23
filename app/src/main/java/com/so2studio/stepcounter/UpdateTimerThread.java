package com.so2studio.stepcounter;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;


/**
 * This class contains the methods that are necessary to run a timer in a separate thread.
 *
 */
public class UpdateTimerThread implements Runnable{
	
	/**
	 * Timer value TextView.
	 */
	public TextView timerValue;
	/**
	 * Timer start time.
	 */
	public long startTime = 0L;
	/**
	 * A HAndler object.
	 */
	public Handler customHandler = new Handler();
	/**
	 * Elapsed time in milliseconds.
	 */
	public long timeInMilliseconds = 0L;
	
	public long timeSwapBuff = 0L;
	public long updatedTime = 0L;
    
	
	/**
	 * Run method implementation.
	 */
	public void run() {
        this.timeInMilliseconds = SystemClock.uptimeMillis() - this.startTime;
        this.updatedTime = this.timeSwapBuff + this.timeInMilliseconds;
        int secs = (int) (this.updatedTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (this.updatedTime % 1000);

        this.timerValue.setText("" + mins + ":"

                + String.format("%02d", secs) + ":"

                + String.format("%03d", milliseconds));

        this.customHandler.postDelayed(this, 0);

    }
	
	/**
	 * Timer pause method.
	 */
	public void timerPause(){
		this.timeSwapBuff += this.timeInMilliseconds;
    	this.customHandler.removeCallbacks(this);
	}
	
	/**
	 * Timer resume method.
	 */
	public void timerResume(){
		this.startTime = SystemClock.uptimeMillis();
    	this.customHandler.postDelayed(this, 0);
	}
	
	
	
}
