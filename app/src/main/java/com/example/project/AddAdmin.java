package com.example.project;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAdmin extends Fragment {

    private EditText etEmail;
    private Button buttonAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_admin, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        buttonAdd = view.findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                if (!email.isEmpty()) {
                    addUserAsAdmin(email);
                }
            }
        });

        return view;
    }

    private void addUserAsAdmin(String email) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        // Check if user exists
        User user = db.getUserByEmail(email);
        if (user != null) {
            // User exists, add to admin table
            boolean added = db.addAdmin(email);
            if (added) {
                sendAdminNotification(email);
                Toast.makeText(getContext(), "User added as admin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "User already an admin or error occurred", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendAdminNotification(String email) {
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("admin_channel_id", "Admin Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notifications for new admins");
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "admin_channel_id")
                .setSmallIcon(R.drawable.add_icon) // replace with your own icon
                .setContentTitle("Admin Privileges Granted")
                .setContentText("User " + email + " is now an admin.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        notificationManager.notify(0, builder.build()); // '0' is a unique int for the notification
    }

}
