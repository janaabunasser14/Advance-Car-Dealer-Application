package com.example.project;
//
//import android.content.Context;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.Button;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import java.util.ArrayList;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentTransaction;
//
//public class ListCarAdapter extends ArrayAdapter<ListCar> {
//    private static final String LOG_TAG = ListCarAdapter.class.getSimpleName();
//    private Context context;
//    boolean isPhoto1 = true;
//
//    public ListCarAdapter(@NonNull FragmentActivity context, ArrayList<ListCar> dataArrayList) {
//        super(context, R.layout.list_item, dataArrayList);
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        ListCar listData = getItem(position);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
//        }
//
//        ImageView listImage = convertView.findViewById(R.id.listImage);
//        Button listType = convertView.findViewById(R.id.listName);
//        TextView listId = convertView.findViewById(R.id.id);
//        TextView price = convertView.findViewById(R.id.Cost);
//        Button rentButton = convertView.findViewById(R.id.Reserve); // Assuming you have a button with this ID in your layout
//        ImageButton Fav=convertView.findViewById(R.id.Fav);
//        // Set the data to your views
//        listImage.setImageResource(listData.getImage());
//        listType.setText(listData.getTypec());
//        price.setText("Rent :" + listData.getDesc() + "$");
//        listId.setText("ID :" + listData.getId());
//        DatabaseHelper databaseHelper = new DatabaseHelper(this); // استبدل "this" بالسياق المناسب إذا كان ذلك ضروريًا
//        String userEmail =User.getInstance().getEmail();
//        String carId =listData.getId();
//
//        boolean isFavorite = databaseHelper.isCarFavorite(userEmail, carId);
//
//        if (isFavorite) {
//            Fav.setImageResource(R.drawable.favorite_icon);
//        } else {
//            Fav.setImageResource(R.drawable.heart_icon);
//        }
//
//
//        // Set an OnClickListener for the button
//        listType.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Create an Intent to start DetailedActivity
//                    DetailedFragment profileFragment = new DetailedFragment();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", listData.getId());
//                    bundle.putString("Fuel", listData.getFuel());
//                    bundle.putString("Tran", listData.getTran());
//                    bundle.putString("Typec", listData.getTypec());
//                    bundle.putInt("Mile", listData.getMile());
//                    bundle.putInt("pri", listData.getDesc());
//                    profileFragment.setArguments(bundle);
//                   // Toast.makeText(getContext(), "Rent button clicked for ID: " + listData.getTypec(), Toast.LENGTH_SHORT).show();
//
////                    // Use the FragmentManager to replace the fragment
//                   FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, profileFragment)
//                            .addToBackStack(null)
//                            .commit();
//                }
//            });
//
//
//        rentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    // Create an Intent to start DetailedActivity
//                    ReservationFragment profileFragment = new ReservationFragment();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", listData.getId());
//                    bundle.putString("Fuel", listData.getFuel());
//                    bundle.putString("Tran", listData.getTran());
//                    bundle.putString("Typec", listData.getTypec());
//                    bundle.putInt("Mile", listData.getMile());
//                bundle.putInt("pri", listData.getDesc());
//
//                profileFragment.setArguments(bundle);
//                    Toast.makeText(getContext(), "Rent button clicked for ID: " + listData.getDesc(), Toast.LENGTH_SHORT).show();
//
////                    // Use the FragmentManager to replace the fragment
//                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, profileFragment)
//                            .addToBackStack(null)
//                            .commit();
//                }
//        });
//
//
//        Fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Toggle the boolean flag
//                isPhoto1 = !isPhoto1;
//
//                // Change the image based on the current state
//                if (isPhoto1) {
//                    Fav.setImageResource(R.drawable.favorite_icon);
//                } else {
//                    Fav.setImageResource(R.drawable.heart_icon);
//                }
//
//
//            }
//        });
//
//        return convertView;
//    }
//
//
//
//
//
//
//
//
//}
//

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.DatabaseHelper;

import java.util.ArrayList;

public class ListCarAdapter extends ArrayAdapter<ListCar> {
    private Context context;
    boolean isPhoto1 = true;

    public ListCarAdapter(@NonNull FragmentActivity context, ArrayList<ListCar> dataArrayList) {
        super(context, R.layout.list_item, dataArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListCar listData = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        ImageView listImage = convertView.findViewById(R.id.listImage);
        Button listType = convertView.findViewById(R.id.listName);
        TextView listId = convertView.findViewById(R.id.id);
        TextView price = convertView.findViewById(R.id.Cost);
        Button rentButton = convertView.findViewById(R.id.Reserve);
        ImageButton Fav = convertView.findViewById(R.id.Fav);

        listImage.setImageResource(listData.getImage());
        listType.setText(listData.getTypec());
        price.setText("Rent: " + listData.getDesc() + "$");
        listId.setText("ID: " + listData.getId());

        String userEmail = User.getInstance().getEmail();
        String carId = listData.getId();

        boolean isFavorite = isCarFavorite(userEmail, carId);

        if (isFavorite) {
            Fav.setImageResource(R.drawable.favorite_icon);
        } else {
            Fav.setImageResource(R.drawable.heart_icon);
        }

        listType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailedFragment profileFragment = new DetailedFragment();

                Bundle bundle = new Bundle();
                bundle.putString("id", listData.getId());
                bundle.putString("Fuel", listData.getFuel());
                bundle.putString("Tran", listData.getTran());
                bundle.putString("Typec", listData.getTypec());
                bundle.putInt("Mile", listData.getMile());
                bundle.putInt("pri", listData.getDesc());
                profileFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationFragment profileFragment = new ReservationFragment();

                Bundle bundle = new Bundle();
                bundle.putString("id", listData.getId());
                bundle.putString("Fuel", listData.getFuel());
                bundle.putString("Tran", listData.getTran());
                bundle.putString("Typec", listData.getTypec());
                bundle.putInt("Mile", listData.getMile());
                bundle.putInt("pri", listData.getDesc());

                profileFragment.setArguments(bundle);
                Toast.makeText(getContext(), "Rent button clicked for ID: " + listData.getDesc(), Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPhoto1 = !isPhoto1;

                if (!isCarFavorite(userEmail, carId)) {
                    Fav.setImageResource(R.drawable.favorite_icon);
                    addToFavorites(userEmail, carId);
                } else {
                    Fav.setImageResource(R.drawable.heart_icon);
                    removeFromFavorites(userEmail, carId);
                }
            }
        });

        return convertView;
    }

    private boolean isCarFavorite(String userEmail, String carId) {
        boolean isFavorite = false;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_FAVORITES +
                " WHERE " + DatabaseHelper.COLUMN_USER_EMAIL_FAVORITE + "=? AND " +
                DatabaseHelper.COLUMN_CAR_ID_FAVORITE + "=?";

        String[] selectionArgs = {userEmail,carId};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        int numberOfRentals = cursor.getInt(0);
        cursor.close();
        db.close();
        return numberOfRentals != 0;
    }

    private void addToFavorites(String userEmail, String carId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_EMAIL_FAVORITE, userEmail);
        values.put(DatabaseHelper.COLUMN_CAR_ID_FAVORITE, carId);

        long newRowId = db.insert(DatabaseHelper.TABLE_FAVORITES, null, values);
        db.close();

        if (newRowId != -1) {
            // Insertion successful
            Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
        } else {
            // Insertion failed
            Toast.makeText(context, "Failed to add to Favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromFavorites(String userEmail, String carId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String selection = DatabaseHelper.COLUMN_USER_EMAIL_FAVORITE + "=? AND " +
                DatabaseHelper.COLUMN_CAR_ID_FAVORITE + "=?";
        String[] selectionArgs = {userEmail, carId};

        int deletedRows = db.delete(DatabaseHelper.TABLE_FAVORITES, selection, selectionArgs);
        db.close();

        if (deletedRows > 0) {
            // Deletion successful
            Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show();
        } else {
            // Deletion failed
            Toast.makeText(context, "Failed to remove from Favorites", Toast.LENGTH_SHORT).show();
        }
    }




}
