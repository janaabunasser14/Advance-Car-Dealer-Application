package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.databinding.ActivityAdminFinalBinding;

public class adminFinalActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAdminFinalBinding binding;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminFinalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        String EmailUser= extras.getString("EmailUser");
        setSupportActionBar(binding.appBarAdminFinal.toolbar);
        binding.appBarAdminFinal.fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
        );

        drawerLayout = binding.drawerLayout; // Changed to class member
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawerLayout) // Changed to class member
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin_final);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            FrameLayout dynamicContentContainer = findViewById(R.id.nav_host_fragment_content_admin_final);
            dynamicContentContainer.removeAllViews();


            if (itemId == R.id.nav_home) {
                profile profileFragment = new profile();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();

                bundle.putString("userEmail", EmailUser);
                profileFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_admin_final, profileFragment)
                        .addToBackStack(null)
                        .commit(); // Assuming HomeFragment exists
            } else if (itemId == R.id.nav_reservation) {
                switchFragment(new ALLReserves(), "ALLReserves"); // Assuming ProfileFragment exists

            }else if (itemId == R.id.nav_delete) {
                switchFragment(new DeleteCustomer(), "Delete customer"); // Assuming ProfileFragment exists
            }else if (itemId == R.id.nav_add) {
                switchFragment(new AddAdmin(), "Add Admin"); // Assuming ProfileFragment exists
            }else if (itemId == R.id.nav_logout) {
                Intent intent = new Intent(adminFinalActivity.this, LoginApp.class);
                startActivity(intent);
            }




            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void switchFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_admin_final, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_final, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin_final);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
