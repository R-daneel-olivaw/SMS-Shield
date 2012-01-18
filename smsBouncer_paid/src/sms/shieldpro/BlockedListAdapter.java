package sms.shieldpro;

import java.util.List;

import sms.shieldpro.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BlockedListAdapter extends BaseAdapter
{
    public List<BlockedEntity> _listEntities;
    Context _context;

    OnClickListener removeItemButton;

    public BlockedListAdapter(Context context, List<BlockedEntity> blockedEntities, OnClickListener itemListner)
    {
	_context = context;
	this._listEntities = blockedEntities;
	removeItemButton = itemListner;
    }

    @Override
    public int getCount()
    {
	return _listEntities.size();
    }

    @Override
    public Object getItem(int position)
    {
	return _listEntities.get(position);
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
	    convertView = View.inflate(_context, R.layout.blocked_list_item, null);
	}

	TextView num;
	TextView count;
	ImageView icon;

	num = (TextView) convertView.findViewById(R.id.number);
	count = (TextView) convertView.findViewById(R.id.blockCount);
	icon = (ImageView) convertView.findViewById(R.id.blockedStatus);

	num.setText(_listEntities.get(position).getEntityIdentifier());
	count.setText(_listEntities.get(position).getBlockedCount() + "");

	icon.setTag("" + position);
	icon.setOnClickListener(removeItemButton);

	return convertView;
    }
}
