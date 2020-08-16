package com.aks.stockpile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aks.stockpile.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class UpsertInventoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    MaterialButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        addToolbarBackButton();
        addDropDownData();
        setOnClickListeners();
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
        String[] COUNTRIES = new String[]{"Item 1", "Item 2", "Item 3", "Item 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup, COUNTRIES);
        AutoCompleteTextView subCategoryDropdown = findViewById(R.id.upsert_sub_cat_dropdown_field);
        subCategoryDropdown.setAdapter(adapter);
        AutoCompleteTextView nameDropdown = findViewById(R.id.upsert_name_dropdown_field);
        nameDropdown.setAdapter(adapter);
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_upsert_inventory);
        toolbar = findViewById(R.id.upsert_inventory_toolbar);
        saveButton = findViewById(R.id.upsert_save_btn);
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