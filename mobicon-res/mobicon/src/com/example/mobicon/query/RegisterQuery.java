package com.example.mobicon.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.mobicon.LoginActivity;
import com.example.mobicon.MainActivity;
import com.example.mobicon.util.JSONParser;

public class RegisterQuery extends AsyncTask<String, String, String> {

	int success;
	int data;
	String username, password, email;
	Activity activity;
	ProgressDialog pDialog;
	String url = MainActivity.server + "check_username.php";
	String urlReg = MainActivity.server + "register.php";

	public RegisterQuery(Activity activity, String username, String password, String email)
	{
		this.activity = activity;
		this.username = username;
		this.password = password;
		this.email = email;
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
				data = json.getInt("data");

				if (data == 0) //username available, now insert data
				{
					//add remaining parameters
					params.add(new BasicNameValuePair("password", password));
					params.add(new BasicNameValuePair("email", email));

					json = jParser.makeHttpRequest(urlReg, "GET", params);
					int successReg = json.getInt("success");

					if (successReg == 0) success = 0;
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
			if (data == 0)
			{
				MainActivity.showToast(activity, "Registration Successfull.");
				Intent i = new Intent(activity, LoginActivity.class);
				activity.startActivity(i);
				activity.finish();
			}
			else
			{
				MainActivity.showToast(activity, "Username not available.");
			}
		}
		else
		{
			MainActivity.showToast(activity, "Error connecting server.");
		}

	}

}
