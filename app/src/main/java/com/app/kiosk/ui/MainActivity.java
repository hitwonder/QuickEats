package com.app.kiosk.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.app.kiosk.R;
import com.app.kiosk.util.Utils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar); // Initialize Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Find the NavHostFragment and get the NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

        // Set a listener for navigation changes
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.mainFragment) {
                setTitle("QuickEats");
            } else if (destination.getId() == R.id.itemDetailFragment) {
                setTitle("Item Detail");
            } else if (destination.getId() == R.id.orderSummaryFragment) {
                setTitle("Order Summary");
            } else if (destination.getId() == R.id.paymentFragment) {
                setTitle("Payment");
            } else {
                setTitle("Default Title");
            }

            updateActionBar(destination);
            invalidateOptionsMenu();
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    private void updateActionBar(NavDestination destination) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show back button only if the current fragment is not mainFragment
            if (destination.getId() == R.id.mainFragment) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Check if the current destination is mainFragment
        if (navController.getCurrentDestination() != null &&
                navController.getCurrentDestination().getId() == R.id.mainFragment) {
            menu.findItem(R.id.action_shop).setVisible(true);
            menu.findItem(R.id.action_fav_list).setVisible(true);
            menu.findItem(R.id.action_fav).setVisible(false);
        } else if (navController.getCurrentDestination() != null &&
                navController.getCurrentDestination().getId() == R.id.itemDetailFragment) {

            menu.findItem(R.id.action_shop).setVisible(false);
            menu.findItem(R.id.action_fav).setVisible(true);
            menu.findItem(R.id.action_fav_list).setVisible(false);
        } else {
            menu.findItem(R.id.action_shop).setVisible(false);
            menu.findItem(R.id.action_fav).setVisible(false);
            menu.findItem(R.id.action_fav_list).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_shop) {
            ShopCart();
            return true;
        } else if (id == R.id.action_fav_list) {
            Utils.INSTANCE.showShortToast(this, "Coming soon...");
            return true;
        } else if (id == android.R.id.home) {
            // Handle the back button action
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ShopCart() {
        navController.navigate(R.id.orderSummaryFragment);
    }

}
