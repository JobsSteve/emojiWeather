package com.fiixed.emojiweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by abell on 12/4/13.
 */
public class WeatherDetailFragment extends Fragment {

    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //When we start we need to possibly get the last article position if there was one

        if(savedInstanceState != null) {  //checking to see if this is a new fragment created
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
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
            //Set the article to be based on what the article object says
            updateWeatherView(args.getInt(ARG_POSITION));
        } else if(mCurrentPosition != -1) {
            updateWeatherView(mCurrentPosition);
        }
    }

    public void updateWeatherView(int position) {
        View v = getView();
        TextView day = (TextView) v.findViewById(R.id.dayTextView);
        TextView date = (TextView) v.findViewById(R.id.dateTextView);
        TextView tempMin = (TextView) v.findViewById(R.id.tempMinTextView);
        TextView tempMinF = (TextView) v.findViewById(R.id.tempMinFTextView);
        TextView tempMax = (TextView) v.findViewById(R.id.tempMaxTextView);
        TextView tempMaxF = (TextView) v.findViewById(R.id.tempMaxFTextView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        ImageView imageView2 = (ImageView) v.findViewById(R.id.imageView2);
        day.setText(Weather[position].simpleDay());
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);

    }
}
