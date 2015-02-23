package com.so2studio.stepcounter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.mainactivity.R;


/**
 * This activity contains a static method that creates the workout elimination pop up:
 * the pop up is created when the user touches on the "New Training" button 
 * and asks the user for the training name and the desired mean speed in 
 * steps/minutes.
 * 
 * This class has no constructors.
 * 
 */
public class WorkoutEliminationConfirmPopup{
	
	/**
	 * This object represents the layout of the pop up.
	 */
	private static RelativeLayout layout;
	
	/**
	 * This object is used for pop up creation.
	 */
	private static LayoutInflater layoutInflater;
	
	/**
	 * This object is a View that contains the pop up.
	 */
	private static View popupView;
	
	/**
	 * This object represents the activity in which the pop up
	 * must be created.
	 */
	private static Activity activ;
	
	/**
	 * This field represents the new training pop up that is shown when
	 * a new training is started by touching on the appropriate button.
	 */
	private static PopupWindow popupWindow = null;
	
	
	/**
	 * Position from which the element shall be removed.
	 */
	public static int position;
	
	/**
	 * Workouts list.
	 */
	public static ArrayList<Workout> data;
	
	
	
	public static WorkoutListAdapter workoutListAdapter;
	
	/**
	 * This method creates a new pop up for the new training.
	 * 
	 */
	public static void createWorkoutEliminationConfirmPopup(Activity activity, ImageView imageView, int pos, final ArrayList<Workout> dat, WorkoutListAdapter wla){
		
		//If a pop up already existed, it is here deleted: 
		//only a pop up can exist at any time.
		dismissPopup();
		
		//The parameter activity is transferred as a local field.
		activ = activity;
		
		/*
		 * Pop up creation code.
		 */
		
		//The pop up layout is fetched. This resource is found within the
		//new_training_popup.xml file
		layout = (RelativeLayout) activity.findViewById(R.id.popup_workout_elimination_confirm);
		//The LayoutInflater service is created using the getSystemService method.
		layoutInflater = (LayoutInflater)activity.getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);  
		//The pop up view is created by the layoutInflater. 
		popupView = layoutInflater.inflate(R.layout.workout_elimination_confirm_popup, layout);
		
		//The pop up window is created from the pop up view.
		popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
		
		//The window is declared to be focusable (this is necessary for the input).
		popupWindow.setFocusable(true);
		//The pop up window is finally shown.
		popupWindow.showAsDropDown(imageView);
		
		
		position = pos;
	
	    data = dat;
	    
	    workoutListAdapter = wla;
	    
	    /*
	     * On click listener methods.
	     */
	    
	    
	    
	    Button workoutEliminationConfirmPopupCancelButton = (Button)popupView.findViewById(R.id.workoutEliminationConfirmPopupCancelButton);
	    workoutEliminationConfirmPopupCancelButton.setOnClickListener(new Button.OnClickListener(){
		
			@Override
			public void onClick(View v) {
				
				dismissPopup();
				
		    }
			
	    });
		
		
		Button workoutEliminationConfirmPopupConfirmButton = (Button)popupView.findViewById(R.id.workoutEliminationConfirmPopupConfirmButton);
	    workoutEliminationConfirmPopupConfirmButton.setOnClickListener(new Button.OnClickListener(){
		
			@Override
			public void onClick(View v) {
				
				Context context = activ.getApplicationContext();
				
				
				DatabaseAdapter dbAdapter = new DatabaseAdapter(context);
				dbAdapter.open();
				
				dbAdapter.deleteWorkout(data.get(position).getTrainingId());
				data.remove(position);
				workoutListAdapter.notifyDataSetChanged();
				
				dbAdapter.close();
				
				dismissPopup();
		    }
			
	    });
		
	    
	
    
    
	}
    
    
	
	
	
	
	private static void dismissPopup(){
		if(popupWindow != null){
			popupWindow.dismiss();
    	}
	}
	
}
