package com.example.mobicon;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobicon.query.CompareQuery;

public class CompareResultActivity extends MyActivity {

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare_result);

		setHeader("Compare", true);

		Intent intent = getIntent();

		String model_1 = intent.getExtras().getString("model_1");
		String model_2 = intent.getExtras().getString("model_2");

		new CompareQuery(this, model_1, model_2).execute();

	}
}
