package com.aks.stockpile.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.adapters.HomePageAdapter;
import com.aks.stockpile.models.dtos.HomeCardDto;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkForFirstLaunch();
        setLayoutFields();
        navDrawerOptions();
        initializeRecyclerView();
    }

    private void checkForFirstLaunch() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean("IS_FIRST_LAUNCH", true);
        if (isFirstLaunch) {
            Intent initIntent = new Intent(this, InitializerActivity.class);
            startActivity(initIntent);
        }
    }

    private void navDrawerOptions() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        addDrawerConfig(this, drawerLayout, toolbar,
                getSupportActionBar(), navigationView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.getItem(0).getIcon().setColorFilter(getResources().getColor(R.color.item_on_primary, getTheme()), PorterDuff.Mode.SRC_ATOP);
        return true;
    }

    public void addDrawerConfig(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                                ActionBar actionBar, NavigationView navigationView) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBar.setDisplayHomeAsUpEnabled(true);
        final Drawable navIcon = getResources().getDrawable(R.drawable.round_menu_open_black_36dp, getTheme());
        navIcon.setColorFilter(getResources().getColor(R.color.item_on_primary, getTheme()), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(navIcon);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        Menu navMenu = navigationView.getMenu();
        this.addMenuItems(navMenu);
    }

    private void addMenuItems(Menu menu) {
        SubMenu settingsMenu = menu.addSubMenu("Support");
        settingsMenu.add("Settings").setIcon(R.drawable.settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            }
        });
        settingsMenu.add("Help & FAQ").setIcon(R.drawable.help).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                return true;
            }
        });
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.main_toolbar);
        recyclerView = findViewById(R.id.home_recycle);
    }

    private void initializeRecyclerView() {
        HomePageAdapter homePageAdapter = new HomePageAdapter(this, HomeCardDto.ofData());
        recyclerView.setAdapter(homePageAdapter);
        int orientationValue = getResources().getConfiguration().orientation;
        if (orientationValue == 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
        if (orientationValue == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Leaving?")
                .setMessage("Are you sure you want to exit the app?")
                .setCancelable(false)
                .setNegativeButton("Exit the app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton("Stay in the app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }
}