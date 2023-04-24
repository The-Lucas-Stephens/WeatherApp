package com.example.therealweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.URL;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class DisplayWeather extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getName();

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);


       // Get references to the layout elements
        TextView cityName = findViewById(R.id.city_name);
        TextView weatherDescription = findViewById(R.id.weather_description);
        TextView temperature = findViewById(R.id.temperature);


        //getting shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);


        //setting data to variables
        String zipCode = sharedPreferences.getString("zipCode","");
        String tempType = sharedPreferences.getString("tempType","");

        if(tempType.equals("MetricSystem")){
            url = String.format("https://api.openweathermap.org/data/2.5/weather?zip=%s,us&units=metric&appid=3c603572a6623fb8cef3f2fb7294d1ab",zipCode);

        }
        else if(tempType.equals("ImperialSystem")){
            url = String.format("https://api.openweathermap.org/data/2.5/weather?zip=%s,us&units=imperial&appid=3c603572a6623fb8cef3f2fb7294d1ab",zipCode);


        }

        //verifying things in the console
        System.out.println("Zip code: " + zipCode); // print the zip code to the console
        System.out.println("Temp Type: " + tempType); // print the zip code to the console
        System.out.println("API URL: " + url); // print the URL to the console





        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String weatherData;

                weatherData = response;

               //getting the json object

                ;try {
                    // Parse JSON data
                    JSONObject jsonObject = new JSONObject(weatherData);

                    // Get data from JSON object

                    //"name" is the key in the JSON object
                    String name = jsonObject.getString("name");

                    //getting weather array inside json response
                    JSONArray weatherArray = jsonObject.getJSONArray("weather");

                    //getting weather object in weather array at position 0
                    JSONObject weather = weatherArray.getJSONObject(0);

                    //getting the current weather type from weather object
                    String currentWeather = weather.getString("main");
                    //getting the weather description from weather object
                    String currentWeatherDescription = weather.getString("description");

                    String weatherIcon = weather.getString("icon");

                    // "main" is another object nested inside the main object
                    JSONObject main = jsonObject.getJSONObject("main");
                    // "temp" is a key inside the "main" object, and we're using getDouble() since it's a decimal value
                    double temp = main.getDouble("temp");


                    System.out.println("City Name:" +name);
                    System.out.println("Current Weather:" +currentWeather);
                    System.out.println("Weather Description:" +currentWeatherDescription);
                    System.out.println("Temperature: " +temp);
                    System.out.println("Icon: " +weatherIcon);


                    //displaying info to the user

                    //changing image source of image view using picasso a external image library
                    ImageView imageView = findViewById(R.id.weather_icon);
                  //setting string URL to get weather icon
                    String imageUrl = String.format("https://openweathermap.org/img/w/%s.png",weatherIcon);

                    System.out.println("Image url: " +imageUrl);

                    //displaying data to the user

                    // Set the values for the layout elements
                    cityName.setText("City Name: " +name);
                    weatherDescription.setText("Current Weather " +currentWeatherDescription);

                    if(tempType.equals("MetricSystem")){
                        temperature.setText("Temperature" + temp + " °C"); // format the temperature value as needed

                    }
                    else if(tempType.equals("ImperialSystem")){
                        temperature.setText(" Temperature: " + temp + " °F"); // format the temperature value as needed


                    }



                    //using Picasso library methods to set image source to the url of weather icon
                    Picasso.get().load(imageUrl).into(imageView);

                } catch (JSONException e) {
                    // Handle JSON exception
                    e.printStackTrace();
                }


                // print the object to the console
                System.out.println("Object: " + weatherData);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);

        Button back_btn = (Button) findViewById(R.id.go_back_btn);

        back_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(DisplayWeather.this, MainActivity.class));
            }
        });
    }






}