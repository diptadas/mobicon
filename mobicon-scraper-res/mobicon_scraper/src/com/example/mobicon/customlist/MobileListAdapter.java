package com.example.mobicon.customlist;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mobicon.R;
import com.example.mobicon.util.AppController;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MobileListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public MobileListAdapter(Activity activity, ArrayList<HashMap<String, String>> data)
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

		if (inflater == null) inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) convertView = inflater.inflate(R.layout.mobile_list_row, null);

		if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);

		TextView model = (TextView) convertView.findViewById(R.id.model);
		TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView price = (TextView) convertView.findViewById(R.id.price);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

		HashMap<String, String> map = data.get(position);

		String imageUrl = map.get("img_url");

		thumbNail.setImageUrl(imageUrl, imageLoader);

		model.setText(map.get("title"));
		rating.setText("Rating: " + map.get("rating"));
		price.setText("Price: " + map.get("price"));
		year.setText("Released: " + map.get("released"));

		return convertView;
	}

}