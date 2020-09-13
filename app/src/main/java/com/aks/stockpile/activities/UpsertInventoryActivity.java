package com.aks.stockpile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aks.stockpile.R;
import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.enums.QuantityType;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_INVENTORY_ID;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_UPDATE;

public class UpsertInventoryActivity extends AppCompatActivity {

    MaterialButton saveButton;
    private Toolbar toolbar;
    private Boolean isUpdateRequest;
    private Integer inventoryId;
    private StockpileDaoService daoService;
    private Map<String, CategoryEntity> categoryMap;
    private Map<String, ArticleEntity> articlesMap;
    private String selectedCategory, selectedArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        addToolbarBackButton();
        readIntentVariables();
        addDropDownData();
        setOnClickListeners();
    }

    private void readIntentVariables() {
        Intent extras = getIntent();
        isUpdateRequest = extras.getBooleanExtra(GROCERY_UPSERT_IS_UPDATE, false);
        if (isUpdateRequest) {
            inventoryId = extras.getIntExtra(GROCERY_INVENTORY_ID, 1);
        }
    }

    private void setOnClickListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity("Successfully added the new item for tracking.");
            }
        });
    }

    private void addDropDownData() {
        final AutoCompleteTextView nameDropdown = findViewById(R.id.upsert_name_dropdown_field);
        final AutoCompleteTextView categoryDropdown = findViewById(R.id.upsert_cat_dropdown_field);
        final AutoCompleteTextView quantityTypeDropdown = findViewById(R.id.upsert_quantity_dropdown_field);
        final AutoCompleteTextView sourceDropdown = findViewById(R.id.upsert_source_dropdown_field);

        final ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup);
        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup);
        final ArrayAdapter<String> quantityTypeAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup);
        final ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup, Arrays.asList("Big Basket", "Grofers", "Amazon", "Local Store"));
        sourceDropdown.setAdapter(sourceAdapter);

        if (isUpdateRequest) {
            AggregatedInventory inventory = daoService.getInventoryById(inventoryId);
            categoryDropdown.setText(inventory.getCategory().getName(), false);
            nameDropdown.setText(inventory.getArticle().getName(), false);
            quantityTypeAdapter.clear();
            quantityTypeAdapter.addAll(getQuantityType(inventory.getCategory(), inventory.getArticle()).getWeightMap().values());
            quantityTypeDropdown.setAdapter(quantityTypeAdapter);
        } else {
            if (categoryMap == null) {
                categoryMap = daoService.getCategoriesForDropdown();
            }
            categoryAdapter.clear();
            categoryAdapter.addAll(categoryMap.keySet());
            categoryDropdown.setAdapter(categoryAdapter);
            categoryDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedCategory = categoryDropdown.getAdapter().getItem(i).toString();
                    articlesMap = daoService.getArticlesForDropdown(
                            categoryMap.get(categoryDropdown.getAdapter().getItem(i).toString()).getId());
                    nameAdapter.clear();
                    nameAdapter.addAll(articlesMap.keySet());
                    nameDropdown.setAdapter(nameAdapter);
                }
            });
            nameDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedArticle = nameDropdown.getAdapter().getItem(i).toString();
                    QuantityType quantityType = getQuantityType(categoryMap.get(selectedCategory), articlesMap.get(selectedArticle));
                    quantityTypeAdapter.clear();
                    quantityTypeAdapter.addAll(quantityType.getWeightMap().values());
                    quantityTypeDropdown.setAdapter(quantityTypeAdapter);
                }
            });
        }
    }

    private QuantityType getQuantityType(CategoryEntity category, ArticleEntity article) {
        if (article != null && article.getQuantityType() != null) {
            return article.getQuantityType();
        }
        return category.getQuantityType();
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_upsert_inventory);
        toolbar = findViewById(R.id.upsert_inventory_toolbar);
        saveButton = findViewById(R.id.upsert_save_btn);
        daoService = new StockpileDaoServiceImpl(this);
    }

    private void addToolbarBackButton() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startAlertDialog();
    }

    private void startAlertDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Discard the changes?")
                .setMessage("Are you sure to discard the changes? Any changes done will be lost!")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishActivity("");
                    }
                })
                .show();
    }

    private void finishActivity(final String message) {
        Intent intent = new Intent();
        intent.putExtra("message", message);
        setResult(RESULT_OK, intent);
        finish();
    }

}