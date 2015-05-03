package com.myplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseSignActivity extends Activity{

	private Button _signin_btn , _signup_btn , _logoutButton;
	//Schedule_notification not;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosesign);
		//not = new Schedule_notification();
		//not.setAlarm(this);
		
		_signin_btn = (Button) findViewById(R.id.signin_btn);
		_signin_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ChooseSignActivity.this, SigninActivity.class));
                ChooseSignActivity.this.finish();

			}
		});
		_signup_btn = (Button) findViewById(R.id.signup_btn);
		_signup_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ChooseSignActivity.this, SignupActivity.class));
                ChooseSignActivity.this.finish();
			}
		});
		
		
	}

}
