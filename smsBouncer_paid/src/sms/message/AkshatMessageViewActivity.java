package sms.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import sms.shieldpro.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AkshatMessageViewActivity extends Activity
{
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        Intent orig = getIntent();
        Bundle bun = orig.getExtras();
        
        AkshatMessage akm = bun.getParcelable("message");
        
        if(akm == null)
        {
            this.finish();
        }
        
        setContentView(R.layout.message_view);
        
        TextView num;
	TextView body;
	TextView time;

	num = (TextView) findViewById(R.id.from);
	body = (TextView) findViewById(R.id.bodySnipet);
	time = (TextView) findViewById(R.id.time);

	num.setText(akm.getSmsFrom());
	body.setText(akm.getSmsBody());
	time.setText(sdf.format(new Date(akm.getSmsTimestamp())));
    }
}
