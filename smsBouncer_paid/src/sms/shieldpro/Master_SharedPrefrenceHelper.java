package sms.shieldpro;

import android.content.Context;
import android.content.SharedPreferences;

public class Master_SharedPrefrenceHelper
{
    private static final String MASTER_PREFS_NAME = "sms.shieldpro_preferences";
    private static final String MAJOR_KEY = "app_active_flag";

    private static final String NOTIFICATION_KEY = "app_notification_flag";
    private static final String TOAST_KEY = "app_toast_flag";

    public static int LIST_CAP = 100;

    public static final int PENDING_BLOCKED_MESSAGE_NOTIFICATION_ID = 1;

    // private SharedPreferences settings;

    static
    {

    }

    synchronized static boolean getMasterStatusPref(Context context)
    {
	// final String PREFS_NAME = "blocked_list_sms_bouncer";
	// final String MAJOR_KEY = "blocked_list";

	SharedPreferences settings;

	settings = context.getSharedPreferences(MASTER_PREFS_NAME, 0);
	if (settings != null)
	{
	    return settings.getBoolean(MAJOR_KEY, true);
	}

	return true;

    }

    synchronized static boolean getNotificationBarPref(Context context)
    {
	// final String PREFS_NAME = "blocked_list_sms_bouncer";
	// final String MAJOR_KEY = "blocked_list";

	SharedPreferences settings;

	settings = context.getSharedPreferences(MASTER_PREFS_NAME, 0);
	if (settings != null)
	{
	    return settings.getBoolean(NOTIFICATION_KEY, true);
	}

	return true;

    }
    
    synchronized static boolean getToastPref(Context context)
    {
	// final String PREFS_NAME = "blocked_list_sms_bouncer";
	// final String MAJOR_KEY = "blocked_list";

	SharedPreferences settings;

	settings = context.getSharedPreferences(MASTER_PREFS_NAME, 0);
	if (settings != null)
	{
	    return settings.getBoolean(TOAST_KEY, true);
	}

	return true;

    }

    // synchronized static void updatePrefrences(String newData , Context
    // context)
    // {
    // SharedPreferences settings;
    // settings = context.getSharedPreferences(PREFS_NAME, 0);
    //
    // SharedPreferences.Editor e = settings.edit();
    // e.putString(MAJOR_KEY, newData);
    // e.commit();
    // }

}