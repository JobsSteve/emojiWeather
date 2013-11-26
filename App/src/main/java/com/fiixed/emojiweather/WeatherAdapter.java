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

    public WeatherAdapter(Context context, int layoutResourceId, Weather[] data) {
        super(context, layoutResourceId, data);
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;
    }

    @Override
    public Weather getItem(int position) {
        return super.getItem(position);
    }

    public int getCount() {
        return mData.length;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherHolder holder;

        View row = convertView;
        //inflate the layout for a single row
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (row == null) {//if the row has not been created before
            row = layoutInflater.inflate(mLayoutResourceId, parent, false);


            holder = new WeatherHolder();

            //get a reference to all the different view elements we wish to update
            holder.day = (TextView) row.findViewById(R.id.dayTextView);
            holder.date = (TextView) row.findViewById(R.id.dateTextView);
            holder.tempMin = (TextView) row.findViewById(R.id.tempMinTextView);
            holder.tempMax = (TextView) row.findViewById(R.id.tempMaxTextView);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);
            row.setTag(holder);

        } else {
            holder = (WeatherHolder) row.getTag();
        }

        //get the data from the data array
        Weather weather = mData[position];


        //setting the view to the data we need to display
        holder.day.setText(weather.simpleDay(weather.getmDate()));
        holder.date.setText(weather.simpleDate(weather.getmDate()));
        holder.tempMin.setText(String.valueOf(weather.fahrenheitToCelsius(weather.getmTempMin())) + "°");
        holder.tempMax.setText(String.valueOf(weather.fahrenheitToCelsius(weather.getmTempMax())) + "°");

        int resId = mContext.getResources().getIdentifier(weather.getEmoji(weather.getIcon()), "drawable", mContext.getPackageName());
        holder.imageView.setImageResource(resId);

        //returning the row view(because this is called getView after all)


        return row;
    }

    public void setData(Weather[] replacementArray) {
        mData = replacementArray;
        this.notifyDataSetChanged();

    }

    private static class WeatherHolder {
        public TextView day;
        public TextView date;
        public TextView tempMin;
        public TextView tempMax;
        public ImageView imageView;
    }
}
