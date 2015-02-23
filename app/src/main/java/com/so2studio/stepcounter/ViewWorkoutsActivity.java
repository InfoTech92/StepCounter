package com.so2studio.stepcounter;

import java.util.ArrayList;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.mainactivity.R;


/**
 * This class represents the training list activity.
 * 
 */
public class ViewWorkoutsActivity extends ActionBarActivity {
	
	/**
	 * The widget in which the training list is shown.
	 */
	public ListView trainingsList;
	/**
	 * An object that is necessary for the list view creation.
	 * It contains data about the trainings.
	 */
	public WorkoutListAdapter trainingListAdapter;
	/**
	 * Trainings ArrayList.
	 */
	public ArrayList<Workout> trainingArray;

	/**
	 * OnCreate method.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_workouts);
		
		boolean dataEnd = true;
		
		Workout temp = null;
		int tempId;
		String tempName;
		String tempJson;
		
		/**
		 * This section extracts the trainings from the database and loads it
		 * in the trainingArray variable.
		 */
		DatabaseAdapter dbAdapter = new DatabaseAdapter(this);
		dbAdapter.open();
		
		this.trainingArray = new ArrayList<Workout>();
		
		Cursor trainingsCursor = dbAdapter.fetchAllWorkouts();
		dataEnd = trainingsCursor.moveToFirst();
		
		while(dataEnd){
			
			tempId 		= Integer.parseInt(trainingsCursor.getString(0));
			tempName 	= trainingsCursor.getString(1);
			tempJson 	= trainingsCursor.getString(2);
			
			temp = new Workout(tempId,tempName,tempJson);
			
			this.trainingArray.add(temp);
			
			dataEnd = trainingsCursor.moveToNext();
			
		}
		
		
		/**
		* Set items into adapter.
		*/
		this.trainingListAdapter = new WorkoutListAdapter(this, ViewWorkoutsActivity.this, R.layout.training_list_row, this.trainingArray);
		this.trainingsList = (ListView) findViewById(R.id.listView);
		this.trainingsList.setItemsCanFocus(false);
		this.trainingsList.setAdapter(this.trainingListAdapter);
		
		
		
		/**
		* Set on item click listener. 
		* This method is invoked when a list item is clicked (touched).
		*/
		trainingsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
				final int position, long id) {
			}
		});

	 }

}


