package com.aks.stockpile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView summaryTitle, groceryTitleText, groceryText, medicineTitleText, medicineText, errandsText, errandsTitleText;
    private Locale locale;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        navDrawerOptions();
        initializeSharedPrefValues();
        setText();
        onClickListenersForTexts();
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
        return true;
    }

    public void addDrawerConfig(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                                ActionBar actionBar, NavigationView navigationView) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.round_menu_open_black_36dp);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        Menu navMenu = navigationView.getMenu();
        this.addMenuItems(navMenu);
    }

    private void addMenuItems(Menu menu) {
        SubMenu groceriesMenu = menu.addSubMenu("Groceries");
        groceriesMenu.add("View Groceries").setIcon(R.drawable.ic_grocery);
        groceriesMenu.add("Out of Stock Items").setIcon(R.drawable.twotone_error_white_24dp);
        groceriesMenu.add("Add new Grocery Item").setIcon(R.drawable.twotone_add_circle_white_24dp);
        groceriesMenu.add("Update Inventory").setIcon(R.drawable.twotone_edit_white_24dp);
        SubMenu medicinesMenu = menu.addSubMenu("Medicines");
        medicinesMenu.add("View Medicines").setIcon(R.drawable.ic_medicines);
        medicinesMenu.add("Add new Medicine").setIcon(R.drawable.twotone_medical_services_white_24dp);
        medicinesMenu.add("Update Inventory").setIcon(R.drawable.twotone_edit_white_24dp);
        medicinesMenu.add("Schedule Reminder").setIcon(R.drawable.twotone_alarm_add_white_24dp);
        SubMenu errandsMenu = menu.addSubMenu("Errands");
        errandsMenu.add("View Errands").setIcon(R.drawable.ic_errands);
        errandsMenu.add("Add new Errand").setIcon(R.drawable.twotone_add_circle_white_24dp);
        errandsMenu.add("Mark Errand as Done").setIcon(R.drawable.twotone_done_all_white_24dp);
        SubMenu settingsMenu = menu.addSubMenu("Support");
        settingsMenu.add("Settings").setIcon(R.drawable.twotone_settings_white_24dp).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            }
        });
        settingsMenu.add("Help & FAQ").setIcon(R.drawable.twotone_help_center_white_24dp).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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
        summaryTitle = findViewById(R.id.home_summary_tv);
        groceryTitleText = findViewById(R.id.grocery_summary_title_tv);
        groceryText = findViewById(R.id.grocery_summary_tv);
        medicineTitleText = findViewById(R.id.medicine_summary_title_tv);
        medicineText = findViewById(R.id.medicine_summary_tv);
        errandsTitleText = findViewById(R.id.errands_summary_title_tv);
        errandsText = findViewById(R.id.errands_summary_tv);
    }

    private void onClickListenersForTexts() {
        final Intent groceryIntent = new Intent(getApplicationContext(), GroceryActivity.class);
        final Intent medicineIntent = new Intent(getApplicationContext(), MedicineActivity.class);
        final Intent errandsIntent = new Intent(getApplicationContext(), ErrandsActivity.class);

        groceryTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(groceryIntent);
            }
        });
        groceryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(groceryIntent);
            }
        });
        medicineTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(medicineIntent);
            }
        });
        medicineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(medicineIntent);
            }
        });
        errandsTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(errandsIntent);
            }
        });
        errandsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(errandsIntent);
            }

        });

    }

    private void initializeSharedPrefValues() {
        SharedPreferences prefs = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        locale = Locale.forLanguageTag(prefs.getString("locale", "en"));
    }

    private void setText() {
        summaryTitle.setText(R.string.main_summary_title);
        groceryTitleText.setText(R.string.main_grocery_title);
        groceryText.setText(R.string.main_grocery_desc);
        medicineTitleText.setText(R.string.main_medicine_title);
        medicineText.setText(R.string.main_medicine_desc);
        errandsTitleText.setText(R.string.main_errand_title);
        errandsText.setText(R.string.main_errand_desc);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    private void setSpinnerAdapter(String[] locales) {
//        Spinner localeDropdown = findViewById(R.id.locale_dropdown);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locales);
//        localeDropdown.setAdapter(adapter);
//    }
}