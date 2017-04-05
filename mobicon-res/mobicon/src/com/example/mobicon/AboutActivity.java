package com.example.mobicon;

import android.os.Bundle;

public class AboutActivity extends MyActivity {

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		setHeader("About", true);

	}

}
