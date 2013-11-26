package com.fiixed.emojiweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by abell on 11/25/13.
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {

    Context mContext;
    int mLayoutResourceId;
    Weather[] mData;

    public WeatherAdapter(Context context, int layoutResourceId, Weather[] data){
        super(context,layoutResourceId,data);
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;
    }

    @Override
    public Weather getItem(int position) {
        return super.getItem(position);
    }

    public int getCount(){
        return mData.length;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        //inflate the layout for a single row
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        row = layoutInflater.inflate(mLayoutResourceId, parent, false);

        //get a reference to all the different view elements we wish to update
        TextView day = (TextView)row.findViewById(R.id.dayTextView);
        TextView date = (TextView)row.findViewById(R.id.dateTextView);
        TextView tempMin = (TextView)row.findViewById(R.id.tempMinTextView);
        TextView tempMax = (TextView)row.findViewById(R.id.tempMaxTextView);
        ImageView imageView = (ImageView)row.findViewById(R.id.imageView);

        //get the data from the data array
        Weather weather = mData[position];


        //setting the view to the data we need to display
        date.setText(weather.simpleDate(weather.getmDate()));
        tempMin.setText(String.valueOf(weather.fahrenheitToCelsius(weather.getmTempMin())));
        tempMax.setText(String.valueOf(weather.fahrenheitToCelsius(weather.getmTempMax())));

        int resId = mContext.getResources().getIdentifier(weather.getEmoji(weather.getIcon()), "drawable", mContext.getPackageName());
        imageView.setImageResource(resId);

        //returning the row view(because this is called getView after all)


        return row;
    }

    public void setData(Weather[] replacementArray) {
        mData = replacementArray;
        this.notifyDataSetChanged();

    }
}
