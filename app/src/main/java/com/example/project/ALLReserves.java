package com.example.project;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ALLReserves extends Fragment implements AdapterView.OnItemSelectedListener {
    private ListCarAdapter4 listAdapter;
    private ArrayList<ListCarResUser> dataArrayList = new ArrayList<>();
    private ListCarResUser listData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_all_reserved_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        dataArrayList = new ArrayList<>();
        listAdapter = new ListCarAdapter4(requireActivity(), dataArrayList);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        ListCarResUser listData;
        List<Rental> rentals = dbHelper.getAllRentals();
      //  String Model = dbHelper.getModelCarById(getContext(),Integer.valueOf(carId));
//
       for (Rental rental : rentals) {
            // Create a ListCarRes object based on the rental data
            listData = new ListCarResUser(rental.getUserEmailRental(),rental.getCarIdRental(),
                      dbHelper.getModelCarById(getContext(),Integer.valueOf(rental.getCarIdRental())).substring(5),
                      rental.getRentalStartDate(), rental.getRentalEndDate()
                    // Use the appropriate method to get the payment amount from the rental object
            );
          dataArrayList.add(listData);
       }
      dbHelper.close();
//       Toast.makeText(requireContext(), rentals.toString(), Toast.LENGTH_LONG).show();
        ListView listView = view.findViewById(R.id.listview);
       listView.setAdapter(listAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle the parsing error as needed
        }
    }

}


