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

	EditText txtModel;
	Spinner txtBrand, txtOS, txtPrice;
	String input[] = new String[10];

	Button btnSearch;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setHeader("Search", true);

		btnSearch = (Button) findViewById(R.id.btnSearch);

		txtModel = (EditText) findViewById(R.id.txtModel);
		txtBrand = (Spinner) findViewById(R.id.txtBrand);
		txtOS = (Spinner) findViewById(R.id.txtOS);
		txtPrice = (Spinner) findViewById(R.id.txtPrice);

		List<String> brand_list = new ArrayList<String>(Arrays.asList(StaticValues.brand_list));
		List<String> os_list = new ArrayList<String>(Arrays.asList(StaticValues.os_list));
		List<String> price_list = new ArrayList<String>(Arrays.asList(StaticValues.price_list));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brand_list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		txtBrand.setAdapter(dataAdapter);

		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, os_list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		txtOS.setAdapter(dataAdapter);

		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, price_list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		txtPrice.setAdapter(dataAdapter);

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				String model = txtModel.getText().toString().trim();
				String brand = txtBrand.getSelectedItem().toString().trim();
				String os = txtOS.getSelectedItem().toString().trim();
				String price = txtPrice.getSelectedItem().toString().trim();

				Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
				intent.putExtra("model", model);
				intent.putExtra("brand", brand);
				intent.putExtra("os", os);
				intent.putExtra("price", price);
				startActivity(intent);

			}
		});

	}
}
