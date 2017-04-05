package com.example.mobicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.mobicon.util.AsyncTaskQuery;
import com.example.mobicon.util.LruBitmapCache;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompareResultActivity extends MyActivity {

	List<NameValuePair> params_1 = new ArrayList<NameValuePair>();
	List<NameValuePair> params_2 = new ArrayList<NameValuePair>();

	Button btnListener_1, btnListener_2; // fake button to listen when AsycTask is completed

	AsyncTaskQuery objAsyncTaskQuery;

	int success_1, success_2;
	ArrayList<HashMap<String, String>> data_list_1, data_list_2;

	ProgressDialog pDialog;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare_result);

		setHeader("Compare", true);

		Intent intent = getIntent();

		final String mobile_id_1 = intent.getExtras().getString("mobile_id_1");
		final String mobile_id_2 = intent.getExtras().getString("mobile_id_2");

		//new CompareQuery(this, model_1, model_2).execute();

		params_1.add(new BasicNameValuePair("mobile_id", mobile_id_1));
		params_2.add(new BasicNameValuePair("mobile_id", mobile_id_2));

		pDialog = new ProgressDialog(CompareResultActivity.this, ProgressDialog.THEME_HOLO_DARK);
		pDialog.setMessage("Loading data...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();

		btnListener_1 = new Button(this);
		btnListener_2 = new Button(this);

		objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.specUrl, StaticValues.specColumns, params_1, btnListener_1);

		btnListener_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				//pDialog.dismiss();

				success_1 = objAsyncTaskQuery.success;
				data_list_1 = objAsyncTaskQuery.data_list;

				if (success_1 == 1)
				{
					objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.specUrl, StaticValues.specColumns, params_2, btnListener_2);

				} else
				{
					StaticValues.showToast(CompareResultActivity.this, "Error connecting server.");
					CompareResultActivity.this.finish();
				}
			}
		});

		btnListener_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				pDialog.dismiss();

				success_2 = objAsyncTaskQuery.success;
				data_list_2 = objAsyncTaskQuery.data_list;

				if (success_2 == 1)
				{
					HashMap<String, String> map_1 = data_list_1.get(0);
					HashMap<String, String> map_2 = data_list_2.get(0);

					// data corrupted
					if (!map_1.get("mobile_id").equals(mobile_id_1) || !map_2.get("mobile_id").equals(mobile_id_2))
					{
						StaticValues.showToast(CompareResultActivity.this, "Error connecting server.");
						CompareResultActivity.this.finish();
						return;
					}

					TextView txt_model_1 = (TextView) CompareResultActivity.this.findViewById(R.id.textView1);
					TextView txt_model_2 = (TextView) CompareResultActivity.this.findViewById(R.id.textView2);

					txt_model_1.setText(map_1.get("title"));
					txt_model_2.setText(map_2.get("title"));

					String url1 = map_1.get("img_url");
					String url2 = map_2.get("img_url");

					ImageLoader.ImageCache imageCache = new LruBitmapCache();
					ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(CompareResultActivity.this), imageCache);

					NetworkImageView imageView = (NetworkImageView) CompareResultActivity.this.findViewById(R.id.fullImage1);
					NetworkImageView imageView2 = (NetworkImageView) CompareResultActivity.this.findViewById(R.id.fullImage2);

					imageView.setImageUrl(url1, imageLoader);
					imageView2.setImageUrl(url2, imageLoader);

					LinearLayout layout = (LinearLayout) CompareResultActivity.this.findViewById(R.id.linearLayoutContainer);

					for (int i = 4; i < StaticValues.specColumns.size(); i++)
					{
						View view = LayoutInflater.from(CompareResultActivity.this).inflate(R.layout.compare_row, null);

						TextView textView1 = (TextView) view.findViewById(R.id.textView1);
						TextView textView2 = (TextView) view.findViewById(R.id.textView2);
						TextView textView3 = (TextView) view.findViewById(R.id.textView3);

						String columnName = StaticValues.specColumns.get(i);

						// capitalize 1st letter
						textView1.setText(columnName.substring(0, 1).toUpperCase() + columnName.substring(1));

						textView2.setText(map_1.get(columnName));
						textView3.setText(map_2.get(columnName));

						layout.addView(view);
					}

				} else
				{
					StaticValues.showToast(CompareResultActivity.this, "Error connecting server.");
					CompareResultActivity.this.finish();
				}
			}
		});

	}
}
