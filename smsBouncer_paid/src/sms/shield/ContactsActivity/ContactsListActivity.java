package sms.shield.ContactsActivity;

import sms.shieldpro.R;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ContactsListActivity extends ListActivity implements OnItemClickListener
{
    String[] from;
    int[] to;
    SimpleCursorAdapter adapter;

    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.contacts_list);

	Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
		new String[] { Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, null);

	startManagingCursor(cursor);

	from = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER };

	to = new int[] { R.id.name_entry, R.id.number_entry };

	adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, from, to);
	this.setListAdapter(adapter);

	getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
	Intent result = new Intent();
	Bundle bundle = new Bundle();

	TextView itemView = (TextView) arg1.findViewById(R.id.number_entry);

	bundle.putString("CONTACT_NAME", "");
	bundle.putString("CONTACT_NUMBER", itemView.getText().toString().replaceAll("-", ""));

	result.putExtras(bundle);

	setResult(Activity.RESULT_OK, result);
	finish();
    }

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
}
