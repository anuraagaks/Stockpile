package com.aks.stockpile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.adapters.GroceryHomeAdapter;
import com.aks.stockpile.models.GroceryHomeCardDto;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class GroceryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView oosRecyclerView, nrpRecyclerView;
    private MaterialTextView oosTitle, nrpTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        setButtonData();
        addToolbarBackButton();
        setRecyclerViewData();
    }

    private void setButtonData() {
        oosTitle.setText("Out of Stock Items");
        nrpTitle.setText("Items to be Restocked");
    }

    private void setRecyclerViewData() {
        GroceryHomeAdapter oosAdapter = new GroceryHomeAdapter(this, GroceryHomeCardDto.ofTest());
        oosRecyclerView.setAdapter(oosAdapter);
        oosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        GroceryHomeAdapter nrpAdapter = new GroceryHomeAdapter(this, GroceryHomeCardDto.ofTest());
        nrpRecyclerView.setAdapter(nrpAdapter);
        nrpRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu_grocery, menu);
        return true;
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_grocery);
        toolbar = findViewById(R.id.main_toolbar_grocery);
        oosRecyclerView = findViewById(R.id.grocery_home_oos_recycle);
        nrpRecyclerView = findViewById(R.id.grocery_home_nrp_recycle);
        oosTitle = findViewById(R.id.out_of_stock_tv);
        nrpTitle = findViewById(R.id.need_replenishment_tv);
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
