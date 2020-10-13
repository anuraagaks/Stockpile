package com.aks.stockpile.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.adapters.GroceryDetailsAdapter;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.services.IGroceryRefresher;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements IGroceryRefresher {

    private RecyclerView recyclerView;
    private StockpileDaoService daoService;
    private TextInputEditText nameField;
    private RelativeLayout relativeLayout;
    private MaterialButton back;
    private String enteredName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        setOnclickListeners();
    }

    private void setOnclickListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enteredName = charSequence.toString();
                initializeRecyclerView(enteredName);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 112) {
            String message = data.getStringExtra("message");
            if (message != null && !message.equals("")) {
                Utilities.makeSnackbar(this, relativeLayout, message, Snackbar.LENGTH_LONG)
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

    private void initializeRecyclerView(String name) {
        GroceryDetailsAdapter groceryDetailsAdapter;
        if (name.length() == 0) {
            groceryDetailsAdapter = new GroceryDetailsAdapter(this, new ArrayList<GroceryDetailsDto>(), daoService);
            recyclerView.setAdapter(groceryDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            List<GroceryDetailsDto> data = daoService.searchInventory(name.toLowerCase());
            if (data.size() > 0) {
                groceryDetailsAdapter = new GroceryDetailsAdapter(this, data, daoService);
                recyclerView.setAdapter(groceryDetailsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                groceryDetailsAdapter = new GroceryDetailsAdapter(this, new ArrayList<GroceryDetailsDto>(), daoService);
                recyclerView.setAdapter(groceryDetailsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                final Snackbar snackbar = Utilities.makeSnackbar(this, relativeLayout,
                        String.format("No grocery found in your inventory for %s", name), Snackbar.LENGTH_LONG);
                ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setMaxLines(4);
                snackbar.setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                }).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshRecycler();
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.search_recycle);
        nameField = findViewById(R.id.search_box_field);
        relativeLayout = findViewById(R.id.drawer_layout_search);
        back = findViewById(R.id.search_back);
        daoService = new StockpileDaoServiceImpl(this);
    }

    @Override
    public void refreshRecycler() {
        if (enteredName != null) {
            initializeRecyclerView(enteredName);
        } else {
            initializeRecyclerView("");
        }
    }
}
