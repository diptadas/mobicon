package com.example.mobicon.customlist;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobicon.R;

public class ShopListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;

	public ShopListAdapter(Activity activity, ArrayList<HashMap<String, String>> data)
	{
		this.activity = activity;
		this.data = data;
	}

	@Override
	public int getCount()
	{
		return data.size();
	}

	@Override
	public Object getItem(int location)
	{
		return data.get(location);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.shop_list_row, null);

		TextView shop_name = (TextView) convertView.findViewById(R.id.textView1);
		TextView address = (TextView) convertView.findViewById(R.id.textView2);
		TextView division = (TextView) convertView.findViewById(R.id.textView3);

		HashMap<String, String> map = data.get(position);

		shop_name.setText(map.get("shop_name"));
		address.setText(map.get("address"));
		division.setText(map.get("division"));

		return convertView;
	}
}