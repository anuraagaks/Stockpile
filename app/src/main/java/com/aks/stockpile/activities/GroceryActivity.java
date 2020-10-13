package com.aks.stockpile.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.aks.stockpile.R;
import com.aks.stockpile.adapters.GroceryFragmentAdapter;
import com.aks.stockpile.fragments.ExpenditureFragment;
import com.aks.stockpile.fragments.GroceryCategoryFragment;
import com.aks.stockpile.fragments.GroceryInventoryFragment;
import com.aks.stockpile.fragments.OutOfStockFragment;
import com.aks.stockpile.models.enums.SortOption;
import com.aks.stockpile.services.IGroceryRefresher;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class GroceryActivity extends AppCompatActivity implements IGroceryRefresher {

    public StockpileDaoService daoService;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private GroceryCategoryFragment groceryCategoryFragment;
    private GroceryInventoryFragment groceryInventoryFragment;
    private OutOfStockFragment outOfStockFragment;
    private ExpenditureFragment expenditureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFields();
        setLayoutFields();
        addToolbarBackButton();
        initializeFragments();
    }

    private void initializeFields() {
        daoService = new StockpileDaoServiceImpl(this);
        groceryCategoryFragment = new GroceryCategoryFragment(daoService);
        groceryInventoryFragment = new GroceryInventoryFragment(daoService);
        outOfStockFragment = new OutOfStockFragment(daoService);
        expenditureFragment = new ExpenditureFragment(daoService);
    }

    private void initializeFragments() {
        tabLayout.setupWithViewPager(viewPager);
        GroceryFragmentAdapter viewPagerAdapter = new GroceryFragmentAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(expenditureFragment, "Expenditure");
        viewPagerAdapter.addFragment(groceryInventoryFragment, "My Inventory");
        viewPagerAdapter.addFragment(groceryCategoryFragment, "Categories");
        viewPagerAdapter.addFragment(outOfStockFragment, "Out of Stock");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.expenditure);
        tabLayout.getTabAt(1).setIcon(R.drawable.inventory);
        tabLayout.getTabAt(2).setIcon(R.drawable.categories);
        tabLayout.getTabAt(3).setIcon(R.drawable.error);

        viewPager.setCurrentItem(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int oosCount = daoService.getOutOfStock().size();
        BadgeDrawable outOfStockBadge = tabLayout.getTabAt(3).getOrCreateBadge();
        outOfStockBadge.setNumber(oosCount);
        if (oosCount > 0) {
            outOfStockBadge.setVisible(true);
        } else {
            outOfStockBadge.setVisible(false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu_grocery, menu);
        menu.getItem(0).getIcon().setColorFilter(getResources().getColor(R.color.item_on_primary, getTheme()), PorterDuff.Mode.SRC_ATOP);
        menu.getItem(1).getIcon().setColorFilter(getResources().getColor(R.color.item_on_primary, getTheme()), PorterDuff.Mode.SRC_ATOP);
        return true;
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_grocery);
        drawerLayout = findViewById(R.id.drawer_layout_grocery);
        toolbar = findViewById(R.id.main_toolbar_grocery);
        viewPager = findViewById(R.id.grocery_view_pager);
        tabLayout = findViewById(R.id.grocery_tab_layout);
    }

    private void addToolbarBackButton() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem != 1)
            viewPager.setCurrentItem(1);
        else
            finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.search) {
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }
        if (item.getItemId() == R.id.shopping_cart) {
            Intent searchIntent = new Intent(this, ShoppingBagActivity.class);
            startActivity(searchIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshRecycler() {
        groceryInventoryFragment.initializeRecyclerView(SortOption.DEFAULT);
        outOfStockFragment.initializeRecyclerView();
    }
}
