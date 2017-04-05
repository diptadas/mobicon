package com.example.mobicon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static String[] os_list = { "Select OS", "Android", "iOS", "Windows", "Firefox" };

	public static String[] brand_list = { "Select Brand", "Apple", "Sony", "Samsung", "HTC", "Nokia", "Walton", "Symphony" };

	public static String[] division_list = { "Select Your Division", "Dhaka", "Chittagong",
			"Rangpur", "Khulna", "Rajshahi", "Barisal", "Sylhet" };

	public static String[] mobile_info_column = { "Mobile id", "Brand", "Model", "Released", "Dual sim",
			"Display(inch)", "OS", "Version", "Chipset", "CPU", "GPU",
			"RAM(GB)", "ROM(GB)", "SD Card(GB)", "Rear camera(MP)", "Front camera(MP)", "Battery(mAh)", "Price(Tk)" };

	public static String user_id = "0";
	public static SharedPreferences prefLoggedIn;

	public static String server = "http://mobicon-test.webege.com/mobicon_server/";

	//public static String server = "http://192.168.43.194/mobicon_server/";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		prefLoggedIn = getPreferences(MODE_PRIVATE);
		user_id = prefLoggedIn.getString("user_id", "0");

		Intent i = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(i);
		finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static void showToast(Activity activity, String msg)
	{
		Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

	}

}
