package ateam.rehashprot2;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ateam.rehashprot2.data.Channel;
import ateam.rehashprot2.data.Condition;
import ateam.rehashprot2.data.Item;
import ateam.rehashprot2.service.WeatherService;
import ateam.rehashprot2.service.WeatherServiceCallback;

/**
 * Created by Warrick
 */
public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private WeatherService service;

    private ProgressDialog dialog;

    // weather service fail flag
    private boolean weatherServicesHasFailed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setTitle("ReHash: Current Weather");

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

        service = new WeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        service.refreshWeather("Auckland, NZ");


    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();
        dialog.dismiss();

        Condition condition = channel.getItem().getCondition();

        Log.e("Code", " " + condition.getCode());

        Item item = channel.getItem();

        int resourceId = getResources().getIdentifier("drawable/icon_" + condition.getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        //String temperatureLabel = getString(condition.getTemperature(), channel.getUnits().getTemperature());
        String temperatureLabel = getString(R.string.temperature_output, condition.getTemperature(), channel.getUnits().getTemperature());
        temperatureTextView.setText(temperatureLabel);
        conditionTextView.setText(condition.getDescription());
        locationTextView.setText(channel.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception) {
        // display error if this is the second failure
        if (weatherServicesHasFailed) {
            dialog.hide();
            dialog.dismiss();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            // error doing reverse geocoding, load weather data from cache
            weatherServicesHasFailed = true;
            // OPTIONAL: let the user know an error has occurred then fallback to the cached data
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
