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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.mobicon.MainActivity;
import com.example.mobicon.R;
import com.example.mobicon.util.JSONParser;
import com.example.mobicon.util.LruBitmapCache;

public class CompareQuery extends AsyncTask<String, String, String> {

	int success;
	Activity activity;
	String model_1;
	String model_2;
	ProgressDialog pDialog;
	String[][] results = new String[5][20];
	String url = MainActivity.server + "compare.php";

	/**
	 * @param activity
	 * @param model_1
	 * @param model_2
	 */
	public CompareQuery(Activity activity, String model_1, String model_2)
	{
		this.activity = activity;
		this.model_1 = model_1;
		this.model_2 = model_2;
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
		params.add(new BasicNameValuePair("model_1", model_1));
		params.add(new BasicNameValuePair("model_2", model_2));

		try
		{
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);

			success = json.getInt("success");

			if (success == 1)
			{
				data = json.getJSONArray("data");

				if (data.length() == 2)
				{

					for (int i = 0; i < data.length(); i++)
					{
						JSONObject obj = data.getJSONObject(i);

						for (int j = 0; j < 18; j++)
						{
							results[i][j] = obj.getString("" + j);
						}
					}
				}
				else
				{
					success = -1;
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
			TextView txt_model_1 = (TextView) activity.findViewById(R.id.textView1);
			TextView txt_model_2 = (TextView) activity.findViewById(R.id.textView2);

			txt_model_1.setText(results[0][1] + " " + results[0][2]);
			txt_model_2.setText(results[1][1] + " " + results[1][2]);

			String url1 = MainActivity.server + "images/" + results[0][0] + ".jpg";
			String url2 = MainActivity.server + "images/" + results[1][0] + ".jpg";

			ImageLoader.ImageCache imageCache = new LruBitmapCache();
			ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(activity), imageCache);

			NetworkImageView imageView = (NetworkImageView) activity.findViewById(R.id.fullImage1);
			NetworkImageView imageView2 = (NetworkImageView) activity.findViewById(R.id.fullImage2);

			imageView.setImageUrl(url1, imageLoader);
			imageView2.setImageUrl(url2, imageLoader);

			LinearLayout layout = (LinearLayout) activity.findViewById(R.id.linearLayoutContainer);

			for (int i = 1; i < 18; i++)
			{
				View view = LayoutInflater.from(activity).inflate(R.layout.compare_row, null);

				TextView textView1 = (TextView) view.findViewById(R.id.textView1);
				TextView textView2 = (TextView) view.findViewById(R.id.textView2);
				TextView textView3 = (TextView) view.findViewById(R.id.textView3);

				textView1.setText(String.valueOf(MainActivity.mobile_info_column[i]));
				textView2.setText(results[0][i]);
				textView3.setText(results[1][i]);

				layout.addView(view);
			}

		}
		else if (success == -1)
		{
			MainActivity.showToast(activity, "Model same or not found.");
			activity.finish();
		}
		else
		{
			MainActivity.showToast(activity, "Error connecting server.");
			activity.finish();
		}

	}
}
