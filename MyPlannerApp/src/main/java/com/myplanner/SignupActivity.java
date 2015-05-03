package com.myplanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends Activity{


	private EditText _nameEditText , _emailEditText, _passwordEditText;
	private Button _registerButton;
	private TextView alternate;
	
	private static final char[] ATEXT_SYMBOLS =	{'@','.','_'};//;{'@','.','!','#','$','%','&','\'','*','+','-','/','=','?','^','_','`','{','|','}','~'};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		_nameEditText = (EditText) findViewById(R.id.username_et);
		_emailEditText =(EditText) findViewById(R.id.email_et);
		_passwordEditText =  (EditText) findViewById(R.id.pass_et);

		_registerButton = (Button) findViewById(R.id.signup_btn);
		
		alternate = (TextView) findViewById(R.id.alternate_tv);

		_registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String name = _nameEditText.getText().toString();
				String email = _emailEditText.getText().toString();
				String password = _passwordEditText.getText().toString();

				if (name.length() == 0 || name == null) {
					Constant.ShowAlertDialog("", "Please enter your username", SignupActivity.this, false);
				}else if(!isValidUsername(name)){
					Constant.ShowAlertDialog("", "Username invalid. Only A-Z,a-z,0-9,.,_ are allowed", SignupActivity.this, false);
				}else if(name.length()<3 || name.length()>20){
					Constant.ShowAlertDialog("", "Username must be 3 to 40 characters long", SignupActivity.this, false);
				}else if(email.length() == 0 || email == null){
					Constant.ShowAlertDialog("", "Please enter an email address", SignupActivity.this, false);
				}/*else if(!isValidMailID(email)){
					Constant.ShowAlertDialog("", "Please enter a valid email address", SignupActivity.this, false);
				}*/else if(password.length()==0||password.length()<5){
					Constant.ShowAlertDialog("", "Please enter a valid password", SignupActivity.this, false);

				}/*else if(isExist(SignupActivity.this ,name)){
					Constant.ShowAlertDialog("", "User already exists.", SignupActivity.this, false);

				}*/else{
					if(new DatabaseHelper(SignupActivity.this).createLogin(new Login(name, email, password)))
                        createSharedPref(name);
						ShowAlertDialog("", "User registered successfully.", SignupActivity.this, false);

				}

			}
		});


		alternate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignupActivity.this,
						SigninActivity.class);
			}

		});
		
	}

    private void createSharedPref(String name){
        Context context = getBaseContext();
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.sp_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.current_user, name);
        editor.commit();
    }
	

	public void ShowAlertDialog(final String title, String message, final Context context, final boolean redirectToPreviousScreen) {
		try {

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(message);
			builder.setTitle(title);
			builder.setCancelable(false);
			builder.setInverseBackgroundForced(true);
			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {

					context.startActivity(new Intent(context,ScheduleActivity.class));
                    SignupActivity.this.finish();

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	public static boolean isExist(Context context, String name ){

		Login login = new DatabaseHelper(context).getLogin(name);

		if(login==null)
			return false;
		else
			return true;

	}


	public boolean isValidUsername(String user){
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z_.]+$");
		Matcher matcher = pattern.matcher(user);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}


	public static boolean isValidMailID(String toAddress) {
		if(!isBasicallyValidEmailAddress(toAddress)	|| toAddress.indexOf('.') == -1)
			return false;
		int index1 = toAddress.lastIndexOf('.');
		int index2 = toAddress.lastIndexOf('@');

		if(toAddress.endsWith("."))
			return false ;

		if( index1 < index2	|| (index2 + 1) >= index1)
			return false;
		return true;
	}

	private static boolean isBasicallyValidEmailAddress(String email) {
		if (email == null) {
			return false;
		}
		boolean atFound = false;
		for (int i = 0; i < email.length(); i++) {
			char c = email.charAt(i);
			if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9') &&	!isAtextSymbol(c)) {
				return false;
			}
			if (c == '@') {
				if (atFound) {
					return false;
				}
				atFound = true;
			}
		}
		return atFound;
	}

	public static boolean isAtextSymbol(char c) {
		for (int i = 0; i < ATEXT_SYMBOLS.length; i++) {
			if (c == ATEXT_SYMBOLS[i]) {
				return true;
			}
		}
		return false;
	}

    public void onClick(View view){
        startActivity(new Intent(SignupActivity.this, SigninActivity.class));
        this.finish();
    }

}
