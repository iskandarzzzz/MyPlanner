package com.myplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SigninActivity extends Activity{

	private Button _signin_Btn;
	private EditText _usernameEditText , _passwordEditText;
	
	public static String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		_usernameEditText = (EditText) findViewById(R.id.username);
		_passwordEditText = (EditText) findViewById(R.id.pass_et);
		
		_signin_Btn = (Button) findViewById(R.id.signin_bttn);
		_signin_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					
			    name = _usernameEditText.getText().toString();
				String password = _passwordEditText.getText().toString();
				
				Login login = new DatabaseHelper(SigninActivity.this).getLogin(name);
				
				if(login==null ){
					Constant.ShowAlertDialog("Oops", "User is not correct.", SigninActivity.this, false);
				}else if(!login.getUsername().equals(name)||!login.getPassword().equals(password)){
					Constant.ShowAlertDialog("Oops", "Username or password does not match", SigninActivity.this, false);
				}else if(login.getPassword().equals(password)&&login.getUsername().equals(name)){
                    createSharedPref();
					startActivity(new Intent(SigninActivity.this, ScheduleActivity.class));
                    SigninActivity.this.finish();
				}
			}
		});
	}

    public void onClick(View view){
        startActivity(new Intent(SigninActivity.this, SignupActivity.class));
        this.finish();
    }

    private void createSharedPref(){
        Context context = getBaseContext();
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.sp_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.current_user, name);
        editor.commit();
    }
}
