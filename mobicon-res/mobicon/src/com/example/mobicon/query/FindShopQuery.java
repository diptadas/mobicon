package com.example.mobicon.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.widget.TextView;

import com.example.mobicon.MainActivity;
import com.example.mobicon.MapActivity;
import com.example.mobicon.customlist.ShopListAdapter;
import com.example.mobicon.util.JSONParser;

public class FindShopQuery extends AsyncTask<String, String, String> {

	int success;
	Activity activity;
	ProgressDialog pDialog;
	ListView listView;
	TextView textView;
	String division;
	ArrayList<HashMap<String, String>> data_list = new ArrayList<HashMap<String, String>>();
	String url = MainActivity.server + "find_shop.php";

	public FindShopQuery(final Activity activity, ListView listView, final TextView textView, String division)
	{
		this.activity = activity;
		this.listView = listView;
		this.textView = textView;
		this.division = division;

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3)
			{
				// TODO Auto-generated method stub

				Intent i = new Intent(activity, MapActivity.class);

				i.putExtra("shop_name", data_list.get(pos).get("shop_name"));
				i.putExtra("division", data_list.get(pos).get("division"));
				i.putExtra("lat", data_list.get(pos).get("lat"));
				i.putExtra("long", data_list.get(pos).get("long"));

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
		params.add(new BasicNameValuePair("division", division));

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

					map.put("shop_name", obj.getString("shop_name"));
					map.put("division", obj.getString("division"));
					map.put("address", obj.getString("address"));
					map.put("lat", obj.getString("lat"));
					map.put("long", obj.getString("long"));

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
			if (data_list.size() == 0)
			{
				MainActivity.showToast(activity, "Sorry, no match found !!!");
				activity.finish();
			}
			else
			{
				ShopListAdapter adapter = new ShopListAdapter(activity, data_list);
				listView.setAdapter(adapter);
				textView.setVisibility(View.VISIBLE);
			}

		}
		else
		{
			listView.setVisibility(0);
			MainActivity.showToast(activity, "Error connecting server.");
		}

	}

}
