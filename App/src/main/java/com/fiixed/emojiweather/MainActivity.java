package com.fiixed.emojiweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements WeatherListFragment.OnWeatherSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

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
//        Intent i = new Intent(this,HelloIntentService.class);
//        startService(i);

        Intent i = new Intent(this,NotificationService.class);
        startService(i);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent i = new Intent(this,NotificationService.class);
        startService(i);
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


        WeatherDetailFragment weatherDetailFragment = (WeatherDetailFragment) getFragmentManager().findFragmentById(R.id.weather_detail_fragment);

//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        if (fragments != null) {
//            for (Fragment f : fragments) {
//                Object o = f;
//                Log.i(TAG, String.format("I am a %s!\n", o.getClass().getSimpleName()));
//            }
//        }

        //One pane layout
        if(weatherDetailFragment == null) {
            WeatherDetailFragment onePaneFragment = new WeatherDetailFragment();

            Bundle args = new Bundle();

            args.putString(WeatherDetailFragment.JSON_OBJECT, data);

            onePaneFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, onePaneFragment)
                    .commit();

        } else {
            //Two Pane Layout
    //            throw new IllegalStateException("WTF, MATE!?");
            weatherDetailFragment.updateWeatherView(data);
        }
    }
}
