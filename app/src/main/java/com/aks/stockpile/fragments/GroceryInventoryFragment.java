package com.aks.stockpile.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.GroceryActivity;
import com.aks.stockpile.activities.UpsertInventoryActivity;
import com.aks.stockpile.adapters.GroceryDetailsAdapter;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.enums.SortOption;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_UPDATE;

public class GroceryInventoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private MaterialButton sort, add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setLayoutFields() {
        recyclerView = getView().findViewById(R.id.grocery_inventory_recycle);
        sort = getView().findViewById(R.id.grocery_inventory_sort_btn);
        add = getView().findViewById(R.id.grocery_inventory_add_btn);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setLayoutFields();
        initializeRecyclerView();
        setOnclickListeners();
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
                                dialogInterface.dismiss();
                            }
                        })
                        .setSingleChoiceItems(choices, selectedValue[0], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedValue[0] = selectedIndex;
                            }
                        })
                        .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

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

    public void initializeRecyclerView() {
        List<GroceryDetailsDto> data = ((GroceryActivity) getActivity()).daoService.getAllInventory();
        GroceryDetailsAdapter groceryDetailsAdapter = new GroceryDetailsAdapter(getContext(), data, ((GroceryActivity) getActivity()).daoService);
        recyclerView.setAdapter(groceryDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocery_inventory, container, false);
    }
}