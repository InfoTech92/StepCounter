package com.so2studio.stepcounter;

import org.json.JSONObject;

import org.json.JSONException;


/**
 * This class contains the fields and the getter/setters methods
 * which are necessary for a training representation,
 * according with the trainings table in the DB.
 * 
 */
public class Workout {
	
	
	/**
	 * Training id.
	 */
	private int _id;
	/**
	 * Training name.
	 */
	private String name;
	/**
	 * Training JSON object: it contains the main training statistics and data.
	 */
	private JSONObject json;
	
	
	/**
	 * Constructor.
	 * @param _id
	 * @param name
	 * @param json
	 */
	public Workout(int _id, String name, String json){
		this._id = _id;
		this.name = name;
        try {
            this.json = new JSONObject(json);
        }catch(JSONException e){
            e.printStackTrace();
        }

    }
	
	/**
	 * Constructor.
	 * @param _id
	 * @param name
	 * @param json
	 */
	public Workout(int _id, String name, JSONObject json){
		this._id = _id;
		this.name = name;
		this.json = json;
	}
	
	/**
	 * Returns the training id.
	 * @return
	 */
	public int getTrainingId(){
		return this._id;
	}
	/**
	 * Returns the training name.
	 * @return
	 */
	public String getTrainingName(){
		return this.name;
	}
	
	/**
	 * Returns the training JSON as a String.
	 * @return
	 */
	public String getTrainingJSONString(){
		return this.json.toString();
	}
	
	/**
	 * Returns the training JSON as a JSONObject.
	 * @return
	 */
	public JSONObject getTrainingJSON(){
		return this.json;
	}
	
	
	
	
	
	
	/**
	 * Sets the training id.
	 * @param _id
	 */
	public void setTrainingId(int _id){
		this._id = _id;
	}
	
	/**
	 * Sets the training name.
	 * @param name
	 */
	public void setTrainingName(String name){
		this.name = name;
	}
	
	/**
	 * Sets the training JSON object by using a String.
	 * @param json
	 */
	public void setTrainingJSON(String json){
		try {
            this.json = new JSONObject(json);
        }catch(JSONException e){
            e.printStackTrace();
        }
	}
	
	/**
	 * Sets the training JSON object by using a JSONObject.
	 * @param json
	 */
	public void setTrainingJSON(JSONObject json){
		this.json = json;
	}
	
	
}
