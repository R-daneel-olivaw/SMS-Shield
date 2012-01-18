package sms.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sms.shieldpro.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AkshatMessageAdapter extends BaseAdapter
{
    public List<AkshatMessage> blockedMessages;
    Context _context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");

    // OnLongClickListener removeItemButton;
    // OnClickListener openListner;

    public AkshatMessageAdapter(Context context, List<AkshatMessage> akshatMessages)
    {
	_context = context;
	blockedMessages = akshatMessages;
	// removeItemButton = itemListner;
	// this.openListner = openListner;
    }

    @Override
    public int getCount()
    {
	// TODO Auto-generated method stub
	return blockedMessages.size();
    }

    @Override
    public Object getItem(int position)
    {
	// TODO Auto-generated method stub
	return blockedMessages.get(position);
    }

    @Override
    public long getItemId(int position)
    {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
	if (convertView == null)
	{
	    convertView = View.inflate(_context, R.layout.message_list_item, null);
	}

	TextView num;
	TextView body;
	TextView time;

	num = (TextView) convertView.findViewById(R.id.from);
	body = (TextView) convertView.findViewById(R.id.bodySnipet);
	time = (TextView) convertView.findViewById(R.id.time);

	num.setText(blockedMessages.get(position).getSmsFrom());
	body.setText(blockedMessages.get(position).getSmsBody());
	time.setText(sdf.format(new Date(blockedMessages.get(position).getSmsTimestamp())));

	convertView.setTag("" + position);
	// convertView.setOnLongClickListener(removeItemButton);
	// convertView.setOnClickListener(openListner);

	// convertView.setClickable(true);
	// convertView.setLongClickable(true);

	return convertView;
    }

}
