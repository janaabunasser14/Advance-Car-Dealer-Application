package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.example.project.databinding.ActivityMainBinding;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private List<CarType> listOfCar;
    //private String userEmail="tala@gmail.com";
    private User user;
    Button button;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
        connectionAsyncTask.execute("https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types");
        DatabaseHelper dbHelper = new DatabaseHelper(context); // Pass your context here
        Bundle extras = getIntent().getExtras();
        String EmailUser= extras.getString("EmailUser");
        Toast.makeText(this, EmailUser, Toast.LENGTH_SHORT).show();

        user=dbHelper.getUserByEmail(EmailUser);



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawerLayout)
                .build();
        //<include layout="@layout/content_main" />
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    FrameLayout dynamicContentContainer = findViewById(R.id.nav_host_fragment_content_main);
                    View includedLayout = getLayoutInflater().inflate(R.layout.content_main, null);
                    dynamicContentContainer.addView(includedLayout);

                }
                else if (itemId == R.id.nav_call) {
                    initiateCall();
                } else if (itemId == R.id.nav_find) {
                    openGoogleMaps();
                } else if (itemId == R.id.nav_menu) {
                    FrameLayout dynamicContentContainer = findViewById(R.id.nav_host_fragment_content_main);
                    dynamicContentContainer.removeAllViews();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("carList", new ArrayList<>(listOfCar));

                    activity_main_list myFragment = new activity_main_list();
                    myFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment_content_main, myFragment)
                            .addToBackStack(null)
                            .commit();
                } else  if (itemId == R.id.nav_reservation) {
                    FrameLayout dynamicContentContainer = findViewById(R.id.nav_host_fragment_content_main);
                    dynamicContentContainer.removeAllViews();
                    activity_main_reserved_list reserved_list = new activity_main_reserved_list();

                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("userEmail", EmailUser);
                    reserved_list.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, reserved_list)
                            .addToBackStack(null)
                            .commit();
                }
                else  if (itemId == R.id.nav_profile) {
                    profile profileFragment = new profile();
                    FrameLayout dynamicContentContainer = findViewById(R.id.nav_host_fragment_content_main);
                    dynamicContentContainer.removeAllViews();

                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("userEmail", EmailUser);
                    profileFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, profileFragment)
                            .addToBackStack(null)
                            .commit();
                }
                else if(itemId == R.id.nav_favorite){
                    FrameLayout dynamicContentContainer = findViewById(R.id.nav_host_fragment_content_main);
                    dynamicContentContainer.removeAllViews();
                    Favorites_list favorites_list = new Favorites_list();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();

                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, favorites_list)
                            .addToBackStack(null)
                            .commit();
                }
                else if(itemId == R.id.nav_special){
                    FrameLayout dynamicContentContainer = findViewById(R.id.nav_host_fragment_content_main);
                    dynamicContentContainer.removeAllViews();
                    Spicial favorites_list = new Spicial();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();

                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, favorites_list)
                            .addToBackStack(null)
                            .commit();
                }
                else if(itemId == R.id.nav_logout){
                    FrameLayout dynamicContentContainer = findViewById(R.id.nav_host_fragment_content_main);
                    dynamicContentContainer.removeAllViews();
                    Intent intent = new Intent(MainActivity.this, LoginApp.class);
                    startActivity(intent);
                }
                // Handle other menu items if needed
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initiateCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0599000000"));
        startActivity(intent);
    }

    private void openGoogleMaps() {
        // Assuming the coordinates of the car dealer's store are known
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=car+dealer");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void setButtonText(String text) {
        button.setText(text);
    }

    public void fillCar(List<CarType> Car) {

        this.listOfCar = Car;
    }



}
