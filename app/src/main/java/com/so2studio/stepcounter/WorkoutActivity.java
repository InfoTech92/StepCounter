package com.so2studio.stepcounter;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mainactivity.R;

/**
 * This activity is the main training activity: it shows the actual training level, training time
 * and suggests when the user should accelerate or decelerate.
 * From this activity it is possible to pause and resume the training.
 *
 */
public class WorkoutActivity extends ActionBarActivity implements Animation.AnimationListener, View.OnClickListener {
	
	
	/**
	 * This field defines a "zoom in" animation.
	 */
    private Animation zoom_in;
    /**
     * This field defines a "zoom out" animation.
     */
    private Animation zoom_out;
    /**
     * This field defines the training start button.
     */
    private ImageView btnSart;
    /**
     * This field defines the training stop button.
     */
    private ImageView btnStop;
    /**
     * This field defines the left shoe image.
     */
    private ImageView shoeImage1;
    /**
     * This field defines the right shoe image.
     */
    private ImageView shoeImage2;
    
    /**
     * Timer thread.
     */
    private UpdateTimerThread updateTimerThread;
    
    /**
     * Step counting thread.
     */
	private StepCountingThread stepCountingThread;
    
    /**
     * This boolean value indicates if the user is actually running.
     */
    private boolean running;
    
    
    /**
     * Actual Training object.
     */
    public Workout training;
    
    
    
    public TextView textStepCount;
    
    
    public Handler handler;
    
    /**
     * OnCreate method implementation.
     */
    @SuppressLint("HandlerLeak")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        //Images initialization...
        this.shoeImage1 = (ImageView) findViewById(R.id.shoeImage1);
        this.shoeImage2 = (ImageView) findViewById(R.id.shoeImage2);

        this.shoeImage1.setOnClickListener(this);
        this.shoeImage2.setOnClickListener(this);

        
        this.textStepCount = (TextView) findViewById(R.id.textStepCount);

        //Animations are loaded and their listeners are set up
        this.zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        this.zoom_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);

        this.zoom_in.setAnimationListener(this);
        this.zoom_out.setAnimationListener(this);


        //Buttons initialization...
        this.btnSart = (ImageView) findViewById(R.id.btnStart);
        this.btnStop = (ImageView) findViewById(R.id.btnStop);
        
        this.btnSart.setOnClickListener(this);
        this.btnStop.setOnClickListener(this);
        
        //Running flag initialization
        this.running = false;
        
        this.updateTimerThread = new UpdateTimerThread();
        
        
        handler = new Handler(){
        	
        	public void handleMessage(Message msg){
        		String text = "" + stepCountingThread.mListener.steps;
        		textStepCount.setText(text);
        	}
        };
        
        this.stepCountingThread = new StepCountingThread(this, handler);
        
        Thread thread = new Thread(stepCountingThread);
        thread.start();
        
        
        
        this.updateTimerThread.timerValue = (TextView) findViewById(R.id.textViewClock);
        
        
        Intent i = getIntent();
        
        this.training = new Workout(0,i.getStringExtra("workoutName"),i.getStringExtra("workoutJson"));
        
    }



    /**
     * AnimationListener method implementation.
     */
    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * AnimationListener method implementation.
     */
    @Override
    public void onAnimationEnd(Animation animation) {
        
    }
    
    
    /**
     * AnimationListener method implementation.
     */
    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    /**
     * OnClickListener method implementation.
     */
    @Override
    public void onClick(View v){
        switch (v.getId()){
        	
        	//When the start button is pressed the animation shows up.
            case R.id.btnStart:
            	
            	if(this.running){
            		
            		this.shoeImage1.clearAnimation();
                	this.shoeImage2.clearAnimation();
                	
                	Drawable playButtonIcon = getResources().getDrawable(R.drawable.play_button);
                	this.btnSart.setImageDrawable(playButtonIcon);
                	
                	stepCountingThread.mListener.running = false;
                	this.updateTimerThread.timerPause();
                	
                	
            	}else{
            		this.shoeImage1.startAnimation(zoom_in);
            		this.shoeImage2.startAnimation(zoom_out);
            		
            		Drawable pauseButtonIcon = getResources().getDrawable(R.drawable.pause_button);
                	this.btnSart.setImageDrawable(pauseButtonIcon);
                	
                	stepCountingThread.mListener.running = true;
                	this.updateTimerThread.timerResume();

            	}
            	
            	
            	this.running = ! this.running;
            	
            	
            break;

            
            
            
            //When the stop button is pressed the animation is interrupted
            //and the TrainingEndActivity is launched.
            case R.id.btnStop:

            	this.shoeImage1.clearAnimation();
            	this.shoeImage2.clearAnimation();
            	
            	Intent i = new Intent(this, WorkoutEndActivity.class);
            	
    	    	i.putExtra("workoutName", this.training.getTrainingName());
    	    	i.putExtra("workoutJson", this.training.getTrainingJSONString());
            	
				startActivity(i);
				
				this.finish();
            break;
                

        }
    };
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
	 * Creates the options menu.
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    /**
	 * Set the click listeners for the menu items.
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    
    
	
}
