package com.example.mobicon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.mobicon.customlist.ShopListAdapter;
import com.example.mobicon.util.AsyncTaskQuery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class FindShopActivity extends MyActivity {

	Button btnSrch;
	Spinner spinner;
	TextView textView;

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
		setContentView(R.layout.activity_find_shop);
		setHeader("Find Shop", true);

		btnSrch = (Button) findViewById(R.id.button1);
		spinner = (Spinner) findViewById(R.id.spinner1);
		listView = (ListView) findViewById(R.id.listShops);
		textView = (TextView) findViewById(R.id.textView1);

		btnListener = new Button(this);

		textView.setVisibility(View.INVISIBLE);

		List<String> division_list = new ArrayList<String>(Arrays.asList(StaticValues.division_list));
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, division_list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		btnSrch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				String division = spinner.getSelectedItem().toString().trim();
				if (division.equals(StaticValues.division_list[0])) division = "";

				//new FindShopQuery(FindShopActivity.this, listView, textView, division).execute();

				params.add(new BasicNameValuePair("division", division));

				pDialog = new ProgressDialog(FindShopActivity.this, ProgressDialog.THEME_HOLO_DARK);
				pDialog.setMessage("Loading data...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();

				objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.findShopUrl, StaticValues.shopColumns, params, btnListener);

			}
		});

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
						StaticValues.showToast(FindShopActivity.this, "Sorry, no match found !!!");
						FindShopActivity.this.finish();
					} else
					{
						ShopListAdapter adapter = new ShopListAdapter(FindShopActivity.this, data_list);
						listView.setAdapter(adapter);
						textView.setVisibility(View.VISIBLE);
					}

				} else
				{
					listView.setVisibility(0);
					StaticValues.showToast(FindShopActivity.this, "Error connecting server.");
				}
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3)
			{
				// TODO Auto-generated method stub

				Intent i = new Intent(FindShopActivity.this, MapActivity.class);

				i.putExtra("shop_name", data_list.get(pos).get("shop_name"));
				i.putExtra("division", data_list.get(pos).get("division"));
				i.putExtra("lat", data_list.get(pos).get("lat"));
				i.putExtra("long", data_list.get(pos).get("long"));

				FindShopActivity.this.startActivity(i);

			}
		});

	}
}
