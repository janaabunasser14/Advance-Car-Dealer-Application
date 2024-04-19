package com.example.project;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//import androidx.fragment.app.Fragment;
//
//import java.util.Calendar;
//import java.util.Locale;
//
//public class ReservationFragment extends Fragment {
//
//    private DatePicker startDatePicker;
//    private DatePicker endDatePicker;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_reservation, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        String carId = getArguments().getString("idCar");
//        User user=User.getInstance();
//        String idUser=user.getEmail();
//        startDatePicker = view.findViewById(R.id.startDatePicker);
//        endDatePicker = view.findViewById(R.id.endDatePicker);
//
//        Button confirmReservationButton = view.findViewById(R.id.confirmReservationButton);
//
//        confirmReservationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get the selected dates from the DatePickers
//                int startDay = startDatePicker.getDayOfMonth();
//                int startMonth = startDatePicker.getMonth() + 1; // Month is zero-based
//                int startYear = startDatePicker.getYear();
//
//                int endDay = endDatePicker.getDayOfMonth();
//                int endMonth = endDatePicker.getMonth() + 1;
//                int endYear = endDatePicker.getYear();
//
//                // Create date strings in your desired format
//                String startDate = String.format(Locale.US, "%02d/%02d/%04d", startMonth, startDay, startYear);
//                String endDate = String.format(Locale.US, "%02d/%02d/%04d", endMonth, endDay, endYear);
//
//                // Display the selected dates or perform further actions
//                showSelectedDates(startDate, endDate);
//            }
//        });
//
//    }
//
//    private void showSelectedDates(String startDate, String endDate) {
//        // You can display the selected dates in a dialog or any other way you prefer
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Selected Dates");
//        builder.setMessage("Start Date: " + startDate + "\nEnd Date: " + endDate);
//        builder.setPositiveButton("OK", null);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//}
//
//
//
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.project.DatabaseHelper;
import com.example.project.User;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReservationFragment extends Fragment {

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private String carId;
    private String pri;
    private String tye;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        carId = getArguments().getString("id");
        pri=String.valueOf(getArguments().getInt("pri"));
        tye=getArguments().getString("Typec");

        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startDatePicker = view.findViewById(R.id.startDatePicker);
        endDatePicker = view.findViewById(R.id.endDatePicker);


        Button confirmReservationButton = view.findViewById(R.id.confirmReservationButton);

        confirmReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startDay = startDatePicker.getDayOfMonth();
                int startMonth = startDatePicker.getMonth() + 1; // Month is zero-based
                int startYear = startDatePicker.getYear();
                int endDay = endDatePicker.getDayOfMonth();
                int endMonth = endDatePicker.getMonth() + 1;
                int endYear = endDatePicker.getYear();

                String startDate = String.format(Locale.US, "%02d/%02d/%04d", startMonth, startDay, startYear);
                String endDate = String.format(Locale.US, "%02d/%02d/%04d", endMonth, endDay, endYear);

                if (canCarBeRented(startDate, endDate)) {
                     updateDatabase(startDate, endDate);
                    showConfirmationDialog(startDate, endDate);
                } else {
                    showNotAvailableDialog();
                }
            }
        });
    }

    private boolean canCarBeRented( String startDate, String endDate) {

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity()); // Assuming "this" is the context
         SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_RENTALS +
                " WHERE " + DatabaseHelper.COLUMN_CAR_ID_RENTAL + " = ?" +
                " AND ? BETWEEN " + DatabaseHelper.COLUMN_RENTAL_START_DATE + " AND " + DatabaseHelper.COLUMN_RENTAL_END_DATE +
                " AND ? BETWEEN " + DatabaseHelper.COLUMN_RENTAL_START_DATE + " AND " + DatabaseHelper.COLUMN_RENTAL_END_DATE;

        String[] selectionArgs = {carId, startDate, endDate};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        int numberOfRentals = cursor.getInt(0);
        cursor.close();
        db.close();
        return numberOfRentals == 0;

    }

    private void updateDatabase(String startDate, String endDate) {
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        User user = User.getInstance();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_EMAIL_RENTAL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_CAR_ID_RENTAL, getArguments().getString("id"));
        values.put(DatabaseHelper.COLUMN_RENTAL_START_DATE, startDate);
        values.put(DatabaseHelper.COLUMN_RENTAL_END_DATE, endDate);

        boolean hasSpecialOffer = dbHelper.doesCarHaveSpecialOffer(db, Integer.valueOf(getArguments().getString("id")));

        if (hasSpecialOffer) {
            values.put(DatabaseHelper.COLUMN_PAYMENT_AMOUNT, getArguments().getInt("pri")*0.85);
        }else { values.put(DatabaseHelper.COLUMN_PAYMENT_AMOUNT, getArguments().getInt("pri"));
        }

        try {
            long newRowId = db.insert(DatabaseHelper.TABLE_RENTALS, null, values);

            if (newRowId != -1) {
                Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "", e);
            Toast.makeText(getContext(), " " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }





    private void showConfirmationDialog(String startDate, String endDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean hasSpecialOffer = dbHelper.doesCarHaveSpecialOffer(db, Integer.valueOf(getArguments().getString("id")));
        builder.setTitle("Reservation Confirmation");

        if(hasSpecialOffer){

            pri=Double.valueOf(pri)*0.85 +"";
            builder.setMessage("This Car Have Special Offer 15% \n Car rented successfully!\nStart Date: " + startDate + "\nEnd Date: " + endDate +"\n car rented="+pri);

        }else{
            builder.setMessage("Car rented successfully!\nStart Date: " + startDate + "\nEnd Date: " + endDate+"\n rent ="+pri);
        }

       // builder.setMessage("Car rented successfully!\nStart Date: " + startDate + "\nEnd Date: " + endDate);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showNotAvailableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Reservation Error");
        builder.setMessage("Car is not available for the selected dates.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
