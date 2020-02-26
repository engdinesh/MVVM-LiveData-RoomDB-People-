package com.example.ui.weather;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.R;
import com.example.data.model.Main;
import com.example.data.model.WeatherItem;
import com.example.data.model.WeatherResponse;
import com.example.databinding.ActivityWeatherBinding;


/**
 * Createdby Dinesh on 24/09/19.
 */

public class WeatherActivity extends AppCompatActivity {


    private WeatherViewModel weatherViewModel;
    private ActivityWeatherBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        binding = DataBindingUtil.setContentView(WeatherActivity.this, R.layout.activity_weather);
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setWeatherViewModel(weatherViewModel);

        weatherViewModel.init();
        weatherViewModel.getWeather(this).observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {

                Log.e("WEATHER DATA",weatherResponse.toString());
                Main main = weatherResponse.getMain();
                WeatherItem weatherItem = weatherResponse.getWeather().get(0);
                int temp = (int) main.getTemp();
                binding.tvTemp.setText(String.valueOf(temp)+"°");
                binding.tvCityName.setText(weatherResponse.getName());
                binding.tvTempHigh.setText(String.valueOf((int)main.getTempMax())+"° C");
                binding.tvTempLow.setText(String.valueOf((int)main.getTempMin())+"° C");
                binding.tvWind.setText(String.valueOf((int)weatherResponse.getWind().getDeg())+" km/h");
                binding.tvHumidity.setText(String.valueOf(main.getHumidity())+" %");
                binding.tvPressure.setText(String.valueOf(main.getPressure())+" in");
                binding.tvVisibility.setText(String.valueOf((int)main.getFeelsLike())+"° C");
                binding.tvWeatherType.setText(weatherItem.getMain());
                binding.progressCircular.setVisibility(View.GONE);
            }
        });
    }


    // Converts to celcius
    private float convertFahrenheitToCelcius(float fahrenheit) {
        return ((fahrenheit - 32) * 5 / 9);
    }



    // Converts to fahrenheit
    private float convertCelciusToFahrenheit(float celsius) {
        return ((celsius * 9) / 5) + 32;
    }


}
