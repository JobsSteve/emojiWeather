package com.fiixed.emojiweather;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
public class WeatherListFragment extends Fragment {
    private final String API_URL = "https://api.forecast.io/forecast/8fc2b0556e166fa4670d4014d318152a/-37.813611,144.963056";


    ListView mListView;
    Weather[] myWeatherArray = {};
    WeatherAdapter weatherAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setup ListView

        weatherAdapter = new WeatherAdapter(getActivity().getApplicationContext(), R.layout.row, myWeatherArray);


        mListView = (ListView) view.findViewById(R.id.listView);

        if (mListView != null) {
            mListView.setAdapter(weatherAdapter);
        }else{
            Log.e("ERROR","I dont have a listview");
        }



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

            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
