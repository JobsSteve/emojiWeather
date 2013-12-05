package com.fiixed.emojiweather;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements WeatherListFragment.OnWeatherSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.container) != null) {

            if(savedInstanceState !=null) {
                return;
            }

            WeatherListFragment weatherListFragment = new WeatherListFragment();

            weatherListFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction()
                    .add(R.id.container, weatherListFragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onWeatherSelected(String data) {
        // The user selected the headline of an article from the HeadlinesFragment


        WeatherDetailFragment weatherDetailFragment = (WeatherDetailFragment) getSupportFragmentManager().findFragmentById(R.id.weather_detail_fragment);

        //One pane layout
        if(weatherDetailFragment == null) {
            WeatherDetailFragment onePaneFragment = new WeatherDetailFragment();

            Bundle args = new Bundle();

            args.putString(WeatherDetailFragment.JSON_OBJECT, data);

            onePaneFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, onePaneFragment)
                    .addToBackStack(null)
                    .commit();

    } else {
        //Two Pane Layout
        weatherDetailFragment.updateWeatherView(data);
    }
    }
}
