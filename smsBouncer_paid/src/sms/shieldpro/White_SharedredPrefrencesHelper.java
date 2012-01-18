package sms.shieldpro;

import android.content.Context;
import android.content.SharedPreferences;

public class White_SharedredPrefrencesHelper
{
    private static final String PREFS_NAME = "white_list_sms_bouncer";
    private static final String MAJOR_KEY = "white_list";

    public static boolean REFESH_REQUIRED = false;

    // private SharedPreferences settings;

    static
    {

    }

    public synchronized static String getBlockedListFromPref(Context context)
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

    public synchronized static void updatePrefrences(String newData, Context context)
    {
	SharedPreferences settings;
	settings = context.getSharedPreferences(PREFS_NAME, 0);

	SharedPreferences.Editor e = settings.edit();
	e.putString(MAJOR_KEY, newData);
	e.commit();
    }

    public static synchronized boolean addToWhiteList(String newEntry, Context context)
    {
	StringBuilder buffer = new StringBuilder(getBlockedListFromPref(context));
	String[] bufferList = buffer.toString().split("#~#");
	if (bufferList.length < Master_SharedPrefrenceHelper.LIST_CAP && !isAlreadyContained(buffer.toString(),newEntry))
	{
	    buffer.append("#~#" + new BlockedEntity(newEntry, 0, true));
	    updatePrefrences(buffer.toString(), context);
	    REFESH_REQUIRED = true;
	    return true;
	}

	return false;
    }

    private static synchronized boolean isAlreadyContained(String bank, String targ)
    {
	bank = "#~#"+bank;
	if (bank.toLowerCase().indexOf("#~#" + targ.toLowerCase() + "&#") != -1)
	{
	    return true;
	}
	return false;
    }

}
