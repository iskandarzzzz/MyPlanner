package com.myplanner;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;



/** The splash activity for MyPlanner App. */
public class SplashActivity extends Activity {
	public static int screenHeight = 0;
	public static int imageSetHeight = 0;
	public static int imageSetWidth = 0;
	public static int screenWidth = 0;


	/**
	 * Called when the activity is created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);



		
		Thread splashThread = new Thread() {

			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < 3500) {
                        new DatabaseHelper(SplashActivity.this).getReadableDatabase();
						sleep(100);
						waited += 100;
					}
				} catch (InterruptedException e) {
				} finally {
					
					new DatabaseHelper(SplashActivity.this);
					
					Intent i = new Intent(SplashActivity.this,
							ChooseSignActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtra("MusicNoStart", 0);
					startActivity(i);
                    SplashActivity.this.finish();
				}
			}
		};
		splashThread.start();
	}


	
}