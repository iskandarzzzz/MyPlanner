package com.myplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


// BEGIN_INCLUDE(autostart)
public class Bootreceiver extends BroadcastReceiver {
    Schedule_notification alarm = new Schedule_notification();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            //alarm.setAlarm(context);
        }
    }
}
//END_INCLUDE(autostart)