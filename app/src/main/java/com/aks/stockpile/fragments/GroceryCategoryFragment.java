package com.aks.stockpile.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.UpsertInventoryActivity;
import com.aks.stockpile.adapters.GroceryCategoryAdapter;
import com.aks.stockpile.models.dtos.GroceryCategoryCardDto;
import com.aks.stockpile.services.StockpileDaoService;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_UPDATE;

public class GroceryCategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private MaterialButton add;
    private StockpileDaoService daoService;

    public GroceryCategoryFragment(StockpileDaoService daoService) {
        this.daoService = daoService;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setLayoutFields() {
        recyclerView = getView().findViewById(R.id.grocery_category_recycle);
        add = getView().findViewById(R.id.grocery_category_add_btn);
    }

    private void initializeRecyclerView() {
        List<GroceryCategoryCardDto> data = daoService.getAllCategories();
        GroceryCategoryAdapter homePageAdapter = new GroceryCategoryAdapter(getContext(), data);
        recyclerView.setAdapter(homePageAdapter);
        int orientationValue = getResources().getConfiguration().orientation;
        if (orientationValue == 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        if (orientationValue == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocery_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setLayoutFields();
        initializeRecyclerView();
        setOnclickListeners();
    }

    private void setOnclickListeners() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upsertIntent = new Intent(getContext(), UpsertInventoryActivity.class);
                upsertIntent.putExtra(GROCERY_UPSERT_IS_UPDATE, false);
                getActivity().startActivityForResult(upsertIntent, 112);
            }
        });
    }
}