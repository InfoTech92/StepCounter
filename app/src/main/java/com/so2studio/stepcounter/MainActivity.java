package com.so2studio.stepcounter;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.mainactivity.R;


/**
 * This class represents the application's main activity.
 * It is the first activity shown when the application is launched.
 *
 *
 */

//TEST
public class MainActivity extends ActionBarActivity implements OnClickListener{

    /**
     * This field is a reference to the object itself.
     */
    private MainActivity self = null;




    /**
     * A boolean field which indicates if a user has been found in the database.
     */
    private boolean userFound;


    /**
     * When the activity is created, this method is launched.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Buttons initialization
        Button btnNewTraining = (Button) findViewById(R.id.btnNewTraining);
        Button btnViewTrainings = (Button)findViewById(R.id.btnViewTrainings);
        Button btnProfileManagement = (Button)findViewById(R.id.btnProfileManagement);

        btnNewTraining.setOnClickListener(this);
        btnViewTrainings.setOnClickListener(this);
        btnProfileManagement.setOnClickListener(this);


        //Self variable assigned
        self = this;


        //A database adapter: this is useful to know the user list in the database.
        DatabaseAdapter dbAdapter = new DatabaseAdapter(this);
        dbAdapter.open();

        Cursor profiles = dbAdapter.fetchAllProfiles();

        this.userFound = profiles.moveToFirst();

        dbAdapter.close();

        long threadId = Thread.currentThread().getId();
        Toast.makeText(this.getApplicationContext(), "" + threadId, Toast.LENGTH_LONG).show();

    }


    /**
     * OnClickListener interface method.
     */
    public void onClick(View v) {

        Intent i = null;

        switch(v.getId()){
		
			/*
			 * When the new training button is touched, a pop up is created (if it doesn't already exist)
			 * that is necessary to initialize training basic data (name and step length).
			 * If no user is registered in the DB, the ErrorActivity is launched.
			 */
            case R.id.btnNewTraining:



                if(this.userFound){

                    NewTrainingPopup.createNewTrainingPopup(this);

                }else{

                    i = new Intent(self, ErrorActivity.class);
                    startActivity(i);
                }

                break;


            //If the "View Trainings" button is touched, ViewTrainingActivity (training list) is started.
            case R.id.btnViewTrainings:
                i = new Intent(this, ViewWorkoutsActivity.class);
                startActivity(i);
                break;


            //When the profile button is touched, ProfileManagementActivity is started.
            case R.id.btnProfileManagement:
                i = new Intent(this, ProfileManagementActivity.class);
                startActivity(i);
                break;

        }



    }





    /**
     * Creates the options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
