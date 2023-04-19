package com.example.therealweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    //creating variables

    double zipCode;

    String tempType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //grabbing widgets from main activity

        EditText userZipCode = (EditText) findViewById(R.id.input_ZipCode);
        Spinner tempPreference = (Spinner) findViewById(R.id.spinner_Weather);
        Button getWeatherButton = (Button) findViewById(R.id.getWeatherButton);

        //grabbing user input and setting it to variables so it can be passed in shard preferences

        zipCode = Integer.parseInt(userZipCode.getText().toString());

        String theUserZipCode = new Double(zipCode).toString();

        tempType = tempPreference.getSelectedItem().toString();


        //creating the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //on click listener for button to pass data to next activity
        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //passing data to next activity via shared preferences
                editor.putString("zipCode",theUserZipCode);
                editor.putString("tempType",tempType);

                //starting next activity
                startActivity(new Intent(MainActivity.this,DisplayWeather.class));
            }
        });



    }


}