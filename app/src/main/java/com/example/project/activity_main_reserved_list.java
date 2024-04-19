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

public class activity_main_reserved_list extends Fragment implements AdapterView.OnItemSelectedListener {
    private ListCarAdapter2 listAdapter;
    private ArrayList<ListCarRes> dataArrayList = new ArrayList<>();
    private ListCarRes listData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_reserved_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataArrayList = new ArrayList<>();
        listAdapter = new ListCarAdapter2(requireActivity(), dataArrayList);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        ListCarRes listData;
        List<Rental> rentals = dbHelper.getRentalsForUser(User.getInstance().getEmail());
      //  String Model = dbHelper.getModelCarById(getContext(),Integer.valueOf(carId));

        for (Rental rental : rentals) {
            // Create a ListCarRes object based on the rental data
            listData = new ListCarRes(
                    dbHelper.getModelCarById(getContext(),Integer.valueOf(rental.getCarIdRental())).substring(5),
                    rental.getCarIdRental(),
                    rental.getPaymentAmount(),// Use the appropriate method to get the car ID from the rental object
                    rental.getRentalStartDate(), // Use the appropriate method to get the start date from the rental object
                    rental.getRentalEndDate() // Use the appropriate method to get the end date from the rental object
                    // Use the appropriate method to get the payment amount from the rental object
            );
            dataArrayList.add(listData);
        }
        dbHelper.close();

        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(listAdapter);


        /*
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        User user = User.getInstance();
        String userEmail = user.getEmail();

        // Clear existing data in the adapter
        dataArrayList.clear();

        // Use rawQuery to fetch data directly
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                DatabaseHelper.COLUMN_CAR_TYPE + ", " +
                DatabaseHelper.COLUMN_CAR_ID_RENTAL + ", " +
                DatabaseHelper.COLUMN_PAYMENT_AMOUNT + ", " +
                DatabaseHelper.COLUMN_RENTAL_START_DATE + ", " +
                DatabaseHelper.COLUMN_RENTAL_END_DATE +
                " FROM " + DatabaseHelper.TABLE_RENTALS +
                " WHERE " + DatabaseHelper.COLUMN_USER_EMAIL_RENTAL + " = ?";

        String[] selectionArgs = {userEmail};

        try {
            Cursor cursor = db.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve column indices outside of the loop to avoid repeated calls
                    int typecIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CAR_TYPE);
                    int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CAR_ID_RENTAL);
                    int costIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PAYMENT_AMOUNT);
                    int startDateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_RENTAL_START_DATE);
                    int endDateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_RENTAL_END_DATE);

                    // Check if the indices are valid (not -1) before extracting values
                    if (typecIndex != -1 && idIndex != -1 && costIndex != -1 && startDateIndex != -1 && endDateIndex != -1) {
                        String typec = cursor.getString(typecIndex);
                        String id = cursor.getString(idIndex);
                        double cost = cursor.getDouble(costIndex);
                        String startDateString = cursor.getString(startDateIndex);
                        String endDateString = cursor.getString(endDateIndex);

                        // Convert date strings to Date objects
                        Date startDate = convertStringToDate(startDateString);
                        Date endDate = convertStringToDate(endDateString);

                        ListCarRes rental = new ListCarRes(typec, id, cost, startDate, endDate);
                        dataArrayList.add(rental);
                    }
                } while (cursor.moveToNext());

                cursor.close();
            } else {
                // Handle the case where no rows were returned
                Log.d("MyApp", "No data found in the database for the user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        } finally {
            // Close the cursor and database
            if (db != null) {
                db.close();
            }
        }

        // Initialize and set up the adapter after fetching the data
        listAdapter = new ListCarAdapter2(requireActivity(), dataArrayList);
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(listAdapter);*/
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


