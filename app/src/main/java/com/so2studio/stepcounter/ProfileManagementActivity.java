package com.so2studio.stepcounter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mainactivity.R;


/**
 * This is the profile management activity which is used to handle personal data such as
 * name, surname and the other fields shown in the activity itself.
 *
 */
public class ProfileManagementActivity extends ActionBarActivity implements OnClickListener{
	
	/**
	 * A DB adapter is useful to handle the database and search for user data.
	 */
	private DatabaseAdapter dbAdapter;
    ImageView myImage;
	
	/**
	 * Creates the activity.
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        //Image initialization...
        myImage = (ImageView) findViewById(R.id.circularImage);

        //Implementation of startActivityForResult
        myImage.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleziona Immagine"), 1);



            }
        } );
        
        //Buttons initialization...
        Button btnProfileManagementActivityStatistics = (Button) findViewById(R.id.btnProfileManagementActivityStatistics);
        Button btnProfileManagementActivitySave = (Button) findViewById(R.id.btnProfileManagementActivitySave);
        
        btnProfileManagementActivityStatistics.setOnClickListener(this);
        btnProfileManagementActivitySave.setOnClickListener(this);
        
        
	}

	/**
	 * OnClickListenere interface implementation.
	 */
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
			
			/*
			 * This button starts an activity that shows user profile general statistics.
			 */
			case R.id.btnProfileManagementActivityStatistics:
				
				Intent i = new Intent(this,WorkoutStatisticsActivity.class);
				startActivity(i);
				
			break;
			
			/*
			 * When the user touches on the save button, data is read from the edit text and, after
			 * input control, it is written on the DB.
			 */
			case R.id.btnProfileManagementActivitySave:
				
				EditText profileManagementActivityNameEditText = (EditText) findViewById(R.id.profileManagementActivityNameEditText);
				EditText profileManagementActivitySurnameEditText = (EditText) findViewById(R.id.profileManagementActivitySurnameEditText);
				EditText profileManagementActivityHeightEditText = (EditText) findViewById(R.id.profileManagementActivityHeightEditText);
				EditText profileManagementActivityWeightEditText = (EditText) findViewById(R.id.profileManagementActivityWeightEditText);
				EditText profileManagementActivityStepLengthEditText = (EditText) findViewById(R.id.profileManagementActivityStepLengthEditText);
				EditText profileManagementActivityGenderEditText = (EditText) findViewById(R.id.profileManagementActivityGenderEditText);
				EditText profileManagementActivityXEditText = (EditText) findViewById(R.id.profileManagementActivityXEditText);
				
				String name = profileManagementActivityNameEditText.getText().toString();
				String surname = profileManagementActivitySurnameEditText.getText().toString();
				String height = profileManagementActivityHeightEditText.getText().toString();
				String weight = profileManagementActivityWeightEditText.getText().toString();
				String stepLen = profileManagementActivityStepLengthEditText.getText().toString();
				String gender = profileManagementActivityGenderEditText.getText().toString();
				String x = profileManagementActivityXEditText.getText().toString();
				
				if(name != null && surname != null && height != null && 
						weight != null && stepLen != null && gender != null && x != null ){
					
					this.dbAdapter = new DatabaseAdapter(this.getApplicationContext());
					this.dbAdapter.open();
					this.dbAdapter.createNewProfile(name, surname, gender, height, weight, stepLen, "0", "0", "0", "0", x);
					this.dbAdapter.close();
					Toast.makeText(this , "Dati Salvati con successo!",
							Toast.LENGTH_SHORT).show();
					
					this.finish();
					
				}else{
					Toast.makeText(this , "Errore! Compilare tutti i campi.",
							Toast.LENGTH_SHORT).show();
				}
				
			break;
			
		}
		
	}

    /**
     * onActivityResult implementation. This code is used when is called startActivityForResult
     */
    public void onActivityResult(int reqCode, int resCode, Intent data){

        if (resCode == RESULT_OK) {
            if (reqCode == 1)
                myImage.setImageURI(data.getData());

        }

    }
	
	
	
	
	
	

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
