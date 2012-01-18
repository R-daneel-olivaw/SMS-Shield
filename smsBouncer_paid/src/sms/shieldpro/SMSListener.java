package sms.shieldpro;

import java.util.ArrayList;
import java.util.List;

import sms.shieldpro.R;
import sms.message.AkshatMessage;
import sms.message.AkshatMessageListActivity;
import sms.message.AkshatMessageSharedPrefHelper;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSListener extends BroadcastReceiver
{

    Context buffer;

    String[] blockedDb;
    String[] whiteDb;
    String recentBlockedFor;
    List<BlockedEntity> blockedEntities = new ArrayList<BlockedEntity>();
    List<BlockedEntity> whiteEntries = new ArrayList<BlockedEntity>();

    private static final String MAJOR_DELIMIT = "#~#";

    private String[] akshatMessageDb;

    private List<AkshatMessage> akshatMessageEntities = new ArrayList<AkshatMessage>();

    private static String KEY = "PENDING_MESSAGE_KEY";

    @Override
    public void onReceive(Context context, Intent intent)
    {
	buffer = context;
	String action = intent.getAction();
	if (action.equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED"))
	{
	    if (Master_SharedPrefrenceHelper.getMasterStatusPref(context))
	    {
		// TelephonyManager tm = (TelephonyManager)
		// context.getSystemService("TELEPHONY_SERVICE");
		// while (tm == null)
		// {
		//
		// }
		// tm.listen(mPhoneListener,
		// PhoneStateListener.LISTEN_CALL_STATE);
		// // if
		// //
		// (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING))
		// // {
		// //
		// doSomething(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
		// // }

		Bundle bundle = intent.getExtras();
		Object[] pdus = (Object[]) bundle.get("pdus");
		SmsMessage message[] = new SmsMessage[pdus.length];
		StringBuilder collatedBody = new StringBuilder();
		for (int i = 0; i < message.length; i++)
		{
		    message[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
		    collatedBody.append(message[i].getMessageBody());
		}

		SmsMessage unit = message[0];

		if (!unit.isEmail())
		{
		    doSomething(unit.getOriginatingAddress());
		}

		if (!searchWhite(unit.getDisplayOriginatingAddress()) && searchBlack(unit.getOriginatingAddress()))
		{
		    // To avoid the sms from reaching the INBOX.
		    this.abortBroadcast();
		    if (Master_SharedPrefrenceHelper.getToastPref(buffer))
		    {
			Toast.makeText(buffer, buffer.getString(R.string.sms_bouncer_blocked) + unit.getOriginatingAddress(),
				Toast.LENGTH_LONG).show();
		    }
		    Black_SharedredPrefrencesHelper.updatePrefrences(collateDb(), buffer);

		    akshatMessageEntities.add(new AkshatMessage(unit.getOriginatingAddress(), collatedBody.toString(), unit
			    .getTimestampMillis(), recentBlockedFor));
		    AkshatMessageSharedPrefHelper.updatePrefrences(akshatMessageCollateDb(), buffer);

		    sendNotification();

		}
	    }
	}
    }

    private boolean searchBlack(String address)
    {
	for (BlockedEntity b : blockedEntities)
	{
	    if (b.getEntityIdentifier() != null)
	    {
		if (address.length() >= b.getEntityIdentifier().length()
			&& address.toLowerCase().startsWith(b.getEntityIdentifier().toLowerCase()))
		{
		    b.setBlockedCount(b.getBlockedCount() + 1);
		    recentBlockedFor = b.getEntityIdentifier();

		    return true;
		}
	    }
	}
	return false;
    }

    private boolean searchWhite(String address)
    {
	for (BlockedEntity b : whiteEntries)
	{
	    if (b.getEntityIdentifier() != null)
	    {
		if (address.length() >= b.getEntityIdentifier().length()
			&& address.toLowerCase().startsWith(b.getEntityIdentifier().toLowerCase()))
		{
		    b.setBlockedCount(b.getBlockedCount() + 1);

		    return true;
		}
	    }
	}
	return false;
    }

    private void doSomething(String number)
    {
	Log.d("<<<>>>", number);

	dataOperations();
	dataOperationsWhite();

	inflateDb();
	inflateDbWhite();

	akshatMessageDataOperations();
	akshatMessageInflateDb();

	// String uri = "tel:" + posted_by.trim();
	// Intent intent = new Intent(buffer, AbhayChachaActivity.class);
	// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// intent.setData(Uri.parse(uri));
	// buffer.startActivity(intent);
    }

    void dataOperations()
    {
	blockedDb = Black_SharedredPrefrencesHelper.getBlockedListFromPref(buffer).split(MAJOR_DELIMIT);
	Log.d("DataUpdate", "dataOperations");
    }

    void inflateDb()
    {
	for (String c : blockedDb)
	{
	    blockedEntities.add(new BlockedEntity(c));
	}

    }

    void dataOperationsWhite()
    {
	whiteDb = White_SharedredPrefrencesHelper.getBlockedListFromPref(buffer).split(MAJOR_DELIMIT);
	Log.d("DataUpdate", "dataOperations");
    }

    void inflateDbWhite()
    {
	for (String c : whiteDb)
	{
	    whiteEntries.add(new BlockedEntity(c));
	}

    }

    String collateDb()
    {
	String collatedDb = "";

	for (BlockedEntity c : blockedEntities)
	{
	    collatedDb += c.toString() + MAJOR_DELIMIT;
	}

	return collatedDb;
    }

    void akshatMessageDataOperations()
    {
	akshatMessageDb = AkshatMessageSharedPrefHelper.getBlockedAkshatMessageList(buffer).split(MAJOR_DELIMIT);
	Log.d("DataUpdate", "dataOperations");
    }

    void akshatMessageInflateDb()
    {
	for (String c : akshatMessageDb)
	{
	    akshatMessageEntities.add(new AkshatMessage(c));
	}

    }

    String akshatMessageCollateDb()
    {
	String collatedDb = "";

	for (AkshatMessage c : akshatMessageEntities)
	{
	    collatedDb += c.toString() + MAJOR_DELIMIT;
	}

	return collatedDb;
    }

    void sendNotification()
    {
	if (Master_SharedPrefrenceHelper.getNotificationBarPref(buffer))
	{
	    Context context = buffer;
	    int count = updatePendingCount();

	    String ns = Context.NOTIFICATION_SERVICE;
	    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

	    int icon = R.drawable.sms_bouncer_icon;
	    CharSequence tickerText = "Sms Blocked";
	    long when = System.currentTimeMillis();

	    Notification notification = new Notification(icon, tickerText, when);

	    CharSequence contentTitle = "Sms Blocked";
	    CharSequence contentText = "You have " + count + " messages to be reviewed";
	    Intent notificationIntent = new Intent(context, AkshatMessageListActivity.class);
	    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

	    notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

	    // *******
	    long[] vibrate = { 0, 200, 100, 400, 100, 800 };
	    notification.vibrate = vibrate;
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;

	    mNotificationManager.notify(Master_SharedPrefrenceHelper.PENDING_BLOCKED_MESSAGE_NOTIFICATION_ID, notification);
	}
    }

    int updatePendingCount()
    {
	SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(buffer);

	int currentCount = app_preferences.getInt(KEY, 0);

	Editor e = app_preferences.edit();

	e.putInt(KEY, ++currentCount);
	e.commit();

	return currentCount;
    }
}