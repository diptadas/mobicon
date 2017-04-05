package com.example.mobicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.mobicon.customdialog.RatingDialog;
import com.example.mobicon.util.AsyncTaskQuery;
import com.example.mobicon.util.LruBitmapCache;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class SpecificationActivity extends MyActivity {

	List<NameValuePair> params = new ArrayList<NameValuePair>();

	ListView listView;

	Button btnListener; // fake button to listen when AsycTask is completed
	Button btnRatingSelected; // fake button to listen rating selection

	AsyncTaskQuery objAsyncTaskQuery;
	RatingDialog objRatingDialog;

	int success;
	ArrayList<HashMap<String, String>> data_list;

	ProgressDialog pDialog;

	Button btn_rate;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specification);

		btn_rate = (Button) findViewById(R.id.button1);

		Intent intent = getIntent();

		String title = intent.getExtras().getString("title");
		final String mobile_id = intent.getExtras().getString("mobile_id");
		String rating = intent.getExtras().getString("rating");
		String img_url = intent.getExtras().getString("img_url");

		setHeader(title, true);

		RatingBar bar = (RatingBar) findViewById(R.id.ratingBar1);
		bar.setRating(Float.parseFloat(rating));

		ImageLoader.ImageCache imageCache = new LruBitmapCache();
		ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getApplicationContext()), imageCache);

		NetworkImageView imageView = (NetworkImageView) findViewById(R.id.fullImage);
		imageView.setImageUrl(img_url, imageLoader);

		btnListener = new Button(this);
		btnRatingSelected = new Button(this);

		params.add(new BasicNameValuePair("mobile_id", mobile_id));

		pDialog = new ProgressDialog(SpecificationActivity.this, ProgressDialog.THEME_HOLO_DARK);
		pDialog.setMessage("Loading data...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();

		objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.specUrl, StaticValues.specColumns, params, btnListener);

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
					HashMap<String, String> map = data_list.get(0);

					// data corrupted
					if (!map.get("mobile_id").equals(mobile_id))
					{
						StaticValues.showToast(SpecificationActivity.this, "Error connecting server.");
						SpecificationActivity.this.finish();
						return;
					}

					LinearLayout layout = (LinearLayout) SpecificationActivity.this.findViewById(R.id.linearLayoutContainer);

					for (int i = 4; i < StaticValues.specColumns.size(); i++)
					{
						View view = LayoutInflater.from(SpecificationActivity.this).inflate(R.layout.specification_row, null);

						TextView textView1 = (TextView) view.findViewById(R.id.textView1);
						TextView textView2 = (TextView) view.findViewById(R.id.textView2);

						String columnName = StaticValues.specColumns.get(i);

						// capitalize 1st letter
						textView1.setText(columnName.substring(0, 1).toUpperCase() + columnName.substring(1));

						textView2.setText(map.get(columnName));

						layout.addView(view);
					}

				} else
				{
					StaticValues.showToast(SpecificationActivity.this, "Error connecting server.");
					SpecificationActivity.this.finish();
				}
			}
		});

		//new SpecificationQuery(this, name).execute();

		btn_rate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				if (StaticValues.user_name.equals("NULL"))
				{
					showLoginAlert();
				} else
				{
					objRatingDialog = new RatingDialog(SpecificationActivity.this);
				}
			}
		});

		btnRatingSelected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				new UpdateRating(SpecificationActivity.this, mobile_id, objRatingDialog.rating);
			}
		});

	}

	@SuppressLint("NewApi")
	public void showLoginAlert()
	{
		new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK).setTitle("Log in").setMessage("Please log in to give rating.").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);

				dialog.dismiss();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

}
