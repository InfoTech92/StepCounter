package com.so2studio.stepcounter;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.mainactivity.R;

/**
 * This activity is launched when the training is terminated by the user.
 *
 */
public class WorkoutEndActivity extends ActionBarActivity implements OnClickListener{
	
	/**
	 * This object contains data which are relative to the training 
	 * that has just ended.
	 */
	public Workout workout;
	
	/**
	 * OnCreate method implementation.
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_workout);
        
        //Buttons initialization...
        Button btnShareEndTraining = (Button) findViewById(R.id.btnShareEndTraining);
        Button btnGraphicEndTraining = (Button) findViewById(R.id.btnGraphicEndTraining);
        Button btnExitNoSaveEndTraining = (Button) findViewById(R.id.btnExitNoSaveEndTraining);
        Button btnExitSaveEndTraining = (Button) findViewById(R.id.btnExitSaveEndTraining);
        
        btnShareEndTraining.setOnClickListener(this);
        btnGraphicEndTraining.setOnClickListener(this);
        btnExitNoSaveEndTraining.setOnClickListener(this);
        btnExitSaveEndTraining.setOnClickListener(this);
        
        Intent i = getIntent();
        
        this.workout = new Workout(0,i.getStringExtra("workoutName"),i.getStringExtra("workoutJson"));
        
        
	}

	/**
	 * OnclickListener method implementation.
	 */
	@Override
	public void onClick(View v) {
		
		Intent i = null;
		
		switch(v.getId()){
		
			/*
			 * If the share button is pressed, the training can be sent via email.
			 */
			case R.id.btnShareEndTraining:
				
				String email = "ale.demurtas92@gmail.com";
				  
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
				intent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
				intent.putExtra(Intent.EXTRA_TEXT, "body text");
				 
				File root = Environment.getExternalStorageDirectory();
				  
				File file = new File(root, "FileProva.stc");
              	  
				 try{
					  FileOutputStream out = new FileOutputStream(file);
				      out.write("buon li".getBytes());
				      out.close();
				  }catch(Exception e){
					  
				  }
				  
				  if (!file.exists()) {
				      Toast.makeText(this.getApplicationContext(), "Attachment Error exist", Toast.LENGTH_SHORT).show();
				      this.finish();
				      return;
				  }
              	  
              	  if(!file.canRead()){
              		  Toast.makeText(this.getApplicationContext(), "Attachment Error read", Toast.LENGTH_SHORT).show();
              		this.finish();
              	      return;
          		  }
              	  
              	  Uri uri = Uri.parse("file://" + file);
              	  intent.putExtra(Intent.EXTRA_STREAM, uri);
              	  this.startActivity(Intent.createChooser(intent, "Send email..."));
              	  
			break;
			
			/*
			 * If the statistics button is pressed, the training statistics are shown in 
			 * the appropriate activity.
			 */
			case R.id.btnGraphicEndTraining:
				i = new Intent(this, WorkoutStatisticsActivity.class);
				startActivity(i); 
			break;
			
			/*
			 * If the exit without save button is pressed, the user goes back to the main 
			 * menu activity and no data is stored on the db.
			 */
			case R.id.btnExitNoSaveEndTraining:
				this.finish();
			break;
			
			/*
			 * If the exit AND save button is pressed, the user goes back to the main menu activity,
			 * training statistics are saved on the DB and user general statistics are updated.
			 */
			case R.id.btnExitSaveEndTraining:
				DatabaseAdapter dbAdapter = new DatabaseAdapter(this);
				dbAdapter.open();
				
				dbAdapter.createNewWorkout(this.workout.getTrainingName(), this.workout.getTrainingJSONString());
				
				dbAdapter.close();
				
				this.finish();
			break;
			
		}
		
		
	}
	
}
