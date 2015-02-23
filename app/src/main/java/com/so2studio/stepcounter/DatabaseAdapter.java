package com.so2studio.stepcounter;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
 

/**
 * This class contains the main methods and fields that are used by
 * the application in order to manipulate the database.
 *  
 */
public class DatabaseAdapter {
	
	/**
	 * Application context.
	 */
	private Context context;
	
	/**
	 * The database which is actually handled by this object.
	 */
	private SQLiteDatabase database;
	
	/**
	 * A database helper object. It contains some basic informations
	 * about the database which is actually handled by this object.
	 */
	private DatabaseHelper dbHelper;
 
	/**
	 * Profiles table name.
	 */
	public static final String PROFILES_TABLE      = "profiles";
	
	/**
	 * Workouts table name.
	 */
	public static final String WORKOUTS_TABLE      = "workouts";
	
	
	
	/**
	 * Profiles table field: _id.
	 */
	public static final String PROFILES_TABLE_KEY_PROFILEID = "_id";
	/**
	 * Profiles table field: name.
	 */
	public static final String PROFILES_TABLE_KEY_NAME = "name";
	/**
	 * Profiles table field: surname.
	 */
	public static final String PROFILES_TABLE_KEY_SURNAME = "surname";
	/**
	 * Profiles table field: gender.
	 */
	public static final String PROFILES_TABLE_KEY_GENDER = "gender";
	/**
	 * Profiles table field: height.
	 */
	public static final String PROFILES_TABLE_KEY_HEIGHT = "height";
	/**
	 * Profiles table field: weight.
	 */
	public static final String PROFILES_TABLE_KEY_WEIGHT = "weight";
	/**
	 * Profiles table field: step_len.
	 */
	public static final String PROFILES_TABLE_KEY_STEP_LEN = "step_len_cm";
	/**
	 * Profiles table field: dist_tot_km.
	 */
	public static final String PROFILES_TABLE_KEY_DIST_TOT_KM = "dist_tot_km";
	/**
	 * Profiles table field: steps_tot.
	 */
	public static final String PROFILES_TABLE_KEY_STEPS_TOT = "steps_tot";
	/**
	 * Profiles table field: time_tot_sec.
	 */
	public static final String PROFILES_TABLE_KEY_TIME_TOT_SEC = "time_tot_sec";
	/**
	 * Profiles table field: kcal_tot.
	 */
	public static final String PROFILES_TABLE_KEY_KCAL_TOT = "kcal_tot";
	/**
	 * Profiles table field: x.
	 */
	public static final String PROFILES_TABLE_KEY_X = "x";
	
	
	
	
	
	/**
	 * Workouts table field: _id.
	 */
	public static final String WORKOUTS_TABLE_KEY_ID = "_id";
	/**
	 * Workouts table field: name.
	 */
	public static final String WORKOUTS_TABLE_KEY_NAME = "name";
	/**
	 * Workouts table field: json.
	 */
	public static final String WORKOUTS_TABLE_KEY_JSON = "json";
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Constructor.
	 * @param context
	 */
	public DatabaseAdapter(Context context) {
		this.context = context;
	}
	
	/**
	 * This method opens a new writable database
	 * that will be manipulated by this object.
	 * 
	 * @return this object
	 * @throws android.database.SQLException
	 */
	public DatabaseAdapter open() throws SQLException {
		this.dbHelper = new DatabaseHelper(context);
		this.database = this.dbHelper.getWritableDatabase();
		return this;
	}
	
	
	/**
	 * This method closes the actually opened database.
	 */
	public void close() {
		dbHelper.close();
	}
	
	
	
	/**
	 * 
	 */
	public void createDatabase(){
		this.dbHelper.onUpgrade(database, 1, 1);
	}
	
	
	/**
	 * This method creates a ContentValues object that represents a database row from the values
	 * associated at it's fields at the namesake parameters. 
	 * 
	 * @param name
	 * @param surname
	 * @param gender
	 * @param height
	 * @param weight
	 * @param stepLen
	 * @param distTotKm
	 * @param stepsTot
	 * @param timeTotSec
	 * @param kcalTot
	 * @param x
	 * @return
	 */
	private ContentValues createContentValuesProfilesTable(
			String name, String surname, String gender, 
			String height, String weight, String stepLen, 
			String distTotKm, String stepsTot, String timeTotSec,
			String kcalTot, String x) {
		
		ContentValues values = new ContentValues();
		
		values.put( PROFILES_TABLE_KEY_NAME, name );
		values.put( PROFILES_TABLE_KEY_SURNAME, surname );
		values.put( PROFILES_TABLE_KEY_GENDER, gender );
		values.put( PROFILES_TABLE_KEY_HEIGHT, height );
		values.put( PROFILES_TABLE_KEY_WEIGHT, weight );
		values.put( PROFILES_TABLE_KEY_STEP_LEN, stepLen );
		values.put( PROFILES_TABLE_KEY_DIST_TOT_KM, distTotKm );
		values.put( PROFILES_TABLE_KEY_STEPS_TOT, stepsTot );
		values.put( PROFILES_TABLE_KEY_TIME_TOT_SEC, timeTotSec );
		values.put( PROFILES_TABLE_KEY_KCAL_TOT, kcalTot );
		values.put( PROFILES_TABLE_KEY_X, x );
		
		return values;
	}
        
	
	
	/**
	 * 
	 * This method creates a new profiles table row from the given parameters.
	 * 
	 * @param name
	 * @param surname
	 * @param gender
	 * @param height
	 * @param weight
	 * @param stepLen
	 * @param distTotKm
	 * @param stepsTot
	 * @param timeTotSec
	 * @param kcalTot
	 * @param x
	 * @return
	 */
	public long createNewProfile(
			String name, String surname, String gender, 
			String height, String weight, String stepLen, 
			String distTotKm, String stepsTot, String timeTotSec,
			String kcalTot, String x) {
		
		ContentValues initialValues = createContentValuesProfilesTable(name, surname, gender, height, weight, stepLen, distTotKm,stepsTot,timeTotSec,kcalTot,x);
		
		return database.insert(PROFILES_TABLE, null, initialValues);
	}
 
	/**
	 * This method updates a profile in the profiles table.
	 * 
	 * @param _id
	 * @param name
	 * @param surname
	 * @param gender
	 * @param height
	 * @param weight
	 * @param stepLen
	 * @param distTotKm
	 * @param stepsTot
	 * @param timeTotSec
	 * @param kcalTot
	 * @param x
	 * @return
	 */
	public boolean updateContact(long _id,
			String name, String surname, String gender, 
			String height, String weight, String stepLen, 
			String distTotKm, String stepsTot, String timeTotSec,
			String kcalTot, String x) {
		ContentValues updateValues = createContentValuesProfilesTable(name, surname, gender, height, weight, stepLen, distTotKm,stepsTot,timeTotSec,kcalTot,x);
		return database.update(PROFILES_TABLE, updateValues, PROFILES_TABLE_KEY_PROFILEID + "=" + _id, null) > 0;
	}
                 
	/**
	 * This method deletes a profile in the profiles table.
	 *     
	 * @param id
	 * @return
	 */
	public boolean deleteProfile(long id) {
		return database.delete(PROFILES_TABLE, PROFILES_TABLE_KEY_PROFILEID + "=" + id, null) > 0;
	}
 
	/**
	 * This method fetches all the profiles contained in the profiles table in a Cursor object.
	 * @return
	 */
	public Cursor fetchAllProfiles() {
		return database.query(PROFILES_TABLE, new String[] { PROFILES_TABLE_KEY_PROFILEID, PROFILES_TABLE_KEY_NAME, PROFILES_TABLE_KEY_SURNAME, 
															PROFILES_TABLE_KEY_GENDER, PROFILES_TABLE_KEY_HEIGHT, PROFILES_TABLE_KEY_WEIGHT, 
															PROFILES_TABLE_KEY_STEP_LEN, PROFILES_TABLE_KEY_DIST_TOT_KM, PROFILES_TABLE_KEY_STEPS_TOT, 
															PROFILES_TABLE_KEY_TIME_TOT_SEC, PROFILES_TABLE_KEY_KCAL_TOT, PROFILES_TABLE_KEY_X}, null, null, null, null,null);
	}
   
	
	
	
	
	/**
	 * This method fetches all the contacts from the profiles table 
	 * that contain the given substring "filter".
	 * 
	 * @param filter: the substring to seek in the profile name.
	 * @return
	 */
	public Cursor fetchProfilesByFilter(String filter) {
        return database.query(true, PROFILES_TABLE, new String[]{ PROFILES_TABLE_KEY_PROFILEID, PROFILES_TABLE_KEY_NAME, PROFILES_TABLE_KEY_SURNAME,
															PROFILES_TABLE_KEY_GENDER, PROFILES_TABLE_KEY_HEIGHT, PROFILES_TABLE_KEY_WEIGHT, 
															PROFILES_TABLE_KEY_STEP_LEN, PROFILES_TABLE_KEY_DIST_TOT_KM, PROFILES_TABLE_KEY_STEPS_TOT, 
															PROFILES_TABLE_KEY_TIME_TOT_SEC, PROFILES_TABLE_KEY_KCAL_TOT, PROFILES_TABLE_KEY_X},
															
															PROFILES_TABLE_KEY_NAME + " like '%"+ filter + "%'", null, null, null, null, null);
         

	}
	
	
	
	
	/**
	 * This method fetches all the workouts contained in the workouts table in a Cursor object.
	 * @return
	 */
	public Cursor fetchAllWorkouts() {
		return database.query(WORKOUTS_TABLE, new String[] { WORKOUTS_TABLE_KEY_ID, WORKOUTS_TABLE_KEY_NAME, WORKOUTS_TABLE_KEY_JSON}, null, null, null, null,null);
	}
	
	

	
	
	
	/**
	 * This method creates a new ContentValues object 
	 * 
	 * @param name
	 * @param json
	 * @return
	 */
	private ContentValues createContentValuesTrainingsTable(
			String name, String json) {
		
		ContentValues values = new ContentValues();
		
		values.put( WORKOUTS_TABLE_KEY_NAME , name );
		values.put( WORKOUTS_TABLE_KEY_JSON , json );
		
		return values;
	}
	
	
	
	
	/**
	 * 
	 * This method creates a new workouts table row from the given parameters.
	 * 
	 * @param name
	 * @param json
	 * @return
	 */
	public long createNewWorkout( String name , String json ) {
		
		ContentValues initialValues = createContentValuesTrainingsTable(name, json);
		return database.insert(WORKOUTS_TABLE, null, initialValues);
		
	}
	
	
	/**
	 * 
	 * This method updates a  workouts table row from the given parameters.
	 * 
	 * @param name
	 * @param json
	 * @return
	 */
	public boolean updateTraining(int _id , String name , String json) {
		
		ContentValues updateValues = createContentValuesTrainingsTable(name, json);
		return database.update(WORKOUTS_TABLE, updateValues, WORKOUTS_TABLE_KEY_ID + "=" + _id, null) > 0;
	}
	
	
	/**
	 * This method deletes the workout specified by the give id.
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteWorkout( long id ) {
	    return database.delete( WORKOUTS_TABLE , WORKOUTS_TABLE_KEY_ID + "=" + id , null ) > 0;
	}
	
	
	
	/**
	 * This method drops the database tables and creates new.
	 */
	public void dropDatabase(){
		this.database.execSQL( "DROP TABLE IF EXISTS " + PROFILES_TABLE + ";" );
		this.database.execSQL( "DROP TABLE IF EXISTS " + WORKOUTS_TABLE + ";" );
		this.dbHelper.onCreate( database );
	}
	
	
	
	
	
  
}