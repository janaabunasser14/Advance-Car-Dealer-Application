package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailedFragment extends Fragment {

    private static final String LABEL_NAME = "Name: ";
    private static final String LABEL_TYPE = "Model: ";
    private static final String LABEL_RENTAL = "Rental Price: ";
    private static final String LABEL_TRANSMISSION = "Transmission: ";
    private static final String LABEL_MILES = "Mileage: ";
    private static final String LABEL_FUEL = "Fuel Type: ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_car, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve the carId from arguments
        String carId = getArguments().getString("id");

        // Set text with constant labels using string resources
        ((TextView) view.findViewById(R.id.Name)).setText(getArguments().getString("Typec"));
        ((ImageView) view.findViewById(R.id.detailImage)).setImageResource(R.drawable.car_icon);
        ((TextView) view.findViewById(R.id.Type)).setText(getArguments().getString("Typec"));
        ((TextView) view.findViewById(R.id.Rental)).setText(String.valueOf(getArguments().getInt("pri")));
        ((TextView) view.findViewById(R.id.transmission)).setText(getArguments().getString("Tran"));
        ((TextView) view.findViewById(R.id.miles)).setText(String.valueOf(getArguments().getInt("Mile")));
        ((TextView) view.findViewById(R.id.fuel)).setText(getArguments().getString("Fuel"));
    }
    // You can also add code to fetch detailed car data using carId here
}



