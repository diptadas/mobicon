package com.example.mobicon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobicon.query.RegisterQuery;

public class RegisterActivity extends Activity {

	Button btnReg, btnLinkLogin;
	EditText txtUsername, txtPassword, txtEmail, txtConfirmPass;

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

		btnReg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				String username = txtUsername.getText().toString().trim();
				String password = txtPassword.getText().toString().trim();
				String confirmPassword = txtConfirmPass.getText().toString().trim();
				String email = txtEmail.getText().toString().trim();

				if (username.equals("") || password.equals("") || confirmPassword.equals("") || email.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Plaese fill up all fields", Toast.LENGTH_LONG).show();
				}
				else if (!password.equals(confirmPassword))
				{
					Toast.makeText(getApplicationContext(), "Plaese confirm password", Toast.LENGTH_LONG).show();
				}
				else
				{
					new RegisterQuery(RegisterActivity.this, username, password, email).execute();
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
