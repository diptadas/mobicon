package com.example.mobicon;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mobicon.util.DownloadTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	GoogleMap map;
	ImageButton direction;

	LatLng loc, myLoc;
	Marker marker, myMarker;

	double lat, myLat, lon, myLon;

	LocationClient locationClient;

	String title = "", snippet = "";

	boolean draw = false;

	@Override
	protected void onCreate(Bundle arg0)
	{
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_map);

		Intent intent = getIntent();

		title = intent.getExtras().getString("shop_name");
		snippet = intent.getExtras().getString("division");
		lat = Double.parseDouble(intent.getExtras().getString("lat"));
		lon = Double.parseDouble(intent.getExtras().getString("long"));

		direction = (ImageButton) findViewById(R.id.button1);

		if (!isMapAvailable())
		{
			Toast.makeText(getApplicationContext(), "Sorry google play service is not installed", Toast.LENGTH_LONG).show();
		} else
		{

			map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			if (map == null) Toast.makeText(getApplicationContext(), "Map can't be created", Toast.LENGTH_LONG).show();
			else
			{
				map.setMyLocationEnabled(true);

				loc = new LatLng(lat, lon);

				marker = map.addMarker(new MarkerOptions().position(loc).title(title).snippet(snippet));
				marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
				marker.showInfoWindow();

				map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 10.0f));

				locationClient = new LocationClient(this, this, this);
			}
		}

		direction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				getMyLoc();

				if (draw == false)
				{
					try
					{
						draw = true;
						Toast.makeText(getApplicationContext(), "Wait to get the direction", Toast.LENGTH_LONG).show();
						try
						{
							final LatLngBounds bounds = new LatLngBounds.Builder().include(myLoc).include(loc).build();
							map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 65));
						} catch (Exception e)
						{
							Toast.makeText(getApplicationContext(), "Direction can not be shown, please try again.", Toast.LENGTH_LONG).show();
						}
						String str_origin = "origin=" + lat + "," + lon;
						// Destination of route
						String str_dest = "destination=" + myLat + "," + myLon;
						// Sensor enabled
						String sensor = "sensor=false";
						// Building the parameters to the web service
						String parameters = str_origin + "&" + str_dest + "&" + sensor;
						// Output format
						String output = "json";
						// Building the url to the web service
						String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
						DownloadTask downloadTask = new DownloadTask(map);
						// Start downloading json data from Google Directions
						// API
						downloadTask.execute(url);
					} catch (Exception e)
					{
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "Direction can not be shown, please try again.", Toast.LENGTH_LONG).show();
					}
				} else
					Toast.makeText(getApplicationContext(), "Wait to get the direction", Toast.LENGTH_LONG).show();
			}
		});

	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		locationClient.disconnect();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		locationClient.connect();
	}

	public boolean isMapAvailable()
	{
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (result == ConnectionResult.SUCCESS)
		{
			return true;

		} else if (GooglePlayServicesUtil.isUserRecoverableError(result))
		{
			Dialog d = GooglePlayServicesUtil.getErrorDialog(result, this, 1);
			d.show();
		}

		else
		{
			Toast.makeText(getApplicationContext(), "Google Play Service is not installed", Toast.LENGTH_LONG).show();
			finish();
		}
		return false;
	}

	@Override
	public void onLocationChanged(Location arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected()
	{
		// TODO Auto-generated method stub

	}

	public void getMyLoc()
	{
		Location location = locationClient.getLastLocation();
		try
		{
			myLat = location.getLatitude();
			myLon = location.getLongitude();
			myLoc = new LatLng(myLat, myLon);

			myMarker = map.addMarker(new MarkerOptions().position(myLoc).title("You are here"));
			myMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			//myMarker.showInfoWindow();
			//map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 10.0f));

		} catch (Exception e)
		{
			Toast.makeText(getBaseContext(), "Location not found", Toast.LENGTH_LONG).show();
		}
	}

}
