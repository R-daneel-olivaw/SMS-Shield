package sms.white;

import java.util.ArrayList;
import java.util.List;

import sms.shieldpro.R;
import sms.message.AkshatMessageListActivity;
import sms.shield.ContactsActivity.ContactsListActivity;
import sms.shieldpro.BlockedEntity;
import sms.shieldpro.Master_SharedPrefrenceHelper;
import sms.shieldpro.White_SharedredPrefrencesHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class WhiteListActivity extends ListActivity
{

    private static final String MAJOR_DELIMIT = "#~#";
    private static final String LOG_TAG = "SmsBouncer";

    EditText entry1;
    Button blockButton;
    Button clearButton;
    Button tempButton;

    String[] blockedDb;

    WhiteListAdapter listAdapter;
    ListView blockedListView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.white_regist_center);

	dataOperations();
	List<BlockedEntity> blockedEntities = inflateDb();

	entry1 = (EditText) findViewById(R.id.target1);
	blockButton = (Button) findViewById(R.id.RegisterButton);
	clearButton = (Button) findViewById(R.id.clearButton);

	blockButton.setOnClickListener(_blockButton);
	clearButton.setOnClickListener(_clearButton);

	blockedListView = getListView();
	listAdapter = new WhiteListAdapter(this, blockedEntities, _blockedListRemoveButton);
	blockedListView.setAdapter(listAdapter);

	/*
	 * tempButton = (Button) findViewById(R.id.buttonShow);
	 * tempButton.setOnClickListener(_showListner);
	 */
    }

    @Override
    protected void onResume()
    {
	// TODO Auto-generated method stub
	super.onResume();
	if (White_SharedredPrefrencesHelper.REFESH_REQUIRED)
	    refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.white_list_activity_op_menu, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId())
	{
	    case R.id.add_frm_contacts:
		startAddFrmContactsActivity();
		return true;
	}
	return super.onOptionsItemSelected(item);
    }

    private void startAddFrmContactsActivity()
    {
	startActivityForResult(new Intent(getApplicationContext(), ContactsListActivity.class), 56);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	if (resultCode == Activity.RESULT_OK)
	{
	    String to = data.getExtras().getString("CONTACT_NUMBER") + "";
	    entry1.setText(to);
	}
    }

    private void refresh()
    {
	Log.d(LOG_TAG, "Refresh");
	dataOperations();
	List<BlockedEntity> blockedEntities = inflateDb();
	listAdapter._listEntities = blockedEntities;
	listAdapter.notifyDataSetChanged();
	White_SharedredPrefrencesHelper.REFESH_REQUIRED = false;
    }

    void dataOperations()
    {
	// settings = getSharedPreferences(PREFS_NAME, 0);
	// if (settings == null)
	// {
	// }

	// blockedDb = settings.getString(MAJOR_KEY, "").split(MAJOR_DELIMIT);
	blockedDb = White_SharedredPrefrencesHelper.getBlockedListFromPref(this).split(MAJOR_DELIMIT);
	Log.d("DataUpdate", "dataOperations");

    }

    void gatherData()
    {

    }

    void updatePrefrences(String newData)
    {
	// SharedPreferences.Editor e = settings.edit();
	// e.putString(MAJOR_KEY, newData);
	// e.commit();

	White_SharedredPrefrencesHelper.updatePrefrences(newData, this);
    }

    List<BlockedEntity> inflateDb()
    {
	List<BlockedEntity> blockedEntities = new ArrayList<BlockedEntity>();

	// if (blockedDb[0] != null)
	for (String c : blockedDb)
	{
	    BlockedEntity buff = new BlockedEntity(c);
	    if (buff.getEntityIdentifier() != null && !buff.getEntityIdentifier().equalsIgnoreCase("null"))
		blockedEntities.add(buff);
	}

	return blockedEntities;
    }

    String collateDb()
    {
	String collatedDb = "";

	for (BlockedEntity c : listAdapter._listEntities)
	{
	    collatedDb += c.toString() + MAJOR_DELIMIT;
	}

	return collatedDb;
    }

    boolean isAlreadyContained(List<BlockedEntity> blockedEntities, String targ)
    {
	for (BlockedEntity e : blockedEntities)
	{
	    if (e.getEntityIdentifier().equals(targ))
	    {
		return true;
	    }
	}
	return false;
    }

    OnClickListener _blockButton = new OnClickListener()
    {

	@Override
	public void onClick(View v)
	{
	    if (entry1.getText().toString().length() >= 2)
	    {
		if (listAdapter._listEntities.size() < Master_SharedPrefrenceHelper.LIST_CAP)
		{
		    if (!isAlreadyContained(listAdapter._listEntities, entry1.getText().toString()))
		    {
			listAdapter._listEntities.add(new BlockedEntity(entry1.getText().toString(), 0, true));
			updatePrefrences(collateDb());
			dataOperations();

			listAdapter.notifyDataSetChanged();
		    }
		    else
		    {
			Toast.makeText(getApplicationContext(), R.string.duplicate_entry, Toast.LENGTH_LONG).show();
		    }
		}
		else
		{
		    Toast.makeText(getApplicationContext(), R.string.white_list_already_full, Toast.LENGTH_LONG).show();
		}
	    }
	    else
	    {
		Toast.makeText(getApplicationContext(), R.string.length_should_be_at_least_2, Toast.LENGTH_LONG).show();
	    }
	}
    };

    OnClickListener _clearButton = new OnClickListener()
    {
	@Override
	public void onClick(View v)
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(WhiteListActivity.this);
	    builder.setMessage(R.string.delete_all_entries).setCancelable(false)
		    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
		    {
			public void onClick(DialogInterface dialog, int id)
			{
			    listAdapter._listEntities.clear();
			    updatePrefrences("");
			    dataOperations();

			    listAdapter.notifyDataSetChanged();
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
	}
    };

    OnClickListener _blockedListRemoveButton = new OnClickListener()
    {

	@Override
	public void onClick(final View v)
	{
	    Log.d("DataUpdate", ((String) v.getTag()));
	    runOnUiThread(new Runnable()
	    {
		@Override
		public void run()
		{
		    AlertDialog.Builder builder = new AlertDialog.Builder(WhiteListActivity.this);
		    builder.setMessage(R.string.delete_entry).setCancelable(false)
			    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
			    {
				public void onClick(DialogInterface dialog, int id)
				{
				    listAdapter._listEntities.remove(Integer.parseInt((String) v.getTag()));

				    // listAdapter.blockedEntities.add(new
				    // BlockedEntity(entry1.getText().toString(),
				    // 0, true));
				    updatePrefrences(collateDb());
				    dataOperations();

				    listAdapter.notifyDataSetChanged();

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
		}
	    });

	}
    };

    OnClickListener _showListner = new OnClickListener()
    {

	@Override
	public void onClick(View v)
	{
	    WhiteListActivity.this.startActivity(new Intent(WhiteListActivity.this, AkshatMessageListActivity.class));
	}
    };
}