package com.myplanner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewEventActivity extends Activity {

	private LinearLayout linearLayout;

	private CheckBox[] checkBoxs;
	private TextView[] textViews;
	private TextView _eventTextView , _dateTextView , _timeTextView;
	private ImageView _ImageView;
    private static String imageUri;

	private Button _completeSchdleButton;

	private int _index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventdetails);

		_eventTextView = (TextView) findViewById(R.id.eventname_et);
		_dateTextView = (TextView) findViewById(R.id.date_tv);
		_timeTextView = (TextView) findViewById(R.id.time_tv);
		_ImageView = (ImageView) findViewById(R.id.event_imgvw);
		_completeSchdleButton = (Button) findViewById(R.id.compltschedule_btn);
		_completeSchdleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(allChecked()==false){
					Constant.ShowAlertDialog("Complete Schedule", "Please finish all the steps to complete the event.", ViewEventActivity.this, false);
				}else{
					ShowAlertDialog("Schedule Completed", "Well done!! You have completed all the steps.", ViewEventActivity.this, false);
				}
			}
		});

		CheckBox _CheckBox;

		Intent intent = getIntent();

		_index = intent.getIntExtra("index", 0);
        imageUri = intent.getStringExtra("image_uri");

		LinearLayout outer_Layout = (LinearLayout) findViewById(R.id.dynamicLayout);



		Schedule schedule = ViewEventListActivity.list.get(_index);

		String name = schedule.get_eventName();
		String date = schedule.get_date();
		String time = schedule.get_time();
		String imagePath = schedule.get_photoId();
		String stepsString = schedule.get_eventsString();
		String statusString = schedule.get_stepStatusString();

		String[] steps = stepsString.split(",");
		String[] status = statusString.split(",");



		_eventTextView.setText(name);
		_dateTextView.setText("Date : "+date);
		_timeTextView.setText("Time : "+time);


		/*********** Load Captured Image And Data Start ****************/

		String imageId = convertImageUriToFile(ViewEventActivity.this);


		//  Create and excecute AsyncTask to load capture image

		new LoadImagesFromSDCard().execute(""+imageId);

		//Uri selectedImageUri = Uri.parse(new File(imagePath)+".jpg"); //Uri.fromFile(new File(imagePath+".jpg"));
		//_ImageView.setImageURI(selectedImageUri);

		int size = steps.length;

		checkBoxs = new CheckBox[size];
		textViews = new TextView[size];

		for(int i=0;i<size;i++){

			_CheckBox = new CheckBox(this);
			_eventTextView = new TextView(this);
			_eventTextView.setWidth(150);
			_eventTextView.setTextColor(Color.BLACK);
			_eventTextView.setTextSize(22);

			linearLayout = new LinearLayout(this);

			linearLayout.addView(_CheckBox);
			linearLayout.addView(_eventTextView);
			linearLayout.setOrientation(LinearLayout.HORIZONTAL);

			if(status[i].equalsIgnoreCase("false")){
				_CheckBox.setChecked(false);
			}else{
				_CheckBox.setChecked(true);
			}

			_eventTextView.setText(steps[i].toString());

			checkBoxs[i] = _CheckBox;
			textViews[i] = _eventTextView;



			outer_Layout.addView(linearLayout);

		}
	}

	public static void ShowAlertDialog(final String title, String message, final Context context, final boolean redirectToPreviousScreen) {
		try {

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(message);
			builder.setTitle(title);
			builder.setCancelable(false);
			builder.setInverseBackgroundForced(true);
			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {

					((ViewEventActivity) context).finish();

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	private boolean allChecked(){
		boolean isChecked = false;

		for(int i=0;i<checkBoxs.length;i++){
			if(checkBoxs[i].isChecked())
				isChecked = true;
			else {
				isChecked = false;
				return false;
			}
		}

		return isChecked;
	}

	/**
	 * Async task for loading the images from the SD card.
	 *
	 * @author Android Example
	 *
	 */

	// Class with extends AsyncTask class

	public class LoadImagesFromSDCard  extends AsyncTask<String, Void, Void> {

		private ProgressDialog Dialog = new ProgressDialog(ViewEventActivity.this);

		Bitmap mBitmap;

		protected void onPreExecute() {
			/****** NOTE: You can call UI Element here. *****/

			// Progress Dialog
			Dialog.setMessage(" Loading image from Sdcard..");
			Dialog.show();
		}


		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			Bitmap bitmap = null;
			Bitmap newBitmap = null;
			Uri uri = null;       


			try {

				/**  Uri.withAppendedPath Method Description
				 * Parameters
				 *    baseUri  Uri to append path segment to
				 *    pathSegment  encoded path segment to append
				 * Returns
				 *    a new Uri based on baseUri with the given segment appended to the path
				 */

				uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);

				/**************  Decode an input stream into a bitmap. *********/
				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

				if (bitmap != null) {

					/********* Creates a new bitmap, scaled from an existing bitmap. ***********/

					newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);

					bitmap.recycle();

					if (newBitmap != null) {

						mBitmap = newBitmap;
					}
				}
			} catch (IOException e) {
				// Error fetching image, try to recover

				/********* Cancel execution of this task. **********/
				cancel(true);
			}

			return null;
		}


		protected void onPostExecute(Void unused) {

			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();

			if(mBitmap != null)
			{
				// Set Image to ImageView  

				_ImageView.setImageBitmap(mBitmap);
			}  

		}

	}


	/************ Convert Image Uri path to physical path **************/

	public static String convertImageUriToFile (Activity activity )  {

		Cursor cursor = null;
		int imageID = 0;

		try {

			/*********** Which columns values want to get *******/
			String [] proj={
					MediaStore.Images.Media.DATA,
					MediaStore.Images.Media._ID,
					MediaStore.Images.Thumbnails._ID,
					MediaStore.Images.ImageColumns.ORIENTATION
			};

            Uri baseUri = Uri.parse(imageUri);
			cursor = activity.managedQuery(

                    baseUri,         //  Get data for specific image URI
					proj,             //  Which columns to return
					null,             //  WHERE clause; which rows to return (all rows)
					null,             //  WHERE clause selection arguments (none)
					null              //  Order-by clause (ascending by name)

					);

			//  Get Query Data

			int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
			int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			//int orientation_ColumnIndex = cursor.
			//    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

			int size = cursor.getCount();

			/*******  If size is 0, there are no images on the SD Card. *****/

			if (size == 0) {


			}
			else
			{

				int thumbID = 0;
				if (cursor.moveToFirst()) {

					/**************** Captured image details ************/

					/*****  Used to show image on view in LoadImagesFromSDCard class ******/
					imageID     = cursor.getInt(columnIndex);

					thumbID     = cursor.getInt(columnIndexThumb);

					String Path = cursor.getString(file_ColumnIndex);

					//String orientation =  cursor.getString(orientation_ColumnIndex);

					String CapturedImageDetails = " CapturedImageDetails : \n\n"
							+" ImageID :"+imageID+"\n"
							+" ThumbID :"+thumbID+"\n"
							+" Path :"+Path+"\n";


				}
			}    
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		// Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

		return ""+imageID;
	}




}
