package com.aks.stockpile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;

    private TextView summaryTitle, groceryTitleText, groceryText, medicineTitleText, medicineText, errandsText, errandsTitleText;
    private Locale locale;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.round_menu_open_black_36dp);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        navView.setNavigationItemSelectedListener(this);


        Menu navMenu = navView.getMenu();

        SubMenu groceriesMenu = navMenu.addSubMenu("Groceries");
        groceriesMenu.add("Out of Stock Items").setIcon(R.drawable.twotone_error_black_24dp);
        groceriesMenu.add("Items to be Refilled").setIcon(R.drawable.twotone_warning_black_24dp);
        groceriesMenu.add("Add new Grocery Item").setIcon(R.drawable.twotone_add_circle_black_24dp);
        groceriesMenu.add("Update Inventory").setIcon(R.drawable.twotone_edit_black_24dp);
        SubMenu medicinesMenu = navMenu.addSubMenu("Medicines");
        medicinesMenu.add("Add new Medicine").setIcon(R.drawable.twotone_medical_services_black_24dp);
        medicinesMenu.add("Update Inventory").setIcon(R.drawable.ic_medicines);
        medicinesMenu.add("Schedule Reminder").setIcon(R.drawable.twotone_alarm_add_black_24dp);
        SubMenu errandsMenu = navMenu.addSubMenu("Errands");
        errandsMenu.add("Add new Errand").setIcon(R.drawable.twotone_add_circle_black_24dp);
        errandsMenu.add("Mark Errand as Done").setIcon(R.drawable.twotone_done_all_black_24dp);
        errandsMenu.add("View Errands").setIcon(R.drawable.ic_errands);
        SubMenu settingsMenu = navMenu.addSubMenu("Settings");
        settingsMenu.add("Configuration").setIcon(R.drawable.twotone_settings_black_24dp);

        initializeSharedPrefValues();
//        setText();
//        onClickListenersForTexts();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.main_toolbar);
//        summaryTitle = findViewById(R.id.home_summary_tv);
//        groceryTitleText = findViewById(R.id.grocery_summary_title_tv);
//        groceryText = findViewById(R.id.grocery_summary_tv);
//        medicineTitleText = findViewById(R.id.medicine_summary_title_tv);
//        medicineText = findViewById(R.id.medicine_summary_tv);
//        errandsTitleText = findViewById(R.id.errands_summary_title_tv);
//        errandsText = findViewById(R.id.errands_summary_tv);
    }
//    private void onClickListenersForTexts() {
//        groceryTitleText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Adding groceries!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        groceryText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Adding groceries!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        medicineTitleText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Adding medicines!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        medicineText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Adding medicines!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        errandsTitleText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Adding errands!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        errandsText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Adding errands!", Toast.LENGTH_SHORT).show();
//            }

//        });

    //    }
    private void initializeSharedPrefValues() {
        SharedPreferences prefs = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        locale = Locale.forLanguageTag(prefs.getString("locale", "en"));
    }
//    private void setText() {
//        summaryTitle.setText("Product Stock Summary");
//        groceryTitleText.setText("Grocery");
//        groceryText.setText("Start adding items to your inventory!\n2 Items running out of stock.\n3 Items need replenishment.\nAll good!!, keep it up for maintaining the stock.");
//        medicineTitleText.setText("Medicines");
//        medicineText.setText("Start tracking the medicines you take!");
//        errandsTitleText.setText("Errands");

//        errandsText.setText("Don't have any errands? Add here if any!");

//    }

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