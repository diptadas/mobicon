package com.example.mobicon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobicon.query.FindShopQuery;

public class FindShopActivity extends MyActivity {

	Button btnSrch;
	Spinner spinner;
	ListView listView;
	TextView textView;

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

		textView.setVisibility(View.INVISIBLE);

		List<String> division_list = new ArrayList<String>(Arrays.asList(MainActivity.division_list));
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, division_list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		btnSrch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				String division = spinner.getSelectedItem().toString().trim();
				if (division.equals(MainActivity.division_list[0])) division = "";

				new FindShopQuery(FindShopActivity.this, listView, textView, division).execute();

			}
		});

	}
}
