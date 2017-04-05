package com.example.mobicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

import com.example.mobicon.customlist.MobileListAdapter;
import com.example.mobicon.util.AsyncTaskQuery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TopRatedActivity extends MyActivity {

	List<NameValuePair> params = new ArrayList<NameValuePair>();

	ListView listView;
	Button btnListener; // fake button to listen when AsycTask is completed

	AsyncTaskQuery objAsyncTaskQuery;

	int success;
	ArrayList<HashMap<String, String>> data_list;

	ProgressDialog pDialog;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_list);
		setHeader("Top Rated", true);

		listView = (ListView) findViewById(R.id.listPhones);

		btnListener = new Button(this);

		//new TopRatedQuery(this, listView).execute();

		pDialog = new ProgressDialog(TopRatedActivity.this, ProgressDialog.THEME_HOLO_DARK);
		pDialog.setMessage("Loading data...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();

		objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.topRatedUrl, StaticValues.searchColumns, params, btnListener);

		btnListener.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				pDialog.dismiss();

				success = objAsyncTaskQuery.success;
				data_list = objAsyncTaskQuery.data_list;

				if (success == 1)
				{
					if (data_list.size() == 0)
					{
						StaticValues.showToast(TopRatedActivity.this, "Sorry, no match found !!!");
						TopRatedActivity.this.finish();
					} else
					{
						MobileListAdapter adapter = new MobileListAdapter(TopRatedActivity.this, data_list);
						listView.setAdapter(adapter);
					}

				} else
				{
					Toast.makeText(TopRatedActivity.this, "Error Connecting Server", Toast.LENGTH_LONG).show();
				}
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3)
			{
				// TODO Auto-generated method stub

				Intent i = new Intent(TopRatedActivity.this, SpecificationActivity.class);

				i.putExtra("title", data_list.get(pos).get("title"));
				i.putExtra("mobile_id", data_list.get(pos).get("mobile_id"));
				i.putExtra("rating", data_list.get(pos).get("rating"));
				i.putExtra("img_url", data_list.get(pos).get("img_url"));

				TopRatedActivity.this.startActivity(i);
			}
		});

	}

}
