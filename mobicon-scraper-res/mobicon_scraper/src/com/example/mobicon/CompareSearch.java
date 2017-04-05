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

public class CompareSearch {

	List<NameValuePair> params = new ArrayList<NameValuePair>();

	ListView listView;
	Button btnListener; // fake button to listen when AsycTask is completed

	AsyncTaskQuery objAsyncTaskQuery;

	int success;
	ArrayList<HashMap<String, String>> data_list;

	ProgressDialog pDialog;

	ListDialog objListDialog;

	public CompareSearch(final Activity activity, String model, final Button btnSelected)
	{
		params.add(new BasicNameValuePair("model", model));

		btnListener = new Button(activity);

		//new SearchQuery(this, listView, params).execute();

		pDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_DARK);
		pDialog.setMessage("Loading data...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();

		objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.searchUrl, StaticValues.searchColumns, params, btnListener);

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
					if (data_list.size() == 0)
					{
						StaticValues.showToast(activity, "Sorry, no match found !!!");
						activity.finish();
					} else
					{
						ArrayList<String> model_list = new ArrayList<String>();

						for (HashMap<String, String> map : data_list)
						{
							model_list.add(map.get("title"));
						}
						objListDialog = new ListDialog(activity, model_list, btnSelected);
					}

				} else
				{
					Toast.makeText(activity, "Error Connecting Server", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

}
