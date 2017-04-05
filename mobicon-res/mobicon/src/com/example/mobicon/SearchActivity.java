package com.example.mobicon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends MyActivity {

	EditText editText[] = new EditText[10];
	Spinner txtBrand, txtOS;
	String input[] = new String[10];

	Button btnSearch;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_arrow);
		setHeader("Search", true);

		btnSearch = (Button) findViewById(R.id.btnSearch);

		txtBrand = (Spinner) findViewById(R.id.txtBrand);
		editText[1] = (EditText) findViewById(R.id.txtModel);
		editText[2] = (EditText) findViewById(R.id.txtStartPrice);
		editText[3] = (EditText) findViewById(R.id.txtEndPrice);
		txtOS = (Spinner) findViewById(R.id.txtOS);
		editText[5] = (EditText) findViewById(R.id.txtRam);
		editText[6] = (EditText) findViewById(R.id.txtRearCamera);
		editText[7] = (EditText) findViewById(R.id.txtFrontCamera);
		editText[8] = (EditText) findViewById(R.id.txtBattery);

		List<String> brand_list = new ArrayList<String>(Arrays.asList(MainActivity.brand_list));
		List<String> os_list = new ArrayList<String>(Arrays.asList(MainActivity.os_list));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brand_list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		txtBrand.setAdapter(dataAdapter);

		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, os_list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		txtOS.setAdapter(dataAdapter);

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				for (int i = 0; i < 9; i++)
				{
					if (i == 0)
					{
						input[i] = txtBrand.getSelectedItem().toString().trim();
						if (input[i].equals(MainActivity.brand_list[0])) input[i] = "";
					}
					else if (i == 4)
					{
						input[i] = txtOS.getSelectedItem().toString().trim();
						if (input[i].equals(MainActivity.os_list[0])) input[i] = "";
					}
					else
						input[i] = editText[i].getText().toString().trim();
				}

				Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
				intent.putExtra("key", input);
				startActivity(intent);

			}
		});

	}
}
