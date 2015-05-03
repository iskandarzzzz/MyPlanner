package com.myplanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;


public class Constant {


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

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}


	private Date _date;


	public Date stringToDate(String date){

		SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyy");  
		try {  
			_date = format.parse(date);  
		} catch (ParseException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}
		return _date;
	}

	public String dateToString(Date date){
		SimpleDateFormat  dateformat = new SimpleDateFormat("dd-MM-yyyy");  
		Date _date = new Date();  
		String datetime = dateformat.format(_date);

		return datetime;
	}

}
