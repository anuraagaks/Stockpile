package com.aks.stockpile.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.UpsertInventoryActivity;
import com.aks.stockpile.adapters.GroceryDetailsAdapter;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.enums.SortOption;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_UPDATE;

public class GroceryInventoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private MaterialButton sort, add;
    private StockpileDaoService daoService;
    private FrameLayout frameLayout;
    private Snackbar snackbar;
    private boolean showSnackbar;

    public GroceryInventoryFragment(StockpileDaoService daoService) {
        this.daoService = daoService;
        showSnackbar = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setLayoutFields() {
        recyclerView = getView().findViewById(R.id.grocery_inventory_recycle);
        sort = getView().findViewById(R.id.grocery_inventory_sort_btn);
        add = getView().findViewById(R.id.grocery_inventory_add_btn);
        frameLayout = getView().findViewById(R.id.grocery_inventory_fragment);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setLayoutFields();
        initializeRecyclerView(SortOption.DEFAULT);
        setOnclickListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeRecyclerView(SortOption.DEFAULT);
    }

    private void setOnclickListeners() {
        final String[] choices = SortOption.getValues();
        final Integer[] selectedValue = {0};
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle(R.string.sort_by)
                        .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.sort, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                initializeRecyclerView(SortOption.ofName(SortOption.getValues()[selectedValue[0]]));
                            }
                        })
                        .setSingleChoiceItems(choices, selectedValue[0], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedValue[0] = selectedIndex;
                            }
                        })
                        .setCancelable(false)
                        .show();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upsertIntent = new Intent(getContext(), UpsertInventoryActivity.class);
                upsertIntent.putExtra(GROCERY_UPSERT_IS_UPDATE, false);
                getActivity().startActivityForResult(upsertIntent, 112);
            }
        });
    }

    public void initializeRecyclerView(SortOption sortOption) {
        List<GroceryDetailsDto> data = getData(sortOption);
        showSnackbar = false;
        GroceryDetailsAdapter groceryDetailsAdapter = new GroceryDetailsAdapter(getContext(), data, daoService);
        recyclerView.setAdapter(groceryDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (data.isEmpty()) {
            showSnackbar = true;
            snackbar = Utilities.makeSnackbar(getContext(), frameLayout, "No Grocery available! Try adding some from the add button above.", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
        }
    }

    private List<GroceryDetailsDto> getData(SortOption sortOption) {
        switch (sortOption) {
            case NAME_A_Z:
                return daoService.getAllInventoryNameAZ();
            case NAME_Z_A:
                return daoService.getAllInventoryNameZA();
            case QUANTITY_H_L:
                return daoService.getAllInventoryQuantityHL();
            case QUANTITY_L_H:
                return daoService.getAllInventoryQuantityLH();
            default:
                return daoService.getAllInventory();
        }
    }

    @Override
    public void setMenuVisibility(boolean isvisible) {
        super.setMenuVisibility(isvisible);
        if (isvisible && showSnackbar && snackbar != null && !snackbar.isShown()) {
            snackbar.show();
        } else if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocery_inventory, container, false);
    }
}