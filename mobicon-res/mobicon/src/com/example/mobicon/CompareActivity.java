package com.example.mobicon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobicon.query.CompareListQuery;

public class CompareActivity extends MyActivity {

	Button btnCompare, btnSrch1, btnSrch2;
	EditText editText1, editText2;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		setHeader("Compare", true);

		btnCompare = (Button) findViewById(R.id.button3);
		btnSrch1 = (Button) findViewById(R.id.button1);
		btnSrch2 = (Button) findViewById(R.id.button2);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);

		btnCompare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				Intent intent = new Intent(getApplicationContext(), CompareResultActivity.class);
				intent.putExtra("model_1", editText1.getText().toString());
				intent.putExtra("model_2", editText2.getText().toString());
				startActivity(intent);

			}
		});

		btnSrch1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				new CompareListQuery(CompareActivity.this, editText1).execute();

			}
		});

		btnSrch2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				new CompareListQuery(CompareActivity.this, editText2).execute();

			}
		});

	}
}
