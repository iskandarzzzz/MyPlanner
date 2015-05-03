package com.myplanner;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast Intent 
 * and then starts the IntentService {@code SampleSchedulingService} to do some work.
 */
public class Schedule_notification extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    NotificationManager mNotificationManager;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;
    public static final int NOTIFICATION_ID =1;
    Context context;
    
  
    @Override
    public void onReceive(Context context, Intent intent) {   
       this.context = context;
    	
        String msg = ("My planner schedule");
        String name = intent.getExtras().getString("event_name");
        msg = msg + " : Event - "+name;
        
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, intent);
        
        sendNotification(msg , context);
       Schedule_notification.completeWakefulIntent(intent);
        // END_INCLUDE(alarm_onreceive)
    }

    // BEGIN_INCLUDE(set_alarm)
    /**
     * Sets a repeating alarm that runs once a day at approximately 8:30 a.m. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     * @param context
     */
    public void setAlarm(Context context,int year,int month,int day,int min,int hour,String eventName) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Schedule_notification.class);
        intent.putExtra("event_name", eventName);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

       
       // String date =  DatabaseHelper.schDate.get_date();
      //  String time = DatabaseHelper.schDate.get_time();
        
       // String date_time = date + time;
        long lng = System.currentTimeMillis();
       // Intent intent1 = new Intent(context, Schedule_notification.class);
       // intent1.putExtra("event_name", eventName);
        
        //long dttime = Long.parseLong(date_time);
        //Log.i("date" , date_time);
        //alarmIntent = PendingIntent.getActivity(context,0,intent1,0);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lng);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);
  
       
        // Set the alarm to fire at approximately 8:30 a.m., according to the device's
        // clock, and to repeat once a day.
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),  alarmIntent);
        
        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, Bootreceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);           
    }
    // END_INCLUDE(set_alarm)

    private void sendNotification(String msg, Context context) {
    	
    	
        mNotificationManager = (NotificationManager)
               context.getSystemService(Context.NOTIFICATION_SERVICE);
    
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
            new Intent(context,ChooseSignActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(("This is you new event"))
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
 
    
    /**
     * Cancels the alarm.
     * @param context
     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
        
        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the 
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, Bootreceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(cancel_alarm)
}
