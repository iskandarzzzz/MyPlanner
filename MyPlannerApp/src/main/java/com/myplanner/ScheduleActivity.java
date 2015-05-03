package com.myplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScheduleActivity extends Activity{

	private Button _createSchedule_Btn , _viewSchedule_Btn;
	private Button _logoutButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		
		_createSchedule_Btn = (Button) findViewById(R.id.createschedule_btn);
		_createSchedule_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ScheduleActivity.this , CreateSchedule.class));
				
			}
		});
		
		_viewSchedule_Btn = (Button) findViewById(R.id.viewschedule_btn);
		_viewSchedule_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ScheduleActivity.this, ViewEventListActivity.class));
			}
		});
		
		_logoutButton = (Button) findViewById(R.id.logout_btn);
		_logoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ScheduleActivity.this, SigninActivity.class));
			}
		});
		
	}
}
