package com.example.project;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText etEmail, etFirstName, etLastName, etPasswordSignup, etConfirmPassword, etPhoneNumber;
    private Spinner spinnerGender, spinnerCountry, spinnerCity;
    private Button btnRegister;

    private Map<String, List<String>> countryCityMap;
    private Map<String, String> countryAreaCodeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etEmail = findViewById(R.id.etEmail);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPasswordSignup = findViewById(R.id.etPasswordSignup);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerCity = findViewById(R.id.spinnerCity);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialize the country-city mapping and area code mapping
        initializeData();

        // Create an ArrayAdapter for the gender spinner with "Male" and "Female" options
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select Gender" ,"Male", "Female"}
        );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);
        spinnerGender.setSelection(genderAdapter.getPosition("Select Gender"));

        // Create an ArrayAdapter for the country spinner with your country list
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new ArrayList<>(countryCityMap.keySet())
        );
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(countryAdapter);
        spinnerCountry.setSelection(countryAdapter.getPosition("Select Country"));


        // Set a listener to the country spinner to populate the city spinner
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCountry = spinnerCountry.getSelectedItem().toString();
                List<String> cities = countryCityMap.get(selectedCountry);

                if (cities != null) {
                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                            SignupActivity.this,
                            android.R.layout.simple_spinner_item,
                            cities
                    );

                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCity.setAdapter(cityAdapter);

                    // Set the area code (pre-zip code) based on the selected country
                    String areaCode = countryAreaCodeMap.get(selectedCountry);
                    if (areaCode != null) {
                        etPhoneNumber.setText(countryAreaCodeMap.get(selectedCountry));
                    } else {
                        etPhoneNumber.setHint("Phone Number");
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Handle the registration button click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String gender = spinnerGender.getSelectedItem().toString();
                String password = etPasswordSignup.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String country = spinnerCountry.getSelectedItem().toString();
                String city = spinnerCity.getSelectedItem().toString();
                String phoneNumber = etPhoneNumber.getText().toString();

                if (validateInput(email, firstName, lastName, gender, password, confirmPassword, country, city, phoneNumber)) {
                    if (registerUser(email, firstName, lastName, gender, password, country, city, phoneNumber)) {
                        showToast("Registration successful. You can now log in.");

                        // Navigate back to the login activity
                        finish();
                    } else {
                        showToast("Registration failed. Please try again.");
                    }
                } else {
                    showToast("Please fill in all fields with valid information.");
                }
            }
        });
    }

    private boolean validateInput(String email, String firstName, String lastName, String gender, String password, String confirmPassword, String country, String city, String phoneNumber) {
        // Check if any field is empty
        if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty() || country.isEmpty() || city.isEmpty() || phoneNumber.isEmpty()) {
            showToast("Please fill in all fields with valid information.");
            return false;
        }

        // Check if password is less than 5 characters
        if (password.length() < 5) {
            showToast("Password must be at least 5 characters long.");
            return false;
        }

        // Check if password contains at least 1 character, 1 number, and 1 special character
        if (!password.matches(".*[a-zA-Z]+.*") || !password.matches(".*\\d+.*") || !password.matches(".*[@#$%^&+=_-]+.*")) {
            showToast("Password must include at least 1 character, 1 number, and 1 special character.");
            return false;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match.");
            return false;
        }

        return true;
    }

    private boolean registerUser(String email, String firstName, String lastName, String gender, String password, String country, String city, String phoneNumber) {


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String tableName = DatabaseHelper.TABLE_USER;
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_FIRST_NAME, firstName);
        values.put(DatabaseHelper.COLUMN_LAST_NAME, lastName);
        values.put(DatabaseHelper.COLUMN_GENDER, gender);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.COLUMN_COUNTRY, country);
        values.put(DatabaseHelper.COLUMN_CITY, city);
        values.put(DatabaseHelper.COLUMN_PHONE_NUMBER, phoneNumber);

        long newRowId = database.insert(tableName, null, values);

        if (newRowId != -1) {
            // User registration successful
            database.close();
            return true;
        } else {
            // User registration failed
            database.close();
            return false;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initializeData() {
        // Initialize the country-city mapping
        countryCityMap = new HashMap<>();
        countryCityMap.put("Palestine", new ArrayList<>(Arrays.asList("Ramallah", "Nablus", "Hebron", "Bethlehem")));
        countryCityMap.put("Jordan", new ArrayList<>(Arrays.asList("Amman", "Irbid", "Zarqa", "Aqaba")));
        countryCityMap.put("Syria", new ArrayList<>(Arrays.asList("Damascus", "Latakia", "Aleppo", "Homs")));
        countryCityMap.put("Lebanon", new ArrayList<>(Arrays.asList("Beirut", "Batroun", "Baalbek", "Tripoli")));
        countryCityMap.put("Select Country", new ArrayList<>(Arrays.asList("Select City")));

        // Initialize the area code mapping
        countryAreaCodeMap = new HashMap<>();
        countryAreaCodeMap.put("Palestine", "00970");
        countryAreaCodeMap.put("Jordan", "00962");
        countryAreaCodeMap.put("Syria", "00963");
        countryAreaCodeMap.put("Lebanon", "00961");
    }
}