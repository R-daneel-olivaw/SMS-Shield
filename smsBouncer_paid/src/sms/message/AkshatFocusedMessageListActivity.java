package sms.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sms.shieldpro.R;
import sms.shieldpro.White_SharedredPrefrencesHelper;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class AkshatFocusedMessageListActivity extends ListActivity implements OnItemClickListener
{
    ListView _listView;
    private String[] blockedDb;
    private static final String MAJOR_DELIMIT = "#~#";
    AkshatMessageAdapter messageAd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.message_list);
	_listView = getListView();

	String t = getIntent().getStringExtra("tagetSender");

	if (t != null)
	{

	    dataOperations();
	    List<AkshatMessage> blockedEntities = inflateDb();
	    List<AkshatMessage> targetBlockedEntries = new ArrayList<AkshatMessage>();

	    for (AkshatMessage a : blockedEntities)
	    {
		if (a.getSmsBlockedFor().equals(t))
		{
		    targetBlockedEntries.add(a);
		}
	    }
	    
	    Collections.reverse(targetBlockedEntries);

	    messageAd = new AkshatMessageAdapter(getApplicationContext(), targetBlockedEntries);
	    _listView.setAdapter(messageAd);

	    _listView.setOnItemClickListener(this);
	    registerForContextMenu(_listView);
	    // _listView.setOnItemLongClickListener(this);

	    // _listView.setDrawSelectorOnTop(true);
	}
    }

    void dataOperations()
    {
	blockedDb = AkshatMessageSharedPrefHelper.getBlockedAkshatMessageList(this).split(MAJOR_DELIMIT);
	Log.d("DataUpdate", "dataOperations");

    }

    List<AkshatMessage> inflateDb()
    {
	List<AkshatMessage> blockedEntities = new ArrayList<AkshatMessage>();

	// if (blockedDb[0] != null)
	for (String c : blockedDb)
	{
	    AkshatMessage buff = new AkshatMessage(c);
	    if (buff.getSmsFrom() != null && !buff.getSmsFrom().equalsIgnoreCase("null"))
		blockedEntities.add(buff);
	}

	return blockedEntities;
    }

    // @Override
    // public boolean onLongClick(View v)
    // {
    // Log.d("AKS", "$$$$$$$$$$$$");
    //
    // int loc = Integer.parseInt((String)v.getTag());
    // messageAd.blockedMessages.remove(loc);
    //
    // AkshatMessageSharedPrefHelper.updatePrefrences(akshatMessageCollateDb(),
    // getApplicationContext());
    //
    // messageAd.notifyDataSetChanged();
    //
    // return true;
    // }

    // @Override
    // public void onClick(View v)
    // {
    // int loc = Integer.parseInt((String)v.getTag());
    // Log.d("AKS", loc+"-JGJHVBJWSDBKJN");
    //
    // Intent i = new Intent(this, AkshatMessageViewActivity.class);
    // i.putExtra("message", messageAd.blockedMessages.get(loc));
    //
    // startActivity(i);
    //
    // }

    String akshatMessageCollateDb()
    {
	String collatedDb = "";

	for (AkshatMessage c : messageAd.blockedMessages)
	{
	    collatedDb += c.toString() + MAJOR_DELIMIT;
	}

	return collatedDb;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3)
    {
	int loc = Integer.parseInt((String) v.getTag());
	// Log.d("AKS", loc + "-JGJHVBJWSDBKJN");

	Intent i = new Intent(this, AkshatMessageViewActivity.class);
	i.putExtra("message", messageAd.blockedMessages.get(loc));

	startActivity(i);

    }

    public boolean deleteItemViaContextMenu(final View v)
    {
	Log.d("AKS", "$$$$$$$$$$$$");

	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage(R.string.delete_blocked_message).setCancelable(false)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
		{
		    public void onClick(DialogInterface dialog, int id)
		    {
			deleteMessageAction(v);
		    }
		}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
		{
		    public void onClick(DialogInterface dialog, int id)
		    {
			dialog.cancel();
		    }
		});
	AlertDialog alert = builder.create();

	alert.show();

	return true;
    }

    void deleteMessageAction(View v)
    {
	int loc = Integer.parseInt((String) v.getTag());
	messageAd.blockedMessages.remove(loc);

	AkshatMessageSharedPrefHelper.updatePrefrences(akshatMessageCollateDb(), getApplicationContext());

	messageAd.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	if (messageAd.blockedMessages.size() != 0)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.message_list_activity_menu, menu);
	}
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	// Handle item selection
	switch (item.getItemId())
	{

	    case R.id.delete_all:
		deleteAll();
		return true;

		// case R.id.select_some:
		// return true;

	    default:
		return super.onOptionsItemSelected(item);
	}
    }

    void deleteAll()
    {
	messageAd.blockedMessages.clear();

	AkshatMessageSharedPrefHelper.updatePrefrences(akshatMessageCollateDb(), getApplicationContext());

	messageAd.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
	super.onCreateContextMenu(menu, v, menuInfo);
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.context_menu_message_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	switch (item.getItemId())
	{
	    case R.id.delete_message:
		deleteItemViaContextMenu(info.targetView);
		return true;
	    case R.id.register_white_list:
		doNotBlockInFuture(info);
	    default:
		return super.onContextItemSelected(item);
	}
    }

    void doNotBlockInFuture(AdapterContextMenuInfo info)
    {
	AkshatMessage whiteEntry = messageAd.blockedMessages.get(info.position);
	if (White_SharedredPrefrencesHelper.addToWhiteList(whiteEntry.getSmsFrom(), getApplicationContext()))
	{
	    Toast.makeText(getApplicationContext(), R.string.entry_added_to_white_list, Toast.LENGTH_LONG).show();
	}
	else
	{
	    Toast.makeText(getApplicationContext(), R.string.white_list_already_full, Toast.LENGTH_LONG).show();
	}
    }
}
