package com.fiixed.emojiweather;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abell on 12/4/13.
 */
public class WeatherDetailFragment extends Fragment {

    Weather weather = new Weather();
    String dataToSave;

    final static String JSON_OBJECT = "object";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //When we start we need to possibly get the last article position if there was one

        if(savedInstanceState != null) {  //checking to see if this is a new fragment created
            dataToSave = savedInstanceState.getString(JSON_OBJECT);
        }

        //inflate the layout for this fragment
        View thisFragmentView = inflater.inflate(R.layout.fragment_detail, container, false);

        return thisFragmentView;

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args !=null) {
            //Set the weather to be based on what the chosen weather object displays
            updateWeatherView(args.getString(JSON_OBJECT));

        } else if(dataToSave != null) {
            updateWeatherView("");
        }
    }

    public void updateWeatherView(String data) {
        dataToSave = data;
        View v = getView();
        TextView day = (TextView) v.findViewById(R.id.detail_day_text_view);
        TextView date = (TextView) v.findViewById(R.id.detail_date_text_view);
        TextView tempMin = (TextView) v.findViewById(R.id.detail_temp_min_text_view);
        TextView tempMinF = (TextView) v.findViewById(R.id.detail_temp_min_f_text_view);
        TextView tempMax = (TextView) v.findViewById(R.id.detail_temp_max_text_view);
        TextView tempMaxF = (TextView) v.findViewById(R.id.detail_temp_max_f_text_view);
        ImageView imageView = (ImageView) v.findViewById(R.id.detail_image_view);
        ImageView imageView2 = (ImageView) v.findViewById(R.id.detail_image_view2);
        try{
            JSONObject jsonObj = new JSONObject(data);

            weather.setmDate(jsonObj.getInt("time"));
            weather.setmTempMin(jsonObj.getInt("temperatureMin"));
            weather.setmTempMax(jsonObj.getInt("temperatureMax"));
            weather.setIcon(jsonObj.getString("icon"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        day.setText(weather.simpleDay());
        date.setText(weather.simpleDate());
        tempMin.setText(String.valueOf(weather.fahrenheitToCelsius(weather.getmTempMin())) + "°C");
        tempMinF.setText(String.valueOf(weather.getmTempMin()) + "°F");
        tempMax.setText(String.valueOf(weather.fahrenheitToCelsius(weather.getmTempMax())) + "°C");
        tempMaxF.setText(String.valueOf(weather.getmTempMax()) + "°F");

        int resId = getActivity().getResources().getIdentifier(weather.getEmoji(weather.getIcon()), "drawable", getActivity().getPackageName());
        imageView.setImageResource(resId);

        int resId2 = getActivity().getResources().getIdentifier(weather.getEmoji(weather.getIcon()), "drawable", getActivity().getPackageName());
        imageView2.setImageResource(resId2);






    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(JSON_OBJECT, dataToSave);


    }


}
