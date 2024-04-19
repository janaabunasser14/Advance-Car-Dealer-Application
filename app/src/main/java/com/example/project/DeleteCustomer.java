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

/**

 */public class DeleteCustomer extends Fragment {

    private EditText etEmail;
    private Button buttonDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_customer, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        buttonDelete = view.findViewById(R.id.buttonAdd);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                deleteCustomer(email);
            }
        });

        return view;
    }


    private void deleteCustomer(String email) {
        DatabaseHelper db = new DatabaseHelper(getContext());

        boolean isDeleted = db.deleteCustomer(email);
        if (isDeleted) {
            sendCustomerDeletedNotification(email);
            Toast.makeText(getContext(), "Customer deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to delete customer", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendCustomerDeletedNotification(String email) {
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("delete_customer_channel", "Customer Deletion", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notifications for deleted customers");
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "delete_customer_channel")
                .setSmallIcon(R.drawable.delete_icon) // replace with your own icon
                .setContentTitle("Customer Deleted")
                .setContentText("Customer with email: " + email + " has been deleted.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        notificationManager.notify(1, builder.build()); // '1' is a unique int for the notification
    }

}
