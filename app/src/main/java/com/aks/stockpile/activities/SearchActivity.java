package com.aks.stockpile.activities;

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
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StockpileDaoService daoService;
    private TextInputEditText nameField;
    private RelativeLayout relativeLayout;
    private MaterialButton back;

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
                initializeRecyclerView(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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
                final Snackbar snackbar = Utilities.makeSnackbar(this, relativeLayout,
                        String.format("No grocery found in your inventory for %s", name));
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

    private void setLayoutFields() {
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.search_recycle);
        nameField = findViewById(R.id.search_box_field);
        relativeLayout = findViewById(R.id.drawer_layout_search);
        back = findViewById(R.id.search_back);
        daoService = new StockpileDaoServiceImpl(this);
    }
}
