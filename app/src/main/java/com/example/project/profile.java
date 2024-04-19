package com.example.project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class profile extends Fragment {

    private TextView customerNameTextView;
    private TextView customerGmailTextView;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editPassword;
    private EditText editPhoneNumber;
    private TextView editCity;
    private String userEmail;
    private String mParam1;
    private String mParam2;
    private User user;
    private TextView editCountry;
    private TextView editGender;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnUpdate;
    private User sampleObject;
    private boolean isEditing = false;
    // Pass your context here



    //public profile()
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        customerNameTextView = view.findViewById(R.id.customerName);
        customerGmailTextView = view.findViewById(R.id.customerGmail);
        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        editPassword = view.findViewById(R.id.editPassword);
        editPhoneNumber = view.findViewById(R.id.editPhoneNumber);
        editCity = view.findViewById(R.id.editCity);
        editCountry = view.findViewById(R.id.editCountry);
        editGender =view.findViewById(R.id.editGender);
        btnUpdate=view.findViewById(R.id.btnUpdate);

        if (getArguments() != null) {
            userEmail = getArguments().getString("userEmail");
            customerNameTextView.setText(userEmail);
            user=dbHelper.getUserByEmail(userEmail);


            if (user != null) {
                customerNameTextView.setText(user.getFirstName()+" "+user.getLastName());
                customerGmailTextView.setText(user.getEmail());
                editFirstName.setText(user.getFirstName());
                editLastName.setText(user.getLastName());
                editPassword.setText(user.getPassword());
                editPhoneNumber.setText(user.getPhoneNumber());
                editCity.setText(user.getCity());
                editCountry.setText(user.getCountry());
                editGender.setText(user.getGender());
            }
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    while(!validateInput(editFirstName.getText().toString(),editLastName.getText().toString(),editPassword.getText().toString(),editPhoneNumber.getText().toString())){
                    // Save changes
                    saveChanges();
                    // Disable editing
                    disableEditing();
                    }
                    btnUpdate.setText("Edit");
                } else {
                    // Enable editing
                    enableEditing();
                    btnUpdate.setText("Save");
                }
                // Toggle the editing flag
                isEditing = !isEditing;
            }
        });

        return view;
        }

    private void saveChanges() {
        // Assuming you have a method in your DatabaseHelper to update user information
        if (user != null) {
            user.setFirstName(editFirstName.getText().toString());
            user.setLastName(editLastName.getText().toString());
            user.setPassword(editPassword.getText().toString());
            user.setPhoneNumber(editPhoneNumber.getText().toString());
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity());

            // Update the user in the database
             dbHelper.updateUser(user);
        }
    }

    private void disableEditing() {
        editFirstName.setEnabled(false);
        editLastName.setEnabled(false);
        editPassword.setEnabled(false);
        editPhoneNumber.setEnabled(false);
    }

    private void enableEditing() {
        editFirstName.setEnabled(true);
        editLastName.setEnabled(true);
        editPassword.setEnabled(true);
        editPhoneNumber.setEnabled(true);

    }



    private boolean validateInput( String firstName, String lastName, String password, String phoneNumber) {
        // Check if any field is empty
        if (firstName.isEmpty() || lastName.isEmpty()
                || password.isEmpty() ||  phoneNumber.isEmpty()) {
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
//        if (!password.equals(confirmPassword)) {
//            showToast("Passwords do not match.");
//            return false;
//        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}




