package com.fiixed.emojiweather;

import android.app.ListFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abell on 11/21/13.
 */
public class WeatherListFragment extends ListFragment {
    private final String initialURL = "https://api.forecast.io/forecast/8fc2b0556e166fa4670d4014d318152a/";

//    private String API_URL = setLatLong(initialURL);
      private String API_URL = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private static String setLatLong(String roughURL, Location location) {
        String latitude =  Location.convert(location.getLatitude(), Location.FORMAT_DEGREES);
        String longitude = Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
        Log.e("gps", latitude + "," + longitude);

        return roughURL +  latitude + "," + longitude;

    }


    Weather[] myWeatherArray = {};
    WeatherAdapter weatherAdapter;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //Setup ListView

        weatherAdapter = new WeatherAdapter(getActivity().getApplicationContext(), R.layout.row, myWeatherArray);


        setListAdapter(weatherAdapter);



        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject daily = response.getJSONObject("daily");
                    JSONArray data = daily.getJSONArray("data");
                    myWeatherArray = new Weather[data.length()];
                    for (int i = 0; i < myWeatherArray.length; i++) {
                        JSONObject day = data.getJSONObject(i);
                        Weather myWeatherObject = new Weather();
                        myWeatherObject.setmDate(day.getInt("time"));
                        myWeatherObject.setmTempMin(day.getInt("temperatureMin"));
                        myWeatherObject.setmTempMax(day.getInt("temperatureMax"));
                        myWeatherObject.setIcon(day.getString("icon"));
                        myWeatherArray[i] = myWeatherObject;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (weatherAdapter != null) {
                    weatherAdapter.setData(myWeatherArray);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "volley died", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void getLocation() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                API_URL = setLatLong(initialURL, location);
                Log.e("gps", API_URL);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        // Remove the listener you previously added
        locationManager.removeUpdates(locationListener);
    }
}
