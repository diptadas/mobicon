package com.example.mobicon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.mobicon.util.AsyncTaskQuery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	List<NameValuePair> params = new ArrayList<NameValuePair>();

	ListView listView;
	Button btnListener; // fake button to listen when AsycTask is completed

	AsyncTaskQuery objAsyncTaskQuery;

	int success;
	ArrayList<HashMap<String, String>> data_list;

	ProgressDialog pDialog;

	Button btnLogin, btnLinkReg;
	EditText txtUsername, txtPassword;

	String user_name, password;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkReg = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		txtUsername = (EditText) findViewById(R.id.username);
		txtPassword = (EditText) findViewById(R.id.password);

		StaticValues.user_name = "NULL";
		SharedPreferences.Editor editor = StaticValues.prefLoggedIn.edit();
		editor.putString("user_name", "NULL");
		editor.commit();

		btnListener = new Button(LoginActivity.this);

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
					if (data_list.size() != 0 && data_list.get(0).get("password").equals(password))
					{
						StaticValues.user_name = user_name;

						CheckBox checkLoggedIn = (CheckBox) LoginActivity.this.findViewById(R.id.checkLoggedIn);
						if (checkLoggedIn.isChecked())
						{
							SharedPreferences.Editor editor = StaticValues.prefLoggedIn.edit();
							editor.putString("user_name", user_name);
							editor.commit();
						}

						StaticValues.showToast(LoginActivity.this, "Log in successful.");
						LoginActivity.this.finish();
					} else
					{
						StaticValues.showToast(LoginActivity.this, "Invalid username or password.");
					}

				} else
				{
					StaticValues.showToast(LoginActivity.this, "Error connecting server.");
				}
			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				user_name = txtUsername.getText().toString().trim();
				password = txtPassword.getText().toString().trim();

				if (user_name.equals("") || password.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please fill up all fields", Toast.LENGTH_LONG).show();
				} else
				{
					//new CheckPasswordQuery(LoginActivity.this, username, password).execute();

					params.add(new BasicNameValuePair("user_name", user_name));

					pDialog = new ProgressDialog(LoginActivity.this, ProgressDialog.THEME_HOLO_DARK);
					pDialog.setMessage("Loading data...");
					pDialog.setIndeterminate(false);
					pDialog.setCancelable(false);
					pDialog.show();

					objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.checkPassUrl, new ArrayList<String>(Arrays.asList("password")), params, btnListener);
				}

			}
		});

		btnLinkReg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
				finish();

			}
		});
	}

}
