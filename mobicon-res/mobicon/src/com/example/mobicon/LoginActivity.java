package com.example.mobicon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobicon.query.CheckPasswordQuery;

public class LoginActivity extends Activity {

	Button btnLogin, btnLinkReg;
	EditText txtUsername, txtPassword;

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

		MainActivity.user_id = "0";
		SharedPreferences.Editor editor = MainActivity.prefLoggedIn.edit();
		editor.putString("user_id", "0");
		editor.commit();

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				String username = txtUsername.getText().toString().trim();
				String password = txtPassword.getText().toString().trim();

				if (username.equals("") || password.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please fill up all fields", Toast.LENGTH_LONG).show();
				}
				else
				{
					new CheckPasswordQuery(LoginActivity.this, username, password).execute();
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
