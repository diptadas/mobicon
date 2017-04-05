package com.example.mobicon.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.CheckBox;

import com.example.mobicon.MainActivity;
import com.example.mobicon.R;
import com.example.mobicon.util.JSONParser;

public class CheckPasswordQuery extends AsyncTask<String, String, String> {

	int success;
	String username, password, data, user_id;
	Activity activity;
	ProgressDialog pDialog;
	String url = MainActivity.server + "check_password.php";

	public CheckPasswordQuery(Activity activity, String username, String password)
	{
		this.activity = activity;
		this.username = username;
		this.password = password;
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
		params.add(new BasicNameValuePair("user_name", username));

		try
		{
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);

			success = json.getInt("success");

			if (success == 1)
			{
				data = json.getString("data");
				user_id = json.getString("user_id");
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
			if (data.equals(password))
			{
				MainActivity.user_id = user_id;

				CheckBox checkLoggedIn = (CheckBox) activity.findViewById(R.id.checkLoggedIn);
				if (checkLoggedIn.isChecked())
				{

					SharedPreferences.Editor editor = MainActivity.prefLoggedIn.edit();
					editor.putString("user_id", user_id);
					editor.commit();
				}

				MainActivity.showToast(activity, "Log in successful.");
				activity.finish();
			}
			else
			{
				MainActivity.showToast(activity, "Invalid username or password.");
			}
		}
		else
		{
			MainActivity.showToast(activity, "Error connecting server.");
		}

	}

}
