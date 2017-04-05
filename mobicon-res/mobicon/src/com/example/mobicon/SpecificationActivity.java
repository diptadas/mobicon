package com.example.mobicon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.mobicon.customdialog.RatingDialog;
import com.example.mobicon.query.SpecificationQuery;
import com.example.mobicon.util.LruBitmapCache;

public class SpecificationActivity extends MyActivity {

	Button btn_rate;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specification);

		btn_rate = (Button) findViewById(R.id.button1);

		Intent intent = getIntent();

		String brand = intent.getExtras().getString("brand");
		String model = intent.getExtras().getString("model");
		final String mobile_id = intent.getExtras().getString("mobile_id");
		String avg_rating = intent.getExtras().getString("avg_rating");

		setHeader(brand + " " + model, true);

		RatingBar bar = (RatingBar) findViewById(R.id.ratingBar1);
		bar.setRating(Float.parseFloat(avg_rating));

		String url = MainActivity.server + "images/" + mobile_id + ".jpg";

		ImageLoader.ImageCache imageCache = new LruBitmapCache();
		ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getApplicationContext()), imageCache);

		NetworkImageView imageView = (NetworkImageView) findViewById(R.id.fullImage);
		imageView.setImageUrl(url, imageLoader);

		new SpecificationQuery(this, mobile_id).execute();

		btn_rate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				if (MainActivity.user_id.equals("0"))
				{
					showLoginAlert();
				}
				else
				{
					new RatingDialog(SpecificationActivity.this, mobile_id);
				}
			}
		});

	}

	public void showLoginAlert()
	{
		new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
				.setTitle("Log in")
				.setMessage("Please log in to give rating.")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent i = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(i);

						dialog.dismiss();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();
	}

}
