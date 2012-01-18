package sms.message;

import android.content.Context;
import android.content.SharedPreferences;

public class AkshatMessageSharedPrefHelper
{
    private static final String PREFS_NAME = "blocked_list_sms_akshat_mesage";
    private static final String MAJOR_KEY = "akshat_mesage_list";

    // private SharedPreferences settings;

    static
    {

    }

    public synchronized static String getBlockedAkshatMessageList(Context context)
    {
	// final String PREFS_NAME = "blocked_list_sms_bouncer";
	// final String MAJOR_KEY = "blocked_list";

	SharedPreferences settings;

	settings = context.getSharedPreferences(PREFS_NAME, 0);
	if (settings == null)
	{
	}

	return settings.getString(MAJOR_KEY, "");
    }
    
    public synchronized static void updatePrefrences(String newData , Context context)
    {
	SharedPreferences settings;
	settings = context.getSharedPreferences(PREFS_NAME, 0);
	
	SharedPreferences.Editor e = settings.edit();
	e.putString(MAJOR_KEY, newData);
	e.commit();
    }
}
