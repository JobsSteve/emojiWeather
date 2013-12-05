package com.fiixed.emojiweather;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
public class WeatherListFragment extends ListFragment implements LocationListener{

    private final String initialURL = "https://api.forecast.io/forecast/8fc2b0556e166fa4670d4014d318152a/";
    Weather[] myWeatherArray = {};
    WeatherAdapter weatherAdapter;
    LocationManager mLocationManager;
    String currentLoc;
    JSONArray data;
    JSONObject day;

    OnWeatherSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnWeatherSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onWeatherSelected(String data);
    }

    @Override
    public void onStart() {
        super.onStart();

        Fragment f = getFragmentManager().findFragmentById(R.id.weather_detail_fragment);
        ListView v = getListView();

        //checking to see if both the article and headline fragment are both active
        if(f != null && v != null) {
            v.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  //make each list item sticky
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //make sure the activity we're attached to actually has implemented the right callback method
        try{
            mCallback = (OnWeatherSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement onHeadLineSelectedListener!!");
        }


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //call back to the parent activity with the selected item
        try{
            String data1 = data.get(position).toString();
            mCallback.onWeatherSelected(data1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
        makeUseOfNewLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        makeUseOfNewLocation(mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Setup ListView

        weatherAdapter = new WeatherAdapter(getActivity().getApplicationContext(), R.layout.row, myWeatherArray);


        setListAdapter(weatherAdapter);
    }


    public void getData() {

        String API_URL = setLatLong(initialURL, currentLoc);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject daily = response.getJSONObject("daily");
                    data = daily.getJSONArray("data");
                    myWeatherArray = new Weather[data.length()];
                    for (int i = 0; i < myWeatherArray.length; i++) {
                        day = data.getJSONObject(i);
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

    public void makeUseOfNewLocation(Location location) {

        if (location == null) {
            return;
        }

        mLocationManager.removeUpdates(this);

        double latDouble = location.getLatitude();
        double longDouble = location.getLongitude();

        String latString = String.valueOf(latDouble);
        String longString = String.valueOf(longDouble);

        String latLong = latString + "," + longString;
        Log.e("gps", latLong);
        currentLoc = latLong;

        getData();

    }

    public String setLatLong(String roughURL, String loc) {

        return roughURL + loc;

    }

}
