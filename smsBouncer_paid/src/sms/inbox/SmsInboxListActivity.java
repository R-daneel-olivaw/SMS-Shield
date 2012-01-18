package sms.inbox;

import java.util.ArrayList;
import java.util.List;

import sms.shieldpro.R;
import sms.message.AkshatMessage;
import sms.message.AkshatMessageAdapter;
import sms.message.InboxMessageViewActivity;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;

public class SmsInboxListActivity extends ListActivity implements OnItemClickListener
{
    String[] from;
    int[] to;
    SimpleCursorAdapter adapter;
    AkshatMessageAdapter messageAd;

    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.inbox_list);

	messageAd = new AkshatMessageAdapter(getApplicationContext(), readInbox());

	this.setListAdapter(messageAd);

	getListView().setOnItemClickListener(this);
    }

    /*
     * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
     * arg2, long arg3) { Intent result = new Intent(); Bundle bundle = new
     * Bundle();
     * 
     * TextView itemView = (TextView) arg1.findViewById(R.id.number_entry);
     * 
     * bundle.putString("CONTACT_NAME", ""); bundle.putString("CONTACT_NUMBER",
     * itemView.getText().toString().replaceAll("-", ""));
     * 
     * result.putExtras(bundle);
     * 
     * setResult(Activity.RESULT_OK, result); finish(); }
     */

    @Override
    public void onBackPressed()
    {

	// Intent result = new Intent();
	// Bundle bundle = new Bundle();
	//
	// bundle.putString("CONTACT_NAME", from[arg2]);
	// bundle.putInt("CONTACT_NUMBER", to[arg2]);
	//
	// result.putExtras(bundle);

	setResult(Activity.RESULT_CANCELED, null);

	super.onBackPressed();
    }

    List<AkshatMessage> readInbox()
    {
	Uri uri = Uri.parse("content://sms/inbox");
	// returns all the results from the given Context
	Cursor c = getApplicationContext().getContentResolver().query(uri, null, null, null, null);

	String body = null;
	String number = null;
	String time = null;

	List<AkshatMessage> listOfAkshatMessages = new ArrayList<AkshatMessage>();

	if (c.moveToFirst())
	{ // move cursor to first row
	  // retrieves the body and number of the SMS
	    while (c.moveToNext())
	    {
		body = c.getString(c.getColumnIndexOrThrow("body")).toString();
		number = c.getString(c.getColumnIndexOrThrow("address")).toString();
		time = c.getString(c.getColumnIndexOrThrow("date")).toString();

		listOfAkshatMessages.add(new AkshatMessage(number, body, Long.parseLong(time), "**"));
	    }
	}

	// when your done, close the cursor.
	c.close();
	return listOfAkshatMessages;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3)
    {
	int loc = Integer.parseInt((String) v.getTag());
	Log.d("AKS", loc + "-JGJHVBJWSDBKJN");

	Intent i = new Intent(this, InboxMessageViewActivity.class);
	i.putExtra("message", messageAd.blockedMessages.get(loc));

	startActivity(i);

    }
}
