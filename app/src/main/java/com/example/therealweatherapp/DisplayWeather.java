package com.example.therealweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class DisplayWeather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);

        //getting shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);


        //setting data to variables
        String zipCode = sharedPreferences.getString("zipCode","");
        String tempType = sharedPreferences.getString("tempType","");


        //testing math with toast
        String toastTest = "User Zip Code: " + zipCode + " Temp Preference : " + tempType;
        Toast.makeText(getApplicationContext(),toastTest ,Toast.LENGTH_LONG).show();

    }
}