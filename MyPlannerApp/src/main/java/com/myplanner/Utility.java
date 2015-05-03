package com.myplanner;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MK00333300 on 5/1/2015.
 */
public class Utility {

    public static String getUserFromSharedPref(Context mContext){
        SharedPreferences sharedPref = mContext.getSharedPreferences(Constants.sp_name,Context.MODE_PRIVATE);
        return sharedPref.getString(Constants.current_user,"");
    }
}
