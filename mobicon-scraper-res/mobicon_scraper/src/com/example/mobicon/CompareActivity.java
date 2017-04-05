package com.example.mobicon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CompareActivity extends MyActivity {

	Button btnCompare, btnSrch_1, btnSrch_2;
	Button btnSelected_1, btnSelected_2; //fake buttons
	EditText editText_1, editText_2;

	CompareSearch objCompareSearchResult_1, objCompareSearchResult_2;

	String title_1 = null, title_2 = null;
	String mobile_id_1 = null, mobile_id_2 = null;
	int pos_1, pos_2;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		setHeader("Compare", true);

		btnCompare = (Button) findViewById(R.id.button3);
		btnSrch_1 = (Button) findViewById(R.id.button1);
		btnSrch_2 = (Button) findViewById(R.id.button2);
		editText_1 = (EditText) findViewById(R.id.editText1);
		editText_2 = (EditText) findViewById(R.id.editText2);

		btnSelected_1 = new Button(this);
		btnSelected_2 = new Button(this);

		btnCompare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				if (mobile_id_1 == null || mobile_id_2 == null || mobile_id_1.equals(mobile_id_2))
				{
					StaticValues.showToast(CompareActivity.this, "Model same or not found.");

				} else
				{
					Intent intent = new Intent(getApplicationContext(), CompareResultActivity.class);
					intent.putExtra("mobile_id_1", mobile_id_1);
					intent.putExtra("mobile_id_2", mobile_id_2);
					startActivity(intent);
				}
			}
		});

		btnSrch_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				objCompareSearchResult_1 = new CompareSearch(CompareActivity.this, editText_1.getText().toString(), btnSelected_1);

			}
		});

		btnSrch_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				objCompareSearchResult_2 = new CompareSearch(CompareActivity.this, editText_2.getText().toString(), btnSelected_2);

			}
		});

		btnSelected_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				pos_1 = objCompareSearchResult_1.objListDialog.pos;
				title_1 = objCompareSearchResult_1.data_list.get(pos_1).get("title");
				mobile_id_1 = objCompareSearchResult_1.data_list.get(pos_1).get("mobile_id");
				editText_1.setText(title_1);

			}
		});

		btnSelected_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				pos_2 = objCompareSearchResult_2.objListDialog.pos;
				title_2 = objCompareSearchResult_2.data_list.get(pos_2).get("title");
				mobile_id_2 = objCompareSearchResult_2.data_list.get(pos_2).get("mobile_id");
				editText_2.setText(title_2);

			}
		});

	}
}
