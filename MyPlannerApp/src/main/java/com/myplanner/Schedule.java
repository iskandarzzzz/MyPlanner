package com.myplanner;

public class Schedule {
	
	private String _eventName, _date , _time , _noOfSteps,_photoId , _eventsString , _stepStatusString, _username ;

	public Schedule() {
		// TODO Auto-generated constructor stub
	}

    public Schedule(String _eventName, String _date, String _time,
			String _noOfSteps, String _photoId ,String _eventsString , String _stepStatusString,String _username  ) {
		super();
		this._eventName = _eventName;
		this._date = _date; 
		this._time = _time;
		this._noOfSteps = _noOfSteps;
		this._photoId = _photoId;
		this._eventsString = _eventsString;
		this._stepStatusString = _stepStatusString;
        this._username = _username;
		

	}

	public String get_eventName() {
		return _eventName;
	}

	public void set_eventName(String _eventName) {
		this._eventName = _eventName;
	}

	public String get_date() {
		return _date;
	}

	public void set_date(String _date) {
		this._date = _date;
	}

	public String get_time() {
		return _time;
	}

	public void set_time(String _time) {
		this._time = _time;
	}

	public String get_noOfSteps() {
		return _noOfSteps;
	}

	public void set_noOfSteps(String _noOfSteps) {
		this._noOfSteps = _noOfSteps;
	}

	public String get_photoId() {
		return _photoId;
	}

	public void set_photoId(String _photoId) {
		this._photoId = _photoId;
	}

	public String get_eventsString() {
		return _eventsString;
	}

	public void set_eventsString(String _eventsString) {
		this._eventsString = _eventsString;
	}
	public String get_stepStatusString() {
		return _stepStatusString;
	}
	public void set_stepStatusString(String _stepStatusString) {
		this._stepStatusString = _stepStatusString;
	}

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }
	
}
