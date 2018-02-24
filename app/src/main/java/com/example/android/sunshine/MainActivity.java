/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);




        // TODO (9) Call loadWeatherData to perform the network request to get the weather

                LoadWeatherData();

    }

    // TODO (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData


            /* here in this load method the default weather location is taken which is mountain view,california
            * to take this as default the sunshinepreference class is created and in that class
            * the getpreferredWeatherLoaction method is defined and in that method the DEFAULT_WEATHER variable is returned
            * due to this we are taking sunshinepreference class's, getpreferredweatherloaction method and contexting it
            * as main activity and soring its value in LOCATION variable.
            * and then executing the FetchWeatherTask Thread with Location variable.
            * */

    private void LoadWeatherData(){

        String location = SunshinePreferences.getPreferredWeatherLocation(this);
            new  FetchweatherTask().execute(location);



        }


    // TODO (5) Create a class that extends AsyncTask to perform network requests

    private class FetchweatherTask extends AsyncTask <String, Void, String[]>
    {

        // TODO (6) Override the doInBackground method to perform your network requests

        @Override
        protected String[] doInBackground(String... param) {


            /* If there's no zip code, there's nothing to look up. */
            if (param.length == 0) {
                return null;
            }


            String location = param[0];

            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


    // TODO (7) Override the onPostExecute method to display the results of the network request


        @Override
        protected void onPostExecute(String[] Weatherdata) {
            if (Weatherdata != null)
            {
                for (String WeatherString:Weatherdata)
                    mWeatherTextView.append((WeatherString) + "\n\n\n");
                Log.d("WeatherString", String.valueOf(Weatherdata));

            }

        }
    }
}