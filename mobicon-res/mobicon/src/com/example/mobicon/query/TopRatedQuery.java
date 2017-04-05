package com.example.mobicon.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.mobicon.MainActivity;
import com.example.mobicon.SpecificationActivity;
import com.example.mobicon.customlist.MobileListAdapter;
import com.example.mobicon.util.JSONParser;

public class TopRatedQuery extends AsyncTask<String, String, String> {

	int success;
	Activity activity;
	ProgressDialog pDialog;
	ListView listView;
	ArrayList<HashMap<String, String>> data_list = new ArrayList<HashMap<String, String>>();
	String url = MainActivity.server + "top_rated.php";

	public TopRatedQuery(final Activity activity, ListView listView)
	{
		this.activity = activity;
		this.listView = listView;

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3)
			{
				// TODO Auto-generated method stub

				Intent i = new Intent(activity, SpecificationActivity.class);

				i.putExtra("mobile_id", data_list.get(pos).get("mobile_id"));
				i.putExtra("brand", data_list.get(pos).get("brand"));
				i.putExtra("model", data_list.get(pos).get("model"));
				i.putExtra("avg_rating", data_list.get(pos).get("avg_rating"));

				activity.startActivity(i);

			}
		});
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

					HashMap<String, String> map = new HashMap<String, String>();

					map.put("mobile_id", obj.getString("mobile_id"));
					map.put("brand", obj.getString("brand"));
					map.put("model", obj.getString("model"));
					map.put("price", obj.getString("price"));
					map.put("avg_rating", obj.getString("avg_rating"));

					data_list.add(map);

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
			MobileListAdapter adapter = new MobileListAdapter(activity, data_list);
			listView.setAdapter(adapter);

		}
		else
		{
			listView.setVisibility(0);
			MainActivity.showToast(activity, "Error connecting server.");
			activity.finish();
		}

	}

}
