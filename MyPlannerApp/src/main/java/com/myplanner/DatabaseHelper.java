package com.myplanner;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	// Database Name
	private static final String DATABASE_NAME = "eventManager";

	private static final int DATABASE_VERSION = 1;


	// Table Names
	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_EVENT = "event";

	private static final String KEY_USERNAME = "username";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASSWORD = "password";


	private static final String KEY_EVENTNAME = "eventname";
	private static final String KEY_DATE = "date";
	private static final String KEY_TIME = "time";
	private static final String KEY_NOOFSTEPS = "noofsteps";
	private static final String KEY_PHOTOID = "photoid";
	private static final String KEY_STEPSSTRING = "stepsstring";
	private static final String KEY_STEPSTATUS = "stepsstatus";
	private static final String KEY_USER = "user";
    private Context mContext;

	//public static Schedule  schDate = new Schedule();

	//private static final String KEY_STATUS = "status";




	private static final String CREATE_TABLE_LOGIN = "CREATE TABLE "
			+ TABLE_LOGIN + "(" + KEY_USERNAME + " VARCHAR PRIMARY KEY," + KEY_EMAIL
			+ " VARCHAR," + KEY_PASSWORD + " VARCHAR" + ")";

	// event table create statement
	private static final String 
	
	CREATE_TABLE_EVENT = "CREATE TABLE " + TABLE_EVENT
			+ "(" + KEY_EVENTNAME + " VARCHAR ," + KEY_DATE + " CURRENT_DATE,"
			+ KEY_TIME + " CURRENT_TIME ," + KEY_NOOFSTEPS + " VARCHAR,"
			+ KEY_PHOTOID + " VARCHAR ," + KEY_STEPSSTRING + " VARCHAR," +KEY_STEPSTATUS 
			+" VARCHAR,"+ KEY_USERNAME + " VARCHAR)";

	/*CREATE_TABLE_EVENT = "CREATE TABLE " + TABLE_EVENT
			+ "(" + KEY_EVENTNAME + " VARCHAR ," + KEY_DATE + " CURRENT_DATE,"
			+ KEY_TIME + " CURRENT_TIME ," + KEY_NOOFSTEPS + " VARCHAR,"
			+ KEY_PHOTOID + " VARCHAR ," + KEY_STEPSSTRING + " VARCHAR," +KEY_STEPSTATUS 
			+" VARCHAR " + KEY_USER + "VARCHAR" + "FOREIGN KEY ("+KEY_USERNAME+") REFERENCES " + TABLE_LOGIN +" ("+KEY_USERNAME+"));"; */

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null,  DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		// creating required tables
		db.execSQL(CREATE_TABLE_LOGIN);
		db.execSQL(CREATE_TABLE_EVENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);

		// create new tables
		onCreate(db);
	}

	/*
	 * Creating a login
	 */
	public boolean createLogin(Login login) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, login.getUsername());
		values.put(KEY_EMAIL, login.getEmail());
		values.put(KEY_PASSWORD, login.getPassword());

		// insert row
		db.insert(TABLE_LOGIN, null, values);


		return true;
	}

	/*
	 * get login info
	 */
	public Login getLogin(String login_name) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE "
				+ KEY_USERNAME + " = '" + login_name+"'";

		Login login = null;
		try{
			Cursor c = db.rawQuery(selectQuery, null);

			if (c.getCount() > 0) {

				c.moveToFirst();

				do {  
					login = new Login();
					login.setUsername(c.getString(c.getColumnIndexOrThrow(KEY_USERNAME)));
					login.setEmail(c.getString(c.getColumnIndexOrThrow(KEY_EMAIL)));
					login.setPassword(c.getString(c.getColumnIndexOrThrow(KEY_PASSWORD)));
				} while (c.moveToNext());  
			}




		}catch(SQLException sqlException){
			login = null;
			sqlException.printStackTrace();
		}
		return login;
	}

	/*
	 * Creating a EVENT
	 */
	public boolean creatEvent(Schedule schedule) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVENTNAME, schedule.get_eventName());
		values.put(KEY_DATE, schedule.get_date());
		values.put(KEY_TIME,schedule.get_time());
		values.put(KEY_NOOFSTEPS, schedule.get_noOfSteps());
		values.put(KEY_PHOTOID, schedule.get_photoId());
		values.put(KEY_STEPSSTRING,schedule.get_eventsString());
		values.put(KEY_STEPSTATUS, schedule.get_stepStatusString());
		values.put(KEY_USERNAME, schedule.get_username());

		// insert row
		db.insert(TABLE_EVENT, null, values);


		return true;
	}

	/*
	 * get Events
	 */
	public ArrayList<Schedule> getEvents(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		ArrayList<Schedule> eventList = new ArrayList<Schedule>();

		String selectQuery = "SELECT * FROM " + TABLE_EVENT  +" WHERE "+KEY_USERNAME + " = '"+ username +"'";

		try{
			Cursor c = db.rawQuery(selectQuery, null);


			if (c.getCount() > 0) {


				c.moveToFirst();

				do {  
					Schedule schedule = new Schedule();
					schedule.set_eventName(c.getString(c.getColumnIndexOrThrow(KEY_EVENTNAME)));  
					schedule.set_date(c.getString(c.getColumnIndexOrThrow(KEY_DATE)));  
					schedule.set_time(c.getString(c.getColumnIndexOrThrow(KEY_TIME)));  
					schedule.set_noOfSteps(c.getString(c.getColumnIndexOrThrow(KEY_NOOFSTEPS)));  
					schedule.set_photoId(c.getString(c.getColumnIndexOrThrow(KEY_PHOTOID)));  
					schedule.set_eventsString(c.getString(c.getColumnIndexOrThrow(KEY_STEPSSTRING)));  
					schedule.set_stepStatusString(c.getString(c.getColumnIndexOrThrow(KEY_STEPSTATUS)));

					// Adding schedule to list  
					eventList.add(schedule);  

				} while (c.moveToNext());  
			}
		}catch(SQLException sqlException){
			eventList = null;
			sqlException.printStackTrace();
		}
		return eventList;
	}

 /*public void Plan_time ()
 {
	 
	 SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT KEY_DATE, KEY_TIME FROM " + TABLE_EVENT +  " ORDER BY KEY_DATE LIMIT 1";

		
		try{
			Cursor c = db.rawQuery(selectQuery, null);

			if (c.moveToFirst()) {

									
					schDate.set_date(c.getString(c.getColumnIndexOrThrow(KEY_DATE)));  
					schDate.set_time(c.getString(c.getColumnIndexOrThrow(KEY_TIME)));
			}
			
			else {
					
					schDate.set_date("30/04/2015");
					schDate.set_time("18:07");
			}
		}catch(SQLException sqlException){
		
			sqlException.printStackTrace();
		}*/
	
 
	 
 
}
