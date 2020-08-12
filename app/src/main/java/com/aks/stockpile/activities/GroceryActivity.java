package com.aks.stockpile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.adapters.GroceryHomeAdapter;
import com.aks.stockpile.models.GroceryDetailsDto;
import com.aks.stockpile.models.GroceryHomeCardDto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class GroceryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView oosRecyclerView, nrpRecyclerView;
    private MaterialTextView oosTitle, nrpTitle;
    private MaterialButton viewAllBtn, addInventoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        setTextViewData();
        addToolbarBackButton();
        setRecyclerViewData();
        setButtonOnClick();
    }

    private void setButtonOnClick() {
        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent groceryViewIntent = new Intent(getApplicationContext(), GroceryListActivity.class);
                groceryViewIntent.putParcelableArrayListExtra("data", GroceryDetailsDto.ofTestData());
                startActivity(groceryViewIntent);
            }
        });
        addInventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addGroceryIntent = new Intent(getApplicationContext(), UpsertInventoryActivity.class);
                startActivity(addGroceryIntent);
            }
        });
    }

    private void setTextViewData() {
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
        viewAllBtn = findViewById(R.id.view_all_grocery_btn);
        addInventoryBtn = findViewById(R.id.add_grocery_btn);
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
