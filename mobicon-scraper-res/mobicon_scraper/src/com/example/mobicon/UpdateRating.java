package com.example.mobicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.mobicon.customdialog.ListDialog;
import com.example.mobicon.util.AsyncTaskQuery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class UpdateRating {

	List<NameValuePair> params = new ArrayList<NameValuePair>();

	ListView listView;
	Button btnListener; // fake button to listen when AsycTask is completed

	AsyncTaskQuery objAsyncTaskQuery;

	int success;
	ArrayList<HashMap<String, String>> data_list;

	ProgressDialog pDialog;

	ListDialog objListDialog;

	public UpdateRating(final Activity activity, String mobile_id, String rating)
	{
		params.add(new BasicNameValuePair("user_name", StaticValues.user_name));
		params.add(new BasicNameValuePair("mobile_id", mobile_id));
		params.add(new BasicNameValuePair("rating", rating));

		btnListener = new Button(activity);

		pDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_DARK);
		pDialog.setMessage("Loading data...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();

		objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.giveRatingUrl, new ArrayList<String>(), params, btnListener);

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
					StaticValues.showToast(activity, "Thanks for rating.");

				} else
				{
					Toast.makeText(activity, "Error Connecting Server", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

}
