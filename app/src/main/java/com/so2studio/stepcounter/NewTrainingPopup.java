package com.so2studio.stepcounter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mainactivity.R;


/**
 * This activity contains a static method that creates the new training pop up:
 * the pop up is created when the user touches on the "New Training" button 
 * and asks the user for the training name and the desired mean speed in 
 * steps/minutes.
 * 
 * This class has no constructors.
 * 
 */
public class NewTrainingPopup{
	
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
	 * This method creates a new pop up for the new training.
	 * 
	 */
	public static void createNewTrainingPopup(Activity activity){
		
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
		layout = (RelativeLayout) activity.findViewById(R.id.popup_new_training);
		//The LayoutInflater service is created using the getSystemService method.
		layoutInflater = (LayoutInflater)activity.getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);  
		//The pop up view is created by the layoutInflater. 
		popupView = layoutInflater.inflate(R.layout.new_workout_popup, layout);
		
		//The pop up window is created from the pop up view.
		popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
		
		//The window is declared to be focusable (this is necessary for the input).
		popupWindow.setFocusable(true);
		//The pop up window is finally shown.
		popupWindow.showAsDropDown(activity.findViewById(R.id.btnNewTraining) );
	
	
	    
	    /*
	     * On click listener methods.
	     */
	    
	    
	    //When the start button is touched, TrainingActivity is started.
	    Button btnStartTrainingNewTrainingPopup = (Button)popupView.findViewById(R.id.btnStartTrainingNewTrainingPopup);
	    btnStartTrainingNewTrainingPopup.setOnClickListener(new Button.OnClickListener(){
		    
	    	@Override
		    public void onClick(View v) {
	    		
	    		//Input fetch
	    		EditText newTrainingPopupEditText = (EditText) popupView.findViewById(R.id.newTrainingPopupEditText);
	    		EditText newTrainingPopupStepsPerMinuteEditText = (EditText) popupView.findViewById(R.id.newTrainingPopupStepsPerMinuteEditText);
	    		
	    		String name = newTrainingPopupEditText.getText().toString();
	    		String exp_mean_speed = newTrainingPopupStepsPerMinuteEditText.getText().toString();
	    		
	    		//This flag is later used to tell if a JSONException has been thrown or not.
	    		boolean jsonFlag = true;
	    		
	    		
	    		//This JSON string contains all the useful data about the training.
	    		String json = "{" +	"\"total_distance\":\"0\"," + 
								
									
									"\"expected_mean_speed\":\""+exp_mean_speed+"\"," +
									
									"\"mean_speeed_km_h\":\"0\"," +			
									"\"mean_speed_step_min\":\"0\"," + 			
									"\"mean_foot_elevation_cm\":\"0\"," +	
									
									"\"mean_speeed_km_h_last_x\":\"0\"," +		
									"\"mean_speed_step_min_last_x\":\"0\"," +
									"\"mean_foot_elevation_cm\":\"0\"," +
									
									"\"time_sampling_frequence\":\"40\"," +
								
									"\"time_samplings\":[\"0\"]," +
									"\"speed_km_h_samplings\":[\"0\"]," + 
									"\"speed_step_min_samplings\":[\"0\"]," +
									"\"foot_elevation_cm_samplings\":[\"0\"],"+
									"\"distance_variation_samplings\":[\"0\"]" + 
								"}" ;
	    						
	    		//The new JSONObject is created from the given string-
	    		try{
	    			new JSONObject(json);
	    		}catch (JSONException e){
	    			jsonFlag = false;
	    		}
	    		
	    		//This String will contain the error message if something later
	    		//should go wrong.
	    		String errorMsg = null;
	    		
	    		//Pop up input is controlled.
	    		//If the name and speed strings are not empty and the JSONObject
	    		//creation hasn't thrown any exceptions, the WorkoutActivity
	    		//is created.
	    		if(! name.equals("")){
	    			
	    			if( ! exp_mean_speed.equals("")){
	    				
		    				if(jsonFlag){
		    					
		    					//If all the previous checks are passed, the new activity is created and...
				    	    	Intent i = new Intent(activ, WorkoutActivity.class);
				    	    	
				    	    	i.putExtra("workoutName", name);
				    	    	i.putExtra("workoutJson", json);
				    	    	
				    	    	activ.startActivity(i);
				    	    	
				    	    	//..the pop up is dismissed.
				    	    	popupWindow.dismiss();
				    	    	
		    				}else{
		    					errorMsg = activ.getString(R.string.new_training_popup_json_creation_error);
		    	    			
		    	    			Toast.makeText(activ , errorMsg,
		    							Toast.LENGTH_LONG).show();
		    				}
	    				
	    			}else{
	    				errorMsg = activ.getString(R.string.new_training_popup_mean_speed_error);
		    			
		    			Toast.makeText(activ , errorMsg,
								Toast.LENGTH_LONG).show();
	    			}
	    			
	    		}else{
	    			
	    			errorMsg = activ.getString(R.string.new_training_popup_training_name_error);
	    			
	    			Toast.makeText(activ , errorMsg,
							Toast.LENGTH_LONG).show();
	    			
	    		}
				
	    		
	    		
				
				
		    }
	    	
	    	
	    });
	    
	    
	    
	    
	    //If the cancel button is touched, the popup is deleted.
	    Button btnCancelNewTrainingPopup = (Button)popupView.findViewById(R.id.btnCancelNewTrainingPopup);
	    btnCancelNewTrainingPopup.setOnClickListener(new Button.OnClickListener(){
		    @Override
		    public void onClick(View v) {
		    	popupWindow.dismiss();
		    }
	    });
	
    
    
	}
    
    
	
	private static void dismissPopup(){
		if(popupWindow != null){
			popupWindow.dismiss();
    	}
	}
	
}
