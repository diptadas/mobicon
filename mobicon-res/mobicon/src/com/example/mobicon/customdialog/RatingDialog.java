package com.example.mobicon.customdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mobicon.R;
import com.example.mobicon.query.RatingQuery;

public class RatingDialog {

	public RatingDialog(final Activity activity, final String mobile_id)
	{
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.rating_dialog, null);

		final RatingBar bar = (RatingBar) view.findViewById(R.id.ratingBar1);
		Button btn_rate = (Button) view.findViewById(R.id.button1);
		Button btn_cancle = (Button) view.findViewById(R.id.button2);

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setView(view);
		builder.setCancelable(false);

		final AlertDialog customDialog = builder.create();

		btn_rate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				if (bar.getRating() == 0)
				{
					Toast.makeText(activity, "Please select one value", Toast.LENGTH_LONG).show();
				}
				else
				{
					customDialog.cancel();
					new RatingQuery(activity, mobile_id, (int) bar.getRating() + "").execute();
				}
			}
		});

		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				customDialog.cancel();

			}
		});

		customDialog.show();
	}
}
