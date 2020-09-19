package com.aks.stockpile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aks.stockpile.R;
import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.dtos.InventoryDto;
import com.aks.stockpile.models.entities.AbstractEntity;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.enums.QuantityType;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;
import com.aks.stockpile.utils.InventoryUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_INVENTORY_ID;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_NAME;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_AVAILABLE_QUANTITY;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_AVAILABLE_QUANTITY_TYPE;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_CONSUME;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_UPDATE;

public class UpsertInventoryActivity extends AppCompatActivity {

    private MaterialButton saveButton;
    private Toolbar toolbar;
    private Boolean isUpdateRequest, isConsumeRequest;
    private Integer inventoryId;
    private Double availableQuantity;
    private StockpileDaoService daoService;
    private Map<String, CategoryEntity> categoryMap;
    private Map<String, ArticleEntity> articlesMap;
    private String selectedCategory, selectedArticle, availableQuantityType;
    private AutoCompleteTextView nameDropdown, categoryDropdown, quantityTypeDropdown, sourceDropdown;
    private TextInputLayout categoryInput, nameInput, quantityInput, quantityTypeInput, sourceInput, priceInput, descriptionInput;
    private TextInputEditText quantity, description, price;

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
        isConsumeRequest = extras.getBooleanExtra(GROCERY_UPSERT_IS_CONSUME, false);
        String name;
        if (isUpdateRequest) {
            inventoryId = extras.getIntExtra(GROCERY_INVENTORY_ID, 1);
            name = extras.getStringExtra(GROCERY_NAME);
            setTitle("Top-up " + name);
        } else if (isConsumeRequest) {
            inventoryId = extras.getIntExtra(GROCERY_INVENTORY_ID, 1);
            availableQuantity = extras.getDoubleExtra(GROCERY_UPSERT_AVAILABLE_QUANTITY, 100000.0);
            availableQuantityType = extras.getStringExtra(GROCERY_UPSERT_AVAILABLE_QUANTITY_TYPE);
            name = extras.getStringExtra(GROCERY_NAME);
            setTitle("Consume " + name);
        } else {
            setTitle("Track a new grocery");
        }
    }

    private void setOnClickListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveData())
                    if (isConsumeRequest)
                        finishActivity("Consumed " + selectedArticle + ".");
                    else if (isUpdateRequest)
                        finishActivity("Updated " + selectedArticle + ".");
                    else
                        finishActivity("Started tracking " + selectedArticle + ".");
            }
        });
    }

    private boolean saveData() {
        if (isConsumeRequest) {
            if (validateFields(categoryDropdown.getText(), categoryInput) || validateFields(nameDropdown.getText(), nameInput)
                    || validateFields(quantity.getText(), quantityInput))
                return false;
            Double factor = InventoryUtils.getQuantityType(categoryMap.get(selectedCategory), articlesMap.get(selectedArticle))
                    .getWeightMap().get(quantity.getText().toString());
            if (Double.parseDouble(quantity.getText().toString()) > factor * availableQuantity) {
                quantityInput.requestFocus();
                quantityInput.setError("Available quantity is only " + availableQuantity + availableQuantityType);
                return false;
            }
            InventoryDto dto = buildDto(inventoryId);
            daoService.updateInventory(dto);
        } else {
            if (validateFields(categoryDropdown.getText(), categoryInput) || validateFields(nameDropdown.getText(), nameInput)
                    || validateFields(quantityTypeDropdown.getText(), quantityTypeInput) || validateFields(quantity.getText(), quantityInput)
                    || validateFields(sourceDropdown.getText(), sourceInput) || validateFields(price.getText(), priceInput))
                return false;
            Integer categoryId = fetchId(selectedCategory, categoryMap, categoryInput);
            Integer articleId = fetchId(selectedArticle, articlesMap, nameInput);
            Integer inventoryId = daoService.getInventoryIdByCategoryAndArticle(categoryId, nameDropdown.getText().toString());
            if (categoryId == 0) {
                return false;
            }
            InventoryDto dto = buildDto(categoryId, articleId, inventoryId);
            daoService.saveInventory(dto);
        }
        return true;
    }

    private boolean validateFields(Editable textField, TextInputLayout inputLayout) {
        if (textField == null || textField.toString().trim().isEmpty()) {
            inputLayout.requestFocus();
            inputLayout.setError("This is a required field!");
            return true;
        }
        return false;
    }

    private InventoryDto buildDto(Integer categoryId, Integer articleId, Integer inventoryId) {
        InventoryDto dto = new InventoryDto();
        dto.setArticleId(articleId);
        dto.setCategoryId(categoryId);
        dto.setName(nameDropdown.getText().toString());
        if (inventoryId != null && inventoryId != 0) {
            dto.setId(inventoryId);
        }
        String quantityString = quantity.getText().toString();
        dto.setQuantity(Double.parseDouble(String.format("%.2f", Double.parseDouble(quantityString))));
        dto.setQuantityType(InventoryUtils.getQuantityType(categoryMap.get(selectedCategory), articlesMap.get(selectedArticle)));
        dto.setQuantityTypeName(quantityTypeDropdown.getText().toString());
        dto.setPrice(Double.parseDouble(price.getText().toString()));
        if (description.getText() != null)
            dto.setDescription(description.getText().toString());
        dto.setSource(sourceDropdown.getText().toString());
        return dto;
    }

    private InventoryDto buildDto(Integer inventoryId) {
        InventoryDto dto = new InventoryDto();
        dto.setId(inventoryId);
        String quantityString = quantity.getText().toString();
        dto.setQuantityType(InventoryUtils.getQuantityType(categoryMap.get(selectedCategory), articlesMap.get(selectedArticle)));
        dto.setQuantityTypeName(quantityTypeDropdown.getText().toString());
        dto.setQuantity(Double.parseDouble(String.format("%.2f", Double.parseDouble(quantityString))));
        return dto;
    }

    private Integer fetchId(String selectedValue, Map<String, ? extends AbstractEntity> valueMap,
                            TextInputLayout inputLayout) {
        if (valueMap.get(selectedValue) == null) {
            inputLayout.requestFocus();
            inputLayout.setError("Invalid value - " + selectedValue + ", please select from the list!");
            return 0;
        } else {
            return valueMap.get(selectedValue).getId();
        }
    }

    private void addDropDownData() {
        nameDropdown = findViewById(R.id.upsert_name_dropdown_field);
        categoryDropdown = findViewById(R.id.upsert_cat_dropdown_field);
        quantityTypeDropdown = findViewById(R.id.upsert_quantity_dropdown_field);
        sourceDropdown = findViewById(R.id.upsert_source_dropdown_field);

        final ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup);
        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup);
        final ArrayAdapter<String> quantityTypeAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup);
        final ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup, Arrays.asList("Big Basket", "Grofers", "Amazon", "Local Store"));
        sourceDropdown.setAdapter(sourceAdapter);

        if (isConsumeRequest) {
            final AggregatedInventory inventory = daoService.getInventoryById(inventoryId);
            categoryDropdown.setText(inventory.getCategory().getName(), false);
            nameDropdown.setText(inventory.getInventory().getName(), false);
            categoryDropdown.setInputType(0);
            nameDropdown.setInputType(0);
            selectedCategory = inventory.getCategory().getName();
            selectedArticle = inventory.getInventory().getName();
            categoryMap = new TreeMap<String, CategoryEntity>(String.CASE_INSENSITIVE_ORDER) {{
                put(selectedCategory, inventory.getCategory());
            }};
            articlesMap = new TreeMap<String, ArticleEntity>(String.CASE_INSENSITIVE_ORDER) {{
                put(selectedArticle, inventory.getArticle());
            }};
            quantityTypeAdapter.clear();
            quantityTypeAdapter.addAll(InventoryUtils.getQuantityType(inventory.getCategory(), inventory.getArticle()).getWeightMap().keySet());
            quantityTypeDropdown.setAdapter(quantityTypeAdapter);
            sourceInput.setVisibility(View.GONE);
            descriptionInput.setVisibility(View.GONE);
            priceInput.setVisibility(View.GONE);
        } else if (isUpdateRequest) {
            final AggregatedInventory inventory = daoService.getInventoryById(inventoryId);
            categoryDropdown.setText(inventory.getCategory().getName(), false);
            nameDropdown.setText(inventory.getInventory().getName(), false);
            categoryDropdown.setInputType(0);
            nameDropdown.setInputType(0);
            selectedCategory = inventory.getCategory().getName();
            selectedArticle = inventory.getInventory().getName();
            categoryMap = new TreeMap<String, CategoryEntity>(String.CASE_INSENSITIVE_ORDER) {{
                put(selectedCategory, inventory.getCategory());
            }};
            articlesMap = new TreeMap<String, ArticleEntity>(String.CASE_INSENSITIVE_ORDER) {{
                put(selectedArticle, inventory.getArticle());
            }};
            quantityTypeAdapter.clear();
            quantityTypeAdapter.addAll(InventoryUtils.getQuantityType(inventory.getCategory(), inventory.getArticle()).getWeightMap().keySet());
            quantityTypeDropdown.setAdapter(quantityTypeAdapter);
        } else {
            categoryMap = daoService.getCategoriesForDropdown();
            categoryAdapter.clear();
            categoryAdapter.addAll(categoryMap.keySet());
            categoryDropdown.setAdapter(categoryAdapter);
            categoryDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedCategory = categoryDropdown.getAdapter().getItem(i).toString();
                    nameDropdown.setText("");
                    quantityTypeDropdown.setText("");
                    articlesMap = daoService.getArticlesForDropdown(
                            categoryMap.get(categoryDropdown.getAdapter().getItem(i).toString()).getId());
                    nameAdapter.clear();
                    nameAdapter.addAll(articlesMap.keySet());
                    nameDropdown.setAdapter(nameAdapter);
                    quantityTypeDropdown.setText("");
                    QuantityType quantityType = InventoryUtils.getQuantityType(categoryMap.get(selectedCategory), null);
                    quantityTypeAdapter.clear();
                    quantityTypeAdapter.addAll(quantityType.getWeightMap().keySet());
                    quantityTypeDropdown.setAdapter(quantityTypeAdapter);
                }
            });
            nameDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedArticle = nameDropdown.getAdapter().getItem(i).toString();
                    quantityTypeDropdown.setText("");
                    QuantityType quantityType = InventoryUtils.getQuantityType(categoryMap.get(selectedCategory),
                            articlesMap.get(selectedArticle));
                    quantityTypeAdapter.clear();
                    quantityTypeAdapter.addAll(quantityType.getWeightMap().keySet());
                    quantityTypeDropdown.setAdapter(quantityTypeAdapter);
                }
            });
        }
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_upsert_inventory);
        daoService = new StockpileDaoServiceImpl(this);
        toolbar = findViewById(R.id.upsert_inventory_toolbar);
        saveButton = findViewById(R.id.upsert_save_btn);
        categoryInput = findViewById(R.id.upsert_cat_dropdown_ipl);
        nameInput = findViewById(R.id.upsert_name_dropdown_ipl);
        quantityInput = findViewById(R.id.upsert_quantity_ipl);
        quantityTypeInput = findViewById(R.id.upsert_quantity_dropdown_ipl);
        sourceInput = findViewById(R.id.upsert_source_dropdown_ipl);
        priceInput = findViewById(R.id.upsert_price_ipl);
        descriptionInput = findViewById(R.id.upsert_description_ipl);
        price = findViewById(R.id.upsert_price_field);
        quantity = findViewById(R.id.upsert_quantity_field);
        description = findViewById(R.id.upsert_description_field);
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