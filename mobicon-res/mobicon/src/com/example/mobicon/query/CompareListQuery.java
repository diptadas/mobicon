package com.example.mobicon.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;

import com.example.mobicon.MainActivity;
import com.example.mobicon.customdialog.ListDialog;
import com.example.mobicon.util.JSONParser;

public class CompareListQuery extends AsyncTask<String, String, String> {

	int success;
	Activity activity;
	EditText editText;
	ProgressDialog pDialog;
	String model;
	ArrayList<String> data_list = new ArrayList<String>();

	String url = MainActivity.server + "compare_list.php";

	public CompareListQuery(final Activity activity, EditText editText)
	{
		this.activity = activity;
		model = editText.getText().toString();
		this.editText = editText;

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
		JSONArray data = null;

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("model", model));

		try
		{
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);

			success = json.getInt("success");

			if (success == 1)
			{
				data = json.getJSONArray("data");

				for (int i = 0; i < data.length(); i++)
				{
					JSONObject obj = data.getJSONObject(i);
					data_list.add(obj.getString("model"));

				}
			}

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
			if (data_list.size() == 0)
			{
				MainActivity.showToast(activity, "Sorry, no match found !!!");
			}
			else
			{
				new ListDialog(activity, data_list, editText);
			}

		}
		else
		{
			MainActivity.showToast(activity, "Error connecting server.");
		}

	}

}
