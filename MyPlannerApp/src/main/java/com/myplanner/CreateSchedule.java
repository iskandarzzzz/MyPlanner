package com.myplanner;

import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateSchedule  extends Activity implements OnItemSelectedListener{

	private String[] state= {"Step one","Step two","Step three","Step four","Step five",};

	private Spinner spinner ;
	
	private Button _createScheduleBtn, _cameraButton;
	private ImageView _picImageView;
	private EditText _eventNameEditText;
	static final int DATE_DIALOG_ID = 999;
	public static Uri imageUri;

	protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
	private int year;
	private int month;
	private int day;
 
	 
	private TimePicker timePicker;
	private DatePicker dpResult;
	private String date;
	
	public EditText[] editText;
	
	public static String image_Path ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createschedule);

		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, state);
		adapter_state
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter_state);
		spinner.setOnItemSelectedListener(this);
		
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		dpResult = (DatePicker) findViewById(R.id.dpResult);
		_eventNameEditText = (EditText) findViewById(R.id.event_et);
		
		_picImageView = (ImageView) findViewById(R.id.imageview);
		
		_createScheduleBtn= (Button) findViewById(R.id.create_btn);
		
		_createScheduleBtn.setOnClickListener(new OnClickListener() {
			
			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String eventName = _eventNameEditText.getText().toString();
				
				year = dpResult.getYear();
				month = dpResult.getMonth();
				day = dpResult.getDayOfMonth();
				Integer min = timePicker.getCurrentMinute();
				Integer hour = timePicker.getCurrentHour();
				
				
				// set selected date into textview
				date = new StringBuilder().append(month + 1)
				   .append("-").append(day).append("-").append(year)
				   .append(" ").toString();
				
				String time = new StringBuilder().append(hour).append(":").append(min).append(" ").toString();
	 
				int index = spinner.getSelectedItemPosition();
				StringBuilder values=new StringBuilder();
				StringBuilder status=new StringBuilder();
				
				
				for(int i=0;i<=index;i++ ){
					
					values = values.append(editText[i].getText().toString()).append(",");
					status = status.append("false").append(",");
				}
				
				String user = Utility.getUserFromSharedPref(CreateSchedule.this);
                if(!user.equals("")) {
                    if (new DatabaseHelper(CreateSchedule.this).creatEvent(new Schedule(eventName, date, time, index + 1 + "", image_Path, values.toString(), status.toString(),user)))
                        ;
                    Schedule_notification not = new Schedule_notification();
                    not.setAlarm(CreateSchedule.this,year, month, day, min, hour,eventName);
                    ShowAlertDialog("", "Event Created successfully.", CreateSchedule.this, false);
                }else{
                    ShowAlertDialog("", "Some problem occurred.", CreateSchedule.this, false);
                }
			}
		});

		_cameraButton = (Button) findViewById(R.id.camera_btn);
		_cameraButton.setOnClickListener(new OnClickListener() {
			
			

			@Override
			public void onClick(View v) {
				/*// TODO Auto-generated method stub
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			      startActivityForResult(intent, 0);
			      */
			      /*************************** Camera Intent Start ************************/ 
	                 
	                // Define the file-name to save photo taken by Camera activity
	                 
	                String fileName = "event"+new Date().getDate()+".jpg";
	                 
	                // Create parameters for Intent with filename
	                 
	                ContentValues values = new ContentValues();
	                 
	                values.put(MediaStore.Images.Media.TITLE, fileName);
	                 
	                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
	                 
	                // imageUri is the current activity attribute, define and save it for later usage  
	                 
	                imageUri = getContentResolver().insert(
	                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	                
	                image_Path = imageUri.toString();
	                 
	                /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/
	 
	                 
	                // Standard Intent action that can be sent to have the camera
	                // application capture an image and return it.  
	                 
	                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
	                 
	                 intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	                  
	                 intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
	                  
	                startActivityForResult( intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	                
	             /*************************** Camera Intent End ************************/
	                 
	                 
	            }    
	           
		});
	}
	
	  @Override
	     protected void onActivityResult( int requestCode, int resultCode, Intent data)
	        {
	            if ( requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	                 
	                if ( resultCode == RESULT_OK) {
	                 
	                   /*********** Load Captured Image And Data Start ****************/
	                     
	                    String imageId = convertImageUriToFile( imageUri,CreateSchedule.this);
	                     
	 
	                   //  Create and excecute AsyncTask to load capture image
	 
	                    new LoadImagesFromSDCard().execute(""+imageId);
	                     
	                  /*********** Load Captured Image And Data End ****************/
	                     
	                
	                } else if ( resultCode == RESULT_CANCELED) {
	                     
	                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
	                } else {
	                     
	                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
	                }
	            }
	        }
	      
	  
	     /************ Convert Image Uri path to physical path **************/
	      
	     public static String convertImageUriToFile ( Uri imageUri, Activity activity )  {
	      
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
	                 
	                cursor = activity.managedQuery(
	                         
	                                imageUri,         //  Get data for specific image URI
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
	      
	      
	         /**
	         * Async task for loading the images from the SD card.
	         *
	         * @author Android Example
	         *
	         */
	          
	        // Class with extends AsyncTask class
	         
	     public class LoadImagesFromSDCard  extends AsyncTask<String, Void, Void> {
	             
	            private ProgressDialog Dialog = new ProgressDialog(CreateSchedule.this);
	             
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
	                   
	                   _picImageView.setImageBitmap(mBitmap);
	                }  
	                 
	            }
	             
	        }
	         
	
	
	/* @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      // TODO Auto-generated method stub
	      super.onActivityResult(requestCode, resultCode, data);
	      Bitmap bp = (Bitmap) data.getExtras().get("data");
	      _picImageView.setImageBitmap(bp);
	      
	      CapturePhotoUtils.insertImage(getContentResolver(), bp, "event"+new Date().getDate(), "");
	   }*/
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.et_layout);
		linearLayout.removeAllViews();
		// TODO Auto-generated method stub
		editText = new EditText[5];

		for(int i=0;i<=position;i++){
			EditText et = new EditText(this);
			et.setWidth(250);
			
			linearLayout.addView(et);
			editText[i] = et;

		}

	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

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
                    CreateSchedule.this.finish();;
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

}
