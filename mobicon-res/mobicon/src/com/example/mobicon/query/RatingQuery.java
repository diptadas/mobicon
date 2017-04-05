package com.example.mobicon.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.mobicon.MainActivity;
import com.example.mobicon.util.JSONParser;

public class RatingQuery extends AsyncTask<String, String, String> {

	int success;
	int data;
	String mobile_id, rating;
	Activity activity;
	ProgressDialog pDialog;
	String url = MainActivity.server + "rating.php";

	public RatingQuery(Activity activity, String mobile_id, String rating)
	{
		this.activity = activity;
		this.mobile_id = mobile_id;
		this.rating = rating;
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		pDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_DARK);
		pDialog.setMessage("Loading data...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}

	protected String doInBackground(String... args)
	{
		JSONParser jParser = new JSONParser();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_id", MainActivity.user_id));
		params.add(new BasicNameValuePair("mobile_id", mobile_id));
		params.add(new BasicNameValuePair("rating", rating));

		try
		{
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);

			success = json.getInt("success");

		} catch (Exception e)
		{
			e.printStackTrace();
			success = 0;
		}

		return null;
	}

	protected void onPostExecute(String file_url)
	{
		pDialog.dismiss();

		if (success == 1)
		{
			MainActivity.showToast(activity, "Thanks for rating.");
		}
		else
		{
			MainActivity.showToast(activity, "Error connecting server.");
		}

	}

}
