package com.aks.stockpile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        navView.setNavigationItemSelectedListener(this);
        initializeSharedPrefValues();
        setText();
        onClickListenersForTexts();

    }

    private void onClickListenersForTexts() {
        groceryTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Adding groceries!", Toast.LENGTH_SHORT).show();
            }
        });
        groceryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Adding groceries!", Toast.LENGTH_SHORT).show();
            }
        });
        medicineTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Adding medicines!", Toast.LENGTH_SHORT).show();
            }
        });
        medicineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Adding medicines!", Toast.LENGTH_SHORT).show();
            }
        });
        errandsTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Adding errands!", Toast.LENGTH_SHORT).show();
            }
        });
        errandsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Adding errands!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeSharedPrefValues() {
        SharedPreferences prefs = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        locale = Locale.forLanguageTag(prefs.getString("locale", "en"));
    }

    private void setText() {
        summaryTitle.setText("Product Stock Summary");
        groceryTitleText.setText("Grocery");
        groceryText.setText("Start adding items to your inventory!\n2 Items running out of stock.\n3 Items need replenishment.\nAll good!!, keep it up for maintaining the stock.");
        medicineTitleText.setText("Medicines");
        medicineText.setText("Start tracking the medicines you take!");
        errandsTitleText.setText("Errands");
        errandsText.setText("Don't have any errands? Add here if any!");
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.home_nav_view);
        toolbar = findViewById(R.id.main_toolbar);
        summaryTitle = findViewById(R.id.home_summary_tv);
        groceryTitleText = findViewById(R.id.grocery_summary_title_tv);
        groceryText = findViewById(R.id.grocery_summary_tv);
        medicineTitleText = findViewById(R.id.medicine_summary_title_tv);
        medicineText = findViewById(R.id.medicine_summary_tv);
        errandsTitleText = findViewById(R.id.errands_summary_title_tv);
        errandsText = findViewById(R.id.errands_summary_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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