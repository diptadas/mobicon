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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobicon.MainActivity;
import com.example.mobicon.R;
import com.example.mobicon.util.JSONParser;

public class SpecificationQuery extends AsyncTask<String, String, String> {

	int success;
	Activity activity;
	String mobile_id;
	ProgressDialog pDialog;
	String[] results = new String[20];
	String url = MainActivity.server + "specification.php";

	public SpecificationQuery(Activity activity, String mobile_id)
	{
		this.activity = activity;
		this.mobile_id = mobile_id;
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
		params.add(new BasicNameValuePair("mobile_id", mobile_id));

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

					for (int j = 0; j < 18; j++)
					{
						results[j] = obj.getString("" + j);
					}
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
			LinearLayout layout = (LinearLayout) activity.findViewById(R.id.linearLayoutContainer);

			for (int i = 1; i < 18; i++)
			{
				View view = LayoutInflater.from(activity).inflate(R.layout.specification_row, null);

				TextView textView1 = (TextView) view.findViewById(R.id.textView1);
				TextView textView2 = (TextView) view.findViewById(R.id.textView2);

				textView1.setText(String.valueOf(MainActivity.mobile_info_column[i]));
				textView2.setText(String.valueOf(results[i]));

				layout.addView(view);
			}

		}
		else
		{
			MainActivity.showToast(activity, "Error connecting server.");
			activity.finish();
		}

	}
}
