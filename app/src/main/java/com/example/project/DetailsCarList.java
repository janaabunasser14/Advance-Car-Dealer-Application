package com.example.project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsCarList extends AppCompatActivity {

    // Define constant variables for text labels
    private static final String LABEL_NAME = "Name: ";
    private static final String LABEL_TYPE = "Model: ";
    private static final String LABEL_RENTAL = "Rental Price: ";
    private static final String LABEL_TRANSMISSION = "Transmission: ";
    private static final String LABEL_MILES = "Mileage: ";
    private static final String LABEL_FUEL = "Fuel Type: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detailed_car);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int carId = extras.getInt("id");

            // Set text with constant labels using string resources
            ((TextView) findViewById(R.id.Name)).setText( extras.getString("Typec"));
            ((ImageView) findViewById(R.id.detailImage)).setImageResource(R.drawable.car_icon);
            ((TextView) findViewById(R.id.Type)).setText(extras.getString("Typec"));
            ((TextView) findViewById(R.id.Rental)).setText(""+extras.getString("pri"));
            ((TextView) findViewById(R.id.transmission)).setText( extras.getString("Tran"));
            ((TextView) findViewById(R.id.miles)).setText( extras.getInt("Mile"));
            ((TextView) findViewById(R.id.fuel)).setText(extras.getString("Fuel"));
        }
    }
}
