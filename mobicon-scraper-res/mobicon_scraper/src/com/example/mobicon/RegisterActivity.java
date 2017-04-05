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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	List<NameValuePair> params = new ArrayList<NameValuePair>();

	ListView listView;
	Button btnListener; // fake button to listen when AsycTask is completed

	AsyncTaskQuery objAsyncTaskQuery;

	int success;
	ArrayList<HashMap<String, String>> data_list;

	ProgressDialog pDialog;

	Button btnReg, btnLinkLogin;
	EditText txtUsername, txtPassword, txtEmail, txtConfirmPass;

	String user_name, password, email;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		btnReg = (Button) findViewById(R.id.btnRegister);
		btnLinkLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		txtUsername = (EditText) findViewById(R.id.username);
		txtPassword = (EditText) findViewById(R.id.password);
		txtEmail = (EditText) findViewById(R.id.email);
		txtConfirmPass = (EditText) findViewById(R.id.confirm_password);

		btnListener = new Button(this);

		btnReg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				user_name = txtUsername.getText().toString().trim();
				password = txtPassword.getText().toString().trim();
				email = txtEmail.getText().toString().trim();

				String confirmPassword = txtConfirmPass.getText().toString().trim();

				if (user_name.equals("") || password.equals("") || confirmPassword.equals("") || email.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Plaese fill up all fields", Toast.LENGTH_LONG).show();
				} else if (!password.equals(confirmPassword))
				{
					Toast.makeText(getApplicationContext(), "Plaese confirm password", Toast.LENGTH_LONG).show();
				} else
				{
					//new RegisterQuery(RegisterActivity.this, username, password, email).execute();

					params.add(new BasicNameValuePair("user_name", user_name));
					params.add(new BasicNameValuePair("password", password));
					params.add(new BasicNameValuePair("email", email));

					pDialog = new ProgressDialog(RegisterActivity.this, ProgressDialog.THEME_HOLO_DARK);
					pDialog.setMessage("Loading data...");
					pDialog.setIndeterminate(false);
					pDialog.setCancelable(false);
					pDialog.show();

					objAsyncTaskQuery = new AsyncTaskQuery(StaticValues.registerUrl, new ArrayList<String>(Arrays.asList("status")), params, btnListener);
				}

			}
		});

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
					String status = data_list.get(0).get("status");

					if (status.equals("ok"))
					{
						StaticValues.showToast(RegisterActivity.this, "Registration Successfull.");
						Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
						RegisterActivity.this.startActivity(i);
						RegisterActivity.this.finish();
					} else
					{
						StaticValues.showToast(RegisterActivity.this, status);
					}

				} else
				{
					StaticValues.showToast(RegisterActivity.this, "Error connecting server.");
				}
			}
		});

		btnLinkLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				finish();

			}
		});
	}

}
