package com.aks.stockpile.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.adapters.GroceryDetailsAdapter;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.services.IGroceryRefresher;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_CATEGORY_ACTIVITY_CATEGORY;

public class GroceryCategoryActivity extends AppCompatActivity implements IGroceryRefresher {

    private Toolbar toolbar;
    private Integer categoryId;
    private RecyclerView recyclerView;
    private StockpileDaoService daoService;
    private CategoryEntity categoryEntity;
    private DrawerLayout drawerLayout;

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
        GroceryDetailsAdapter groceryDetailsAdapter = new GroceryDetailsAdapter(this, data, daoService);
        recyclerView.setAdapter(groceryDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (data.isEmpty()) {
            final Snackbar snackbar = Utilities.makeSnackbar(this, drawerLayout,
                    String.format("No Grocery available in %s! Try adding some from the add button in previous screen.", categoryEntity.getName()), Snackbar.LENGTH_INDEFINITE);
            ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setMaxLines(4);
            snackbar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            }).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 112) {
            String message = data.getStringExtra("message");
            if (message != null && !message.equals("")) {
                Utilities.makeSnackbar(this, drawerLayout, message, Snackbar.LENGTH_LONG)
                        .show();
            }
            String isOOSName = data.getStringExtra("isOOSName");
            boolean isOOS = data.getBooleanExtra("isOOS", false);
            if (isOOS && isOOSName != null) {
                Utilities.sendNotification(this, (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE),
                        isOOSName + " is out of stock", isOOSName + " is out of stock now, it was added to the shopping bag. Click here for shopping bag.", "Out of Stock");
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        initializeRecyclerView();
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_grocery_category);
        toolbar = findViewById(R.id.main_toolbar_grocery_category);
        recyclerView = findViewById(R.id.grocery_category_view_recycle);
        drawerLayout = findViewById(R.id.drawer_layout_grocery_category);
        daoService = new StockpileDaoServiceImpl(this);
    }

    private void addToolbarBackButton() {
        toolbar.setTitle(categoryEntity.getName());
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

    @Override
    public void refreshRecycler() {
        initializeRecyclerView();
    }
}