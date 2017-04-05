package com.example.mobicon;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.widget.ListView;

import com.example.mobicon.query.SearchQuery;

public class SearchResultActivity extends MyActivity {

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_list);
		setHeader("Search Results", true);

		String input[] = getIntent().getExtras().getStringArray("key");

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("brand", input[0]));
		params.add(new BasicNameValuePair("model", input[1]));
		params.add(new BasicNameValuePair("start_price", input[2]));
		params.add(new BasicNameValuePair("end_price", input[3]));
		params.add(new BasicNameValuePair("os", input[4]));
		params.add(new BasicNameValuePair("ram", input[5]));
		params.add(new BasicNameValuePair("rear_camera", input[6]));
		params.add(new BasicNameValuePair("front_camera", input[7]));
		params.add(new BasicNameValuePair("battery", input[8]));

		ListView listView = (ListView) findViewById(R.id.listPhones);
		new SearchQuery(this, listView, params).execute();
	}
}
