package com.example.mobicon;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;

public class StaticValues {

	public static String[] price_list = { "Select Price Range", "Less than 10,000 Tk.", "10,000 - 20,000 Tk.", "20,000 - 30,000 Tk.", "20,000 - 30,000 Tk.", "More than 40,000 Tk." };

	public static String[] os_list = { "Select OS", "Android", "iOS", "Windows", "Firefox" };

	public static String[] brand_list = { "Select Brand", "Apple", "Sony", "Samsung", "HTC", "Nokia", "Walton", "Symphony" };

	public static String[] division_list = { "Select Your Division", "Dhaka", "Chittagong", "Rangpur", "Khulna", "Rajshahi", "Barisal", "Sylhet" };

	public static ArrayList<String> searchColumns = new ArrayList<String>(Arrays.asList("title", "mobile_id", "img_url", "price", "rating", "released"));

	public static ArrayList<String> specColumns = new ArrayList<String>(Arrays.asList("mobile_id", "title", "img_url", "rating", "Technology", "Status", "Dimensions", "Weight", "SIM", "Disp. Type", "Disp. Size", "Resolution", "Protection", "OS", "Chipset", "CPU", "GPU", "Internal", "Card slot", "Primary Camera", "Secondary Camera", "Video", "WLAN", "Bluetooth", "GPS", "NFC", "Radio", "USB", "Sensors", "Battery", "Colors", "price"));

	public static ArrayList<String> shopColumns = new ArrayList<String>(Arrays.asList("shop_name", "division", "address", "lat", "long"));

	public static String server = "http://wepoka.org/mobicon_server/";
	//public static String server = "http://192.168.43.194/mobicon_server/";

	public static String specUrl = StaticValues.server + "specification.php";
	public static String searchUrl = StaticValues.server + "search.php";
	public static String checkPassUrl = StaticValues.server + "check_password.php";
	public static String registerUrl = StaticValues.server + "register.php";
	public static String giveRatingUrl = StaticValues.server + "give_rating.php";
	public static String topRatedUrl = StaticValues.server + "top_rated.php";
	public static String findShopUrl = StaticValues.server + "find_shop.php";

	public static String user_name = "NULL";
	public static SharedPreferences prefLoggedIn;

	public static void showToast(Activity activity, String msg)
	{
		Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

	}

}
