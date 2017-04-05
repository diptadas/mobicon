package com.example.mobicon;

import android.os.Bundle;
import android.widget.ListView;

import com.example.mobicon.query.TopRatedQuery;

public class TopRatedActivity extends MyActivity {

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_list);
		setHeader("Top Rated", true);

		ListView listView = (ListView) findViewById(R.id.listPhones);
		new TopRatedQuery(this, listView).execute();

	}

}
