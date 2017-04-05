package com.example.mobicon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends MyActivity {

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setHeader(getString(R.string.app_name), false);

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();

		Button btnLog = (Button) findViewById(R.id.btn_log);

		if (StaticValues.user_name.equals("NULL"))
		{
			Drawable imgLogin = getResources().getDrawable(R.drawable.ic_login);
			btnLog.setCompoundDrawablesWithIntrinsicBounds(null, imgLogin, null, null);
			btnLog.setText("Log in");
		} else
		{
			Drawable imgLogout = getResources().getDrawable(R.drawable.ic_logout);
			btnLog.setCompoundDrawablesWithIntrinsicBounds(null, imgLogout, null, null);
			btnLog.setText("Log out");
		}
	}

	public void onButtonClicker(View v)
	{
		Intent intent;

		switch (v.getId())
		{
		case R.id.btn_top_rated:
			intent = new Intent(this, TopRatedActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_search:
			intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_compare:
			intent = new Intent(this, CompareActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_find_shop:
			intent = new Intent(this, FindShopActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_log:
			if (StaticValues.user_name.equals("NULL"))
			{
				intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			} else
			{
				StaticValues.user_name = "NULL";
				SharedPreferences.Editor editor = StaticValues.prefLoggedIn.edit();
				editor.putBoolean("logged_in", false);
				editor.putString("user_name", "NULL");
				editor.commit();

				intent = getIntent();
				finish();
				startActivity(intent);

				StaticValues.showToast(this, "Successfully logged out.");
			}

			break;

		case R.id.btn_about:

			intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
