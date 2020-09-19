package com.aks.stockpile.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aks.stockpile.R;

public class MedicineActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        addToolbarBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu_grocery, menu);
        menu.getItem(0).getIcon().setColorFilter(getResources().getColor(R.color.item_on_primary, getTheme()), PorterDuff.Mode.SRC_ATOP);
        menu.getItem(1).getIcon().setColorFilter(getResources().getColor(R.color.item_on_primary, getTheme()), PorterDuff.Mode.SRC_ATOP);
        return true;
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_medicine);
        toolbar = findViewById(R.id.main_toolbar_medicine);
    }

    private void addToolbarBackButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
