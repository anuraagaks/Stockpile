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
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;

import java.util.List;

import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_CATEGORY_ACTIVITY_CATEGORY;

public class GroceryCategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Integer categoryId;
    private RecyclerView recyclerView;
    private StockpileDaoService daoService;
    private CategoryEntity categoryEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        getIntentVariables();
        getCategory();
        addToolbarBackButton();
        initializeRecyclerView();
    }

    private void getIntentVariables() {
        categoryId = getIntent().getIntExtra(GROCERY_CATEGORY_ACTIVITY_CATEGORY, 1);
    }

    private void initializeRecyclerView() {
        List<GroceryDetailsDto> data = daoService.getInventoryByCategory(categoryId);
        GroceryDetailsAdapter groceryDetailsAdapter = new GroceryDetailsAdapter(this, data);
        recyclerView.setAdapter(groceryDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getCategory() {
        categoryEntity = daoService.getCategory(categoryId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu_help, menu);
        return true;
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_grocery_category);
        toolbar = findViewById(R.id.main_toolbar_errands);
        recyclerView = findViewById(R.id.grocery_category_view_recycle);
        daoService = new StockpileDaoServiceImpl(this);
    }

    private void addToolbarBackButton() {
        toolbar.setTitle(categoryEntity.getDisplayName());
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