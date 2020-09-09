package com.aks.stockpile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.adapters.GroceryCategoryAdapter;
import com.aks.stockpile.models.dtos.GroceryCategoryCardDto;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;

import java.util.List;

public class GroceryCategoryFragment extends Fragment {

    private RecyclerView recyclerView;

    private StockpileDaoService daoService;

    public GroceryCategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setLayoutFields() {
        recyclerView = getView().findViewById(R.id.grocery_category_recycle);
        daoService = new StockpileDaoServiceImpl(getContext());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocery_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setLayoutFields();
        initializeRecyclerView();
    }
}