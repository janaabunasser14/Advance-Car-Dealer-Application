package com.example.project;

import android.content.Intent;
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

import java.util.ArrayList;

public class activity_main_list extends Fragment implements AdapterView.OnItemSelectedListener {
    activity_main_list binding;
    private ListCarAdapter listAdapter;
    private ArrayList<ListCar> dataArrayList = new ArrayList<>();
    private ArrayList<CarType> dataArrayList2 = new ArrayList<>();
    private ListCar listData;
    private Spinner searchTypeSpinner;
    private String theListFromActivity;
    private TextView dataTextView;
    String[] search_types = {"Price", "Model", "type", "fuel", "transmission", "miles"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_list, container, false);

        if (getArguments() != null) {
            dataArrayList2 = getArguments().getParcelableArrayList("carList");
        }

        searchTypeSpinner = view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.search_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] imageList = {R.drawable.grandcaravancar, R.drawable.grandcherokeecar, R.drawable.volt_car, R.drawable.wrangler_car, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon, R.drawable.car_icon};
        int[] ingredientList = {R.string.pastaIngredients, R.string.maggiIngredients, R.string.cakeIngredients, R.string.pancakeIngredients, R.string.pizzaIngredients, R.string.burgerIngredients, R.string.friesIngredients};
        int[] descList = {R.string.pastaDesc, R.string.maggieDesc, R.string.cakeDesc, R.string.pancakeDesc, R.string.pizzaDesc, R.string.burgerDesc, R.string.friesDesc};
        int arr[] = {120, 180, 250, 320, 140, 420, 380, 200, 470, 300, 150, 410, 280, 490, 350, 420, 160, 450, 230, 370, 160, 450, 230, 370};
        String[] fuelTypeList = {"Gasoline", "Diesel", "Electric", "Hybrid"};
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        int[] arrMile = {120, 180, 250, 320, 1100, 3457, 890, 900, 1290, 754, 1200};
        String[] transmissionList = {"Automatic", "Manual", "CVT"};
        // Assuming dataArrayList2 has the same size as the other arrays
        for (int i = 0; i < dataArrayList2.size(); i++) {
            listData = new ListCar(dataArrayList2.get(i).getType(), dataArrayList2.get(i).getId(), R.string.pastaIngredients, arr[i], imageList[i], fuelTypeList[i % 3], arrMile[i % 10], transmissionList[i % 2]);
            dataArrayList.add(listData);
            dbHelper.addCar(getContext(),dataArrayList2.get(i).getId(), dataArrayList2.get(i).getType(), 12345, arr[i], transmissionList[i % 2], arrMile[i % 10], fuelTypeList[i % 3], "model" + dataArrayList2.get(i).getType());

        }
        listAdapter = new ListCarAdapter(requireActivity(), dataArrayList);
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(listAdapter);

        ImageButton searchButton = view.findViewById(R.id.imageButton2);
        EditText searchEditText = view.findViewById(R.id.editTextText);
        searchTypeSpinner.setOnItemSelectedListener(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSearchType = search_types[searchTypeSpinner.getSelectedItemPosition()];
                String searchValue = searchEditText.getText().toString();
                if (searchValue.isEmpty()) {
                    for (int i = 0; i < dataArrayList2.size(); i++) {
                        listData = new ListCar(dataArrayList2.get(i).getType(), dataArrayList2.get(i).getId(), R.string.pastaIngredients, arr[i], imageList[i], fuelTypeList[i % 3], arrMile[i % 10], transmissionList[i % 2]);
                        dataArrayList.add(listData);
                    }
                    listAdapter = new ListCarAdapter(requireActivity(), dataArrayList);
                    ListView listView = view.findViewById(R.id.listview);
                    listView.setAdapter(listAdapter);
                    return;  // Exit the onClick method
                }
//                    // Perform the search and update the dataArrayList
                ArrayList<ListCar> filteredList = new ArrayList<>();

                for (ListCar car : dataArrayList) {
//                            // Numeric condition for Price
                    if (selectedSearchType.equals("Price")) {
                        try {
                            int searchIntValue = Integer.parseInt(searchValue);
                            if (car.getDesc() < searchIntValue) {
                                filteredList.add(car);
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
//
                    else if (selectedSearchType.equals("type")) {
                        try {

                            if (car.getTypec().toLowerCase().trim().equals(searchValue.trim())) {
                                filteredList.add(car);
                            }
                        } catch (NumberFormatException e) {
                            // Handle parsing error if needed
                        }
                    } else if (selectedSearchType.trim().equals("miles")) {
                        try {
                            int searchIntValue = Integer.parseInt(searchValue);
                            if (car.getMile() < searchIntValue) {
                                filteredList.add(car);
                            }
                        } catch (NumberFormatException e) {
                            // Handle parsing error if needed
                        }
                    }
                    if (selectedSearchType.equals("fuel")) {
                        if (car.getFuel().equalsIgnoreCase(searchValue)) {
                            filteredList.add(car);
                        }
                    }

                    if (selectedSearchType.equals("transmission")) {
                        if (car.getTran().equalsIgnoreCase(searchValue)) {
                            filteredList.add(car);
                        }
                    }
                }
                listAdapter.clear();
                listAdapter.addAll(filteredList);
                listAdapter.notifyDataSetChanged();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



