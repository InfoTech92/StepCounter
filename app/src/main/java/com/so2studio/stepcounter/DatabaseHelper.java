package com.so2studio.stepcounter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 *	This class implements some basic methods 
 *	for database creation and upgrade. 
 *	It also contains some fields such as database name and version.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    
	/**
	 * Database name field.
	 */
	private static final String DATABASE_NAME = "stepCounterDatabase.db";
	/**
	 * Database version field.
	 * Actual Version number: 1.
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * Create profiles Table SQL statement.
	 * 
	 * Fields:
	 * - _id: profile ID. There will be only one, but we provide this field too;
	 * - name;
	 * - surname;
	 * - gender;
	 * - height;
	 * - weight;
	 * - step_len_cm: Step length, in centimeters;
	 * - dist_tot_km: Total walked distance, in kilometers;
	 * - steps_tot: Total number of steps;
	 * - time_tot_sec: Total amount of time spent on training, in seconds;
	 * - kcal_tot: Total amount of kcal loss;
	 * - x: last x meters for mean speed and step/min computation.
	 */
	private static final String CREATE_PROFILES_TABLE = 
			"create table profiles ("+
				"_id integer primary key autoincrement, " +
				" name text not null," + 
				" surname text not null,"+
				" gender text not null, " + 
				" height integer not null," + 
				" weight integer not null," +
				" step_len_cm integer not null," +
				" dist_tot_km integer not null," +
				" steps_tot integer not null," +
				" time_tot_sec integer not null," +
				" kcal_tot integer not null," +
				" x integer not null" +
			" );";
	
	/**
	 * Create workouts Table SQL statement.
	 * 
	 * Fields:
	 * - _id: training ID;
	 * - name: training name;
	 * - json: json object. It contains training data.
	 */
	private static final String CREATE_WORKOUTS_TABLE = 
			"create table workouts ("+
				"_id integer primary key autoincrement, " +
				" name text not null," + 
				" json text not null"+
			" );";
	
	
	
	/**
	 * Database helper constructor.
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	/**
	 * This methods executes the create profiles table and the create training table queries.
	 * 
	 * @param database
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_PROFILES_TABLE);
		database.execSQL(CREATE_WORKOUTS_TABLE);
		
	}

	
	/**
	 * This method is invoked on database upgrade.
	 * It drops the old database and creates an empty new one.
	 * 
	 * @param database
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {
			database.execSQL("DROP TABLE IF EXISTS " + DatabaseAdapter.PROFILES_TABLE + "; DROP TABLE IF EXISTS " + DatabaseAdapter.WORKOUTS_TABLE + ";");
			onCreate( database );
	}
}