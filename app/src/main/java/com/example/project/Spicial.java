package com.example.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Spicial extends Fragment implements AdapterView.OnItemSelectedListener {
    Favorites_list binding;
    private ListCarAdapter3 listAdapter;
    private ArrayList<ListCarSp> dataArrayList = new ArrayList<>();
    private ListCarSp listData;
    private Spinner searchTypeSpinner;
    private String theListFromActivity;
    private TextView dataTextView;
    String[] search_types = {"Price"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_list, container, false);
        int[] Car= {4,7,5,8,10};
         DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        //dbHelper.applySpecialOfferForCars(getContext(),Car, 15); // 15% discount

        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize dataArrayList and listAdapter
        dataArrayList = new ArrayList<>();
        listAdapter = new ListCarAdapter3(requireActivity(), dataArrayList);

        // Retrieve favorite cars' information from the database
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        ListCarSp listData;
        List<CarSpecialOffer> special=dbHelper.getAllSpecialOfferCars(getActivity());


        for (CarSpecialOffer rental : special) {
            // Create a ListCarRes object based on the rental data
            listData = new ListCarSp(
                    dbHelper.getModelCarById(getContext(),Integer.valueOf(rental.getCarId())).substring(5),
                    rental.getCarId(),
                    rental.getCarId()+"",// Use the appropriate method to get the car ID from the rental object
                    rental.getPriceBefore(), // Use the appropriate method to get the start date from the rental object
                    rental.getPriceAfter() // Use the appropriate method to get the end date from the rental object
                    // Use the appropriate method to get the payment amount from the rental object
            );
            dataArrayList.add(listData);
        }
        dbHelper.close();

        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(listAdapter);

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        //  Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}



