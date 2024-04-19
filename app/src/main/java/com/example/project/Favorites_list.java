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

public class Favorites_list extends Fragment implements AdapterView.OnItemSelectedListener {
    Favorites_list binding;
    private ListCarAdapter listAdapter;
    private ArrayList<ListCar> dataArrayList = new ArrayList<>();
    private ArrayList<CarType> dataArrayList2 = new ArrayList<>();
    private ListCar listData;
    private Spinner searchTypeSpinner;
    private String theListFromActivity;
    private TextView dataTextView;
    String[] search_types = {"Price"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_list, container, false);

        // Retrieve the list from the arguments
        if (getArguments() != null) {
            dataArrayList2 = getArguments().getParcelableArrayList("carList");
        }

        searchTypeSpinner = view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.search_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(adapter);
        // Usage example to add a user


        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize dataArrayList and listAdapter
        dataArrayList = new ArrayList<>();
        listAdapter = new ListCarAdapter(requireActivity(), dataArrayList);

        // Retrieve favorite cars' information from the database
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        ListCar listData;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DatabaseHelper.COLUMN_CAR_ID_FAVORITE};
        String selection = DatabaseHelper.COLUMN_USER_EMAIL_FAVORITE + "=?";
        String[] selectionArgs = {User.getInstance().getEmail()};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_FAVORITES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int columnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CAR_ID_FAVORITE);
                if (columnIndex != -1) {
                    String carId = cursor.getString(columnIndex);
                    Car car = dbHelper.getCarById(getContext(),Integer.valueOf(carId));
                                if (car != null) {
                                    //Toast.makeText(getContext(),car.getModel()+""+car.getRentalPrice(),Toast.LENGTH_SHORT).show();
                //    public ListCar(String name, String id, int ingredients, int desc, int image, String fuel, int mile, String tran) {
                listData = new ListCar(
                        car.getModel().substring(5), // You may need to modify this based on your Car class structure
                        String.valueOf(car.getId()),
                        1234,
                        (int)car.getRentalPrice(),
                        R.drawable.car_icon,
                        car.getFuelType(),
                        car.getMileage(),
                        car.getTransmission()
                );

                dataArrayList.add(listData);
            }
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(listAdapter);



//
//        // Loop through favoriteCars and create ListCar objects
//        for (FavoriteCar favoriteCar : favoriteCars) {
//            Car car = dbHelper.getCarById(favoriteCar.getCarId()); // Create a method to fetch car details by ID
//            if (car != null) {
//                //    public ListCar(String name, String id, int ingredients, int desc, int image, String fuel, int mile, String tran) {
//                listData = new ListCar(
//                        car.getName(), // You may need to modify this based on your Car class structure
//                        String.valueOf(car.getId()),
//                        1234,
//                        car.getNumber(),
//                        R.drawable.car_icon,
//                        car.getFuelType(),
//                        car.getMileage(),
//                        car.getTransmission()
//                );
//
//                dataArrayList.add(listData);
//            }
//        }
//
//        // Initialize the ListView and set the adapter
//        ListView listView = view.findViewById(R.id.listview);
//        listView.setAdapter(listAdapter);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        for (int i = 0; i < dataArrayList2.size(); i++) {
//            listData = new ListCar(dataArrayList2.get(i).getType(), dataArrayList2.get(i).getId(), R.string.pastaIngredients, arr[i], imageList[i], fuelTypeList[i % 3], arrMile[i % 10], transmissionList[i % 2]);
//            dataArrayList.add(listData);
//        }
//        listAdapter = new ListCarAdapter(requireActivity(), dataArrayList);
//        ListView listView = view.findViewById(R.id.listview);
//        listView.setAdapter(listAdapter);
//
//        ImageButton searchButton = view.findViewById(R.id.imageButton2);
//        EditText searchEditText = view.findViewById(R.id.editTextText);
//        searchTypeSpinner.setOnItemSelectedListener(this);
//
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String selectedSearchType = search_types[searchTypeSpinner.getSelectedItemPosition()];
//                String searchValue = searchEditText.getText().toString();
//                if (searchValue.isEmpty()) {
//                    for (int i = 0; i < dataArrayList2.size(); i++) {
//                        listData = new ListCar(dataArrayList2.get(i).getType(), dataArrayList2.get(i).getId(), R.string.pastaIngredients, arr[i], imageList[i], fuelTypeList[i % 3], arrMile[i % 10], transmissionList[i % 2]);
//                        dataArrayList.add(listData);
//                    }
//                    listAdapter = new ListCarAdapter(requireActivity(), dataArrayList);
//                    ListView listView = view.findViewById(R.id.listview);
//                    listView.setAdapter(listAdapter);
//                    return;  // Exit the onClick method
//                }
////                    // Perform the search and update the dataArrayList
//                ArrayList<ListCar> filteredList = new ArrayList<>();
//
//                for (ListCar car : dataArrayList) {
////                            // Numeric condition for Price
//                    if (selectedSearchType.equals("Price")) {
//                        try {
//                            int searchIntValue = Integer.parseInt(searchValue);
//                            if (car.getDesc() < searchIntValue) {
//                                filteredList.add(car);
//                            }
//                        } catch (NumberFormatException e) {
//                        }
//                    }
////
//
//                }
//                listAdapter.clear();
//                listAdapter.addAll(filteredList);
//                listAdapter.notifyDataSetChanged();
//            }
//        });
//
//
//    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       String text= parent.getItemAtPosition(position).toString();
     //  Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
 //   @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Button yourButton = list.findViewById(R.id.your_button_id);
//        yourButton.setFocusable(false);
//        yourButton.setClickable(false);
//        yourButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle button click
//                // You can use the position parameter of the getView() method to know which item's button was clicked
//            }
//        });
//    }
}
/*
* package com.example.annascott.buttondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an ArrayList of Dessert objects
        final ArrayList<Dessert> desserts = new ArrayList<Dessert>();

        desserts.add(new Dessert("Donut", 0, R.drawable.doughnut));
        desserts.add(new Dessert("Cookie", 0, R.drawable.cookie));
        desserts.add(new Dessert("PieceOfCake", 0, R.drawable.piece_of_cake));
        desserts.add(new Dessert("Pastry", 0, R.drawable.pastry));

        // Create an {@link DessertAdapter}, whose data source is a list of
        // {@link Dessert}s. The adapter knows how to create list item views for each item
        // in the list.
        DesertAdapter flavorAdapter = new DesertAdapter(this, desserts);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.listview_dessert);
        listView.setAdapter(flavorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Dessert dessert = desserts.get(i);
                switch(i) {
                    case 0:
                        Intent donut = new Intent(MainActivity.this, Donut.class);
                        startActivity(donut);
                        break;
                    case 1:
                        Intent cookie = new Intent(MainActivity.this, Cookie.class);
                        startActivity(cookie);
                        break;
                    case 2:
                        Intent pieceOfCake = new Intent(MainActivity.this, PieceOfCake.class);
                        startActivity(pieceOfCake);
                        break;
                    case 3:
                        Intent pastry = new Intent(MainActivity.this, Pastry.class);
                        startActivity(pastry);
                        break;
                }
    }
});
    }
}
*
*
* */


