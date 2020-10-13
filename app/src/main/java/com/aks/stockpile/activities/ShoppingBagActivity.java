package com.aks.stockpile.activities;

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
import com.aks.stockpile.adapters.ShoppingAdapter;
import com.aks.stockpile.models.dtos.AggregatedExpenditure;
import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.dtos.ShoppingDetailDto;
import com.aks.stockpile.models.enums.InventoryUpdateType;
import com.aks.stockpile.services.IShoppingRefresher;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShoppingBagActivity extends AppCompatActivity implements IShoppingRefresher {

    private Toolbar toolbar;
    private StockpileDaoService daoService;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutFields();
        addToolbarBackButton();
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        List<ShoppingDetailDto> data = buildData();
        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(this, data);
        recyclerView.setAdapter(shoppingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (data.isEmpty()) {
            recyclerView.setAdapter(shoppingAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            final Snackbar snackbar = Utilities.makeSnackbar(this, drawerLayout,
                    "Nothing in your bag! Add your inventory into shopping bag from My Inventory Tab.", Snackbar.LENGTH_INDEFINITE);
            ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setMaxLines(4);
            snackbar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            }).show();
        }
    }

    private List<ShoppingDetailDto> buildData() {
        Set<String> shoppingIdsStrings = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getStringSet("Shopping_cart", new HashSet<String>());
        List<Integer> shoppingIds = new ArrayList<>();
        for (String shoppingIdsString : shoppingIdsStrings) {
            shoppingIds.add(Integer.parseInt(shoppingIdsString));
        }
        List<AggregatedInventory> inventories = daoService.findInventoryIn(shoppingIds);
        List<ShoppingDetailDto> shoppingDetails = new ArrayList<>();
        for (AggregatedInventory inventory : inventories) {
            ShoppingDetailDto dto = new ShoppingDetailDto();
            dto.setId(inventory.getInventory().getId());
            dto.setName(inventory.getInventory().getName());
            dto.setCategoryId(inventory.getCategory().getId());
            dto.setCategoryName(inventory.getCategory().getName());
            dto.setImageResourceId(inventory.getCategory().getImageResource());
            dto.setQuantity(inventory.getInventory().getQuantity());
            dto.setQuantityType(Utilities.getQuantityType(inventory.getCategory(), inventory.getArticle()).getValue());
            List<AggregatedExpenditure> expenditures = daoService.getExpenditureByInventoryId(inventory.getInventory().getId());
            Map<String, Integer> sources = new HashMap<>();
            for (AggregatedExpenditure expenditure : expenditures) {
                if (expenditure.getExpenditure().getUpdateType() == InventoryUpdateType.ADDED) {
                    String source = expenditure.getExpenditure().getSource();
                    if (sources.get(source) != null) {
                        sources.put(source, sources.get(source) + 1);
                    } else {
                        sources.put(source, 1);
                    }
                }
            }
            int max = 0;
            String maxSource = null;
            for (Map.Entry<String, Integer> sourcesEntry : sources.entrySet()) {
                if (sourcesEntry.getValue() > max) {
                    max = sourcesEntry.getValue();
                    maxSource = sourcesEntry.getKey();
                }
            }
            dto.setSource(maxSource);
            shoppingDetails.add(dto);
        }
        return shoppingDetails;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu_help, menu);
        return true;
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_shopping_bag);
        toolbar = findViewById(R.id.main_toolbar_shopping);
        recyclerView = findViewById(R.id.shopping_recycle);
        drawerLayout = findViewById(R.id.drawer_layout_shopping);
        daoService = new StockpileDaoServiceImpl(this);
    }

    private void addToolbarBackButton() {
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