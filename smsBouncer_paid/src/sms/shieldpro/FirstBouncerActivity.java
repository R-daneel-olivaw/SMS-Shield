package sms.shieldpro;

import sms.shieldpro.R;
import sms.white.WhiteListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

public class FirstBouncerActivity extends TabActivity
{

    EditText entry1;
    Button blockButton;
    Button clearButton;

    String[] blockedDb;

    BlockedListAdapter listAdapter;
    ListView blockedListView;

    private static final String BLACK_LIST_ID = "black";
    private static final String WHITE_LIST_ID = "white";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.first);

	TabHost tabHost = getTabHost();
	TabHost.TabSpec spec;
	Intent intent;

	intent = new Intent(this, BouncerListActivity.class);
	spec = tabHost.newTabSpec(BLACK_LIST_ID)
		.setIndicator(getString(R.string.black_list), getResources().getDrawable(R.drawable.ic_action_delete_5)).setContent(intent);
	tabHost.addTab(spec);

	intent = new Intent(this, WhiteListActivity.class);
	spec = tabHost.newTabSpec(WHITE_LIST_ID).setIndicator(getString(R.string.white_list), getResources().getDrawable(R.drawable.the_good))
		.setContent(intent);
	tabHost.addTab(spec);

	intent = new Intent(this, SmsPrefrenceActivity.class);
	spec = tabHost.newTabSpec(WHITE_LIST_ID)
		.setIndicator(getString(R.string.control), getResources().getDrawable(R.drawable.control_tab)).setContent(intent);
	tabHost.addTab(spec);

	// tabHost.setOnTabChangedListener(tabListner);

	// dataOperations();
	// List<BlockedEntity> blockedEntities = inflateDb();
	//
	// entry1 = (EditText) findViewById(R.id.target1);
	// blockButton = (Button) findViewById(R.id.RegisterButton);
	// clearButton = (Button) findViewById(R.id.clearButton);
	//
	// blockButton.setOnClickListener(_blockButton);
	// clearButton.setOnClickListener(_clearButton);
	//
	// blockedListView = (ListView) findViewById(R.id.blockedList);
	// listAdapter = new BlockedListAdapter(this, blockedEntities,
	// _blockedListRemoveButton);
	// blockedListView.setAdapter(listAdapter);
    }

    // void dataOperations()
    // {
    // // settings = getSharedPreferences(PREFS_NAME, 0);
    // // if (settings == null)
    // // {
    // // }
    //
    // // blockedDb = settings.getString(MAJOR_KEY, "").split(MAJOR_DELIMIT);
    // blockedDb =
    // SharedredPrefrencesHelper.getBlockedListFromPref(this).split(MAJOR_DELIMIT);
    // Log.d("DataUpdate", "dataOperations");
    //
    // }
    //
    // void gatherData()
    // {
    //
    // }
    //
    // void updatePrefrences(String newData)
    // {
    // // SharedPreferences.Editor e = settings.edit();
    // // e.putString(MAJOR_KEY, newData);
    // // e.commit();
    //
    // SharedredPrefrencesHelper.updatePrefrences(newData, this);
    // }
    //
    // List<BlockedEntity> inflateDb()
    // {
    // List<BlockedEntity> blockedEntities = new ArrayList<BlockedEntity>();
    //
    // // if (blockedDb[0] != null)
    // for (String c : blockedDb)
    // {
    // BlockedEntity buff = new BlockedEntity(c);
    // if (buff.getEntityIdentifier() != null &&
    // !buff.getEntityIdentifier().equalsIgnoreCase("null"))
    // blockedEntities.add(buff);
    // }
    //
    // return blockedEntities;
    // }
    //
    // String collateDb()
    // {
    // String collatedDb = "";
    //
    // for (BlockedEntity c : listAdapter.blockedEntities)
    // {
    // collatedDb += c.toString() + MAJOR_DELIMIT;
    // }
    //
    // return collatedDb;
    // }
    //
    // OnClickListener _blockButton = new OnClickListener()
    // {
    //
    // @Override
    // public void onClick(View v)
    // {
    // listAdapter.blockedEntities.add(new
    // BlockedEntity(entry1.getText().toString(), 0, true));
    // updatePrefrences(collateDb());
    // dataOperations();
    //
    // listAdapter.notifyDataSetChanged();
    // }
    // };
    //
    // OnClickListener _clearButton = new OnClickListener()
    // {
    // @Override
    // public void onClick(View v)
    // {
    // listAdapter.blockedEntities.clear();
    // updatePrefrences("");
    // dataOperations();
    //
    // listAdapter.notifyDataSetChanged();
    // }
    // };
    //
    // OnClickListener _blockedListRemoveButton = new OnClickListener()
    // {
    //
    // @Override
    // public void onClick(final View v)
    // {
    // Log.d("DataUpdate", ((String) v.getTag()));
    // runOnUiThread(new Runnable()
    // {
    // @Override
    // public void run()
    // {
    // listAdapter.blockedEntities.remove(Integer.parseInt((String)
    // v.getTag()));
    // listAdapter.notifyDataSetChanged();
    // }
    // });
    //
    // }
    // };

    OnTabChangeListener tabListner = new OnTabChangeListener()
    {

	@Override
	public void onTabChanged(String tabId)
	{

	    if (tabId.equals(BLACK_LIST_ID))
	    {
		TabHost bufferHost = getTabHost();
		TabWidget tabW = bufferHost.getTabWidget();

		Log.w("SMS_Bouncer", tabW.getChildAt(0).toString());

	    }
	    else if (tabId.equals(WHITE_LIST_ID))
	    {

	    }
	    else
	    {
		Log.w("SMS_Bouncer", tabId + " is unkown tab id");
	    }
	    setTabColor(getTabHost());
	}
    };

    public void setTabColor(TabHost tabhost)
    {
	// Bitmap bmp_light = BitmapFactory.decodeResource(getResources(),
	// R.drawable.imagestab_selected);
	// BitmapDrawable bitmapDrawable_light = new BitmapDrawable(bmp_light);
	// Bitmap bmp_dark = BitmapFactory.decodeResource(getResources(),
	// R.drawable.toolbar_background);
	// BitmapDrawable bitmapDrawable_dark = new BitmapDrawable(bmp_dark);
	// bitmapDrawable_dark.setTileModeXY(Shader.TileMode.REPEAT,
	// Shader.TileMode.REPEAT);
	for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++)
	{
	    tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.CYAN);
	    TextView tv = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	    tv.setTextColor(Color.MAGENTA);

	}
	tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
		.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_borderless_selected)); // selected
	TextView tv = (TextView) tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).findViewById(android.R.id.title);
	tv.setTextColor(Color.BLACK);
    }
}