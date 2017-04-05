package com.example.mobicon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView txt1, txt2;
	String app_name = "MobiCon";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txt1 = (TextView) findViewById(R.id.textView1);
		txt2 = (TextView) findViewById(R.id.textView2);

		txt1.setText("");

		startTimerThread();

	}

	private void startTimerThread()
	{
		final Handler handler = new Handler();

		Runnable runnable = new Runnable() {

			int i;

			public void run()
			{
				for (i = 0; i < 10; i++)
				{
					try
					{
						Thread.sleep(300);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						public void run()
						{
							if (i < 7) txt1.setText(app_name.substring(0, i + 1));
							else if (i == 9) doIntent();
						}
					});
				}
			}
		};

		new Thread(runnable).start();
	}

	public void doIntent()
	{
		if (isConnectedToInternet())
		{
			StaticValues.prefLoggedIn = getPreferences(MODE_PRIVATE);
			StaticValues.user_name = StaticValues.prefLoggedIn.getString("user_name", "NULL");

			Intent i = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(i);
			finish();
		} else
			showAlert();

	}

	public boolean isConnectedToInternet()
	{
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
			{
				for (int i = 0; i < info.length; i++)
				{
					if (info[i].getState() == NetworkInfo.State.CONNECTED) return true;
				}
			}
		}
		return false;
	}

	@SuppressLint("NewApi")
	public void showAlert()
	{
		new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK).setTitle("Connection error").setMessage(R.string.connection_error_alert).setCancelable(false).setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				MainActivity.this.finish();
			}
		}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

}
