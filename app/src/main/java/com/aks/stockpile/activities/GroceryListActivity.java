package com.aks.stockpile.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.adapters.GroceryDetailsAdapter;
import com.aks.stockpile.models.GroceryDetailsDto;

import java.util.ArrayList;
import java.util.Objects;

public class GroceryListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        addToolbarBackButton();
        setRecyclerViewData();
    }

    private void setRecyclerViewData() {
        ArrayList<GroceryDetailsDto> dtos = getIntent().getParcelableArrayListExtra("data");
        GroceryDetailsAdapter oosAdapter = new GroceryDetailsAdapter(this, dtos);
        recyclerView.setAdapter(oosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu_grocery, menu);
        return true;
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_grocery_list);
        toolbar = findViewById(R.id.details_toolbar_grocery);
        recyclerView = findViewById(R.id.grocery_details_recycle);
    }

    private void addToolbarBackButton() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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