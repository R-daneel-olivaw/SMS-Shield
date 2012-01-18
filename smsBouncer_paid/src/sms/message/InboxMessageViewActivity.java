package sms.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import sms.shieldpro.R;
import sms.shieldpro.Black_SharedredPrefrencesHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InboxMessageViewActivity extends Activity
{
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
    AkshatMessage akm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	Intent orig = getIntent();
	Bundle bun = orig.getExtras();

	akm = bun.getParcelable("message");

	if (akm == null)
	{
	    this.finish();
	}

	setContentView(R.layout.inbox_message_view);

	TextView num;
	TextView body;
	TextView time;

	num = (TextView) findViewById(R.id.from);
	body = (TextView) findViewById(R.id.bodySnipet);
	time = (TextView) findViewById(R.id.time);

	num.setText(akm.getSmsFrom());
	body.setText(akm.getSmsBody());
	time.setText(sdf.format(new Date(akm.getSmsTimestamp())));

	Button block = (Button) findViewById(R.id.button1);
	block.setOnClickListener(new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		addToBlackList();
	    }
	});
    }

    void addToBlackList()
    {
	if (Black_SharedredPrefrencesHelper.addToBlackList(akm.getSmsFrom(), getApplicationContext()))
	{
	    Toast.makeText(getApplicationContext(), R.string.entry_added_to_black_list, Toast.LENGTH_LONG).show();
	}
	else
	{
	    Toast.makeText(getApplicationContext(), R.string.black_list_already_full, Toast.LENGTH_LONG).show();
	}
    }
}
